<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-12 col-md-12">
		<div class="widget-box">
			<div class="widget-header bg-palegreen">
				<i class="widget-icon fa fa-arrow-down"></i>
				<h5 class="widget-caption">List Record</h5>
				<div class="widget-toolbar">
					<a href="#" data-action="maximize"> <i class="fa fa-expand"></i>
					</a> <a href="#" data-action="collapse"> <i class="fa fa-minus"></i>
					</a>
				</div>
				<!--Widget Toolbar-->
			</div>
			<!--Widget Header-->
			<div class="widget-body">
				<div class="widget-main">
					<table class="table">
						<thead>
							<tr>
								<th>id</th>
								<th>Name</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="record" items="${listRecord}">
								<tr>
									<td>${recordId}</td>
									<td><a
										href="${pageContext.request.contextPath}/extract/record?id=${record.id}">${record.name}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
