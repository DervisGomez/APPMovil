package com.app.main;

import com.app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PantallaEleccion extends Activity implements OnClickListener{
	private Button btnLlenarN;
	private Button btnRegresarI;
	
	public PantallaEleccion() {
		// TODO Auto-generated constructor stub
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_eleccion);
		btnLlenarN=(Button)findViewById(R.id.btnLlenarN);
		btnRegresarI=(Button)findViewById(R.id.btnRegresarI);
		btnLlenarN.setOnClickListener(this);
		btnRegresarI.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.btnLlenarN:
			Intent intent =new Intent();
			intent.putExtra("cerrar_cuatro", 1);
			setResult(RESULT_OK,intent);
			finish();
			break;
		case R.id.btnRegresarI:
			Intent inten =new Intent();
			inten.putExtra("cerrar_cuatro", 2);
			setResult(RESULT_OK,inten);
			finish();
			break;
		}
		
	}

}
