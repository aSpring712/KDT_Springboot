<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <%@ include file="/WEB-INF/view/layout/header.jsp" %>
  
  	<!-- col-sm-8 : bootstrap이 12등분을 해두었는데 그 중 이 부분이 8등분을 차지하라는 뜻 -->
  	<div class="col-sm-8">
      <h2>회원 가입</h2>
      <h5>어서오세요</h5>
      <div>
      	<!-- 처리하는 녀석 -->
      	<!-- 기본 Form MIME type : enctype="application/x-www-form-urlencoded" -->
      	<!-- 파일 전송을 위해 enctype="multipart/form-data" -->
    	<form action="/user/sign-up" method="post" enctype="multipart/form-data">
		  <div class="form-group">
		    <label for="username">username:</label>
		    <!-- spring : key value 파싱 전략 -> name 반드시 있어야 함 -->
		    <input type="text" class="form-control" 
		    	placeholder="Enter username" id="username" name="username">
		  </div>
		  <div class="form-group">
		    <label for="pwd">password:</label>
		    <input type="password" class="form-control" 
		    	placeholder="Enter password" id="pwd" name="password">
		  </div>
		  <div class="form-group">
		    <label for="fullname">fullname:</label>
		    <input type="text" class="form-control" 
		    	placeholder="Enter fullname" id="pwd" name="fullname">
		  </div>
		  
		    <div class="custom-file">
			    <input type="file" class="custom-file-input" id="customFile" name="file">
			    <label class="custom-file-label" for="customFile">Choose file</label>
		  	</div>
		  
		  <button type="submit" class="btn btn-primary">회원가입</button>
		</form>
		
      </div>
    
    </div>
  </div>
</div>

<script>
// Add the following code if you want the name of the file appear on select
$(".custom-file-input").on("change", function() {
  var fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});
</script>

  
<%@ include file="/WEB-INF/view/layout/footer.jsp" %>