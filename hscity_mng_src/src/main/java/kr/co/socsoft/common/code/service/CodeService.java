package kr.co.socsoft.common.code.service;

import java.util.List;
import kr.co.socsoft.common.vo.CodeVO;

public interface CodeService {
	
	List<CodeVO> getCommCode(CodeVO codeVo) throws Exception;

	//내부시스템 연계코드
	List<CodeVO> getSystemLinkCode(CodeVO codeVo) throws Exception;
}
