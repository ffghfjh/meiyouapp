package com.meiyou.service.impl;

import com.meiyou.mapper.*;
import com.meiyou.model.AppointmentVO;
import com.meiyou.model.ClubVO;
import com.meiyou.model.ShopVO;
import com.meiyou.model.TourVO;
import com.meiyou.myEnum.StateEnum;
import com.meiyou.pojo.*;
import com.meiyou.service.MyAskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: meiyou
 * @description: 我的报名实现类
 * @author: JK
 * @create: 2019-08-26 15:17
 **/
@Service
public class MyAskServiceImpl extends BaseServiceImpl implements MyAskService {
    @Autowired(required = false)
    private AppointAskMapper appointAskMapper;
    @Autowired(required = false)
    private AppointmentMapper appointmentMapper;
    @Autowired(required = false)
    private TourAskMapper tourAskMapper;
    @Autowired(required = false)
    private TourMapper tourMapper;
    @Autowired(required = false)
    ClubMapper clubMapper;
    @Autowired(required = false)
    ClubBuyMapper clubBuyMapper;
    @Autowired(required = false)
    ShopMapper shopMapper;
    @Autowired(required = false)
    ShopBuyMapper shopBuyMapper;

    /**
     * @Description: 查询我的约会报名
     * @Author: JK
     * @Date: 2019/8/26
     */
    @Override
    public List<AppointmentVO> selectMyAppointmentAsk(String uid, String token) {
        AppointAskExample appointAskExample = new AppointAskExample();
        appointAskExample.setOrderByClause("create_time desc");
        appointAskExample.createCriteria().andAskerIdEqualTo(Integer.parseInt(uid));
        List<AppointAsk> appointAsks = appointAskMapper.selectByExample(appointAskExample);

        List<AppointmentVO> appointmentVOS = new ArrayList<>();

        if (appointAsks == null && appointAsks.size() == 0) {
            return appointmentVOS;
        }
        for (AppointAsk ask : appointAsks) {
            AppointmentVO appointmentVO = new AppointmentVO();
            Appointment appointment = appointmentMapper.selectByPrimaryKey(ask.getAppointId());
            if(appointment == null){
                continue;
            }

            User publish = getUserByUid(appointment.getPublisherId());

            //添加发布者信息
            appointmentVO.setPublishId(publish.getId());
            System.out.println("发布者id"+publish.getId());
            appointmentVO.setPublishNickName(publish.getNickname());
            appointmentVO.setPublishHeader(publish.getHeader());
            appointmentVO.setPublishSignature(publish.getSignature());
            appointmentVO.setPublishBirthday(publish.getBirthday());

            //添加约会信息
            appointmentVO.setId(ask.getId());
            appointmentVO.setAppointId(ask.getAppointId());
            appointmentVO.setAppointContext(appointment.getAppointContext());
            appointmentVO.setAppointAddress(appointment.getAppointAddress());
            appointmentVO.setAppointTime(appointment.getAppointTime());
            //添加报名者状态
            appointmentVO.setAskState(ask.getAskState());

            appointmentVOS.add(appointmentVO);
        }
        return appointmentVOS;
    }

    /**
    * @Description: 查询我的旅游报名
    * @Author: JK
    * @Date: 2019/8/26
    */
    @Override
    public List<TourVO> selectMyTourAsk(String uid, String token) {
        TourAskExample tourAskExample = new TourAskExample();
        tourAskExample.setOrderByClause("create_time desc");
        tourAskExample.createCriteria().andAskerIdEqualTo(Integer.parseInt(uid));
        List<TourAsk> tourAsks = tourAskMapper.selectByExample(tourAskExample);

        ArrayList<TourVO> tourVOS = new ArrayList<>();
        if (tourAsks == null && tourAsks.size() == 0) {
            return tourVOS;
        }
        for (TourAsk ask : tourAsks) {
            TourVO tourVO= new TourVO();
            Tour tour = tourMapper.selectByPrimaryKey(ask.getAppointId());
            if(tour == null){
                continue;
            }

            User publish = getUserByUid(tour.getPublishId());

            //添加发布者信息
            tourVO.setPublishId(publish.getId());
            tourVO.setPublishNickName(publish.getNickname());
            tourVO.setPublishHeader(publish.getHeader());
            tourVO.setPublishSignature(publish.getSignature());
            tourVO.setPublishBirthday(publish.getBirthday());

            //添加旅游信息
            tourVO.setId(ask.getId());
            tourVO.setAppointId(ask.getAppointId());
            tourVO.setStartAddress(tour.getStartAddress());
            tourVO.setEndAddress(tour.getEndAddress());
            tourVO.setGoMessage(tour.getGoMessage());
            tourVO.setGoTime(tour.getGoTime());
            //添加报名者状态
            tourVO.setAskState(ask.getAskState0());

            tourVOS.add(tourVO);
        }
        return tourVOS;
    }

