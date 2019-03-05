
package com.fariseu.persistencia;

import com.fariseu.util.Excecao;
import com.fariseu.util.RepositorioDB;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;

/**
 *
 * @author hallef.wantek
 */
public class PTela extends PGenerica {
    
    public PTela(Connection oConn) throws Excecao {
        super(oConn);
    }
    
    public List<LinkedHashMap<String,Object>> getTela(Integer idMenu) throws Excecao, ServletException, UnknownHostException, SQLException {
        List<LinkedHashMap<String,Object>> lista = new LinkedList<>();
        LinkedHashMap<String,Object> dados;
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM TELA WHERE ID_MENU = ? ");
        setRset(new RepositorioDB(oConn).exPreparedReturnResultSet(sql.toString(), idMenu));
        try {
            while(getRset().next()) {
                dados = new LinkedHashMap<>();
                dados.put("id", getRset().getInt("ID"));
                dados.put("descricao", getRset().getString("DESCRICAO"));
                dados.put("url", getRset().getString("URL"));
                dados.put("idMenu", getRset().getString("ID_MENU"));
                lista.add(dados);
            }
            return lista;
        } catch (SQLException ex) {
            throw new Excecao(ex.getErrorCode(), ex.getSQLState() + " - " + ex.getMessage());
        }finally {
            closeObjects(getRset());
        }
    }
}
