package model;

public class Telefono {

	private int id;
	private Persona persona;
	private String telefono;
	
	public Telefono(int id, Persona persona, String telefono) {
		super();
		this.id = id;
		this.persona = persona;
		this.telefono = telefono;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	@Override
	public String toString() {
		return telefono;
	}
	
	
}
