package com.fariseu.util;

import com.fariseu.anotacoes.Entidade;
import com.fariseu.anotacoes.Coluna;
import com.fariseu.anotacoes.Tempo;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Mapear {
    
    static {
        initLogs();
    }
    
    private final Logger msgLog = Logger.getLogger(Mapear.class);
    private String nomeSchema;
    private List<String> listaDeFielsAnotados = new LinkedList<>();
    private List<String> listaDeColunasDaTabelaEntidade = new LinkedList<>();
    private List<Object> listaDevaloresAtributos = new LinkedList<>();
    private final List<Object> listaDevaloresAtributosConsulta = new LinkedList<>();
    private String retornSql = "";
    private String nomeDoSchema = "";
    private String nomeDaEntidade = "";
    private boolean mostrarTodasMsgLog;
    private final RepositorioDB oRepositorioDB;
    private final Connection oConn;

    public Mapear(Connection oConn) throws Excecao {
        this.oConn = oConn;
        oRepositorioDB = new RepositorioDB(this.oConn);
        if (this.oConn == null) {
            throw new Excecao("Impossivel fazer mapeamento, conexao nula");
        } else {
            msgLog(0, true);
        }
    }
    
    private String getNomeEntidade(Object oEntidade) throws Excecao {
        msgLog(1,true);
        Class<? extends Object> entidadeAnotada = oEntidade.getClass();
        Entidade entidade = entidadeAnotada.getAnnotation(Entidade.class);
        nomeDaEntidade = "";
        if (entidade != null) {
            String nomeCasoSejaVazio = entidadeAnotada.getSimpleName();
            if (entidade.nome().isEmpty()) {
                nomeDaEntidade = nomeCasoSejaVazio.toLowerCase();
                return nomeDaEntidade;
            } else {
                nomeDaEntidade = entidade.nome();
            }
            msgLog(2,true);
            return nomeDaEntidade;
        }
        throw new Excecao("A entidade nao foi anotada " + oEntidade.getClass().getSimpleName());
    }

    //Uso este para retornar o nome da entidade evitando que o metodo
    //getNomeEntidade(Object oEntidade) seja acionado varias vezes
    private String getNomeDaEntidade() {
        return nomeDaEntidade;
    }
    
    public String getNomeDaEntidade(Object entidade) throws Excecao {
        return getNomeEntidade(entidade);
    }

    private String getSchemaEntidade(Object oEntidade) throws Excecao {
        msgLog(3,true);
        Class<? extends Object> entidadeAnotada = oEntidade.getClass();
        Entidade anotationEntidade = entidadeAnotada.getAnnotation(Entidade.class);
        nomeDoSchema = "";
        if (getNomeDaEntidade() != null && !getNomeDaEntidade().isEmpty()) {
            boolean possuiSchema = oRepositorioDB.validaSchema(anotationEntidade.tabelaSchema(), getNomeDaEntidade());
            String schema = oRepositorioDB.getNomeSchema();
            if (possuiSchema) {
                nomeDoSchema = anotationEntidade.tabelaSchema().isEmpty() ? schema : anotationEntidade.tabelaSchema();
                msgLog(4,true);
                return nomeDoSchema;
            } else {
                throw new Excecao(" Schema inexistente " + oEntidade.getClass().getName() + " " + anotationEntidade.tabelaSchema());
            }
        }
        throw new Excecao("Entidade nao foi anotada " + oEntidade.getClass().getName());
    }

    //Uso este metodo para retornar o nome do schema
    //para nao realizar conexoes quando o metodo InfoBanco..validaSchema
    //receber uma msg.
    private String getNomeDoSchema() {
        return nomeDoSchema;
    }

    private List<String> varrerFildsColuna(Object oEntidade) throws Excecao {
        msgLog(5,true);
        List<String> listaColunas = new ArrayList<>();
        listaDevaloresAtributos = new LinkedList<>();
        Long idSeq;
        String nomeEntidade;
        Field[] fields = getFieldsAnotados(oEntidade.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            Coluna col = field.getAnnotation(Coluna.class);
            try {
                listaDevaloresAtributosConsulta.add(field.get(oEntidade));
                if (col != null) {
                    listaColunas.add(!col.name().equals("") ? col.name() : field.getName());

                    if (field.get(oEntidade) == null) {
                        listaDevaloresAtributos.add(field.get(oEntidade));
                    } else {
                        if (field.get(oEntidade).toString().contains("@") && !field.get(oEntidade).toString().contains(".")) {
                            nomeEntidade = getNomeEntidade(field.get(oEntidade));
                            nomeSchema = getSchemaEntidade(field.get(oEntidade));
                            idSeq = oRepositorioDB.getIdSequenceTable(nomeSchema, nomeEntidade);
                            nomeDaEntidade = getNomeEntidade(oEntidade);
                            nomeDoSchema = getSchemaEntidade(oEntidade);
                            listaDevaloresAtributos.add(idSeq);
                        } else {
                            listaDevaloresAtributos.add(field.get(oEntidade));
                        }
                    }
                }

            } catch (IllegalAccessException e) {
                throw new Excecao("metodo varrerFieldsAnotados" + oEntidade.getClass().getSimpleName()+" "+e);
            }
        }
        if(listaColunas.size() > 0) {
            msgLog(6,true);
        }
        if (listaColunas.isEmpty()) {
            throw new Excecao("Nenhum atributo anotado da entidade: " + oEntidade.getClass().getSimpleName());
        }
        return listaColunas;
    }
    
    //Uso na classe ListenerOperacaoDB
    public List<String> varrerFildsColunaListenerOperacoesDB(Object oEntidade) throws Excecao {
        msgLog(5,true);
        final List<String> listaColunas = new ArrayList<>();

        Field[] fields = getFieldsAnotados(oEntidade.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            Coluna col = field.getAnnotation(Coluna.class);
            if (col != null) {
                listaColunas.add(!col.name().equals("") ? col.name() : field.getName());
            }
        }
        if(listaColunas.size() > 0) {
            msgLog(6,true);
        }
        if (listaColunas.isEmpty()) {
            throw new Excecao("Nenhum atributo anotado da entidade: " + oEntidade.getClass().getSimpleName());
        }
        return listaColunas;
    }
    
    //Uso na classe ListenerOperacaoDB
    public String nomeEntidade(Object oEntidade) throws Excecao {
        msgLog(1,true);
        Class<? extends Object> entidadeAnotada = oEntidade.getClass();
        Entidade entidade = entidadeAnotada.getAnnotation(Entidade.class);
        if (entidade != null) {
            String nomeCasoSejaVazio = entidadeAnotada.getSimpleName();
            if (entidade.nome().isEmpty()) {
                return nomeCasoSejaVazio.toLowerCase();
            }
            msgLog(2,true);
            return entidade.nome();
        }
        throw new Excecao("A entidade nao foi anotada " + oEntidade.getClass().getSimpleName());
    }
    
    //Uso na classe ListenerOperacaoDB
    public String nomeSchema(Object oEntidade) throws Excecao {
        msgLog(3,true);
        Class<? extends Object> entidadeAnotada = oEntidade.getClass();
        Entidade anotationEntidade = entidadeAnotada.getAnnotation(Entidade.class);
        if (nomeEntidade(oEntidade) != null && !nomeEntidade(oEntidade).isEmpty()) {
            boolean possuiSchema = oRepositorioDB.validaSchema(anotationEntidade.tabelaSchema(), nomeEntidade(oEntidade));
            String schema = oRepositorioDB.getNomeSchema();
            if (possuiSchema) {
                msgLog(4,true);
                return anotationEntidade.tabelaSchema().isEmpty() ? schema : anotationEntidade.tabelaSchema();
            } else {
                throw new Excecao(" Schema inexistente " + oEntidade.getClass().getName() + " " + anotationEntidade.tabelaSchema());
            }
        }
        throw new Excecao("Entidade nao foi anotada " + oEntidade.getClass().getName());
    }
    
    //Uso na classe ListenerOperacaoDB
    public DataEnum tidoData(Object entidade) {
        Field[] fields = getFieldsAnotados(entidade.getClass());
        Tempo col;
        DataEnum nome = null;
        boolean flag = false;
        for (Field field : fields) {
            field.setAccessible(true);
            col = field.getAnnotation(Tempo.class);
            if (col != null) {
                nome = col.data();
                flag = true;
                break;
            }
        }
        if(flag) return nome;
        return null;
    }

    private boolean compararListaDeAtributosComListaColunas(Object oEntidade) throws Excecao {
        msgLog(7,true);
        listaDeFielsAnotados = varrerFildsColuna(oEntidade);
        listaDeColunasDaTabelaEntidade = oRepositorioDB.getListaColunasTababela(getNomeDoSchema(), getNomeDaEntidade());
        int qtdEntrouNoIf = 0;
        if (listaDeFielsAnotados.size() == listaDeColunasDaTabelaEntidade.size()) {
            for (int i = 0; i < listaDeFielsAnotados.size(); i++) {
                if (listaDeFielsAnotados.get(i).equals(listaDeColunasDaTabelaEntidade.get(i))) {
                    qtdEntrouNoIf++;
                } else {
                    throw new Excecao("Anotacao incorreta, entidade: " + getNomeDaEntidade() + listaDeFielsAnotados + " tabela:" + listaDeColunasDaTabelaEntidade + " " + oEntidade.getClass().getSimpleName());
                }
            }
        } else {
            throw new Excecao("Quantidade de atributos da entidade com as da tabela estao diferentes\n ou a tabela nao possui chave primaria "+ listaDeColunasDaTabelaEntidade + " " + listaDeFielsAnotados);
        }
        if(qtdEntrouNoIf == listaDeFielsAnotados.size())msgLog(8,true);
        return qtdEntrouNoIf == listaDeFielsAnotados.size();
    }

    private String sqlInsert() {
        StringBuilder tb = new StringBuilder();
        StringBuilder at = new StringBuilder();
        StringBuilder sql = new StringBuilder();
        retornSql = "";

        for (int i = 0; i < listaDeFielsAnotados.size(); i++) {
            tb = tb.append("'").append(listaDevaloresAtributos.get(i)).append("'").append(",");
            at = at.append(listaDeFielsAnotados.get(i)).append(",");
        }
        sql.append("INSERT INTO ").append(getNomeDoSchema()).append(".").append(getNomeDaEntidade()).append("(").append(at.substring(0, at.length() - 1)).append(")").append(" VALUES (").append(tb.substring(0, tb.length() - 1)).append(")");
        retornSql = sql.toString();
        msgLog(9,true);
        return retornSql;
    }

    private String sqlUpdate(Long id) throws Excecao {
        String tb = "";
        String at = "";
        retornSql = "";
        StringBuilder sql = new StringBuilder();

        for (int i = 0; i < listaDeFielsAnotados.size(); i++) {
            tb = tb + listaDeColunasDaTabelaEntidade.get(i) + "=" + "'" + listaDevaloresAtributos.get(i) + "'" + ",";//+ listaDeColunasDaTabelaEntidade.get(i)+"="+
            at = at + listaDeFielsAnotados.get(i) + ",";
        }

        sql.append("UPDATE ").append(getNomeDoSchema()).append(".").append(getNomeDaEntidade()).append(" SET ").append(tb.substring(0, tb.length() - 1)).append(" WHERE ").append(oRepositorioDB.getPrimaryKey(getNomeDoSchema(), getNomeDaEntidade())).append(" = ").append(id);

        retornSql = sql.toString();
        System.err.println(retornSql);
        return retornSql;
    }

    private String sqlDelete(Long id) throws Excecao {
        retornSql = "";
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(getNomeDoSchema()).append(".").append(getNomeDaEntidade()).append(" WHERE ").append(oRepositorioDB.getPrimaryKey(getNomeDoSchema(), getNomeDaEntidade())).append(" = ").append(id);

        retornSql = sql.toString();
        System.err.println(retornSql);
        return retornSql;
    }

    private String sqlConsult() throws Excecao {
        StringBuilder tb = new StringBuilder();
        retornSql = "";
        StringBuilder sql = new StringBuilder();
        listaDeColunasDaTabelaEntidade = oRepositorioDB.getListaColunasTababela(getNomeDoSchema(), getNomeDaEntidade());
        for (int i = 0; i < listaDevaloresAtributosConsulta.size(); i++) {
            tb = tb.append(listaDeColunasDaTabelaEntidade.get(i)).append("=").append("'").append(listaDevaloresAtributosConsulta.get(i)).append("'").append(" OR ");
        }
        sql.append("SELECT * FROM ").append(getNomeDoSchema()).append(".").append(getNomeDaEntidade()).append(" WHERE ").append(tb.substring(0, tb.lastIndexOf(" OR ")));
        
        retornSql = sql.toString();
        System.err.println(retornSql);
        return retornSql;
    }

    private Field[] getFieldsAnotados(Class<?> c) {
        if (c.getSuperclass() != null) {
            Field[] superClassFields = getFieldsAnotados(c.getSuperclass());
            Field[] thisFields = c.getDeclaredFields();
            Field[] allFields = new Field[superClassFields.length + thisFields.length];
            System.arraycopy(superClassFields, 0, allFields, 0, superClassFields.length);
            System.arraycopy(thisFields, 0, allFields, superClassFields.length, thisFields.length);
            return allFields;
        } else {
            return c.getDeclaredFields();
        }
    }
    
    /**
     * <p>Realiza a insercao dos dados da entidade anotada</p>
     * @param oEntidade
     * @return
     * @throws Excecao 
     */
    public String persist(Object oEntidade) throws Excecao {
        if (getNomeEntidade(oEntidade) != null && !getNomeDaEntidade().isEmpty()
                && getSchemaEntidade(oEntidade) != null && !getNomeDoSchema().isEmpty()
                && compararListaDeAtributosComListaColunas(oEntidade)) {
            return sqlInsert();
        }
        return null;
    }

    public boolean update(Object oEntidade, Long id) throws Excecao {
        if (getNomeEntidade(oEntidade) != null && !getNomeDaEntidade().isEmpty()
                && getSchemaEntidade(oEntidade) != null && !getNomeDoSchema().isEmpty()
                && compararListaDeAtributosComListaColunas(oEntidade)) {
            sqlUpdate(id);
            return true;
        }
        return false;
    }

    public boolean delete(Object oEntidade, Long id) throws Excecao {
        if (getNomeEntidade(oEntidade) != null && !getNomeDaEntidade().isEmpty()
                && getSchemaEntidade(oEntidade) != null && !getNomeDoSchema().isEmpty()
                && compararListaDeAtributosComListaColunas(oEntidade)) {
            sqlDelete(id);
            return true;
        }
        return false;
    }

    public boolean consult(Object oEntidade) throws Excecao {
        if (getNomeEntidade(oEntidade) != null && !getNomeDaEntidade().isEmpty()
                && getSchemaEntidade(oEntidade) != null && !getNomeDoSchema().isEmpty()
                && compararListaDeAtributosComListaColunas(oEntidade)) {
            sqlConsult();
            return true;
        }
        return false;
    }

    public String retornaSql() {
        return this.retornSql;
    }

    private static void initLogs() {
        DOMConfigurator.configure("log4j.xml");
    }

    /**
     * @author Brno
     * @param tipoMsg Tipode mensagem
     * <html>
     * <body>
     * <div align="center">
     * <h1>Fariseu</h1><br>
     * <h3>Classe Banco</h3>
     * <h3>Metodo</h3>
     * @see #msgLog(int tipoMsg)
     * <p>
     * Detalhes: 1-Tentativa de conexao</p>
     * <p>
     * Detalhes: 2-Conexao estabelecida!</p>
     * <p>
     * Detalhes: 3-Tentativa de fechar conexao!</p>
     * <p>
     * Detalhes: 4-Conexao fechada!</p>
     * </div>
     * </body>
     * </html>
     */

    private void mostrarTodosMsgLog(int tipoMsg) {

        switch (tipoMsg) {
            case 0:
                msgLog.info(new AtividadeBanco("Inicializando classe Mapear"));
                break;
            case 1:
                msgLog.info(new AtividadeBanco("Tentativa de obter nome da classe anotada!"));
                break;
            case 2:
                msgLog.info(new AtividadeBanco("Nome da classe obtido!"));
                break;
            case 3:
                msgLog.info(new AtividadeBanco("Tentativa de obter nome do schema!"));
                break;
            case 4:
                msgLog.info(new AtividadeBanco("Schema obtido!"));
                break;
            case 5:
                msgLog.info(new AtividadeBanco("Iniciando varredura nos atributos da classe " + nomeDaEntidade + " anotada!"));
                break;
            case 6:
                msgLog.info(new AtividadeBanco("Varredura nos atributos da classe " + nomeDaEntidade + " finalizado!"));
                break;
            case 7:
                msgLog.info(new AtividadeBanco("Iniciando comparacao da lista de colunas com a anotacao da entidade " + nomeDaEntidade + "!"));
                break;
            case 8:
                msgLog.info(new AtividadeBanco("Comparacao finalizada!"));
                break;
            case 9:
                msgLog.info(new AtividadeBanco("Montando sql insert!"));
                break;
            case 10:
                msgLog.info(new AtividadeBanco("Sql insert montado!"));
                break;
        }
    }
    
    private void mostrarMsgSelecionadas(int tipoMesg, boolean print) {
        if(tipoMesg == 0 && print) {
            msgLog.info(new AtividadeBanco("Inicializando classe Mapear"));
        } else if(tipoMesg == 1 && print) {
            msgLog.info(new AtividadeBanco("Tentativa de obter nome da classe anotada!"));
        } else if (tipoMesg == 2 && print) {
            msgLog.info(new AtividadeBanco("Nome da classe obtido!"));
        } else if (tipoMesg == 3 && print) {
            msgLog.info(new AtividadeBanco("Tentativa de obter nome do schema!"));
        } else if (tipoMesg == 4 && print) {
            msgLog.info(new AtividadeBanco("Schema obtido!"));
        } else if (tipoMesg == 5 && print) {
            msgLog.info(new AtividadeBanco("Iniciando varredura nos atributos da classe " + nomeDaEntidade + " anotada!"));
        } else if (tipoMesg == 6 && print) {
            msgLog.info(new AtividadeBanco("Varredura nos atributos da classe " + nomeDaEntidade + " finalizado!"));
        } else if (tipoMesg == 7 && print) {
            msgLog.info(new AtividadeBanco("Iniciando comparacao da lista de colunas com a anotacao da entidade " + nomeDaEntidade + "!"));
        } else if (tipoMesg == 8 && print) {
            msgLog.info(new AtividadeBanco("Comparacao finalizada!"));
        } else if (tipoMesg == 9 && print) {
            System.err.println(retornSql);
            msgLog.info(new AtividadeBanco("Montando sql insert!"));
        } else if (tipoMesg == 10 && print) {
            msgLog.info(new AtividadeBanco("Sql insert montado!"));
        }
    }
    
    private void msgLog(int tipoMsg, boolean print) {
        if(mostrarTodasMsgLog) {
            mostrarTodosMsgLog(tipoMsg);
        } else if(mostrarTodasMsgLog == false && print) {
            mostrarMsgSelecionadas(tipoMsg,print);
        }
    }
}
