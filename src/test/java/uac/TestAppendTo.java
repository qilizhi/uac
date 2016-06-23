package uac;

import java.util.Map;

import org.junit.Test;

import com.github.scribejava.core.model.ParameterList;
import com.mlx.uac.oauth.weixin.WeixinOAuthConstants;

public class TestAppendTo {

	@Test
	public void test() {
		Map<String, String> additionalParams = null;
		final ParameterList parameters = new ParameterList(additionalParams);
		parameters.add(WeixinOAuthConstants.APPID, "111");

		final String callback = "wwww";
		if (callback != null) {
			parameters.add(WeixinOAuthConstants.REDIRECT_URI, callback);
		}

		parameters.add(WeixinOAuthConstants.RESPONSE_TYPE, WeixinOAuthConstants.CODE);

		final String scope = "0000";
		if (scope != null) {
			parameters.add(WeixinOAuthConstants.SCOPE, scope);
		}

		final String state = "0099999";
		if (state != null) {
			parameters.add(WeixinOAuthConstants.STATE, state);
		}
		String tempUrl = parameters.appendTo("https://open.weixin.qq.com/connect/oauth2/authorize")
				+ WeixinOAuthConstants.WECHAT_REDIRECT;
		System.out.println(tempUrl);
	}

	@Test
	public void test1() {
		String state = "WEIXINWWWWWRR5drktjbtmp0bwyupWAP";
		String tradeType = state.substring(state.length() - 3, state.length());
		System.out.println(tradeType);
		System.out.println(state.substring(0, state.length() - 19));
	}

}
