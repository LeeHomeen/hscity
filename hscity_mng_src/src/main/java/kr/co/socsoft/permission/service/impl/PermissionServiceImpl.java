package kr.co.socsoft.permission.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import kr.co.socsoft.common.vo.MenuPermitVO;
import kr.co.socsoft.permission.service.PermissionDao;
import kr.co.socsoft.permission.service.PermissionService;
import kr.co.socsoft.permission.vo.LogSyncUserVO;
import kr.co.socsoft.permission.vo.UserPermitVO;


@Service("permissionService")
public class PermissionServiceImpl implements PermissionService{
	
	@Resource(name = "permissionDao")
	private PermissionDao permissionDao;
	
	@Override
	public List<UserPermitVO> getUserPermissionList(Map<String, Object> params) throws Exception{
		return permissionDao.getUserPermissionList(params);
	}
	
	
	@Override
	public List<UserPermitVO> getUserPermissionExcel(Map<String, Object> params) throws Exception{
		return permissionDao.getUserPermissionExcel(params);
	}

	@Override
	public List<UserPermitVO> getDeptNamesOne() throws Exception {
		return permissionDao.getDeptNamesOne();
	}

	@Override
	public List<UserPermitVO> getDeptNamesTwo() throws Exception {
		return permissionDao.getDeptNamesTwo();

	}

	@Override
	public List<MenuPermitVO> getHsBizMenuMst(Map<String, Object> data) throws Exception {
		return permissionDao.getHsBizMenuMst(data);
	}

	@Override
	public List<MenuPermitVO> getHsBizUserMap(Map<String, Object> data) throws Exception {
		return permissionDao.getHsBizUserMap(data);
	}

	@Override
	public int setMenuUserMap(Map<String, Object> newParams) throws Exception {
		return permissionDao.setMenuUserMap(newParams);
	}

	@Override
	public int deleteMenuUserMap(Map<String, Object> newParams) throws Exception {
		return permissionDao.deleteMenuUserMap(newParams);
	}

	@Override
	public int setAdminPermit(Map<String, Object> newParams) throws Exception {
		return permissionDao.setAdminPermit(newParams);

	}

	@Override
	public List<MenuPermitVO> getAllUserMenuPermit(Map<String, Object> newParams) throws Exception {
		return permissionDao.getAllUserMenuPermit(newParams);

	}

	@Override
	public List<MenuPermitVO> getRootCategory() throws Exception {
		return permissionDao.getRootCategory();

	}

	@Override
	public List<MenuPermitVO> getUpperCategory() throws Exception {
		return permissionDao.getUpperCategory();

	}

	@Override
	public List<MenuPermitVO> getCategory() throws Exception {
		return permissionDao.getCategory();

	}

	@Override
	public List<LogSyncUserVO> getLogSyncList(Map<String, Object> newParams) throws Exception {
		return permissionDao.getLogSyncList(newParams);
	}
	
	
	@Override
	public List<LogSyncUserVO> getLogSyncExcel(Map<String, Object> newParams) throws Exception {
		return permissionDao.getLogSyncExcel(newParams);
	}

	@Override
	public List<LogSyncUserVO> getLogSyncType() throws Exception {
		return permissionDao.getLogSyncType();

	}

	@Override
	public List<MenuPermitVO> getDeptMenuPermit(Map<String, Object> data) throws Exception {
		return permissionDao.getDeptMenuPermit(data);

	}

	@Override
	public int setNewUserPassword(Map<String, Object> newParams) throws Exception {
		return permissionDao.setNewUserPassword(newParams);
	}

	@Override
	public int triggerUserSync() throws Exception {
		return permissionDao.triggerUserSync();
	}

	@Override
	public List<UserPermitVO> getDeptNamesThatFoundUpper(Map<String, Object> data) throws Exception {
		return permissionDao.getDeptNamesThatFoundUpper(data);
	}

	@Override
	public int setMenuDeptMap(Map<String, Object> newParams) throws Exception {
		return permissionDao.setMenuDeptMap(newParams);
	}

	@Override
	public int deleteMenuDeptMap(Map<String, Object> newParams) throws Exception {
		return permissionDao.deleteMenuDeptMap(newParams);
	}

	@Override
	public UserPermitVO getUserInfoToId(String userId) throws Exception {
		return permissionDao.getUserInfoToId(userId);
	}


	@Override
	public List<MenuPermitVO> getHsBizMenuMst2(Map<String, Object> newParams) throws Exception {
		return permissionDao.getHsBizMenuMst2(newParams);
	}
}
