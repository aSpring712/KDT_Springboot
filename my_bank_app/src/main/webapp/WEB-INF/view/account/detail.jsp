<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/layout/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	.user--box{
		border: 1px solid black;
		padding: 10px;
	}
</style>
		<div class="col-sm-8">
			<h2>계좌 상세 보기(인증)</h2>
			<h5>어서오세요</h5>
			<div class="bg-light p-md-5 h-75">
				<div class="user--box">
					${principal.username} 님 계좌<br>
					계좌 번호 : ${account.number}<br>
					잔액 : ${account.formatBalance()}
				</div>
				
				<br>
				
				<div>
					<!-- 같은 컨트롤러를 타서 다른 값을 들고올 것 -->
					<a href="/account/detail/${account.id}">전체조회</a>&nbsp;
					<a href="/account/detail/${account.id}?type=deposit">입금조회</a>&nbsp;
					<a href="/account/detail/${account.id}?type=withdraw">출금조회</a>&nbsp;	
				</div>
				
				<br>
				
				<c:choose>
					<c:when test="${historyList != null}">
					
						<table class="table">
							<thead>
								<tr>
									<th>날짜</th>
									<th>보낸이</th>
									<th>받은이</th>
									<th>입출금 금액</th>
									<th>계좌 잔액</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="history" items="${historyList}">
									<tr>
										<td>${history.formatCreatedAt()}</td>
										<td>${history.sender}</td>
										<td>${history.receiver}</td>
										<td>${history.formatBalance()}</td>
										<td>${history.formatBalance()}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
					</c:when>
					<c:otherwise>
						<p>거래 내역이 없습니다.</p>
					</c:otherwise>
				</c:choose>
			</div>
		
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
