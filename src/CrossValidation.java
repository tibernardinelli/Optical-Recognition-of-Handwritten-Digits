import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CrossValidation{
	
	private Map<Integer, KFold> exemplosPorKFold;
	
	public CrossValidation(){
		exemplosPorKFold = new HashMap<>();
	}
	
	public void divideMassa(List<Exemplo> exemplos){
			exemplosPorKFold.clear();
			int n = exemplos.size();
			int foldSize = ((double)n/10).intValue;
			
			for (int i = 0; i<10; i++){
				
				List<exemplo> sublist = exemplos.sublist(i * foldSize, (i * foldsize) + foldsize - 1);
										
				exemplosPorKFold.put(i, new KFold(treinamento, teste);
			}	
	}
	
	//retorna erro a cada época executada;
	public double epoca(){
		
	}
	
	private class KFold(){
		
		private List<Exemplo> conjuntoTreinamento;
		private List<Exemplo> conjuntoTeste;
		
		public KFold(List<Exemplo> conjuntoTreinamento, List<Exemplo> conjuntoTeste){
			this.conjuntoTreinamento = conjuntoTreinamento;
			this.conjuntoTeste = conjuntoTeste;
		}
		
		public getConjuntoTreinamento(){
			return conjuntoTreinamento;
		}
		
		public getConjuntoTeste(){
			return conjuntoTeste;
		}
	}
	
}