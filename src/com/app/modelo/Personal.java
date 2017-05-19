package com.app.modelo;

public class Personal {
	
	private String cedula;
	private String nombre;
	private String dependencia;
	private String cargo;
	private int formresp;
	private String sincronizado;

	public Personal() {
		// TODO Auto-generated constructor stub
	}

	public Personal( String nombre, String dependencia,
			String cargo, String cedula, int formresp) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.dependencia = dependencia;
		this.cargo = cargo;
		this.formresp = formresp;
		this.sincronizado = "S";
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public int getFormresp() {
		return formresp;
	}

	public void setFormresp(int formresp) {
		this.formresp = formresp;
	}

	public String getSincronizacion() {
		return sincronizado;
	}

	public void setSincronizacion(String sincronizacion) {
		this.sincronizado = sincronizacion;
	}
	

}
