//package kr.co.socsoft.common.until;
//
//
//import egovframework.com.cmm.view.AbstractExcelView;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFDataFormat;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import com.ibm.icu.impl.Row;
//
//import kr.co.socsoft.common.vo.AnalysisColumnVO;
//import kr.co.socsoft.common.vo.AnalysisExcelDataVO;
//import kr.co.socsoft.common.vo.AnalysisExcelVO;
//
//import java.net.URLEncoder;
//import java.util.List;
//import java.util.Map;
//
//import javax.xml.bind.DatatypeConverter;
//
//
///**
// * @author Lim Sang Su
// * @version 1.0
// * @Class Name : ExcelView.java
// * @Description : Excel View class
// * @Modification Information
// * @see
// * @since 2017-10-11
// */
//public class ExcelView extends AbstractExcelView{
//
//    private String EXCEL_FILE_NAME = "분석_결과";
//    private final String EXCEL_FONT_NAME = "맑은 고딕";
//    // private final int EXCEL_HEADER_ROW_NUMBER = 20;
//    private int EXCEL_HEADER_ROW_NUMBER = 20;
//
//    @Override
//    protected void buildExcelDocument(Map<String, Object> ModelMap, SXSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        AnalysisExcelDataVO datas = (AnalysisExcelDataVO) ModelMap.get("excelData");
//        for (int i = 0, len = datas.getSheets().size(); i < len; i++) {
//            AnalysisExcelVO data = datas.getSheets().get(i);
//            Sheet sheet = workbook.createSheet(data.getSheetName());
//            sheet.setColumnWidth(0, 200 * 5);
//            sheet.setColumnWidth(1, 400 * 10);
//            sheet.setColumnWidth(2, 400 * 10);
//            sheet.setColumnWidth(3, 400 * 10);
//            sheet.setColumnWidth(4, 400 * 10);
//
//            // 엑셀 이미지 추가
//            /*
//            String base64 = data.getChartURI().split(",")[1];
//            byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64);
//
//            int pictureIdx = workbook.addPicture(imageBytes, XSSFWorkbook.PICTURE_TYPE_PNG);
//            int pictureIdx2 = workbook.addPicture(imageBytes, XSSFWorkbook.PICTURE_TYPE_PNG);
//            CreationHelper helper = workbook.getCreationHelper();
//            Drawing drawing = sheet.createDrawingPatriarch();
//            ClientAnchor anchor = helper.createClientAnchor();
//
//            anchor.setRow1(0);
//            anchor.setCol1(1);
//
//            Picture pict = drawing.createPicture(anchor, pictureIdx);
//            pict.resize();
//
//            Picture pict2 = drawing.createPicture(anchor, pictureIdx2);
//            pict2.resize();
//            */
//
//            CreationHelper helper = workbook.getCreationHelper();
//            Drawing drawing = sheet.createDrawingPatriarch();
//            if (data.getChartURIList() != null) {
//                String[] imgUri = data.getChartURIList();
//
//                EXCEL_HEADER_ROW_NUMBER = data.getChartURIList().length * 13;
//
//                for (int j = 0; j < imgUri.length; j++) {
//                    String base64 = imgUri[j].split(",")[1];
//                    byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64);
//                    int pictureIdx = workbook.addPicture(imageBytes, XSSFWorkbook.PICTURE_TYPE_PNG);
//                    ClientAnchor anchor = helper.createClientAnchor();
//                    anchor.setRow1(j * 13);
//                    anchor.setCol1(1);
//
//                    Picture pict = drawing.createPicture(anchor, pictureIdx);
//                    pict.resize();
//                }
//            } else {
//                if (data.getGrid().getRows() != null) {
//                    EXCEL_HEADER_ROW_NUMBER = 2;
//                } else {
//                    EXCEL_HEADER_ROW_NUMBER = 0;
//                }
//            }
//
//            Font headerFont = workbook.createFont();
//            headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
//            headerFont.setFontName(EXCEL_FONT_NAME);
//            headerFont.setFontHeightInPoints((short) 10);
//
//            CellStyle headerCellStyle = workbook.createCellStyle();
//            headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//            headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
//            headerCellStyle.setFont(headerFont);
//
//            // 테이블 데이터가 없고 그래프만 있는 경우 예외처리
//            if (data.getGrid() != null) {
//                // 헤더 데이터
//                Row row = sheet.createRow(EXCEL_HEADER_ROW_NUMBER);
//
//                List<AnalysisColumnVO> columns = data.getGrid().getColumns();
//                int intForAdd = 0;
//                if (data.getGrid().getRows() != null) {
//                    intForAdd = 1;
//                }
//                for (int columnsIndex = 0, columnsLength = columns.size(); columnsIndex < columnsLength; columnsIndex++) {
//                    AnalysisColumnVO column = columns.get(columnsIndex);
//                    Cell cell = row.createCell(columnsIndex + intForAdd);
//                    cell.setCellValue(column.getNm());
//                    cell.setCellStyle(headerCellStyle);
//                }
//
//                Font nomalFont = workbook.createFont();
//                nomalFont.setFontName(EXCEL_FONT_NAME);
//                nomalFont.setFontHeightInPoints((short) 9);
//
//                CellStyle nomalCellStyle = workbook.createCellStyle();
//                nomalCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//                nomalCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
//                nomalCellStyle.setFont(nomalFont);
//
//                CellStyle numberCellStyle = workbook.createCellStyle();
//                numberCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//                numberCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
//                XSSFDataFormat df = (XSSFDataFormat) workbook.createDataFormat();
//                numberCellStyle.setDataFormat(df.getFormat("#,###,###"));
//                numberCellStyle.setFont(nomalFont);
//                // 테이블 로우가 없고 컬럼만 있는 경우 예외처리 (템플릿)
//                if (data.getGrid().getRows() != null) {
//                    // 표 데이터
//                    List<Map<String, Object>> rows = data.getGrid().getRows();
//                    for (int rowsIndex = 0, rowsLength = rows.size(); rowsIndex < rowsLength; rowsIndex++) {
//                        row = sheet.createRow((EXCEL_HEADER_ROW_NUMBER + 1) + rowsIndex);
//                        Map<String, Object> rowData = rows.get(rowsIndex);
//                        for (int columnsIndex = 0, columnsLength = columns.size(); columnsIndex < columnsLength; columnsIndex++) {
//                            AnalysisColumnVO column = columns.get(columnsIndex);
//                            Cell cell = row.createCell(columnsIndex + 1);
//                            cell.setCellValue(rowData.get(column.getCd()).toString());
//                            cell.setCellStyle(nomalCellStyle);
//                        }
//                    }
//
//                } else {
//                    EXCEL_FILE_NAME = "엑셀업로드_템플릿";
//                }
//            }
//        }
//
//        response.setContentType("Application/Msexcel");
//        response.setHeader("Content-Disposition", "ATTachment; Filename=" + URLEncoder.encode(EXCEL_FILE_NAME, "UTF-8") + ".xlsx");
//    }
//}