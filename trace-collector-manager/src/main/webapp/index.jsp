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
		<script src="js/glow/1.7.0/core/core.js" type="text/javascript"></script>
		<script src="js/glow/1.7.0/widgets/widgets.js" type="text/javascript"></script>
		<link href="js/glow/1.7.0/widgets/widgets.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript">
			glow.ready(function(){
				new glow.widgets.Sortable(
					'#content .grid_5, #content .grid_6, #content .grid_11',
					{
						draggableOptions : {
							handle : 'h2'
						}
					}
				);
			});
		</script>
	</head>

	<%
        DatabaseUtils dbUtils = (DatabaseUtils)pageContext.getServletContext().getAttribute(DatabaseUtils.class.getName());
    %>

	<body>

		<h1 id="head">Trace Collector Manager Application</h1>
		
		<ul id="navigation">
			<li><span class="active">Main</span></li>
			<li><a href="hosts.jsp">Hosts</a></li>
			<li><a href="add_host.jsp">Add new host</a></li>
		</ul>

			<div id="content" class="container_16 clearfix">
				<div class="grid_5">
					<div class="box">
						<h2>Elizaveta</h2>
						<div class="utils">
							<a href="#">View More</a>
						</div>
						<p><strong>Last Signed In : </strong> Wed 11 Nov, 7:31<br /><strong>IP Address : </strong> 192.168.1.101</p>
					</div>
					<div class="box">
						<h2>System Status</h2>
						<div class="utils">
							<a href="#">View More</a>
						</div>
						<p class="center">OPERATIONAL</p>
					</div>
                    <div class="box">
                        <h2>Controls</h2>
                        <div class="utils">
                            <a href="#">Advanced</a>
                        </div>
                        <form action="#" method="post">
                            <p>
                                <label for="title">Send politic</label>
                                <input type="text" name="send_politic" />
                            </p>
                            <p>
                                <label for="post">Send interval</label>
                                <textarea name="send_interval"></textarea>
                            </p>
                            <p>
                                <input type="submit" value="Start" />
                                <input type="submit" value="Stop" />
                                <input type="submit" value="Send" />
                            </p>
                        </form>
                    </div>
				</div>
				<div class="grid_11">
                    <div class="box">
                        <h2>Hosts</h2>
                        <div class="utils">
                            <a href="#">View More</a>
                        </div>
                        <table>
                            <thead>
                                <tr>
                            	    <th>IP address</th>
                            		<th>Port</th>
                            	</tr>
                            </thead>
                            <tbody>
                            <%  for(Host host : dbUtils.getAllHosts()) { %>
                                <tr>
                                    <td><%=host.getIp()%></td>
                                    <td><%=host.getPort()%></td>
                                </tr>
                            <%  }  %>
                            </tbody>
                        </table>
                        <form action="add_host.jsp">
                            <input type="submit" value="Add new" />
                        </form>
                    </div>
				</div>
			</div>
		<div id="foot">
			<div class="container_16 clearfix">
				<div class="grid_16">
					<a href="#">Contact Me</a>
				</div>
			</div>
		</div>
	</body>
</html>