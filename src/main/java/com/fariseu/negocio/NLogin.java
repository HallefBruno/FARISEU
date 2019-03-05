
package com.fariseu.negocio;

import com.fariseu.persistencia.PLogin;
import com.fariseu.util.Excecao;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;

/**
 *
 * @author hallef.wantek
 */
public class NLogin extends PLogin {

    public NLogin(Connection oConn) throws Excecao  {
        super(oConn);
    }
 
    @Override
    public boolean validarLogin(String login,String password,Integer idLicenca) throws Excecao, ServletException, UnknownHostException, SQLException {
        return super.validarLogin(login, password, idLicenca);
    }

    @Override
    public boolean permissaoAcessarTelaLogin() throws Excecao, ServletException, UnknownHostException, SQLException, IOException {
        return super.permissaoAcessarTelaLogin();
    }
}
