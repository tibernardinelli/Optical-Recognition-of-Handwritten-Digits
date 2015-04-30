import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Exemplo {
	
	/*
		formatação do conjunto em atributos descritivos e atributo de classe:
		Os atributos descritivos estão dentro do mapa valores e o de classe dentro da propriedade classe.
	*/
	
	private Map<Integer, Double> valores;
	private double classe;
	
	public Exemplo(){
		valores = new HashMap<Integer, Double>();
	}
	
	public Exemplo(double classe, Double ... parametros){
		this();
		setClasse(classe);
		setValores(parametros);
	}
	
	public int getNumeroPropriedades(){
		return valores.size();
	}
	
	public void setValores(Double ... parametros){
		for (int i = 0; i< parametros.length; i++){
			valores.put(i, parametros[i]);
		}
	}
	
	public void setValor(int indice, double novoValor){
		valores.put(indice, novoValor);	
	}
	
	public Double getValor(int indice){
		if (!valores.containsKey(indice))
			return null;
		return valores.get(indice);
	}
	
	public void setClasse(double classe){
		this.classe = classe;
	}
	
	public Double getClasse(){
		return classe;
	}
	
	// implementção do z-score normalization
	public static void normalizadorZScore(List<Exemplo> exemplos){
		//normaliza todas as propriedades;	
		if (exemplos.size() == 0)
			return;
		for (int i = 0; i < exemplos.get(0).getNumeroPropriedades(); i ++ ){
			//Calculo da m[edia;
			double somatoria = 0.0;
			for (Exemplo e: exemplos){
				somatoria += e.getValor(i);
			}
			double media = somatoria / exemplos.size();
			//Calculo da variancia;
			Double variancia = 0.0;
			for (Exemplo e: exemplos){
				variancia += Math.pow(e.getValor(i) - media, 2);
			}
			double n = exemplos.size();
			//calculo do desvio padrao
			double desvioPadrao = Math.sqrt(variancia/(n-1));
			
			for (Exemplo e: exemplos){
				Double valorAtual = e.getValor(i);
				Double novoValor = (valorAtual - media) / desvioPadrao;
				e.setValor(i, novoValor);
			}
		}
	}
}