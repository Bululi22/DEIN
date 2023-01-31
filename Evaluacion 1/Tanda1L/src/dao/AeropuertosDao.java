package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexionBD.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Vuelos;

public class AeropuertosDao {
	private ConexionBD conexion;

    public ObservableList<Vuelos> cargarVuelos(Boolean publico) {
        ObservableList<Vuelos> vuelos = FXCollections.observableArrayList();
        String sql;
        
        try {
			conexion = new ConexionBD();
	        Connection con = conexion.getConexion();
	        
	        
	        //AEROPUERTOS PRIVADOS
	        if (publico==false) {
	        	sql = "SELECT * FROM aeropuertos a, direcciones d, aeropuertos_privados p WHERE id_direccion = d.id AND p.id_aeropuerto = a.id;";
	        }
	        //AEROPUERTOS PUBLICOS
	        else {
	        	sql = "SELECT * FROM aeropuertos a, direcciones d, aeropuertos_publicos p WHERE id_direccion = d.id AND p.id_aeropuerto = a.id;";
	        }
	        
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	    		if (publico==false) {
	    			Vuelos v = new Vuelos(rs.getInt("a.id"), rs.getString("nombre"), rs.getString("pais"), rs.getString("ciudad"), rs.getString("calle"), rs.getInt("numero"), rs.getInt("anio_inauguracion"), rs.getInt("capacidad"), rs.getInt("numero_socios"), rs.getInt("id_direccion"));
	    			vuelos.add(v);
	    		}else {
	    			Vuelos v = new Vuelos(rs.getInt("a.id"), rs.getString("nombre"), rs.getString("pais"), rs.getString("ciudad"), rs.getString("calle"), rs.getInt("numero"), rs.getInt("anio_inauguracion"), rs.getInt("capacidad"), rs.getInt("id_direccion"));
	    			vuelos.add(v);
	    		}
	        }
	        rs.close();
	        ps.close();
	        
	        con.close();
        } catch (SQLException e) {
			e.printStackTrace();
		}
        return vuelos;
    }
    
    
    /*public boolean insertar(AeropuertoPrivado aeropuertoPrivado) throws SQLException {

    	conexion = new ConexionBD();
    	String SQL = "INSERT INTO aeropuertos VALUES (?,?,?,?,?,?)";
    	PreparedStatement ps = conexion.getConexion().prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
    	ps.setString(1, null);
    	ps.setString(2, aeropuertoPrivado.getNombre());
    	ps.setInt(3, aeropuertoPrivado.getAnioInauguracion());
    	ps.setInt(4, aeropuertoPrivado.getCapacidad());
    	ps.setInt(5, aeropuertoPrivado.getDireccion().getId_direccion());
    	ps.setBinaryStream(6, aeropuertoPrivado.getImagen());
    	int n = ps.executeUpdate();
    	int id = -1;
    	if (n > 0) {
	    	ResultSet rs = ps.getGeneratedKeys();
	    	if (rs.next()) {
	    		id = rs.getInt(1);
	    	}
	    	rs.close();
	    	aeropuertoPrivado.setIdAeropuerto(id);
	    	SQL = "INSERT INTO aeropuertos_privados VALUES (?,?)";
	    	ps = conexion.getConexion().prepareStatement(SQL);
	    	ps.setInt(1, id);
	    	ps.setInt(2, aeropuertoPrivado.getNumSocios());
	    	n = ps.executeUpdate();
    	}
    	ps.close();
    	conexion.cerrarConexion();
    	return n > 0;

	}*/
    
    
    public void crearAeropuerto(Vuelos v, String financiacion, String trabajadores, Boolean publico) {
    	String sql;
    	int id, n;
    	
    	try {
    		System.out.println("0");
			conexion = new ConexionBD();
	        Connection con = conexion.getConexion();
	        PreparedStatement ps;
	        ResultSet rs;
	        
	        //CREAR DIRECCION Y SACAR ID_DIRECCION
	        /*s = con.createStatement();
	        sql = "INSERT INTO direcciones (pais, ciudad, calle, numero) VALUES ("+ v.getPais() +", "+ v.getCiudad() +", "+ v.getCalle() +", "+ v.getNumero() +");";
	        s.executeUpdate(sql);
	        s.close();*/
	        
	        /*System.out.println("1");
	        sql = "SELECT * FROM direcciones WHERE pais = "+ v.getPais() +" AND ciudad = "+ v.getCiudad() +" AND calle = "+ v.getCalle() +" AND numero = "+ v.getNumero() +";";  
	        ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
	        int n = ps.executeUpdate();
	        
	        rs = ps.executeQuery();
	        if (rs.next()) {
	        	idDireccion = rs.getInt("id");
	        }
	        rs.close();
	        ps.close();*/
	        System.out.println("1");
	        
	        String SQL = "INSERT INTO direcciones VALUES (?,?,?,?,?)";
	    	ps = conexion.getConexion().prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
	    	ps.setString(1, null);
	    	ps.setString(2, v.getPais());
	    	ps.setString(3, v.getCiudad());
	    	ps.setString(4, v.getCalle());
	    	ps.setInt(5, v.getNumero());
	    	n = ps.executeUpdate();
	    	System.out.println("2");
	    	id = -1;
	    	if (n > 0) {
		    	rs = ps.getGeneratedKeys();
		    	if (rs.next()) {
		    		id = rs.getInt(1);
		    	}
		    	rs.close();
		    	v.setIdDirecciones(id);
		    	SQL = "INSERT INTO aeropuertos (nombre, anio_inauguracion, capacidad, id_direccion) VALUES ("+ v.getNombre() +", "+ v.getAnio() +", "+ v.getCapacidad() +", "+ v.getIdDirecciones() +")";
		    	ps = conexion.getConexion().prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
		    	n = ps.executeUpdate();
		    	System.out.println("3");
		    	id = -1;
		    	if (n > 0) {
			    	rs = ps.getGeneratedKeys();
			    	if (rs.next()) {
			    		id = rs.getInt(1);
			    	}
			    	rs.close();
			    	v.setId(id);
			    	//CREAR PUBLICOS O PRIVADOS
			    	System.out.println(publico);
			        if (publico) {
			        	SQL = "INSERT INTO aeropuertos_publicos (id_aeropuerto, financiacion, num_trabajadores) VALUES ("+ id +", "+ financiacion +", "+ trabajadores +");";
				        ps = conexion.getConexion().prepareStatement(SQL);
				        ps.executeUpdate();
			        }else {
			        	System.out.println("4");
			        	SQL = "INSERT INTO aeropuertos_privados (id_aeropuerto, numero_socios) VALUES ("+ id +", "+ v.getNumSocios() +");";
				        ps = conexion.getConexion().prepareStatement(SQL);
				        System.out.println("5");
				        ps.executeUpdate();
				        System.out.println("6");
			        }
		    	}
	    	}
	    	ps.close();
	    	conexion.cerrarConexion();
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        //CREAR AEROPUERTO Y SACAR ID
	        /*System.out.println("2");
	        s = con.createStatement();
	        sql = "INSERT INTO aeropuertos (nombre, anio_inauguracion, capacidad, id_direccion) VALUES ("+ v.getNombre() +", "+ v.getAnio() +", "+ v.getCapacidad() +", "+ idDireccion +")";
	        s.executeUpdate(sql);
	        s.close();
	        
	        System.out.println("3");
	        sql = "SELECT * FROM aeropuertos where nombre = "+ v.getNombre() +";";
	        ps = con.prepareStatement(sql);
	        rs = ps.executeQuery();
	        id = rs.getInt("id");
	        rs.close();
	        ps.close();
	        System.out.println("4");
	        /*
	        //CREAR PUBLICOS O PRIVADOS
	        if (publico) {
		        sql = "INSERT INTO aeropuertos_publicos (id_aeropuerto, financiacion, num_trabajadores) VALUES ("+ id +", "+ financiacion +", "+ trabajadores +");";
		        ps = con.prepareStatement(sql);
		        ps.close();
	        }else {
		        sql = "INSERT INTO aeropuertos_privados (id_aeropuerto, numero_socios) VALUES ("+ id +", "+ v.getNumSocios() +");";
		        ps = con.prepareStatement(sql);
		        ps.close();
	        }*/
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    }
    
    public void editarAeropuerto(Vuelos v, String financiacion, String trabajadores, Boolean publico) {
    	String sql;
    	
    	try {
			conexion = new ConexionBD();
	        Connection con = conexion.getConexion();
	        PreparedStatement ps;
	        
	        if (publico) {
		        sql = "UPDATE aeropuertos_publicos SET financiacion = "+ financiacion +", num_trabajadores = "+ trabajadores +" WHERE (id_aeropuerto = "+ v.getId() +");";
		        ps = con.prepareStatement(sql);
		        ps.close();
	        }else {
		        sql = "UPDATE aeropuertos_privados SET numero_socios = "+ v.getNumSocios() +" WHERE (id_aeropuerto = "+ v.getId() +");";
		        ps = con.prepareStatement(sql);
		        ps.close();
	        }
	        
	        sql = "UPDATE direcciones SET pais = "+ v.getPais() +", ciudad = "+ v.getCiudad() +", calle = "+ v.getCalle() +", numero = "+ v.getNumero() +" WHERE (id = "+ v.getIdDirecciones() +");";
	        ps = con.prepareStatement(sql);
	        ps.close();
	        
	        sql = "UPDATE aeropuertos SET nombre = "+ v.getNombre() +", anio_inauguracion = "+ v.getAnio() +", capacidad = "+ v.getCapacidad() +" WHERE (id = "+ v.getId() +");";
	        ps = con.prepareStatement(sql);
	        ps.close();
	        //System.out.println("***");
	        con.close();
        } catch (SQLException e) {
			e.printStackTrace();
		}
    }
}