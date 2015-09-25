package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Genome {
	
	private int ID = 0;
	private Map<String,Double> fitnessProperties = new HashMap <String, Double> ();
	private List <Gene> genes = new ArrayList <Gene>();
	private double overallFitness = 0.0;
	private double  boltzmannFitness = 0.0;
	private double  boltzmannFitnessWheel = 0.0;
	private Genome mother = null;
	private Genome father = null;
	
	public Genome (){}
	public Genome (int ID){
		this.ID = ID;
	}
	
	public void calculateOverallFitness (){
		overallFitness = 0.0;
		for (Double score : fitnessProperties.values()){
			overallFitness = overallFitness + score;
		}
	}
	public double getOverallFitness() {
		return overallFitness;
	}
	
	public void setOverallFitness(double fitness) {
		 overallFitness = fitness;
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
	public double getBoltzmannFitness() {
		return boltzmannFitness;
	}
	public void setBoltzmannFitness(double boltzmannFitness) {
		this.boltzmannFitness = boltzmannFitness;
	}
	public double getBoltzmannFitnessWheel() {
		return boltzmannFitnessWheel;
	}
	public void setBoltzmannFitnessWheel(double boltzmannFitnessWheel) {
		this.boltzmannFitnessWheel = boltzmannFitnessWheel;
	}
	public Genome getMother() {
		return mother;
	}
	public void setMother(Genome mother) {
		this.mother = mother;
	}
	public Genome getFather() {
		return father;
	}
	public void setFather(Genome father) {
		this.father = father;
	}
	
	
}
