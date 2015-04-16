package com.sebastianypol.logisticaforraje;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.Log;
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
public class CarroForrajero extends ActionBarActivity {

    /*Titulos principales hojaPicadora*/
    private final String T_PREP = "Tiempo preparatorio";
    private final String C_ACARREO = "Ciclo de acarreo";
    private final String T_CARG_INDV = "Tiempo carga individual";
    private final String T_ACARREO = "Tiempo de acarreo";
    private final String T_ESP_EMB = "Tiempo espera embolsadora";
    private final String T_DES = "Tiempo de descarga";
    private final String T_TRANS = "Tiempo de transporte en vacío";
    private final String T_ESP_PIC = "Tiempo espera en picadora";
    private final String T_REPMANT = "Tiempo de reparación y mantenimiento";
    /*Titulos secundarios hojaPicadora*/
    private final String IPM = "Inicio puesta en marcha";
    private final String FPM = "Fin puesta en marcha";
    private final String IC = "Inicio de ciclo";
    private final String FC = "Fin de ciclo";
    private final String ICA = "Inicio de carga";
    private final String FCA = "Fin de carga";
    private final String IA = "Inicio acarreo";
    private final String FA = "Fin acarreo";
    private final String IEE = "Inicio de espera";
    private final String FEE = "Fin de espera";
    private final String ID = "Inicio de descarga";
    private final String FD = "Fin de descarga";
    private final String ITV = "Inicio de transporte en vacío";
    private final String FTV = "Fin de transporte en vacío";
    private final String IEP = "Inicio espera";
    private final String FEP = "Fin espera";
    private final String I = "Inicio";
    private final String F = "Fin";

