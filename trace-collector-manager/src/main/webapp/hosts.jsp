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
            dbUtils.addHost(new Host(request.getParameter("ip_address"), request.getParameter("port")));
            entryAddedSuccess = true;
        }
    %>

	<body>
	<h1 id="head">Trace Collector Manager Application</h1>
        <ul id="navigation">
        	<li><a href="index.jsp">Main</a></li>
            <li><span class="active">Hosts</span></li>
            <li><a href="hosts.jsp">Add new host</a></li>
        </ul>
			<div id="content" class="container_16 clearfix">
				<div class="grid_16">
					<table>
						<thead>
							<tr>
								<th>IP address</th>
								<th>Port</th>
								<th>URL context</th>
								<th colspan="2" width="10%">Actions</th>
							</tr>
						</thead>
						<tbody>
						    <%  for(Host host : dbUtils.getAllHosts()) { %>
                                <tr>
                                    <td><%=host.getIp()%></td>
                                    <td><%=host.getPort()%></td>
                                    <td>/unit</td>
                                    <td><a href="#" class="edit">Edit</a></td>
                                    <td><a href="#" class="delete">Delete</a></td>
                                </tr>
                            <%  }  %>
						</tbody>
					</table>
				</div>
			</div>
		
		<div id="foot">
					<a href="#">Contact Me</a>
				
		</div>
	</body>
</html>