
package com.fariseu.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author hallef.wantek
 */
public enum ButtonsOperacoes {
    
    //0 salvar
    //1 alterar
    //2 deletar
    
    BTN_SALVAR("btnSalvar"),
    BTN_ALTERAR("btnAlterar"),
    BTN_DELETAR("btnDeletar");

    private static final List<String> listBtn;
    private final String value;

    static {
        listBtn = new ArrayList<>();
        for (ButtonsOperacoes nomes : ButtonsOperacoes.values()) {
            listBtn.add(nomes.value);
        }
    }

    private ButtonsOperacoes(String value) {
        this.value = value;
    }

    public static List<String> getValues() {
        return Collections.unmodifiableList(listBtn);
    }
}
