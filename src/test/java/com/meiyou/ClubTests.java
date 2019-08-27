package com.meiyou;

import com.meiyou.mapper.ClubBuyMapper;
import com.meiyou.mapper.ClubMapper;
import com.meiyou.mapper.ShopMapper;
import com.meiyou.pojo.Club;
import com.meiyou.pojo.ClubBuy;
import com.meiyou.pojo.ClubBuyExample;
import com.meiyou.pojo.Shop;
import com.meiyou.service.ShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Mr.Z
 * @create: 2019-08-22 09:03
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClubTests {

    @Autowired
    ClubBuyMapper clubBuyMapper;

    @Autowired
    ClubMapper clubMapper;

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    ShopService shopService;

    @Test
    public void findPhoto(){
        Club club = clubMapper.selectByPrimaryKey(33);
        String imgsUrl = club.getImgsUrl();
        String substring = imgsUrl.substring(0, 1);
        System.out.println(substring);
        String[] split = imgsUrl.split(",");
        System.out.println(split.length);
        List<String> list = new ArrayList<>();
        for(int i =0 ;i < split.length ;i++){
            System.out.println(split[i]);
            list.add(split[i]);
        }
        System.out.println("才分后为:"+split[0]);
        System.out.println(list);
    }

    @Test
    public void contextLoads() {
        ClubBuy clubBuy = new ClubBuy();
        clubBuy.setClubId(7);
        clubBuy.setBuyerId(9);
        int i = clubBuyMapper.insertSelective(clubBuy);
        if(i == 1){
            System.out.println("购买成功!!!");
        }
    }

    @Test
    public void testShop(){
        Shop shop = new Shop();
        shop.setPublishId(19);
        //shop.setImgsUrl(array.toString());////以json数组的形式存图片
        shop.setServiceArea("龙花");
        shop.setTravelTime("后晚");
        shop.setCharge(16);
        String token = "5655";
        String pas = "123456";
        Double lo = 114.00;
        Double la = 23.00;
        shopService.addShop(shop,token,5, pas, lo, la);
    }


    @Test
    public void udate(){
        ClubBuy clubBuy = new ClubBuy();
        //clubBuy.setId(2);
        clubBuy.setState(6);
        //clubBuy.setClubId(7);
        //clubBuy.setBuyerId(9);
        ClubBuyExample clubBuyExample = new ClubBuyExample();
        clubBuyExample.createCriteria().andClubIdEqualTo(7).andBuyerIdEqualTo(9);
        clubBuyMapper.updateByExampleSelective(clubBuy,clubBuyExample);
    }
}
