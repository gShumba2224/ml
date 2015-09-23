package GeneticAlgorithm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GeneticAlgorithmTest {

	public GeneticAlgorithm unit = new GeneticAlgorithm ();
	@Test
	public void elitismSelectionTest() {
		List <Genome> storage = new ArrayList <Genome> ();
		List <Genome> population = new  ArrayList <Genome> ();
		for (int i = 0; i < 10 ; i++){
			Genome genome = new Genome (i);
			genome.getFitnessProperties().put("A", (double) (i));
			genome.getFitnessProperties().put("B", (double) (i * 2));
			genome.getFitnessProperties().put("C", (double) (i * 3));
			population.add(genome);
		}
		unit.setPopulation(population);
		unit.elitismSelection("B", 3);
		
		assertEquals(unit.getNextGenParents().size(), 3);
		int expected = 18;
		for (Genome genome : unit.getNextGenParents()){
			assertEquals(expected, genome.getFitnessProperties().get("B"),1.0);
			assertEquals(expected/2, genome.getID());
			expected = expected - 2;
		}	
	}
	
	@Test
	public void boltzmannSelectionTest (){
		
	}

}
