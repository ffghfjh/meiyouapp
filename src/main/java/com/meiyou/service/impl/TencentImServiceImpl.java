package com.meiyou.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meiyou.pojo.User;
import com.meiyou.service.TencentImService;
import com.meiyou.utils.Constants;
import com.meiyou.utils.HttpUtil;
import com.tls.tls_sigature.tls_sigature;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-22 10:33
 **/
@Service
public class TencentImServiceImpl implements TencentImService {


    @Override
    public boolean registTencent(User user) {
        CloseableHttpClient client;
        CloseableHttpResponse response;

        /**
         * 随机数生成
         */
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 32; i++) {
            int randNum = rand.nextInt(9) + 1;
            String num = randNum + "";
            sb = sb.append(num);
        }
        String random = String.valueOf(sb); // 32未随机数

        tls_sigature.GenTLSSignatureResult result = tls_sigature.genSig(Constants.SDKAPPID, Constants.IDENTIFIER, Constants.PRIKEY);
        String usersig = result.urlSig; // 管理员账号签名
        System.out.println("注册腾讯云用户sig:"+usersig);

        /**
         * 接口调用地址
         */
        String URL = "https://console.tim.qq.com/v4/im_open_login_svc/account_import?sdkappid=" + Constants.SDKAPPID
                + "&identifier=" + Constants.IDENTIFIER + "&usersig=" + usersig + "&random=" + random
                + "&contenttype=json";
        // TODO Auto-generated method stub

        HttpPost post = new HttpPost(URL);

        ObjectMapper objectmapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("Identifier", user.getAccount());
        if(user.getNickname()!=null&&!user.getNickname().equals("")) {
            data.put("Nick",user.getNickname());
        }
        if(user.getHeader()!=null&&!user.getHeader().equals("")) {
            data.put("FaceUrl", user.getHeader());
        }
        HttpEntity entity;
        try {
            entity = new StringEntity(objectmapper.writeValueAsString(data), "utf-8");
            post.setEntity(entity);
            client = HttpClients.createDefault();
            response = client.execute(post);
            HttpEntity repentity = response.getEntity();
            String resresult = EntityUtils.toString(repentity);

            JSONObject json = JSONObject.fromObject(resresult);
            System.out.println("注册腾讯云："+json.toString());
            if (json.get("ActionStatus").equals("OK")) {
                Boolean re =setAddFriMode(user.getAccount(),"AllowType_Type_NeedConfirm");//设置加好友须经同意
                if(!re) {
                    return false;
                }else {
                    return true;
                }
            } else {
                return false;
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getUserState(String account) {
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 32; i++) {
            int randNum = rand.nextInt(9) + 1;
            String num = randNum + "";
            sb = sb.append(num);
        }
        String random = String.valueOf(sb); // 32未随机数
        String usersig = tls_sigature.genSig(Constants.SDKAPPID, Constants.IDENTIFIER, Constants.PRIKEY).urlSig;
        String url = "https://console.tim.qq.com/v4/openim/querystate?sdkappid="+Constants.SDKAPPID+"&identifier="+Constants.IDENTIFIER+"&usersig="+usersig+"&random="+random+"&contenttype=json";
        Map<String,Object> map = new HashMap<String,Object>();
        List<String> list = new ArrayList<String>();
        list.add(account);
        map.put("To_Account",list);
        JSONObject object = HttpUtil.postToJSONObject(url, map);
        System.out.println("获取在线状态："+object.toString());
        if(object.getString("ActionStatus").equals("OK")) {
            List<Map<String,String>> list1 = (List<Map<String,String>>) object.get("QueryResult");
            Map<String,String> map1 = list1.get(0);
            String state = map1.get("State");
            if(state.equals("Online")) {
                return 1;
            }else {
                return 0;
            }
        }else {
            return 3;
        }
    }

    @Override
    public boolean setUserInfo(User user) {
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 32; i++) {
            int randNum = rand.nextInt(9) + 1;
            String num = randNum + "";
            sb = sb.append(num);
        }
        String random = String.valueOf(sb); // 32未随机数
        String usersig = tls_sigature.genSig(Constants.SDKAPPID, Constants.IDENTIFIER, Constants.PRIKEY).urlSig;
        String URL = "https://console.tim.qq.com/v4/profile/portrait_set?sdkappid=" + Constants.SDKAPPID + "&identifier="+ Constants.IDENTIFIER +"&usersig=" + usersig + "&random=" + random + "&contenttype=json";

        Map<String,Object> map = new HashMap<String,Object>();
        List<Object> list = new ArrayList<Object>();
        if(user.getNickname()!=null){
            //昵称
            Map<String,Object> nickname = new HashMap<String,Object>();
            nickname.put("Tag", "Tag_Profile_IM_Nick");
            nickname.put("Value", user.getNickname());
            list.add(nickname);
        }

        if(user.getSex()!=null){
            //性别
            Map<String,Object> sex = new HashMap<String,Object>();

            sex.put("Tag", "Tag_Profile_IM_Gender");
            if(user.getSex()) {
                sex.put("Value", "Gender_Type_Female");
            }else {
                sex.put("Value", "Gender_Type_Male");
            }
            list.add(sex);
        }
        if(user.getSignature()!=null){
            //签名
            Map<String,Object> qianming = new HashMap<String,Object>();
            qianming.put("Tag", "Tag_Profile_IM_SelfSignature");
            qianming.put("Value", user.getSignature());
            list.add(qianming);
        }

        if(user.getHeader()!=null) {
            //头像
            Map<String,Object> img = new HashMap<String,Object>();
            img.put("Tag", "Tag_Profile_IM_Image");
            img.put("Value", user.getHeader());
            list.add(img);
        }
        map.put("From_Account", user.getAccount());
        map.put("ProfileItem", list);
        JSONObject json = HttpUtil.postToJSONObject(URL, map);
        System.out.println(json.toString());
        if(json.getString("ActionStatus").equals("OK")) {
          return true;
        }else {
           return false;
        }
    }

