
package com.fariseu.persistencia;

import com.fariseu.entidade.ELogin;
import com.fariseu.util.Excecao;
import com.fariseu.util.RepositorioDB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;

/**
 *
 * @author hallef.sud
 */
public class PLogin extends PGenerica<ELogin> {

    public PLogin(Connection oConn) throws Excecao {
        super(oConn);
    }
    
    protected boolean _validarLogin(String login,String password,Integer idLicenca) throws Excecao, ServletException, UnknownHostException, SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT LOGIN.LOGIN, LOGIN.PASSWORD, LOGIN.ID_LICENCA ");
        sql.append("FROM LOGIN ");
        sql.append("WHERE LOGIN.LOGIN ILIKE ? AND PASSWORD = ? AND ID_LICENCA = ? ");
        setRset(new RepositorioDB(oConn).exPreparedReturnResultSet(sql.toString(), login,password,idLicenca));
        try {
            while(getRset().next()) {
                if(getRset().getString("LOGIN").equalsIgnoreCase(login) && getRset().getString("PASSWORD").equals(password) && Integer.valueOf(getRset().getString("ID_LICENCA")).equals(idLicenca)) {
                    return true;
                }
            }
        }catch(SQLException e) {
            throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
        } finally {
            closeObjects(getRset());
        }
        return false;
    }
    
    protected boolean _permissaoAcessarTelaLogin() throws Excecao, ServletException, UnknownHostException,FileNotFoundException, UnsupportedEncodingException, IOException, SQLException {
        StringBuilder sql = new StringBuilder();

        int superUsuario = 0;
        String ip = InetAddress.getLocalHost().getHostAddress();
        
        String host = InetAddress.getLocalHost().getHostName();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.home")+File.separator+"super_usuario.txt"), "ISO-8859-1"))) {
            while(br.ready()) {
                superUsuario = Integer.valueOf(br.readLine());
            }
        }

        if(ip.equals("127.0.0.1")) {
            
            sql.append("SELECT IP, HOST_NAME, ID_SUPER_USUARIO ");
            sql.append("FROM PERMITE_TELA_LOGIN ");
            sql.append("WHERE HOST_NAME = ? AND ID_SUPER_USUARIO = ? ");
            
            setRset(new RepositorioDB(oConn).exPreparedReturnResultSet(sql.toString(),host,superUsuario));
        } else {
            
            sql.append("SELECT IP, HOST_NAME, ID_SUPER_USUARIO ");
            sql.append("FROM PERMITE_TELA_LOGIN ");
            sql.append("WHERE IP = ? AND HOST_NAME = ? AND ID_SUPER_USUARIO = ? ");
            
            setRset(new RepositorioDB(oConn).exPreparedReturnResultSet(sql.toString(),ip,host,superUsuario));
        }
        
        try {
            while(getRset().next()) {
                if(ip.equals("127.0.0.1")) {
                    if(getRset().getString("HOST_NAME").equalsIgnoreCase(host) && Integer.valueOf(getRset().getString("ID_SUPER_USUARIO")).equals(superUsuario)) {
                        return true;
                    }
                } else if(getRset().getString("IP").equals(ip) && getRset().getString("HOST_NAME").equalsIgnoreCase(host) && Integer.valueOf(getRset().getString("ID_SUPER_USUARIO")).equals(superUsuario)) {
                    return true;
                }
            }
        }catch(SQLException e) {
            throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
        } finally {
            closeObjects(getRset());
        }
        return false;
    }
    
    public boolean validarLogin(String login,String password,Integer idLicenca) throws Excecao, ServletException, UnknownHostException, SQLException {
        return _validarLogin(login, password, idLicenca);
    }
    
    public boolean permissaoAcessarTelaLogin() throws Excecao, ServletException, UnknownHostException, SQLException, UnsupportedEncodingException, IOException {
        return _permissaoAcessarTelaLogin();
    }

}
