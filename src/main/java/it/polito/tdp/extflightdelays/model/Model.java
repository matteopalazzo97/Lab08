package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private Map<Integer, Airport> mappaAeroporti;
	private ExtFlightDelaysDAO dao;
	
	
	
	public Model() {
		super();
		this.dao = new ExtFlightDelaysDAO();
		this.mappaAeroporti = this.dao.loadAllAirportsMap();
		
	}



	public void creaGrafo(double distanzaMin) {
		
		this.grafo = new SimpleWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.mappaAeroporti.values());
		
		List<Rotta> rotte = this.dao.getRotte(mappaAeroporti);
		
		/*
		 * ora devo aggregare sti cazzo di voli A->B con B->A
		 * aggregare vuol dire che devo sommare la distanza tot se trovo una rotta  ba o ab quando già una 
		 * o l'altra esistono e sommare n_voli di una rotta a quelli dell'altra
		 */
		
		List<Rotta> rotteAggregate = new ArrayList<Rotta>();
		
		//scorro tutta la lista delle rotte
		for(Rotta fuori : rotte) {
			//scorro tutta la lista delle rotte aggregate con r che rappresenta la tratta A->B in cerca 
			//dell'eventuale tratta B->A, se la trovo aumento distanza e n_voli della rotta A->B con 
			//i valori della rotta B->A
			
			boolean presente = false;
			
			for(Rotta dentro : rotteAggregate) {
				
				if(fuori.getOrigin().equals(dentro.getDestination()) && 
						fuori.getDestination().equals(dentro.getOrigin())) {
					presente = true;
					fuori.aumentaTotDistance(dentro.getTotDistance());
					fuori.aumentaNvoli(dentro.getnVoli());
					break;
				}
			}
			
			if(!presente) {
				rotteAggregate.add(fuori);
			}
			
			for(Rotta r : rotteAggregate) {
				if(r.getAvgDistance() > distanzaMin)
					Graphs.addEdge(this.grafo, r.getOrigin(), r.getDestination(), r.getAvgDistance());
			}
			
		}
		
		System.out.println("Grafo creato.\n");
		System.out.println("Ci sono " + getNumeroVertici() + " vertici.\n");
		System.out.println("Ci sono " + getNumeroArchi(distanzaMin) + " archi.\n");
		
	}
	
	public int getNumeroVertici() {
		return this.grafo.vertexSet().size();
	}



	public int getNumeroArchi(double distanza) {
		return this.grafo.edgeSet().size();
	}
	
	public List<Rotta> getArchi (){
		
		List<Rotta> archi = new ArrayList<Rotta>();
		
		for(DefaultWeightedEdge a : this.grafo.edgeSet()) {
			
			archi.add(new Rotta(this.grafo.getEdgeSource(a),
					this.grafo.getEdgeTarget(a), this.grafo.getEdgeWeight(a)));
			
		}
		
		return archi;
	}

}
