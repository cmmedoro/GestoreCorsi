package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	//metodo statico perchè vogliamo che l'unico punto in cui accediamo alla connessione sia qui
	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/iscritticorsi?user=root&password=MDB.123";
		try {
			Connection conn = DriverManager.getConnection(url);
			return conn;
		}catch(SQLException e) {
			System.out.println("ERRORE di connessione al database");
			e.printStackTrace();
			return null;
		}
	}
}
