package kr.co.socsoft.login.web;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.EgovUserDetailsHelper;
import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.internal.main.service.InMainService;
import kr.co.socsoft.login.service.LoginService;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name = "loginService")
	private LoginService loginService;
	
	@Resource(name = "inMainService")
	private InMainService inMainService; 
	
	@RequestMapping(value = "/loginPage.do")
	public String loginPage(HttpServletRequest request, Model model) throws Exception {
		return "/internal/main/login";
	}
	
    private String getBrowser(HttpServletRequest request) {
  	  String header = request.getHeader("User-Agent");
  	  if (header != null) {
  	   if (header.indexOf("Trident") > -1) {
  	    return "ie";
  	   } else if (header.indexOf("Chrome") > -1) {
  	    return "chrome";
  	   } else if (header.indexOf("Opera") > -1) {
  	    return "opera";
  	   } else if (header.indexOf("iPhone") > -1
  	     && header.indexOf("Mobile") > -1) {
  	    return "iphone";
  	   } else if (header.indexOf("Android") > -1
  	     && header.indexOf("Mobile") > -1) {
  	    return "android";
  	   }
  	  }
  	  return "firefox";
  	 }

	@RequestMapping(value = "/userLogin.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView userLogin(HttpServletRequest request, ModelAndView mv) throws Exception {
		String loginId = request.getParameter("userId");
		String loginPwd = request.getParameter("userPwd");
		String returnPage = "";
		
		Map<String, Object> params = new HashMap<>();
        params.put("userId", loginId);
        params.put("userPwd", loginPwd);
        
		Map<String, Object> loginInfoCheck = loginService.checkLoginInfo(params);
		String status = loginInfoCheck.get("status").toString();
		String msg = loginInfoCheck.get("msg").toString();
		
		HttpSession session = request.getSession();
		
		if (status.equals("true")) {
			Map<String, Object> loginInfo = loginService.getUserInfo(loginId);
			LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
			
			Map<String, Object> newParams = new HashMap<>();
			newParams.put("userId", loginId);
			newParams.put("ip", request.getRemoteAddr());
			newParams.put("browser", getBrowser(request));
			
			inMainService.setBizUserLog(newParams);
			
			loginVO.setUserId(loginId);
			loginVO.setUserName(loginInfo.get("user_name").toString());
			loginVO.setDeptId(loginInfo.get("dept_id").toString());
			loginVO.setDeptName(loginInfo.get("dept_name").toString());
			loginVO.setSysYn(loginInfo.get("sys_yn").toString());
			loginVO.setSsoUserYn("n");
			session.setAttribute("LoginVO", loginVO);
			returnPage = "redirect:/internal/index.do";
			//mv.addObject(attributeName, attributeValue);
		} else {
			mv.addObject("status", status);
			mv.addObject("msg", msg);
			returnPage = "/internal/main/login";
		}
		mv.setViewName(returnPage);
        return mv;
	}
	
	@RequestMapping(value = "/SSOLogin.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ssoLogin(HttpServletRequest request, ModelAndView mv) throws Exception {
		HttpSession session = request.getSession();
		String returnPage = "";
		try {
			//String loginId = request.getParameter("userId");
			String loginId = session.getAttribute("userId").toString();
			
			Map<String, Object> params = new HashMap<>();
	        params.put("userId", loginId);
	        
			Map<String, Object> loginInfoCheck = loginService.checkSSOLoginInfo(params);
			String status = loginInfoCheck.get("status").toString();
			String msg = loginInfoCheck.get("msg").toString();
			
			//HttpSession session = request.getSession();
			
			if (status.equals("true")) {
				Map<String, Object> loginInfo = loginService.getUserInfo(loginId);
				LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
				
				Map<String, Object> newParams = new HashMap<>();
				newParams.put("userId", loginId);
				newParams.put("ip", request.getRemoteAddr());
				newParams.put("browser", getBrowser(request));
				
				inMainService.setBizUserLog(newParams);
				
				loginVO.setUserId(loginId);
				loginVO.setUserName(loginInfo.get("user_name").toString());
				loginVO.setDeptId(loginInfo.get("dept_id").toString());
				loginVO.setDeptName(loginInfo.get("dept_name").toString());
				loginVO.setSysYn(loginInfo.get("sys_yn").toString());
				loginVO.setSsoUserYn("y");
				session.setAttribute("LoginVO", loginVO);
				returnPage = "redirect:/internal/index.do";
				//mv.addObject(attributeName, attributeValue);
			} else {
				mv.addObject("status", status);
				mv.addObject("msg", msg);
				returnPage = "/internal/main/login";
			}
		} catch (Exception e) {
			mv.addObject("status", "false");
			mv.addObject("msg", "sso 인증실패");
			returnPage = "/internal/main/login";
		}
		mv.setViewName(returnPage);
		
        return mv;
	}
	
    @ResponseBody
	@RequestMapping(value = "/setMenuId.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String setMenuId(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		String msg = "";
			try {
				HttpSession session = request.getSession();
				 String menuId = params.get("menuId").toString();
					LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
					loginVO.setCurrentMenuId(menuId);
					session.setAttribute("LoginVO", loginVO);
					msg = "success";
			} catch(Exception e) {
				System.out.println("G1>>> catch >> " + e);
					msg = "fail";
			} finally {
				
			}
		  
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMenuId.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String getMenuId(HttpServletRequest request) throws Exception {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		  
		return loginVO.getCurrentMenuId();
	}
	
	@RequestMapping(value = "/logout.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String logout(HttpServletRequest request, ModelAndView mv) throws Exception {
		HttpSession session = request.getSession();
        session.removeAttribute("userId");
        session.removeAttribute("userName");
        session.removeAttribute("deptId");
        session.removeAttribute("deptName");
        session.removeAttribute("LoginVO");
        session.invalidate();
        return "/internal/main/login";
	}
}
