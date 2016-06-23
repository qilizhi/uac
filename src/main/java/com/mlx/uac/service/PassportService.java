package com.mlx.uac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlx.uac.dao.PassportMapper;
import com.mlx.uac.model.Passport;

@Service
public class PassportService {

	@Autowired
	private PassportMapper passportMapper;

	public int delete(Integer userId) {
		return passportMapper.delete(userId);
	}

	public int insert(Passport passport) {
		return passportMapper.insert(passport);
	}

	public Passport select(Passport passport) {
		return passportMapper.select(passport);
	}

	public List<Passport> list(Passport passport) {
		return passportMapper.list(passport);
	}

	public int update(Passport passport) {
		return passportMapper.update(passport);
	}
}
