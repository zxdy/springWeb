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
			$.post("rs/api/", {
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
		
	});
  
</script>
</head>