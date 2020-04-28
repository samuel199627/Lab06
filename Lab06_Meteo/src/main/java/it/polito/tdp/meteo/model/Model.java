package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	//sono le costanti che compaiono nel programma da implementare e che sono fisse
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	//le variabili di conteggio della citta' si potevano trovare dentro la classe citta', ma ho provato
	//qui per avere tutto piu' chiaro e visibile
	
	//queste 3 contano i giorni totali in una citta'
	int visiteTotG;
	int visiteTotM;
	int visiteTotT;
	
	//variabili che contano il numero di visite per ogni citta' in quanto sappiamo che non possono essere visitate
	//piu' di due volte
	int numVisiteG;
	int numVisiteM;
	int numVisiteT;
	
	//queste 3 contano i giorni consecutivi nella citta' da quando sono arrivato
	int visiteG;
	int visiteM;
	int visiteT;
	//sappiamo se siamo ancora nel range dei primi 3 giorni di visita
	//boolean nuovaVisita;
	
	//indica il giorno che stiamo decidendo, ma non e' necessaria come variabile globale perche' vive dentro ogni
	//chiamata della ricorsione
	//int livello;
	
	//variabile non usata, ma originariamente era pensata per evitare di scorrere posizioni inutili
	//dai rilevamenti importati
	int posizione;
	
	//localita' in cui ci troviamo oggi che serve per distinguere se ci muoviamo in una nuova citta'
	//oppure se stiamo rimanendo dove gia' eravamo
	//String localitaAttuale;
	
	//mi serve per estrarre il giorno dalle data
	Calendar calendar;
	
	
	List<Rilevamento> rilevamentiPrimiGiorni;
	List<Rilevamento> parziale;
	List<Rilevamento> soluzione;
	
	int conta=0;
	int contaSoluzione=0;
	int costoBest;
	int costoParziale;
	
	String precedente;
	String attuale;
	
	boolean riscontro;
	
	
	boolean uscita;
	boolean cambio;
	boolean accettato;
	
	MeteoDAO dao = new MeteoDAO();

	public Model() {
		
		
	}

	/*
	 //nella mia idea ho ritenuto meglio ritornare un vettore con le 3 medie separate, ma effettivamente
	 //bastava anche anche una stringa con le medie salvate direttamente, ma almeno ho meglio indicato
	 //quello che sono andato a fare
	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {
		return "TODO!";
	}
	*/
	
	//metodo che setta i parametri della ricorsione e richiama la funzione di ricerca annidata
	/*
	 	Si trovi la sequenza delle città da visitare nei primi 15 giorni del mese selezionato, tale da minimizzare il costo complessivo.
	 	Abbiamo una misurazione per ogni citta' e dobbiamo controllare i giorni in maniera correttascorrendo i rilevamenti che 
	 	selezionato il mese faccio ritornare in ordine di data.
	 	
	 	Commento qui di seguito la soluzione che e' stata presentata a lezione di questo laboratorio.
	 	Si potevano inserire direttamente tutti i rilevamenti di una citta' all'interno della sua classe che la rappresenta. In 
	 	questa maniera ad ogni chiamata della ricorsione, scorriamo tutte le citta' (con un ciclo) ed esploriamo per ogni data
	 	tutte e tre le citta' da inserire (sempre se per la data indicata dal livello abbiamo un rilevamento per la citta' che 
	 	stiamo analizzando e sempre se possibile per le condizioni imposte dal problema). Per ogni citta' nel ciclo che vado
	 	ad analizzare richiamiamo una funzione che restituisce vero o falso sull'aggiunta possibile o meno. In questa funzione
	 	per prima cosa scorriamo parziale e contiamo per la citta' che abbiamo in analisi quante occorrenze totali fino ad ora.
	 	Controlliamo a questo punto tutte le condizioni: la dimensione di parziale che se e' superiore a 3 dobbiamo controllare
	 	gli ultimi 3 elementi inseriti e se sono uguali possiamo cambiare citta', mentre se l'ultimo elemento che era stato
	 	inserito era la stessa citta' che stiamo analizzando possiamo tranquillamente inserirla, tanto e' quando andremo in un'
	 	altra citta' che esploreremo il caso del cambio. Ci sono poi anche le condizioni sul numero massimo di giorni in una citta'
	 	di cui tenere conto.
	 	
	 	
	 	
	 */
	public List<Rilevamento> trovaSequenza(int mese) {
		rilevamentiPrimiGiorni=new ArrayList<>();
		parziale=new ArrayList<>();
		soluzione=new ArrayList<>();
		rilevamentiPrimiGiorni=this.getAllRilevamentiMesePrimiGiorni(mese);
		visiteTotG=0;
		visiteTotM=0;
		visiteTotT=0;
		visiteG=0;
		visiteM=0;
		visiteT=0;
		conta=0;
		numVisiteG=0;
		numVisiteM=0;
		numVisiteT=0;
		costoBest=0;
		costoParziale=0;
		//mi indica in che giorno siamo, ma non e' necessaria come variabile
		//livello=0;
		
		//ci dice da che posizione iniziare a scorrere il vettore con tutti i rilevamenti dei primi 15 giorni importati
		//per evitare di scorrere elementi che abbiamo gia' analizzato in precedenza dato che li abbiamo ordinati per data 
		//in realta' non si puo' usare perche' e' un problema il suo aggiornamento per quando si ritorna indietro con le ricorsioni
		//analizzando altri rami. 
		//Per cui per il momento la lascio in sospeso
		posizione=0;
		
		//localitaAttuale="inizio";
		
		//nuovaVisita=false;
		ricorsiva(parziale, 0, "inizio",false);
		
		System.out.println("\nFINITO ANALISI con costoBest: "+costoBest);
		
		return soluzione;
	}
	
	public void ricorsiva(List<Rilevamento> parziale, int livello, String localitaAttuale, boolean nuovaVisita) {
		
		
		
		
		if(livello==15) {
			//siamo arrivati alla fine e abbiamo una possibile sequenza da confrontare con il costo totale con
			//la migliore sequenza di rilevamenti che avevamo fino ad adesso
			conta++;
			precedente="";
			costoParziale=0;
			
			//System.out.println("\nSOLUZIONE SEQUENZA "+conta+":\n");
			contaSoluzione=0;
			for(Rilevamento r: parziale) {
				contaSoluzione++;
				if(!precedente.equals(r.getLocalita())) {
					costoParziale=costoParziale+100;
				}
				costoParziale=costoParziale+r.getUmidita();
				//System.out.println(" N."+contaSoluzione+" "+r.getLocalita()+" "+r.getData()+" "+r.getUmidita());
				precedente=r.getLocalita();
				
			}
			//System.out.println(" Costo Parziale: "+costoParziale);
			
			if(conta==1) {
				costoBest=costoParziale;
				soluzione=new ArrayList(parziale);
			}
			if(costoParziale<costoBest) {
				costoBest=costoParziale;
				soluzione=new ArrayList(parziale);
			}
			//System.out.println(" Costo Best: "+costoBest);
			/*
			System.out.println("\nStampa Variabili: \n");
			System.out.println("nuovaVisita: "+nuovaVisita);
			System.out.println("localitaAttuale: "+localitaAttuale);
			System.out.println("VisiteTotG: "+visiteTotG);
			System.out.println("VisiteTotM: "+visiteTotM);
			System.out.println("VisiteTotT: "+visiteTotT);
			System.out.println("VisiteG: "+visiteG);
			System.out.println("VisiteM: "+visiteM);
			System.out.println("VisiteT: "+visiteT);
			System.out.println("numVisiteG: "+numVisiteG);
			System.out.println("numVisiteM: "+numVisiteM);
			System.out.println("numVisiteT: "+numVisiteT+"\n");
			*/
			
			
			return;
		}
		
		//siamo in uno dei giorni intemedi e quindi dobbiamo decidere dove andare domani come citta'.
		//Precisiamo che al livello zero siamo ancora in partenza e dobbiamo scegliere dove recarci domani che e' il giorno 1
		//al massimo abbiamo un rilevamento al giorno per citta' quindi in questo livello, cioe' per scegliere
		//dove andare domani posso solo scegliere tra tre opzioni, cioe' le tre citta'. 
		//livello ora sta puntando a domani, mentre le variabili di conteggio puntano ad oggi.
		livello++;
		//System.out.println("\nLIVELLO "+livello);
		
		if(nuovaVisita==true) {
			//incremento le visite consecutive per la citta' in cui sono e arrivo a tre
			//giorni consecutivi metto la nuova visita a false in quanto nel giro dopo
			//potro' cambiare citta' e non sono piu' vincolato a stare qui
			
			switch(localitaAttuale.toLowerCase()) {
			case "genova":
					if(visiteG==2) {
						//gia' due visite le ho fatte e domani sara' il terzo giorno di fila in questa citta'
						//e quindi da domani posso scegliere il giorno dopo liberamente
						//System.out.println("\n\nFALSO\n\n");
						nuovaVisita=false;
					}
					visiteG++;
					visiteTotG++;
					break;
			case "milano":
					if(visiteM==2) {
						nuovaVisita=false;
					}
					visiteM++;
					visiteTotM++;
					break;
			// eventuali altri case
			case "torino":
					if(visiteT==2) {
						nuovaVisita=false;
					}
					visiteT++;
					visiteTotT++;
			default:
			}
			
			//cerco in domani la stessa localita' in cui sono adesso perche' devo stare almeno 3 giorni nella citta' in cui sono
			//se pero' ho i dati incompleti, mi metto un booleano che mi dice che domani non ho corrispondenza in dove dovrei stare
			//quindi e' come se annullassi questo passaggio, ritorno indietro il livello e metto come se non dovessi stare nella 
			//stessa citta', cosi' non entrera' piu' in questo blocco di nuova visita
			riscontro=false;
			for(int i=0;i<rilevamentiPrimiGiorni.size();i++) {
				calendar=Calendar.getInstance();
				calendar.setTime(rilevamentiPrimiGiorni.get(i).getData());
				if(calendar.get(Calendar.DAY_OF_MONTH)==livello&&rilevamentiPrimiGiorni.get(i).getLocalita().equals(localitaAttuale)) {
					riscontro=true;
					parziale.add(rilevamentiPrimiGiorni.get(i));
					break;
				}
			}
			
			if(riscontro==true) {
				ricorsiva(parziale,livello, localitaAttuale, nuovaVisita);
				
				parziale.remove(parziale.size()-1);
				
				//rimetto a posto le variabili di conteggio

				switch(localitaAttuale.toLowerCase()) {
				case "genova":
						visiteG--;
						visiteTotG--;
						break;
				case "milano":
						visiteM--;
						visiteTotM--;
						break;
				// eventuali altri case
				case "torino":
						visiteT--;
						visiteTotT--;
				default:
				}
				
				//non ci sono altre strade da esplorare perche' 3 giorni consecutivi li devo fare quindi torno indietro
				return;
			}
			else {
				livello--;
				nuovaVisita=false;
				//rimetto a posto queste perche' non avremo di fatto la visita domani nella stessa citta' di oggi
				switch(localitaAttuale.toLowerCase()) {
				case "genova":
						visiteG--;
						visiteTotG--;
						break;
				case "milano":
						visiteM--;
						visiteTotM--;
						break;
				// eventuali altri case
				case "torino":
						visiteT--;
						visiteTotT--;
				default:
				}
				
				ricorsiva(parziale,livello, localitaAttuale, nuovaVisita);
				//non ho nulla da togliere perche' nulla e' stato inserito
				//parziale.remove(parziale.size()-1);
				
				//rimetto a posto le variabili di conteggio

				
				
				//non ci sono altre strade da esplorare perche' 3 giorni consecutivi li devo fare quindi torno indietro
				return;
			}
			
			
			
		}
		else {
			/*
			for(int i=posizione; i<posizione+3; i++) {
				
			}
			*/
			//System.out.println("\nNON SIAMO IN UNA NUOVA VISITA!");
			for(int i=0; i<rilevamentiPrimiGiorni.size(); i++) {
				//se sono qui significa che siamo nella condizione di poter cambiare citta'
				//in quanto esauriti i tre giorni della nuova visita.
				//Dobbiamo scegliere la data giusta, che corrisponde al prossimo giorno che e'
				//indicato in livello.
				//Avendo i rilevamenti ordinati per date crescenti, allora appena il giorno
				//supera il livello usciamo dal ciclo perche' significa che stiamo andando in
				//giorni futuri da quelli che dobbiamo scegliere e non ci riguardano ancora.
				
				//per andare in una citta' dobbiamo controllare che le visite per quella non
				//siano gia' state 2, perche' in quel caso non ci possiamo andare dato che solo
				//per 6 giorni possiamo stare in una citta' e poiche' una volta che ci muoviamo
				//non possiamo stare meno di tre giorni, se ho gia' avuto due visite allora
				//non posso piu' andarci.
				
				//dobbiamo inoltre controllare se la citta' in cui vado non sia quella attuale
				//che significa che rimaniamo dove siamo oggi: in questo caso dobbiamo controllare
				//che il numero di giorni non superi 6 e soprattutto NON dobbiamo mettere a true 
				//la nuova visita perche' di fatto non ci siamo mossi da nessuna parte.
				
				//System.out.println("\nANALIZZIAMO LA LOCALITA' DI "+rilevamentiPrimiGiorni.get(i).getLocalita()+" IN DATA "+rilevamentiPrimiGiorni.get(i).getData());
				calendar=Calendar.getInstance();
				calendar.setTime(rilevamentiPrimiGiorni.get(i).getData());
				
				if(calendar.get(Calendar.DAY_OF_MONTH)<livello) {
					//sono in giorni precedenti a quello che devo fissare
				}
				else {
					if(calendar.get(Calendar.DAY_OF_MONTH)>livello) {
						//siamo gia' in giorni successivi e dunque non dobbiamo piu' esplorare nessuna strada
						//per cui usciamo dal for
						break;
					}
					
					//qui sono in un giorno giusto e devo prima verificare se per questa localita' posso inserirla
					//nella visita di giorno indicato da livello che sarebbe domani
					
					//System.out.println("\nGIORNO CORRISPONDENTE! ");
					
					uscita=false;
					
				
					
						
					switch(localitaAttuale.toLowerCase()) {
					case "genova":
							if(numVisiteG==2||visiteTotG==6) {
								//scartiamo la scelta
								uscita=true;
							}
							break;
					case "milano":
							if(numVisiteM==2||visiteTotM==6) {
								uscita=true;
							}
							break;
					// eventuali altri case
					case "torino":
							if(numVisiteT==2||visiteTotT==6) {
								uscita=true;
							}
							break;
					case "inizio":
						
					default:
					}
					
					
					
					cambio=false;
					accettato=false;
					
					//qui esploro la prossima strada in quanto ho ancora giorni e visite disponibili
					if(rilevamentiPrimiGiorni.get(i).getLocalita().toLowerCase().equals(localitaAttuale.toLowerCase()) ) {
						
						if(uscita == false) {
							accettato=true;
							nuovaVisita=false;
							
							switch(localitaAttuale.toLowerCase()) {
							case "genova":
									visiteG++;
									visiteTotG++;
									break;
							case "milano":
									visiteM++;
									visiteTotM++;
									break;
							// eventuali altri case
							case "torino":
									visiteT++;
									visiteTotT++;
							default:
							}
						}
						
					}
					else {
						//System.out.println("\nLOCALITA' DIVERSA DALL'ULTIMA! ");
						cambio=true;
						//mi salvo la citta' dove saro' domani e per i prossimi 3 giorni
						//localitaAttuale=rilevamentiPrimiGiorni.get(i).getLocalita();
						
						switch(rilevamentiPrimiGiorni.get(i).getLocalita().toLowerCase()) {
						case "genova":
								if(visiteTotG==6||numVisiteG==2) {
									accettato=false;
								}
								else {
									//System.out.println("\nNUOVA VISITA GENOVA CAMBIO!");
									localitaAttuale=rilevamentiPrimiGiorni.get(i).getLocalita();
									nuovaVisita=true;
									//costoParziale=costoParziale+100;
									accettato=true;
									visiteTotG++;
									numVisiteG++;
									visiteG=1;
								}
								
								break;
						case "milano":
								if(visiteTotM==6||numVisiteM==2) {
									accettato=false;
								}
								else {
									//System.out.println("\nNUOVA VISITA MILANO CAMBIO!");
									localitaAttuale=rilevamentiPrimiGiorni.get(i).getLocalita();
									nuovaVisita=true;
									//costoParziale=costoParziale+100;
									accettato=true;
									visiteTotM++;
									numVisiteM++;
									visiteM=1;
								}
								
								break;
						// eventuali altri case
						case "torino":
								if(visiteTotT==6||numVisiteT==2) {
									accettato=false;
								}
								else {
									//System.out.println("\nNUOVA VISITA TORINO CAMBIO!");
									localitaAttuale=rilevamentiPrimiGiorni.get(i).getLocalita();
									nuovaVisita=true;
									accettato=true;
									//costoParziale=costoParziale+100;
									visiteTotT++;
									numVisiteT++;
									visiteT=1;
								}
								
						default:
						}
						
					}
					
					if( accettato==true ) {
						parziale.add(rilevamentiPrimiGiorni.get(i));
						
						ricorsiva(parziale, livello,localitaAttuale,nuovaVisita);
						
						//togliamo l'ultimo che avevamo inserito per il giorno attuale per esplorare altre strade possibili
						parziale.remove(parziale.size()-1);
						
						//rimetto a posto le variabili di conteggio
						switch(localitaAttuale.toLowerCase()) {
						case "genova":
								visiteTotG--;
								if(nuovaVisita==true) {
									numVisiteG--;
									visiteG=1;
									//costoParziale=costoParziale-100;
									nuovaVisita=false;
								}
								else {
									visiteG--;
								}
								
								break;
						case "milano":
								visiteTotM--;
								if(nuovaVisita==true) {
									numVisiteM--;
									visiteM=1;
									//costoParziale=costoParziale-100;
									nuovaVisita=false;
								}
								else {
									visiteM--;
								}
								
								break;
						// eventuali altri case
						case "torino":
								visiteTotT--;
								if( nuovaVisita==true) {
									numVisiteT--;
									//costoParziale=costoParziale-100;
									visiteT=1;
									nuovaVisita=false;
								}
								else {
									visiteT--;
								}
						default:
						}
						
						
					}
					
					//livello non e' necessario che venga diminuita in quanto e' una variabile locale che vive e muore nella ricorsione
					//dunque quella che passo nella chiamata, viene modificata nelle ricorsioni successive, ma tecnicamente e' un'altra
					//variabile e quella che abbiamo qui non viene modificata
					
				}
				
				
			}
		}
		
		
		
	}
	
	public List<Rilevamento> getAllRilevamenti(){
		//System.out.println("CIAO");
		return dao.getAllRilevamenti();
	}
	
	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita){
		//System.out.println("CIAO");
		return dao.getAllRilevamentiLocalitaMese(mese, localita);
	}
	
	public List<Rilevamento> getAllRilevamentiMesePrimiGiorni(int mese){
		//System.out.println("CIAO");
		return dao.getAllRilevamentiMesePrimiGiorni(mese);
	}
	
	public Float[] getAllRilevamentiMese(int mese){
		//System.out.println("CIAO");
		//l'ordine di ritorno di umidita' e':
		//Genova, Milano, Torino 
		Float[] ritorna=new Float[3];
		List<Rilevamento> rilevamentiMese= new ArrayList<>();
		rilevamentiMese=dao.getAllRilevamentiMese(mese);
		int sommaG=0;
		int sommaM=0;
		int sommaT=0;
		int contaG=0;
		int contaM=0;
		int contaT=0;
		
		for(Rilevamento r: rilevamentiMese) {
			if(r.getLocalita().toLowerCase().equals("genova")) {
				contaG++;
				sommaG=sommaG+r.getUmidita();
			}
			else if(r.getLocalita().toLowerCase().equals("milano")) {
				contaM++;
				sommaM=sommaM+r.getUmidita();
			}
			else {
				contaT++;
				sommaT=sommaT+r.getUmidita();
			}
		}
		ritorna[0]=((float) sommaG)/contaG;
		ritorna[1]=((float) sommaM)/contaM;
		ritorna[2]=((float) sommaT)/contaT;
		
		return ritorna;
	}
	

}
