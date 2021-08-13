package com.acircle.circle.service;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;

public interface OssService {
    AssumeRoleResponse assumeRole(String roleSessionName) throws ClientException;
}
