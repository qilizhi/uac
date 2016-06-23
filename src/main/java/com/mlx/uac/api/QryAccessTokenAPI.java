package com.mlx.uac.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mlx.uac.common.AjaxResult;
import com.mlx.uac.exception.BusiException;
import com.mlx.uac.oauth.weixin.WeixinExceptionModel;
import com.mlx.uac.oauth.weixin.WeixinInfoModel;
import com.mlx.uac.oauth.weixin.WeixinOAuth2AccessToken;
import com.mlx.uac.oauth.weixin.WeixinOAuthConstants;
import com.mlx.uac.oauth.weixin.app.WeixinAppApi;
import com.mlx.uac.oauth.weixin.wap.WeixinWapApi;
import com.mlx.uac.oauth.weixin.wap.WeixinWapServiceImpl;
import com.mlx.uac.service.ThirdUnionLoginService;
import com.mlx.uac.utils.ApplicationProperties;

@Controller
public class QryAccessTokenAPI extends BaseAPI {
	private static final Logger logger = LoggerFactory.getLogger(QryAccessTokenAPI.class);

	@Autowired
	private ThirdUnionLoginService thirdUnionLoginService;

	/**
	 * 根据授权码去获取相应的access_token
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/qryaccesstoken.do")
	public AjaxResult qryAccessToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("欢迎使用查询access_token");
		Map<String, String> paramMap = transformParam(request);
		String responseCode = "0000";
		String apiKey = null;
		String apiSecret = null;
		String callback = null;
		String scope = null;
		String state = paramMap.get("state");
		logger.debug("sysCnl==" + state.substring(state.length() - 3, state.length()));
		logger.debug("bindType==" + state.substring(0, state.length() - 19));

		paramMap.put("sysCnl", state.substring(state.length() - 3, state.length()));
		paramMap.put("bindType", state.substring(0, state.length() - 19));

		OAuth20Service service = null;
		OAuthRequest requestInfo = null;
		Response responseInfo = null;
		Map<String, String> resultMap = new HashMap<String, String>();

		if ("WEIXIN".equals(paramMap.get("bindType"))) {
			WeixinOAuth2AccessToken accessToken = null;

			if ("0".equals(paramMap.get("scope"))) {
				scope = WeixinOAuthConstants.SNSAPI_BASE;
			} else {
				scope = WeixinOAuthConstants.SNSAPI_USERINFO;
			}

			if ("WAP".equals(paramMap.get("sysCnl"))) {
				apiKey = ApplicationProperties.getMessage("WAP_WX_APPID");
				apiSecret = ApplicationProperties.getMessage("WAP_WX_APPSECRET");
				callback = ApplicationProperties.getMessage("WAP_CALLBACK_URL");

				// 创建服务
				service = new ServiceBuilder().apiKey(apiKey).apiSecret(apiSecret).callback(callback).scope(scope)
						.state(state).build(WeixinWapApi.instance());
				// 获取accessToken
				try {
					accessToken = ((WeixinWapServiceImpl) service).getWeixinAccessToken(paramMap.get("code"));
				} catch (OAuthException e) {
					logger.error("获取微信accessToken失败，原因是:" + e.getMessage());
					WeixinExceptionModel weixinExceptionModel = new Gson().fromJson(e.getMessage(),
							new TypeToken<WeixinExceptionModel>() {
							}.getType());
					responseCode = weixinExceptionModel.getErrcode();
					if (ApplicationProperties.getMessage(responseCode).trim().isEmpty()) {
						responseCode = "4000";
					}
					throw new BusiException(responseCode, ApplicationProperties.getMessage(responseCode));
				}

				// 组装获取微信会员个人信息请求
				requestInfo = new OAuthRequest(Verb.GET, "https://api.weixin.qq.com/sns/userinfo", service);
				((WeixinWapServiceImpl) service).signWeixinRequest(accessToken, requestInfo);

			} else if ("APP".equals(paramMap.get("sysCnl"))) {
				apiKey = ApplicationProperties.getMessage("APP_WX_APPID");
				apiSecret = ApplicationProperties.getMessage("APP_WX_APPSECRET");
				callback = ApplicationProperties.getMessage("APP_CALLBACK_URL");

				// 创建服务
				service = new ServiceBuilder().apiKey(apiKey).apiSecret(apiSecret).callback(callback).scope(scope)
						.state(state).build(WeixinAppApi.instance());
				// 获取accessToken
				try {
					accessToken = ((WeixinWapServiceImpl) service).getWeixinAccessToken(paramMap.get("code"));
				} catch (Exception e) {
					logger.error("获取微信accessToken失败，原因是:" + e.getMessage());
					WeixinExceptionModel weixinExceptionModel = new Gson().fromJson(e.getMessage(),
							new TypeToken<WeixinExceptionModel>() {
							}.getType());
					responseCode = weixinExceptionModel.getErrcode();
					throw new BusiException(responseCode, ApplicationProperties.getMessage(responseCode));
				}
				// 组装获取微信会员个人信息请求
				requestInfo = new OAuthRequest(Verb.GET, "https://api.weixin.qq.com/sns/userinfo", service);
				((WeixinWapServiceImpl) service).signWeixinRequest(accessToken, requestInfo);

			} else {
				responseCode = "1001";
				logger.error("暂时不支持[{}]的[{}]联合登陆方式", paramMap.get("bindType"), paramMap.get("sysCnl"));
				throw new BusiException(responseCode, ApplicationProperties.getMessage(responseCode));
			}
			logger.info("[{}]的[{}]的授权码[{}]的accessToken值为[{}]", paramMap.get("bindType"), paramMap.get("sysCnl"),
					paramMap.get("code"), accessToken);

			// 获取微信会员个人信息
			responseInfo = requestInfo.send();
			// 转换微信用户个人信息到相应的po对象
			WeixinInfoModel weixinInfoModel = new Gson().fromJson(responseInfo.getBody(),
					new TypeToken<WeixinInfoModel>() {
					}.getType());

			resultMap.put("accessToken", accessToken.toString());
			resultMap.put("userInfo", responseInfo.getBody());

			paramMap.put("loginToken", accessToken.getAccessToken());
			paramMap.put("headImgUrl", weixinInfoModel.getHeadimgurl());
			paramMap.put("openId", weixinInfoModel.getOpenid());
			paramMap.put("openName", weixinInfoModel.getNickname());
			paramMap.put("nickname", weixinInfoModel.getNickname());
			paramMap.put("bindId", weixinInfoModel.getUnionid());
		} else {
			responseCode = "1001";
			logger.error("暂时不支持[{}]的[{}]联合登陆方式", paramMap.get("bindType"), paramMap.get("sysCnl"));
			throw new BusiException(responseCode, ApplicationProperties.getMessage(responseCode));
		}

		// 调用第三方联登处理逻辑
		paramMap.putAll(resultMap);
		resultMap = thirdUnionLoginService.thirdUnionLogin(paramMap);

		return new AjaxResult(responseCode, ApplicationProperties.getMessage(responseCode), resultMap);
	}

	/**
	 * 转换HttpServletRequest参数
	 * @param request
	 * @return
	 */
	private Map<String, String> transformParam(HttpServletRequest request) {
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
