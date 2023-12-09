package common;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.UUID;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CommonModules {
	static Properties prop = new Properties();
	
	public static String randomUUID() {
		
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public String getpreSignedUrl(String data, String keyName) throws IOException {
		
		ObjectNode node = new ObjectMapper().readValue(data, ObjectNode.class);
		return node.get(keyName).toString();
	}
	
	public String getDateandtime() {
		
		LocalDateTime currenttimestamp = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String neweventdatetime = currenttimestamp.format(formatter).toString();
		return neweventdatetime;

	}
	
	public static void getEnvironmentConfigurationsOpen() throws IOException,FileNotFoundException {

//		String sysEnvStr = "src/test/java/configurations.properties";
//		InputStream input1 = new FileInputStream(sysEnvStr);
//		InputStreamReader inputStreamReader = new InputStreamReader(input1);
		
		String AWS_ACCESS_KEY_ID = System.getenv("AWS_ACCESS_KEY_ID");
		String AWS_SECRET_ACCESS_KEY = System.getenv("AWS_SECRET_ACCESS_KEY");
		String AWS_Shared_S3_Bucket_Name = "test-automation-solution-shared";
		String AWS_Shared_S3_Bucket_File_Name = "pact.properties";
		
		AmazonS3Client s3client = new AmazonS3Client(new BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY));
		S3Object s3Object = s3client.getObject(new GetObjectRequest(AWS_Shared_S3_Bucket_Name, AWS_Shared_S3_Bucket_File_Name)); 																					
		InputStream inputStreamReader = s3Object.getObjectContent();
		
		prop.load(inputStreamReader);
	}

   public static String getEnvironmentConfigurations(String variableName) {
		
		String variable = prop.getProperty(variableName);
		System.out.println(variableName + " = " + variable);
		return variable;
	}

	

}
