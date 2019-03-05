
package com.fariseu.genericoservlet;

import com.fariseu.util.ButtonsOperacoes;
import com.fariseu.util.TipoRetorno;
import java.io.Serializable;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hallef.sud
 */
public abstract class GenericoServlet extends HttpServlet implements Serializable {

    private String tipo;
    
    protected void tipoRetorno(TipoRetorno retorno) {
        tipo = retorno.name();
        if(tipo==null || tipo.equals(""))tipo="application/html";
    }
    
    protected void configuraCabecalho(HttpServletResponse response) {
        response.setContentType(tipo+"; charset=UTF-8");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Progma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Header", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
    }
    
    protected Integer getOpercaoRealizarDB(String tipoOperacao) {
        int i;
        if(tipoOperacao != null) {
            for(i=0; i<ButtonsOperacoes.getValues().size(); i++) {
                if(tipoOperacao.equalsIgnoreCase(ButtonsOperacoes.getValues().get(i))) {
                    System.out.println("Posição "+i+" botao "+ButtonsOperacoes.getValues().get(i));
                    break;
                }
            }
            return i;
        }
        return -1;
    }
}