package kr.co.socsoft.screen.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.socsoft.screen.vo.BannerVO;
import kr.co.socsoft.screen.vo.ScreenVO;

@Mapper("screenDao")
public interface ScreenDao {

	List<ScreenVO> getPubScreenList(Map<String, Object> newParams);
	
	List<ScreenVO> getBizScreenList(Map<String, Object> newParams);

	int getCheckLocNumbers(Map<String, Object> data);

	void setLocNumbers(Map<String, Object> newParams);

	void removeBefNumers(Map<String, Object> newParams);

	int removeMainMng(Map<String, Object> newParams);
	
	int updateMenuImgUseYn(ScreenVO screenVO);

	int getMenuImgYn(ScreenVO svo);
	
	
	
	List<BannerVO> getBannerList(Map<String, Object> newParams);
	
	BannerVO getBannerDetail(String bannerSn);
	
	int updateBanner(BannerVO bannerVO);
	
	int removeBanner(BannerVO bannerVO);
	
	int getCheckBannerOrder(Map<String, Object> data) throws Exception;
	
	void removeBefOrder(Map<String, Object> newParams);
	
	int setBannerOrder(Map<String, Object> newParams);
	
	int updateBannerUseYn(BannerVO bannerVO) throws Exception;
	
	void makeBanner(BannerVO bannerVO);
	
	void updateLayout(Map<String, Object> newParams);
}