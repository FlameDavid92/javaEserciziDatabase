package it.corsobackend.provedatabase.classi;

import it.corsobackend.provedatabase.interfaces.Esame;
import it.corsobackend.provedatabase.interfaces.Indirizzo;
import it.corsobackend.provedatabase.interfaces.Studente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class StudenteClasse implements Studente {
    Connection db;
    Integer id;
    String nome;
    String cognome;
    Integer id_indirizzo;

    public StudenteClasse(Connection db,Integer id, String nome, String cognome, Integer id_indirizzo){
        this.db = db;
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.id_indirizzo = id_indirizzo;
    }

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getCognome() {
        return cognome;
    }

    @Override
    public Indirizzo getIndirizzo() {
        Statement st = null;
        try {
            st = db.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM indirizzi AS i WHERE i.id="+id_indirizzo+";");
            resultSet.next();
            return new IndirizzoClasse(db,resultSet.getInt("id"),resultSet.getString("nome"));
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
            ResultSet resultSet = st.executeQuery("SELECT * FROM esami AS e, iscrizioni AS i WHERE i.id_esame=e.id AND i.id_studente="+id+";");
            Set<Esame> esami = new HashSet<>();
            while (resultSet.next()) {
                esami.add(new EsameClasse(db,resultSet.getInt("id"),resultSet.getString("nome")));
            }
            return esami;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return nome+" "+cognome;
    }
}