    public boolean userSendMsg(String fromUser, String toUser, String context, Integer syncOtherMachine) {
        // TODO Auto-generated method stub

        CloseableHttpClient client;
        CloseableHttpResponse response;
        /**
         * 随机数生成
         */
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 32; i++) {
            int randNum = rand.nextInt(9) + 1;
            String num = randNum + "";
            sb = sb.append(num);
        }
        String random = String.valueOf(sb); // 32未随机数
        String usersig = tls_sigature.genSig(Constants.SDKAPPID, Constants.IDENTIFIER, Constants.PRIKEY).urlSig;
        String URL = "https://console.tim.qq.com/v4/openim/sendmsg?sdkappid=" + Constants.SDKAPPID + "&identifier="
                + Constants.IDENTIFIER + "&usersig=" + usersig + "&random=" + random + "&contenttype=json";

        HttpPost post = new HttpPost(URL);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("SyncOtherMachine", syncOtherMachine);
        map.put("From_Account", fromUser);
        map.put("To_Account", toUser);

        StringBuffer msgrandom = new StringBuffer();
        for (int i = 1; i <= 9; i++) {
            int randNum = rand.nextInt(9) + 1;
            String num = randNum + "";
            msgrandom = msgrandom.append(num);
        }

        map.put("MsgRandom", Integer.parseInt(String.valueOf(msgrandom)));
        List<Map<String, Object>> msgBody = new ArrayList<Map<String, Object>>();
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("MsgType", "TIMTextElem");
        Map<String, String> msg = new HashMap<String, String>();
        msg.put("Text", context);
        body.put("MsgContent", msg);
        msgBody.add(body);
        map.put("MsgBody", msgBody);

        try {
            HttpEntity entity = new StringEntity(mapper.writeValueAsString(map));
            post.setEntity(entity);
            client = HttpClients.createDefault();
            response = client.execute(post);
            String result = EntityUtils.toString(response.getEntity());
            JSONObject json = JSONObject.fromObject(result);

            if (json.get("ActionStatus").equals("OK")) {
                return true;
            } else {
                return false;
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }



    /**
     * 设置用户加好友方式
     * @param account
     * @param mode
     * @return
     */
    private Boolean setAddFriMode(String account,String mode) {
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 32; i++) {
            int randNum = rand.nextInt(9) + 1;
            String num = randNum + "";
            sb = sb.append(num);
        }
        String random = String.valueOf(sb); // 32未随机数
        String usersig = tls_sigature.genSig(Constants.SDKAPPID, Constants.IDENTIFIER, Constants.PRIKEY).urlSig;
        String url = "https://console.tim.qq.com/v4/profile/portrait_set?sdkappid="+Constants.SDKAPPID+"&identifier="+Constants.IDENTIFIER+"&usersig="+usersig+"&random="+random+"&contenttype=json";
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> profileItem = new HashMap<String,Object>();
        profileItem.put("Tag","Tag_Profile_IM_AllowType");
        profileItem.put("Value", mode);
        List<Object> list = new ArrayList<Object>();
        list.add(profileItem);
        map.put("From_Account", account);
        map.put("ProfileItem", list);
        JSONObject object = HttpUtil.postToJSONObject(url, map);
        String result = object.getString("ActionStatus");
        System.out.println("设置验证方式："+object.toString());
        if(result.equals("OK")) {
            return true;
        }else {
            return false;
        }
    }
}