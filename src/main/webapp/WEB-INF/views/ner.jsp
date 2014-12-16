<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row">
	<div class="col-xs-12 col-md-6">
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
							<label for="selectedLanguage">Language</label>
							<select id="selectedLanguage" name="selectedLanguage">
								<option value="en">English</option>
								<option value="vn">Vietnamese</option>
							</select>
							<!-- <span id="featuredContainer">
								<label>
	                                <input class="featuresGroup" type="checkbox" id="chkPOS" value="pos">
	                                <span class="text">POS</span>
                            	</label>
                            	<label>
	                                <input class="featuresGroup" type="checkbox" id="chkOrtho" value="ortho">
	                                <span class="text">Orthographic</span>
                            	</label>
							</span> -->
							<textarea class="form-control" id="textContent" name="textContent" rows="20"></textarea>
						</div>
						<button type="button" data-annotate-text="Annotating for Concept..." class="btn-blue btn-lg" id=btnSubmitTextContent>Submit</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6">
		<div class="widget-box">
			<div class="widget-header">
				<i class="widget-icon fa fa-arrow-down"></i>
				<h5 class="widget-caption">Result</h5>
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
				    <div class="form-group">
					    <button type="button" class="btn btn-danger btnClassConcept" id="btnAssignClassProblem" 
			              data-toggle="tooltip" data-placement="top" title="Những vấn đề bệnh nhân gặp phải">Problem</button>
			            <button type="button" class="btn btn-success btnClassConcept" id="btnAssignClassTest" 
			              data-toggle="tooltip" data-placement="top" title="Những xét nghiệm, khám và kiểm tra của bác sĩ">Test</button>
			            <button type="button" class="btn btn-primary btnClassConcept" id="btnAssignClassTreatment" 
			              data-toggle="tooltip" data-placement="top" title="Những điều trị, thuốc, phẫu thuật dành cho bệnh nhân">Treatment</button>
					</div>
					<div class="form-group" id="clinicDataContent">
					</div>
					<div id="contributionContainer" class="hideContent">
						<p id="contributionTitle">Your contribution will be used to improve concept annotation quality</p>
					    <button type="button" data-contribute-text="Contributing New Content..." class="btn-primary btn-lg" id=btnContributeContent>Contribute</button>
					    <button type="button" class="btn-default btn-lg" id=btnCloseContribution>Close</button>
				    </div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/contextmenu/jquery.contextMenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/contextmenu/jquery.ui.position.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ner.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.contextMenu.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/ner.css"/>