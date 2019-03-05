package com.fariseu.filtros;

import com.fariseu.genericoservlet.GenericoServlet;
import com.fariseu.negocio.NLogin;
import com.fariseu.util.Criptografia;
import com.fariseu.util.TipoRetorno;
import com.fariseu.util.Excecao;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/validarLogin")
public class FiltroValidaLogin extends GenericoServlet {
    
    private NLogin oNLogin;
    
    public FiltroValidaLogin() {
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        
    }
    
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException,UnknownHostException, SQLException, Excecao {
        super.tipoRetorno(TipoRetorno.HTML);
        super.configuraCabecalho(resp);
        
        String usuario = req.getParameter("login");
        String senha = req.getParameter("password");
        //Session
        try(Connection oConn = (Connection) req.getAttribute("conexao")) {
            oNLogin = new NLogin(oConn);
            if(usuario != null && senha!=null) {
                if(oNLogin.validarLogin(usuario, Criptografia.criptografar(senha), 2)) {
                    resp.getWriter().append("true");
                } else {
                    resp.getWriter().append("Usuario ou senha invalido!");
                }
            } else {
                resp.getWriter().append("Usuário e senha invalidos!");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, UnknownHostException {
        try {
            processRequest(req, resp);
        } catch (Excecao | SQLException ex) {
            Logger.getLogger(FiltroValidaLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, UnknownHostException {
        try {
            processRequest(req, resp);
        } catch (Excecao | SQLException ex) {
            Logger.getLogger(FiltroValidaLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configuraCabecalho(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Progma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Header", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
    }
    
    
    
    @Override
    public void destroy() {

    }
}
//HttpSession session = request.getSession(false);
//if (session != null) {
//    session.invalidate();
//}