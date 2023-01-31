package model;

public class Deporte {

	private String id_deporte, nombre;
	
	public Deporte(String id_deporte, String nombre) {
		this.id_deporte = id_deporte;
		this.nombre = nombre;
	}

	public String getId_deporte() {
		return id_deporte;
	}

	public void setId_deporte(String id_deporte) {
		this.id_deporte = id_deporte;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	public String leer() {
		return "Deportes [id_deporte=" + id_deporte + ", nombre=" + nombre + "]";
	}
	
	
	
	
}
