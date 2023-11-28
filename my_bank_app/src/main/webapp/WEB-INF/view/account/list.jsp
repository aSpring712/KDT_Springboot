<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/layout/header.jsp"%>
<!-- 반복문 돌기기 위한 태그 라이브러리 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<div class="col-sm-8">
			<h2>나의 계좌 목록</h2>
			<!-- 로그인 한 사람만 들어올 수 있고, 자신의 계좌 목록만 뿌려주어야 함 -->
			<h5>어서오세요</h5>
			<div class="bg-light p-md-5 h-75">
				<c:choose>
					<c:when test="${accountList != null}">
		
						<table class="table">
							<thead>
								<tr>
									<th>계좌 번호</th>
									<th>잔액</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="account" items="${ accountList }">
									<tr>
									
										<td><a href="/account/detail/${account.id}">${ account.number }</a></td>
										<td>${ account.formatBalance() }</td>
									</tr>
								</c:forEach>
		
							</tbody>
						</table>
		
					</c:when>
					<c:otherwise>
						<p>아직 생성된 계좌가 없습니다</p>
					</c:otherwise>
				</c:choose>
		
			</div>
		
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/view/layout/footer.jsp"%>