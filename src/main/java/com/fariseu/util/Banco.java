package com.fariseu.util;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.postgresql.ds.PGConnectionPoolDataSource;

/**
 * @author Hallef.sud
 * @since 2018
 * @version 1.0
 * <html>
 * <body>
 * <div align="center">
 * <h1>Fariseu</h1><br>
 * <h3>Classe Banco</h3>
 *      <B>Metodos:</B>
 *      @see #getConnection();
 *      @see #closeConnection();
 *      @see #salvar(String sql, Connection oConn);
 * </div>
 * </body>
 * </html>
 */
public class Banco {

    private final Logger msgLog = Logger.getLogger(Banco.class);
    private int qtdConexao = 0;
    private int idBanco = 0;
    private static TipoSGBD SGBD;
    private boolean isConnected = false;
    private boolean isTansaction = false;

    private Connection oConn;

    static {
        initLogs();
    }

    public Banco() {
        idBanco++;
    }

    public Banco(Connection oConn) {
        idBanco++;
        this.oConn = oConn;
        if(oConn!=null) isConnected = true;
    }

    public Banco(TipoSGBD qualSGBD) {
        idBanco++;
        SGBD = qualSGBD;
    }
    
    /**
     * <B>Metodo getConnection()</B>
     * <p>Retorna a conexao</p>
     * @return connection
     * @throws Excecao 
     */
    public Connection getConnection() throws Excecao {
        PGConnectionPoolDataSource source = new PGConnectionPoolDataSource();
        try {
            if (SGBD == TipoSGBD.POSTGRES) {
                source.setPassword(SGBD.passwordPG);
                source.setServerName(SGBD.serverNamePG);
                source.setDatabaseName(SGBD.dataBaseNamePG);
                source.setUser(SGBD.userPG);
            } else if (SGBD == TipoSGBD.MARIADB) {
                source.setPassword(SGBD.passwordMDB);
                source.setServerName(SGBD.serverNameMDB);
                source.setDatabaseName(SGBD.dataBaseNameMDB);
                source.setUser(SGBD.userMDB);
            }
            oConn = source.getConnection();
            qtdConexao++;
            if(oConn!=null) isConnected = true;
            return oConn;
        } catch (SQLException e) {
            throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
        }
    }
    
    /**
     * <B>Metodo closeConnection():</B>
     * <p>fecha a conexao</p>
     * @return true ou false
     * @throws Excecao 
     */
    public boolean closeConnection() throws Excecao {
        if (isConnected) {
            msgLogs(3);
            try {
                if(isTansaction) {
                    msgLog.warn(new AtividadeBanco("Transacao aberta, feche a transacao!", idBanco, qtdConexao));
                    return false;
                }
                oConn.close();
                msgLogs(4);
            } catch (SQLException ex) {
                throw new Excecao(ex.getErrorCode(), ex.getSQLState() + " - " + ex.getMessage());
            }
        }
        return isConnected;
    }
    
    public void abrirTransacao() throws Excecao {
        if (isConnected) {
            try {
                oConn.setAutoCommit(false);
                isTansaction = true;
                msgLog.info(new AtividadeBanco("Transacao estabelecida!", idBanco, qtdConexao));
            } catch (SQLException e) {
                try {
                    oConn.setAutoCommit(true);
                    oConn.rollback();
                } catch (SQLException ex) {
                    throw new Excecao(ex.getErrorCode(), ex.getSQLState() + " - " + ex.getMessage());
                }
                throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
            }
        }
    }

    public void fecharTransacao() throws Excecao {
        if (isConnected && isTansaction) {
            try {
                oConn.commit();
                oConn.setAutoCommit(true);
                isTansaction = false;
                msgLog.info(new AtividadeBanco("Transacao fechada!", idBanco, qtdConexao));
            } catch (SQLException e) {
                throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
            }
        }
    }
    
    /**
     * <B>Metodo msgLogs(int tipoMsg):</B>
     * <p>Mostra no console as acoes do banco</p>
     * @param tipoMsg 
     */
    private void msgLogs(int tipoMsg) {
        switch (tipoMsg) {
            case 1:
                msgLog.info(new AtividadeBanco("Tentativa de conexao", idBanco, qtdConexao));
                break;
            case 2:
                msgLog.info(new AtividadeBanco("Conexao estabelecida!", idBanco, qtdConexao));
                break;
            case 3:
                msgLog.info(new AtividadeBanco("Tentativa de fachar conexao!", idBanco, qtdConexao));
                break;
            case 4:
                msgLog.info(new AtividadeBanco("Conexao fechada!", idBanco, qtdConexao));
                break;
            case 5:
                msgLog.info(new AtividadeBanco("Tentativa de abrir transacao!", idBanco, qtdConexao));
                break;
            case 6:
                msgLog.info(new AtividadeBanco("Transacao fechada!", idBanco, qtdConexao));
                break;
            case 7:
                msgLog.info(new AtividadeBanco("Tentativa de obter chave primaria MDB!", idBanco, qtdConexao));
                break;
            case 8:
                msgLog.info(new AtividadeBanco("Chave primaria MDB obtida!", idBanco, qtdConexao));
                break;
            case 9:
                msgLog.info(new AtividadeBanco("Tentativa de obter chave primaria PG!", idBanco, qtdConexao));
                break;
            case 10:
                msgLog.info(new AtividadeBanco("Chave primaria PG obtida!", idBanco, qtdConexao));
                break;

        }
    }

    private static void initLogs() {
        DOMConfigurator.configure("log4j.xml");
    }

}
