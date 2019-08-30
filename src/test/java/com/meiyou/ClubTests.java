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
import org.apache.commons.collections.bag.SynchronizedSortedBag;
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

    @Autowired
    CountPublishMoneyService publishMoneyService;

    @Autowired
    CountVideoService videoService;

    @Autowired
    CountRechargeService rechargeService ;

    @Autowired
    CountCashService cashService;

    @Test
    public void cash(){
        System.out.println(
                cashService.nowCashNums()
                        +","+
                        cashService.yesterdayCashNums()
                        +","+
                        cashService.nowWeekCashNums()
                        +","+
                        cashService.nowMonthCashNums()
                        +","+
                        cashService.lastMonthCashNums()
                        +","+
                        cashService.nowYearCashNums()
                        +","+
                        cashService.allCashNums()
        );
        System.out.println(cashService.countCashNums());
    }

    @Test
    public void rewar(){
        System.out.println(
                rechargeService.nowRechargeNums()
                        +","+
                        rechargeService.yesterdayRechargeNums()
                        +","+
                        rechargeService.nowWeekRechargeNums()
                        +","+
                        rechargeService.nowMonthRechargeNums()
                        +","+
                        rechargeService.lastMonthRechargeNums()
                        +","+
                        rechargeService.nowYearRechargeNums()
                        +","+
                        rechargeService.allRechargeNums()
        );

        System.out.println(rechargeService.countRechargeNums());
    }

    @Test
    public void ve(){
//        System.out.println(
//                videoService.nowVideoNums()
//                        +","+
//                        videoService.yesterdayVideoNums()
//                        +","+
//                        videoService.nowWeekVideoNums()
//                        +","+
//                        videoService.nowMonthVideoNums()
//                        +","+
//                        videoService.lastMonthVideoNums()
//                        +","+
//                        videoService.nowYearVideoNums()
//                        +","+
//                        videoService.allVideoNums()
//        );
    }

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
        System.out.println(publishService.countPublishNums());

        System.out.println(
                publishMoneyService.nowPublishNums()
                        +","+
                        publishMoneyService.yesterdayPublishNums()
                        +","+
                        publishMoneyService.nowWeekPublishNums()
                        +","+
                        publishMoneyService.nowMonthPublishNums()
                        +","+
                        publishMoneyService.lastMonthPublishNums()
                        +","+
                        publishMoneyService.nowYearPublishNums()
                        +","+
                        publishMoneyService.allPublishNums()
        );
        System.out.println(publishMoneyService.countPublishNums());
    }

    @Test
    public void shareSum(){
        Integer integer = service.nowShareMoney();
        Integer integer1 = service.yesterdayShareMoney();
        Integer integer2 = service.nowWeekShareMoney();
        Integer integer3 = service.nowMonthShareMoney();
        Integer integer4 = service.lastMonthShareMoney();
        Integer integer5 = service.nowYearShareMoney();
        Integer integer6 = service.allShareMoney();
        System.out.println(integer+","+integer);
        System.out.println(integer+","+integer1);
        System.out.println(integer+","+integer2);
        System.out.println(integer+","+integer3);
        System.out.println(integer+","+integer4);
        System.out.println(integer+","+integer5);
        System.out.println(integer+","+integer6);

        List<Integer> list = service.countShareMoney();
        System.out.println(list);
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

    @Autowired
    CountAskService askService;

    @Test
    public void asks(){
        Integer integer = askService.nowAskNums();
        Integer integer1 = askService.yesterdayAskNums();
        Integer integer2 = askService.nowWeekAskNums();
        Integer integer3 = askService.nowMonthAskNums();
        Integer integer4 = askService.lastMonthAskNums();
        Integer integer5 = askService.nowYearAskNums();
        Integer integer6 = askService.allAskNums();
        System.out.println(integer);
        System.out.println(integer1);
        System.out.println(integer2);
        System.out.println(integer3);
        System.out.println(integer4);
        System.out.println(integer5);
        System.out.println(integer6);

        List<Integer> list = askService.countAskNums();
        System.out.println(list);
    }

    @Test
    public void re(){
//

        Integer integer = rewardService.nowRewardNums();
        Integer integer1 = rewardService.yesterdayRewardNums();
        Integer integer2 = rewardService.nowWeekRewardNums();
        Integer integer3 = rewardService.nowMonthRewardNums();
        Integer integer4 = rewardService.lastMonthRewardNums();
        Integer integer5 = rewardService.nowYearRewardNums();
        Integer integer6 = rewardService.allRewardNums();
        System.out.println(integer);
        System.out.println(integer1);
        System.out.println(integer2);
        System.out.println(integer3);
        System.out.println(integer4);
        System.out.println(integer5);
        System.out.println(integer6);

        List<Integer> list = rewardService.countRewardNums();
        System.out.println(list);
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

    @Autowired
    CountUserService userService;

    @Test
    public void user(){
        List<Integer> list = videoService.CountVideoNums();
        System.out.println(list);
    }

    @Autowired
    CountSincerityService sincerityService;

    @Test
    public void sin(){
        System.out.println(sincerityService.nowSincerityNums());
        System.out.println(sincerityService.yesterdaySincerityNums());
        System.out.println(sincerityService.nowWeekSincerityNums());
        System.out.println(sincerityService.nowMonthSincerityNums());
        System.out.println(sincerityService.lastMonthSincerityNums());
        System.out.println(sincerityService.nowYearSincerityNums());
        System.out.println(sincerityService.allSincerityNums());

        List<Integer> list = sincerityService.countSincerityNums();
        System.out.println(list);
    }
}
