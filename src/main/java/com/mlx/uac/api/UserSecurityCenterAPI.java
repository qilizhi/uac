package com.mlx.uac.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mlx.uac.common.AjaxResult;
import com.mlx.uac.dict.ConstEC;
import com.mlx.uac.model.UserInfo;
import com.mlx.uac.model.UserSecurityCenter;
import com.mlx.uac.service.UserInfoService;
import com.mlx.uac.service.UserSecurityCenterService;
import com.mlx.uac.utils.ApplicationProperties;

@Controller
@RequestMapping("/userSecurityCenter")
public class UserSecurityCenterAPI {

	private UserInfoService userInfoService;

	private UserSecurityCenterService userSecurityCenterService;

	/**
	 * 
	 * @param request
	 * @param userId
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bindMobile")
	@ResponseBody
	public AjaxResult bindMobile(HttpServletRequest request, Integer userId, String mobile) throws Exception {

		UserSecurityCenter userSecurityCenter = new UserSecurityCenter();
		userSecurityCenter.setUserId(userId);
		userSecurityCenter = userSecurityCenterService.select(userSecurityCenter);
		
		if (null == userSecurityCenter) {
			return new AjaxResult("0002", ApplicationProperties.getMessage("0002"), null);
		}
		
		if (1 == userSecurityCenter.getVerifyMobile()) {
			return new AjaxResult("0020", ApplicationProperties.getMessage("0020"), null);
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setMobile(mobile);
		userInfo = userInfoService.select(userInfo);
		if (null != userInfo) {
			return new AjaxResult("0040", ApplicationProperties.getMessage("0040"), null);
		}
		
		userInfo = new UserInfo();
		userInfo.setUserId(userId);
		userInfo = userInfoService.select(userInfo);
		if (null == userInfo) {
			return new AjaxResult("0002", ApplicationProperties.getMessage("0002"), null);
		}
		
		userInfo.setMobile(mobile);
		userInfoService.update(userInfo);
		
		userSecurityCenter.setVerifyMobile(1);
		userSecurityCenterService.update(userSecurityCenter);
		
		return new AjaxResult(ConstEC.CODE_0000, ConstEC.SUCCESS, null);
	}
	
	@RequestMapping("/bindEmail")
	@ResponseBody
	public AjaxResult bindEmail(HttpServletRequest request, Integer userId, String email) throws Exception {

		UserSecurityCenter userSecurityCenter = new UserSecurityCenter();
		userSecurityCenter.setUserId(userId);
		userSecurityCenter = userSecurityCenterService.select(userSecurityCenter);

		if (null == userSecurityCenter) {
			return new AjaxResult("0002", ApplicationProperties.getMessage("0002"), null);
		}

		if (1 == userSecurityCenter.getVerifyEmail()) {
			return new AjaxResult("0021", ApplicationProperties.getMessage("0021"), null);
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(email);
		userInfo = userInfoService.select(userInfo);
		if (null != userInfo) {
			return new AjaxResult("0041", ApplicationProperties.getMessage("0041"), null);
		}
		
		userInfo = new UserInfo();
		userInfo.setUserId(userId);
		userInfo = userInfoService.select(userInfo);
		if (null == userInfo) {
			return new AjaxResult("0002", ApplicationProperties.getMessage("0002"), null);
		}
		
		userInfo.setEmail(email);
		userInfoService.update(userInfo);
		
		userSecurityCenter.setVerifyEmail(1);
		userSecurityCenterService.update(userSecurityCenter);
		
		return new AjaxResult(ConstEC.CODE_0000, ConstEC.SUCCESS, null);
	}
	
	@RequestMapping("/bindCardNo")
	@ResponseBody
	public AjaxResult bindCardNo(HttpServletRequest request, Integer userId, Integer cardType, 
			String cardNo) throws Exception {

		UserSecurityCenter userSecurityCenter = new UserSecurityCenter();
		userSecurityCenter.setUserId(userId);
		userSecurityCenter = userSecurityCenterService.select(userSecurityCenter);

		if (null == userSecurityCenter) {
			return new AjaxResult("0002", ApplicationProperties.getMessage("0002"), null);
		}

		if (1 == userSecurityCenter.getVerifyCardNo()) {
			return new AjaxResult("0022", ApplicationProperties.getMessage("0022"), null);
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setCardNo(cardNo);
		userInfo = userInfoService.select(userInfo);
		if (null != userInfo) {
			return new AjaxResult("0042", ApplicationProperties.getMessage("0042"), null);
		}
		
		userInfo = new UserInfo();
		userInfo.setUserId(userId);
		userInfo = userInfoService.select(userInfo);
		if (null == userInfo) {
			return new AjaxResult("0002", ApplicationProperties.getMessage("0002"), null);
		}
		
		userInfo.setCardType(cardType);
		userInfo.setCardNo(cardNo);
		userInfoService.update(userInfo);
		
		userSecurityCenter.setVerifyCardNo(1);
		userSecurityCenterService.update(userSecurityCenter);
		
		return new AjaxResult(ConstEC.CODE_0000, ConstEC.SUCCESS, null);
	}
	
	@RequestMapping("/modifyMobile")
	@ResponseBody
	public AjaxResult modifyMobile(HttpServletRequest request, Integer userId, String mobile) throws Exception {

		UserSecurityCenter userSecurityCenter = new UserSecurityCenter();
		userSecurityCenter.setUserId(userId);
		userSecurityCenter = userSecurityCenterService.select(userSecurityCenter);

		if (null == userSecurityCenter) {
			return new AjaxResult("0002", ApplicationProperties.getMessage("0002"), null);
		}

		if (1 != userSecurityCenter.getVerifyMobile()) {
			return new AjaxResult("0030", ApplicationProperties.getMessage("0030"), null);
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setMobile(mobile);
		userInfo = userInfoService.select(userInfo);
		if (null != userInfo) {
			return new AjaxResult("0040", ApplicationProperties.getMessage("0040"), null);
		}
		
		userInfo = new UserInfo();
		userInfo.setUserId(userId);
		userInfo = userInfoService.select(userInfo);
		if (null == userInfo) {
			return new AjaxResult("0002", ApplicationProperties.getMessage("0002"), null);
		}
		
		if (mobile.equals(userInfo.getMobile())) {
			return new AjaxResult("0050", ApplicationProperties.getMessage("0050"), null);
		}
		
		userInfo.setMobile(mobile);
		userInfoService.update(userInfo);
		
		return new AjaxResult(ConstEC.CODE_0000, ConstEC.SUCCESS, null);
	}
}
