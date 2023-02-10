package kr.co.socsoft.stats.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.socsoft.common.vo.DataUpldLogVO;
import kr.co.socsoft.common.vo.DataVO;
import kr.co.socsoft.common.vo.DataLinkVO;
import kr.co.socsoft.stats.vo.AccessVO;

@Mapper("statsDao")
public interface StatsDao {
	
	List<AccessVO> getPublicAccessCountList(AccessVO accessVo) throws Exception;	
	
	int getPublicAccessCountListTot(AccessVO accessVo) throws Exception;

	List<AccessVO> getPublicAccessCountExcel(AccessVO accessVo) throws Exception;
	
	
	
	
	List<AccessVO> getUserAccessCountList(AccessVO accessVo) throws Exception;	
	
	int getUserAccessCountListTot(AccessVO accessVo) throws Exception;

	List<AccessVO> getUserAccessCountExcel(AccessVO accessVo) throws Exception;
	
	
	
	List<AccessVO> getPublicMenuCountList(AccessVO accessVo) throws Exception;	
	
	int getPublicMenuCountListTot(AccessVO accessVo) throws Exception;

	List<AccessVO> getPublicMenuCountExcel(AccessVO accessVo) throws Exception;
	
	
	
	
	List<AccessVO> getUserMenuCountList(AccessVO accessVo) throws Exception;	
	
	int getUserMenuCountListTot(AccessVO accessVo) throws Exception;

	List<AccessVO> getUserMenuCountExcel(AccessVO accessVo) throws Exception;
	
	
}