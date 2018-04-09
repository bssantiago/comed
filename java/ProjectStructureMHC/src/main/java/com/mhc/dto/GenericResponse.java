package com.mhc.dto;

public class GenericResponse {

	private Meta meta;
	private Object response;

	public GenericResponse(String msg, int errorCode,Object obj) {
		meta = new Meta(msg, errorCode);
		this.response = obj;
	}
	
	public GenericResponse(String msg, int errorCode) {
		meta = new Meta(msg, errorCode);
	}

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

		public Meta(String msg, int errCode) {
			this.msg = msg;
			this.errCode = errCode;
		}

		public Meta() {
		}

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
