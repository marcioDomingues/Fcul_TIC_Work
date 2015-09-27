package domain;

import javax.ejb.Remote;

@Remote
public interface CalculatorRemoteCSS {
	public int produtoCSS(int a, int b);
}
