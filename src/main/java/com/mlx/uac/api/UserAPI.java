package com.mlx.uac.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mlx.uac.common.AjaxResult;
import com.mlx.uac.dict.ConstEC;
import com.mlx.uac.exception.BusiException;
import com.mlx.uac.model.Passport;
import com.mlx.uac.model.Token;
import com.mlx.uac.model.UserBindInfo;
import com.mlx.uac.model.UserInfo;
import com.mlx.uac.model.UserSecurityCenter;
import com.mlx.uac.service.PassportService;
import com.mlx.uac.service.TokenService;
import com.mlx.uac.service.UserBindInfoService;
import com.mlx.uac.service.UserInfoService;
import com.mlx.uac.sign.MD5Sign;
import com.mlx.uac.utils.ApplicationProperties;
import com.mlx.uac.utils.CharacterUtils;
import com.mlx.uac.utils.DateTimeUtil;
import com.mlx.uac.utils.HexStr;

/**
 * 会员Controller类
 * @author chenfh
 * 2016年6月22日 下午5:06:20
 */
@Controller
@RequestMapping("/user")
public class UserAPI extends BaseAPI {

	private static Logger log = LoggerFactory.getLogger(UserAPI.class);

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private PassportService passportService;

	@Autowired
	private UserBindInfoService userBindInfoService;

	@Autowired
	private TokenService tokenService;

