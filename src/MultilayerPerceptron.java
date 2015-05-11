import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
public class MultilayerPerceptron implements RNA{

	private int neuroniosEntrada;
	private double taxaDeAprendizagem;

	private Camada intermediaria;
	private Camada saida;

	public MultilayerPerceptron(double taxaDeAprendizagem, int neuroniosEntrada, int neuroniosCamadaIntermediaria, int neuroniosSaida){
		this.neuroniosEntrada = neuroniosEntrada;
		this.taxaDeAprendizagem = taxaDeAprendizagem;
		intermediaria = new Camada(neuroniosEntrada, neuroniosCamadaIntermediaria);
		saida = new Camada(neuroniosCamadaIntermediaria, neuroniosSaida);
	}
	
	public void treinarRede(Exemplo exemplo){
		Double[] saida = executar(exemplo.getValores());
		
		//valor da classe é o índice saida[classe] = 1 
		Double classe = exemplo.getClasse();
		Double[] resultadoEsperado = new Double[saida.length];
		Arrays.fill(resultadoEsperado, 0d);
		resultadoEsperado[classe.intValue()] = 1.0d;	
		
		
		//fase backpropagation
		Double[] termosErro = new Double[saida.length];
		//descoberta dos termos de errro
		for (int i = 0; i < saida.length; i++) {
			termosErro[i] = saida[i] * (1 - saida[i]) * (resultadoEsperado[i] - saida[i]);
		}
		//ajustes da rede.
		termosErro = this.saida.ajustarPesos(termosErro);
		intermediaria.ajustarPesos(termosErro);
	}
	
	//fase feedforward
	public Double[] executar(Double ... inputs){
		
		if (inputs.length != this.neuroniosEntrada){
			System.out.println("Número de entradas diferente do que esperado para a rede.");
			return null;
		}
		
		//feedforward
		Double[] saidaCamadaIntermediaria = intermediaria.executar(inputs);
		Double[] saidaRede = saida.executar(saidaCamadaIntermediaria);
		
		return saidaRede;
	}
	//Estrutura de armazenamento dos neuronios. 
	class Camada {
		
		//Entradas do neuronio
		private Double[] entradas;
		
		//Neuronio x pesos
		private Map<Integer, Double[]> pesosPorNeuronios;
		
		private Camada(int pesos, int neuronios){
			pesosPorNeuronios = new HashMap<>();
			for (int i = 0; i < neuronios; i ++){
				Double[] d = InicializadorPesosFactory.obterPesosInicializados(pesos);
				pesosPorNeuronios.put(i, d);
			}		
		}
		
		//Unidade de saida (índice é o neuronio, valor vetor[i] = 0.9 )
		//fase de feedforward para cada camada.
		public Double[] executar(Double ... inputs) {
			entradas = inputs;
			Double[] saida = new Double[pesosPorNeuronios.size()];
			for (Integer neuronio : pesosPorNeuronios.keySet()) {
				Double[] weights = pesosPorNeuronios.get(neuronio);
				Double net = 0.0d;
				for (int i = 0; i < weights.length; i++) {
					net += inputs[i] * weights[i];
				}
				//ativa;áo do neuronio 
				saida[neuronio] = 1.0 / (1.0 + Math.exp(-net));
			}
			return saida;
		}
		
		public Double[] ajustarPesos(Double ... errorTerms) {
			for (Entry<Integer, Double[]> entry: pesosPorNeuronios.entrySet()){
				double errorTerm = errorTerms[entry.getKey()];
				Double[] pesos = entry.getValue();
				for (int i = 0; i < pesos.length; i++) {
					//alteração de pesos
					pesos[i] += (taxaDeAprendizagem * errorTerm * entradas[i]);
				}
				pesosPorNeuronios.put(entry.getKey(), pesos);
			}
		
			int inputNumber = entradas.length;
			Double[] retorno = new Double[inputNumber];
			for (int i = 0; i < inputNumber; i++){
				double sum = 0.0;
				for (int j = 0; j < errorTerms.length; j++){
					sum += pesosPorNeuronios.get(j)[i] * errorTerms[j];
				}
				retorno[i] = sum * (entradas[i] * (1 - entradas[i]));
			}
		
			return retorno;
		}
	}
}

