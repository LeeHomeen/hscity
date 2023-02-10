package kr.co.socsoft.menu.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.cmm.EgovUserDetailsHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kr.co.socsoft.menu.vo.CategoryVo;
import kr.co.socsoft.util.JsonHelper;
import kr.co.socsoft.common.vo.FileVO;
import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.manage.file.vo.BbsAttFileVO;
import kr.co.socsoft.menu.service.CategoryService;

@Controller
@RequestMapping(value = "/menu")
public class CategoryController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	@Resource(name = "categoryService")
	private CategoryService categoryService;
	
	@RequestMapping(value = "/pubCategoryList.do")
	public String pubCategoryList(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("type", "category");
        model.addAttribute("menu", "pubctg");
        return "/admin/menu/pubCategoryList";
	}
	
	@RequestMapping(value = "/bizCategoryList.do")
	public String bizCategoryList(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("type", "category");
        model.addAttribute("menu", "bizctg");
        return "/admin/menu/bizCategoryList";
	}
	
	@RequestMapping(value = "/{menu}/menuList.do")
	public String menuList(@PathVariable(value="menu") String menu, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("type", "category");
        model.addAttribute("menu", menu);
        return "/admin/menu/menuList";
	}

	@RequestMapping(value = "/{menu}/editMenu.do")
	public String editMenu(@PathVariable(value="menu") String menu
			             , @ModelAttribute CategoryVo categoryVo
			             , Model model
			             , HttpServletRequest request) throws Exception {
		categoryVo.setCategoryType(menu);
		model.addAttribute("categoryVo", categoryService.getMenuDetailInfo(categoryVo));
		model.addAttribute("attachFile", categoryService.getMenuDetailAttachFile(categoryVo));
		if (categoryService.getMenuImg(categoryVo) != null) {
			model.addAttribute("menuImg", categoryService.getMenuImg(categoryVo).getMenuImgSrc());
		}
		model.addAttribute("type", "category");
		model.addAttribute("menu", menu);
		return "/admin/menu/editMenu";
	}
	
	@RequestMapping(value = "/{menu}/editAuthDept.do")
	public String editAuthDept(@PathVariable(value="menu") String menu
			                 , @ModelAttribute CategoryVo categoryVo
			                 , Model model
			                 , HttpServletRequest request) throws Exception {
		categoryVo.setCategoryType(menu);
		model.addAttribute("categoryVo", categoryService.getMenuDetailInfo(categoryVo));
		model.addAttribute("type", "category");
		model.addAttribute("menu", menu);
		return "/admin/menu/editAuthDept";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDeptList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<CategoryVo> getDeptList(@RequestBody Map<String, Object> data) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		if (data.get("deptId") != null && !data.get("deptId").equals("")) {
            newParams.put("deptId", data.get("deptId").toString());
        }
		List<CategoryVo> result = categoryService.getDeptList(newParams);
        return result;
    }
	
	@ResponseBody
	@RequestMapping(value = "/getMappingDeptList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<CategoryVo> getMappingDeptList(@RequestBody Map<String, Object> data) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		if (data.get("menuCd") != null && !data.get("menuCd").equals("")) {
            newParams.put("menuCd", data.get("menuCd").toString());
        }
		List<CategoryVo> result = categoryService.getMappingDeptList(newParams);
        return result;
    }
	
	@ResponseBody
	@RequestMapping(value = "/addDeptMap.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int addDeptMap(@RequestBody Map<String, Object> data) throws Exception {
	    List<String> deptList = (List<String>)data.get("deptId");
	    LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		int count = 0;
		for(String param : deptList) {
			Map<String, Object> newParams = new HashMap<>();
            newParams.put("menuCd", data.get("menuCd").toString());
            newParams.put("deptId", param);
            newParams.put("createId", loginVO.getUserId());
            newParams.put("updateId", loginVO.getUserId());
			count += categoryService.addDeptMap(newParams);
		}
		return count;
		
    }
	
	@ResponseBody
	@RequestMapping(value = "/removeDeptMap.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int removeDeptMap(@RequestBody Map<String, Object> data) throws Exception {
	    List<String> deptList = (List<String>)data.get("deptId");

		int count = 0;
		for(String param : deptList) {
			Map<String, Object> newParams = new HashMap<>();
            newParams.put("menuCd", data.get("menuCd").toString());
            newParams.put("deptId", param);

			 count += categoryService.removeDeptMap(newParams);
		}
		return count;
		
    }
	
	@RequestMapping(value = "/{menu}/editAuthDeptUser.do")
	public String editAuthDeptUser(@PathVariable(value="menu") String menu
			                     , @ModelAttribute CategoryVo categoryVo
			                     , Model model
			                     , HttpServletRequest request) throws Exception {
		categoryVo.setCategoryType(menu);
		model.addAttribute("categoryVo", categoryService.getMenuDetailInfo(categoryVo));
		model.addAttribute("type", "category");
		model.addAttribute("menu", menu);
		return "/admin/menu/editAuthDeptUser";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<CategoryVo> getUserList(@RequestBody Map<String, Object> data) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		if (data.get("menuCd") != null && !data.get("menuCd").equals("")) {
            newParams.put("menuCd", data.get("menuCd").toString());
        }
		if (data.get("searchType") != null && !data.get("searchType").equals("")) {
            newParams.put("searchType", data.get("searchType").toString());
        }
		if (data.get("searchText") != null && !data.get("searchText").equals("")) {
            newParams.put("searchText", data.get("searchText").toString());
        }
		List<CategoryVo> result = categoryService.getUserList(newParams);
        return result;
    }
	
	@ResponseBody
	@RequestMapping(value = "/getMappingUserList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<CategoryVo> getMappingUserList(@RequestBody Map<String, Object> data) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		if (data.get("menuCd") != null && !data.get("menuCd").equals("")) {
            newParams.put("menuCd", data.get("menuCd").toString());
        }
		List<CategoryVo> result = categoryService.getMappingUserList(newParams);
        return result;
    }
	
	@ResponseBody
	@RequestMapping(value = "/addUserMap.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int addUserMap(@RequestBody Map<String, Object> data) throws Exception {
		List<Map<String, Object>> userList = (List<Map<String, Object>>)data.get("userInfo");
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		int count = 0;
		for(Map<String, Object> param : userList) {
			Map<String, Object> newParams = new HashMap<>();
            newParams.put("menuCd", data.get("menuCd").toString());
            newParams.put("deptId", param.get("deptId"));
            newParams.put("userId", param.get("userId"));
            newParams.put("createId", loginVO.getUserId());
            newParams.put("updateId", loginVO.getUserId());
            count += categoryService.addUserMap(newParams);
		}
		return count;
    }
	
	@ResponseBody
	@RequestMapping(value = "/removeUserMap.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int removeUserMap(@RequestBody Map<String, Object> data) throws Exception {
		List<Map<String, Object>> userList = (List<Map<String, Object>>)data.get("userInfo");
		int count = 0;
		for(Map<String, Object> param : userList) {
			Map<String, Object> newParams = new HashMap<>();
            newParams.put("menuCd", data.get("menuCd").toString());
            newParams.put("deptId", param.get("deptId"));
            newParams.put("userId", param.get("userId"));
            count += categoryService.removeUserMap(newParams);
		}
		return count;
    }
	
	@RequestMapping(value = "/deleteMenuInfo.do")
	@ResponseBody
	public JSONObject deleteMenuInfo(@RequestBody JSONObject data
					               , HttpServletRequest request
					               , Model model) throws Exception {
		String rtnCode = "";
		JSONObject jObj = (JSONObject) JSONSerializer.toJSON(data.get("data"));
		CategoryVo categoryVo = JsonHelper.parseJsonObject(jObj.toString(), CategoryVo.class);
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		categoryVo.setUpdateId(loginVO.getUserId());
		try {
			categoryService.deleteMenuInfo(categoryVo);
			rtnCode = "ok";
		} catch (Exception e) {
			rtnCode = "ng";
		} finally {
			jObj.accumulate("isOk", rtnCode);
		}
		
		return jObj;
	}
	
	@RequestMapping(value = "/deleteMenuFileData.do")
	@ResponseBody
	public JSONObject deleteMenuFileData(@RequestBody JSONObject data
					                 , HttpServletRequest request
					                 , Model model) throws Exception {
		String rtnCode = "";
		JSONObject jObj = (JSONObject) JSONSerializer.toJSON(data.get("data"));
		CategoryVo categoryVo = JsonHelper.parseJsonObject(jObj.toString(), CategoryVo.class);
		
		try {
			categoryService.deleteMenuFileData(categoryVo);
			rtnCode = "ok";
		} catch (Exception e) {
			rtnCode = "ng";
		} finally {
			jObj.accumulate("isOk", rtnCode);
		}
		
		return jObj;
	}
	
	@RequestMapping(value="/saveMenuInfo.do", method=RequestMethod.POST, headers = "content-type=multipart/*", consumes = "application/json")
	@ResponseBody
	public JSONObject saveMenuInfo(HttpServletRequest request) throws Exception { 
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		JSONObject jObj = (JSONObject) JSONSerializer.toJSON(multipartRequest.getParameter("saveData"));
		String rtnCode = "";
		CategoryVo categoryVo = JsonHelper.parseJsonObject(jObj.toString(), CategoryVo.class);
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		categoryVo.setCreateId(loginVO.getUserId());
		categoryVo.setUpdateId(loginVO.getUserId());
		String menuCd = "";
		if (categoryVo.getMenuCd().equals("NEW")) {
			menuCd = categoryService.getMaxMenuCd(categoryVo);
			categoryVo.setMenuCd(menuCd);
		} else {
			menuCd = categoryVo.getMenuCd();
		}
		int imgCnt = Integer.parseInt(multipartRequest.getParameter("menuImgCnt"));
		if (imgCnt != 0) {
			MultipartFile file = multipartRequest.getFile("menuImg");
			categoryVo.setMenuImg(file.getBytes());
		}
		int fileCnt = Integer.parseInt(multipartRequest.getParameter("fileDataCnt"));
		List<CategoryVo> categoryFileList = new ArrayList();
		
		for (int i=0; i<fileCnt; i++) {
			MultipartFile file = multipartRequest.getFile("fileData" + i);
			CategoryVo cvo = new CategoryVo(); 
			cvo.setMenuCd(menuCd);
			cvo.setCategoryType(categoryVo.getCategoryType());
			cvo.setFileNm(file.getOriginalFilename());
			cvo.setFileData(file.getBytes());
			cvo.setFileSize(String.valueOf(file.getSize()));
			cvo.setCreateId(loginVO.getUserId());
			categoryFileList.add(cvo);
		}
		
		if (categoryVo.getLayout().equals("")) {
			categoryVo.setLayout(String.valueOf(categoryService.getLayout(categoryVo)));
		}
		
		try {
			categoryService.saveMenuInfo(categoryVo);
			
			if (categoryFileList.size()>0) {
				categoryService.saveMenuFileData(categoryFileList);
			}
			
			if (categoryVo.getMainUseYn().equals("y")) {
				categoryService.deleteMainMng(categoryVo);
				categoryService.saveMainMng(categoryVo);
			} else if (categoryVo.getMainUseYn().equals("n")) {
				categoryService.deleteMainMng(categoryVo);
			}
			
			rtnCode = "ok";
		} catch (Exception e) {
			rtnCode = "ng";
		} finally {
			jObj.put("isOk", rtnCode);
		}
		return jObj;
		
	}
	
	@RequestMapping(value = "/getCategoryGroupList.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<CategoryVo> getCategoryGroupList(CategoryVo categoryVo, HttpServletRequest request) throws Exception {
		logger.debug("param::" + categoryVo.getCategoryType());
		List<CategoryVo> result = categoryService.getCategoryGroupList(categoryVo);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMenuList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<CategoryVo> getMenuList(@RequestBody Map<String, Object> data, HttpServletRequest request) throws Exception {
		CategoryVo categoryVo = new CategoryVo();
		if (data.get("categoryCd") != null && !data.get("categoryCd").equals("")) {
			categoryVo.setCategoryCd(data.get("categoryCd").toString());
		}
		if (data.get("menuNm") != null && !data.get("menuNm").equals("")) {
			categoryVo.setMenuNm(data.get("menuNm").toString());
		}
		if (data.get("categoryType") != null && !data.get("categoryType").equals("")) {
			categoryVo.setCategoryType(data.get("categoryType").toString());
		}
		List<CategoryVo> result = categoryService.getMenuList(categoryVo);
        return result;
    }
	
	@RequestMapping(value = "/getCategoryData.do")
	public String getCategoryData(@RequestParam("categoryType") String categoryType
			                    , HttpServletRequest request
			                    , Model model
			                    , CategoryVo categoryVo) throws Exception {
		if ("Pub".equals(categoryType)) {// Pub
			model.addAttribute("result", categoryService.getPubCategoryData(categoryVo));
		} else {// Biz
			model.addAttribute("result", categoryService.getBizCategoryData(categoryVo));
		}
		
		return "jsonView";
	}
	
	@RequestMapping(value = "/saveCategoryData.do")
	@ResponseBody
	public JSONObject saveCategoryData(@RequestBody JSONObject data
			                         , HttpServletRequest request
			                         , Model model
			                         , CategoryVo categoryVo) throws Exception {
		String categoryType = (String) data.get("categoryType");
		JSONArray saveDataList = (JSONArray) data.get("saveDataList");
		List<CategoryVo> saveCategoryVoList = JsonHelper.parseJsonArray(saveDataList.toString(), CategoryVo.class);
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		for (int i=0; i<saveCategoryVoList.size(); i++) {
			saveCategoryVoList.get(i).setCreateId(loginVO.getUserId().toString());
			saveCategoryVoList.get(i).setUpdateId(loginVO.getUserId().toString());
		}
		
		String rtnCode = "";
		JSONObject jObj = new JSONObject();
		if ("Pub".equals(categoryType)) {// Pub
			try {
				categoryService.savePubCategoryData(saveCategoryVoList);
				rtnCode = "ok";
			} catch (Exception e) {
				rtnCode = "ng";
			} finally {
				jObj.accumulate("isOk", rtnCode);
			}
		} else {// Biz
			try {
				categoryService.saveBizCategoryData(saveCategoryVoList);
				rtnCode = "ok";
			} catch (Exception e) {
				rtnCode = "ng";
			} finally {
				jObj.accumulate("isOk", rtnCode);
			}
		}
		return jObj;
	}
	
	@RequestMapping(value = "/updateMenuMst.do")
	@ResponseBody
	public JSONObject updateMenuMst(@RequestBody JSONObject data
			                      , HttpServletRequest request
			                      , Model model
			                      , CategoryVo categoryVo) throws Exception {
		CategoryVo svo = JsonHelper.parseJsonObject(data.toString(), CategoryVo.class);
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		
		svo.setUpdateId(loginVO.getUserId());
		String rtnCode = "";
		JSONObject jObj = new JSONObject();
		try {
			categoryService.updateMenuMst(svo);
			rtnCode = "ok";
		} catch (Exception e) {
			rtnCode = "ng";
		} finally {
			jObj.accumulate("isOk", rtnCode);
		}
		return jObj;
	}
	
	@RequestMapping(value="/downloadFile.do")
    public String downloadFile(CategoryVo categoryVo, Model model) throws Exception {
		categoryVo = categoryService.getMenuDetailAttachFileOne(categoryVo);
        FileVO fileVO = new FileVO(categoryVo.getFileNm(), Long.parseLong(categoryVo.getFileSize()), categoryVo.getFileData());
        model.addAttribute("fileVO", fileVO);
        return "downloadView";
    }

}
