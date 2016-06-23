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

import com.mlx.uac.common.AjaxResult;

/**
 * 
 * TODO 第三方联登接口
 * @author libafei
 * @time 2016年6月21日下午4:54:22
 * @type_name ThirdAccountLoginAPI
 *
 */
@Controller
@RequestMapping("/thirdaccount")
public class ThirdAccountLoginAPI extends BaseAPI {
	private static final Logger logger = LoggerFactory.getLogger(ThirdAccountLoginAPI.class);

	@RequestMapping("/login.do")
	@ResponseBody
	public AjaxResult thirdAccountLogin(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("欢迎使用第三方联登接口");
		Map<String, String[]> requestMap = request.getParameterMap();
		Map<String, String> paramMap = new HashMap<String, String>();
		String[] values = null;
		for (String key : requestMap.keySet()) {
			values = requestMap.get(key);
			if (null != values && values.length > 0) {
				paramMap.put(key, values[0]);
			}
		}

		return new AjaxResult();
	}
}
