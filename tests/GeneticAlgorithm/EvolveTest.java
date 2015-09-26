package GeneticAlgorithm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Neurons.DuplicateNeuronID_Exception;
import Neurons.InputConnection;
import Neurons.NeuralNetwork;
import Neurons.Neuron;

public class EvolveTest {

	public GeneticAlgorithm unit = new Evolve ();
	
	@Before 
	public void initUnit (){
		List <Genome> storage = new ArrayList <Genome> ();
		List <Genome> population = new  ArrayList <Genome> ();
		unit.setPopulation(population);
	}
	
	public void generatePopulation (int size){
		Random rand = new Random();
		for (int i = 0; i < size ; i++){
			Genome genome = new Genome (i);
			genome.setOverallFitness(i);
			genome.getFitnessProperties().put("A", (double) (i));
			genome.getFitnessProperties().put("B", (double) (i * 2));
			genome.getFitnessProperties().put("C", (double) (i * 3));
			for (int j = 0; j < 10 ; j++){
				Gene gene = new Gene(j, j, j, rand.nextDouble(), Gene.WEIGHT);
				genome.getGenes().add(gene);
			}
			unit.getPopulation().add(genome);
		}
		unit.getFitnessAverages().put("A", 1275.0/size);
		unit.getFitnessAverages().put("B", (1275.0*2)/2);
		unit.getFitnessAverages().put("C", (1275.0*3)/2);
	}
	
	@Test
	public void elitismSelectionTest() {
		generatePopulation(10);
		unit.elitismSelection("B", 3);
		assertEquals(unit.getEliteParents().size(), 3);
		int expected = 18;
		for (Genome genome : unit.getEliteParents()){
			assertEquals(expected, genome.getFitnessProperties().get("B"),1.0);
			assertEquals(expected/2, genome.getID());
			expected = expected - 2;
		}	
	}
	
	@Test
	public void boltzmannSelectionTest (){
		generatePopulation(50);
		double temperature = 3.0;
		double average = 0.0;
		double sumFitness = 0.0;
		for (Genome genome : unit.getPopulation()){
			average = average + boltzmannFunction (temperature,genome.getOverallFitness());
		}
		average = average / unit.getPopulation().size();
		
		int count = 0;
		for (Genome genome : unit.getPopulation()){
			genome.setBoltzmannFitness( boltzmannFunction(temperature, genome.getOverallFitness())/average );
			sumFitness = sumFitness + genome.getBoltzmannFitness();
			count++;
		}
		
		unit.boltzmannSelection(temperature, 20);
		double idealAvgFitness = 0.0;
		for (int i = 0; i < unit.getCandidateParents().size() ; i++ ){
			int index = (unit.getPopulation().size()- i)-1;
			idealAvgFitness = idealAvgFitness  + unit.getPopulation().get(index).getOverallFitness();
		}
		idealAvgFitness = idealAvgFitness/unit.getCandidateParents().size();
		
		double actualAvgFitness = 0.0;
		for (String key : unit.getCandidateParents().keySet()){
			Genome genome = unit.getCandidateParents().get(key);
			actualAvgFitness = genome.getOverallFitness() + actualAvgFitness;
		}
		actualAvgFitness =actualAvgFitness/ unit.getCandidateParents().size();
		double error = actualAvgFitness/idealAvgFitness;
		assertTrue(error > 0.8);
	}
	
	public double boltzmannFunction (double temperature, double fitness){
		return Math.pow(Math.E, (fitness/temperature) );
	}
	
	@Test
	public void newGenerationTest_fromPreviousGeneration (){
		
		generatePopulation(20);
		int count = unit.getPopulation().size();
		unit.newGeneration(10, 10, 20, 20);
		assertEquals(20, unit.getPopulation().size());
		
		ArrayList <Integer> parentIDs = new ArrayList <Integer> ();
		Map<Integer,Integer> occurrance = new HashMap <Integer,Integer> ();
		for (Genome genome :unit.getPopulation()){
			assertEquals(count, genome.getID());
			count ++;
			parentIDs.add(genome.getMother().getID());
			parentIDs.add(genome.getFather().getID());
		}
		for (Integer num : parentIDs){
			if (occurrance.get(num) == null){
				occurrance.put(num, 1);
			}else {
				Integer current = occurrance.get(num);
				occurrance.put(num, current + 1);
			}
		}
		int sum = 0;
		for (Integer num : occurrance.values()){
			sum = num + 1;
		}
		double variation = (double)sum/(double)20;
		assertTrue(variation < 0.5);
	}
	
