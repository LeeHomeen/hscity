package kr.co.socsoft.stats.web;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;

import egovframework.com.cmm.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import kr.co.socsoft.common.vo.JqgridVO;
import kr.co.socsoft.data.vo.DbUpldLogVO;
import kr.co.socsoft.stats.service.StatsService;
import kr.co.socsoft.stats.vo.AccessVO;
import kr.co.socsoft.util.JsonHelper;

@Controller
@RequestMapping(value = "/stats")
public class StatsController {
	private static final Logger logger = LoggerFactory.getLogger(StatsController.class);
	
	@Resource(name = "statsService")
	private StatsService statsService;
	
	@Value("#{globals['Global.bi.stats.public.access']}")
	private String biStatsPubAccessUrl;

	@Value("#{globals['Global.bi.stats.public.menu']}")
	private String biStatsPubMenuUrl;
	
	@Value("#{globals['Global.bi.stats.user.access']}")
	private String biStatsUserAccessUrl;
	
	@Value("#{globals['Global.bi.stats.user.menu']}")
	private String biStatsUserMenuUrl;


	
	@RequestMapping(value = "/{menu}/publicAccessCountList.do")
	public String publicAccessCountList(@PathVariable(value="menu") String menu, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("type", "stats");
        model.addAttribute("menu", menu);
        model.addAttribute("biUrl",biStatsPubAccessUrl);
		return "/admin/stats/publicAccessCountList";
	}
	
	
	@RequestMapping(value = "/{menu}/userAccessCountList.do")
	public String userAccessCountList(@PathVariable(value="menu") String menu, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("type", "stats");
        model.addAttribute("menu", menu);
        model.addAttribute("biUrl",biStatsUserAccessUrl);
		return "/admin/stats/userAccessCountList";
	}
	
	
	
