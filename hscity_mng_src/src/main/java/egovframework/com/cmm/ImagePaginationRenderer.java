package egovframework.com.cmm;

import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * ImagePaginationRenderer.java 클래스
 *
 * @author 서준식
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2011. 9. 16.   서준식       이미지 경로에 ContextPath추가
 * </pre>
 * @since 2011. 9. 16.
 */
public class ImagePaginationRenderer extends AbstractPaginationRenderer implements ServletContextAware {

    private ServletContext servletContext;

    public ImagePaginationRenderer() {

    }

    public void initVariables() {
        firstPageLabel = "<span class=\"arrow prev\"><a class=\"_paging\" data-page-index=\"{1}\" href=\"#\" title=\"처음페이지\"><img src=\"/images/biz/btn_pre_end.png\" alt=\"처음 페이지\"/></a>";
        previousPageLabel = "<a class=\"_paging\" data-page-index=\"{1}\" href=\"#\" title=\"이전페이지\"><img src=\"/images/biz/btn_pre.png\" alt=\"이전 페이지\"/></a></span>";
        currentPageLabel = "<span class=\"list\"><span class=\"now\">{0}</span></span>";
        otherPageLabel = "<span class=\"list\"><a class=\"_paging\" data-page-index=\"{1}\" href=\"#\">{2}</a></span>";
        nextPageLabel = "<span class=\"arrow newx\"><a class=\"_paging\" data-page-index=\"{1}\" href=\"#\" title=\"다음페이지\"><img src=\"/images/biz/btn_next_end.png\" alt=\"다음 페이지\"/></a>";
        lastPageLabel = "<a class=\"_paging\" data-page-index=\"{1}\" href=\"#\" title=\"마지막페이지\"><img src=\"/images/biz/btn_next.png\" alt=\"마지막 페이지\"/></a></span>";
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
        initVariables();
    }

}
