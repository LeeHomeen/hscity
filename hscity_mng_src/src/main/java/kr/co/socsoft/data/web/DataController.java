package kr.co.socsoft.data.web;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;

import egovframework.com.cmm.EgovUserDetailsHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.socsoft.permission.vo.UserPermitVO;
import kr.co.socsoft.util.JsonHelper;
import kr.co.socsoft.data.service.DataService;
import kr.co.socsoft.data.vo.DataTableInfoVO;
import kr.co.socsoft.data.vo.DbUpldLogVO;
import kr.co.socsoft.common.vo.DataVO;
import kr.co.socsoft.common.vo.DataLinkVO;
import kr.co.socsoft.common.vo.DataUpldLogVO;
import kr.co.socsoft.common.vo.LoginVO;


@Controller
@RequestMapping(value = "/data")
public class DataController {
	private static final Logger logger = LoggerFactory.getLogger(DataController.class);
	
	@Value("#{globals['Globals.gg.write.file.encoing']}")
	private String writeFileEncoding;
			
	@Value("#{globals['Globals.ftp.after.transfer.file.encoding']}")
	private String ftpTransferType;//euc-kr
	
	@Value("#{globals['Globals.gg.write.file.encoing.euc']}")
	private String writeFileEncoding_euc;
	
	@Value("#{globals['Global.minwon.openapi.seviceKey']}")
	private String minwonServiceKey;
	
	@Resource(name = "dataService")
	private DataService dataService;
	
	@Autowired
    private DirectChannel ftpChannel;
	
	@RequestMapping(value = "/bigDataUploadList.do")
	public ModelAndView bigDataUploadList(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("/admin/data/bigDataUploadList");
		return mv;
	}
	
	@RequestMapping(value = "/{menu}/dataStatusList.do")
	public String dataStatusList(@PathVariable(value="menu") String menu, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("type", "data");
        model.addAttribute("menu", menu);
		return "/admin/data/dataStatusList";
	}
	
	@RequestMapping(value = "/{menu}/editDataStatus.do")
	public String editDataStatus(@PathVariable(value="menu") String menu
			                   , @ModelAttribute DataVO dataVo
			                   , ModelMap model
			                   , HttpServletRequest request) throws Exception {
		String pTableId = request.getParameter("tableId");
		List<DataVO> uploadUserVoList = null;
		if (pTableId != null) {
			dataVo = dataService.getDataTableInfo(pTableId);
			uploadUserVoList = dataService.getDataUploadUser(pTableId);
		}
		model.addAttribute("dataVo", dataVo);
		model.addAttribute("dataUploadUserList", uploadUserVoList);
		model.addAttribute("type", "data");
		model.addAttribute("menu", menu);
		return "/admin/data/editDataStatus";
	}
	
	@RequestMapping(value = "/dataColumnStatusList.do")
	public ModelAndView dataColumnStatusList(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("/admin/data/dataColumnStatusList");
		return mv;
	}
	
	@RequestMapping(value = "/dataUploadList.do")
	public ModelAndView dataUploadList(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("/admin/data/dataUploadList");
		return mv;
	}
	
	@RequestMapping(value = "/fileDataUpload.do")
	public ModelAndView fileDataUpload(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.setViewName("/admin/data/fileDataUpload");
		return mv;
	}
	
	@RequestMapping(value = "/{menu}/systemLinkList.do")
	public String systemLinkList(@PathVariable(value="menu") String menu, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("type", "data");
        model.addAttribute("menu", menu);
		return "/admin/data/systemLinkList";
		

	}
	
	@RequestMapping(value = "/{menu}/gyeonggiDataLink.do")
	public String gyeonggiDataLink(@PathVariable(value="menu") String menu, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("type", "data");
        model.addAttribute("menu", menu);
		return "/admin/data/gyeonggiDataLinkList";
	}
	
	@RequestMapping(value = "/{menu}/rightsMinwonDataLink.do")
	public String rightsMinwonDataLink(@PathVariable(value="menu") String menu, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("type", "data");
        model.addAttribute("menu", menu);
		return "/admin/data/rightsMinwonDataLinkList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getExcelUploadTables.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<DataVO> getExcelUploadTables(HttpServletRequest request) throws Exception {
		List<DataVO> result = dataService.getExcelUploadTables();
        return result;
    }
	
	@ResponseBody
	@RequestMapping(value = "/getColumnsInTables.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<DataVO> getColumnsInTables(@RequestBody Map<String, Object> params) throws Exception {
		List<DataVO> result = dataService.getColumnsInTables(params.get("tableId").toString());
        return result;
    }
	
	@ResponseBody
	@RequestMapping(value = "/getGyeonggiDataLinkTableList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<DataLinkVO> getGyeonggiDataLinkTableList(HttpServletRequest request) throws Exception {
		List<DataLinkVO> result = dataService.getGyeonggiDataLinkTableList();
        return result;
    }
	
