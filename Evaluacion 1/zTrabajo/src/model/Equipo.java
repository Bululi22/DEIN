package model;

public class Equipo {

	private String id_equipo, nombre, iniciales;

	public Equipo(String id_equipo, String nombre, String iniciales) {
		this.id_equipo = id_equipo;
		this.nombre = nombre;
		this.iniciales = iniciales;
	}

	public String getId_equipo() {
		return id_equipo;
	}

	public void setId_equipo(String id_equipo) {
		this.id_equipo = id_equipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIniciales() {
		return iniciales;
	}

	public void setIniciales(String iniciales) {
		this.iniciales = iniciales;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	public String leer() {
		return "Equipos [id_equipo=" + id_equipo + ", nombre=" + nombre + ", iniciales=" + iniciales + "]";
	}
	
	
}
