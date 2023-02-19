package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Persona;
import util.ConexionDB;

public class PersonasDao {
	
	public ArrayList<Persona> getpersonas(){
		ArrayList<Persona> listpersonas = new ArrayList<Persona>();
		try(Connection conexion = new ConexionDB().getConexion();){
			String sql = "select * from persona";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Persona p = new Persona(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellido1"),rs.getString("apellido2"), rs.getInt("anio_nacimiento"));
				listpersonas.add(p);
			}

			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listpersonas;
	}
	
	public static Persona getPersona(String dni) {
		try(Connection conexion = new ConexionDB().getConexion();) {
			
			String sql = "select * from persona where dni = '"+dni+"'";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Persona p = new Persona(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellido1"),rs.getString("apellido2"), rs.getInt("anio_nacimiento"));
				return p;
			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	
	

}
