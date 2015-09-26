package GeneticAlgorithm;

import java.io.File;

public class Evolve  extends GeneticAlgorithm{

	public Evolve (){
	}
	@Override
	public void evaluateGenome(Object...parameters) {
	}

	@Override
	public void evaluateGeneration(File logFile) {
		double averageFitness = 0.0;
		Genome fitest = this.getPopulation().get(0);
		
		for (Genome genome : this.getPopulation()){
			averageFitness = genome.getOverallFitness() + averageFitness;
			if (genome.getOverallFitness() > fitest.getOverallFitness()){fitest = genome;}
			for (String property : this.getFitnessAverages().keySet()){
				double fitness = this.getFitnessAverages().get(property);
				fitness = fitness + genome.getFitnessProperties().get(property);
				this.getFitnessAverages().put(property, fitness);
			}
		}
		for (String property : this.getFitnessAverages().keySet()){
			double average = this.getFitnessAverages().get(property);
			average = average/this.getPopulation().size();
			this.getFitnessAverages().put(property, average);
		}
		this.setOverallFitnessAverage(averageFitness/this.getPopulation().size());
	}

}
