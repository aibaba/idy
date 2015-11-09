package com.idy.exception;

/**
 * Indicate when access protected resource with illegal hrmPrivilege.
 * 
 * @author 
 * 
 */
public class IllegalHrmPrivilegeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String[] codes;

    public IllegalHrmPrivilegeException(String msg, String... codes) {
        super(msg);
        this.codes = codes;
    }

    /**
     * @return should have these hrmPrivilege codes
     */
    public String[] getCodes() {
        return codes;
    }
}
