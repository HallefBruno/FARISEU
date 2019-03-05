package com.fariseu.util;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author hallef.sud
 */
public class RepositorioDB {

    static {
        initLogs();
    }

    private final Logger msgLog = Logger.getLogger(RepositorioDB.class);

    private Connection oConn;
    private boolean isConnected = false;
    private String driver;
    private final String driverPG = "PostgreSQL JDBC Driver";
    private final String driverMaria = "MariaDB";
    private long nextValeu;
    private String strSeq;
    private boolean isAutoIncremento;
    private String nomeSchema;
    private boolean mostrarTodasMsgLog = false;
    

    public RepositorioDB() {
    }

    public RepositorioDB(Connection oConn) throws Excecao {
        msgLog(0,true);
        this.oConn = oConn;
        if (this.oConn != null) {
            msgLog(1,true);
            isConnected = true;
            try {
                driver = this.oConn.getMetaData().getDriverName();
            } catch (SQLException e) {
                throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
            }
        } else {
            throw new Excecao("Conexao nula!");
        }
    }

    public List getListaColunasTababela(String schema, String tabela) throws Excecao {
        List<String> lista = new LinkedList<>();
        msgLog(2,true);
        String pk = getPrimaryKey(schema, tabela);
        if (tabela != null && !tabela.isEmpty()) {
            try {
                if (isConnected) {
                    DatabaseMetaData metadata = oConn.getMetaData();
                    ResultSet resultSet = metadata.getColumns(oConn.getCatalog(), schema, tabela, null);
                    while (resultSet.next()) {
                        if (!resultSet.getString("COLUMN_NAME").equals(pk)) {
                            lista.add(resultSet.getString("COLUMN_NAME"));
                        }
                    }
                    if (lista.size() > 0) {
                        msgLog(3,true);
                    }
                    return lista;
                } else {
                    throw new Excecao("Conexao fechada!");
                }
            } catch (SQLException e) {
                throw new Excecao(e);
            }
        }
        throw new Excecao("Tabela invalida");
    }

    public String getPrimaryKey(String schema, String tabela) throws Excecao {
        msgLog(4,true);
        if (tabela != null && !tabela.isEmpty()) {
            if (driver.contains(driverMaria)) {
                msgLog(5,true);
                return getColumnPrimaryKeyMDB(tabela);
            } else if (driver.contains(driverPG)) {
                msgLog(5,true);
                return getColumnPrimaryKeyPG(schema, tabela);
            }

        } else {
            throw new Excecao("Erro no método getPrimaryKey");
        }
        return null;
    }

    private String getColumnPrimaryKeyMDB(String tabela) throws Excecao {
        StringBuilder sql = new StringBuilder();
        String pk;
        sql.append("SELECT COLUMN_NAME ");
        sql.append("FROM information_schema.COLUMNS ");
        sql.append("WHERE TABLE_NAME = '").append(tabela).append("' ");
        sql.append("AND EXTRA = '").append("AUTO_INCREMENT").append("'");
        pk = executaSqlRetorna(sql.toString(), "COLUMN_NAME");
        if (pk != null) {
            return pk;
        }
        return null;
    }

    private String getColumnPrimaryKeyPG(String schema, String tabela) throws Excecao {
        DatabaseMetaData metadata;
        ResultSet oRset = null;
        if (isConnected) {
            try {
                metadata = oConn.getMetaData();
                oRset = metadata.getPrimaryKeys(oConn.getCatalog(), schema, tabela);
                while (oRset.next()) {
                    return oRset.getString("COLUMN_NAME");// ou 4
                }
            } catch (SQLException e) {
                throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
            } finally {
                try {
                    if (oRset != null) {
                        oRset.close();
                    }
                } catch (SQLException e) {
                    throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
                }
            }
        } else {
            throw new Excecao("A conexao esta fechada!");
        }
        return null;
    }

    public boolean validaSchema(String schema, String tabela) throws Excecao {

        if (schema.isEmpty()) {
            if (!getSchema(tabela).isEmpty()) {
                return true;
            }
        } else {
            if (getSchema(tabela).equals(schema)) {
                return true;
            }
        }
        return false;
    }

    public Long getIdSequenceTable(String schema, String tabela) throws Excecao {
        msgLog(12,true);
        if (!getPrimaryKey(schema, tabela).isEmpty()) {
            if (driver.contains(driverPG)) {
                msgLog(13,true);
                return getSequencePG(schema, tabela, getColumnPrimaryKeyPG(schema, tabela));
            } else if (driver.contains(driverMaria)) {
                msgLog(13,true);
                return getSequenceMDB(tabela, schema);
            }
        }
        throw new Excecao("getIdSequenceTable");
    }

