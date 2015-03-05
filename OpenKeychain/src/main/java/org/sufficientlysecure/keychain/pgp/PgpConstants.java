package org.sufficientlysecure.keychain.pgp;

import org.spongycastle.bcpg.CompressionAlgorithmTags;
import org.spongycastle.bcpg.HashAlgorithmTags;
import org.spongycastle.bcpg.SymmetricKeyAlgorithmTags;

import java.util.LinkedList;

public class PgpConstants {

    public static interface OpenKeychainSymmetricKeyAlgorithmTags extends SymmetricKeyAlgorithmTags {
        public static final int USE_PREFERRED = -1;
    }

    public static interface OpenKeychainHashAlgorithmTags extends HashAlgorithmTags {
        public static final int USE_PREFERRED = -1;
    }

    public static interface OpenKeychainCompressionAlgorithmTags extends CompressionAlgorithmTags {
        public static final int USE_PREFERRED = -1;
    }

    /*
     * Most preferred is first
     * These arrays are written as preferred algorithms into the keys on creation.
     * Other implementations may choose to honor this selection.
     *
     * These lists also define the only algorithms which are used in OpenKeychain.
     * We do not support algorithms such as MD5
     */

    public static LinkedList<Integer> sPreferredSymmetricAlgorithms = new LinkedList<>();
    public static LinkedList<Integer> sPreferredHashAlgorithms = new LinkedList<>();
    public static LinkedList<Integer> sPreferredCompressionAlgorithms = new LinkedList<>();

    static {
        sPreferredSymmetricAlgorithms.add(SymmetricKeyAlgorithmTags.AES_256);
        sPreferredSymmetricAlgorithms.add(SymmetricKeyAlgorithmTags.AES_192);
        sPreferredSymmetricAlgorithms.add(SymmetricKeyAlgorithmTags.AES_128);
        sPreferredSymmetricAlgorithms.add(SymmetricKeyAlgorithmTags.TWOFISH);

        // NOTE: some implementations do not support SHA512, thus we choose SHA256 as default (Mailvelope?)
        sPreferredHashAlgorithms.add(HashAlgorithmTags.SHA256);
        sPreferredHashAlgorithms.add(HashAlgorithmTags.SHA512);
        sPreferredHashAlgorithms.add(HashAlgorithmTags.SHA384);
        sPreferredHashAlgorithms.add(HashAlgorithmTags.SHA224);
        sPreferredHashAlgorithms.add(HashAlgorithmTags.SHA1);
        sPreferredHashAlgorithms.add(HashAlgorithmTags.RIPEMD160);

        sPreferredCompressionAlgorithms.add(CompressionAlgorithmTags.ZLIB);
        sPreferredCompressionAlgorithms.add(CompressionAlgorithmTags.BZIP2);
        sPreferredCompressionAlgorithms.add(CompressionAlgorithmTags.ZIP);
    }

    public static int[] getAsArray(LinkedList<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i); // Watch out for NullPointerExceptions!
        }
        return array;
    }

    /*
     * Note: s2kcount is a number between 0 and 0xff that controls the
     * number of times to iterate the password hash before use. More
     * iterations are useful against offline attacks, as it takes more
     * time to check each password. The actual number of iterations is
     * rather complex, and also depends on the hash function in use.
     * Refer to Section 3.7.1.3 in rfc4880.txt. Bigger numbers give
     * you more iterations.  As a rough rule of thumb, when using
     * SHA256 as the hashing function, 0x10 gives you about 64
     * iterations, 0x20 about 128, 0x30 about 256 and so on till 0xf0,
     * or about 1 million iterations. The maximum you can go to is
     * 0xff, or about 2 million iterations.
     * from http://kbsriram.com/2013/01/generating-rsa-keys-with-bouncycastle.html
     *
     * Bouncy Castle default: 0x60
     * kbsriram proposes: 0xc0
     * OpenKeychain: 0x90
     */
    public static final int SECRET_KEY_ENCRYPTOR_S2K_COUNT = 0x90;
    public static final int SECRET_KEY_ENCRYPTOR_HASH_ALGO = HashAlgorithmTags.SHA256;
    public static final int SECRET_KEY_ENCRYPTOR_SYMMETRIC_ALGO = SymmetricKeyAlgorithmTags.AES_256;
    public static final int SECRET_KEY_SIGNATURE_HASH_ALGO = HashAlgorithmTags.SHA256;
    // NOTE: only SHA1 is supported for key checksum calculations in OpenPGP,
    // see http://tools.ietf.org/html/rfc488 0#section-5.5.3
    public static final int SECRET_KEY_SIGNATURE_CHECKSUM_HASH_ALGO = HashAlgorithmTags.SHA1;

}
