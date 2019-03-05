
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
       

        <%@include file="../../WEB-INF/includes_bootstrap3/includheade.jsp"%>
        <script src="../../jquery/external/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
        <!--<script src="../../jquery/jquery-1.4.2.min.js" type="text/javascript"></script>-->
        <!--<script src="../../jquery/external/jquery/jquery.js" type="text/javascript"></script>-->
        <script src="../../jquery/jquery.autocomplete.js" type="text/javascript"></script>
        <script>$autoCom = jQuery.noConflict();</script>

        <link href="css-auto-complete.css" rel="stylesheet" type="text/css"/>

        <title>Fariseu</title>

        <%@taglib tagdir="/WEB-INF/tags" prefix="data"%>

        <script type="text/javascript">
            $(function () {
                $autoCom("#pessoa").autocomplete("../../autocompletepessoa");
            });
        </script>

        <script>
            $("#tabs").tabs({active: 0});
        </script>

        <!--script operacoes DB-->
        <script type="text/javascript">
            $(function () {
                var nomeBtn;
                $("button").click(function () {
                    nomeBtn = $(this).val();
                    if (nomeBtn !== "btnLimpar") {
                        console.log(nomeBtn);
                        $.ajax({
                            type: 'post',
                            data: {
                                primeiroNome: $("#primeiroNome").val(),
                                sobreNome: $("#sobreNome").val(),
                                email: $("#email").val(),
                                sexo: $("#sexo").val(),
                                rg: $("#rg").val(),
                                cpf: $("#cpf").val(),
                                btn: $(this).val()
                            },
                            url: "/Fariseu/controller/ServletPessoaCtrl",

                            beforeSend: function () {
                                $("#dvgif").show();
                            },
                            success: function (retorno) {
                                mensagem("Pessoa", "Salvo com sucesso!!!");
                                listaDados();
                                $('#dvgif').hide();
                            },
                            error: function (erro) {
                                $('#resultado').html(erro);
                            }
                        });
                    }
                });
                //.done(function(msg){
                // $("#resultado").html(msg);
                //})
                //.fail(function(jqXHR, textStatus, msg) {
                //alert(jqXHR+" "+textStatus+" "+msg);
                //});
            });

            function listaDados() {

                var tbPessoa = $('#tbPessoa').DataTable({
                    select: true,
                    dom: 'Bfrtip',
                    buttons: [
                        'copy', 'csv', 'excel', 'pdf', 'print'
                    ],
                    "bRetrieve": true,
                    "sAjaxSource": '/Fariseu/controller/ServletPessoaCtrl',
//                    possivel passagem de parametro
//                    "fnServerParams": function ( aoData ) {
//                        aoData.push( { "name": "more_data", "value": "my_value" } );
//                    },
                    "language": {
                        "sEmptyTable": 'Sem dados disponíveis na tabela',
                        "sInfoEmpty": "Mostrando 0 a 0 de 0 entradas",
                        "sInfo": "Mostrando _START_ a _END_ de _TOTAL_ entradas",
                        "sInfoFiltered": "(Filtrando de um total de _MAX_ entradas)",
                        "sInfoPostFix": "",
                        "sInfoThousands": ",",
                        "sLengthMenu": "Mostrando _MENU_ entradas",
                        "sLoadingRecords": "<img src='../../images/gifload.gif' />",
                        "sProcessing": "<img src='../../images/gifload.gif' />",
                        "sSearch": "Buscar:",
                        "sZeroRecords": "No se encontraron coincidencias",
                        "oPaginate": {
                            "sFirst": "Primero",
                            "sLast": "Ultimo",
                            "sNext": "Siguinte",
                            "sPrevious": "Anterior"
                        },
                        select: {
                            rows: {
                                _: "Selecionado %d linhas",
                                0: "Clique em uma linha para selecioná-lo",
                                1: "Selecionada 1 linha"
                            }
                        }
                    },

                    "columns": [
                        {"mData": "id"},
                        {"mData": "nome"},
                        {"mData": "sobreNome"},
                        {"mData": "email"},
                        {"mData": "sexo"},
                        {"mData": "rg"},
                        {"mData": "cpf"},
                        {"mData": "dataRegistro"}
                    ]

                });

                //$('#tbPessoa').DataTable().ajax.reload();

                $('#tbPessoa tbody').on('click', 'tr', function () {
                    var pos = tbPessoa.row(this).index();
                    var row = tbPessoa.row(pos).data();
                    $("#primeiroNome").val(row.nome);
                    $("#sobreNome").val(row.sobreNome);
                    $("#email").val(row.email);
                    $("#sexo").val(row.sexo);
                    $("#rg").val(row.rg);
                    $("#cpf").val(row.cpf);
                });
            }
        </script>

    </head>

    <header>
        <%@include file="../../WEB-INF/includes_bootstrap3/includeheader.jsp"%>
    </header>

    <body style="width: 100%" onload="listaDados();">

        <!--GIF-->
        <div id="dvgif" class="gif_event_ajax" ></div>

        <div class="container-fluid ">
            <div class="row">
                <div class="col-sm-12">
                    <br><br><br>

                    <div id="tabs">
                        <ul>
                            <li class="active "><a href="#tabs-1">Nunc tincidunt</a></li>
                            <li><a href="#tabs-2">Proin dolor</a></li>
                            <li><a href="#tabs-3">Aenean lacinia</a></li>
                        </ul>
                        <div id="tabs-1">


                            <form method="POST" id="formGeral">

                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="primeiroNome"><h4>Primeiro nome</h4></label>
                                        <input type="text" class="form-control" name="primeiroNome" id="primeiroNome" placeholder="Primeiro nome" title="Ensira o primeiro nome aqui.">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for=sobreNome><h4>Sobre nome</h4></label>
                                        <input type="text" class="form-control" name="sobreNome" id="sobreNome" placeholder="Sobre nome" title="Ensira o restante do nome aqui.">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="email"><h4>Email</h4></label>
                                        <input type="email" class="form-control" name="email" id="email" placeholder="you@email.com" title="entre com o email.">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="sexo"><h4>Sexo</h4></label>
                                        <select name="sexo" class="form-control" id="sexo">
                                            <option>Masculino</option>
                                            <option>Feminino</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="rg"><h4>RG</h4></label>
                                        <input type="text" class="form-control" name="rg" id="rg" placeholder="rg" title="Ensira o RG.">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="cpf"><h4>CPF</h4></label>
                                        <input type="text" class="form-control" name="cpf" id="cpf" placeholder="cpf" title="Ensira o CPF.">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <br>
                                        <button value="btnSalvar_" class="btn  btn-success" type="button"><i class="glyphicon glyphicon-ok-sign"></i> Salvar</button>
                                        <button value="btnAlterar_" class="btn  btn-warning" type="button"><i class="glyphicon glyphicon-pencil"></i> Alterar</button>
                                        <button value="btnDeletar_" class="btn  btn-danger" type="button"><i class="glyphicon glyphicon-trash"></i> Deletar</button>
                                        <button value="btnLimpar" class="btn bg-light" type="reset"><i class="glyphicon glyphicon-repeat"></i> Limpar</button>
                                        <br>
                                        <p id="resultado"></p>
                                        <br>
                                    </div>


                            </form>

                            <div>
                                <table id="tbPessoa" class="responsive nowrap table table-striped table-bordered" cellspacing="0" style="width:100%;">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Primeiro nome</th>
                                            <th>Segundo nome</th>
                                            <th>Email</th>
                                            <th>Sexo</th>
                                            <th>RG</th>
                                            <th>CPF</th>
                                            <th>Data Registro</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>ID</th>
                                            <th>Primeiro nome</th>
                                            <th>Segundo nome</th>
                                            <th>Email</th>
                                            <th>Sexo</th>
                                            <th>RG</th>
                                            <th>CPF</th>
                                            <th>Data Registro</th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div id="tabs-2">
                        <p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
                    </div>
                    <div id="tabs-3">
                        <p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
                        <p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
                    </div>

                </div>
            </div>
        </div>
    </div>

