package com.app.modelo;

import java.util.ArrayList;
import java.util.List;

public class ListaPregunta {
	private List<PreguntaFormato> lista =new ArrayList<PreguntaFormato>();
	private int formindx;

	public ListaPregunta() {
		// TODO Auto-generated constructor stub
	}

	public ListaPregunta(List<PreguntaFormato> lista, int formindx) {
		super();
		this.lista = lista;
		this.formindx = formindx;
	}

	public List<PreguntaFormato> getLista() {
		return lista;
	}

	public void setLista(List<PreguntaFormato> lista) {
		this.lista = lista;
	}

	public int getFormindx() {
		return formindx;
	}

	public void setFormindx(int formindx) {
		this.formindx = formindx;
	}
	
}
