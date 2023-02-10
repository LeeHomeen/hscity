package kr.co.socsoft;

import kr.co.socsoft.util.DownloadUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class DataExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] excel_title = (String[]) model.get("excel_title");
        String[] excel_column = (String[]) model.get("excel_column");
        List<Map> excel_data = (List<Map>) model.get("data_list");

        HSSFSheet worksheet = wb.createSheet();
        HSSFFont font = wb.createFont();

        /* 엑셀 스타일 설정 */
        CellStyle cs1 = wb.createCellStyle();
        cs1.setAlignment(HorizontalAlignment.CENTER);
        cs1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        cs1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // font.setBold(true);
        cs1.setFont(font);

        Cell c = null;
        Row row = null;

        row = worksheet.createRow(0); // 헤더 생성
        for (int i = 0; i < excel_title.length; i++) {
            c = row.createCell(i);
            c.setCellStyle(cs1);
            c.setCellValue(excel_title[i]);
        }

        String tmp = "";
        for (int i = 0; i < excel_data.size(); i++) {
            row = worksheet.createRow(i + 1);
            Map map = (Map) excel_data.get(i);

            for (int column = 0; column < excel_column.length; column++) {
                tmp = "" + map.get(excel_column[column]);
                if ("null".equals(tmp)) {
                    tmp = "";
                }
                c = row.createCell(column);
                c.setCellValue(tmp);
            }
        }

        // 엑셀 컬럼 오토사이즈
        for (int column = 0; column < excel_column.length; column++) {
            worksheet.autoSizeColumn(column);
        }

        response.setContentType(this.getContentType());
        response.setHeader("Content-Disposition", "ATTachment; Filename=" + DownloadUtil.getDisposition((String) model.get("excel_name"), DownloadUtil.getBrowser(request)) + ".xls");
    }
}
