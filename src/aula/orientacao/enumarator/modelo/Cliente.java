package aula.orientacao.enumarator.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Cliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String cpf;
	String nome;
	private ArrayList<IConta> contas = new ArrayList<>();
	
	public Cliente(String cpf, String nome) {
		super();
		this.cpf = cpf;
		this.nome = nome;
	}
	
	public void listarContas(){
		System.out.print("Contas: "+contas);
	}
	
	public boolean contaExistente(String numeroDaConta) {
		for(IConta conta : contas) {
			if(conta.getNumeroDaConta().equals(numeroDaConta)) {
				return true;
			}
		}
		return false;
	}
	
	public void adicionarConta(IConta c) {
		if(!contas.contains(c)) {
			contas.add(c);			
		}else {
			System.out.println("Conta cadastrada");
		}
	}
	
	public void removerConta(IConta c) {
		if(contas.contains(c))
			contas.remove(c);
		else
			System.out.println("Error: Conta inexistente");
	}
	
	public IConta localizarContaPorNumero(String numero) {
		for (IConta conta : contas) {
			if (conta.getNumeroDaConta().equals(numero)) {
				return conta;
			}
		}
		return null;
	}
	
	public void atualizarConta(IConta c) {
		if(contas.contains(c)) {
			int index = contas.indexOf(c);
			contas.set(index, c);
		}else
			System.out.println("Error: Conta não localizada");
	}

	public ArrayList<IConta> getContas() {
		return contas;
	}
	
	public void calcularSaldoTotal() {
		float saldoTotal = 0f;
		for(IConta conta : contas) {
			saldoTotal += conta.getSaldo();
		}
		System.out.println("Saldo total: "+saldoTotal);
	}


	@Override
	public String toString() {
		return "Cliente [ Nome: " + nome + ", CPF: " + cpf + ", contas: " + contas + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}
}