	@ResponseBody
	@RequestMapping(value = "/getGyeonggiOpenapiList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<DataLinkVO> getGyeonggiOpenapiList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		if (params.get("dataUpdPerScd") != null && !params.get("dataUpdPerScd").equals("")) {
			newParams.put("dataUpdPerScd", params.get("dataUpdPerScd").toString());
        }
		if (params.get("searchType") != null && !params.get("searchType").equals("")) {
            newParams.put("searchType", params.get("searchType").toString());
        }
		if (params.get("searchText") != null && !params.get("searchText").equals("")) {
            newParams.put("searchText", params.get("searchText").toString());
        }
		List<DataLinkVO> result = dataService.getGyeonggiOpenapiList(newParams);
        return result;
    }
	
	
	 @RequestMapping(value="/getGyeonggiOpenapiExcel.do")
	 public String getGyeonggiOpenapiExcel(Model model,DataTableInfoVO dataTableInfoVO) throws Exception {

	    	
	    	Map<String, Object> param = new HashMap<>();
	    	param.put("dataUpdPerScd",  dataTableInfoVO.getDataUpdPerScd());
	    	param.put("searchType",     dataTableInfoVO.getSearchType());
	    	param.put("searchText",     dataTableInfoVO.getSearchText());

	    	model.addAttribute("excel_name", "경기데이터드림api리스트");
	        model.addAttribute("excel_title", new String[]{"구분","테이블ID","테이블명","OpenAPI_URL","갱신설정","관리기준년월일","갱신주기","갱신일","상태"});
	        model.addAttribute("excel_column", new String[]{"data_gubun_nm","table_id","table_nm","openapi_url","openapi_time_scd_nm","mng_std_ymd_scd_nm","data_upd_per_scd_nm","last_upload_dt","status"});
	        model.addAttribute("data_list", dataService.getGyeonggiOpenapiExcel(param));
	        return "dataExcelView";
	    }
	
	
	/**
	 * 내부시스템 연계리스트
	 * @param params
	 * @param request
	 * @return 내부시스템 연계리스트
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getSystemLinkList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<DataLinkVO> getSystemLinkList(@RequestBody Map<String, Object> params, HttpServletRequest request  , Model model) throws Exception {
		
		Map<String, Object> newParams = new HashMap<>();
		if (params.get("dataConnScd") != null && !params.get("dataConnScd").equals("")) {
			newParams.put("dataConnScd", params.get("dataConnScd").toString());
        }
		if (params.get("searchType") != null && !params.get("searchType").equals("")) {
            newParams.put("searchType", params.get("searchType").toString());
        }
		if (params.get("searchText") != null && !params.get("searchText").equals("")) {
            newParams.put("searchText", params.get("searchText").toString());
        }
		List<DataLinkVO> result = dataService.getSystemLinkList(newParams);
        return result;
    }
	
	
	 @RequestMapping(value="/getSystemLinkExcel.do")
	 public String getSystemLinkExcel(Model model,DataTableInfoVO dataTableInfoVO) throws Exception {

	    	
	    	Map<String, Object> param = new HashMap<>();
	    	param.put("dataConnScd",  dataTableInfoVO.getDataConnScd());
	    	param.put("searchType",     dataTableInfoVO.getSearchType());
	    	param.put("searchText",     dataTableInfoVO.getSearchText());

	    	model.addAttribute("excel_name", "내부시스템연계관리");
	        model.addAttribute("excel_title", new String[]{"구분","테이블ID","테이블명","데이터타입","적재방법","관리기준년월일","갱신주기","원본데이터보유기관","원본데이터담당자","갱신일","상태"});
	        model.addAttribute("excel_column", new String[]{"data_gubun_nm","table_id","table_nm","data_type_scd_nm","data_conn_scd_nm","mng_std_ymd_scd_nm","data_upd_per_scd_nm","ori_data_owner","ori_data_mng_nm","last_upload_dt","status"});
	        model.addAttribute("data_list", dataService.getDataStatusExcel(param));
	        return "dataExcelView";
	    }
	
	
	
	/**
	 * 내부시스템 연계 수동업로드
	 * @param tableId
	 * @param dataConnScd
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/excuteSystemLinkManualUpload.do")
	public String excuteSystemLinkManualUpload(@RequestParam("tableId") String tableId
							                      , @RequestParam("dataConnScd") String dataConnScd
							                      , HttpServletRequest request
							                      , Model model) throws Exception {
		
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		
		DataVO dataVo = new DataVO();
		dataVo.setTableId(tableId);
		dataVo.setDataConnScd(dataConnScd);
		dataVo.setDbUpldUserId(loginVO.getUserId());

        String test = dataService.excuteSystemLinkManualUpload(dataVo); 
		return test;

	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getDataStatusList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<DataLinkVO> getDataStatusList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		if (params.get("dataConnScd") != null && !params.get("dataConnScd").equals("")) {
			newParams.put("dataConnScd", params.get("dataConnScd").toString());
        }
		if (params.get("dataUpdPerScd") != null && !params.get("dataUpdPerScd").equals("")) {
			newParams.put("dataUpdPerScd", params.get("dataUpdPerScd").toString());
        }
		if (params.get("searchType") != null && !params.get("searchType").equals("")) {
            newParams.put("searchType", params.get("searchType").toString());
        }
		if (params.get("searchText") != null && !params.get("searchText").equals("")) {
            newParams.put("searchText", params.get("searchText").toString());
        }
		List<DataLinkVO> result = dataService.getDataStatusList(newParams);
        return result;
    }
	
	 @RequestMapping(value="/getDataStatusExcel.do")
	 public String getDataStatusExcel(Model model,DataTableInfoVO dataTableInfoVO) throws Exception {

	    	
	    	Map<String, Object> param = new HashMap<>();
	    	param.put("dataConnScd",    dataTableInfoVO.getDataConnScd());
	    	param.put("dataUpdPerScd",  dataTableInfoVO.getDataUpdPerScd());
	    	param.put("searchType",     dataTableInfoVO.getSearchType());
	    	param.put("searchText",     dataTableInfoVO.getSearchText());

	    	model.addAttribute("excel_name", "데이터현황");
	        model.addAttribute("excel_title", new String[]{"구분","테이블ID","테이블명","데이터타입","적재방법","관리기준년월일","갱신주기","원본데이터보유기관","원본데이터담당자","갱신일","상태"});
	        model.addAttribute("excel_column", new String[]{"data_gubun_nm","table_id","table_nm","data_type_scd_nm","data_conn_scd_nm","mng_std_ymd_scd_nm","data_upd_per_scd_nm","ori_data_owner","ori_data_mng_nm","last_upload_dt","status"});
	        model.addAttribute("data_list", dataService.getDataStatusExcel(param));
	        return "dataExcelView";
	    }

	
	
	
	@ResponseBody
	@RequestMapping(value = "/getConfirmTableExistYn.do", method = {RequestMethod.GET, RequestMethod.POST})
    public DataVO getConfirmTableExistYn(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		if (params.get("tableId") != null && !params.get("tableId").equals("")) {
			newParams.put("tableId", params.get("tableId").toString());
        }
		DataVO result = dataService.getConfirmTableExistYn(newParams);
        return result;
    }
	
	@ResponseBody
	@RequestMapping(value = "/getDataTableColumnList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<DataVO> getDataTableColumnList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		if (params.get("tableId") != null && !params.get("tableId").equals("")) {
			newParams.put("tableId", params.get("tableId").toString());
        }
		List<DataVO> result = dataService.getDataTableColumnList(newParams);
        return result;
    }
	
	@RequestMapping(value = "/saveDataTableColumn.do")
	@ResponseBody
	public JSONObject saveDataTableColumn(@RequestBody JSONObject data
			                            , HttpServletRequest request
			                            , DataVO dataVo) throws Exception {
		String rtnCode = "";
		
		JSONParser parser = new JSONParser();
		JSONObject jObj = (JSONObject) parser.parse(data.toString());
		JSONArray saveDataList = (JSONArray) jObj.get("saveDataList");
		JSONArray deleteDataList = (JSONArray) jObj.get("deleteRowList");
		List<DataVO> dataVoList = JsonHelper.parseJsonArray(saveDataList.toString(), DataVO.class);
		List<DataVO> deleteDataVoList = JsonHelper.parseJsonArray(deleteDataList.toString(), DataVO.class);
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		for (int i=0; i<dataVoList.size(); i++) {
			dataVoList.get(i).setCreateId(loginVO.getUserId());
			dataVoList.get(i).setUpdateId(loginVO.getUserId());
		}
		
		for (int i=0; i<deleteDataVoList.size(); i++) {
			deleteDataVoList.get(i).setCreateId(loginVO.getUserId());
			deleteDataVoList.get(i).setUpdateId(loginVO.getUserId());
		}
		
		try {
			dataService.saveDataTableColumn(dataVoList);
			dataService.deleteDataTableColumn(deleteDataVoList);
			rtnCode = "ok";
		} catch (Exception e) {
			rtnCode = "ng";
		} finally {
			jObj.put("isOk", rtnCode);
		}
		return jObj;
	}
	
	@RequestMapping(value = "/saveDataTableInfo.do")
	@ResponseBody
	public JSONObject saveDataTableInfo(@RequestBody JSONObject data
						              , HttpServletRequest request) throws Exception {
		String rtnCode = "";
		JSONParser parser = new JSONParser();
		JSONObject jObj = (JSONObject) parser.parse(data.toString());
		DataVO dataVo = JsonHelper.parseJsonObject(jObj.get("saveData").toString(), DataVO.class);
		JSONArray dataUploadUserList = (JSONArray) jObj.get("dataUploadUser");
		List<DataVO> dataUploadUserVo = JsonHelper.parseJsonArray(dataUploadUserList.toString(), DataVO.class);
		
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		dataVo.setCreateId(loginVO.getUserId());
		dataVo.setUpdateId(loginVO.getUserId());
		for(int i=0; i<dataUploadUserVo.size(); i++) {
			dataUploadUserVo.get(i).setCreateId(loginVO.getUserId());
			dataUploadUserVo.get(i).setUpdateId(loginVO.getUserId());
		}
		
		try {
			dataService.saveDataTableInfo(dataVo);
			dataService.saveDataUploadUser(dataUploadUserVo);
			rtnCode = "ok";
		} catch (Exception e) {
			rtnCode = "ng";
		} finally {
			jObj.put("isOk", rtnCode);
		}
		return jObj;
		
	}
	
	@RequestMapping(value = "/deleteDataUploadUser.do")
	@ResponseBody
	public JSONObject deleteDataUploadUser(@RequestBody Map<String, Object> params
			                             , DataVO dataVo
						                 , HttpServletRequest request) throws Exception {
		String rtnCode = "";
		JSONObject jObj = new JSONObject();
		
		if (params.get("tableId") != null && !params.get("tableId").equals("")) {
			dataVo.setTableId(params.get("tableId").toString());
        }
		if (params.get("uploadUserId") != null && !params.get("uploadUserId").equals("")) {
			dataVo.setUploadUserId(params.get("uploadUserId").toString());
        }
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		dataVo.setCreateId(loginVO.getUserId());
		dataVo.setUpdateId(loginVO.getUserId());
		
		try {
			dataService.deleteDataUploadUser(dataVo);
			rtnCode = "ok";
		} catch (Exception e) {
			rtnCode = "ng";
		} finally {
			jObj.put("isOk", rtnCode);
		}
		return jObj;
		
	}
	
	/* 사용안함 */
	@RequestMapping(value = "/excuteGyeonggiOpenapiManualUpload.do")
	public String excuteGyeonggiOpenapiManualUpload(@RequestParam("tableId") String tableId
							                      , @RequestParam("stdYm") String stdYm
							                      , HttpServletRequest request
							                      , Model model) throws Exception {
		
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		DataVO dataVo = new DataVO();
		dataVo.setTableId(tableId);
		dataVo.setStdColumn("std_ym");
		dataVo.setStdYm(stdYm);
		
		boolean rtnCode = true;
		String rtnMsg = "";

		// table info 가져오기
		DataVO tableInfoVo = new DataVO();
		tableInfoVo = dataService.getDataTableInfo(tableId);
		String tOpenapiUrl = tableInfoVo.getOpenapiUrl();
		String tOpenapiKey = tableInfoVo.getOpenapiKey();
		String tDataFilePath = tableInfoVo.getDataFilePath();
		String tSaveFileNm = tableId + "_" + stdYm + ".csv";
		
		
		int pIndex = 1;
		int pSize = 1000;
		String requestUrl = tOpenapiUrl + "&type=json&KEY=" + tOpenapiKey + "&pSize=" + pSize + "&pIndex=" + pIndex;
		
		logger.debug("#### ggapi url ###:: "+requestUrl);
		
		int callCount = getPageCount(requestUrl, pSize);
		List<String> dataList = new ArrayList<String>();
		
		// 호출한 개수만큼 리스트에 담는다.
		if (callCount > 0) {
			for (int index=1; index<=callCount; index++) {
				requestUrl = tOpenapiUrl + "&type=json&KEY=" + tOpenapiKey + "&pSize=" + pSize + "&pIndex=" + index;
				dataList.add(requestAPIJsonData(requestUrl, "UTF-8"));
			}
		}
		
		String sql = " copy ( ";
		
		if (dataList.size() > 0) {
			//기존자료 삭제
			dataService.deleteLinkData(dataVo);
			int idx = 0;
			for (int h=0; h<dataList.size(); h++) {
				//logger.debug("현재 (" + (h+1) + "/" + dataList.size() + ")번째 작업중~~~~");
				JSONParser parser = new JSONParser();
				JSONObject jObj = (JSONObject) parser.parse(dataList.get(h));
				String keyName = jObj.keySet().toString().replace("[", "").replace("]", "");
				JSONArray resultArray = (JSONArray) jObj.get(keyName);
				
				// header info
				JSONObject headJson   = (JSONObject) parser.parse(resultArray.get(0).toString());
				JSONArray  headArray  = (JSONArray) headJson.get("head");
				JSONObject headInfo01 = (JSONObject) parser.parse(headArray.get(0).toString());
				JSONObject headInfo02 = (JSONObject) parser.parse(headArray.get(1).toString());
				JSONObject headStatus = (JSONObject) parser.parse(headInfo02.get("RESULT").toString());
				
				int totalCount = Integer.parseInt(headInfo01.get("list_total_count").toString());
				
				logger.debug("totalCount>>>" + totalCount);
				//String code = headStatus.get("CODE").toString();
				//String message = headStatus.get("MESSAGE").toString();
				// row data 추출
				JSONObject rowJson = (JSONObject) parser.parse(resultArray.get(1).toString());
				JSONArray rowArray = (JSONArray) rowJson.get("row");
				
				// column 정보 추출
				ObjectMapper mapper = new ObjectMapper(); 
				HashMap<String, Object> map = mapper.readValue(rowArray.get(0).toString(), new HashMap<String, String>().getClass()); 
				List<String> columnList = new ArrayList<String>();
				//Collections.sort(columnList);

				for(String key : map.keySet()){
					columnList.add(String.format("%s", key));
		        }
				
				// hs_data_table_info에서 해당 테이블 정보 가져오기
				List<DataVO> dataTableInfoList = (List<DataVO>) dataService.getDataTableColumnInfo(tableId);
				
				// insert script 생성
				for (int i=0; i<rowArray.size(); i++) {
					sql += " select ";
					JSONObject dataJson = (JSONObject) parser.parse(rowArray.get(i).toString());
					for (int j=0; j<dataTableInfoList.size(); j++) {
						String cColumnId = dataTableInfoList.get(j).getColumnId().toLowerCase();
						String cColumnType = dataTableInfoList.get(j).getColumnType().toLowerCase();
						String value = "";
						
						for (int k=0; k<columnList.size(); k++) {
							String jColumnId = columnList.get(k).toString().toLowerCase(); 
							if (cColumnId.equals(jColumnId)) {
								String orgVal = (dataJson.get(columnList.get(k)) == null ? null : dataJson.get(columnList.get(k)).toString());
								Double intVal = 0.0;
								
								if (cColumnType.equals("numeric")) {
									intVal = (orgVal == null ? 0.0 : Double.parseDouble(orgVal));
									value = (i==0 ? intVal.toString() + " as " + cColumnId : intVal.toString());
								} else {
									value = ((orgVal == null || orgVal.toLowerCase() == "null") ? null : orgVal.replaceAll("'", ""));
									
									if (value == null) {
										value = (i==0 ? value + " as " + cColumnId : value);
									} else {
										value = (i==0 ? "'" + value + "' as " + cColumnId : "'" + value + "'");
									}
								}
								sql += (j==dataTableInfoList.size() - 1) ? value + " " : value + ", ";
							}
						}
					}
					
					//sql += (i==rowArray.size() - 1) ? "" : " union all ";
					if ((idx == (dataList.size() - 1)) && (i==rowArray.size() - 1)) {
						sql += "";
					} else {
						sql += " union all ";
					}
				}
				
				// 페이지 종료 체크
				idx++;
			}
		}
		sql += " ) to  '" + tDataFilePath + "\\" + tSaveFileNm + "' delimiter '|' csv header ";
		dataVo.setSql(sql);
		
		try {
			dataService.saveDataCsv(dataVo);
			rtnCode = true;
			rtnMsg = "정상처리";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.debug("ExceptionUtils.getRootCause::" + ExceptionUtils.getRootCause(e) + "");
			rtnCode = false;
			rtnMsg = "err:csv 생성 오류";
		}  finally {
			tableInfoVo.setEncodingType("utf-8");
			tableInfoVo.setSaveFileNm(tSaveFileNm);
			tableInfoVo.setUpdateId(loginVO.getUserId());
			tableInfoVo.setStdYm(stdYm);
			dataService.insretCsvToTable(tableInfoVo);
		}
		model.addAttribute("resultMsg", rtnMsg);
		return "jsonView";
	}
	
	@RequestMapping(value = "/excuteGyeonggiOpenapiManualUploadFtp.do")
	public String excuteGyeonggiOpenapiManualUploadFtp(@RequestParam("tableId") String tableId
							                      , @RequestParam("stdYm") String stdYm
							                      , HttpServletRequest request
							                      , Model model) throws Exception {
		
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		DataVO dataVo = new DataVO();
		dataVo.setTableId(tableId);
		dataVo.setStdColumn("std_ym");
		dataVo.setStdYm(stdYm);
		
		boolean rtnCode = true;
		String rtnMsg = "";

		// table info 가져오기
		DataVO tableInfoVo = new DataVO();
		tableInfoVo = dataService.getDataTableInfo(tableId);
		String tOpenapiUrl = tableInfoVo.getOpenapiUrl();
		String tOpenapiKey = tableInfoVo.getOpenapiKey();
		String tDataFilePath = tableInfoVo.getDataFilePath();
		String tSaveFileNm = tableId + "_" + stdYm + ".csv";
		File file = new File(tableInfoVo.getDataFilePath() + "'\'" +tSaveFileNm);
		
		DataUpldLogVO dataUpldLogVo = new DataUpldLogVO();
		
		BufferedWriter fw = null;
		try {
//			FileWriter fw = null;
//			fw = new FileWriter(file);
			//BufferedWriter fw;
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),writeFileEncoding_euc));
			int pIndex = 1;
			int pSize = 1000;
			String requestUrl = tOpenapiUrl + "&type=json&KEY=" + tOpenapiKey + "&pSize=" + pSize + "&pIndex=" + pIndex;
			
			logger.debug("####### gg url #####: "+requestUrl);
			
			int callCount = getPageCount(requestUrl, pSize);
			List<String> dataList = new ArrayList<String>();
			
			// 호출한 개수만큼 리스트에 담는다.
			
			if (callCount > 0) {
				for (int index=1; index<=callCount; index++) {
					requestUrl = tOpenapiUrl + "&type=json&KEY=" + tOpenapiKey + "&pSize=" + pSize + "&pIndex=" + index;
					String rtnJsonString = requestAPIJsonData(requestUrl, "UTF-8"); 
					dataList.add(rtnJsonString);
					//logger.debug("return String>>>>>>>" + rtnJsonString);
				}
			}
			
			
			
			
			if (dataList.size() > 0) {
				//기존자료 삭제
				dataService.deleteLinkData(dataVo);
				int idx = 0;
				for (int h=0; h<dataList.size(); h++) {
					JSONParser parser = new JSONParser();
					JSONObject jObj = (JSONObject) parser.parse(dataList.get(h));
					String keyName = jObj.keySet().toString().replace("[", "").replace("]", "");
					JSONArray resultArray = (JSONArray) jObj.get(keyName);
					
					// header info
					JSONObject headJson   = (JSONObject) parser.parse(resultArray.get(0).toString());
					JSONArray  headArray  = (JSONArray) headJson.get("head");
					JSONObject headInfo01 = (JSONObject) parser.parse(headArray.get(0).toString());
					JSONObject headInfo02 = (JSONObject) parser.parse(headArray.get(1).toString());
					JSONObject headStatus = (JSONObject) parser.parse(headInfo02.get("RESULT").toString());
					
					int totalCount = Integer.parseInt(headInfo01.get("list_total_count").toString());
					// row data 추출
					JSONObject rowJson = (JSONObject) parser.parse(resultArray.get(1).toString());
					JSONArray rowArray = (JSONArray) rowJson.get("row");
					
					// column 정보 추출
					ObjectMapper mapper = new ObjectMapper(); 
					HashMap<String, Object> map = mapper.readValue(rowArray.get(0).toString(), new HashMap<String, String>().getClass()); 
					List<String> columnList = new ArrayList<String>();
					//Collections.sort(columnList);
	
					for(String key : map.keySet()){
						columnList.add(String.format("%s", key));
			        }
					
					// hs_data_table_info에서 해당 테이블 정보 가져오기
					List<DataVO> dataTableInfoList = (List<DataVO>) dataService.getDataTableColumnInfo(tableId);
					
					if(h==0) {  //bskim 20200217 헤더는 1번만 작성.
					// 헤더 생성
						for (int i = 0; i < dataTableInfoList.size(); i++) {
							if (i == dataTableInfoList.size() - 1) {
		                        fw.write(dataTableInfoList.get(i).getColumnId() + "");
		                    } else {
		                        fw.write(dataTableInfoList.get(i).getColumnId() + "|");
		                    }
				        }
						fw.write("\n");
					}
					// Data Row 생성
					for (int i=0; i<rowArray.size(); i++) {
						JSONObject dataJson = (JSONObject) parser.parse(rowArray.get(i).toString());
						for (int j=0; j<dataTableInfoList.size(); j++) {
							String cColumnId = dataTableInfoList.get(j).getColumnId().toLowerCase();
							String cColumnType = dataTableInfoList.get(j).getColumnType().toLowerCase();
							String value = "";
							for (int k=0; k<columnList.size(); k++) {
								String jColumnId = columnList.get(k).toString().toLowerCase(); 
								if (cColumnId.equals(jColumnId)) {
									//bskim 20200212 null일경우 null로 처리
									//String orgVal = (dataJson.get(columnList.get(k)) == null ? "" : dataJson.get(columnList.get(k)).toString());
									String orgVal = (dataJson.get(columnList.get(k)) == null ? null : dataJson.get(columnList.get(k)).toString());
									
									Double intVal = 0.0;
									if (cColumnType.equals("numeric")) {
										intVal = (orgVal == null ? 0.0 : Double.parseDouble(orgVal));
										value = intVal.toString();
									} else {
										value = ((orgVal == null || orgVal.toLowerCase() == "null") ? "" : orgVal.replaceAll("'", ""));
										
									}
									value = value.replace("|", " ").replace("\"", " ");
								}
							}
							if (j == (dataTableInfoList.size() - 1)) {
								fw.write(value + "");
							} else {
								fw.write(value + "|");
							}
						}
						fw.write("\n");
					}
					// 페이지 종료 체크
					idx++;
				}
			}
			fw.flush();
			//String binaryString = FileUtils.readFileToString(file, "UTF-8");
			String binaryString = FileUtils.readFileToString(file);
	        final Message<byte[]> message = MessageBuilder.withPayload(binaryString.getBytes())
	                .setHeader("fileName", tSaveFileNm)
	                .setHeader("remoteDir", "/")
	                .build();

	        boolean uploadSuccess = ftpChannel.send(message, 30000);
	        if (uploadSuccess) {
	        	tableInfoVo.setEncodingType(ftpTransferType);
				tableInfoVo.setSaveFileNm(tSaveFileNm);
				tableInfoVo.setUpdateId(loginVO.getUserId());//==> 세션으로 변경
				tableInfoVo.setStdYm(stdYm);
				dataService.insretCsvToTable(tableInfoVo);
	        }
			rtnCode = true;
			rtnMsg = "정상처리";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			//오류 로그남기기.
			dataUpldLogVo.setTableId(tableInfoVo.getTableId());
			dataUpldLogVo.setTableNm(tableInfoVo.getTableNm());
			dataUpldLogVo.setDataConnScd(tableInfoVo.getDataConnScd());
			dataUpldLogVo.setLogTypeScd("fail");
			dataUpldLogVo.setLogMsg("[오류] API연계에 문제가 발생했습니다. "+e.toString());
			dataUpldLogVo.setCreateId(loginVO.getUserId());
			dataService.insertDataUpldLog(dataUpldLogVo);

			fw.flush();
			logger.debug("ExceptionUtils.getRootCause::" + ExceptionUtils.getRootCause(e) + "");
			rtnCode = false;			
			rtnMsg = "err:csv 생성 오류";
			
		} finally {
            fw.close();		
			if(file != null) {
                file.delete();
            }
        }
		model.addAttribute("resultMsg", rtnMsg);
		return "jsonView";
	}
	
	public int getPageCount(String requestUrl, int requestSize) throws Exception {
		URL url = new URL(requestUrl);
		StringBuffer result = new StringBuffer();
		String readLine = null;
		
		try {
			InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
			BufferedReader br = new BufferedReader(isr); 
			while ((readLine = br.readLine()) != null) {
				result.append(readLine);
			}
			br.close();
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONParser parser = new JSONParser();
		JSONObject jObj = (JSONObject) parser.parse(result.toString());
		String keyName = jObj.keySet().toString().replace("[", "").replace("]", "");
		JSONArray resultArray = (JSONArray) jObj.get(keyName);
		
		int totalCount = 0;
		JSONObject headJson   = (JSONObject) parser.parse(resultArray.get(0).toString());
		JSONArray  headArray  = (JSONArray) headJson.get("head");
		JSONObject headInfo01 = (JSONObject) parser.parse(headArray.get(0).toString());
		
		totalCount = Integer.parseInt(headInfo01.get("list_total_count").toString());
		int callCount = (int) Math.ceil(1.0*totalCount/requestSize);
		return callCount;
	}
	
	public String requestAPIJsonData(String pUrl, String encoding) throws Exception {
		StringBuffer result = new StringBuffer();
		URL url;
		try {
			url = new URL(pUrl);
			//logger.debug("protocol>>>" + url.getProtocol());
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			//logger.debug("contentType>>" + conn.getRequestMethod());
			//logger.debug("contentType>>" + conn.getContentType());
			//logger.debug("contents" + conn.getContent().toString());
			//logger.debug("response code>>" + conn.getResponseCode());
			//logger.debug("response msg>>" + conn.getResponseMessage());
			String readLine = null;
			InputStreamReader isr = new InputStreamReader(conn.getInputStream(), encoding);
			BufferedReader br = new BufferedReader(isr); 
			while ((readLine = br.readLine()) != null) {
				result.append(readLine);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/openapiConfSave.do")
    public JSONObject openapiConfSave(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		boolean rtnCode = true;
		String rtnMsg = "";
		
		DataLinkVO dataVo = new DataLinkVO();
		dataVo.setTableId(params.get("tableId").toString());
		dataVo.setOpenapiUrl(params.get("openapiUrl").toString());
		dataVo.setOpenapiTimeScd(params.get("openapiTimeScd").toString());
		dataVo.setUpdateId("admin"); //==> 추후 session 변경
		JSONObject jObj = new JSONObject();
		try {
			dataService.openapiConfSave(dataVo);
			rtnCode = true;
			rtnMsg = "정상처리";
		} catch (Exception e) {
			rtnCode = false;
			rtnMsg = "[오류]" + ExceptionUtils.getRootCause(e);
		} finally {
			jObj.put("resultCode", rtnCode);
			jObj.put("resultMsg", rtnMsg);
		}
		return jObj;
    }
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/gyeonggiOpenapiKeySave.do")
    public JSONObject gyeonggiOpenapiKeySave(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		boolean rtnCode = true;
		String rtnMsg = "";
		
		DataLinkVO dataVo = new DataLinkVO();
		dataVo.setOpenapiKey(params.get("openapiKey").toString());
		dataVo.setUpdateId("admin"); //==> 추후 session 변경
		JSONObject jObj = new JSONObject();
		try {
			dataService.gyeonggiOpenapiKeySave(dataVo);
			rtnCode = true;
			rtnMsg = "정상처리";
		} catch (Exception e) {
			rtnCode = false;
			rtnMsg = "[오류]" + ExceptionUtils.getRootCause(e);
		} finally {
			jObj.put("resultCode", rtnCode);
			jObj.put("resultMsg", rtnMsg);
		}
		return jObj;
    }
	
	@ResponseBody
	@RequestMapping(value = "/getRightsMinwonLinkList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<DataLinkVO> getRightsMinwonLinkList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		if (params.get("dataUpdPerScd") != null && !params.get("dataUpdPerScd").equals("")) {
			newParams.put("dataUpdPerScd", params.get("dataUpdPerScd").toString());
        }
		if (params.get("searchType") != null && !params.get("searchType").equals("")) {
            newParams.put("searchType", params.get("searchType").toString());
        }
		if (params.get("searchText") != null && !params.get("searchText").equals("")) {
            newParams.put("searchText", params.get("searchText").toString());
        }
		
		List<DataLinkVO> result = dataService.getRightsMinwonLinkList(newParams);
        return result;
    }
	
	 @RequestMapping(value="/getRightsMinwonLinkExcel.do")
	 public String getRightsMinwonLinkExcel(Model model,DataTableInfoVO dataTableInfoVO) throws Exception {

	    	
	    	Map<String, Object> param = new HashMap<>();
	    	param.put("dataUpdPerScd",  dataTableInfoVO.getDataUpdPerScd());
	    	param.put("searchType",     dataTableInfoVO.getSearchType());
	    	param.put("searchText",     dataTableInfoVO.getSearchText());

	    	model.addAttribute("excel_name", "국민권익위 api연계 리스트");
	        model.addAttribute("excel_title", new String[]{"구분","테이블ID","테이블명","OpenAPI_URL","관리기준년월일","갱신주기","갱신일","상태"});
	        model.addAttribute("excel_column", new String[]{"data_gubun_nm","table_id","table_nm","openapi_url","mng_std_ymd_scd_nm","data_upd_per_scd_nm","last_upload_dt","status"});
	        model.addAttribute("data_list", dataService.getRightsMinwonLinkExcel(param));
	        return "dataExcelView";
	    }
	
	
	
	@RequestMapping(value = "/excuteRightsMinwonDataLinkManualUpload.do")
	public String excuteRightsMinwonDataLinkManualUpload(@RequestParam("tableId") String tableId
			                                           , @RequestParam("stdYm") String stdYm
							                           , HttpServletRequest request
							                           , Model model) throws Exception {
		
		String rtnMsg = "";
		switch(tableId) {
			case "hsmn_min_rising_kwrd" :
				executeOpenApiMinwonDataLink(tableId, "keywordOpenApiList", stdYm);
	            break;
			case "hsmn_min_wd_cloud" :
				executeOpenApiMinwonDataLink(tableId, "keywordWdCloud", stdYm);
	            break;
	        case "hsm_acrc_top_kwrd" : 
	        	excuteRightsMinwonDataLink(tableId, "keywordApiList", stdYm);
	            break;
	        case "hsm_acrc_incrs_kwrd" : 
	        	excuteRightsMinwonDataLink(tableId, "keywordApiList", stdYm); 
	            break;
	        case "hsm_acrc_warn_kwrd" : 
	        	excuteRightsMinwonDataLink(tableId, "keywordWarningApiList", stdYm);
	            break;  
	        case "hsm_acrc_ntwrk_kwrd" : 
	        	excuteRightsMinwonDataLink(tableId, "keywordListList", stdYm);
	            break;    
	        default:
	        	excuteRightsMinwonDataLink(tableId, "keywordApiList", stdYm);
	    }
		
		model.addAttribute("resultMsg", rtnMsg);
		return "jsonView";
	}
	
	// 민원 오픈 API 링크 (20200709 신규 추가)
	public void executeOpenApiMinwonDataLink(String tableId, String jsonObjectKeyNm, String stdYm) throws Exception {
		// table info 가져오기
		DataVO tableInfoVo = new DataVO();
		tableInfoVo = dataService.getDataTableInfo(tableId);
		tableInfoVo.setStdColumn("std_ymd");
		tableInfoVo.setStdYm(stdYm);
		
		String tUrlParmas = "&serviceKey=" + minwonServiceKey;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date dt = formatter.parse(stdYm);
        Date newDt = new Date();
        Calendar cal = Calendar.getInstance();
        // analysisTime
        cal.setTime(dt);
        newDt = cal.getTime();
        String analysisTime = formatter.format(newDt);
        // paramStDate
        cal.setTime(dt);
        cal.add(cal.DATE, -1);
        newDt = cal.getTime();
        String paramStDate = formatter.format(newDt);

        logger.debug("------------------------------------------------------------------------------------");
        logger.debug("analysisTime::" + analysisTime);
        logger.debug("paramStDate::" + paramStDate);
        logger.debug("------------------------------------------------------------------------------------");
        if (jsonObjectKeyNm.equals("keywordOpenApiList")) { // 급증
			tUrlParmas += "&analysisTime=" + analysisTime;
			tableInfoVo.setStdColumn("date");
		} else if (jsonObjectKeyNm.equals("keywordWdCloud")) { // 클라우드
			tUrlParmas += "&dateFrom=" + paramStDate;
			tUrlParmas += "&deteTo=" + analysisTime;
			tableInfoVo.setStdColumn("std_date");
		}
        
        String tOpenapiUrl = tableInfoVo.getOpenapiUrl() + tUrlParmas;
        
        logger.debug("########## URL################ ::" +tOpenapiUrl);
		String tDataFilePath = tableInfoVo.getDataFilePath();
		String tSaveFileNm = tableId + "_" + stdYm + ".csv";
		
		JSONParser parser = new JSONParser();
		ObjectMapper mapper = new ObjectMapper();
		// hs_data_table_info에서 해당 테이블 정보 가져오기
		List<DataVO> dataTableInfoList = (List<DataVO>) dataService.getDataTableColumnInfo(tableId);
		boolean exeYn = true;
		String sql = " copy ( ";
		if ("keywordOpenApiList".equals(jsonObjectKeyNm)) {// 급증 키워드
    		String requestUrl = tOpenapiUrl;
    		String rtnJsonString = requestAPIJsonData(requestUrl, "UTF-8");
    		if (!"{}".equals(rtnJsonString)) {
	    		JSONObject jObj = (JSONObject)parser.parse(rtnJsonString);
	    		
	    		JSONArray jsonArray = (JSONArray) jObj.get("returnObject");
	    		
	    		// column 정보 추출
	    		HashMap<String, Object> map = mapper.readValue(jsonArray.get(0).toString(), new HashMap<String, String>().getClass()); 
	    		List<String> columnList = new ArrayList<String>();
	
	    		for(String key : map.keySet()){
	    			columnList.add(String.format("%s", key));
	            }
	
	    		if (jsonArray.size() > 0) {
	    			//기존자료 삭제
	    			dataService.deleteLinkData(tableInfoVo);
	    			// insert script 생성
	        		for (int i=0; i<jsonArray.size(); i++) {
	        			sql += " select ";
	        			JSONObject dataJson = (JSONObject) parser.parse(jsonArray.get(i).toString());
	        			for (int j=0; j<dataTableInfoList.size(); j++) {
	        				// ColumnAliasId로 매핑
	        				String cColumnId = dataTableInfoList.get(j).getColumnAliasId().toLowerCase();
	        				String cColumnType = dataTableInfoList.get(j).getColumnType().toLowerCase();
	        				String value = "";
	        				
	        				for (int k=0; k<columnList.size(); k++) {
	        					String jColumnId = columnList.get(k).toString().toLowerCase(); 
	        					if (cColumnId.equals(jColumnId)) {
	        						String orgVal = (dataJson.get(columnList.get(k)) == null ? null : dataJson.get(columnList.get(k)).toString());
	        						Double intVal = 0.0;
	        						
	        						if (cColumnType.equals("numeric")) {
	        							intVal = (orgVal == null ? 0.0 : Double.parseDouble(orgVal));
	        							value = (i==0 ? intVal.toString() + " as " + cColumnId : intVal.toString());
	        						} else {
	        							value = (orgVal == null ? null : orgVal.replaceAll("'", ""));
	        							value = (i==0 ? "'" + value + "' as " + cColumnId : "'" + value + "'");
	        						}
	        						sql += (j==dataTableInfoList.size() - 1) ? value + " " : value + ", ";
	        					}
	        				}
	        			}
	        			sql += (i==jsonArray.size() - 1) ? "" : " union all ";
	        		}
	    		}
    		} else {
    			exeYn = false;
    		}
		}else if ("keywordWdCloud".equals(jsonObjectKeyNm)) {
			String requestUrl = tOpenapiUrl;
    		String rtnJsonString = requestAPIJsonData(requestUrl, "UTF-8");
    		if (!"{}".equals(rtnJsonString)) {
    			
    			org.codehaus.jettison.json.JSONArray jsonArray = new org.codehaus.jettison.json.JSONArray(rtnJsonString);
    			
    			// column 정보 추출
	    		HashMap<String, Object> map = mapper.readValue(jsonArray.get(0).toString(), new HashMap<String, String>().getClass()); 
	    		List<String> columnList = new ArrayList<String>();
	
	    		for(String key : map.keySet()){
	    			columnList.add(String.format("%s", key));
	            }
	    		
	    		if (jsonArray.length() > 0) {
	    			//기존자료 삭제
	    			dataService.deleteLinkData(tableInfoVo);
	    			// insert script 생성
	        		for (int i=0; i<jsonArray.length(); i++) {
	        			sql += " select ";
	        			sql += "'" + analysisTime + "' as std_date,";
	        			JSONObject dataJson = (JSONObject) parser.parse(jsonArray.get(i).toString());
	        			for (int j=0; j<dataTableInfoList.size(); j++) {
	        				// ColumnAliasId로 매핑
	        				String cColumnId = dataTableInfoList.get(j).getColumnAliasId().toLowerCase();
	        				String cColumnType = dataTableInfoList.get(j).getColumnType().toLowerCase();
	        				String value = "";
	        				
	        				for (int k=0; k<columnList.size(); k++) {
	        					String jColumnId = columnList.get(k).toString().toLowerCase(); 
	        					if (cColumnId.equals(jColumnId)) {
	        						String orgVal = (dataJson.get(columnList.get(k)) == null ? null : dataJson.get(columnList.get(k)).toString());
	        						Double intVal = 0.0;
	        						
	        						if (cColumnType.equals("numeric")) {
	        							intVal = (orgVal == null ? 0.0 : Double.parseDouble(orgVal));
	        							value = (i==0 ? intVal.toString() + " as " + cColumnId : intVal.toString());
	        						} else {
	        							value = (orgVal == null ? null : orgVal.replaceAll("'", ""));
	        							value = (i==0 ? "'" + value + "' as " + cColumnId : "'" + value + "'");
	        						}
	        						sql += (j==dataTableInfoList.size() - 1) ? value + " " : value + ", ";
	        					}
	        				}
	        			}
	        			sql += (i==jsonArray.length() - 1) ? "" : " union all ";
	        		}
	    		}
    		} else {
    			exeYn = false;
    		}
		}
		
		if (exeYn) {
			sql += " ) to  '" + tDataFilePath + "\\" + tSaveFileNm + "' delimiter '|' csv header ";
			tableInfoVo.setSql(sql);
			try {
				dataService.saveDataCsv(tableInfoVo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.debug("ExceptionUtils.getRootCause::" + ExceptionUtils.getRootCause(e) + "");
			}  finally {
				tableInfoVo.setEncodingType("UTF-8");
				tableInfoVo.setSaveFileNm(tSaveFileNm);
				tableInfoVo.setUpdateId("admin");//==> 세션으로 변경
				tableInfoVo.setStdYm(stdYm);
				dataService.insretCsvToTable(tableInfoVo);
			}
		}
	}
	
	// (미사용)민원 요청 링크
	public void excuteRightsMinwonDataLink(String tableId, String jsonObjectKeyNm, String stdYm) throws Exception {
		// table info 가져오기
		DataVO tableInfoVo = new DataVO();
		tableInfoVo = dataService.getDataTableInfo(tableId);
		tableInfoVo.setStdColumn("std_ymd");
		tableInfoVo.setStdYm(stdYm);
		
		String tUrlParmas = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date dt = formatter.parse(stdYm);
        Date newDt = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(cal.DATE, -1);
        newDt = cal.getTime();
        String paramStDate = formatter.format(newDt);
        cal.setTime(dt);
        newDt = cal.getTime();
        String paramEdDate = formatter.format(newDt);
        cal.setTime(dt);
        cal.add(cal.DATE, -2);
        newDt = cal.getTime();
        String paramBefStDate = formatter.format(newDt);
        cal.setTime(dt);
        cal.add(cal.DATE, -1);
        newDt = cal.getTime();
        String paramBefEdDate = formatter.format(newDt);
        cal.setTime(dt);
        cal.add(cal.DATE, -7);
        newDt = cal.getTime();
        String paramBef7Days = formatter.format(newDt);
        

        logger.debug("###################################################################################");
        logger.debug("paramStDate::" + paramStDate);
        logger.debug("paramEdDate::" + paramEdDate);
        logger.debug("paramBefStDate::" + paramBefStDate);
        logger.debug("paramBefEdDate::" + paramBefEdDate);
        logger.debug("###################################################################################");
        if (jsonObjectKeyNm.equals("keywordApiList")) { //top, 급증
        	String hhmmdd = "000000";
			tUrlParmas += "&periodStart=" + paramStDate + hhmmdd; 
			tUrlParmas += "&periodEnd=" + paramEdDate + hhmmdd;
			tUrlParmas += "&previousPeriodStart=" + paramBefStDate + hhmmdd;
			tUrlParmas += "&previousPeriodEnd=" + paramBefEdDate + hhmmdd;
		} else if (jsonObjectKeyNm.equals("keywordWarningApiList")) { //경보
			tUrlParmas = "&stdde=" + paramStDate;
		}
        
		//String tOpenapiUrl = ("hsm_acrc_warn_kwrd".equals(tableId) ? tableInfoVo.getOpenapiUrl() + "&stdde=" + stdYm : tableInfoVo.getOpenapiUrl());
        String tOpenapiUrl = tableInfoVo.getOpenapiUrl() + tUrlParmas;
        
        logger.debug("########## URL################ ::" +tOpenapiUrl);
        //String tOpenapiUrl = "" + tUrlParmas;
		String tDataFilePath = tableInfoVo.getDataFilePath();
		String tSaveFileNm = tableId + "_" + stdYm + ".csv";//==> 변경필요
		
		JSONParser parser = new JSONParser();
		ObjectMapper mapper = new ObjectMapper();
		// hs_data_table_info에서 해당 테이블 정보 가져오기
		List<DataVO> dataTableInfoList = (List<DataVO>) dataService.getDataTableColumnInfo(tableId);
		
		boolean exeYn = true;
		String sql = " copy ( ";
		if (!"keywordListList".equals(jsonObjectKeyNm)) {// top, 급증, 경보 키워드
    		String requestUrl = tOpenapiUrl;
    		String rtnJsonString = requestAPIJsonData(requestUrl, "euc-kr");
    		if (!"{}".equals(rtnJsonString)) {
	    		JSONObject jObj = (JSONObject) parser.parse(rtnJsonString);//==>운영서버 확인
	    		//JSONObject jObj = (JSONObject) parser.parse(SampleJson);//==> 상상코디용~~~~~~~~~~~~~~~~~~
	    		JSONArray jsonArray = (JSONArray) jObj.get(jsonObjectKeyNm);
	    		// column 정보 추출
	    		HashMap<String, Object> map = mapper.readValue(jsonArray.get(0).toString(), new HashMap<String, String>().getClass()); 
	    		List<String> columnList = new ArrayList<String>();
	    		//Collections.sort(columnList);
	
	    		for(String key : map.keySet()){
	    			columnList.add(String.format("%s", key));
	            }
	
	    		if (jsonArray.size() > 0) {
	    			//기존자료 삭제
	    			dataService.deleteLinkData(tableInfoVo);
	    			// insert script 생성
	        		for (int i=0; i<jsonArray.size(); i++) {
	        			sql += " select ";
	        			JSONObject dataJson = (JSONObject) parser.parse(jsonArray.get(i).toString());
	        			for (int j=0; j<dataTableInfoList.size(); j++) {
	        				// ColumnAliasId로 매핑
	        				String cColumnId = dataTableInfoList.get(j).getColumnAliasId().toLowerCase();
	        				String cColumnType = dataTableInfoList.get(j).getColumnType().toLowerCase();
	        				String value = "";
	        				
	        				for (int k=0; k<columnList.size(); k++) {
	        					String jColumnId = columnList.get(k).toString().toLowerCase(); 
	        					if (cColumnId.equals(jColumnId)) {
	        						String orgVal = (dataJson.get(columnList.get(k)) == null ? null : dataJson.get(columnList.get(k)).toString());
	        						Double intVal = 0.0;
	        						
	        						if (cColumnType.equals("numeric")) {
	        							intVal = (orgVal == null ? 0.0 : Double.parseDouble(orgVal));
	        							value = (i==0 ? intVal.toString() + " as " + cColumnId : intVal.toString());
	        						} else {
	        							value = (orgVal == null ? null : orgVal.replaceAll("'", ""));
	        							value = (i==0 ? "'" + value + "' as " + cColumnId : "'" + value + "'");
	        						}
	        						
	        						sql += (j==dataTableInfoList.size() - 1) ? value + " " : value + ", ";
	        					}
	        				}
	        			}
	        			sql += (i==jsonArray.size() - 1) ? "" : " union all ";
	        		}
	    		}
    		} else {
    			exeYn = false;
    		}
		} else {// 연관 키워드
			String topRequestUrl = "http://10.184.164.16:80/gpkisecureweb/acrc/keywordRankingJson.jsp?gbn=top&organizationCode=5530000";
			String hhmmdd = "000000";
			tUrlParmas += "&periodStart=" + paramStDate + hhmmdd; 
			tUrlParmas += "&periodEnd=" + paramEdDate + hhmmdd;
			tUrlParmas += "&previousPeriodStart=" + paramBefStDate + hhmmdd;
			tUrlParmas += "&previousPeriodEnd=" + paramBefEdDate + hhmmdd;
			topRequestUrl += tUrlParmas;
			String topJsonObjectKeyNm = "keywordApiList";
			String topJsonSting = requestAPIJsonData(topRequestUrl, "euc-kr");

			JSONObject topObj = (JSONObject) parser.parse(topJsonSting);//==>운영서버 확인
			//JSONObject topObj = (JSONObject) parser.parse(SampleJson);//==> 상상코디용~~~~~~~~~~~~~~~~~~
    		JSONArray topJsonArray = (JSONArray) topObj.get(topJsonObjectKeyNm);
    		
    		// 연관 키워드 파라미터 추출
    		List<String> topNtwkParamList = new ArrayList<String>();
    		for (int i=0; i<topJsonArray.size(); i++) {
    			JSONObject topJsonData = (JSONObject) parser.parse(topJsonArray.get(i).toString());
				topNtwkParamList.add(topJsonData.get("keywordName").toString());
    		}
    		// 연관 키워드 파라미터별로 requestAPIJsonData 호출
    		String ntwkRequestUrl = "";
    		List<String> ntwkDataList = new ArrayList<String>();
    		List<String> topNtwkParamRealList = new ArrayList<String>();
    		for (int i=0; i<topNtwkParamList.size(); i++) {
    			ntwkRequestUrl = tOpenapiUrl + "&keywordName=" + topNtwkParamList.get(i);
    			String hhmmdd2 = "000000";
    			tUrlParmas += "&periodStart=" + paramBef7Days + hhmmdd2;// -7
    			tUrlParmas += "&periodEnd=" + paramEdDate + hhmmdd2;//현재일
    			ntwkRequestUrl += tUrlParmas; 
    			String rtnJsonString = requestAPIJsonData(ntwkRequestUrl, "euc-kr");
    			if (!"{}".equals(rtnJsonString)) {
    				ntwkDataList.add(rtnJsonString);//==>운영서버 확인
    				//ntwkDataList.add(SampleNtwkJson);//==> 상상코디용~~~~~~~~~~~~~~~~~~
    				topNtwkParamRealList.add(topNtwkParamList.get(i));
    			}
    		}
    		//기존자료 삭제
			dataService.deleteLinkData(tableInfoVo);
			int idx = 0;
    		if (ntwkDataList.size() > 0) {
    			for (int h=0; h<ntwkDataList.size(); h++) {
    				JSONObject ntwkObj = (JSONObject) parser.parse(ntwkDataList.get(h));
		    		JSONArray ntwkJsonArray = (JSONArray) ntwkObj.get(jsonObjectKeyNm);
		    		
		    		for (int i=0; i<ntwkJsonArray.size(); i++) {
    					JSONObject ntwkJsonData = (JSONObject) parser.parse(ntwkJsonArray.get(i).toString());
    					JSONObject ntwkJsonKeyword = (JSONObject) ntwkJsonData.get("keyword");
						JSONObject ntwkJsonResult = new JSONObject();
						
						ntwkJsonResult.put("keyword", topNtwkParamRealList.get(h));//==> 확인필요
						ntwkJsonResult.put("networkKeyword", ntwkJsonKeyword.get("name").toString());
						ntwkJsonResult.put("cnt", Integer.parseInt(ntwkJsonData.get("cnt").toString()));
						sql += " select '" + ntwkJsonResult.get("keyword") + "' as keyword_name, ";
						sql += "'" + ntwkJsonResult.get("networkKeyword") + "' as network_keyword_name, ";
						sql += ntwkJsonResult.get("cnt") + " as network_keyword_count ";
						
						idx++;
						if (h == (ntwkDataList.size() - 1) && (i==ntwkJsonArray.size() - 1)) {
							sql += "";
						} else {
							sql += " union all ";
						}
		    		}
    			}
    		}
		}
		
		if (exeYn) {
			sql += " ) to  '" + tDataFilePath + "\\" + tSaveFileNm + "' delimiter '|' csv header ";
			tableInfoVo.setSql(sql);
			try {
				dataService.saveDataCsv(tableInfoVo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.debug("ExceptionUtils.getRootCause::" + ExceptionUtils.getRootCause(e) + "");
			}  finally {
				tableInfoVo.setEncodingType(ftpTransferType);
				tableInfoVo.setSaveFileNm(tSaveFileNm);
				tableInfoVo.setUpdateId("admin");//==> 세션으로 변경
				tableInfoVo.setStdYm(stdYm);
				dataService.insretCsvToTable(tableInfoVo);
			}
		}
	}
			
}
