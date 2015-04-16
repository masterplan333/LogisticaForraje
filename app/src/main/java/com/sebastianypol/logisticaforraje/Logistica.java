package com.sebastianypol.logisticaforraje;

import android.content.Context;
import android.os.Environment;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;


public class Logistica extends ActionBarActivity {

    /*Titulos principales hojaPicadora*/
    private final String T_PREP = "Tiempo preparatorio";
    private final String T_TRAS = "Tiempo de traslado interno";
    private final String T_PICD = "Tiempo de picado";
    private final String T_ESP = "Tiempo de espera";
    private final String T_REPMAN = "Tiempo de reparación y mantenimiento";
    private final String F_ROTOR = "Funcionamiento del rotor";
    /*Titulos secundarios hojaPicadora*/
    private final String IPM = "Inicio puesta en marcha";
    private final String FPM = "Fin puesta en marcha";
    private final String SL = "Salida al lote";
    private final String LT = "Llegada a tranquera";
    private final String IP = "Inicio picado";
    private final String FP = "Fin picado";
    private final String ITE = "Inicio tiempo de espera";
    private final String FTE = "Fin tiempo de espera";
    private final String IRM = "Inicio de RepMant";
    private final String FRM = "Fin de RepMant";
    private final String ER = "Encendido del rotor";
    private final String AR = "Apagado del rotor";

    /*Definimos los botones*/
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

            File file = null;
            String path = null;
            if (isExternalStorageReadable()){
                path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            }
            //file = new File(path + File.separator + "Tiempo.xls");
            file = new File(path + File.separator + "Logistica de Forraje"+ File.separator +"Tiempo.xls");
            if(file.exists()){
                fileIn = new FileInputStream(file);
            }
            else{
                throw new FileNotFoundException();
            }

