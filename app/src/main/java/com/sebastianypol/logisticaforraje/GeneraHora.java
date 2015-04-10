package com.sebastianypol.logisticaforraje;

import android.content.res.AssetManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * Created by Pablo on 02/04/2015.
 */

public class GeneraHora {

    /*Titulos principales hojaPicadora*/
    public final String T_PREP = "Tiempo preparatorio";
    public final String T_TRAS = "Tiempo de traslado interno";
    public final String T_PICD = "Tiempo de picado";
    public final String T_ESP = "Tiempo de espera";
    public final String T_REPMAN = "Tiempo de reparación y mantenimiento";
    public final String F_ROTOR = "Funcionamiento del rotor";
    /*Titulos secundarios hojaPicadora*/
    public final String IPM = "Inicio puesta en marcha";
    public final String FPM = "Fin puesta en marcha";
    public final String SL = "Salida al lote";
    public final String LT = "Llegada a tranquera";
    public final String IP = "Inicio picado";
    public final String FP = "Fin picado";
    public final String ITE = "Inicio tiempo de espera";
    public final String FTE = "Fin tiempo de espera";
    public final String IRM = "Inicio de RepMant";
    public final String FRM = "Fin de RepMant";
    public final String ER = "Encendido del rotor";
    public final String AR = "Apagado del rotor";

    // documento con las hojas de calculo
    private final Workbook libro;

    // la hoja de calculo
    private final Sheet hojaPicadora;
    private final Sheet hojaLogicaForraje;
    private final Sheet hojaEmbolsadora;

    // estilo de las celdas del encabezado (con el nombre de las columnas)
    private final CellStyle estiloTitulo;

    // estilo de las celdas con fórmula
    private final CellStyle estiloCeldaConFormula;
    private AssetManager assets;

    public GeneraHora() {
        this.libro = new HSSFWorkbook();
        this.hojaPicadora = this.libro.createSheet("Picadora");
        this.hojaLogicaForraje = this.libro.createSheet("Lógica de Forraje");
        this.hojaEmbolsadora = this.libro.createSheet("Embolsadora");
        this.estiloTitulo = getEstiloTitulo();
        this.estiloCeldaConFormula = getEstiloCeldaConFormula();
        hojaPicadora.setSelected(true);
        anadeFilaEncabezadoPicadora();
    }

    // crea una fila con los datos del piloto: nombre, tiempos, total, media y mejor tiempo ---> Función en desuso
//    public void anadeTiemposPiloto(Piloto piloto, Sheet sheet) {
//        final Row filaPiloto = getNuevaFila(sheet);
//        filaPiloto.createCell(0).setCellValue(piloto.getNombre());
//        for (int i = 1; i <= Piloto.NUMERO_VUELTAS_ENTRENAMIENTO; i++) {
//            final Cell celda = filaPiloto.createCell(i);
//            celda.setCellValue(piloto.getTiemposVueltas().get(i - 1));
//            celda.setCellType(Cell.CELL_TYPE_NUMERIC);
//        }
//        generaFormulaSumaTiempos(filaPiloto);
//        generaFormulaMediaTiempos(filaPiloto);
//        generaFormulaMejorTiempo(filaPiloto);
//    }

    // crea la celda con la fórmula de suma de tiempos correspondiente a una fila ---> Funcion en desuso
//    private void generaFormulaSumaTiempos(Row filaPiloto) {
//        final int numeroFila = filaPiloto.getRowNum() + 1;
//        final String formula = "SUM" + generaRangoFormulaEnFila(numeroFila);
//        anadeFormulaYEstiloACelda(filaPiloto.createCell(Piloto.NUMERO_VUELTAS_ENTRENAMIENTO + 1), formula);
//    }

    // crea la celda con la fórmula de media de tiempos correspondiente a una fila ---> Funcion en desuso
//    private void generaFormulaMediaTiempos(Row filaPiloto) {
//        final int numeroFila = filaPiloto.getRowNum() + 1;
//        final String formula = "AVERAGE" + generaRangoFormulaEnFila(numeroFila);
//        anadeFormulaYEstiloACelda(filaPiloto.createCell(Piloto.NUMERO_VUELTAS_ENTRENAMIENTO + 2), formula);
//    }

    // crea la celda con la fórmula de que calcula el mejor tiempo a una fila --->Funcion en desuso
//    private void generaFormulaMejorTiempo(Row filaPiloto) {
//        final int numeroFila = filaPiloto.getRowNum() + 1;
//        final String formula = "MIN" + generaRangoFormulaEnFila(numeroFila);
//        anadeFormulaYEstiloACelda(filaPiloto.createCell(Piloto.NUMERO_VUELTAS_ENTRENAMIENTO + 3), formula);
//    }

    // devuelve el rango de columnas sobre las que actuará la formula. Ej: (B2:F2) --->Funcion en desuso
//    private static String generaRangoFormulaEnFila(int numeroFila) {
//        // la columna donde se situa el primer tiempo será la B (codigo ASCII 66) ya que en la A está el nombre del piloto)
//        final byte columnaB = 66;
//        final char primeraColumna = (char)columnaB;
//        final char ultimaColumna = (char)columnaB + Piloto.NUMERO_VUELTAS_ENTRENAMIENTO - 1;
//        return "(" + primeraColumna + numeroFila + ":" + ultimaColumna + numeroFila + ")";
//    }

    // añade la fórmula a una celda y añade el estilo de las celdas con fórmula
    private void anadeFormulaYEstiloACelda(Cell celda, String formula) {
        celda.setCellFormula(formula);
        celda.setCellStyle(estiloCeldaConFormula);
    }

