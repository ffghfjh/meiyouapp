package com.meiyou.service;

import com.meiyou.model.Coordinate;
import com.meiyou.utils.Msg;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-22 21:26
 **/
public interface PeopleService {
    public Msg selPeoples(Coordinate coordinate,double radius);
}