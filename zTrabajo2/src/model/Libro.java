package model;

public class Libro {
	private int codigo;
	private String titulo, autor, editorial, estado, baja;
	
	public Libro(int codigo, String titulo, String autor, String editorial, String estado, int baja) {
		this.codigo = codigo;
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.estado = estado;
		if (baja == 0) {
			this.baja = "Disponible";
		}else {
			this.baja = "Baja";
		}
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

	public String getBaja() {
		return baja;
	}

	public void setBaja(String baja) {
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
