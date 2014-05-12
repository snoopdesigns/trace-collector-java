<%@page import="java.util.Collection"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="com.emc.traceloader.service.db.DatabaseService"%>
<%@page import="com.emc.traceloader.service.sync.UnitSynchronizationService"%>
<%@page import="com.emc.traceloader.service.db.impl.entity.Host"%>
<%@page import="com.emc.traceloader.session.SessionParameters"%>
<%@page import="com.emc.traceloader.service.Services"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>Trace Collector Manager</title>
		<link rel="stylesheet" href="css/960.css" type="text/css" media="screen" charset="utf-8" />
		<link rel="stylesheet" href="css/template.css" type="text/css" media="screen" charset="utf-8" />
		<link rel="stylesheet" href="css/colour.css" type="text/css" media="screen" charset="utf-8" />
	</head>

    <%
        DatabaseService dbUtils = Services.databaseUtilsInstance(pageContext.getServletContext());
        String session_id = SessionParameters.sessionId(session);
        String user_id = SessionParameters.userId(session);
        if(null == session_id) {
            response.sendRedirect("login.jsp");
        }
    %>

    <script>
        function deleteHost(id) {
            var xmlHttp = null;
            xmlHttp = new XMLHttpRequest();
            xmlHttp.open( "GET", "/db?action=delete&id=" + id, false);
            xmlHttp.send( null );
            location.reload();
        }
    </script>

	<body>
	<h1 id="head">Trace Collector Manager Application</h1>
        <ul id="navigation">
        	<li><a href="index.jsp">Main</a></li>
            <li><span class="active">Hosts</span></li>
            <li><a href="add_host.jsp">Add new host</a></li>
        </ul>
			<div id="content" class="container_16 clearfix">
				<div class="grid_16">
					<table>
						<thead>
							<tr>
								<th>IP address</th>
								<th>Port</th>
								<th>Status</th>
								<th colspan="2" width="10%">Actions</th>
							</tr>
						</thead>
						<tbody>
						    <%  for(Host host : dbUtils.getAllHosts(user_id)) { %>
                                <tr>
                                    <td><%=host.getIp()%></td>
                                    <td><%=host.getPort()%></td>
                                    <td><%=host.getStatus()%></td>
                                    <td><a onclick="deleteHost(<%=host.getId()%>)" href="#" class="delete">Delete</a></td>
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