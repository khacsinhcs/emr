<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${pageContext.request.userPrincipal.name != null}">
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<input id="hasRoleAdmin" type="hidden" value="admin">
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
		<input id="hasRoleUser" type="hidden" value="user">
	</sec:authorize>
</c:if>
<!-- Navbar -->
    <div class="navbar">
        <div class="navbar-inner">
            <div class="navbar-container">
                <!-- Navbar Barnd -->
                <div class="navbar-header pull-left">
                    <a href="#" class="navbar-brand">
                        <small>
                            <img src="${pageContext.request.contextPath}/resources/images/emr-ie.png" alt="" />
                        </small>
                    </a>
                </div>
                <!-- /Navbar Barnd -->
                <!-- Account Area and Settings --->
                <div class="navbar-header pull-right">
                    <div class="navbar-account">
                        <ul class="account-area">
                        	<c:if test="${pageContext.request.userPrincipal.name != null}">
	                            <li>
	                             	<span style="color: white; font-size: 25px">${pageContext.request.userPrincipal.name}</span>
	                             	<input id="loggedUserId" type="hidden" value="${pageContext.request.userPrincipal.name}">
	                            </li>
	                            <li>
									<a id="linkLogout" style="font-size: 15px; top: 7px; font-weight: bold" href="javascript:formSubmit()">&nbsp;&nbsp;&nbsp;&nbsp;Logout</a>
	                            </li>
                            </c:if>
                            <!-- /Account Area -->
                            <!--Note: notice that setting div must start right after account area list.
                            no space must be between these elements-->
                            <!-- Settings -->
                         </ul>
                        <!--<div class="setting">
                            <a id="btn-setting" title="Setting" href="#">
                                <i class="icon glyphicon glyphicon-cog"></i>
                            </a>
                        </div><div class="setting-container">
                            <label>
                                <input type="checkbox" id="checkbox_fixednavbar">
                                <span class="text">Fixed Navbar</span>
                            </label>
                            <label>
                                <input type="checkbox" id="checkbox_fixedsidebar">
                                <span class="text">Fixed SideBar</span>
                            </label>
                            <label>
                                <input type="checkbox" id="checkbox_fixedbreadcrumbs">
                                <span class="text">Fixed BreadCrumbs</span>
                            </label>
                            <label>
                                <input type="checkbox" id="checkbox_fixedheader">
                                <span class="text">Fixed Header</span>
                            </label>
                        </div> -->
                        <!-- Settings -->
                    </div>
                </div>
                <!-- /Account Area and Settings -->
            </div>
        </div>
    </div>
    <!-- /Navbar -->