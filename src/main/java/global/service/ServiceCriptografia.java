package global.service;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.security.InvalidKeyException;

public class ServiceCriptografia {

    private final static Logger logger = LoggerFactory.getLogger(ServiceCriptografia.class.getName());

    private char[] key;
    private int[] sbox;
    private static final int SBOX_LENGTH = 256;
    private static final int TAM_MIN_CHAVE = 5;

    public ServiceCriptografia(String key) throws InvalidKeyException {
        setKey(key);
    }

    public ServiceCriptografia() {

    }

    public byte[] decriptografa(final byte[] msg) {
        return criptografa(msg);
    }

    public byte[] criptografa(final byte[] msg) {
        sbox = initSBox(key);
        byte[] code = new byte[msg.length];
        int i = 0;
        int j = 0;
        for (int n = 0; n < msg.length; n++) {
            i = (i + 1) % SBOX_LENGTH;
            j = (j + sbox[i]) % SBOX_LENGTH;
            swap(i, j, sbox);
            int rand = sbox[(sbox[i] + sbox[j]) % SBOX_LENGTH];
            code[n] = (byte) (rand ^ (int) msg[n]);
        }
        return code;
    }

    private int[] initSBox(char[] key) {
        int[] sbox = new int[SBOX_LENGTH];
        int j = 0;

        for (int i = 0; i < SBOX_LENGTH; i++) {
            sbox[i] = i;
        }

        for (int i = 0; i < SBOX_LENGTH; i++) {
            j = (j + sbox[i] + key[i % key.length]) % SBOX_LENGTH;
            swap(i, j, sbox);
        }
        return sbox;
    }

    private void swap(int i, int j, int[] sbox) {
        int temp = sbox[i];
        sbox[i] = sbox[j];
        sbox[j] = temp;
    }

    public void setKey(String key) throws InvalidKeyException {
        if (!(key.length() >= TAM_MIN_CHAVE && key.length() < SBOX_LENGTH)) {
            throw new InvalidKeyException("Tamanho da chave deve ser entre "
                    + TAM_MIN_CHAVE + " e " + (SBOX_LENGTH - 1));
        }

        this.key = key.toCharArray();
    }

}