</body>

</html>

<!--<div class="container project-tab">
            <form method="GET">

                <nav>
                    <div class="nav nav-tabs" id="nav-tab" role="tablist">
                        <a class="nav-item nav-link active" id="nav-tab-home" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">Cadastro de pessoa</a>
                        <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">Cadastro de telefone</a>
                        <a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#nav-contact" role="tab" aria-controls="nav-contact" aria-selected="false">Cadastro de endereço</a>
                    </div>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">    
                        <div class="card">
                            <div class="card-header bg-info" style="color: white;">
                                Cadastro de pessoa
                            </div>
                            <div class="card-body">
                                <div class="form-control">

                                    <div class="row">

                                        <div class="col">
                                            <div class="form-group col-md-12 ">
                                                <label for="nome" class="form-group ">Primeiro nome</label>
                                                <input class="form-control form-control-sm" type="text" name="nome" id="nome"/>
                                            </div>
                                        </div>

                                        <div class="col">
                                            <div class="form-group col-md-12">
                                                <label for="sobrenome" class="form-group ">Sobre nome</label>
                                                <input class="form-control form-control-sm" type="text" name="sobrenome" id="sobrenome"/>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="row">

                                        <div class="col">
                                            <div class="form-group col-md-12">
                                                <label for="email" class="form-group ">Email</label>
                                                <input class="form-control form-control-sm" type="email" name="email" id="email"/>
                                            </div>
                                        </div>

                                        <div class="col">
                                            <div class="form-group col-md-12">
                                                <label for="sexo" class="form-group ">Sexo</label>
                                                <select name="sexo" class="form-control form-control-sm" id="sexo">
                                                    <option>Masculino</option>
                                                    <option>Feminino</option>
                                                </select>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="row">

                                        <div class="col">
                                            <div class="form-group col-md-12">
                                                <label for="rg" class="form-group ">RG</label>
                                                <input class="form-control form-control-sm" type="text" name="rg" id="rg"/>
                                            </div>
                                        </div>

                                        <div class="col">
                                            <div class="form-group col-md-12">
                                                <label for="cpf" class="form-group ">CPF</label>
                                                <input class="form-control form-control-sm" type="text" name="cpf" id="cpf"/>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="bootstrap-iso">
                                        
                                        <table id="tbPessoa" class="table table-striped table-bordered" style="width:100%;">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Primeiro nome</th>
                                                    <th>Segundo nome</th>
                                                    <th>Email</th>
                                                    <th>Sexo</th>
                                                    <th>RG</th>
                                                    <th>CPF</th>
                                                    <th>Data Registro</th>
                                                </tr>
                                            </thead>
                                            <tfoot>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Primeiro nome</th>
                                                    <th>Segundo nome</th>
                                                    <th>Email</th>
                                                    <th>Sexo</th>
                                                    <th>RG</th>
                                                    <th>CPF</th>
                                                    <th>Data Registro</th>
                                                </tr>
                                            </tfoot>
                                        </table>
                                    </div>

                                    <div class="form-inline">

                                        <div >
                                            <button type="button" onclick="enviarParaServidor(), validarForm(), carregaListaPessoas()" class="btn btn-primary">Salvar 
                                                <i class="fa fa-floppy-o"></i>
                                            </button>
                                        </div>

                                        <div>
                                            <button type="submit" class="btn btn-success">Alterar 
                                                <i class="fa fa-pencil-square-o"></i>
                                            </button>
                                        </div>

                                        <div>
                                            <button type="submit" class="btn btn-danger">Deletar 
                                                <i class="fa fa-trash"></i>
                                            </button>
                                        </div>

                                        <div>
                                            <button type="reset" id="btnAlterar"  class="btn btn-foursquare">Limpar 
                                                <i class="fa fa-refresh"></i>
                                            </button>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">

                        <div class="card">
                                                        <div class="card-header bg-info" style="color: white;">
                                                            Cadastro de pessoa
                                                        </div>
                            <div class="card-body">
                                <div class="form-control">
                                    <div class="row">
                                        <div class="col">
                                            <div class="form-group col-md-12">
                                                <label for="pessoa" class="autocomplete form-group ">Pessoa</label>
                                                <input id="pessoa" name="pessoa" class="form-control">
                                            </div>
                                        </div>
                                    </div>                
                                    <div class="row">

                                        <div class="col">
                                            <div class="form-group col-md-12">
                                                <label for="ddtel" class="form-group ">DD</label>
                                                <input class="form-control" type="text" name="ddtel" id="ddtel"/>
                                            </div>
                                        </div>

                                        <div class="col">
                                            <div class="form-group col-md-12">
                                                <label for="telefone" class="form-group ">Telefone</label>
                                                <input class="form-control" type="text" name="telefone" id="telefone"/>
                                            </div>
                                        </div>

                                    </div>



                                    <div class="form-inline">

                                        <div >
                                            <button type="submit" class="btn btn-primary">Salvar 
                                                <i class="fa fa-floppy-o"></i>
                                            </button>
                                        </div>

                                        <div>
                                            <button type="submit" class="btn btn-success">Alterar 
                                                <i class="fa fa-pencil-square-o"></i>
                                            </button>
                                        </div>

                                        <div>
                                            <button type="submit" class="btn btn-danger">Deletar 
                                                <i class="fa fa-trash"></i>
                                            </button>
                                        </div>

                                        <div>
                                            <button type="button" id="btnAlterar"  class="btn btn-foursquare">Limpar 
                                                <i class="fa fa-refresh"></i>
                                            </button>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div> 

                    </div>


                    <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">...</div>

                </div>     
            </form>
        </div>-->

