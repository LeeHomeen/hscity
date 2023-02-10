package kr.co.socsoft.manage.board.vo;

import egovframework.com.cmm.DefaultVO;
import kr.co.socsoft.manage.file.vo.BbsAttFileVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class BoardVO extends DefaultVO {
    private BigDecimal seq; /* 일련번호 */
    private String bbsTypeScd; /* 게시글구분_코드 */
    private String bbsTypeNm; /* 게시글구분_코드명 */
    private String title; /* 제목 */
    private String contents; /* 내용 */
    private String rmk; /* 비고 */
    private String useYn; /* 사용여부 */
    private String attFileMngSeq; /* 첨부파일관리일련번호 */
    private String createId; /* 생성자아이디 */
    private String createNm; /* 생성자명 */
    private String createDt; /* 생성일자 */
    private String updateId; /* 수정자아이디 */
    private String updateDt; /* 수정일자 */
    private BigDecimal upperQnaSeq; /* 상위 게시판 코드 */
    private String isNew;

    private String site; // 대시민, 업무 구분시 필요

    private List<AnswerVO> answers; /* 답변을 처리 */
    private List<BbsAttFileVO> bbsAttFiles;
    private List<BbsAttFileVO> answerBbsAttFiles;
    private List<MultipartFile> files; /* 첨부파일 */
    private BigDecimal[] fileDelList; /* 첨부파일 삭제 */
    
    private List<AnswerVO> thinks; /* 생각의 탄생 답변을 처리 */
    private BigDecimal upperThinkSeq; /* 생각의 탄생 */
    
    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getBbsTypeNm() {
        return bbsTypeNm;
    }

    public void setBbsTypeNm(String bbsTypeNm) {
        this.bbsTypeNm = bbsTypeNm;
    }

    public List<BbsAttFileVO> getAnswerBbsAttFiles() {
        return answerBbsAttFiles;
    }

    public void setAnswerBbsAttFiles(List<BbsAttFileVO> answerBbsAttFiles) {
        this.answerBbsAttFiles = answerBbsAttFiles;
    }

    public BigDecimal getUpperQnaSeq() {
        return upperQnaSeq;
    }

    public void setUpperQnaSeq(BigDecimal upperQnaSeq) {
        this.upperQnaSeq = upperQnaSeq;
    }

    public List<AnswerVO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerVO> answers) {
        this.answers = answers;
    }

    public BigDecimal[] getFileDelList() {
        return fileDelList;
    }

    public void setFileDelList(BigDecimal[] fileDelList) {
        this.fileDelList = fileDelList;
    }

    public List<BbsAttFileVO> getBbsAttFiles() {
        return bbsAttFiles;
    }

    public void setBbsAttFiles(List<BbsAttFileVO> bbsAttFiles) {
        this.bbsAttFiles = bbsAttFiles;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public BigDecimal getSeq() {
        return seq;
    }

    public void setSeq(BigDecimal seq) {
        this.seq = seq;
    }

    public String getBbsTypeScd() {
        return bbsTypeScd;
    }

    public void setBbsTypeScd(String bbsTypeScd) {
        this.bbsTypeScd = bbsTypeScd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getAttFileMngSeq() {
        return attFileMngSeq;
    }

    public void setAttFileMngSeq(String attFileMngSeq) {
        this.attFileMngSeq = attFileMngSeq;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }
    
    public String getCreateNm() {
		return createNm;
	}

	public void setCreateNm(String createNm) {
		this.createNm = createNm;
	}

	public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
	public List<AnswerVO> getThinks() {
		return thinks;
	}

	public void setThinks(List<AnswerVO> thinks) {
		this.thinks = thinks;
	}

	public BigDecimal getUpperThinkSeq() {
		return upperThinkSeq;
	}

	public void setUpperThinkSeq(BigDecimal upperThinkSeq) {
		this.upperThinkSeq = upperThinkSeq;
	}
    
    
}
