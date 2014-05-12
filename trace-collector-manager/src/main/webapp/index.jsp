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

    <script>
        function startCollectingCmd() {
            var send_politic = document.getElementById("send_politic").value;
            var send_interval = document.getElementById("send_interval").value;
            var xmlHttp = null;
            xmlHttp = new XMLHttpRequest();
            xmlHttp.open( "GET", "/mgr?cmd_type=START_COLLECTING&postback_ip=<%=(String)session.getAttribute(SessionParameters.KEEPER_URL_PARAM)%>&send_interval="+
                send_interval+"&send_politic="+send_politic+"&log_entity_per_msg=10&requested_luns=1,2,3", false);
            xmlHttp.send( null );
            document.getElementById("success_msg").innerHTML = '<p class="success">START command success</p>';
        }

        function stopCollectingCmd() {
            var send_politic = document.getElementById("send_politic").value;
            var send_interval = document.getElementById("send_interval").value;
            var xmlHttp = null;
            xmlHttp = new XMLHttpRequest();
            xmlHttp.open( "GET", "/mgr?cmd_type=STOP_COLLECTING&postback_ip=<%=(String)session.getAttribute(SessionParameters.KEEPER_URL_PARAM)%>&send_interval="+
                send_interval+"&send_politic="+send_politic+"&log_entity_per_msg=10&requested_luns=1,2,3", false);
            xmlHttp.send( null );
            document.getElementById("success_msg").innerHTML = '<p class="success">STOP command success</p>';
                }

        function sendCmd() {
            var send_politic = document.getElementById("send_politic").value;
            var send_interval = document.getElementById("send_interval").value;
            var xmlHttp = null;
            xmlHttp = new XMLHttpRequest();
            xmlHttp.open( "GET", "/mgr?cmd_type=SEND&postback_ip=<%=(String)session.getAttribute(SessionParameters.KEEPER_URL_PARAM)%>&send_interval="+
            send_interval+"&send_politic="+send_politic+"&log_entity_per_msg=10&requested_luns=1,2,3", false);
            xmlHttp.send( null );
            document.getElementById("success_msg").innerHTML = '<p class="success">SEND command success</p>';
        }

        function handleClick(cb, id) {
            var xmlHttp = null;
            xmlHttp = new XMLHttpRequest();
            xmlHttp.open( "GET", "/db?action=select&id="+ id +"&selected="+cb.checked, false);
            xmlHttp.send( null );
        }
    </script>

	<%
        DatabaseUtils dbUtils = (DatabaseUtils)pageContext.getServletContext().getAttribute(DatabaseUtils.class.getName());
        String session_id = (String)session.getAttribute(SessionParameters.SESSION_ID_PARAM);
        String user_id = (String)session.getAttribute(SessionParameters.USER_ID_PARAM);
        if(null == session_id) {
            response.sendRedirect("login.jsp");
        }
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
						<h2><%=user_id%></h2>
						<div class="utils">
							<a href="/auth?logout">Logout</a>
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
                        <div id="success_msg">
                            <!--<p class="success">New host successfully added.</p>-->
                        </div>
                            <p>
                                <label for="title">Send politic</label>
                                <select id="send_politic">
                                    <option value="SEND_ON_END">Send traces on end</option>
                                	<option value="SEND_ON_END">Send traces continiously</option>
                                </select>
                            </p>
                            <p>
                                <label for="post">Send interval</label>
                                <input id="send_interval" type="text" name="send_interval" />
                            </p>
                            <p>
                                <input type="submit" value="Start" onclick="startCollectingCmd()"/>
                                <input type="submit" value="Stop" onclick="stopCollectingCmd()"/>
                                <input type="submit" value="Send" onclick="sendCmd()"/>
                            </p>
                    </div>
				</div>
				<div class="grid_11">
                    <div class="box">
                        <h2>Hosts</h2>
                        <div class="utils">
                            <a href="hosts.jsp">View More</a>
                        </div>
                        <table>
                            <thead>
                                <tr>
                                    <th>Select</th>
                            	    <th>IP address</th>
                            		<th>Port</th>
                            		<th>Status</th>
                            	</tr>
                            </thead>
                            <tbody>
                            <%  for(Host host : dbUtils.getAllHosts(user_id)) { %>
                                <tr>
                                    <td><input type="checkbox"
                                        onclick='handleClick(this,<%=host.getId()%>);'<%if(host.isSelected()){%>checked<%}%>/></td>
                                    <td><%=host.getIp()%></td>
                                    <td><%=host.getPort()%></td>
                                    <td><%=host.getStatus()%></td>
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