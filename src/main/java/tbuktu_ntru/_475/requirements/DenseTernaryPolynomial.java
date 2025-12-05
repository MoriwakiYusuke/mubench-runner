/**
 * Copyright (c) 2011, Tim Buktu
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package tbuktu_ntru._475.requirements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * A <code>TernaryPolynomial</code> with a "high" number of nonzero coefficients.<br/>
 * Coefficients are represented as an array of length <code>N</code> containing
 * ones, negative ones, and zeros.
 */
public class DenseTernaryPolynomial extends IntegerPolynomial implements TernaryPolynomial {
    
    /**
     * Constructs a <code>DenseTernaryPolynomial</code> from a <code>IntegerPolynomial</code>. The two polynomials are
     * independent of each other.
     * @param intPoly the original polynomial
     */
    public DenseTernaryPolynomial(IntegerPolynomial intPoly) {
        this(intPoly.coeffs);
    }
    
    /**
     * Constructs a new <code>DenseTernaryPolynomial</code> with a given set of coefficients.
     * @param coeffs the coefficients
     */
    public DenseTernaryPolynomial(int[] coeffs) {
        super(coeffs);
    }
    
    /**
     * Generates a random polynomial with <code>numOnes</code> coefficients equal to 1,
     * <code>numNegOnes</code> coefficients equal to -1, and the rest equal to 0.
     * @param N number of coefficients
     * @param numOnes number of 1's
     * @param numNegOnes number of -1's
     * @param rng the random number generator to use
     */
    public static DenseTernaryPolynomial generateRandom(int N, int numOnes, int numNegOnes, Random rng) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<numOnes; i++)
            list.add(1);
        for (int i=0; i<numNegOnes; i++)
            list.add(-1);
        while (list.size() < N)
            list.add(0);
        Collections.shuffle(list, rng);
        
        int[] arr = new int[N];
        for (int i=0; i<N; i++)
            arr[i] = list.get(i);
        return new DenseTernaryPolynomial(arr);
    }
    
    @Override
    public IntegerPolynomial mult(IntegerPolynomial poly2, int modulus) {
        // even on 32-bit systems, LongPolynomial5 multiplies faster than IntegerPolynomial
        if (modulus == 2048) {
            IntegerPolynomial poly2Pos = poly2.clone();
            poly2Pos.modPositive(2048);
            LongPolynomial5 poly5 = new LongPolynomial5(poly2Pos);
            return poly5.mult(this).toIntegerPolynomial();
        }
        else
            return super.mult(poly2, modulus);
    }

    @Override
    public int[] getOnes() {
        int N = coeffs.length;
        int[] ones = new int[N];
        int onesIdx = 0;
        for (int i=0; i<N; i++) {
            int c = coeffs[i];
            if (c == 1)
                ones[onesIdx++] = i;
        }
        return Arrays.copyOf(ones, onesIdx);
    }
    
    @Override
    public int[] getNegOnes() {
        int N = coeffs.length;
        int[] negOnes = new int[N];
        int negOnesIdx = 0;
        for (int i=0; i<N; i++) {
            int c = coeffs[i];
            if (c == -1)
                negOnes[negOnesIdx++] = i;
        }
        return Arrays.copyOf(negOnes, negOnesIdx);
    }

    @Override
    public int size() {
        return coeffs.length;
    }
}