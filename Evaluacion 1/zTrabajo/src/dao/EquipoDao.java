package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexionBD.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Equipo;

public class EquipoDao {

	private ConexionBD conexion;
	
	public ObservableList<Equipo> cargarEquipo() throws Exception {
		ObservableList<Equipo> equipos = FXCollections.observableArrayList();
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM Equipo;";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Equipo e = new Equipo(rs.getString("id_equipo"), rs.getString("nombre"), rs.getString("iniciales"));
			equipos.add(e);

		}
		rs.close();
		ps.close();

		con.close();

		return equipos;
	}

	public Equipo getEquipo(String id) throws Exception {
		Equipo x = null;
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM Equipo WHERE id_equipo = '" + id + "';";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			x = new Equipo(rs.getString("id_equipo"), rs.getString("nombre"), rs.getString("iniciales"));
		}
		rs.close();
		ps.close();

		con.close();
		return x;
	}

	public String crearEquipo(Equipo e) {
		String sql, msg = "";
		try {
			conexion = new ConexionBD();
			@SuppressWarnings("unused")
			Connection con = conexion.getConexion();
			PreparedStatement ps;

			sql = "INSERT INTO Equipo VALUES (?,?,?)";
			ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, null);
			ps.setString(2, e.getNombre());
			ps.setString(3, e.getIniciales());
			ps.executeUpdate();

			ps.close();
			conexion.cerrarConexion();
		} catch (Exception ex) {
			ex.printStackTrace();
			msg+="Error al crear evento";
		}
		return msg;
	}

	public String editarEquipo(Equipo e) {
		String sql, msg = "";

		try {
			conexion = new ConexionBD();
			Connection con = conexion.getConexion();
			PreparedStatement ps;

			sql = "UPDATE Equipo SET nombre = '" + e.getNombre() + "', iniciales = '" + e.getIniciales()
					+ "' WHERE (id_equipo = '" + e.getId_equipo() + "');";
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

			con.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			msg = "Fallo al editar Equipo";
			return msg;
		}
		return msg;
	}

	public void borrarEquipo(Equipo e) throws Exception {
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();
		PreparedStatement ps;

		sql = "DELETE FROM Participacion WHERE (id_equipo = '" + e.getId_equipo() + "');";
		ps = con.prepareStatement(sql);
		ps.executeUpdate();

		sql = "DELETE FROM Equipo WHERE (id_equipo = '" + e.getId_equipo() + "');";
		ps = con.prepareStatement(sql);
		ps.executeUpdate();

		ps.close();
		conexion.cerrarConexion();
	}
	
	public int verificadorRepetidos(String nom) throws Exception {
		String sql;
		int respuesta = -1;
		try {
			conexion = new ConexionBD();
			Connection con = conexion.getConexion();
	
			sql = "SELECT count(*)as resp FROM Equipo WHERE nombre = ? ;";
	
			PreparedStatement ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, nom);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				respuesta = rs.getInt("resp");
			}
			rs.close();
			ps.close();
	
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			respuesta = -1;
		}
		return respuesta;
			
	}
}