	@Test
	public void newRandomPopulationTest (){
		
		NeuralNetwork network = null;
		try {
			network = new  NeuralNetwork(5, 5, 5, 5);
		} catch (DuplicateNeuronID_Exception e) {
			e.printStackTrace();
		}
		unit.newRandomPopulation(network, 20, "A","B");
		assertEquals(unit.getFitnessAverages().size(), 2);
		assertEquals(unit.getPopulation().size(), 20);
		
		int count = 0;
		for (Neuron neuron : network.getAllNeurons().values()){
			count++;
			for (InputConnection connection : neuron.getInputConnections()){
				count++;
			}
		}
		int totalWeights = unit.getPopulation().get(1).getGenes().size();
		assertEquals(totalWeights, count);
	
		int weightGenes = 0;
		for (Genome genome : unit.getPopulation()){
			assertEquals(genome.getFitnessProperties().size(), 2);
			int biasGenes = 0;
			for (Gene gene : genome.getGenes()){
				assertTrue(gene.getWeight() > 0.0);
				if (gene.getType() == Gene.BIAS){biasGenes++;}
				if (gene.getType() == Gene.WEIGHT){weightGenes++;}
			}
			assertEquals(biasGenes, network.getAllNeurons().size());
		}
	}
	
	@Test
	public void singlePointCrossOverTest (){
		generatePopulation(10);
		Genome father = unit.getPopulation().get(0);
		Genome mother = unit.getPopulation().get(1);
		int crossPoint = father.getGenes().size()/2;
		Genome child = unit.singlePointCrossOver(father, mother, 1, crossPoint);
		
		for (int i = 0; i < crossPoint;i++){
			assertTrue(child.getGenes().get(i).getWeight() == (father.getGenes().get(i).getWeight()));
		}
		for (int i = crossPoint; i < mother.getGenes().size();i++){
			assertTrue(child.getGenes().get(i).getWeight() ==  mother.getGenes().get(i).getWeight());
		}
	}
	
	@Test
	public void uniformCrossOverTest (){
		
		generatePopulation(10);
		Genome father = unit.getPopulation().get(0);
		Genome mother = unit.getPopulation().get(1);
		int crossPoint = father.getGenes().size()/2;
		Genome child = unit.uniformCrossOver(father, mother, 1);
		
		int momGenes = 0;
		int dadGenes = 0;
		
		for (int i = 0; i < child.getGenes().size() ; i++){
			Gene gene = child.getGenes().get(i);
			if (gene.getWeight() == mother.getGenes().get(i).getWeight()){
				momGenes ++;
			}else if (gene.getWeight() == father.getGenes().get(i).getWeight()) {
				dadGenes ++;
			}
		}
		assertEquals(mother.getGenes().size(), momGenes+dadGenes);
	}
	
	@Test
	public void mutateTest (){
		generatePopulation(10);
		Genome child = unit.getPopulation().get(0);
		ArrayList <Double> weights = new ArrayList <Double> ();
		for (Gene gene: child.getGenes()){weights.add(gene.getWeight());}
		unit.setMutationRate(0.6);
		unit.setMutationScale(1.0);
		unit.setMaxGeneVal(10.0);
		unit.setMinGeneVal(-10.0);
		unit.mutate(child);
		
		boolean isSame = true; 
		int i = 0;
		for (Gene gene : child.getGenes()){
			if (gene.getWeight() != weights.get(i)){
				isSame = false;
			}
			assertTrue (gene.getWeight() <= unit.getMaxGeneVal() && gene.getWeight() >= unit.getMinGeneVal());
			i++;
		}
		assertEquals(isSame, false);
	}
	

}
