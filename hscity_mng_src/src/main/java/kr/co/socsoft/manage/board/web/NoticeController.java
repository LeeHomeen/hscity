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
public class NoticeController {

    @Resource(name = "beanValidator")
    private DefaultBeanValidator validator;

    @Resource(name = "manageBoardService")
    private ManageBoardService manageBoardService;

    @Resource(name = "propertiesService")
    private EgovPropertyService egovPropertyService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @RequestMapping(value = "/notice/list.do")
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

        items = manageBoardService.selectNoticeBoardList(boardVO);
        totCnt = manageBoardService.selectNoticeBoardListToCnt(boardVO);

        codeVO.setGroupCd("bbs_type_scd");

        // 게시글구분(대시민, 업무용)
        model.addAttribute("bbsType", codeService.getCommCode(codeVO));

        paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("items", items);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("type", "board");

        return "/admin/board/notice/noticeList";
    }

    @RequestMapping(value = "/notice/view.do")
    public String boardView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) {
        model.addAttribute("result", manageBoardService.selectNoticeBoard(boardVO));
        model.addAttribute("type", "board");

        return "/admin/board/notice/noticeView";
    }

    @RequestMapping(value = "/notice/addView.do")
    public String boardAddView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
        CodeVO codeVO = new CodeVO();
        codeVO.setSortCol("detail_cd");
        codeVO.setSort("asc");
        codeVO.setGroupCd("bbs_type_scd");

        // 게시글구분(대시민, 업무용)
        model.addAttribute("bbsType", codeService.getCommCode(codeVO));
        model.addAttribute("type", "board");

        return "/admin/board/notice/noticeRegist";
    }

    @RequestMapping(value = "/notice/add.do")
    public String boardAdd(Model model, BoardVO boardVO, BindingResult bindingResult) throws Exception {
        if (validateError(model, boardVO, bindingResult)) {
            return "/admin/board/notice/noticeRegist";
        }

        LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        boardVO.setCreateId(loginVO.getUserId());

        int insertCount = manageBoardService.insertNoticeBoard(boardVO);
        model.addAttribute("seq", boardVO.getSeq());
        return "redirect:/admin/board/notice/view.do";
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

    @RequestMapping(value = "/notice/updateView.do")
    public String boardUpdateView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
        CodeVO codeVO = new CodeVO();
        codeVO.setSortCol("detail_cd");
        codeVO.setSort("asc");
        codeVO.setGroupCd("bbs_type_scd");

        // 게시글구분(대시민, 업무용)
        model.addAttribute("bbsType", codeService.getCommCode(codeVO));
        model.addAttribute("result", manageBoardService.selectNoticeBoard(boardVO));
        model.addAttribute("type", "board");

        return "/admin/board/notice/noticeRegist";
    }

    @RequestMapping(value = "/notice/update.do")
    public String boardUpdate(Model model, BoardVO boardVO, BindingResult bindingResult) throws Exception {
        if (validateError(model, boardVO, bindingResult)) {
            return "/admin/board/notice/noticeRegist";
        }

        LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        boardVO.setUpdateId(loginVO.getUserId());

        int updateCount = manageBoardService.updateNoticeBoard(boardVO);
        boardVO = manageBoardService.selectNoticeBoard(boardVO);

        model.addAttribute("count", updateCount);
        model.addAttribute("result", boardVO);
        model.addAttribute("type", "board");

        return "/admin/board/notice/noticeRegist";
    }

    @RequestMapping(value = "/notice/delete.do")
    public String boardDelete(BoardVO boardVO) {
        int deleteCount = manageBoardService.deleteNoticeBoard(boardVO);
        return "redirect:/admin/board/notice/list.do";
    }
}
