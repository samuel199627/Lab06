package it.polito.tdp.meteo.model;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestModel {

	public static void main(String[] args) throws ParseException {
		
		Model m = new Model();

		//m.getAllRilevamenti();
		//m.getAllRilevamentiLocalitaMese(3, "torino");
		//List<Rilevamento> ritorno = m.getAllRilevamenti();
		
		/*
		System.out.println(m.getUmiditaMedia(12));
		
		System.out.println(m.trovaSequenza(5));
		*/
		
		//prova sulle date
		
		/*
		
		//'yyyy-mm-dd'
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd"); 
		String dataS="2020-01-20";
		Date data=formatter.parse(dataS);
		System.out.println(data);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		//int day=data.getDay();
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println(""+day);
		
		*/
		/*
		List<Rilevamento> ritorno=m.getAllRilevamentiMesePrimiGiorni(2);
		int conta=0;
		Calendar calendar;
		for(Rilevamento r: ritorno) {
			conta++;
			calendar=Calendar.getInstance();
			calendar.setTime(r.getData());
			System.out.println("\n"+r.getLocalita()+" "+calendar.get(Calendar.DAY_OF_MONTH)+" "+r.getUmidita());
		}
		
		//verifica per sapere se con un break dentro un if di un for esco anche dal for
		System.out.println("\nVERIFICA FOR:\n");
		int i;
		for(i=0;i<5;i++) {
			if(i>3) {
				break;
			}
		}
		System.out.println(""+i); //4
		
		
		switch("Torino".toLowerCase()) {
		case "genova":
				System.out.print("ciao1");
				break;
		case "milano":
				System.out.print("ciao2");
				break;
		// eventuali altri case
		case "torino":
				System.out.print("ciao3");
		default:
		}
		*/
		
		
		List<Rilevamento> parziale=m.trovaSequenza(2);
		for(Rilevamento r: parziale) {
			
			System.out.println(" "+r.getLocalita()+" "+r.getData()+" "+r.getUmidita());
			
			
		}
		
	}

}
