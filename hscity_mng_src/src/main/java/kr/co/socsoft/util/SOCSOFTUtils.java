package kr.co.socsoft.util;

import org.springframework.util.ObjectUtils;

public class SOCSOFTUtils {
    /**
     * 스프링 ObjectUtils 클래스 isEmpty 메소드는 값이 숫자 0 일 경우 false 로 판단
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        return ObjectUtils.isEmpty(obj);
    }

    /**
     * 스프링 ObjectUtils 클래스 isEmpty 메소드는 값이 숫자 0 일 경우 false 로 판단
     *
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return !ObjectUtils.isEmpty(obj);
    }
}
