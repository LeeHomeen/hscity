<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/common/head.jsp" %>
<style>
/* subPage.jsp 에서만 적용되어야함 */
	.stitle {
		width: 94%;
	}
</style>
<body>
	 <%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/header.jsp" %>
      <%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/right.jsp" %>

	<div class="AllWrap">
	

	<script type="text/javascript">

		
		$(document).ready(function(){
			$('#treemenu h3').on('click',function(e){
				$.ajax({
				    url: "/login/setMenuId.do",
				    type : "POST",
				    dataType: 'json',
				    data :JSON.stringify({menuId : $(this)[0].id}), 
				    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
				    success: function(response) {
					   alert(response);
				    },
			        error : function(request, status, error) { 		        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

			        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			   	 	}
				});
			});
			$.ajax({
			    url: "/login/getMenuId.do",
			    type : "POST",
			    dataType: 'json',
			  //  data :JSON.stringify({menuId : $(this)[0].id}), 
			    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
			    success: function(response) {
			    		$('#treemenu h3#' + response).trigger('click');
			    },
		        error : function(request, status, error) { 
		        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

		        	if(request.status == 200){
			    		$('#treemenu h3#' + request.responseText).trigger('click');
		        	}
		   	 	}
			});
			
			// 파일 다운로드
		    $('.download-file').on('click', function(){
		        var menuCd = $(this).data('menuCd');
		        var seq = $(this).data('seq');

		        CM.postFileDownload('<c:url value="/menu/downloadFile.do"/>', {
		        	categoryType: 'biz',// 'biz', 'pub'
		            menuCd: menuCd,
		            seq: seq
		        });
		    });
			
			$('.contentWrap .stitle i.favorite').on('click',function(){
				var menuCode = '${menuCd}';
				$.ajax({
				    url: "/internal/setFavPage.do",
				    type : "POST",
				    dataType: 'json',
				    data :JSON.stringify({menuCd : menuCode}), 
				    headers : { 'Content-Type' : 'application/json; charset=UTF-8'},
				    success: function(response) {
					   alert(response);
				    },
			        error : function(request, status, error) { 
			          	if(request.status == 200){
							   alert(request.responseText);
			       	 	}
			          	
			        	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			   	 	}
				});
			})
			
		});
	</script>
	<jsp:include page="/internal/leftMenu.do"/>
		<!-- 콘텐츠영역 //-->
		<div class="contentWrap">
			<img src="<c:url value="/images/biz/btncate_close.png"/>" alt="카테고리 열기&닫기" class="cateBtn">
			
			<h3 class="stitle">
				<img src="<c:url value="/images/biz/icon_graph.png"/>" alt="" class="bl">${menuVo.menuNm} 
				<c:if test="${menuVo.favYn == 0}">
				<i class="favorite"></i>
				</c:if>
				<c:if test="${menuVo.favYn == 1}">
				<i class="favorite on"></i>
				</c:if>
			</h3>
			
			<div class="subContentWrap">
				<div class="subContent">
					<!-- 콘텐츠 //-->
					
						<table class="tbl_view mt10">
							<caption></caption>
							<colgroup>
								<col width="80px">
								<col>
								<col width="180px">
							</colgroup>
							<tbody>
							<c:if test="${menuVo.fileNm.length() > 0}">
								<tr class="tbl_view_file">
									<th scope="row">첨부파일</th>
									<td colspan="2">
									<c:if test="${fn:length(attachFile) > 0}">
									<c:forEach var="file" items="${attachFile}" varStatus="status">
                                            <a href="#" class="download-file" data-menu-cd="${file.menuCd}" data-seq="${file.seq}" onclick="return false;">${file.fileNm}</a>
									</c:forEach>
								</c:if>
								</tr>
							</c:if>
							<c:if test="${menuVo.contents != '<p>&nbsp;</p>'}">
								<tr>
									<td colspan="3">
										<div class="tbl_view_txt">
											${menuVo.contents}
										</div>
									</td>
								</tr>
								</c:if>
							</tbody>
						</table>

						<c:if test="${url != ''}">
						<div class="bi_div">
							<iframe id="" name="" src="${url}" style="width: 100%; height: 943px;"> <!-- //943px -->
						   </iframe>
					    </div>
						</c:if>

				</div>
			</div>
		</div>
		<!--// 콘텐츠영역 -->		
	</div>
		
	<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/footer.jsp" %>
	<%@ include file="/WEB-INF/jsp/kr/co/socsoft/internal/layout/modals.jsp" %>
	
</body>
</html>