            wb = new HSSFWorkbook(fileIn);
            sheet = wb.getSheetAt(0);
            sheet.setSelected(true);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            hoja = new GeneraHora();
            wb = (HSSFWorkbook) hoja.getLibro();
            sheet = wb.getSheetAt(0);
            sheet.setSelected(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        //Verificamos que no hayan quedado botones sin apretar, si es el caso se llena la celda con un mensaje
        btnFinPrep = (Button) findViewById(R.id.btnFinPreparatorioPicadora);
        btnLlegada = (Button) findViewById(R.id.btnLlegadaPicadora);
        btnFinPica = (Button) findViewById(R.id.btnFinPicadoPicadora);
        btnFinEsp = (Button) findViewById(R.id.btnFinEsperaPicadora);
        btnFinRepMant = (Button) findViewById(R.id.btnFinRepMantPicadora);
        btnApagado = (Button) findViewById(R.id.btnApagadoPicadora);

        if(btnFinPrep.isEnabled()){
            escribirCeldaFaltante(FPM);
        }
        if(btnLlegada.isEnabled()){
            escribirCeldaFaltante(LT);
        }
        if(btnFinPica.isEnabled()){
            escribirCeldaFaltante(FP);
        }
        if(btnFinEsp.isEnabled()){
            escribirCeldaFaltante(FTE);
        }
        if(btnFinRepMant.isEnabled()){
            escribirCeldaFaltante(FRM);
        }
        if(btnApagado.isEnabled()){
            escribirCeldaFaltante(AR        );
        }
        // Write the output to a file
        try {
            File file = null;
            String path = null;
            if (isExternalStorageWritable()){
                path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            }
            //file = new File(path + File.separator + "Tiempo.xls");
            file = new File(path + File.separator + "Logistica de Forraje"+ File.separator +"Tiempo.xls");
            file.getParentFile().mkdirs();
            fileOut = new FileOutputStream(file);
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
    protected void onResume() {
        try {
            File file = null;
            String path = null;
            if (isExternalStorageReadable()) {
                path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            }
            //file = new File(path + File.separator + "Tiempo.xls");
            file = new File(path + File.separator + "Logistica de Forraje" + File.separator + "Tiempo.xls");
            if (file.exists()) {
                fileIn = new FileInputStream(file);
            } else {
                throw new FileNotFoundException();
            }

            wb = new HSSFWorkbook(fileIn);
            sheet = wb.getSheetAt(0);
            sheet.setSelected(true);
            super.onResume();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onResume();
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

        escribirCelda(IPM);

        btnFinPrep.setEnabled(true);
        btnInicioPrep.setEnabled(false);

    }

    public void preparatorioFin(View v){

        btnInicioPrep = (Button) findViewById(R.id.btnInicioPreparatorioPicadora);
        btnFinPrep = (Button) findViewById(R.id.btnFinPreparatorioPicadora);

        escribirCelda(FPM);

        btnInicioPrep.setEnabled(true);
        btnFinPrep.setEnabled(false);
    }

    public void trasladoSalida(View v){

        btnSalida = (Button) findViewById(R.id.btnSalidaPicadora);
        btnLlegada = (Button) findViewById(R.id.btnLlegadaPicadora);

        escribirCelda(SL);

        btnLlegada.setEnabled(true);
        btnSalida.setEnabled(false);
    }

    public void trasladoLlegada(View v){

        btnSalida = (Button) findViewById(R.id.btnSalidaPicadora);
        btnLlegada = (Button) findViewById(R.id.btnLlegadaPicadora);

        escribirCelda(LT);

        btnSalida.setEnabled(true);
        btnLlegada.setEnabled(false);
    }

    public void picadoInicio(View v){

        btnInicioPica = (Button) findViewById(R.id.btnInicioPicadoPicadora);
        btnFinPica = (Button) findViewById(R.id.btnFinPicadoPicadora);

        escribirCelda(IP);

        btnFinPica.setEnabled(true);
        btnInicioPica.setEnabled(false);
    }

    public void picadoFin(View v){

        btnInicioPica = (Button) findViewById(R.id.btnInicioPicadoPicadora);
        btnFinPica = (Button) findViewById(R.id.btnFinPicadoPicadora);

        escribirCelda(FP);

        btnInicioPica.setEnabled(true);
        btnFinPica.setEnabled(false);
    }

    public void esperaInicio(View v){

        btnInicioEsp = (Button) findViewById(R.id.btnInicioEsperaPicadora);
        btnFinEsp = (Button) findViewById(R.id.btnFinEsperaPicadora);

        escribirCelda(ITE);

        btnFinEsp.setEnabled(true);
        btnInicioEsp.setEnabled(false);
    }

    public void esperaFin(View v){

        btnInicioEsp = (Button) findViewById(R.id.btnInicioEsperaPicadora);
        btnFinEsp = (Button) findViewById(R.id.btnFinEsperaPicadora);

        escribirCelda(FTE);

        btnInicioEsp.setEnabled(true);
        btnFinEsp.setEnabled(false);
    }

    public void repMantInicio(View v){

        btnInicioRepMant = (Button) findViewById(R.id.btnInicioRepMantPicadora);
        btnFinRepMant = (Button) findViewById(R.id.btnFinRepMantPicadora);

        escribirCelda(IRM);

        btnFinRepMant.setEnabled(true);
        btnInicioRepMant.setEnabled(false);
    }

    public void repMantFin(View v){

        btnInicioRepMant = (Button) findViewById(R.id.btnInicioRepMantPicadora);
        btnFinRepMant = (Button) findViewById(R.id.btnFinRepMantPicadora);

        escribirCelda(FRM);

        btnInicioRepMant.setEnabled(true);
        btnFinRepMant.setEnabled(false);
    }

    public void rotorEncendido(View v){

        btnEncendido = (Button) findViewById(R.id.btnEncendidoPicadora);
        btnApagado = (Button) findViewById(R.id.btnApagadoPicadora);

        escribirCelda(ER);

        btnApagado.setEnabled(true);
        btnEncendido.setEnabled(false);
    }

    public void rotorApagado(View v){

        btnEncendido = (Button) findViewById(R.id.btnEncendidoPicadora);
        btnApagado = (Button) findViewById(R.id.btnApagadoPicadora);

        escribirCelda(AR);

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


        //Log.v("COLUMNA--->", String.valueOf(columna));

    }

    private void escribirCeldaFaltante(String valorEncabezado){

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

      // celda = sheet.getRow(fila).getCell(columna);
        celda = sheet.getRow(fila).createCell(columna);
        celda.setCellValue("Valor no registrado");

    }

    public String getT_PREP() {
        return T_PREP;
    }

    public String getT_TRAS() {
        return T_TRAS;
    }

    public String getT_PICD() {
        return T_PICD;
    }

    public String getT_ESP() {
        return T_ESP;
    }

    public String getT_REPMAN() {
        return T_REPMAN;
    }

    public String getF_ROTOR() {
        return F_ROTOR;
    }

    public String getIPM() {
        return IPM;
    }

    public String getFPM() {
        return FPM;
    }

    public String getSL() {
        return SL;
    }

    public String getLT() {
        return LT;
    }

    public String getIP() {
        return IP;
    }

    public String getFP() {
        return FP;
    }

    public String getITE() {
        return ITE;
    }

    public String getFTE() {
        return FTE;
    }

    public String getIRM() {
        return IRM;
    }

    public String getFRM() {
        return FRM;
    }

    public String getER() {
        return ER;
    }

    public String getAR() {
        return AR;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
