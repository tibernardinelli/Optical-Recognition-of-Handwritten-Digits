public class InicializadorPesosRandom implements InicializadorPesos{
		public Double[] obterPesos(int tamanho){
			Double[] retorno = new Double[tamanho];			
			for (int i = 0; i<tamanho; i++)
				retorno[i] = Math.random();
			return retorno;
		}
}