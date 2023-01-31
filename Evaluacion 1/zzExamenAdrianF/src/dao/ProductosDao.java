package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexionBD.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Producto;

public class ProductosDao {
	private ConexionBD conexion;
	
	public ObservableList<Producto> cargarProductos() throws Exception {
		ObservableList<Producto> productos = FXCollections.observableArrayList();
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM productos;";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Producto p = new Producto(rs.getString("codigo"), rs.getString("nombre"), rs.getString("precio"), rs.getInt("disponible"));
			productos.add(p);

		}
		rs.close();
		ps.close();

		con.close();

		return productos;
	}

	public String crearProductos(Producto p) {
		@SuppressWarnings("unused")
		String sql, msg = "";
		try {
			conexion = new ConexionBD();
			@SuppressWarnings("unused")
			Connection con = conexion.getConexion();
			PreparedStatement ps;

			String SQL = "INSERT INTO productos VALUES (?,?,?,?,?)";
			ps = conexion.getConexion().prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getCodigo());
			ps.setString(2, p.getNombre());
			ps.setString(3, p.getPrecio());
			ps.setInt(4, p.getDisponible());
			ps.setString(5, null);
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

	public String editarProductos(Producto p) {
		String sql, msg = "";

		try {
			conexion = new ConexionBD();
			Connection con = conexion.getConexion();
			PreparedStatement ps;

			sql = "UPDATE productos SET nombre = '"+ p.getNombre() +"', precio = '"+ p.getPrecio() +"', disponible = '"+ p.getDisponible() +"' WHERE (codigo = '"+ p.getCodigo() +"');";
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

	public void borrarProductos(Producto p) throws Exception {
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();
		PreparedStatement ps;

		sql = "DELETE FROM productos WHERE (codigo = '"+ p.getCodigo() +"');";
		ps = con.prepareStatement(sql);
		ps.executeUpdate();

		ps.close();
		conexion.cerrarConexion();
	}
	
}
