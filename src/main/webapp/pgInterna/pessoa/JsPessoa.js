
var request;
var nome;
var sobreNome;
var email;
var sexo;
var rg;
var cpf;
var btn;
var isIE;


$(document).ready(function() {
    $('#rg').mask('AAAAAAAAAA', {reverse: true});
    $('#cpf').mask('000.000.000-00', {reverse: true});
    $('#primeiroNome').focus();
});

function initPessoa () {
    
    nome = document.getElementById("primeiroNome");
    sobreNome = document.getElementById("sobreNome");
    email = document.getElementById("email");
    sexo = document.getElementById("sexo");
    rg = document.getElementById("rg");
    cpf = document.getElementById("cpf");
    btn = document.getElementById("btnClicado");
    
}

function enviarParaServidor(tipoBtn) {
   
    var url = "../../ServletPessoaCtrl?primeiroNome="+escape(nome.value)+"&sobreNome="+escape(sobreNome.value)+"&email="+escape(email.value)+"&sexo="+escape(sexo.value)+"&rg="+escape(rg.value)+"&cpf="+escape(cpf.value)+"&btn="+escape(tipoBtn.value);
    request = initRequest();
    request.open("POST", url, true);
    request.onreadystatechange = callback;
    request.send(null);
    
}

function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') !== -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function callback() {
    if (request.readyState === 4) {
        if (request.status === 200) {
            nome.onfocus="this.value=''";
            alert("Registro salvo com sucesso! "+btn);
        }
    } else if(request.readyState === 500) {
	//alert("Erro");  
    }
}