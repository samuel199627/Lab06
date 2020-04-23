package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	//andiamo ad estrapolare solo la data, la localita' e l'umidita' dal database che
	//sono i tre parametri che ci interessano di questo laboratorio e che andiamo ad estrapolare dal dataset
	public List<Rilevamento> getAllRilevamenti() {

		//System.out.println("CIAO DAO");
		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			/*
			System.out.println("Stampo i primi 10 rilevamenti che abbiamo: ");
			int conta=0;
			for(Rilevamento r:rilevamenti) {
				conta++;
				if(conta<10) {
					System.out.println(""+r.getLocalita()+" "+r.getData()+" "+r.getUmidita());
				}
			}
			*/
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
		/*
		 	La seguente istruzione sql ad esempio estrae tutte le righe del database che contengono marzo nella loro data:
		 	
		 	SELECT *
			from situazione
			where extract(month from data)=3
			
			mentre la seguente le estrae per la localita' di torino:
			
			SELECT *
			from situazione
			where extract(month from data)=3 and localita='torino'
			
		 */
		
		final String sql = "SELECT Localita, Data, Umidita FROM situazione where extract(month from data)=? and localita=? ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			st.setString(2, localita);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			
			System.out.println("Stampo i primi 10 rilevamenti che abbiamo: ");
			int conta=0;
			for(Rilevamento r:rilevamenti) {
				conta++;
				if(conta<10) {
					System.out.println(""+r.getLocalita()+" "+r.getData()+" "+r.getUmidita());
				}
			}
			
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	public List<Rilevamento> getAllRilevamentiMese(int mese) {
		
		
		final String sql = "SELECT Localita, Data, Umidita FROM situazione where extract(month from data)=? ";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			//st.setString(2, localita);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			/*
			System.out.println("Stampo i primi 10 rilevamenti che abbiamo: ");
			int conta=0;
			for(Rilevamento r:rilevamenti) {
				conta++;
				if(conta<10) {
					System.out.println(""+r.getLocalita()+" "+r.getData()+" "+r.getUmidita());
				}
			}
			*/
			
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	public List<Rilevamento> getAllRilevamentiMesePrimiGiorni(int mese) {
		
		
		//ho tutti i rilevamenti sono del mese che ho passato come parametro nei primi 15 giorni
		final String sql = "SELECT Localita, Data, Umidita FROM situazione where extract(month from data)=? and extract(day from data)<=15 order by data asc";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			//st.setString(2, localita);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			/*
			System.out.println("Stampo i primi 10 rilevamenti che abbiamo: ");
			int conta=0;
			for(Rilevamento r:rilevamenti) {
				conta++;
				if(conta<10) {
					System.out.println(""+r.getLocalita()+" "+r.getData()+" "+r.getUmidita());
				}
			}
			*/
			
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}


}
