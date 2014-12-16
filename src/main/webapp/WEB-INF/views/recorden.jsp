<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/recorden.css">
<div class="row">
	<div class="col-xs-12 col-md-8">
		<div class="widget-box">
			<div class="widget-header">
				<i class="widget-icon fa fa-arrow-down"></i>
				<h5 class="widget-caption">Record</h5>
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
					<form method="post">
						<div class="form-group">
							  <!-- <button type="button" class="btn btn-danger btnClassConcept" id="btnAssignClassProblem" 
							  data-toggle="tooltip" data-placement="top" title="Những vấn đề bệnh nhân gặp phải">Problem</button>
							  <button type="button" class="btn btn-success btnClassConcept" id="btnAssignClassTest" 
							  data-toggle="tooltip" data-placement="top" title="Những xét nghiệm, khám và kiểm tra của bác sĩ">Test</button>
							  <button type="button" class="btn btn-primary btnClassConcept" id="btnAssignClassTreatment" 
							  data-toggle="tooltip" data-placement="top" title="Những điều trị, thuốc, phẫu thuật dành cho bệnh nhân">Treatment</button> -->
							  <label for="listDataSet">Data: </label>
							  <select id="listDataSet">
								<option value="train">Training</option>
								<option value="test">Testing</option>
							  </select>
							  <label for="listRecords">Record: </label>
							  <select id="listRecords"></select>
							  &nbsp;&nbsp;<span id="userAnnotatedRecordNum"></span> Records
						</div>
						<div id="clinicDataContent">
						</div>
					</form>
<!-- 					<button type="button" data-record-text="Loading New Record..." data-loading-text="Updating Concept..." class="disabled btn-blue btn-lg" id="btnUpdatetListConcept">Update</button> -->
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-4">
		<div class="widget-box">
			<div class="widget-header">
				<i class="widget-icon fa fa-arrow-down"></i>
				<h5 class="widget-caption">Concept</h5>
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
					<div class="panel panel-default">
						<div class="panel-heading">
					    	<h3 class="panel-title">
					    		<button type="button" class="btn btn-danger btnClassConcept">Problem</button>
					    	</h3>
					  	</div>
					  	<div class="panel-body" id="listClassProblemContainer">
				    		<span>
				    			Những bất thường về sức khỏe thân thể hay tinh thần của một bệnh nhân, 
				    			được mô tả bởi bệnh nhân hay quan sát của bác sĩ, y tá.
				    		</span>
					  	</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
					    	<h3 class="panel-title">
					    		<button type="button" class="btn btn-success btnClassConcept">Test</button>
					    	</h3>
					  	</div>
					  	<div class="panel-body" id="listClassTestContainer">
				    		<span>
				    			Những thủ tục y tế như xét nghiệm, đo đạc, 
				    			kiểm tra trên cơ thể bệnh nhân để cung cấp thêm thông tin cho “Problems” của bệnh nhân.
				    		</span>
					  	</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
					    	<h3 class="panel-title">
					    		<button type="button" class="btn btn-primary btnClassConcept">Treatment</button>
					    	</h3>
					  	</div>
					  	<div class="panel-body" id="listClassTreatmentContainer">
				    		<span>
				    			Những thủ tục y tế và quy trình áp dụng để đối phó với “Problems”, 
				    			bao gồm: thuốc, phẫu thuật và phương pháp điều trị.
				    		</span>
					  	</div>
					</div>
				</div>
			</div>
			<div>
			</div>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/resources/js/recorden.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/contextmenu/jquery.contextMenu.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/contextmenu/jquery.ui.position.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.contextMenu.css">
