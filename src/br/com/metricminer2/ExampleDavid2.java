package br.com.metricminer2;

import java.util.HashMap;
import java.util.Map;

import br.com.metricminer2.domain.Commit;
import br.com.metricminer2.domain.Modification;
import br.com.metricminer2.domain.ModificationType;
import br.com.metricminer2.metric.ClassLevelMetricCalculator;
import br.com.metricminer2.metric.ClassLevelMetricFactory;
import br.com.metricminer2.metric.java8.cc.ClassLevelCyclomaticComplexityFactory;
import br.com.metricminer2.persistence.PersistenceMechanism;
import br.com.metricminer2.persistence.PersistenceMechanismBuilder;
import br.com.metricminer2.persistence.csv.CSVFile;
import br.com.metricminer2.scm.GitRepository;
import br.com.metricminer2.scm.SCMRepository;
import br.com.metricminer2.scm.SourceCodeRepositoryNavigator;


public class ExampleDavid2 {
	

	public static void main(String[] args) {
		String repoPath = "/opt/webapps/";
		String csvPath = "/opt/david.csv";
		MMOptions opts = new MMOptions();
		new SourceCodeRepositoryNavigator(opts)
		.in(GitRepository.allIn(repoPath)).process(new ClassLevelMetricCalculatorImpl(new ClassLevelCyclomaticComplexityFactory()),new CSVFile(csvPath)).start();;
	}
}

class ClassLevelMetricCalculatorImpl extends ClassLevelMetricCalculator{
	static int contador = 1;
	
	public ClassLevelMetricCalculatorImpl(ClassLevelMetricFactory factory) {
		super(factory);
	}
	
	public void process(SCMRepository repo, Commit commit,
			PersistenceMechanism writer) {
		for (Modification modification : commit.getModifications()) {
			if (modification.getOldPath().endsWith(".xsd")) {
				if (modification.getType().equals(ModificationType.MODIFY)|| modification.getType().equals(ModificationType.ADD)) {
					writer.write("********");
					writer.write(contador++, modification.getWorkTree()+"/"+modification.getOldPath(), commit.getCommitter().getName(), modification.getType(),commit.getDate().getTime());
					Map<String, Integer> lista = new HashMap<String, Integer>();
					for (Modification m : commit.getModifications()) {
						if (modification != m) {
							if(m.getOldPath().endsWith(".xsd") || m.getOldPath().endsWith(".java") || m.getOldPath().endsWith(".xml")){
								if(lista.get(m.getOldPath().substring(m.getOldPath().lastIndexOf(".")))==null){
									lista.put(m.getOldPath().substring(m.getOldPath().lastIndexOf(".")), 1);	
								}else{
									lista.put(m.getOldPath().substring(m.getOldPath().lastIndexOf(".")), lista.get(m.getOldPath().substring(m.getOldPath().lastIndexOf(".")))+1	);	
								}
							}
						}
					}
					for(String tipo: lista.keySet()){
						writer.write(tipo, lista.get(tipo));
					}
					
				}
			}
		}
	}
}