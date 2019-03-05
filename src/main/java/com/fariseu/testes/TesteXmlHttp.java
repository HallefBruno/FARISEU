package com.fariseu.testes;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TesteXmlHttp implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        //context.addServlet("MeuServlet", TesteXmlHttp.class);
    }

    public void contextDestroyed(ServletContextEvent event) {
    }

    private void cabecalhoConfig(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setHeader("Cache-control", "no-cache, no-store");
        resp.setHeader("Progma", "no-cache");
        resp.setHeader("Expires", "-1");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods","POST");
        resp.setHeader("Access-Control-Allow-Header","Content-Type");
        resp.setHeader("Access-Control-Max-Age","86400");
    }
}
