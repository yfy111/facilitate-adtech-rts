package com.adtech.rts.pingan;

import com.adtech.rts.model.response.medicalAction.PingAnMedicalActionResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.List;

public class PingAnTest {

    public static final String url = "http://test-hpcp-cqdp.pingan.com.cn/qwService/mhis-nhfpc/hisQueryApi";

    public static final String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVq2yEArlj+BTe25buxTaVbmHs5caEvaHqBT+TWJupr0ES5v8/2Mn1lgy4yFrfPWkHFs4AEZct/cpWDGG84RdbqWYxtlQLa6NQWss2dmpzOz+J2zRsC3/NoFBrwyXaUvWchEyyTQj615rYUo9pvRBPsZuBhrgIUnkLN2IouW9eRQIDAQAB";

    /**
     * 将返回结果转化为String
     *
     * @param entity
     * @return
     * @throws Exception
     */
    private static String getRespString(HttpEntity entity) throws Exception {
        if (entity == null) {
            return null;
        }
        InputStream is = entity.getContent();
        StringBuffer strBuf = new StringBuffer();
        byte[] buffer = new byte[4096];
        int r = 0;
        while ((r = is.read(buffer)) > 0) {
            strBuf.append(new String(buffer, 0, r, "UTF-8"));
        }
        return strBuf.toString();
    }


    public static String  httpPostWithJson(String params){
        String responseContent = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity pentity = new StringEntity(params,"UTF-8");
            httpPost.setEntity(pentity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                responseContent = getRespString(entity);
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }


    /**
     * 调用平安接口数据
     */
    public static  List<PingAnMedicalActionResponse> getPingAnData(String idCard){
        List<PingAnMedicalActionResponse> paList = null;
        JSONObject param = new JSONObject();
        param.put("busseId","Q610");
        param.put("identity",idCard);
        param.put("pageNum","1");
        String p = param.toJSONString();
        try {
            PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
            //用公钥加密
            byte[] publicEncrypt = RSAUtil.publicEncrypt(p.getBytes(), publicKey);
            String body = "";
            //加密后的内容Base64编码
            body = RSAUtil.byte2Base64(publicEncrypt);
            body = body.replaceAll("[\\t\\n\\r]", "");
            String result = httpPostWithJson(body.replace("\n",""));
            byte[] r = RSAUtil.base642Byte(result);
            byte[] r2 = RSAUtil.publicDecrypt(r,publicKey);
            String resultStr = new String(r2);
            System.out.println("返回密文解密："+new String(r2));
            paList = JSON.parseArray(resultStr,PingAnMedicalActionResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return paList;
    }
    public static void main(String[] args) {
        //512224195204122824
        //500228200511047356
        //500228198809250525
        //512224194207224274
        getPingAnData("512224195204122824");
//        JSONObject param = new JSONObject();
//        param.put("busseId","Q610");
//        param.put("identity","500230199108286329");
//        param.put("pageNum","1");
//        String p = param.toJSONString();
//        String body = "";
//        try {
//            //Map<String, Object> keyMap = RSAUtils.genKeyPair();
//            //publicKey = RSAUtils.getPublicKey(keyMap);
//            //privateKey = RSAUtils.getPrivateKey(keyMap);
//            //byte[] encodedData = RSAUtils.encryptByPublicKey(p.getBytes(), publicKey);
//            //System.out.println(new String(encodedData));
//            //byte[] rsaStr = RSAUtils.encryptByPublicKey(p.getBytes(), publicKeyStr);
//            //System.out.println(new String(rsaStr);
//            PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
//            //用公钥加密
//            byte[] publicEncrypt = RSAUtil.publicEncrypt(p.getBytes(), publicKey);
//            //加密后的内容Base64编码
//            body = RSAUtil.byte2Base64(publicEncrypt);
//            body = body.replaceAll("[\\t\\n\\r]", "");
//            String result = httpPostWithJson(body.replace("\n",""));
//            System.out.println("参数："+p);
//            System.out.println("加密参数：" + body);
//            System.out.println("返回密文：" + result );
//            byte[] r = RSAUtil.base642Byte(result);
//            byte[] r2 = RSAUtil.publicDecrypt(r,publicKey);
//            System.out.println("返回密文解密："+new String(r2));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



    }

}
