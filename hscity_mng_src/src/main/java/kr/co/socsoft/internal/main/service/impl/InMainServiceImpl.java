package kr.co.socsoft.internal.main.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import kr.co.socsoft.common.vo.MenuPermitVO;
import kr.co.socsoft.internal.main.service.InMainDao;
import kr.co.socsoft.internal.main.service.InMainService;
import kr.co.socsoft.internal.main.vo.MenuCategoryVO;
import kr.co.socsoft.manage.board.vo.BoardVO;
import kr.co.socsoft.menu.vo.CategoryVo;
import kr.co.socsoft.permission.service.PermissionDao;
import kr.co.socsoft.permission.service.PermissionService;
import kr.co.socsoft.permission.vo.LogSyncUserVO;
import kr.co.socsoft.permission.vo.UserPermitVO;


@Service("inMainService")
public class InMainServiceImpl implements InMainService{
	
	@Resource(name = "inMainDao")
	private InMainDao inMainDao;

	
	@Override
	public List<MenuCategoryVO> getSearchMenuCategory(Map<String, Object> newParams) throws Exception {
		return inMainDao.getSearchMenuCategory(newParams);
	}
	
	@Override
	public List<MenuCategoryVO> getMenuCategory(Map<String, Object> newParams) throws Exception {
		return inMainDao.getMenuCategory(newParams);
	}

	@Override
	public MenuCategoryVO getMenuInfo(Map<String, Object> newParams) throws Exception {
		return inMainDao.getMenuInfo(newParams);
	}

	@Override
	public List<CategoryVo> getMainImg() throws Exception {
		return inMainDao.getMainImg();
	}

	@Override
	public int getCheckPassYn(Map<String, Object> newParams) throws Exception {
		return inMainDao.getCheckPassYn(newParams);
	}

	@Override
	public int setNewPassword(Map<String, Object> newParams) throws Exception {
		return inMainDao.setNewPassword(newParams);
	}

	@Override
	public int setFavPage(Map<String, Object> newParams) throws Exception {
		return inMainDao.setFavPage(newParams);
	}

	@Override
	public int checkFavPage(Map<String, Object> newParams) throws Exception {
		return inMainDao.checkFavPage(newParams);
	}

	@Override
	public int removeFavPage(Map<String, Object> newParams) throws Exception {
		return inMainDao.removeFavPage(newParams);
	}

	@Override
	public List<MenuCategoryVO> getFavMenuList(Map<String, Object> newParams) throws Exception {
		return inMainDao.getFavMenuList(newParams);
	}

	@Override
	public BoardVO getMainNotice() throws Exception {
		return inMainDao.getMainNotice();
	}

	@Override
	public int getHsPopulations() throws Exception {
		return inMainDao.getHsPopulations();
	}

	@Override
	public int getAllPopulations() throws Exception {
		return inMainDao.getAllPopulations();
	}

	@Override
	public int getDataCount() throws Exception {
		return inMainDao.getDataCount();
	}

	@Override
	public int getLogCount() throws Exception {
		return inMainDao.getLogCount();
	}

	@Override
	public int setBizUserLog(Map<String, Object> newParams) throws Exception {
		return inMainDao.setBizUserLog(newParams);
	}

	@Override
	public int setBizMenuLog(Map<String, Object> newParams) throws Exception {
		return inMainDao.setBizMenuLog(newParams);
	}

	
}