package model;

public class Participacion {

	private String edad, medalla;
	private Deportista deportista;
	private Evento evento;
	private Equipo equipo;
		

	public Participacion(Deportista deportista, Evento evento, Equipo equipo, String edad, String medalla) {
		this.deportista = deportista;
		this.evento = evento;
		this.equipo = equipo;
		this.edad = edad;
		this.medalla = medalla;
	}

	public String getNomDeportista() {
		return deportista.getNombre();
	}
	
	public String getNomEvento() {
		return evento.getNombre();
	}
	
	public String getNomEquipo() {
		return equipo.getNombre();
	}
	
	public Deportista getDeportista() {
		return deportista;
	}

	public void setDeportista(Deportista deportista) {
		this.deportista = deportista;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getMedalla() {
		return medalla;
	}

	public void setMedalla(String medalla) {
		this.medalla = medalla;
	}
	
	@Override
	public String toString() {
		return "Participaciones [deportista=" + deportista.getNombre() + ", evento=" + evento.getNombre() + ", equipo="
				+ equipo.getNombre() + ", edad=" + edad + ", medalla=" + medalla + "]";
	}
	
}
