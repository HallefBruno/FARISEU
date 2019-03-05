
package com.fariseu.entidade;

import com.fariseu.anotacoes.Coluna;
import com.fariseu.anotacoes.Entidade;
import com.fariseu.anotacoes.Tempo;
import com.fariseu.util.DataEnum;
import java.sql.Timestamp;

/**
 *
 * @author hallef.sud
 */
@Entidade(nome = "pessoa",tabelaSchema = "public")
public class EPessoa {
    
    private Integer id;
    @Coluna
    private String nome;
    @Coluna(name = "sobre_nome")
    private String sobreNome;
    @Coluna
    private String email;
    @Coluna
    private String sexo;
    @Coluna
    private String rg;
    @Coluna
    private String cpf;
    
    @Coluna(name = "data_registro")
    @Tempo(data = DataEnum.DATA_HORA)
    private Timestamp dataRegistro;

    public EPessoa() {
    }

    public EPessoa(String nome, String sobreNome, String email, String sexo, String rg, String cpf, Timestamp dataRegistro) {
        
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.email = email;
        this.sexo = sexo;
        this.rg = rg;
        this.cpf = cpf;
        this.dataRegistro = dataRegistro;
    }

    public EPessoa(Integer id, String nome, String sobreNome, String email, String sexo, String rg, String cpf, Timestamp dataRegistro) {
        this.id = id;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.email = email;
        this.sexo = sexo;
        this.rg = rg;
        this.cpf = cpf;
        this.dataRegistro = dataRegistro;
    }
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Timestamp getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Timestamp dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

}
