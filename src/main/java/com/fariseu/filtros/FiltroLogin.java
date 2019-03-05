package com.fariseu.filtros;

import com.fariseu.negocio.NLogin;
import com.fariseu.util.Excecao;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hallef.sud
 */
@WebFilter(urlPatterns = "/PaginaValidaAcesso.jsp")
public class FiltroLogin implements Filter {

    private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    private static final String[] IP_HEADER_CANDIDATES = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

    private Pattern pattern;
    private Matcher matcher;
    private NLogin oNLogin;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pattern = Pattern.compile(IPADDRESS_PATTERN);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, UnknownHostException {

        try (Connection oConn = (Connection) request.getAttribute("conexao")) {
            try {
                oNLogin = new NLogin(oConn);
            } catch (Excecao ex) {
                Logger.getLogger(FiltroLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            HttpServletResponse resp = (HttpServletResponse) response;
            try {
                if (oNLogin.permissaoAcessarTelaLogin()) {
                    System.out.println("Redirecionando para http://127.0.0.1:8080/Fariseu/Login.html");
                    resp.sendRedirect("http://127.0.0.1:8080/Fariseu/Login.html");
                    
                } else {
                    System.out.println("Redirecionando para http://127.0.0.1:8088/Fariseu/PaginaNaoTemAcesso.jsp");
                    resp.sendRedirect("http://127.0.0.1:8080/Fariseu/PaginaNaoTemAcesso.jsp");
                }
            } catch (Excecao ex) {
                Logger.getLogger(FiltroLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FiltroLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Validate ip address with regular expression
     *
     * @param ip ip address for validation
     * @return true valid ip address, false invalid ip address
     */
    private boolean validate(final String ip) {
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    @Override
    public void destroy() {

    }
}
//        Banco banco = new Banco(TipoSGBD.POSTGRES);
//        try {
//            banco.getConnection();
//            HttpServletResponse resp = (HttpServletResponse) response;
//            String idTabela = new LoginService().getIpUsuario();
//            
//            if (idTabela == null) {
//                System.out.println("Redirecionando para http://127.0.0.1:5433/Fariseu/PaginaNaoTemAcesso.jsp");
//                resp.sendRedirect("http://127.0.0.1:5433/Fariseu/PaginaNaoTemAcesso.jsp");
//            } else {
//                System.out.println("Redirecionando para http://127.0.0.1:5433/Fariseu/Login.jsp");
//                resp.sendRedirect("http://127.0.0.1:5433/Fariseu/Login.jsp");
//            }
//            banco.closeConnection();
//        } catch (Excecao ex) {
//            Logger.getLogger(FiltroLogin.class.getName()).log(Level.SEVERE, null, ex);
//        }