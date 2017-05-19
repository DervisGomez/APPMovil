package com.app.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.R;
import com.app.bd.BDDato;
import com.app.conexion.VerificarConexion;
import com.app.modelo.DatosCombo;
import com.app.modelo.DatosFormato;
import com.app.modelo.Diligenciar;
import com.app.modelo.Personal;
import com.app.modelo.PreguntaFormato;
import com.app.modelo.Respuesta;
import com.app.modelo.RespuestaPregunta;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InicioActivity extends Activity implements OnClickListener {
	private static final int REQUE_CODE_ONE = 100;
	private Spinner spCN;
	private Button btnCN;
	private Button btnDescargar;
	private Button btnDescar;
	private Button btnSubir;
	private Button btnSub;
	private List<DatosCombo> datosCombo;
	private String tiraJsonCN;
	private int control;
	private Button btnMenu;
	private TextView tvDescargar;
	private TextView tvSubir;
	private TextView tvCN;
	private LinearLayout llSiguiente;
	private RelativeLayout rlDescargar;
	private RelativeLayout rlSubir;
	private boolean menu;
	private int cp;
	private List<Personal> listPersonal=new ArrayList<Personal>();
	private List<Diligenciar> listDiligenciar=new ArrayList<Diligenciar>();
	private List<Respuesta> listRespuesta=new ArrayList<Respuesta>();
	private int contr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
		spCN=(Spinner)findViewById(R.id.spCN);
		btnCN=(Button)findViewById(R.id.btnCN);
		btnCN.setOnClickListener(this);
		btnMenu=(Button)findViewById(R.id.btnMenu);
		btnDescargar=(Button)findViewById(R.id.btnDescargar);
		btnDescar=(Button)findViewById(R.id.btnDescar);
		tvDescargar=(TextView)findViewById(R.id.tvDescargar);
		rlDescargar=(RelativeLayout)findViewById(R.id.rlDescargar);
		btnSubir=(Button)findViewById(R.id.btnSubir);
		btnSub=(Button)findViewById(R.id.btnSub);
		tvSubir=(TextView)findViewById(R.id.tvSubir);
		rlSubir=(RelativeLayout)findViewById(R.id.rlSubir);
		tvCN=(TextView)findViewById(R.id.tvCN);
		llSiguiente=(LinearLayout)findViewById(R.id.llSiguiente);
		btnMenu.setOnClickListener(this);
		tvDescargar.setOnClickListener(this);
		rlDescargar.setOnClickListener(this);
		btnDescargar.setOnClickListener(this);
		btnDescar.setOnClickListener(this);
		rlSubir.setOnClickListener(this);
		btnSubir.setOnClickListener(this);
		btnSub.setOnClickListener(this);
		tvSubir.setOnClickListener(this);
		invisible();
		VerificarConexion verificar=new VerificarConexion(this);
		if(verificar.estaConectado()){
			conConexion();
		}else{
			sinConexion();
		}

	}
	public void invisibleComponente(){
		spCN.setVisibility(View.GONE);
		llSiguiente.setVisibility(View.GONE);
		tvCN.setVisibility(View.GONE);
	}
	public void visibleComponente(){
		spCN.setVisibility(View.VISIBLE);
		llSiguiente.setVisibility(View.VISIBLE);
		btnMenu.setVisibility(View.VISIBLE);
		tvCN.setVisibility(View.VISIBLE);
		tvCN.setText("Centro de Negocio");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==REQUE_CODE_ONE){
			if(resultCode==RESULT_OK){
				int c=data.getIntExtra("cerrar_one", -1);
				if(c==1){
					finish();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void iniciar(){
		datosCombo=new ArrayList<DatosCombo>();
    	DatosCombo datos=new DatosCombo();
    	datosCombo = datos.getListDatosCombo(tiraJsonCN);
    	ArrayAdapter<String> spCNAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getStringCN());
    	spCN.setAdapter(spCNAdapter);
	}
	
	private void sinConexion() {
		datosCombo=new ArrayList<DatosCombo>();
    	BDDato bdDato=new BDDato(this);
    	bdDato.abrir();
    	try {
			datosCombo=bdDato.consultarCentroNegocio();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	bdDato.cerrar();
    	ArrayAdapter<String> spCNAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getStringCN());
    	spCN.setAdapter(spCNAdapter);
    	btnMenu.setVisibility(View.INVISIBLE);
	}

	private void conConexion() {
		control=0;
		new MiTarea(InicioActivity.this,"http://pruebas.akc.co:8087/FormatosSO/formatos/listarDependencias").execute();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int y=0;
		switch(v.getId()){
		case R.id.btnCN:
			int x=spCN.getSelectedItemPosition();
			if(x<0){
				Toast t=Toast.makeText(this, "Seleccionar Centro de Negocio", Toast.LENGTH_SHORT);
				t.show();
			}else{
				Bundle bolsa=new Bundle();
				bolsa.putInt("centro",datosCombo.get(x).getId());
				Intent int1=new Intent("com.app.main.SeleccionarFormato");
				int1.putExtras(bolsa);				
				startActivityForResult(int1,REQUE_CODE_ONE);
				
				break;
			}
		case R.id.btnMenu:
			y++;
			if(menu){
				rlDescargar.setVisibility(View.VISIBLE);
				rlSubir.setVisibility(View.VISIBLE);
                invisibleComponente();
				menu=false;
			}else{
				invisible();
                visibleComponente();
			}
		case R.id.tvDescargar:
			if(y<1){
				y++;
				descargarDatos();		        
			}
		case R.id.rlDescargar:
			if(y<1){
				y++;
				descargarDatos();		        
			}
		case R.id.btnDescargar:
			if(y<1){
				y++;
				descargarDatos();		        
			}
		case R.id.btnDescar:
			if(y<1){
				y++;
				descargarDatos();		        
			}
		case R.id.tvSubir:
			if(y<1){
				y++;
				 subirDatos();	
			}
		case R.id.btnSubir:
			if(y<1){
				y++;
				 subirDatos();	
			}
		case R.id.btnSub:
			if(y<1){
				y++;
				 subirDatos();	
			}
		case R.id.rlSubir:
			if(y<1){
				y++;
				 subirDatos();	
			}
		}
		
	}
	
	public void subirDatos(){
		invisible();
		AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);  
        dialogo1.setTitle("Importante");  
        dialogo1.setMessage("¿Subir datos guandados en tu dipositivo?");            
        dialogo1.setCancelable(false);  
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
            	VerificarConexion verificar=new VerificarConexion(InicioActivity.this);
        		if(verificar.estaConectado()){
        			subirPersonal();
        		}else{
        			visibleComponente();
        			Toast t=Toast.makeText(InicioActivity.this, "No tiene conexión", Toast.LENGTH_SHORT);
        			t.show();
        		}
                
            }  
        });  
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
            	invisible();
            	visibleComponente();
            }  
        });            
        dialogo1.show();
	}
	
	public void descargarDatos(){
        invisible();
		AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);  
        dialogo1.setTitle("Importante");  
        dialogo1.setMessage("¿Descargar datos a tu dispositivo?");            
        dialogo1.setCancelable(false);  
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
                VerificarConexion verificar=new VerificarConexion(InicioActivity.this);
        		if(verificar.estaConectado()){
        			tvCN.setVisibility(View.VISIBLE);
        			tvCN.setText("Descargando Datos");
        			btnMenu.setVisibility(View.GONE);
	            	descargarCN();
        		}else{
        			visibleComponente();
        			Toast t=Toast.makeText(InicioActivity.this, "No tiene conexión", Toast.LENGTH_SHORT);
        			t.show();
        		}
                
            }  
        });  
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {
            	visibleComponente();
            }  
        });            
        dialogo1.show();
	}
	
	public void invisible(){
		rlSubir.setVisibility(View.INVISIBLE);
		rlDescargar.setVisibility(View.INVISIBLE);
		menu=true;
	}
	
	public void subirPersonal(){
		BDDato bdDato=new BDDato(this);
		
		bdDato.abrir();
		try {
			listPersonal=bdDato.consultarPersonal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bdDato.cerrar();
		cp=0;
		if(listPersonal.size()>0){
			invisibleComponente();
			tvCN.setVisibility(View.VISIBLE);
            tvCN.setText("Subiendo Datos");
            btnMenu.setVisibility(View.GONE);
			Gson gson=new Gson();
			String json = gson.toJson(listPersonal.get(cp));
			contr=1;
			new MiTareaPersonal("http://pruebas.akc.co:8087/FormatosSO/formatos/personalFormato",json).execute();
		}else{
			visibleComponente();
			Toast t=Toast.makeText(this, "No hay datos para subir", Toast.LENGTH_SHORT);
			t.show();
		}
	}
	
	public void subirDiligenciar(String retur){
		cp++;
		if(cp<listPersonal.size()){
			Gson gson=new Gson();
			String json = gson.toJson(listPersonal.get(cp));
			new MiTareaPersonal("http://pruebas.akc.co:8087/FormatosSO/formatos/personalFormato",json).execute();
		}else{
			BDDato bdDato=new BDDato(this);
			bdDato.abrir();
			try {
				bdDato.eliminarPersonal();
				listDiligenciar=bdDato.consultarDIligenciar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bdDato.cerrar();
			cp=0;
			if(listDiligenciar.size()>0){
				Gson gson=new Gson();
				String json = gson.toJson(listDiligenciar.get(cp));
				contr=2;
				new MiTareaPersonal("http://pruebas.akc.co:8087/FormatosSO/formatos/nuevoFormRespuesta",json).execute();
			}else{
				datosSubido();
			}
		}
	}
	
	public void subirRespuesta(String retur){
		cp++;
		if(cp<listDiligenciar.size()){
			Gson gson=new Gson();
			String json = gson.toJson(listDiligenciar.get(cp));
			new MiTareaPersonal("http://pruebas.akc.co:8087/FormatosSO/formatos/nuevoFormRespuesta",json).execute();
		}else{
			BDDato bdDato=new BDDato(this);
			bdDato.abrir();
			try {
				bdDato.eliminarDiligenciar();
				listRespuesta=bdDato.consultarRespuesta();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bdDato.cerrar();
			if(listRespuesta.size()>0){
				Gson gson=new Gson();
				String json = gson.toJson(listRespuesta);
				contr=3;
				new MiTareaPersonal("http://pruebas.akc.co:8087/FormatosSO/formatos/nuevasRespuestas",json).execute();
			}else{
				datosSubido();
			}
		}
	}
	
	public void finalizarSubida(String retur){
		BDDato bdDato=new BDDato(this);
		bdDato.abrir();
		try {
			bdDato.eliminarRespuesta();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bdDato.cerrar();
		datosSubido();
	}
	public void datosSubido(){
		Toast t=Toast.makeText(this,"Datos subidos", Toast.LENGTH_SHORT);
		t.show();
		visibleComponente();
	}
		
	public void descargarCN(){
		BDDato bdDato=new BDDato(this);
		DatosCombo datosCombo=new DatosCombo();
		List<DatosCombo> datosCombos=datosCombo.getListDatosCombo(tiraJsonCN);
		bdDato.abrir();
		bdDato.eliminarCentroNegocio();
		for(int x=0;x<datosCombos.size();x++){
			
			try {
				bdDato.registrarCentroNegocio(datosCombos.get(x));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bdDato.cerrar();
		control=1;
		new MiTarea(InicioActivity.this,"http://pruebas.akc.co:8087/FormatosSO/formatos/listarFormatosAll").execute();
	}
	
	public void descargarDatosFormato(String datos){
		
		JsonParser parser = new JsonParser();
		Object obje = parser.parse(datos);
		JsonArray array=(JsonArray)obje;
		BDDato bdDato=new BDDato(this);
		bdDato.abrir();
		bdDato.eliminarPregunta();
		bdDato.eliminarOpcion();
		for(int x=0;x<array.size();x++){
			
			JsonObject obj=array.get(x).getAsJsonObject();
			PreguntaFormato preguntaFormato=new PreguntaFormato();
			int r=obj.get("formindx").getAsInt();
			List<PreguntaFormato> listaPregunta=preguntaFormato.getLista(obj.toString());
			tvCN.setText(String.valueOf(listaPregunta.size()));
			for(int y=0;y<listaPregunta.size();y++){
				try {
					long regi=bdDato.registrarPreg(listaPregunta.get(y), r);
					if(regi>0){
						for(int i=0;i<listaPregunta.get(y).getOpciones().size();i++){
							regi=bdDato.registrarOpcion(listaPregunta.get(y).getOpciones().get(i), listaPregunta.get(y).getId());
						}
					}					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		bdDato.cerrar();
		Toast t =Toast.makeText(this, "descargar Terminada", Toast.LENGTH_SHORT);
		t.show();
		visibleComponente();
	}
	
	public void descargarFormato(String tiraJson){
		
		BDDato bdDato=new BDDato(this);
		DatosFormato datosCombo=new DatosFormato();
		List<DatosFormato> datosCombos=datosCombo.getListDatosFormato(tiraJson);
		bdDato.abrir();
		bdDato.eliminarFormato();
		for(int x=0;x<datosCombos.size();x++){
			
			try {
				bdDato.registrarFormato(datosCombos.get(x));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bdDato.cerrar();
		control=2;
		new MiTarea(InicioActivity.this,"http://pruebas.akc.co:8087/FormatosSO/formatos/datosPorFormatoSinc").execute();
	}
	
	private class MiTarea extends AsyncTask<String, Float, String>{
		 private String ur;
		 public MiTarea(Context c, String url){
			 this.ur=url;
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
        	if(control==0){
        		tiraJsonCN=tiraJson;
        		iniciar();
        	}else if(control==1){
        		descargarFormato(tiraJson);
        	}else if(control==2){
        		descargarDatosFormato(tiraJson);
        	}        	
        }
	}
	
	private class MiTareaPersonal extends AsyncTask<String, Float, String>{
		 private String jsonObject;
		 private final String HTTP_EVENT; 
		 private HttpClient httpclient; 
		 BufferedReader in = null;
		 
		 public MiTareaPersonal(String url,String jsonObject){
			 this.HTTP_EVENT=url;
			 this.jsonObject=jsonObject;
		 }
    protected void onPreExecute() {

     }

     protected String doInBackground(String... urls){
   	  String resul = "";
   	  try {		  
   		  
   		//Creamos un objeto Cliente HTTP para manejar la peticion al servidor
   			HttpClient httpClient = new DefaultHttpClient();
   			//Creamos objeto para armar peticion de tipo HTTP POST 
   			HttpPost post = new HttpPost(HTTP_EVENT);
   		 
   			//Configuramos los parametos que vaos a enviar con la peticion HTTP POST
   			StringEntity stringEntity = new StringEntity( jsonObject);
   			post.setHeader("Content-type", "application/json");
   			post.setEntity(stringEntity);
   				        
   			//Se ejecuta el envio de la peticion y se espera la respuesta de la misma. 
   			HttpResponse response = httpClient.execute(post);
//   			Log.w(APP_TAG, response.getStatusLine().toString());
   		 
   			//Obtengo el contenido de la respuesta en formato InputStream Buffer y la paso a formato String 
   			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
   			StringBuffer sb = new StringBuffer("");
   			String line = "";
   			String NL = System.getProperty("line.separator");
   			while ((line = in.readLine()) != null) {
   			  sb.append(line + NL);
   			}
   			resul=sb.toString();
   		  
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
         return resul;
     }

     protected void onProgressUpdate (Float... valores) {

     }

     protected void onPostExecute(String tiraJson) {
    	 if(contr==1){
    		 subirDiligenciar(tiraJson);
    	 }else if(contr==2){
    		 subirRespuesta(tiraJson);
    	 } else if(contr==3){
    		 finalizarSubida(tiraJson);
    	 }
     	
     }
     private StringBuilder inputStreamToString(InputStream is) {  
   	  String line = "";
   	  StringBuilder stringBuilder = new StringBuilder();
   	  BufferedReader rd = new BufferedReader( new InputStreamReader(is) );  
   	  try{
   		  while( (line = rd.readLine()) != null ){
   			  stringBuilder.append(line);
   		  }
   	  }catch( IOException e){
   		  e.printStackTrace(); 
   	  }
	  return stringBuilder;
	 }
	}
}
