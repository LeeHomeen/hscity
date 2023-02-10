package kr.co.socsoft.menu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.sun.istack.logging.Logger;

import kr.co.socsoft.menu.vo.CategoryVo;
import kr.co.socsoft.menu.service.CategoryDao;
import kr.co.socsoft.menu.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{
	
	@Resource(name = "categoryDao")
	private CategoryDao categoryDao;
	
	@Override
	public List<?> getPubCategoryData(CategoryVo categoryVo) {
		return categoryDao.getPubCategoryData(categoryVo);
	}
	
	@Override
	public List<?> getBizCategoryData(CategoryVo categoryVo) {
		return categoryDao.getBizCategoryData(categoryVo);
	}
	
	@Override
	public int savePubCategoryData(List<CategoryVo> categoryVoList) throws Exception {
		return categoryDao.savePubCategoryData(categoryVoList);
	}
	
	@Override
	public int saveBizCategoryData(List<CategoryVo> categoryVoList) throws Exception {
		return categoryDao.saveBizCategoryData(categoryVoList);
	}
	
	@Override
	public List<CategoryVo> getCategoryGroupList(CategoryVo categoryVo) {
		return categoryDao.getCategoryGroupList(categoryVo);
	}
	
	@Override
	public List<CategoryVo> getMenuList(CategoryVo categoryVo) {
		return categoryDao.getMenuList(categoryVo);
	}
	
	@Override
	public CategoryVo getMenuDetailInfo(CategoryVo categoryVo) {
		return categoryDao.getMenuDetailInfo(categoryVo);
	}
	
	@Override
	public List<CategoryVo> getMenuDetailAttachFile(CategoryVo categoryVo) {
		return categoryDao.getMenuDetailAttachFile(categoryVo);
	}
	
	@Override
	public CategoryVo getMenuDetailAttachFileOne(CategoryVo categoryVo) {
		return categoryDao.getMenuDetailAttachFileOne(categoryVo);
	}
	
	@Override
	public CategoryVo getMenuImg(CategoryVo categoryVo) {
		return categoryDao.getMenuImg(categoryVo);
	}
	
	@Override
	public String getMaxMenuCd(CategoryVo categoryVo) throws Exception {
		return categoryDao.getMaxMenuCd(categoryVo);
	}
	
	@Override
	public int saveMenuInfo(CategoryVo categoryVo) throws Exception {
		return categoryDao.saveMenuInfo(categoryVo);
	}
	
	@Override
	public int saveMainMng(CategoryVo categoryVo) throws Exception {
		return categoryDao.saveMainMng(categoryVo);
	}
	
	@Override
	public int deleteMainMng(CategoryVo categoryVo) throws Exception {
		return categoryDao.deleteMainMng(categoryVo);
	}
	
	@Override
	public int saveMenuFileData(List<CategoryVo> categoryVoList) throws Exception {
		int rtn = 0;
		for (int i=0; i<categoryVoList.size(); i++) {
			categoryDao.saveMenuFileData(categoryVoList.get(i));
			rtn++;
		}
		return rtn;
	}
	
	@Override
	public int deleteMenuInfo(CategoryVo categoryVo) throws Exception {
		return categoryDao.deleteMenuInfo(categoryVo);	
	}
	
	@Override
	public int deleteMenuFileData(CategoryVo categoryVo) throws Exception {
		return categoryDao.deleteMenuFileData(categoryVo);	
	}
	
	@Override
	public List<CategoryVo> getDeptList(Map<String, Object> data) {
		return categoryDao.getDeptList(data);
	}
	
	@Override
	public List<CategoryVo> getMappingDeptList(Map<String, Object> data) {
		return categoryDao.getMappingDeptList(data);
	}
	
	@Override
	public int addDeptMap(Map<String, Object> newParams) throws Exception {
		return categoryDao.addDeptMap(newParams);
	}
	
	@Override
	public int removeDeptMap(Map<String, Object> newParams) throws Exception {
		return categoryDao.removeDeptMap(newParams);
	}
	
	@Override
	public List<CategoryVo> getUserList(Map<String, Object> data) {
		return categoryDao.getUserList(data);
	}
	
	@Override
	public List<CategoryVo> getMappingUserList(Map<String, Object> data) {
		return categoryDao.getMappingUserList(data);
	}
	
	@Override
	public int addUserMap(Map<String, Object> newParams) throws Exception {
		return categoryDao.addUserMap(newParams);
	}
	
	@Override
	public int removeUserMap(Map<String, Object> newParams) throws Exception {
		return categoryDao.removeUserMap(newParams);
	}
	
	@Override
	public int updateMenuMst(CategoryVo categoryVo) throws Exception {
		return categoryDao.updateMenuMst(categoryVo);
	}

	@Override
	public int getLayout(CategoryVo categoryVo) {
		return categoryDao.getLayout(categoryVo);
	}
}
