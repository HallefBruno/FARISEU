<%-- 
    Document   : includeheader
    Created on : 25/11/2018, 17:30:31
    Author     : Brno
--%>

<%@page import="fariseu.negocio.NTela"%>
<%@page import="fariseu.entidade.ETela"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="fariseu.util.TipoSGBD"%>
<%@page import="fariseu.util.Banco"%>
<%@page import="fariseu.entidade.EMenu"%>
<%@page import="fariseu.negocio.NMenu"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>

<html>
    
    <style>
	#telas:hover {
	    background-color: #003eff;
	    color: white;
	}
	
	#navbarDropdown {
	    color: #000000;
	}
	

    </style>
    

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
    
    <body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	    <a style="color: #009eba;" class="navbar-brand" href="#"><i class="fa fa-tachometer fa-2x"></i>Fariseu</a>
	    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	    </button>

	    <div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
		    <li class="nav-item active">
			<a class="nav-link fa fa-home" href="/Fariseu/PaginaIndex.jsp">Home <span class="sr-only">(current)</span></a>
		    </li>

		    <%
                        //if (request.getRequestURI().equals("/Fariseu/PaginaIndex.jsp")) {
                            Connection oConn = (Connection) request.getAttribute("conexao");
                            List<LinkedHashMap<String, Object>> menu = new NMenu(oConn).getMenu();
			    
                            for (LinkedHashMap emenu : menu) {
		    %>

				<li class="nav-item dropdown">

				    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					<%
					    out.println(emenu.get("descricao"));
					%>
				    </a>
				    
				    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
					<%
					    List<LinkedHashMap<String,Object>> tela = new NTela(oConn).getTela(Integer.valueOf(emenu.get("id").toString()));
					    for(LinkedHashMap<String,Object> telas : tela) {
					    
					%>
					    <div class="dropdown-divider"></div>
					    <a class="dropdown-item" id="telas" href=<%out.println(telas.get("url"));%>><i class=""></i> <%out.println(telas.get("descricao"));%></a><!--fa fa-hand-o-right-->
					<% 
					    }
					%>
				    </div>

				</li>
		    <%  
			    }
			    oConn.close();
			//}
		    %>

		</ul>



		<form class="form-inline my-2 my-lg-0">
		    <input class="form-control mr-sm-2" type="search" placeholder="Pesquisar" aria-label="Search">
		    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Pesquisar</button>
		</form>
		    
	    </div>
	</nav>
    </body>
</html>
<br>
