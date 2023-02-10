package kr.co.socsoft.stats.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.socsoft.common.vo.DataUpldLogVO;
import kr.co.socsoft.common.vo.DataVO;
import kr.co.socsoft.common.vo.DataLinkVO;
import kr.co.socsoft.data.service.DataDao;
import kr.co.socsoft.data.service.DataService;
import kr.co.socsoft.data.vo.DbUpldLogVO;
import kr.co.socsoft.stats.service.StatsDao;
import kr.co.socsoft.stats.service.StatsService;
import kr.co.socsoft.stats.vo.AccessVO;


@Service("statsService")
public class StatsServiceImpl implements StatsService{
	
	
	@Resource(name = "statsDao")
	private StatsDao statsDao;

	

    @Override
    public List<AccessVO> getPublicAccessCountList(AccessVO accessVo) throws Exception {
        return statsDao.getPublicAccessCountList(accessVo);
    }

    @Override
    public int getPublicAccessCountListTot(AccessVO accessVo) throws Exception {
        return statsDao.getPublicAccessCountListTot(accessVo);
    }
    
    @Override
    public List<AccessVO> getPublicAccessCountExcel(AccessVO accessVo) throws Exception {
        return statsDao.getPublicAccessCountExcel(accessVo);
    }
    
    
    
    @Override
    public List<AccessVO> getUserAccessCountList(AccessVO accessVo) throws Exception {
        return statsDao.getUserAccessCountList(accessVo);
    }

    @Override
    public int getUserAccessCountListTot(AccessVO accessVo) throws Exception {
        return statsDao.getUserAccessCountListTot(accessVo);
    }
    
    @Override
    public List<AccessVO> getUserAccessCountExcel(AccessVO accessVo) throws Exception {
        return statsDao.getUserAccessCountExcel(accessVo);
    }


    
    @Override
    public List<AccessVO> getPublicMenuCountList(AccessVO accessVo) throws Exception {
        return statsDao.getPublicMenuCountList(accessVo);
    }

    @Override
    public int getPublicMenuCountListTot(AccessVO accessVo) throws Exception {
        return statsDao.getPublicMenuCountListTot(accessVo);
    }
    
    @Override
    public List<AccessVO> getPublicMenuCountExcel(AccessVO accessVo) throws Exception {
        return statsDao.getPublicMenuCountExcel(accessVo);
    }
    
    
    
    @Override
    public List<AccessVO> getUserMenuCountList(AccessVO accessVo) throws Exception {
        return statsDao.getUserMenuCountList(accessVo);
    }

    @Override
    public int getUserMenuCountListTot(AccessVO accessVo) throws Exception {
        return statsDao.getUserMenuCountListTot(accessVo);
    }
    
    @Override
    public List<AccessVO> getUserMenuCountExcel(AccessVO accessVo) throws Exception {
        return statsDao.getUserMenuCountExcel(accessVo);
    }

}
