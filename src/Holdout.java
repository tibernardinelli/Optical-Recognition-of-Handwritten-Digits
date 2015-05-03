import java.util.List;

public class Holdout implements EstrategiaTeste{
	
	private List<Exemplo> subListaTeste = null;
	private List<Exemplo> subListaExercicio = null;
	
	public void divideMassa(List<Exemplo> exemplos){
		int size = tuples.size() / 3;
		List<Exemplo> subListaTeste = tuples.subList(0, size - 1);
		List<Exemplo> subListaExercicio = tuples.subList(size, tuples.size() - 1);
	}
	
	public double epoca(){
		for (Exemplo e: subListaExercicio){
			
		}
		
		return 0.0d;
	}
}