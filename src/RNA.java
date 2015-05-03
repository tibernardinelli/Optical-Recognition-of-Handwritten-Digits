import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
public class RNA{

	private int neuroniosEntrada;
	private double taxaDeAprendizagem;

	private Camada intermediaria;
	private Camada Saída;

	public RNA(double taxaDeAprendizagem, int neuroniosEntrada, int neuroniosCamadaIntermediaria, int neuroniosSaida){
		this.neuroniosEntrada = neuroniosEntrada;
		this.taxaDeAprendizagem = taxaDeAprendizagem;
		intermediaria = new Camada(neuroniosEntrada, neuroniosCamadaIntermediaria);
		Saída = new Camada(neuroniosCamadaIntermediaria, neuroniosSaida);
	}
	
	public void treinarRede(Exemplo exemplo){
		double[] saida = executar(exemplo.getValores());
		
		Double classe = exemplo.getClasse();
		Double[] resultadoEsperado = new Double[saida.length];
		Arrays.fill(resultadoEsperado, 0d);
		resultadoEsperado[classe.intValue] = 1.0d;	
		
		double[] termosErro = new double[saida.length];
		for (int i = 0; i < saida.length; i++) {
			termosErro[i] = saida[i] * (1 - saida[i]) * (resultadoEsperado[i] - saida[i]);
		}
		
		termosErro = saida.ajustarPesos(taxaDeAprendizagem, termosErro);
		intermediaria.ajustarPesos(taxaDeAprendizagem, termosErro);
	}
	
	public double[] executar(double ... inputs){
		if (inputs.length != this.neuroniosEntrada){
			System.out.println("Número de entradas diferente do que esperado para a rede.")
			return null;
		}
		
		//feedforward
		double[] saidaCamadaIntermediaria = intermediaria.executar(inputs);
		double[] saidaRede = saida.executar(saidaCamadaIntermediaria);
		
		return saidaRede;
	}

	class Camada {
		
		private Double[] entradas;
		private Map<Integer, Double[]> pesosPorNeuronios;
		
		private Camada(int pesos, int neuronios){
			pesosPorNeuronios = new HashMap<>();
			for (int i = 0; i < neuronios; i ++){
				Double[] d = InicializadorPesosFactory.obterPesosInicializados(pesos);
				pesosPorNeuronios.put(i, d);
			}		
		}
		
		public double[] executar(double ... inputs) {
			entradas = inputs;
			double[] saida = new double[pesosPorNeuronios.size()];
			for (Integer neuronio : pesosPorNeuronios.keySet()) {
				Double[] weights = pesosPorNeuronios.get(neuronio);
				Double net = 0.0d;
				for (int i = 0; i < weights.length; i++) {
					net += inputs[i] * weights[i];
				}
				saida[neuronio] = 1 / (1 + Math.exp(-net));
			}
			return outputs;
		}
		
		public double[] ajustarPesos(double ... errorTerms) {
			for (Entry<Integer, Double[]> entry: pesosPorNeuronios.entrySet()){
				double errorTerm = errorTerms[entry.getKey()];
				Double[] pesos = entry.getValue();
				for (int i = 0; i < pesos.length; i++) {
					pesos[i] += (learnRate * errorTerm * entradas[i]);
				}
				pesosPorNeuronios.put(entry.getKey(), pesos);
			}
		
			int inputNumber = entradas.length;
			double[] retorno = new double[inputNumber];
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

