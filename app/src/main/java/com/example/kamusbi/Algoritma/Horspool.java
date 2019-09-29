package com.example.kamusbi.Algoritma;

public class Horspool {

    public static int[] computeLastOcc(String P) {
        int[] lastOcc = new int[128];

        for (int i = 0; i < 128; i++) {
            lastOcc[i] = -1;
        }

        for (int i = 0; i < 128; i++) {
            lastOcc[P.charAt(i)] = i;
        }
        return lastOcc;
    }

    public static int horspool(String T, String P) {
        int i0, j, m, n;
        int[] lastOcc;

        n = T.length();
        m = P.length();

        lastOcc = computeLastOcc(P);
        System.out.println("Pattern found at position : " + lastOcc);

        i0 = 0;

        while (i0 < (n - m)){
            j = m - 1;

            System.out.println("+++++++++++++++++++++++++++++++++++++");

            while (P.charAt(j) == T.charAt(i0 + j)){
                j--;

                System.out.println("Pattern found at position : "+ j);

                if (j < 0)
                    return (i0);
            }

            i0 = i0 + (m - 1) - lastOcc[T.charAt(i0 + (m - 1))];
        }
        return -1; //no match
    }
}
