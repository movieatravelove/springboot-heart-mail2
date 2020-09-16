package com.example.mail.utils;

import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.net.HttpURLConnection;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 兼容调Https接口
 * @Author mazq
 * @Date 2020/06/04 17:16
 * @Param
 * @return
 */
public class HttpsClientRequestFactory extends SimpleClientHttpRequestFactory {

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) {
        try {
            if (!(connection instanceof HttpsURLConnection)) {// http协议
                //throw new RuntimeException("An instance of HttpsURLConnection is expected");
                super.prepareConnection(connection, httpMethod);
            }
            if (connection instanceof HttpsURLConnection) {// https协议，修改协议版本
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                // 信任任何链接
                TrustStrategy anyTrustStrategy = new TrustStrategy() {
                    @Override
                    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        return true;
                    }
                };
                SSLContext ctx = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();
                ((HttpsURLConnection) connection).setSSLSocketFactory(ctx.getSocketFactory());
                HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
                super.prepareConnection(httpsConnection, httpMethod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

