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
import com.acircle.circle.service.UserCacheService;
import com.acircle.circle.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.nimbusds.jose.JWSObject;
import com.sankuai.inf.leaf.api.SnowflakeService;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    @Reference
    private SnowflakeService snowflakeService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserCacheService userCacheService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;
    @Autowired
    private HttpServletRequest request;

    @Override
    public long create(UserCreateDto userCreateDto) {
        //查询是否有相同用户名的用户
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(userCreateDto.getName());
        List<User> users = userMapper.selectByExample(userExample);
        if (!CollectionUtils.isEmpty(users)) {
            Asserts.fail("该用户已经存在");
        }
        User user = new User();
        user.setId(userCreateDto.getId());
        user.setName(userCreateDto.getName());
        //将密码进行加密操作
        user.setPassword(BCrypt.hashpw(userCreateDto.getPassword()));
        user.setCreateTime(new Date());
        user.setStatus(1);
        user.setAvatarUrl("https://yitianjiang-circle.oss-cn-beijing.aliyuncs.com/default-avatar.png");
        return userMapper.insert(user);
    }

    @Override
    public User getCurrentUser(){
        String realToken = request.getHeader(AuthConstant.JWT_TOKEN_HEADER).replace(AuthConstant.JWT_TOKEN_PREFIX, "");
        User user = null;
        try {
            UserDto userDto = JSONUtil.toBean(JWSObject.parse(realToken).getPayload().toString(), UserDto.class);
            user = userCacheService.getUser(userDto.getId());
            if(user!=null){
                return user;
            }else{
                user = userMapper.selectByPrimaryKey(userDto.getId());
                userCacheService.setUser(user);
                return user;
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }finally {
            return user;
        }
    }

    public User getUserByUsername(String username) {
        User user = userCacheService.getUserByName(username);
        if(user!=null){
            return user;
        }else{
            UserExample userExample = new UserExample();
            userExample.createCriteria().andNameEqualTo(username);
            List<User> users = userMapper.selectByExample(userExample);
            if (!CollectionUtils.isEmpty(users)) {
                userCacheService.setUserByName(users.get(0));
                return users.get(0);
            }
            return null;
        }
    }

    @Override
    public UserDto loadUserByUsername(String username){
        User user = getUserByUsername(username);
        if(user!=null){
            UserDto userDto = new UserDto();
            BeanUtil.copyProperties(user,userDto);
            userDto.setRoles(CollUtil.toList("前台会员"));
            return userDto;
        }
        return null;
    }

    //本来想增加手机号登录 但是验证码要企业主体
    //北京这边的公司注册还是有点麻烦(深圳那边相对简单一些，不过我人不在深圳那边)，还要房产证，你在这边租的房，
    // 向那些中介要都不一定要得到(上次我就没要到),这里只能仅支持密码登录了。
    @Override
    public CommonResult login(User user) {
        if(StrUtil.isEmpty(user.getName())||StrUtil.isEmpty(user.getPassword())){
            Asserts.fail("用户名或密码不能为空！");
        }
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.PORTAL_CLIENT_ID);
        params.put("client_secret","123456");
        params.put("grant_type","password");
        if(user.getToutiaoOpenId() != null){
            params.put("toutiaoUserId",user.getToutiaoOpenId().toString());
        }else {
            params.put("username", user.getName());
            params.put("password", user.getPassword());
        }
        CommonResult commonResult = authService.getAccessToken(params);
        LoginReturnDto loginReturnDto = new LoginReturnDto();
        if(ResultCode.SUCCESS.getCode() == commonResult.getCode() && commonResult.getData() != null){
            //todo 把getUserByUsername拿出来的信息存到缓存中 之前在oauth模块已经调用了一次getUserByUsername（为了签发token）
            User userDetal = getUserByUsername(user.getName());
            insertLoginLog(userDetal);
            userDetal.setPassword("");
            loginReturnDto.setTokenDetail((LinkedHashMap) commonResult.getData());
            loginReturnDto.setUserDetail(userDetal);
            commonResult.setData(loginReturnDto);
        }
        if(user.getToutiaoOpenId() != null){
            //检查数据库中是否有toutiaoopenid对应的记录
            //如果有 则根据这条记录返回token
            //没有 则创建一条新的记录 再返回token
        }
        return commonResult;
    }

    /**
     * 添加登录记录
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
        userCacheService.delUser(user.getId());
        return 1;
    }

    @Override
    public long update(User user){
        return  userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public long delete(long id){
        return userMapper.deleteByPrimaryKey(id);
    }

}
