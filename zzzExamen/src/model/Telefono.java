package model;

public class Telefono {

	private int id, telefono;
	private String dni;
	
	public Telefono(int id, String dni, int telefono) {
		this.id = id;
		this.telefono = telefono;
		this.dni = dni;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	
	
	
}
