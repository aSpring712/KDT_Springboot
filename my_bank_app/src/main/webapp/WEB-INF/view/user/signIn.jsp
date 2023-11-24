<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <%@ include file="/WEB-INF/view/layout/header.jsp" %>
  
  	<div class="col-sm-8">
      <h2>로그인</h2>
      <!-- 자원의 요청은 원래 GET 방식으로 요청해야 함, 유저 정보를 조회하는 것이기 때문에
      민감한 정보(password)가 들어있는 로그인은 예외적으로 POST로 하는 것이 맞음, DOM에 남아서? -->
      <h5>어서오세요</h5>
      <div>
    	<form action="/user/sign-in" method="post">
		  <div class="form-group">
		    <label for="username">username:</label>
		    <input type="text" class="form-control" 
		    	placeholder="Enter username" id="username" name="username" value="길동">
		  </div>
		  <div class="form-group">
		    <label for="pwd">password:</label>
		    <input type="password" class="form-control" 
		    	placeholder="Enter password" id="pwd" name="password" value="1234">
		  </div>
		  <button type="submit" class="btn btn-primary">로그인</button>
		</form>
		
      </div>
    
    </div>
  </div>
</div>
  
<%@ include file="/WEB-INF/view/layout/footer.jsp" %>