package domain;

/**
 * The Class Morada.
 * O tamanho da string e usado como STUB para calcular a distancia
 */
public class Morada {
	
	/** The distancia. */
	private int distancia;
	
	/** The morada. */
	private String morada;
	
	/**
	 * Instantiates a new morada.
	 * O tamanho da string e usado como STUB para calcular a distancia 
	 * @param morada the morada
	 */
	public Morada( String morada ){
		this.morada = morada;
		this.distancia = morada.length();
	}
	
	/**
	 * Gets the distancia.
	 *
	 * @return the distancia
	 */
	public int getDistancia() {
		return distancia;
	}
	
	/**
	 * Sets the distancia.
	 *
	 * @param distancia the new distancia
	 */
	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	
	/**
	 * Gets the morada.
	 *
	 * @return the morada
	 */
	public String getMorada() {
		return morada;
	}
	
	/**
	 * Sets the morada.
	 *
	 * @param morada the new morada
	 */
	public void setMorada(String morada) {
		this.morada = morada;
	}
	
}
