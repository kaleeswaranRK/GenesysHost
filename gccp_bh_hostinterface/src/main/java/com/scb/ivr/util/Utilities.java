package com.scb.ivr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.RSAPublicKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
//import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.scb.ivr.exception.CustomException;
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.log.custom.CustomLogger;
import com.scb.ivr.model.uaas.GenerateAPIN_Req;
import com.scb.ivr.model.uaas.GenerateCCPIN_Req;
import com.scb.ivr.model.uaas.ValidateAPIN_Req;
import com.scb.ivr.model.uaas.ValidateCCPIN_Req;
import com.scb.ivr.model.uaas.res.generateotp.RandomChallengeVO;

@Component

public class Utilities {

	@Value("${propertyfilepath}")
	private String propertyfilepath;

//	@Autowired
//	ConfigController configController;

	//// Generate Tracking ID
	public String generateTrackingId() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmssMs");
		String trackingId = "IVR-" + dateFormat.format(new Date()) + "-" + UUID.randomUUID().toString(); // "IVR-19062020013055600-01a8671b-3918-4cc3-8ead-eb30c9728721";
		return trackingId;
	}

	//// Get Current Class Name and Method Name
	public String getCurrentClassAndMethodName() {
		final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		final String s = e.getClassName();
		return s.substring(s.lastIndexOf('.') + 1, s.length()) + "." + e.getMethodName();
	}

	//// Null or empty field check
	public boolean isNullOrEmpty(String inputValue) {
		if (inputValue == null || "".equals(inputValue.trim())) {
			return true;
		} else {
			return false;
		}
	}

	//// Get and load all properties from file ( config, operations, parametric,
	//// payload )
	/*public Properties getServiceConfig(Properties properties) throws Exception {

		Properties serviceProperties = new Properties();
		serviceProperties.putAll(properties);

		String sessionId = properties.getProperty("sessionId");
		String serviceName = properties.getProperty("serviceName");
		String tpSystem = properties.getProperty("tpSystem");

		Logger sessionLogger = CustomLogger.getLogger(sessionId);

		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Config));
		
		
		//// LOAD DUMMY FLAG PARAMETERS
		try {
			if (properties != null && properties.getProperty(serviceName) != null) {

				String configPropertyStr = properties.getProperty(serviceName);
				String serviceConfigValues[] = configPropertyStr.split(",");

				if (serviceConfigValues.length >= 4) {
					serviceProperties.setProperty("dummyFlag", serviceConfigValues[0]);
					serviceProperties.setProperty("operationName", serviceConfigValues[1]);
					serviceProperties.setProperty("serviceId", serviceConfigValues[2]);
					serviceProperties.setProperty("serviceType", serviceConfigValues[3]);
				} else {
					serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
					sessionLogger.debug(getCurrentClassAndMethodName()
							+ " Config property value is not match with the expected format. Property is: "
							+ configPropertyStr);
				}
			} else {
				sessionLogger.debug(getCurrentClassAndMethodName() + " - " + properties.getProperty(serviceName)
						+ " is not found in config.properties.");
				serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
			}
		} catch (Exception e) {
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
			sessionLogger.debug(getCurrentClassAndMethodName()
					+ " Config.properties -> Exception occured while processing the config propery:" + serviceName
					+ ". " + e.getMessage());
		}

		properties = new Properties();
		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Operations));
		
		
		//// LOAD ENDPOINT PARAMETER
		try {
			if (properties != null && properties.getProperty("endPoint." + serviceName) != null) {
				String endPointString = properties.getProperty("endPoint." + serviceName);

				String endPoints[] = endPointString.split(";");
				if (endPoints.length >= 2 && !isNullOrEmpty(endPoints[0])) {
					serviceProperties.setProperty("endPoint", endPoints[0].trim());
					serviceProperties.setProperty("timeOut", endPoints[1] != null ? endPoints[1].trim() : "20000");
				} else {
					serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
					sessionLogger.debug(getCurrentClassAndMethodName()
							+ " Operations.properties -> Incorrect endPoint params found for the service : "
							+ serviceName);
				}
			} else {
				serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
				sessionLogger.debug(getCurrentClassAndMethodName()
						+ " Operations.properties -> No endPoint URL found for the service: " + serviceName);
			}

			if (properties != null && properties.getProperty("endPoint." + tpSystem + ".tokenUrl") != null) {
				String endPointString = properties.getProperty("endPoint." + tpSystem + ".tokenUrl");

				String endPoints[] = endPointString.split(";");
				if (endPoints.length >= 2 && !isNullOrEmpty(endPoints[0])) {
					serviceProperties.setProperty("tokenURL", endPoints[0].trim());
					serviceProperties.setProperty("tokenTimeOut", endPoints[1] != null ? endPoints[1].trim() : "20000");
				}
			}
		} catch (Exception e) {
			sessionLogger.debug(getCurrentClassAndMethodName()
					+ " Exception occured while fetching endPoint in operations.properties. Service: " + serviceName
					+ " - " + e.getMessage());
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		properties = new Properties();
		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Parametric));

		
		//// LOAD Parametric properties
		try {
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();

				if (key.contains(tpSystem + ".")) {
					serviceProperties.put(key, value);
				}

				if (!key.contains(".")) {
					serviceProperties.put(key, value);
				}

			}

		} catch (Exception e) {
			sessionLogger.debug(getCurrentClassAndMethodName()
					+ " Exception occured while loading Parametric properties." + e.getMessage());
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		//// LOAD PAYLOAD PARAMETER
		try {

			File file = new File(propertyfilepath + tpSystem + "Payload.properties");
			Properties propPayload = new Properties();
			InputStream input = new FileInputStream(file);
			propPayload.load(input);

			if (propPayload != null && propPayload.getProperty(serviceName) != null) {
				serviceProperties.setProperty("requestPayload", propPayload.getProperty(serviceName));
			} else {
				sessionLogger.debug(getCurrentClassAndMethodName() + " No request payload found in " + tpSystem
						+ "Payload.properties");
				serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
			}

		} catch (Exception e) {
			sessionLogger.debug(getCurrentClassAndMethodName() + " Exception occured while loading payload properties."
					+ e.getMessage());
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		return serviceProperties;
	}*/

	/// Get token related endpoint Parameter and credentials from file
	/*public Properties getTokenParams(String tpSystem, String serviceName) {
		Properties serviceProperties = new Properties();
		serviceProperties.setProperty("serviceName", serviceName);
		serviceProperties.setProperty("tpSystem", tpSystem);

		Properties properties = new Properties();

		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Operations));
		//// LOAD ENDPOINT TOKEN PARAMETER
		try {
			if (properties != null && properties.getProperty("endPoint." + tpSystem + ".tokenUrl") != null) {
				String endPointString = properties.getProperty("endPoint." + tpSystem + ".tokenUrl");

				String endPoints[] = endPointString.split(";");
				if (endPoints.length >= 2 && !isNullOrEmpty(endPoints[0].trim())) {
					String tokenUrl = endPoints[0].trim();
					String tokenTimeOut = endPoints[1].trim();
					serviceProperties.setProperty("tokenURL", tokenUrl);
					serviceProperties.setProperty("tokenTimeOut", endPoints[1] != null ? tokenTimeOut : "20000");
				}
			}
		} catch (Exception e) {
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		properties = new Properties();
		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Parametric));

		//// LOAD Parametric properties
		try {
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();

				if (key.contains(tpSystem + ".")) {
					serviceProperties.put(key, value);
				}

				if (!key.contains(".")) {
					serviceProperties.put(key, value);
				}

			}

		} catch (Exception e) {

			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		return serviceProperties;
	}*/

	//// Parse XML Data
	public String removeXmlNamespaceAndPreamble(String xmlString) {
		return xmlString.replaceAll("(<\\?[^<]*\\?>)?", ""). /* remove preamble */
				replaceAll("xmlns.*?(\"|\').*?(\"|\')", "") /* remove xmlns declaration */
				.replaceAll("(<)(\\w+:)(.*?>)", "$1$3") /* remove opening tag prefix */
				.replaceAll("(</)(\\w+:)(.*?>)", "$1$3"); /* remove closing tags prefix */
	}

	public String getAttribute(String xmlString, String tagName) {
		String output = xmlString;
		if (xmlString.indexOf("<" + tagName + ">") >= 0 && xmlString
				.indexOf("</" + tagName + ">") > xmlString.indexOf("<" + tagName + ">") + tagName.length()) {
			output = xmlString.substring(xmlString.indexOf("<" + tagName + ">"),
					xmlString.indexOf("</" + tagName + ">") + tagName.length() + 3);
		}
		return output;
	}

	/// Xml to Json conversion
	public String convertXMLintoJsonString(String xmlString){
		String jsonPrettyPrintString = "";
		try {
			JSONObject jsonObj = XML.toJSONObject(xmlString);
			jsonPrettyPrintString = jsonObj.toString();
			return jsonPrettyPrintString;
		}catch(Exception e) {
			return jsonPrettyPrintString;
		}
		
	}

	/// Load parameter to payload and retrieve request
	public String injectDataIntoRequestString(String requestString, Map<String, Object> inputElemets) {

		if (inputElemets != null) {
			for (Map.Entry<String, Object> entry : inputElemets.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();

				String inputElement = "[{%" + key + "%}]";
				String tempStr = requestString.replace(inputElement, value);
				requestString = tempStr;
			}
		}
		return requestString;
	}

	//// Oauth Token Generator
	public String callOAuth2Token(Properties serviceProps) throws Exception {
		String token = "";

		JSONObject obj = null;
		try {

			/***
			 * @param grant_type mandatory,not null
			 * @param client_id mandatory,not null
			 * @param client_secret mandatory,not null
			 * @param scope mandatory,not null
			 * @param token_URL mandatory,not null
			 * @param keystore mandatory,not null
			 * @param keystoreType mandatory,not null
			 * @param keystorePwd mandatory,not null
			 * @param truststore mandatory,not null
			 * @param truststoreType mandatory,not null
			 * @param truststoreType mandatory,not null
			 * 
			 * all credentials stored in parametric.properties file
			 * call token api with credential and retrieve oauth2 token
			 * 
			 ***/
		
			String tpSystem = serviceProps.getProperty("tpSystem");
			String serviceName = serviceProps.getProperty("serviceName");
			/// TOKEN CREDENTIALS
			String grantType = serviceProps.getProperty(tpSystem + ".grant_type");
			String clientId = serviceProps.getProperty(tpSystem + ".client_id");
			String clientSecret = serviceProps.getProperty(tpSystem + ".client_secret");
			String scope = serviceProps.getProperty(tpSystem + "." + serviceName + ".scope");

			String tokenUrl = serviceProps.getProperty("tokenURL");
			String tokenUrlTimeout = serviceProps.getProperty("tokenTimeOut");

			String keyStoreFile = serviceProps.getProperty(tpSystem + ".keyStore");
			String keyStoreType = serviceProps.getProperty(tpSystem + ".keyStoreType");
			String keyStorePwd = serviceProps.getProperty(tpSystem + ".keyStorePwd");

			String trustStoreFile = serviceProps.getProperty(tpSystem + ".trustStore");
			String trustStoreType = serviceProps.getProperty(tpSystem + ".trustStoreType");
			String trustStorePwd = serviceProps.getProperty(tpSystem + ".trustStorePwd");

			/// HTTP CLIENT CONFIG
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			/// SSL CONTEXT SETUP
			httpClient = (CloseableHttpClient) sslContextSetup(keyStoreFile, keyStoreType, keyStorePwd, trustStoreFile,
					trustStoreType, trustStorePwd);

			/// TIMEOUT CONFIGURATION
			RequestConfig timoutConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(Integer.parseInt(tokenUrlTimeout))
					.setConnectTimeout(Integer.parseInt(tokenUrlTimeout))
					.setSocketTimeout(Integer.parseInt(tokenUrlTimeout)).build();

			HttpPost httpPost = new HttpPost(tokenUrl);
			httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
			httpPost.setEntity(new StringEntity("grant_type=" + grantType + "&client_id=" + clientId + "&client_secret="
					+ clientSecret + "&scope=" + scope));
			httpPost.setConfig(timoutConfig);

			HttpResponse response = httpClient.execute(httpPost);

			String content = EntityUtils.toString(response.getEntity());
			obj = new JSONObject(content);

			/// GENERATED TOKEN
			if (obj != null && obj.has("access_token")) {
				token = obj.getString("access_token");

				if (token != null && !"".equalsIgnoreCase(token.trim())) {
					token = "Bearer " + token;
				}
			} else {
				throw new Exception("The token response is : " + obj.toString());
			}
			return token;
		} catch (NullPointerException e) {
			throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
					"Exception Occured : Could not load token credentials from properties or not found "
							+ e.getMessage());
		} catch (Exception e) {
			throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
					"Exception Occured : OAuth Token Creation failed - " + e.getMessage());
		}
	}

	//// SSL context setup
	public HttpClient sslContextSetup(String keyStoreFile, String keyStoreType, String keyStorePwd,
			String trustStoreFile, String trustStoreType, String trustStorePwd)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException,
			FileNotFoundException, IOException, UnrecoverableKeyException {

		KeyStore clientStore = KeyStore.getInstance(keyStoreType);
		String decodePassword = new String(Base64.getDecoder().decode(keyStorePwd.getBytes()));
		clientStore.load(new FileInputStream(new File(propertyfilepath + keyStoreFile)), decodePassword.toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(clientStore, decodePassword.toCharArray());
		KeyManager[] kms = kmf.getKeyManagers();

		TrustManager[] trustAllCerts = null;

		if (trustStoreFile != null && !"".equalsIgnoreCase(trustStoreFile.trim())) {
			KeyStore trustStore = KeyStore.getInstance(trustStoreType);
			String decodeTrustPasswd = new String(Base64.getDecoder().decode(trustStorePwd.getBytes()));
			trustStore.load(new FileInputStream(new File(propertyfilepath + trustStoreFile)),
					decodeTrustPasswd.toCharArray());

			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trustStore);
			trustAllCerts = tmf.getTrustManagers();
		} else {
			trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}
			} };
		}

		final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

		sslContext.init(kms, trustAllCerts, new SecureRandom());
		SSLContext.setDefault(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.setSSLContext(sslContext).build();
		return httpClient;
	}

	/// BAsic Token Generator
	public String callBasicAuthToken(String userName, String password) throws Exception {
		String token = "";

		try {
			String valueToEncode = userName + ":" + password;
			token = "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
		} catch (NullPointerException e) {
			throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
					"Exception Occured : Could not load token credentials from properties or not found "
							+ e.getMessage());
		} catch (Exception e) {
			throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
					"Exception Occured : Basic Token Creation failed - " + e.getMessage());
		}

		return token;
	}

	//// Trust all certificate
	/*public HttpClient certificateExclude() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
						return true;
					}
				}).build()).build();

		return httpClient;
	}*/
	
	public HttpClient certificateExclude() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		TrustManager[] trustAllCerts = null;
		
		trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

		sslContext.init(null, trustAllCerts, new SecureRandom());
		SSLContext.setDefault(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.setSSLContext(sslContext).build();
		return httpClient;
	}

	/// Calculate the time difference(in seconds) between two dates
	public long getTimeDiffBW2Date(String requestInitiatedTimestamp, String endTime) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GlobalConstants.DateTimeFormat);
		LocalDateTime from = LocalDateTime.parse(requestInitiatedTimestamp, formatter);
		LocalDateTime to = LocalDateTime.parse(endTime, formatter);
		Duration duration = Duration.between(from, to);
		long timeInSeconds = duration.getSeconds();

		return timeInSeconds;
	}

	//// Generate Transaction Sequence Number(Euronet)
	public String generateSequenceNumber() {
		String sequenceNumber = "000000000000000";
		try {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMddHHmmssSSS");
			sequenceNumber = dateTimeFormat.format(new Date());
//			Random r = new Random(System.currentTimeMillis());
//			sequenceNumber = sequenceNumber + ((1 + r.nextInt(2)) * 10 + r.nextInt(10));
			SecureRandom rand = new SecureRandom();
	        int rand_int1 = rand.nextInt(100);
	        sequenceNumber = sequenceNumber + String.format("%02d", rand_int1);
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return sequenceNumber;
	}

	/// RSA Data encryption
	public String encryptDataRSA(String exponent, String modulus, String encryptedBlock, String data,
			String sessionID) {
		Logger logger = CustomLogger.getLogger(sessionID);
		String encData = "";
		String plainString = data + "_-_" + encryptedBlock;
		PublicKey publicKey = null;
		BigInteger keyInt = new BigInteger(modulus, 16);
		BigInteger exponentInt = new BigInteger(exponent, 16);

		try {
			logger.debug("Encryption logic entered");
			RSAPublicKeySpec keySpeck = new RSAPublicKeySpec(keyInt, exponentInt);
			logger.debug("Key factory get instance");
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpeck);
			logger.debug("RSA Padding");
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] encryptedData = cipher.doFinal(plainString.getBytes("UTF-8"));
			logger.debug("Encryption completed");
			encData = new String(Base64.getEncoder().encode(encryptedData));
		} catch (Exception e) {
			logger.error("Exception Occured - " + e.getMessage());
		}

		return encData;
	}

	/// Generate Card PIN Data(PIN Generation/Reset/Validate)
	public Object generateAPINEncCardPin(GenerateAPIN_Req reqObj,RandomChallengeVO randomChallengeVo) {
		/*
		 * Card PIN information: values are delimited by_-_ entire string is encrypted
		 * with RSA public key, then Base64-encoded. Plaintext string format :
		 * field1_-field2-_ … _-_fieldN
		 * 
		 * CardPIN params are follows: cardNo = arrCardPIN[0]; oldPin = arrCardPIN[1];
		 * newPin = arrCardPIN[2]; confirmNewPin = arrCardPIN[3]; cardExpiryDate =
		 * arrCardPIN[4]; RandomChallengeForCardPIN = arrCardPIN[5];
		 */

		String cardNo = reqObj.getCardNo();
		String oldPin = reqObj.getOldPin();
		String newPin = reqObj.getNewPin();
		String confirmNewPin = reqObj.getConfirmNewPin();
		String cardExpiryDate = reqObj.getCardExpiryDate();

		String encryptFormatFiledValues = cardNo + "_-_" + oldPin + "_-_" + newPin + "_-_" + confirmNewPin + "_-_"
				+ cardExpiryDate;
//		String encPin = encryptDataRSA(reqObj.getExponent(), reqObj.getModulus(), reqObj.getEncryptedBlock(),
//				encryptFormatFiledValues, reqObj.getSessionId());
		
		System.out.println("CardPIN--->" + encryptFormatFiledValues);
		String encPin = encryptDataRSA(randomChallengeVo.getExponent(), randomChallengeVo.getModulus(), randomChallengeVo.getBase64Challenge(),
				encryptFormatFiledValues, reqObj.getSessionId());

		return encPin;

	}

	/// Generate Card Info Data(PIN Generation/Reset/validate)
	public String generateAPINEncCardInfo(GenerateAPIN_Req reqObj, RandomChallengeVO randomChallengeVo, String cardToken) {

		/*
		 * Card information: values are delimited by_-_ entire string is encrypted with
		 * RSA public key, then Base64-encoded. Plaintext string format:
		 * field1_-field2-_ … _-_fieldN credit/Debit cardNumber = arrCardInfo[0];
		 * dateOfBirth (Format: yyyyMMdd)= arrCardInfo[1]; expiryDate (Format: yyyy-MM)=
		 * arrCardInfo[2]; cvv(CVV number on the back of the card) = arrCardInfo[3];
		 * nric(Customer NRIC number) = arrCardInfo[4]; Embossed Name (Name that is
		 * embossed on the card)= arrCardInfo[5]; creditLimit (Customer credit limit
		 * (digits only))= arrCardInfo[6]; cardToken(CardToken for
		 * EURONET)=arraCradInfo[7]; Base64RandomChallenge(Obtained via Generate Random
		 * Challenge API call) = arrCardInfo[8];
		 */

		String debitcardNumber = reqObj.getCardNo();
		String dateOfBirth = reqObj != null && reqObj.getDateOfBirth() != null ? reqObj.getDateOfBirth() : "";
		String expiryDate = reqObj != null && reqObj.getExpiryDate() != null ? reqObj.getExpiryDate() : "";
		String cvv = reqObj != null && reqObj.getCvv() != null ? reqObj.getCvv() : "";
		String nric = reqObj != null && reqObj.getNric() != null ? reqObj.getNric() : "";
		String embossedName = reqObj != null && reqObj.getEmbossedName() != null ? reqObj.getEmbossedName() : "";
		String creditLimit = reqObj != null && reqObj.getCreditLimit() != null ? reqObj.getCreditLimit() : "";
		//String cardToken = reqObj != null && reqObj.getCardToken() != null ? reqObj.getCardToken() : "";

		String encryptFormatFiledValues = debitcardNumber + "_-_" + dateOfBirth + "_-_" + expiryDate + "_-_" + cvv
				+ "_-_" + nric + "_-_" + embossedName + "_-_" + creditLimit + "_-_" + cardToken;

		System.out.println("CardInfo--->" + encryptFormatFiledValues);
		
		String encInfo = encryptDataRSA(randomChallengeVo.getExponent(), randomChallengeVo.getModulus(), randomChallengeVo.getBase64Challenge(),
				encryptFormatFiledValues, reqObj.getSessionId());

		return encInfo;

	}

	/// Generate Transaction Reference Number(PIN Generation)
	public String generateTxnRefNo() {
		SimpleDateFormat dateFromat = new SimpleDateFormat("MMddHHmmss");
		String txnRefNo = dateFromat.format(new Date());

//		Random r = new Random();
//		txnRefNo = txnRefNo + String.format("%05d", r.nextInt(99999));
		
		SecureRandom rand = new SecureRandom();
        int rand_int1 = rand.nextInt(100000);
        txnRefNo = txnRefNo + String.format("%05d", rand_int1);
		return txnRefNo;
	}

	public String validateAPINEncCardPin(ValidateAPIN_Req reqObj, RandomChallengeVO randomChallengeVo) {

		// Card PIN information:
		// values are delimited by_-_
		// entire string is encrypted with RSA public key, then Base64-encoded.
		// Plaintext string format :
		// field1_-field2-_ … _-_fieldN
		//
		// SL.No FieldName Description
		// 1. CardNumber credit/Debitcard Number
		// 2. PIN CardPINNumber
		// 3. Card Expiry Date Format:yyyy-MM
		// 4. Base64 RandomChallenge Obtained via Generate Random Chellange API Call

		String cardNo = reqObj.getCardNo();
		String pin = reqObj.getPin();
		String cardExpiryDate = reqObj.getCardExpiryDate();

		String encryptFormatFiledValues = cardNo + "_-_" + pin + "_-_" + cardExpiryDate;
		//String encryptFormatFiledValues = cardNo + "_-_" + pin + "_-__-__-_" + cardExpiryDate; 
		
		//System.out.println(encryptFormatFiledValues);
		
		String encPin = encryptDataRSA(randomChallengeVo.getExponent(), randomChallengeVo.getModulus(), randomChallengeVo.getBase64Challenge(),
				encryptFormatFiledValues, reqObj.getSessionId());

		return encPin;

	}

	public String validateAPINEncCardInfo(ValidateAPIN_Req reqObj, RandomChallengeVO randomChallengeVo, String cardToken) {

		// Card information:
		// values are delimited by_-_
		// entire string is encrypted with RSA public key, then Base64-encoded.
		// Plaintext string format:
		// field1_-field2-_ … _-_fieldN
		// credit/Debit cardNumber = arrCardInfo[0];
		// dateOfBirth (Format: yyyyMMdd)= arrCardInfo[1];
		// expiryDate (Format: yyyy-MM)= arrCardInfo[2];
		// cvv(CVV number on the back of the card) = arrCardInfo[3];
		// nric(Customer NRIC number) = arrCardInfo[4];
		// Embossed Name (Name that is embossed on the card)= arrCardInfo[5];
		// creditLimit (Customer credit limit (digits only))= arrCardInfo[6];
		// cardToken(CardToken for EURONET)=arraCradInfo[7];
		// Base64RandomChallenge(Obtained via Generate Random Challenge API call) =
		// arrCardInfo[8];

		String debitcardNumber = reqObj.getCardNo();
		String dateOfBirth = reqObj != null && reqObj.getDateOfBirth() != null ? reqObj.getDateOfBirth() : "";
		String expiryDate = reqObj != null && reqObj.getExpiryDate() != null ? reqObj.getExpiryDate() : "";
		String cvv = reqObj != null && reqObj.getCvv() != null ? reqObj.getCvv() : "";
		String nric = reqObj != null && reqObj.getNric() != null ? reqObj.getNric() : "";
		String embossedName = reqObj != null && reqObj.getEmbossedName() != null ? reqObj.getEmbossedName() : "";
		String creditLimit = reqObj != null && reqObj.getCreditLimit() != null ? reqObj.getCreditLimit() : "";
		cardToken =cardToken != null ? cardToken : "";

		
		String encryptFormatFiledValues = debitcardNumber + "_-_" + dateOfBirth + "_-_" + expiryDate + "_-_" + cvv
				+ "_-_" + nric + "_-_" + embossedName + "_-_" + creditLimit + "_-_" + cardToken;

		System.out.println(encryptFormatFiledValues);
		
		String encInfo = encryptDataRSA(randomChallengeVo.getExponent(), randomChallengeVo.getModulus(), randomChallengeVo.getBase64Challenge(),
				encryptFormatFiledValues, reqObj.getSessionId());

		return encInfo;
	}

	public String generateCCPINEncCardPin(GenerateCCPIN_Req reqObj) {
		/*
		 * Card PIN information: values are delimited by_-_ entire string is encrypted
		 * with RSA public key, then Base64-encoded. Plaintext string format :
		 * field1_-field2-_ … _-_fieldN
		 * 
		 * CardPIN params are follows: cardNo = arrCardPIN[0]; oldPin = arrCardPIN[1];
		 * newPin = arrCardPIN[2]; confirmNewPin = arrCardPIN[3]; cardExpiryDate =
		 * arrCardPIN[4]; RandomChallengeForCardPIN = arrCardPIN[5];
		 */

		String cardNo = reqObj.getCardNo();
		String oldPin = reqObj.getOldPin();
		String newPin = reqObj.getNewPin();
		String confirmNewPin = reqObj.getConfirmNewPin();
		String cardExpiryDate = reqObj.getCardExpiryDate();

		String encryptFormatFiledValues = cardNo + "_-_" + oldPin + "_-_" + newPin + "_-_" + confirmNewPin + "_-_"
				+ cardExpiryDate;
		String encPin = encryptDataRSA(reqObj.getExponent(), reqObj.getModulus(), reqObj.getEncryptedBlock(),
				encryptFormatFiledValues, reqObj.getSessionId());

		return encPin;

	}

	public String generateCCPINEncCardInfo(GenerateCCPIN_Req reqObj) {

		/*
		 * Card information: values are delimited by_-_ entire string is encrypted with
		 * RSA public key, then Base64-encoded. Plaintext string format:
		 * field1_-field2-_ … _-_fieldN credit/Debit cardNumber = arrCardInfo[0];
		 * dateOfBirth (Format: yyyyMMdd)= arrCardInfo[1]; expiryDate (Format: yyyy-MM)=
		 * arrCardInfo[2]; cvv(CVV number on the back of the card) = arrCardInfo[3];
		 * nric(Customer NRIC number) = arrCardInfo[4]; Embossed Name (Name that is
		 * embossed on the card)= arrCardInfo[5]; creditLimit (Customer credit limit
		 * (digits only))= arrCardInfo[6]; cardToken(CardToken for
		 * EURONET)=arraCradInfo[7]; Base64RandomChallenge(Obtained via Generate Random
		 * Challenge API call) = arrCardInfo[8];
		 */

		String debitcardNumber = reqObj.getCardNo();
		String dateOfBirth = reqObj != null && reqObj.getDateOfBirth() != null ? reqObj.getDateOfBirth() : "";
		String expiryDate = reqObj != null && reqObj.getExpiryDate() != null ? reqObj.getExpiryDate() : "";
		String cvv = reqObj != null && reqObj.getCvv() != null ? reqObj.getCvv() : "";
		String nric = reqObj != null && reqObj.getNric() != null ? reqObj.getNric() : "";
		String embossedName = reqObj != null && reqObj.getEmbossedName() != null ? reqObj.getEmbossedName() : "";
		String creditLimit = reqObj != null && reqObj.getCreditLimit() != null ? reqObj.getCreditLimit() : "";
		String cardToken = reqObj != null && reqObj.getCardToken() != null ? reqObj.getCardToken() : "";

		String encryptFormatFiledValues = debitcardNumber + "_-_" + dateOfBirth + "_-_" + expiryDate + "_-_" + cvv
				+ "_-_" + nric + "_-_" + embossedName + "_-_" + creditLimit + "_-_" + cardToken;

		String encInfo = encryptDataRSA(reqObj.getExponent(), reqObj.getModulus(), reqObj.getEncryptedBlock(),
				encryptFormatFiledValues, reqObj.getSessionId());

		return encInfo;
	}

	public String validateCCPINEncCardPin(ValidateCCPIN_Req reqObj) {

		// Card PIN information:
		// values are delimited by_-_
		// entire string is encrypted with RSA public key, then Base64-encoded.
		// Plaintext string format :
		// field1_-field2-_ … _-_fieldN
		//
		// SL.No FieldName Description
		// 1. CardNumber credit/Debitcard Number
		// 2. PIN CardPINNumber
		// 3. Card Expiry Date Format:yyyy-MM
		// 4. Base64 RandomChallenge Obtained via Generate Random Chellange API Call

		String cardNo = reqObj.getCardNo();
		String pin = reqObj.getPin();
		String cardExpiryDate = reqObj.getCardExpiryDate();

		String encryptFormatFiledValues = cardNo + "_-_" + pin + "_-_" + cardExpiryDate;
		String encPin = encryptDataRSA(reqObj.getExponent(), reqObj.getModulus(), reqObj.getEncryptedBlock(),
				encryptFormatFiledValues, reqObj.getSessionId());

		return encPin;

	}

	public String validateCCPINEncCardInfo(ValidateCCPIN_Req reqObj) {

		// Card information:
		// values are delimited by_-_
		// entire string is encrypted with RSA public key, then Base64-encoded.
		// Plaintext string format:
		// field1_-field2-_ … _-_fieldN
		// credit/Debit cardNumber = arrCardInfo[0];
		// dateOfBirth (Format: yyyyMMdd)= arrCardInfo[1];
		// expiryDate (Format: yyyy-MM)= arrCardInfo[2];
		// cvv(CVV number on the back of the card) = arrCardInfo[3];
		// nric(Customer NRIC number) = arrCardInfo[4];
		// Embossed Name (Name that is embossed on the card)= arrCardInfo[5];
		// creditLimit (Customer credit limit (digits only))= arrCardInfo[6];
		// cardToken(CardToken for EURONET)=arraCradInfo[7];
		// Base64RandomChallenge(Obtained via Generate Random Challenge API call) =
		// arrCardInfo[8];

		String debitcardNumber = reqObj.getCardNo();
		String dateOfBirth = reqObj != null && reqObj.getDateOfBirth() != null ? reqObj.getDateOfBirth() : "";
		String expiryDate = reqObj != null && reqObj.getExpiryDate() != null ? reqObj.getExpiryDate() : "";
		String cvv = reqObj != null && reqObj.getCvv() != null ? reqObj.getCvv() : "";
		String nric = reqObj != null && reqObj.getNric() != null ? reqObj.getNric() : "";
		String embossedName = reqObj != null && reqObj.getEmbossedName() != null ? reqObj.getEmbossedName() : "";
		String creditLimit = reqObj != null && reqObj.getCreditLimit() != null ? reqObj.getCreditLimit() : "";
		String cardToken = reqObj != null && reqObj.getCardToken() != null ? reqObj.getCardToken() : "";

		String encryptFormatFiledValues = debitcardNumber + "_-_" + dateOfBirth + "_-_" + expiryDate + "_-_" + cvv
				+ "_-_" + nric + "_-_" + embossedName + "_-_" + creditLimit + "_-_" + cardToken;

		String encInfo = encryptDataRSA(reqObj.getExponent(), reqObj.getModulus(), reqObj.getEncryptedBlock(),
				encryptFormatFiledValues, reqObj.getSessionId());

		return encInfo;

	}
	
	public String getErrorCodeInFormat(String errorCode) {
		if (errorCode.length() < 6) {
			errorCode = "000000" + errorCode;
			errorCode = errorCode.substring(errorCode.length() - 6);
		}

		return errorCode;
	}
	
	public SimpleDateFormat getDateFormat(String dateFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/kolkata"));
		return simpleDateFormat;

	}
	
	public String getOTPExpiryMsgTemplate(String msgTemplate) {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/kolkata"));
		String myTime = simpleDateFormat.format(new Date());

		Date d = null;
		try {
			d = simpleDateFormat.parse(myTime);
		} catch (ParseException e) {
			System.out.println("Exception Occured while parsing otp Expiry time");
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.SECOND, 100);
		String expiredTime = simpleDateFormat.format(cal.getTime());
		
		msgTemplate = msgTemplate.replace("[{%otpExpireTime%}]", expiredTime);
		return msgTemplate;
	}
	
}
