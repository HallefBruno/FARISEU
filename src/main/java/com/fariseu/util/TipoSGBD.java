package com.fariseu.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum TipoSGBD {
    
    POSTGRES("postgres","127.0.0.1","fariseu","postgres",getPassword()),
    MARIADB("postgres","127.0.0.1","fariseu","postgres",getPassword());
    
    public String sgbdPG;
    public String serverNamePG;
    public String dataBaseNamePG;
    public String userPG;
    public String passwordPG;
    
    public String sgbdMDB;
    public String serverNameMDB;
    public String dataBaseNameMDB;
    public String userMDB;
    public String passwordMDB;
    
    private TipoSGBD(String sgbd, String serverName,String dataBaseName, String user, String password) {
        
        this.sgbdPG = sgbd;
        this.serverNamePG = serverName;
        this.dataBaseNamePG = dataBaseName;
        this.userPG = user;
        this.passwordPG = password;
        
        this.sgbdMDB = sgbd;
        this.serverNameMDB = serverName;
        this.dataBaseNameMDB = dataBaseName;
        this.userMDB = user;
        this.passwordMDB = password;
        
        
    }
    
    private static String getPassword() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            if (!ip.getHostAddress().equals("192.168.1.91")) {
                return "5432";
            } else {
                return "91696192";
            }
        } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        }
    }
}
