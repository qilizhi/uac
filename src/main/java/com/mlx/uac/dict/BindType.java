package com.mlx.uac.dict;

/**
 * 订单状态枚举
 * 
 * @author chenfh 2016年4月11日 上午10:28:46
 */
public enum BindType {
	MLX("MLX", "美丽行"), WEIXIN("WEIXIN", "微信"), ALIPAY("ALIPAY", "支付宝"), WEIBO("WEIBO", "微博"), QQ("QQ", "qq");

	private String id;

	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private BindType(String id, String name) {
		this.id = id;
		this.name = name;
	}

}
