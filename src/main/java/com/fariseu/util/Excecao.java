
package com.fariseu.util;


import java.io.PrintWriter;
import java.io.StringWriter;

public class Excecao extends Exception {
    private int code = 0;

    public Excecao(String msg) {
        super(msg);
        this.code = -1;
    }

    public Excecao(int codigo, String msg) {
        super(msg);
        this.code = codigo;
    }

    public Excecao(Throwable t) {
        super(t);
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public Excecao(int codigo, String msg, boolean gravarLog) {
        super(msg);
        this.code = codigo;
        if (gravarLog) {
            super.printStackTrace();
        }
    }

    public int getCode() {
        return this.code;
    }

    public String recuperarStackTrace() {
        StringWriter sw = new StringWriter();
        super.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
