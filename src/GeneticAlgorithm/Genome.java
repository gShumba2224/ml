package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Genome {
	
	private double overallFitness;
	private Map<String,?> fitnessProperties;
	private List <Gene> genes = new ArrayList <Gene>();
	
	public double getOverallFitness() {
		return overallFitness;
	}
	public void setOverallFitness(double overallFitness) {
		this.overallFitness = overallFitness;
	}
	public Map<String, ?> getFitnessProperties() {
		return fitnessProperties;
	}
	public void setFitnessProperties(Map<String, ?> fitnessProperties) {
		this.fitnessProperties = fitnessProperties;
	}
	public List<Gene> getGenes() {
		return genes;
	}
	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}
}
