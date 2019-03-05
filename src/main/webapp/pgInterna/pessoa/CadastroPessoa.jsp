<%-- 
    Document   : newjsp
    Created on : 25/01/2019, 17:09:04
    Author     : POSITIVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fariseu-Cadastro</title>
        
        <%@include file="../../WEB-INF/includes_bootstrap3/includheade.jsp"%>  
        <%@taglib tagdir="/WEB-INF/tags" prefix="data"%>
        
        <!--Necessario para complete-->
        <script src="../../jquery/external/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
        <script src="../../jquery/jquery.autocomplete.js" type="text/javascript"></script>
        <link href="css-auto-complete.css" rel="stylesheet" type="text/css"/>
        <script>$autoCom = jQuery.noConflict();</script>
        
        <script type="text/javascript">
            $(function() {
                $autoCom("#pessoaAutoComplete").autocomplete("../../autocompletepessoa");
            });
        </script>
        
        <script>
            $(function () {
                $("#tabs").tabs({active: 0});
            });
        </script>
        
        <style>
            .inner-addon {
                position: relative;
            }

            /* style glyph */
            .inner-addon .glyphicon {
                position: absolute;
                padding: 10px;
                pointer-events: none;
            }

            /* align glyph */
            .left-addon .glyphicon  { left:  0px;}
            .right-addon .glyphicon { right: 0px;}

            /* add padding  */
            .left-addon input  { padding-left:  30px; }
            .right-addon input { padding-right: 30px; }

        </style>
        
        <!--script operacoes DB-->
        <script type="text/javascript">
            $(function () {
                $("button").click(function () {
                    var nomeBtn = $(this).val();
                    if (nomeBtn === "btnSalvar_" || nomeBtn === "btnAlterar_" || nomeBtn === "btnDeletar_") {
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
                                $('#tbPessoa').DataTable().ajax.reload();
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
                       'pdf', 'csv', 'excel', 'copy','print'
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

    <body onload="listaDados();">
        
        <!--GIF-->
        <div id="dvgif" class="gif_event_ajax" ></div>
        
        <div class="container-fluid "style="height: 100%;">
            <div class="row">
                <div class="col-sm-12">
                    <br><br><br><br>

                    <div id="tabs">
                        <ul>
                            <li><a href="#pessoa">Pessoa</a></li>
                            <li><a href="#telPesoa">Tel. Pessoa</a></li>
                            <li><a href="#tabs-3">End. Pessoa</a></li>
                        </ul>
                        <div id="pessoa">

                            <form method="POST" id="formGeral">
                                <div class="form-row">

                                    <div class="form-group col-md-6">
                                        <label for="primeiroNome">Primeiro nome</label>
                                        <input type="text" class="form-control" name="primeiroNome" id="primeiroNome" placeholder="Primeiro nome" title="Ensira o primeiro nome aqui.">
                                    </div>

                                    <div class="form-group col-md-6">
                                        <label for=sobreNome>Sobre nome</label>
                                        <input type="text" class="form-control" name="sobreNome" id="sobreNome" placeholder="Sobre nome" title="Ensira o restante do nome aqui.">
                                    </div>

                                </div>
                                
                                <div class="form-row">

                                    <div class="form-group col-md-6">
                                        <label for="email">Email</label>
                                        <input type="email" class="form-control" name="email" id="email" placeholder="you@email.com" title="entre com o email.">
                                    </div>

                                    <div class="form-group col-md-6">
                                        <label for="sexo">Sexo</label>
                                        <select name="sexo" class="form-control" id="sexo">
                                            <option>Masculino</option>
                                            <option>Feminino</option>
                                        </select>
                                    </div>

                                </div>
                                
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="rg">RG</label>
                                        <input type="text" class="form-control" name="rg" id="rg" placeholder="rg" title="Ensira o RG.">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="cpf">CPF</label>
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

                        <div id="telPesoa">
                            
                            <div class="container">

                                <div class="panel panel-default" >

                                    <div class="panel-heading">
                                        <div class="panel-title text-center" style="color: #ffffff;">Cadastro de telefone por pessoa</div>
                                    </div>     

                                    <div class="panel-body" >
                                        <form>

                                            <div class="form-row">
                                                <div class="form-group col-xs-6">
                                                    <label class="control-label">
                                                        <code>Digite as iniciais do <i>nome</i> ou <i>cpf</i> da pessoa</code>
                                                    </label>
                                                    <div class="inner-addon right-addon">
                                                        <i class="glyphicon glyphicon-search"></i>
                                                        <input name="pessoaAutoComplete" type="text" id="pessoaAutoComplete" class="form-control" placeholder="Pequisar" />
                                                    </div>
                                                </div>
                                            </div>  
                                            <!--                                                <div class="form-group col-md-6">
                                                                                                <label for="pessoaAutoComplete">Pessoa</label>
                                                                                                <input name="pessoaAutoComplete" type="text" class="form-control input_text " id="pessoaAutoComplete" placeholder="Pessoa">
                                                                                            </div>-->
                                            <div class="form-group col-md-6">
                                                <label for="telefone">Telefone</label>
                                                <input type="text" class="form-control" id="telefone" placeholder="Telefone">
                                            </div>


                                            <div class="form-group">
                                                <div class="col-xs-12">
                                                    <button class="btn  btn-success" type="button"><i class="glyphicon glyphicon-ok-sign"></i> Salvar</button>
                                                    <button class="btn  btn-warning" type="button"><i class="glyphicon glyphicon-pencil"></i> Alterar</button>
                                                    <button class="btn  btn-danger" type="button"><i class="glyphicon glyphicon-trash"></i> Deletar</button>
                                                    <button class="btn bg-light" type="reset"><i class="glyphicon glyphicon-repeat"></i> Limpar</button>
                                                    <br><br>
                                                </div>
                                            </div>

                                            <div class="form-row">
                                                <div class="form-group col-md-12">
                                                    <table id="tbTelefone" class="responsive nowrap table table-striped table-bordered" cellspacing="0" style="width:100%;">
                                                        <thead>
                                                            <tr>
                                                                <th>ID</th>
                                                                <th>Primeiro nome</th>
                                                                <th>CPF</th>
                                                                <th>Telefone</th>
                                                            </tr>
                                                        </thead>
                                                        <tfoot>
                                                            <tr>
                                                                <th>ID</th>
                                                                <th>Primeiro nome</th>
                                                                <th>CPF</th>
                                                                <th>Telefone</th>
                                                            </tr>
                                                        </tfoot>
                                                    </table>
                                                </div>
                                            </div>
                                        </form>
                                    </div>                     
                                </div> 
                            </div>
                        </div>
                        
                        <div id="tabs-3">
                         
                        </div>
                    </div>        
                </div>
            </div>
        </div>
    </div>

</body>
</html>
