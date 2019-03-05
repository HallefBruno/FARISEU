<%-- 
    Document   : includes
    Created on : 24/11/2018, 21:15:29
    Author     : Brno
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<script src="../../jquery/external/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
<script src="../../jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="../../jquery/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
<link href="../../jquery/jquery-ui.theme.min.css" rel="stylesheet" type="text/css"/>

<!--Mask-->
<script src="../../jquerymask/jquery.mask.min.js" type="text/javascript"></script>

<!--DataTables css-->
<link href="../../bootstrap3css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="../../datatablecss/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="../../datatablecss/responsive.dataTables.min.css" rel="stylesheet" type="text/css"/>

<!--Scripst DataTables -->
<script src="../../datatablejs/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="../../datatablejs/dataTables.bootstrap.min.js" type="text/javascript"></script>
<script src="../../datatablejs/dataTables.responsive.min.js" type="text/javascript"></script>
<link href="../../datatablecss/buttons.dataTables.min.css" rel="stylesheet" type="text/css"/>
<link href="../../datatablecss/select.dataTables.min.css" rel="stylesheet" type="text/css"/>

<!--Bootstrap3 js-->
<script src="../../bootstrap3js/bootstrap.min.js" type="text/javascript"></script>
<link href="../../bootstrap3css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

<!--Scripst DataTables -->
<script src="../../datatablejs/dataTables.buttons.min.js" type="text/javascript"></script>
<script src="../../datatablejs/buttons.colVis.js" type="text/javascript"></script>
<script src="../../datatablejs/buttons.flash.min.js" type="text/javascript"></script>
<script src="../../datatablejs/buttons.html5.min.js" type="text/javascript"></script>
<script src="../../datatablejs/buttons.print.min.js" type="text/javascript"></script>
<script src="../../datatablejs/jszip.min.js" type="text/javascript"></script>
<script src="../../datatablejs/pdfmake.min.js" type="text/javascript"></script>
<script src="../../datatablejs/vfs_fonts.js" type="text/javascript"></script>
<script src="../../datatablejs/dataTables.select.min.js" type="text/javascript"></script>


<!--validador-->
<link href="../../valitatorbootstrap/bootstrapValidator.min.css" rel="stylesheet" type="text/css"/>
<script src="../../valitatorbootstrap/bootstrapValidator.min.js" type="text/javascript"></script>
<link href="../../fonts/font-awesome.min.css" rel="stylesheet" type="text/css"/>

<!--script de mensagem-->
<script src="../../script-fariseu/MensagemBox.js" type="text/javascript"></script>

<head>
    
    <!--Muda cor do botao de proximo e anterior do plugin DataTable-->
    <style>
        .pagination>.active>a, .pagination>.active>a:focus, .pagination>.active>a:hover, .pagination>.active>span, .pagination>.active>span:focus, .pagination>.active>span:hover {
            z-index: 3;
            color: #fff;
            cursor: default;
            background-color: #563d7c;
            border-color: #563d7c;
        }
    </style>
    
    <!--Muda a cor ao clicar na linha da tabela DataTable-->
    <style>
        
/*        table.dataTable tbody>tr.hover, table.dataTable tbody>tr>.hover {
            background-color: #563d7c;
            color:white;
            cursor: pointer;
        }*/

        table.dataTable tbody>tr:hover {
            background-color: #563d7c;
            color:white;
            cursor: pointer;
        }
        
        table.dataTable tbody>tr.selected, table.dataTable tbody>tr>.selected {
            background-color: #563d7c;
            color:white;
            cursor: pointer;
        }
    </style>
    
    <!--muda cor das tab do jquery e altera a panel-->
    <style>
        
        .ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active, a.ui-button:active, .ui-button:active, .ui-button.ui-state-active:hover {
            border: 1px solid #563d7c;
            background: #563d7c;
            font-weight: normal;
            color: #fff;
        }

        .ui-tabs .ui-tabs-panel {
            padding:1px 20px 20px 20px;
	    border:1px solid #ccc;
	    margin-top:1px;
	    display:none;
	    border-radius:5px;
	    box-shadow:2px 2px 2px #bbb;
	    border-radius: 0px 0px 5px 5px;
	    padding: 10px;
        }
    </style>
    
    <!--Ao usar panel-default do bootstrap, a cor da mudará para roxo-->
    <style>
        .panel-default {
            border-color: #563d7c;
        }
        .panel-default>.panel-heading {
            color: #fff;
            background-color: #563d7c;
            border-color: #563d7c;
        }
    </style>
    
    <!--style Alterar a cor da borda dos inputs para roxo-->
    <style>
        .form-control:focus {
	    border-color: #563d7c;
	    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(114,84,173,1);
	}
    </style>
    
    <!--style novidade tela inicial-->
    <style type="text/css">

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
    
    <!--style panel tela de cadastro de pessoa -->
    <style type="text/css">
        
        .tab-pane {
	    padding:1px 20px 20px 20px;
	    border:1px solid #ccc;
	    margin-top:1px;
	    display:none;
	    border-radius:5px;
	    box-shadow:2px 2px 2px #bbb;
	    border-radius: 0px 0px 5px 5px;
	    padding: 10px;
	}
	.nav-tabs {
	    margin-bottom: 0;
	}
        
    </style>
    
    <!--style do gif ao fazer requisição ao servidor-->
    <style type="text/css">
        .gif_event_ajax {
            display:    none;
            position:   fixed;
            z-index:    1000;
            top:        0;
            left:       0;
            height:     100%;
            width:      100%;
            background: rgba( 255, 255, 255, .8 )
            url('../../images/gifload.gif')
            50% 50% no-repeat;
        }

        body.loading {
            overflow: hidden;   
        }

        body.loading .gif_event_ajax {
            display: block;
        }
    </style>
    
</head>