    /*Definimos los botones*/
    private Button btnIPM;
    private Button btnFPM;
    private Button btnIC;
    private Button btnFC;
    private Button btnICA;
    private Button btnFCA;
    private Button btnIA;
    private Button btnFA;
    private Button btnIEE;
    private Button btnFEE;
    private Button btnID;
    private Button btnFD;
    private Button btnITV;
    private Button btnFTV;
    private Button btnIEP;
    private Button btnFEP;
    private Button btnI;
    private Button btnF;

    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private GeneraHora hoja;
    FileInputStream fileIn;
    FileOutputStream fileOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro_forrajero);

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
            sheet = wb.getSheetAt(1);
            sheet.setSelected(true);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            hoja = new GeneraHora();
            wb = (HSSFWorkbook) hoja.getLibro();
            sheet = wb.getSheetAt(1);
            sheet.setSelected(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        //Verificamos que no hayan quedado botones sin apretar, si es el caso se llena la celda con un mensaje
        btnFPM = (Button) findViewById(R.id.btnFinTPreparatorioCarro);
        btnFC = (Button) findViewById(R.id.btnFinCicloAcarreoCarro);
        btnFCA = (Button) findViewById(R.id.btnFinCargaIndividualCarro);
        btnFA = (Button) findViewById(R.id.btnFinTiempoAcarreoCarro);
        btnFEE = (Button) findViewById(R.id.btnFinTEsperaEmbolsadoraCarro);
        btnFD = (Button) findViewById(R.id.btnFinTiempoDescargaCarro);
        btnFTV = (Button) findViewById(R.id.btnFinTransporteEnVacioCarro);
        btnFEP = (Button) findViewById(R.id.btnFinTEsperaPicadoraCarro);
        btnF = (Button) findViewById(R.id.btnFinRepMantCarro);

        if(btnFPM.isEnabled()){
            escribirCeldaFaltante(FPM);
        }
        if(btnFC.isEnabled()){
            escribirCeldaFaltante(FC);
        }
        if(btnFCA.isEnabled()){
            escribirCeldaFaltante(FCA);
        }
        if(btnFA.isEnabled()){
            escribirCeldaFaltante(FA);
        }
        if(btnFD.isEnabled()){
            escribirCeldaFaltante(FD);
        }
        if(btnFEE.isEnabled()){
            escribirCeldaFaltante(FEE);
        }
        if(btnFTV.isEnabled()){
            escribirCeldaFaltante(FTV);
        }
        if(btnFEP.isEnabled()){
            escribirCeldaFaltante(FEP);
        }
        if(btnF.isEnabled()){
            escribirCeldaFaltante(F);
        }

        // Write the output to a file
        try {
            File file = null;
            String path = null;
            if (isExternalStorageWritable()){
                path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            }
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
            sheet = wb.getSheetAt(1);
            sheet.setSelected(true);
            super.onResume();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    public void inicioPM(View v){

        btnIPM = (Button) findViewById(R.id.btnInicioTPreparatorioCarro);
        btnFPM = (Button) findViewById(R.id.btnFinTPreparatorioCarro);

        escribirCelda(IPM);

        btnFPM.setEnabled(true);
        btnIPM.setEnabled(false);

    }

    public void finPM(View v){

        btnIPM = (Button) findViewById(R.id.btnInicioTPreparatorioCarro);
        btnFPM = (Button) findViewById(R.id.btnFinTPreparatorioCarro);

        escribirCelda(FPM);

        btnIPM.setEnabled(true);
        btnFPM.setEnabled(false);

    }

    public void inicioCiclo(View v){

        btnIC = (Button) findViewById(R.id.btnInicioCicloAcarreoCarro);
        btnFC = (Button) findViewById(R.id.btnFinCicloAcarreoCarro);

        escribirCelda(IC);

        btnFC.setEnabled(true);
        btnIC.setEnabled(false);

    }

    public void finCiclo(View v){

        btnIC = (Button) findViewById(R.id.btnInicioCicloAcarreoCarro);
        btnFC = (Button) findViewById(R.id.btnFinCicloAcarreoCarro);

        escribirCelda(FC);

        btnIC.setEnabled(true);
        btnFC.setEnabled(false);

    }

    public void inicioCarga(View v){

        btnICA = (Button) findViewById(R.id.btnInicioCargaIndividualCarro);
        btnFCA = (Button) findViewById(R.id.btnFinCargaIndividualCarro);

        escribirCelda(ICA);

        btnFCA.setEnabled(true);
        btnICA.setEnabled(false);

    }

    public void finCarga(View v){

        btnICA = (Button) findViewById(R.id.btnInicioCargaIndividualCarro);
        btnFCA = (Button) findViewById(R.id.btnFinCargaIndividualCarro);

        escribirCelda(FCA);

        btnICA.setEnabled(true);
        btnFCA.setEnabled(false);

    }

    public void inicioAcarreo(View v){

        btnIA = (Button) findViewById(R.id.btnInicioTiempoAcarreoCarro);
        btnFA = (Button) findViewById(R.id.btnFinTiempoAcarreoCarro);

        escribirCelda(IA);

        btnFA.setEnabled(true);
        btnIA.setEnabled(false);

    }

    public void finAcaarreo(View v){

        btnIA = (Button) findViewById(R.id.btnInicioTiempoAcarreoCarro);
        btnFA = (Button) findViewById(R.id.btnFinTiempoAcarreoCarro);

        escribirCelda(FA);

        btnIA.setEnabled(true);
        btnFA.setEnabled(false);

    }

    public void inicioEsperaE(View v){

        btnIEE = (Button) findViewById(R.id.btnInicioTEsperaEmbolsadoraCarro);
        btnFEE = (Button) findViewById(R.id.btnFinTEsperaEmbolsadoraCarro);

        escribirCelda(IEE);

        btnFEE.setEnabled(true);
        btnIEE.setEnabled(false);

    }

    public void finEsperaE(View v){

        btnIEE = (Button) findViewById(R.id.btnInicioTEsperaEmbolsadoraCarro);
        btnFEE = (Button) findViewById(R.id.btnFinTEsperaEmbolsadoraCarro);

        escribirCelda(FEE);

        btnIEE.setEnabled(true);
        btnFEE.setEnabled(false);

    }

    public void inicioDescarga(View v){

        btnID = (Button) findViewById(R.id.btnInicioTIempoDescargaCarro);
        btnFD = (Button) findViewById(R.id.btnFinTiempoDescargaCarro);

        escribirCelda(ID);

        btnFD.setEnabled(true);
        btnID.setEnabled(false);

    }

    public void finDescarga(View v){

        btnID = (Button) findViewById(R.id.btnInicioTIempoDescargaCarro);
        btnFD = (Button) findViewById(R.id.btnFinTiempoDescargaCarro);

        escribirCelda(FD);

        btnID.setEnabled(true);
        btnFD.setEnabled(false);

    }

    public void inicioTV(View v){

        btnITV = (Button) findViewById(R.id.btnInicioTransporteEnVacioCarro);
        btnFTV = (Button) findViewById(R.id.btnFinTransporteEnVacioCarro);

        escribirCelda(ITV);

        btnFTV.setEnabled(true);
        btnITV.setEnabled(false);

    }

    public void finTV(View v){

        btnITV = (Button) findViewById(R.id.btnInicioTransporteEnVacioCarro);
        btnFTV = (Button) findViewById(R.id.btnFinTransporteEnVacioCarro);

        escribirCelda(FTV);

        btnITV.setEnabled(true);
        btnFTV.setEnabled(false);

    }

    public void inicioEsperaP(View v){

        btnIEP = (Button) findViewById(R.id.btnInicioTEsperaPicadoraCarro);
        btnFEP = (Button) findViewById(R.id.btnFinTEsperaPicadoraCarro);

        escribirCelda(IEP);

        btnFEP.setEnabled(true);
        btnIEP.setEnabled(false);

    }

    public void finEsperaP(View v){

        btnIEP = (Button) findViewById(R.id.btnInicioTEsperaPicadoraCarro);
        btnFEP = (Button) findViewById(R.id.btnFinTEsperaPicadoraCarro);

        escribirCelda(FEP);

        btnIEP.setEnabled(true);
        btnFEP.setEnabled(false);

    }

    public void inicio(View v){

        btnI = (Button) findViewById(R.id.btnInicioRepMantCarro);
        btnF = (Button) findViewById(R.id.btnFinRepMantCarro);

        escribirCelda(I);

        btnF.setEnabled(true);
        btnI.setEnabled(false);

    }

    public void fin(View v){

        btnI = (Button) findViewById(R.id.btnInicioRepMantCarro);
        btnF = (Button) findViewById(R.id.btnFinRepMantCarro);

        escribirCelda(F);

        btnI.setEnabled(true);
        btnF.setEnabled(false);

    }

    private void escribirCelda(String valorEncabezado){

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        //sheet.rowIterator();
        Cell celda;
        int columna = 0;
        int fila = 0;
        sheet = wb.getSheetAt(1);

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
        sheet = wb.getSheetAt(1);

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

    public String getC_ACARREO() {
        return C_ACARREO;
    }

    public String getT_CARG_INDV() {
        return T_CARG_INDV;
    }

    public String getT_ACARREO() {
        return T_ACARREO;
    }

    public String getT_ESP_EMB() {
        return T_ESP_EMB;
    }

    public String getT_DES() {
        return T_DES;
    }

    public String getT_TRANS() {
        return T_TRANS;
    }

    public String getT_ESP_PIC() {
        return T_ESP_PIC;
    }

    public String getT_REPMANT() {
        return T_REPMANT;
    }

    public String getIPM() {
        return IPM;
    }

    public String getFPM() {
        return FPM;
    }

    public String getICA() {
        return ICA;
    }

    public String getFCA() {
        return FCA;
    }

    public String getIC() {
        return IC;
    }

    public String getFC() {
        return FC;
    }

    public String getIA() {
        return IA;
    }

    public String getFA() {
        return FA;
    }

    public String getIEE() {
        return IEE;
    }

    public String getFEE() {
        return FEE;
    }

    public String getID() {
        return ID;
    }

    public String getFD() {
        return FD;
    }

    public String getITV() {
        return ITV;
    }

    public String getFTV() {
        return FTV;
    }

    public String getIEP() {
        return IEP;
    }

    public String getFEP() {
        return FEP;
    }

    public String getI() {
        return I;
    }

    public String getF() {
        return F;
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
