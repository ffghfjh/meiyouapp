package com.meiyou.service.impl;

import com.meiyou.mapper.ClubMapper;
import com.meiyou.mapper.ShopBuyMapper;
import com.meiyou.mapper.ShopMapper;
import com.meiyou.model.AskerVO;
import com.meiyou.model.ClubVO;
import com.meiyou.model.ShopVO;
import com.meiyou.myEnum.StateEnum;
import com.meiyou.pojo.*;
import com.meiyou.service.ShopBuyService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Mr.Z
 * @create: 2019-08-21 17:39
 **/
@Service
public class ShopBuyServiceImpl extends BaseServiceImpl implements ShopBuyService {

    @Autowired
    ShopBuyMapper shopBuyMapper;

    @Autowired
    ShopMapper shopMapper;

    /**
     * 添加聘请导游记录
     * @param shopBuy
     * @param token
     * @param password
     * @return
     */
    @Override
    @Transactional
    public Msg addShopBuy(ShopBuy shopBuy, String token, Integer password) {
        if(!RedisUtil.authToken(shopBuy.getBuyerId().toString(),token)){
            return Msg.noLogin();
        }

        Msg msg = new Msg();

        //获取经典商家发布者id，对比购买者id，防止自己买
        Integer publishId = shopMapper.selectByPrimaryKey(shopBuy.getGuideId()).getPublishId();
        if(publishId.equals(shopBuy.getBuyerId())){
            msg.setCode(501);
            msg.setMsg("自己不允许聘请");
            return msg;
        }

        shopBuy.setCreateTime(new Date());
        shopBuy.setUpdateTime(new Date());
        shopBuy.setState(StateEnum.INIT.getValue());

        //从系统数据表获取报名费用
        //String ask_money = getRootMessage("ask_money");

        //获取购买者的支付密码和余额
        User result = getUserByUid(shopBuy.getBuyerId());
        String payWord = result.getPayWord();
        Float money = result.getMoney();

        //计算所购买的导游的总收费
        Integer time = shopBuy.getTime();
        Integer charge = shopMapper.selectByPrimaryKey(shopBuy.getGuideId()).getCharge();
        Integer charges = charge * time;


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
        if(money < charges){
            msg.setMsg("发布失败,账户余额不足!");
            msg.setCode(1002);
            return msg;
        }else {
            shopBuyMapper.insertSelective(shopBuy);

            //计算剩余金额
            User user = new User();
            money = money - Float.valueOf(charges);
            user.setMoney(money);
            user.setUpdateTime(new Date());

            //执行扣钱操作
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(shopBuy.getBuyerId());
            userMapper.updateByExampleSelective(user, userExample);

            return Msg.success();
        }
    }

    /**
     * 给完成了的景点商家评星
     * @param uid
     * @param token
     * @param shopBuyId shopBuy的Id
     * @param star
     * @return
     */
    @Override
    public Msg addShopStar(Integer uid, String token, Integer shopBuyId, Integer star) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }

        ShopBuy result = shopBuyMapper.selectByPrimaryKey(shopBuyId);

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

        ShopStar shopStar = new ShopStar();
        shopStar.setStart(star);
        shopStar.setCreateTime(new Date());
        shopStar.setUpdateTime(new Date());
        shopStar.setEvaluationId(uid);
        shopStar.setGuideId(result.getGuideId());

        int i = shopStarMapper.insertSelective(shopStar);
        if(i != 1){
            return Msg.fail();
        }

        return Msg.success();
    }

    /**
     * 取消聘请导游
     * @param uid
     * @param token
     * @param shopBuyId 聘请导游这条记录的Id
     * @return
     */
    @Override
    @Transactional
    public Msg updateShopBuy(Integer uid, String token, Integer shopBuyId) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }

