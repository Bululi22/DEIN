package model;

public class Aeropuerto {

	private int id, anio, capacidad, idDireccion;
	private String nombre;
	
	public Aeropuerto(int id, String nombre, int anio, int capacidad, int idDireccion) {
		this.id = id;
		this.anio = anio;
		this.capacidad = capacidad;
		this.idDireccion = idDireccion;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(int idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
