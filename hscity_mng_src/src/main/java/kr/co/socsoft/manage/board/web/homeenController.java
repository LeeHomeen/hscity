package kr.co.socsoft.manage.board.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.socsoft.common.code.service.CodeService;
import kr.co.socsoft.common.vo.CodeVO;
import kr.co.socsoft.manage.board.service.ManageBoardService;
import kr.co.socsoft.manage.board.vo.BoardVO;

@Controller
@RequestMapping("/admin/board")
public class homeenController {
    @Resource(name = "beanValidator")
    private DefaultBeanValidator validator;

    @Resource(name = "manageBoardService")
    private ManageBoardService manageBoardService;

    @Resource(name = "propertiesService")
    private EgovPropertyService egovPropertyService;

    @Resource(name = "codeService")
    private CodeService codeService;
    
   
	@RequestMapping(value = "/homeen/list.do")
	public String boardList(BoardVO boardVO, Model model) throws Exception{
//
//        List<String> items = null;
//
//        items = manageBoardService.selectHominNew();
//        
//        for(String board : items) {
//        	System.out.println(board.toString());
//        }

        return "/admin/board/homeen/homeenList";
    }
}
