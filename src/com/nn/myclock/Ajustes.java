package com.nn.myclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ajustes extends Activity {

	Button bok, bcan;
	EditText getHora;
	Intent i;
	int hora, minuto, segundo;
	String [] parseInt;
	String aux;
	String horaParse;
	boolean isOK;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajustes);
		
		bok = (Button) findViewById(R.id.bOK);
		bcan = (Button) findViewById(R.id.bCancel);
		getHora = (EditText) findViewById(R.id.eHora);
	}
	
	
	
	/**
	 * Este metodo es lo mas parecido al main en concepto de que el inicia
	 * todo el programa en si a excepcion de la llamada de las vistas y/o 
	 * componentes del programa
	 * */
	private void inicializar(){
		aux = getHora.getText().toString();
		if(aux.equals("")){
			mensaje("Ingresa el ajuste de la hora", 2);
		}else{
			parsear(aux);
			if(comprobarNum(hora, minuto, segundo)){
				i = new Intent();
				i.putExtra(S.HORA, hora);
				i.putExtra(S.MINUTO, minuto);
				i.putExtra(S.SEGUNDO, segundo);
				i.putExtra(S.IS_UPDATE_TIME, isOK);
				this.setResult(S.AJUSTES, i);
				this.finish();
			}
		}
	}
	
	/**
	 * Este metodo comprueba que la hora ingresada es correcta y hace el retorno de falso 
	 * oh verdadero, es decir no hace nada oh es cero oh es uno
	 * @param n1: es la hora
	 * @param n2: los minitos
	 * @param n3: los segundos
	 * @return
	 */
	private boolean comprobarNum(int n1, int n2, int n3){
		horaParse = ""+n1+":"+n2+":"+n3;
		if(n1 >= 0 & n1 <= 24){
			if(n2 >= 0 & n2 <= 59){
				if(n3 >= 0 & n3 <= 59){
					isOK = true;
				}else{
					getHora.setText(horaParse);
					mensaje("Segundos no validos", 1);
					isOK=false;
				}
			}else{
				getHora.setText(horaParse);
				mensaje("Minutos no validos", 1);
				//isOK=false;
			}
		}else{
			getHora.setText(horaParse);
			mensaje("Hora no valida", 1);
			//isOK=false;
		}
		return isOK;
	}
	
	/**
	 * El metodo parsear simplemente convierte la cadena que obtenemos del 
	 * EditText y lo pasa a las variables de hora, minuto y segundo
	 * @param a
	 */
	private void parsear(String a){
		parseInt = a.split(":");
		for(int i = 0; i < parseInt.length; i ++){
			if(i ==0){
				hora = Integer.parseInt(parseInt[i].toString());
			}else if(i==1){
				minuto = Integer.parseInt(parseInt[i].toString());
			}else{
				segundo = Integer.parseInt(parseInt[i].toString());
			}
		}
	}
	public void setHora(View v){
		inicializar();
	}
	/**
	 * el @param m es simplemente el mensaje que enviamos
	 * Mietras que @param i es el tiempo del mensaje, si es 1 el mensaje sera corto, si es mayor a 1
	 * y siempre entero sera un mensaje largo
	 */
	private void mensaje(String m, int i){
		if(i==1){
			Toast.makeText(getApplicationContext(), m, Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(getApplicationContext(), m, Toast.LENGTH_LONG).show();
		}
		
	}
	public void cancelar(View v){
		this.finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ajustes, menu);
		return true;
	}

}