<!--<script>
            function carregaListaPessoas() {//http://localhost:5433/MyCrudAjaxServlet/primeiro
                var inputNome = document.getElementById("nome").value;
                var inputEmail = document.getElementById("email").value;
                if (inputNome !== '' & inputEmail !== '') {
                    $('#tbPessoa').DataTable({
                        dom: 'Bfrtip',
                        buttons: [
                            'copy', 'csv', 'excel', 'pdf', 'print'
                        ],
                        //'bProcessing': true,
                        "bServerSide": false,
                        "processing": true,
                        "retrieve": true,
                        "scrollY": 300,
                        'sAjaxSource': '../../pessoactrl',
                        "columns": [
                            {"mData": "id"},
                            {"mData": "nome"},
                            {"mData": "sobreNome"},
                            {"mData": "email"},
                            {"mData": "sexo"},
                            {"mData": "rg"},
                            {"mData": "cpf"},
                            {"mData": "dataRegistro"}
                        ]
                    });
                    $('#tbPessoa').DataTable().ajax.reload();
                }
            }
            //		    dom: 'Bfrtip',
//		    buttons: [
//			{
//			    extend: 'pdfHtml5',
//			    orientation: 'landscape',
//			    pageSize: 'LEGAL'
//			}
//		    ],
//		    "bProcessing": true,
//		    "language": {
//			"processing": "Processando..."
//		    },
//		    "bRetrieve": true,
//		    "bResponsive":  true,



