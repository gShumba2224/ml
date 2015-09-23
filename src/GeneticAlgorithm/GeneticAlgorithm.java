package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
	
	private int generation = 0;
	private List <Genome> population = new ArrayList <Genome> ();
	private List <Genome> nextGenParents = new ArrayList <Genome> ();
	
	public void elitismSelection (String property, int sampleSize){
		List <Genome> tempList = new ArrayList <Genome> ();
		for (Genome genome : population){tempList.add(genome);}
		
		while (sampleSize > 0){
			Genome highest = tempList.get(0);
			for (Genome genome : tempList){
				Double candidate = genome.getFitnessProperties().get(property);
				Double current = highest.getFitnessProperties().get(property);
				if (Double.compare(candidate, current) > 0){highest = genome;}
			} 
			nextGenParents.add(highest);
			tempList.remove(highest);
			sampleSize--;
		}
	}
	
	public void boltzmannSelection (){
		
	}
	
	public void stochasticUniversallSampling (){
	}
	
	public Genome singlePointCrossOver (Genome father, Genome mother, int crossPoint){
		Genome child = new Genome ();
		for (int i = 0 ; i < crossPoint ; i++){
			Gene gene = father.getGenes().get(i);
			child.getGenes().add(gene);
		}
		for (int i = crossPoint; i < mother.getGenes().size(); i++){
			Gene gene = mother.getGenes().get(i);
			child.getGenes().add(gene);
		}
		return child;
	}
	
	public Genome uniformCrossOver(Genome father, Genome mother){
		Genome child = new Genome ();
		Random rand = new Random ();
		int randNum = 0;
		for (int i = 0; i < father.getGenes().size(); i++){
			randNum = rand.nextInt(12);
			if (randNum <= 6){
				child.getGenes().add(father.getGenes().get(i));
			}else{
				child.getGenes().add(mother.getGenes().get(i));
			}
		}
		return child;
	}
	
	public void mutate (Genome child, int mutateRate, double mutationScale){
		Random rand = new Random ();
		double randNum = 0;
		(((OldValue - OldMin) * (NewMax - NewMin)) / (OldMax - OldMin)) + NewMin
		(1.0-0.0) * ( mutationSclae-(-1*mutationScale))
		for (Gene gene : child.getGenes()){
			randNum = rand.nextDouble();
			if (randNum <= mutateRate){
				randNum = rand.nextDouble()
			}
		}
		
	}

	public List<Genome> getPopulation() {
		return population;
	}

	public void setPopulation(List<Genome> population) {
		this.population = population;
	}


	public List<Genome> getNextGenParents() {
		return nextGenParents;
	}


	public void setNextGenParents(List<Genome> nextGenParents) {
		this.nextGenParents = nextGenParents;
	}
	
	
	
	
	
}
