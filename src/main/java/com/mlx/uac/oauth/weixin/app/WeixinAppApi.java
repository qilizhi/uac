package com.mlx.uac.oauth.weixin.app;

import java.util.Map;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.ParameterList;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.mlx.uac.oauth.weixin.WeixinOAuthConstants;

/**
 * 
 * TODO 实现微信APP交互逻辑
 * @author libafei
 * @time 2016年6月20日下午4:11:58
 * @type_name WeixinAppApi
 *
 */
public class WeixinAppApi extends DefaultApi20 {
	protected WeixinAppApi() {
	}

	private static class InstanceHolder {
		private static final WeixinAppApi INSTANCE = new WeixinAppApi();
	}

	public static WeixinAppApi instance() {
		return InstanceHolder.INSTANCE;
	}

	@Override
	public String getAccessTokenEndpoint() {
		// TODO Auto-generated method stub
		return "https://api.weixin.qq.com/sns/oauth2/access_token";
	}

	@Override
	public String getAuthorizationUrl(OAuthConfig config, Map<String, String> additionalParams) {
		// TODO Auto-generated method stub
		final ParameterList parameters = new ParameterList(additionalParams);
		parameters.add(WeixinOAuthConstants.APPID, config.getApiKey());

		final String callback = config.getCallback();
		if (callback != null) {
			parameters.add(WeixinOAuthConstants.REDIRECT_URI, callback);
		}

		parameters.add(WeixinOAuthConstants.RESPONSE_TYPE, WeixinOAuthConstants.CODE);

		final String scope = config.getScope();
		if (scope != null) {
			parameters.add(WeixinOAuthConstants.SCOPE, scope);
		}

		final String state = config.getState();
		if (state != null) {
			parameters.add(WeixinOAuthConstants.STATE, state);
		}
		return parameters.appendTo("https://open.weixin.qq.com/connect/oauth2/authorize")
				+ WeixinOAuthConstants.WECHAT_REDIRECT;
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("use getAuthorizationUrl instead");
	}

	@Override
	public OAuth20Service createService(OAuthConfig config) {
		return new WeixinAppServiceImpl(this, config);
	}
}
