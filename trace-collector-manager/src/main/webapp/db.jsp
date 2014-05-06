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
        Map req_params = request.getParameterMap();
                if (req_params.size() > 1) {
                %>
                        <h3>Host successfuly added: <%=request.getParameter("ip")%></h3>
                <%
                    dbUtils.addHost(new Host(request.getParameter("ip"), request.getParameter("port")));
                }

    %>
    </table>
    <body>
        <form>
            <table>
                <tr>
                    <td>HOST IP ADDRESS</td>
                    <td><input name="ip" value=""></td>
                </tr>
                <tr>
                    <td>CMD_TYPE</td>
                    <td><input name="port" value=""></td>
                </tr>
            </table>
            <input type="submit" value="Save" title="Save" class="button" />
        </form>
    </body>
</html>
