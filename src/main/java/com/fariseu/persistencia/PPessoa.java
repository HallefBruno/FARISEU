
package com.fariseu.persistencia;

import com.fariseu.entidade.EPessoa;
import com.fariseu.util.Excecao;
import com.fariseu.util.RepositorioDB;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;

/**
 *
 * @author Brno
 */
public class PPessoa extends PGenerica<EPessoa> {
    
    public PPessoa(Connection oConn) throws Excecao {
        super(oConn);
    }
    
    public List<LinkedHashMap<String, Object>> getListaPessoa() throws Excecao, ServletException, UnknownHostException {
        List<LinkedHashMap<String, Object>> lista = new LinkedList<>();
        LinkedHashMap<String, Object> itens;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * FROM PESSOA ");
        setRset(new RepositorioDB(oConn).exPreparedReturnResultSet(sql.toString()));
        try {
            while(getRset().next()) {
                itens = new LinkedHashMap<>();
                itens.put("id", getRset().getInt("ID"));
                itens.put("nome", getRset().getString("NOME"));
                itens.put("sobreNome", getRset().getString("SOBRE_NOME"));
                itens.put("email", getRset().getString("EMAIL"));
                itens.put("sexo", getRset().getString("SEXO"));
                itens.put("rg", getRset().getString("RG"));
                itens.put("cpf", getRset().getString("CPF"));
                String dataFormatada = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(getRset().getTimestamp("DATA_REGISTRO"));
                itens.put("dataRegistro", dataFormatada);
                lista.add(itens);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex.getErrorCode()+" "+ex.getSQLState() + " - " + ex.getMessage());
        }
        return lista;
    }
}
