package kr.co.socsoft.gis.main.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * gis 관련
 * @author SocSoft
 *
 */
@Controller
public class GisController {
	
	@Value("#{globals['Global.gis.naver.openapi.key']}")
	private String naverOpenapiKey;

	@Value("#{globals['Global.gis.daum.openapi.key']}")
	private String daumOpenapiKey;

	/**
	 * gis pop popup open (인구/매출)
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gis/pop.do")
	public String movePopPage(ModelMap model)throws Exception{		
		model.addAttribute("naverOpenapiKey", naverOpenapiKey);
		model.addAttribute("daumOpenapiKey",daumOpenapiKey);
		return "/gis/gisPop";
	}
	
	/**
	 * gis traffic open (교통)
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gis/traffic.do")
	public String moveTrafficPage(ModelMap model)throws Exception{
		model.addAttribute("naverOpenapiKey", naverOpenapiKey);
		model.addAttribute("daumOpenapiKey",daumOpenapiKey);
		return "/gis/gisTraffic";
	}
	
	/**
	 * top header 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gis/top.do")
	public String moveTopPage(String flag, ModelMap model)throws Exception{
		model.addAttribute("flag", flag);
		return "/gis/form/top";
	}
	
	/**
	 * side menu 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gis/menu.do")
	public String moveMenuPage(String flag, ModelMap model)throws Exception{
		model.addAttribute("flag", flag);
		return "/gis/form/menu/"+flag;
	}
	
	/**
	 * 인구매출 공통 영역설정 화면 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gis/area.do")
	public String moveAreaPage(String flag, ModelMap model)throws Exception{
		model.addAttribute("flag", flag);
		return "/gis/form/search/"+flag+"/popArea";
	}
	
	/**
	 * 버스 공통정보 영역설정 화면 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gis/bus.do")
	public String moveBusPage(String flag, ModelMap model)throws Exception{
		model.addAttribute("flag", flag);
		return "/gis/form/search/"+flag+"/busInfo";
	}
	
	/**
	 * 인구매출 공통 영역설정 화면 (법정동) 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gis/cstArea.do")
	public String moveCstAreaPage(String flag, ModelMap model)throws Exception{
		model.addAttribute("flag", flag);
		return "/gis/form/search/"+flag+"/popCstArea";
	}
	
	/**
	 * side search 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gis/search.do")
	public String moveSearchPage(String flag, String menu, ModelMap model)throws Exception{
		String page = menu.trim() + "Anals";
		model.addAttribute("flag", flag); //혅재 menu정보 저장 (지도 객체명)
		model.addAttribute("menu", menu); //메뉴명
		return "/gis/form/search/"+flag+"/"+page;
	}
	
	/**
	 * gis result 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gis/result.do")
	public String moveResultPage(String flag, String menu, ModelMap model)throws Exception{
		String page = menu.trim() + "Result";
		model.addAttribute("flag", flag); //혅재 menu정보 저장 (지도 객체명)
		model.addAttribute("menu", menu); //메뉴명
		return "/gis/form/result/"+flag+"/"+page;
	}
	
	/**
	 * gis map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gis/map.do")
	public String moveMapPage(String flag, ModelMap model)throws Exception{
		model.addAttribute("flag", flag);
		return "/gis/form/map";
	}
	

	@RequestMapping(value="/gis/map_v2.do")
	public String moveMapPage_v2(String flag, ModelMap model)throws Exception{
		model.addAttribute("flag", flag);
		return "/gis/form/map_v2";
	}
	
	@RequestMapping(value="/gis/pop_v2.do")
	public String movePopPage_v2(ModelMap model)throws Exception{		
//		model.addAttribute("naverOpenapiKey", naverOpenapiKey);
		model.addAttribute("daumOpenapiKey",daumOpenapiKey);
		return "/gis/gisPop_v2";
	}
	
	@RequestMapping(value="/gis/top_v2.do")
	public String moveTopPage_v2(String flag, ModelMap model)throws Exception{
		model.addAttribute("flag", flag);
		return "/gis/form/top_v2";
	}
	@RequestMapping(value="/gis/search_v2.do")
	public String moveSearchPage_v2(String flag, String menu, ModelMap model)throws Exception{
		String page = menu.trim() + "Anals";
		System.out.println("page : " + page);
		model.addAttribute("flag", flag); //혅재 menu정보 저장 (지도 객체명)
		model.addAttribute("menu", menu); //메뉴명
		return "/gis/form/search/"+flag+"/"+page+"_v2";
	}

	@RequestMapping(value="/gis/menu_v2.do")
	public String moveMenuPage_v2(String flag, ModelMap model)throws Exception{
		model.addAttribute("flag", flag);
		return "/gis/form/menu/"+flag+"_v2";
	}
	/**
	 * gis popup
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gis/popup_v2.do")
	public String movePopupPage_v2(String flag, ModelMap model)throws Exception{
		model.addAttribute("flag", flag);
		return "/gis/gisPopup_v2";
	}
	@RequestMapping(value="/gis/result_v2.do")
	public String moveResultPage_v2(String flag, String menu, ModelMap model)throws Exception{
		String page = menu.trim() + "Result";
		model.addAttribute("flag", flag); //혅재 menu정보 저장 (지도 객체명)
		model.addAttribute("menu", menu); //메뉴명
		return "/gis/form/result/"+flag+"/"+page + "_v2";
	}

	@RequestMapping(value="/gis/popup.do")
	public String movePopupPage(String flag, ModelMap model)throws Exception{
		model.addAttribute("flag", flag);
		return "/gis/gisPopup";
	}

	@RequestMapping(value = "/gis/kakaoKeyword.do")
	public @ResponseBody Object kakaoKeyword(@RequestParam Map<String, Object> keyword) {
		String url = "http://dapi.kakao.com/v2/local/search/keyword.json";
        
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + "e08d77deb7d505b0374f6451af138f06");
        headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("size", 10)
                .queryParam("page", 1)
                .queryParam("query", keyword.get("keyword"))
                .build(false);
		return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), Object.class).getBody();
	}
	
	@RequestMapping(value = "/gis/proxy.do")
	public void proxy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		OutputStream ostream = null;
		InputStream in = null;

		try {
			String orginalUrl = "url";
			int maxInactiveInterval = 3600;		// [second]
			
			// session 갱신
			request.getSession().setMaxInactiveInterval(maxInactiveInterval);
			// 한글인코딩
			request.setCharacterEncoding("UTF-8");	// tomcat server.xml UTF-8로 수정필요

			// GET방식에서 key, value를 url에 넣는 과정
			Map paramMap = request.getParameterMap();
			
			// post, get에서 쓸 url을 각각 지정
			String reqUrl = ((String[])paramMap.get(orginalUrl))[0];
			
			// kvp 파라미터 정리
			String middleUrl = "";
			
			if(reqUrl.indexOf("?") == -1){
				reqUrl = reqUrl + "?";
			} else {
				if (reqUrl.charAt(reqUrl.length()-1) != '?') {
					String[] tempUrl = reqUrl.split("\\?");
					String[] midUrl = tempUrl[1].split("\\=");
					if (midUrl.length == 2) {
						middleUrl = midUrl[0] + "=" + URLEncoder.encode(midUrl[1], "UTF-8") + "&";
					}
					reqUrl = tempUrl[0] + "?";
				}
			}

			Iterator iterator = paramMap.keySet().iterator();
			while (iterator.hasNext()) {
				
				String key = (String) iterator.next();
				
				if (key.equalsIgnoreCase(orginalUrl)) {
					continue;
				}
				
				String values[] = (String[]) paramMap.get(key);

				if (values[0] != null) {
					middleUrl = middleUrl + key + "=" + URLEncoder.encode(values[0], "UTF-8") + "&";
				}
			}
			
			String resultUrl = reqUrl + middleUrl;
			
			URL url = null;
			HttpURLConnection con = null;
			
			if (request.getMethod().equalsIgnoreCase("GET")) {
				url = new URL(resultUrl);
				
				con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod(request.getMethod());
				con.setRequestProperty("charset", "UTF-8");
				con.setRequestProperty("O2MAP_PID", request.getSession().getId());		// session through proxy
			} else {		// POST Method
				
				url = new URL(reqUrl);
				
				String cType = request.getContentType();
				if (cType.contains("multipart/form-data") == true) {	// multipart/form-data

					con = (HttpURLConnection) url.openConnection();
					con.setDoOutput(true);
					con.setRequestMethod(request.getMethod());
					con.setRequestProperty("Content-Type", cType);
					con.setRequestProperty("O2MAP_PID", request.getSession().getId());
					
					int clength = request.getContentLength();
					InputStream istream = null;
					OutputStream os = null;
					if (clength > 0) {
						con.setDoInput(true);
			            istream = request.getInputStream();
			            os = con.getOutputStream();
			            final int length = 5000;
				        byte[] bytes = new byte[length];
				        int bytesRead = 0;
				        while ((bytesRead = istream.read(bytes, 0, length)) > 0) {
			    	      os.write(bytes, 0, bytesRead);
			        	}
			      	}
					
					if (os != null) {
						os.flush();
						os.close();
						os = null;
					}
					
					if (istream != null) {
						istream.close();
						istream = null;
					}
					
				} else {
					// POST방식에서 inputStream을 StringBuilder에 붙임
					StringBuilder sb = new StringBuilder();
					InputStreamReader is = new InputStreamReader(request.getInputStream());
					BufferedReader br = new BufferedReader(is);
					String read = br.readLine();
				
					while (read != null) {
						sb.append(read);	
						read = br.readLine();
					}
					
					String strBody = sb.toString();
					con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod(request.getMethod());
					con.setDoOutput(true);
					con.setUseCaches(false);
					con.setRequestProperty("charset", "UTF-8");
					con.setRequestProperty("O2MAP_PID", request.getSession().getId());		// session through proxy
					if (strBody.trim().startsWith("<")) {
						// XML of Post Method
						con.setRequestProperty("Content-Type", "application/xml");
					} else {
						// KVP of Post Method
						strBody = strBody + middleUrl;
						con.setRequestProperty("Content-Type", request.getContentType());
					}
					
					byte[] postData = strBody.getBytes();
					con.setRequestProperty("Content-Length", Integer.toString(postData.length));
					con.getOutputStream().write(postData);
				}
			}
			
			ostream = response.getOutputStream();
			response.setContentType(con.getContentType());
			
			in = con.getInputStream();
			final int length = 5000;
			byte[] bytes = new byte[length];
			int bytesRead = 0;
			while ((bytesRead = in.read(bytes, 0, length)) > 0) {
				ostream.write(bytes, 0, bytesRead);
			}

			
		} catch (Exception e) {
			response.setStatus(500);
		} finally {
			if (ostream != null) {
				ostream.flush();
				ostream.close();
				ostream = null;
			}
			if (in != null) {
				in.close();
				in = null;
			}
		}
	}
}