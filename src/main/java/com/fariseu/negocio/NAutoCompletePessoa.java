
package com.fariseu.negocio;

import com.fariseu.persistencia.PAutoCompletePessoa;
import com.fariseu.util.Excecao;
import java.net.UnknownHostException;
import java.sql.SQLException;
import javax.servlet.ServletException;

/**
 *
 * @author hallef.sud
 */
public class NAutoCompletePessoa extends PAutoCompletePessoa {

    @Override
    public String autoCompletePessoa(String parametro) throws Excecao, ServletException, UnknownHostException, SQLException {
        return super.autoCompletePessoa(parametro);
    }
    
}
