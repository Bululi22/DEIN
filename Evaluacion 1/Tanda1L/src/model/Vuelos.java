package model;

public class Vuelos {
	
	private int id, numero, anio, capacidad, numSocios, idDirecciones;
	private String nombre, pais, ciudad, calle;
	
	public Vuelos(int id, String nombre, String pais, String ciudad, String calle, int numero, int anio, int capacidad, int numSocios, int idDirecciones) {
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
		this.calle = calle;
		this.numero = numero;
		this.anio = anio;
		this.capacidad = capacidad;
		this.numSocios = numSocios;
		this.idDirecciones = idDirecciones;
	}

	public Vuelos(int id, String nombre, String pais, String ciudad, String calle, int numero, int anio, int capacidad, int idDirecciones) {
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
		this.calle = calle;
		this.numero = numero;
		this.anio = anio;
		this.capacidad = capacidad;
		this.idDirecciones = idDirecciones;
	}
	
	
	@Override
	public String toString() {
		return "Vuelos [id=" + id + ", numero=" + numero + ", anio=" + anio + ", capacidad=" + capacidad + ", numSocios=" + numSocios + ", nombre=" + nombre + ", pais=" + pais + ", ciudad=" + ciudad + ", calle=" + calle + ", idDireccion=" + idDirecciones +"]";
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
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

	public int getNumSocios() {
		return numSocios;
	}

	public void setNumSocios(int numSocios) {
		this.numSocios = numSocios;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getIdDirecciones() {
		return idDirecciones;
	}

	public void setIdDirecciones(int idDirecciones) {
		this.idDirecciones = idDirecciones;
	}
	
	
}
