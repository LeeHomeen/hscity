package kr.co.socsoft.common.vo;

import java.util.List;
import java.util.Map;

public class AnalysisGridVO {
    private List<AnalysisColumnVO> columns;
    private List<Map<String, Object>> rows;

    public List<AnalysisColumnVO> getColumns() {
        return columns;
    }

    public void setColumns(List<AnalysisColumnVO> columns) {
        this.columns = columns;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }
}
