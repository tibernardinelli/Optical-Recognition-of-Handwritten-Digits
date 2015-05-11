import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrossValidation implements EstrategiaTeste{
	
	private Map<Integer, KFold> exemplosPorKFold;
	private RNA rna;
	
	public CrossValidation(RNA rna){
		exemplosPorKFold = new HashMap<>();
		this.rna = rna;
	}
	
	public void divideMassa(List<Exemplo> exemplos){
			exemplosPorKFold.clear();
			int n = exemplos.size();
			int foldSize = n/10;
			int testesize = foldSize/10;
			for (int i = 0; i<10; i++){
				
				List<Exemplo> sublist = exemplos.subList(i * foldSize, (i * foldSize) + foldSize - 1);
				List<Exemplo> treinamento = sublist.subList(0, sublist.size()-testesize-1);
				List<Exemplo> teste = sublist.subList(sublist.size()-testesize, sublist.size()-1);
				exemplosPorKFold.put(i, new KFold(treinamento, teste));
			}	
	}
	
	//retorna erro a cada época executada;
	public double epoca(){
		double erroTotalRede = 0d;		
		for (KFold kfold: exemplosPorKFold.values()){
			for (Exemplo e: kfold.getConjuntoTreinamento()){
				rna.treinarRede(e);
			}
			int acertos = 0;
			for (Exemplo e: kfold.getConjuntoTeste()){
				double erroExemplo = 0d;
			
				Double[] resultadoObtido = rna.executar(e.getValores());
				
				Double classe = e.getClasse();
				Double[] resultadoEsperado = new Double[resultadoObtido.length];
				Arrays.fill(resultadoEsperado, 0d);
				resultadoEsperado[classe.intValue()] = 1.0d;	
				
				Double maior = Double.MIN_VALUE;
				int maiorIndice = -1;
				for (int i = 0; i < resultadoObtido.length; i++)
					if (resultadoObtido[i] > maior){
						maiorIndice = i;
						maior = resultadoObtido[i];
					}
				
				if (maiorIndice == classe.intValue())
					acertos ++;
				
				//System.out.println(String.format("indice esperado: %f, indice obtido %d com valor %f | acertos: %d", classe, maiorIndice, maior, acertos));
				
				for (int i = 0; i < resultadoObtido.length; i++){
					erroExemplo += Math.pow(resultadoEsperado[i] - resultadoObtido[i], 2);
				}
				erroTotalRede += erroExemplo;	
			}
			System.out.println(String.format("Acertos da época = %d de  %d", acertos, kfold.getConjuntoTeste().size()));
		}
		return erroTotalRede;
	}
	
	class KFold{
		
		private List<Exemplo> conjuntoTreinamento;
		private List<Exemplo> conjuntoTeste;
		
		public KFold(List<Exemplo> conjuntoTreinamento, List<Exemplo> conjuntoTeste){
			this.conjuntoTreinamento = conjuntoTreinamento;
			this.conjuntoTeste = conjuntoTeste;
		}
		
		public List<Exemplo> getConjuntoTreinamento(){
			return conjuntoTreinamento;
		}
		
		public List<Exemplo> getConjuntoTeste(){
			return conjuntoTeste;
		}
	}
	
}