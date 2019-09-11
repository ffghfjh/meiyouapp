package com.meiyou.service.impl;

import com.meiyou.mapper.*;
import com.meiyou.model.ClubVO;
import com.meiyou.model.Coordinate;
import com.meiyou.model.ShopVO;
import com.meiyou.myEnum.StateEnum;
import com.meiyou.pojo.*;
import com.meiyou.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.GeoRadiusResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 重复写的一些方法，进行了简单封装，可以一起用
 * @author: Mr.Z
 * @create: 2019-08-23 08:37
 **/
public class BaseServiceImpl {

    @Autowired
    RootMessageMapper rootMessageMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ClubBuyMapper clubBuyMapper;

    @Autowired
    ClubStarMapper clubStarMapper;

    @Autowired
    ShopBuyMapper shopBuyMapper;

    @Autowired
    ShopStarMapper shopStarMapper;

    /**
     * 获取系统数据表数据
     * @param message 需要在系统数据表获取的参数名称
     * @return
     */
    public String getRootMessage(String message){
        RootMessageExample rootMessageExample = new RootMessageExample();
        rootMessageExample.createCriteria().andNameEqualTo(message);
        List<RootMessage> rootMessages = rootMessageMapper.selectByExample(rootMessageExample);
        if(rootMessages.isEmpty()&& rootMessages == null){
            return null;
        }
        return rootMessages.get(0).getValue();
    }

    /**
     * 通过用户id查找用户
     * @param uid
     * @return
     */
    public User getUserByUid(Integer uid){
        return userMapper.selectByPrimaryKey(uid);
    }

    /**
     * 发布添加地理位置
     * @param latitude 经度
     * @param longitude 纬度
     * @param key 活动的id
     * @param value
     * @return
     */
    public Boolean setPosition(Double longitude, Double latitude, Integer key, String value){
        //添加地理位置和aid到Redis缓存中
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(latitude);
        coordinate.setLongitude(longitude);
        coordinate.setKey(Integer.toString(key));
        Long reo = RedisUtil.addReo(coordinate, value);
        if (reo == null) {
            return false;
        }
        return true;
    }

    /**
     * 查找附近的ClubKey
     * @param uid
     * @param longitude
     * @param latitude
     * @return
     */
    public List<GeoRadiusResponse> getClubGeoRadiusResponse(Integer uid, Double longitude, Double latitude){
        String range = getRootMessage("range");
        Coordinate coordinate = new Coordinate();
        coordinate.setKey(Integer.toString(uid));
        coordinate.setLatitude(latitude);
        coordinate.setLongitude(longitude);
        return RedisUtil.geoQueryClub(coordinate, Double.valueOf(range));
    }

    /**
     * 查找附近的ShopKey
     * @param uid
     * @param longitude
     * @param latitude
     * @return
     */
    public List<GeoRadiusResponse> getShopGeoRadiusResponse(Integer uid, Double longitude, Double latitude){
        String range = getRootMessage("range");
        Coordinate coordinate = new Coordinate();
        coordinate.setKey(Integer.toString(uid));
        coordinate.setLatitude(latitude);
        coordinate.setLongitude(longitude);
        return RedisUtil.geoQueryShop(coordinate, Double.valueOf(range));
    }

    /**
     * 把Club对象中的值转移到ClubVO对象中
     * @param club
     * @return
     */
    public ClubVO setClubToClubVO(Club club){

        //查找报名每个会所的人数
        ClubBuyExample example = new ClubBuyExample();
        example.createCriteria().andClubIdEqualTo(club.getId());
        List<ClubBuy> clubBuys = clubBuyMapper.selectByExample(example);
        List<String> list = new ArrayList<>();

        Integer nums = 0;
        //通过查询出来的Club,报名人头像,Club星级赋值给ClubVO
        if(!clubBuys.isEmpty()){
            nums = clubBuys.size();
        }

        //查询每一个发布的Club中购买了的每一个用户的每一个头像，遍历出来并添加到头像集合List<String>中
        for(ClubBuy c : clubBuys){
            list.add(getUserByUid(c.getBuyerId()).getHeader());
        }

        ClubVO clubVO = new ClubVO();
        clubVO.setNums(nums);
        clubVO.setId(club.getId());
        clubVO.setPublishId(club.getPublishId());
        clubVO.setImgsUrl(club.getImgsUrl());
        clubVO.setProjectName(club.getProjectName());
        clubVO.setProjectDesc(club.getProjectDesc());
        clubVO.setProjectAddress(club.getProjectAddress());
        clubVO.setProjectPrice(club.getProjectPrice());
        clubVO.setMarketPrice(club.getMarketPrice());
        clubVO.setHeader(list);
        clubVO.setState(club.getState());
        clubVO.setCreateTime(club.getCreateTime());

        //查找此club的星级
        Integer starNums = getStarNumsByClubId(club.getId());
        //System.out.println("set:"+starNums);

        clubVO.setStar(starNums);

        //封装发布者头像
        User user = getUserByUid(club.getPublishId());
        clubVO.setPublishHeader(user.getHeader());

        return clubVO;
    }

