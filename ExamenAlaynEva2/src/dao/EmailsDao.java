package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.PersonasDao;
import model.Email;
import model.Persona;
import util.ConexionDB;

public class EmailsDao {
	public ArrayList<Email> getEmails(){
		ArrayList<Email> emails = new ArrayList<Email>();
		try(Connection conexion = new ConexionDB().getConexion();){
			String sql = "select * from email";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Persona p = PersonasDao.getPersona(rs.getString("dni"));
				Email e = new Email(rs.getInt("id"), p, rs.getString("email"));
				emails.add(e);
			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return emails;
	}
	
	public ArrayList<Email> getEmailsPersona(Persona p){
		ArrayList<Email> emails = new ArrayList<Email>();
		try(Connection conexion = new ConexionDB().getConexion();){
			String dni = p.getDni();
			String sql = "select * from email where dni = '" + dni + "'";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Email e = new Email(rs.getInt("id"), p, rs.getString("email"));
				emails.add(e);
			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return emails;
	}
	

	public boolean insertEmail(String email, String dni) {
		try(Connection conexion = new ConexionDB().getConexion();){
			String sql = "Insert into email (dni, email) values (?, ?)";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, dni);
			ps.setString(2, email);
			ps.executeUpdate();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean deleteEmail(String email, String dni) {
		try(Connection conexion = new ConexionDB().getConexion();){
			String sql = "delete from email where email = ? and dni = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, dni);
			ps.executeUpdate();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	
}
