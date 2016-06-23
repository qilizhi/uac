package com.mlx.uac.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mlx.uac.dict.ConstEC;
import com.mlx.uac.exception.BusiException;
import com.mlx.uac.sign.Sign;
import com.mlx.uac.utils.ApplicationProperties;

/**
 * 拦截器处理
 * 
 * @author chenfh 2016年4月11日 上午10:08:11
 */
public class ParamCheckInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(ParamCheckInterceptor.class);

	private static final String OPTIONAL = "^\\[\\w+\\]$";

	private static final String MIDBRACKETS = "\\[|\\]";

	private static final String PRE = "req.";

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		MDC.put("type", "detail");
		this.checkParam(request);
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	private void checkParam(HttpServletRequest request) throws Exception {

		String methodType = ServletRequestUtils.getStringParameter(request, ConstEC.METHODTYPE, "");

		log.debug("{} 开始请求参数合法性校验", methodType);
		if ("".equals(methodType) || methodType.isEmpty()) {
			log.info("methodType [{}] 请求参数不存在", methodType);
			throw new BusiException("0003", ApplicationProperties.getMessage("0003"));
		}

		// 从配置文件中读取请求接口的相应参数
		String itfParam = ApplicationProperties.getMessage(PRE + methodType);

		if ("".equals(itfParam.trim()) || itfParam.isEmpty()) {
			log.info("{}[{}] 请求url和methodType对应的接口不存在!", methodType, itfParam);
			throw new BusiException("0011", ApplicationProperties.getMessage("0011"));
		}

		// 是否属于非必传参数,配置文件中使用[]来区分非必传参数
		boolean isOption = false;

		// 分割请求参数
		String[] keys = itfParam.split(",");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		int index = 0;
		String value = null;
		for (String key : keys) {
			value = request.getParameter(key);
			isOption = key.matches(OPTIONAL);
			// 判断是否必传
			if (isOption) {
				// 切出[] 中的参数名
				key = key.replaceAll(MIDBRACKETS, "");
				value = request.getParameter(key);
				// 非必传参数校验,不为空的不满足的抛出异常
				if (!StringUtils.isEmpty(value) && !value.trim()
						.matches(ApplicationProperties.getMessage(ConstEC.PARAMTER_VERIFY_PREFIX + key))) {
					ApplicationProperties.getMessage(ConstEC.PARAMTER_VERIFY_PREFIX + key);
					log.error("请求参数key[{}],value[{}]不合法", key, value);
					throw new BusiException("0005",
							key + "=" + value + "参数_" + ApplicationProperties.getMessage("0005"));
				}
				parameterMap.put(key, value);

				keys[index] = key;
				// 判断必传是否为空，再用正则匹配
			} else if (!StringUtils.isEmpty(value)
					&& value.trim().matches(ApplicationProperties.getMessage(ConstEC.PARAMTER_VERIFY_PREFIX + key))) {
				parameterMap.put(key, value);
			} else {
				// 抛出异常
				log.error("请求参数key[{}],value[{}]不合法", key, value);
				throw new BusiException("0005", key + "=" + value + "参数_" + ApplicationProperties.getMessage("0005"));
			}
			index++;
			log.debug("参数key[{}]=value[{}]", key, value);
		}
		String sign = request.getParameter("sign");
		String plain = Sign.getPlain(parameterMap, keys);
		plain += ApplicationProperties.getMessage(ConstEC.MLX_PRIVATEKEY);

		if (!Sign.verify(plain, sign)) {
			log.info("验签失败 plain[{}], sign[{}] ", plain, sign);
			throw new BusiException("0017", ApplicationProperties.getMessage("0017"));
		}

	}

}
