package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {

	public List<Corso> getCorsiByPeriodo(int periodo) {
		String sql = "SELECT * "
				+ "FROM corso "
				+ "WHERE pd = ?";
		List<Corso> result = new ArrayList<Corso>();
		try {
			Connection conn = ConnectDB.getConnection(); //apro connessione col database
			//creo statement con cui eseguire la query
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo); //primo parametro è la posizione, il secondo è il valore
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.add(c);
			}
			st.close();
			rs.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			System.err.println("ERRORE nel DAO");
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Corso, Integer> getIscritti(int periodo){
		String sql = "SELECT c.codins, c.crediti, c.nome, c.pd, COUNT(*) AS n "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins = i.codins and c.pd = ? "
				+ "GROUP BY c.codins, c.crediti, c.nome, c.pd";
		Map<Corso, Integer> result = new HashMap<Corso, Integer>();
		try {
			Connection conn = ConnectDB.getConnection(); 
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo); 
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.put(c, rs.getInt("n"));
			}
			st.close();
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
