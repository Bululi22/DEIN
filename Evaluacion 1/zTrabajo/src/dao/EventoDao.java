package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexionBD.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deporte;
import model.Evento;
import model.Olimpiada;

public class EventoDao {
	
	private ConexionBD conexion;

	public ObservableList<Evento> cargarEvento() throws Exception {
		ObservableList<Evento> eventos = FXCollections.observableArrayList();
		String sql;
		OlimpiadaDao oliDao = new OlimpiadaDao();
		DeporteDao depDao = new DeporteDao();
		Olimpiada olimpiada;
		Deporte deporte;
		
		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM Evento;";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			olimpiada = oliDao.getOlimpiada(rs.getString("id_olimpiada"));
			deporte = depDao.getDeporte(rs.getString("id_deporte"));
			Evento e = new Evento(rs.getString("id_evento"), rs.getString("nombre"), olimpiada, deporte);
			eventos.add(e);
		}
		rs.close();
		ps.close();

		con.close();

		return eventos;
	}

	public Evento getEvento(String id) throws Exception {
		Evento x = null;
		String sql;
		OlimpiadaDao oliDao = new OlimpiadaDao();
		DeporteDao depDao = new DeporteDao();
		Olimpiada olimpiada;
		Deporte deporte;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM Evento WHERE id_evento = ?;";

		PreparedStatement ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			olimpiada = oliDao.getOlimpiada(rs.getString("id_olimpiada"));
			deporte = depDao.getDeporte(rs.getString("id_deporte"));
			x = new Evento(rs.getString("id_evento"), rs.getString("nombre"), olimpiada, deporte);
		}
		
		rs.close();
		ps.close();

		con.close();
		return x;
	}

	public String crearEvento(Evento e) {
		String sql, msg = "";
		try {
			conexion = new ConexionBD();
			PreparedStatement ps;

			sql = "INSERT INTO Evento VALUES (?,?,?,?)";
			ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, null);
			ps.setString(2, e.getNombre());
			ps.setInt(3, e.getOlimpiada().getId_olimpiada());
			ps.setString(4, e.getDeporte().getId_deporte());
			ps.executeUpdate();

			ps.close();
			conexion.cerrarConexion();
		} catch (Exception ex) {
			ex.printStackTrace();
			msg+="Error al crear evento";
		}
		return msg;
	}

	public String editarEvento(Evento e) {
		String sql, msg = "";

		try {
			conexion = new ConexionBD();
			Connection con = conexion.getConexion();
			
			sql = "UPDATE Evento SET nombre = ?, id_olimpiada = ?, id_deporte = ? WHERE (id_evento = ?);";
			
			PreparedStatement ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, e.getNombre());
			ps.setInt(2, e.getOlimpiada().getId_olimpiada());
			ps.setString(3, e.getDeporte().getId_deporte());
			ps.setString(4, e.getId_evento());
			ps.executeUpdate();
			ps.close();

			con.close();

			return msg;
		} catch (Exception ex) {
			ex.printStackTrace();
			msg = "Fallo al editar";
		}
		return msg;
	}

	public void borrarEvento(Evento e) throws Exception {
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();
		PreparedStatement ps;

		sql = "DELETE FROM Participacion WHERE (id_evento = '" + e.getId_evento() + "');";
		ps = con.prepareStatement(sql);
		ps.executeUpdate();

		sql = "DELETE FROM Evento WHERE (id_evento = '" + e.getId_evento() + "');";
		ps = con.prepareStatement(sql);
		ps.executeUpdate();

		ps.close();
		conexion.cerrarConexion();
	}
	
	public int verificadorRepetidos(String nom, int id_oli, String id_dep) throws Exception {
		String sql;
		int respuesta = -1;
		try {
			conexion = new ConexionBD();
			Connection con = conexion.getConexion();
	
			sql = "SELECT count(*)as resp FROM Evento WHERE nombre = ? AND id_olimpiada = ? AND id_deporte = ?;";
	
			PreparedStatement ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, nom);
			ps.setInt(2, id_oli);
			ps.setString(3, id_dep);
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
