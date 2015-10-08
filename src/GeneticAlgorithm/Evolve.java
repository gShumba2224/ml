package GeneticAlgorithm;

import java.io.File;

import Neurons.NeuralNetwork;
import Utils.Round;

public class Evolve  extends GeneticAlgorithm{

	public Evolve (){
	}
	
	public Evolve (NeuralNetwork network, int populationSize, 
			double minWeight,double maxWeight,String...fitnessProperties){
		this.setMaxGeneVal(maxWeight);
		this.setMinGeneVal(minWeight);
		this.newRandomPopulation (network, populationSize, fitnessProperties);
	}

	
	public Evolve (NeuralNetwork network, int populationSize, String...fitnessProperties){
		this.newRandomPopulation (network, populationSize, fitnessProperties);
	}
	@Override
	public void evaluateGenome(Genome genome ,Object...parameters) {
	}

	@Override
	public void evaluateGeneration () {
//		double averageFitness = 0.0;
//		this.setFittestGenome( this.getPopulation().get(0));
//		
//		for (Genome genome : this.getPopulation()){
//			averageFitness = genome.getOverallFitness() + averageFitness;
//			if (genome.getOverallFitness() > this.getFittestGenome().getOverallFitness()){
//				this.setFittestGenome(genome);
//			}
//			for (String property : this.getFitnessAverages().keySet()){
//				double fitness = this.getFitnessAverages().get(property);
//				fitness = fitness + genome.getFitnessProperties().get(property);
//				this.getFitnessAverages().put(property, fitness);
//			}
//		}
//		for (String property : this.getFitnessAverages().keySet()){
//			double average = this.getFitnessAverages().get(property);
//			average = average/this.getPopulation().size();
//			this.getFitnessAverages().put(property, average);
//		}
//		this.setOverallFitnessAverage(averageFitness/this.getPopulation().size());
	}

	@Override
	public void calculateFitnesses() {
		for (Genome genome : this.getPopulation()){
			double fitness = 0.0;
			for (double score : genome.getFitnessProperties().values()){
				fitness = fitness + score;
			}
			genome.setOverallFitness(fitness);
		}
		double sum = this.sumFitness();
		double average = 0.0;
		if (sum != 0){
			average = this.averageFitness();
			for (Genome genome : this.getPopulation() ){
				if (genome.getOverallFitness() != 0){
					double fitness = genome.getOverallFitness()/average;
					fitness = Round.round(fitness, 5);
					genome.setOverallFitness(fitness);
				}
			}
		}
		
		
	}
}
