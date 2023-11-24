<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/layout/header.jsp" %>
  
  	<div class="col-sm-8">
      <h2>나의 계좌 목록</h2>
      <!-- 로그인 한 사람만 들어올 수 있고, 자신의 계좌 목록만 뿌려주어야 함 -->
      <h5>어서오세요</h5>
      <div class="bg-light p-md-5 h-75">
    	<table class="table">
    		<thead>
    			<tr>
    				<th>계좌 번호</th>
    				<th>잔액</th>
    			</tr>
    		</thead>
    		<tbody>
    		<!-- 일단 sample 값 -->
    			<tr>
	    			<!-- 계좌번호 -->	
    				<td>1111</td>
    				<!-- 잔액 -->
    				<td>1300</td>
    			</tr>
    			<tr>	
    				<td>2222</td>
    				<td>1000</td>
    			</tr>
    		</tbody>
    	</table>
		
      </div>
    
    </div>
  </div>
</div> 
  
<%@ include file="/WEB-INF/view/layout/footer.jsp" %>