
package com.pojo;


public class CompaniesPojo {
    private String companyId;
    private String companyName;
    private String password;
    private String emailId;
    private String securityKey;
    private String ownerName;

    public String getCompany_id() {
        return companyId;
    }

    public void setCompany_id(String company_id) {
        this.companyId = company_id;
    }

    public String getCompany_name() {
        return companyName;
    }

    public void setCompany_name(String company_name) {
        this.companyName = company_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail_id() {
        return emailId;
    }

    public void setEmail_id(String email_id) {
        this.emailId = email_id;
    }

    public String getOwner_name() {
        return ownerName;
    }

    public void setOwner_name(String owner_name) {
        this.ownerName = owner_name;
    }

    public CompaniesPojo() {
    }

    public String getSecurity_key() {
        return securityKey;
    }

    public void setSecurity_key(String security_key) {
        this.securityKey = security_key;
    }

    @Override
    public String toString() {
        return "CompaniesPojo{" + "companyId=" + companyId + ", companyName=" + companyName + ", password=" + password + ", emailId=" + emailId + ", securityKey=" + securityKey + ", ownerName=" + ownerName + '}';
    }
          
}
