package conexion;

import java.sql.*;

public class Conexion {
   static String bd = "codeunam";
   static String login = "root";
   static String password = "";
   static String url = "jdbc:mysql://localhost:3306/"+bd+"?useUnicode=true&use"
		   + "JDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&"
		   + "serverTimezone=UTC";

   Connection conn = null;

   /** Constructor de DbConnection */
   public Conexion() {
      try{
         //obtenemos el driver de para mysql
         Class.forName("com.mysql.cj.jdbc.Driver");
         //obtenemos la conexión
         conn = DriverManager.getConnection(url,login,password);

         if (conn!=null){
            //System.out.println("Conección a base de datos "+bd+" OK");
         }
      }
      catch(SQLException e){
         System.out.println(e);
      }catch(ClassNotFoundException e){
         System.out.println(e);
      }catch(Exception e){
         System.out.println(e);
      }
   }
   /**Permite retornar la conexión*/
   public Connection getConnection(){
      return conn;
   }

   public void desconectar(){
      conn = null;
   }

}