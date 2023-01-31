package model;

import java.util.Objects;

public class Persona {
	private String nombre,apellido,edad;
	private int id;
	
	public Persona() {
		this(0,null, null, null);
	}
	public Persona(int id,String nom,String ape,String edad) {
		this.id=id;
		this.nombre=nom;
		this.apellido=ape;
		this.edad=edad;
	}
	public Persona(String nom,String ape,String edad) {

		this.nombre=nom;
		this.apellido=ape;
		this.edad=edad;
	}
	
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + "]";
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}
	public String getApellido() {
		return apellido;
	}
	public String getEdad() {
		return edad;
	}
	@Override
	public int hashCode() {
		return Objects.hash(apellido, edad, nombre);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(apellido, other.apellido) && Objects.equals(edad, other.edad)
				&& Objects.equals(nombre, other.nombre);
	}
	
}
