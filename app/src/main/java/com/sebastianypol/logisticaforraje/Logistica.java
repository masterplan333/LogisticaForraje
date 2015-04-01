package com.sebastianypol.logisticaforraje;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Logistica extends ActionBarActivity {

    private Button btnInicioPrep;// = (Button) findViewById(R.id.btnInicioPreparatorioPicadora);
    private Button btnFinPrep;// = (Button) findViewById(R.id.btnFinPreparatorioPicadora);
    private Button btnSalida;// = (Button) findViewById(R.id.btnSalidaPicadora);
    private Button btnLlegada;// = (Button) findViewById(R.id.btnLlegadaPicadora);
    private Button btnInicioPica;// = (Button) findViewById(R.id.btnInicioPicadoPicadora);
    private Button btnFinPica;// = (Button) findViewById(R.id.btnFinPicadoPicadora);
    private Button btnInicioEsp;// = (Button) findViewById(R.id.btnInicioEsperaPicadora);
    private Button btnFinEsp;// = (Button) findViewById(R.id.btnFinEsperaPicadora);
    private Button btnInicioRepMant;// = (Button) findViewById(R.id.btnInicioRepMantPicadora);
    private Button btnFinRepMant;// = (Button) findViewById(R.id.btnFinRepMantPicadora);
    private Button btnEncendido;// = (Button) findViewById(R.id.btnEncendidoPicadora);
    private Button btnApagado;// = (Button) findViewById(R.id.btnApagadoPicadora);
    private String hora = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistica);

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logistica, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void preparatorioInicio(View v){

        btnInicioPrep = (Button) findViewById(R.id.btnInicioPreparatorioPicadora);
        btnFinPrep = (Button) findViewById(R.id.btnFinPreparatorioPicadora);

        Toast toast = Toast.makeText(getApplicationContext(), "Función en desarrollo!!", Toast.LENGTH_SHORT);
        toast.show();
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        Log.d("Hora inicio -->", today.format("%k:%M:%S").toString());

        btnFinPrep.setEnabled(true);
        btnInicioPrep.setEnabled(false);

    }

    public void preparatorioFin(View v){

        btnInicioPrep = (Button) findViewById(R.id.btnInicioPreparatorioPicadora);
        btnFinPrep = (Button) findViewById(R.id.btnFinPreparatorioPicadora);

        Toast toast = Toast.makeText(getApplicationContext(), "Función en desarrollo!!", Toast.LENGTH_SHORT);
        toast.show();
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        Log.d("Hora fin -->", today.format("%k:%M:%S").toString());

        btnInicioPrep.setEnabled(true);
        btnFinPrep.setEnabled(false);
    }

    public void trasladoSalida(View v){

        Toast toast = Toast.makeText(getApplicationContext(), "Función en desarrollo!!", Toast.LENGTH_LONG);
        toast.show();
    }

    public void trasladoLlegada(View v){

        Toast toast = Toast.makeText(getApplicationContext(), "Función en desarrollo!!", Toast.LENGTH_LONG);
        toast.show();
    }

    public void picadoInicio(View v){

        Toast toast = Toast.makeText(getApplicationContext(), "Función en desarrollo!!", Toast.LENGTH_LONG);
        toast.show();
    }

    public void picadoFin(View v){

        Toast toast = Toast.makeText(getApplicationContext(), "Función en desarrollo!!", Toast.LENGTH_LONG);
        toast.show();
    }

    public void esperaInicio(View v){

        Toast toast = Toast.makeText(getApplicationContext(), "Función en desarrollo!!", Toast.LENGTH_LONG);
        toast.show();
    }

    public void esperaFin(View v){

        Toast toast = Toast.makeText(getApplicationContext(), "Función en desarrollo!!", Toast.LENGTH_LONG);
        toast.show();
    }

    public void repMantInicio(View v){

        Toast toast = Toast.makeText(getApplicationContext(), "Función en desarrollo!!", Toast.LENGTH_LONG);
        toast.show();
    }

    public void repMantFin(View v){

        Toast toast = Toast.makeText(getApplicationContext(), "Función en desarrollo!!", Toast.LENGTH_LONG);
        toast.show();
    }

    public void rotorEncendido(View v){

        Toast toast = Toast.makeText(getApplicationContext(), "Función en desarrollo!!", Toast.LENGTH_LONG);
        toast.show();
    }

    public void rotorApagado(View v){

        Toast toast = Toast.makeText(getApplicationContext(), "Función en desarrollo!!", Toast.LENGTH_LONG);
        toast.show();
    }
}
