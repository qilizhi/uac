package com.mlx.uac.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlx.uac.exception.BusiException;
import com.mlx.uac.model.UserBindInfo;
import com.mlx.uac.model.UserInfo;
import com.mlx.uac.utils.ApplicationProperties;

@Service
public class ThirdUnionLoginService {
	private static final Logger logger = LoggerFactory.getLogger(ThirdUnionLoginService.class);

	@Autowired
	private UserBindInfoService userBindInfoService;

	@Autowired
	private UserInfoService userInfoService;

	public Map<String, String> thirdUnionLogin(Map<String, String> paramMap) throws Exception {
		logger.debug("第三方登陆统一处理服务");
		String responseCode = "0000";
		Map<String, String> resultMap = new HashMap<String, String>();
		// 根据bindId bindType sysCnl openId loginType loginToken source tokenExpTime
		UserBindInfo userBindInfo = new UserBindInfo();
		userBindInfo.setBindId(paramMap.get("bindId"));
		userBindInfo.setBindType(paramMap.get("bindType"));

		List<UserBindInfo> userBindInfoList = userBindInfoService.list(userBindInfo);

		userBindInfo.setOpenId(paramMap.get("openId"));
		userBindInfo = userBindInfoService.select(userBindInfo);

		if (null == userBindInfoList || userBindInfoList.isEmpty() || 0 == userBindInfoList.size()
				|| null == userBindInfo) {
			// 不存在bindId的用户则新增一个用户
			logger.debug("不存在该用户信息，新增一个会员绑定信息");
			userBindInfo = new UserBindInfo();
			BeanUtils.populate(userBindInfo, paramMap);

			if (!(null == userBindInfoList || userBindInfoList.isEmpty() || 0 == userBindInfoList.size())) {
				// 若已经存在同样的绑定id，则把最旧那条记录的头像地址和昵称作为当前插入记录的头像地址和昵称
				UserBindInfo userBindInfoTemp = userBindInfoService.selectFirstRecord(userBindInfo);
				userBindInfo.setHeadImgUrl(userBindInfoTemp.getHeadImgUrl());
				userBindInfo.setUserId(userBindInfoTemp.getUserId());
				userBindInfo.setNickname(userBindInfoTemp.getNickname());
			}
			userBindInfo.setCreateTime(Calendar.getInstance().getTime());
			userBindInfoService.insert(userBindInfo);
		}

		if (0 == userBindInfo.getUserId()) {
			// 同一个用户没有成功注册过
			logger.debug("该用户从来没有成功注册过,请注册");
			responseCode = "1002";
			throw new BusiException(responseCode, ApplicationProperties.getMessage(responseCode));
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userBindInfo.getUserId());
		userInfo = userInfoService.select(userInfo);

		if (null == userInfo) {
			logger.debug("该用户在会员属性表中不存在记录,请重新注册");
			responseCode = "1003";
			throw new BusiException(responseCode, ApplicationProperties.getMessage(responseCode));
		}

		return resultMap;
	}

	public void handlerThirdInfo(Map<String, String> paramMap) {

	}
}
