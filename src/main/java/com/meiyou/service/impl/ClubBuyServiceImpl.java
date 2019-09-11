package com.meiyou.service.impl;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.meiyou.mapper.ClubBuyMapper;
import com.meiyou.mapper.ClubMapper;
import com.meiyou.mapper.ClubStarMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.model.AskerVO;
import com.meiyou.model.ClubVO;
import com.meiyou.myEnum.StateEnum;
import com.meiyou.pojo.*;
import com.meiyou.service.ClubBuyService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 会所购买业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-21 15:38
 **/
@Service
public class ClubBuyServiceImpl extends BaseServiceImpl implements ClubBuyService {

    @Autowired
    ClubBuyMapper clubBuyMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ClubMapper clubMapper;

    @Autowired
    ClubStarMapper clubStarMapper;

    /**
     * 生成购买会所记录
     * @param clubBuy
     * @return
     */
    @Transactional
    @Override
    public Msg addBuyClub(ClubBuy clubBuy,String token,Integer password) {
        if(!RedisUtil.authToken(clubBuy.getBuyerId().toString(),token)){
            return Msg.noLogin();
        }
        Msg msg = new Msg();

        //获取会所发布者id，对比购买者id，防止自己买
        Integer publishId = clubMapper.selectByPrimaryKey(clubBuy.getClubId()).getPublishId();
        if(publishId.equals(clubBuy.getBuyerId())){
            msg.setCode(501);
            msg.setMsg("自己不允许购买");
            return msg;
        }

        clubBuy.setCreateTime(new Date());
        clubBuy.setUpdateTime(new Date());
        clubBuy.setState(StateEnum.INIT.getValue());

        //从系统数据表获取报名费用
        //String ask_money = getRootMessage("ask_money");

        //获取购买者的支付密码,余额及需要支付的费用(会所项目缴费)
        User result = getUserByUid(clubBuy.getBuyerId());
        String payWord = result.getPayWord();
        Float money = result.getMoney();
        Integer price = clubMapper.selectByPrimaryKey(clubBuy.getClubId()).getProjectPrice();

        if(payWord == null){
            msg.setMsg("请设置支付密码!");
            msg.setCode(1000);
            return msg;
        }
        if(!password.toString().equals(payWord)){
            msg.setMsg("支付密码错误!");
            msg.setCode(1001);
            return msg;
        }
        if(money < price){
            msg.setMsg("发布失败,账户余额不足!");
            msg.setCode(1002);
            return msg;
        }else {
            clubBuyMapper.insertSelective(clubBuy);

            //计算剩余金额
            User user = new User();
            money = money - Float.valueOf(price);
            user.setMoney(money);
            Date date = new Date();
            user.setUpdateTime(date);

            //执行扣钱的操作
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(clubBuy.getBuyerId());
            userMapper.updateByExampleSelective(user, userExample);

            return Msg.success();
        }
    }

