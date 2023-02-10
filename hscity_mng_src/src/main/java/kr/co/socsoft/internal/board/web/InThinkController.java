package kr.co.socsoft.internal.board.web;

import egovframework.com.cmm.EgovUserDetailsHelper;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.socsoft.common.code.service.CodeService;
import kr.co.socsoft.common.vo.CodeVO;
import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.manage.board.service.ManageBoardService;
import kr.co.socsoft.manage.board.vo.BoardVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/internal/think")
public class InThinkController {
	private static final Logger logger = LoggerFactory.getLogger(InThinkController.class);

	@Resource(name = "manageBoardService")
	private ManageBoardService manageBoardService;

	@Resource(name = "beanValidator")
	private DefaultBeanValidator validator;

	@Resource(name = "propertiesService")
	private EgovPropertyService egovPropertyService;

	@Resource(name = "codeService")
	private CodeService codeService;
	
	@RequestMapping(value = "/list.do")
	public String list(Model model, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 model.addAttribute("sysYn", loginVO.getSysYn());

		// 업무용
		boardVO.setSite("biz");

		boardVO.setPageUnit(egovPropertyService.getInt("pageUnit"));
		boardVO.setPageSize(egovPropertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<BoardVO> items = null;
		int totCnt = 0;

		CodeVO codeVO = new CodeVO();
		codeVO.setSortCol("detail_cd");
		codeVO.setSort("asc");

		items = manageBoardService.selectThinkBoardList(boardVO);
		totCnt = manageBoardService.selectThinkBoardListToCnt(boardVO);

		codeVO.setGroupCd("bbs_type_scd");

		// 게시글구분(대시민, 업무용)
		model.addAttribute("bbsType", codeService.getCommCode(codeVO));

		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("items", items);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("type", "board");

		return "/internal/think/thinkList";
	}

	@RequestMapping(value = "/view.do")
	public String boardView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 model.addAttribute("sysYn", loginVO.getSysYn());

		model.addAttribute("result", manageBoardService.selectThinkBoard(boardVO));
		return "/internal/think/thinkView";
	}

	@RequestMapping(value = "/addView.do")
	public String addView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 model.addAttribute("sysYn", loginVO.getSysYn());

        return "/internal/think/thinkWrite";
	}

    @RequestMapping(value = "/add.do")
    public String add(Model model, BoardVO boardVO) throws Exception {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 model.addAttribute("sysYn", loginVO.getSysYn());

        boardVO.setCreateId(loginVO.getUserId());

        // 업무용으로 설정
        boardVO.setBbsTypeScd("biz");
        manageBoardService.insertThinkBoard(boardVO);

        model.addAttribute("seq", boardVO.getSeq());
        return "redirect:/internal/think/view.do";
    }

    @RequestMapping(value = "/updateView.do")
    public String updateView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
    	LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 model.addAttribute("sysYn", loginVO.getSysYn());
        model.addAttribute("result", manageBoardService.selectThinkBoard(boardVO));
        return "/internal/think/thinkWrite";
    }

    @RequestMapping(value = "/update.do")
    public String update(Model model, BoardVO boardVO) throws Exception {
        LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        boardVO.setUpdateId(loginVO.getUserId());

        // 업무용으로 설정
        boardVO.setBbsTypeScd("biz");
        int updateCount = manageBoardService.updateThinkBoard(boardVO);

        model.addAttribute("seq", boardVO.getSeq());
        return "redirect:/internal/think/updateView.do";
    }

    @RequestMapping(value = "/newThinkList.do")
	public String newThinkList(Model model, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 model.addAttribute("sysYn", loginVO.getSysYn());

		// 업무용
		boardVO.setSite("biz");

		boardVO.setPageUnit(egovPropertyService.getInt("pageUnit"));
		boardVO.setPageSize(egovPropertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<BoardVO> items = null;
		int totCnt = 0;

		CodeVO codeVO = new CodeVO();
		codeVO.setSortCol("detail_cd");
		codeVO.setSort("asc");

		items = manageBoardService.selectThinkBoardList(boardVO);
		totCnt = manageBoardService.selectThinkBoardListToCnt(boardVO);

		codeVO.setGroupCd("bbs_type_scd");

		// 게시글구분(대시민, 업무용)
		model.addAttribute("bbsType", codeService.getCommCode(codeVO));

		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("items", items);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("type", "board");

		return "/internal/think/newThinkList";
	}
    
    @RequestMapping(value = "/newAddView.do")
	public String newAddView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 model.addAttribute("sysYn", loginVO.getSysYn());

        return "/internal/think/newThinkWrite";
	}
    
    @RequestMapping(value = "/newThinkAdd.do")
    public String newThinkAdd(Model model, BoardVO boardVO) throws Exception {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 model.addAttribute("sysYn", loginVO.getSysYn());

        boardVO.setCreateId(loginVO.getUserId());

        // 업무용으로 설정
        boardVO.setBbsTypeScd("biz");
        manageBoardService.insertThinkBoard(boardVO);

        model.addAttribute("seq", boardVO.getSeq());
        return "redirect:/internal/think/newThinkView.do";
    }
    
    @RequestMapping(value = "/newThinkUpdate.do")
    public String newThinkUpdate(Model model, BoardVO boardVO) throws Exception {
        LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        boardVO.setUpdateId(loginVO.getUserId());

        // 업무용으로 설정
        boardVO.setBbsTypeScd("biz");
        int updateCount = manageBoardService.updateThinkBoard(boardVO);

        model.addAttribute("seq", boardVO.getSeq());
        return "redirect:/internal/think/newThinkUpdateView.do";
    }
    
	@RequestMapping(value = "/newThinkView.do")
	public String newThinkView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 model.addAttribute("sysYn", loginVO.getSysYn());

		model.addAttribute("result", manageBoardService.selectThinkBoard(boardVO));
		return "/internal/think/newThinkView";
	}
	

    @RequestMapping(value = "/newThinkUpdateView.do")
    public String newThinkUpdateView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
    	LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		 model.addAttribute("sysYn", loginVO.getSysYn());
        model.addAttribute("result", manageBoardService.selectThinkBoard(boardVO));
        return "/internal/think/newThinkWrite";
    }
    
}