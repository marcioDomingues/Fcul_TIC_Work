package tests;

import domain.Cliente;
import domain.Funcionario;
import domain.Morada;
import domain.Ordem;
import domain.ProcessOrders;

public class DomainTest {

	public static void main(String[] args) {
		
		Funcionario f = new Funcionario();
		f.setFuncionarioID(10);
		
		Cliente c1 = new Cliente();
		c1.setClienteID(1);
		
		Cliente c2 = new Cliente();
		c2.setClienteID(2);
		
		Morada m1 = new Morada("Rua B");
		
		Morada m2 = new Morada("Rua B");
		
		ProcessOrders p = new ProcessOrders();
		
		Ordem o1 = new Ordem(1, f.getFuncionarioID(), c1.getClienteID(), m1.getMorada(), m2.getMorada(), new java.util.Date() , 5);
		Ordem o2 = new Ordem(2, f.getFuncionarioID(), c2.getClienteID(), m1.getMorada(), m2.getMorada(), new java.util.Date() , 9);
		
		System.out.println( "Ordem "+o1.getIdOrdem()+" adicionada: "+p.adicionarOrdem(o1) );
		System.out.println( "Ordem "+o2.getIdOrdem()+" adicionada: "+p.adicionarOrdem(o2) );
		System.out.println( "Tentar adicionar a ordem "+o1.getIdOrdem()+" novamente: "+p.adicionarOrdem(o1) );
		System.out.println( "Tentar adicionar a ordem "+o2.getIdOrdem()+" novamente: "+p.adicionarOrdem(o2) );
	}
}
