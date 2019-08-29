package com.meiyou.service.impl;

import com.meiyou.mapper.ClubMapper;
import com.meiyou.mapper.ClubStarMapper;
import com.meiyou.mapper.ShopMapper;
import com.meiyou.mapper.ShopStarMapper;
import com.meiyou.model.StarVo;
import com.meiyou.pojo.*;
import com.meiyou.service.MyStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: meiyou
 * @description: 我的评星实现类
 * @author: JK
 * @create: 2019-08-26 15:27
 **/
@Service
public class MyStarServiceImpl extends BaseServiceImpl implements MyStarService {

    @Autowired
    ClubStarMapper clubStarMapper;

    @Autowired
    ShopStarMapper shopStarMapper;

    @Autowired
    ClubMapper clubMapper;

    @Autowired
    ShopMapper shopMapper;

    /**
     * 查询全部会所的评星
     * @param uid
     * @return
     */
    @Override
    public List<StarVo> selectClubStar(Integer uid) {
        ClubStarExample clubStarExample = new ClubStarExample();
        clubStarExample.createCriteria().andEvaluationIdEqualTo(uid);
        List<ClubStar> clubStars = clubStarMapper.selectByExample(clubStarExample);

        List<StarVo> starVos = new ArrayList<>();
        User user = getUserByUid(uid);
        if(clubStars.isEmpty()){
            return starVos;
        }
        for(ClubStar clubStar : clubStars){

            //获取被评论的商家
            Club club = clubMapper.selectByPrimaryKey(clubStar.getClubId());
            //获取商家发布者id
            Integer publishId = club.getPublishId();
            //获取商家发布者信息
            User publish = getUserByUid(publishId);

            StarVo starVo = new StarVo();
            //添加发布者商家信息
            starVo.setPublishId(publishId);
            starVo.setPublishHeader(publish.getHeader());
            starVo.setPublishDesc(club.getProjectName());

            //添加评论信息到StarVo
            starVo.setId(clubStar.getId());
            starVo.setAddTime(clubStar.getCreateTime());
            starVo.setStar(clubStar.getStar());

            //添加评论者信息
            starVo.setAddId(user.getId());
            starVo.setAddHeader(user.getHeader());
            starVo.setAddNickname(user.getNickname());

            starVos.add(starVo);
        }
        return starVos;
    }

    /**
     * 查询全部导游的评星
     * @param uid
     * @return
     */
    @Override
    public List<StarVo> selectShopStar(Integer uid) {
        ShopStarExample shopStarExample = new ShopStarExample();
        shopStarExample.createCriteria().andEvaluationIdEqualTo(uid);
        List<ShopStar> shopStars = shopStarMapper.selectByExample(shopStarExample);

        List<StarVo> starVos = new ArrayList<>();
        User user = getUserByUid(uid);
        if(shopStars.isEmpty()){
            return starVos;
        }
        for(ShopStar shopStar : shopStars){

            //获取被评论的商家
            Shop shop = shopMapper.selectByPrimaryKey(shopStar.getGuideId());
            //获取商家发布者id
            Integer publishId = shop.getPublishId();
            //获取商家发布者信息
            User publish = getUserByUid(publishId);

            StarVo starVo = new StarVo();
            //添加发布者商家信息
            starVo.setPublishId(publishId);
            starVo.setPublishHeader(publish.getHeader());
            starVo.setPublishDesc(shop.getServiceArea());

            //添加评论信息到StarVo
            starVo.setId(shopStar.getId());
            starVo.setAddTime(shopStar.getCreateTime());
            starVo.setStar(shopStar.getStart());

            //添加评论者信息
            starVo.setAddId(user.getId());
            starVo.setAddHeader(user.getHeader());
            starVo.setAddNickname(user.getNickname());

            starVos.add(starVo);
        }
        return starVos;
    }
}
