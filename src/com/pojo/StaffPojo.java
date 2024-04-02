
package com.pojo;


public class StaffPojo {
    private String staffId;
    private String companyId;
    private String emailId;
    private String password;
    private String staffName;

    public StaffPojo() {
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String company_id) {
        this.companyId = company_id;
    }

    @Override
    public String toString() {
        return "StaffPojo{" + "staffId=" + staffId + ", companyId=" + companyId + ", emailId=" + emailId + ", password=" + password + ", staffName=" + staffName + '}';
    }

    public StaffPojo(String staffId, String companyId, String emailId, String password, String staffName) {
        this.staffId = staffId;
        this.companyId = companyId;
        this.emailId = emailId;
        this.password = password;
        this.staffName = staffName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
