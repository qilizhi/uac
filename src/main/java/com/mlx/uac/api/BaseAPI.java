package com.mlx.uac.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mlx.uac.common.AjaxResult;
import com.mlx.uac.exception.BusiException;
import com.mlx.uac.utils.ApplicationProperties;

@Controller
public class BaseAPI {

	private static Logger log = LoggerFactory.getLogger(BaseAPI.class);

	@ExceptionHandler
	@ResponseBody
	public AjaxResult exception(HttpServletRequest request, Exception ex) {

		log.error("拦截到异常信息 ", ex);

		String code = "9999";
		String msg = ApplicationProperties.getMessage(code);

		if (ex instanceof BusiException) {
			BusiException busiException = (BusiException) ex;
			code = busiException.getCode();
			msg = busiException.getMessage();
			if (null == msg || "".equals(msg.trim())) {
				msg = ApplicationProperties.getMessage(code);
			}
		}
		
		log.error("拦截到异常信息code[{}], msg[{}]", code, msg);

		return new AjaxResult(code, msg, null);
	}
	
}
