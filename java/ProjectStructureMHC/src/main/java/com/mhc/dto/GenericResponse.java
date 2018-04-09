package com.mhc.dto;

public class GenericResponse {

	private Meta meta;
	private Object response;

	public GenericResponse() {
		meta = new Meta();
	}
	
	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public class Meta {
		private int errCode;
		private String msg;

		public int getErrCode() {
			return errCode;
		}

		public void setErrCode(int errCode) {
			this.errCode = errCode;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

	}
}
