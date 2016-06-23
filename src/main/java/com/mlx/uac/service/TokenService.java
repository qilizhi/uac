package com.mlx.uac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlx.uac.dao.TokenMapper;
import com.mlx.uac.model.Token;

@Service
public class TokenService {

	@Autowired
	private TokenMapper tokenMapper;

	public int delete(Token token) {
		return tokenMapper.delete(token);
	}

	public int insert(Token token) {
		return tokenMapper.insert(token);
	}

	public Token select(Token token) {
		return tokenMapper.select(token);
	}

	public List<Token> list(Token token) {
		return tokenMapper.list(token);
	}

	public int update(Token token) {
		return tokenMapper.update(token);
	}
}
