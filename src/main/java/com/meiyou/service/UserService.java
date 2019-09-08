package com.meiyou.service;

import com.meiyou.pojo.User;
import com.meiyou.utils.Msg;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {
    //支付宝登录
    public Msg alipayLogin(String auth_code);

    //手机号登录
    public Msg phoneLogin(String phone, String password);

    //用户注册
    public Msg userRegist(String code, String shareCode, String phone, String password, String nickname, String birthday, boolean sex, String qianming, MultipartFile img, HttpServletRequest req);

    //获取腾讯云IM鉴权Sig
    public Msg getSig(int uid, String token);

    //微信登录
    public Msg weChatLogin(String auth_code);

    /**
     * 通过用户id对用户进行封号
     * @param uid
     * @param type
     * @return
     */
    Msg hideUserById(String uid, String type);

    /**
     * hzy
     * 根据id获取用户信息
     *
     * @param uid
     * @return
     */
    User getUserById(int uid);

    /**
     * 添加金币
     *
     * @param id
     * @param money
     * @return
     */
    boolean addMoney(int id, float money);

    /**
     * 减少金币
     *
     * @param id
     * @param money
     * @return
     */
    boolean delMoney(int id, float money);

    /**
     * 发送红包
     *
     * @param id
     * @param toAccount
     * @return
     */
    Msg sendMoney(int id, String text, int money, String toAccount);

    /**
     * 查询红包
     *
     * @param id
     * @return
     */
    Msg selRedPackage(int id);

    /**
     * 领取红包
     *
     * @param id
     * @return
     */
    Msg getRedPackage(int id);

    /**
     * @Description: 查询用户余额
     * @Author: JK
     * @Date: 2019/8/26
     */
    String selectUserMoney(String uid, String password);

    /**
     * 手机绑定支付宝
     *
     * @param uId
     * @param aliId
     * @param aliToken
     * @param phone
     * @param code
     * @param password
     * @param shareCode
     * @return
     */
    Msg registBindAlipay(int uId, String aliId, String aliToken, String phone, String code, String password, String shareCode);

    /**
     * 手机绑定微信
     *
     * @param uId
     * @param openId
     * @param accesssToken
     * @param phone
     * @param code
     * @param password
     * @param shareCode
     * @return
     */
    Msg registBindWeChat(int uId, String openId, String accesssToken, String phone, String code, String password, String shareCode);

    /**
     * 手机绑定QQ
     *
     * @param uId
     * @param qqOpenId
     * @param qqToken
     * @param phone
     * @param code
     * @param password
     * @param shareCode
     * @return
     */
    Msg registBindQQ(int uId, String qqOpenId, String qqToken, String phone, String code, String password, String shareCode);

    /**
     * QQ登录
     *
     * @param qqOpenId
     * @param qqToken
     * @return
     */
    Msg qqLogin(String qqOpenId, String qqToken);

    /**
     * 用户举报
     * @param reporter_id [举报人]
     * @param reported_id [被举报人]
     * @param type [举报类型]
     * @param content [举报备注]
     * @return
     */
    Msg userReport(Integer reporter_id, Integer reported_id,  String type, String content);

    /**
     * 拉取用户资料
     */
    Msg getUserInfo(int uID, String token);

    /**
     * 红包过期处理
     *
     * @param hid
     */
    public void redPackageOverdue(int hid);

    /**
     * 账号拉取资料
     *
     * @param account
     * @return
     */
    public Msg getOtherMsg(String account);

    /**
     * 分页拉取用户资料
     * @param page
     * @param number
     * @return
     */
    public List<Map<String,Object>> selUserInfoByPage(int page, int number);

    /**
     * 查询所有用户数据
     * @return
     */
    public List<User> selAllUser();

    /**
     * 管理员拉取用户资料
     * @param account
     * @return
     */
    public User selUserInfoByAdmin(String account);

    /**
     * 通话结束
     * @param channelId
     * @return
     */
    public Msg videoEnd(String channelId);

    /**
     * 提交审核
     * @param cashId
     * @param result
     * @return
     */
    public Msg cashAudit(int cashId,int result);


    /**
     * 通过ID获取用户显示资料，包括是否在线
     * @param uId
     * @return
     */
    public Msg selUserInfoById(int uId);

    /**
     * 忘记密码
     * @param phone
     * @param code
     * @param password
     * @return
     */
    public Msg forgetPassword(String phone,String code,String password);
}
