package com.dao;

import com.dbutil.DBConnection;
import com.pojo.CustomerPojo;
import com.pojo.StaffPojo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StaffDao {
//    Id genration

    public static String getNewId() throws SQLException {
        Statement st = DBConnection.getConn().createStatement();
        ResultSet rs = st.executeQuery("select max(staff_id) from staff");
        rs.next();
        String id = rs.getString(1);
        String staffId = "";
        if (id != null) {
            id = id.substring(4);
            staffId = "STF-" + (Integer.parseInt(id) + 1);
        } else {
            staffId = "STF-101";
        }
        return staffId;
    }

//    Adding Staff
    public static String addStaff(StaffPojo staff) throws SQLException {
        System.out.println(staff.toString());
        PreparedStatement ps = DBConnection.getConn().prepareStatement("insert into staff values(?,?,?,?,?)");
        staff.setStaffId(getNewId());
        ps.setString(1, staff.getStaffId());
        ps.setString(2, staff.getCompanyId());
        ps.setString(3, staff.getEmailId());
        ps.setString(4, staff.getPassword());
        ps.setString(5, staff.getStaffName());
        return ps.executeUpdate() == 1 ? staff.getStaffId() : null;
    }
    
//    getting staff ids
    public static List<String> getAllStaffIdByCompanyId(String cmpId)throws SQLException{
        PreparedStatement ps = DBConnection.getConn().prepareStatement("select staff_id from staff where company_id=?");
        ps.setString(1, cmpId);
        List<String> staffList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            staffList.add(rs.getString(1));
        }
        return staffList;
    }
    
//    getting staff details
    public static StaffPojo getStaffDetailsByStaffId(String staffId)throws SQLException{
        PreparedStatement ps = DBConnection.getConn().prepareStatement("select * from staff where staff_id=?");
        ps.setString(1, staffId);
        StaffPojo staff = new StaffPojo();
        ResultSet rs = ps.executeQuery();
        rs.next();
        staff.setStaffName(rs.getString(5));
        staff.setEmailId(rs.getString(3));
        return staff;
    }
    
//    random staff id
    public static String getRandomStaffIdFromCompany(String compId)throws SQLException{
        List<String> staffList = StaffDao.getAllStaffIdByCompanyId(compId);
        Random rand = new Random();
        int index = rand.nextInt(staffList.size());
        return staffList.get(index);
    }
    
    //    Validation of user 
    public static StaffPojo validate(String emailId,String password) throws SQLException{
        System.out.println("["+emailId+","+password+"]");
        PreparedStatement ps = DBConnection.getConn().prepareStatement("select staff_id,staff_name from staff where email_id=? and password=?");
        ps.setString(1, emailId);
        ps.setString(2, password);
        ResultSet rs= ps.executeQuery();
        StaffPojo staff = null;
        if(rs.next()){
            staff=new StaffPojo();
            staff.setStaffId(rs.getString(1));
            staff.setStaffName(rs.getString(2));
            staff.setEmailId(emailId);
            
        }
        System.out.println(staff.toString());
        return staff;
    }
    

    
}
