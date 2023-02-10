package kr.co.socsoft.screen.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import kr.co.socsoft.screen.service.ScreenDao;
import kr.co.socsoft.screen.service.ScreenService;
import kr.co.socsoft.screen.vo.BannerVO;
import kr.co.socsoft.screen.vo.ScreenVO;



@Service("screenService")
public class ScreenServiceImpl implements ScreenService{
	
	@Resource(name = "screenDao")
	private ScreenDao screenDao;
	
	@Override
	public List<ScreenVO> getPubScreenList(Map<String, Object> newParams) throws Exception {
		return screenDao.getPubScreenList(newParams);
	}

	@Override
	public List<ScreenVO> getBizScreenList(Map<String, Object> newParams) throws Exception {
		return screenDao.getBizScreenList(newParams);
	}

	@Override
	public int getCheckLocNumbers(Map<String, Object> data) throws Exception {
		return screenDao.getCheckLocNumbers(data);
	}

	@Override
	public void setLocNumbers(Map<String, Object> newParams) throws Exception {
		 screenDao.setLocNumbers(newParams);
	}

	@Override
	public void removeBefNumers(Map<String, Object> newParams) throws Exception {
		screenDao.removeBefNumers(newParams);
		
	}

	@Override
	public int removeMainMng(Map<String, Object> newParams) throws Exception {
		return screenDao.removeMainMng(newParams);
	}

	@Override
	public int updateMenuImgUseYn(ScreenVO screenVO) throws Exception {
		return screenDao.updateMenuImgUseYn(screenVO);
	}

	@Override
	public int getMenuImgYn(ScreenVO svo) throws Exception {
		return screenDao.getMenuImgYn(svo);
	}

	@Override
	public List<BannerVO> getBannerList(Map<String, Object> newParams) throws Exception {
		return screenDao.getBannerList(newParams);
	}

	@Override
	public BannerVO getBannerDetail(String bannerSn) {
		return screenDao.getBannerDetail(bannerSn);
	}

	@Override
	public int updateBanner(BannerVO bannerVO) {
		return screenDao.updateBanner(bannerVO);
	}
	
	@Override
	public int removeBanner(BannerVO bannerVO) {
		return screenDao.removeBanner(bannerVO);
	}

	@Override
	public int getCheckBannerOrder(Map<String, Object> data) throws Exception {
		return screenDao.getCheckBannerOrder(data);
	}

	@Override
	public void removeBefOrder(Map<String, Object> newParams) {
		screenDao.removeBefOrder(newParams);		
	}

	@Override
	public int setBannerOrder(Map<String, Object> newParams) {
		return screenDao.setBannerOrder(newParams);
	}

	@Override
	public int updateBannerUseYn(BannerVO bannerVO) throws Exception {
		return screenDao.updateBannerUseYn(bannerVO);
	}

	@Override
	public void makeBanner(BannerVO bannerVO) {
		screenDao.makeBanner(bannerVO);
	}

	@Override
	public void updateLayout(Map<String, Object> newParams) {
		screenDao.updateLayout(newParams);
	}
}