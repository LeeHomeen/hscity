package kr.co.socsoft.common.code.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.socsoft.common.code.service.CodeService;
import kr.co.socsoft.common.vo.CodeVO;


@Controller
@RequestMapping(value = "/code")
public class CodeController {
	private static final Logger logger = LoggerFactory.getLogger(CodeController.class);
	
	@Resource(name = "codeService")
	private CodeService codeService;
	
	@ResponseBody
	@RequestMapping(value = "/getCommCode.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<CodeVO> getCommCode(CodeVO codeVo, HttpServletRequest request) throws Exception {
		List<CodeVO> result = codeService.getCommCode(codeVo);
        return result;
    }

	
	/**
	 * 내부시스템 연계관련 코드
	 * @param codeVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getSystemLinkCode.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<CodeVO> getSystemLinkCode(CodeVO codeVo, HttpServletRequest request) throws Exception {
		List<CodeVO> result = codeService.getSystemLinkCode(codeVo);
        return result;
    }

}
