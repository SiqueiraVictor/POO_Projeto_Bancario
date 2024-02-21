package aula.orientacao.enumarator.persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import aula.orientacao.enumarator.modelo.Cliente;
import aula.orientacao.enumarator.modelo.IConta;

public class ClientePersistencia implements Serializable {

	ArrayList<Cliente> clientesCadastrados = new ArrayList<>();

	public ClientePersistencia() {
		clientesCadastrados = new ArrayList<>();
		carregarArquivo();
	}
	
	public void listarClienteCadastrados() {
		for(Cliente cliente : clientesCadastrados) {
			System.out.println(cliente);
		}
	}
	
	public ArrayList<Cliente> getClienteCadastrados(){
		return clientesCadastrados;
	}

	public void salvarCliente(Cliente c) {
		if (!clientesCadastrados.contains(c)) {
			clientesCadastrados.add(c);
			salvarArquivo();
		} else
			System.out.println("Error: Cliente já cadastrado");

	}

	public void removerCliente(Cliente c) {
		if (clientesCadastrados.contains(c))
			clientesCadastrados.remove(c);
		else
			System.out.println("Error: Cliente não encontrado");
	}

	public Cliente localizarClientePorCPF(String cpf) {
		Cliente temp = new Cliente(cpf, null);
		if (clientesCadastrados.contains(temp)) {
			int index = clientesCadastrados.indexOf(temp);
			temp = clientesCadastrados.get(index);
			return temp;
		}
		return null;
	}

	public void atualizarCliente(Cliente c) {
		if(clientesCadastrados.contains(c)) {
			int index = clientesCadastrados.indexOf(c);
			clientesCadastrados.set(index, c);
			salvarArquivo();
		}
	}

	private void carregarArquivo() {

		try {
			FileInputStream fis = new FileInputStream("dados");
			ObjectInputStream ois = new ObjectInputStream(fis);
			clientesCadastrados = (ArrayList<Cliente>) ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void salvarArquivo() {
		try {
			FileOutputStream fos = new FileOutputStream("dados");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(clientesCadastrados);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
