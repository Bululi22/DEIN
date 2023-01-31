package conexionBD;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;
import java.util.TimeZone;





public class ConexionDB {

    

   

	

	

	private Connection conexion;

	public static Connection getConexion() throws SQLException {
	        String url = Propiedades.getValor("url") + "?serverTimezone=" + TimeZone.getDefault().getID();
	        String user = Propiedades.getValor("user");
	        String password = Propiedades.getValor("password");
	        return DriverManager.getConnection(url, user, password);
	}
	public ConexionDB() throws SQLException {

//		String user = "admin";
//
//	        String password = "password";
//
//	        String url = "jdbc:mysql://localhost/personas?serverTimezone=Europe/Madrid";

		conexion = this.getConexion();

		conexion.setAutoCommit(true);

	}


    

}