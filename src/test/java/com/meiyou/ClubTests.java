package com.meiyou;

import com.meiyou.mapper.*;
import com.meiyou.model.ClubVO;
import com.meiyou.myEnum.StateEnum;
import com.meiyou.myEnum.TimeTypeEnum;
import com.meiyou.pojo.Club;
import com.meiyou.pojo.ClubBuy;
import com.meiyou.pojo.ClubBuyExample;
import com.meiyou.pojo.Shop;
import com.meiyou.service.*;
//import com.meiyou.service.MyAskService;
import com.meiyou.service.impl.BaseServiceImpl;
import com.meiyou.utils.Msg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.DatabaseMetaData;
import java.util.*;

/**
 * @description:
 * @author: Mr.Z
 * @create: 2019-08-22 09:03
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClubTests extends BaseServiceImpl {

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
    public void enums(){
        Integer days = 0;
        String top_money = null;
        String timeType = "一年";

        TimeTypeEnum type = TimeTypeEnum.getTimeTypeByDesc(timeType);
        if(type == null){
            System.out.println(Msg.fail());
        }
        switch (type){
            case DAY:
                days = type.getValue();
                top_money = getRootMessage(timeType);
            case WEEK:
                days = type.getValue();
                top_money = getRootMessage(timeType);
            case MONTH:
                days = type.getValue();
                top_money = getRootMessage(timeType);
            case QUARTER:
                days = type.getValue();
                top_money = getRootMessage(timeType);
            case YEAR:
                days = type.getValue();
                top_money = getRootMessage(timeType);
        }

        //Long millisecond = days*1000*60*60*24L;
        //Long second = days*1000*60*60*24;
        System.out.println(days*1000*60*60*24L+"--"+days*1000*60*60*24);
        System.out.println(days+","+top_money);
    }

    @Test
    public void findss(){
        Shop shop = shopMapper.selectByPrimaryKey(99);
        System.out.println(shop);
    }

    @Autowired
    CountUserMapper mapper;

    @Autowired
    CountShareMapper shareMapper;

    @Autowired
    CountShareService service;

    @Autowired
    CountPublishMapper publishMapper;

    @Test
    public void pub(){
        List<Integer> integers = publishMapper.nowWeekPublishNums();
        System.out.println(integers);
    }

    @Autowired
    CountPublishService publishService;

    @Test
    public void pu(){
        System.out.println(
                publishService.nowPublishNums()
                +","+
                publishService.yesterdayPublishNums()
                +","+
                publishService.nowWeekPublishNums()
                +","+
                publishService.nowMonthPublishNums()
                +","+
                publishService.lastMonthPublishNums()
                +","+
                publishService.nowYearPublishNums()
                +","+
                publishService.allPublishNums()
        );
    }

    @Test
    public void shareSum(){
        Integer integer = service.nowShareMoney();
        Integer integer1 = service.nowWeekShareMoney();
        System.out.println(integer+","+integer1);
    }

    @Autowired
    CountRewardService rewardService;

    @Test
    public void rewardSum(){
        Integer integer = rewardService.nowRewardNums();
        System.out.println(integer);
    }

    @Test
    public void count(){
        List<Integer> integers = shareMapper.allShareMoney();
        if(integers.isEmpty()){
            System.out.println("没有就是0");
        }
        System.out.println(integers);
    }

    @Autowired
    MyAskService myAskService;

    @Test
    public void ask(){
        List<ClubVO> clubVOS = myAskService.selectMyClubAsk(100);
        HashMap<String, Object> map = new HashMap<>();
        if(clubVOS.isEmpty()){
            map.put("clubVOS",null);
            System.out.println(map);
        }
        map.put("clubVOS",clubVOS);
        System.out.println(map);
    }

    @Autowired
    CountRewardMapper rewardMapper;

    @Test
    public void re(){
//        List<List<Integer>> lists2 = rewardMapper.lastMonthRewardNums();
//        List<List<Integer>> lists3 = rewardMapper.nowYearRewardNums();
//        List<List<Integer>> lists4 = rewardMapper.allRewardNums();
//        System.out.println(lists2.get(0));
//        System.out.println(lists3.get(0)+","+lists3.size());
        List<Integer> list = rewardMapper.nowRewardNums();
        System.out.println(list);
        List<Integer> list1 = rewardMapper.yesterdayRewardNums();
        System.out.println(list1);
        List<Integer> list2 = rewardMapper.nowWeekRewardNums();
        System.out.println(list2);

        Integer integer = rewardService.nowRewardNums();
        System.out.println(integer);
        Integer integer1 = rewardService.yesterdayRewardNums();
        System.out.println(integer1);
        Integer integer2 = rewardService.nowWeekRewardhNums();
        System.out.println(integer2);
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
