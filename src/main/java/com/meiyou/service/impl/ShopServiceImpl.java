package com.meiyou.service.impl;

import com.meiyou.mapper.ShopMapper;
import com.meiyou.pojo.Shop;
import com.meiyou.pojo.User;
import com.meiyou.pojo.UserExample;
import com.meiyou.service.ShopService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description: 景点商家(同城导游)业务层实现类
 * @author: Mr.Z
 * @create: 2019-08-21 17:38
 **/
@Service
public class ShopServiceImpl extends BaseServiceImpl implements ShopService{

    @Autowired
    ShopMapper shopMapper;

    /**
     * 发布
     * @param shop
     * @param token
     * @param time
     * @param password
     * @return
     */
    @Override
    public Msg addShop(Shop shop, String token, Integer time, String password) {
//        if(!RedisUtil.authToken(shop.getPublishId().toString(),token)){
//            return Msg.noLogin();
//        }

        Msg msg = new Msg();
        Date now = new Date();
        shop.setUpdateTime(now);
        shop.setCreateTime(now);
        shop.setState(0);
        //添加过期时间
        Long millisecond = now.getTime()+24*60*60*1000*time;
        shop.setOutTime(new Date(millisecond));

        //获取用户密码和余额
        Float money = getUserByUid(shop.getPublishId()).getMoney();
        String payWord = getUserByUid(shop.getPublishId()).getPayWord();

        //从系统数据表获取置顶费用和发布费用
        String top_money = getRootMessage("top_money");
        String publish_money = getRootMessage("publish_money");
        //计算出支付费用
        Float pay_money = Float.valueOf(publish_money) + Float.valueOf(top_money)*time;

        if(payWord.equals("")){
            msg.setMsg("请设置支付密码!!");
            msg.setCode(1000);
            return msg;
        }else if(!payWord.equals(password)){
            msg.setMsg("支付密码错误!!!");
            msg.setCode(1001);
            return msg;
            //用户金额与发布金额进行比较
        }else if(money < pay_money){
            msg.setMsg("发布失败,账户余额不足!!!");
            msg.setCode(1002);
            return msg;
        }else {
            shopMapper.insertSelective(shop);

            //执行扣钱操作
            User user = new User();
            money = money - pay_money;
            user.setMoney(money);
            user.setUpdateTime(new Date());

            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(shop.getPublishId());
            userMapper.updateByExampleSelective(user,userExample);

            return Msg.success();
        }
    }

    @Override
    public Msg updateShop(Integer uid, String token, Integer sid) {
        return null;
    }

    @Override
    public Msg selectByUid(Integer uid, String token) {
        return null;
    }

    @Override
    public Msg selectBySid(Integer uid, String token, Integer sid) {
        return null;
    }

    @Override
    public Msg selectShop(float longitude, float latitude) {
        return null;
    }
}
