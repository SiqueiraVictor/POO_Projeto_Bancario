package aula.orientacao.enumarator.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ContaPoupanca implements Serializable, IConta{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String numero;
	float saldo;
	LocalDateTime dataAbertura;
	boolean status;

	private ArrayList<Transacao> transacoes = new ArrayList<>();
	private static final long DIAS_ATUALIZACAO_RENDIMENTO = 1;

	public ContaPoupanca() {
	}
	
	public ContaPoupanca(String numero) {
		super();
		this.numero = numero;
		this.saldo = 0f;
		this.dataAbertura = LocalDateTime.now();
		this.status = true;

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::rendimentoPoupanca, 0, DIAS_ATUALIZACAO_RENDIMENTO, TimeUnit.DAYS);
	}
	
	@Override
	public String toString() {
		return "\n"+"Conta [Número da Conta = " + numero + ", Saldo em Conta = " + saldo + ", Data de abertura = " + dataAbertura + 
		" Tipo de Conta = Conta Poupança." + ", Status = " + status + "]" + "\n";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaPoupanca other = (ContaPoupanca) obj;
		return Objects.equals(numero, other.numero);
	}
	
	



	
	public void depositar(float quantia) {
		if(status && quantia > 0) {
			saldo += quantia;
			transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.CREDITO));
		}
			
		else
			System.out.println("Error: Transação não realizada.");
	}
	
	public void sacar(float quantia) {
		if(status && quantia > 0 && quantia <= saldo) {
			saldo -= quantia;
			transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.DEBITO));
		}
		else
			System.out.println("Error: Transação não realizada.");
	}
	
	public void extrato(int year, Month month) {
		for(Transacao t : transacoes) {
			if(t.dataTransacao.getYear() == year && t.dataTransacao.getMonth() == month) {
				System.out.println(t);
			}
		}
	}
	
	public void transferir(float quantia, IConta contaDestino) {
		if(contaDestino instanceof ContaPoupanca) {
			if (this.status && contaDestino.getSatus() && quantia > 0 && quantia <= this.saldo) {
				this.saldo -= quantia;
				contaDestino.setSaldo(contaDestino.getSaldo() + quantia);
				this.transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.TRANSFERENCIA_DEBITO, contaDestino));
				contaDestino.getTransacoes().add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.TRANSFERENCIA_CREDITO, this));
			}else{
				System.out.println("Error: Conta desabilitada.");
				}
		}else{
			if (this.getSatus() && contaDestino.getSatus() && quantia > 0 && getSaldo() >= (quantia+(quantia*IConta.TAXA_ADMINISTRATIVA))) {
				this.saldo -= (quantia+(quantia*IConta.TAXA_ADMINISTRATIVA));
				contaDestino.setSaldo(contaDestino.getSaldo() + quantia);
				this.transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.TRANSFERENCIA_DEBITO, contaDestino));
				contaDestino.getTransacoes().add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.TRANSFERENCIA_CREDITO, this));
			}else{
				System.out.println("Error: Conta desabilitada.");
				}
		}	
	}

	public void rendimentoPoupanca(){
			if(status == true) {
				LocalDateTime dataAtual = LocalDateTime.now();
				long diasDesdeAbertura = ChronoUnit.DAYS.between(dataAbertura, dataAtual);
				if (diasDesdeAbertura >= 1) {
					float rendimento = saldo * TAXA_DE_RENDIMENTO;
					saldo = saldo + rendimento;
					this.transacoes.add(new Transacao(rendimento, dataAtual, TipoTransacao.RENDIMENTO));
				}
			}
	}

	

	@Override
	public boolean getSatus() {
		return status;
	}

	@Override
	public float getSaldo() {
		return saldo;
	}

	@Override
	public void setSaldo(float novoSaldo) {
		this.saldo = novoSaldo;
	}


	@Override
	public List<Transacao> getTransacoes() {
		return transacoes;
	}


	@Override
	public String getNumeroDaConta() {
		return numero;
	}

}
