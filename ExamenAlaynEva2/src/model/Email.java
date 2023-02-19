package model;


public class Email {
	private int id;
	private Persona persona;
	private String email;
	
	public Email(int id, Persona persona, String email) {
		super();
		this.id = id;
		this.persona = persona;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return email;
	}
	
	
}
