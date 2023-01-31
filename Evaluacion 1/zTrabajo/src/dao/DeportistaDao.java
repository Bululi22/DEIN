package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexionBD.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deportista;

public class DeportistaDao {
	
	private ConexionBD conexion;

	public ObservableList<Deportista> cargarDeportista() throws Exception {
		ObservableList<Deportista> deportistas = FXCollections.observableArrayList();
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM Deportista;";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Deportista d = new Deportista(rs.getString("id_deportista"), rs.getString("nombre"), rs.getString("sexo"),
					rs.getString("peso"), rs.getString("altura")/*, rs.getBinaryStream("foto")*/);
			deportistas.add(d);
		}
		rs.close();
		ps.close();

		con.close();

		return deportistas;
	}

	public Deportista getDeportista(String id) throws Exception {
		Deportista x = null;
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM Deportista WHERE id_deportista = '" + id + "';";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			
			x = new Deportista(rs.getString("id_deportista"), rs.getString("nombre"), rs.getString("sexo"),
					rs.getString("peso"), rs.getString("altura")/*, (InputStream)rs.getBlob("foto")*/);
		}
		rs.close();
		ps.close();

		con.close();
		return x;
	}

	public String crearDeportista(Deportista d) {
		String sql, msg = "";
		try {
			conexion = new ConexionBD();
			@SuppressWarnings("unused")
			Connection con = conexion.getConexion();
			PreparedStatement ps;

			sql = "INSERT INTO Deportista VALUES (?,?,?,?,?,?)";
			ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, null);
			ps.setString(2, d.getNombre());
			ps.setString(3, d.getSexo());
			if (d.getPeso() == "") {
				ps.setString(4, null);
			}else {
				ps.setString(4, d.getPeso());
			}
			if (d.getAltura() == "") {
				ps.setString(5, null);
			}else {
				ps.setString(5, d.getAltura());
			}
			ps.setString(6, null);
			//ps.setBlob(6, d.getFoto());
			ps.executeUpdate();

			ps.close();
			conexion.cerrarConexion();
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			msg+="Fallo de creacion";
		}
		return msg;
	}

	public String editarDeportista(Deportista d) {
		String sql, msg = "";

		try {
			conexion = new ConexionBD();
			Connection con = conexion.getConexion();
			PreparedStatement ps;

			sql = "UPDATE Deportista SET nombre = ? , sexo = ? , peso = ? , altura = ? WHERE (id_deportista = ? );";
			ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, null);
			ps.setString(1, d.getNombre());
			ps.setString(2, d.getSexo());
			if (d.getPeso() == "") {
				ps.setString(3, null);
			}else {
				ps.setString(3, d.getPeso());
			}
			if (d.getAltura() == "") {
				ps.setString(4, null);
			}else {
				ps.setString(4, d.getAltura());
			}
			ps.setString(5, d.getId_deportista());
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

	public void borrarDeportista(Deportista d) throws Exception {
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();
		PreparedStatement ps;

		sql = "DELETE FROM Participacion WHERE (id_deportista = '" + d.getId_deportista() + "');";
		ps = con.prepareStatement(sql);
		ps.executeUpdate();

		sql = "DELETE FROM Deportista WHERE (id_deportista = '" + d.getId_deportista() + "');";
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
	
			sql = "SELECT count(*)as resp FROM Deportista WHERE nombre = ? ;";
	
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
