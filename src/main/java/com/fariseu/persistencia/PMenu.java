
package com.fariseu.persistencia;

import com.fariseu.entidade.EMenu;
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
 * @author hallef.sud
 */
public class PMenu extends PGenerica<EMenu> {
    
    public PMenu(Connection oConn) throws Excecao {
        super(oConn);
    }
    
    public List<LinkedHashMap<String, Object>> getMenu() throws Excecao, ServletException, UnknownHostException, SQLException{
        List<LinkedHashMap<String,Object>> lista = new LinkedList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM MENU ");
        LinkedHashMap<String,Object> itens;
        setRset(new RepositorioDB(oConn).exPreparedReturnResultSet(sql.toString()));
        try {
            while(getRset().next()) {
                itens = new LinkedHashMap<>();
                itens.put("id", getRset().getInt("ID"));
                itens.put("descricao", getRset().getString("DESCRICAO"));
                lista.add(itens);
            }
            return lista;
        } catch (SQLException ex) {
            throw new Excecao(ex.getErrorCode(), ex.getSQLState() + " - " + ex.getMessage());
        }finally {
            closeObjects(getRset());
        }
    }
}
