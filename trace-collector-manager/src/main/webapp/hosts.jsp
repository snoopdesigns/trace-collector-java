<%@page import="java.util.Collection"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="com.emc.traceloader.db.DatabaseUtils"%>
<%@page import="com.emc.traceloader.db.entity.Host"%>
<%@page import="com.emc.traceloader.auth.SessionParameters"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>Trace Collector Manager</title>
		<link rel="stylesheet" href="css/960.css" type="text/css" media="screen" charset="utf-8" />
		<link rel="stylesheet" href="css/template.css" type="text/css" media="screen" charset="utf-8" />
		<link rel="stylesheet" href="css/colour.css" type="text/css" media="screen" charset="utf-8" />
	</head>

    <%
        DatabaseUtils dbUtils = (DatabaseUtils)pageContext.getServletContext().getAttribute(DatabaseUtils.class.getName());
        String session_id = (String)session.getAttribute(SessionParameters.SESSION_ID_PARAM);
        String user_id = (String)session.getAttribute(SessionParameters.USER_ID_PARAM);
        if(null == session_id) {
            response.sendRedirect("login.jsp");
        }
    %>

    <script>
        function deleteHost(id) {
            var xmlHttp = null;
            xmlHttp = new XMLHttpRequest();
            xmlHttp.open( "GET", "/db?id=" + id, false);
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
								<th>URL context</th>
								<th colspan="2" width="10%">Actions</th>
							</tr>
						</thead>
						<tbody>
						    <%  for(Host host : dbUtils.getAllHosts(user_id)) { %>
                                <tr>
                                    <td><%=host.getIp()%></td>
                                    <td><%=host.getPort()%></td>
                                    <td><%=host.getUrlContext()%></td>
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