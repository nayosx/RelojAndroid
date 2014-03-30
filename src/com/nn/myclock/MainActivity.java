package com.nn.myclock;

import java.util.Calendar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView tHora;
	int hora=0, minuto =0, segundo = 0; 
	Intent i;
	Thread iniReloj = null;
	Runnable r;
	boolean isUpdate = false;
	String sec, min, hor;
	
	String curTime;
	
	//Calendar c = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tHora = (TextView) findViewById(R.id.tClock);
		
		
	    r = new RefreshClock();
	    iniReloj= new Thread(r);   
	    iniReloj.start();
		
	}
	
	public void ajuste(View v){
		i = new Intent(this, Ajustes.class);
		i.putExtra(S.FULL_TIME, curTime);
		
        // hay una clase llamada S, que se encarga de contener var estaticas como AJUSTES
		this.startActivityForResult(i, S.AJUSTES);
	}
	
	
	/**
    basicamente es el que hace el limpiado del layout cada segundo, un simple if es el 
    que identifica si se ah actualizado la hora desde ajustes oh si tiene que seguir 
    mostrando la hora actual

    isUpdate muestra el valor que se envio de la  clase Ajustes 
    */
	private void initClock() {
	    runOnUiThread(new Runnable() {
	        public void run() {
	            try{
	            	
	            	if(isUpdate){
	            		settingNewClock();
	            	} else {
	            		updateTime();
	            	}
	            	curTime =hor+ hora + min + minuto + sec + segundo;
	            	tHora.setText(curTime);
	            	
	            }catch (Exception e) {}
	        }
	    });
	}



    /**
    Esta clase hace uso de la interface Runnable la cual es la encargada de estar
    refrescando cada 1000 milisegundos es decir, un segudo, no tiene gran ciencia


     @SuppressWarnings("unused") es para decirle al compilador que obvie la advertencia 
    que se genera, pero la verdad no afecta en nada el funcionamiento del mismo
    
    */
	class RefreshClock implements Runnable{
	    // @Override
	    @SuppressWarnings("unused")
		public void run() {
	            while(!Thread.currentThread().isInterrupted()){
	                try {
	                	initClock();
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                        Thread.currentThread().interrupt();
	                }catch(Exception e){
	                }
	            }
	    }
	}

    /**
    Este es el metodo inicial del reloj, a partir de el es que se muestra la hora
    cada segundo es la encargada Java.Util.Calendar

    */
	
	private void updateTime(){
		
		Calendar c = Calendar.getInstance();
		hora = c.get(Calendar.HOUR_OF_DAY);
		minuto = c.get(Calendar.MINUTE);
		segundo = c.get(Calendar.SECOND);
		setZeroClock();
		
	}
	/**
    setZeroClock es para que se nos ponga el numero 0 en aquellos valores menores a
    10, pero no he podido resolver un pequeño inconveniente al momento de la llegada 
    de 0:0:0 y por ende en sus derivadas, aunque no es por falta de logica, he revisado 
    muy bien, pero si le encuentran arreglo me hacen el favor y me avisan de como 
    solucionarlo
    */
	private void setZeroClock(){
		if(hora >=0 & hora <=9){
			hor = "0";
		}else{
			hor = "";
		}
		
		if(minuto >=0 & minuto <=9){
			min = ":0";
		}else{
			min = ":";
		}
		
		if(segundo >=0 & segundo <=9){
			sec = ":0";
			
		}else{
			sec = ":";
		}
	}
	

    /**
    Que puedo decir de este metodo mas que es el encargado de parsear la hora de una 
    manera que al llegar a 24:59:59 esta retome los valores de 00:00:00 aunque en la practica
    como mencionaba en un comentario anterior esta se pone en 0:0:0, pero luego se restaura a
    00:00:01    
    */
	private void settingNewClock(){
		segundo +=1; 

		setZeroClock();
		
		if(segundo >=0 & segundo <=59){		
			
		}else {
			segundo = 0;
			minuto +=1;
		}
		if(minuto >=0 & minuto <=59){
				
		}else{
			minuto = 0;
			hora +=1;
		}
		if(hora >= 0 & hora <= 24){		
			
		}else{
			hora = 0;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == S.AJUSTES){
			hora = data.getExtras().getInt(S.HORA);
			minuto = data.getExtras().getInt(S.MINUTO);
			segundo = data.getExtras().getInt(S.SEGUNDO);
			isUpdate = data.getExtras().getBoolean(S.IS_UPDATE_TIME);
		}
	}
	

    /**
    Bueno el metodo mensaje se uso simplemente para pruebas y como se nota es un simple Toast
    
    Tiene dos parametros, el mensaje que se desea mostrar y el tiempo que este dura
    1 para un mensaje corto y cualquier otro valor  mayor a uno para mensajes largos    
    */
	private void mensaje(String m, int i){
		if(i==1){
			Toast.makeText(getApplicationContext(), m, Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(getApplicationContext(), m, Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
	}
	
	
}
