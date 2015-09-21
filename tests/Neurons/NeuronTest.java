package Neurons;

import static org.junit.Assert.*;

import org.junit.Test;

public class NeuronTest {

	public SigmoidNeuron unit = new SigmoidNeuron ();
	@Test
	public void addInputConnectionTest() {
		int count = 0;
		for (int i = 0; i < 10 ; i++){
			SigmoidNeuron neuron = new SigmoidNeuron (i);
			neuron.setOutputValue(i);
			unit.addInputConnection(neuron,(double) i);
			count = count + (i * i);
			System.out.println(i + " " + count);
		}
		assertEquals(count, unit.getOutputValue(), 6.0);
	}

}
