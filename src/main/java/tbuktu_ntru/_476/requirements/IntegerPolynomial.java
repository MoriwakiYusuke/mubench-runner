/**
 * Minimal IntegerPolynomial for SignaturePublicKey test (Case 476)
 */
package tbuktu_ntru._476.requirements;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static tbuktu_ntru._476.requirements.ArrayEncoder.decodeModQ;
import static tbuktu_ntru._476.requirements.ArrayEncoder.encodeModQ;

/**
 * Minimal IntegerPolynomial that supports only the operations needed by SignaturePublicKey:
 * - fromBinary(InputStream, N, q)
 * - toBinary(q)
 */
public class IntegerPolynomial {
    public int[] coeffs;
    
    public IntegerPolynomial(int N) {
        coeffs = new int[N];
    }
    
    public IntegerPolynomial(int[] coeffs) {
        this.coeffs = coeffs;
    }
    
    /**
     * Decodes a byte array to a polynomial with <code>N</code> ternary coefficients
     * @param is an input stream containing an encoded polynomial
     * @param N number of coefficients
     * @param q modulus
     * @return the decoded polynomial
     * @throws IOException
     */
    public static IntegerPolynomial fromBinary(InputStream is, int N, int q) throws IOException {
        return new IntegerPolynomial(decodeModQ(is, N, q));
    }
    
    /**
     * Encodes a polynomial with coefficients between 0 and q-1 to a byte array
     * @param q modulus
     * @return the encoded polynomial
     */
    public byte[] toBinary(int q) {
        return encodeModQ(coeffs, q);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(coeffs);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IntegerPolynomial other = (IntegerPolynomial) obj;
        return Arrays.equals(coeffs, other.coeffs);
    }
}
