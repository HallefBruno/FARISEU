
package com.fariseu.persistencia;

import com.fariseu.anotacoes.Coluna;
import com.fariseu.util.Banco;
import com.fariseu.util.Excecao;
import com.fariseu.util.Mapear;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <PRE>
 *      ANY-ACCESS-MODIFIER Object writeReplace() throws ObjectStreamException;
 * </PRE>
 * @author Brno
 * @param <T>
 */
public abstract class PGenerica<T> {
    
    Connection oConn;
    private ResultSet oRset;
    Statement oStm;
    private final Mapear mapear;
    private final Banco banco;
    
    public PGenerica(Connection oConn) throws Excecao {
        this.oConn = oConn;
        banco = new Banco(this.oConn);
        mapear = new Mapear(this.oConn);
    }

    /**
     *
     * @param entidade
     * @throws Excecao
     */
    public void salvar(T entidade) throws Excecao {
        this._salvar(mapear.persist(entidade));
    }
    
    @Deprecated
    public List<LinkedHashMap<String, Object>> getLista(T entidade) throws Excecao, SQLException{
        if(queryList(entidade).size() > 240) {
            throw new Excecao("Lista de dados muito grando não é recomendável a utilizacao desse metodo!");
        }
        return this.queryList(entidade);
    }
    
    public void consultar(T entidade) {
        
    }
    
    
    
    
    
    
    /**
     * <B>Metodo salvar(String sql, Connection oConn):</B>
     * <p>Salva os dados no banco</p>
     * @param sql
     * @param oConn
     * @throws Excecao 
     */
    
    private void _salvar(String sql) throws Excecao {
        oStm = null;
        if (oConn != null) {
            try {
                banco.abrirTransacao();
                oStm = oConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                oStm.execute(sql);
                banco.fecharTransacao();
            } catch (SQLException ex) {
                try {
                    oConn.rollback();
                } catch (SQLException e) {
                    throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
                }
                throw new Excecao(ex.getErrorCode(), ex.getSQLState() + " - " + ex.getMessage());
            } finally {
                try {
                    if (oStm != null) {
                        oStm.close();
                    }
                } catch (SQLException e) {
                    throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
                }
            }
        }
    }
    
    private List<LinkedHashMap<String,Object>> queryList(Object oEntidade) throws Excecao, SQLException {
        
        List<LinkedHashMap<String,Object>> lista = new LinkedList<>();
        LinkedHashMap<String,Object> itens;
        List<String> nomesAtributos = new LinkedList<>();
        
        Class<? extends Object> c = oEntidade.getClass();
        if(oConn!=null) {
            oStm = null;
            try {
                oConn.setAutoCommit(false);
                oStm = oConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                setRset(oStm.executeQuery("SELECT * FROM "+mapear.getNomeDaEntidade(oEntidade)));

                for(Field f: c.getDeclaredFields()) {
                    f.setAccessible(true);
                    Coluna col = f.getAnnotation(Coluna.class);
                    if(col!=null) {
                        nomesAtributos.add(!col.name().equals("") ? col.name() : f.getName());
                    } else {
                        nomesAtributos.add(f.getName());
                    }
                }

                while(getRset().next()) {
                    for (int i = 0; i < nomesAtributos.size(); i++) {
                        itens = new LinkedHashMap<>();
                        itens.put(nomesAtributos.get(i),getRset().getObject(nomesAtributos.get(i)));
                        lista.add(itens);
                    }
                }

            }catch(SQLException e) {
                try {
                    oConn.rollback();
                } catch (SQLException ex) {
                    throw new Excecao(ex.getErrorCode(), ex.getSQLState() + " - " + ex.getMessage());
                }
                throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
            } catch (SecurityException | IllegalArgumentException ex) {
                throw new Excecao(ex);
            } finally {
                oStm.close();
                getRset().close();
            }
        }
        
        return lista;
    }

    
    private void _consultar(String sql) throws Excecao {
        oStm = null;
        if(oConn != null) {
            try {
                banco.abrirTransacao();
                oStm = oConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                banco.fecharTransacao();
            }catch(SQLException ex) {
                try {
                    oConn.rollback();
                } catch (SQLException e) {
                    throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
                }
                throw new Excecao(ex.getErrorCode(), ex.getSQLState() + " - " + ex.getMessage());
            }
        }
    }
    
    protected void _closeObjects(Object...objts) {
        try {
            for (Object objt : objts) {
                if (objt instanceof Connection) {
                    if(oConn != null)oConn.close();
                } else if (objt instanceof ResultSet) {
                    if(getRset() != null)getRset().close();
                }
            }
        }catch(SQLException e) {
            try {
                throw new Excecao(e.getErrorCode(), e.getSQLState() + " - " + e.getMessage());
            } catch (Excecao ex) {
                Logger.getLogger(PGenerica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void closeObjects(Object...objs) throws SQLException {
        _closeObjects(objs);
    }

	public ResultSet getRset() {
		return oRset;
	}

	public void setRset(ResultSet oRset) {
		this.oRset = oRset;
	}
}
