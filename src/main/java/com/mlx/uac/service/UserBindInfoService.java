package com.mlx.uac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlx.uac.dao.UserBindInfoMapper;
import com.mlx.uac.model.UserBindInfo;

@Service
public class UserBindInfoService {

	@Autowired
	private UserBindInfoMapper userBindInfoMapper;

	public int delete(UserBindInfo userBindInfo) {
		return userBindInfoMapper.delete(userBindInfo);
	}

	public int insert(UserBindInfo userBindInfo) {
		return userBindInfoMapper.insert(userBindInfo);
	}

	public UserBindInfo select(UserBindInfo userBindInfo) {
		return userBindInfoMapper.select(userBindInfo);
	}

	public List<UserBindInfo> list(UserBindInfo userBindInfo) {
		return userBindInfoMapper.list(userBindInfo);
	}

	public int update(UserBindInfo userBindInfo) {
		return userBindInfoMapper.update(userBindInfo);
	}

	public UserBindInfo selectFirstRecord(UserBindInfo userBindInfo) {
		return userBindInfoMapper.selectFirstRecord(userBindInfo);
	}
}
