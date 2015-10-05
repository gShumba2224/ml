package Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import GeneticAlgorithm.Genome;
import Neurons.NeuralNetwork;

public class CacheObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1836423478182442318L;
	private NeuralNetwork network;
	private Map <Integer,ArrayList<Genome>> populations = new HashMap <Integer,ArrayList<Genome>>();
	
	public CacheObject (){}
	
	public CacheObject (NeuralNetwork network){
		this.network = network;
	}
	
	public CacheObject (NeuralNetwork network, ArrayList <Genome> population, int generation){
		this.network = network;
		this.populations.put(generation, population);
	}
	
	public NeuralNetwork getNetwork() {
		return network;
	}
	public void setNetwork(NeuralNetwork network) {
		this.network = network;
	}
	public Map<Integer, ArrayList<Genome>> getPopulations() {
		return populations;
	}
	public void setPopulations(Map<Integer, ArrayList<Genome>> populations) {
		this.populations = populations;
	}
}
