package util;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Cifrado y verificación de contraseñas con PBKDF2 (HMAC-SHA256).
 *
 * <p>Nunca se guarda la contraseña real: se almacena el texto
 * "iteraciones:sal:hash", donde la sal es aleatoria para cada usuario.</p>
 *
 * @author Alexander Gaviria Cossío
 */
public final class Clave {

    /** Número de iteraciones del algoritmo (a más iteraciones, más seguro). */
    private static final int ITERACIONES = 120000;

    /** Longitud del hash resultante, en bits. */
    private static final int BITS = 256;

    /** Clase utilitaria: no debe instanciarse. */
    private Clave() {
    }

    /**
     * Genera el valor que se guarda en la base de datos para una contraseña.
     *
     * @param clave contraseña en texto plano
     * @return texto con formato "iteraciones:sal:hash" (Base64)
     */
    public static String hash(String clave) {
        byte[] sal = new byte[16];
        new SecureRandom().nextBytes(sal);
        byte[] resumen = derivar(clave, sal, ITERACIONES);
        return ITERACIONES + ":" + aBase64(sal) + ":" + aBase64(resumen);
    }

    /**
     * Comprueba si una contraseña coincide con el valor guardado.
     *
     * @param clave    contraseña escrita por el usuario
     * @param guardado valor almacenado con el formato "iteraciones:sal:hash"
     * @return true si coincide; false si no coincide o el formato es inválido
     */
    public static boolean verificar(String clave, String guardado) {
        try {
            String[] partes = guardado.split(":");
            int iteraciones = Integer.parseInt(partes[0]);
            byte[] sal = Base64.getDecoder().decode(partes[1]);
            byte[] esperado = Base64.getDecoder().decode(partes[2]);
            // Comparación en tiempo constante para evitar ataques por tiempo
            return MessageDigest.isEqual(esperado, derivar(clave, sal, iteraciones));
        } catch (RuntimeException e) {
            return false;
        }
    }

    /** Aplica PBKDF2 a la contraseña con la sal y las iteraciones indicadas. */
    private static byte[] derivar(String clave, byte[] sal, int iteraciones) {
        try {
            PBEKeySpec especificacion = new PBEKeySpec(clave.toCharArray(), sal, iteraciones, BITS);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(especificacion).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(e);
        }
    }

    /** Convierte bytes a texto Base64. */
    private static String aBase64(byte[] datos) {
        return Base64.getEncoder().encodeToString(datos);
    }
}
