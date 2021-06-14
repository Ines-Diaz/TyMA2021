package TyMA2021;

import java.io.FileNotFoundException;

public class PruebaApps {

	public static void main(String[] args) throws FileNotFoundException {		
		
		System.out.println("Ejercicio 1:\n");
		String[] argF = new String[1];
		argF[0] = "fastas/CAVIA-HEMO.fasta";
		Fragmentos.main(argF);
		System.out.println("\n\n\n");
		
		System.out.println("Ejercicio 2:\n");
		String[] argS = new String[1];
		argS[0] = "fastas/COVID.fasta";
		SecuenciasRyRC.main(argS);
		System.out.println("\n\n\n");
		
		System.out.println("Ejercicio 3:\n");
		String[] argFK = new String[2];
		argFK[0] = "fastas/multiFasta.fasta";
		argFK[1] = String.valueOf(2);
		FrecuenciasKmers.main(argFK);
		System.out.println("\n\n\n");
		
		System.out.println("Ejercicio 4:\n");
		String[] argCS = new String[3];
		argCS[0] = "fastas/Cadena1.fasta";
		argCS[1] = "fastas/Cadena2.fasta";
		argCS[2] = String.valueOf(2);
		ComparacionSecuencias.main(argCS);
	}
}
