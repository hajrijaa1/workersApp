/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baza;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 *
 * @author Korisnik
 */
public class Konekcija {
     
    private static final String korisnik = "root";
    private static final String sifra = "root";
    private static final String kon = "jdbc:mysql://localhost:3306/radnik?serverTimezone=UTC";
    public Connection veza = null;
    public Konekcija() throws ClassNotFoundException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            veza = DriverManager.getConnection(kon,korisnik,sifra);
        }
        catch(SQLException e){
            System.err.println(e);
        }
    }

    
    public void unosRadnika (String ime, String prezime, int godinaRodjenja) throws SQLException{
        Statement upitBaza = (Statement) veza.createStatement();
        String upit = "INSERT INTO osoba (ime, prezime, godinaRodjenja) VALUES ('"+ime+"', '"+prezime+"', '"+godinaRodjenja+"')";
        try{
            upitBaza.executeUpdate(upit);
        } 
        catch (SQLException e) {
            System.err.println(e);
        }
    }
    
    public void unosRadnogMjesta(String naziv, LocalDate datumZaposlenja) throws SQLException{
         Date d=Date.valueOf(datumZaposlenja);
        Statement upitBaza = (Statement) veza.createStatement();
        String upit = "INSERT INTO radnoMjesto(naziv, datumZaposlenja) VALUES ('"+naziv+"', '"+d+"')";
        try{
            upitBaza.executeUpdate(upit);
        } 
        catch (SQLException e) {
            System.err.println(e);
        }
    }
    
     public ResultSet ispisRadnika() throws SQLException 
    {
        Statement upitBaza=(Statement) veza.createStatement();
        ResultSet rezultat=null;
        try
        {
            rezultat=upitBaza.executeQuery("select osoba.idOsoba, osoba.ime, osoba.prezime, osoba.godinaRodjenja from osoba"); 
            return rezultat;
        }
        catch(SQLException e)
        {
            System.err.println(e);
        }
        return rezultat;
    }
     
       public ResultSet pretragaRadnika(String ime) throws SQLException 
    {
        Statement upitBaza=(Statement) veza.createStatement();
        String upit = "SELECT ime, prezime, godinaRodjenja, naziv from radnoMjesto inner join osoba on osoba.idOsoba=radnoMjesto.Osoba WHERE osoba.ime='"+ime+"' ";
        
        //ResultSet koji vraca funkcija
        ResultSet rs=null;
        try {
            //preuzimanje podataka iz baze u resultSet
            rs=upitBaza.executeQuery(upit);
        } catch (SQLException e) {
            //u slucaju greske ispisuje se error
            System.err.println(e);
        }
        return rs;
    }
}
