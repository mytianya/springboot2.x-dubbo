package jjwt;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/***
 * Header{
 *     alg:xx
 *     typ:jwt
 * }
 * payload{
 *     aa:aa,
 *     bb:bb
 * }
 * signature alg(base64(header).base64(payload).secretkey)
 * token=base64(header).base64(payload).signature
 */
public class Test {
    public static void main(String args[]) {

    }

    private static final String MAC_INSTANCE_NAME = "HMacSHA256";

    public static String Hmacsha256(String secret, String message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmac_sha256 = Mac.getInstance(MAC_INSTANCE_NAME);
        SecretKeySpec key = new SecretKeySpec(secret.getBytes(), MAC_INSTANCE_NAME);
        hmac_sha256.init(key);
        byte[] buff = hmac_sha256.doFinal(message.getBytes());
        return Base64.encodeBase64URLSafeString(buff);
    }

    public static String testJWT() throws InvalidKeyException, NoSuchAlgorithmException {
        String secret = "eerp";
        String header = "{\"type\":\"JWT\",\"alg\":\"HS256\"}";
        String claim = "{\"iss\":\"cnooc\",\"admin\":true}";
        String base64Header = Base64.encodeBase64URLSafeString(header.getBytes());
        String base64Claim = Base64.encodeBase64URLSafeString(claim.getBytes());
        String signature = Hmacsha256(secret, base64Header + "." + base64Claim);
        String jwt = base64Header + "." + base64Claim + "." + signature;
        return jwt;
    }

}
