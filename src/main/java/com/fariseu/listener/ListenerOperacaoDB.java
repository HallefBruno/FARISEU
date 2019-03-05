
package com.fariseu.listener;

import com.fariseu.genericoservlet.GenericoServlet;
import com.fariseu.util.Banco;
import com.fariseu.util.DataEnum;
import com.fariseu.util.Excecao;
import com.fariseu.util.Mapear;
import com.fariseu.util.RepositorioDB;
import com.fariseu.util.TipoSGBD;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Web application lifecycle listener.
 * @author hallef.Brno
 */
@WebListener
public class ListenerOperacaoDB extends GenericoServlet implements ServletRequestListener {
    
    private RepositorioDB oRepositorioDB;
    private Mapear oMapear;
    private Enumeration<?> params;
    private List<Object> dados;
    

    @Override
    public void requestInitialized(ServletRequestEvent requestEvent) {
        
        HttpServletRequest request = ((HttpServletRequest) requestEvent.getServletRequest());
        
        String pathRequest = request.getRequestURI();
        Integer operacaoDB;
        String nomeEntidade = "";

        if(pathRequest.contains("Controller") || pathRequest.contains("controller") && 
           pathRequest.contains("servlet") || pathRequest.contains("Servlet") && 
           pathRequest.contains("ctrl") || pathRequest.contains("Ctrl")) {
            
            if(request.getParameter("btn")!=null && !request.getParameter("btn").isEmpty() && request.getParameter("btn").contains("_")) {
                String retirarCaracter = request.getParameter("btn").replace("_", "");
                operacaoDB = super.getOpercaoRealizarDB(retirarCaracter);
                
                if(pathRequest.contains("controller") && pathRequest.contains("servlet") && pathRequest.contains("ctrl")) {
                nomeEntidade = pathRequest.substring(pathRequest.lastIndexOf("servlet")+7, pathRequest.indexOf("ctrl"));
                } else if(pathRequest.contains("Controller") && pathRequest.contains("Servlet") && pathRequest.contains("Ctrl")) {
                    nomeEntidade = pathRequest.substring(pathRequest.lastIndexOf("Servlet")+7, pathRequest.indexOf("Ctrl"));
                } else if(pathRequest.contains("controller") && pathRequest.contains("Servlet") && pathRequest.contains("Ctrl")) {
                    nomeEntidade = pathRequest.substring(pathRequest.lastIndexOf("Servlet")+7, pathRequest.indexOf("Ctrl"));
                }
                
                if(operacaoDB != -1) {
                    
                    try (Connection oConn = new Banco(TipoSGBD.POSTGRES).getConnection()) {
                        oMapear = new Mapear(oConn);
                        oRepositorioDB = new RepositorioDB(oConn);
                        Class<?> classe = Class.forName("fariseu.entidade.E"+nomeEntidade);
                        Object entidade = classe.newInstance();
                        String colunas = Arrays.toString(oMapear.varrerFildsColunaListenerOperacoesDB(entidade).toArray()).replace("[", "(").replace("]", ")");
                        String nomeEntidadeAnotada = oMapear.nomeEntidade(entidade);
                        String schema = oMapear.nomeSchema(entidade);
                        DataEnum data = oMapear.tidoData(entidade);
                        StringBuilder sql = new StringBuilder();

                        params = request.getParameterNames();
                        String paramName;
                        String[] paramValues;
                        dados = new LinkedList<>();
                        
                        while (params.hasMoreElements()) {
                            paramName = (String) params.nextElement();
                            paramValues = request.getParameterValues(paramName);
                            if(!paramValues[0].contains("_"))
                                dados.add(paramValues[0]);
                        }
                        

                        if(data!=null && !data.name().isEmpty()) {
                            dados.add(Timestamp.from(Instant.now()));
                        }
                        
                        StringBuilder pontoVirgula = new StringBuilder();
                        
                        for(int i=0; i<dados.size(); i++) {
                            
                            if(i < dados.size()-1) {
                                pontoVirgula.append(" ?, ");
                            } else {
                                pontoVirgula.append(" ? ");
                            }
                        }
                        
                        if(schema!=null && !schema.isEmpty()) {
                            sql.append("INSERT INTO ").append(schema).append(".")
                               .append(nomeEntidadeAnotada).append(" ").append(colunas)
                               .append(" VALUES (").append(pontoVirgula.toString()).append(")");
                            
                        } else {
                            sql.append("INSERT INTO ").append(nomeEntidadeAnotada)
                               .append(" ").append(colunas).append(" VALUES (")
                               .append(pontoVirgula.toString()).append(")");
                        }
                        
                        System.out.println(sql.toString());
                        
                        switch(operacaoDB) {
                            case 0:
                                oRepositorioDB.exPreparedReturnResultSet(sql.toString(), dados);
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    } catch (Excecao | ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | ServletException | UnknownHostException ex) {
                        Logger.getLogger(ListenerOperacaoDB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent requestEvent) {
        
    }
}
