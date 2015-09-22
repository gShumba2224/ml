package Neurons;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NeuronTest {

	public SigmoidNeuron unit; 
	
	@Before
	public void resetUnit (){
		unit = new SigmoidNeuron();
	}
	
	@Test
	public void addInputConnectionTest() {
		
		int count = 0;
		for (int i = 0; i < 10 ; i++){
			SigmoidNeuron neuron = new SigmoidNeuron (i);
			unit.addInputConnection(neuron,(double) i);
		}
		assertEquals(10, unit.getInputConnections().size());
	}
	
	@Test
	public void updateTest (){
		
		double count = 0.0;
		for (int i = 0; i < 10 ; i++){
			double weight = 5.0/(i+1);
			SigmoidNeuron neuron = new SigmoidNeuron (i);
			neuron.setOutputValue( (double)i);
			unit.addInputConnection(neuron,weight);
			count = count + (weight* (double)i);
		}
		double bias = 34.0;
		double exp = -1 * (count - bias);
		double result = 1.0/ (1.0+ (Math.pow( SigmoidNeuron.BASE, exp)));
		
		unit.setBias(bias);
		unit.update();
		assertEquals(result, unit.getOutputValue(),5.0);
		assertEquals(count, unit.getSumValue(),5.0);
	}

}
