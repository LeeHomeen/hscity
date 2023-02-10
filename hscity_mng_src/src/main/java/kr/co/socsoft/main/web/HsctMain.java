package kr.co.socsoft.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 메인화면 관련
 * @author SocSoft
 *
 */
@Controller
public class HsctMain {
	
	/**
	 * 메인페이지 이동
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/main.do")
	public String moveMainPage(ModelMap model)throws Exception{
		return "/main/hsctMain";
	}
}