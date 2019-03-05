
package com.fariseu.datatable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author hallef.
 */
public final class GenericaDataTable {
    
    private int  iTotalRecords;
 
    private int  iTotalDisplayRecords;

    private String  sEcho;

    private String sColumns;
    
    private List<LinkedHashMap<String,Object>> aaData;
    
    private Gson gson;
    
    private GenericaDataTable genericDataTable;

    public String listaGson(List<LinkedHashMap<String,Object>> lista) {
        genericDataTable = new GenericaDataTable();
        gson = new GsonBuilder().setPrettyPrinting().create();
        genericDataTable.setiTotalRecords(lista.size());
        genericDataTable.setAaData(lista);
        return gson.toJson(genericDataTable);
    }
    
    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public String getsColumns() {
        return sColumns;
    }

    public void setsColumns(String sColumns) {
        this.sColumns = sColumns;
    }

    public List<LinkedHashMap<String,Object>> getAaData() {
        return aaData;
    }

    public void setAaData(List<LinkedHashMap<String,Object>> aaData) {
        this.aaData = aaData;
    }
}
