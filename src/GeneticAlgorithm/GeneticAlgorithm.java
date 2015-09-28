package GeneticAlgorithm;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import Neurons.Connection;
import Neurons.InputConnection;
import Neurons.NeuralLayer;
import Neurons.NeuralNetwork;
import Neurons.Neuron;
import Utils.Round;

public abstract class GeneticAlgorithm {
	
	
	private static final double WHEEL = 360.0;
	private List <Genome> population = new ArrayList <Genome> ();
	private Map <String,Genome> candidateParents = new HashMap <String,Genome> ();
	private List <Genome> eliteParents = new ArrayList <Genome> ();
	private Map <String,Double> fitnessAverages = new HashMap <String,Double>();
	private double overallFitnessAverage = 0.0;
	private double mutationRate = 0.05;
	private double mutationScale = 1.0;
	private int generation = -1;
	private double maxGeneVal = 1.0;
	private double minGeneVal = 0.0;
	private File logFile = null;
	
	public abstract void evaluateGenome (Object...parameters);
	public abstract void evaluateGeneration (File logFile);
	public abstract void calculateFitnesses ();

	
	public void newRandomPopulation (NeuralNetwork network, int populationSize, String...fitnessProperties){
		for (int i = 0; i < populationSize ; i++){
			Genome genome = new Genome (i,fitnessProperties);
			genome.setBoltzmannFitness(0.0);
			for (Neuron neuron : network.getInputLayer().getNeurons()){generateRandomGenes(neuron, genome);}
			for (NeuralLayer layer : network.getHiddenLayers()){
				for (Neuron neuron : layer.getNeurons()){
					generateRandomGenes(neuron, genome);
				}
			}
			for (Neuron neuron : network.getOutputLayer().getNeurons()){generateRandomGenes(neuron, genome);}
			population.add(genome);
		}
		for (String property : fitnessProperties){fitnessAverages.put(property, 0.0);}
		generation++;
	}
	
	private void generateRandomGenes (Neuron neuron, Genome genome){
		double weight =  0.0;
		Random rand = new Random();
		int count = 0;
		Gene gene = null;
		for (InputConnection connection : neuron.getInputConnections()){
			weight = rand.nextDouble();
			weight = (weight *(maxGeneVal - minGeneVal))+ minGeneVal;
			gene = new  Gene(count, neuron.getID(), count, weight, Gene.WEIGHT);
			genome.getGenes().add(gene);
			count++;
		}
		count++;
		weight = rand.nextDouble();
		weight = (weight *(maxGeneVal - minGeneVal))+ minGeneVal;
		gene = new  Gene(count, neuron.getID(), count, weight, Gene.BIAS);
		genome.getGenes().add(gene);
	}
	
