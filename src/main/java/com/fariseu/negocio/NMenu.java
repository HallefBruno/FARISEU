
package com.fariseu.negocio;

import com.fariseu.persistencia.PMenu;
import com.fariseu.util.Excecao;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.ServletException;

/**
 *
 * @author hallef.sud
 */
public class NMenu extends PMenu {
    
    public NMenu(Connection oConn) throws Excecao{
        super(oConn);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getMenu() throws Excecao, ServletException, UnknownHostException, SQLException {
        return super.getMenu(); 
    }

}
