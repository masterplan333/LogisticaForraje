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
        hojaEmbolsadora.setSelected(true);
        anadeFilaEncabezadoEmbolsadora();
        hojaLogicaForraje.setSelected(true);
        anadeFilaEncabezadoLogicaForraje();

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
        Logistica log = new Logistica();
        Row filaEncabezado = getNuevaFila(hojaPicadora);
        /*Se crean el encabezado principal*/
        int numeroCelda = 0;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getT_PREP());
        unirCeldas(hojaPicadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, (numeroCelda), log.getT_TRAS());
        unirCeldas(hojaPicadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, (numeroCelda), log.getT_PICD());
        unirCeldas(hojaPicadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, (numeroCelda), log.getT_ESP());
        unirCeldas(hojaPicadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, (numeroCelda), log.getT_REPMAN());
        unirCeldas(hojaPicadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, (numeroCelda), log.getF_ROTOR());
        unirCeldas(hojaPicadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);

        /*Se crea el encabezado secundario*/
        filaEncabezado = getNuevaFila(hojaPicadora);
        numeroCelda = 0;
        //filaEncabezado.setRowNum(filaEncabezado.getRowNum() + 1);
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getIPM());
        ajustaColumna(hojaPicadora, numeroCelda, log.getIPM());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getFPM());
        ajustaColumna(hojaPicadora, numeroCelda, log.getFPM());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getSL());
        ajustaColumna(hojaPicadora, numeroCelda, log.getSL());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getLT());
        ajustaColumna(hojaPicadora, numeroCelda, log.getLT());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getIP());
        ajustaColumna(hojaPicadora, numeroCelda, log.getIP());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getFP());
        ajustaColumna(hojaPicadora, numeroCelda, log.getFP());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getITE());
        ajustaColumna(hojaPicadora, numeroCelda, log.getITE());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getFTE());
        ajustaColumna(hojaPicadora, numeroCelda, log.getFTE());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getIRM());
        ajustaColumna(hojaPicadora, numeroCelda, log.getIRM());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getFRM());
        ajustaColumna(hojaPicadora, numeroCelda, log.getFRM());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getER());
        ajustaColumna(hojaPicadora, numeroCelda, log.getER());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, log.getAR());
        ajustaColumna(hojaPicadora, numeroCelda, log.getAR());

    }

    private void anadeFilaEncabezadoEmbolsadora() {
        Embolsadora emb = new Embolsadora();
        Row filaEncabezado = getNuevaFila(hojaEmbolsadora);
        /*Se crean el encabezado principal*/
        int numeroCelda = 0;

        /*Se crean el encabezado principal*/
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getT_PREP());
        unirCeldas(hojaEmbolsadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getT_BOLSA());
        unirCeldas(hojaEmbolsadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getT_EMBOLSADO());
        unirCeldas(hojaEmbolsadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getT_ESP());
        unirCeldas(hojaEmbolsadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getT_MUERTO());
        unirCeldas(hojaEmbolsadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
//        numeroCelda = numeroCelda + 2;
//        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getT_OP_BOLSA());
//        unirCeldas(hojaEmbolsadora, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);

        /*Se crean el encabezado secundario*/
        filaEncabezado = getNuevaFila(hojaEmbolsadora);
        numeroCelda = 0;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getIPM());
        ajustaColumna(hojaEmbolsadora, numeroCelda, emb.getIPM());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getFPM());
        ajustaColumna(hojaEmbolsadora, numeroCelda,emb.getFPM() );
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getIC());
        ajustaColumna(hojaEmbolsadora, numeroCelda, emb.getIC());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getFC());
        ajustaColumna(hojaEmbolsadora, numeroCelda, emb.getFC());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getIE());
        ajustaColumna(hojaEmbolsadora, numeroCelda, emb.getIE());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getFE());
        ajustaColumna(hojaEmbolsadora, numeroCelda, emb.getFE());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getITE());
        ajustaColumna(hojaEmbolsadora, numeroCelda, emb.getITE());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getFTE());
        ajustaColumna(hojaEmbolsadora, numeroCelda, emb.getFTE());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getITM());
        ajustaColumna(hojaEmbolsadora, numeroCelda, emb.getITM());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getFTM());
        ajustaColumna(hojaEmbolsadora, numeroCelda,emb.getFTM());
//        numeroCelda = numeroCelda + 1;
//        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getI_EMBUTIDO());
//        ajustaColumna(hojaEmbolsadora, numeroCelda, emb.getI_EMBUTIDO());
//        numeroCelda = numeroCelda + 1;
//        creaCeldaEncabezado(filaEncabezado, numeroCelda, emb.getF_EMBUTIDO());
//        ajustaColumna(hojaEmbolsadora, numeroCelda, emb.getF_EMBUTIDO());

    }

    private void anadeFilaEncabezadoLogicaForraje() {
        CarroForrajero car = new CarroForrajero();
        Row filaEncabezado = getNuevaFila(hojaLogicaForraje);
        /*Se crean el encabezado principal*/
        int numeroCelda = 0;

         /*Se crean el encabezado principal*/
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getT_PREP());
        unirCeldas(hojaLogicaForraje, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getC_ACARREO());
        unirCeldas(hojaLogicaForraje, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getT_CARG_INDV());
        unirCeldas(hojaLogicaForraje, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getT_ACARREO());
        unirCeldas(hojaLogicaForraje, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getT_ESP_EMB());
        unirCeldas(hojaLogicaForraje, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getT_DES());
        unirCeldas(hojaLogicaForraje, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getT_TRANS());
        unirCeldas(hojaLogicaForraje, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getT_ESP_PIC());
        unirCeldas(hojaLogicaForraje, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);
        numeroCelda = numeroCelda + 2;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getT_REPMANT());
        unirCeldas(hojaLogicaForraje, filaEncabezado.getRowNum(), filaEncabezado.getRowNum(), numeroCelda, numeroCelda+1);

        /*Se crean el encabezado secundario*/
        filaEncabezado = getNuevaFila(hojaLogicaForraje);
        numeroCelda = 0;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getIPM());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getIPM());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getFPM());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getFPM());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getIC());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getIC());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getFC());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getFC());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getICA());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getICA());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getFCA());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getFCA());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getIA());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getIA());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getFA());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getFA());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getIEE());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getIEE());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getFEE());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getFEE());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getID());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getID());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getFD());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getFD());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getITV());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getITV());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getFTV());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getFTV());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getIEP());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getIEP());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getFEP());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getFEP());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getI());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getI());
        numeroCelda = numeroCelda + 1;
        creaCeldaEncabezado(filaEncabezado, numeroCelda, car.getF());
        ajustaColumna(hojaLogicaForraje, numeroCelda, car.getF());

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
