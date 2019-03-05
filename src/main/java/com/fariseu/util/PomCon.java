
package com.fariseu.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author hallef.sud
 */
public enum PomCon {

    PomCon1("/Fariseu/"),
    PomCon2("/Fariseu/validarLogin"),
    PomCon3("/Fariseu/PaginaIndex.jsp"),
    PomCon4("/Fariseu/Login.jsp"),
    PomCon5("/Fariseu/autocompletepessoa"),
    PomCon6("/Fariseu/pgInterna/pessoa/CadastroPessoa.jsp"),
    PomCom8("/Fariseu/controller/ServletPessoaCtrl");

    private static final List<String> VALUES;

    private final String value;

    static {
        VALUES = new ArrayList<>();
        for (PomCon caminho : PomCon.values()) {
            VALUES.add(caminho.value);
        }
    }

    private PomCon(String value) {
        this.value = value;
    }

    public static List<String> getValues() {
        return Collections.unmodifiableList(VALUES);
    }
}
