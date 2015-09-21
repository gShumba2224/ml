package Neurons;

import java.util.List;

public class NeuralNetwork{
	
	private NeuralLayer inputLayer;
	private NeuralLayer outputLayer;
	private List <NeuralLayer> hiddenLayers;
	
	public NeuralLayer getInputLayer() {
		return inputLayer;
	}
	public void setInputLayer(NeuralLayer inputLayer) {
		this.inputLayer = inputLayer;
	}
	public NeuralLayer getOutputLayer() {
		return outputLayer;
	}
	public void setOutputLayer(NeuralLayer outputLayer) {
		this.outputLayer = outputLayer;
	}
	public List<NeuralLayer> getHiddenLayers() {
		return hiddenLayers;
	}
	public void setHiddenLayers(List<NeuralLayer> hiddenLayers) {
		this.hiddenLayers = hiddenLayers;
	}
}
