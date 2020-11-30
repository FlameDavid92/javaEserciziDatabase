package it.corsobackend.provedatabase.classi;

import it.corsobackend.provedatabase.interfaces.Indirizzo;
import it.corsobackend.provedatabase.interfaces.Studente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class IndirizzoClasse implements Indirizzo {
    Connection db;
    Integer id;
    String nome;

    public IndirizzoClasse(Connection db,Integer id, String nome){
        this. id = id;
        this.db = db;
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public Set<Studente> getStudenti() {
        Statement st = null;
        try {
            st = db.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM studenti AS s WHERE s.id_indirizzo="+id+";");
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
    public String toString() {
        return nome;
    }
}
