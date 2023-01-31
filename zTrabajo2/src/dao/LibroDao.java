package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexionBD.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Libro;

public class LibroDao {
	private ConexionBD conexion;

	public ObservableList<Libro> cargarLibros() throws Exception {
		ObservableList<Libro> libros = FXCollections.observableArrayList();
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM Libro WHERE baja = 0;";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Libro d = new Libro(rs.getInt("codigo"), rs.getString("titulo"), rs.getString("autor"), rs.getString("editorial"), rs.getString("estado"), rs.getInt("baja"));
			libros.add(d);
		}
		rs.close();
		ps.close();

		con.close();

		return libros;
	}

	public Libro getLibro(String id) throws Exception {
		Libro l =null;
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM Libro WHERE codigo = '" + id + "';";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			l = new Libro(rs.getInt("codigo"), rs.getString("titulo"), rs.getString("autor"), rs.getString("editorial"), rs.getString("estado"), rs.getInt("baja"));
		}
		rs.close();
		ps.close();

		con.close();
		return l;
	}

	public String crearLibro(Libro l) {
		String sql, msg = "";
		try {
			conexion = new ConexionBD();
			PreparedStatement ps;
	
			sql = "INSERT INTO Libro VALUES (?,?,?,?,?,?)";
			ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, null);
			ps.setString(2, l.getTitulo());
			ps.setString(3, l.getAutor());
			ps.setString(4, l.getEditorial());
			ps.setString(5, l.getEstado());
			ps.setString(6, l.getBaja());
			ps.executeUpdate();
	
			ps.close();
			conexion.cerrarConexion();
		} catch (Exception ex) {
			ex.printStackTrace();
			msg = "Fallo al crear";
		}
		return msg;
	}

	public String editarLibro(Libro l) {
		String sql, msg = "";
		
		try {
			conexion = new ConexionBD();
			Connection con = conexion.getConexion();
			PreparedStatement ps;
			sql = "UPDATE Libro SET titulo = ?, autor = ?, editorial = ?, estado = ?, baja = ? WHERE (codigo = ?);";
			ps = conexion.getConexion().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, l.getTitulo());
			ps.setString(2, l.getAutor());
			ps.setString(3, l.getEditorial());
			ps.setString(4, l.getEstado());
			ps.setString(5, l.getBaja());
			ps.setInt(6, l.getCodigo());
			ps.executeUpdate();

			ps.close();
			con.close();

			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Fallo al editar";
		}
		return msg;
	}
}
