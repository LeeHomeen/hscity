package kr.co.socsoft.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.com.cmm.EgovUserDetailsHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.socsoft.common.vo.LoginVO;

@Configuration
public class AuthenticInterceptor extends HandlerInterceptorAdapter {

    //private static final Logger LOG = (Logger) LogManager.getLogger(AuthenticInterceptor.class);
   
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 일반 사용자 ajax 요청일 경우 AJAX 값을 헤더에서 꺼내 세션이 없을경우 권한에러를 표시하도록 수정
        if (request.getHeader("AJAX") != null && request.getHeader("AJAX").equals(Boolean.TRUE.toString())) {
        	LoginVO loginVO = EgovUserDetailsHelper.getAuthenticatedUser();
        	String userId = loginVO.getUserId();//system
            if (StringUtils.isEmpty(loginVO.getUserId()) 
            		|| userId.trim().equals("system") 
            		|| userId.trim().equals("sys")
            		|| userId.trim().equals("test") ) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {
        HttpSession session = request.getSession();        
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception arg) throws Exception {
       
    }
}