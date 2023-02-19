package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexionBD.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Persona;

public class PersonaDao {
	private ConexionBD conexion;

	public ObservableList<Persona> cargarPersonas() throws Exception {
		ObservableList<Persona> personas = FXCollections.observableArrayList();
		String sql;

		conexion = new ConexionBD();
		Connection con = conexion.getConexion();

		sql = "SELECT * FROM persona;";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Persona d = new Persona(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("anio_nacimiento"));
			personas.add(d);
		}
		rs.close();
		ps.close();

		con.close();

		return personas;
	}
}
