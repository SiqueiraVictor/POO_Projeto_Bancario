package aula.orientacao.enumarator.modelo;

import java.time.Month;
import java.util.Scanner;
import aula.orientacao.enumarator.persistencia.ClientePersistencia;

public class Aplicacao {

	
	public static void main(String[] args) {
		ClientePersistencia cp = new ClientePersistencia();
		Scanner scanner = new Scanner(System.in);
		int opcao = 0;
		String cpf;
		String numeroDaConta;
		float quantia = 0f;

		do {
			exibirMenu();		
		
		opcao = scanner.nextInt();
		scanner.nextLine();
		
		switch (opcao) {
			case 1:
				criarCliente(scanner, cp);
				break;
			
			case 2:
				cp.listarClienteCadastrados();
				System.out.println("");
				break;
				
			case 3:
				System.out.println("Digite o CPF do cliente: ");
				cpf = scanner.nextLine();
				if(cpf == null || cpf.trim().isEmpty()) {
					System.out.println("Error: O CPF não pode ser vazio.");
				}else {
					
					Cliente cliente = cp.localizarClientePorCPF(cpf);
					if(cliente != null) {
						System.out.println("Cliente encontrado: "+ cliente);
						System.out.println("");
					}else {
						System.out.println("Erro: Cliente não encontrado.");
						System.out.println("");
					}
				}
				break;
				
			case 4:
				System.out.println("Digite o CPF: ");
				cpf = scanner.nextLine();
				removerCliente(cpf, cp);
				break;
				
			case 5:
				System.out.println("Digite o número da conta: ");
				numeroDaConta = scanner.nextLine();
				System.out.println("Digite o CPF do cliente: ");
				cpf = scanner.nextLine();
				criarConta(numeroDaConta, cpf, cp);
				break;
				
			case 6:
				System.out.println("Digite o CPF do cliente: ");
				cpf = scanner.nextLine();
				listarContas(cpf, cp);
				System.out.println("");
				break;

				
			case 7:
				System.out.println("Digite o CPF do cliente: ");
				cpf = scanner.nextLine();
				System.out.println("Digite o número da conta: ");
				numeroDaConta = scanner.nextLine();
				removerConta(cpf, numeroDaConta, cp);
				System.out.println("");
				break;
			
			case 8:
				System.out.println("Digite o CPF do cliente: ");
				cpf = scanner.nextLine();
				System.out.println("Digite o número da conta do cliente: ");
				numeroDaConta = scanner.nextLine();
				System.out.println("Digite a quantia que irá depositar: ");
				quantia = scanner.nextFloat();
				depositar(cpf, numeroDaConta, quantia, cp);
				break;
				
			case 9:
				System.out.println("Digite o CPF do cliente: ");
				cpf = scanner.nextLine();
				System.out.println("Digite o número da conta do cliente: ");
				numeroDaConta = scanner.nextLine();
				System.out.println("Digite a quantia desejada.");
				quantia = scanner.nextFloat();
				sacar(cpf, numeroDaConta, quantia, cp);
				break;
				
			case 10:
				System.out.println("Digite o CPF do remetente: ");
				String cpfDoRemetente = scanner.nextLine();
				if(cpfDoRemetente == null || cpfDoRemetente.trim().isEmpty()) {
					System.out.println("CPF vazio, não foi possível realizar a operação.");
				}else {
					System.out.println("Digite o número da conta do remetente: ");
					String contaDoRemetente = scanner.nextLine();					
					System.out.println("Digite o CPF do destinatário: ");
					String cpfDeDestino = scanner.nextLine();
					System.out.println("Digite o número da conta do destinatario: ");
					String contaDeDestino = scanner.nextLine();
					
					System.out.println("Digite a quantia a ser transferida: ");
					quantia = scanner.nextFloat();
					transferencia(cpfDoRemetente, contaDoRemetente, cp, cpfDeDestino, contaDeDestino, quantia);
				}
				break;
				
			case 11:
				System.out.println("Digite o CPF do cliente: ");
				cpf = scanner.nextLine();
				System.out.println("Digite o número da conta: ");
				numeroDaConta = scanner.nextLine();
				System.out.println("Digite o ano desejado: ");
				int year = scanner.nextInt();
				System.out.println("Digite o mês desejado (de 1 a 12): ");
				int month = scanner.nextInt();
				extrato(cpf, numeroDaConta, year, month, cp);
				break;
				
			case 12:
				System.out.println("Digite o CPF do cliente: ");
				cpf = scanner.nextLine();
				System.out.println("Digite o número da conta do cliente: ");
				numeroDaConta = scanner.nextLine();
				consultarSaldo(cpf, numeroDaConta, cp);
				break;
				
			case 13:
				System.out.println("Digite o CPF do cliente: ");
				cpf = scanner.nextLine();
				balancoGeral(cpf, cp);
				break;
		}
		
		}while(opcao != 0);
		scanner.close();
		
	}
	
