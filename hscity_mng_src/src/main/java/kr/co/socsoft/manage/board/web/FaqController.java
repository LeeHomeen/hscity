package kr.co.socsoft.manage.board.web;

import egovframework.com.cmm.EgovUserDetailsHelper;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.socsoft.common.code.service.CodeService;
import kr.co.socsoft.common.vo.CodeVO;
import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.manage.board.service.ManageBoardService;
import kr.co.socsoft.manage.board.vo.BoardVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin/board")
public class FaqController {

    @Resource(name = "beanValidator")
    private DefaultBeanValidator validator;

    @Resource(name = "manageBoardService")
    private ManageBoardService manageBoardService;

    @Resource(name = "propertiesService")
    private EgovPropertyService egovPropertyService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @RequestMapping(value = "/faq/list.do")
    public String boardList(Model model, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
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

        return "/admin/board/faq/faqList";
    }

    @RequestMapping(value = "/faq/view.do")
    public String boardView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) {
        model.addAttribute("result", manageBoardService.selectFaqBoard(boardVO));

        return "/admin/board/faq/faqView";
    }

    @RequestMapping(value = "/faq/addView.do")
    public String boardAddView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
        CodeVO codeVO = new CodeVO();
        codeVO.setSortCol("detail_cd");
        codeVO.setSort("asc");
        codeVO.setGroupCd("bbs_type_scd");

        // 게시글구분(대시민, 업무용)
        model.addAttribute("bbsType", codeService.getCommCode(codeVO));
        model.addAttribute("type", "board");

        return "/admin/board/faq/faqRegist";
    }

    @RequestMapping(value = "/faq/add.do")
    public String boardAdd(Model model,BoardVO boardVO, BindingResult bindingResult) throws Exception {
        if (validateError(model, boardVO, bindingResult)) {
            return "/admin/board/faq/faqRegist";
        }

        LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        boardVO.setCreateId(loginVO.getUserId());

        int insertCount = manageBoardService.insertFaqBoard(boardVO);
        model.addAttribute("seq", boardVO.getSeq());
        return "redirect:/admin/board/faq/view.do";
    }

    @RequestMapping(value = "/faq/updateView.do")
    public String boardUpdateView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
        CodeVO codeVO = new CodeVO();
        codeVO.setSortCol("detail_cd");
        codeVO.setSort("asc");
        codeVO.setGroupCd("bbs_type_scd");

        // 게시글구분(대시민, 업무용)
        model.addAttribute("bbsType", codeService.getCommCode(codeVO));
        model.addAttribute("result", manageBoardService.selectFaqBoard(boardVO));
        model.addAttribute("type", "board");

        return "/admin/board/faq/faqRegist";
    }

    @RequestMapping(value = "/faq/update.do")
    public String boardUpdate(Model model, BoardVO boardVO, BindingResult bindingResult) throws Exception {
        if (validateError(model, boardVO, bindingResult)) {
            return "/admin/board/faq/faqRegist";
        }

        LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        boardVO.setUpdateId(loginVO.getUserId());

        int updateCount = manageBoardService.updateFaqBoard(boardVO);
        boardVO = manageBoardService.selectFaqBoard(boardVO);

        model.addAttribute("count", updateCount);
        model.addAttribute("result", boardVO);
        model.addAttribute("type", "board");

        return "/admin/board/faq/faqRegist";
    }

    @RequestMapping(value = "/faq/delete.do")
    public String boardDelete(BoardVO boardVO) {
        int deleteCount = manageBoardService.deleteFaqBoard(boardVO);
        return "redirect:/admin/board/faq/list.do";
    }

    private boolean validateError(Model model, BoardVO boardVO, BindingResult bindingResult) throws Exception {
        validator.validate(boardVO, bindingResult);
        if(bindingResult.hasErrors()) {
            CodeVO codeVO = new CodeVO();
            codeVO.setSortCol("detail_cd");
            codeVO.setSort("asc");
            codeVO.setGroupCd("bbs_type_scd");

            // 게시글구분(대시민, 업무용)
            model.addAttribute("bbsType", codeService.getCommCode(codeVO));
            model.addAttribute("result", boardVO);
            model.addAttribute("type", "board");
            return true;
        }
        return false;
    }
}
