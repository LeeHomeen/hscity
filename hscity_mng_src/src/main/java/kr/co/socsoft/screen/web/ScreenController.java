package kr.co.socsoft.screen.web;

import org.apache.commons.beanutils.converters.StringConverter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.EgovUserDetailsHelper;
import kr.co.socsoft.common.vo.DataLinkVO;
import kr.co.socsoft.common.vo.DataVO;
import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.common.vo.MenuPermitVO;
import kr.co.socsoft.permission.service.PermissionService;
import kr.co.socsoft.permission.vo.LogSyncUserVO;
import kr.co.socsoft.permission.vo.UserPermitVO;
import kr.co.socsoft.screen.service.ScreenService;
import kr.co.socsoft.screen.vo.BannerVO;
import kr.co.socsoft.screen.vo.ScreenVO;
import kr.co.socsoft.util.JsonHelper;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/screen")
public class ScreenController {
	private static final Logger logger = LoggerFactory.getLogger(ScreenController.class);

	@Resource(name = "screenService")
	private ScreenService screenService; 
	
	@RequestMapping(value = "/externalMainSreenManagent.do")
	public ModelAndView permissionManagement(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.addObject("type", "screen");
		mv.addObject("menu", "pub");
		mv.setViewName("/admin/screen/mainSreenManagent");

		return mv;
	}
	
	@RequestMapping(value = "/internalMainSreenManagent.do")
	public ModelAndView internalMainSreenManagent(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.addObject("type", "screen");
		mv.addObject("menu", "biz");
		mv.setViewName("/admin/screen/mainSreenManagent");
		return mv;
	}
	
