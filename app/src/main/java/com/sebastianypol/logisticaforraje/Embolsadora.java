package com.sebastianypol.logisticaforraje;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Pablo on 31/03/2015.
 */
public class Embolsadora extends ActionBarActivity {

    /*Titulos principales hojaPicadora*/
    private final String T_PREP = "Tiempo preparatorio";
    private final String T_BOLSA = "Tiempo de colocación de bolsa";
    private final String T_EMBOLSADO = "Tiempo de embolsado";
    private final String T_ESP = "Tiempo de espera";
    private final String T_MUERTO = "Tiempo muerto";
//    private final String T_OP_BOLSA = "Tiempo operativo individual por bolsa";

    /*Titulos secundarios hojaPicadora*/
    private final String IPM = "Inicio puesta en marcha";
    private final String FPM = "Fin puesta en marcha";
    private final String IC = "Inicio de colocación";
    private final String FC = "Fin de colocación";
    private final String IE = "Inicio embolsado";
    private final String FE = "Fin embolsado";
    private final String ITE = "Inicio tiempo de espera";
    private final String FTE = "Fin tiempo de espera";
    private final String ITM = "Inicio RepMant";
    private final String FTM = "Fin de RepMant";
//    private final String I_EMBUTIDO = "Inicio de embutido";
//    private final String F_EMBUTIDO = "Fin de embutido";