	@RequestMapping(value = "/{menu}/publicMenuCountList.do")
	public String publicMenuCountList(@PathVariable(value="menu") String menu, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("type", "stats");
        model.addAttribute("menu", menu);
        model.addAttribute("biUrl",biStatsPubMenuUrl);
		return "/admin/stats/publicMenuCountList";
	}
	

	
	@RequestMapping(value = "/{menu}/userMenuCountList.do")
	public String userMenuCountList(@PathVariable(value="menu") String menu, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("type", "stats");
        model.addAttribute("menu", menu);
        model.addAttribute("biUrl",biStatsUserMenuUrl);
		return "/admin/stats/userMenuCountList";
	}
	
	
	
	
	
	
	/**
	 * 대시민 접속 리스트
	 * @param jqgridVO
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/{menu}/getPublicAccessCountList.do")
    @ResponseBody
    public Map<String, Object> getPublicAccessCountList(JqgridVO jqgridVO, AccessVO searchVO) throws Exception {
        

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
        List<AccessVO> items = statsService.getPublicAccessCountList(searchVO);

        int totCnt = statsService.getPublicAccessCountListTot(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        result.put("total", paginationInfo.getTotalPageCount());
        result.put("page", jqgridVO.getPage());
        result.put("records", totCnt);
        result.put("rows", items);

        return result;
    }
	
	
	

	
	/**
	 * 내부용사용자 접속 리스트
	 * @param jqgridVO
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/{menu}/getUserAccessCountList.do")
    @ResponseBody
    public Map<String, Object> getUserAccessCountList(JqgridVO jqgridVO, AccessVO searchVO) throws Exception {
        

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
        List<AccessVO> items = statsService.getUserAccessCountList(searchVO);

        int totCnt = statsService.getUserAccessCountListTot(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        result.put("total", paginationInfo.getTotalPageCount());
        result.put("page", jqgridVO.getPage());
        result.put("records", totCnt);
        result.put("rows", items);

        return result;
    }
	

	
	

	
	/**
	 * 대시민 메뉴접속 리스트
	 * @param jqgridVO
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/{menu}/getPublicMenuCountList.do")
    @ResponseBody
    public Map<String, Object> getPublicMenuCountList(JqgridVO jqgridVO, AccessVO searchVO) throws Exception {
        

		if(searchVO.getCategory1depth().equals("") && searchVO.getCategory2depth().equals("") && searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(0);
			searchVO.setCategoryCd("");
		}
		
		if(!searchVO.getCategory1depth().equals("") && searchVO.getCategory2depth().equals("") && searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(2);
			searchVO.setCategoryCd(searchVO.getCategory1depth());
		}
		
		if(!searchVO.getCategory2depth().equals("") && searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(3);
			searchVO.setCategoryCd(searchVO.getCategory2depth());
		}

		if(!searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(4);
			searchVO.setCategoryCd(searchVO.getCategory3depth());
		}
		
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
        List<AccessVO> items = statsService.getPublicMenuCountList(searchVO);

        int totCnt = statsService.getPublicMenuCountListTot(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        result.put("total", paginationInfo.getTotalPageCount());
        result.put("page", jqgridVO.getPage());
        result.put("records", totCnt);
        result.put("rows", items);

        return result;
    }
	
	
	
	/**
	 * 내부용 사용자 메뉴접속 리스트
	 * @param jqgridVO
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/{menu}/getUserMenuCountList.do")
    @ResponseBody
    public Map<String, Object> getUserMenuCountList(JqgridVO jqgridVO, AccessVO searchVO) throws Exception {
	
		if(searchVO.getCategory1depth().equals("") && searchVO.getCategory2depth().equals("") && searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(0);
			searchVO.setCategoryCd("");
		}
		
		if(!searchVO.getCategory1depth().equals("") && searchVO.getCategory2depth().equals("") && searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(2);
			searchVO.setCategoryCd(searchVO.getCategory1depth());
		}
		
		if(!searchVO.getCategory2depth().equals("") && searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(3);
			searchVO.setCategoryCd(searchVO.getCategory2depth());
		}

		if(!searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(4);
			searchVO.setCategoryCd(searchVO.getCategory3depth());
		}


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
        List<AccessVO> items = statsService.getUserMenuCountList(searchVO);

        int totCnt = statsService.getUserMenuCountListTot(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        result.put("total", paginationInfo.getTotalPageCount());
        result.put("page", jqgridVO.getPage());
        result.put("records", totCnt);
        result.put("rows", items);

        return result;
    }
	
	

	
	
	
    @RequestMapping(value="/{menu}/getPublicAccessCountExcel.do")
    public String getPublicAccessCountExcel(Model model, AccessVO searchVO) throws Exception {
        model.addAttribute("excel_name", "대시민사용자접속정보");
        model.addAttribute("excel_title", new String[]{"순번","접속일자","접속IP","브라우저"});
        model.addAttribute("excel_column", new String[]{"log_seq", "conn_dt", "conn_ip", "conn_browser"});
        model.addAttribute("data_list", statsService.getPublicAccessCountExcel(searchVO));
        return "dataExcelView";
    }
	
    @RequestMapping(value="/{menu}/getUserAccessCountExcel.do")
    public String getUserAccessCountExcel(Model model, AccessVO searchVO) throws Exception {
        model.addAttribute("excel_name", "내부사용자접속정보");
        model.addAttribute("excel_title", new String[]{"순번","접속일자","접속IP","사용자ID","사용자명","브라우저"});
        model.addAttribute("excel_column", new String[]{"log_seq", "conn_dt", "conn_ip","user_id","user_nm", "conn_browser"});
        model.addAttribute("data_list", statsService.getUserAccessCountExcel(searchVO));
        return "dataExcelView";
    }


    
  
    @RequestMapping(value="/{menu}/getUserMenuCountExcel.do")
    public String getUserMenuCountExcel(Model model, AccessVO searchVO) throws Exception {
      
		if(searchVO.getCategory1depth().equals("") && searchVO.getCategory2depth().equals("") && searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(0);
			searchVO.setCategoryCd("");
		}
		
		if(!searchVO.getCategory1depth().equals("") && searchVO.getCategory2depth().equals("") && searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(2);
			searchVO.setCategoryCd(searchVO.getCategory1depth());
		}
		
		if(!searchVO.getCategory2depth().equals("") && searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(3);
			searchVO.setCategoryCd(searchVO.getCategory2depth());
		}

		if(!searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(4);
			searchVO.setCategoryCd(searchVO.getCategory3depth());
		}
    	
    	model.addAttribute("excel_name", "내부사용자접속정보");
        model.addAttribute("excel_title", new String[]{"순번","접속일자","카테고리","메뉴명","메뉴코드","사용자ID","사용자명"});
        model.addAttribute("excel_column", new String[]{"log_seq", "conn_dt", "category_path","menu_nm","menu_cd","user_id","user_nm"});
        model.addAttribute("data_list", statsService.getUserMenuCountExcel(searchVO));
        return "dataExcelView";
    }



    @RequestMapping(value="/{menu}/getPublicMenuCountExcel.do")
    public String getPublicMenuCountExcel(Model model, AccessVO searchVO) throws Exception {
      
		if(searchVO.getCategory1depth().equals("") && searchVO.getCategory2depth().equals("") && searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(0);
			searchVO.setCategoryCd("");
		}
		
		if(!searchVO.getCategory1depth().equals("") && searchVO.getCategory2depth().equals("") && searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(2);
			searchVO.setCategoryCd(searchVO.getCategory1depth());
		}
		
		if(!searchVO.getCategory2depth().equals("") && searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(3);
			searchVO.setCategoryCd(searchVO.getCategory2depth());
		}

		if(!searchVO.getCategory3depth().equals("")){
			searchVO.setCategoryLevel(4);
			searchVO.setCategoryCd(searchVO.getCategory3depth());
		}
    	
    	model.addAttribute("excel_name", "내부사용자접속정보");
        model.addAttribute("excel_title", new String[]{"순번","접속일자","카테고리","메뉴명","메뉴코드","접속IP"});
        model.addAttribute("excel_column", new String[]{"log_seq", "conn_dt", "category_path","menu_nm","menu_cd","conn_ip"});
        model.addAttribute("data_list", statsService.getPublicMenuCountExcel(searchVO));
        return "dataExcelView";
    }



}
