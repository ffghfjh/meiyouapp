package com.meiyou.controller;

import com.meiyou.model.*;
import com.meiyou.service.MyPublishService;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: meiyou
 * @description: 我的发布控制层
 * @author: JK
 * @create: 2019-08-26 15:04
 **/
@RestController
@Api(value = "我的发布控制层", tags = {"我的发布控制层"})
public class MyPublishController {
    @Autowired
    private MyPublishService myPublishService;

//    @Caching(
//            cacheable = {
//                    @Cacheable(value = "myPublish")
//            },
//            put = {
//                    //先执行方法
//                    @CachePut(value = "myPublish"),
//            }
//    )
    @PostMapping("/selectMyPublishList")
    @ApiOperation(value = "通过用户id查找指定用户id发布的全部景点商家",notes = "返回为ShopVO类,nums为报名人数")
    public Map<String,Object> selectMyPublishList(@RequestParam("uid") String uid,
                                   @RequestParam("token") String token){
        HashMap<String, Object> hashMap = new HashMap<>();
        List<MyPublishVo> list = new ArrayList<>();
//        if(!RedisUtil.authToken(uid,token)){
//            hashMap.put("Code",300);
//            hashMap.put("Msg","未登陆");
//            return hashMap;
//        }
        Msg msg = new Msg();
        List<AppointmentVO> appointmentList = myPublishService.selectAppointmentList(uid, token);
        System.out.println(appointmentList);
        if(appointmentList.isEmpty()){
            hashMap.put("600",null);
        }else {
            for (AppointmentVO appointmentVO : appointmentList) {
                try {
                    MyPublishVo myPublishVo = new MyPublishVo();
                    ConvertUtils.register(new DateConverter(null), java.util.Date.class);
//                    System.out.println(appointmentVO.getAppointContext());
//                    System.out.println(appointmentVO.getCreateTime());
                    BeanUtils.copyProperties(myPublishVo,appointmentVO);
//                    System.out.println("++++++"+myPublishVo);
//                    System.out.println(myPublishVo.getCreateTime().getTime());
                    myPublishVo.setType(1);
                    list.add(myPublishVo);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        List<TourVO> tourList = myPublishService.selectTourList(uid, token);
        if(tourList.isEmpty()){
            hashMap.put("600",null);
        }else {
            for (TourVO tourVO : tourList) {
                try {
                    MyPublishVo myPublishVo = new MyPublishVo();
                    ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                    BeanUtils.copyProperties(myPublishVo,tourVO);
                    myPublishVo.setType(2);
                    list.add(myPublishVo);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        List<ClubVO> clubList = myPublishService.selectClubByUid(Integer.valueOf(uid), token);
        if(clubList.isEmpty()){
            hashMap.put("600",null);
        }else {
            for (ClubVO clubVO : clubList) {
                try {
                    MyPublishVo myPublishVo = new MyPublishVo();
                    ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                    BeanUtils.copyProperties(myPublishVo,clubVO);
                    myPublishVo.setType(3);
                    list.add(myPublishVo);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        List<ShopVO> shopList = myPublishService.selectShopByUid(Integer.valueOf(uid), token);
        if(shopList.isEmpty()){
            hashMap.put("600",null);
        }else {
            for (ShopVO shopVO : shopList) {
                try {
                    MyPublishVo myPublishVo = new MyPublishVo();
                    ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                    BeanUtils.copyProperties(myPublishVo,shopVO);
                    myPublishVo.setType(4);
                    list.add(myPublishVo);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = 0; i < list .size(); i++)    {
            for (int j = list .size()-1; j > i; j--)  {
                Long time= list .get(j).getCreateTime().getTime();
                Long time1= list .get(j-1).getCreateTime().getTime();
                if (time.compareTo(time1)>0)    {
                    //互换位置
                    MyPublishVo  myPublish = list.get(j);
                    list.set(j, list.get(j-1));
                    list.set(j-1, myPublish );
                }
            }
        }
        hashMap.put("Code",100);
        hashMap.put("Msg","返回成功");
        hashMap.put("list",list);
        return hashMap;
    }
}
