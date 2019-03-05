
package com.fariseu.negocio;

import com.fariseu.entidade.EPessoa;
import com.fariseu.persistencia.PPessoa;
import com.fariseu.util.Excecao;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.ServletException;

/**
 *
 * @author Brno
 */
public class NPessoa extends PPessoa {
    
    public NPessoa(Connection oConn) throws Excecao {
        super(oConn);
    }

    @Override
    public void salvar(EPessoa entidade) throws Excecao {
        super.salvar(entidade);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getListaPessoa() throws Excecao, ServletException, UnknownHostException {
        return super.getListaPessoa();
    }

    @Override
    public List<LinkedHashMap<String, Object>> getLista(EPessoa entidade) throws Excecao, SQLException {
        return super.getLista(entidade);
    }
}
