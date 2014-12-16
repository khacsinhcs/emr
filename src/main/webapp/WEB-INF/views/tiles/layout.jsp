<%@page import="com.molisys.framework.utils.DateTimeHelper"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta name="description" content="blank page" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/emr-ie.png"
	type="image/x-icon">

<!-- Basic style -->
<link
	href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/css/jquery-ui-1.10.4.custom.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/fileinput.min.css"
	rel="stylesheet" />
<link id="bootstrap-rtl-link" href="" rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/css/molisys.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/css/weather-icons.min.css"
	rel="stylesheet" />

<!--Fonts-->
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,600,700,300"
	rel="stylesheet" type="text/css">

<!--Beyond styles-->
<link id="beyond-link"
	href="${pageContext.request.contextPath}/resources/css/beyond.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/css/demo.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/css/typicons.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/css/animate.min.css"
	rel="stylesheet" />
<link id="skin-link"
	href="${pageContext.request.contextPath}/resources/css/skins/purple.min.css"
	rel="stylesheet" type="text/css" />

<!--Skin Script: Place this script in head to load scripts for skins and rtl support-->
<script type="text/javascript">
	var host = "${pageContext.request.contextPath}"
</script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-2.0.3.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.4.custom.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/skins.js"></script>
<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body>
	<!-- Loading Container -->
	<div class="loading-container">
		<div class="loading-progress">
			<div class="rotator">
				<div class="rotator">
					<div class="rotator colored">
						<div class="rotator">
							<div class="rotator colored">
								<div class="rotator colored"></div>
								<div class="rotator"></div>
							</div>
							<div class="rotator colored"></div>
						</div>
						<div class="rotator"></div>
					</div>
					<div class="rotator"></div>
				</div>
				<div class="rotator"></div>
			</div>
			<div class="rotator"></div>
		</div>
	</div>
	<!--  /Loading Container -->

	<tiles:insertAttribute name="navbar" />

	<!-- Main Container -->
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">
			<tiles:insertAttribute name="sidebar" />
			<!-- Page Content -->
			<div class="page-content">
				<!-- Page Breadcrumb -->
				<div class="page-breadcrumbs"></div>
				<!-- /Page Breadcrumb -->
				<!-- Page Header -->
				<div class="page-header position-relative">
					<div class="header-title">
						<h1 id="header-title-detail"></h1>
					</div>
					<!--Header Buttons-->
					<div class="header-buttons">
						<a class="sidebar-toggler" href="#"> <i class="fa fa-bars"></i>
						</a> <a class="refresh" id="refresh-toggler" href=""> <i
							class="glyphicon glyphicon-refresh"></i>
						</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
							class="glyphicon glyphicon-fullscreen"></i>
						</a>
					</div>
					<!--Header Buttons End-->
				</div>
				<!-- /Page Header -->
				<!-- Page Body -->
				<div class="page-body">
					<tiles:insertAttribute name="body" ignore="true" />
				</div>
				<!-- /Page Body -->
			</div>
			<!-- /Page Content -->
		</div>
		<!-- /Page Container -->
		<!-- Main Container -->
	</div>

	<!--Basic Scripts-->
	<script
		src="${pageContext.request.contextPath}/resources/js/common.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.timer.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/fileinput.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/molisys.js"></script>
	<!--Beyond Scripts-->
	<script src="${pageContext.request.contextPath}/resources/js/beyond.js"></script>

	<!--Page Related Scripts-->

	<!--Google Analytics::Demo Only-->
	<script>
		(function(i, s, o, g, r, a, m) {
			i['GoogleAnalyticsObject'] = r;
			i[r] = i[r] || function() {
				(i[r].q = i[r].q || []).push(arguments)
			}, i[r].l = 1 * new Date();
			a = s.createElement(o), m = s.getElementsByTagName(o)[0];
			a.async = 1;
			a.src = g;
			m.parentNode.insertBefore(a, m)
		})(window, document, 'script',
				'//www.google-analytics.com/analytics.js', 'ga');

		ga('create', 'UA-52103994-1',
				'beyondadmin.s3-website-us-west-2.amazonaws.com');
		ga('send', 'pageview');
		
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
</body>
</html>