package com.mlx.uac.dao;

import java.util.List;

import com.mlx.uac.model.UserSecurityCenter;

public interface UserSecurityCenterMapper {
	
	public int delete(Integer userId);

    public int insert(UserSecurityCenter userSecurityCenter);

    public UserSecurityCenter select(UserSecurityCenter userSecurityCenter);
    
    public List<UserSecurityCenter> list(UserSecurityCenter userSecurityCenter);

    public int update(UserSecurityCenter userSecurityCenter);

}