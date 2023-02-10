package kr.co.socsoft.common.code.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.socsoft.common.vo.CodeVO;

@Mapper("codeDao")
public interface CodeDao {
	
	List<CodeVO> getCommCode(CodeVO codeVo);
	
	//시스템 연계코드
	List<CodeVO> getSystemLinkCode(CodeVO codeVo);
	
}