<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localInclude.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<script type="text/javascript">	

/**
 * Updates the page controls based on the currently running task
 */
function updateTaskStatus() {
    $.ajax({
        url: 'status.form',
        dataType: 'json',
        success: function (data) {
        	var task = data.task;
			if (task == null) {
				$('#startbutton').removeAttr('disabled');
				$('#statusmsg').text('<spring:message code="pagecheck.check.ready" />');
			}
			else {			
				if (task.completed) {
					$('#startbutton').removeAttr('disabled');					
					$('#statusmsg').text('<spring:message code="pagecheck.check.finished" /> (' + task.checkedURLs + ' URLs checked)');	
	        	}
				else {
					$('#progressbar').progressbar("value", task.progress);
					$('#statusmsg').text(Math.floor(task.progress) + "%");
					$('#startbutton').attr('disabled', 'disabled');
				}
				
				$('#issues').empty();
				for (var i = 0; i < task.issues.length; ++i) {
					var issue = task.issues[i];
					$('#issues').append(issue.message + '<br />');
				}
			}

			setTimeout('updateTaskStatus()', 3000);
        }
    });
}

$(function() {
	$('#progressbar').progressbar();
	
	updateTaskStatus();
});
</script>

<b class="boxHeader"><spring:message code="pagecheck.check.title" /></b>
<form class="box" method="post">
	<table width="100%" border="0">
		<tr>
	    	<td style="font-weight: bold" width="300"><spring:message code="pagecheck.check.status" /></td>
	    	<td>
	    		<div id="progressbar" style="width: 400px; position: relative;">
	    			<div id="statusmsg" style="width: 400px; position: absolute; left: 0; top: 0; text-align: center; z-index: 10;"></div>
	    		</div>
	    	</td>
	    </tr>
	    <tr>
	    	<td style="font-weight: bold; vertical-align: top;" width="300"><spring:message code="pagecheck.check.issues" /></td>
	    	<td>
	    		<div id="issues" style="background-color: #EEE; font-style: italic"></div>
	    	</td>
	    </tr>
	</table>
	<input type="submit" id="startbutton" value="<spring:message code="pagecheck.check.start" />" />
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>