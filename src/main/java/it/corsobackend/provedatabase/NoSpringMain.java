package it.corsobackend.provedatabase;

import it.corsobackend.provedatabase.classi.ServiceClasse;
import it.corsobackend.provedatabase.interfaces.Esame;
import it.corsobackend.provedatabase.interfaces.Indirizzo;
import it.corsobackend.provedatabase.interfaces.Studente;

import java.sql.*;
import java.util.Set;

public class NoSpringMain {
    public static void main(String[] args) {
        Connection db = null;
        try {
            db = DriverManager.getConnection("jdbc:postgresql://localhost:5433/corsobackendtree", "postgres", Password.PASSWORD.getPassword());
            Statement st = db.createStatement();
            creaTabelle(st);
            inserisciDati(db,st);

            ServiceClasse serviceClasse = new ServiceClasse(db);
            Set<Studente> studenti = serviceClasse.getStudenti();
            Set<Esame> esami = serviceClasse.getEsami();
            Set<Indirizzo> indirizzi = serviceClasse.getIndirizzi();

            System.out.println("STUDENTI: "+studenti);
            System.out.println("ESAMI: "+esami);
            System.out.println("INDIRIZZI: "+indirizzi);

            Studente studente21 = studenti.stream().filter(s-> s.id() == 21).findAny().get();
            Indirizzo indirizzoStudente21 = studente21.getIndirizzo();
            Set<Esame> esamiStudente21 = studente21.getEsami();
            System.out.println("INDIRIZZO Studente21: "+indirizzoStudente21);
            System.out.println("ESAMI Studente21: "+esamiStudente21);

            Set<Studente> compagniIndirizzoS21 = indirizzoStudente21.getStudenti();
            System.out.println("Compagni indirizzo studente 21 (incluso s.21): "+compagniIndirizzoS21);

            Esame esame = esamiStudente21.stream().findAny().get();
            Set<Studente> studentiEsame = esame.getStudenti();
            System.out.println("Studenti esame "+esame.getNome()+": "+studentiEsame);

            eliminaTabelle(st);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.print("\nFine :P");
    }

    private static void creaTabelle(Statement st) throws SQLException {
        st.executeUpdate("CREATE TABLE indirizzi(ID BIGSERIAL PRIMARY KEY, NOME TEXT NOT NULL, unique(NOME));");
        st.executeUpdate("CREATE TABLE studenti(ID BIGSERIAL PRIMARY KEY , NOME TEXT NOT NULL, COGNOME TEXT NOT NULL, ID_INDIRIZZO BIGINT, FOREIGN KEY (ID_INDIRIZZO) REFERENCES indirizzi(ID) MATCH FULL ON DELETE CASCADE, unique(NOME,COGNOME));");
        st.executeUpdate("CREATE TABLE esami(ID BIGSERIAL PRIMARY KEY, NOME TEXT NOT NULL, DATA DATE, unique(NOME,DATA));");
        st.executeUpdate("CREATE TABLE iscrizioni(ID_STUDENTE BIGINT, ID_ESAME BIGINT, PRIMARY KEY (ID_STUDENTE,ID_ESAME), FOREIGN KEY (ID_STUDENTE) REFERENCES studenti(ID) MATCH FULL ON DELETE CASCADE, FOREIGN KEY (ID_ESAME) REFERENCES esami(ID) MATCH FULL ON DELETE CASCADE);");
    }

    private static void inserisciDati(Connection db, Statement st) throws SQLException {
        st.executeUpdate("INSERT INTO public.indirizzi(\"nome\") VALUES ('INGEGNERIA')");
        st.executeUpdate("INSERT INTO public.indirizzi(\"nome\") VALUES ('MATEMATICA')");

        PreparedStatement pst = db.prepareStatement(
                "INSERT INTO public.studenti(\"nome\", \"cognome\", \"id_indirizzo\") VALUES (?, ?, ?);");
        for(int i=1; i<=20; i++){
            pst.setString(1,"NomeI"+i);
            pst.setString(2,"CognomeI"+i);
            pst.setInt(3,1);
            pst.executeUpdate();
        }
        for(int i=1; i<=20; i++){
            pst.setString(1,"NomeM"+i);
            pst.setString(2,"CognomeM"+i);
            pst.setInt(3,2);
            pst.executeUpdate();
        }

        st.executeUpdate("INSERT INTO public.esami(\"nome\",\"data\") VALUES ('Linguaggi e compilatori', '2020/12/05')");
        st.executeUpdate("INSERT INTO public.esami(\"nome\",\"data\") VALUES ('Analisi 1', '2020/11/30')");
        st.executeUpdate("INSERT INTO public.iscrizioni(\"id_studente\",\"id_esame\") VALUES (2, 1)");
        st.executeUpdate("INSERT INTO public.iscrizioni(\"id_studente\",\"id_esame\") VALUES (21, 2)");
        st.executeUpdate("INSERT INTO public.iscrizioni(\"id_studente\",\"id_esame\") VALUES (25, 2)");
    }

    private static void eliminaTabelle(Statement st) throws SQLException {
        st.executeUpdate("DROP TABLE iscrizioni");
        st.executeUpdate("DROP TABLE studenti");
        st.executeUpdate("DROP TABLE indirizzi");
        st.executeUpdate("DROP TABLE esami");
    }
}


