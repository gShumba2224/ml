package GeneticAlgorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
	
	public void writeLogFile (String fileName, String log) throws IOException{
		PrintWriter outWriter = new PrintWriter(new FileWriter(fileName, true));
		FileWriter fileWritter = new FileWriter(fileName,true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        bufferWritter.write(log);
        bufferWritter.close();
        outWriter.write(log);
	}
	
	public String generationLog (){
		StringBuilder builder = new StringBuilder();
		
		String output = ("*********************************************** \n"+
						"*********************************************** \n"+
							"GENERATION = " + getGeneration() + "\n" + 
		"Fittest = "+getFittestGenome().getID() +" AverageFitness = " + getOverallFitnessAverage() + " Sum Fitnesses ="+ getSumFitness()+ 
		"\n" +"FITTNESS AVERAGES = ");
		builder.append(output);
		for (String property : getFitnessAverages().keySet()){
			builder.append( "< " + property + " : " + getFitnessAverages().get(property) + " >");
		}
		builder.append(" \n Selected Elite Parents = ");
		for (Genome genome : getEliteParents()){
			builder.append(" \n < ID = " + genome.getID() + ">"+ "< OverallFitness = "+genome.getOverallFitness()+">"+ "< Fitnesses = ");
			
			for (String property : genome.getFitnessProperties().keySet()){
				builder.append( "< " + property + " : " + genome.getFitnessProperties().get(property) + " >");
			}
//			builder.append(" \n < ID = " + genome.getID() + ">"+ "< weights = ");
//			for (Gene gene : genome.getGenes()){
//				builder.append(" \n neuron ID = " + gene.getNeuronID() + "input No = " + gene.getInputNumber()+ " weight = "+ gene.getWeight());
//			}

		}
		
		builder.append("\n  Selected Other Parents = ");
		for (Genome genome : getCandidateParents()){
			builder.append("\n <" + genome.getID() + ">"+ "<"+genome.getOverallFitness()+">");
		}
//		builder.append("\n  All GENOMES ******* = ");
//		for (Genome genome : population){
//			
//			String momID = "NONE";
//			String dadID = "None";
//			if (genome.getMother() != null && genome.getFather() != null){
//				momID = String.valueOf(genome.getMother().getID());
//				dadID = String.valueOf(genome.getFather().getID());
//			}
//			builder.append( "\n Genome ID = " + genome.getID() + " Parents = <mom :" + momID + " dad :" + dadID + ">"+
//					" Overallfitness = " + genome.getOverallFitness() + " Fitnesses = ");
//			for (String property : genome.getFitnessProperties().keySet()){
//				builder.append( "< " + property + " : " + genome.getFitnessProperties().get(property) + " >");
//			}
//		}
		System.out.println(builder.toString());
		return (builder.toString());
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

	@Override
	public void preEvolutionActions() {
		
	}

	@Override
	public void postEvolutionActions() {
		
	}
	
}
