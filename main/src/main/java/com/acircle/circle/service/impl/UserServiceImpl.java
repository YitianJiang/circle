package com.acircle.circle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONUtil;
import com.acircle.circle.common.api.CommonResult;
import com.acircle.circle.common.api.ResultCode;
import com.acircle.circle.common.constant.AuthConstant;
import com.acircle.circle.common.domain.UserDto;
import com.acircle.circle.common.exception.Asserts;
import com.acircle.circle.dto.LoginReturnDto;
import com.acircle.circle.dto.UpdatePasswordParam;
import com.acircle.circle.dto.UserCreateDto;
import com.acircle.circle.mapper.UserLoginLogMapper;
import com.acircle.circle.mapper.UserMapper;
import com.acircle.circle.model.User;
import com.acircle.circle.model.UserExample;
import com.acircle.circle.model.UserLoginLog;
import com.acircle.circle.service.AuthService;
import com.acircle.circle.service.CacheService;
import com.acircle.circle.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.nimbusds.jose.JWSObject;
import com.sankuai.inf.leaf.api.SnowflakeService;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = LoggerFactory.getLogger(EsArticleServiceImpl.class);
    @Reference
    private SnowflakeService snowflakeService;
    @Autowired
    private AuthService authService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;
    @Autowired
    private HttpServletRequest request;

    @Override
    public User getCurrentUser(){
        String userStr = request.getHeader(AuthConstant.USER_TOKEN_HEADER);
        if(StrUtil.isEmpty(userStr)){
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }
        UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
        User user = cacheService.getUser(userDto.getId());
        if(user!=null){
            return user;
        }else{
            user = userMapper.selectByPrimaryKey(userDto.getId());
            cacheService.setUser(user);
            return user;
        }
    }

    @Override
    public UserDto getCurrentUserBaseInfoByJWT(){
        String realToken = request.getHeader(AuthConstant.JWT_TOKEN_HEADER).replace(AuthConstant.JWT_TOKEN_PREFIX, "");
        String userStr = "";
        try {
            JWSObject jwsObject = JWSObject.parse(realToken);
            userStr = jwsObject.getPayload().toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
        return userDto;
    }

    public User getUserByUsername(String username) {
        User user = cacheService.getUserByName(username);
        if(user!=null){
            return user;
        }else{
            UserExample userExample = new UserExample();
            userExample.createCriteria().andNameEqualTo(username);
            List<User> users = userMapper.selectByExample(userExample);
            if (!CollectionUtils.isEmpty(users)) {
                cacheService.setUserByName(users.get(0));
                return users.get(0);
            }
            return null;
        }
    }

    @Override
    public User getUserByUserId(long userId) {
        User user = cacheService.getUser(userId);
        if(user!=null){
            return user;
        }else{
            user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                cacheService.setUser(user);
                return user;
            }
            return null;
        }
    }

    //auth???????????????????????????jwt
    @Override
    public UserDto loadUserByUsername(String username){
        User user = getUserByUsername(username);
        if(user!=null){
            UserDto userDto = new UserDto();
            BeanUtil.copyProperties(user,userDto);
            userDto.setRoles(CollUtil.toList("????????????"));
            return userDto;
        }
        return null;
    }

    @Override
    public CommonResult create(UserCreateDto userCreateDto) {
        //???????????????????????????????????????
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(userCreateDto.getName());
        List<User> users = userMapper.selectByExample(userExample);
        if (!CollectionUtils.isEmpty(users)) {
            Asserts.fail("?????????????????????");
        }
        User user = new User();
        user.setId(userCreateDto.getId());
        user.setName(userCreateDto.getName());
        //???????????????????????????
        user.setPassword(BCrypt.hashpw(userCreateDto.getPassword()));
        user.setCreateTime(new Date());
        user.setStatus(1);
        user.setAvatarUrl("https://yitianjiang-circle.oss-cn-beijing.aliyuncs.com/default-avatar.png");
        if(userMapper.insert(user) == 0)  Asserts.fail("????????????");
        user.setPassword(userCreateDto.getPassword());
        return login(user);
    }

    //???????????????????????????????????????????????????????????????????????????????????????????????????
    @Override
    public CommonResult login(User user) {
        if(StrUtil.isEmpty(user.getName())||StrUtil.isEmpty(user.getPassword())){
            Asserts.fail("?????????????????????????????????");
        }
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.PORTAL_CLIENT_ID);
        params.put("client_secret","123456");
        params.put("grant_type","password");
        params.put("username", user.getName());
        params.put("password", user.getPassword());
        CommonResult commonResult = authService.getAccessToken(params);
        LoginReturnDto loginReturnDto = new LoginReturnDto();
        if(ResultCode.SUCCESS.getCode() == commonResult.getCode() && commonResult.getData() != null){
            User userDetal = getUserByUsername(user.getName());
            insertLoginLog(userDetal);
            userDetal.setPassword("");
            loginReturnDto.setTokenDetail((LinkedHashMap) commonResult.getData());
            loginReturnDto.setUserDetail(userDetal);
            commonResult.setData(loginReturnDto);
        }
        return commonResult;
    }

    /**
     * ??????????????????
     */
    private void insertLoginLog(User userDetail) {
        Result result = snowflakeService.getId("normal");
        if(result.getStatus() == Status.EXCEPTION) Asserts.fail("fail to get id");
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setId(result.getId());
        userLoginLog.setUserId(userDetail.getId());
        userLoginLog.setCreateTime(new Date());
        userLoginLog.setIp(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr());
        userLoginLogMapper.insert(userLoginLog);
    }

    @Override
    public int updatePassword(UpdatePasswordParam param) {
        if(StrUtil.isEmpty(param.getUsername())
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(param.getUsername());
        List<User> users = userMapper.selectByExample(userExample);
        if(CollUtil.isEmpty(users)){
            return -2;
        }
        User user = users.get(0);
        if(!BCrypt.checkpw(param.getOldPassword(),user.getPassword())){
            return -3;
        }
        user.setPassword(BCrypt.hashpw(param.getNewPassword()));
        userMapper.updateByPrimaryKey(user);
        cacheService.delUser(user.getId());
        return 1;
    }

    @Override
    public long update(User user){
        UserDto userDto = getCurrentUserBaseInfoByJWT();
        user.setId(userDto.getId());
//        user.setPassword(BCrypt.hashpw(user.getPassword()));
        long res = userMapper.updateByPrimaryKeySelective(user);
        if( res > 0) {
            cacheService.delUserByName(userDto.getName());
            cacheService.delUser(userDto.getId());
        }
        return res;
    }

    @Override
    public long delete(long id){
        long res = userMapper.deleteByPrimaryKey(id);
        if(res > 0) {
            cacheService.delUserByName(getCurrentUserBaseInfoByJWT().getName());
            cacheService.delUser(id);
        }
        return res;
    }

}
