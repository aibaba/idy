package redisson;

/**
 * 业务类型枚举
 * 
 * @author junchang@yirendai.com
 * @date 2016年8月12日
 */
public enum BusinessEnum {
	
	REPAYMENT("1", "还款"),
	TRANSFER_LOAN("2", "转让债权"),
	OVERDUE("3", "逾期"),
	FUARANTEE("4", "保障"),
	;
	
	private String code;
	private String cName;
	
	BusinessEnum(String code, String cName){
		this.code = code;
		this.cName = cName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}
	
	public static BusinessEnum getBusinessEnumByCode(String code) {
		if(null == code || "".equals(code)){
			return null;
		}
		for (BusinessEnum att : BusinessEnum.values()) {
			if (att.getCode().equals(code))
				return att;
		}
		return null;
	}

}
