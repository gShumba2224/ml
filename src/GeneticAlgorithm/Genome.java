package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Genome {
	
	private int ID;
	private Map<String,Double> fitnessProperties = new HashMap <String, Double> ();
	private List <Gene> genes = new ArrayList <Gene>();
	
	public Genome (){}
	public Genome (int ID){
		this.ID = ID;
	}
	
	public double getOverallFitness() {
		double overallFitness = 0.0;
		for (Double score : fitnessProperties.values()){
			overallFitness = overallFitness + score;
		}
		return overallFitness;
	}
	public Map<String,Double> getFitnessProperties() {
		return fitnessProperties;
	}
	public void setFitnessProperties(Map<String, Double> fitnessProperties) {
		this.fitnessProperties = fitnessProperties;
	}
	public List<Gene> getGenes() {
		return genes;
	}
	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
}
