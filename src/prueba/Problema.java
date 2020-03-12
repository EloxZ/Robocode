package prueba;
import java.util.*;

public class Problema {
  private char[][] matriz;
  private int numObstaculos;
  private int iX;
  private int iY;
  private int fX;
  private int fY;

  public Problema(long seed, int fi, int co, int nObst) {
	matriz = new char[fi][co];
	for (int i = 0; i<fi; i++) {
		for (int o = 0; o<co; o++) {
			matriz[i][o]='-';
		}
	}
	Random r = new Random(seed);
    if (fi*co < nObst + 2) {
      numObstaculos = 0;
    } else {
      numObstaculos = nObst;
    }
    int num, num2;
    num = r.nextInt(fi);
    num2 = r.nextInt(co);
    // Establezco lugar de inicio
    matriz[num][num2] = 'I';
    iX=num;
    iY=num2;

    // Establezco lugar final

    do {
      num = r.nextInt(fi);
      num2 = r.nextInt(co);
    } while (matriz[num][num2] != '-');
    matriz[num][num2] = 'F';
    fX=num;
    fY=num2;

    // Establezco obstaculos
    int i = 0;
    while (i<numObstaculos) {
      do {
        num = r.nextInt(fi);
        num2 = r.nextInt(co);
      } while (matriz[num][num2] != '-');
      matriz[num][num2] = 'O';
      i++;
    }
  }

  public int getNumObstaculos() {
    return numObstaculos;
  }

	public int getiX() {
		return iX;
	}


	public int getiY() {
		return iY;
	}

	public void setiY(int iY) {
		this.iY = iY;
	}

	public int getfX() {
		return fX;
	}

	public int getfY() {
		return fY;
	}

	public char[][] getMatriz() {
	    return matriz;
  }
}
