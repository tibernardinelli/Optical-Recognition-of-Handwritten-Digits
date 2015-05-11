import java.util.Arrays;
import java.util.List;

public class Holdout implements EstrategiaTeste{
	
	private List<Exemplo> subListaTeste = null;
	private List<Exemplo> subListaExercicio = null;
	private RNA rna;
	
	public Holdout(RNA rna){
		this.rna = rna;
	}
	
	
	public void divideMassa(List<Exemplo> exemplos){
		int size = exemplos.size() / 3;
		subListaTeste = exemplos.subList(0, size - 1);
		subListaExercicio = exemplos.subList(size, exemplos.size() - 1);
	}
	
	public double epoca(){
		for (Exemplo e: subListaExercicio){
			rna.treinarRede(e);
		}
		double erroTotalRede = 0d;
		int acertos = 0;
		for (Exemplo e: subListaTeste){
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
		System.out.println(String.format("Acertos da época = %d", acertos));
		return erroTotalRede;
	}
}