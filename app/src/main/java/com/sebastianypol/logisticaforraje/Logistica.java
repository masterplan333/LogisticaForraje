package com.sebastianypol.logisticaforraje;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;


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
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private GeneraHora hoja;
    FileInputStream fileIn;
    FileOutputStream fileOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistica);

        fileIn = null;
        fileOut = null;

        /*Tratamos de leer el archivos xls de lo contrario lo creamos.*/
        try {
            fileIn = new FileInputStream("Tiempos.xls");
            POIFSFileSystem fs = new POIFSFileSystem(fileIn);
            wb = new HSSFWorkbook(fs);
            sheet = wb.getSheetAt(0);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            hoja = new GeneraHora();
            wb = (HSSFWorkbook) hoja.getLibro();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        // Write the output to a file
        try {
//            fileOut = new FileOutputStream("Tiempos.xls");
            //hoja.ajustaColumnas(wb.getSheet("hojaPicadora"));
            fileOut = openFileOutput("Tiempos.xls", Context.MODE_PRIVATE);
            wb.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fileOut != null)
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (fileIn != null)
                try {
                    fileIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        super.onPause();
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

        escribirCelda(hoja.IPM);

        btnFinPrep.setEnabled(true);
        btnInicioPrep.setEnabled(false);

    }

    public void preparatorioFin(View v){

        btnInicioPrep = (Button) findViewById(R.id.btnInicioPreparatorioPicadora);
        btnFinPrep = (Button) findViewById(R.id.btnFinPreparatorioPicadora);

        escribirCelda(hoja.FPM);

        btnInicioPrep.setEnabled(true);
        btnFinPrep.setEnabled(false);
    }

    public void trasladoSalida(View v){

        btnSalida = (Button) findViewById(R.id.btnSalidaPicadora);
        btnLlegada = (Button) findViewById(R.id.btnLlegadaPicadora);

        escribirCelda(hoja.SL);

        btnLlegada.setEnabled(true);
        btnSalida.setEnabled(false);
    }

    public void trasladoLlegada(View v){

        btnSalida = (Button) findViewById(R.id.btnSalidaPicadora);
        btnLlegada = (Button) findViewById(R.id.btnLlegadaPicadora);

        escribirCelda(hoja.LT);

        btnSalida.setEnabled(true);
        btnLlegada.setEnabled(false);
    }

    public void picadoInicio(View v){

        btnInicioPica = (Button) findViewById(R.id.btnInicioPicadoPicadora);
        btnFinPica = (Button) findViewById(R.id.btnFinPicadoPicadora);

        escribirCelda(hoja.IP);

        btnFinPica.setEnabled(true);
        btnInicioPica.setEnabled(false);
    }

    public void picadoFin(View v){

        btnInicioPica = (Button) findViewById(R.id.btnInicioPicadoPicadora);
        btnFinPica = (Button) findViewById(R.id.btnFinPicadoPicadora);

        escribirCelda(hoja.FP);

        btnInicioPica.setEnabled(true);
        btnFinPica.setEnabled(false);
    }

    public void esperaInicio(View v){

        btnInicioEsp = (Button) findViewById(R.id.btnInicioEsperaPicadora);
        btnFinEsp = (Button) findViewById(R.id.btnFinEsperaPicadora);

        escribirCelda(hoja.ITE);

        btnFinEsp.setEnabled(true);
        btnInicioEsp.setEnabled(false);
    }

    public void esperaFin(View v){

        btnInicioEsp = (Button) findViewById(R.id.btnInicioEsperaPicadora);
        btnFinEsp = (Button) findViewById(R.id.btnFinEsperaPicadora);

        escribirCelda(hoja.FTE);

        btnInicioEsp.setEnabled(true);
        btnFinEsp.setEnabled(false);
    }

    public void repMantInicio(View v){

        btnInicioRepMant = (Button) findViewById(R.id.btnInicioRepMantPicadora);
        btnFinRepMant = (Button) findViewById(R.id.btnFinRepMantPicadora);

        escribirCelda(hoja.IRM);

        btnFinRepMant.setEnabled(true);
        btnInicioRepMant.setEnabled(false);
    }

    public void repMantFin(View v){

        btnInicioRepMant = (Button) findViewById(R.id.btnInicioRepMantPicadora);
        btnFinRepMant = (Button) findViewById(R.id.btnFinRepMantPicadora);

        escribirCelda(hoja.FRM);

        btnInicioRepMant.setEnabled(true);
        btnFinRepMant.setEnabled(false);
    }

    public void rotorEncendido(View v){

        btnEncendido = (Button) findViewById(R.id.btnEncendidoPicadora);
        btnApagado = (Button) findViewById(R.id.btnApagadoPicadora);

        escribirCelda(hoja.ER);

        btnApagado.setEnabled(true);
        btnEncendido.setEnabled(false);
    }

    public void rotorApagado(View v){

        btnEncendido = (Button) findViewById(R.id.btnEncendidoPicadora);
        btnApagado = (Button) findViewById(R.id.btnApagadoPicadora);

        escribirCelda(hoja.AR);

        btnEncendido.setEnabled(true);
        btnApagado.setEnabled(false);
    }

    private void escribirCelda(String valorEncabezado){

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        //sheet.rowIterator();
        Cell celda;
        int columna = 0;
        int fila = 0;
        sheet = wb.getSheetAt(0);

        for (Row row : sheet){
            for (Cell cell : row) {
                if (cell.getStringCellValue().equals(valorEncabezado)) {
                    columna = cell.getColumnIndex();
                    break;
                }
            }
        }
        Log.v("Columna----> ", String.valueOf(columna));

        for (Row row : sheet){
            if(row.getRowNum() != 0) {
                Cell aux = row.getCell(columna, Row.RETURN_NULL_AND_BLANK);
                if (aux == null) {
                    fila = row.getRowNum();
                    break;
                }
                else{
                    if(row.getRowNum()==sheet.getLastRowNum()) {
                        Row auxRow = sheet.createRow(sheet.getPhysicalNumberOfRows());
                        fila = auxRow.getRowNum();
                    }
                }
            }
        }

        celda = sheet.getRow(fila).createCell(columna);
        celda.setCellValue(today.format("%k:%M:%S"));


        Log.v("COLUMNA--->", String.valueOf(columna));

    }
}
