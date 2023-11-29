<!-- JSP 문법 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  	<!-- jstl 사용을 위해 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!-- 이 페이지 랜더링 시 User Entity를 가지고 오라는 말 -->
<%@ page import="com.tenco.bankapp.repository.entity.User" %>
<!-- JSP 문법 -->
<!DOCTYPE html>
<html lang="en">
<head>
  <title>My Bank</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <!-- jQuery lib : ajax는 안될 것 -> 실무에서 다른것 사용하기 -->
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  <!-- 외부 스타일 시트 가져오기 -->
  <link rel="stylesheet" href="/css/style.css">
 
</head>
<body>

	<div class="jumbotron text-center banner--img" style="margin-bottom:0">
	  <h1>My Bank</h1>
	  <p>코린이 은행 관리 시스템 입니다</p> 
	</div>
	
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
	  <a class="navbar-brand" href="#">MENU</a>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	  <div class="collapse navbar-collapse" id="collapsibleNavbar">
	    <ul class="navbar-nav">
	      <li class="nav-item">
	        <a class="nav-link" href="#">Home</a>
	      </li>
	      
	      <!-- 세션 여부에 따라서 header 노출 다르게 처리 -->
	      <c:choose>
	      	<c:when test="${principal != null}">
			      <li class="nav-item">
			      	<!-- 로그아웃 -->
			        <a class="nav-link" href="/user/logout">logout</a>
			      </li>
		      </c:when>
		      <c:otherwise>
		      	<li class="nav-item">
			      	<!-- 로그인 -->
			        <a class="nav-link" href="/user/sign-in">SignIn</a>
			      </li>
			      <li class="nav-item">
			      	<!-- 회원가입 -->
			        <a class="nav-link" href="/user/sign-up">SignUp</a>
		      	</li>
		      </c:otherwise>
	      </c:choose>
	    </ul>
	  </div>  
	</nav>
	
	<div class="container" style="margin-top:30px">
	  <div class="row">
	    <div class="col-sm-4">
	      <h2>About Me</h2>
	      <h5>Photo of me:</h5>
	      <!-- picsum에서 가져온 이미지로 변경 -->
	      <div class="m--profile"></div>
	      <p>자라나는 코린이의 은행 관리 시스템</p>
	      <h3>Some Links</h3>
	      <p>Lorem ipsum dolor sit ame.</p>
	      <ul class="nav nav-pills flex-column">
	        <li class="nav-item">
	          <a class="nav-link active" href="/account/save">계좌생성</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="/account/list">계좌목록</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="/account/withdraw">출금</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="/account/deposit">입금</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="/account/transfer">이체</a>
	        </li>
	      </ul>
	      <hr class="d-sm-none">
	    </div>