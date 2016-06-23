package com.mlx.uac.oauth.weixin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.utils.Preconditions;

public class WeixinOAuth2AccessTokenJsonExtractor implements TokenExtractor<WeixinOAuth2AccessToken> {
	private static final String ACCESS_TOKEN_REGEX = "\"access_token\"\\s*:\\s*\"(\\S*?)\"";
	private static final String TOKEN_TYPE_REGEX = "\"token_type\"\\s*:\\s*\"(\\S*?)\"";
	private static final String EXPIRES_IN_REGEX = "\"expires_in\"\\s*:\\s*\"?(\\d*?)\"?\\D";
	private static final String REFRESH_TOKEN_REGEX = "\"refresh_token\"\\s*:\\s*\"(\\S*?)\"";
	private static final String SCOPE_REGEX = "\"scope\"\\s*:\\s*\"(\\S*?)\"";

	public static WeixinOAuth2AccessTokenJsonExtractor instance() {
		return InstanceHolder.INSTANCE;
	}

	public WeixinOAuth2AccessToken extract(String response) {
		Preconditions.checkEmptyString(response,
				"Response body is incorrect. Can't extract a token from an empty string");

		String accessToken = extractParameter(response, "\"access_token\"\\s*:\\s*\"(\\S*?)\"", true);
		String openid = extractParameter(response, "\"openid\"\\s*:\\s*\"(\\S*?)\"", true);
		String tokenType = extractParameter(response, "\"token_type\"\\s*:\\s*\"(\\S*?)\"", false);
		String expiresInString = extractParameter(response, "\"expires_in\"\\s*:\\s*\"?(\\d*?)\"?\\D", false);
		// Integer expiresIn;
		Integer expiresIn;
		try {
			expiresIn = (expiresInString == null) ? null : Integer.valueOf(expiresInString);
		} catch (NumberFormatException nfe) {
			expiresIn = null;
		}
		String refreshToken = extractParameter(response, "\"refresh_token\"\\s*:\\s*\"(\\S*?)\"", false);
		String scope = extractParameter(response, "\"scope\"\\s*:\\s*\"(\\S*?)\"", false);

		return createToken(accessToken, tokenType, expiresIn, refreshToken, scope, openid, response);
	}

	protected WeixinOAuth2AccessToken createToken(String accessToken, String tokenType, Integer expiresIn,
			String refreshToken, String scope, String openid, String response) {
		return new WeixinOAuth2AccessToken(accessToken, tokenType, expiresIn, refreshToken, scope, openid, response);
	}

	protected static String extractParameter(String response, String regex, boolean required) throws OAuthException {
		Matcher matcher = Pattern.compile(regex).matcher(response);
		if (matcher.find()) {
			return matcher.group(1);
		}

		if (required) {
			throw new OAuthException(response, null);
			// throw new OAuthException(
			// "Response body is incorrect. Can't extract a '" + regex + "' from this: '" + response + "'", null);
		}

		return null;
	}

	private static class InstanceHolder {
		private static final WeixinOAuth2AccessTokenJsonExtractor INSTANCE = new WeixinOAuth2AccessTokenJsonExtractor();
	}

}