	private static void exibirMenu() {
		System.out.println("    MENU ADMINISTRATIVO");
		System.out.println(" ");
		System.out.println("----- Menu do Cliente -----");
		System.out.println(" ");
		System.out.println("1  - Cadastrar cliente");
		System.out.println("2  - Listar clientes cadastrados");
		System.out.println("3  - Consultar cliente por CPF");
		System.out.println("4  - Remover cliente");
		System.out.println(" ");
		System.out.println("---- Controle de Contas ----");
		System.out.println(" ");
		System.out.println("5  - Criar conta");
		System.out.println("6  - Listar as contas cadastradas do cliente");
		System.out.println("7  - Excluir conta de cliente");
		System.out.println(" ");
		System.out.println("---- Menu de Transações ----");
		System.out.println(" ");
		System.out.println("8  - Depósito");
		System.out.println("9  - Saque");
		System.out.println("10 - Transferência");
		System.out.println("11 - Imprimir Extrato");
		System.out.println("12 - Consultar Saldo");
		System.out.println("13 - Consultar Balanço das contas");
		System.out.println(" ");
		System.out.println("0  - Sair");
		System.out.print("Escolha uma opção: ");
	}
	

	private static void criarCliente(Scanner scanner, ClientePersistencia cp) {
		try{
			System.out.println("Digite o nome do cliente: ");
			String nome = scanner.nextLine();
			
			if(nome.trim().isEmpty()) {
				System.out.println("Error: O nome não pode ser vazio.");
				return;
			}
			System.out.println("Digite o CPF do cliente: ");
			String cpf = scanner.nextLine();
			
			if(cpf.trim().isEmpty()) {
				System.out.println("O CPF não pode ser vazio. Tente novamnte.");
			}
			Cliente cliente = new Cliente(cpf, nome);
			cp.salvarCliente(cliente);
			
			System.out.println("O cliente cadastrado com sucesso.");
			System.out.println("");
			}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: Não foi possível cadastrar o cliente.");
		}
	}
	

	private static void removerCliente(String cpf, ClientePersistencia cp) {
		Cliente cliente = cp.localizarClientePorCPF(cpf);
		if(cliente != null) {
			cp.removerCliente(cliente);
			System.out.println("Cliente removido!");
			System.out.println("");
		}else {
			System.out.println("Error: Cliente não foi encontrado.");
			System.out.println("");
		}
	}
	

	private static void criarConta(String numeroDaConta, String cpf, ClientePersistencia cp) {
		Cliente cliente = cp.localizarClientePorCPF(cpf);
		Scanner scanner = new Scanner(System.in);
		
		if(cliente != null) {
			if(cliente.contaExistente(numeroDaConta)) {
				System.out.println("Error: Conta já existente com esse numero.");
			}else {
				System.out.println("Escolha um tipo de conta:");
				System.out.println("1 - Conta Corrente");
				System.out.println("2 - Conta Poupança");
				int tipoConta = scanner.nextInt();
				
				IConta conta;
				switch (tipoConta){
				case 1:
					conta = new ContaCorrente(numeroDaConta);
					break;
				case 2:
					conta = new ContaPoupanca(numeroDaConta);
					break;
				default:
					System.out.println("Error: Opção inválida.");
					return;
				}
				cliente.adicionarConta(conta);
				cp.atualizarCliente(cliente);
				System.out.println("Conta criada!");
				System.out.println("");			
			}
		}else {
			System.out.println("Error: Não foi possível criar a conta.");
			System.out.println("");
		}
	}
	

	public static void listarContas(String cpf, ClientePersistencia cp) {
		Cliente cliente = cp.localizarClientePorCPF(cpf);
		
		if(cliente != null) {
			cliente.listarContas();
			System.out.println("");
		}else {
			System.out.println("Error: Não foi possível listar os cliente.");
			System.out.println("");
		}
	}
	
	private static void removerConta(String cpf, String numeroDaconta, ClientePersistencia cp) {
		Cliente cliente = cp.localizarClientePorCPF(cpf);
		
		if(cliente != null) {
			IConta conta = cliente.localizarContaPorNumero(numeroDaconta);
			if(conta != null) {
				cliente.removerConta(conta);
				System.out.println("Conta removida.");
				cp.atualizarCliente(cliente);
			}else {
				System.out.println("Error: Não foi possível remover a conta.");
			}
		}
	}
	
	private static void depositar(String cpf, String numeroDaConta, float quantia, ClientePersistencia cp) {
		Cliente cliente = cp.localizarClientePorCPF(cpf);
		if(cliente != null) {
			IConta conta = cliente.localizarContaPorNumero(numeroDaConta);
			if(conta != null) {
				conta.depositar(quantia);
				cp.atualizarCliente(cliente);
				System.out.println("Deposito realizado com sucesso.");
				System.out.println("");
			}else {
				System.out.println("Error: Não foi possível realizar o deposito.");
				System.out.println("");
			}
		}
	}
	
	private static void sacar(String cpfDoRemetente, String numeroDaconta, float quantia, ClientePersistencia cp) {
		Cliente cliente = cp.localizarClientePorCPF(cpfDoRemetente);
		if(cliente != null) {
			IConta conta = cliente.localizarContaPorNumero(numeroDaconta);
			if(conta != null) {
				conta.sacar(quantia);
				cp.atualizarCliente(cliente);
				System.out.println("Saque realizado com sucesso.");
				System.out.println("");
			}else {
				System.out.println("Não foi possível realizar o saque.");
				System.out.println("");
			}
		}
	}
	
	private static void transferencia(String cpfDoRemetente, String contaDoRemetente, ClientePersistencia cp, String cpfDeDestino, String contaDeDestino, float quantia) {
		Cliente remetente = cp.localizarClientePorCPF(cpfDoRemetente);
		Cliente destino = cp.localizarClientePorCPF(cpfDeDestino);
		
		if(remetente != null && destino != null) {
			IConta contaRemetente = remetente.localizarContaPorNumero(contaDoRemetente);
			IConta contaDestino = destino.localizarContaPorNumero(contaDeDestino);
			if(contaRemetente != null && contaDestino != null) {
				if(quantia > 0 && quantia < contaRemetente.getSaldo()) {	
					contaRemetente.transferir(quantia, contaDestino);
					cp.atualizarCliente(remetente);
					cp.atualizarCliente(destino);
					System.out.println("Transferância realizada com sucesso.");
					System.out.println("");
				}else{
					System.out.println("Error: Quantia inválida.");
				}
			}else{
				System.out.println("Error: A conta nao foi encontrada.");
			}
		}else{
			System.out.println("Error: Um dos clientes não foi encontrado.");
		}
	}
	
	private static void extrato(String cpf, String numeroDaConta, int year, int month, ClientePersistencia cp) {
		Cliente cliente = cp.localizarClientePorCPF(cpf);
		
		if(cliente != null) {
			IConta conta = cliente.localizarContaPorNumero(numeroDaConta);
			if(conta != null) {
				if(month < 1 || month > 12) {
					System.out.println("Error: Número de mês inválido.");
				}else {
					Month mes = Month.of(month);
					conta.extrato(year, mes);
				}
			}else{
				System.out.println("Error: Conta não encontrada.");
			}
		}
	}
	
	private static void consultarSaldo(String cpf, String numeroDaConta, ClientePersistencia cp) {
		Cliente cliente = cp.localizarClientePorCPF(cpf);
		if(cliente != null) {
			IConta conta = cliente.localizarContaPorNumero(numeroDaConta);
			if(conta != null) {
				float saldo = conta.getSaldo();
				System.out.println("Saldo da conta: "+saldo);
			}
			else {
				System.out.println("Error: Conta não encontrada.");
			}
		}
	}
	
	public static void balancoGeral(String cpf, ClientePersistencia cp) {
		Cliente cliente = cp.localizarClientePorCPF(cpf);
		if(cliente != null) {
			cliente.calcularSaldoTotal();
		}else {
			System.out.println("Error: Cliente não encontrado.");
		}
	}
}
