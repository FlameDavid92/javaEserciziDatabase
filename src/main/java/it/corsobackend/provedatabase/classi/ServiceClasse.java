package it.corsobackend.provedatabase.classi;

import it.corsobackend.provedatabase.interfaces.Esame;
import it.corsobackend.provedatabase.interfaces.Indirizzo;
import it.corsobackend.provedatabase.interfaces.Service;
import it.corsobackend.provedatabase.interfaces.Studente;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceClasse implements Service {
    Connection db;

    public ServiceClasse(Connection db) {
        this.db = db;
    }

    @Override
    public Set<Studente> getStudenti() {
        Statement st = null;
        try {
            st = db.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM studenti");

            Set<Studente> studenti = new HashSet<>();
            while (resultSet.next()) {
                studenti.add(new StudenteClasse(db, resultSet.getInt("id"), resultSet.getString("nome"),
                        resultSet.getString("cognome"), resultSet.getInt("id_indirizzo")));
            }
            return studenti;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<Esame> getEsami() {
        Statement st = null;
        try {
            st = db.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM esami");

            Set<Esame> esami = new HashSet<>();
            while (resultSet.next()) {
                esami.add(new EsameClasse(db,resultSet.getInt("id"), resultSet.getString("nome")));
            }
            return esami;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<Indirizzo> getIndirizzi() {
        Statement st = null;
        try {
            st = db.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM indirizzi");

            Set<Indirizzo> indirizzi = new HashSet<>();
            while (resultSet.next()) {
                indirizzi.add(new IndirizzoClasse(db,resultSet.getInt("id"), resultSet.getString("nome")));
            }
            return indirizzi;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
