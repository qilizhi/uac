package com.mlx.uac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlx.uac.dao.UserSecurityCenterMapper;
import com.mlx.uac.model.UserSecurityCenter;

@Service
public class UserSecurityCenterService {

	@Autowired
	private UserSecurityCenterMapper userSecurityCenterMapper;

	public int delete(Integer userId) {
		return userSecurityCenterMapper.delete(userId);
	}

	public int insert(UserSecurityCenter userSecurityCenter) {
		return userSecurityCenterMapper.insert(userSecurityCenter);
	}

	public UserSecurityCenter select(UserSecurityCenter userSecurityCenter) {
		return userSecurityCenterMapper.select(userSecurityCenter);
	}

	public List<UserSecurityCenter> list(UserSecurityCenter userSecurityCenter) {
		return userSecurityCenterMapper.list(userSecurityCenter);
	}

	public int update(UserSecurityCenter userSecurityCenter) {
		return userSecurityCenterMapper.update(userSecurityCenter);
	}
}
