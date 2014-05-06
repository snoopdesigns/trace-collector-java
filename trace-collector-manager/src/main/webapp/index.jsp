<%@page import="java.util.Collection"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="com.emc.traceloader.db.DatabaseUtils"%>
<%@page import="com.emc.traceloader.db.entity.Host"%>
<html>
    <head>
        <title>Trace Collector manager:</title>
        <meta http-equiv="content-type" content="text/html;charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    </head>

    <%
        DatabaseUtils dbUtils = (DatabaseUtils)pageContext.getServletContext().getAttribute(DatabaseUtils.class.getName());
        System.out.println("From jsp: " + dbUtils.getAllHosts());
    %>

    <h2>Hosts:</h2>

    <table border="1" bgcolor="#ffcc00">
    <%  for(Host host : dbUtils.getAllHosts()) { %>
        <tr>
            <td><%=host.getIp()%></td>
            <td><%=host.getPort()%></td>
        </tr>
    <%  }  %>
    </table>

    <body>
        <td>
            <input type="text" name="details" value="">
        </td>
        <td align="center">
            <input type="button" name="choice" onclick="window.open('db.jsp','popuppage','width=200,toolbar=1,resizable=1,scrollbars=yes,height=150,top=100,left=100');" value="Add new host">
        </td>
        <form action="/mgr">
            <table>
                <tr>
                    <td>SEND_ADDRESS</td>
                    <td><input name="send_address" value="http://localhost:8080/unit"></td>
                </tr>
                <tr>
                    <td>CMD_TYPE</td>
                    <td><input name="cmd_type" value="START_COLLECTING"></td>
                </tr>
                <tr>
                     <td>SEND_POLITIC:</td>
                     <td><input name="send_politic" value="SEND_ON_END"></td>
                </tr>
                <tr>
                     <td>LOG_ENTITY_PER_MSG:</td>
                     <td><input name="log_entity_per_msg" value="100"></td>
                </tr>
                <tr>
                     <td>SEND_INTERVAL:</td>
                     <td><input name="send_interval" value="1000"></td>
                </tr>
                <tr>
                     <td>POSTBACK_IP:</td>
                     <td><input name="postback_ip" value="http://localhost:8080/unit"></td>
                </tr>
                <tr>
                     <td>REQUESTED_LUNS:</td>
                     <td><input name="requested_luns" value="1,2,3"></td>
                </tr>
            </table>
            <input type="submit" value="Save" title="Save" class="button" />
        </form>
    </body>
</html>
