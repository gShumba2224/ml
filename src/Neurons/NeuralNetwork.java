package Neurons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GeneticAlgorithm.Gene;
import GeneticAlgorithm.Genome;

public class NeuralNetwork{
	
	private NeuralLayer inputLayer;
	private NeuralLayer outputLayer;
	private List <NeuralLayer> hiddenLayers = new ArrayList <NeuralLayer> ();
	private Map<Integer,Neuron> allNeurons = new HashMap <Integer,Neuron> ();
	
	public NeuralNetwork (){
	}
	public NeuralNetwork (NeuralLayer in, NeuralLayer hidden ,NeuralLayer out){
		inputLayer = in;
		outputLayer = out;
		hiddenLayers = new ArrayList <NeuralLayer> ();
		hiddenLayers.add(hidden);
		connectAllLayers (null,null,null);
	}
	
	public void connectAllLayers (double[] inputWeights, double[] ouputWeights, double[] hiddenWeights){
		connectLayers(inputLayer, hiddenLayers.get(0) , inputWeights);
		connectLayers( hiddenLayers.get(hiddenLayers.size()-1),outputLayer , ouputWeights);
		int toIndex = 0;
		int fromIndex = 0;
		for (int i = 0; i < hiddenLayers.size()-1; i++){
			toIndex = hiddenLayers.get(i).getNeurons().size() + fromIndex;
			double[] weights = Arrays.copyOfRange(hiddenWeights, fromIndex, toIndex);
			connectLayers(hiddenLayers.get(i), hiddenLayers.get(i+1), weights);
			fromIndex = toIndex;
		}
	}
	
	public void connectLayers (NeuralLayer connectFrom, NeuralLayer connectTo, double[] weights){
		int weightIndex = 0;
		for (Neuron layer1Neuron : connectFrom.getNeurons()){
			for (Neuron layer2Neuron : connectTo.getNeurons()){
				if (weights != null){
					layer2Neuron.addInputConnection(layer1Neuron,weights[weightIndex]);
				} else{
					layer2Neuron.addInputConnection(layer1Neuron,0.0);
				}
			}
			weightIndex ++;
		}
	}
	
	public void update () throws DuplicateNeuronID_Exception {
		for (Neuron neuron : inputLayer.getNeurons()){
			mapNeuronToID(neuron);
			neuron.update();
		}
		for (NeuralLayer layer : hiddenLayers){
			for (Neuron neuron : layer.getNeurons()){
				mapNeuronToID(neuron);
				neuron.update();
			}
		}
		for (Neuron neuron : outputLayer.getNeurons()){
			mapNeuronToID(neuron);
			neuron.update();
		}
	}
	
	private void setWeights (Genome genome){
		for (Gene gene: genome.getGenes()){
			Neuron neuron = allNeurons.get(gene.getNeuronID());
			if (gene.getType() == Gene.WEIGHT){
				neuron.getInputConnections().get(gene.getInputNumber()).setWeight(gene.getWeight());
			}else{
				neuron.setBias(gene.getWeight());
			}
		}
	}
	
	private void mapNeuronToID (Neuron neuron) throws DuplicateNeuronID_Exception{
		
		if (allNeurons.containsKey(neuron.getID()) == true){
			throw new DuplicateNeuronID_Exception (neuron.getID());
		}else{
			allNeurons.put(neuron.getID(), neuron);
		}
	}
	public NeuralLayer getInputLayer() {return inputLayer;}
	
	public void setInputLayer(NeuralLayer inputLayer) {this.inputLayer = inputLayer;}
	
	public NeuralLayer getOutputLayer() {return outputLayer;}
	
	public void setOutputLayer(NeuralLayer outputLayer) {this.outputLayer = outputLayer;}
	
	public List<NeuralLayer> getHiddenLayers() {return hiddenLayers;}
	
	public void setHiddenLayers(List<NeuralLayer> hiddenLayers) {this.hiddenLayers = hiddenLayers;}
}
