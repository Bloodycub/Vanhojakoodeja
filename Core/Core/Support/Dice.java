package Core.Support;

import java.util.Random;

public class Dice {

	private Random random;
	private int numero;
	private int arvo;
	public Dice(int arvo) {
		this.random = new Random();
	}

	public int Trow() {
		int u = random.nextInt(numero);
		return u + 1;
	}
	public void Givearvo(int numero) {
		this.arvo = numero;
		if (arvo == 0) return; //Lisäsin rivin ettei koodi mussuta turhia erroreita :D
	}

	
}
