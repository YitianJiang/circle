package com.acircle.circle.service.impl;


import com.acircle.circle.service.OssService;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class OssServiceImpl implements OssService{

    @Value("${aliyun.sts.AccessKeyID}")
    private String aliyunOssAccessKeyID;

    @Value("${aliyun.sts.AccessKeySecret}")
    private String aliyunOssAccessKeySecret;

    @Value("${aliyun.sts.RoleArn}")
    private String aliyunOssRoleArn;

    @Value("${aliyun.sts.PolicyFile}")
    private String aliyunOssPolicyFile;

    @Value("${aliyun.sts.TokenExpireTime}")
    private int aliyunOssTokenExpireTime;

    @Value("${aliyun.sts.EndPoint}")
    private String endpoint;

    /**
     * 获取获取访问oss 的token
     *
     * @param roleSessionName 获取token的会话名称
     * @throws ClientException
     * @return 令牌
     */
    @Override
    public AssumeRoleResponse assumeRole(String roleSessionName) throws ClientException {
        // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
        // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
        // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
        // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
        DefaultProfile.addEndpoint("", "", "Sts", endpoint);
        IClientProfile profile = DefaultProfile.getProfile("", aliyunOssAccessKeyID, aliyunOssAccessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        // 创建一个 AssumeRoleRequest 并设置请求参数
        final AssumeRoleRequest request = new AssumeRoleRequest();
        // RoleArn 需要在 RAM 控制台上获取
        request.setRoleArn(aliyunOssRoleArn);
        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        request.setRoleSessionName(roleSessionName);
        // 授权策略
        request.setPolicy(readJson(aliyunOssPolicyFile));
        // 设置token时间
        request.setDurationSeconds(aliyunOssTokenExpireTime * 60L);
        // 发起请求，并得到response
        return client.getAcsResponse(request);
    }

    public String readJson(String path) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        // 返回值,使用StringBuffer
        StringBuffer data = new StringBuffer();
        //
        try {
            inputStream = new ClassPathResource(path).getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            // 每次读取文件的缓存
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                data.append(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭文件流
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }
}