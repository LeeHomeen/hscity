package kr.co.socsoft.internal.main.service;

import java.util.List;
import java.util.Map;

import kr.co.socsoft.internal.main.vo.MenuCategoryVO;
import kr.co.socsoft.manage.board.vo.BoardVO;
import kr.co.socsoft.menu.vo.CategoryVo;

public interface InMainService {
	
	List<MenuCategoryVO> getSearchMenuCategory(Map<String, Object> newParams) throws Exception;
	List<MenuCategoryVO> getMenuCategory(Map<String, Object> newParams) throws Exception;
	MenuCategoryVO getMenuInfo(Map<String, Object> newParams) throws Exception;
	List<CategoryVo> getMainImg() throws Exception;
	int getCheckPassYn(Map<String, Object> newParams) throws Exception;
	int setNewPassword(Map<String, Object> newParams) throws Exception;
	int setFavPage(Map<String, Object> newParams) throws Exception;
	int checkFavPage(Map<String, Object> newParams) throws Exception;
	int removeFavPage(Map<String, Object> newParams) throws Exception;
	List<MenuCategoryVO> getFavMenuList(Map<String, Object> newParams) throws Exception;
	BoardVO getMainNotice() throws Exception;
	int getHsPopulations() throws Exception;
	int getAllPopulations() throws Exception;
	int getDataCount() throws Exception;
	int getLogCount() throws Exception;
	
	int setBizUserLog(Map<String, Object> newParams) throws Exception;
	int setBizMenuLog(Map<String, Object> newParams) throws Exception;
	

}