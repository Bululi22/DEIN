package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Persona;
import model.Telefono;
import util.ConexionDB;

public class TelefonosDao {
	public ArrayList<Telefono> getTelefonos(){
		ArrayList<Telefono> telefonos = new ArrayList<Telefono>();
		try(Connection conexion = new ConexionDB().getConexion();){
			String sql = "select * from telefono";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Persona p = PersonasDao.getPersona(rs.getString("dni"));
				Telefono t = new Telefono(rs.getInt("id"), p, rs.getString("telefono"));
				telefonos.add(t);
			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return telefonos;
	}
	


	public ArrayList<Telefono> getTelefonosPersona(Persona p){
		ArrayList<Telefono> telefonos = new ArrayList<Telefono>();
		try(Connection conexion = new ConexionDB().getConexion();){
			String dni = p.getDni();
			String sql = "select * from telefono where dni = '" + dni + "'";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Telefono t = new Telefono(rs.getInt("id"), p, rs.getString("telefono"));
				telefonos.add(t);
			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return telefonos;
	}
	public boolean deleteTelefono(String telefono, String dni) {
		try(Connection conexion = new ConexionDB().getConexion();){
			String sql = "delete from telefono where telefono = ? and dni = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, telefono);
			ps.setString(2, dni);
			ps.executeUpdate();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean insertTelefono(String telefono, String dni) {
		try(Connection conexion = new ConexionDB().getConexion();){
			String sql = "Insert into telefono (dni, telefono) values (?, ?)";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, dni);
			ps.setString(2, telefono);
			ps.executeUpdate();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
