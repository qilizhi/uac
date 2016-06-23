package com.mlx.uac.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.mlx.uac.common.AjaxResult;
import com.mlx.uac.exception.BusiException;
import com.mlx.uac.oauth.weixin.WeixinOAuthConstants;
import com.mlx.uac.oauth.weixin.app.WeixinAppApi;
import com.mlx.uac.oauth.weixin.wap.WeixinWapApi;
import com.mlx.uac.utils.ApplicationProperties;
import com.mlx.uac.utils.CreateRandomUtil;

@Controller
public class CreAuthorizeUrlAPI extends BaseAPI {
	private static final Logger logger = LoggerFactory.getLogger(CreAuthorizeUrlAPI.class);

	/**
	 * 返回302跳转授权登陆地址
	 * @param request
	 * @param sysCnl WAP、IOS、ANDROID、WEB
	 * @param bindType MLX:美丽行 WEIXIN:微信 ALIPAY:支付宝 WEIBO:微博 QQ:qq
	 * @param scope 0:静默授权 1:用户确认授权
	 * @param methodType 接口类型xxxxxx(主要是用来做验签识别标识使用)
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/authorizeurl.do")
	public AjaxResult creAuthorizeUrl(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("查询授权登陆");
		Map<String, String> paramMap = transformParam(request);
		String responseCode = "0000";
		String apiKey = null;
		String apiSecret = null;
		String callback = null;
		String scope = null;
		String state = paramMap.get("bindType");
		OAuth20Service service = null;

		if ("WEIXIN".equals(paramMap.get("bindType")) && "WAP".equals(paramMap.get("sysCnl"))) {
			apiKey = ApplicationProperties.getMessage("WAP_WX_APPID");
			apiSecret = ApplicationProperties.getMessage("WAP_WX_APPSECRET");
			callback = ApplicationProperties.getMessage("WAP_CALLBACK_URL");
			state = state + CreateRandomUtil.createRandom(false, 16) + "WAP";
			if ("0".equals(paramMap.get("scope"))) {
				scope = WeixinOAuthConstants.SNSAPI_BASE;
			} else {
				scope = WeixinOAuthConstants.SNSAPI_USERINFO;
			}
			service = new ServiceBuilder().apiKey(apiKey).apiSecret(apiSecret).callback(callback).scope(scope)
					.state(state).build(WeixinWapApi.instance());
		} else if ("WEIXIN".equals(paramMap.get("bindType"))
				&& ("IOS".equals(paramMap.get("sysCnl")) || "ANDROID".equals(paramMap.get("sysCnl")))) {

			apiKey = ApplicationProperties.getMessage("APP_WX_APPID");
			apiSecret = ApplicationProperties.getMessage("APP_WX_APPSECRET");
			callback = ApplicationProperties.getMessage("APP_CALLBACK_URL");
			state = state + CreateRandomUtil.createRandom(false, 16) + "APP";
			if ("0".equals(paramMap.get("scope"))) {
				scope = WeixinOAuthConstants.SNSAPI_BASE;
			} else {
				scope = WeixinOAuthConstants.SNSAPI_USERINFO;
			}
			service = new ServiceBuilder().apiKey(apiKey).apiSecret(apiSecret).callback(callback).scope(scope)
					.state(state).build(WeixinAppApi.instance());

		} else {
			logger.error("暂时不支持[{}]的[{}]联合登陆方式", paramMap.get("bindType"), paramMap.get("sysCnl"));
			throw new BusiException(responseCode, ApplicationProperties.getMessage(responseCode));
		}

		// 组装跳转地址
		final String authorizationUrl = service.getAuthorizationUrl();

		logger.info("授权地址为[{}]", authorizationUrl);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("authorizationUrl", authorizationUrl);
		resultMap.putAll(paramMap);
		return new AjaxResult(responseCode, ApplicationProperties.getMessage(responseCode), resultMap);
	}

	/**
	 * 转换HttpServletRequest参数
	 * @param request
	 * @return
	 */
	public Map<String, String> transformParam(HttpServletRequest request) {
		Map<String, String[]> requestMap = request.getParameterMap();
		Map<String, String> paramMap = new HashMap<String, String>();
		String[] values = null;
		for (String key : requestMap.keySet()) {
			values = requestMap.get(key);
			if (null != values && values.length > 0) {
				paramMap.put(key, values[0]);
			}
		}
		return paramMap;
	}
}
