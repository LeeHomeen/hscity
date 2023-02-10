
package kr.co.socsoft.external.main.web;

import java.text.DecimalFormat;
import java.util.HashMap;
import kr.co.socsoft.menu.vo.CategoryVo;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@RequestMapping(value = "/external")
public class ExMainController {
	private static final Logger logger = LoggerFactory.getLogger(ExMainController.class);

//	@Resource(name = "exMainService")
//	private ExMainService exMainService; 
	
	@RequestMapping(value = "/index.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView userList(ModelAndView mv, HttpServletRequest request) throws Exception {

		mv.setViewName("/external/index");
		return mv;
	}
	
}