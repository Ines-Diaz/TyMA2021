package TyMA2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Fragmentos {

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

	public static ArrayList<ArrayList<String>> marcosDeLectura(StringBuilder seq, StringBuilder seqRC) {
		ArrayList<ArrayList<String>> marcosLectura = new ArrayList<>();
		ArrayList<String> seqSepCodonesM1 = new ArrayList<>();
		ArrayList<String> seqSepCodonesM2 = new ArrayList<>();
		ArrayList<String> seqSepCodonesM3 = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			ArrayList<String> seqSepCodones = new ArrayList<>();
			for (int j = i; j < seq.length(); j = j + 3) {
				if (j + 2 < seq.length()) {
					StringBuilder codon = new StringBuilder();
					codon.append(seq.charAt(j));
					codon.append(seq.charAt(j + 1));
					codon.append(seq.charAt(j + 2));
					seqSepCodones.add(codon.toString());
				}
			}
			if (i == 0) {
				seqSepCodonesM1 = seqSepCodones;
			} else if (i == 1) {
				seqSepCodonesM2 = seqSepCodones;
			} else if (i == 2) {
				seqSepCodonesM3 = seqSepCodones;
			}
		}
		marcosLectura.add(seqSepCodonesM1);
		marcosLectura.add(seqSepCodonesM2);
		marcosLectura.add(seqSepCodonesM3);
		ArrayList<String> seqSepCodonesM4 = new ArrayList<>();
		ArrayList<String> seqSepCodonesM5 = new ArrayList<>();
		ArrayList<String> seqSepCodonesM6 = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			ArrayList<String> seqRCSepCodones = new ArrayList<>();
			for (int j = i; j < seqRC.length(); j = j + 3) {
				if (j + 2 < seqRC.length()) {
					StringBuilder codon = new StringBuilder();
					codon.append(seqRC.charAt(j));
					codon.append(seqRC.charAt(j + 1));
					codon.append(seqRC.charAt(j + 2));
					seqRCSepCodones.add(codon.toString());
				}
			}
			if (i == 0) {
				seqSepCodonesM4 = seqRCSepCodones;
			} else if (i == 1) {
				seqSepCodonesM5 = seqRCSepCodones;
			} else if (i == 2) {
				seqSepCodonesM6 = seqRCSepCodones;
			}
		}
		marcosLectura.add(seqSepCodonesM4);
		marcosLectura.add(seqSepCodonesM5);
		marcosLectura.add(seqSepCodonesM6);

		return marcosLectura;
	}

	public static ArrayList<ArrayList<String>> fragmentos_Marcos(ArrayList<ArrayList<String>> marcosLectura) {
		ArrayList<ArrayList<String>> fragmentosMarcos = new ArrayList<>();
		for (int i = 0; i < marcosLectura.size(); i++) {
			ArrayList<String> fragmentos = new ArrayList<>();
			StringBuilder fragmento = new StringBuilder();
			for (int j = 0; j < marcosLectura.get(i).size(); j++) {
				if (marcosLectura.get(i).get(j).equals("TAA")) {
					fragmento.append(marcosLectura.get(i).get(j));
					fragmentos.add(fragmento.toString());
					fragmento = new StringBuilder();
				} else if (marcosLectura.get(i).get(j).equals("TAG")) {
					fragmento.append(marcosLectura.get(i).get(j));
					fragmentos.add(fragmento.toString());
					fragmento = new StringBuilder();
				} else if (marcosLectura.get(i).get(j).equals("TGA")) {
					fragmento.append(marcosLectura.get(i).get(j));
					fragmentos.add(fragmento.toString());
					fragmento = new StringBuilder();
				} else {
					fragmento.append(marcosLectura.get(i).get(j));
				}
			}
			fragmentosMarcos.add(fragmentos);
		}
		return fragmentosMarcos;
	}

	public static ArrayList<ArrayList<Integer>> tamFragmentos_Marcos(ArrayList<ArrayList<String>> marcosLectura) {
		ArrayList<ArrayList<Integer>> tamFragmentosMarcos = new ArrayList<>();
		for (int j = 0; j < 6; j++) {
			int cCodones = 0;
			ArrayList<Integer> tamanosFragmentos = new ArrayList<>();
			for (int i = 0; i < marcosLectura.get(j).size(); i++) {
				if (marcosLectura.get(j).get(i).equals("TAA")) {
					cCodones++;
					tamanosFragmentos.add(cCodones);
					cCodones = 0;
				} else if (marcosLectura.get(j).get(i).equals("TAG")) {
					cCodones++;
					tamanosFragmentos.add(cCodones);
					cCodones = 0;
				} else if (marcosLectura.get(j).get(i).equals("TGA")) {
					cCodones++;
					tamanosFragmentos.add(cCodones);
					cCodones = 0;
				} else {
					cCodones++;
				}
			}
			tamFragmentosMarcos.add(tamanosFragmentos);
		}
		return tamFragmentosMarcos;
	}

	public static String histograma(ArrayList<ArrayList<Integer>> tamFragmentosMarcos,
			ArrayList<ArrayList<String>> fragmentosMarcos) {
		int sizeTotal = fragmentosMarcos.get(0).size() + fragmentosMarcos.get(1).size() + fragmentosMarcos.get(2).size()
				+ fragmentosMarcos.get(3).size() + fragmentosMarcos.get(4).size() + fragmentosMarcos.get(5).size();
		ArrayList<Integer> sizes = new ArrayList<>();
		sizes.add(fragmentosMarcos.get(0).size());
		sizes.add(fragmentosMarcos.get(1).size());
		sizes.add(fragmentosMarcos.get(2).size());
		sizes.add(fragmentosMarcos.get(3).size());
		sizes.add(fragmentosMarcos.get(4).size());
		sizes.add(fragmentosMarcos.get(5).size());
		String[] ID = new String[sizeTotal];
		int c = 0;
		for (int i = 1; i <= 6; i++) {
			for (int j = 1; j <= sizes.get(i - 1); j++) {
				ID[c] = "M" + i + "-F" + j;
				c++;
			}
		}
		ArrayList<ArrayList<Integer>> conjuntoTamanos = new ArrayList<>();
		conjuntoTamanos.add(tamFragmentosMarcos.get(0));
		conjuntoTamanos.add(tamFragmentosMarcos.get(1));
		conjuntoTamanos.add(tamFragmentosMarcos.get(2));
		conjuntoTamanos.add(tamFragmentosMarcos.get(3));
		conjuntoTamanos.add(tamFragmentosMarcos.get(4));
		conjuntoTamanos.add(tamFragmentosMarcos.get(5));
		String salida = "Fragmento\tLongitud\tHistograma";
		c = 0;
		for (int i = 0; i < conjuntoTamanos.size(); i++) {
			for (int j = 0; j < sizes.get(i); j++) {
				salida += "\n" + ID[c] + "\t\t" + conjuntoTamanos.get(i).get(j) + "\t\t";
				c++;
				for (int a = 0; a < conjuntoTamanos.get(i).get(j); a++) {
					salida += "*";
				}
			}
		}
		return salida;
	}

	public static void main(String[] args) throws FileNotFoundException {

		String file = args[0];

		StringBuilder seq = leerFichero(file);
		System.out.println("Secuencia molde 5'->3':\n" + seq + "\n");

		StringBuilder seqR = new StringBuilder();
		seqR = seq.reverse();

		StringBuilder seqRC = secuenciaRC(seqR);
		System.out.println("Secuencia complementaria 5'->3':\n" + seqRC + "\n");

		seq.reverse();

		ArrayList<ArrayList<String>> marcosLectura = marcosDeLectura(seq, seqRC);

		ArrayList<ArrayList<String>> fragmentosMarcos = fragmentos_Marcos(marcosLectura);		
		ArrayList<ArrayList<Integer>> tamFragmentosMarcos = tamFragmentos_Marcos(marcosLectura);

		System.out.println("\nFragmentos del primer marco de lectura:\n" + fragmentosMarcos.get(0) + "\n");
		System.out.println(
				"Numero de codones por fragmento del primer marco de lectura:\n" + tamFragmentosMarcos.get(0) + "\n");
		System.out.println("\nFragmentos del segundo marco de lectura:\n" + fragmentosMarcos.get(1) + "\n");
		System.out.println(
				"Numero de codones por fragmento del segundo marco de lectura:\n" + tamFragmentosMarcos.get(1) + "\n");
		System.out.println("\nFragmentos del tercer marco de lectura:\n" + fragmentosMarcos.get(2) + "\n");
		System.out.println(
				"Numero de codones por fragmento del tercer marco de lectura:\n" + tamFragmentosMarcos.get(2) + "\n");
		System.out.println("\nFragmentos del cuarto marco de lectura:\n" + fragmentosMarcos.get(3) + "\n");
		System.out.println(
				"Numero de codones por fragmento del cuarto marco de lectura:\n" + tamFragmentosMarcos.get(3) + "\n");
		System.out.println("\nFragmentos del quinto marco de lectura:\n" + fragmentosMarcos.get(4) + "\n");
		System.out.println(
				"Numero de codones por fragmento del quinto marco de lectura:\n" + tamFragmentosMarcos.get(4) + "\n");
		System.out.println("\nFragmentos del sexto marco de lectura:\n" + fragmentosMarcos.get(5) + "\n");
		System.out.println(
				"Numero de codones por fragmento del sexto marco de lectura:\n" + tamFragmentosMarcos.get(5) + "\n");

		String salida = histograma(tamFragmentosMarcos, fragmentosMarcos);
		System.out.println(salida);

	}

}
