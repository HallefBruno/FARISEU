function mensagem(titulo, mensagem) {

    $('body').append('<div id="janelaMensagem" title="' + titulo + '"style="border-color: rgb(114, 84, 173);"> <p style="color:red;">' + mensagem + '</p> </div>');

    $(function () {
        $("#janelaMensagem").dialog({
            buttons: {
                "Fechar": function () {
                    $(this).dialog("close");
                    $(this).remove();
                    $(this).addClass("#notice");
                }
            }
        });
    });
}

//                            $( "#dialog" ).dialog({
//                                open: function(event, ui) {
//                                    setTimeout("$('#dialog').dialog('close')",3000);
//                                }
//                            });

// Msg salvo com sucesso
//        <div id="dialog" style="background-color: royalblue;" title="Cadastro de pessoa">
//	    <p style="color: royalblue;">Pessoa salva com sucesso!</p>
//	</div>
