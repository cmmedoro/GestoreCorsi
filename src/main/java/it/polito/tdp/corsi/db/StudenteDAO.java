package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import it.polito.tdp.corsi.model.Divisione;
import it.polito.tdp.corsi.model.Studente;

public class StudenteDAO {

	public List<Studente> getStudentiByCorso(String codins) {
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND i.codins = ?";
		List<Studente> result = new ArrayList<Studente>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins); 
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				result.add(s);
			}
			st.close(); //chiusura delle risorse
			rs.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			System.err.println("ERRORE nel DAO");
			e.printStackTrace();
			return null;
		}
	}
	/*
	public Map<String, Integer> getStudenti(String codins){
		String sql = "SELECT s.CDS, COUNT(*) AS n "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND i.codins = '01KSUPG' AND s.CDS<> \"\" "
				+ "GROUP BY s.CDS;";
		Map<String,Integer> result = new HashMap<String,Integer>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins); 
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				
				result.put(rs.getString("CDS"), rs.getInt("n"));
			}
			st.close(); //chiusura delle risorse
			rs.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			System.err.println("ERRORE nel DAO");
			e.printStackTrace();
			return null;
		}
	}*/
	public List<Divisione> getDivisioneStudenti(String codins){
		String sql = "SELECT s.CDS, COUNT(*) AS n "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND i.codins = ? AND s.CDS <> '' "
				+ "GROUP BY s.CDS;";
		List<Divisione> result = new ArrayList<Divisione>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins); 
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				result.add(new Divisione(rs.getString("CDS"),rs.getInt("n")));
			}
			st.close(); //chiusura delle risorse
			rs.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			System.err.println("ERRORE nel DAO");
			e.printStackTrace();
			return null;
		}
	}
	
}
