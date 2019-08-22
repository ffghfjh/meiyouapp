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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @program: meiyou
 * @description:
 * @author: dengshilin
 * @create: 2019-08-22 10:33
 **/
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