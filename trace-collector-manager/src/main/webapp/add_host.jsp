<%@page import="java.util.Collection"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="com.emc.traceloader.db.DatabaseUtils"%>
<%@page import="com.emc.traceloader.db.entity.Host"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>Trace Collector Manager</title>
		<link rel="stylesheet" href="css/960.css" type="text/css" media="screen" charset="utf-8" />
		<link rel="stylesheet" href="css/template.css" type="text/css" media="screen" charset="utf-8" />
		<link rel="stylesheet" href="css/colour.css" type="text/css" media="screen" charset="utf-8" />
	</head>

    <%
        boolean entryAddedSuccess = false;
        DatabaseUtils dbUtils = (DatabaseUtils)pageContext.getServletContext().getAttribute(DatabaseUtils.class.getName());
        Map req_params = request.getParameterMap();
        if (req_params.size() > 1) {
            dbUtils.addHost(new Host(request.getParameter("ip_address"), request.getParameter("port"), request.getParameter("url_context")));
            entryAddedSuccess = true;
        }
    %>

	<body>
	<h1 id="head">Trace Collector Manager Application</h1>
        <ul id="navigation">
        	<li><a href="index.jsp">Main</a></li>
            <li><a href="hosts.jsp">Hosts</a></li>
            <li><span class="active">Add new host</span></li>
        </ul>
			<div id="content" class="container_16 clearfix">
				<form>
				<div class="grid_16">
					<h2>Add new host</h2>
					<% if(entryAddedSuccess) { %>
					<p class="success">New host successfully added.</p>
					<% } %>
				</div>

				<div class="grid_5">
				    <div class="box">
                        <p>
                            <label for="title">IP <small>IP address of host machine.</small></label>
                            <input type="text" name="ip_address" />
                        </p>
                        <p>
                            <label for="title">Port <small>Port of Trace Loader Unit application.</small></label>
                            <input type="text" name="port" />
                        </p>
                        <p>
                            <label for="title">URL context <small>URL context.</small></label>
                            <input type="text" name="url_context" />
                        </p>
					</div>
				</div>
				<div class="grid_16">
					<p class="submit">
						<input type="reset" value="Reset" />
						<input type="submit" value="Add" />
					</p>
				</div>
				</form>
			</div>
		
		<div id="foot">
		    <a href="#">Contact Me</a>
		</div>
	</body>
</html>