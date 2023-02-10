package kr.co.socsoft.login.service;

import java.util.Map;
import kr.co.socsoft.common.vo.LoginVO;

public interface LoginService {
	Map<String, Object> getUserInfo(String userId) throws Exception;
	
	Map<String, Object> checkLoginInfo(Map<String, Object> params) throws Exception;
	
	Map<String, Object> checkSSOLoginInfo(Map<String, Object> params) throws Exception;
}