    /*Definimos los botones*/
    private Button btnInicioPrepEmbolsadora;
    private Button btnFinPrepEmbolsadora;
    private Button btnInicioColoBolsaEmbol;
    private Button btnFinColoBolsaEmbol;
    private Button btnInicioTGE;
    private Button btnFinTGE;
    private Button btnInicioTiempEsp;
    private Button btnFinTiempEsp;
    private Button btnInicioRepMan;
    private Button btnFinRepMan;

    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private GeneraHora hoja;
    FileInputStream fileIn;
    FileOutputStream fileOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embolsadora);

        fileIn = null;
        fileOut = null;

        /*Tratamos de leer el archivos xls de lo contrario lo creamos.*/
        try {

            File file = null;
            String path = null;
            if (isExternalStorageReadable()){
                path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            }
            file = new File(path + File.separator + "Logistica de Forraje"+ File.separator +"Tiempo.xls");
            if(file.exists()){
                fileIn = new FileInputStream(file);
            }
            else{
                throw new FileNotFoundException();
            }

            wb = new HSSFWorkbook(fileIn);
            sheet = wb.getSheetAt(2);
            sheet.setSelected(true);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            hoja = new GeneraHora();
            wb = (HSSFWorkbook) hoja.getLibro();
            sheet = wb.getSheetAt(2);
            sheet.setSelected(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        //Verificamos que no hayan quedado botones sin apretar, si es el caso se llena la celda con un mensaje
        btnFinPrepEmbolsadora = (Button) findViewById(R.id.btnFinTiempoPreparatorioEmbolsadora);
        btnFinColoBolsaEmbol = (Button) findViewById(R.id.btnFinColocacionBolsaEmbolsadora);
        btnFinTGE = (Button) findViewById(R.id.btnFinTiempoGeneralEmbolsadora);
        btnFinTiempEsp = (Button) findViewById(R.id.btnFinTiempoEsperaEmbolsadora);
        btnFinRepMan = (Button) findViewById(R.id.btnFinTiempoRepMantEmbolsadora);

        if(btnFinPrepEmbolsadora.isEnabled()){
            escribirCeldaFaltante(FPM);
        }
        if(btnFinColoBolsaEmbol.isEnabled()){
            escribirCeldaFaltante(FC);
        }
        if(btnFinTGE.isEnabled()){
            escribirCeldaFaltante(FE);
        }
        if(btnFinTiempEsp.isEnabled()){
            escribirCeldaFaltante(FTE);
        }
        if(btnFinRepMan.isEnabled()){
            escribirCeldaFaltante(FTM);
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
            file = new File(path + File.separator + "Logistica de Forraje" + File.separator + "Tiempo.xls");
            if (file.exists()) {
                fileIn = new FileInputStream(file);
            } else {
                throw new FileNotFoundException();
            }

            wb = new HSSFWorkbook(fileIn);
            sheet = wb.getSheetAt(2);
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

    public void inicioPrep(View v){

        btnInicioPrepEmbolsadora = (Button) findViewById(R.id.btnInicioTiempoPreparatorioEmbolsadora);
        btnFinPrepEmbolsadora = (Button) findViewById(R.id.btnFinTiempoPreparatorioEmbolsadora);

        escribirCelda(IPM);

        btnFinPrepEmbolsadora.setEnabled(true);
        btnInicioPrepEmbolsadora.setEnabled(false);

    }

    public void finPrep(View v){

        btnInicioPrepEmbolsadora = (Button) findViewById(R.id.btnInicioTiempoPreparatorioEmbolsadora);
        btnFinPrepEmbolsadora = (Button) findViewById(R.id.btnFinTiempoPreparatorioEmbolsadora);

        escribirCelda(FPM);

        btnInicioPrepEmbolsadora.setEnabled(true);
        btnFinPrepEmbolsadora.setEnabled(false);

    }

    public void inicioColBolsa(View v){

        btnInicioColoBolsaEmbol = (Button) findViewById(R.id.btnInicioColocacionBolsaEmbolsadora);
        btnFinColoBolsaEmbol = (Button) findViewById(R.id.btnFinColocacionBolsaEmbolsadora);

        escribirCelda(IC);

        btnFinColoBolsaEmbol.setEnabled(true);
        btnInicioColoBolsaEmbol.setEnabled(false);

    }

    public void finColBolsa(View v){

        btnInicioColoBolsaEmbol = (Button) findViewById(R.id.btnInicioColocacionBolsaEmbolsadora);
        btnFinColoBolsaEmbol = (Button) findViewById(R.id.btnFinColocacionBolsaEmbolsadora);

        escribirCelda(FC);

        btnInicioColoBolsaEmbol.setEnabled(true);
        btnFinColoBolsaEmbol.setEnabled(false);

    }

    public void inicioTGE(View v){

        btnInicioTGE = (Button) findViewById(R.id.btnInicioTiempoGeneralEmbolsadora);
        btnFinTGE = (Button) findViewById(R.id.btnFinTiempoGeneralEmbolsadora);

        escribirCelda(IE);

        btnFinTGE.setEnabled(true);
        btnInicioTGE.setEnabled(false);

    }

    public void finTGE(View v){

        btnInicioTGE = (Button) findViewById(R.id.btnInicioTiempoGeneralEmbolsadora);
        btnFinTGE = (Button) findViewById(R.id.btnFinTiempoGeneralEmbolsadora);

        escribirCelda(FE);

        btnInicioTGE.setEnabled(true);
        btnFinTGE.setEnabled(false);

    }

    public void inicioTiempEsp(View v){

        btnInicioTiempEsp = (Button) findViewById(R.id.btnInicioTiempoEsperaEmbolsadora);
        btnFinTiempEsp = (Button) findViewById(R.id.btnFinTiempoEsperaEmbolsadora);

        escribirCelda(ITE);

        btnFinTiempEsp.setEnabled(true);
        btnInicioTiempEsp.setEnabled(false);

    }

    public void finTiempEsp(View v){

        btnInicioTiempEsp = (Button) findViewById(R.id.btnInicioTiempoEsperaEmbolsadora);
        btnFinTiempEsp = (Button) findViewById(R.id.btnFinTiempoEsperaEmbolsadora);

        escribirCelda(FTE);

        btnInicioTiempEsp.setEnabled(true);
        btnFinTiempEsp.setEnabled(false);

    }

    public void inicioRepMant(View v){

        btnInicioRepMan = (Button) findViewById(R.id.btnInicioTiempoRepMantEmbolsadora);
        btnFinRepMan = (Button) findViewById(R.id.btnFinTiempoRepMantEmbolsadora);

        escribirCelda(ITM);

        btnFinRepMan.setEnabled(true);
        btnInicioRepMan.setEnabled(false);

    }

    public void finREpMant(View v){

        btnInicioRepMan = (Button) findViewById(R.id.btnInicioTiempoRepMantEmbolsadora);
        btnFinRepMan = (Button) findViewById(R.id.btnFinTiempoRepMantEmbolsadora);

        escribirCelda(FTM);

        btnInicioRepMan.setEnabled(true);
        btnFinRepMan.setEnabled(false);

    }

    private void escribirCelda(String valorEncabezado){

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        //sheet.rowIterator();
        Cell celda;
        int columna = 0;
        int fila = 0;
        sheet = wb.getSheetAt(2);

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
        sheet = wb.getSheetAt(2);

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

    public String getT_BOLSA() {
        return T_BOLSA;
    }

    public String getT_EMBOLSADO() {
        return T_EMBOLSADO;
    }

    public String getT_ESP() {
        return T_ESP;
    }

    public String getT_MUERTO() {
        return T_MUERTO;
    }

//    public String getT_OP_BOLSA() {
//        return T_OP_BOLSA;
//    }

    public String getIPM() {
        return IPM;
    }

    public String getFPM() {
        return FPM;
    }

    public String getIC() {
        return IC;
    }

    public String getFC() {
        return FC;
    }

    public String getIE() {
        return IE;
    }

    public String getFE() {
        return FE;
    }

    public String getITE() {
        return ITE;
    }

    public String getFTE() {
        return FTE;
    }

    public String getITM() {
        return ITM;
    }

    public String getFTM() {
        return FTM;
    }

//    public String getI_EMBUTIDO() {
//        return I_EMBUTIDO;
//    }
//
//    public String getF_EMBUTIDO() {
//        return F_EMBUTIDO;
//    }

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