//		    dom: 'Bfrtip',
//		    buttons: [
//			{
//			    extend: 'collection'
//			},
//			{
//			    extend: 'print',
//			    customize: function ( win ) {
//				$(win.document.body)
//				    .css( 'font-size', '10pt' )
//				    .prepend(
//					'<img src="http://datatables.net/media/images/logo-fade.png" style="position:absolute; top:0; left:0;" />'
//				    );
//
//				$(win.document.body).find( 'table' )
//				    .addClass( 'compact' )
//				    .css( 'font-size', 'inherit' );
//			    }
//			},
//			{
//			    extend:    'copyHtml5',
//			    text:      '<i class="fa fa-files-o"></i>',
//			    titleAttr: 'Copy'
//			    
//			},
//			{
//			    extend:    'excelHtml5',
//			    text:      '<i class="fa fa-file-excel-o"></i>',
//			    titleAttr: 'Excel'
//			    
//			},
//			{
//			    extend:    'csvHtml5',
//			    text:      '<i class="fa fa-file-text-o"></i>',
//			    titleAttr: 'CSV'
//			},
//			{
//			    extend:    'pdfHtml5',
//			    text:      '<i class="fa fa-file-pdf-o"></i>',
//			    titleAttr: 'PDF',
//			    messageTop: 'This print was produced using the Print button for DataTables'
//			    
//			}
//		    ],
        </script>-->