package kr.co.socsoft.login.service;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.socsoft.common.vo.LoginVO;

@Mapper("loginDao")
public interface LoginDao {
	
	Map<String, Object> getUserInfo(String userId);
	
	Map<String, Object> checkLoginInfo(Map<String, Object> params);
	
	Map<String, Object> checkSSOLoginInfo(Map<String, Object> params);
	
}