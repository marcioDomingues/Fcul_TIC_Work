package domain;

import javax.ejb.Stateless;

@Stateless
public class CalculatorCSS implements CalculatorLocalCSS, CalculatorRemoteCSS {
	@Override 
	public int produtoCSS(int a, int b) {
		return a*b;
	}
}
