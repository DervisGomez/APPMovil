package com.app.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.R;
import com.app.bd.BDDato;
import com.app.conexion.VerificarConexion;
import com.app.modelo.DatosFormato;
import com.app.modelo.Diligenciar;
import com.app.modelo.Personal;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BuscarUsuario extends Activity implements OnClickListener {
	private static final int REQUES_CODE_TRES = 300;
	private EditText etBuscar;
	private Button btnBuscar;
	private TextView tvCedula;
	private TextView tvNombre;
	private TextView tvCentro;
	private TextView tvCargo;
	private TextView tvCedula2;
	private EditText etNombre;
	private EditText etNombre2;
	private EditText etCentro;
	private EditText etCargo;
	private EditText etCed;
	private TextView tvSiguiente;
	private Button btnDiligenciar;
	private Button btnRegresar;
	private LinearLayout llBuscar;
	private LinearLayout llMostrar;
	private LinearLayout llFormulario;
	private String cedula;
	private String nombre;
	private boolean encontrado;
	private int xx;
	private boolean diligenciar;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buscar_usuario);
		etBuscar=(EditText)findViewById(R.id.etBuscar);
		btnBuscar=(Button)findViewById(R.id.btnBuscar);
		tvCedula=(TextView)findViewById(R.id.tvCedula);
		tvNombre=(TextView)findViewById(R.id.tvNombre);
		tvCentro=(TextView)findViewById(R.id.tvCentro);
		tvCargo=(TextView)findViewById(R.id.tvCargo);
		tvCedula2=(TextView)findViewById(R.id.tvCedula2);
		etNombre=(EditText)findViewById(R.id.etNombre);
		etNombre2=(EditText)findViewById(R.id.etNombre2);
		etCentro=(EditText)findViewById(R.id.etCentro);
		etCargo=(EditText)findViewById(R.id.etCargo);
		etCed=(EditText)findViewById(R.id.etCed);
		tvSiguiente=(TextView)findViewById(R.id.tvSiguiente);
		btnDiligenciar=(Button)findViewById(R.id.btnEmpezarDiligenciar);
		btnRegresar=(Button)findViewById(R.id.btnRegresarBU);
		llMostrar=(LinearLayout)findViewById(R.id.llMostrar);
		llFormulario=(LinearLayout)findViewById(R.id.llFormulario);
		llBuscar=(LinearLayout)findViewById(R.id.llBuscar);
		llMostrar.setVisibility(View.GONE);
		etNombre2.setVisibility(View.GONE);
		btnRegresar.setOnClickListener(this);
		btnBuscar.setOnClickListener(this);
		btnDiligenciar.setOnClickListener(this);
		diligenciar=true;
		VerificarConexion verificar=new VerificarConexion(this);
		if(verificar.estaConectado()){
			btnDiligenciar.setVisibility(View.INVISIBLE);
			tvSiguiente.setVisibility(View.INVISIBLE);
			llFormulario.setVisibility(View.GONE);
			etCed.setVisibility(View.GONE);
		}else{
			etCed.setVisibility(View.VISIBLE);
			llBuscar.setVisibility(View.GONE);
			tvCedula2.setVisibility(View.GONE);
			etNombre2.setVisibility(View.VISIBLE);
			etNombre.setVisibility(View.GONE);
			iniciarFormulario();
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode==REQUES_CODE_TRES){
			if(resultCode==RESULT_OK){
				
				int c=data.getIntExtra("cerrar_tres", -1);
				if(c==2){
					Intent inten =new Intent();
					inten.putExtra("cerrar_dos", 2);
					setResult(RESULT_OK,inten);
					finish();
				}else{
					
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	public void iniciar(String datos){
		JsonParser parser = new JsonParser();
		Object obje = parser.parse(datos);
 		JsonObject array=(JsonObject)obje;
 		String r=array.get("resultado").getAsString();
 		diligenciar=true;
 		if (r.equals("S")){
 			JsonObject objeDatos=array.get("datos").getAsJsonObject();
 			mostrarDatos(objeDatos);
 		}else{
 			iniciarFormulario();
 		}
	}
	
	private void sinConexion() {
		
	}

	private void conConexion() {
		new MiTarea().execute();
	}
	
	public void iniciarFormulario(){
		tvCedula2.setText("Cedula: "+cedula);
		llFormulario.setVisibility(View.VISIBLE);
		btnDiligenciar.setVisibility(View.VISIBLE);
		llMostrar.setVisibility(View.GONE);
		tvSiguiente.setVisibility(View.VISIBLE);
		encontrado=false;
	}
	public void mostrarDatos(JsonObject objeDatos){
		tvCedula.setText("Cedula: "+cedula);
		nombre=objeDatos.get("nombre").getAsString();
		tvNombre.setText("Nombre: "+nombre);
		tvCentro.setText("Dependencia: "+objeDatos.get("dependencia").getAsString());
		tvCargo.setText("Cargo: "+objeDatos.get("cargo").getAsString());
		llMostrar.setVisibility(View.VISIBLE);
		btnDiligenciar.setVisibility(View.VISIBLE);
		llFormulario.setVisibility(View.GONE);
		tvSiguiente.setVisibility(View.VISIBLE);
		encontrado=true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(diligenciar){
			switch (v.getId()){
			case R.id.btnBuscar:
				diligenciar=false;
				cedula=etBuscar.getText().toString();
				if(cedula.equals("")){
					Toast t=Toast.makeText(this, "Introducir Cedula", Toast.LENGTH_SHORT);
					t.show();
				}else{
					VerificarConexion verificar=new VerificarConexion(this);
					if(verificar.estaConectado()){
						conConexion();
					}else{
						sinConexion();
					}
				}
				break;
			case R.id.btnRegresarBU:
				finish();
				break;
			case R.id.btnEmpezarDiligenciar:
					diligenciar=false;
					Bundle bolsa=getIntent().getExtras();
					xx=bolsa.getInt("formato");
					VerificarConexion verificar=new VerificarConexion(this);
					if(verificar.estaConectado()){
						conConexionDiligenciar();
					}else{
						sinConexionDiligenciar();
					}			
				break;
			}
		}else{
			Toast t=Toast.makeText(this, "Hay un acción en proceso", Toast.LENGTH_SHORT);
			t.show();
		}
	}
	
	public void sinConexionDiligenciar(){
		String cedu=etCed.getText().toString();
		nombre=etNombre2.getText().toString();
		String centro=etCentro.getText().toString();
		String cargo=etCargo.getText().toString();
		if(nombre.length()>0&&centro.length()>0&&cargo.length()>0&&cedu.length()>0){
			Personal personal=new Personal(nombre,centro,cargo,cedu,xx);
			BDDato bdDato=new BDDato(this);
			bdDato.abrir();
			try {
				long r=bdDato.registrarPersonal(personal);
				if(r>0){
					String id= bdDato.consultaSecuencia();
					if (id.equals("")){
						id="1";
					}else{
						id=String.valueOf(Integer.valueOf(id)+1);
					}
					Diligenciar digenciar= new Diligenciar(id,xx,cedu,nombre);
					r=bdDato.registrarDiligencia(digenciar);
					if(r>0){
						Toast t=Toast.makeText(this, "Datos Guardados", Toast.LENGTH_SHORT);
						t.show();
						bdDato.eliminarSecuencia();
						r=bdDato.registrarSecuencia(id);	
						Bundle bols=new Bundle();
						bols.putInt("format",xx);
						bols.putString("nombre", nombre);
						bols.putString("resofore", id);
						Intent intent=new Intent("com.app.main.FormatoDiligenciar");
						intent.putExtras(bols);
						diligenciar=true;
						startActivityForResult(intent,REQUES_CODE_TRES);
					}else{
						Toast t=Toast.makeText(this, "Error al guardar datos", Toast.LENGTH_SHORT);
						t.show();
					}
				}else{
					Toast t=Toast.makeText(this, "Error al guardar datos", Toast.LENGTH_SHORT);
					t.show();
					
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
			bdDato.cerrar();
		}else{
			Toast t=Toast.makeText(this, "Llener datos", Toast.LENGTH_SHORT);
			t.show();
			diligenciar=true;
		}
	}
	
	public void conConexionDiligenciar(){
		JSONObject jsonObject = new JSONObject();
		if(encontrado){
	    	try {
	    		jsonObject.put("formulario", xx);
		    	jsonObject.put("usuario", cedula );
				jsonObject.put("nombre", nombre);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	new MiTarea2("http://pruebas.akc.co:8087/FormatosSO/formatos/nuevoFormRespuesta",jsonObject).execute();
		}else{
			nombre=etNombre.getText().toString();
			String centro=etCentro.getText().toString();
			String cargo=etCargo.getText().toString();
			if(nombre.length()>0&&centro.length()>0&&cargo.length()>0){
				try {
					jsonObject.put("nombre",nombre );
					jsonObject.put("dependencia", centro);
					jsonObject.put("cargo", cargo);
					jsonObject.put("cedula", cedula);
					jsonObject.put("formresp", xx);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new MiTarea2("http://pruebas.akc.co:8087/FormatosSO/formatos/personalFormato",jsonObject).execute();
			
			}else{
				Toast t=Toast.makeText(this, "Llener datos", Toast.LENGTH_SHORT);
				t.show();
				diligenciar=true;
			}
		}
	}
	
	public void guardar(String dato){
		if(encontrado){
			JsonParser parser = new JsonParser();
			Object obje = parser.parse(dato);
	 		JsonObject array=(JsonObject)obje;
	 		String r=array.get("id").getAsString();
	 		
	 		BDDato bdDato=new BDDato(this);
	 		bdDato.abrir();
			bdDato.eliminarSecuencia();
			bdDato.registrarSecuencia(r);
			bdDato.cerrar();
	 		
			Bundle bolsa=new Bundle();
			bolsa.putInt("format",xx);
			bolsa.putString("nombre", nombre);
			bolsa.putString("resofore", r);
			Intent intent=new Intent("com.app.main.FormatoDiligenciar");
			intent.putExtras(bolsa);
			diligenciar=true;
			startActivityForResult(intent,REQUES_CODE_TRES);
		}else{
			JSONObject jsonObject = new JSONObject();
	    	try {
	    		jsonObject.put("formulario", xx );
		    	jsonObject.put("usuario", cedula );
				jsonObject.put("nombre", nombre);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	new MiTarea2("http://pruebas.akc.co:8087/FormatosSO/formatos/nuevoFormRespuesta",jsonObject).execute();
	    	encontrado=true;
		}
	}
	
	
	private class MiTarea extends AsyncTask<String, Float, String>{
		 private String ur;
		 public MiTarea(){
			 this.ur="http://pruebas.akc.co:8087/FormatosSO/formatos/datosCedula/"+cedula;
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

	private class MiTarea2 extends AsyncTask<String, Float, String>{
		 private JSONObject jsonObject;
		 private final String HTTP_EVENT; 
		 private HttpClient httpclient; 
		 BufferedReader in = null;
		 
		 public MiTarea2(String url,JSONObject jsonObject){
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
    			StringEntity stringEntity = new StringEntity( jsonObject.toString());
    			post.setHeader("Content-type", "application/json");
    			post.setEntity(stringEntity);
    				        
    			//Se ejecuta el envio de la peticion y se espera la respuesta de la misma. 
    			HttpResponse response = httpClient.execute(post);
//    			Log.w(APP_TAG, response.getStatusLine().toString());
    		 
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
      	guardar(tiraJson);
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
