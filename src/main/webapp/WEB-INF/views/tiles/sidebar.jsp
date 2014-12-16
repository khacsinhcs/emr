<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>

<!-- Page Sidebar -->
<div class="page-sidebar" id="sidebar">
	<!-- Sidebar Menu -->
	<ul class="nav sidebar-menu">
		<li><a href="${pageContext.request.contextPath}/home.html"> <i
				class="menu-icon glyphicon glyphicon-home"></i> <span
				class="menu-text">Home</span>
		</a></li>
		<li><a href="${pageContext.request.contextPath}/ner/"> <i
				class="menu-icon glyphicon glyphicon-play"></i> <span
				class="menu-text">Clinical NER</span>
		</a></li>
		<li><a href="${pageContext.request.contextPath}/tool/"> <i
				class="menu-icon glyphicon glyphicon-tasks"></i> <span
				class="menu-text">Annotation Tool</span>
		</a></li>
		<li><a href="${pageContext.request.contextPath}/recordvn/"> <i
				class="menu-icon fa fa-table"></i> <span
				class="menu-text">Record VN</span>
		</a></li>
		<li><a href="${pageContext.request.contextPath}/recorden/"> <i
				class="menu-icon fa fa-table"></i> <span
				class="menu-text">Record EN</span>
		</a></li>
		<%-- <li><a href="#" class="menu-dropdown"> <i
				class="menu-icon fa fa-table"></i> <span class="menu-text">
					i2b2 data </span> <b class="menu-expand"></b>
		</a>

			<ul class="submenu">
				<li><a href="${pageContext.request.contextPath}/i2b2/record/">
						<span class="menu-text">Record upload</span>
				</a></li>
				<li><a href="${pageContext.request.contextPath}/i2b2/ibo/">
						<span class="menu-text">Concepts upload</span>
				</a></li>

		</ul></li> --%>
		<li><a href="${pageContext.request.contextPath}/about.html"> <i
				class="menu-icon fa fa-phone-square"></i> <span class="menu-text">
					About Us </span>
		</a></li>
		<!--Setting-->
		<li style="display: none;"><a href="#" class="menu-dropdown">
				<i class="menu-icon fa fa-align-right"></i> <span class="menu-text">
					Right to Left </span> <b class="menu-expand"></b>
		</a>
			<ul class="submenu">
				<li><a> <span class="menu-text">RTL</span> <label
						class="pull-right margin-top-10"> <input id="rtl-changer"
							class="checkbox-slider slider-icon colored-primary"
							type="checkbox"> <span class="text"></span>
					</label>
				</a></li>
				<li><a href="${pageContext.request.contextPath}/i2b2/record/">
						<span class="menu-text">Arabic Layout</span>
				</a></li>

				<li><a href="${pageContext.request.contextPath}/i2b2/ibo/">
						<span class="menu-text">Persian Layout</span>
				</a></li>
			</ul></li>
	</ul>
	<!-- /Sidebar Menu -->
	<!-- Sidebar Collapse -->
	<div class="sidebar-collapse" id="sidebar-collapse">
		<i class="collapse-icon"></i>
	</div>
	<!-- /Sidebar Collapse -->

</div>
<!-- /Page Sidebar -->
</html>