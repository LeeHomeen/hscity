package kr.co.socsoft.internal.main.web;

import java.net.InetAddress;
import java.text.DecimalFormat;
import java.util.HashMap;

import kr.co.socsoft.menu.vo.CategoryVo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.EgovUserDetailsHelper;
import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.internal.main.service.InMainService;
import kr.co.socsoft.internal.main.vo.MenuCategoryVO;
import kr.co.socsoft.manage.board.vo.BoardVO;
import kr.co.socsoft.menu.service.CategoryService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@Controller
@RequestMapping(value = "/internal")
public class InMainController {
	private static final Logger logger = LoggerFactory.getLogger(InMainController.class);

	@Resource(name = "inMainService")
	private InMainService inMainService; 
	
	@Resource(name = "categoryService")
	private CategoryService categoryService;
	
	
	@Value("#{globals['Global.dataportal.weather.openapi.key']}")
	private String weatherApiKey;
	
	@Value("#{globals['Global.openApiportal.weather.key']}")
	private String openApiWeatherApiKey;
	
	
	@Value("#{globals['Global.bi.stats.user.access']}")
	private String biStatsUserAccess;
	
    @RequestMapping(value = "leftMenu.do")
    public String leftMenu(Model model) throws Exception {
    		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();

	    	Map<String, Object> newParams = new HashMap<>();
	    	newParams.put("deptId", loginVO.getDeptId());
        newParams.put("userId", loginVO.getUserId());
        List<MenuCategoryVO> result = inMainService.getMenuCategory(newParams);
    
        String html = "";
        for(int i = 0; i < result.size(); i++) {
		      if(result.get(i).getLevel() == 1) {
		        	html += "<h3 id='" + result.get(i).getCategoryMenuCd() + "'>" + result.get(i).getCategoryMenuNm() + "</h3>";
			        	if(i < (result.size() - 1)) {
			        		if(result.get(i + 1).getLevel() == 2) {
			        			html += "<div>\n" + 
				        					"<ul>\n";
				        				for(int e = (i + 1); e < result.size(); e++) {
				        					if(result.get(e).getLevel() == 2) {
				        						if(result.get(e).getGbn().equals("m")) {
				        							//html += "<ul>\n" ;
		        						 			html += 		"<li id='" + result.get(e).getMenuGbn() + "/" +  result.get(e).getCategoryMenuCd() +"'><a href='#' id='" + result.get(e).getUrl() + "'>" + result.get(e).getCategoryMenuNm() + "</a></li>\n" ;
		        						 			//html += "</ul>\n";
				        						} else if(result.get(e).getGbn().equals("c")) {  //2뎁스이면서 카테고리인것
				        							html += "<li>" + result.get(e).getCategoryMenuNm() +"\n" ;
				        							//레벨 3,4인 것 구하기.
				        						 	if(e < (result.size() - 1)) {
				        						 		if(result.get(e + 1).getLevel() == 3 || result.get(e + 1).getLevel() == 4) {
				        						 			int count = 0;
				        						 			for(int x = 1; x < (result.size() - e); x++) {
				        						 				if((result.get(e + x).getLevel()) == 3 || (result.get(e + x).getLevel()) == 4) {
				        						 					count++;
				        						 				}else {
				        						 					x = (result.size() - e);
				        						 				}
				        						 			}
				        						 			/*
				        						 			 * 쿼리가 SORT 되어 있다는 전제에서 구성함.
				        						 			 */
				        						 			html += "<ul>\n" ;
				        						 			for(int z = 0; z < count; z++) {
				        						 			    if(result.get(e + (z+1)).getGbn().equals("m")){
				        						 			    	html += 	"<li id='" + result.get(e + (z+1)).getMenuGbn() +"/" +  result.get(e +  (z+1)).getCategoryMenuCd() + "'><a href='#' id='" + result.get(e +  (z+1)).getUrl() + "'>" + result.get(e +  (z+1)).getCategoryMenuNm() + "</a></li>\n" ;
				        						 			    }else{
				        						 			    	html +="<li>"+result.get(e + (z+1)).getCategoryMenuNm() 
				        						 			    			+"<ul>";
				        						 			    	  int cnt = z;
				        						 			    	  for(int j=0; j < count; j++){
				        						 			    		 if(result.get(j+(e+cnt+2)).getLevel()==4){
				        						 			    			 html += 	"<li id='" + result.get(j+(e+cnt+2)).getMenuGbn() +"/" +  result.get(j+(e+cnt+2)).getCategoryMenuCd() + "'><a href='#' id='" + result.get(j+(e+cnt+2)).getUrl() + "'>" + result.get(j+(e+cnt+2)).getCategoryMenuNm() + "</a></li>\n" ; 
				        						 			    	         
				        						 			    			 z++;
				        						 			    		 }else{
				        						 			    			 //레벨3을 만나면 빠져나오기
				        						 			    			 break;
				        						 			    		 }
				        						 			    	  }
				        						 			    	html +="</ul>"
				        						 			    			+ "</li>";
				        						 			    }
				        						 				
				        						 			}			        						 			
				        						 			
				        						 			html += "</ul>\n";
				        						 		}
				        						 	}
				        						}
			        						 	html += "</li>\n" ;
				        					}else if (result.get(e).getLevel() == 1) {
				        						break;
				        					}
				        				}
				        				html += "</ul>\n" +
			        				"</div>";
			        		} 
			        		
			        	}
		      }
        }
// 		System.out.println(html);	
        model.addAttribute("leftMenu", html);
        return "/internal/layout/left";
    }
    
