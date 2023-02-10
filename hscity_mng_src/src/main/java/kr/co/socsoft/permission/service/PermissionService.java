package kr.co.socsoft.permission.service;

import java.util.List;
import java.util.Map;

import kr.co.socsoft.common.vo.MenuPermitVO;
import kr.co.socsoft.permission.vo.LogSyncUserVO;
import kr.co.socsoft.permission.vo.UserPermitVO;

public interface PermissionService {

	List<UserPermitVO> getUserPermissionList(Map<String, Object> params) throws Exception;
	
	List<UserPermitVO> getUserPermissionExcel(Map<String, Object> params) throws Exception;

	List<UserPermitVO> getDeptNamesOne() throws Exception;
	
	List<UserPermitVO> getDeptNamesTwo() throws Exception;

	List<MenuPermitVO> getHsBizMenuMst(Map<String, Object> data) throws Exception;
	
	List<MenuPermitVO> getHsBizUserMap(Map<String, Object> data) throws Exception;

	int setMenuUserMap(Map<String, Object> newParams) throws Exception;

	int deleteMenuUserMap(Map<String, Object> newParams) throws Exception;

	int setAdminPermit(Map<String, Object> newParams) throws Exception;

	List<MenuPermitVO> getAllUserMenuPermit(Map<String, Object> newParams) throws Exception;

	List<MenuPermitVO> getRootCategory() throws Exception;

	List<MenuPermitVO> getUpperCategory() throws Exception;

	List<MenuPermitVO> getCategory() throws Exception;

	List<LogSyncUserVO> getLogSyncList(Map<String, Object> newParams) throws Exception;

	List<LogSyncUserVO> getLogSyncExcel(Map<String, Object> newParams) throws Exception;
	
	
	List<LogSyncUserVO> getLogSyncType() throws Exception;

	List<MenuPermitVO> getDeptMenuPermit(Map<String, Object> data) throws Exception;

	int setNewUserPassword(Map<String, Object> newParams) throws Exception;

	int triggerUserSync() throws Exception;

	List<UserPermitVO> getDeptNamesThatFoundUpper(Map<String, Object> data) throws Exception;

	int setMenuDeptMap(Map<String, Object> newParams) throws Exception;

	int deleteMenuDeptMap(Map<String, Object> newParams) throws Exception;

	UserPermitVO getUserInfoToId(String userId) throws Exception;

	List<MenuPermitVO> getHsBizMenuMst2(Map<String, Object> newParams) throws Exception;
}