    // genera el documento ---> Funcion en desuso
//    public OutputStream generaDocumento() throws IOException {
//        final OutputStream outputStream = new FileOutputStream("Tiempos.xls");
//        libro.write(outputStream);
//        outputStream.close();
//        return outputStream;
//    }

    // crea la fila y celdas del encabezado con el nombre de las columnas
    private void anadeFilaEncabezadoPicadora() {
        Row filaEncabezado = getNuevaFila(hojaPicadora);
        /*Se crean el encabezado principal*/
        int numeroCelda = 0;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, T_PREP);
        unirCeldas(hojaPicadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, (numeroCelda), T_TRAS);
        unirCeldas(hojaPicadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, (numeroCelda), T_PICD);
        unirCeldas(hojaPicadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, (numeroCelda), T_ESP);
        unirCeldas(hojaPicadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, (numeroCelda), T_REPMAN);
        unirCeldas(hojaPicadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, (numeroCelda), F_ROTOR);
        unirCeldas(hojaPicadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);

        /*Se crea el encabezado secundario*/
        filaEncabezado = getNuevaFila(hojaPicadora);
        numeroCelda = 0;
        //filaEncabezado.setRowNum(filaEncabezado.getRowNum() + 1);
        creaCeldaEncabezado(filaEncabezado, numeroCelda, IPM);
        ajustaColumna(hojaPicadora, numeroCelda, IPM);
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, FPM);
        ajustaColumna(hojaPicadora, numeroCelda, FPM);
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, SL);
        ajustaColumna(hojaPicadora, numeroCelda, SL);
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, LT);
        ajustaColumna(hojaPicadora, numeroCelda, LT);
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, IP);
        ajustaColumna(hojaPicadora, numeroCelda, IP);
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, FP);
        ajustaColumna(hojaPicadora, numeroCelda, FP);
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, ITE);
        ajustaColumna(hojaPicadora, numeroCelda, ITE);
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, FTE);
        ajustaColumna(hojaPicadora, numeroCelda, FTE);
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, IRM);
        ajustaColumna(hojaPicadora, numeroCelda, IRM);
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, FRM);
        ajustaColumna(hojaPicadora, numeroCelda, FRM);
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, ER);
        ajustaColumna(hojaPicadora, numeroCelda, ER);
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, AR);
        ajustaColumna(hojaPicadora, numeroCelda, AR);

//        creaCeldaEncabezado(filaEncabezado, (numeroCelda + 1), "Tiempo Preparatorio"); numeroCelda = numeroCelda + 2;
//        creaCeldaEncabezado(filaEncabezado, numeroCelda, "Ciclo de acarreo"); numeroCelda = numeroCelda + 2;
//        creaCeldaEncabezado(filaEncabezado, (numeroCelda + 1), "Tiempo de carga individual"); numeroCelda = numeroCelda + 2;
//        creaCeldaEncabezado(filaEncabezado, (numeroCelda + 1), "Tiempo de acarreo"); numeroCelda = numeroCelda + 2;
//        creaCeldaEncabezado(filaEncabezado, (numeroCelda + 1), "Tiempo de espera embolsadora"); numeroCelda = numeroCelda + 2;
//        creaCeldaEncabezado(filaEncabezado, (numeroCelda + 1), "Tiempo de descarga"); numeroCelda = numeroCelda + 2;
//        creaCeldaEncabezado(filaEncabezado, (numeroCelda + 1), "Tiempo de transporte en vacío"); numeroCelda = numeroCelda + 2;
//        creaCeldaEncabezado(filaEncabezado, (numeroCelda + 1), "Tiempo de espera en picadora"); numeroCelda = numeroCelda + 2;
//        creaCeldaEncabezado(filaEncabezado, (numeroCelda + 1), "Tiempo de reparación y mantenimiento"); numeroCelda = numeroCelda + 2;

    }

    // crea una celda de encabezado (las del título) y añade el estilo
    private void creaCeldaEncabezado(Row filaEncabezado, int numeroCelda, String valor) {
        final Cell celdaEncabezado = filaEncabezado.createCell(numeroCelda);
        celdaEncabezado.setCellValue(valor);
        celdaEncabezado.setCellStyle(estiloTitulo);
        //CellRangeAdress(RowFrom, RowTo, ColFrom, ColTo);

    }

    private void unirCeldas(Sheet sheet, int rowFrom, int rowTo, int colFrom, int colTo){
        sheet.addMergedRegion(new CellRangeAddress(
                rowFrom, //first row (0-based)
                rowTo, //last row  (0-based)
                colFrom, //first column (0-based)
                colTo  //last column  (0-based)
        ));
    }

    // ajusta el ancho de las columnas en función de su contenido
    private void ajustaColumna(Sheet sheet, int numeroCelda, String valor){
        sheet.setColumnWidth(numeroCelda,((valor.length() + 4)*256));
    }

    // devuelve el estilo que tendrán las celdas del título (negrita y color de fondo azul)
    private CellStyle getEstiloTitulo() {
        final CellStyle cellStyle = libro.createCellStyle();
        final Font cellFont = libro.createFont();
        cellFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle.setFont(cellFont);
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setAlignment(cellStyle.ALIGN_CENTER);

        return cellStyle;
    }

    // devuelve el estilo que tendrán las celdas con fórmula (color de fondo gris claro)
    private CellStyle getEstiloCeldaConFormula() {
        final CellStyle cellStyle = libro.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    // crea una nueva fila a continuación de la anterior
    private Row getNuevaFila(Sheet sheet) {
        return sheet.createRow(sheet.getPhysicalNumberOfRows());
    }

    // Devuelve el libro
    public Workbook getLibro() {
        return libro;
    }

}
