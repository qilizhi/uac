package com.mlx.uac.dao;

import java.util.List;

import com.mlx.uac.model.UserInfo;

public interface UserInfoMapper {
	
	public int delete(Integer userId);

    public int insert(UserInfo userInfo);

    public UserInfo select(UserInfo userInfo);
    
    public UserInfo getUserInfoByLoginName(UserInfo userInfo);
    
    public List<UserInfo> list(UserInfo userInfo);

    public int update(UserInfo userInfo);
    
}