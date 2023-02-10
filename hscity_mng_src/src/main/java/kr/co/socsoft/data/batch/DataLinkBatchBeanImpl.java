package kr.co.socsoft.data.batch;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Controller;

import kr.co.socsoft.common.vo.DataVO;
import kr.co.socsoft.data.service.DataService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import kr.co.socsoft.common.vo.DataUpldLogVO;

@Controller
public class DataLinkBatchBeanImpl implements DataLinkBatchBean {
	
	private static final Logger logger = LoggerFactory.getLogger(DataLinkBatchBeanImpl.class);
	
	@Value("#{globals['Globals.ftp.after.transfer.file.encoding']}")
	private String ftpTransferType;//euc-kr
	
	@Resource(name = "dataService")
	private DataService dataService;
	
	@Autowired
    private DirectChannel ftpChannel;
	
	@Value("#{globals['Global.minwon.openapi.seviceKey']}")
	private String minwonServiceKey;
	
    @Override
    public void batchWork_gyeonggi() throws Exception {
    	@SuppressWarnings("unchecked")
		List<DataVO> tagetTableList = (List<DataVO>) dataService.getOpenapiTableList();
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MMMM-dd (EEEE) HH:mm:ss (z Z)", Locale.KOREA);
        Date currentTime = new Date();
        String batchTime = formatter.format(currentTime);
        logger.debug("[" + batchTime + "]배치시작~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
		for (int i=0; i<tagetTableList.size(); i++) {
			this.excuteGyeonggiOpenapi(tagetTableList.get(i).getTableId());
		}
		
    }
    
    @Override
    public void batchWork_minwon() throws Exception {
    	@SuppressWarnings("unchecked")
		List<DataVO> tagetTableList = (List<DataVO>) dataService.getRightsMinwonTableList();
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MMMM-dd (EEEE) HH:mm:ss (z Z)", Locale.KOREA);
        Date currentTime = new Date();
        String batchTime = formatter.format(currentTime);
        logger.debug("[" + batchTime + "]배치시작~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        SimpleDateFormat formatter_1 = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String stdYm = formatter_1.format(currentTime);
        for (int i=0; i<tagetTableList.size(); i++) {
        	switch(tagetTableList.get(i).getTableId()) {
			case "hsmn_min_rising_kwrd" :
				this.executeOpenApiMinwonDataLink(tagetTableList.get(i).getTableId(), "keywordOpenApiList", stdYm);
	            break;
			case "hsmn_min_wd_cloud" :
				this.executeOpenApiMinwonDataLink(tagetTableList.get(i).getTableId(), "keywordWdCloud", stdYm);
	            break;
        	}
		}
    }
    
    public void excuteGyeonggiOpenapi(String tableId) throws Exception {
    	// table info 가져오기
		DataVO tableInfoVo = new DataVO();
		tableInfoVo = dataService.getDataTableInfo(tableId);
		String tOpenapiUrl = tableInfoVo.getOpenapiUrl();
		String tOpenapiKey = tableInfoVo.getOpenapiKey();
		String tMngStdYmdScd = tableInfoVo.getMngStdYmdScd();
		String tDataFilePath = tableInfoVo.getDataFilePath();
		
    	DataUpldLogVO dataUpldLogVo = new DataUpldLogVO();
		
		Date dt = new Date();
		String formatter = "";
		if (tMngStdYmdScd.equals("stdym")) {
			formatter = "yyyyMM";
		} else if (tMngStdYmdScd.equals("stdymd")) {
			formatter = "yyyyMMdd";
		}
		
		
		SimpleDateFormat sf = new SimpleDateFormat(formatter);
		String stdYm = sf.format(dt);
		String tSaveFileNm = tableId + "_" + stdYm + ".csv";//==> 변경필요
		
		tableInfoVo.setStdColumn("std_ym");
		tableInfoVo.setStdYm(stdYm);
		
		File file = new File(tSaveFileNm);
		FileWriter fw = null;
    	try {
    		//FileWriter fw = null;
			fw = new FileWriter(file);
			
    		//BufferedWriter fw =null;
			//fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getPath()),ftpTransferType));
			
			int pIndex = 1;
			int pSize = 1000;
			String requestUrl = tOpenapiUrl + "&type=json&KEY=" + tOpenapiKey + "&pSize=" + pSize + "&pIndex=" + pIndex;
			
			int callCount = getPageCount(requestUrl, pSize);
			List<String> dataList = new ArrayList<String>();
			
			// 호출한 개수만큼 리스트에 담는다.
			if (callCount > 0) {
				for (int index=1; index<=callCount; index++) {
					requestUrl = tOpenapiUrl + "&type=json&KEY=" + tOpenapiKey + "&pSize=" + pSize + "&pIndex=" + index;
					String rtnJsonString = requestAPIJsonData(requestUrl, "UTF-8"); 
					dataList.add(rtnJsonString);
					logger.debug("return String>>>>>>>" + rtnJsonString);
				}
			}
			
			if (dataList.size() > 0) {
				//기존자료 삭제
				dataService.deleteLinkData(tableInfoVo);
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
					
					// 헤더 생성
					if(h==0) {  //bskim 20200217 헤더는 1번만 작성.
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
									String orgVal = (dataJson.get(columnList.get(k)) == null ? null : dataJson.get(columnList.get(k)).toString());
									//String orgVal = (dataJson.get(columnList.get(k)) == null ? "" : dataJson.get(columnList.get(k)).toString());
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
			//fw.close();
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
				tableInfoVo.setUpdateId("batch");
				tableInfoVo.setStdYm(stdYm);
				dataService.insretCsvToTable(tableInfoVo);
	        }
        } catch(Exception e) {
        	//bskim 20200212 wirte한곳까지 우선 작성. finally에서 close후 delete
       	
			//오류 로그남기기.
			dataUpldLogVo.setTableId(tableInfoVo.getTableId());
			dataUpldLogVo.setTableNm(tableInfoVo.getTableNm());
			dataUpldLogVo.setDataConnScd(tableInfoVo.getDataConnScd());
			dataUpldLogVo.setLogTypeScd("fail");
			dataUpldLogVo.setLogMsg("[오류] API연계에 문제가 발생했습니다. "+e.toString());
			dataUpldLogVo.setCreateId("system");
			dataService.insertDataUpldLog(dataUpldLogVo);
        	
        	fw.flush();
        	logger.debug("ExceptionUtils.getRootCause::" + ExceptionUtils.getRootCause(e) + "");
        } finally {
        	//bskim 20200212 fw.close()추가
        	fw.close();
            if(file != null) {
                file.delete();
            }
        }
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
    
    // (사용안함) 기존 민원 API 요청 
    public void excuteRightsMinwonLink(String tableId) {
    	try {
    		// table info 가져오기
    		DataVO tableInfoVo = new DataVO();
    		tableInfoVo = dataService.getDataTableInfo(tableId);
    		
    		Date today = new Date();
    		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
    		String stdYm = sf.format(today);
    		
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
            
            String jsonObjectKeyNm = "";
    		switch(tableId) {
	            case "hsm_acrc_top_kwrd" : 
	            	jsonObjectKeyNm = "keywordApiList";
	                break;
	            case "hsm_acrc_incrs_kwrd" : 
	            	jsonObjectKeyNm = "keywordApiList"; 
	                break;
	            case "hsm_acrc_warn_kwrd" : 
	            	jsonObjectKeyNm = "keywordWarningApiList"; 
	                break;  
	            case "hsm_acrc_ntwrk_kwrd" :
	            	jsonObjectKeyNm = "keywordListList"; 
	                break;    
	            default:
	            	jsonObjectKeyNm = "keywordApiList";
	        }
    		
            if (jsonObjectKeyNm.equals("keywordApiList")) { //top, 급증
            	String hhmmdd = "000000";
    			tUrlParmas += "&periodStart=" + paramStDate + hhmmdd; 
    			tUrlParmas += "&periodEnd=" + paramEdDate + hhmmdd;
    			tUrlParmas += "&previousPeriodStart=" + paramBefStDate + hhmmdd;
    			tUrlParmas += "&previousPeriodEnd=" + paramBefEdDate + hhmmdd;
    		} else if (jsonObjectKeyNm.equals("keywordWarningApiList")) { //경보
    			tUrlParmas = "&stdde=" + paramStDate;
    		}
    		
            String tOpenapiUrl = tableInfoVo.getOpenapiUrl() + tUrlParmas;
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
        } catch(Exception e) {
        	logger.debug("ExceptionUtils.getRootCause::" + ExceptionUtils.getRootCause(e) + "");
        }
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
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
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
}
