package com.fariseu.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <b>Titulo:</b> Extenso <br>
 * <b>Descricao:</b> Classe responsavel por escrever numero decimal por extenso.
 * <br>
 */
public final class Extenso {

    private ArrayList<Integer> nro;

    private BigInteger num;

    private final String Qualificadores[][] = {{"centavo", "centavos"}, {"", ""}, {"mil", "mil"}, {"milhão", "milhões"},
    {"bilhão", "bilhões"}, {"trilhão", "trilhões"}, {"quatrilhão", "quatrilhões"}, {"quintilhão", "quintilhões"},
    {"sextilhão", "sextilhões"}, {"septilhão", "septilhões"}};

    private final String Numeros[][] = {
        {"zero", "um", "dois", "tres", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez", "onze", "doze", "treze",
            "quatorze", "quinze", "desesseis", "desessete", "dezoito", "desenove"},
        {"vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"},
        {"cem", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos",
            "novecentos"}};

    /**
     * Construtor
     */
    public Extenso() {
        this.nro = new ArrayList<>();
    }

    /**
     * Construtor
     *
     * @param dec valor para colocar por extenso
     */
    public Extenso(BigDecimal dec) {
        this();
        this.setNumero(dec);
    }

    /**
     * Construtor
     *
     * @param dec valor para colocar por extenso
     */
    public Extenso(double dec) {
        this();
        this.setNumero(dec);
    }

    /**
     * Define o numero que sera escrito por extenso
     *
     * @param dec Valor do numero a ser escrito
     */
    public void setNumero(BigDecimal dec) {
        // Converte para inteiro arredondando os centavos
        this.num = dec.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).toBigInteger();

        // Adiciona valores
        this.nro.clear();
        if (this.num.equals(BigInteger.ZERO)) {
            // Centavos
            this.nro.add(0);
            // Valor
            this.nro.add(0);
        } else {
            // Adiciona centavos
            this.addRemainder(100);

            // Adiciona grupos de 1000
            while (!this.num.equals(BigInteger.ZERO)) {
                this.addRemainder(1000);
            }
        }
    }

    public void setNumero(double dec) {
        this.setNumero(new BigDecimal(dec));
    }

    /**
     * Description of the Method
     */
    public void show() {
        Iterator<Integer> valores = this.nro.iterator();

        while (valores.hasNext()) {
            System.out.println(valores.next().intValue());
        }
        System.out.println(this.toString());
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();

        int ct;

        for (ct = this.nro.size() - 1; ct > 0; ct--) {
            // Se ja existe texto e o atual nao e zero
            if ((buf.length() > 0) && !this.ehGrupoZero(ct)) {
                buf.append(" e ");
            }
            buf.append(this.numToString(this.nro.get(ct), ct));
        }
        if (buf.length() > 0) {
            if (this.ehUnicoGrupo()) {
                buf.append(" de ");
            }
            while (buf.toString().endsWith(" ")) {
                buf.setLength(buf.length() - 1);
            }
            if (this.ehPrimeiroGrupoUm()) {
                buf.insert(0, "h");
            }
            if ((this.nro.size() == 2) && (this.nro.get(1) == 1)) {
                buf.append(" real");
            } else {
                buf.append(" reais");
            }
            if (this.nro.get(0) != 0) {
                buf.append(" e ");
            }
        }
        if (this.nro.get(0) != 0) {
            buf.append(this.numToString(this.nro.get(0), 0));
        }
        return buf.toString();
    }

    private boolean ehPrimeiroGrupoUm() {
        return this.nro.get(this.nro.size() - 1) == 1;
    }

    private void addRemainder(int divisor) {
        // Encontra newNum[0] = num modulo divisor, newNum[1] = num dividido
        // divisor
        BigInteger[] newNum = this.num.divideAndRemainder(BigInteger.valueOf(divisor));

        // Adiciona modulo
        this.nro.add(newNum[1].intValue());

        // Altera numero
        this.num = newNum[0];
    }

    private boolean ehUnicoGrupo() {
        if (this.nro.size() <= 3) {
            return false;
        }
        if (!this.ehGrupoZero(1) && !this.ehGrupoZero(2)) {
            return false;
        }
        boolean hasOne = false;
        for (int i = 3; i < this.nro.size(); i++) {
            if (this.nro.get(i) != 0) {
                if (hasOne) {
                    return false;
                }
                hasOne = true;
            }
        }
        return true;
    }

    boolean ehGrupoZero(int ps) {
        if ((ps <= 0) || (ps >= this.nro.size())) {
            return true;
        }
        return this.nro.get(ps) == 0;
    }

    public String numToString(int numero, int escala) {
        int unidade = (numero % 10);
        int dezena = (numero % 100); // * nao pode dividir por 10 pois
        // verifica de 0..19
        int centena = (numero / 100);
        StringBuilder buf = new StringBuilder();

        if (numero != 0) {
            if (centena != 0) {
                if ((dezena == 0) && (centena == 1)) {
                    buf.append(this.Numeros[2][0]);
                } else {
                    buf.append(this.Numeros[2][centena]);
                }
            }

            if ((buf.length() > 0) && (dezena != 0)) {
                buf.append(" e ");
            }
            if (dezena > 19) {
                dezena /= 10;
                buf.append(this.Numeros[1][dezena - 2]);
                if (unidade != 0) {
                    buf.append(" e ");
                    buf.append(this.Numeros[0][unidade]);
                }
            } else if ((centena == 0) || (dezena != 0)) {
                buf.append(this.Numeros[0][dezena]);
            }

            buf.append(" ");
            if (numero == 1) {
                buf.append(this.Qualificadores[escala][0]);
            } else {
                buf.append(this.Qualificadores[escala][1]);
            }
        }

        return buf.toString();
    }

    public String inteiroString(int numero, int escala) {
        int unidade = (numero % 10);
        int dezena = (numero % 100); // * nao pode dividir por 10 pois
        // verifica de 0..19
        int centena = (numero / 100);
        StringBuilder buf = new StringBuilder();

        if (numero != 0) {
            if (centena != 0) {
                if ((dezena == 0) && (centena == 1)) {
                    buf.append(this.Numeros[2][0]);
                } else {
                    buf.append(this.Numeros[2][centena]);
                }
            }

            if ((buf.length() > 0) && (dezena != 0)) {
                buf.append(" e ");
            }
            if (dezena > 19) {
                dezena /= 10;
                buf.append(this.Numeros[1][dezena - 2]);
                if (unidade != 0) {
                    buf.append(" e ");
                    buf.append(this.Numeros[0][unidade]);
                }
            } else if ((centena == 0) || (dezena != 0)) {
                buf.append(this.Numeros[0][dezena]);
            }

        }

        return buf.toString();
    }
}
