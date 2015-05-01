import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Random;
/**
	javac Main.java
	java Main data/optdigits.tra data/optdigits.tra data/optdigits.tra 0.1 3 3 False
**/
public class Main{
	public static void main(String[] args){
		if (args.length != 7) {
			System.out.println(String.format("Argumentos esperados: \n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s", 
				"nome do arquivo do conjunto de dados de treino",
				"nome do arquivo do conjunto de dados de valida ̧c ̃ao",
				"nome do arquivo do conjunto de dados de teste",
				"taxa de aprendizado inicial",
				"nu ́mero de neurˆonios na cada escondida (para a rede MLP)",
				"nu ́mero de neur ́onios para cada classe (para a rede LVQ)",
				"inicializa ̧c ̃ao de pesos (zero/aleato ́ria, True/False)"
			));
			return;
		}
		Path caminhoTreino;
		Path caminhoValidacao;
		Path caminhoTeste;
		
		double taxaAprendizadoInicial;
		int numeroNeuroniosCamadaEscondida;
		int numeroNeuroniosClasse;
		boolean estrategiaInicializacaoPesos;
		 
		try {
			caminhoTreino = Paths.get(args[0]);
			caminhoValidacao = Paths.get(args[1]);
			caminhoTeste = Paths.get(args[2]);
			
			taxaAprendizadoInicial = Double.parseDouble(args[3]);
			
			numeroNeuroniosCamadaEscondida = Integer.parseInt(args[4]);
			
			numeroNeuroniosClasse = Integer.parseInt(args[5]);
			
			estrategiaInicializacaoPesos = Boolean.parseBoolean(args[6]);
			InicializadorPesosFactory.init(estrategiaInicializacaoPesos);
		} catch (Exception e){
			e.printStackTrace();
			throw new IllegalArgumentException(e.toString());
		}
		List<Exemplo> exemplos;
		try {
			Instant inicio = Instant.now();
			 
			// EXECUÇÃO.
			exemplos = Leitor.obterExemplos(caminhoTreino);
			Exemplo.normalizadorZScore(exemplos);
			Collections.shuffle(exemplos, new Random());
			Exemplo temp = exemplos.get(0);
			RNA rna = new RNA(temp.getNumeroPropriedades(), numeroNeuroniosCamadaEscondida, 9);
			// FIM EXECUÇÃO
			Duration duracao = Duration.between(inicio, Instant.now());
			System.out.println(String.format("%d em %d", exemplos.size(), duracao.toMillis()));
		} catch(IOException e) {
			System.err.println("Nome do arquivo errado");
			throw new RuntimeException(e.toString());
		}
	}
}