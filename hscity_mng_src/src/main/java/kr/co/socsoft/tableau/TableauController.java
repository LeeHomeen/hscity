package kr.co.socsoft.tableau;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.ModelMap;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "/tableau")
public class TableauController {
	private static final Logger logger = LoggerFactory.getLogger(TableauController.class);
	
	@Value("#{globals['Globals.TableauUrl']}")
	private String TableauUrl;

	@Value("#{globals['Globals.TableauUserId']}")
	private String TableauUserId;
	
	
	@Value("#{globals['Globals.TableauUserPwd']}")
	private String TableauUserPwd;
	

		
	@RequestMapping(value = "/goTableauTicket.do")
	public @ResponseBody Map<String, String> getTableauTicket(ModelMap model, HttpServletRequest request) throws Exception {
		/**
		 * 태블로 인증페이지
		 * 태블로 인증을 통해 티켓을 발행받아 온다. 태블로 화면(BI)을 연결하기 위해선 이 티켓값이 필요함.
		 * ticket 값을 BI공유 URL에 붙여서 사용한다.
		 */		
		
		String host=TableauUrl;

		//System.out.println("태블로 호스트::"+biServerUrl);
		OutputStreamWriter outStream = null;
		BufferedReader in = null;
		StringBuffer rsp = new StringBuffer();
		String token = "trusted/";
		String param = "?:tabs=no&:embed=y&:showShareOptions=true&:display_count=no&:showVizHome=no&:toolbar=no&:render=false";
		String urls= host;
		String ticket = "";

		Map<String, String> result = new HashMap<String, String>();
		result.put("TableauUrl", TableauUrl);
		
		try
		{
			//System.out.println("태블로 아이디::"+biServerUserId);
			    // 태블로 계정 정보 등록
				StringBuffer data = new StringBuffer();
				
				data.append(URLEncoder.encode("username", "UTF-8"));
				data.append("=");
				data.append(URLEncoder.encode(TableauUserId, "UTF-8"));  //태블로 서버의 사용자 ID 넣을것.


				// Send the request
				URL url = new URL(host+"/trusted");
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				outStream = new OutputStreamWriter(conn.getOutputStream());
				outStream.write(data.toString());
				outStream.flush();

				// Read the response

				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				// 태블로 신뢰인증을 위한 ticket 생성
				String line;
				while ( (line = in.readLine()) != null)
				{
			    	rsp.append(line);
				}

		 		ticket = rsp.toString();
		 		//System.out.println("티켓::"+ticket);
		}
		catch (Exception e)
		{
			ticket="err";
			e.printStackTrace();
		    throw new ServletException(e);
		    
		}
		finally
		{
			result.put("ticket", ticket);
		    
			try
		    {
		        if (in != null) in.close();
		        if (outStream != null) outStream.close();
		    }
		    catch (IOException e)
		    {
		    	e.printStackTrace();
		    	 throw new ServletException(e);
		    }
		}
	
		
		return result;		
	}	
	

}
