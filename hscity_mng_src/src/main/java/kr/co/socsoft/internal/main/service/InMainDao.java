package kr.co.socsoft.internal.main.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.socsoft.internal.main.vo.MenuCategoryVO;
import kr.co.socsoft.manage.board.vo.BoardVO;
import kr.co.socsoft.menu.vo.CategoryVo;

@Mapper("inMainDao")
public interface InMainDao {
	
	List<MenuCategoryVO> getSearchMenuCategory(Map<String, Object> newParams);
	List<MenuCategoryVO> getMenuCategory(Map<String, Object> newParams);
	MenuCategoryVO getMenuInfo(Map<String, Object> newParams);
	List<CategoryVo> getMainImg();
	int getCheckPassYn(Map<String, Object> newParams);
	int setNewPassword(Map<String, Object> newParams);
	int setFavPage(Map<String, Object> newParams);
	int checkFavPage(Map<String, Object> newParams);
	int removeFavPage(Map<String, Object> newParams);
	List<MenuCategoryVO> getFavMenuList(Map<String, Object> newParams);
	BoardVO getMainNotice();
	int getHsPopulations();
	int getAllPopulations();
	int getDataCount();
	int getLogCount();
	int setBizUserLog(Map<String, Object> newParams);
	int setBizMenuLog(Map<String, Object> newParams);

}