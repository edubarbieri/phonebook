package br.com.edubarbieri.app.mbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class DefaultMBean {
	private String nome;
	private String senha;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void onClick(){
		System.out.println(this);
	}
	@Override
	public String toString() {
		return "DefaultMBean [nome=" + nome + ", senha=" + senha + "]";
	}
	
	
	
}
