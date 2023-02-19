package model;

public class Persona {

	private String dni, nombre, apellido1, apellido2;
	private int anio_nacimiento;
	
	public Persona(String dni, String nombre, String apellido1, String apellido2, int anio_nacimiento) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.anio_nacimiento = anio_nacimiento;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public int getAnio_nacimiento() {
		return anio_nacimiento;
	}

	public void setAnio_nacimiento(int anio_nacimiento) {
		this.anio_nacimiento = anio_nacimiento;
	}

	@Override
	public String toString() {
		return "Persona [dni=" + dni + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2
				+ ", anio_nacimiento=" + anio_nacimiento + "]";
	}
	
	
	
	
}
