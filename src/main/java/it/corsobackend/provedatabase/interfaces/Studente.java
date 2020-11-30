package it.corsobackend.provedatabase.interfaces;

import java.util.Set;

public interface Studente {
  public Integer id();
  public String getNome();
  public String getCognome();
  public Indirizzo getIndirizzo();
  public Set<Esame> getEsami();
}
