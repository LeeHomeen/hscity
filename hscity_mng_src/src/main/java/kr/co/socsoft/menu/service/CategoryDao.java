package kr.co.socsoft.menu.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.socsoft.menu.vo.CategoryVo;

@Mapper("categoryDao")
public interface CategoryDao {
	
	List<?> getPubCategoryData(CategoryVo categoryVo);
	
	List<?> getBizCategoryData(CategoryVo categoryVo);	
	
	int savePubCategoryData(List<CategoryVo> categoryVoList);
	
	int saveBizCategoryData(List<CategoryVo> categoryVoList);
	
	List<CategoryVo> getCategoryGroupList(CategoryVo categoryVo);
	
	List<CategoryVo> getMenuList(CategoryVo categoryVo);
	
	CategoryVo getMenuDetailInfo(CategoryVo categoryVo);
	
	List<CategoryVo> getMenuDetailAttachFile(CategoryVo categoryVo);
	
	CategoryVo getMenuDetailAttachFileOne(CategoryVo categoryVo);
	
	CategoryVo getMenuImg(CategoryVo categoryVo);
	
	int saveMenuInfo(CategoryVo categoryVo);
	
	String getMaxMenuCd(CategoryVo categoryVo);
	
	int saveMainMng(CategoryVo categoryVo);
	
	int deleteMainMng(CategoryVo categoryVo);
	
	int saveMenuFileData(CategoryVo categoryVo);
	
	int deleteMenuInfo(CategoryVo categoryVo);
	
	int deleteMenuFileData(CategoryVo categoryVo);
	
	List<CategoryVo> getDeptList(Map<String, Object> data);
	
	List<CategoryVo> getMappingDeptList(Map<String, Object> data);
	
	int addDeptMap(Map<String, Object> newParams);
	
	int removeDeptMap(Map<String, Object> newParams);
	
	List<CategoryVo> getUserList(Map<String, Object> data);
	
	List<CategoryVo> getMappingUserList(Map<String, Object> data);
	
	int addUserMap(Map<String, Object> newParams);
	
	int removeUserMap(Map<String, Object> newParams);
	
	int updateMenuMst(CategoryVo categoryVo);
	
	int getLayout(CategoryVo categoryVo);
}