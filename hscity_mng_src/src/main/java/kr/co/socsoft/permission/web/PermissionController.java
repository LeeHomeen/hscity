package kr.co.socsoft.permission.web;

import org.apache.commons.beanutils.converters.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.EgovUserDetailsHelper;
import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.common.vo.MenuPermitVO;
import kr.co.socsoft.permission.service.PermissionService;
import kr.co.socsoft.permission.vo.LogSyncUserVO;
import kr.co.socsoft.permission.vo.UserPermitVO;
import kr.co.socsoft.stats.vo.AccessVO;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/permission")
public class PermissionController {
	private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);

	@Resource(name = "permissionService")
	private PermissionService permissionService; 

	@RequestMapping(value = "/userList.do")
	public ModelAndView userList(ModelAndView mv, HttpServletRequest request) throws Exception {

		mv.addObject("type", "user");
		mv.addObject("menu", "list");

		mv.setViewName("/admin/permission/userList");

		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserPermissionList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<UserPermitVO> getUserPermissionList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		 Map<String, Object> newParams = new HashMap<>();
		 if (params.get("deptOne") != null && !params.get("deptOne").equals("")) {
	            newParams.put("deptOne", params.get("deptOne").toString());
	        }
		 if (params.get("deptTwo") != null && !params.get("deptTwo").equals("")) {
	            newParams.put("deptTwo", params.get("deptTwo").toString());
	        }
		 if (params.get("idOrName") != null && !params.get("idOrName").equals("")) {
	            newParams.put("idOrName", params.get("idOrName").toString());
	        }
		 if (params.get("keyword") != null && !params.get("keyword").equals("")) {
	            newParams.put("keyword", params.get("keyword").toString());
	        }
		List<UserPermitVO> result = permissionService.getUserPermissionList(newParams);
        return result;
    }
	
	
    /**
     * 사용자 리스트 엑셀내려받기
     * @param model
     * @param userPermitVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getUserPermissionExcel.do")
    public String getUserPermissionExcel(Model model,UserPermitVO userPermitVO) throws Exception {

    	
    	Map<String, Object> param = new HashMap<>();
    	param.put("deptOne",  userPermitVO.getDeptOne());
    	param.put("deptTwo",  userPermitVO.getDeptTwo());
    	param.put("idOrName", userPermitVO.getIdOrName());
    	param.put("keyword",  userPermitVO.getKeyword());

    	model.addAttribute("excel_name", "사용자 목록");
        model.addAttribute("excel_title", new String[]{"실_국","실과","아이디","이름","상태","관리자여부","갱신일"});
        model.addAttribute("excel_column", new String[]{"upper_dept_name", "dept_name", "user_id","user_name","user_stat_name","sys_yn","update_dt"});
        model.addAttribute("data_list", permissionService.getUserPermissionExcel(param));
        return "dataExcelView";
    }

	
	
	@ResponseBody
	@RequestMapping(value = "/getDeptNamesOne.do", method = RequestMethod.GET)
    public List<UserPermitVO> getDeptNamesOne() throws Exception {
		List<UserPermitVO> result = permissionService.getDeptNamesOne();
        return result;
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/getDeptNamesTwo.do", method = RequestMethod.GET)
    public List<UserPermitVO> getDeptNamesTwo() throws Exception {
		List<UserPermitVO> result = permissionService.getDeptNamesTwo();
        return result;
    }
	
	@ResponseBody
	@RequestMapping(value = "/getDeptNamesThatFoundUpper.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<UserPermitVO> getDeptNamesThatFoundUpper(@RequestBody Map<String, Object> data) throws Exception {
		List<UserPermitVO> result = permissionService.getDeptNamesThatFoundUpper(data);
        return result;
    }

	@RequestMapping(value = "/editUserPermission.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editUserPermission(@ModelAttribute UserPermitVO vo,ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.addObject("userId", vo.getUserId());
		mv.addObject("type", "user");
		mv.addObject("menu", "list");
		
		UserPermitVO userPermitVO = permissionService.getUserInfoToId(vo.getUserId());
		mv.addObject("deptId", userPermitVO.getDeptId());

		mv.addObject("userName", userPermitVO.getUserName());
		mv.addObject("deptName", userPermitVO.getDeptName());
		mv.addObject("upperDeptName", userPermitVO.getUpperDeptName());
		mv.addObject("sysYn", userPermitVO.getSysYn());
		mv.setViewName("/admin/permission/editUserPermission");
		request.setCharacterEncoding("UTF-8");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/getHsBizMenuMst.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<MenuPermitVO> getHsBizMenuMst(@RequestBody Map<String, Object> data) throws Exception {
		Map<String, Object> newParams = new HashMap<>();

		if (data.get("categoryL") != null && !data.get("categoryL").equals("")) {
            newParams.put("categoryL", data.get("categoryL").toString());
        }
		if (data.get("categoryM") != null && !data.get("categoryM").equals("")) {
            newParams.put("categoryM", data.get("categoryM").toString());
        }
		if (data.get("categoryS") != null && !data.get("categoryS").equals("")) {
            newParams.put("categoryS", data.get("categoryS").toString());
        }
		 newParams.put("type", data.get("type").toString());
		List<MenuPermitVO> result = permissionService.getHsBizMenuMst(newParams);
        return result;
    }
	
	@ResponseBody
	@RequestMapping(value = "/getHsBizMenuMst2.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<MenuPermitVO> getHsBizMenuMst2(@RequestBody Map<String, Object> data) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		
		newParams.put("userId", data.get("userId"));
		if (data.get("categoryL") != null && !data.get("categoryL").equals("")) {
            newParams.put("categoryL", data.get("categoryL").toString());
        }
		if (data.get("categoryM") != null && !data.get("categoryM").equals("")) {
            newParams.put("categoryM", data.get("categoryM").toString());
        }
		if (data.get("categoryS") != null && !data.get("categoryS").equals("")) {
            newParams.put("categoryS", data.get("categoryS").toString());
        }
		List<MenuPermitVO> result = permissionService.getHsBizMenuMst2(newParams);
        return result;
    }
	
	@ResponseBody
	@RequestMapping(value = "/getAllUserMenuPermit.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<MenuPermitVO> getAllUserMenuPermit(@RequestBody Map<String, Object> data) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
        newParams.put("userId", data.get("userId").toString());
		List<MenuPermitVO> result = permissionService.getAllUserMenuPermit(newParams);
        return result;
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/getHsBizUserMap.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<MenuPermitVO> getHsBizUserMap(@RequestBody Map<String, Object> data) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
        newParams.put("userId", data.get("userId").toString());
        newParams.put("deptId", data.get("deptId").toString());
		if (data.get("categoryL") != null && !data.get("categoryL").equals("")) {
            newParams.put("categoryL", data.get("categoryL").toString());
        }
		if (data.get("categoryM") != null && !data.get("categoryM").equals("")) {
            newParams.put("categoryM", data.get("categoryM").toString());
        }
		if (data.get("categoryS") != null && !data.get("categoryS").equals("")) {
            newParams.put("categoryS", data.get("categoryS").toString());
        }
		
		List<MenuPermitVO> result = permissionService.getHsBizUserMap(newParams);
        return result;
    }
	
	
	
	@ResponseBody
	@RequestMapping(value = "/deleteMenuUserMap.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int deleteMenuUserMap(@RequestBody Map<String, Object> data) throws Exception {
		List<String> bizList = (List<String>)data.get("bizMenuCd");

		int count = 0;
		for(String param : bizList) {
			Map<String, Object> newParams = new HashMap<>();
            newParams.put("userId", data.get("userId").toString());
            newParams.put("bizMenuCd", param);
            
			 count += permissionService.deleteMenuUserMap(newParams);
		}
		return count;
    }
	
	@ResponseBody
	@RequestMapping(value = "/deleteMenuDeptMap.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int deleteMenuDeptMap(@RequestBody Map<String, Object> data) throws Exception {
		List<String> bizList = (List<String>)data.get("bizMenuCd");

		int count = 0;
		for(String param : bizList) {
			Map<String, Object> newParams = new HashMap<>();
            newParams.put("deptId", data.get("deptId").toString());
            newParams.put("bizMenuCd", param);

			 count += permissionService.deleteMenuDeptMap(newParams);
		}
		return count;
    }
	
	
	
	@ResponseBody
	@RequestMapping(value = "/setMenuUserMap.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int setMenuUserMap(@RequestBody Map<String, Object> data) throws Exception {
	    List<String> bizList = (List<String>)data.get("bizMenuCd");
	    LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
	    
		int count = 0;
		for(String param : bizList) {
			Map<String, Object> newParams = new HashMap<>();
            newParams.put("userId", data.get("userId").toString());
            newParams.put("bizMenuCd", param);
            newParams.put("createId", loginVO.getUserId().toString()); 
            newParams.put("updateId", loginVO.getUserId().toString());
			count += permissionService.setMenuUserMap(newParams);
		}
		return count;
		
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/setMenuDeptMap.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int setMenuDeptMap(@RequestBody Map<String, Object> data) throws Exception {
	    List<String> bizList = (List<String>)data.get("bizMenuCd");
	    LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
	    
		int count = 0;
		for(String param : bizList) {
			Map<String, Object> newParams = new HashMap<>();
            newParams.put("deptId", data.get("deptId").toString());
            newParams.put("bizMenuCd", param);
            newParams.put("createId", loginVO.getUserId().toString()); 
            newParams.put("updateId", loginVO.getUserId().toString());
		    count += permissionService.setMenuDeptMap(newParams);
		}
		return count;
		
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/setAdminPermit.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int setAdminPermit(@RequestBody Map<String, Object> data) throws Exception {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		
		Map<String, Object> newParams = new HashMap<>();
        newParams.put("userId", data.get("userId").toString());
        newParams.put("sysYn", data.get("sysYn").toString());
        newParams.put("createId", loginVO.getUserId().toString()); 
        newParams.put("updateId", loginVO.getUserId().toString());
		return permissionService.setAdminPermit(newParams);
		
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/getRootCategory.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<MenuPermitVO> getRootCategory() throws Exception {
		return permissionService.getRootCategory();
    }
	
	@ResponseBody
	@RequestMapping(value = "/getUpperCategory.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<MenuPermitVO> getUpperCategory() throws Exception {
		return permissionService.getUpperCategory();
    }
	
	@ResponseBody
	@RequestMapping(value = "/getCategory.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<MenuPermitVO> getCategory() throws Exception {
		return permissionService.getCategory();
    }
	
	@RequestMapping(value = "/userSyncLogList.do")
	public ModelAndView userSyncLogList(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.addObject("type", "user");
		mv.addObject("menu", "sync");

		mv.setViewName("/admin/permission/userSyncLogList");

		return mv;
	}
	

	@ResponseBody
	@RequestMapping(value = "/getLogSyncList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<LogSyncUserVO> getLogSyncList(@RequestBody Map<String, Object> data) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		if (data.get("stdYm1") != null && !data.get("stdYm1").equals("")) {
            newParams.put("stdYm1", data.get("stdYm1").toString());
        }
		if (data.get("stdYm2") != null && !data.get("stdYm2").equals("")) {
            newParams.put("stdYm2", data.get("stdYm2").toString());
        }
		if (data.get("logType") != null && !data.get("logType").equals("")) {
            newParams.put("logType", data.get("logType").toString());
        }if (data.get("key") != null && !data.get("key").equals("")) {
            newParams.put("key", data.get("key").toString());
        }
		return permissionService.getLogSyncList(data);
    }
	
	
    @RequestMapping(value="/getLogSyncExcel.do")
    public String getLogSyncExcel(Model model,LogSyncUserVO logSyncUserVO) throws Exception {

    	
    	Map<String, Object> param = new HashMap<>();
    	param.put("stdYm1", logSyncUserVO.getStdYm1());
    	param.put("stdYm2", logSyncUserVO.getStdYm2());
    	param.put("logType",logSyncUserVO.getLogType());
    	param.put("key",    logSyncUserVO.getSrch());

    	model.addAttribute("excel_name", "사용자 동기화로그");
        model.addAttribute("excel_title", new String[]{"순번","로그일자","로그타입","로그메세지"});
        model.addAttribute("excel_column", new String[]{"log_seq", "log_date", "log_type_scd_nm","log_msg"});
        model.addAttribute("data_list", permissionService.getLogSyncExcel(param));
        return "dataExcelView";
    }


	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/getLogSyncType.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<LogSyncUserVO> getLogSyncType() throws Exception {
		return permissionService.getLogSyncType();
    }
	
	@RequestMapping(value = "/permissionManagement.do")
	public ModelAndView permissionManagement(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.addObject("type", "user");
		mv.addObject("menu", "permissionManagement");
		mv.setViewName("/admin/permission/permissionManagement");

		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDeptMenuPermit.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<MenuPermitVO> getDeptMenuPermit(@RequestBody Map<String, Object> data) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
	     newParams.put("deptId", data.get("deptId").toString());
			if (data.get("categoryL") != null && !data.get("categoryL").equals("")) {
	            newParams.put("categoryL", data.get("categoryL").toString());
	        }
			if (data.get("categoryM") != null && !data.get("categoryM").equals("")) {
	            newParams.put("categoryM", data.get("categoryM").toString());
	        }
			if (data.get("categoryS") != null && !data.get("categoryS").equals("")) {
	            newParams.put("categoryS", data.get("categoryS").toString());
	        }
			
		return permissionService.getDeptMenuPermit(newParams);
    }
	
	@ResponseBody
	@RequestMapping(value = "/setNewUserPassword.do", method = RequestMethod.POST)
    public int setNewUserPassword(@RequestBody Map<String, Object> data) throws Exception {
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        
		Map<String, Object> newParams = new HashMap<>();
		newParams.put("userId", data.get("userId").toString());
		newParams.put("password", data.get("password").toString());
		newParams.put("updateId", loginVO.getUserId().toString());
			
		return permissionService.setNewUserPassword(newParams);
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/triggerUserSync.do", method = RequestMethod.POST)
    public int triggerUserSync() throws Exception {
			
		return permissionService.triggerUserSync();
    }

}
