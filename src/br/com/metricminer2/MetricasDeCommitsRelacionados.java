package br.com.metricminer2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import br.com.metricminer2.domain.ModificationType;
import br.com.metricminer2.persistence.csv.CSVFile;
import br.com.metricminer2.scm.GitRepository;
import br.com.metricminer2.scm.SourceCodeRepositoryNavigator;

	
public class MetricasDeCommitsRelacionados {
	public static void main(String[] args) {
		String repoPath = "E:/webapps/";
		String csvPath = "E:/metricas.csv";
		List<String> extensoesProcuradas = Arrays.asList("xml", "java");
		List<String> extensoesRelacionadasProcuradas = Arrays.asList("java");
		List<ModificationType> tipoDeCommit = Arrays.asList(ModificationType.ADD, ModificationType.MODIFY);
		CSVFile writer = new CSVFile(csvPath);
		gerarCabecalhoCSV(writer, extensoesRelacionadasProcuradas);
		new SourceCodeRepositoryNavigator(new MMOptions())
		.in(GitRepository.allIn(repoPath)).process(new ContadorCommitsEArquivosRelacionados(extensoesProcuradas,extensoesRelacionadasProcuradas,tipoDeCommit),writer).start();
	}

	private static void gerarCabecalhoCSV(CSVFile writer, List<String> extensoesRelacionadasProcuradas) {
		String cabecalho = "id,nome_arquivo,commiter,tipo_commit,data";
		 Iterator<String> i = extensoesRelacionadasProcuradas.iterator();
         while (i.hasNext()){
        	 	cabecalho+=",";
                String it = i.next();
                cabecalho+=it+",qnt_"+it;
         }
         writer.write(cabecalho);
	}
}