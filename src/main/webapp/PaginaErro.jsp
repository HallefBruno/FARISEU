<%-- 
    Document   : PaginaErro
    Created on : 04/12/2018, 08:22:49
    Author     : hallef.wantek
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fariseu-Página de erro</title>
	<%@include file="WEB-INF/includes_bootstrap4/IncludesLinksScripts.jsp"%>
    </head>
    
    <body>
        <div class="card text-center ">
            <div class="card-header">
                Fariseu-Página de erro.
            </div>
            <div class="card-body alert-danger">
                <h5 class="card-title">Um erro ocorreu!</h5>
                <p class="card-text"><%= exception.getMessage()%></p>
            </div>
            <div class="card-footer text-muted">
                <% 
                    String data = new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
                %>
                <p><%= data%></p>
            </div>
        </div>
    </body>
</html>
