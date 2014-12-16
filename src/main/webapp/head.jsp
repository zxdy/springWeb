<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>Index</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="css/bootstrap-theme.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/theme.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="js/ie-emulation-modes-warning.js"></script>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-2.0.0.min.js "></script>
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<script>
  $(document).ready(function() {
		//$("#start").datepicker();  
		$("#datepicker" ).datepicker();
		$('#sualert').hide();
		$('#failalert').hide();
		$("#loading").hide();
		$("#searchfailed").click(function() {
			$(".result").html("");
			$.post("rs/api/reprocess/1.0/searchfailed", {
				logDate : $(".startDate").val()
			}, function(data) {
				$.each(data,function(i,item){
					$(".result").append(
							"<table class='table table-striped' id="+item.SESSIONID+">"+
							"<thead>"+
								"<tr>"+
									"<th>SessionId</th>"+
									"<th>Last Failed@</th>"+
									"<th>Pool Name</th>"+
									"<th>Server List</th>"+
									"<th>Failed Reason<button type='submit' class='btn btn-sm btn-danger resubmit' id='resubmit"+item.SESSIONID+"' onClick=resbumitFailed('"+item.SESSIONID+"')>Resubmit</button></th></tr></thead><tbody>"
							);
					$.each(item.SERVERLIST, function(i, server){    
						$('#'+item.SESSIONID+' tbody').append(
								"<tr id='job"+i+"'>"+
								"<td>" + item.SESSIONID+ "</td>" + 
				                 "<td>" + item.TIMESTAMP + "</td>"+
				                 "<td>" + item.POOL    + "</td>" +
				                 "<td>" + server.SERVERNAME + "</td>" +
				                 "<td>" + server.DESCRIPTION + "</td>"+
				                 "</tr>");
						}); 
					$(".result").append("</tbody></table>");
				});
			},"json");
		});
		$("#servertype").change(function() {
			$(".poollist").html("<option></option>");
			$.post("rs/api/pool/1.0/list", {
				serverType : $(".servertype").val()
			}, function(data) {
				$.each(data,function(i,item){
					$(".poollist").append(
							"<option>" + item.POOL+ "</option>" );
				});
			},"json");
		});
		
		$("#process").click(function() {
			if($(".startDate").val()===''){
				alert("please choose log date");
			}else if($(".servertype").val()!=''){
			$(".jobtrack").html("");
			$("#loading").show();
			$.post("rs/api/reprocess/1.0/process", {
				serverType : $(".servertype").val(),
				poolName: $(".poollist").val(),
				logDate : $(".startDate").val()
			}, function(data) {
				$("#loading").hide();
				$('#sualert').show();
				$.each(data,function(i,item){
					$(".jobtrack").append(
					"<tr id='job"+i+"'>"+
					"<td>" + i+ "</td>" + 
					 "<td>" + item.SESSIONID+ "</td>" + 
	                 "<td>" + item.POOL    + "</td>" +
	                 "<td>" + item.LOGDATE + "</td>" +
	                 "<td>" + item.TIMESTAMP + "</td>"+
	                 "<td><button type='submit' class='btn btn-sm btn-danger' name='reprocess' onClick=reprocess('"+item.SESSIONID+"')>Reprocess</button></td>"+
	                 "</tr>");
				});
			},"json");
			}else{
				alert("please choose server type or pool name")
			}
		});
		$("#query").click(function() {
			$(".jobtrack").html("");
			$("#loading").show();
			$.post("rs/api/reprocess/1.0/query", {
				serverType : $(".servertype").val(),
				poolName: $(".poollist").val(),
				logDate : $(".startDate").val()
			}, function(data) {
				$("#loading").hide();
				$.each(data,function(i,item){
					$(".jobtrack").append(
					"<tr id='job"+i+"'>"+
					"<td>" + i+ "</td>" + 
					 "<td>" + item.SESSIONID+ "</td>" + 
	                 "<td>" + item.POOL    + "</td>" +
	                 "<td>" + item.LOGDATE + "</td>" +
	                 "<td>" + item.TIMESTAMP + "</td>"+
	                 "<td><button type='submit' class='btn btn-sm btn-danger' name='reprocess' onClick=reprocess('"+item.SESSIONID+"')>Reprocess</button></td>"+
	                 "</tr>");
				});
			},"json");
		});
		
		$("#querys").click(function() {
			$(".scheduledJob").html("");
			$("#loading").show();
			$.post("rs/api/process/1.0/querys", {
				serverType : $(".servertype").val(),
				poolName: $(".poollist").val(),
				logDate : $(".startDate").val()
			}, function(data) {
				$("#loading").hide();
				$.each(data,function(i,item){
					$(".scheduledJob").append(
					"<tr id='job"+i+"'>"+
					 "<td>" + item.JOBID+ "</td>" + 
	                 "<td>" + item.JOBGROUP    + "</td>" +
	                 "<td>" + item.POOL + "</td>" +
	                 "<td>" + item.CRON + "</td>"+
	                 "<td>" + item.JOBDEC + "</td>" +
	                 "<td>" + item.JOBSTATUS + "</td>"+
	                 "<td>" + item.NEXTFIRETIME + "</td>"+
	                 "<td>"+"<div class='btn-group'>"+
	                 "<button type='button' class='btn btn-default'>"+"edit</button>"+
	                 "<button type='button' class='btn btn-default' onClick=deleteJob("+item.JOBID+")>"+"delete</button>"+
	               	 "</div></td>"+
	                 "</tr>");
				});
			},"json");
		});
		
		$("#submit").click(function() {
			if($(".jobname").val()===''){
				alert("please set a jobName");
			}else if($(".jobGroup").val()===''){
				alert("please choose jobGroup");
			}else if($(".scheduleTime").val()===''){
				alert("please set the scheduled time");
			}else{
			$.post("rs/api/job/1.0/add", {
				jobName : $(".jobname").val(),
				jobGroup : $(".servertype").val(),
				poolName: $(".poollist").val(),
				scheduleTime : $(".scheduletime").val(),
				jobDesc : $(".jobdesc").val()
			}, function(data) {
				if(data){
					$('#sualert').show();
					hideByTime();
				}else{
					$('#failalert').show();
					hideByTime();
				}
			},"json");
			}
		});
	});
  
  function deleteJob(jobId){
	 	$(".scheduledJob").html("");
		$.post("rs/api/process/1.0/submited/delete", {
			jobId : jobId
		}, function(data) {
			$.each(data,function(i,item){
				$(".scheduledJob").append(
						"<tr id='job"+i+"'>"+
						 "<td>" + item.JOBID+ "</td>" + 
		                 "<td>" + item.JOBGROUP    + "</td>" +
		                 "<td>" + item.POOL + "</td>" +
		                 "<td>" + item.CRON + "</td>"+
		                 "<td>" + item.JOBDEC + "</td>" +
		                 "<td>" + item.JOBSTATUS + "</td>"+
		                 "<td>" + item.NEXTFIRETIME + "</td>"+
		                 "<td>"+"<div class='btn-group'>"+
		                 "<button type='button' class='btn btn-default'>"+"edit</button>"+
		                 "<button type='button' class='btn btn-default' onClick=deleteJob("+item.JOBID+")>"+"delete</button>"+
		               	 "</div></td>"+
		                 "</tr>");
			});
		},"json");
  }
  
  function reprocess(sessionId){
	 	$(".jobtrack").html("");
	 	$("#loading").show();
		$.post("rs/api/reprocess/1.0/submited/reprocess", {
			sessionId : sessionId
		}, function(data) {
			$('#sualert').show();
			$.each(data,function(i,item){
				$("#loading").hide();
				$(".jobtrack").append(
				"<tr id='job"+i+"'>"+
				"<td>" + i+ "</td>" + 
				 "<td>" + item.SESSIONID+ "</td>" + 
                 "<td>" + item.POOL    + "</td>" +
                 "<td>" + item.LOGDATE + "</td>" +
                 "<td>" + item.TIMESTAMP + "</td>"+
                 "<td><button type='submit' class='btn btn-sm btn-danger' name='reprocess' onClick=reprocess('"+item.SESSIONID+"')>Reprocess</button></td>"+
                 "</tr>");
			});
		},"json");
	}
  	function resbumitFailed(sessionId){
  		$.post("rs/api/reprocess/1.0/resubmit/failedjob", {
			sessionId : sessionId
		}, function(data) {
			alert("Succeed! You have resubmit the failed job: " +sessionId);
		},"json");
  	}
  	function hideByTime(){
  	    setTimeout("divHide()",2000);
  	}
  	function divHide(){
		$('#sualert').hide();
		$('#failalert').hide();
  	}
</script>
</head>