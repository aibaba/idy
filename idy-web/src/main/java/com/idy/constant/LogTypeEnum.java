package com.idy.constant;

public enum LogTypeEnum {

	ADD(0, "新增")
	,UPDATE(1, "修改")
	,DEL(2, "删除")
	,OTHER(3, "未知")
	;
	
	private Integer code;
	
	private String value;
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	LogTypeEnum(Integer code, String value){
		this.code = code;
		this.value = value;
	}
	
	public static String getValue(Integer code){
		if(code == null) return null;
		for(LogTypeEnum e : LogTypeEnum.values()){
			if(e.getCode().intValue() == code.intValue()){
				return e.getValue();
			}
		}
		return null;
	}
}
