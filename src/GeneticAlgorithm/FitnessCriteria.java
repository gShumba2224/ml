package GeneticAlgorithm;

import java.util.Map;

abstract class FitnessCriteria {
	
	private double overallFitness;
	private Map<String,Double> fitnessParameters;
	public double getOverallFitness(){
		return overallFitness; 
	}
	
	abstract double calculateOverallFitness (Genome genome);
	
	
}
