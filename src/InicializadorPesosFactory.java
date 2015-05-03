public abstract class InicializadorPesosFactory{
	private static InicializadorPesos inicializadorPesos;
	
	public static void init(boolean inicializaPesos){
		if (inicializaPesos)
			inicializadorPesos = new InicializadorPesosZero();
		else 
			inicializadorPesos = new InicializadorPesosRandom();
	}
	
	public static Double[] obterPesosInicializados(int tamanho){
		if (inicializadorPesos == null)
			init(false);
		return inicializadorPesos.obterPesos(tamanho);
	}
}