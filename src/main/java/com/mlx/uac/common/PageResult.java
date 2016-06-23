package com.mlx.uac.common;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public class PageResult {

	private String code;

	private String msg;

	private Integer page;

	private Integer pageSize;

	private Integer pageCount;

	private Integer rowCount;

	private Integer total;

	private Object result;

	public PageResult() {

	}

	public PageResult(String code, String message, Object result) {
		this.code = code;
		this.msg = message;
		this.result = result;
		this.setPage();
	}

	private void setPage() {
		if (result != null && result instanceof PageList) {
			PageList<?> pgList = (PageList<?>) result;
			this.page = pgList.getPaginator().getPage();
			this.pageSize = pgList.getPaginator().getLimit();
			this.pageCount = pgList.getPaginator().getTotalPages();
			this.rowCount = pgList.size();
			this.total = pgList.getPaginator().getTotalCount();
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
		this.setPage();
	}

}