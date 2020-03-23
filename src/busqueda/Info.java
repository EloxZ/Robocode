package busqueda;

public class Info {
	private Info padre;
	private Estado estado;
	
	public Info(Info p, Estado e) {
		padre = p;
		estado = e;
		
	}

	public Info getPadre() {
		return padre;
	}

	public void setPadre(Info padre) {
		this.padre = padre;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
