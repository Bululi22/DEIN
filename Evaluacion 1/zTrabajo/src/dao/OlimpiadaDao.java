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

public class OlimpiadaDao {

	private ConexionBD conexion;

	public ObservableList<Olimpiada> cargarOlimpiadas() throws Exception {
		ObservableList<Olimpiada> olimpiadas = FXCollections.observableArrayList();
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM Olimpiada ORDER BY anio;";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Olimpiada v = new Olimpiada(rs.getInt("id_olimpiada"), rs.getString("nombre"), rs.getInt("anio"),
					rs.getString("temporada"), rs.getString("ciudad"));
			olimpiadas.add(v);

		}
		rs.close();
		ps.close();

		con.close();
		return olimpiadas;
	}

	public Olimpiada getOlimpiada(String id) throws Exception {
		Olimpiada o = null;
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM Olimpiada WHERE id_olimpiada = ?;";

		PreparedStatement ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			o = new Olimpiada(rs.getInt("id_olimpiada"), rs.getString("nombre"), rs.getInt("anio"),
					rs.getString("temporada"), rs.getString("ciudad"));
		}
		rs.close();
		ps.close();

		con.close();
		return o;
	}

	public String crearOlimpiada(Olimpiada o) {
		String sql, msg = "";
		try {
			conexion = new ConexionBD();
			@SuppressWarnings("unused")
			Connection con = conexion.getConexion();
			PreparedStatement ps;

			sql = "INSERT INTO Olimpiada VALUES (?,?,?,?,?)";
			ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, null);
			ps.setString(2, o.getNombre());
			ps.setInt(3, o.getAnio());
			ps.setString(4, o.getTemporada());
			ps.setString(5, o.getCiudad());
			ps.executeUpdate();

			ps.close();
			conexion.cerrarConexion();
		} catch (Exception ex) {
			ex.printStackTrace();
			msg+="Fallo al crear Olimpiada";
		}
		return msg;
	}

	public String editarOlimpiada(Olimpiada o) {
		String sql, msg = "";

		try {
			conexion = new ConexionBD();
			Connection con = conexion.getConexion();
			PreparedStatement ps;

			sql = "UPDATE Olimpiada SET nombre = '"+ o.getNombre() +"', anio = '"+ o.getAnio() +"', temporada = '"+ o.getTemporada() +"', ciudad = '"+ o.getCiudad() +"' WHERE (id_olimpiada = '"+ o.getId_olimpiada() +"');";
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

			con.close();

			return msg;
		} catch (Exception ex) {
			ex.printStackTrace();
			msg = "Fallo al editar Olimpiada";
		}
		return msg;
	}
	
	public void borrarOlimpiada(Olimpiada o) throws Exception {
		ObservableList<Evento> eventos = FXCollections.observableArrayList();
		String sql;
		OlimpiadaDao oliDao = new OlimpiadaDao();
		DeporteDao depDao = new DeporteDao();
		Olimpiada olimpiada;
		Deporte deporte;
		

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();
		PreparedStatement ps;

		// Todos lo eventos que sean de esa olimpiada
		sql = "SELECT * FROM Evento WHERE id_olimpiada = " + o.getId_olimpiada() + ";";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			olimpiada = oliDao.getOlimpiada(rs.getString("id_olimpiada"));
			deporte = depDao.getDeporte(rs.getString("id_deporte"));
			Evento e = new Evento(rs.getString("id_evento"), rs.getString("nombre"), olimpiada, deporte);
			eventos.add(e);
		}
		rs.close();

		for (Evento e : eventos) {
			// Recorro todos los participantes con el evento
			sql = "SELECT * FROM Participacion WHERE (id_evento = '" + e.getId_evento() + "');";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// Segun lo recorro los elimino
				sql = "DELETE FROM Participacion WHERE (id_evento = '" + e.getId_evento() + "') AND (id_deportista = '"
						+ rs.getString("id_deportista") + "');";
				ps = con.prepareStatement(sql);
				ps.executeUpdate();
			}
			rs.close();

			// Usado = eliminar
			sql = "DELETE FROM Evento WHERE (id_evento = '" + e.getId_evento() + "');";
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
		}
		sql = "DELETE FROM Olimpiada WHERE (id_olimpiada = '" + o.getId_olimpiada() + "');";
		ps = con.prepareStatement(sql);
		ps.executeUpdate();

		ps.close();
		conexion.cerrarConexion();
	}
	
	public int verificadorRepetidos(String nom, String temp) throws Exception {
		String sql;
		int respuesta = -1;
		try {
			conexion = new ConexionBD();
			Connection con = conexion.getConexion();
	
			sql = "SELECT count(*)as resp FROM Olimpiada WHERE nombre = ? AND temporada = ? ;";
	
			PreparedStatement ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, nom);
			ps.setString(2, temp);
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
