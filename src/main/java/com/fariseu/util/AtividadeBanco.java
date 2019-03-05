package com.fariseu.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

@XStreamAlias("Fariseu")
public class AtividadeBanco implements Serializable {

    @XStreamAlias("tempo")
    @XStreamAsAttribute
    public long tempo;
    
    @XStreamAlias("tempoFormatado")
    @XStreamAsAttribute
    public String tempoFormatado;
    
    @XStreamAsAttribute
    public StringBuffer id;

    @XStreamAlias("sistema")
    @XStreamAsAttribute
    public String sistema;

    //@XStreamAlias("aplicacao")
    //@XStreamAsAttribute
    public StringBuilder aplicacao;

    @XStreamAlias("mensagem")
    @XStreamAsAttribute
    public String mensagem;

    public AtividadeBanco(String mensagem, long id, long idConexao) {
        NumberFormat formatarNumero = NumberFormat.getNumberInstance();
        formatarNumero.setMinimumIntegerDigits(7);
        formatarNumero.setGroupingUsed(false);
        
        //tempo = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        this.tempo = Calendar.getInstance().getTimeInMillis();
        
        //Date date = new Date(tempo);
        //DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        //formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        //tempoFormatado = formatter.format(date);
        
        this.mensagem = mensagem;
        this.id = new StringBuffer(formatarNumero.format(id) + "/" + formatarNumero.format(idConexao));

        //StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        //this.aplicacao = new StringBuilder();

        // varrendo a pilha de execucao para montar a seguencia de metodos invocados ate o momento.
        // Limita a pilha entre uma aplicacao JSP ou classe com metodo main ate o primeiro metodo da classe banco.
//        for (int i = stack.length - 1; i > 1; i--) {
//            String caminhoAplicacao = stack[i].toString();
//            String nomeAplicacao = stack[i].getFileName();
//            aplicacao.append(nomeAplicacao);
//            if ((caminhoAplicacao.contains("jsp")|| caminhoAplicacao.contains("doPost") || (this.aplicacao.length() > 0)) && !caminhoAplicacao.contains("Servlet")) {
//                if ((this.aplicacao.indexOf("Banco.") >= 0)) {
//                    break;
//                }
//
//                if (nomeAplicacao.contains("jsp")) {
//                    this.aplicacao.append(nomeAplicacao.substring(0, nomeAplicacao.lastIndexOf(".")).replace("_", ".")).append(" - ");
//                } else {
//                    this.aplicacao.append(nomeAplicacao.replace("java", "")).append(stack[i].getMethodName()).append(" - ");
//                }
//            }
//        }
//
//        if (this.aplicacao.lastIndexOf("-") > 0) {
//            this.aplicacao = this.aplicacao.delete(this.aplicacao.lastIndexOf("-"), this.aplicacao.length());
//        }

    }
    
    public AtividadeBanco(String mensagem) {
        NumberFormat formatarNumero = NumberFormat.getNumberInstance();
        formatarNumero.setMinimumIntegerDigits(7);
        formatarNumero.setGroupingUsed(false);

        //tempo = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        this.tempo = Calendar.getInstance().getTimeInMillis();
        //Date date = new Date(tempo);
        //DateFormat formatter = new SimpleDateFormat("ss.SSS");
        //formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        //tempoFormatado = formatter.format(date);
        this.mensagem = mensagem;
        //this.id = new StringBuffer(formatarNumero.format(id) + "/" + formatarNumero.format(idConexao));
    }
    
    @Override
    public String toString() {
        XStream oXStream = new XStream(new DomDriver());
        oXStream.autodetectAnnotations(true);
        return oXStream.toXML(this);
    }
}
