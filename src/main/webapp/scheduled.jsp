<!doctype html>
<html lang="en">
<%@ include file="head.jsp"%>
<body role="document">
	<%@ include file="nav.jsp"%>
	<div class="container theme-showcase" role="main">
		
		<div class="homerow">
			<div class="page-header">
			<h1>Submit New Scheduled Job</h1>
			</div>
			<div class="alert alert-success alert-dismissible" role="alert"
					id="sualert">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<strong>Succeed!</strong> you have submited a new scheduled job to download
					and analysis log
				</div>
				<div class="alert alert-danger alert-dismissible" role="alert"
					id="failalert">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<strong>Failed!</strong> Oh snap! job already exists,change a few things up and try submitting again.
				</div>
			<div class="col-xs-2">
					JOB GROUP <select class="form-control servertype"
						placeholder=".col-xs-2" id="servertype">
						<option></option>
						<option>TSP</option>
						<option>CMS</option>
						<option>TAHOE</option>
						<option>MMP</option>
					</select>
				</div>
				<div class="col-xs-2">
					POOL NAME <select class="form-control poollist"
						placeholder="col-xs-2" id="poollist">
					</select>
				</div>
				<div class="col-xs-2">
				JOB NAME <input type="text" class="form-control jobname" placeholder="Job Name" id="jobname">
				</div>
				<div class="col-xs-2">
					SCHEDULE TIME <input type="text" class="form-control scheduletime" placeholder="schedule time" id="scheduletime">
				</div>
				<div class="col-xs-2">
					JOB DESCRIPTION
					<textarea class="form-control jobdesc" rows="3" id="jobdesc" ></textarea>
				</div>
				<div class="col-xs-2">
					<button type='submit' class='btn btn-sm btn-danger' id='submit'>Submit</button>
				</div>
				<div class="operartionrow">
					<div class="page-header2">
					<h1> Scheduled Job Operation</h1><button type="submit" class="btn btn-sm btn-info" id="querys">Query</button>
					</div>
					<table class="table table-striped">
					<thead>
						<tr>
							<th>Job Id</th>
							<th>Job Group</th>
							<th>Pool Name</th>
							<th>Schedule Time</th>
							<th>Job Description</th>
							<th>Job Status</th>
							<th>Next Fire Time</th>
							<th>#</th>
						</tr>
					</thead>
					<tbody class="scheduledJob">
					</tbody>
				</table>
				</div>
		 </div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>