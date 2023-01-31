package model;

public class Olimpiada {

	private int id_olimpiada, anio;
	private String nombre, temporada, ciudad;
	
	public Olimpiada(int id_olimpiada, String nombre, int anio, String temporada, String ciudad) {
		super();
		this.id_olimpiada = id_olimpiada;
		this.anio = anio;
		this.nombre = nombre;
		this.temporada = temporada;
		this.ciudad = ciudad;
	}

	
	
	@Override
	public String toString() {
		return nombre;
	}

	public int getId_olimpiada() {
		return id_olimpiada;
	}

	public void setId_olimpiada(int id_olimpiada) {
		this.id_olimpiada = id_olimpiada;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTemporada() {
		return temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	
}
