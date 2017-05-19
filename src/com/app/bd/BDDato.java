package com.app.bd;

import java.util.ArrayList;
import java.util.List;

import com.app.modelo.DatosCombo;
import com.app.modelo.DatosFormato;
import com.app.modelo.Diligenciar;
import com.app.modelo.Personal;
import com.app.modelo.PreguntaFormato;
import com.app.modelo.Respuesta;
import com.app.modelo.RespuestaPregunta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDDato extends SQLiteOpenHelper{
	
	Context ctx;
	BDDato bdDato;
	SQLiteDatabase bd;

	public BDDato(Context context) {
		super(context, "dato", null, 5);
		// TODO Auto-generated constructor stub
		ctx=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE centronegocio(id_centronegocio INTEGER PRIMARY KEY, nombre TEXT NOT NULL)");
		db.execSQL("CREATE TABLE formato(id_formato INTEGER PRIMARY KEY, descripcion TEXT NOT NULL, sigla TEXT, centronegocio INTEGER NOT NULL)");
		db.execSQL("CREATE TABLE respuesta(resppreg TEXT NOT NULL, resofere TEXT NOT NULL, respucre TEXT NOT NULL, respresp TEXT NOT NULL)");
		db.execSQL("CREATE TABLE diligenciar(id_diligenciar TEXT PRIMARY KEY, formulario INTEGER NOT NULL, usuario TEXT NOT NULL, nombre TEXT NOT NULL)");
		db.execSQL("CREATE TABLE personal(nombre TEXT NOT NULL, dependencia TEXT NOT NULL, cargo TEXT NOT NULL, cedula TEXT NOT NULL, formresp INTEGER NOT NULL)");
		db.execSQL("CREATE TABLE secuencia(ultimo TEXT NOT NULL)");
		db.execSQL("CREATE TABLE pregunta(pregindx INTEGER PRIMARY KEY, pregdesc TEXT NOT NULL, pregtico TEXT NOT NULL, formindx INTEGER)");
		db.execSQL("CREATE TABLE opcion(opciindx INTEGER PRIMARY KEY, opcidesc TEXT NOT NULL, pregunta INTEGER NOT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS centronegocio");
		db.execSQL("DROP TABLE IF EXISTS formato");
		db.execSQL("DROP TABLE IF EXISTS respuesta");
		db.execSQL("DROP TABLE IF EXISTS diligenciar");
		db.execSQL("DROP TABLE IF EXISTS personal");
		db.execSQL("DROP TABLE IF EXISTS secuencia");
		db.execSQL("DROP TABLE IF EXISTS pregunta");
		db.execSQL("DROP TABLE IF EXISTS opcion");
		onCreate(db);
	}
	
	public void abrir(){
		bdDato=new BDDato(ctx);
		bd=bdDato.getWritableDatabase();
	}
	
	public void cerrar(){
		bd.close();
	}
	
	public long registrarDiligencia(Diligenciar dili){
		ContentValues valores=new ContentValues();
		valores.put("id_diligenciar", dili.getId());
		valores.put("formulario", dili.getFormulario());
		valores.put("usuario", dili.getUsuario());
		valores.put("nombre", dili.getNombre());
		return bd.insert("diligenciar", null, valores);
	}
	
	public long registrarPreg(PreguntaFormato preguntaFormato,int r){
		ContentValues valores=new ContentValues();
		valores.put("pregindx", preguntaFormato.getId());
		valores.put("pregdesc", preguntaFormato.getPregunta());
		valores.put("pregtico", preguntaFormato.getTipo());
		valores.put("formindx", r);
		return bd.insert("pregunta", null, valores);
	}
	
	public long registrarOpcion(DatosCombo d,int r){
		ContentValues valores=new ContentValues();
		valores.put("opciindx", d.getId());
		valores.put("opcidesc", d.getNombre());
		valores.put("pregunta", r);
		return bd.insert("opcion", null, valores);
	}
	
	public long registrarPersonal(Personal persona){
		ContentValues valores=new ContentValues();
		valores.put("nombre", persona.getNombre());
		valores.put("dependencia", persona.getDependencia());
		valores.put("cargo", persona.getCargo());
		valores.put("cedula", persona.getCedula());
		valores.put("formresp", persona.getFormresp());
		return bd.insert("personal", null, valores);
	}
	
	public long registrarSecuencia(String secuencia){
		ContentValues valores=new ContentValues();
		valores.put("ultimo", secuencia);
		return bd.insert("secuencia", null, valores);
	}
	
	public long registrarPregunta(RespuestaPregunta respuesta){
		ContentValues valores=new ContentValues();
		valores.put("resppreg", respuesta.getResppreg());
		valores.put("resofere", respuesta.getResofore());
		valores.put("respucre", respuesta.getRespucre());
		valores.put("respresp", respuesta.getRespresp());
		return bd.insert("respuesta", null, valores);
	}

	public long registrarCentroNegocio(DatosCombo datosCombo) throws Exception{
		ContentValues valores=new ContentValues();
		valores.put("id_centronegocio", datosCombo.getId());
		valores.put("nombre", datosCombo.getNombre());
		return bd.insert("centronegocio", null, valores);
	}
	
	public List<DatosCombo> consultarCentroNegocio() throws Exception{
		List<DatosCombo> datosCombos=new ArrayList<DatosCombo>();
		String[] columns=new String[]{"id_centronegocio","nombre"};
		Cursor c=bd.query(true, "centronegocio", columns,null, null,null, null, null, null);
		if(c.moveToFirst()){
			do{
				int codigo=c.getInt(0);
				String descrip=c.getString(1);
				DatosCombo datosCombo=new DatosCombo(codigo,descrip);
				datosCombos.add(datosCombo);
				}while(c.moveToNext());
		}
		return datosCombos;
	}
	
	public long registrarFormato(DatosFormato datosCombo) throws Exception{
		ContentValues valores=new ContentValues();
		valores.put("id_formato", datosCombo.getId());
		valores.put("descripcion", datosCombo.getDescripcion());
		valores.put("sigla", datosCombo.getSigla());
		valores.put("centronegocio", datosCombo.getCentronNegocio());
		return bd.insert("formato", null, valores);
	}
	
	public List<Personal> consultarPersonal() throws Exception{
		List<Personal> datosCombos=new ArrayList<Personal>();
		String[] columns=new String[]{"nombre","dependencia","cargo","cedula","formresp"};
		Cursor c=bd.query(true, "personal", columns,null, null,null, null, null, null);
		if(c.moveToFirst()){
			do{
				String nombre=c.getString(0);
				String dependencia=c.getString(1);
				String cargo=c.getString(2);
				String cedula=c.getString(3);
				int formresp=c.getInt(4);
				Personal datosCombo=new Personal(nombre,dependencia,cargo,cedula,formresp);
				datosCombos.add(datosCombo);
				}while(c.moveToNext());
		}
		return datosCombos;
	}
	
	public List<Respuesta> consultarRespuesta() throws Exception{
		List<Respuesta> datosCombos=new ArrayList<Respuesta>();
		String[] columns=new String[]{"resppreg","resofere","respucre","respresp"};
		Cursor c=bd.query(true, "respuesta", columns,null, null,null, null, null, null);
		if(c.moveToFirst()){
			do{
				String resppreg=c.getString(0);
				String resofere=c.getString(1);
				String respucre=c.getString(2);
				String respresp=c.getString(3);
				Respuesta datosCombo=new Respuesta(resppreg,resofere,respucre,respresp);
				datosCombos.add(datosCombo);
				}while(c.moveToNext());
		}
		return datosCombos;
	}
	
	public List<Diligenciar> consultarDIligenciar() throws Exception{
		List<Diligenciar> datosCombos=new ArrayList<Diligenciar>();
		String[] columns=new String[]{"id_diligenciar","formulario","usuario","nombre"};
		Cursor c=bd.query(true, "diligenciar", columns,null, null,null, null, null, null);
		if(c.moveToFirst()){
			do{
				String id=c.getString(0);
				int formulario=c.getInt(1);
				String usuario=c.getString(2);
				String nombre=c.getString(3);
				Diligenciar datosCombo=new Diligenciar(id,formulario,usuario,nombre);
				datosCombos.add(datosCombo);
				}while(c.moveToNext());
		}
		return datosCombos;
	}
	
	public List<DatosFormato> consultarFormato(int cn) throws Exception{
		List<DatosFormato> datosCombos=new ArrayList<DatosFormato>();
		String[] columns=new String[]{"id_formato","descripcion","sigla","centronegocio"};
		String[] where=new String[]{String.valueOf(cn)};
		Cursor c=bd.query(true, "formato", columns,"centronegocio=?", where,null, null, null, null);
		if(c.moveToFirst()){
			do{
				int codigo=c.getInt(0);
				String descrip=c.getString(1);
				String sigla=c.getString(2);
				int centroNegocio=c.getInt(3);
				DatosFormato datosCombo=new DatosFormato(codigo,descrip,sigla,centroNegocio);
				datosCombos.add(datosCombo);
				}while(c.moveToNext());
		}
		return datosCombos;
	}
	
	public List<DatosCombo> consultarFormatoDC(int cn) throws Exception{
		List<DatosCombo> datosCombos=new ArrayList<DatosCombo>();
		String[] where=new String[]{String.valueOf(cn)};
		Cursor c=bd.rawQuery(" SELECT id_formato,descripcion FROM formato WHERE centronegocio=? ", where);
		if(c.moveToFirst()){
			do{
				int codigo=c.getInt(0);
				String descrip=c.getString(1);
				DatosCombo datosCombo=new DatosCombo(codigo,descrip);
				datosCombos.add(datosCombo);
				}while(c.moveToNext());
		}
		return datosCombos;
	}
	
	public List<PreguntaFormato> consultarPreg(int f) throws Exception{
		List<PreguntaFormato> datosCombos=new ArrayList<PreguntaFormato>();
		String[] where=new String[]{String.valueOf(f)};
		Cursor c=bd.rawQuery(" SELECT pregindx,pregdesc,pregtico,formindx FROM pregunta WHERE formindx=?",where);
		if(c.moveToFirst()){
			do{
				int codigo=c.getInt(0);
				String descrip=c.getString(1);
				String tipo=c.getString(2);
				List<DatosCombo> datosC=new ArrayList<DatosCombo>();
				datosC=consultarOpcion(codigo);
				PreguntaFormato datosCombo=new PreguntaFormato(codigo,descrip,Integer.valueOf(tipo),datosC);
				datosCombos.add(datosCombo);
				}while(c.moveToNext());
		}
		return datosCombos;
	}
	
	public List<DatosCombo> consultarOpcion(int p) throws Exception{
		List<DatosCombo> datosCombos=new ArrayList<DatosCombo>();
		String[] where=new String[]{String.valueOf(p)};
		Cursor c=bd.rawQuery(" SELECT opciindx, opcidesc FROM opcion WHERE pregunta=? ", where);
		if(c.moveToFirst()){
			do{
				int codigo=c.getInt(0);
				String descrip=c.getString(1);
				DatosCombo datosCombo=new DatosCombo(codigo,descrip);
				datosCombos.add(datosCombo);
				}while(c.moveToNext());
		}
		return datosCombos;
	}
	
	public String consultaSecuencia() throws Exception{
		String ultimo="";
		String[] columns=new String[]{"ultimo"};
		Cursor c=bd.query(true, "secuencia", columns,null, null,null, null, null, null);
		if(c.moveToFirst()){
			do{
				ultimo=c.getString(0);
				}while(c.moveToNext());
		}
		return ultimo;
	}
	
	public void eliminarCentroNegocio(){
		bd.execSQL("DELETE FROM centronegocio");
	}
	
	public void eliminarFormato(){
		bd.execSQL("DELETE FROM formato");
	}
	
	public void eliminarSecuencia(){
		bd.execSQL("DELETE FROM secuencia");
	}
	
	public void eliminarPersonal(){
		bd.execSQL("DELETE FROM personal");
	}
	
	public void eliminarDiligenciar(){
		bd.execSQL("DELETE FROM diligenciar");
	}
	
	public void eliminarPregunta(){
		bd.execSQL("DELETE FROM pregunta");
	}
	public void eliminarOpcion(){
		bd.execSQL("DELETE FROM opcion");
	}
	public void eliminarRespuesta(){
		bd.execSQL("DELETE FROM respuesta");
	}
}
