package bdProd;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DB {
    
    private static Connection conn = null;
    
    public static Connection getConnection(){
        if (conn == null){
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            }
            catch (SQLException e){
                    throw new DbException(e.getMessage());
                }        
            }
        return conn;        
    }
    
    public static void closeConnection(){
        if (conn != null){
            try{
                conn.close();
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }
    
    private static Properties loadProperties(){
        try ( FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;
        } 
        catch (IOException e){
            throw new DbException(e.getMessage());
        }   
    }
    
    public static void closeResultSet(ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
               throw new DbException (e.getMessage());
            }
        }
    }
    
     public static void closeStatement(Statement st){
        if (st != null){
            try {
                st.close();
            } catch (SQLException e) {
               throw new DbException (e.getMessage());
            }
        }
    }
     
     public static String encontrarCaminho(String caminho) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM caminhos WHERE Tela = ?");
            st.setString(1, caminho);
            rs = st.executeQuery();
            if (rs.next()) {
                caminho = rs.getString("Caminho");

                return caminho;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
     
     public static void update(String tela ,String caminho) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE caminhos "
                    + "SET caminho = ? "
                    + "WHERE tela = ?");

            st.setString(1, caminho);
            st.setString(2, tela);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }    
}
