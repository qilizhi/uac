package com.mlx.uac.utils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mlx.uac.dict.ConstEC;
import com.mlx.uac.exception.BusiException;

/**
 * httClient工具类
 * @author chenfh 2016年4月11日 上午10:37:34
 */
public class HttpClientUtil {

	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

	private static int CONNECTIONREQUESTTIMEOUT = 10 * 1000; // 秒
	private static int CONNECTTIMEOUT = 10 * 1000; // 秒
	private static int SOCKETTIMEOUT = 5 * 1000; // 秒

	public static CloseableHttpClient createSSLClientDefault(boolean isHttps) {
		try {
			if (isHttps) {
				SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					// 信任所有
					public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						return true;
					}
				}).build();
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
				return HttpClients.custom().setSSLSocketFactory(sslsf).build();
			}
		} catch (Exception e) {
			log.error("创建HttpClient失败", e);
		}
		return HttpClients.createDefault();
	}

	public static String httpGet(String url, Map<String, Object> requestMap) throws Exception {
		return httpGet(url, false, requestMap);
	}

	public static String httpGet(String url, boolean isHttps, Map<String, Object> requestMap) throws Exception {

		StringBuffer sBuffer = new StringBuffer("");

		if (null != requestMap && requestMap.size() > 0) {
			for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
				sBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			sBuffer.deleteCharAt(sBuffer.lastIndexOf("&"));
		}

		log.info("request entity body：[{}]", sBuffer.toString());
		url += "?" + sBuffer.toString();
		HttpRequestBase requestBase = new HttpGet(url);
		return sendRequest(requestBase, isHttps);
	}

	public static String httpPost(String url, Map<String, Object> requestMap) throws Exception {
		return httpPost(url, false, requestMap);
	}

	public static String httpPost(String url, boolean isHttps, Map<String, Object> requestMap) throws Exception {

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (null != requestMap && requestMap.size() > 0) {
			for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
		}

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, ConstEC.ENCODE_UTF8);

		HttpRequestBase requestBase = new HttpPost(url);
		((HttpPost) requestBase).setEntity(entity);

		log.info("request entity body：[{}]", EntityUtils.toString(entity));
		return sendRequest(requestBase, isHttps);
	}

	private static String sendRequest(HttpRequestBase requestBase, boolean isHttps) throws Exception {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(CONNECTIONREQUESTTIMEOUT)
					.setConnectTimeout(CONNECTTIMEOUT).setSocketTimeout(SOCKETTIMEOUT).build();
			requestBase.setConfig(config);
			log.info("request line: {}", requestBase.getRequestLine());
			httpClient = HttpClientUtil.createSSLClientDefault(isHttps);

			response = httpClient.execute(requestBase);

			int statusCode = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK != statusCode) {
				throw new BusiException("9990", "连接失败，返回状态[" + statusCode + "]");
			}
			String result = EntityUtils.toString(response.getEntity(), ConstEC.ENCODE_UTF8);

			return result;
		} catch (IOException e) {
			log.error("调用外部IO异常", e);
			throw new BusiException("9001", "IO异常");
		} catch (Exception e) {
			log.error("交易失败!", e);
			throw new BusiException("9991", "交易失败");
		} finally {
			if (null != response) {
				response.close();
				response = null;
			}
			if (null != httpClient) {
				httpClient.close();
				httpClient = null;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aa", "value");
		String result = HttpClientUtil.httpPost("http://wjapi.mlxing.com/guide/v1/homepage/list", true, map);
		System.out.println(result);
	}
}
