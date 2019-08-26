package com.meiyou;

import com.meiyou.mapper.CommentMapper;
import com.meiyou.pojo.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/24 17:27
 * @description：评论业务测试类
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommetTest {

    @Autowired
    CommentMapper commentMapper;

    @Test
    public void getMyActivityComments() {
        List<Comment> comments = commentMapper.listMyActivityComments(16);
        if (comments.isEmpty()) {
            System.out.println("对象为空");
        }
        System.out.println("评论的条数为：" + comments.size());
        for (Comment comment : comments) {
            System.out.println("hzy---评论内容为： " + comment.getContent());
            System.out.println("hzy---用户昵称为： " + comment.getUser().getNickname());
            System.out.println("hzy--动态id为：" + comment.getActivity().getId());
            System.out.println("hzy---动态内容为： " + comment.getActivity().getContent());
        }
    }

}
