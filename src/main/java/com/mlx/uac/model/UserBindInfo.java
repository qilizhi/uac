package com.mlx.uac.model;

import java.util.Date;

import com.mlx.uac.dict.ConstEC;

/**
 * 用户绑定信息
 * @author chenfh
 * 2016年6月16日 下午3:57:47
 */
public class UserBindInfo {
	
	/**
	 * 用户绑定信息表名
	 */
	private String tableName;
	
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.id
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.user_id
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.bind_id
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private String bindId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_weibo.head_img_url
     *
     * @mbggenerated Wed Jun 22 11:10:53 CST 2016
     */
    private String headImgUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_weibo.nickname
     *
     * @mbggenerated Wed Jun 22 11:10:53 CST 2016
     */
    private String nickname;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.status
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.open_id
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private String openId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.open_name
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private String openName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.bind_type
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private String bindType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.login_token
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private String loginToken;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.login_type
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private Integer loginType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.source
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private String source;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.sys_cnl
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private String sysCnl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.token_exp_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private Date tokenExpTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.has_exp_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private Date hasExpTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.reg_date
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private String regDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.reg_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private String regTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.create_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bind_mlx.update_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    private Date updateTime;
    
    public String getTableName() {
    	
    	if (null != this.tableName && !"".equals(this.tableName.trim())) {
			return this.tableName;
		}
		
		if (null != this.bindType) {
			String tableName = this.getClass().getSimpleName();
			if (ConstEC.BINDTYPEADAPTER.containsKey(tableName + "_" + this.bindType)) {
				this.tableName = ConstEC.BINDTYPEADAPTER.get(tableName + "_" + this.bindType);
				return this.tableName;
			}
		}
		
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.id
     *
     * @return the value of user_bind_mlx.id
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.id
     *
     * @param id the value for user_bind_mlx.id
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.user_id
     *
     * @return the value of user_bind_mlx.user_id
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.user_id
     *
     * @param userId the value for user_bind_mlx.user_id
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.bind_id
     *
     * @return the value of user_bind_mlx.bind_id
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public String getBindId() {
        return bindId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.bind_id
     *
     * @param bindId the value for user_bind_mlx.bind_id
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setBindId(String bindId) {
        this.bindId = bindId == null ? null : bindId.trim();
    }

    public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.status
     *
     * @return the value of user_bind_mlx.status
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.status
     *
     * @param status the value for user_bind_mlx.status
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.open_id
     *
     * @return the value of user_bind_mlx.open_id
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.open_id
     *
     * @param openId the value for user_bind_mlx.open_id
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.open_name
     *
     * @return the value of user_bind_mlx.open_name
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public String getOpenName() {
        return openName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.open_name
     *
     * @param openName the value for user_bind_mlx.open_name
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setOpenName(String openName) {
        this.openName = openName == null ? null : openName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.bind_type
     *
     * @return the value of user_bind_mlx.bind_type
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public String getBindType() {
        return bindType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.bind_type
     *
     * @param bindType the value for user_bind_mlx.bind_type
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setBindType(String bindType) {
        this.bindType = bindType == null ? null : bindType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.login_token
     *
     * @return the value of user_bind_mlx.login_token
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public String getLoginToken() {
        return loginToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.login_token
     *
     * @param loginToken the value for user_bind_mlx.login_token
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken == null ? null : loginToken.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.login_type
     *
     * @return the value of user_bind_mlx.login_type
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public Integer getLoginType() {
        return loginType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.login_type
     *
     * @param loginType the value for user_bind_mlx.login_type
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.source
     *
     * @return the value of user_bind_mlx.source
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public String getSource() {
        return source;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.source
     *
     * @param source the value for user_bind_mlx.source
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.sys_cnl
     *
     * @return the value of user_bind_mlx.sys_cnl
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public String getSysCnl() {
        return sysCnl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.sys_cnl
     *
     * @param sysCnl the value for user_bind_mlx.sys_cnl
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setSysCnl(String sysCnl) {
        this.sysCnl = sysCnl == null ? null : sysCnl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.token_exp_time
     *
     * @return the value of user_bind_mlx.token_exp_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public Date getTokenExpTime() {
        return tokenExpTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.token_exp_time
     *
     * @param tokenExpTime the value for user_bind_mlx.token_exp_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setTokenExpTime(Date tokenExpTime) {
        this.tokenExpTime = tokenExpTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.has_exp_time
     *
     * @return the value of user_bind_mlx.has_exp_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public Date getHasExpTime() {
        return hasExpTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.has_exp_time
     *
     * @param hasExpTime the value for user_bind_mlx.has_exp_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setHasExpTime(Date hasExpTime) {
        this.hasExpTime = hasExpTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.reg_date
     *
     * @return the value of user_bind_mlx.reg_date
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public String getRegDate() {
        return regDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.reg_date
     *
     * @param regDate the value for user_bind_mlx.reg_date
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setRegDate(String regDate) {
        this.regDate = regDate == null ? null : regDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.reg_time
     *
     * @return the value of user_bind_mlx.reg_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public String getRegTime() {
        return regTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.reg_time
     *
     * @param regTime the value for user_bind_mlx.reg_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setRegTime(String regTime) {
        this.regTime = regTime == null ? null : regTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.create_time
     *
     * @return the value of user_bind_mlx.create_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.create_time
     *
     * @param createTime the value for user_bind_mlx.create_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bind_mlx.update_time
     *
     * @return the value of user_bind_mlx.update_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bind_mlx.update_time
     *
     * @param updateTime the value for user_bind_mlx.update_time
     *
     * @mbggenerated Wed Jun 15 17:37:05 CST 2016
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}