package model;

public class Libro {
	private int codigo, baja;
	private String titulo, autor, editorial, estado;
	
	public Libro(int codigo, String titulo, String autor, String editorial, String estado, int baja) {
		this.codigo = codigo;
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.estado = estado;

//		BAJA = 0   /     ALTA = 1
		this.baja = baja;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editoria) {
		this.editorial = editoria;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getBaja() {
		return baja;
	}

	public void setBaja(int baja) {
		this.baja = baja;
	}

	@Override
	public String toString() {
		return titulo;
	}
	
	public String leer() {
		return "Libro [codigo=" + codigo + ", titulo=" + titulo + ", autor=" + autor + ", editoria=" + editorial
				+ ", estado=" + estado + ", baja=" + baja + "]";
	}

	
	
}