    /**
     * 把Shop对象中的值转移到ShopVO对象中
     * @param shop
     * @return
     */
    public ShopVO setShopToShopVO(Shop shop){

        //查找报名每个会所的人数
        ShopBuyExample example = new ShopBuyExample();
        example.createCriteria().andGuideIdEqualTo(shop.getId());
        List<ShopBuy> shopBuys = shopBuyMapper.selectByExample(example);
        List<String> list = new ArrayList<>();

        Integer nums = 0;
        //通过查询出来的Shop,报名人头像,Shop星级赋值给ShopVO
        if(!shopBuys.isEmpty()){
            nums = shopBuys.size();
        }

        //查询每一个发布的Shop中购买了的每一个用户的每一个头像，遍历出来并添加到头像集合List<String>中
        for(ShopBuy s : shopBuys){
            User userByUid = getUserByUid(s.getBuyerId());
            if(userByUid == null){
                continue;
            }
            list.add(userByUid.getHeader());
        }

        ShopVO shopVO = new ShopVO();
        shopVO.setNums(nums);
        shopVO.setId(shop.getId());
        shopVO.setPublishId(shop.getPublishId());
        shopVO.setImgsUrl(shop.getImgsUrl());
        shopVO.setServiceArea(shop.getServiceArea());
        shopVO.setTravelTime(shop.getTravelTime());
        shopVO.setCharge(shop.getCharge());
        shopVO.setBoolClose(shop.getBoolClose());
        shopVO.setHeader(list);
        shopVO.setState(shop.getState());
        shopVO.setCreateTime(shop.getCreateTime());

        //查找此Shop的星级
        Integer starNums = getStarNumsByGuideId(shop.getId());

        shopVO.setStar(starNums);

        //把发布者的信息传入ShopVO类中
        User user = getUserByUid(shop.getPublishId());
        shopVO.setPublishIdNickname(user.getNickname());
        shopVO.setPublishIdBirthday(user.getBirthday());
        shopVO.setPublishIdHeader(user.getHeader());
        shopVO.setPublishIdSex(user.getSex());
        shopVO.setPublishIdSignature(user.getSignature());

        return shopVO;
    }

    /**
     * 通过club_id查找此club的星级
     * @param cid
     * @return
     */
    public Integer getStarNumsByClubId(Integer cid){
        //查询这个club的星级(每一个clubStar的和除以评论人)
        ClubStarExample clubStarExample = new ClubStarExample();
        clubStarExample.createCriteria().andClubIdEqualTo(cid);
        List<ClubStar> clubStars = clubStarMapper.selectByExample(clubStarExample);

        //没有人评论，默认为5星
        if(clubStars.isEmpty()){
            return 5;
        }

        //有人评星则进行计算
        int starNums = 0;
        for(ClubStar clubStar: clubStars){
            starNums = starNums + clubStar.getStar();
        }
        //System.out.println(starNums);
        //求平均星个数
        int size = clubStars.size();
        if(size == 0){
            return 5;
        }
        starNums = starNums/size;
        //System.out.println(starNums);
        return starNums;
    }

    /**
     * 通过guide_id查找此的星级
     * @param gid
     * @return
     */
    public Integer getStarNumsByGuideId(Integer gid){
        //查询这个shop的星级(每一个shopStar的和除以评论人)
        ShopStarExample example = new ShopStarExample();
        example.createCriteria().andGuideIdEqualTo(gid);
        List<ShopStar> shopStars = shopStarMapper.selectByExample(example);

        //没有人评论，默认为5星
        if(shopStars.isEmpty()){
            return 5;
        }

        //有人评星则进行计算
        int starNums = 0;
        for(ShopStar shopStar: shopStars){
            starNums = starNums + shopStar.getStart();
        }

        //求平均星个数
        int size = shopStars.size();
        if(size == 0){
            return 5;
        }
        starNums = starNums/size;
        return starNums;
    }

    /**
     * 算出集合内元素的和
     * @param list
     * @return
     */
    public Integer listSum(List<Integer> list){
        Integer sum = 0;
        if(list.isEmpty()){
            return sum;
        }
        for(int i=0; i<list.size(); i++){
            sum=sum+list.get(i);
        }
        return sum;
    }

}
