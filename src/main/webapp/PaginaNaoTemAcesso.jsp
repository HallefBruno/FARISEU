<%-- 
    Document   : PaginaNaoTemAcesso
    Created on : 04/12/2018, 21:58:16
    Author     : Brno
--%>

<%@page import="java.io.IOException"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Farisel-Permissão</title>
        <script src="jquery/external/jquery/jquery.js" type="text/javascript"></script>
        <script src="bootstrap4js/bootstrap.min.js" type="text/javascript"></script>
        <link href="bootstrap4css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="TimerJs.js" type="text/javascript"></script>
    </head>

    <body onload="iniciarTimer()">
        <form>
            <div class="card text-center ">
                <div class="card-header">
                    Fariseu-Permissão.
                </div>
                <div class="card-body alert-danger">
                    <h5 class="card-title">Um erro ocorreu!</h5>
                    <p class="card-text">Você não possui permissão para acessar este sistema.</p><br>
                </div>
                <div class="card-footer text-muted">
                    <p id="p"></p>
                </div>
            </div>
        </form>
    </body>
</html>


