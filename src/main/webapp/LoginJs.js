var request;
var url;
var login;
var password;

function initLogin() {
    login = document.getElementById("login");
    password = document.getElementById("password");
}

function enviandoParaServidor() {

    url = "validarLogin?login="+escape(login.value)+"&password="+escape(password.value);
    request = initRequest();
    request.open("POST", url, true);
    request.onreadystatechange = retornoDoServidor;
    request.send(null);
    
}

function retornoDoServidor() {
    if(request.readyState === 4) {
	if(request.status === 200) {
	    if(request.responseText === "true") {
		window.location = "PaginaIndex.jsp";
	    }
	} else if(request.status === 500) {
	    alert(request.responseText);
	}
    }
}

function initRequest() {
    if (window.XMLHttpRequest) {
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}