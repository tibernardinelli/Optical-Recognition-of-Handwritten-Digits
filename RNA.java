import java.util.Map;
import java.util.HashMap;

public class RNA{

	private Camada intermediaria;
	private Camada Saída;

	public RNA(int neuroniosEntrada, int neuroniosCamadaIntermediaria, int neuroniosSaida){
		intermediaria = new Camada(neuroniosEntrada, neuroniosCamadaIntermediaria);
		Saída = new Camada(neuroniosCamadaIntermediaria, neuroniosSaida);
		
	}
	

	class Camada {
		private Map<Integer, Double[]> pesosPorNeuronios;
		
		private Camada(int pesos, int neuronios){
			pesosPorNeuronios = new HashMap<>();
			for (int i = 0; i < neuronios; i ++){
				Double[] d = InicializadorPesosFactory.obterPesosInicializados(pesos);
				pesosPorNeuronios.put(i, d);
			}		
		}
	}

}

