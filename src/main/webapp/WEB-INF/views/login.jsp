<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>EMR-IE Login</title>
		<meta name="generator" content="Bootply" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<link href="${pageContext.request.contextPath}/resources/css/login2.css" rel="stylesheet">
	</head>
	<body>
<!--login modal-->
<div id="loginPopupModal" class="modal show" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
  <div class="modal-content">
      <div class="modal-header">
<!--           <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> -->
          <span id="loginTitle">Login</span>
      </div>
      <div class="modal-body">
 
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
      
          <form n class="form col-md-12 center-block" action="<c:url value='/j_spring_security_check' />" method="POST">
            <div class="form-group">
              <input name="username" id="username" type="text" class="form-control input-lg" placeholder="Username">
            </div>
            <div class="form-group">
              <input name="password" id="password" type="password" class="form-control input-lg" placeholder="Password">
            </div>
            <div class="form-group">
<!--               <button class="btn btn-primary btn-lg btn-block">Sign In</button> -->
			  <input class="btn btn-primary btn-lg btn-block" type="submit" name="signIn" value="Sign In"/>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          </form>
          
      </div>
      <div class="modal-footer">
<!--           <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button> -->
              <span id="linkRegister"><a href="#">Register</a></span>
              <span id="linkNeedHelop"><a href="https://www.facebook.com/diep12892">Need help?</a></span>
      </div>
  </div>
  </div>
</div>
	<!-- script references -->
		<script src="${pageContext.request.contextPath}/resources/js/jquery-2.0.3.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/login2.js"></script>
</body>
</html>