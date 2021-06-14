package TyMA2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SecuenciasRyRC {
	
	public static StringBuilder leerFichero(String file) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(file));
		StringBuilder seq = new StringBuilder();
		while (sc.hasNextLine()) {
			String line = sc.nextLine().trim();
			if (!line.equals("")) {
				if (line.charAt(0) != '>') {
					seq.append(line.toUpperCase());
				}
			}
		}
		return seq;
	}

	public static StringBuilder secuenciaRC(StringBuilder seqR) {
		StringBuilder seqRC = new StringBuilder();
		for (int i = 0; i < seqR.length(); i++) {
			if (seqR.charAt(i) == 'A') {
				seqRC.append('T');
			} else if (seqR.charAt(i) == 'C') {
				seqRC.append('G');
			} else if (seqR.charAt(i) == 'G') {
				seqRC.append('C');
			} else if (seqR.charAt(i) == 'T') {
				seqRC.append('A');
			}
		}
		return seqRC;
	}
	
	public static void main(String[] args) throws FileNotFoundException {

		String file = args[0];

		StringBuilder seq = leerFichero(file);
		System.out.println("Secuencia molde:\n" + seq + "\n");

		StringBuilder seqR = new StringBuilder();
		seqR = seq.reverse();
		System.out.println("Secuencia reversa:\n" + seqR + "\n");

		StringBuilder seqRC = secuenciaRC(seqR);
		System.out.println("Secuencia reversa complementaria:\n" + seqRC);

	}

}
