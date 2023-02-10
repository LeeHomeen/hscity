package kr.co.socsoft;

import kr.co.socsoft.util.DownloadUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class StandardExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String excelName = (String) model.get("excel_name");
        String[] excelColumn = (String[]) model.get("excel_column");

        HSSFSheet worksheet = null;
        HSSFRow row = null;

        worksheet = wb.createSheet();

        CellStyle cs1 = wb.createCellStyle();
        cs1.setAlignment(HorizontalAlignment.RIGHT);
        cs1.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());

        CellStyle cs2 = wb.createCellStyle();
        cs2.setAlignment(HorizontalAlignment.RIGHT);

        Cell c = null;

        // 헤더 생성
        row = worksheet.createRow(0);
        for (int i = 0; i < excelColumn.length; i++) {
            c = row.createCell(i);
            c.setCellStyle(cs1);
            c.setCellValue(excelColumn[i]);
        }

        response.setContentType(this.getContentType());
        response.setHeader("Content-Disposition", "ATTachment; Filename=" + DownloadUtil.getDisposition(excelName, DownloadUtil.getBrowser(request)) + ".xls");
    }
}