	/**
	 * 获取会员基础信息
	 * @param request
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUser")
	@ResponseBody
	public AjaxResult getUser(HttpServletRequest request, UserInfo userInfo) throws Exception {
		userInfo = userInfoService.select(userInfo);
		if (null == userInfo) {
			return new AjaxResult("0002", ApplicationProperties.getMessage("0002"), null);
		}
		return new AjaxResult(ConstEC.CODE_0000, ConstEC.SUCCESS, userInfo);
	}

	/**
	 * 会员校验
	 * @param request
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkUser")
	@ResponseBody
	public AjaxResult checkUser(HttpServletRequest request, String userName) throws Exception {

		if (null != userInfoService.selectByLoginName(userName)) {
			return new AjaxResult("0100", ApplicationProperties.getMessage("0100"), null);
		}

		return new AjaxResult(ConstEC.CODE_0000, ConstEC.SUCCESS, null);
	}
	
	/**
	 * 修改会员个人资料
	 * @param request
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyUser")
	@ResponseBody
	public AjaxResult modifyUser(HttpServletRequest request, UserInfo userInfo) throws Exception {
		
		UserInfo userInfoTmp = new UserInfo();
		userInfoTmp.setUserId(userInfo.getUserId());
		userInfoTmp = userInfoService.select(userInfoTmp);
		
		if (null != userInfoTmp) {
			return new AjaxResult("0002", ApplicationProperties.getMessage("0002"), null);
		}
		
		userInfoService.update(userInfo);
		
		return new AjaxResult(ConstEC.CODE_0000, ConstEC.SUCCESS, null);
	}

	/**
	 * 会员认证
	 * @param request
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/authenticationUser")
	@ResponseBody
	public AjaxResult authenticationUser(HttpServletRequest request, String userName, String password)
			throws Exception {
		UserInfo userInfo = userInfoService.selectByLoginName(userName);

		if (null == userInfo) {
			return new AjaxResult("0101", ApplicationProperties.getMessage("0101"), null);
		}

		Passport passport = new Passport();
		passport.setUserId(userInfo.getUserId());

		passport = passportService.select(passport);

		password += passport.getSalt().trim();
		// 对密码做MD5加密
		password = HexStr.bytesToHexString(MD5Sign.encode(password.getBytes()));

		if (!password.equals(userInfo.getPassword())) {
			return new AjaxResult("0101", ApplicationProperties.getMessage("0101"), null);
		}

		return new AjaxResult(ConstEC.CODE_0000, ConstEC.SUCCESS, null);
	}

	/**
	 * 会员注册
	 * @param request
	 * @param userName
	 * @param password
	 * @param source
	 * @param tokenId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/registerUser")
	@ResponseBody
	public AjaxResult registerUser(HttpServletRequest request,
			@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "source", defaultValue = "") String source,
			@RequestParam(value = "tokenId", defaultValue = "") String tokenId) throws Exception {

		//如果userName不为空,则需要查询用户基础信息是否已经存在
		if (!"".equals(userName) && null != userInfoService.selectByLoginName(userName)) {
			return new AjaxResult("0100", ApplicationProperties.getMessage("0100"), null);
		}
		
		UserInfo userInfo = new UserInfo();

		UserSecurityCenter userSecurityCenter = new UserSecurityCenter();

		UserBindInfo userBindInfo = new UserBindInfo();
		List<UserBindInfo> userBindInfos = null;

		// 如果tokenId存在,第三方联合登录用户注册,否则用户直接在注册页面注册新用户
		if (!"".equals(tokenId.trim())) {
			Token token = checkToken(tokenId);

			userBindInfo.setBindId(token.getBindId());
			userBindInfo.setBindType(token.getBindType());

			// 获取会员第三方绑定信息
			userBindInfos = userBindInfoService.list(userBindInfo);

			if (null == userBindInfos || userBindInfos.size() <= 0) {
				return new AjaxResult("0006", "[" + tokenId + "]" + ApplicationProperties.getMessage("0006"), null);
			}
			
			// 从第三方绑定信息获取头像地址和昵称
			userInfo.setHeadImgUrl(userBindInfos.get(0).getHeadImgUrl());
			userInfo.setNickname(userBindInfos.get(0).getNickname());

			if (!"".equals(userName.trim()) && !"".equals(password.trim())) {
//				userSecurityCenter.setVerifyUserName(1);
				userSecurityCenter.setVerifyMobile(1);
				userSecurityCenter.setVerifyPassword(1);
			}
		} else {
			//新用户注册,userName和password不能为空
			if ("".equals(userName.trim()) || "".equals(password.trim())) {
				return new AjaxResult("0102", "[" + tokenId + "]" + ApplicationProperties.getMessage("0102"), null);
			}
			userBindInfo.setBindId(userName);
			userBindInfo.setBindType("MLX");
			userBindInfo.setOpenId(userName);
			userBindInfo.setSource(source);
			userBindInfos = new ArrayList<UserBindInfo>();
			userBindInfos.add(userBindInfo);

			userSecurityCenter.setVerifyMobile(1);
//			userSecurityCenter.setVerifyUserName(1);
			userSecurityCenter.setVerifyPassword(1);

			// 如果是美丽行用户,userName就是手机号
			userInfo.setMobile(userName);
			userInfo.setNickname("MLX_" + CharacterUtils.getRandomString(8));
		}

		// 盐值
		String salt = CharacterUtils.getRandomString(16);

		password += salt;
		// 对密码做MD5加密
		password = HexStr.bytesToHexString(MD5Sign.encode(password.getBytes()));

//		userInfo.setUserName(userName);
		userInfo.setPassword(password);
		userInfo.setRegDate(DateTimeUtil.date8());
		userInfo.setCreateTime(new Date());

		Passport passport = new Passport();
		passport.setSalt(salt);
		passport.setRegDate(DateTimeUtil.date8());
		passport.setCreateTime(new Date());
		passport.setStatus(1);

		userInfo.setPassport(passport);
		userInfo.setUserBindInfos(userBindInfos);
		userInfo.setUserSecurityCenter(userSecurityCenter);

		userInfoService.insert(userInfo);
		
		
		Map<String, Object> resultMap= new HashMap<String, Object>();
		resultMap.put("userId", userInfo.getUserId());
		resultMap.put("userName", userInfo.getUserName());
		resultMap.put("nickname", userInfo.getNickname());
		resultMap.put("headImgUrl", userInfo.getHeadImgUrl());
		
		return new AjaxResult(ConstEC.CODE_0000, ConstEC.SUCCESS, resultMap);
	}

	/**
	 * 校验token信息
	 * @param tokenId
	 * @return
	 */
	private Token checkToken(String tokenId) {
		Token token = new Token();
		token.setTokenId(tokenId);
		token = tokenService.select(token);

		if (null == token) {
			log.error("tokenId[{}]在数据库中不存在", tokenId);
			throw new BusiException("0200", "[" + tokenId + "]" + ApplicationProperties.getMessage("0200"), null);
		}

		if (token.getStatus() != 1) {
			log.error("tokenId[{}]已失效[{}]", tokenId, token.getStatus());
			throw new BusiException("0201", "[" + tokenId + "]" + ApplicationProperties.getMessage("0201"), null);
		}

		if (token.getAccessCount() >= token.getTokenCount()) {
			log.error("tokenId[{}]访问次数已超过最大限制AccessCount[{}] >= TokenCount[{}]", tokenId, token.getAccessCount(),
					token.getTokenCount());
			token.setStatus(2);
			tokenService.update(token);
			throw new BusiException("0202", "[" + tokenId + "]" + ApplicationProperties.getMessage("0202"), null);
		}

		String currentTime = DateTimeUtil.formatTimestamp2String(new Date(), "yyyyMMddHHmmss");
		if (token.getExpTime().compareTo(currentTime) < 0) {
			log.error("tokenId[{}]已超时 expTime[{}] < currentTime[{}]", tokenId, token.getExpTime(), currentTime);
			token.setStatus(2);
			tokenService.update(token);
			throw new BusiException("0203", "[" + tokenId + "]" + ApplicationProperties.getMessage("0203"), null);
		}

		token.setAccessCount(token.getAccessCount() + 1);
		tokenService.update(token);
		return token;
	}

}
