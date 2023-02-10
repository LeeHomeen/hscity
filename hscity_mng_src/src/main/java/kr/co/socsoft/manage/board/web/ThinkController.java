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
public class ThinkController {

    @Resource(name = "beanValidator")
    private DefaultBeanValidator validator;

    @Resource(name = "manageBoardService")
    private ManageBoardService manageBoardService;

    @Resource(name = "propertiesService")
    private EgovPropertyService egovPropertyService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @RequestMapping(value = "/think/list.do")
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

        items = manageBoardService.selectThinkBoardList(boardVO);
        totCnt = manageBoardService.selectThinkBoardListToCnt(boardVO);

        codeVO.setGroupCd("bbs_type_scd");

        // 게시글구분(대시민, 업무용)
        model.addAttribute("bbsType", codeService.getCommCode(codeVO));

        paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("items", items);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("type", "board");

        return "/admin/board/think/thinkList";
    }

    @RequestMapping(value = "/think/view.do")
    public String boardView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) {
        model.addAttribute("result", manageBoardService.selectThinkBoard(boardVO));
        model.addAttribute("type", "board");

        return "/admin/board/think/thinkView";
    }

    @RequestMapping(value = "/think/addView.do")
    public String boardAddView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
        CodeVO codeVO = new CodeVO();
        codeVO.setSortCol("detail_cd");
        codeVO.setSort("asc");
        codeVO.setGroupCd("bbs_type_scd");

        // 게시글구분(대시민, 업무용)
        model.addAttribute("bbsType", codeService.getCommCode(codeVO));
        model.addAttribute("type", "board");

        return "/admin/board/think/thinkRegist";
    }

    @RequestMapping(value = "/think/add.do")
    public String boardAdd(Model model, BoardVO boardVO) throws Exception {
        LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        boardVO.setCreateId(loginVO.getUserId());

        int insertCount = manageBoardService.insertThinkAnswerBoard(boardVO);
        model.addAttribute("seq", boardVO.getUpperThinkSeq());
        return "redirect:/admin/board/think/view.do";
    }

    @RequestMapping(value = "/think/updateView.do")
    public String boardUpdateView(Model model, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
        CodeVO codeVO = new CodeVO();
        codeVO.setSortCol("detail_cd");
        codeVO.setSort("asc");
        codeVO.setGroupCd("bbs_type_scd");

        // 게시글구분(대시민, 업무용)
        model.addAttribute("bbsType", codeService.getCommCode(codeVO));
        model.addAttribute("result", manageBoardService.selectThinkBoard(boardVO));
        model.addAttribute("type", "board");

        return "/admin/board/think/thinkRegist";
    }

    @RequestMapping(value = "/think/update.do")
    public String update(Model model, BoardVO boardVO, BindingResult bindingResult) throws Exception {
        if (validateError(model, boardVO, bindingResult)) {
            return "/admin/board/think/thinkRegist";
        }

        LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        boardVO.setUpdateId(loginVO.getUserId());

         int updateCount = manageBoardService.updateThinkBoard(boardVO);
        boardVO = manageBoardService.selectThinkBoard(boardVO);

        model.addAttribute("count", updateCount);
        model.addAttribute("result", boardVO);
        model.addAttribute("type", "board");

        return "/admin/board/think/thinkRegist";
    }

    @RequestMapping(value = "/think/answerUpdate.do")
    public String answerUpdate(Model model, BoardVO boardVO) throws Exception {
        /* 사용하지 않는값 빈값 처리 */
        boardVO.setTitle("");
        boardVO.setBbsTypeScd("");

        int updateCount = manageBoardService.updateThinkAnswerBoard(boardVO);

        model.addAttribute("seq", boardVO.getUpperThinkSeq());
        return "redirect:/admin/board/think/view.do";
    }

    @RequestMapping(value = "/think/delete.do")
    public String boardDelete(BoardVO boardVO) {
        int deleteCount = manageBoardService.deleteThinkBoard(boardVO);
        return "redirect:/admin/board/think/list.do";
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
