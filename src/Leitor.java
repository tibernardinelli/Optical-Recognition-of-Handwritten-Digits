import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Leitor {
	
	public static List<Exemplo> obterExemplos(Path path) throws IOException{
		List<String> temp = Files.readAllLines(path);
		
		return
			temp.stream().map(linha -> {
				Double[] parametros = 
					Arrays.stream(linha.split(","))
						.map(s-> Double.parseDouble(s))
						.toArray(Double[]::new);
				return new Exemplo(parametros[parametros.length - 1], Arrays.copyOfRange(parametros, 0, parametros.length - 2));
			}).collect(Collectors.toList());
		
	}
}