package prueba;

public class pruebas {

	public static void main(String[] args) {
		long seed = 0;
		int nFil = 16;
		int nCol = 12;
		int nObst = 40;
		
		Problema p = new Problema(seed, nFil, nCol, nObst);
		char[][] m = p.getMatriz();
		for (int fi = 0; fi<nFil; fi++) {
			for (int co = 0; co<nCol; co++) {
				System.out.print(m[fi][co] + " ");
			}
			System.out.println();
		}

	}

}
