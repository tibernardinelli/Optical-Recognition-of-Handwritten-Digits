import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * javac Main.java java Main data/optdigits.tra data/optdigits.tra
 * data/optdigits.tra 0.1 3 3 False
 **/
public class Main {
	public static void main(String[] args) {
		if (args.length != 7) {
			System.out
					.println(String
							.format("Argumentos esperados: \n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s",
									"nome do arquivo do conjunto de dados de treino",
									"nome do arquivo do conjunto de dados de valida ̧c ̃ao",
									"nome do arquivo do conjunto de dados de teste",
									"taxa de aprendizado inicial",
									"número de neurônios na cada escondida (para a rede MLP)",
									"nú́mero de neurônios para cada classe (para a rede LVQ)",
									"inicialização de pesos (zero/aleató́ria, True/False)"));
			return;
		}
		Path caminhoTreino;
		Path caminhoValidacao;
		Path caminhoTeste;

		double taxaAprendizadoInicial;
		int numeroNeuroniosCamadaEscondida;
		int numeroNeuroniosClasse;
		boolean estrategiaInicializacaoPesos;
		boolean rnaTrueLvqFalse;
		try {
			caminhoTreino = Paths.get(args[0]);
			caminhoValidacao = Paths.get(args[1]);
			caminhoTeste = Paths.get(args[2]);

			taxaAprendizadoInicial = Double.parseDouble(args[3]);

			numeroNeuroniosCamadaEscondida = Integer.parseInt(args[4]);

			numeroNeuroniosClasse = Integer.parseInt(args[5]);
			rnaTrueLvqFalse = numeroNeuroniosCamadaEscondida != 0
					&& numeroNeuroniosClasse == 0;

			estrategiaInicializacaoPesos = Boolean.parseBoolean(args[6]);
			InicializadorPesosFactory.init(estrategiaInicializacaoPesos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.toString());
		}
		List<Exemplo> exemplos;
		try {
			Instant inicio = Instant.now();

			// EXECUÇÃO.
			// 1. leitura;
			exemplos = Leitor.obterExemplos(caminhoTreino);
			// 2. Normalizaçáo
			Exemplo.normalizadorZScore(exemplos);
			// 3. Configuração da rede.
			Exemplo temp = exemplos.get(0);
			RNA rna = null;

			if (rnaTrueLvqFalse)
				rna = new MultilayerPerceptron(taxaAprendizadoInicial,
						temp.getNumeroPropriedades(),
						numeroNeuroniosCamadaEscondida, 10);
			else
				rna = new LearningVectorQuantization();

			// 4. holdout
			Collections.shuffle(exemplos, new Random());
			//EstrategiaTeste holdout = new CrossValidation(rna);
			EstrategiaTeste holdout = new Holdout(rna);
			holdout.divideMassa(exemplos);
			double erro = 100.0;
			int execucaoNr = 1;
			do {
				erro = holdout.epoca();
				System.out.println(String.format("Epoca %d com erro %f",
						execucaoNr, erro));
				execucaoNr++;
			} while (erro > 10);

			// FIM EXECUÇÃO
			Duration duracao = Duration.between(inicio, Instant.now());
			System.out.println(String.format("tempo total: %d em %d",
					exemplos.size(), duracao.toMillis()));
		} catch (IOException e) {
			System.err.println("Nome do arquivo errado");
			throw new RuntimeException(e.toString());
		}
	}
}