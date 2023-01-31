package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import conexionBD.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deportista;
import model.Equipo;
import model.Evento;
import model.Participacion;

public class ParticipacionDao {
	
	private ConexionBD conexion;

	public ObservableList<Participacion> cargarParticipacion() throws Exception {
		ObservableList<Participacion> participaciones = FXCollections.observableArrayList();
		String sql;
		DeportistaDao depDao = new DeportistaDao();
		EventoDao eveDao = new EventoDao();
		EquipoDao equDao = new EquipoDao();
		Deportista deportista;
		Evento evento;
		Equipo equipo;
		

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM Participacion;";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			deportista = depDao.getDeportista(rs.getString("id_deportista"));
			evento = eveDao.getEvento(rs.getString("id_evento"));
			equipo = equDao.getEquipo(rs.getString("id_equipo"));
			Participacion p = new Participacion(deportista, evento, equipo, rs.getString("edad"), rs.getString("medalla"));
			participaciones.add(p);
		}
		rs.close();
		ps.close();

		con.close();

		return participaciones;
	}
	
	public String crearParticipacion(Participacion p) {
		String sql, msg = "";
		try {
			conexion = new ConexionBD();
			PreparedStatement ps;

			sql = "INSERT INTO Participacion VALUES (?,?,?,?,?)";
			ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getDeportista().getId_deportista());
			ps.setString(2, p.getEvento().getId_evento());
			ps.setString(3, p.getEquipo().getId_equipo());
			if (p.getEdad() == "") {
				ps.setString(4, null);
			}else {
				ps.setString(4, p.getEdad());
			}
			if (p.getMedalla() == "Ninguna") {
				ps.setString(5, null);
			}else {
				ps.setString(5, p.getMedalla());
			}
			ps.executeUpdate();

			ps.close();
			conexion.cerrarConexion();
		}catch (SQLIntegrityConstraintViolationException e) {
			msg+=p.getDeportista().getNombre()+" ya a competido en "+p.getEvento().getNombre();
		} catch (Exception ex) {
			ex.printStackTrace();
			msg+="Fallo de creacion";
		}
		System.out.println(msg);
		return msg;
	}

	public String editarParticipacion(Participacion p, String id_deportista, String id_evento) {
		String sql, msg = "";
		
		sql = "UPDATE Participacion SET id_deportista = ?, id_evento = ?, id_equipo = ?, edad = ?, medalla = ? WHERE (id_deportista = ?) and (id_evento = ?);";
		
		try {
			conexion = new ConexionBD();
			
			PreparedStatement ps;

			ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getDeportista().getId_deportista());
			ps.setString(2, p.getEvento().getId_evento());
			ps.setString(3, p.getEquipo().getId_equipo());
			if (p.getEdad() == "") {
				System.out.println("*");
				ps.setString(4, null);
				System.out.println("*");
			}else {
				ps.setString(4, p.getEdad());
			}
			if (p.getMedalla() == "Ninguna") {
				ps.setString(5, null);
			}else {
				ps.setString(5, p.getMedalla());
			}
			ps.setString(6, id_deportista);
			ps.setString(7, id_evento);
			
			ps.executeUpdate();
			ps.close();

			

			return msg;
		} catch (Exception ex) {
			ex.printStackTrace();
			msg = "Fallo al editar";
		}
		return msg;
	}

	public void borrarParticipacion(Participacion p) throws Exception {
		String sql;
		PreparedStatement ps;
		conexion = new ConexionBD();

		sql = "DELETE FROM Participacion WHERE (id_evento = ?) AND (id_deportista = ?);";
		ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, p.getEvento().getId_evento());
		ps.setString(2, p.getDeportista().getId_deportista());
		ps.executeUpdate();

		ps.close();
		conexion.cerrarConexion();
	}
	
}
