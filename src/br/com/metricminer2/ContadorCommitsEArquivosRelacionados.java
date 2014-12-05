package br.com.metricminer2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.metricminer2.domain.Commit;
import br.com.metricminer2.domain.Modification;
import br.com.metricminer2.domain.ModificationType;
import br.com.metricminer2.persistence.PersistenceMechanism;
import br.com.metricminer2.scm.CommitVisitor;
import br.com.metricminer2.scm.SCMRepository;

class ContadorCommitsEArquivosRelacionados implements CommitVisitor{
	private static int contador = 1;
	private List<String> tiposArquivos = new ArrayList<String>();  //para pesquisar várias extensões
	private List<String> tiposArquivosRelacionados = new ArrayList<String>(); //para pesquisar várias extensão como arquivos relacionados no commit
	private List<ModificationType> tiposModificacoes = new ArrayList<ModificationType>();
	
	
	public ContadorCommitsEArquivosRelacionados(List<String> tiposArquivos,
			List<String> tiposArquivosRelacionados,
			List<ModificationType> tiposModificacoes) {
		super();
		this.tiposArquivos = tiposArquivos;
		this.tiposArquivosRelacionados = tiposArquivosRelacionados;
		this.tiposModificacoes = tiposModificacoes;
	}


	public void process(SCMRepository repo, Commit commit,
			PersistenceMechanism writer) {
		for (Modification modification : commit.getModifications()) {
			if (possuiAExtensao(modification.getOldPath(),tiposArquivos)!=null && tiposModificacoes.contains(modification.getType())) {
				
				writer.write(contador++, getAbsolutePath(modification), commit.getCommitter().getName(), modification.getType(),commit.getDate().getTime(),  imprimirMapa(getNumeroDeArquivosRelacionadosNoMesmoCommit(modification, commit.getModifications())));
			}
		}
	}

		
	private String imprimirMapa(Map<String, Integer> quantidadePorTipo) {
		String linha = "";
		Iterator entries = quantidadePorTipo.entrySet().iterator();
		while (entries.hasNext()) {
		    Map.Entry entry = (Map.Entry) entries.next();
		    linha += (String)entry.getKey() + ","+(Integer)entry.getValue();
		    if(entries.hasNext()){
		    	linha+=",";
		    }
		}
		return linha;
	}


	private Map<String, Integer> getNumeroDeArquivosRelacionadosNoMesmoCommit(Modification arquivo, List<Modification> arquivosDoMesmoCommit){
		Map<String, Integer> quantidadePorTipo = new HashMap<String, Integer>();
		for(String extensao: tiposArquivosRelacionados){
			quantidadePorTipo.put(extensao,0);
		}
		for (Modification m :arquivosDoMesmoCommit) {
			if(arquivo==m){
				continue;
			}
			String extensao = possuiAExtensao(m.getOldPath(),tiposArquivosRelacionados);
			if(extensao!=null){
				quantidadePorTipo.put(extensao, quantidadePorTipo.get(extensao)+1);
			}
		}
		return quantidadePorTipo;
	}
	
	private String possuiAExtensao(String pathArquivo, List<String> extensoes){
		for(String extensao: extensoes){
			if(pathArquivo.toLowerCase().endsWith(extensao)){
				return extensao;
			}
		}
		return null;
	}
	
	private String getAbsolutePath(Modification modification){
		return modification.getWorkTree()+"/"+modification.getOldPath();
	}

	@Override
	public String name() {
		return "Contador de Commits e Arquivos Relacionados";
	}


	public List<String> getTiposArquivos() {
		return tiposArquivos;
	}


	public void setTiposArquivos(List<String> tiposArquivos) {
		this.tiposArquivos = tiposArquivos;
	}


	public List<String> getTiposArquivosRelacionados() {
		return tiposArquivosRelacionados;
	}


	public void setTiposArquivosRelacionados(List<String> tiposArquivosRelacionados) {
		this.tiposArquivosRelacionados = tiposArquivosRelacionados;
	}

}