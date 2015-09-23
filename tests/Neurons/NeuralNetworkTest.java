package Neurons;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class NeuralNetworkTest {
	
	public NeuralNetwork unit = new NeuralNetwork ();
	public NeuralLayer in = new NeuralLayer();
	public NeuralLayer out = new NeuralLayer ();
	public ArrayList <NeuralLayer> hidden = new ArrayList <NeuralLayer> ();
	double[] weights = new double[20];
	
	@Before
	public void generateLayers (){
		NeuralLayer hiddenLayer = new NeuralLayer ();
		hidden.add(hiddenLayer);
		for (int j = 0; j < 20; j++){weights[j] = j;}
		
		for (int i = 0; i < 60; i++){
			if (i < 20) { 
				in.getNeurons().add(new SigmoidNeuron (i));
			}
			else if ( i >= 40 ) { 
				out.getNeurons().add(new SigmoidNeuron (i));
			}
			else if (i >= 20 && i < 40) {
				if (hiddenLayer.getNeurons().size() >= 5){
					hiddenLayer = new NeuralLayer ();
					hidden.add(hiddenLayer);
				}
				hiddenLayer.getNeurons().add(new SigmoidNeuron (i));
			}
		}
	}
	
	@Test
	public void connectAllLayersTests (){
		unit.setInputLayer(in);
		unit.setHiddenLayers(hidden);
		unit.setOutputLayer(out);
		unit.connectAllLayers(weights, weights, weights);
		
		for (int i = 0; i < 20; i++){
			assertEquals(i, in.getNeurons().get(i).getID() );
		}
		
		int count = 20;
		for (int i = 0; hidden.size() > i ; i++){
			for (int j = 0; hidden.get(i).getNeurons().size() > j ; j++){
				Neuron neuron = hidden.get(i).getNeurons().get(j);
				assertEquals(count, neuron.getID());
				count++;
			}
		}
		
		for (int i = 40; i < 60; i++){
			assertEquals(i, out.getNeurons().get(i-40).getID() );
			double expectedWeight = 0.0;
			for (int j = 0; j < out.getNeurons().get(i-40).getInputConnections().size();j++){
				Neuron neuron = out.getNeurons().get(i-40);
				double neuronWeight = neuron.getInputConnections().get(j).getWeight();
				assertEquals(expectedWeight, neuronWeight,1.0);
				expectedWeight ++;
			}
		}
	}
	
	@Test
	public void connectLayersTest (){
		
	}
	
	
}
