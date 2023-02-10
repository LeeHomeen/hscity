package kr.co.socsoft;

import kr.co.socsoft.common.vo.FileVO;
import kr.co.socsoft.manage.file.vo.BbsAttFileVO;
import kr.co.socsoft.util.DownloadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Map;

public class DownloadView extends AbstractView {

    public DownloadView() {
        setContentType("application/download; UTF-8");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FileVO fileVO = (FileVO) model.get("fileVO");

        if(fileVO.getFileSize() > 0) {
            response.setContentType(getContentType());
            response.setContentLength(fileVO.getFileSize().intValue());

            response.setHeader("Content-Disposition", "ATTachment; Filename=" + DownloadUtil.getDisposition(fileVO.getFileName(), DownloadUtil.getBrowser(request)));
            response.setHeader("Content-Transfer-Encoding", "binary");

            OutputStream out = response.getOutputStream();
            InputStream in = null;

            try {
                byte[] fileData = fileVO.getFileData();
                in = new ByteArrayInputStream(fileData);
                FileCopyUtils.copy(in, out);
            } catch(Exception e) {
                logger.error("file download error!!", e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e2) {
                        logger.error("stream close error", e2);
                    }
                }
            }
            out.flush();
        }
    }
}