    @RequestMapping(value = "mainSubPage.do")
    public ModelAndView sub2(ModelAndView mv) throws Exception {
    		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
    			mv.addObject("notice", inMainService.getMainNotice());
    			//mv.addObject("noticeFile", inMainService.getMainNotice());
			mv.addObject("hsPop", toNumFormat(inMainService.getHsPopulations()));
			mv.addObject("allPop", toNumFormat(inMainService.getAllPopulations()));
			mv.addObject("dataCount", toNumFormat(inMainService.getDataCount()));
			mv.addObject("logCount", toNumFormat(inMainService.getLogCount()));
			//mv.addObject("weatherApiKey",weatherApiKey);
			mv.addObject("biStatsUserAccess",biStatsUserAccess);

			
			//bskim 추가 20190404 공공데이터포털 동네예보 가져오기(20200820 ksj openapi 동네예보 조회서비스로 변경)
			 BufferedReader br = null;
			 String strWeather ="";
		        try{            
		        	Calendar cal = Calendar.getInstance();
		        	String base_date = String.format("%04d",cal.get(cal.YEAR)) + String.format("%02d",cal.get(cal.MONTH)+1) +String.format("%02d",cal.get(cal.DAY_OF_MONTH));
		        	String base_time = String.format("%02d", cal.get(cal.HOUR_OF_DAY))+ String.format("%02d", cal.get(cal.MINUTE));
		        	
//		        	(미사용)newsky2 서비스 종료 예상됨
//		        	String urlstr = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastTimeData?serviceKey="
//		                    + weatherApiKey
//		                    +"&base_date=" + base_date
//		                    +"&base_time=" + base_time
//		                    +"&nx=57"
//		                    +"&ny=119"
//		                    +"&numOfRows=50"
//		                    +"&pageSize=10"
//		                    +"&pageNo=1"
//		                    +"&startPage=1"
//		                    +"&_type=json";
		        	// (신규) openapi 동네예보 조회서비스
		        	String urlstr = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtFcst?serviceKey="
		        		+openApiWeatherApiKey
		        		+"&base_date=" + base_date
		        		+"&base_time=" + base_time
		        		+"&nx=57&ny=119"
		        		+"&pageNo=1"
		        		+"&numOfRows=100"
		        		+"&dataType=json";
		            URL url = new URL(urlstr);
		            /*
		            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
		            urlconnection.setRequestMethod("GET");
		            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
		            String result = "";
		            String line;
		            while((line = br.readLine()) != null) {
		                result = result + line + "\n";
		            }
		            strWeather = result;
		            */
		            
		            InputStream in = url.openStream(); 
		            CachedOutputStream bos = new CachedOutputStream();
		            IOUtils.copy(in, bos);
		            in.close();
		            bos.close();
		            
//		            out.println(addr);
		            
		           strWeather = bos.getOut().toString();     
		            
		        }catch(Exception e){
		        	strWeather=e.toString();
		        }
		      //System.out.print("날씨:::"+strWeather);
		      mv.addObject("strWeather",strWeather);
			  mv.setViewName("/internal/mainSubPage");
			
        return mv;
    }
    
    

    
    public static String toNumFormat(int num) {
    	  DecimalFormat df = new DecimalFormat("#,###");
    	  return df.format(num);
    	 }
    
