package Neurons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GeneticAlgorithm.Gene;
import GeneticAlgorithm.Genome;

public class NeuralNetwork{
	
	private NeuralLayer inputLayer = new NeuralLayer(1);
	private NeuralLayer outputLayer =  new NeuralLayer(2);
	private List <NeuralLayer> hiddenLayers = new ArrayList <NeuralLayer> ();
	private Map<Integer,Neuron> allNeurons = new HashMap <Integer,Neuron> ();
	
	public NeuralNetwork (){
	}
	public NeuralNetwork (NeuralLayer in, List<NeuralLayer> hidden ,NeuralLayer out) throws DuplicateNeuronID_Exception{
		inputLayer = in;
		outputLayer = out;
		hiddenLayers = hidden;
		
		for (Neuron neuron : inputLayer.getNeurons()){mapNeuronToID(neuron);}
		for (NeuralLayer layer : hiddenLayers){
			for (Neuron neuron : layer.getNeurons()){mapNeuronToID(neuron);}
		}
		for (Neuron neuron : outputLayer.getNeurons()){mapNeuronToID(neuron);}
		connectAllLayers (null,null,null);
	}
	
	public NeuralNetwork (int inputLayerSize, int outputLayerSize, int numberOfhidden, int hiddenLayerSizes) throws DuplicateNeuronID_Exception{
		int count = 0;
		inputLayer = new NeuralLayer (0);
		for (int i = 0 ;  i < inputLayerSize ; i++){
			PerceptronNeuron neuron = new PerceptronNeuron(count);
			inputLayer.getNeurons().add(neuron);
			mapNeuronToID(neuron);
			count++;
		}
		hiddenLayers = new ArrayList<NeuralLayer> ();
		for (int i = 0 ;  i < numberOfhidden ; i++){
			NeuralLayer layer = new NeuralLayer (1+i);
			for (int j = 0 ; j < hiddenLayerSizes ; j++){
				PerceptronNeuron neuron = new PerceptronNeuron(count);
				layer.getNeurons().add(neuron);
				mapNeuronToID(neuron);
				count ++;
			}
			hiddenLayers.add(layer);
		}
		outputLayer = new NeuralLayer (numberOfhidden);
		for (int i = 0 ;  i < inputLayerSize ; i++){
			PerceptronNeuron neuron = new PerceptronNeuron(count);
			outputLayer.getNeurons().add(neuron);
			mapNeuronToID(neuron);
			count ++;
		}
		connectAllLayers (null,null,null);
	}
	
	
	public void connectAllLayers (double[] inputWeights, double[] ouputWeights, double[] hiddenWeights){
		connectLayers(inputLayer, hiddenLayers.get(0) , inputWeights);
		connectLayers( hiddenLayers.get(hiddenLayers.size()-1),outputLayer , ouputWeights);
		int toIndex = 0;
		int fromIndex = 0;
		double[] weights = null;
		for (int i = 0; i < hiddenLayers.size()-1; i++){
			toIndex = hiddenLayers.get(i).getNeurons().size() + fromIndex;
			if (hiddenWeights != null){weights = Arrays.copyOfRange(hiddenWeights, fromIndex, toIndex);}
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
	
	public void setWeights (Genome genome){
		for (Gene gene: genome.getGenes()){
			Neuron neuron = allNeurons.get(gene.getNeuronID());
			if (gene.getType() == Gene.WEIGHT){
				neuron.getInputConnections().get(gene.getInputNumber()).setWeight(gene.getWeight());
			}else{
				neuron.setBias(gene.getWeight());
			}
		}
	}
	
	public List<Double> getOutputs (){
		
		 List<Double>  output = new  ArrayList<Double> ();
		for ( Neuron neuron :outputLayer.getNeurons()){
			output.add(neuron.getOutputValue());
		}
		return output;
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
	
	public Map<Integer, Neuron> getAllNeurons() {return allNeurons;}
	
	
	
	
}
