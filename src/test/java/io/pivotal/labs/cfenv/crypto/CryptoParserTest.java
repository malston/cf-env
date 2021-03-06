package io.pivotal.labs.cfenv.crypto;

import org.junit.Test;

import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import java.math.BigInteger;
import java.security.Key;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CryptoParserTest {

    public static final BigInteger P_256_ORDER = new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369");

    @Test
    public void shouldParseAnX509Certificate() throws Exception {
        String certificateString = "" +
                "-----BEGIN CERTIFICATE-----\n" +
                "MIIBxTCCAW+gAwIBAgIJAP6oSMcKL5/RMA0GCSqGSIb3DQEBBQUAMCMxITAfBgNV\n" +
                "BAsTGElBbUFDZXJ0aWZpY2F0ZUF1dGhvcml0eTAeFw0xNjA2MTYxNTA2MzhaFw0x\n" +
                "NjA3MTYxNTA2MzhaMCMxITAfBgNVBAsTGElBbUFDZXJ0aWZpY2F0ZUF1dGhvcml0\n" +
                "eTBcMA0GCSqGSIb3DQEBAQUAA0sAMEgCQQCzDE9WLL7gvS8jKqoEDfg0fo11eV1K\n" +
                "76aoN7+LakBBJlLEMAgO9G1/dYscb9BsSH3lvKrHP3VqOdNxXkyoEeSzAgMBAAGj\n" +
                "gYUwgYIwHQYDVR0OBBYEFOpbFIPCq891mF/ara3TbvBFJQ+WMFMGA1UdIwRMMEqA\n" +
                "FOpbFIPCq891mF/ara3TbvBFJQ+WoSekJTAjMSEwHwYDVQQLExhJQW1BQ2VydGlm\n" +
                "aWNhdGVBdXRob3JpdHmCCQD+qEjHCi+f0TAMBgNVHRMEBTADAQH/MA0GCSqGSIb3\n" +
                "DQEBBQUAA0EArVBMPE+epXTh0DnI9cgkf4+qLcgHAS2k8gC3/cfSc3gCG1v15DbN\n" +
                "xr0CoWh17P9Vb3X1CkOLMhyHYpD3tOpKgw==\n" +
                "-----END CERTIFICATE-----";

        Certificate certificate = CryptoParser.parseCertificate(certificateString);

        assertThat(((X509Certificate) certificate).getSubjectDN().getName(), equalTo("OU=IAmACertificateAuthority"));
    }

    @Test
    public void shouldParseAPKCS8RSAPrivateKey() throws Exception {
        String keyString = "" +
                "-----BEGIN PRIVATE KEY-----\n" +
                "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA5qUWM0y4rQQWZdr1\n" +
                "D4bxiq1jZH1NSuYn21LApnDnt+Q4/CQ4/OIdh/hZljgFKq1oJg4ToeDubCtSLyR3\n" +
                "L89qDwIDAQABAkEAo6ekFwZrS6jI08EHfdr8bLAXBGi8fVbOFRukwvT+FkYqyc7G\n" +
                "0ja/6hQaO+kIA3ToVlKpyn/9mYTt/R/bXofMgQIhAP6nu78Qcn/Uy3qz/H5iifcf\n" +
                "ZRorAh4DqmjKZ5NGEDqhAiEA59zk2ExZUcpc4n65xVFHBFCT2ystB9NdzXicvNA9\n" +
                "lq8CIADL64Vser81njFTEM4gZsgUHA/Z5JbNciIDyBEo3fIhAiEA0ZITNn4r7YWf\n" +
                "Vwl8GCFSs0+xlNP9q6kci++MnA0M3fkCIHsxjW/AkmNozt8AsfIpBAGQ9teGLDPE\n" +
                "8WVZgWlRIjvA\n" +
                "-----END PRIVATE KEY-----";

        Key key = CryptoParser.parseKey(keyString);

        assertThat(((RSAPrivateKey) key).getPrivateExponent(), hasToString(startsWith("8571299855")));
        assertThat(((RSAPrivateKey) key).getModulus(), hasToString(startsWith("1207985201")));
    }

    @Test
    public void shouldParseAPKCS8ECPrivateKey() throws Exception {
        String keyString = "" +
                "-----BEGIN PRIVATE KEY-----\n" +
                "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgcPpehQM6yoYp8/ZX\n" +
                "ouOlHTYO0WR+SneBWnBB07XgzKGhRANCAAT5PnuxmD5FygLB3x6sO/AEdmtCPKNA\n" +
                "BR867n9Hx2eVFxhxrb8g0ZAXNsYyrLKxNZV3YQVS816O5MN996XKA6bD\n" +
                "-----END PRIVATE KEY-----";

        Key key = CryptoParser.parseKey(keyString);

        assertThat(((ECPrivateKey) key).getS(), hasToString(startsWith("5110140315")));
        assertThat(((ECPrivateKey) key).getParams().getOrder(), equalTo(P_256_ORDER));
    }

    @Test
    public void shouldParseAPKCS8DSAPrivateKey() throws Exception {
        String keyString = "" +
                "-----BEGIN PRIVATE KEY-----\n" +
                "MIHHAgEAMIGpBgcqhkjOOAQBMIGdAkEA/8/aIwYwD4TUzee5AQvz4Bk24nAozkCJ\n" +
                "OOK/WEtLmlfdK3pWeZ7WttD65kJFgFZE1hDi0D0ipuXwFIJhqzoMcQIVAORLzKnx\n" +
                "1wfBs3Mngrh3XfyqOmUlAkEAvjDa+zB5mfAfIaYOgpuJzEGnLnj9VGLZEGVC/w3l\n" +
                "5ML3PblMCLMniHzIT3UQjQtTwOfiWa7RdAFrmjU7OQxJCQQWAhQETyaxnsWJO293\n" +
                "DZPkGsuQaq2xdw==\n" +
                "-----END PRIVATE KEY-----";

        Key key = CryptoParser.parseKey(keyString);

        assertThat(((DSAPrivateKey) key).getX(), hasToString(startsWith("2460109266")));
    }

    @Test
    public void shouldParseAPKCS8DiffieHellmanPrivateKey() throws Exception {
        String keyString = "" +
                "-----BEGIN PRIVATE KEY-----\n" +
                "MIGcAgEAMFMGCSqGSIb3DQEDATBGAkEAshUWtJRfsLnR5aFCGNa22yN2hxI5g95M\n" +
                "gd9ef3/JxE3m6bDawc5elyPGmKTdNSJbLFlXvlWOccZoMCNMDnrHYwIBAgRCAkBz\n" +
                "dbu4cSepe0ZleXxM0EwuVO9TJcYPM3s3aZL7NQl5xbLA0FIJrKI3ectYvxklhdS7\n" +
                "weJkKpkB0lUG2Rvmmt7q\n" +
                "-----END PRIVATE KEY-----";

        Key key = CryptoParser.parseKey(keyString);

        assertThat(((DHPrivateKey) key).getX(), hasToString(startsWith("6047125407")));
    }

    @Test
    public void shouldParseAnX509RSAPublicKey() throws Exception {
        String keyString = "" +
                "-----BEGIN PUBLIC KEY-----\n" +
                "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOalFjNMuK0EFmXa9Q+G8YqtY2R9TUrm\n" +
                "J9tSwKZw57fkOPwkOPziHYf4WZY4BSqtaCYOE6Hg7mwrUi8kdy/Pag8CAwEAAQ==\n" +
                "-----END PUBLIC KEY-----";

        Key key = CryptoParser.parseKey(keyString);

        assertThat(((RSAPublicKey) key).getPublicExponent(), hasToString(startsWith("65537")));
    }

    @Test
    public void shouldParseAnX509ECPublicKey() throws Exception {
        String keyString = "" +
                "-----BEGIN PUBLIC KEY-----\n" +
                "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE+T57sZg+RcoCwd8erDvwBHZrQjyj\n" +
                "QAUfOu5/R8dnlRcYca2/INGQFzbGMqyysTWVd2EFUvNejuTDffelygOmww==\n" +
                "-----END PUBLIC KEY-----";

        Key key = CryptoParser.parseKey(keyString);

        assertThat(((ECPublicKey) key).getW().getAffineX(), hasToString(startsWith("1127362975")));
    }

    @Test
    public void shouldParseAnX509DSAPublicKey() throws Exception {
        String keyString = "" +
                "-----BEGIN PUBLIC KEY-----\n" +
                "MIHyMIGpBgcqhkjOOAQBMIGdAkEA/8/aIwYwD4TUzee5AQvz4Bk24nAozkCJOOK/\n" +
                "WEtLmlfdK3pWeZ7WttD65kJFgFZE1hDi0D0ipuXwFIJhqzoMcQIVAORLzKnx1wfB\n" +
                "s3Mngrh3XfyqOmUlAkEAvjDa+zB5mfAfIaYOgpuJzEGnLnj9VGLZEGVC/w3l5ML3\n" +
                "PblMCLMniHzIT3UQjQtTwOfiWa7RdAFrmjU7OQxJCQNEAAJBALhjbXYy4uG3yMV+\n" +
                "h/Sd6SgxqgDr17n1dk2QH2r/4sMppgtMgCLNvb/3kuvK8novAEaHDEojWUkwtsSr\n" +
                "sgXFLac=\n" +
                "-----END PUBLIC KEY-----";

        Key key = CryptoParser.parseKey(keyString);

        assertThat(((DSAPublicKey) key).getY(), hasToString(startsWith("9657203532")));
    }

    @Test
    public void shouldParseAnX509DiffieHellmanPublicKey() throws Exception {
        String keyString = "" +
                "-----BEGIN PUBLIC KEY-----\n" +
                "MIGbMFMGCSqGSIb3DQEDATBGAkEAshUWtJRfsLnR5aFCGNa22yN2hxI5g95Mgd9e\n" +
                "f3/JxE3m6bDawc5elyPGmKTdNSJbLFlXvlWOccZoMCNMDnrHYwIBAgNEAAJBAIkh\n" +
                "EFFcS4ISXXpl4ZpUX+pt59EWMMc0gR+icpUiMyl4+KCC04aTcSUS4KcD8quH9/9a\n" +
                "TpZe/fw4hiJTgE71Aqs=\n" +
                "-----END PUBLIC KEY-----";

        Key key = CryptoParser.parseKey(keyString);

        assertThat(((DHPublicKey) key).getY(), hasToString(startsWith("7182036621")));
    }

    @Test
    public void shouldParseAPKCS1RSAPrivateKey() throws Exception {
        String keyString = "" +
                "-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIIBOwIBAAJBAOalFjNMuK0EFmXa9Q+G8YqtY2R9TUrmJ9tSwKZw57fkOPwkOPzi\n" +
                "HYf4WZY4BSqtaCYOE6Hg7mwrUi8kdy/Pag8CAwEAAQJBAKOnpBcGa0uoyNPBB33a\n" +
                "/GywFwRovH1WzhUbpML0/hZGKsnOxtI2v+oUGjvpCAN06FZSqcp//ZmE7f0f216H\n" +
                "zIECIQD+p7u/EHJ/1Mt6s/x+Yon3H2UaKwIeA6poymeTRhA6oQIhAOfc5NhMWVHK\n" +
                "XOJ+ucVRRwRQk9srLQfTXc14nLzQPZavAiAAy+uFbHq/NZ4xUxDOIGbIFBwP2eSW\n" +
                "zXIiA8gRKN3yIQIhANGSEzZ+K+2Fn1cJfBghUrNPsZTT/aupHIvvjJwNDN35AiB7\n" +
                "MY1vwJJjaM7fALHyKQQBkPbXhiwzxPFlWYFpUSI7wA==\n" +
                "-----END RSA PRIVATE KEY-----";

        Key key = CryptoParser.parseKey(keyString);

        assertThat(((RSAPrivateKey) key).getPrivateExponent(), hasToString(startsWith("8571299855")));
        assertThat(((RSAPrivateKey) key).getModulus(), hasToString(startsWith("1207985201")));
    }

    @Test
    public void shouldParseAPKCS1ECPrivateKey() throws Exception {
        String keyString = "" +
                "-----BEGIN EC PRIVATE KEY-----\n" +
                "MHcCAQEEIHD6XoUDOsqGKfP2V6LjpR02DtFkfkp3gVpwQdO14MyhoAoGCCqGSM49\n" +
                "AwEHoUQDQgAE+T57sZg+RcoCwd8erDvwBHZrQjyjQAUfOu5/R8dnlRcYca2/INGQ\n" +
                "FzbGMqyysTWVd2EFUvNejuTDffelygOmww==\n" +
                "-----END EC PRIVATE KEY-----";

        Key key = CryptoParser.parseKey(keyString);

        assertThat(((ECPrivateKey) key).getS(), hasToString(startsWith("5110140315")));
        assertThat(((ECPrivateKey) key).getParams().getOrder(), equalTo(P_256_ORDER));
    }

    @Test
    public void shouldParseAPKCS1DSAPrivateKey() throws Exception {
        String keyString = "" +
                "-----BEGIN DSA PRIVATE KEY-----\n" +
                "MIH5AgEAAkEA/8/aIwYwD4TUzee5AQvz4Bk24nAozkCJOOK/WEtLmlfdK3pWeZ7W\n" +
                "ttD65kJFgFZE1hDi0D0ipuXwFIJhqzoMcQIVAORLzKnx1wfBs3Mngrh3XfyqOmUl\n" +
                "AkEAvjDa+zB5mfAfIaYOgpuJzEGnLnj9VGLZEGVC/w3l5ML3PblMCLMniHzIT3UQ\n" +
                "jQtTwOfiWa7RdAFrmjU7OQxJCQJBALhjbXYy4uG3yMV+h/Sd6SgxqgDr17n1dk2Q\n" +
                "H2r/4sMppgtMgCLNvb/3kuvK8novAEaHDEojWUkwtsSrsgXFLacCFARPJrGexYk7\n" +
                "b3cNk+Qay5BqrbF3\n" +
                "-----END DSA PRIVATE KEY-----";

        Key key = CryptoParser.parseKey(keyString);

        assertThat(((DSAPrivateKey) key).getX(), hasToString(startsWith("2460109266")));
    }

    @Test
    public void shouldParseAPKCS1RSAPublicKey() throws Exception {
        String keyString = "" +
                "-----BEGIN RSA PUBLIC KEY-----\n" +
                "MEgCQQDmpRYzTLitBBZl2vUPhvGKrWNkfU1K5ifbUsCmcOe35Dj8JDj84h2H+FmW\n" +
                "OAUqrWgmDhOh4O5sK1IvJHcvz2oPAgMBAAE=\n" +
                "-----END RSA PUBLIC KEY-----";

        Key key = CryptoParser.parseKey(keyString);

        assertThat(((RSAPublicKey) key).getPublicExponent(), hasToString(startsWith("65537")));
    }
}
