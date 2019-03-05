package com.fariseu.controller;

import com.fariseu.datatable.GenericaDataTable;
import com.fariseu.genericoservlet.GenericoServlet;
import com.fariseu.negocio.NPessoa;
import com.fariseu.util.Excecao;
import com.fariseu.util.TipoRetorno;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Brno
 */
public class ServletPessoaCtrl extends GenericoServlet {
    
    private NPessoa oNPessoa;
    
    
    public ServletPessoaCtrl() {
    }

    @Override
    public void init() throws ServletException {
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, UnknownHostException, Excecao, IOException {
        super.tipoRetorno(TipoRetorno.JSON);
        super.configuraCabecalho(response);
        try(Connection oConn = (Connection) request.getAttribute("conexao")) {
            oNPessoa = new NPessoa(oConn);
            response.getWriter().write(listarPessoas());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public String listarPessoas() throws Excecao, ServletException, UnknownHostException, IOException {
        return new GenericaDataTable().listaGson(oNPessoa.getListaPessoa());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, UnknownHostException {
        try {
            processRequest(req, resp);
        } catch (Excecao ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, UnknownHostException {
        try {
            processRequest(req, resp);
        } catch (Excecao ex) {
            throw new ServletException(ex);
        }
    }
}
