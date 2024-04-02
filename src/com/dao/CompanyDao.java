/*
                                Data Access Object of Company
                                =============================
 */
package com.dao;

import com.dbutil.DBConnection;
import com.pojo.CompaniesPojo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class CompanyDao {

//    Auto Genrating new ID
    public static String getNewId() throws SQLException {
        Statement st = DBConnection.getConn().createStatement();
        ResultSet rs = st.executeQuery("select max(company_id) from companies");
        rs.next();
        String id = rs.getString(1);
        if (id != null) {
            id = id.substring(4);
            return "CMP-" + (Integer.parseInt(id) + 1);
        } else {
            return "CMP-101";
        }
    }

//    Adding new seller in DATABASE
    public static boolean addSeller(CompaniesPojo cmp) throws SQLException {
        System.out.println(cmp.toString());
        PreparedStatement ps = DBConnection.getConn().prepareStatement("insert into companies values(?,?,?,?,?,?,?)");
        ps.setString(1, getNewId());
        ps.setString(2, cmp.getCompany_name());
        ps.setString(3, cmp.getOwner_name());
        ps.setString(4, cmp.getPassword());
        ps.setString(5, "ACTIVE");
        ps.setString(6, cmp.getEmail_id());
        ps.setString(7, cmp.getSecurity_key());
        return ps.executeUpdate() == 1;
    }

//    Validation of user 
    public static CompaniesPojo validate(String userId, String password) throws SQLException {
        System.out.println("[" + userId + "," + password + "]");
        PreparedStatement ps = DBConnection.getConn().prepareStatement("select * from companies where company_name=? and password=? and status='ACTIVE'");
        ps.setString(1, userId);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        CompaniesPojo comp = null;
        if (rs.next()) {
            comp = new CompaniesPojo();
            comp.setCompany_id(rs.getString(1));
            comp.setCompany_name(rs.getString(2));
            comp.setOwner_name(rs.getString(3));

        }
        System.out.println(comp.toString());
        return comp;
    }

//    geting security key
    public static Map<String, String> getEmailCredentialsByCompanyId(String companyId) throws SQLException {
        PreparedStatement ps = DBConnection.getConn().prepareStatement("select email_id,security_key from companies where company_id=? and status='ACTIVE'");
        ps.setString(1, companyId);
        Map<String, String> companyCredentials = new HashMap<>();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            companyCredentials.put("emailId", rs.getString(1));
            companyCredentials.put("securityKey", rs.getString(2));
        }
        return companyCredentials;
    }

//    geting companies with products added
    public static Map<String, String> getAllCompanyIdAndName() throws SQLException {
        PreparedStatement ps = DBConnection.getConn().prepareStatement("select company_id,company_name from companies where company_id in (select company_id from products) and status='ACTIVE'");
        ResultSet rs = ps.executeQuery();
        Map<String, String> compList = new HashMap<>();
        while (rs.next()) {
            String c_id = rs.getString(1);
            String c_name = rs.getString(2);
            compList.put(c_name, c_id);
        }
        System.out.println(compList);
        return compList;
    }
    
//    deleting company account
    public static boolean deleteAccoutById(String compId)throws SQLException{
        PreparedStatement ps = DBConnection.getConn().prepareStatement("update companies set status='INACTIVE' where company_id=?");
        ps.setString(1, compId);
        return ps.executeUpdate()==1;
    }
    
}