//        从系统数据表获取报名费用
        String ask_money = getRootMessage("ask_money");

        Msg msg = new Msg();
        ShopBuy buy = shopBuyMapper.selectByPrimaryKey(shopBuyId);
        //获取每小时收费和聘请时间
        Integer charge = shopMapper.selectByPrimaryKey(buy.getGuideId()).getCharge();

        ShopBuyExample example = new ShopBuyExample();
        example.createCriteria().andIdEqualTo(shopBuyId);
        Integer time = shopBuyMapper.selectByPrimaryKey(shopBuyId).getTime();
        Integer money = time * charge;

        //修改聘用表状态-->取消状态-2
        ShopBuy shopBuy = new ShopBuy();
        shopBuy.setState(StateEnum.INVALID.getValue());
        shopBuy.setUpdateTime(new Date());

        ShopBuyExample shopBuyExample = new ShopBuyExample();
        shopBuyExample.createCriteria().andIdEqualTo(shopBuyId);
        shopBuyMapper.updateByExampleSelective(shopBuy,shopBuyExample);

        //执行退款
        User user = getUserByUid(uid);
        user.setMoney(user.getMoney() + Float.valueOf(money)+Float.valueOf(ask_money));
        user.setUpdateTime(new Date());

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(uid);
        userMapper.updateByExampleSelective(user,userExample);

        return Msg.success();
    }

    /**
     * 修改状态为已赴约(已完成)--->>1
     * @param uid
     * @param shopBuyId 购买景点商家这条记录的ID
     * @param token
     * @return
     */
    @Override
    public Msg updateShopBuyComplete(Integer uid, Integer shopBuyId, String token) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }
        ShopBuy shopBuy = new ShopBuy();
        shopBuy.setState(StateEnum.COMPLETE.getValue());
        shopBuy.setUpdateTime(new Date());

        //修改购买表状态
        ShopBuyExample example = new ShopBuyExample();
        example.createCriteria().andIdEqualTo(shopBuyId);
        int rows = shopBuyMapper.updateByExampleSelective(shopBuy, example);
        if(rows != 1){
            return Msg.fail();
        }
        return Msg.success();
    }

    /**
     * 查询当前用户指定的聘请导游记录
     * @param uid
     * @param token
     * @param sid
     * @return
     */
    @Override
    public Msg selectBySidAndUid(Integer uid, String token, Integer sid) {
        if(!RedisUtil.authToken(uid.toString(),token)){
            return Msg.noLogin();
        }

        ShopBuyExample shopBuyExample = new ShopBuyExample();
        shopBuyExample.createCriteria().andIdEqualTo(sid).andBuyerIdEqualTo(uid);
        List<ShopBuy> result = shopBuyMapper.selectByExample(shopBuyExample);
        Msg msg = new Msg();
        if(result == null && result.size() ==0){
            msg.setCode(404);
            msg.setMsg("没找到指定的聘请的导游记录");
            return msg;
        }

        //对查找出来的ShopBuy进行封装
        Shop shop = shopMapper.selectByPrimaryKey(result.get(0).getGuideId());
        ShopVO shopVO = setShopToShopVO(shop);
        shopVO.setState(result.get(0).getState());

        msg.add("shopVO", shopVO);
        msg.setMsg("成功");
        msg.setCode(100);
        return msg;
    }

    /**
     * 查询聘请了此导游的全部记录
     * @param uid
     * @param sid
     * @param token
     * @return
     */
    @Override
    public Msg selectBySid(Integer uid, Integer sid, String token) {
//        if(!RedisUtil.authToken(uid.toString(),token)){
//            return Msg.noLogin();
//        }

        Msg msg = new Msg();
        //判断访问者是否为发布者
        Integer publishId = shopMapper.selectByPrimaryKey(sid).getPublishId();
        if(publishId != uid){
            msg.setCode(506);
            msg.setMsg("没有访问权限");
            return msg;
        }

        //查找购买经典商家的记录
        ShopBuyExample shopBuyExample = new ShopBuyExample();
        //聘请了id为sid的所有购买记录
        shopBuyExample.createCriteria().andGuideIdEqualTo(sid);

        List<ShopBuy> result = shopBuyMapper.selectByExample(shopBuyExample);

        if(result.isEmpty()){
            msg.setCode(404);
            msg.setMsg("找不到指定的会所购买记录");
            return msg;
        }

        //对查找出来的ShopBuy进行封装
        List<AskerVO> askerVOS = new ArrayList<>();
        for(ShopBuy shopBuy : result){
            User buyer = getUserByUid(shopBuy.getBuyerId());

            AskerVO askerVO = new AskerVO();

            askerVO.setId(buyer.getId());
            askerVO.setNickname(buyer.getNickname());
            askerVO.setHeader(buyer.getHeader());
            askerVO.setBirthday(buyer.getBirthday());
            askerVO.setSex(buyer.getSex());
            askerVO.setSignature(buyer.getSignature());
            askerVO.setAskId(shopBuy.getId());

            //清除发布者不想看到的
            if(shopBuy.getState() == StateEnum.CUT.getValue()
                    || shopBuy.getState() == StateEnum.RESET.getValue()
                    || shopBuy.getState() == StateEnum.IGNORE.getValue()){
                continue;
            }

            switch (shopBuy.getState()){
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
        System.out.println(askerVOS);
        msg.setMsg("成功");
        msg.setCode(100);
        return msg;
    }

    /**
     * 购买者不想看了
     * 修改状态为已删除--》3 或 6
     * @param uid
     * @param token
     * @param shopBuyId
     * @return
     */
    @Override
    public Msg deleteByShopBuyId(Integer uid, String token, Integer shopBuyId) {
//        if(!RedisUtil.authToken(uid.toString(),token)){
//            return Msg.noLogin();
//        }

        ShopBuy shopBuy = new ShopBuy();
        shopBuy.setUpdateTime(new Date());
        shopBuy.setId(shopBuyId);

        Integer state = shopBuyMapper.selectByPrimaryKey(shopBuyId).getState();

        Msg msg = new Msg();
        //只有状态是已完成、已取消、报名者已删除才可以进行删除操作
        switch (state){
            case 1:
                shopBuy.setState(StateEnum.DUMP.getValue());
                break;
            case 2:
                shopBuy.setState(StateEnum.DELETE.getValue());
                break;
            case 4:
                shopBuy.setState(StateEnum.IGNORE.getValue());
                break;
            case 8:
                shopBuy.setState(StateEnum.IGNORE.getValue());
                break;
            default:
                msg.setMsg("此状态不能修改");
                msg.setCode(100);
                return msg;
        }

        shopBuyMapper.updateByPrimaryKeySelective(shopBuy);

        return Msg.success();
    }
}
