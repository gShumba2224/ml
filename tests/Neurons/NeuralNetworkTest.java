package Neurons;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GeneticAlgorithm.Gene;
import GeneticAlgorithm.Genome;

public class NeuralNetworkTest {
	
	public NeuralNetwork unit = unit = new NeuralNetwork(3, 3, 3, 3);

	@Test
	public void getOutputsTest (){
	
		NeuralLayer layer = new NeuralLayer(1);
		Neuron neuron1 = new SigmoidNeuron(0);
		Neuron neuron2 = new SigmoidNeuron(1);
		
		neuron1.setOutputValue(1.0);
		neuron2.setOutputValue(2.0);
		
		layer.getNeurons().add(neuron1);
		layer.getNeurons().add(neuron2);
		
		unit.setOutputLayer(layer);
		
		assertEquals(unit.getOutputs().get(0), 1.0,0.001);
		assertEquals(unit.getOutputs().get(1), 2.0,0.001);
	}
	
	@Test
	public void setWeightsTest (){
		Genome genome = new Genome();
		int count = 0;
		for (Neuron neuron : unit.getAllNeurons().values()){
			int i = 0;
			for (InputConnection connection : neuron.getInputConnections()){
				Gene gene = new Gene(count, neuron.getID(), i, count, Gene.WEIGHT);
				genome.getGenes().add(gene);
				count++;
				i++;
			}
			i = 0;
			count ++;
			Gene gene = new Gene(count, neuron.getID(), i, count, Gene.BIAS);
			genome.getGenes().add(gene);
		}
		try {
			unit.setWeights(genome);
		} catch (DuplicateNeuronID_Exception e) {
			e.printStackTrace();
		}
		for (Gene gene : genome.getGenes()){
			Neuron neuron = unit.getAllNeurons().get(gene.getNeuronID());
			if (gene.getType() == Gene.WEIGHT){
				assertEquals(gene.getWeight(), 
				neuron.getInputConnections().get(gene.getInputNumber()).getWeight(),0.1);
			}else{
				assertEquals(gene.getWeight(), 
				neuron.getBias(),0.1);
			}
		}
	}
	
	@Test
	public void connectAllLayersTest (){
			unit = new NeuralNetwork(5, 5, 5, 5);
			for (Neuron neuron : unit.getHiddenLayers().get(0).getNeurons()){
				int count = 0;
				for (InputConnection connection : neuron.getInputConnections()){
					assertEquals(connection.getNeuron().getID(), count);
					count++;
				}
			}
			
			for (Neuron neuron : unit.getOutputLayer().getNeurons()){
				int count = 5*5;
				for (InputConnection connection : neuron.getInputConnections()){
					assertEquals(connection.getNeuron().getID(), count);
					count++;
				}
			}
			
			for (int i = 1; i < unit.getHiddenLayers().size(); i++){
				for (Neuron neuron : unit.getHiddenLayers().get(i).getNeurons()){
					int count = 5 * i;
					for (InputConnection connection : neuron.getInputConnections()){
						assertEquals(connection.getNeuron().getID(), count);
						count++;
					}
				}
			}
			
			assertEquals(unit.getAllNeurons().values().size(), 35);
			assertEquals(unit.getInputLayer().getNeurons().size(), 5);
			assertEquals(unit.getOutputLayer().getNeurons().size(), 5);
			for (NeuralLayer layer : unit.getHiddenLayers()){
				assertEquals(layer.getNeurons().size(), 5);
			}
			assertEquals(unit.getHiddenLayers().size(), 5);
	}
	
	@Test
	public void connectLayersTest (){
		
	}
	
//	@Before
//	public void generateLayers (){
//		NeuralLayer hiddenLayer = new NeuralLayer ();
//		hidden.add(hiddenLayer);
//		for (int j = 0; j < 20; j++){weights[j] = j;}
//		
//		for (int i = 0; i < 60; i++){
//			if (i < 20) { 
//				in.getNeurons().add(new SigmoidNeuron (i));
//			}
//			else if ( i >= 40 ) { 
//				out.getNeurons().add(new SigmoidNeuron (i));
//			}
//			else if (i >= 20 && i < 40) {
//				if (hiddenLayer.getNeurons().size() >= 5){
//					hiddenLayer = new NeuralLayer ();
//					hidden.add(hiddenLayer);
//				}
//				hiddenLayer.getNeurons().add(new SigmoidNeuron (i));
//			}
//		}
//	}
//	
//	@Test
//	public void connectAllLayersTests (){
//		unit.setInputLayer(in);
//		unit.setHiddenLayers(hidden);
//		unit.setOutputLayer(out);
//		unit.connectAllLayers(weights, weights, weights);
//		
//		for (int i = 0; i < 20; i++){
//			assertEquals(i, in.getNeurons().get(i).getID() );
//		}
//		
//		int count = 20;
//		for (int i = 0; hidden.size() > i ; i++){
//			for (int j = 0; hidden.get(i).getNeurons().size() > j ; j++){
//				Neuron neuron = hidden.get(i).getNeurons().get(j);
//				assertEquals(count, neuron.getID());
//				count++;
//			}
//		}
//		
//		for (int i = 40; i < 60; i++){
//			assertEquals(i, out.getNeurons().get(i-40).getID() );
//			double expectedWeight = 0.0;
//			for (int j = 0; j < out.getNeurons().get(i-40).getInputConnections().size();j++){
//				Neuron neuron = out.getNeurons().get(i-40);
//				double neuronWeight = neuron.getInputConnections().get(j).getWeight();
//				assertEquals(expectedWeight, neuronWeight,1.0);
//				expectedWeight ++;
//			}
//		}
//	}
//	
//	@Test
//	public void connectLayersTest (){
//		
//	}
//	
//	@Test 
//	public void setWeightsTests (){
//		
//	}
	
	
}
