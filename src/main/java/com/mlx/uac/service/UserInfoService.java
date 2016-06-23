package com.mlx.uac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mlx.uac.dao.PassportMapper;
import com.mlx.uac.dao.UserBindInfoMapper;
import com.mlx.uac.dao.UserInfoMapper;
import com.mlx.uac.dao.UserSecurityCenterMapper;
import com.mlx.uac.dict.BindType;
import com.mlx.uac.model.UserBindInfo;
import com.mlx.uac.model.UserInfo;

@Service
public class UserInfoService {
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
    private PassportMapper passportMapper;
    
	@Autowired
    private UserBindInfoMapper userBindInfoMapper;
    
	@Autowired
    private UserSecurityCenterMapper userSecurityCenterMapper;
	
	public int delete(Integer userId) {
		return userInfoMapper.delete(userId);
	}
	
	@Transactional
    public int insert(UserInfo userInfo) {
    	int num = userInfoMapper.insert(userInfo);
    	
    	if (null != userInfo.getPassport()) {
    		userInfo.getPassport().setUserId(userInfo.getUserId());
    		passportMapper.insert(userInfo.getPassport());
    	}
    	
    	if (null != userInfo.getUserSecurityCenter()) {
    		userInfo.getUserSecurityCenter().setUserId(userInfo.getUserId());
    		userSecurityCenterMapper.insert(userInfo.getUserSecurityCenter());
    	}
    	
    	if (null != userInfo.getUserBindInfos()) {
    		for (UserBindInfo userBindInfo : userInfo.getUserBindInfos()) {
    			userBindInfo.setUserId(userInfo.getUserId());
    			//如果美丽行注册用户新增会员绑定绑定信息,第三方联登用户更新
    			if (BindType.MLX.getId().equals(userBindInfo.getBindType())) {
    				userBindInfoMapper.insert(userBindInfo);
    			} else {
    				userBindInfoMapper.update(userBindInfo);
    			}
    		}
    	}
    	
    	return num;
    }
    
    public UserInfo select(UserInfo userInfo) {
    	return userInfoMapper.select(userInfo);
    }
    
    public UserInfo selectByLoginName(String loginName) {
    	UserInfo userInfo = new UserInfo();
    	userInfo.setUserName(loginName);
    	
    	//根据userName查询用户信息
    	userInfo = userInfoMapper.getUserInfoByLoginName(userInfo);
    	
    	if (null == userInfo) {
    		userInfo = new UserInfo();
        	userInfo.setMobile(loginName);
        	
        	//根据mobile查询用户信息
        	userInfo = userInfoMapper.getUserInfoByLoginName(userInfo);
    	}
    	
    	return userInfo;
    }
    
    public List<UserInfo> list(UserInfo userInfo) {
    	return userInfoMapper.list(userInfo);
    }
    
    public int update(UserInfo userInfo) {
    	return userInfoMapper.update(userInfo);
    }
	
}
