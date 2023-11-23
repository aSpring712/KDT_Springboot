<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <!-- 반드시 세팅 해주어야 하는 태그 -->
    <%@ page isErrorPage='true' %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>에러 페이지</h1>
	<!-- statusCode, message는 어디서 넘겨 받도록 setting 되냐? MyPageExceptionHandler -->
	<p>에러 코드 : ${statusCode}</p>
	<p>에러 메시지 : ${message}</p>
</body>
</html>