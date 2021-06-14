package TyMA2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ComparacionSecuencias {

	public static ArrayList<ArrayList<String>> leerFicheros(String file1, String file2) throws FileNotFoundException {
		ArrayList<String> files = new ArrayList<>();
		files.add(file1);
		files.add(file2);
		ArrayList<ArrayList<String>> sequences_and_names = new ArrayList<>();
		ArrayList<String> sequences = new ArrayList<>();
		ArrayList<String> namesSequences = new ArrayList<>();

		for (int i = 0; i < files.size(); i++) {
			Scanner sc = new Scanner(new File(files.get(i)));
			StringBuilder seq = new StringBuilder();
			StringBuilder nameSeq = new StringBuilder();
			int cn = 0;
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (!line.equals("")) {
					if (line.charAt(0) != '>') {
						seq.append(line.toUpperCase());
					} else {
						for (int j = 0; j < line.length(); j++) {
							if (line.charAt(j) == ' ' && cn == 0) {
								j++;
								while (line.charAt(j) != ',') {
									nameSeq.append(line.charAt(j));
									j++;
								}
								cn++;
							}
						}
						namesSequences.add(nameSeq.toString());
					}
				}
			}
			sequences.add(seq.toString());
		}
		sequences_and_names.add(sequences);
		sequences_and_names.add(namesSequences);
		return sequences_and_names;
	}
	
	public static ArrayList<String> getPosiblesKmers(int k) {
		ArrayList<String> posiblesKmers = new ArrayList<>();
		char[] bases = { 'A', 'C', 'G', 'T' };
		int randomNum = 0;
		String randomKmer = new String();
		int cont = 0;
		int comb = (int) Math.pow(bases.length, k);
		while (cont < comb) {
			for (int j = 0; j < k; j++) {
				randomNum = ThreadLocalRandom.current().nextInt(0, 4);
				randomKmer += bases[randomNum];
			}
			if (!posiblesKmers.contains(randomKmer)) {
				posiblesKmers.add(randomKmer);
				randomKmer = new String();
				cont++;
			} else {
				randomKmer = new String();
			}
		}
		Collections.sort(posiblesKmers);
		return posiblesKmers;
	}
	
	public static ArrayList<ArrayList<ArrayList<String>>> getColeccionKmers(ArrayList<String> sequences, int k,
			ArrayList<String> posiblesKmers) {
		ArrayList<ArrayList<ArrayList<String>>> coleccionKmers = new ArrayList<>();
		for (int n = 0; n < sequences.size(); n++) {
			ArrayList<ArrayList<String>> colKmers = new ArrayList<>();
			for (int i = 0; i < posiblesKmers.size(); i++) {
				ArrayList<String> colKmer = new ArrayList<>();
				int c = 0;
				colKmer.add(posiblesKmers.get(i));
				for (int j = 0; j < (sequences.get(n).length() - k + 1); j++) {
					String kmer = sequences.get(n).substring(j, k + j);
					if (posiblesKmers.get(i).equals(kmer)) {
						colKmer.add("pos" + (j + 1));
						c++;
					}
				}
				colKmer.add("Numero total de repeticiones: " + c);
				colKmers.add(colKmer);
			}
			coleccionKmers.add(colKmers);
		}
		return coleccionKmers;
	}
	
	public static ArrayList<ArrayList<ArrayList<String>>> getColeccionHits(ArrayList<String> posiblesKmers,
			ArrayList<ArrayList<ArrayList<String>>> coleccionKmers) {
		ArrayList<ArrayList<ArrayList<String>>> coleccionHits = new ArrayList<>();
		for (int i = 0; i < posiblesKmers.size(); i++) {
			ArrayList<ArrayList<String>> hits = new ArrayList<>();
			for (int j = 1; j < (coleccionKmers.get(0).get(i).size() - 1); j++) {
				for (int r = 1; r < (coleccionKmers.get(1).get(i).size() - 1); r++) {
					ArrayList<String> hit = new ArrayList<>();
					hit.add(posiblesKmers.get(i));
					hit.add(coleccionKmers.get(0).get(i).get(j));
					hit.add(coleccionKmers.get(1).get(i).get(r));
					hits.add(hit);
				}
			}
			if (!hits.isEmpty()) {
				coleccionHits.add(hits);
			}
		}
		return coleccionHits;
	}
	
	public static ArrayList<ArrayList<String>> getColeccionHitsOrdenados(ArrayList<String> sequences, int k,
			ArrayList<ArrayList<ArrayList<String>>> coleccionHits) {
		ArrayList<ArrayList<String>> colHits = new ArrayList<>();
		for (int i = 0; i < coleccionHits.size(); i++) {
			for (int j = 0; j < coleccionHits.get(i).size(); j++) {
				colHits.add(coleccionHits.get(i).get(j));
			}
		}
		ArrayList<ArrayList<String>> coleccionHitsOrdenados = new ArrayList<>();
		for (int x = 1; x <= (sequences.get(0).length() - k + 1); x++) {
			String pos = "pos" + x;
			for (int i = 0; i < colHits.size(); i++) {
				if (pos.equals(colHits.get(i).get(1))) {
					coleccionHitsOrdenados.add(colHits.get(i));
				}
			}
		}
		return coleccionHitsOrdenados;
	}
	
	public static ArrayList<ArrayList<String>> getConjuntoFragmentosConPeso(int k, ArrayList<ArrayList<String>> coleccionHitsOrdenados) {
		ArrayList<String> conjuntoFragmentos = new ArrayList<>();
		ArrayList<Integer> conjuntoContadorDiferencias = new ArrayList<>();
		ArrayList<String> diagonales = new ArrayList<>();
		for (int i = 0; i < coleccionHitsOrdenados.size(); i++) {
			diagonales.add(coleccionHitsOrdenados.get(i).get(1) + "-" + coleccionHitsOrdenados.get(i).get(2));
			String fragmento = coleccionHitsOrdenados.get(i).get(0);
			int cDif = 0;
			int charFinalx1 = coleccionHitsOrdenados.get(i).get(1).length();
			int charFinaly1 = coleccionHitsOrdenados.get(i).get(2).length();
			int x1 = Integer.parseInt(coleccionHitsOrdenados.get(i).get(1).substring(3, charFinalx1));
			int y1 = Integer.parseInt(coleccionHitsOrdenados.get(i).get(2).substring(3, charFinaly1));
			for (int j = i + 1; j < coleccionHitsOrdenados.size(); j++) {
				int charFinalx2 = coleccionHitsOrdenados.get(j).get(1).length();
				int charFinaly2 = coleccionHitsOrdenados.get(j).get(2).length();
				int x2 = Integer.parseInt(coleccionHitsOrdenados.get(j).get(1).substring(3, charFinalx2));
				int y2 = Integer.parseInt(coleccionHitsOrdenados.get(j).get(2).substring(3, charFinaly2));
				int difx = x2 - x1;
				int dify = y2 - y1;
				if (difx == k && difx == dify) {
					fragmento += coleccionHitsOrdenados.get(j).get(0);
					x1 += k;
					y1 = y2;
				} else if (difx > k && difx == dify) {
					for (int r = 0; r < (int) (difx / k); r++) {
						fragmento += "-";
						cDif++;
					}
					fragmento += coleccionHitsOrdenados.get(j).get(0);
					x1 += k + difx;
					y1 = y2;
				}
			}
			conjuntoFragmentos.add(fragmento);
			conjuntoContadorDiferencias.add(cDif);
		}

		ArrayList<ArrayList<String>> conjuntoFragmentosConPeso = new ArrayList<>();
		for (int i = 0; i < conjuntoFragmentos.size(); i++) {
			ArrayList<String> fragmentoConPeso = new ArrayList<>();
			int diferencias = conjuntoContadorDiferencias.get(i);
			int coincidencias = conjuntoFragmentos.get(i).length() - diferencias;
			int peso = coincidencias - diferencias;
			fragmentoConPeso
					.add("Fragmento de la diagonal de la " + diagonales.get(i) + ": " + conjuntoFragmentos.get(i));
			fragmentoConPeso.add("Coincidencias: " + coincidencias);
			fragmentoConPeso.add("Difirencias: " + diferencias);
			fragmentoConPeso.add("Peso: " + peso);
			conjuntoFragmentosConPeso.add(fragmentoConPeso);
		}
		return conjuntoFragmentosConPeso;
	}

	public static void main(String[] args) throws FileNotFoundException {

		String file1 = args[0];
		String file2 = args[1];
		
		ArrayList<ArrayList<String>> sequences_and_names = leerFicheros(file1, file2);
		ArrayList<String> sequences = sequences_and_names.get(0);
		ArrayList<String> namesSequences = sequences_and_names.get(1);		
		for (int i = 0; i < sequences.size(); i++) {
			System.out.println("Cadena correspondiente a " + namesSequences.get(i) + ":\n" + sequences.get(i) + "\n");
		}

		String kArg = args[2];
		int k = Integer.parseInt(kArg);

		ArrayList<String> posiblesKmers = getPosiblesKmers(k);

		ArrayList<ArrayList<ArrayList<String>>> coleccionKmers = getColeccionKmers(sequences, k, posiblesKmers);
		for (int n = 0; n < coleccionKmers.size(); n++) {
			System.out
					.println("Coleccion de Kmers para k = " + k + " de la cadena de " + namesSequences.get(n) + ":\n");
			for (int i = 0; i < coleccionKmers.get(n).size(); i++) {
				if (coleccionKmers.get(n).get(i).size() > 2) {
					System.out.println(coleccionKmers.get(n).get(i) + "\n");
				}
			}
		}

		ArrayList<ArrayList<ArrayList<String>>> coleccionHits = getColeccionHits(posiblesKmers, coleccionKmers);
		System.out.println("Coleccion de hits entre las cadenas de " + namesSequences.get(0) + " y de "
				+ namesSequences.get(1) + " ([kmer, posX, posY]):\n");
		for (int i = 0; i < coleccionHits.size(); i++) {
			System.out.println(coleccionHits.get(i) + "\n");
		}

		ArrayList<ArrayList<String>> coleccionHitsOrdenados = getColeccionHitsOrdenados(sequences, k, coleccionHits);
		System.out.println(
				"A continuacion, se muestra la colección de los hits obtenidos " + "ordenados en funcion de X:\n");
		System.out.println(coleccionHitsOrdenados + "\n");

		ArrayList<ArrayList<String>> conjuntoFragmentosConPeso = getConjuntoFragmentosConPeso(k, coleccionHitsOrdenados);

		System.out.println("Reporte del conjunto de fragmentos obtenidos por diagonal:\n");
		for (int i = 0; i < conjuntoFragmentosConPeso.size(); i++) {
			System.out.println(conjuntoFragmentosConPeso.get(i));
		}
	}

}
