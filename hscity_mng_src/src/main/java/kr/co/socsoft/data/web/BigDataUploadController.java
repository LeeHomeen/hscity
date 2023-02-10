package kr.co.socsoft.data.web;


import egovframework.com.cmm.EgovUserDetailsHelper;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.socsoft.common.code.service.CodeService;
import kr.co.socsoft.common.vo.CodeVO;
import kr.co.socsoft.common.vo.JqgridVO;
import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.data.service.BigDataUploadService;
import kr.co.socsoft.data.vo.DataTableInfoVO;
import kr.co.socsoft.data.vo.DataUploadScheduleVO;
import kr.co.socsoft.data.vo.DbUpldLogVO;
import kr.co.socsoft.data.vo.TextdataUploadManualVO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/data/upload")
public class BigDataUploadController {

    private static final Logger logger = LoggerFactory.getLogger(BigDataUploadController.class);

    @Resource(name = "propertiesService")
    private EgovPropertyService egovPropertyService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name="bigDataUploadService")
    private BigDataUploadService bigDataUploadService;
    
    
	@Value("#{globals['Globals.ftp.sendType']}")
	private String ftpSendType;
	
	@Value("#{globals['Globals.ftp.write.file.encoing']}")
	private String writeFileEncoding;

	@Value("#{globals['Globals.ftp.after.transfer.file.encoding']}")
	private String ftpTransferFileEncoding;
	


    @Autowired
    private DirectChannel ftpChannel;

    @RequestMapping(value="/bigDataUploadList.do")
    public String bigDataUploadList(Model model) {
        model.addAttribute("type", "data");
        return "/admin/data/bigDataUploadList";
    }

    @RequestMapping(value="/bigDataUploadPopup.do")
    public String bigDataUploadPopup(Model model, DataUploadScheduleVO searchVO) {
        model.addAttribute("result", bigDataUploadService.selectDataUploadSchedule(searchVO));
        return "/admin/data/bigDataUploadPopup";
    }

    @RequestMapping(value="/addBigDataUpload.do")
    @ResponseBody
    public int addBigDataUpload(Model model, DataUploadScheduleVO dataUploadScheduleVO) {
        // 날짜입력에서 하이픈(-) 제거
        // dataUploadScheduleVO.setSplitTableYm(dataUploadScheduleVO.getShceduleDay().replaceAll("-", ""));
        dataUploadScheduleVO.setShceduleDay(dataUploadScheduleVO.getShceduleDay().replaceAll("-", ""));

        return bigDataUploadService.updateDataUploadSchedule(dataUploadScheduleVO);
    }

    @RequestMapping(value="/bigDataUploadListJson.do")
    @ResponseBody
    public Map<String, Object> bigDataUploadListJson(Model model, JqgridVO jqgridVO, DataUploadScheduleVO searchVO) {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(jqgridVO.getPage());
        paginationInfo.setRecordCountPerPage(jqgridVO.getRows());
        paginationInfo.setPageSize(jqgridVO.getRows());

        searchVO.setOrderByColumn(jqgridVO.getSidx());
        searchVO.setOrderBySord(jqgridVO.getSord());
        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Map<String, Object> result = new HashMap<>();
        List<DataUploadScheduleVO> items = bigDataUploadService.selectDataUploadScheduleList(searchVO);

        int totCnt = bigDataUploadService.selectDataUploadScheduleListToCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        result.put("total", paginationInfo.getTotalPageCount());
        result.put("page", jqgridVO.getPage());
        result.put("records", totCnt);
        result.put("rows", items);

        return result;
    }

    @RequestMapping(value="/bigDataUploadExcelList.do")
    public String bigDataUploadExcelList(Model model, DataUploadScheduleVO searchVO) {
        model.addAttribute("excel_name", "대용량데이터 업로드 관리");
        model.addAttribute("excel_title", new String[]{"테이블명", "데이터명", "예약일", "입력기준년월(일)", "업로드 파일명", "상태", "얘약상태", "예약설명"});
        model.addAttribute("excel_column", new String[]{"table_id", "table_nm", "shcedule_day", "insert_stdym", "upld_file_nm", "schedule_state", "use_yn", "description"});
        model.addAttribute("data_list", bigDataUploadService.selectDataUploadScheduleExcelList(searchVO));
        return "dataExcelView";
    }

    @RequestMapping(value="/fileDataUpload.do")
    public String fileDataUpload(Model model, DataTableInfoVO dataTableInfoVO, @RequestParam(value = "code", required = false) String code) {
        List<DataTableInfoVO> items = bigDataUploadService.selectDataTableInfoList(dataTableInfoVO);

        model.addAttribute("items", items);
        model.addAttribute("code", code);
        model.addAttribute("type", "data");
        return "/admin/data/fileDataUpload";
    }

    @RequestMapping(value="/fileDataUploadAdd.do")
    public String fileDataUploadAdd(Model model, DataTableInfoVO dataTableInfoVO) throws Exception {
        MultipartFile multipartFile = dataTableInfoVO.getFile();
        String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase();

        Workbook workbook = null;
        File file = null;
        try {
            if (ext.equals("xls") || ext.equals("xlsx")) {
                String path = dataTableInfoVO.getTableId() + ".csv";
                workbook = WorkbookFactory.create(multipartFile.getInputStream());
                file = this.convertToCsv(workbook, new File(path));

                String binaryString = FileUtils.readFileToString(file, ftpSendType);

                final Message<byte[]> message = MessageBuilder.withPayload(binaryString.getBytes())
                        .setHeader("fileName", dataTableInfoVO.getTableId() + ".csv")
                        .setHeader("remoteDir", "/")
                        .build();
                        

                boolean uploadSuccess = ftpChannel.send(message, 310000);
                if (uploadSuccess) {
                    LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();

                    TextdataUploadManualVO manualVO = new TextdataUploadManualVO();
                    manualVO.setTableId(dataTableInfoVO.getTableId());
                    manualVO.setUploadFileName(dataTableInfoVO.getTableId() + ".csv");
                    manualVO.setUploadFileEncoding(ftpTransferFileEncoding);
                    //운영쪽에서 utf-8 오류 발생
                    //manualVO.setUploadFileEncoding("euc-kr");
                    manualVO.setUserId(loginVO.getUserId());

                    String stdYmd = dataTableInfoVO.getInsertStdYmd();
                    if (StringUtils.isNotEmpty(stdYmd)) {
                        manualVO.setInsertStdym(stdYmd.replaceAll("-", ""));
                    } else {
                        manualVO.setInsertStdym("");
                    }

                    logger.debug("{}", manualVO);
                    bigDataUploadService.selectTextdataUploadManual(manualVO);

                } else {
                    model.addAttribute("code", 0);
                    return "redirect:/data/upload/fileDataUpload.do";
                }

                model.addAttribute("code", 1);
                return "redirect:/data/upload/fileDataUpload.do";
            } else {
                model.addAttribute("code", 0);
                return "redirect:/data/upload/fileDataUpload.do";
            }
        } catch (Exception e) {
            logger.error("error ==> ", e);
            model.addAttribute("code", 0);
            return "redirect:/data/upload/fileDataUpload.do";
        } finally {
            if(file != null) {
                file.delete();
            }
            if(workbook != null) {
                workbook.close();
            }
        }
    }

    private File convertToCsv(Workbook workbook, File file) throws IOException {
       // FileWriter fw = null;
        BufferedWriter fw =null;
        try {
           // fw = new FileWriter(file);
        	fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getPath()),writeFileEncoding));
            for (int sheet_i = 0; sheet_i < workbook.getNumberOfSheets(); sheet_i++) {
                Sheet sheet = workbook.getSheetAt(sheet_i);

                for (int row_i = 0; row_i < sheet.getLastRowNum() + 1; row_i++) {
                    Row row = sheet.getRow(row_i);

                    if(row != null) {
                        for (int cell_i = 0; cell_i < row.getLastCellNum(); cell_i++) {
                            Cell cell = row.getCell(cell_i);
                            cell.setCellType(CellType.STRING);

                            if (cell_i == row.getLastCellNum() - 1) {
                                fw.write(cell + "");
                            } else {
                                fw.write(cell + "|");
                            }
                        }
                        fw.write("\n");
                    }
                }
            }
            fw.flush();
        } catch (IOException e) {
            logger.error("csv 파일 저장 에러 ==> {}", e);
            throw new IOException("csv 파일 저장 에러");
        } finally {
            if (fw != null) {
                fw.close();
            }
        }

        return file;
    }

    @RequestMapping(value="/dataUploadList.do")
    public String dataUploadList(Model model, DbUpldLogVO dbUpldLogVO) throws Exception {
        CodeVO codeVO = new CodeVO();
        codeVO.setSortCol("detail_cd");
        codeVO.setSort("asc");
        codeVO.setGroupCd("data_conn_scd");

        // 데이터연계방법
        model.addAttribute("dataConnScd", codeService.getCommCode(codeVO));
        model.addAttribute("type", "data");
        return "/admin/data/dataUploadList";
    }

    @RequestMapping(value="/dataUploadExcelList.do")
    public String dataUploadExcelList(Model model, DbUpldLogVO searchVO) {
        model.addAttribute("excel_name", "데이터 업로드 관리");
        model.addAttribute("excel_title", new String[]{"순번","로그일자","테이블명","데이터명","구분","타입","메세지"});
        model.addAttribute("excel_column", new String[]{"upld_log_seq", "log_date", "table_id", "table_nm", "data_conn_scd", "log_type_scd", "log_msg"});
        model.addAttribute("data_list", bigDataUploadService.selectDbUpldLogExcelList(searchVO));
        return "dataExcelView";
    }


    @RequestMapping(value="/dataUploadListJson.do")
    @ResponseBody
    public Map<String, Object> dataUploadListJson(JqgridVO jqgridVO, DbUpldLogVO searchVO) throws Exception {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(jqgridVO.getPage());
        paginationInfo.setRecordCountPerPage(jqgridVO.getRows());
        paginationInfo.setPageSize(jqgridVO.getRows());

        searchVO.setOrderByColumn(jqgridVO.getSidx());
        searchVO.setOrderBySord(jqgridVO.getSord());
        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Map<String, Object> result = new HashMap<>();
        List<DbUpldLogVO> items = bigDataUploadService.selectDbUpldLogList(searchVO);

        int totCnt = bigDataUploadService.selectDbUpldLogListToCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        result.put("total", paginationInfo.getTotalPageCount());
        result.put("page", jqgridVO.getPage());
        result.put("records", totCnt);
        result.put("rows", items);

        return result;
    }

    @RequestMapping(value="/standardExcelDownload.do")
    public String standardExcelDownload(Model model, @RequestParam Map<String, Object> params) throws Exception {
        List<String> result = bigDataUploadService.selectExcelUploadDataColumnList(params);

        model.addAttribute("excel_name", params.get("tableNm"));
        model.addAttribute("excel_column", result.toArray(new String[result.size()]));

        return "standardExcelView";
    }
}
