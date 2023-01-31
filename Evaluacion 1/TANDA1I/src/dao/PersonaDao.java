package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import conexionBD.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Persona;

public class PersonaDao {
    private ConexionDB conexion;

    public ObservableList<Persona> cargarPersonas() throws SQLException {
        ObservableList<Persona> listP= FXCollections.observableArrayList();
        conexion = new ConexionDB();
        Connection con = conexion.getConexion();
        String sql = "select * from Persona";
        PreparedStatement ps = con.prepareStatement(sql);
        //ps.setInt(1, 200);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nombre= rs.getString("nombre");
            String apellidos= rs.getString("apellidos");
            String edad= rs.getString("edad");
            
            Persona a = new Persona(id, nombre, apellidos, edad);
            listP.add(a);

        }
        rs.close();
        ps.close();
        con.close();
        return listP;
    }
    public void insertarPersonas(Persona p) {
    	
    	
    	
		try {
			conexion = new ConexionDB();
	        Connection con = conexion.getConexion();
			
			PreparedStatement pst = con.prepareStatement("insert into Persona (nombre,apellidos,edad) values(?,?,?)");
			
			pst.setInt(1, p.getId());
			pst.setString(1, p.getNombre());
			pst.setString(2, p.getApellido());
			pst.setString(3, p.getEdad());

			
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
    }
    public void eliminarPersona(Persona p) {

		try {
	    	conexion = new ConexionDB();
	        Connection con = conexion.getConexion();
	        
	    	PreparedStatement pst;
			pst = con.prepareStatement("delete from Persona where id=?");
			pst.setInt(1, p.getId());
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
    }
    public void modificarPersona(Persona p) throws SQLException {
    	conexion = new ConexionDB();
        Connection con = conexion.getConexion();
        
    	PreparedStatement pst=con.prepareStatement("update Persona set nombre=? , apellidos=? , edad=? where id=?");
    	pst.setString(1, p.getNombre());
    	pst.setString(2, p.getApellido());
    	pst.setString(3, p.getEdad());
    	pst.setInt(4, p.getId());
    	System.out.println(pst);
    	pst.execute();
    }
}

