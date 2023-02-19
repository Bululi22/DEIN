package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexionBD.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Email;

public class EmailDao {
	private ConexionBD conexion;

	public ObservableList<Email> cargarEmail(String dni) throws Exception {
		ObservableList<Email> emails = FXCollections.observableArrayList();
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		
		sql = "SELECT * FROM email WHERE dni=?;";

		PreparedStatement ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, dni);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Email d = new Email(rs.getInt("id"), rs.getString("dni"), rs.getString("email"));
			emails.add(d);
		}
		rs.close();
		ps.close();

		con.close();

		return emails;
	}
	
	public String crearEmail(String dni, String email) {
		String sql, msg = "";
		try {
			conexion = new ConexionBD();
			PreparedStatement ps;
			
			sql = "INSERT INTO email VALUES (?,?,?)";
			ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, null);
			ps.setString(2, dni);
			ps.setString(3, email);
			ps.executeUpdate();
	
			ps.close();
			conexion.cerrarConexion();
		} catch (Exception ex) {
			ex.printStackTrace();
			msg = "Fallo al crear";
		}
		return msg;
	}
	
	public void borrarEmail(Email email) throws Exception {
		String sql;

		conexion = new ConexionBD();
		PreparedStatement ps;
		sql = "DELETE FROM email WHERE (dni = ?) AND (email = ?);";
		ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, email.getDni());
		ps.setString(2, email.getEmail());
		ps.executeUpdate();

		ps.close();
		conexion.cerrarConexion();
	}
}
