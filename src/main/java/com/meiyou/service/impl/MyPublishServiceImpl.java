package com.meiyou.service.impl;

import com.meiyou.mapper.*;
import com.meiyou.model.AppointmentVO;
import com.meiyou.model.ClubVO;
import com.meiyou.model.ShopVO;
import com.meiyou.model.TourVO;
import com.meiyou.pojo.*;
import com.meiyou.service.MyPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: meiyou
 * @description: 我的发布实现类
 * @author: JK
 * @create: 2019-08-26 15:03
 **/
@Service
public class MyPublishServiceImpl extends BaseServiceImpl implements MyPublishService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private AppointAskMapper appointAskMapper;
    @Autowired
    private TourMapper tourMapper;
    @Autowired
    private TourAskMapper tourAskMapper;

    @Autowired
    ClubMapper clubMapper;

    @Autowired
    ShopMapper shopMapper;

    /**
     * @Description: 查询我的约会发布列表
     * @Author: JK
     * @Date: 2019/8/22
     */
    @Override
    public List<AppointmentVO> selectAppointmentList(String uid, String token) {
        List<AppointmentVO> appointmentVOS = new ArrayList<>();
        AppointmentExample example = new AppointmentExample();
        example.createCriteria().andPublisherIdEqualTo(Integer.parseInt(uid));
        List<Appointment> appointments = appointmentMapper.selectByExample(example);

        if(appointments.isEmpty() && appointments ==null){
            return appointmentVOS;
        }

        //获取报名者头像集合
        List<String> askerHears = new ArrayList<>();
        for (Appointment appointment : appointments) {
            AppointAskExample appointAskExample = new AppointAskExample();
            appointAskExample.createCriteria().andAskStateEqualTo(1).andAppointIdEqualTo(appointment.getId());
            List<AppointAsk> appointAsks = appointAskMapper.selectByExample(appointAskExample);

            AppointmentVO appointmentVO = new AppointmentVO();

            if(appointAsks.isEmpty() && appointAsks == null){
                appointmentVO.setNums(0);
                appointmentVO.setAskerHeader(null);
            }else {
                for (AppointAsk appointAsk : appointAsks) {
                    Integer askerId = appointAsk.getAskerId();
                    User user = getUserByUid(askerId);
                    String askerHeader = user.getHeader();
                    askerHears.add(askerHeader);
                }
                appointmentVO.setAskerHeader(askerHears);
                appointmentVO.setNums(appointAsks.size());
                appointmentVO.setCreateTime(appointment.getCreateTime());
            }

            //获取用户id
            Integer publisherId = appointment.getPublisherId();
            User user = getUserByUid(publisherId);

            //封装发布者信息
            appointmentVO.setPublishBirthday(user.getBirthday());
            appointmentVO.setPublishHeader(user.getHeader());
            appointmentVO.setPublishNickName(user.getNickname());
            appointmentVO.setPublishSignature(user.getSignature());

            //封装约会信息
            appointmentVO.setId(appointment.getId());
            appointmentVO.setAppointAddress(appointment.getAppointAddress());
            appointmentVO.setAppointContext(appointment.getAppointContext());
            appointmentVO.setAppointTime(appointment.getAppointTime());
            appointmentVO.setState(appointment.getState());

            appointmentVOS.add(appointmentVO);
        }

        return appointmentVOS;
    }

    /**
     * @Description: 查询我的旅游发布列表
     * @Author: JK
     * @Date: 2019/8/26
     */
    @Override
    public List<TourVO> selectTourList(String uid, String token) {
        List<TourVO> tourVOS = new ArrayList<>();
        TourExample tourExample = new TourExample();
        tourExample.createCriteria().andPublishIdEqualTo(Integer.parseInt(uid));
        List<Tour> tours = tourMapper.selectByExample(tourExample);

        if(tours.isEmpty() && tours ==null){
            return tourVOS;
        }

        //获取报名者头像集合
        List<String> askerHears = new ArrayList<>();
        for (Tour tour : tours) {
            TourAskExample tourAskExample = new TourAskExample();
            tourAskExample.createCriteria().andAskState0EqualTo(1).andAppointIdEqualTo(tour.getId());
            List<TourAsk> tourAsks = tourAskMapper.selectByExample(tourAskExample);

            TourVO tourVO = new TourVO();

            if(tourAsks.isEmpty() && tourAsks == null){
                tourVO.setAskerHeader(null);
                tourVO.setNums(0);
            }else {
                for (TourAsk tourAsk : tourAsks) {
                    Integer askerId = tourAsk.getAskerId();
                    User user = getUserByUid(askerId);
                    String askerHeader = user.getHeader();
                    askerHears.add(askerHeader);
                }

                //添加报名者头像
                tourVO.setAskerHeader(askerHears);
                //添加报名的总人数
                tourVO.setNums(tourAsks.size());
                tourVO.setCreateTime(tour.getCreateTime());
            }

            //获取用户id
            Integer publisherId = tour.getPublishId();
            User user = getUserByUid(publisherId);

            //封装发布者信息
            tourVO.setPublishBirthday(user.getBirthday());
            tourVO.setPublishHeader(user.getHeader());
            tourVO.setPublishNickName(user.getNickname());
            tourVO.setPublishSignature(user.getSignature());

            //封装旅游信息
            tourVO.setId(tour.getId());
            tourVO.setStartAddress(tour.getStartAddress());
            tourVO.setEndAddress(tour.getEndAddress());
            tourVO.setGoMessage(tour.getGoMessage());
            tourVO.setGoTime(tour.getGoTime());
            tourVO.setState(tour.getState());

            tourVOS.add(tourVO);
        }

        return tourVOS;
    }

    /**
     * 查找指定用户发布的有效按摩会所
     * @param uid
     * @return
     */
    @Override
    //@Cacheable(value = "clubVO",keyGenerator = "myKeyGenerator", unless = "#result.isEmpty()")
    public List<ClubVO> selectClubByUid(Integer uid,String token) {
        //查找发布出去的有效按摩会所
        ClubExample clubExample = new ClubExample();
        clubExample.createCriteria().andPublishIdEqualTo(uid);
        List<Club> result = clubMapper.selectByExample(clubExample);

        List<ClubVO> clubVOS = new ArrayList<>();
        if(result.isEmpty()){
            return clubVOS;
        }

        for(Club club : result){
            //把每一个重新赋值的clubVO类加到新的集合中
            clubVOS.add(setClubToClubVO(club));
        }

        return clubVOS;
    }

    /**
     * 查找用户发布的景点商家shop
     * @param uid
     * @param token
     * @return
     */
    @Override
    public List<ShopVO> selectShopByUid(Integer uid, String token) {
        ShopExample shopExample = new ShopExample();
        shopExample.createCriteria().andPublishIdEqualTo(uid);
        List<Shop> result = shopMapper.selectByExample(shopExample);

        //添加人数到VO类中
        List<ShopVO> shopVOS = new ArrayList<>();
        if(result.isEmpty()){
            return shopVOS;
        }

        for(Shop shop : result){
            //把每一个重新赋值的shopVOS类加到新的集合中
            shopVOS.add(setShopToShopVO(shop));
        }
        return shopVOS;
    }

}
