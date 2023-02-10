package kr.co.socsoft.common.vo;

public class LoginVO {
    private String userId;
    private String userName;
    private String deptId;
    private String deptName;
    private String sysYn;
    private String ssoUserYn;
    
    private String currentMenuId;
    
    public LoginVO(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSysYn() {
		return sysYn;
	}

	public void setSysYn(String sysYn) {
		this.sysYn = sysYn;
	}

	public String getCurrentMenuId() {
		return currentMenuId;
	}

	public void setCurrentMenuId(String currentMenuId) {
		this.currentMenuId = currentMenuId;
	}

	public String getSsoUserYn() {
		return ssoUserYn;
	}

	public void setSsoUserYn(String ssoUserYn) {
		this.ssoUserYn = ssoUserYn;
	}

}
