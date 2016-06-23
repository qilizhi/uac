package com.mlx.uac.dao;

import java.util.List;

import com.mlx.uac.model.Token;

public interface TokenMapper {

	public int delete(Token token);

    public int insert(Token token);

    public Token select(Token token);

    public int update(Token token);

	public List<Token> list(Token token);
}