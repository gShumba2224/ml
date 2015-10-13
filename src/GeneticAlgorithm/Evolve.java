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
		this.setSumFitness(0.0);
		this.setOverallFitnessAverage(0.0);
		this.setFittestGenome( this.getPopulation().get(0));
		
		for (String property : this.getFitnessAverages().keySet()){
			this.getFitnessAverages().replace(property, 0.0);
		}
		for (Genome genome : this.getPopulation()){
			double fitness = 0.0;
			for (String property : genome.getFitnessProperties().keySet()){
				double score = genome.getFitnessProperties().get(property);
				double propertyAvg = this.getFitnessAverages().get(property)+score;
				this.getFitnessAverages().replace(property, propertyAvg);
				fitness = fitness + score;
			}
			genome.setOverallFitness(fitness);
			if (genome.getOverallFitness() > this.getFittestGenome().getOverallFitness()){
				this.setFittestGenome(genome);}
			this.setSumFitness(fitness + this.getSumFitness());
		}
		for (String property : this.getFitnessAverages().keySet()){
			double average = this.getFitnessAverages().get(property);
			if (average > 0){average = average/this.getPopulation().size();}
			this.getFitnessAverages().put(property, average);
		}
		this.setOverallFitnessAverage(this.getSumFitness()/this.getPopulation().size());
	}
}