    @RequestMapping(value = "/index.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView index(ModelAndView mv, HttpServletRequest request) throws Exception {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		
    	Map<String, Object> newParams = new HashMap<>();
    	newParams.put("deptId", loginVO.getDeptId());
    newParams.put("userId", loginVO.getUserId());
    List<MenuCategoryVO> result = inMainService.getMenuCategory(newParams);

    String html = "";
    for(int i = 0; i < result.size(); i++) {
	      if(result.get(i).getLevel() == 1) {
	        	html += "<h3 id='" + result.get(i).getCategoryMenuCd() + "'>" + result.get(i).getCategoryMenuNm() + "</h3>";
		        	if(i < (result.size() - 1)) {
		        		if(result.get(i + 1).getLevel() == 2) {
		        			html += "<div style=\"overflow-y: auto; height:400px;\">\n" + 
			        					"<ul>\n";
			        				for(int e = (i + 1); e < result.size(); e++) {
			        					if(result.get(e).getLevel() == 2) {
			        						if(result.get(e).getGbn().equals("m")) {
			        							//html += "<ul>\n" ;
	        						 			html += 		"<li id='" + result.get(e).getMenuGbn() + "/" +  result.get(e).getCategoryMenuCd() +"'><a href='#' id='" + result.get(e).getUrl() + "'>" + result.get(e).getCategoryMenuNm() + "</a></li>\n" ;
	        						 			//html += "</ul>\n";
			        						} else if(result.get(e).getGbn().equals("c")) {  //2뎁스이면서 카테고리인것
			        							html += "<li>" + result.get(e).getCategoryMenuNm() +"\n" ;
			        							//레벨 3,4인 것 구하기.
			        						 	if(e < (result.size() - 1)) {
			        						 		if(result.get(e + 1).getLevel() == 3 || result.get(e + 1).getLevel() == 4) {
			        						 			int count = 0;
			        						 			for(int x = 1; x < (result.size() - e); x++) {
			        						 				if((result.get(e + x).getLevel()) == 3 || (result.get(e + x).getLevel()) == 4) {
			        						 					count++;
			        						 				}else {
			        						 					x = (result.size() - e);
			        						 				}
			        						 			}
			        						 			/*
			        						 			 * 쿼리가 SORT 되어 있다는 전제에서 구성함.
			        						 			 */
			        						 			html += "<ul>\n" ;
			        						 			for(int z = 0; z < count; z++) {
			        						 			    if(result.get(e + (z+1)).getGbn().equals("m")){
			        						 			    	html += 	"<li id='" + result.get(e + (z+1)).getMenuGbn() +"/" +  result.get(e +  (z+1)).getCategoryMenuCd() + "'><a href='#' id='" + result.get(e +  (z+1)).getUrl() + "'>" + result.get(e +  (z+1)).getCategoryMenuNm() + "</a></li>\n" ;
			        						 			    }else{
			        						 			    	html +="<li style=\"background-position: 0px 2px !important;\">"+result.get(e + (z+1)).getCategoryMenuNm() 
			        						 			    			+"<ul>";
			        						 			    	  int cnt = z;
			        						 			    	  for(int j=0; j < count; j++){
			        						 			    		 if(result.get(j+(e+cnt+2)).getLevel()==4){
			        						 			    			 html += 	"<li id='" + result.get(j+(e+cnt+2)).getMenuGbn() +"/" +  result.get(j+(e+cnt+2)).getCategoryMenuCd() + "'><a href='#' id='" + result.get(j+(e+cnt+2)).getUrl() + "'>" + result.get(j+(e+cnt+2)).getCategoryMenuNm() + "</a></li>\n" ; 
			        						 			    	         
			        						 			    			 z++;
			        						 			    		 }else{
			        						 			    			 //레벨3을 만나면 빠져나오기
			        						 			    			 break;
			        						 			    		 }
			        						 			    	  }
			        						 			    	html +="</ul>"
			        						 			    			+ "</li>";
			        						 			    }
			        						 				
			        						 			}			        						 			
			        						 			
			        						 			html += "</ul>\n";
			        						 		}
			        						 	}
			        						}
		        						 	html += "</li>\n" ;
			        					}else if (result.get(e).getLevel() == 1) {
			        						break;
			        					}
			        				}
			        				html += "</ul>\n" +
		        				"</div>";
		        		} 
		        	}
	      }
  }

    		
    

           //	mv.addObject("leftMenu", html);
			mv.addObject("sysYn", loginVO.getSysYn());
			mv.addObject("menuCd", request.getParameter("menuCd"));
			mv.addObject("gbn", request.getParameter("gbn"));
			mv.addObject("url", request.getParameter("url"));
			
			
		
			//logger.debug("파라메터 >>>>>>>" + request.getParameter("menuCd") + ">>>>>"+ request.getParameter("gbn") + ">>>>>"+ request.getParameter("url"));
//			logger.debug("HTML:: "+html);
			mv.addObject("leftMenu", html);
			mv.setViewName("/internal/index");
		
		return mv;
	}

//	@RequestMapping(value = "/index.do", method = {RequestMethod.GET, RequestMethod.POST})
//	public ModelAndView userList(ModelAndView mv, HttpServletRequest request) throws Exception {
//		 LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
//		 
//			mv.addObject("sysYn", loginVO.getSysYn());
//			mv.addObject("notice", inMainService.getMainNotice());
//			mv.addObject("hsPop", toNumFormat(inMainService.getHsPopulations()));
//			mv.addObject("allPop", toNumFormat(inMainService.getAllPopulations()));
//			mv.addObject("dataCount", toNumFormat(inMainService.getDataCount()));
//			mv.addObject("logCount", toNumFormat(inMainService.getLogCount()));
//			mv.addObject("weatherApiKey",weatherApiKey);
//		mv.setViewName("/internal/index");
//		
//		
//		return mv;
//	}
//	
	@ResponseBody
	@RequestMapping(value = "/getMenuCategory.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<MenuCategoryVO> getUserPermissionList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		 Map<String, Object> newParams = new HashMap<>();
		 LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 newParams.put("deptId", loginVO.getDeptId());

		 logger.debug("menuNm>>>>>>>" + params.get("menuNm"));
		 logger.debug("userId>>>>>>>" + loginVO.getUserId());
		 
		 if (params.get("menuNm") != null && !params.get("menuNm").equals("")) {
			 newParams.put("menuNm", params.get("menuNm").toString());
	     }
		 newParams.put("userId", loginVO.getUserId());
		 List<MenuCategoryVO> result = inMainService.getSearchMenuCategory(newParams);
		 return result;
    }
	
