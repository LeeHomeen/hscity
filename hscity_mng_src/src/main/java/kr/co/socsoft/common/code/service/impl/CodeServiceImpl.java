package kr.co.socsoft.common.code.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import kr.co.socsoft.common.vo.CodeVO;
import kr.co.socsoft.common.code.service.CodeDao;
import kr.co.socsoft.common.code.service.CodeService;


@Service("codeService")
public class CodeServiceImpl implements CodeService {
	
	@Resource(name = "codeDao")
	private CodeDao codeDao;
	
	@Override
	public List<CodeVO> getCommCode(CodeVO codeVo) throws Exception {
		return codeDao.getCommCode(codeVo);
	}
	
	
	//내부시스템 연계코드
	@Override
	public List<CodeVO> getSystemLinkCode(CodeVO codeVo) throws Exception {
		return codeDao.getSystemLinkCode(codeVo);
	}
	
}
