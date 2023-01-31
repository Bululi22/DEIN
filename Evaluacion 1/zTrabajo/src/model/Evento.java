package model;

public class Evento {

	private String id_evento, nombre;
	private Olimpiada olimpiada;
	private Deporte deporte;

	public Evento(String id_evento, String nombre, Olimpiada olimpiada, Deporte deporte) {
		this.id_evento = id_evento;
		this.nombre = nombre;
		this.olimpiada = olimpiada;
		this.deporte = deporte;
	}

	public String getNomOlimpiada() {
		return olimpiada.getNombre();
	}
	
	public String getNomDeporte() {
		return deporte.getNombre();
	}
	
	public String getId_evento() {
		return id_evento;
	}

	public void setId_evento(String id_evento) {
		this.id_evento = id_evento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Olimpiada getOlimpiada() {
		return olimpiada;
	}

	public void setOlimpiada(Olimpiada olimpiada) {
		this.olimpiada = olimpiada;
	}

	public Deporte getDeporte() {
		return deporte;
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}
	
	@Override
	public String toString() {
		return nombre+" "+olimpiada.getNombre();
	}
	
	public String leer() {
		return "Eventos [id_evento=" + id_evento + ", nombre=" + nombre + ", olimpiada=" + olimpiada.getNombre()
				+ ", deporte=" + deporte.getNombre() + "]";
	}
	
	
}
