<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: G1
  Date: 2018. 2. 1.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../layout/top.jsp" />
<jsp:include page="../../layout/leftMenu.jsp" />
                <div class="sec-right">
                    <!-- 상단 경로 및 페이지 타이틀 -->
                    <div class="top-bar">
                        <div class="path-wrap">
                            <span class="home">홈</span>&nbsp;&nbsp;&gt;
                            <span class="path">데이터 관리</span>&nbsp;&nbsp;&gt;
                            <span class="path">데이터 현황</span>&nbsp;&nbsp;&gt;
                            <span class="now">데이터 컬럼 등록&#47;수정</span>
                        </div>
                        <div class="clearfix">
                            <p class="tit-page">데이터 컬럼 등록&#47;수정</p>
                            <ul class="btn-wrap">
                                <li>
                                    <button class="btn sr3 st3" title="DB에 물리테이블이 반드시 필요합니다.">
                                        <i class="fa fa-download" aria-hidden="true"></i>물리테이블 정보 불러오기
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- //상단 경로 및 페이지 타이틀 -->
                    
                    <!-- 컨텐츠 영역 -->
                    <div class="cont">
                        <!-- 표 상단 설정 영역 -->
                        <div class="tbl-top">
                            <form method="post" action="#">
                                <fieldset>
                                    <legend>표,게시판 정보</legend>
                                    <div class="left">
                                        <span class="star">강조</span>테이블 명: HS_DATA_001, 데이터 명: 인구이동 현황
                                    </div>
                                    <div class="right">
                                        <a href="#" class="btn sr2 st2">저장</a>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                        <!-- //표 상단 설정 영역 -->
                        <div class="tbl-wrap">
                            <table class="tbl sr1 st1">
                                <caption class="hide">대시보드 목록 게시판 표</caption>
                                <colgroup>
                                    <col style="width:7%"/>
                                    <col style="width:15%"/>
                                    <col style="width:15%"/>
                                    <col style="width:auto"/>
                                    <col style="width:8%"/>
                                    <col style="width:7%"/>
                                    <col style="width:8%"/>
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th scope="col">컬럼 순서</th>
                                        <th scope="col">컬럼</th>
                                        <th scope="col">컬럼 이름</th>
                                        <th scope="col">설명</th>
                                        <th scope="col">원본 컬럼여부</th>
                                        <th scope="col">사용여부</th>
                                        <th scope="col">GIS 설정</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td class="ft-st1">std_ym</td>
                                        <td class="td2">기준년월</td>
                                        <td class="align-l">설명영역~</td>
                                        <td>
                                            <button class="btn sr3 st6">없음</button>
                                        </td>
                                        <td>
                                            <button class="btn sr3 st3">사용</button>
                                        </td>
                                        <td>
                                            <label>
                                                <select class="ip sr1 st1 w5">
                                                    <option value="" selected>X</option>
                                                    <option value="">경도</option>
                                                    <option value="">위도</option>
                                                    <option value="">wkt 컬럼</option>
                                                </select>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td class="ft-st1">std_ym</td>
                                        <td class="td2">기준년월</td>
                                        <td class="align-l">설명영역~</td>
                                        <td>
                                            <button class="btn sr3 st6">없음</button>
                                        </td>
                                        <td>
                                            <button class="btn sr3 st3">사용</button>
                                        </td>
                                        <td>
                                            <label>
                                                <select class="ip sr1 st1 w5">
                                                    <option value="" selected>X</option>
                                                    <option value="">경도</option>
                                                    <option value="">위도</option>
                                                    <option value="">wkt 컬럼</option>
                                                </select>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td class="ft-st1">std_ym</td>
                                        <td class="td2">기준년월</td>
                                        <td class="align-l">설명영역~</td>
                                        <td>
                                            <button class="btn sr3 st6">없음</button>
                                        </td>
                                        <td>
                                            <button class="btn sr3 st3">사용</button>
                                        </td>
                                        <td>
                                            <label>
                                                <select class="ip sr1 st1 w5">
                                                    <option value="" selected>X</option>
                                                    <option value="">경도</option>
                                                    <option value="">위도</option>
                                                    <option value="">wkt 컬럼</option>
                                                </select>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td class="ft-st1">std_ym</td>
                                        <td class="td2">기준년월</td>
                                        <td class="align-l">설명영역~</td>
                                        <td>
                                            <button class="btn sr3 st6">없음</button>
                                        </td>
                                        <td>
                                            <button class="btn sr3 st3">사용</button>
                                        </td>
                                        <td>
                                            <label>
                                                <select class="ip sr1 st1 w5">
                                                    <option value="" selected>X</option>
                                                    <option value="">경도</option>
                                                    <option value="">위도</option>
                                                    <option value="">wkt 컬럼</option>
                                                </select>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td class="ft-st1">std_ym</td>
                                        <td class="td2">기준년월</td>
                                        <td class="align-l">설명영역~</td>
                                        <td>
                                            <button class="btn sr3 st6">없음</button>
                                        </td>
                                        <td>
                                            <button class="btn sr3 st3">사용</button>
                                        </td>
                                        <td>
                                            <label>
                                                <select class="ip sr1 st1 w5">
                                                    <option value="" selected>X</option>
                                                    <option value="">경도</option>
                                                    <option value="">위도</option>
                                                    <option value="">wkt 컬럼</option>
                                                </select>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td class="ft-st1">std_ym</td>
                                        <td class="td2">기준년월</td>
                                        <td class="align-l">설명영역~</td>
                                        <td>
                                            <button class="btn sr3 st6">없음</button>
                                        </td>
                                        <td>
                                            <button class="btn sr3 st3">사용</button>
                                        </td>
                                        <td>
                                            <label>
                                                <select class="ip sr1 st1 w5">
                                                    <option value="" selected>X</option>
                                                    <option value="">경도</option>
                                                    <option value="">위도</option>
                                                    <option value="">wkt 컬럼</option>
                                                </select>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td class="ft-st1">std_ym</td>
                                        <td class="td2">기준년월</td>
                                        <td class="align-l">설명영역~</td>
                                        <td>
                                            <button class="btn sr3 st6">없음</button>
                                        </td>
                                        <td>
                                            <button class="btn sr3 st3">사용</button>
                                        </td>
                                        <td>
                                            <label>
                                                <select class="ip sr1 st1 w5">
                                                    <option value="" selected>X</option>
                                                    <option value="">경도</option>
                                                    <option value="">위도</option>
                                                    <option value="">wkt 컬럼</option>
                                                </select>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td class="ft-st1">std_ym</td>
                                        <td class="td2">기준년월</td>
                                        <td class="align-l">설명영역~</td>
                                        <td>
                                            <button class="btn sr3 st7">있음</button>
                                        </td>
                                        <td>
                                            <button class="btn sr3 st3">사용</button>
                                        </td>
                                        <td>
                                            <label>
                                                <select class="ip sr1 st1 w5">
                                                    <option value="" selected>X</option>
                                                    <option value="">경도</option>
                                                    <option value="">위도</option>
                                                    <option value="">wkt 컬럼</option>
                                                </select>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td class="ft-st1">std_ym</td>
                                        <td class="td2">기준년월</td>
                                        <td class="align-l">설명영역~</td>
                                        <td>
                                            <button class="btn sr3 st6">없음</button>
                                        </td>
                                        <td>
                                            <button class="btn sr3 st3">사용</button>
                                        </td>
                                        <td>
                                            <label>
                                                <select class="ip sr1 st1 w5">
                                                    <option value="" selected>X</option>
                                                    <option value="">경도</option>
                                                    <option value="">위도</option>
                                                    <option value="">wkt 컬럼</option>
                                                </select>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td class="ft-st1">std_ym</td>
                                        <td class="td2">기준년월</td>
                                        <td class="align-l">설명영역~</td>
                                        <td>
                                            <button class="btn sr3 st7">있음</button>
                                        </td>
                                        <td>
                                            <button class="btn sr3 st3">사용</button>
                                        </td>
                                        <td>
                                            <label>
                                                <select class="ip sr1 st1 w5">
                                                    <option value="" selected>X</option>
                                                    <option value="">경도</option>
                                                    <option value="">위도</option>
                                                    <option value="">wkt 컬럼</option>
                                                </select>
                                            </label>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        
                    </div>
                    <!-- //컨텐츠 영역 -->
                    
                </div>

<jsp:include page="../../layout/bottom.jsp" />
                