	@RequestMapping(value = "/subPage.do")
	public ModelAndView subPage(ModelAndView mv, HttpServletRequest request) throws Exception {
		 LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> newParams = new HashMap<>();
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setCategoryType("biz");
		categoryVo.setMenuCd(request.getParameter("menuCd"));

		newParams.put("userId" , loginVO.getUserId());
		newParams.put("menuCd", request.getParameter("menuCd"));
		
		mv.addObject("url", request.getParameter("url"));
		
		mv.addObject("menuCd", request.getParameter("menuCd"));
		mv.addObject("attachFile", categoryService.getMenuDetailAttachFile(categoryVo));
		mv.addObject("menuVo", inMainService.getMenuInfo(newParams));
		mv.addObject("sysYn", loginVO.getSysYn());

		mv.setViewName("/internal/subPage");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMenuInfomation.do")
	public Map<String, Object> getMenuInfomation(@RequestBody Map<String, Object> params) throws Exception {
		 LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> result = new HashMap<>();
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setCategoryType("biz");
		categoryVo.setMenuCd(params.get("menuCd").toString());

		result.put("userId" , loginVO.getUserId());
		result.put("menuCd", params.get("menuCd").toString());
		result.put("menuCd", params.get("menuCd").toString());
		result.put("attachFile", categoryService.getMenuDetailAttachFile(categoryVo));
		result.put("menuVo", inMainService.getMenuInfo(result));
		result.put("sysYn", loginVO.getSysYn());

		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMainImg.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<CategoryVo> getMainImg(HttpServletRequest request) throws Exception {
		 return inMainService.getMainImg();
    }
	
	@ResponseBody
	@RequestMapping(value = "/setNewPassword.do",produces = "application/text; charset=utf8", method = {RequestMethod.GET, RequestMethod.POST})
    public String setNewPassword(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		
		Map<String, Object> newParams = new HashMap<>();
		newParams.put("userPw" , params.get("userPw"));
		newParams.put("userId" , loginVO.getUserId());
		
		 if(1 == inMainService.getCheckPassYn(newParams)) {
			newParams.put("userPwNew" , params.get("userPwNew"));		
			
			int result = inMainService.setNewPassword(newParams);

			return result == 1 ? "비밀번호가 변경되었습니다.":"비밀번호 변경이 실패하였습니다 관리자에게 문의하세요";
		 } else {
			 return "비밀번호가 틀렸습니다 확인해주세요 ";
		 }
    }
	
	@ResponseBody
	@RequestMapping(value = "/setFavPage.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String setFavPage(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> newParams = new HashMap<>();
		
		newParams.put("menuCd" ,params.get("menuCd"));
		 logger.debug("menuCd>>>>>>>" + params.get("menuCd"));

		newParams.put("userId" , loginVO.getUserId());
		
		int checkResult = inMainService.checkFavPage(newParams);
		String result = "";
		if(checkResult == 1) {
			//즐겨 찾기가 이미 있기때문에 삭제처리한다.
			result = (inMainService.removeFavPage(newParams) == 1)? "즐겨찾기가 삭제되었습니다." : "즐겨찾기 삭제에 실패하였습니다 관리자에게 문의하세요";
		}else if(checkResult == 0) {
			//즐겨찾기에 없다면  insert 
			result = (inMainService.setFavPage(newParams) == 1)? "즐겨찾기가 추가되었습니다." : "즐겨찾기 추가에 실패하였습니다 관리자에게 문의하세요";
		}
		
		
		 return result;
    }
	@ResponseBody
	@RequestMapping(value = "/getFavMenuList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<MenuCategoryVO> getFavMenuList(HttpServletRequest request) throws Exception {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 Map<String, Object> newParams = new HashMap<>();
		 
		 newParams.put("deptId", loginVO.getDeptId());
		 newParams.put("userId", loginVO.getUserId());
		 List<MenuCategoryVO> result = inMainService.getFavMenuList(newParams);
	
		 return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/setBizMenuLog.do", method = {RequestMethod.GET, RequestMethod.POST})
    public void setBizMenuLog(@RequestBody Map<String, Object> params,HttpServletRequest request) throws Exception {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 Map<String, Object> newParams = new HashMap<>();
		 
		 newParams.put("menuCd", params.get("menuCd"));
		 newParams.put("userId", loginVO.getUserId());
		 InetAddress local = InetAddress.getLocalHost();
		 newParams.put("ip", local.getHostAddress());
		 int result = inMainService.setBizMenuLog(newParams);
	
	}
	
	
//	 @RequestMapping(value = "/noticeView.do")
//	    public String boardView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) {
//	        model.addAttribute("result", manageBoardService.selectNoticeBoard(boardVO));
//
//	        return "/internal/notice/noticeView";
//	    }
	

}