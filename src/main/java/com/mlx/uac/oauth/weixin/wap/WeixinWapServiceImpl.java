package com.mlx.uac.oauth.weixin.wap;

import java.io.IOException;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.AbstractRequest;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.mlx.uac.oauth.weixin.WeixinOAuth2AccessToken;
import com.mlx.uac.oauth.weixin.WeixinOAuthConstants;

public class WeixinWapServiceImpl extends OAuth20Service {
	private WeixinWapApi api;

	public WeixinWapServiceImpl(DefaultApi20 api, OAuthConfig config) {
		super(api, config);
		// TODO Auto-generated constructor stub
	}

	public WeixinWapServiceImpl(WeixinWapApi api, OAuthConfig config) {
		super(api, config);
		this.api = api;
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

	public WeixinOAuth2AccessToken getWeixinAccessToken(String code) throws Exception {
		OAuthRequest request = (OAuthRequest) createAccessTokenRequest(code,
				new OAuthRequest(this.api.getAccessTokenVerb(), this.api.getAccessTokenEndpoint(), this));

		return sendWeixinAccessTokenRequestSync(request);
	}

	protected WeixinOAuth2AccessToken sendWeixinAccessTokenRequestSync(OAuthRequest request) throws IOException {
		return ((WeixinOAuth2AccessToken) this.api.getWeixinAccessTokenExtractor().extract(request.send().getBody()));
	}

	public void signWeixinRequest(WeixinOAuth2AccessToken accessToken, AbstractRequest request) {
		request.addQuerystringParameter("access_token", accessToken.getAccessToken());
		request.addQuerystringParameter("openid", accessToken.getOpenid());
	}
}
