package com.app.modelo;

public class Diligenciar {
	private String id;
	private int formulario;
	private String usuario;
	private String nombre;
	private String sincronizado;

	public Diligenciar() {
		// TODO Auto-generated constructor stub
	}

	public Diligenciar(String id, int formulario, String usuario, String nombre) {
		super();
		this.id = id;
		this.formulario = formulario;
		this.usuario = usuario;
		this.nombre = nombre;
		this.sincronizado = "S";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFormulario() {
		return formulario;
	}

	public void setFormulario(int formulario) {
		this.formulario = formulario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
