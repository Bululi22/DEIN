package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexionBD.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Telefono;

public class TelefonoDao {
	private ConexionBD conexion;

	public ObservableList<Telefono> cargarTelefonos(String dni) throws Exception {
		ObservableList<Telefono> telefonos = FXCollections.observableArrayList();
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		
		sql = "SELECT * FROM telefono WHERE dni=?;";

		PreparedStatement ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, dni);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Telefono d = new Telefono(rs.getInt("id"), rs.getString("dni"), rs.getInt("telefono"));
			telefonos.add(d);
		}
		rs.close();
		ps.close();

		con.close();

		return telefonos;
	}
	
	public String crearTelefono(String dni, String telefono) {
		String sql, msg = "";
		try {
			conexion = new ConexionBD();
			PreparedStatement ps;
			
			sql = "INSERT INTO telefono VALUES (?,?,?)";
			ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, null);
			ps.setString(2, dni);
			ps.setInt(3, Integer.parseInt(telefono));
			ps.executeUpdate();
	
			ps.close();
			conexion.cerrarConexion();
		} catch (Exception ex) {
			ex.printStackTrace();
			msg = "Fallo al crear";
		}
		return msg;
	}
	
	public void borrarTelefono(Telefono telefono) throws Exception {
		String sql;

		conexion = new ConexionBD();
		PreparedStatement ps;

		sql = "DELETE FROM telefono WHERE (dni = ?) AND (telefono = ?);";
		ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, telefono.getDni());
		ps.setInt(2, telefono.getTelefono());
		ps.executeUpdate();

		ps.close();
		conexion.cerrarConexion();
	}
}
