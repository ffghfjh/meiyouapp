package com.meiyou.service.impl;

import com.meiyou.mapper.ShopBuyMapper;
import com.meiyou.mapper.ShopMapper;
import com.meiyou.pojo.*;
import com.meiyou.service.ShopBuyService;
import com.meiyou.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//        if(!RedisUtil.authToken(clubBuy.getBuyerId().toString(),token)){
//            return Msg.noLogin();
//        }

        shopBuy.setCreateTime(new Date());
        shopBuy.setUpdateTime(new Date());
        //shopBuy.setState(0);

        //从系统数据表获取报名费用
        //String ask_money = getRootMessage("ask_money");

        //获取购买者的支付密码和余额
        User result = getUserByUid(shopBuy.getBuyerId());
        String payWord = result.getPayWord();
        Float money = result.getMoney();

        //计算所购买的导游的总收费
        Integer charge = shopMapper.selectByPrimaryKey(shopBuy.getGuideId())
                .getCharge() * shopBuy.getTime();

        Msg msg = new Msg();
        if(payWord.equals("")){
            msg.setMsg("请设置支付密码!");
            msg.setCode(1000);
            return msg;
        }else if(!payWord.equals(password)){
            msg.setMsg("支付密码错误!");
            msg.setCode(1001);
            return msg;
        }else if(money < charge){
            msg.setMsg("发布失败,账户余额不足!");
            msg.setCode(1002);
            return msg;
        }else {
            shopBuyMapper.insertSelective(shopBuy);

            //计算剩余金额
            User user = new User();
            money = money - Float.valueOf(charge);
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
     * 取消聘请导游
     * @param uid
     * @param token
     * @param sid shop_id
     * @return
     */
    @Override
    @Transactional
    public Msg updateShopBuy(Integer uid, String token, Integer sid) {
//        if(!RedisUtil.authToken(clubBuy.getBuyerId().toString(),token)){
//            return Msg.noLogin();
//        }

//        从系统数据表获取置顶费用
//        String ask_money = getRootMessage("ask_money");

        //获取每小时收费和聘请时间
        Integer projectPrice = shopMapper.selectByPrimaryKey(sid).getCharge();

        ShopBuyExample example = new ShopBuyExample();
        example.createCriteria().andGuideIdEqualTo(sid).andBuyerIdEqualTo(uid);
        Integer time = shopBuyMapper.selectByExample(example).get(0).getTime();
        Integer money = time * projectPrice;

        //修改聘用表状态-->取消状态-2
        ShopBuy shopBuy = new ShopBuy();
        shopBuy.setState(2);
        shopBuy.setUpdateTime(new Date());

        ShopBuyExample shopBuyExample = new ShopBuyExample();
        shopBuyExample.createCriteria().andBuyerIdEqualTo(uid).andIdEqualTo(sid);
        shopBuyMapper.updateByExampleSelective(shopBuy,shopBuyExample);

        //执行退款
        User user = getUserByUid(uid);
        user.setMoney(user.getMoney() + Float.valueOf(money));
        user.setUpdateTime(new Date());

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(uid);
        userMapper.updateByExampleSelective(user,userExample);

        return Msg.success();
    }

    /**
     * 查询用户聘请的全部导游记录
     * @param uid
     * @param token
     * @return
     */
    @Override
    public Msg selectByUid(Integer uid, String token) {
//        if(!RedisUtil.authToken(clubBuy.getBuyerId().toString(),token)){
//            return Msg.noLogin();
//        }

        //查找购买按摩会所的记录
        ShopBuyExample shopBuyExample = new ShopBuyExample();
        shopBuyExample.createCriteria().andBuyerIdEqualTo(uid);
        List<ShopBuy> result = shopBuyMapper.selectByExample(shopBuyExample);

        Msg msg = new Msg();
        if(result.size() == 0){
            msg.setCode(404);
            msg.setMsg("找不到用户所购买的聘请记录");
        }

        msg.add("shopBuy",result);
        msg.setMsg("成功");
        msg.setCode(100);
        return null;
    }

    /**
     * 查询指定的聘请的导游记录
     * @param uid
     * @param token
     * @param sid
     * @return
     */
    @Override
    public Msg selectBySid(Integer uid, String token, Integer sid) {
//        if(!RedisUtil.authToken(clubBuy.getBuyerId().toString(),token)){
//            return Msg.noLogin();
//        }

        ShopBuyExample shopBuyExample = new ShopBuyExample();
        shopBuyExample.createCriteria().andIdEqualTo(sid).andBuyerIdEqualTo(uid);
        List<ShopBuy> result = shopBuyMapper.selectByExample(shopBuyExample);
        Msg msg = new Msg();
        if(result.size() ==0 ){
            msg.setCode(404);
            msg.setMsg("没找到指定的聘请的导游记录");
            return msg;
        }

        msg.add("shopBuy", result.get(0));
        msg.setMsg("成功");
        msg.setCode(100);
        return msg;
    }
}
