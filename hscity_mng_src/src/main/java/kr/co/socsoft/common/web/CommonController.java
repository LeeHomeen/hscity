package kr.co.socsoft.common.web;

import kr.co.socsoft.common.vo.AnalysisExcelDataVO;
import kr.co.socsoft.common.vo.FileVO;
import kr.co.socsoft.manage.file.service.FileService;
import kr.co.socsoft.manage.file.vo.BbsAttFileVO;
import kr.co.socsoft.manage.file.vo.SmartEditorFileUploadVO;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Controller
@RequestMapping(value = "/com")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Resource(name="fileService")
    private FileService fileService;

    /**
     * 세션에 파일 다운로드 값이 있는지 확인한다.
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/downloadCheck.do")
    @ResponseBody
    public boolean fileSessionCheck(HttpSession session) throws Exception {
        return session.getAttribute("file") != null;
    }

    /**
     * 분석영역 데이터(이미지 + 표) 엑셀 시트별로 저장한다.
     *
     * @param model
     * @param data  분석영역 데이터 (json)
     * @return Excel View
     * @throws IOException
     */
    @RequestMapping(value = "/excel/analysis")
    public String analysisExcelDownlaod(Model model, @RequestParam String data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AnalysisExcelDataVO excelData = mapper.readValue(data, AnalysisExcelDataVO.class);
        model.addAttribute("excelData", excelData);
        return "excelView";
    }

    @RequestMapping(value = "leftMenu.do")
    public String leftMenu(Model model, @RequestParam(value = "type") String type, @RequestParam(value = "menu") String menu) {
        model.addAttribute("type", type);
        model.addAttribute("menu", menu);
        return "/admin/layout/left";
    }

    @RequestMapping(value = "/file_uploader_html5.do")
    public void fileUploadHTML(SmartEditorFileUploadVO uploadVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String return1 = "";
        String return2 = "";
        String return3 = "";
        //변경 title 태그에는 원본 파일명을 넣어주어야 하므로
        String name = "";

        return1 = uploadVO.getCallback();
        return2 = "?callback_func=" + uploadVO.getCallback_func();
        MultipartFile filedata = uploadVO.getFiledata();
        if (filedata.getSize() > 0) {
            name = filedata.getOriginalFilename().substring(filedata.getOriginalFilename().lastIndexOf(File.separator) + 1);
            String filename_ext = name.substring(name.lastIndexOf(".") + 1);
            filename_ext = filename_ext.toLowerCase();
            String[] allow_file = {"jpg", "png", "bmp", "gif"};
            int cnt = 0;
            for (int i = 0; i < allow_file.length; i++) {
                if (filename_ext.equals(allow_file[i])) {
                    cnt++;
                }
            }
            if (cnt == 0) {
                return3 = "&errstr=" + name;
            } else {
                String dftFilePath = request.getServletContext().getRealPath("/");
                String filePath = dftFilePath + "editor" + File.separator + "upload" + File.separator;

                File file = null;
                file = new File(filePath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                String realFileNm = "";
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String today = formatter.format(new java.util.Date());
                realFileNm = today + UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));

                String rlFileNm = filePath + realFileNm;
                ///////////////// 서버에 파일쓰기 /////////////////
                InputStream is = filedata.getInputStream();
                OutputStream os = new FileOutputStream(rlFileNm);
                int numRead;
                byte b[] = new byte[(int) filedata.getSize()];
                while ((numRead = is.read(b, 0, b.length)) != -1) {
                    os.write(b, 0, numRead);
                }
                if (is != null) {
                    is.close();
                }
                os.flush();
                os.close();

                return3 += "&bNewLine=true";
                return3 += "&sFileName=" + name;
                return3 += "&sFileURL=/editor/upload/" + realFileNm;
            }
        }
        response.sendRedirect(return1 + return2 + return3);
    }

    @RequestMapping(value="/downloadFile.do")
    public String downloadFile(BbsAttFileVO bbsAttFileVO, Model model) throws IOException {
        bbsAttFileVO = fileService.selectBbsAttFile(bbsAttFileVO);
        FileVO fileVO = new FileVO(bbsAttFileVO.getFileNm(), bbsAttFileVO.getFileSize().longValue(), bbsAttFileVO.getFileData());
        model.addAttribute("fileVO", fileVO);
        return "downloadView";
    }
}
