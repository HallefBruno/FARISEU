
package com.fariseu.autocomplete;

import com.fariseu.genericoservlet.GenericoServlet;
import com.fariseu.negocio.NAutoCompletePessoa;
import com.fariseu.util.Excecao;
import com.fariseu.util.RepositorioDB;
import com.fariseu.util.TipoRetorno;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hallef.sud
 */
@WebServlet("/autocompletepessoa")
public class AutoCompletePessoa extends GenericoServlet {

    public AutoCompletePessoa() {
    }

    @Override
    public void init() throws ServletException {
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UnknownHostException {
        tipoRetorno(TipoRetorno.HTML);
        configuraCabecalho(response);
        Connection oConn = (Connection) request.getAttribute("conexao");
        String parametro = request.getParameter("q");
        try {
            try (ResultSet rs = new RepositorioDB(oConn).exPreparedReturnResultSet(new NAutoCompletePessoa().autoCompletePessoa(parametro))) {
                while(rs.next()) {
                    response.getWriter().write(rs.getString(1)+"\n");
                }
            }        
        } catch (Excecao | SQLException ex) {
            Logger.getLogger(AutoCompletePessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void configuraCabecalho(HttpServletResponse response) {
        super.configuraCabecalho(response);
    }
    
    @Override
    public void destroy() {
    }
}
