import java.util.Arrays;

public class InicializadorPesosZero implements InicializadorPesos{
		public Double[] obterPesos(int tamanho){
			Double [] retorno = new Double[tamanho];
			Arrays.fill(retorno, 0.0);
			return retorno;
		}
}