package com.mlx.uac.dao;

import java.util.List;

import com.mlx.uac.model.UserBindInfo;

public interface UserBindInfoMapper {

	public int delete(UserBindInfo userBindInfo);

	public int insert(UserBindInfo userBindInfo);

	public UserBindInfo select(UserBindInfo userBindInfo);

	public List<UserBindInfo> list(UserBindInfo userBindInfo);

	public int update(UserBindInfo userBindInfo);

	public UserBindInfo selectFirstRecord(UserBindInfo userBindInfo);
}