
package com.fariseu.filtros;


import com.fariseu.util.Banco;
import com.fariseu.util.Excecao;
import com.fariseu.util.PomCon;
import com.fariseu.util.TipoSGBD;
import java.io.IOException;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hallef.sud
 */
@WebFilter("/*")
public class FiltroConexao implements Filter {
    
    private ServletContext context;
    private String pomCom;
    private Banco banco;
    private String timeStamp;
    private String uri;
    private boolean pedurarConexao;

    @Override
    public void init(FilterConfig config) throws ServletException {
        banco = new Banco(TipoSGBD.POSTGRES);
        context = config.getServletContext();
        context.log("Iniciando filtro conexao");
        pomCom = Arrays.toString(PomCon.getValues().toArray());
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        uri = req.getRequestURI();
        pedurarConexao = pomCom.contains(uri);

        timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        this.context.log(" Date: "+timeStamp+" Caminho da requisicao:: " + uri);
        
        if(pedurarConexao) {
            try {
                try (Connection oConn = banco.getConnection()) {
                    request.setAttribute("conexao", oConn);
                    chain.doFilter(request, response);
                }
            }catch(Excecao e) {
                throw new ServerException(e.getMessage());
            } catch (SQLException ex) {
                throw new ServerException(String.valueOf(ex.getErrorCode())+" "+ String.valueOf(ex.getSQLState()) + " - " + String.valueOf(ex.getMessage()));
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        
    }
}
