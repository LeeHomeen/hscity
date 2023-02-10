package kr.co.socsoft.common.vo;

public class AnalysisExcelVO {
    private String sheetName;
    private String chartURI;
    private AnalysisGridVO grid;
    private String[] chartURIList;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getChartURI() {
        return chartURI;
    }

    public void setChartURI(String chartURI) {
        this.chartURI = chartURI;
    }

    public AnalysisGridVO getGrid() {
        return grid;
    }

    public void setGrid(AnalysisGridVO grid) {
        this.grid = grid;
    }

    public String[] getChartURIList() {
        return chartURIList;
    }

    public void setChartURIList(String[] chartURIList) {
        this.chartURIList = chartURIList;
    }
}
