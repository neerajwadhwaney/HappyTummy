
package com.dao;

import com.dbutil.DBConnection;
import com.pojo.CompaniesPojo;
import com.pojo.CustomerPojo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDao {
//    Id genration
    public static String getNewId() throws SQLException {
        Statement st = DBConnection.getConn().createStatement();
        ResultSet rs = st.executeQuery("select max(customer_id) from customers");
        rs.next();
        String id = rs.getString(1);
        String customerId = "";
        if (id != null) {
            id = id.substring(4);
            customerId = "CUS-" + (Integer.parseInt(id) + 1);
        } else {
            customerId = "CUS-101";
        }
        return customerId;
    }

//    Adding Customer
    public static boolean addCustomer(CustomerPojo customer) throws SQLException {
        System.out.println(customer.toString());
        PreparedStatement ps = DBConnection.getConn().prepareStatement("insert into customers values(?,?,?,?,?,?)");
        customer.setCustomerId(getNewId());
        ps.setString(1, customer.getCustomerId());
        ps.setString(2, customer.getCustomerName());
        ps.setString(3, customer.getEmailId());
        ps.setString(4, customer.getPassword());
        ps.setString(5, customer.getMobileNo());
        ps.setString(6, customer.getAddress());
        return ps.executeUpdate() == 1;
    }
    
//    Validation of user 
    public static CustomerPojo validate(String emailId,String password) throws SQLException{
        System.out.println("["+emailId+","+password+"]");
        PreparedStatement ps = DBConnection.getConn().prepareStatement("select * from customers where email_id=? and password=?");
        ps.setString(1, emailId);
        ps.setString(2, password);
        ResultSet rs= ps.executeQuery();
        CustomerPojo customer = null;
        if(rs.next()){
            customer=new CustomerPojo();
            customer.setCustomerId(rs.getString(1));
            customer.setCustomerName(rs.getString(2));
            customer.setEmailId(emailId);
            
        }
        System.out.println(customer.toString());
        return customer;
    }

//    getting customer details
    public static CustomerPojo getCustomerDetailsById(String custId)throws SQLException{
        PreparedStatement ps = DBConnection.getConn().prepareStatement("select * from customers where customer_id=?");
        ps.setString(1, custId);
        ResultSet rs= ps.executeQuery();
        CustomerPojo customer = null;
        if(rs.next()){
            customer=new CustomerPojo();
            customer.setCustomerId(rs.getString(1));
            customer.setCustomerName(rs.getString(2));
            customer.setEmailId(rs.getString("email_id"));
            customer.setMobileNo(rs.getString("mobile_no"));
            customer.setAddress(rs.getString("address"));
            customer.setPassword(rs.getString("password"));
            
        }
        System.out.println(customer.toString());
        return customer;
    }
//    updating details
    public static boolean updateCustomer(CustomerPojo customer)throws SQLException{
        PreparedStatement ps = DBConnection.getConn().prepareStatement("update customers set customer_name=?,password=?,mobile_no=?,address=? where customer_id=?");
        ps.setString(5, customer.getCustomerId());
        ps.setString(1, customer.getCustomerName());
        ps.setString(2,customer.getPassword());
        ps.setString(3,customer.getMobileNo());
        ps.setString(4, customer.getAddress());
        return ps.executeUpdate()==1;
    }
    
//    load all products
}