    /**
     * 查找指定用户的会所购买记录
     * @param uid
     * @return
     */
    @Override
    //@Cacheable(cacheNames = "buy")
    public List<ClubVO> selectMyClubAsk(Integer uid) {

        //查找购买按摩会所的记录
        ClubBuyExample clubBuyExample = new ClubBuyExample();
        clubBuyExample.setOrderByClause("create_time desc");
        //购买者id为uid的购买记录
        clubBuyExample.createCriteria().andBuyerIdEqualTo(uid)
                .andStateNotEqualTo(StateEnum.DELETE.getValue());

        List<ClubBuy> result = clubBuyMapper.selectByExample(clubBuyExample);

        //对查找出来的ClubBuy进行封装
        List<ClubVO> clubVOS = new ArrayList<>();
        if(result.isEmpty()){
            return clubVOS;
        }

        for(ClubBuy c : result){
            Club club = clubMapper.selectByPrimaryKey(c.getClubId());

            ClubVO clubVO = setClubToClubVO(club);
            //设置购买者状态
            clubVO.setId(c.getId());

            //清除报名者不想看到的
            if(c.getState() == StateEnum.DELETE.getValue()
                    || c.getState() == StateEnum.DUMP.getValue()
                    || c.getState() == StateEnum.IGNORE.getValue()){
                continue;
            }

            switch (c.getState()){
                case 0:
                    clubVO.setAskState(StateEnum.INIT.getValue());
                    break;
                case 1:
                    clubVO.setAskState(StateEnum.COMPLETE.getValue());
                    break;
                case 2:
                    clubVO.setAskState(StateEnum.INVALID.getValue());
                    break;
                case 4:
                    clubVO.setAskState(StateEnum.INVALID.getValue());
                    break;
                case 8:
                    clubVO.setAskState(StateEnum.COMPLETE.getValue());
                    break;
            }

            //设置按摩会所id
            clubVO.setPublishId(c.getClubId());
            //设置按摩会所的发布者id
            clubVO.setClubId(club.getPublishId());

            clubVOS.add(clubVO);
        }

        //返回一个封装好的ClubVO类

        for (int i = 0; i < clubVOS .size(); i++)    {
            for (int j = clubVOS .size()-1; j > i; j--)  {
                Long time= clubVOS .get(j).getCreateTime().getTime();
                Long time1= clubVOS .get(j-1).getCreateTime().getTime();
                if (time.compareTo(time1)>0)    {
                    //互换位置
                    ClubVO clubVO = clubVOS.get(j);
                    clubVOS.set(j, clubVOS.get(j-1));
                    clubVOS.set(j-1, clubVO );
                }
            }
        }

        return clubVOS;
    }

    /**
     * 查询用户聘请的全部导游记录
     * @param uid
     * @return
     */
    @Override
    public List<ShopVO> selectMyShopAsk(Integer uid) {

        //查找购买按摩会所的记录
        ShopBuyExample shopBuyExample = new ShopBuyExample();
        shopBuyExample.setOrderByClause("create_time desc");
        shopBuyExample.createCriteria().andBuyerIdEqualTo(uid)
                .andStateNotEqualTo(StateEnum.DELETE.getValue());
        List<ShopBuy> result = shopBuyMapper.selectByExample(shopBuyExample);

        //对查找出来的ShopBuy进行封装
        List<ShopVO> shopVOS = new ArrayList<>();
        if(result.isEmpty()){
            return shopVOS;
        }
        for(ShopBuy shopBuy : result){
            Shop shop = shopMapper.selectByPrimaryKey(shopBuy.getGuideId());

            ShopVO shopVO = setShopToShopVO(shop);
            //设置购买者状态
            shopVO.setId(shopBuy.getId());

            //清除报名者不想看到的
            if(shopBuy.getState() == StateEnum.DELETE.getValue()
                    || shopBuy.getState() == StateEnum.DUMP.getValue()
                    || shopBuy.getState() == StateEnum.IGNORE.getValue()){
                continue;
            }

            switch (shopBuy.getState()){
                case 0:
                    shopVO.setAskState(StateEnum.INIT.getValue());
                    break;
                case 1:
                    shopVO.setAskState(StateEnum.COMPLETE.getValue());
                    break;
                case 2:
                    shopVO.setAskState(StateEnum.INVALID.getValue());
                    break;
                case 4:
                    shopVO.setAskState(StateEnum.INVALID.getValue());
                    break;
                case 8:
                    shopVO.setAskState(StateEnum.COMPLETE.getValue());
                    break;
            }

            //设置经典商家id
            shopVO.setPublishId(shopBuy.getGuideId());
            //设置设置经典商家的发布者id
            shopVO.setShopId(shop.getPublishId());

            shopVOS.add(shopVO);
        }
        //返回一个封装好的ShopVO类

        for (int i = 0; i < shopVOS .size(); i++)    {
            for (int j = shopVOS .size()-1; j > i; j--)  {
                Long time= shopVOS .get(j).getCreateTime().getTime();
                Long time1= shopVOS .get(j-1).getCreateTime().getTime();
                if (time.compareTo(time1)>0)    {
                    //互换位置
                    ShopVO shopVO = shopVOS.get(j);
                    shopVOS.set(j, shopVOS.get(j-1));
                    shopVOS.set(j-1, shopVO );
                }
            }
        }

        return shopVOS;
    }
}
