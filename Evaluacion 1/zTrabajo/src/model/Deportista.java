package model;

public class Deportista {

	private String id_deportista, nombre, sexo, peso, altura;
	//private InputStream foto;

	public Deportista(String id_deportista, String nombre, String sexo, String peso, String altura/*, InputStream foto*/) {
		this.id_deportista = id_deportista;
		this.nombre = nombre;
		this.sexo = sexo;
		this.peso = peso;
		this.altura = altura;
		//this.foto = foto;
	}

	public String getId_deportista() {
		return id_deportista;
	}

	public void setId_deportista(String id_deportista) {
		this.id_deportista = id_deportista;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	/*public InputStream getFoto() {
		return foto;
	}

	public void setFoto(InputStream foto) {
		this.foto = foto;
	}*/

	@Override
	public String toString() {
		return nombre;
	}
	
	public String leer() {
		return "Deportistas [id_deportista=" + id_deportista + ", nombre=" + nombre + ", sexo=" + sexo + ", peso="
				+ peso + ", altura=" + altura + "]";
	}
	
	
	
	
	
}
