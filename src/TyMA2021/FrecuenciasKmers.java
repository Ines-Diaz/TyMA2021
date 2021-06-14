package TyMA2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class FrecuenciasKmers {

	public static ArrayList<ArrayList<String>> leerFichero(String file) throws FileNotFoundException {
		ArrayList<ArrayList<String>> sequences_and_names = new ArrayList<>();
		ArrayList<String> sequences = new ArrayList<>();
		ArrayList<String> namesSequences = new ArrayList<>();
		StringBuilder seq = new StringBuilder();
		StringBuilder nameSeq = new StringBuilder();
		Scanner sc = new Scanner(new File(file));
		int c = 0;
		int cn = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine().trim();
			if (!line.equals("")) {
				if (line.charAt(0) != '>') {
					seq.append(line.toUpperCase());
					c = 1;
				} else if (line.charAt(0) == '>' && c == 0) {
					for (int i = 0; i < line.length(); i++) {
						if (line.charAt(i) == ' ' && cn == 0) {
							i++;
							while (line.charAt(i) != ',') {
								nameSeq.append(line.charAt(i));
								i++;
							}
							cn++;
						}
					}
					namesSequences.add(nameSeq.toString());
					nameSeq = new StringBuilder();
					cn = 0;
				} else if (line.charAt(0) == '>' && c > 0) {
					for (int i = 0; i < line.length(); i++) {
						if (line.charAt(i) == ' ' && cn == 0) {
							i++;
							while (line.charAt(i) != ',') {
								nameSeq.append(line.charAt(i));
								i++;
							}
							cn++;
						}
					}
					sequences.add(seq.toString());
					seq = new StringBuilder();
					namesSequences.add(nameSeq.toString());
					nameSeq = new StringBuilder();
					cn = 0;
				}
			}
		}
		sequences.add(seq.toString());
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

	public static ArrayList<ArrayList<Integer>> getFrecAbs(ArrayList<ArrayList<String>> sequencesKmers,
			ArrayList<String> posiblesKmers) {
		ArrayList<String> sequen = new ArrayList<>();
		ArrayList<ArrayList<Integer>> totalKmersFrecAbs = new ArrayList<>();
		for (int i = 0; i < sequencesKmers.size(); i++) {
			ArrayList<Integer> kmersFrecAbs = new ArrayList<>();
			sequen = sequencesKmers.get(i);
			for (int j = 0; j < sequen.size(); j++) {
				if (!posiblesKmers.contains(sequen.get(j))) {
					posiblesKmers.add(sequen.get(j));
				}
			}
			for (int j = 0; j < posiblesKmers.size(); j++) {
				int cK = 0;
				for (int l = 0; l < sequen.size(); l++) {
					if (posiblesKmers.get(j).equals(sequen.get(l))) {
						cK++;
					}
				}
				kmersFrecAbs.add(cK);
			}
			totalKmersFrecAbs.add(kmersFrecAbs);
		}
		return totalKmersFrecAbs;
	}

	public static ArrayList<ArrayList<String>> getFrecRel(ArrayList<ArrayList<String>> sequencesKmers,
			ArrayList<Integer> nSequencesKmers, ArrayList<String> posiblesKmers) {
		ArrayList<String> sequen = new ArrayList<>();
		int totalKmers = 0;
		ArrayList<ArrayList<String>> totalKmersFrecRel = new ArrayList<>();
		for (int i = 0; i < sequencesKmers.size(); i++) {
			ArrayList<String> kmersFrecRel = new ArrayList<>();
			sequen = sequencesKmers.get(i);
			totalKmers = nSequencesKmers.get(i);
			for (int j = 0; j < sequen.size(); j++) {
				if (!posiblesKmers.contains(sequen.get(j))) {
					posiblesKmers.add(sequen.get(j));
				}
			}
			for (int j = 0; j < posiblesKmers.size(); j++) {
				int cK = 0;
				for (int l = 0; l < sequen.size(); l++) {
					if (posiblesKmers.get(j).equals(sequen.get(l))) {
						cK++;
					}
				}
				kmersFrecRel.add(cK + "/" + totalKmers);
			}
			totalKmersFrecRel.add(kmersFrecRel);
		}
		return totalKmersFrecRel;
	}

	public static ArrayList<ArrayList<String>> getSequencesKmers(ArrayList<String> sequences, int k) {
		ArrayList<ArrayList<String>> sequencesKmers = new ArrayList<>();
		String sequence = "";
		String kmer = "";
		for (int i = 0; i < sequences.size(); i++) {
			ArrayList<String> kmers = new ArrayList<>();
			sequence = sequences.get(i);
			for (int j = 0; j <= sequence.length() - k; j++) {
				for (int l = 0; l < k; l++) {
					kmer += sequence.charAt(j + l);
				}
				kmers.add(kmer);
				kmer = "";
			}
			sequencesKmers.add(kmers);
		}
		return sequencesKmers;
	}

	public static ArrayList<Integer> getNumSequencesKmers(ArrayList<String> sequences, int k) {
		ArrayList<Integer> nSequencesKmers = new ArrayList<>();
		String sequence = "";
		for (int i = 0; i < sequences.size(); i++) {
			int cKmers = 0;
			sequence = sequences.get(i);
			for (int j = 0; j <= sequence.length() - k; j++) {
				cKmers++;
			}
			nSequencesKmers.add(cKmers);
		}
		return nSequencesKmers;
	}

	public static ArrayList<ArrayList<String>> get_Kmers_FA_FR(ArrayList<String> sequences, int k) {

		ArrayList<ArrayList<String>> sequencesKmers = getSequencesKmers(sequences, k);
		ArrayList<Integer> nSequencesKmers = getNumSequencesKmers(sequences, k);

		ArrayList<String> posiblesKmers = getPosiblesKmers(k);

		ArrayList<ArrayList<Integer>> totalKmersFrecAbs = getFrecAbs(sequencesKmers, posiblesKmers);
		ArrayList<ArrayList<String>> totalKmersFrecRel = getFrecRel(sequencesKmers, nSequencesKmers, posiblesKmers);

		ArrayList<ArrayList<String>> conjunto = new ArrayList<>();
		for (int i = 1; i <= sequencesKmers.size(); i++) {
			ArrayList<String> conj = new ArrayList<>();
			for (int j = 0; j < posiblesKmers.size(); j++) {
				conj.add("(Kmer: " + posiblesKmers.get(j) + ", Frec. Abs.: " + totalKmersFrecAbs.get(i - 1).get(j)
						+ ", Frec. Rel.: " + totalKmersFrecRel.get(i - 1).get(j) + ")");
			}
			conjunto.add(conj);
		}
		return conjunto;
	}

	public static void metodoParaDinucleotidos(ArrayList<String> sequences, ArrayList<String> namesSequences, int k) {
		ArrayList<ArrayList<String>> sequencesKmers = getSequencesKmers(sequences, k);
		ArrayList<Integer> nSequencesKmers = getNumSequencesKmers(sequences, k);

		ArrayList<String> posiblesKmers = getPosiblesKmers(k);

		ArrayList<ArrayList<Integer>> totalKmersFrecAbs = getFrecAbs(sequencesKmers, posiblesKmers);
		ArrayList<ArrayList<String>> totalKmersFrecRel = getFrecRel(sequencesKmers, nSequencesKmers, posiblesKmers);

		String salida1 = "";
		String cad1 = "Cadena";
		int cCad1 = cad1.length();
		for (int i = 0; i < 20; i++) {
			while (cCad1 != 0) {
				salida1 += cad1.charAt(i);
				i++;
				cCad1--;
			}
			salida1 += ' ';
		}
		for (int i = 0; i < posiblesKmers.size(); i++) {
			salida1 += "\t" + posiblesKmers.get(i);
		}
		for (int i = 0; i < namesSequences.size(); i++) {
			salida1 += "\n";
			int cName = 17;
			for (int j = 0; j < 20; j++) {
				while (cName != 0) {
					salida1 += namesSequences.get(i).charAt(j);
					j++;
					cName--;
				}
				salida1 += '.';
			}
			for (int j = 0; j < totalKmersFrecAbs.get(i).size(); j++) {
				salida1 += "\t" + totalKmersFrecAbs.get(i).get(j);
			}
		}
		System.out.println("Tabla para Frecuencia Absoluta de Kmers con k = " + k + ":\n" + salida1 + "\n");

		String salida2a = "";
		String cad2a = "Cadena";
		int cCad2a = cad2a.length();
		for (int i = 0; i < 20; i++) {
			while (cCad2a != 0) {
				salida2a += cad2a.charAt(i);
				i++;
				cCad2a--;
			}
			salida2a += ' ';
		}
		for (int i = 0; i < (posiblesKmers.size() / 2); i++) {
			salida2a += "\t" + posiblesKmers.get(i) + "\t";
		}
		for (int i = 0; i < namesSequences.size(); i++) {
			salida2a += "\n";
			int cName = 17;
			for (int j = 0; j < 20; j++) {
				while (cName != 0) {
					salida2a += namesSequences.get(i).charAt(j);
					j++;
					cName--;
				}
				salida2a += '.';
			}
			for (int j = 0; j < (posiblesKmers.size() / 2); j++) {
				if (totalKmersFrecRel.get(i).get(j).length() > 7) {
					salida2a += "\t" + totalKmersFrecRel.get(i).get(j);
				} else {
					salida2a += "\t" + totalKmersFrecRel.get(i).get(j) + "\t";
				}
			}
		}
		String salida2b = "";
		String cad2b = "Cadena";
		int cCad2b = cad2b.length();
		for (int i = 0; i < 20; i++) {
			while (cCad2b != 0) {
				salida2b += cad2b.charAt(i);
				i++;
				cCad2b--;
			}
			salida2b += ' ';
		}
		for (int i = (posiblesKmers.size() / 2); i < posiblesKmers.size(); i++) {
			salida2b += "\t" + posiblesKmers.get(i) + "\t";
		}
		for (int i = 0; i < namesSequences.size(); i++) {
			salida2b += "\n";
			int cName = 17;
			for (int j = 0; j < 20; j++) {
				while (cName != 0) {
					salida2b += namesSequences.get(i).charAt(j);
					j++;
					cName--;
				}
				salida2b += '.';
			}
			for (int j = (posiblesKmers.size() / 2); j < posiblesKmers.size(); j++) {
				if (totalKmersFrecRel.get(i).get(j).length() > 7) {
					salida2b += "\t" + totalKmersFrecRel.get(i).get(j);
				} else {
					salida2b += "\t" + totalKmersFrecRel.get(i).get(j) + "\t";
				}
			}
		}
		String extra = "";
		for (int i = 0; i < 150; i++) {
			extra += '-';
		}
		System.out.println("Tabla para Frecuencia Relativa de Kmers con k = " + k + ":\n" + salida2a + "\n" + extra
				+ "\n" + salida2b + "\n");

		for (int i = 0; i < namesSequences.size(); i++) {
			String salida = "Kmers\tFrec. Abs.\tHistograma";
			for (int j = 0; j < totalKmersFrecAbs.get(i).size(); j++) {
				salida += "\n" + posiblesKmers.get(j) + "\t" + totalKmersFrecAbs.get(i).get(j) + "\t\t";
				for (int a = 0; a < totalKmersFrecAbs.get(i).get(j); a++) {
					salida += "*";
				}
			}
			System.out.println("Histograma para la cadena de " + namesSequences.get(i) + ":\n" + salida + "\n");
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		String file = args[0];

		ArrayList<ArrayList<String>> sequences_and_names = leerFichero(file);
		ArrayList<String> sequences = sequences_and_names.get(0);
		ArrayList<String> namesSequences = sequences_and_names.get(1);
		for (int i = 0; i < sequences.size(); i++) {
			System.out.println("Cadena correspondiente a " + namesSequences.get(i) + ":\n" + sequences.get(i) + "\n");
		}

		String kArg = args[1];
		int k = 0;
		int k0 = 0;
		int k1 = 0;
		if (kArg.contains("-")) {
			String[] kS = kArg.split("-");
			k0 = Integer.parseInt(kS[0]);
			k1 = Integer.parseInt(kS[1]);
		} else {
			k = Integer.parseInt(kArg);
		}

		if (k != 0) {
			ArrayList<ArrayList<String>> conjunto = get_Kmers_FA_FR(sequences, k);
			for (int i = 0; i < sequences.size(); i++) {
				System.out.println("Conjunto de los kmers con sus respectivas frecuencias absoluta y relativa "
						+ "de la cadena correspondiente a" + namesSequences.get(i) + " para k = " + k + ":\n"
						+ conjunto.get(i) + "\n");
			}

			if (k == 2) {
				metodoParaDinucleotidos(sequences, namesSequences, k);
			}

		} else {
			for (int kp = k0; kp <= k1; kp++) {
				ArrayList<ArrayList<String>> conjunto = get_Kmers_FA_FR(sequences, kp);
				for (int i = 0; i <= sequences.size(); i++) {
					System.out.println("Conjunto de los kmers con sus respectivas frecuencias absoluta y relativa "
							+ "de la cadena correspondiente a" + namesSequences.get(i) + " para k = " + kp + ":\n"
							+ conjunto.get(i) + "\n");
				}

			}
		}

	}

}
