package it.corsobackend.provedatabase.interfaces;

import java.util.Set;

public interface Service {
  public Set<Studente> getStudenti();
  public Set<Esame> getEsami();
  public Set<Indirizzo> getIndirizzi();
}
