package com.dsys.exception;

public class CommonException extends RuntimeException{
	private String code;
	private String message;
	public CommonException() {
		super();
	}
	public CommonException(String code,String message) {
		this.code=code;
		this.message=message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static enum StatusCode {
		SYS("sys","系统故障"),AUTH("auth","验证错误"),PARAM("param","参数错误"),NULL("null","空指针异常"),SUCCESS("0","成功"),FAIL("1","失敗");
		private String code;
		private String message;
		public String getCode() {
			return this.code;
		}
		public String getMessage() {
			return this.message;
		}
		private StatusCode(String code, String message) {this.code=code;this.message=message;}
	}
}
