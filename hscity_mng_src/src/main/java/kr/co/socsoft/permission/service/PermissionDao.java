package kr.co.socsoft.permission.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.socsoft.common.vo.MenuPermitVO;
import kr.co.socsoft.permission.vo.LogSyncUserVO;
import kr.co.socsoft.permission.vo.UserPermitVO;

@Mapper("permissionDao")
public interface PermissionDao {

	List<UserPermitVO> getUserPermissionList(Map<String, Object> params);

	List<UserPermitVO> getUserPermissionExcel(Map<String, Object> params);
	
	List<UserPermitVO> getDeptNamesOne();	
	
	List<UserPermitVO> getDeptNamesTwo();

	List<MenuPermitVO> getHsBizMenuMst(Map<String, Object> data);

	List<MenuPermitVO> getHsBizUserMap(Map<String, Object> data);

	int setMenuUserMap(Map<String, Object> params);

	int deleteMenuUserMap(Map<String, Object> newParams);

	int setAdminPermit(Map<String, Object> newParams);

	List<MenuPermitVO> getAllUserMenuPermit(Map<String, Object> newParams);

	List<MenuPermitVO> getRootCategory();

	List<MenuPermitVO> getUpperCategory();

	List<MenuPermitVO> getCategory();

	List<LogSyncUserVO> getLogSyncList(Map<String, Object> newParams);
	
	List<LogSyncUserVO> getLogSyncExcel(Map<String, Object> newParams);

	List<LogSyncUserVO> getLogSyncType();

	List<MenuPermitVO> getDeptMenuPermit(Map<String, Object> newParams);

	int setNewUserPassword(Map<String, Object> newParams);

	int triggerUserSync();

	List<UserPermitVO> getDeptNamesThatFoundUpper(Map<String, Object> data);

	int setMenuDeptMap(Map<String, Object> newParams);

	int deleteMenuDeptMap(Map<String, Object> newParams);

	UserPermitVO getUserInfoToId(String userId);

	List<MenuPermitVO> getHsBizMenuMst2(Map<String, Object> newParams);	
}