    /**
     * 取消购买(退款全部)
     * @param uid
     * @param clubBuyId
     * @param token
     * @return
     */
    @Transactional
    @Override
    public Msg updateBuyClub(Integer uid,Integer clubBuyId,String token) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }

        Msg msg = new Msg();
        //从系统数据表获取置顶费用
        String ask_money = getRootMessage("ask_money");

        ClubBuy buy = clubBuyMapper.selectByPrimaryKey(clubBuyId);
        if(buy == null){
            msg.setMsg("没找到这条记录");
            msg.setCode(404);
            return msg;
        }

        //获取项目金额
        Integer projectPrice = clubMapper.selectByPrimaryKey(buy.getClubId()).getProjectPrice();

        //修改购买表状态
        ClubBuy clubBuy = new ClubBuy();
        clubBuy.setState(StateEnum.INVALID.getValue());
        clubBuy.setUpdateTime(new Date());

        ClubBuyExample clubBuyExample = new ClubBuyExample();
        clubBuyExample.createCriteria().andIdEqualTo(clubBuyId);
        clubBuyMapper.updateByExampleSelective(clubBuy,clubBuyExample);

        //退钱操作
        Float money = Float.valueOf(ask_money)+Float.valueOf(projectPrice);
        User user = getUserByUid(uid);
        user.setMoney(user.getMoney()+money);
        user.setUpdateTime(new Date());

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(uid);
        userMapper.updateByExampleSelective(user,userExample);

        return Msg.success();
    }

    /**
     * 修改状态为已到店(已完成)--->>1
     * @param uid
     * @param clubBuyId 购买按摩会所这条记录的ID
     * @param token
     * @return
     */
    @Override
    public Msg updateClubBuyComplete(Integer uid, Integer clubBuyId, String token) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }

        //修改购买表状态
        ClubBuy clubBuy = new ClubBuy();
        clubBuy.setState(StateEnum.COMPLETE.getValue());
        clubBuy.setUpdateTime(new Date());

        ClubBuyExample clubBuyExample = new ClubBuyExample();
        clubBuyExample.createCriteria().andIdEqualTo(clubBuyId);
        int rows = clubBuyMapper.updateByExampleSelective(clubBuy, clubBuyExample);
        if(rows < 1){
            return Msg.fail();
        }

        return Msg.success();
    }

    /**
     * 查找当前用户指定的会所购买记录
     * @param uid
     * @param cid
     * @param token
     * @return
     */
    @Override
    public Msg selectByCidAndUid(Integer uid, Integer cid, String token) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }

        ClubBuyExample clubBuyExample = new ClubBuyExample();
        clubBuyExample.createCriteria().andClubIdEqualTo(cid).andBuyerIdEqualTo(uid);
        List<ClubBuy> result = clubBuyMapper.selectByExample(clubBuyExample);
        Msg msg = new Msg();
        if(result.size() ==0 && result == null){
            msg.setCode(404);
            msg.setMsg("找不到指定的会所购买记录");
            return msg;
        }

        //对查找出来的ClubBuy进行封装
        Club club = clubMapper.selectByPrimaryKey(result.get(0).getClubId());
        ClubVO clubVO = setClubToClubVO(club);
        clubVO.setAskState(result.get(0).getState());

        msg.add("clubVO", clubVO);
        msg.setMsg("成功");
        msg.setCode(100);
        return msg;
    }

    /**
     * 查找购买了cid的全部购买记录
     * @param uid
     * @param cid
     * @param token
     * @return
     */
    @Override
    public Msg selectByCid(Integer uid, Integer cid, String token) {
//        if(!RedisUtil.authToken(uid.toString(),token)){
//            return Msg.noLogin();
//        }

        Msg msg = new Msg();
        //判断访问者是否为发布者
        Integer publishId = clubMapper.selectByPrimaryKey(cid).getPublishId();
        if(publishId != uid){
            msg.setCode(506);
            msg.setMsg("没有访问权限");
            return msg;
        }

        //查找购买按摩会所的记录
        ClubBuyExample clubBuyExample = new ClubBuyExample();
        //购买者了id为cid的所有购买记录
        clubBuyExample.createCriteria().andClubIdEqualTo(cid);

        List<ClubBuy> result = clubBuyMapper.selectByExample(clubBuyExample);

        if(result.isEmpty()){
            msg.setCode(404);
            msg.setMsg("找不到指定的导游聘请记录");
            return msg;
        }

        //对查找出来的ClubBuy进行封装
        List<AskerVO> askerVOS = new ArrayList<>();
        for(ClubBuy c : result){
            User buyer = getUserByUid(c.getBuyerId());

            AskerVO askerVO = new AskerVO();
            askerVO.setId(buyer.getId());
            askerVO.setNickname(buyer.getNickname());
            askerVO.setHeader(buyer.getHeader());
            askerVO.setBirthday(buyer.getBirthday());
            askerVO.setSex(buyer.getSex());
            askerVO.setSignature(buyer.getSignature());
            askerVO.setAskId(c.getId());

            //清除发布者不想看到的
            if(c.getState() == StateEnum.CUT.getValue()
                    || c.getState() == StateEnum.RESET.getValue()
                    || c.getState() == StateEnum.IGNORE.getValue()){
                continue;
            }

            switch (c.getState()){
            case 0:
                askerVO.setAskState(StateEnum.INIT.getValue());
                break;
            case 1:
                askerVO.setAskState(StateEnum.COMPLETE.getValue());
                break;
            case 2:
                askerVO.setAskState(StateEnum.INVALID.getValue());
                break;
            case 3:
                askerVO.setAskState(StateEnum.INVALID.getValue());
                break;
            case 7:
                askerVO.setAskState(StateEnum.COMPLETE.getValue());
                break;
            }

            askerVOS.add(askerVO);
        }

        //返回一个封装好的askerVO类
        msg.add("askerVOS",askerVOS);
        msg.setMsg("成功");
        msg.setCode(100);
        return msg;
    }

    /**
     * 评星
     * @param uid
     * @param token
     * @param clubBuyId 购买会所的记录Id
     * @param star
     * @return
     */
    @Override
    public Msg addClubStar(Integer uid, String token, Integer clubBuyId,Integer star) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }

        ClubBuy result = clubBuyMapper.selectByPrimaryKey(clubBuyId);

        Msg msg = new Msg();
        if(result == null){
            msg.setCode(404);
            msg.setMsg("找不到指定的会所购买记录");
            return msg;
        }

        //判断订单状态是否完成(完成了才可以评星)
        if(result.getState() != StateEnum.COMPLETE.getValue()){
            return Msg.fail();
        }
        ClubStar clubStar = new ClubStar();
        clubStar.setClubId(result.getClubId());
        clubStar.setEvaluationId(uid);
        clubStar.setStar(star);
        clubStar.setCreateTime(new Date());
        clubStar.setUpdateTime(new Date());
        int i = clubStarMapper.insertSelective(clubStar);
        if(i != 1){
            return Msg.fail();
        }
        return Msg.success();
    }

    /**
     * 购买者不想看了
     * @param uid
     * @param token
     * @param clubBuyId
     * @return
     */
    @Override
    public Msg deleteByClubBuyId(Integer uid, String token, Integer clubBuyId) {
//        if(!RedisUtil.authToken(uid.toString(),token)){
//            return Msg.noLogin();
//        }

        ClubBuy clubBuy = new ClubBuy();
        clubBuy.setUpdateTime(new Date());
        clubBuy.setId(clubBuyId);

        Integer state = clubBuyMapper.selectByPrimaryKey(clubBuyId).getState();

        Msg msg = new Msg();
        //只有状态是已完成、已取消、报名者已删除才可以进行删除操作
        switch (state){
            case 1:
                clubBuy.setState(StateEnum.DUMP.getValue());
                break;
            case 2:
                clubBuy.setState(StateEnum.DELETE.getValue());
                break;
            case 4:
                clubBuy.setState(StateEnum.IGNORE.getValue());
                break;
            case 8:
                clubBuy.setState(StateEnum.IGNORE.getValue());
                break;
            default:
                msg.setMsg("此状态不能修改");
                msg.setCode(100);
                return msg;
        }

        clubBuyMapper.updateByPrimaryKeySelective(clubBuy);

        return Msg.success();
    }
}
