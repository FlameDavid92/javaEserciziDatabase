package it.corsobackend.provedatabase.classi;

import it.corsobackend.provedatabase.interfaces.Esame;
import it.corsobackend.provedatabase.interfaces.Studente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class EsameClasse implements Esame {
    Connection db;
    Integer id;
    String nome;

    public EsameClasse(Connection db, Integer id, String nome){
        this.id = id;
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
            ResultSet resultSet = st.executeQuery("SELECT * FROM studenti AS s, iscrizioni AS i WHERE i.id_studente=s.id AND i.id_esame="+id+";");
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
