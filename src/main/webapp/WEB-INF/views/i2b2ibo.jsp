<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-xs-12 col-md-6">
		<div class="widget-box">
			<div class="widget-header">
				<i class="widget-icon fa fa-arrow-down"></i>
				<h5 class="widget-caption">Submit one file</h5>
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
					<form action="${pageContext.request.contextPath}/i2b2/ibo/upload"
						enctype="multipart/form-data" method="post">
						<div class="form-group">
							<label for="image">Record</label> <input id="ibofile"
								name="ibofile" type="file" class="fileupload">
						</div>
						<button type="submit" class="btn btn-blue">Submit</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-md-6">
		<div class="widget-box">
			<div class="widget-header">
				<i class="widget-icon fa fa-arrow-down"></i>
				<h5 class="widget-caption">Submit one file</h5>
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
					<form action="${pageContext.request.contextPath}/i2b2/ibo/upload"
						enctype="multipart/form-data" method="post">
						<div class="form-group">
							<label for="image">Record</label> <input id="ibofile"
								name="ibofile" type="file" class="fileupload">
						</div>
						<div class="form-group">
							<label for="image">Record</label> <input id="ibofile"
								name="ibofile1" type="file" class="fileupload">
						</div>
						<div class="form-group">
							<label for="image">Record</label> <input id="ibofile"
								name="ibofile2" type="file" class="fileupload">
						</div>
						<div class="form-group">
							<label for="image">Record</label> <input id="ibofile"
								name="ibofile3" type="file" class="fileupload">
						</div>
						<div class="form-group">
							<label for="image">Record</label> <input id="ibofile"
								name="ibofile4" type="file" class="fileupload">
						</div>
						<button type="submit" class="btn btn-blue">Submit</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