	@RequestMapping(value = "/externalMainBannerManagent.do")
	public ModelAndView externalMainBannerManagent(ModelAndView mv, HttpServletRequest request) throws Exception {
		mv.addObject("type", "screen");
		mv.addObject("menu", "pubBanner");
		mv.setViewName("/admin/screen/bannerSreenManagent");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getScreenList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<ScreenVO> getScreenList(@RequestBody Map<String, Object> data,HttpServletRequest request) throws Exception {
		 Map<String, Object> newParams = new HashMap<>();
		 if (data.get("key") != null && !data.get("key").equals("")) {
	            newParams.put("key", data.get("key").toString());
	        }
		List<ScreenVO> result = null;
		if(data.get("type").equals("biz")) {
			 result = screenService.getBizScreenList(newParams);
		}else if (data.get("type").equals("pub")) {
			 result = screenService.getPubScreenList(newParams);
		}
        return result;
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/getCheckLocNumbers.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int getCheckLocNumbers(@RequestBody Map<String, Object> data,HttpServletRequest request) throws Exception {
		 Map<String, Object> newParams = new HashMap<>();
	     newParams.put("type", data.get("type").toString());
		 newParams.put("key", data.get("key").toString());

        return screenService.getCheckLocNumbers(newParams);
    }
	
	@ResponseBody
	@RequestMapping(value = "/setLocNumbers.do", method = {RequestMethod.GET, RequestMethod.POST})
    public void setLocNumbers(@RequestBody Map<String, Object> data,HttpServletRequest request) throws Exception {
		 Map<String, Object> newParams = new HashMap<>();
	     newParams.put("type", data.get("type").toString());
		 newParams.put("key", data.get("key").toString());
		 newParams.put("seq", data.get("seq").toString());
		 
		 if (data.get("dupYn") != null && !data.get("dupYn").equals("")) {
			 screenService.removeBefNumers(newParams);
	        }
         screenService.setLocNumbers(newParams);
    }
	
	@ResponseBody
	@RequestMapping(value = "/removeMainMng.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int removeMainMng(@RequestBody Map<String, Object> data,HttpServletRequest request) throws Exception {
		 Map<String, Object> newParams = new HashMap<>();
	     newParams.put("type", data.get("type").toString());
		 newParams.put("seq", data.get("seq").toString());

        return screenService.removeMainMng(newParams);
    }
	
	@RequestMapping(value = "/updateMenuImgUseYn.do")
	@ResponseBody
	public JSONObject updateMenuImgUseYn(@RequestBody JSONObject data
			                           , HttpServletRequest request
			                           , Model model
			                           , ScreenVO screenVO) throws Exception {
		ScreenVO svo = JsonHelper.parseJsonObject(data.toString(), ScreenVO.class);
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		
		svo.setUpdateId(loginVO.getUserId());
		String rtnCode = "";
		JSONObject jObj = new JSONObject();
		try {
			screenService.updateMenuImgUseYn(svo);
			rtnCode = "ok";
		} catch (Exception e) {
			rtnCode = "ng";
		} finally {
			jObj.accumulate("isOk", rtnCode);
		}
		return jObj;
	}
	
	
	
	
	@RequestMapping(value = "/getMenuImgYn.do")
	@ResponseBody
	public int getMenuImgYn(@RequestBody JSONObject data, HttpServletRequest request, Model model, ScreenVO screenVO) throws Exception {
		ScreenVO svo = JsonHelper.parseJsonObject(data.toString(), ScreenVO.class);
		
		return screenService.getMenuImgYn(svo);
	}
	
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/getBannerList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public List<BannerVO> getBannerList(@RequestBody Map<String, Object> params,HttpServletRequest request) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		if (params.get("key") != null && !params.get("key").equals("")) {
	            newParams.put("key", params.get("key").toString());
	        }
		List<BannerVO> result = null;
		if(params.get("type").equals("pubBanner")) {
			 result = screenService.getBannerList(newParams);
		}
        return result;
    }
	
	@RequestMapping(value = "/getBannerDetail.do")
	@ResponseBody
	public BannerVO getBannerDetail(@RequestBody Map<String, Object> params,HttpServletRequest request) throws Exception {
		BannerVO result = screenService.getBannerDetail(params.get("bannerSn").toString());
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/BannerConfSave.do")
    public JSONObject bannerConfSave(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		boolean rtnCode = true;
		String rtnMsg = "";
		BannerVO bannerVO = new BannerVO();
		bannerVO.setBannerContents(params.get("bannerContents").toString());
		bannerVO.setBannerStartDate(params.get("bannerStartDate").toString());
		bannerVO.setBannerEndDate(params.get("bannerEndDate").toString());
		bannerVO.setBannerUseYn(params.get("bannerUseYn").toString());
		bannerVO.setBannerOrder(params.get("bannerOrder").toString());
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		bannerVO.setUpdateId(loginVO.getUserId());
		bannerVO.setBannerSn(params.get("bannerSn").toString());
		JSONObject jObj = new JSONObject();
		
		if (params.get("bannerOrder").toString().equals("0")) {
			try {
				screenService.updateBanner(bannerVO);
				rtnCode = true;
				rtnMsg = "정상처리";
			} catch (Exception e) {
				rtnCode = false;
				rtnMsg = "[오류]" + ExceptionUtils.getRootCause(e);
			} finally {
				jObj.put("resultCode", rtnCode);
				jObj.put("resultMsg", rtnMsg);
			}
		}else {
			Map<String, Object> newParams = new HashMap<>();
			newParams.put("key", params.get("bannerOrder").toString());
			newParams.put("bannerSn", params.get("bannerSn").toString());
			int result = screenService.getCheckBannerOrder(newParams);
			if (result>0) {
				jObj.put("resultMsg", "dup");
			} else {
				try {
					screenService.updateBanner(bannerVO);
					rtnCode = true;
					rtnMsg = "정상처리";
				} catch (Exception e) {
					rtnCode = false;
					rtnMsg = "[오류]" + ExceptionUtils.getRootCause(e);
				} finally {
					jObj.put("resultCode", rtnCode);
					jObj.put("resultMsg", rtnMsg);
				}
			}
		}
		return jObj;
    }
	
	@ResponseBody
	@RequestMapping(value = "/BannerConfSave2.do")
	public JSONObject bannerConfSave2(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		boolean rtnCode = true;
		String rtnMsg = "";
		BannerVO bannerVO = new BannerVO();
		bannerVO.setBannerContents(params.get("bannerContents").toString());
		bannerVO.setBannerStartDate(params.get("bannerStartDate").toString());
		bannerVO.setBannerEndDate(params.get("bannerEndDate").toString());
		bannerVO.setBannerUseYn(params.get("bannerUseYn").toString());
		bannerVO.setBannerOrder(params.get("bannerOrder").toString());
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		bannerVO.setUpdateId(loginVO.getUserId());
		bannerVO.setBannerSn(params.get("bannerSn").toString());
		JSONObject jObj = new JSONObject();
		
		try {
			Map<String, Object> newParams = new HashMap<>();
			newParams.put("key", params.get("bannerOrder").toString());
			newParams.put("seq", params.get("bannerSn").toString());
			screenService.removeBefOrder(newParams);
	        screenService.setBannerOrder(newParams);
			screenService.updateBanner(bannerVO);
			rtnCode = true;
			rtnMsg = "정상처리";
		} catch (Exception e) {
			rtnCode = false;
			rtnMsg = "[오류]" + ExceptionUtils.getRootCause(e);
		} finally {
			jObj.put("resultCode", rtnCode);
			jObj.put("resultMsg", rtnMsg);
		}
		return jObj;
	}
	
	@RequestMapping(value = "/removeBanner.do")
	@ResponseBody
	public JSONObject removeBanner(@RequestBody Map<String, Object> params,HttpServletRequest request) throws Exception {
		boolean rtnCode = true;
		String rtnMsg = "";
		BannerVO bannerVO = new BannerVO();
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		bannerVO.setUpdateId(loginVO.getUserId());
		bannerVO.setBannerSn(params.get("bannerSn").toString());
		JSONObject jObj = new JSONObject();
		try {
			screenService.removeBanner(bannerVO);
			rtnCode = true;
			rtnMsg = "정상처리";
		} catch (Exception e) {
			rtnCode = false;
			rtnMsg = "[오류]" + ExceptionUtils.getRootCause(e);
		} finally {
			jObj.put("resultCode", rtnCode);
			jObj.put("resultMsg", rtnMsg);
		}
		return jObj;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCheckBannerOrder.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int getCheckBannerOrder(@RequestBody Map<String, Object> data,HttpServletRequest request) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		newParams.put("key", data.get("key").toString());
        return screenService.getCheckBannerOrder(newParams);
    }
	
	@ResponseBody
	@RequestMapping(value = "/setBannerOrder.do", method = {RequestMethod.GET, RequestMethod.POST})
    public int setBannerOrder(@RequestBody Map<String, Object> data,HttpServletRequest request) throws Exception {
		Map<String, Object> newParams = new HashMap<>();
		newParams.put("key", data.get("key").toString());
		newParams.put("seq", data.get("seq").toString());
		screenService.removeBefOrder(newParams);
        return screenService.setBannerOrder(newParams);
        
    }
	
	@RequestMapping(value = "/updateBannerUseYn.do")
	@ResponseBody
	public JSONObject updateBannerUseYn(@RequestBody JSONObject data, HttpServletRequest request, Model model, BannerVO bannerVO) throws Exception {
		BannerVO svo = JsonHelper.parseJsonObject(data.toString(), BannerVO.class);
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		svo.setUpdateId(loginVO.getUserId());
		String rtnCode = "";
		JSONObject jObj = new JSONObject();
		try {
			screenService.updateBannerUseYn(svo);
			rtnCode = "ok";
		} catch (Exception e) {
			rtnCode = "ng";
		} finally {
			jObj.accumulate("isOk", rtnCode);
		}
		return jObj;
	}
	
	@RequestMapping(value = "/makeNewBanner.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JSONObject makeBanner(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		boolean rtnCode = true;
		String rtnMsg = "";
		BannerVO bannerVO = new BannerVO();
		bannerVO.setBannerContents(params.get("bannerContents").toString());
		bannerVO.setBannerStartDate(params.get("bannerStartDate").toString());
		bannerVO.setBannerEndDate(params.get("bannerEndDate").toString());
		bannerVO.setBannerUseYn(params.get("bannerUseYn").toString());
		bannerVO.setBannerOrder(params.get("bannerOrder").toString());
		LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
		bannerVO.setCreateId(loginVO.getUserId());
		JSONObject jObj = new JSONObject();
		
		Map<String, Object> newParams = new HashMap<>();
		newParams.put("key", params.get("bannerOrder").toString());
		screenService.removeBefOrder(newParams);
		
		try {
			screenService.makeBanner(bannerVO);
			rtnCode = true;
			rtnMsg = "정상처리";
		} catch (Exception e) {
			rtnCode = false;
			rtnMsg = "[오류]" + ExceptionUtils.getRootCause(e);
		} finally {
			jObj.put("resultCode", rtnCode);
			jObj.put("resultMsg", rtnMsg);
		}
		return jObj;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateLayout.do", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONObject updateLayout(@RequestBody Map<String, Object> data,HttpServletRequest request) throws Exception {
		boolean rtnCode = true;
		String rtnMsg = "";
		Map<String, Object> newParams = new HashMap<>();
		newParams.put("type", data.get("type").toString());
		newParams.put("key", data.get("key").toString());
		JSONObject jObj = new JSONObject();
		try {
			screenService.updateLayout(newParams);
			rtnCode = true;
			rtnMsg = "정상처리";
		} catch (Exception e) {
			rtnCode = false;
			rtnMsg = "[오류]" + ExceptionUtils.getRootCause(e);
		} finally {
			jObj.put("resultCode", rtnCode);
			jObj.put("resultMsg", rtnMsg);
		}
		return jObj;
    }
	
}
