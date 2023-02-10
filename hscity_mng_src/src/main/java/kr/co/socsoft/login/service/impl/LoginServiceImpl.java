package kr.co.socsoft.login.service.impl;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import kr.co.socsoft.common.vo.LoginVO;
import kr.co.socsoft.login.service.LoginDao;
import kr.co.socsoft.login.service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
	
	@Resource(name = "loginDao")
	private LoginDao loginDao;
	
	public Map<String, Object> getUserInfo(String userId) {
		return loginDao.getUserInfo(userId);
	}
	
	public Map<String, Object> checkLoginInfo(Map<String, Object> params) {
		return loginDao.checkLoginInfo(params);
	}
	
	public Map<String, Object> checkSSOLoginInfo(Map<String, Object> params) {
		return loginDao.checkSSOLoginInfo(params);
	}
}
