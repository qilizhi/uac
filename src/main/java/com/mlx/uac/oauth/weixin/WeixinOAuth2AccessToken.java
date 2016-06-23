package com.mlx.uac.oauth.weixin;

import java.util.Objects;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.utils.Preconditions;

public class WeixinOAuth2AccessToken extends OAuth2AccessToken {
	private static final long serialVersionUID = 8901381135476613449L;
	private String accessToken;
	private String tokenType;
	private Integer expiresIn;
	private String refreshToken;
	private String scope;
	private String openid;

	public WeixinOAuth2AccessToken(String accessToken) {
		this(accessToken, null);
	}

	public WeixinOAuth2AccessToken(String accessToken, String rawResponse) {
		this(accessToken, null, null, null, null, rawResponse);
	}

	public WeixinOAuth2AccessToken(String accessToken, String tokenType, Integer expiresIn, String refreshToken,
			String scope, String rawResponse) {
		super(rawResponse);
		Preconditions.checkNotNull(accessToken, "access_token can't be null");
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
		this.refreshToken = refreshToken;
		this.scope = scope;
	}

	public WeixinOAuth2AccessToken(String accessToken, String tokenType, Integer expiresIn, String refreshToken,
			String scope, String openid, String rawResponse) {
		super(rawResponse);
		Preconditions.checkNotNull(accessToken, "access_token can't be null");
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
		this.refreshToken = refreshToken;
		this.scope = scope;
		this.openid = openid;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public String getTokenType() {
		return this.tokenType;
	}

	public Integer getExpiresIn() {
		return this.expiresIn;
	}

	public String getRefreshToken() {
		return this.refreshToken;
	}

	public String getScope() {
		return this.scope;
	}

	public int hashCode() {
		int hash = 7;
		hash = 41 * hash + Objects.hashCode(this.accessToken);
		hash = 41 * hash + Objects.hashCode(this.tokenType);
		hash = 41 * hash + Objects.hashCode(this.expiresIn);
		hash = 41 * hash + Objects.hashCode(this.refreshToken);
		hash = 41 * hash + Objects.hashCode(this.scope);
		hash = 41 * hash + Objects.hashCode(this.openid);
		return hash;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (super.getClass() != obj.getClass()) {
			return false;
		}
		WeixinOAuth2AccessToken other = (WeixinOAuth2AccessToken) obj;
		if (!(Objects.equals(this.accessToken, other.getAccessToken()))) {
			return false;
		}
		if (!(Objects.equals(this.tokenType, other.getTokenType()))) {
			return false;
		}
		if (!(Objects.equals(this.refreshToken, other.getRefreshToken()))) {
			return false;
		}
		if (!(Objects.equals(this.scope, other.getScope()))) {
			return false;
		}
		if (!(Objects.equals(this.openid, other.getOpenid()))) {
			return false;
		}
		return Objects.equals(this.expiresIn, other.getExpiresIn());
	}

	public String toString() {
		return "WeixinOAuth2AccessToken{access_token=" + this.accessToken + ", token_type=" + this.tokenType
				+ ", expires_in=" + this.expiresIn + ", refresh_token=" + this.refreshToken + ", scope=" + this.scope
				+ ", openid=" + this.openid + '}';
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