    public Long getSequencePG(String schema, String tabela, String pk) throws Excecao {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT PG_GET_SERIAL_SEQUENCE('").append(schema).append(".").append(tabela).append("'").append(",")
                .append("'").append(pk).append("'").append(") AS GETSEQ;");
        boolean executeProximoPasso;
        executeProximoPasso = isAutoIncrement(sql);

        sql = new StringBuilder();
        if (executeProximoPasso) {
            sql.append("SELECT NEXTVAL('").append(strSeq).append("') AS PROSEQ;");
            nextValeu = (Long.valueOf(executaSqlRetorna(sql.toString(), "PROSEQ")) - 1);
            setValSeq();
            return nextValeu;
        }

        throw new Excecao("getSequence");
    }

    private boolean isAutoIncrement(StringBuilder sql) throws Excecao {
        strSeq = executaSqlRetorna(sql.toString(), "GETSEQ");
        return strSeq.contains("seq");
    }

    //Setando seq para evitar desperdício de seq
    private void setValSeq() throws Excecao {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SETVAL('").append(strSeq).append("',").append(nextValeu).append(");");
        executaSqlRetorna(sql.toString(), "");
    }

    public boolean getIsAutoIncremento() {
        return isAutoIncremento;
    }

    public Long getSequeValuePostgres() {
        return nextValeu;
    }

    public Long getSequenceMDB(String tabela, String schema) throws Excecao {
        StringBuilder sql = new StringBuilder();
        Long id;
        sql.append("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = '").append(tabela).append("'").append(" AND ").append("TABLE_SCHEMA = '").append(schema).append("'");
        id = Long.valueOf(executaSqlRetorna(sql.toString(), "AUTO_INCREMENT"));
        if (id != null) {
            return (id - 1);
        }
        throw new Excecao("Erro no metodo getSequenceMDB");
    }

    private String getSchema(String tabela) throws Excecao {
        msgLog(6,true);
        StringBuilder sql = new StringBuilder();
        if(driver.contains(driverPG)) {
            sql.append("SELECT * FROM information_schema.TABLES where TABLE_NAME = ").append("'").append(tabela).append("'");
            setNomeSchema(executaSqlRetorna(sql.toString(), "TABLE_SCHEMA"));
        }
        msgLog(7,true);
        return getNomeSchema();
    }

    private void setNomeSchema(String nomeSchema) {
        this.nomeSchema = nomeSchema;
    }

    public String getNomeSchema() {
        return nomeSchema;
    }

    public String executaSqlRetorna(String sql, String coluna) throws Excecao {
        msgLog(8,true);
        Statement oStm;
        ResultSet oSet;
        String col[] = null;
        List<String> colunas = new LinkedList<>();
        if (coluna != null && coluna.contains(",")) {
            col = coluna.split(",");
        }
        if (sql != null && !sql.isEmpty() && coluna != null && !coluna.isEmpty()) {
            if (oConn != null) {
                try {
                    oStm = oConn.createStatement();
                    oSet = oStm.executeQuery(sql);
                    if (col != null && col.length > 1) {
                        int cont = 0;
                        while (oSet.next()) {
                            while (col.length > cont) {
                                colunas.add(oSet.getString(col[cont]));
                                cont++;
                            }
                        }
                        msgLog(9,true);
                        return Arrays.asList(colunas).toString().replace("[[", "").replace("]]", "");
                    } else {
                        while (oSet.next()) {
                            msgLog(9,true);
                            return oSet.getString(coluna);
                        }
                    }
                } catch (SQLException ex) {
                    throw new Excecao(ex.getErrorCode(), ex.getSQLState() + " - " + ex.getMessage());
                }

            }
            //Caso nao seja necessario o retorno
        } else if (sql != null && !sql.isEmpty() && coluna != null && coluna.isEmpty()) {
            if (oConn != null) {
                try {
                    oStm = oConn.createStatement();
                    oStm.execute(sql);
                    msgLog(9,true);
                } catch (SQLException ex) {
                    throw new Excecao(ex.getErrorCode(), ex.getSQLState() + " - " + ex.getMessage());
                }
            }
        }
        return null;
    }
    
    /**
     * <B>Execulta uma string passada no banco e uma lista de objetos por parametro</B>
     * <B>o uso desse método e mais empregado caso queira salvar dados em uma tabela sem usar o método</B>
     * @see Mapear#persist(java.lang.Object) 
     * @param sql
     * @param listaObjeto
     * @return
     * @throws ServletException
     * @throws UnknownHostException
     * @throws Excecao 
     */
    public ResultSet exPreparedReturnResultSet(String sql, Object...listaObjeto) throws ServletException, UnknownHostException, Excecao {
        PreparedStatement oPstm;
        msgLog(9,true);
        try {
            if (listaObjeto.length > 0) {
                if (isConnected) {
                    oPstm = oConn.prepareStatement(sql);
                    for (int i = 0; i < listaObjeto.length; i++) {
                        instancia(oPstm, i, listaObjeto);
                    }
                    return oPstm.executeQuery();
                }

            } else {
                throw new RuntimeException("Lista de objeto passada precisa ser maior que zero!");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        return null;
    }
    
    /**
     * <p>Execulta uma string passada no banco e uma lista</p>
     * <p>o uso desse método e mais empregado caso queira salvar dados em uma tabela sem usar o método</p>
     * <p>este metodo e semelhante ao </p>
     * @see RepositorioDB#exPreparedReturnResultSet(java.lang.String, java.lang.Object...) 
     * @see Mapear#persist(java.lang.Object)
     * @param sql
     * @param lista
     * @return
     * @throws ServletException
     * @throws UnknownHostException
     * @throws Excecao 
     */
    public Integer exPreparedReturnResultSet(String sql, List lista) throws ServletException, UnknownHostException, Excecao {
        PreparedStatement oPstm;
        msgLog(9,true);
        try {
            if (lista.size() > 0) {
                if (isConnected) {
                    oPstm = oConn.prepareStatement(sql);
                    for (int i = 0; i < lista.size(); i++) {
                        instancia(oPstm, i, lista);
                    }
                    return oPstm.executeUpdate();
                }

            } else {
                throw new RuntimeException("Lista de objeto passada precisa ser maior que zero!");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        return null;
    }

    public ResultSet exPreparedReturnResultSet(String sql) throws ServletException, UnknownHostException {
        try {
            if(oConn!=null) {
                return oConn.prepareStatement(sql).executeQuery();
            }
        } catch(SQLException e) {
            throw new ServletException(e);
        }
        return null;
    }
    
    private void instancia(PreparedStatement oPstm, int i, Object... listaObjeto) throws SQLException {
        
        if(listaObjeto[i].getClass().getName().equals("java.sql.Timestamp")) {
            oPstm.setTimestamp(i + 1, (java.sql.Timestamp) listaObjeto[i]);
        }
        
        if (listaObjeto[i] instanceof String) {
            oPstm.setString(i + 1, listaObjeto[i].toString());
        } else if (listaObjeto[i] instanceof Integer) {
            oPstm.setInt(i + 1, Integer.valueOf(listaObjeto[i].toString()));
        } else if (listaObjeto[i] instanceof Long) {
            oPstm.setLong(i + 1, Long.valueOf(listaObjeto[i].toString()));
        } else if (listaObjeto[i] instanceof Double) {
            oPstm.setDouble(i + 1, Double.valueOf(listaObjeto[i].toString()));
        } else if (listaObjeto[i] instanceof BigDecimal) {
            oPstm.setBigDecimal(i + 1, (BigDecimal) listaObjeto[i]);
        } else if (listaObjeto[i] instanceof java.util.Date) {
            oPstm.setDate(i + 1, (java.sql.Date) listaObjeto[i]);
        } else if (listaObjeto[i] instanceof java.sql.Date) {
            oPstm.setDate(i + 1, (java.sql.Date) listaObjeto[i]);
        }
    }
    
    private void instancia(PreparedStatement oPstm, int i, List lista) throws SQLException {
        boolean timestamp = false;
        if(lista.get(i).getClass().getName().equals("java.sql.Timestamp")) {
            oPstm.setTimestamp(i + 1, (java.sql.Timestamp) lista.get(i));
            timestamp = true;
        }
        
        if (lista.get(i) instanceof String) {
            oPstm.setString(i + 1, lista.get(i).toString());
        } else if (lista.get(i) instanceof Integer) {
            oPstm.setInt(i + 1, Integer.valueOf(lista.get(i).toString()));
        } else if (lista.get(i) instanceof Long) {
            oPstm.setLong(i + 1, Long.valueOf(lista.get(i).toString()));
        } else if (lista.get(i) instanceof Double) {
            oPstm.setDouble(i + 1, Double.valueOf(lista.get(i).toString()));
        } else if (lista.get(i) instanceof BigDecimal) {
            oPstm.setBigDecimal(i + 1, (BigDecimal) lista.get(i));
        } else if (lista.get(i) instanceof java.util.Date && timestamp==false) {
            oPstm.setDate(i + 1, (java.sql.Date) lista.get(i));
        } else if (lista.get(i) instanceof java.sql.Date) {
            oPstm.setDate(i + 1, (java.sql.Date) lista.get(i));
        }
    }

    private void mostrarTodosMsgLog(int tipoMsg) {

        switch (tipoMsg) {
            case 0:
                msgLog.info(new AtividadeBanco("Inicilizando classe RepositorioDB"));
                break;
            case 1:
                msgLog.info(new AtividadeBanco("Conexao estabelecida!"));
                break;
            case 2:
                msgLog.info(new AtividadeBanco("Tentativa de obter todas as colunas da entidade!"));
                break;
            case 3:
                msgLog.info(new AtividadeBanco("Lista obtida!"));
                break;
            case 4:
                msgLog.info(new AtividadeBanco("Tentativa de obter chave primaria!"));
                break;
            case 5:
                msgLog.info(new AtividadeBanco("Chave primaria obtida!"));
                break;
            case 6:
                msgLog.info(new AtividadeBanco("Tentativa de obter schema!"));
                break;
            case 7:
                msgLog.info(new AtividadeBanco("Schema obtido!"));
                break;
            case 8:
                msgLog.info(new AtividadeBanco("Tentativa de executar sql com retorno!"));
                break;
            case 9:
                msgLog.info(new AtividadeBanco("Executar sql com retorno finalizado!"));
                break;
            case 10:
                msgLog.info(new AtividadeBanco("Tentativa de execultar preparedstatemet com retorno!"));
                break;
            case 11:
                msgLog.info(new AtividadeBanco("Preparedstatemet com retorno executado!"));
                break;
            case 12:
                msgLog.info(new AtividadeBanco("Tentativa de obter sequence!"));
                break;
            case 13:
                msgLog.info(new AtividadeBanco("Sequence obtida!"));
                break;
        }

    }
    
    private void mostrarMsgSelecionadas(int tipoMesg, boolean print) {
        if(tipoMesg == 0 && print) {
            msgLog.info(new AtividadeBanco("Inicilizando classe RepositorioDB"));
        } else if(tipoMesg == 1 && print) {
            msgLog.info(new AtividadeBanco("Conexao estabelecida!"));
        } else if (tipoMesg == 2 && print) {
            msgLog.info(new AtividadeBanco("Tentativa de obter todas as colunas da entidade!"));
        } else if (tipoMesg == 3 && print) {
            msgLog.info(new AtividadeBanco("Lista obtida!"));
        } else if (tipoMesg == 4 && print) {
            msgLog.info(new AtividadeBanco("Tentativa de obter chave primaria!"));
        } else if (tipoMesg == 5 && print) {
            msgLog.info(new AtividadeBanco("Chave primaria obtida!"));
        } else if (tipoMesg == 6 && print) {
            msgLog.info(new AtividadeBanco("Tentativa de obter schema!"));
        } else if (tipoMesg == 7 && print) {
            msgLog.info(new AtividadeBanco("Schema obtido!"));
        } else if (tipoMesg == 8 && print) {
            msgLog.info(new AtividadeBanco("Tentativa de executar sql com retorno!"));
        } else if (tipoMesg == 9 && print) {
            msgLog.info(new AtividadeBanco("Executar sql com retorno finalizado!"));
        } else if (tipoMesg == 10 && print) {
            msgLog.info(new AtividadeBanco("Tentativa de execultar preparedstatemet com retorno!"));
        } else if (tipoMesg == 11 && print) {
            msgLog.info(new AtividadeBanco("Preparedstatemet com retorno executado!"));
        } else if (tipoMesg == 12 && print) {
            msgLog.info(new AtividadeBanco("Tentativa de obter sequence!"));
        } else if (tipoMesg == 13 && print) {
            msgLog.info(new AtividadeBanco("Sequence obtida!"));
        }
    }
    
    private void msgLog(int tipoMsg, boolean print) {
        if(mostrarTodasMsgLog) {
            mostrarTodosMsgLog(tipoMsg);
        } else if(mostrarTodasMsgLog == false && print) {
            mostrarMsgSelecionadas(tipoMsg,print);
        }
    }

    private static void initLogs() {
        DOMConfigurator.configure("log4j.xml");
    }
}