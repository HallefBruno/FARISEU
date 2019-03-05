<%-- 
    Document   : PaginaIndex
    Created on : 30/11/2018, 16:32:37
    Author     : hallef.sud
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Fariseu-Principal</title>
        
	<script src="jquery/external/jquery/jquery.js" type="text/javascript"></script>
	<script src="jquery/jquery-ui.min.js" type="text/javascript"></script>

	<link href="jquery/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
	<link href="jquery/jquery-ui.theme.css" rel="stylesheet" type="text/css"/>
	
	<!--Bootstrap3 js-->
	<script src="bootstrap3js/bootstrap.min.js" type="text/javascript"></script>
	
	<!--Mask-->
	<script src="jquerymask/jquery.mask.js" type="text/javascript"></script>
	<script src="jquerymask/jquery.mask.min.js" type="text/javascript"></script>

	<!--DataTables css-->
	<link href="bootstrap3css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="datatablecss/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css"/>

	<!--Scripst DataTables--> 
	<script src="datatablejs/jquery.dataTables.min.js" type="text/javascript"></script>
	<script src="datatablejs/dataTables.bootstrap.min.js" type="text/javascript"></script>

	<!--validador-->
	<link href="valitatorbootstrap/bootstrapValidator.min.css" rel="stylesheet" type="text/css"/>
	<script src="valitatorbootstrap/bootstrapValidator.min.js" type="text/javascript"></script>

	<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        
         <!--style Alterar a cor da borda dos inputs para roxo-->
        <style>
            .form-control:focus {
                border-color: #563d7c;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(114,84,173,1);
            }
        </style>

        <style>
            
	    body {
		/*background-color: #dbdbdb;*/
	    }
	    
	    .notice {
		padding: 15px;
		background-color: #fafafa;
		border-left: 6px solid #7f7f84;
		margin-bottom: 10px;
		-webkit-box-shadow: 0 5px 8px -6px rgba(0,0,0,.2);
		-moz-box-shadow: 0 5px 8px -6px rgba(0,0,0,.2);
		box-shadow: 0 5px 8px -6px rgba(0,0,0,.2);
	    }
	    .notice-sm {
		padding: 10px;
		font-size: 80%;
	    }
	    .notice-lg {
		padding: 35px;
		font-size: large;
	    }
	    .notice-success {
		border-color: #80D651;
	    }
	    .notice-success>strong {
		color: #80D651;
	    }
	    .notice-info {
		border-color: #45ABCD;
	    }
	    .notice-info>strong {
		color: #45ABCD;
	    }
	    .notice-warning {
		border-color: #FEAF20;
	    }
	    .notice-warning>strong {
		color: #FEAF20;
	    }
	    .notice-danger {
		border-color: #d73814;
	    }
	    .notice-danger>strong {
		color: #d73814;
	    }
	    
	    a.list-group-item:hover {
		background-color: #7254ad;
		color: white;
	    }

	    .bs-example{
		margin: 20px;
	    }
        </style>


    </head>

    <body>
        <header>
            <%@include file= "WEB-INF/includes_bootstrap3/includeheader.jsp"%>
        </header>
	<div class="container" style="position: absolute; top: 50%; left: 50%; margin-right: -50%; transform: translate(-50%, -50%);">
	    
	    <div class="notice notice-info ">
		<div class="jumbotron">
		    <p><strong>Novidades!</strong></p>
		    <a href="#" class="list-group-item">Importacao arquivo REDE</a>
		    <a href="#" class="list-group-item ">Envio de email</a>
		    <a href="#" class="list-group-item">Feed-back entre gestores</a>
		    <a href="#" class="list-group-item">Dash admin</a>
		</div>
	    </div>

	</div>
    </body>

</html>



















<!--        <div class="container">
            <div class="list-group">
                <div class="card-header bg-info" style="color: white;">
                    <h5 style="text-align: center">Fariseu destaques</h5>
                </div>
                <a href="#" class="list-group-item border">Importacao bancaria</a>
                <a href="#" class="list-group-item ">Envio de email</a>
                <a href="#" class="list-group-item">Feed-back entre gestores</a>
                <a href="#" class="list-group-item">Dash admin</a>
            </div>
<div class="container">
	    <div class="notice notice-success">
		<a href="#" class="list-group-item border">Importacao bancaria</a>
                
	    </div>
	    <div class="notice notice-danger">
		<strong>Notice</strong> notice-danger
	    </div>
	    <div class="notice notice-info">
		<l1>
		<a href="#" class="list-group-item">Importacao bancaria</a>
		
                <a href="#" class="list-group-item ">Envio de email</a>
		
                <a href="#" class="list-group-item">Feed-back entre gestores</a>
		
                <a href="#" class="list-group-item">Dash admin</a>
		</l1>
	    </div>
	    <div class="notice notice-warning">
		<strong>Notice</strong> notice-warning
	    </div>
	    <div class="notice notice-lg">
		Letra grande
	    </div>
	    <div class="notice notice-sm">
		<strong>Small notice</strong> notice-sm
	    </div>
	</div>
        </div>

	/* Sticky footer styles
            -------------------------------------------------- */
            html {
                position: relative;
                min-height: 100%;
            }
            body {
                /* Margin bottom by footer height */
                margin-bottom: 60px;
                background-color: #dbdbdb;
            }
            .footer {
                position: absolute;
                bottom: 0;
                width: 100%;
                /* Set the fixed height of the footer here */
                height: 60px;
                line-height: 60px; /* Vertically center the text there */
                background-color: #f4f7f7;
            }


            /* Custom page CSS
            -------------------------------------------------- */
            /* Not required for template or sticky footer method. */

            body > .container {
                padding: 60px 15px 0;
            }

            .footer > .container {
                padding-right: 15px;
                padding-left: 15px;
            }

            code {
                font-size: 80%;
            }
-->