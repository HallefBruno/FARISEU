
package com.fariseu.persistencia;

import com.fariseu.util.Excecao;
import java.net.UnknownHostException;
import java.sql.SQLException;
import javax.servlet.ServletException;

/**
 *
 * @author hallef.sud
 */
public class PAutoCompletePessoa{

    
    protected String _autoCompletePessoa(String parametro) throws Excecao, ServletException, UnknownHostException, SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT pe_cpf.nome, pe_cpf.id, pe_cpf.rg ");
        sql.append("FROM (SELECT (Lpad(pe.cpf, 15, '0') || ' ' || pe.nome) AS pessoa, pe.cpf || ' ' || pe.nome AS nome, pe.id, pe.cpf, pe.rg FROM pessoa pe)pe_cpf ");
        sql.append("WHERE  pe_cpf.pessoa ILIKE '%").append(parametro).append("%'");
        sql.append("ORDER  BY pe_cpf.nome ");
        return sql.toString();
    }
    
    public String autoCompletePessoa(String parametro) throws Excecao, ServletException, UnknownHostException, SQLException {
        return _autoCompletePessoa(parametro);
    }

}
