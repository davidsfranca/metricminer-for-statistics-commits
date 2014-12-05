package br.com.metricminer2;

import java.util.Arrays;
import java.util.List;

import br.com.metricminer2.domain.ModificationType;
import br.com.metricminer2.persistence.csv.CSVFile;
import br.com.metricminer2.scm.GitRepository;
import br.com.metricminer2.scm.SourceCodeRepositoryNavigator;

	
public class MetricasDeCommitsRelacionados {
	public static void main(String[] args) {
		String repoPath = "/opt/webapps/";
		String csvPath = "/opt/metricas.csv";
		List<String> extesoesProcuradas = Arrays.asList("java");
		List<String> extesoesRelacionadasProcuradas = Arrays.asList("java","xml");
		List<ModificationType> tipoDeCommit = Arrays.asList(ModificationType.ADD, ModificationType.MODIFY);
		new SourceCodeRepositoryNavigator(new MMOptions())
		.in(GitRepository.allIn(repoPath)).process(new ContadorCommitsEArquivosRelacionados(extesoesProcuradas,extesoesRelacionadasProcuradas,tipoDeCommit),
				new CSVFile(csvPath)).start();;
	}
}