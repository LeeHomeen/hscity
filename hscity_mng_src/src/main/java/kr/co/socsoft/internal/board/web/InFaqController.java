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
@RequestMapping(value = "/internal/faq")
public class InFaqController {
	private static final Logger logger = LoggerFactory.getLogger(InFaqController.class);

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

		items = manageBoardService.selectFaqBoardList(boardVO);
		totCnt = manageBoardService.selectFaqBoardListToCnt(boardVO);

		codeVO.setGroupCd("bbs_type_scd");

		// 게시글구분(대시민, 업무용)
		model.addAttribute("bbsType", codeService.getCommCode(codeVO));

		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("items", items);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("type", "board");
		model.addAttribute("sysYn", loginVO.getSysYn());

		return "/internal/faq/faq";
	}
	
	@RequestMapping(value = "/newFaqList.do")
	public String newFaqList(Model model, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
		 LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
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

		items = manageBoardService.selectFaqBoardList(boardVO);
		totCnt = manageBoardService.selectFaqBoardListToCnt(boardVO);

		codeVO.setGroupCd("bbs_type_scd");

		// 게시글구분(대시민, 업무용)
		model.addAttribute("bbsType", codeService.getCommCode(codeVO));

		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("items", items);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("type", "board");
		model.addAttribute("sysYn", loginVO.getSysYn());

		return "/internal/faq/newFaq";
	}
}