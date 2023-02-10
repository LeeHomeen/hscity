package kr.co.socsoft.menu.service;

import java.util.List;
import java.util.Map;

import kr.co.socsoft.menu.vo.CategoryVo;


public interface CategoryService {
	
	List<?> getPubCategoryData(CategoryVo categoryVo) throws Exception;
	
	List<?> getBizCategoryData(CategoryVo categoryVo) throws Exception;
	
	int savePubCategoryData(List<CategoryVo> categoryVoList) throws Exception;
	
	int saveBizCategoryData(List<CategoryVo> categoryVoList) throws Exception;
	
	List<CategoryVo> getCategoryGroupList(CategoryVo categoryVo) throws Exception;
	
	List<CategoryVo> getMenuList(CategoryVo categoryVo) throws Exception;
	
	CategoryVo getMenuDetailInfo(CategoryVo categoryVo) throws Exception;
	
	List<CategoryVo> getMenuDetailAttachFile(CategoryVo categoryVo) throws Exception;
	
	CategoryVo getMenuDetailAttachFileOne(CategoryVo categoryVo) throws Exception;
	
	CategoryVo getMenuImg(CategoryVo categoryVo) throws Exception;
	
	String getMaxMenuCd(CategoryVo categoryVo) throws Exception;
	
	int saveMenuInfo(CategoryVo categoryVo) throws Exception;
	
	int saveMainMng(CategoryVo categoryVo) throws Exception;
	
	int deleteMainMng(CategoryVo categoryVo) throws Exception;
	
	int saveMenuFileData(List<CategoryVo> categoryVoList) throws Exception;
	
	int deleteMenuInfo(CategoryVo categoryVo) throws Exception;
	
	int deleteMenuFileData(CategoryVo categoryVo) throws Exception;
	
	List<CategoryVo> getDeptList(Map<String, Object> data) throws Exception;
	
	List<CategoryVo> getMappingDeptList(Map<String, Object> data) throws Exception;
	
	int addDeptMap(Map<String, Object> newParams) throws Exception;
	
	int removeDeptMap(Map<String, Object> newParams) throws Exception;
	
	List<CategoryVo> getUserList(Map<String, Object> data) throws Exception;
	
	List<CategoryVo> getMappingUserList(Map<String, Object> data) throws Exception;
	
	int addUserMap(Map<String, Object> newParams) throws Exception;
	
	int removeUserMap(Map<String, Object> newParams) throws Exception;
	
	int updateMenuMst(CategoryVo categoryVo) throws Exception;
	
	int getLayout(CategoryVo categoryVo);
}
