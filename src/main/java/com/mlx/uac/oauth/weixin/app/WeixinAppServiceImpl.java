package com.mlx.uac.oauth.weixin.app;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.AbstractRequest;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.mlx.uac.oauth.weixin.WeixinOAuthConstants;

public class WeixinAppServiceImpl extends OAuth20Service {
	public WeixinAppServiceImpl(DefaultApi20 api, OAuthConfig config) {
		super(api, config);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected <T extends AbstractRequest> T createAccessTokenRequest(String code, T request) {
		final OAuthConfig config = getConfig();
		request.addBodyParameter(WeixinOAuthConstants.APPID, config.getApiKey());
		request.addBodyParameter(WeixinOAuthConstants.SECRET, config.getApiSecret());
		request.addParameter(OAuthConstants.CODE, code);
		request.addParameter(OAuthConstants.GRANT_TYPE, OAuthConstants.AUTHORIZATION_CODE);
		return request;
	}
}
