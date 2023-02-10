package kr.co.socsoft.util;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

public class DownloadUtil {
    /**
     * 브라우져에 맞는 파일명으로 변환
     *
     * @param filename
     * @param browser
     * @return
     * @throws Exception
     */
    public static String getDisposition(String filename, String browser) throws Exception {
        String encodedFilename = null;
        if (browser.equals("MSIE")) {
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.equals("Firefox")) {
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Opera")) {
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Chrome")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < filename.length(); i++) {
                char c = filename.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            encodedFilename = sb.toString();
        } else {
            throw new RuntimeException("Not supported browser");
        }
        return encodedFilename;
    }

    /**
     * 브라우져 판별
     *
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if (header.contains("MSIE")) {
            return "MSIE";
        } else if (header.contains("Chrome")) {
            return "Chrome";
        } else if (header.contains("Opera")) {
            return "Opera";
        } else if (header.contains("rv:")){
            return "MSIE";
        }

        return "MSIE";
    }
}
