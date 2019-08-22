package com.meiyou.service.impl;

import com.meiyou.mapper.ClubBuyMapper;
import com.meiyou.mapper.ClubMapper;
import com.meiyou.mapper.RootMessageMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.pojo.ClubBuy;
import com.meiyou.pojo.RootMessageExample;
import com.meiyou.pojo.User;
import com.meiyou.service.ClubBuyService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @description: 会所购买业务层接口实现类
 * @author: Mr.Z
 * @create: 2019-08-21 15:38
 **/
@Service
public class ClubBuyServiceImpl implements ClubBuyService {

    @Autowired
    ClubBuyMapper clubBuyMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ClubMapper clubMapper;

    @Autowired
    RootMessageMapper rootMessageMapper;

    /**
     * 生成购买会所记录
     * @param clubBuy
     * @return
     */
    @Transactional
    @Override
    public Msg addBuyClub(ClubBuy clubBuy,String token,Integer password) {
//        if(!RedisUtil.authToken(clubBuy.getBuyerId().toString(),token)){
//            return Msg.noLogin();
//        }
        Msg msg = new Msg();
        clubBuy.setCreateTime(new Date());
        clubBuy.setUpdateTime(new Date());

        //从系统数据表获取置顶费用
        RootMessageExample rootMessageExample = new RootMessageExample();
        rootMessageExample.createCriteria().andNameEqualTo("ask_money");
        String ask_money = rootMessageMapper.selectByExample(rootMessageExample).get(0).getValue();

        //获取购买者的支付密码,余额及需要支付的费用(报名缴费+会所项目缴费)
        String payWord = userMapper.selectByPrimaryKey(clubBuy.getBuyerId()).getPayWord();
        Float money = userMapper.selectByPrimaryKey(clubBuy.getBuyerId()).getMoney();
        Integer price = clubMapper.selectByPrimaryKey(clubBuy.getClubId()).getProjectPrice()
                +Integer.valueOf(ask_money);

        if(payWord.equals("")){
            msg.setMsg("请设置支付密码!");
            msg.setCode(1000);
            return msg;
        }else if(!payWord.equals(password)){
            msg.setMsg("支付密码错误!");
            msg.setCode(1001);
            return msg;
        }else if(money < price){
            msg.setMsg("发布失败,账户余额不足!");
            msg.setCode(1002);
            return msg;
        }else {
            clubBuyMapper.insert(clubBuy);

            //执行扣钱操作
            User user = new User();
            money = money - price;
            user.setMoney(money);
            user.setId(clubBuy.getBuyerId());
            user.setUpdateTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);
            return Msg.success();
        }
    }

    @Override
    public Msg deleteBuyClub(Integer id) {
        return null;
    }

    @Override
    public Msg selectByUid(Integer id) {
        return null;
    }
}
