package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private Graph<Album,DefaultWeightedEdge> grafo;
	private ItunesDAO dao;
	private List<Album> allAlbums;
	private List<Album> bestPath;
	private int bestScore;
	
	
	public Model() {
		this.dao = new ItunesDAO();
		this.allAlbums = dao.getAllAlbums();
	}
	
	public void createGraph(Integer n) {
		//Ogni volta che creo un nuovo grafo resetto tutti i bilanci
		for(Album a : this.allAlbums) {
			a.setBilancio(0);
		}
		
		this.grafo = new SimpleDirectedWeightedGraph<Album,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<Album> vertici = dao.getAllAlbumsWithMoreThanNTracks(n);
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		for(Album a1 : vertici) {
			for(Album a2 : vertici) {
				int diff = a1.getnCanzoni() - a2.getnCanzoni();
				if(Math.abs(diff) != 0) {
					if(diff>0) {
						Graphs.addEdgeWithVertices(this.grafo, a2, a1, Math.abs(diff));
						//Incremento il bilancio dei vertici in cui arriva arco
						a1.increaseBilancio(Math.abs(diff));
					}
					if(diff<0) {
						Graphs.addEdgeWithVertices(this.grafo, a1, a2, Math.abs(diff));
						//Decremento il bilancio dei vertici da cui parte arco
						a1.decreaseBilancio(Math.abs(diff));
					}
				}
			}
		}
	}	
	
	public List<Album> getAdiacenze(Album a1){
		List<Album> result = Graphs.successorListOf(this.grafo, a1);
		Collections.sort(result);
		return result;
	}
	
	
	public List<Album> getPath(Album aSource, Album aTarget, Integer x){
		List<Album> parziale = new ArrayList<>();
		this.bestPath = new ArrayList<>();
		this.bestScore = 0;
		parziale.add(aSource);
		ricorsione(parziale, aTarget, x);
		return this.bestPath;
	}
	
	
	private void ricorsione(List<Album> parziale, Album aTarget, Integer x) {
		Album current = parziale.get(parziale.size()-1);
		//Condizione di terminazione
		if(current.equals(aTarget)) {
			//Controllo se ho una soluzione migliore e nel caso la salvo
			if(calculateNBilancioMaggiore(parziale.get(0), parziale)>bestScore) {
				this.bestScore = calculateNBilancioMaggiore(parziale.get(0), parziale);
				this.bestPath = new ArrayList<>(parziale);
			}
			return;
		}
		
		//Chiamata ricorsiva
		 //Ciclo su tutti i successori del nodo current
		List<Album> successori = Graphs.successorListOf(this.grafo, current);
		for(Album a : successori) {
			if(this.grafo.getEdgeWeight(this.grafo.getEdge(current, a))>= x) {
				parziale.add(a);
				ricorsione(parziale, aTarget, x);
				//Backtracking
				parziale.remove(a);
			}
		}
	}
	

	public int calculateNBilancioMaggiore(Album aPartenza, List<Album> listaAlbum) {
		int NBilancioMaggiore = 0;
		for(Album a : listaAlbum) {
			if(a.getBilancio()>aPartenza.getBilancio()) {
				NBilancioMaggiore++;
			}
		}
		return NBilancioMaggiore;
	}

	public Set<Album> getVertexSetAlphabetically() {
		return this.grafo.vertexSet();
	}
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}	
}
