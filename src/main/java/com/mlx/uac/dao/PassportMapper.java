package com.mlx.uac.dao;

import java.util.List;

import com.mlx.uac.model.Passport;

public interface PassportMapper {
	
	public int delete(Integer id);

	public int insert(Passport passport);

	public Passport select(Passport passport);

	public List<Passport> list(Passport passport);

	public int update(Passport passport);
}