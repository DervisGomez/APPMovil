package com.app.modelo;

public class Respuesta {

	private String resppreg; //corresponde al  pregindx del servicio de datosPorFormato
	private String resofore; //corresponde al id retornado por el servicio nuevoFormRespuesta
	private String respucre; //usuario logeado
	private String respresp; //opciindx seleccionado en las opciones
	private String sincronizado;

	public Respuesta() {
		// TODO Auto-generated constructor stub
	}

	public Respuesta(String resppreg, String resofore, String respucre,
			String respresp) {
		super();
		this.resppreg = resppreg;
		this.resofore = resofore;
		this.respucre = respucre;
		this.respresp = respresp;
		this.sincronizado="S";
	}

	public String getResppreg() {
		return resppreg;
	}

	public void setResppreg(String resppreg) {
		this.resppreg = resppreg;
	}

	public String getResofore() {
		return resofore;
	}

	public void setResofore(String resofore) {
		this.resofore = resofore;
	}

	public String getRespucre() {
		return respucre;
	}

	public void setRespucre(String respucre) {
		this.respucre = respucre;
	}

	public String getRespresp() {
		return respresp;
	}

	public void setRespresp(String respresp) {
		this.respresp = respresp;
	}

}
