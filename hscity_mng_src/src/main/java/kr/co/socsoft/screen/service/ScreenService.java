package kr.co.socsoft.screen.service;

import java.util.List;
import java.util.Map;

import kr.co.socsoft.screen.vo.BannerVO;
import kr.co.socsoft.screen.vo.ScreenVO;

public interface ScreenService {

	List<ScreenVO> getPubScreenList(Map<String, Object> newParams) throws Exception;
	List<ScreenVO> getBizScreenList(Map<String, Object> newParams) throws Exception;
	int getCheckLocNumbers(Map<String, Object> data) throws Exception;
	void setLocNumbers(Map<String, Object> newParams) throws Exception;
	void removeBefNumers(Map<String, Object> newParams) throws Exception;
	int removeMainMng(Map<String, Object> newParams) throws Exception;
	int updateMenuImgUseYn(ScreenVO screenVO) throws Exception;
	int getMenuImgYn(ScreenVO svo) throws Exception;
	
	
	
	List<BannerVO> getBannerList(Map<String, Object> newParams) throws Exception;
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