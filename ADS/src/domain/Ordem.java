package domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class Ordem.
 */
public class Ordem{
	
	private int idOrdem;
	private int idFuncionario;
	private int idCliente;
	private Morada descOrigem;
	private Morada descDestino;
	private Date dataPedido;
	private int volume;

	/**
	 * Instantiates a new ordem.
	 *
	 * @param idOrdem the id ordem
	 * @param idFuncionario the id funcionario
	 * @param idCliente the id cliente
	 * @param descOrigem the desc origem
	 * @param descDestino the desc destino
	 * @param dataPedido the data pedido
	 * @param volume the volume
	 */
	public Ordem(int idOrdem, int idFuncionario, int idCliente, String descOrigem,
			String descDestino, Date dataPedido, int volume) {
		
		
		this.idOrdem = idOrdem;
		this.idFuncionario = idFuncionario;
		this.idCliente = idCliente;
		this.descOrigem = new Morada(descOrigem);
		this.descDestino = new Morada(descDestino);
		this.dataPedido = dataPedido;
		this.volume = volume;
	}

	/**
	 * Gets the id ordem.
	 *
	 * @return the id ordem
	 */
	public int getIdOrdem() {
		return idOrdem;
	}

	/**
	 * Gets the id funcionario.
	 *
	 * @return the id funcionario
	 */
	public int getIdFuncionario() {
		return idFuncionario;
	}
	

	/**
	 * Gets the id cliente.
	 *
	 * @return the id cliente
	 */
	public int getIdCliente() {
		return idCliente;
	}
	

	/**
	 * Gets the desc origem.
	 *
	 * @return the desc origem
	 */
	public String getDescOrigem() {
		return descOrigem.getMorada();
	}

	/**
	 * Gets the descdestino.
	 *
	 * @return the descdestino
	 */
	public String getDescdestino() {
		return descDestino.getMorada();
	}

	/**
	 * Gets the data pedido.
	 *
	 * @return the data pedido
	 */
	public Date getDataPedido() {
		return dataPedido;
	}

	/**
	 * Gets the volume.
	 *
	 * @return the volume
	 */
	public int getVolume() {
		return volume;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		return this.getIdOrdem() + ";" + this.getIdFuncionario() + ";" + this.getIdCliente() + ";" + this.getDescOrigem() + ";" + this.getDescdestino() + ";" + dateFormat.format(this.getDataPedido()) + ";" + this.getVolume();
	}

}
