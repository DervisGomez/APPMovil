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

import com.app.R;
import com.app.bd.BDDato;
import com.app.conexion.VerificarConexion;
import com.app.modelo.DatosCombo;
import com.app.modelo.PreguntaFormato;
import com.app.modelo.RespuestaPregunta;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FormatoDiligenciar extends Activity implements OnClickListener  {
	private static final int REQUES_CODE_CUATRO = 400;
	private TextView tvPregunta;
	private EditText etArea;
	private EditText etTexto;
	private EditText etNumero;
	private CheckBox cb0;
	private CheckBox cb1;
	private CheckBox cb2;
	private CheckBox cb3;
	private CheckBox cb4;
	private CheckBox cb5;
	private RadioGroup rgOpcion;
	private RadioButton rb0;
	private RadioButton rb1;
	private RadioButton rb2;
	private RadioButton rb3;
	private RadioButton rb4;
	private RadioButton rb5;
	private Spinner spOpcion;
	private Button btnSiguienteP;
	private TextView tvSiguienteP;
	private TextView tvPreguntas;
	private List<PreguntaFormato> listaPregunta=new ArrayList<PreguntaFormato>();
	private int preI;
	private int preF;
	private List<RespuestaPregunta> listRespuestas=new ArrayList<RespuestaPregunta>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formato_diligenciar);
		tvPregunta=(TextView)findViewById(R.id.tvPregunta);
		etArea=(EditText)findViewById(R.id.etArea);
		etTexto=(EditText)findViewById(R.id.etTexto);
		etNumero=(EditText)findViewById(R.id.etNumero);
		cb0=(CheckBox)findViewById(R.id.cb0);
		cb1=(CheckBox)findViewById(R.id.cb1);
		cb2=(CheckBox)findViewById(R.id.cb2);
		cb3=(CheckBox)findViewById(R.id.cb3);
		cb4=(CheckBox)findViewById(R.id.cb4);
		cb5=(CheckBox)findViewById(R.id.cb5);
		rgOpcion=(RadioGroup)findViewById(R.id.rgOpcion);
		rb0=(RadioButton)findViewById(R.id.rb0);
		rb1=(RadioButton)findViewById(R.id.rb1);
		rb2=(RadioButton)findViewById(R.id.rb2);
		rb3=(RadioButton)findViewById(R.id.rb3);
		rb4=(RadioButton)findViewById(R.id.rb4);
		rb5=(RadioButton)findViewById(R.id.rb5);
		spOpcion=(Spinner)findViewById(R.id.spOpcion);
		btnSiguienteP=(Button)findViewById(R.id.btnSiguienteP);
		tvSiguienteP=(TextView)findViewById(R.id.tvSiguienteP);
		tvPreguntas=(TextView)findViewById(R.id.tvPreguntas);
		btnSiguienteP.setOnClickListener(this);
		tvSiguienteP.setVisibility(View.INVISIBLE);
		btnSiguienteP.setVisibility(View.INVISIBLE);
		cb0.setVisibility(View.GONE);
		rb0.setVisibility(View.GONE);
		invisible();
		tvPreguntas.setText("");
		tvPregunta.setText("Cargando Datos");
		Bundle bolsa=getIntent().getExtras();
		int x=bolsa.getInt("format");
		VerificarConexion verificar=new VerificarConexion(this);
		if(verificar.estaConectado()){
			conConexion(x);
		}else{
			sinConexion(x);
			
		}
	}	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode==REQUES_CODE_CUATRO){
			if(resultCode==RESULT_OK){
				int c=data.getIntExtra("cerrar_cuatro", -1);
				if(c==1){
					finish();
				}else if(c==2){
					Intent inten =new Intent();
					inten.putExtra("cerrar_tres", 2);
					setResult(RESULT_OK,inten);
					finish();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	private void sinConexion(int x) {
    	BDDato bdDato=new BDDato(this);
    	bdDato.abrir();
    	try {
			listaPregunta=bdDato.consultarPreg(x);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	bdDato.cerrar();   	

		preI=0;
		preF=listaPregunta.size();
		if(preF>0){
			tvSiguienteP.setVisibility(View.VISIBLE);
			tvSiguienteP.setText("Siguiente Pregunta");
			btnSiguienteP.setVisibility(View.VISIBLE);
			mostrarPreguntas(listaPregunta.get(preI));
		}else{
			tvPreguntas.setText("No hay datos para mostrar");
		}
	}

	private void conConexion(int x) {
		new MiTarea(x).execute();
	}
	
	public void invisible(){
		etArea.setVisibility(View.GONE);
		etTexto.setVisibility(View.GONE);
		etNumero.setVisibility(View.GONE);
		cb1.setVisibility(View.GONE);
		cb2.setVisibility(View.GONE);
		cb3.setVisibility(View.GONE);
		cb4.setVisibility(View.GONE);
		cb5.setVisibility(View.GONE);
		rgOpcion.setVisibility(View.GONE);
		rb1.setVisibility(View.GONE);
		rb2.setVisibility(View.GONE);
		rb3.setVisibility(View.GONE);
		rb4.setVisibility(View.GONE);
		rb5.setVisibility(View.GONE);
		spOpcion.setVisibility(View.GONE);
	}
	
	public void iniciar(String datos){
		PreguntaFormato preguntaFormato=new PreguntaFormato();
		listaPregunta=preguntaFormato.getLista(datos);
		preI=0;
		preF=listaPregunta.size();
		if(preF>0){
			tvSiguienteP.setVisibility(View.VISIBLE);
			tvSiguienteP.setText("Siguiente Pregunta");
			btnSiguienteP.setVisibility(View.VISIBLE);
			mostrarPreguntas(listaPregunta.get(preI));
		}else{
			tvPreguntas.setText("No hay datos para mostrar");
		}
	}

	public void mostrarPreguntas(PreguntaFormato preguntaFormato) {
		invisible();
		tvPregunta.setText(preguntaFormato.getPregunta());
		switch (preguntaFormato.getTipo()){
		case 1:
			etArea.setVisibility(View.VISIBLE);
			break;
		case 2:
			int l=preguntaFormato.getOpciones().size();
			rgOpcion.setVisibility(View.VISIBLE);
			if(l>0){
				rb1.setVisibility(View.VISIBLE);
				rb0.setChecked(true);
				rb1.setText(preguntaFormato.getOpciones().get(0).getNombre());
				if(l>1){
					rb2.setVisibility(View.VISIBLE);
					rb2.setText(preguntaFormato.getOpciones().get(1).getNombre());
					if(l>2){
						rb3.setVisibility(View.VISIBLE);
						rb3.setText(preguntaFormato.getOpciones().get(2).getNombre());
						if(l>3){
							rb4.setVisibility(View.VISIBLE);
							rb4.setText(preguntaFormato.getOpciones().get(3).getNombre());
							if(l>4){
								rb5.setVisibility(View.VISIBLE);
								rb5.setText(preguntaFormato.getOpciones().get(4).getNombre());
							}
						}
					}
				}
			}
			break;
		case 3:
			int r=preguntaFormato.getOpciones().size();
			if(r>0){
				cb1.setVisibility(View.VISIBLE);
				cb0.setChecked(true);
				cb1.setText(preguntaFormato.getOpciones().get(0).getNombre());
				if(r>1){
					cb2.setVisibility(View.VISIBLE);
					cb2.setText(preguntaFormato.getOpciones().get(1).getNombre());
					if(r>2){
						cb3.setVisibility(View.VISIBLE);
						cb3.setText(preguntaFormato.getOpciones().get(2).getNombre());
						if(r>3){
							cb4.setVisibility(View.VISIBLE);
							cb4.setText(preguntaFormato.getOpciones().get(3).getNombre());
							if(r>4){
								cb5.setVisibility(View.VISIBLE);
								cb5.setText(preguntaFormato.getOpciones().get(4).getNombre());
							}
						}
					}
				}
			}
			break;
		case 4:
			spOpcion.setVisibility(View.VISIBLE);
			ArrayAdapter<String> spFormatoAdaptener=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getStringCN(preguntaFormato.getOpciones()));
			spOpcion.setAdapter(spFormatoAdaptener);
			break;
		case 5:
			etTexto.setVisibility(View.VISIBLE);
			break;
		case 6:
			etNumero.setVisibility(View.VISIBLE);
			break;
		}
		preI++;
		tvPreguntas.setText(String.valueOf(preI)+"/"+String.valueOf(preF));
	}

	public String[] getStringCN(List<DatosCombo> datosCombo) {
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
		case R.id.btnSiguienteP:
			if(validarRespuesta(listaPregunta.get(preI-1))){
				if(preI<preF){
					leerRespuesta();
					mostrarPreguntas(listaPregunta.get(preI));
				}else{
					leerRespuesta();
					Toast t=Toast.makeText(this, "No hay más preguntas", Toast.LENGTH_SHORT);
					t.show();
					tvSiguienteP.setVisibility(View.INVISIBLE);
					btnSiguienteP.setVisibility(View.INVISIBLE);
					invisible();
					tvPreguntas.setText("");
					tvPregunta.setText("Guardando Datos");
					VerificarConexion verificar=new VerificarConexion(this);
					if(verificar.estaConectado()){
						conConexion();
					}else{
						sinConexion();
					}
				}
			}else{
				Toast t=Toast.makeText(this, "Debe ingresar una respuesta", Toast.LENGTH_SHORT);
				t.show();
			}			
			break;
		}
	}
	
	public boolean validarRespuesta(PreguntaFormato preguntaFormato){
		switch (preguntaFormato.getTipo()){
		case 1:
			if(etArea.length()>0){
				return true;
			}else{
				return false;
			}
		case 2:
			if(rb1.isChecked()){
				return true;
			}else if (rb2.isChecked()){
				return true;
			}else if (rb3.isChecked()){
				return true;
			}else if (rb4.isChecked()){
				return true;
			}else if (rb5.isChecked()){
				return true;
			}else{
				return false;
			}
		case 3:
			if(cb1.isChecked()){
				return true;
			}else if (cb2.isChecked()){
				return true;
			}else if (cb3.isChecked()){
				return true;
			}else if (cb4.isChecked()){
				return true;
			}else if (cb5.isChecked()){
				return true;
			}else{
				return false;
			}
		case 4:
			if(spOpcion.getSelectedItemPosition()>-1){
				return true;
			}else{
				return false;
			}
		case 5:
			if(etTexto.length()>0){
				return true;
			}else{
				return false;
			}
		case 6:
			if(etNumero.length()>0){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	public void leerRespuesta(){
		Bundle bolsa=getIntent().getExtras();
		String resppreg=String.valueOf(listaPregunta.get(preI-1).getId());
		String resofore=bolsa.getString("resofore");
		String respucre=bolsa.getString("nombre");
		
		if(listaPregunta.get(preI-1).getTipo()==3){
			List<String> respresp=respuesta2(listaPregunta.get(preI-1));
			for(int x=0;x<respresp.size();x++){
				RespuestaPregunta respuestaPregunta=new RespuestaPregunta(resppreg,resofore,respucre,respresp.get(x));
				listRespuestas.add(respuestaPregunta);
			}
		}else{
			String respresp=respuesta(listaPregunta.get(preI-1));
			RespuestaPregunta respuestaPregunta=new RespuestaPregunta(resppreg,resofore,respucre,respresp);
			listRespuestas.add(respuestaPregunta);
		}	
	}
	
	public void conConexion(){
		Gson gson=new Gson();
		String jsonObject=gson.toJson(listRespuestas);
		tvPregunta.setText("Guardando Datos");
//		tvPregunta.setText(jsonObject);
		new MiTarea2("http://pruebas.akc.co:8087/FormatosSO/formatos/nuevasRespuestas",jsonObject).execute();
	}
	
	public void guardarRespuesta(){
		BDDato bdDato=new BDDato(this);
		bdDato.abrir();
		for(int x=0;x<listRespuestas.size();x++){
			
			try {
				bdDato.registrarPregunta(listRespuestas.get(x));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bdDato.cerrar();
	}
	
	public void sinConexion(){
		tvPregunta.setText("Guardando Datos");
		guardarRespuesta();
		Intent int1=new Intent("com.app.main.PantallaEleccion");
		startActivityForResult(int1,REQUES_CODE_CUATRO);
	}
	
	public List<String> respuesta2(PreguntaFormato preguntaFormato){
		List<String> lista=new ArrayList<String>();
		if(cb1.isChecked()){
			lista.add(String.valueOf(preguntaFormato.getOpciones().get(0).getId()));
		}if (cb2.isChecked()){
			lista.add(String.valueOf(preguntaFormato.getOpciones().get(1).getId()));
		}if (cb3.isChecked()){
			lista.add(String.valueOf(preguntaFormato.getOpciones().get(2).getId()));
		}if (cb4.isChecked()){
			lista.add(String.valueOf(preguntaFormato.getOpciones().get(3).getId()));;
		}if (cb5.isChecked()){
			lista.add(String.valueOf(preguntaFormato.getOpciones().get(4).getId()));
		}		
		return lista;
	}
	
	public String respuesta(PreguntaFormato preguntaFormato){
		tvPregunta.setText(preguntaFormato.getPregunta());
		switch (preguntaFormato.getTipo()){
		case 1:
			return etArea.getText().toString();
		case 2:
			if(rb1.isChecked()){
				return String.valueOf(preguntaFormato.getOpciones().get(0).getId());
			}else if (rb2.isChecked()){
				return String.valueOf(preguntaFormato.getOpciones().get(1).getId());
			}else if (rb3.isChecked()){
				return String.valueOf(preguntaFormato.getOpciones().get(2).getId());
			}else if (rb4.isChecked()){
				return String.valueOf(preguntaFormato.getOpciones().get(3).getId());
			}else if (rb5.isChecked()){
				return String.valueOf(preguntaFormato.getOpciones().get(4).getId());
			}
			break;
		case 3:
			
			if(cb1.isChecked()){
				return String.valueOf(preguntaFormato.getOpciones().get(0).getId());
			}else if (cb2.isChecked()){
				return String.valueOf(preguntaFormato.getOpciones().get(1).getId());
			}else if (cb3.isChecked()){
				return String.valueOf(preguntaFormato.getOpciones().get(2).getId());
			}else if (cb4.isChecked()){
				return String.valueOf(preguntaFormato.getOpciones().get(3).getId());
			}else if (cb5.isChecked()){
				return String.valueOf(preguntaFormato.getOpciones().get(4).getId());
			}
			break;
		case 4:
			return String.valueOf(preguntaFormato.getOpciones().get(spOpcion.getSelectedItemPosition()).getId());
		case 5:
			return etTexto.getText().toString();
		case 6:
			return etNumero.getText().toString();
		}
		return"";
	}
	
	public void guardar(String tiraJson){
		JsonParser parser = new JsonParser();
		Object obje = parser.parse(tiraJson);
 		JsonObject array=(JsonObject)obje;
 		String r=array.get("respuesta").getAsString();
 		if(r.equals("S")){
			Intent int1=new Intent("com.app.main.PantallaEleccion");
			startActivityForResult(int1,REQUES_CODE_CUATRO);
 		}else{
 			tvPregunta.setText("Ha ocurrido un error datos no guardados");
 		}
		
	}
	
	private class MiTarea extends AsyncTask<String, Float, String>{
		 private String ur;

		 public MiTarea(int x){
			 this.ur="http://pruebas.akc.co:8087/FormatosSO/formatos/datosPorFormato/"+String.valueOf(x);
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
		 private String jsonObject;
		 private final String HTTP_EVENT; 
		 private HttpClient httpclient; 
		 BufferedReader in = null;
		 
		 public MiTarea2(String url,String jsonObject){
			 this.HTTP_EVENT=url;
			 this.jsonObject=jsonObject;
		 }
    protected void onPreExecute() {

     }

     protected String doInBackground(String... urls){
   	  String resul = "";
   	  try {		  
   		  HttpClient httpClient = new DefaultHttpClient();
   		HttpPost post = new HttpPost(HTTP_EVENT);
   		StringEntity stringEntity = new StringEntity( jsonObject);
   		post.setHeader("Content-type", "application/json");
   		post.setEntity(stringEntity); 
   		HttpResponse response = httpClient.execute(post);
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
