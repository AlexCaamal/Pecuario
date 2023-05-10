package Models;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class conexion {
    
    // Declaramos la conexion a mysql
    public static Connection con;
    // Declaramos los datos de conexion a la bd
    private static final String driver="com.mysql.jdbc.Driver";
    private static final String user="root";
    private static final String pass="";
    private static final String url="jdbc:mysql://localhost:3305/pecuario?characterEncoding=utf8";
    // Funcion que va conectarse a mi bd de mysql
    public  Connection conectar(){
      con = null;
      try{
          con = (Connection) DriverManager.getConnection(url, user, pass);
          if(con!=null){
              
          }
      }
      catch(SQLException e)
      {
          JOptionPane.showMessageDialog(null,"Error" + e.toString());
      }
      return con;
    } 
    
    public static Connection establecerConnection(){
      con = null;
      try{
          con = (Connection) DriverManager.getConnection(url, user, pass);
          return con;
      }
      catch(SQLException e)
      {
          JOptionPane.showMessageDialog(null,"Error" + e.toString());
      }
      return con;
    } 
    
    //Método para llenar comboBox
    public void llenaCombo(String tabla,String valor,JComboBox combo){
        String sql = "select * from " + tabla;
        Statement st;
        conexion con = new conexion();
        Connection conexion = con.conectar();
        try{
            st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getString(valor));
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Error" + e.toString());
        } 
    }
    
    public void llenarTablas(JComboBox combo){
        String sql = "show tables";
        Statement st;
        conexion con = new conexion();
        Connection conexion = con.conectar();
        try{
            st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getString("Tables_in_veterinaria"));
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Error" + e.toString());
        } 
    }
}
    
