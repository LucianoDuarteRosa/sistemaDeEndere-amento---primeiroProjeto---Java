package Utils;

import Entidades.Enderecamento;
import Entidades.Endereco;
import Entidades.Inventario;
import Entidades.Lista;
import bdProd.DAOEnd;
import bdProd.DAOEnderecamento;
import bdProd.DAOLista;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class Impressao {

    public static void onImpressao(Lista lista) throws FileNotFoundException, IOException, SQLException {
        Workbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Planilha");
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        List<Enderecamento> list = new ArrayList<>();
        Enderecamento end = new Enderecamento();
        list = DAOLista.findAllList(lista);

        // primeira linha da impressão
        HSSFRow header = sheet.createRow(0);
        sheet.setColumnWidth(0, 8 * 256);
        sheet.setColumnWidth(1, 50 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 8 * 256);
        sheet.setColumnWidth(4, 8 * 256);
        sheet.setColumnWidth(5, 8 * 256);
        sheet.setColumnWidth(6, 8 * 256);
        sheet.setColumnWidth(7, 20 * 256);
        HSSFCell headerCell0 = header.createCell(0);
        HSSFCell headerCell1 = header.createCell(1);
        HSSFCell headerCell2 = header.createCell(2);
        HSSFCell headerCell3 = header.createCell(3);
        HSSFCell headerCell4 = header.createCell(4);
        HSSFCell headerCell5 = header.createCell(5);
        HSSFCell headerCell6 = header.createCell(6);
        HSSFCell headerCell7 = header.createCell(7);
        headerCell0.setCellValue("Lista : " + lista.getId() + "       Nome: " + lista.getNome());
        CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 7);
        sheet.addMergedRegion(mergedRegion);
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom((short) BorderStyle.THIN.ordinal());
        style.setBorderTop((short) BorderStyle.THIN.ordinal());
        style.setBorderRight((short) BorderStyle.THIN.ordinal());
        style.setBorderLeft((short) BorderStyle.THIN.ordinal());
        HSSFFont font = (HSSFFont) workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment((short) HorizontalAlignment.CENTER.ordinal());
        headerCell0.setCellStyle(style);
        headerCell1.setCellStyle(style);
        headerCell2.setCellStyle(style);
        headerCell3.setCellStyle(style);
        headerCell4.setCellStyle(style);
        headerCell5.setCellStyle(style);
        headerCell6.setCellStyle(style);
        headerCell7.setCellStyle(style);

        // segunda linha da impressão
        HSSFRow header2 = sheet.createRow(1);
        HSSFCell headerCellCodInt = header2.createCell(0);
        HSSFCell headerCellDesc = header2.createCell(1);
        HSSFCell headerCellCodFab = header2.createCell(2);
        HSSFCell headerCellTon = header2.createCell(3);
        HSSFCell headerCellBit = header2.createCell(4);
        HSSFCell headerCellRua = header2.createCell(5);
        HSSFCell headerCellPred = header2.createCell(6);
        HSSFCell headerCellObs = header2.createCell(7);
        headerCellCodInt.setCellValue("CodInt");
        headerCellDesc.setCellValue("Descrição");
        headerCellCodFab.setCellValue("CodFab");
        headerCellTon.setCellValue("Ton");
        headerCellBit.setCellValue("Bit");
        headerCellRua.setCellValue("Rua");
        headerCellPred.setCellValue("Predio");
        headerCellObs.setCellValue("Observação");
        headerCellCodInt.setCellStyle(style);
        headerCellDesc.setCellStyle(style);
        headerCellCodFab.setCellStyle(style);
        headerCellTon.setCellStyle(style);
        headerCellBit.setCellStyle(style);
        headerCellRua.setCellStyle(style);
        headerCellPred.setCellStyle(style);
        headerCellObs.setCellStyle(style);

        //estilo para os dados da lista no for
        CellStyle style1 = workbook.createCellStyle();
        style1.setBorderBottom((short) BorderStyle.THIN.ordinal());
        style1.setBorderTop((short) BorderStyle.THIN.ordinal());
        style1.setBorderRight((short) BorderStyle.THIN.ordinal());
        style1.setBorderLeft((short) BorderStyle.THIN.ordinal());

        int rownum = 2;
        HSSFRow row = sheet.createRow(rownum);

        for (Enderecamento enderecamento : list) {
            row = sheet.createRow(rownum++);
            HSSFCell headerCellCodInt2 = row.createCell(0);
            headerCellCodInt2.setCellValue(enderecamento.getCodInt());
            headerCellCodInt2.setCellStyle(style1);
            HSSFCell headerCellDesc2 = row.createCell(1);
            headerCellDesc2.setCellValue(enderecamento.getDescricao());
            headerCellDesc2.setCellStyle(style1);
            HSSFCell headerCellCodFab2 = row.createCell(2);
            headerCellCodFab2.setCellValue(enderecamento.getCodFab());
            headerCellCodFab2.setCellStyle(style1);
            HSSFCell headerCellTon2 = row.createCell(3);
            headerCellTon2.setCellValue(enderecamento.getTon());
            headerCellTon2.setCellStyle(style1);
            HSSFCell headerCellBit2 = row.createCell(4);
            headerCellBit2.setCellValue(enderecamento.getBitola());
            headerCellBit2.setCellStyle(style1);
            HSSFCell headerCellRua2 = row.createCell(5);
            headerCellRua2.setCellValue(enderecamento.getRua());
            headerCellRua2.setCellStyle(style1);
            HSSFCell headerCellPred2 = row.createCell(6);
            headerCellPred2.setCellValue(enderecamento.getPredio());
            headerCellPred2.setCellStyle(style1);
            HSSFCell headerCellObs2 = row.createCell(7);
            headerCellObs2.setCellValue(enderecamento.getObservacao());
            headerCellObs2.setCellStyle(style1);

        }
        FileOutputStream outputStream = new FileOutputStream("Planilha.xls");
        workbook.write(outputStream);

        try {
            File file = new File("Planilha.xls");
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Criar arquivo", null, "Arquivo não foi criado corretamente ou diretório desconhecido.", Alert.AlertType.ERROR);
        }
    }

    public static void impressaoInventario(List<Inventario> list) throws IOException {
        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm");
        String dataHoraString = dataHora.format(formatter);
        Workbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Inventario " + dataHoraString);
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);

        // primeira linha da impressão
        HSSFRow header = sheet.createRow(0);
        sheet.setColumnWidth(0, 8 * 256);
        sheet.setColumnWidth(1, 50 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 8 * 256);
        sheet.setColumnWidth(5, 10 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 12 * 256);
        sheet.setColumnWidth(8, 8 * 256);
        sheet.setColumnWidth(9, 8 * 256);
        sheet.setColumnWidth(10, 8 * 256);
        sheet.setColumnWidth(11, 8 * 256);
        sheet.setColumnWidth(12, 8 * 256);
        sheet.setColumnWidth(13, 8 * 256);
        sheet.setColumnWidth(14, 8 * 256);
        sheet.setColumnWidth(15, 8 * 256);
        sheet.setColumnWidth(16, 8 * 256);
        sheet.setColumnWidth(17, 8 * 256);
        sheet.setColumnWidth(18, 8 * 256);
        sheet.setColumnWidth(19, 8 * 256);
        sheet.setColumnWidth(20, 8 * 256);
        sheet.setColumnWidth(21, 8 * 256);
        sheet.setColumnWidth(22, 8 * 256);
        sheet.setColumnWidth(23, 8 * 256);
        sheet.setColumnWidth(24, 10 * 256);

        HSSFCell headerCell0 = header.createCell(0);
        HSSFCell headerCell1 = header.createCell(1);
        HSSFCell headerCell2 = header.createCell(2);
        HSSFCell headerCell3 = header.createCell(3);
        HSSFCell headerCell4 = header.createCell(4);
        HSSFCell headerCell5 = header.createCell(5);
        HSSFCell headerCell6 = header.createCell(6);
        HSSFCell headerCell7 = header.createCell(7);
        HSSFCell headerCell8 = header.createCell(8);
        HSSFCell headerCell9 = header.createCell(9);
        HSSFCell headerCell10 = header.createCell(10);
        HSSFCell headerCell11 = header.createCell(11);
        HSSFCell headerCell12 = header.createCell(12);
        HSSFCell headerCell13 = header.createCell(13);
        HSSFCell headerCell14 = header.createCell(14);
        HSSFCell headerCell15 = header.createCell(15);
        HSSFCell headerCell16 = header.createCell(16);
        HSSFCell headerCell17 = header.createCell(17);
        HSSFCell headerCell18 = header.createCell(18);
        HSSFCell headerCell19 = header.createCell(19);
        HSSFCell headerCell20 = header.createCell(20);
        HSSFCell headerCell21 = header.createCell(21);
        HSSFCell headerCell22 = header.createCell(22);
        HSSFCell headerCell23 = header.createCell(23);
        HSSFCell headerCell24 = header.createCell(24);

        headerCell0.setCellValue("Inventário: " + dataHoraString);
        CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 24);
        sheet.addMergedRegion(mergedRegion);
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom((short) BorderStyle.THIN.ordinal());
        style.setBorderTop((short) BorderStyle.THIN.ordinal());
        style.setBorderRight((short) BorderStyle.THIN.ordinal());
        style.setBorderLeft((short) BorderStyle.THIN.ordinal());
        HSSFFont font = (HSSFFont) workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment((short) HorizontalAlignment.CENTER.ordinal());
        headerCell0.setCellStyle(style);
        headerCell1.setCellStyle(style);
        headerCell2.setCellStyle(style);
        headerCell3.setCellStyle(style);
        headerCell4.setCellStyle(style);
        headerCell5.setCellStyle(style);
        headerCell6.setCellStyle(style);
        headerCell7.setCellStyle(style);
        headerCell8.setCellStyle(style);
        headerCell9.setCellStyle(style);
        headerCell10.setCellStyle(style);
        headerCell11.setCellStyle(style);
        headerCell12.setCellStyle(style);
        headerCell13.setCellStyle(style);
        headerCell14.setCellStyle(style);
        headerCell15.setCellStyle(style);
        headerCell16.setCellStyle(style);
        headerCell17.setCellStyle(style);
        headerCell18.setCellStyle(style);
        headerCell19.setCellStyle(style);
        headerCell20.setCellStyle(style);
        headerCell21.setCellStyle(style);
        headerCell22.setCellStyle(style);
        headerCell23.setCellStyle(style);
        headerCell24.setCellStyle(style);

        // segunda linha da impressão
        HSSFRow header2 = sheet.createRow(1);
        HSSFCell headerCellCodInt = header2.createCell(0);
        HSSFCell headerCellDesc = header2.createCell(1);
        HSSFCell headerCellCodFab = header2.createCell(2);
        HSSFCell headerCellObs = header2.createCell(3);
        HSSFCell headerCellQntMinVenda = header2.createCell(4);
        HSSFCell headerCellEstoque = header2.createCell(5);
        HSSFCell headerCellDif1 = header2.createCell(6);
        HSSFCell headerCellQntcx = header2.createCell(7);
        HSSFCell headerCellCx1 = header2.createCell(8);
        HSSFCell headerCellTon1 = header2.createCell(9);
        HSSFCell headerCellBit1 = header2.createCell(10);
        HSSFCell headerCellCx2 = header2.createCell(11);
        HSSFCell headerCellTon2 = header2.createCell(12);
        HSSFCell headerCellBit2 = header2.createCell(13);
        HSSFCell headerCellCx3 = header2.createCell(14);
        HSSFCell headerCellTon3 = header2.createCell(15);
        HSSFCell headerCellBit3 = header2.createCell(16);
        HSSFCell headerCellCx4 = header2.createCell(17);
        HSSFCell headerCellTon4 = header2.createCell(18);
        HSSFCell headerCellBit4 = header2.createCell(19);
        HSSFCell headerCellCx5 = header2.createCell(20);
        HSSFCell headerCellTon5 = header2.createCell(21);
        HSSFCell headerCellBit5 = header2.createCell(22);
        HSSFCell headerCellCusto = header2.createCell(23);
        HSSFCell headerCellCustoTotal = header2.createCell(24);

        headerCellCodInt.setCellValue("CodInt");
        headerCellDesc.setCellValue("Descrição");
        headerCellCodFab.setCellValue("CodFab");
        headerCellObs.setCellValue("Observação");
        headerCellQntMinVenda.setCellValue("M2");
        headerCellEstoque.setCellValue("Estoque");
        headerCellDif1.setCellValue("Dif");
        headerCellQntcx.setCellValue("TotalCx");

        headerCellCx1.setCellValue("Cx");
        headerCellTon1.setCellValue("Ton");
        headerCellBit1.setCellValue("Bit");
        headerCellCx2.setCellValue("Cx");
        headerCellTon2.setCellValue("Ton");
        headerCellBit2.setCellValue("Bit");
        headerCellCx3.setCellValue("Cx");
        headerCellTon3.setCellValue("Ton");
        headerCellBit3.setCellValue("Bit");
        headerCellCx4.setCellValue("Cx");
        headerCellTon4.setCellValue("Ton");
        headerCellBit4.setCellValue("Bit");
        headerCellCx5.setCellValue("Cx");
        headerCellTon5.setCellValue("Ton");
        headerCellBit5.setCellValue("Bit");
        headerCellCusto.setCellValue("Custo");
        headerCellCustoTotal.setCellValue("Custo total");

        headerCellCodInt.setCellStyle(style);
        headerCellDesc.setCellStyle(style);
        headerCellCodFab.setCellStyle(style);
        headerCellObs.setCellStyle(style);
        headerCellQntMinVenda.setCellStyle(style);
        headerCellEstoque.setCellStyle(style);
        headerCellDif1.setCellStyle(style);
        headerCellQntcx.setCellStyle(style);
        headerCellCx1.setCellStyle(style);
        headerCellTon1.setCellStyle(style);
        headerCellBit1.setCellStyle(style);
        headerCellCx2.setCellStyle(style);
        headerCellTon2.setCellStyle(style);
        headerCellBit2.setCellStyle(style);
        headerCellCx3.setCellStyle(style);
        headerCellTon3.setCellStyle(style);
        headerCellBit3.setCellStyle(style);
        headerCellCx4.setCellStyle(style);
        headerCellTon4.setCellStyle(style);
        headerCellBit4.setCellStyle(style);
        headerCellCx5.setCellStyle(style);
        headerCellTon5.setCellStyle(style);
        headerCellBit5.setCellStyle(style);
        headerCellCusto.setCellStyle(style);
        headerCellCustoTotal.setCellStyle(style);

        //estilo para os dados da lista no for
        CellStyle style1 = workbook.createCellStyle();
        style1.setBorderBottom((short) BorderStyle.THIN.ordinal());
        style1.setBorderTop((short) BorderStyle.THIN.ordinal());
        style1.setBorderRight((short) BorderStyle.THIN.ordinal());
        style1.setBorderLeft((short) BorderStyle.THIN.ordinal());

        int rownum = 2;
        HSSFRow row = sheet.createRow(rownum);

        for (Inventario inventario : list) {
            row = sheet.createRow(rownum++);
            HSSFCell headerCellCodInt2 = row.createCell(0);
            headerCellCodInt2.setCellValue(inventario.getCodInterno());
            headerCellCodInt2.setCellStyle(style1);

            HSSFCell headerCellDesc2 = row.createCell(1);
            headerCellDesc2.setCellValue(inventario.getDescricao());
            headerCellDesc2.setCellStyle(style1);

            HSSFCell headerCellCodFab2 = row.createCell(2);
            headerCellCodFab2.setCellValue(inventario.getCodFabricante());
            headerCellCodFab2.setCellStyle(style1);

            HSSFCell headerCellObs2 = row.createCell(3);
            headerCellObs2.setCellValue(inventario.getObs());
            headerCellObs2.setCellStyle(style1);

            HSSFCell headerCellQuantMinVenda = row.createCell(4);
            headerCellQuantMinVenda.setCellValue(inventario.getQuantMinVenda());
            headerCellQuantMinVenda.setCellStyle(style1);

            HSSFCell headerCellEst2 = row.createCell(5);
            headerCellEst2.setCellValue(inventario.getEstoque());
            headerCellEst2.setCellStyle(style1);

            HSSFCell headerCellDif = row.createCell(6);
            headerCellDif.setCellFormula("((H" + rownum + " * E" + rownum + ") - " + "F" + rownum + ")");
            headerCellDif.setCellStyle(style1);

            HSSFCell headerCellTotalCx = row.createCell(7);
            headerCellTotalCx.setCellFormula("(I" + rownum + "+ L" + rownum + "+ O" + rownum + "+ R" + rownum + "+ U" + rownum + ")");
            headerCellTotalCx.setCellStyle(style1);

            HSSFCell headerCellCx01 = row.createCell(8);
            if (inventario.getCx1() == 0) {
            } else {
                headerCellCx01.setCellValue(inventario.getCx1());
            }
            headerCellCx01.setCellStyle(style1);

            HSSFCell headerCellTon01 = row.createCell(9);
            headerCellTon01.setCellValue(inventario.getTon1());
            headerCellTon01.setCellStyle(style1);

            HSSFCell headerCellBit01 = row.createCell(10);
            headerCellBit01.setCellValue(inventario.getBit1());
            headerCellBit01.setCellStyle(style1);

            HSSFCell headerCellCx02 = row.createCell(11);
            if (inventario.getCx2() == 0) {
            } else {
                headerCellCx02.setCellValue(inventario.getCx2());
            }
            headerCellCx02.setCellStyle(style1);

            HSSFCell headerCellTon02 = row.createCell(12);
            headerCellTon02.setCellValue(inventario.getTon2());
            headerCellTon02.setCellStyle(style1);

            HSSFCell headerCellBit02 = row.createCell(13);
            headerCellBit02.setCellValue(inventario.getBit2());
            headerCellBit02.setCellStyle(style1);

            HSSFCell headerCellCx03 = row.createCell(14);
            if (inventario.getCx3() == 0) {
            } else {
                headerCellCx03.setCellValue(inventario.getCx3());
            }
            headerCellCx03.setCellStyle(style1);

            HSSFCell headerCellTon03 = row.createCell(15);
            headerCellTon03.setCellValue(inventario.getTon3());
            headerCellTon03.setCellStyle(style1);

            HSSFCell headerCellBit03 = row.createCell(16);
            headerCellBit03.setCellValue(inventario.getBit3());
            headerCellBit03.setCellStyle(style1);

            HSSFCell headerCellCx04 = row.createCell(17);
            if (inventario.getCx4() == 0) {
            } else {
                headerCellCx04.setCellValue(inventario.getCx4());
            }
            headerCellCx04.setCellStyle(style1);

            HSSFCell headerCellTon04 = row.createCell(18);
            headerCellTon04.setCellValue(inventario.getTon4());
            headerCellTon04.setCellStyle(style1);

            HSSFCell headerCellBit04 = row.createCell(19);
            headerCellBit04.setCellValue(inventario.getBit4());
            headerCellBit04.setCellStyle(style1);

            HSSFCell headerCellCx05 = row.createCell(20);
            if (inventario.getCx5() == 0) {
            } else {
                headerCellCx05.setCellValue(inventario.getCx5());
            }
            headerCellCx05.setCellStyle(style1);

            HSSFCell headerCellTon05 = row.createCell(21);
            headerCellTon05.setCellValue(inventario.getTon5());
            headerCellTon05.setCellStyle(style1);

            HSSFCell headerCellBit05 = row.createCell(22);
            headerCellBit05.setCellValue(inventario.getBit5());
            headerCellBit05.setCellStyle(style1);

            HSSFCell headerCellCusto1 = row.createCell(23);
            headerCellCusto1.setCellValue(inventario.getCusto());
            headerCellCusto1.setCellStyle(style1);

            HSSFCell headerCellCustoTotal1 = row.createCell(24);
            headerCellCustoTotal1.setCellFormula("(G" + rownum + "* X" + rownum + ")");
            headerCellCustoTotal1.setCellStyle(style1);

        }

        try {
            FileOutputStream outputStream = new FileOutputStream("Inventario " + dataHoraString + ".xls");
            workbook.write(outputStream);
            File file = new File("Inventario " + dataHoraString + ".xls");
            Desktop.getDesktop().open(file);

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Criar arquivo", null, "Arquivo não foi criado corretamente ou diretório desconhecido.", Alert.AlertType.ERROR);
        }

    }

    public static void impressaoInvOcorrencias(List<Inventario> list) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Inventário Ocorrências");
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);

        // primeira linha da impressão
        HSSFRow header = sheet.createRow(0);
        sheet.setColumnWidth(0, 8 * 256);
        sheet.setColumnWidth(1, 50 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 8 * 256);
        sheet.setColumnWidth(5, 10 * 256);
        sheet.setColumnWidth(6, 14 * 256);
        sheet.setColumnWidth(7, 10 * 256);

        HSSFCell headerCell0 = header.createCell(0);
        HSSFCell headerCell1 = header.createCell(1);
        HSSFCell headerCell2 = header.createCell(2);
        HSSFCell headerCell3 = header.createCell(3);
        HSSFCell headerCell4 = header.createCell(4);
        HSSFCell headerCell5 = header.createCell(5);
        HSSFCell headerCell6 = header.createCell(6);
        HSSFCell headerCell7 = header.createCell(7);

        headerCell0.setCellValue("Inventário Ocorrências");
        CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 7);
        sheet.addMergedRegion(mergedRegion);
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom((short) BorderStyle.THIN.ordinal());
        style.setBorderTop((short) BorderStyle.THIN.ordinal());
        style.setBorderRight((short) BorderStyle.THIN.ordinal());
        style.setBorderLeft((short) BorderStyle.THIN.ordinal());
        HSSFFont font = (HSSFFont) workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment((short) HorizontalAlignment.CENTER.ordinal());
        headerCell0.setCellStyle(style);
        headerCell1.setCellStyle(style);
        headerCell2.setCellStyle(style);
        headerCell3.setCellStyle(style);
        headerCell4.setCellStyle(style);
        headerCell5.setCellStyle(style);
        headerCell6.setCellStyle(style);
        headerCell7.setCellStyle(style);

        // segunda linha da impressão
        HSSFRow header2 = sheet.createRow(1);
        HSSFCell headerCellCodInt = header2.createCell(0);
        HSSFCell headerCellDesc = header2.createCell(1);
        HSSFCell headerCellCodFab = header2.createCell(2);
        HSSFCell headerCellObs = header2.createCell(3);
        HSSFCell headerCellQntMinVenda = header2.createCell(4);
        HSSFCell headerCellEstoque = header2.createCell(5);
        HSSFCell headerCellTon = header2.createCell(6);
        HSSFCell headerCellBit = header2.createCell(7);

        headerCellCodInt.setCellValue("CodInt");
        headerCellDesc.setCellValue("Descrição");
        headerCellCodFab.setCellValue("CodFab");
        headerCellObs.setCellValue("Observação");
        headerCellQntMinVenda.setCellValue("M2");
        headerCellEstoque.setCellValue("Estoque");
        headerCellTon.setCellValue("Tonalidade");
        headerCellBit.setCellValue("Bitola");

        headerCellCodInt.setCellStyle(style);
        headerCellDesc.setCellStyle(style);
        headerCellCodFab.setCellStyle(style);
        headerCellObs.setCellStyle(style);
        headerCellQntMinVenda.setCellStyle(style);
        headerCellEstoque.setCellStyle(style);
        headerCellTon.setCellStyle(style);
        headerCellBit.setCellStyle(style);

        //estilo para os dados da lista no for
        CellStyle style1 = workbook.createCellStyle();
        style1.setBorderBottom((short) BorderStyle.THIN.ordinal());
        style1.setBorderTop((short) BorderStyle.THIN.ordinal());
        style1.setBorderRight((short) BorderStyle.THIN.ordinal());
        style1.setBorderLeft((short) BorderStyle.THIN.ordinal());

        int rownum = 2;
        HSSFRow row = sheet.createRow(rownum);

        for (Inventario inventario : list) {
            row = sheet.createRow(rownum++);
            HSSFCell headerCellCodInt2 = row.createCell(0);
            headerCellCodInt2.setCellValue(inventario.getCodInterno());
            headerCellCodInt2.setCellStyle(style1);

            HSSFCell headerCellDesc2 = row.createCell(1);
            headerCellDesc2.setCellValue(inventario.getDescricao());
            headerCellDesc2.setCellStyle(style1);

            HSSFCell headerCellCodFab2 = row.createCell(2);
            headerCellCodFab2.setCellValue(inventario.getCodFabricante());
            headerCellCodFab2.setCellStyle(style1);

            HSSFCell headerCellObs2 = row.createCell(3);
            headerCellObs2.setCellValue(inventario.getObs());
            headerCellObs2.setCellStyle(style1);

            HSSFCell headerCellQuantMinVenda = row.createCell(4);
            headerCellQuantMinVenda.setCellValue(inventario.getQuantMinVenda());
            headerCellQuantMinVenda.setCellStyle(style1);

            HSSFCell headerCellEst2 = row.createCell(5);
            headerCellEst2.setCellValue(inventario.getEstoque());
            headerCellEst2.setCellStyle(style1);

            HSSFCell headerCellTon2 = row.createCell(6);
            headerCellTon2.setCellValue(inventario.getTon1());
            headerCellTon2.setCellStyle(style1);

            HSSFCell headerCellBit2 = row.createCell(7);
            headerCellBit2.setCellValue(inventario.getBit1());
            headerCellBit2.setCellStyle(style1);

        }

        try {
            FileOutputStream outputStream = new FileOutputStream("Inventário Ocorrências.xls");
            workbook.write(outputStream);
            File file = new File("Inventário Ocorrências.xls");
            Desktop.getDesktop().open(file);

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Criar arquivo", null, "Arquivo não foi criado corretamente ou diretório desconhecido.", Alert.AlertType.ERROR);
        }

    }

    public static void exportarEnderecamento(String caminho) {
        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String dataHoraString = dataHora.format(formatter);
        Workbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Enderecamento " + dataHoraString);
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        
        int rownum = 0;
        
        List<Endereco> list1 = DAOEnd.findAll();
        for (Endereco end : list1) {
            List<Endereco> list2 = DAOEnd.findAllPredio2(end);
            for (Endereco end2 : list2) {
                // primeira linha da impressão
                HSSFRow header = sheet.createRow(rownum);
                sheet.setColumnWidth(0, 8 * 256);
                sheet.setColumnWidth(1, 50 * 256);
                sheet.setColumnWidth(2, 25 * 256);
                sheet.setColumnWidth(3, 14 * 256);
                sheet.setColumnWidth(4, 12 * 256);
                sheet.setColumnWidth(5, 8 * 256);
                sheet.setColumnWidth(6, 20 * 256);
                HSSFCell headerCell0 = header.createCell(0);
                HSSFCell headerCell1 = header.createCell(1);
                HSSFCell headerCell2 = header.createCell(2);
                HSSFCell headerCell3 = header.createCell(3);
                HSSFCell headerCell4 = header.createCell(4);
                HSSFCell headerCell5 = header.createCell(5);
                HSSFCell headerCell6 = header.createCell(6);
                headerCell0.setCellValue("Rua : " + end2.getRua() + "    Prédio: " + end2.getPredio());
                CellRangeAddress mergedRegion = new CellRangeAddress(rownum, rownum, 0, 6);
                sheet.addMergedRegion(mergedRegion);
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom((short) BorderStyle.THIN.ordinal());
                style.setBorderTop((short) BorderStyle.THIN.ordinal());
                style.setBorderRight((short) BorderStyle.THIN.ordinal());
                style.setBorderLeft((short) BorderStyle.THIN.ordinal());
                HSSFFont font = (HSSFFont) workbook.createFont();
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                font.setFontHeightInPoints((short) 12);                
                style.setFont(font);
                style.setAlignment((short) HorizontalAlignment.CENTER.ordinal());
                
                headerCell0.setCellStyle(style);
                headerCell1.setCellStyle(style);
                headerCell2.setCellStyle(style);
                headerCell3.setCellStyle(style);
                headerCell4.setCellStyle(style);
                headerCell5.setCellStyle(style);
                headerCell6.setCellStyle(style);
                rownum++;

                // segunda linha da impressão
                HSSFRow header2 = sheet.createRow(rownum);
                HSSFCell headerCellCodInt = header2.createCell(0);
                HSSFCell headerCellDesc = header2.createCell(1);
                HSSFCell headerCellCodFab = header2.createCell(2);
                HSSFCell headerCellCodBarras = header2.createCell(3);
                HSSFCell headerCellTon = header2.createCell(4);
                HSSFCell headerCellBit = header2.createCell(5);
                HSSFCell headerCellObs = header2.createCell(6);

                headerCellCodInt.setCellValue("CodInt");
                headerCellDesc.setCellValue("Descrição");
                headerCellCodFab.setCellValue("CodFab");
                headerCellCodBarras.setCellValue("CodBarras");
                headerCellTon.setCellValue("Ton");
                headerCellBit.setCellValue("Bit");
                headerCellObs.setCellValue("Observação");

                headerCellCodInt.setCellStyle(style);
                headerCellDesc.setCellStyle(style);
                headerCellCodFab.setCellStyle(style);
                headerCellCodBarras.setCellStyle(style);
                headerCellTon.setCellStyle(style);
                headerCellBit.setCellStyle(style);
                headerCellObs.setCellStyle(style);
                rownum++;

                //estilo para os dados da lista no for
                CellStyle style1 = workbook.createCellStyle();
                style1.setBorderBottom((short) BorderStyle.THIN.ordinal());
                style1.setBorderTop((short) BorderStyle.THIN.ordinal());
                style1.setBorderRight((short) BorderStyle.THIN.ordinal());
                style1.setBorderLeft((short) BorderStyle.THIN.ordinal());

                HSSFRow row = sheet.createRow(rownum);

                List<Enderecamento> list3 = DAOEnderecamento.findAllEndereco(end2);
                for (Enderecamento enderecamento : list3) {
                    row = sheet.createRow(rownum++);
                    HSSFCell headerCellCodInt2 = row.createCell(0);
                    headerCellCodInt2.setCellValue(enderecamento.getCodInt());
                    headerCellCodInt2.setCellStyle(style1);
                    HSSFCell headerCellDesc2 = row.createCell(1);
                    headerCellDesc2.setCellValue(enderecamento.getDescricao());
                    headerCellDesc2.setCellStyle(style1);
                    HSSFCell headerCellCodFab2 = row.createCell(2);
                    headerCellCodFab2.setCellValue(enderecamento.getCodFab());
                    headerCellCodFab2.setCellStyle(style1);
                    HSSFCell headerCellCodBarras2 = row.createCell(3);
                    headerCellCodBarras2.setCellValue(enderecamento.getCodBarras());
                    headerCellCodBarras2.setCellStyle(style1);
                    HSSFCell headerCellTon2 = row.createCell(4);
                    headerCellTon2.setCellValue(enderecamento.getTon());
                    headerCellTon2.setCellStyle(style1);
                    HSSFCell headerCellBit2 = row.createCell(5);
                    headerCellBit2.setCellValue(enderecamento.getBitola());
                    headerCellBit2.setCellStyle(style1);
                    HSSFCell headerCellObs2 = row.createCell(6);
                    headerCellObs2.setCellValue(enderecamento.getObservacao());
                    headerCellObs2.setCellStyle(style1);
                }
                row = sheet.createRow(rownum++);
                row = sheet.createRow(rownum++);
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(caminho + "\\" + "Enderecamento " + dataHoraString + ".xls");
            workbook.write(outputStream);
            File file = new File(caminho + "\\" + "Enderecamento " + dataHoraString + ".xls");
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Criar arquivo", null, "Arquivo não foi criado corretamente ou diretório desconhecido.", Alert.AlertType.ERROR);
        }

    }
}
