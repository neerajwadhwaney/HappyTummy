package com.dao;

import com.dbutil.DBConnection;
import com.pojo.ProductPojo;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public class ProductDao {

//    Auto genrating product ID
    public static String getNewId() throws SQLException {
        Statement st = DBConnection.getConn().createStatement();
        ResultSet rs = st.executeQuery("select max(product_id) from products");
        rs.next();
        String id = rs.getString(1);
        if (id != null) {
            id = id.substring(4);
            return "PRD-" + (Integer.parseInt(id) + 1);
        } else {
            return "PRD-101";
        }
    }

//    Adding a product
    public static boolean addProduct(ProductPojo product) throws SQLException, IOException {
//        convert img to buffered image
        BufferedImage bf = new BufferedImage(product.getProductImage().getWidth(null), product.getProductImage().getHeight(null), BufferedImage.TYPE_INT_RGB);

//        Draw the buffered image
        Graphics gr = bf.getGraphics();
        gr.drawImage(product.getProductImage(), 0, 0, null);

//        convert buffered image into byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bf, product.getProductImageType(), baos);
        byte[] imageData = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);

        PreparedStatement ps = DBConnection.getConn().prepareStatement("insert into products values(?,?,?,?,?)");
        ps.setString(1, getNewId());
        ps.setString(2, product.getCompanyId());
        ps.setString(3, product.getProductName());
        ps.setDouble(4, product.getProductPrice());
        ps.setBinaryStream(5, bais, imageData.length);
        int x = ps.executeUpdate();
        return x > 0;
    }
    
//    view food
    public static Map<String,ProductPojo> getProductDetailsByCompanyId(String compnayId)throws SQLException,IOException{
        Connection conn = DBConnection.getConn();
        PreparedStatement ps = conn.prepareStatement("select * from products where company_id=?");
        ps.setString(1, compnayId);
        ResultSet rs = ps.executeQuery();
        Map<String,ProductPojo> productDetails = new HashMap<>();
        while(rs.next()){
            ProductPojo product = new ProductPojo();
            product.setProductPrice(rs.getDouble(4));
            product.setProductName(rs.getString(3));
            
            InputStream is = rs.getBinaryStream("product_image");
            BufferedImage bi = ImageIO.read(is);
            Image img = bi;
            
            product.setProductImage(img);
            productDetails.put(product.getProductName(), product);
        }
        return productDetails;
    }
    
//    getting product details
    public static List<ProductPojo> getAllProductsByCompanyId(String compId)throws SQLException,IOException{
        
        Connection conn = DBConnection.getConn();
        PreparedStatement ps;
        if(compId.equalsIgnoreCase("all")){
            ps = conn.prepareStatement("select * from products where company_id in (select company_id from companies where status='ACTIVE')");
        }
        else{
            ps = conn.prepareStatement("select * from products where company_id=?");
            ps.setString(1, compId);
        }
         
        
        ResultSet rs = ps.executeQuery();
        List<ProductPojo> productDetails = new ArrayList();
        while(rs.next()){
            ProductPojo product = new ProductPojo();
            product.setProductId(rs.getString("product_id"));
            product.setProductName(rs.getString("product_name"));
            product.setProductPrice(rs.getDouble("product_price"));
            product.setCompanyId(rs.getString("company_id"));
            InputStream is = rs.getBinaryStream("product_image");
            BufferedImage bi = ImageIO.read(is);
            Image img = bi;
            
            product.setProductImage(img);
            productDetails.add(product);
        }
        return productDetails;
    }
}