	public void newGeneration (int eliteSampleSize, int boltzSampleSize,double boltzTemperature, int populationSize ){
		Random rand = new Random ();
		Genome child = null;
		int childID = population.size();
		for (String property: fitnessAverages.keySet()){fitnessAverages.put(property, 0.0);}
		overallFitnessAverage = 0.0;
		generation ++;
		
		int totalParents = eliteSampleSize + boltzSampleSize;
		int maxFromElite = (int)((double)populationSize*((double)eliteSampleSize/(double)totalParents));
		int maxFromOther = populationSize - maxFromElite;
		
		for (String property : fitnessAverages.keySet()){elitismSelection(property, eliteSampleSize);}
		boltzmannSelection(boltzTemperature, boltzSampleSize);
		population.clear();
		
		for (int i = 0; i < maxFromElite ; i++){
			Genome father = eliteParents.get(rand.nextInt(eliteParents.size()));
			Genome mother = eliteParents.get(rand.nextInt(eliteParents.size()));
			int crossPoint = rand.nextInt(father.getGenes().size());
			child = mate(mother, father, childID, crossPoint);
			population.add(child);
			childID++;
		}
		
		List <Genome> candidateList = new ArrayList <Genome>(candidateParents.values());
		for (int i = 0; i < maxFromOther ; i++){
			Genome father =candidateList.get(rand.nextInt(candidateList.size()));
			Genome mother = candidateList.get(rand.nextInt(candidateList.size()));
			while  (father.equals(mother) == true){
				mother = candidateList.get(rand.nextInt(candidateList.size()));}
			int crossPoint = rand.nextInt(father.getGenes().size());
			child = mate(mother, father, childID, crossPoint);
			population.add(child);
			childID++;
		}
		eliteParents.clear();
		candidateParents.clear();
	}
	
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
			eliteParents.add(highest);
			tempList.remove(highest);
			sampleSize--;
		}
	}
	
	public void boltzmannSelection (double temperature, int numberOfSelections){
		double sumFitness = 0.0;
		calculateFitnesses();
		Map <String, Genome> selectionPool = new HashMap <String, Genome> ();
		
		for (Genome genome : population){
			genome.setBoltzmannFitness( boltzmannFitness(genome.getOverallFitness(), temperature, population.size()));
			sumFitness = sumFitness + genome.getBoltzmannFitness();
		}
		double lastPoint = 0.0;
		for (Genome genome : population){
			double fitness = genome.getBoltzmannFitness();
			if (fitness > 0 && sumFitness > 0){
				double startRange = lastPoint;
				double finishRange = startRange  + ((fitness/sumFitness) * WHEEL);
				String key = String.valueOf(Round.round(startRange,3))+"-"
							+String.valueOf(Round.round(finishRange,3));
				selectionPool.put(key, genome);
				lastPoint = finishRange;
			}
		}
		stochasticUniversallSampling (selectionPool,numberOfSelections);
	}
	
	private double  boltzmannFitness (double fitness , double temperature, double currentAverage){
		return  Round.round((Math.pow(Math.E,  (fitness/temperature)))/currentAverage, 3 );
	}
	
	private  void stochasticUniversallSampling (Map <String,Genome> selectionPool,int numberOfSelections){
		double stepSize =  WHEEL / numberOfSelections;
		double nextPoint = new Random ().nextDouble() * WHEEL;
		
		double sumBoltzmann = 0.0;
		int selected = 0;
		while (selected < numberOfSelections ){
			if (nextPoint > WHEEL){nextPoint = nextPoint - WHEEL;}
			String selectedGenome =  checkRange (selectionPool,nextPoint);
			if (selectedGenome != null){
				candidateParents.put(selectedGenome, selectionPool.get(selectedGenome));
				sumBoltzmann = sumBoltzmann + selectionPool.get(selectedGenome).getBoltzmannFitness();
				selected ++;
			} else return;
			nextPoint = nextPoint + stepSize;
		}
	}

	private String checkRange (Map <String,Genome> selectionPool , double point){
		NumberFormat formatter = NumberFormat.getInstance();
		for (String key : selectionPool.keySet()){
			String[] splitKey = key.split("-");
			double start;
			double finish;
			try {
				start = formatter.parse( splitKey[0] ).doubleValue();
				 finish = Double.parseDouble( splitKey[1] );
				 if (point >= start && point < finish){
					 return key;
				}
			} catch (ParseException e) {}
		}
		return null;
	}
	
	public Genome singlePointCrossOver (Genome father, Genome mother,int childID, int crossPoint,String...fitnesses){
		Genome child = new Genome (childID,fitnesses);
		Gene gene = null;
		for (int i = 0 ; i < crossPoint ; i++){
			Gene dadGene = father.getGenes().get(i);
			gene = new Gene(i, dadGene.getNeuronID(), dadGene.getInputNumber(), dadGene.getWeight(), dadGene.getType());
			child.getGenes().add(gene);
		}
		for (int i = crossPoint; i < mother.getGenes().size(); i++){
			Gene momGene = mother.getGenes().get(i);
			 gene = new Gene(i, momGene.getNeuronID(), momGene.getInputNumber(), momGene.getWeight(), momGene.getType());
			child.getGenes().add(gene);
		}
		return child;
	}
	
	public Genome uniformCrossOver(Genome father, Genome mother, int childID, String...fitnesses){
		Genome child = new Genome (childID, fitnesses);
		Random rand = new Random ();
		Gene gene;
		int randNum = 0;
		for (int i = 0; i < father.getGenes().size(); i++){
			randNum = rand.nextInt(10);
			if (randNum <= 4){
				Gene dadGene = father.getGenes().get(i);
				gene = new Gene(i, dadGene.getNeuronID(), dadGene.getInputNumber(), dadGene.getWeight(), dadGene.getType());
				child.getGenes().add(gene);
			}else{
				Gene momGene = mother.getGenes().get(i);
				gene = new Gene(i, momGene.getNeuronID(), momGene.getInputNumber(), momGene.getWeight(), momGene.getType());
				child.getGenes().add(gene);
			}
		}
		return child;
	}
	
	public void mutate (Genome child ){
		Random rand = new Random ();
		double max = mutationScale;
		double min = -1* max;
		double willMutate = 0.0;
		double mutateAmount = 0.0;
		
		for (Gene gene : child.getGenes()){
			willMutate = rand.nextDouble();
			if (willMutate <= mutationRate){
				mutateAmount = ( rand.nextDouble());
				mutateAmount = min + mutateAmount * (max - min);
				double newWeight = gene.getWeight() + mutateAmount;
				if (newWeight < minGeneVal){newWeight = minGeneVal;}
				if (newWeight > maxGeneVal){newWeight = maxGeneVal;}
				gene.setWeight(newWeight);
			}
		}
	}
	
	public Genome mate (Genome father, Genome mother,int childID, int crossPoint){
		Genome child;
		int method = new Random().nextInt(10);
		if (method <= 4){
			child = uniformCrossOver (father,mother,childID);
		}else{
			child = singlePointCrossOver (father,mother,childID,crossPoint);
		}
		for (String property : fitnessAverages.keySet()){child.getFitnessProperties().put(property, 0.0);}
		child.setMother(mother);
		child.setFather(father);
		mutate (child);
		return child;
	}
	
	
	//-----------------------GETTERS & SETTERS --------------

	public List<Genome> getPopulation() {
		return population;
	}
	public void setPopulation(List<Genome> population) {
		this.population = population;
	}

	public Map<String, Genome> getCandidateParents() {
		return candidateParents;
	}
	public void setCandidateParents(Map<String, Genome> candidateParents) {
		this.candidateParents = candidateParents;
	}
	public List<Genome> getEliteParents() {
		return eliteParents;
	}
	public void setEliteParents(List<Genome> eliteParents) {
		this.eliteParents = eliteParents;
	}
	public Map<String, Double> getFitnessAverages() {
		return fitnessAverages;
	}
	public void setFitnessAverages(Map<String, Double> fitnessAverages) {
		this.fitnessAverages = fitnessAverages;
	}
	public double getOverallFitnessAverage() {
		return overallFitnessAverage;
	}
	public void setOverallFitnessAverage(double overallFitnessAverage) {
		this.overallFitnessAverage = overallFitnessAverage;
	}
	public double getMutationRate() {
		return mutationRate;
	}
	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}
	public double getMutationScale() {
		return mutationScale;
	}
	public void setMutationScale(double mutationScale) {
		this.mutationScale = mutationScale;
	}
	public int getGeneration() {
		return generation;
	}
	public void setGeneration(int generation) {
		this.generation = generation;
	}
	public static double getWheel() {
		return WHEEL;
	}
	public double getMaxGeneVal() {
		return maxGeneVal;
	}
	public void setMaxGeneVal(double maxGeneVal) {
		this.maxGeneVal = maxGeneVal;
	}
	public double getMinGeneVal() {
		return minGeneVal;
	}
	public void setMinGeneVal(double minGeneVal) {
		this.minGeneVal = minGeneVal;
	}
	
}


class StringDoublePair {
	public String string;
	public double number;
	
	public StringDoublePair(String string, double number) {
		this.string = string;
		this.number = number;
	}
}

