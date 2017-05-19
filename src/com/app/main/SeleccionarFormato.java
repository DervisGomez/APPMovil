package com.app.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.app.bd.BDDato;
import com.app.conexion.VerificarConexion;
import com.app.modelo.DatosCombo;
import com.app.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SeleccionarFormato extends Activity implements OnClickListener  {
	private static final int REQUES_CODE_DOS = 200;
	private Spinner spFormato;
	private Button btnIniciar;
	private Button btnRegresar;
	private List<DatosCombo> datosCombo;
	
	public SeleccionarFormato() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seleccinar_formato);
		Bundle bolsa=getIntent().getExtras();
		int x=bolsa.getInt("centro");
		spFormato=(Spinner)findViewById(R.id.spFormato);
		btnIniciar=(Button)findViewById(R.id.btnIniciar);
		btnRegresar=(Button)findViewById(R.id.btnRegresar);
		btnIniciar.setOnClickListener(this);
		btnRegresar.setOnClickListener(this);
		VerificarConexion verificar=new VerificarConexion(this);
		
		if(verificar.estaConectado()){
			conConexion(x);
		}else{
			sinConexion(x);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==REQUES_CODE_DOS){
			if(resultCode==RESULT_OK){
				int c=data.getIntExtra("cerrar_dos", -1);
				if(c==2){
					finish();
				}
			}
		}		
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void iniciar(String tiraJson){
		
		datosCombo=new ArrayList<DatosCombo>();
    	DatosCombo datos=new DatosCombo();
    	datosCombo = datos.getListDatosCombo(tiraJson);
		ArrayAdapter<String> spFormatoAdaptener=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getStringCN());
		spFormato.setAdapter(spFormatoAdaptener);
	}
	
	private void sinConexion(int x) {
		datosCombo=new ArrayList<DatosCombo>();
		BDDato bdDato=new BDDato(this);
		bdDato.abrir();
		try {
			datosCombo=bdDato.consultarFormatoDC(x);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bdDato.cerrar();
		ArrayAdapter<String> spFormatoAdaptener=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getStringCN());
		spFormato.setAdapter(spFormatoAdaptener);
	}

	private void conConexion(int x) {
		new MiTarea(SeleccionarFormato.this,x).execute();
	}

	private String[] getStringCN() {
		// TODO Auto-generated method stub
		String[] stringCN=new String[datosCombo.size()];
		for(int x=0;x<datosCombo.size();x++){
			stringCN[x]=datosCombo.get(x).getNombre();
		}
		return stringCN;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btnIniciar:
			int x=spFormato.getSelectedItemPosition();
			if(x<0){
				Toast t=Toast.makeText(this, "Seleccionar Formato", Toast.LENGTH_SHORT);
				t.show();
			}else{
				Bundle bolsa=new Bundle();
				bolsa.putInt("formato",datosCombo.get(x).getId());
				Intent intent=new Intent("com.app.main.BuscarUsuario");
				intent.putExtras(bolsa);
//				startActivity(intent);
				startActivityForResult(intent,REQUES_CODE_DOS);
			}
			break;
		case R.id.btnRegresar:
			finish();
			break;
		}
	}
	
	private class MiTarea extends AsyncTask<String, Float, String>{
		 private String ur;
		 private Context c;
		 public MiTarea(Context c,int x){
			 this.ur="http://pruebas.akc.co:8087/FormatosSO/formatos/listarFormatos/"+String.valueOf(x);
			 this.c=c;
		 }
       protected void onPreExecute() {

        }

        protected String doInBackground(String... urls) {
       	 String responce = "";
            BufferedReader rd = null;
            try {
                URL url = new URL(ur);
                rd = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    responce += line;
                }

            } catch (Exception e) {
                
            } finally {
                if (rd != null) {
                    try {
                        rd.close();
                    } catch (IOException ex) {
                        
                    }
                }
            }
            return responce;
        }

        protected void onProgressUpdate (Float... valores) {

        }

        protected void onPostExecute(String tiraJson) {
        	iniciar(tiraJson);
        	
        }
  }

}
