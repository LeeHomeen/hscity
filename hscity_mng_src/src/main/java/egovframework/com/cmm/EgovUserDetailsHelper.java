package egovframework.com.cmm;

import egovframework.rte.fdl.string.EgovObjectUtil;
import kr.co.socsoft.common.vo.LoginVO;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * EgovUserDetails Helper 클래스
 *
 * @author sjyoon
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2009.03.10  sjyoon         최초 생성
 *   2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *   2018.03.13 임상수           화성시 로그인 서비스 커스터마이징
 *
 * </pre>
 * @since 2009.06.01
 */

public class EgovUserDetailsHelper {

    /**
     * 인증된 사용자객체를 VO형식으로 가져온다.
     *
     * @return Object - 사용자 ValueObject
     */
    public static LoginVO getAuthenticatedUser() {
        return (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("LoginVO", RequestAttributes.SCOPE_SESSION) == null ?
                new LoginVO("system") : (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("LoginVO", RequestAttributes.SCOPE_SESSION);

    }

    /**
     * 인증된 사용자의 권한 정보를 가져온다.
     * 예) [ROLE_ADMIN, ROLE_USER, ROLE_A, ROLE_B, ROLE_RESTRICTED, IS_AUTHENTICATED_FULLY, IS_AUTHENTICATED_REMEMBERED, IS_AUTHENTICATED_ANONYMOUSLY]
     *
     * @return List - 사용자 권한정보 목록
     */
    public static List<String> getAuthorities() {
        List<String> listAuth = new ArrayList<String>();

        if (EgovObjectUtil.isNull((LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("LoginVO", RequestAttributes.SCOPE_SESSION))) {
            return null;
        }

        return listAuth;
    }

    /**
     * 인증된 사용자 여부를 체크한다.
     *
     * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)
     */
    public static Boolean isAuthenticated() {

        if (EgovObjectUtil.isNull((LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("LoginVO", RequestAttributes.SCOPE_SESSION))) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
