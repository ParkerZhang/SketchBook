package org.example.swappayment.MQService;



import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
@Component
public class SslFactory
{
    // TODO , to setup SSL properties
    private static SSLSocketFactory _sslFactory;
    private static String _keyStorePath;
    private static String _keyStorePassword;
    private static String _keyStoreType;
    private static String _trustStoreType;
    private static String _sslProtocol;
    private static SSLContext _sslContext;

    public static SSLSocketFactory getSSLSocketFactory() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {

        if (_sslFactory != null)
            return _sslFactory;

        String keyStorePath = _keyStorePath;
        String trustStorePath = keyStorePath;
        String password = _keyStorePassword;

        KeyStore keyStore = KeyStore.getInstance(_keyStoreType);
        FileInputStream keyStoreInput = new FileInputStream(keyStorePath);
        try {
            keyStore.load(keyStoreInput, password.toCharArray());
        } finally {
            keyStoreInput.close();
        }
        KeyStore trustStore = KeyStore.getInstance(_trustStoreType);
        FileInputStream trustStoreInput = new FileInputStream(trustStorePath);
        try {
            trustStore.load(trustStoreInput, null);
        } finally {
            trustStoreInput.close();
        }
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyManagerFactory keyManagerFactory =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        keyManagerFactory.init(keyStore, password.toCharArray());
        SSLContext sslContext = SSLContext.getInstance(_sslProtocol);
        sslContext.init(
                keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(),
                null);
        _sslFactory = sslContext.getSocketFactory();
        return _sslFactory;
    }

    public static SSLContext getSSLContext() throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        if (_sslContext != null)
            return _sslContext;
        getSSLSocketFactory();
        return _sslContext;
    }
}
