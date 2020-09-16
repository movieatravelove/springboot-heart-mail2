package com.example.mail.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

@SuppressWarnings("deprecation")
public class HttpClientUtil {
	/**
	 * Get请求
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url)
			throws Exception {
		String body = null;
		// Get请求
		HttpGet httpget = new HttpGet(url);
		 
		httpget.setURI(new URI(url));
		// 发送请求
		CloseableHttpClient hc = getHttpClient();
		HttpResponse httpresponse = hc.execute(httpget);
		// 获取返回数据
		HttpEntity entity = httpresponse.getEntity();
		body = EntityUtils.toString(entity);
		if (entity != null) {
			entity.consumeContent();
		}
		hc.close();
		return body;
	}

	/**
	 * Get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String get(String url, List<NameValuePair> params)
			throws Exception {
		String body = null;
		// Get请求
		HttpGet httpget = new HttpGet(url);
		// 设置参数
		String str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
		httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
		// 发送请求
		CloseableHttpClient hc = getHttpClient();
		HttpResponse httpresponse = hc.execute(httpget);
		// 获取返回数据
		HttpEntity entity = httpresponse.getEntity();
		body = EntityUtils.toString(entity);
		if (entity != null) {
			entity.consumeContent();
		}
		hc.close();
		return body;
	}

	/**
	 * Get请求
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static String get(String url, List<NameValuePair> params, String header)
			throws Exception {
		String body = null;
		// Get请求
		HttpGet httpget = new HttpGet(url);
		// 设置参数
		String str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
		httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
		// 设置header
		httpget.setHeader("platformId",header);
		// 发送请求
		CloseableHttpClient hc = getHttpClient();
		HttpResponse httpresponse = hc.execute(httpget);
		// 获取返回数据
		HttpEntity entity = httpresponse.getEntity();
		body = EntityUtils.toString(entity);
		if (entity != null) {
			entity.consumeContent();
		}
		hc.close();
		return body;
	}

	/**
	 * // Post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, List<NameValuePair> params)
			throws Exception {
		String body = null;
		// Post请求
		HttpPost httppost = new HttpPost(url);
		UrlEncodedFormEntity req = new UrlEncodedFormEntity(params, Consts.UTF_8);
		httppost.setEntity(req);
		// 发送请求
		CloseableHttpClient hc = getHttpClient();
		HttpResponse httpresponse = hc.execute(httppost);
		// 获取返回数据
		HttpEntity entity = httpresponse.getEntity();
		body = EntityUtils.toString(entity,"UTF-8");
		if (entity != null) {
			entity.consumeContent();
		}
		hc.close();
		return body;
	}

	/**
	 * // Post请求
	 *
	 */
	public static String post(String url,String data)
			throws Exception {
		String body = "";
		// Post请求
		HttpPost httppost = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(data, Charset.forName("UTF-8"));
		stringEntity.setContentType("application/x-www-form-urlencoded");
		httppost.setEntity(stringEntity);
		// 发送请求
		CloseableHttpClient hc = getHttpClient();
		HttpResponse httpresponse = hc.execute(httppost);
		HttpEntity entity = httpresponse.getEntity();
		body = EntityUtils.toString(entity,"UTF-8");
		if (entity != null) {
			entity.consumeContent();
		}
		hc.close();
		return body;
	}

	public static void main(String[] args) {
		try {
			String str= post("http://221.231.139.161:8083/Oauth/Token","grant_type=authorization_code&code=2oBU!IAAAADzualY73WD9_185W0cp1w-mgt8MaoaHGGDMYJJ8fphC8QAAAAGkvS-Ui4cq-1-9iu_CYzpBjR_hpth5ff0NX3jez_aGzCXaOiU6OwFHmQdvEL570U1NPWa9c-mdipNMhhrxMvgK5JoHoL184izO0-4UKdKqybMa3DZ99FsuXonRIIVl4Lg50J6-0mzKktLwQ7IX54ZLauM8wpy2WaTvJnoltmGAKaC6E_XFbW3La-PnJKbj7rCzci-ocS_v-XSjZEHAoSNtizHcNXgdwW2qmzzJUlttNt9yxobHGlCoIiHwugn87_kWTkKPQiPJgecelXciyzuoA_3Vo_B4TW4cWny8CH0hBd0UEws67XhjVNJ9UjovSwA&client_id=c47fbb5336cc197524f4adfa0ed2c63b&client_secret=78656157568b2533723fde6fbaeed665&redirect_uri=http%3A%2F%2F127.0.0.1%3A8080%2FrequestCode");
			System.out.println("reuslt:"+str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static synchronized CloseableHttpClient getHttpClient() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(3000);// 连接池最大并发连接数
		cm.setDefaultMaxPerRoute(3000);// 单路由最大并发数
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(cm).build();
		return httpClient;

	}
}
