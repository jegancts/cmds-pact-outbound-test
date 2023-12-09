package Outbound_Events_Test;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import common.CommonModules;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
 
  public class Test_Options_IDS_to_Inspera_Test  {
	  static CommonModules common = new CommonModules();
  public static final String providerServiceName = "Test_Options_Inspera_Provider";
  public static final String consumerServiceName = "Test_Options_IDS_Consumer";

  public static String AuthURL;

  public static int providerServicePort = 8110;
  public static String providerUrl_POST_Test = "http://localhost:" + providerServicePort + "/api/v1/test/84561316/options";
  public String postbody = new String(Files.readAllBytes(Paths.get("src/Resources/Input_Int51_IdsToInspera_SetOrUpdateOptionsOnTest_Post.txt")), StandardCharsets.UTF_8);
  public Test_Options_IDS_to_Inspera_Test() throws IOException {   }
    @Rule
    public PactProviderRuleMk2 provider = new PactProviderRuleMk2(providerServiceName, "localhost", providerServicePort, this);
    
    public static String AccessToken = RetrieveToken();
    public static String JWTtoken ="Bearer "+AccessToken;

    public static String RetrieveToken() 
    {

    	try {
    		CommonModules.getEnvironmentConfigurationsOpen();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
       
        if (System.getenv("ENVIRONMENT").equalsIgnoreCase("sandbox"))
    	{
        	System.out.println("ENVIRONMENT sandbox");
    		 AuthURL =  common.getEnvironmentConfigurations("TestOptionAuthURL.sandbox");
                 
         }
        else if (System.getenv("ENVIRONMENT").equalsIgnoreCase("dev"))
    	{
        	System.out.println("ENVIRONMENT dev");
    		 AuthURL =  common.getEnvironmentConfigurations("CreateStudentUserIDStoInsperaAuthURL.dev");
                 
         }
        else if (System.getenv("ENVIRONMENT").equalsIgnoreCase("sit"))
    	{
        	System.out.println("ENVIRONMENT sit");
    		 AuthURL =  common.getEnvironmentConfigurations("CreateStudentUserIDStoInsperaAuthURL.sit");
                 
         }
        else if (System.getenv("ENVIRONMENT").equalsIgnoreCase("uat"))
    	{
        	System.out.println("ENVIRONMENT uat");
    		 AuthURL =  common.getEnvironmentConfigurations("CreateStudentUserIDStoInsperaAuthURL.uat");
                 
         }
    	
    	AuthURL = "https://fjordint.inspera.com/api/authenticate/token/?code=092a35b5e2bcdce08c3bb1520a8999d4&grant_type=authorization_code&client_id=fjordint";
	    RestAssured.baseURI = AuthURL;
    		
    Response response = given().headers("Content-Type", "application/x-www-form-urlencoded").contentType(ContentType.URLENC).log().all().queryParams("code", "82da7a07f412c7cd9754df175e8a17d4", "grant_type", "authorization_code" ,"client_id", "ieltstraining").post();
    System.out.println("Response: "+response.getStatusCode());
    JSONObject jsonObject = new JSONObject(response.getBody().asString());
    String AccessToken = jsonObject.get("access_token").toString();
    return AccessToken;
    }
    
    
    @Pact(consumer = consumerServiceName)
    public RequestResponsePact createPact(PactDslWithProvider builder) {
    	Map<String, String> reqheaders = new HashMap();
    	reqheaders.put("Content-Type", "application/json");
    	reqheaders.put("Authorization", JWTtoken);
        
        Map<String, String> resheaders = new HashMap();
        resheaders.put("Content-Type", "application/json");

        DslPart reqBody = new PactDslJsonBody()	 
        		 .object("testOptions")
                 .array("explanationOptions")
                 .closeArray()
                 .array("securityOptions")
                 .object()
                 .stringType("option", "LOCKDOWN_BROWSER")
                 .booleanType("value", true)
                 .closeObject()
                 .object()
                 .stringType("option", "SEB_SETTINGS_PINCODE")
                 .stringType("value", "Password123")
                 .closeObject()
                 .closeArray()
                 .array("examDayOptions")
                 .closeArray()
                 .array("generalOption")
                 .closeArray()
                 .closeObject()
                 .asBody();
        
       DslPart resBody = new PactDslJsonBody()    
    		     .booleanType("success", true)
    	         .asBody();
        
       return builder
             .given("IDS Publishes Set Or Update Options On Test to Inspera")
             .uponReceiving("Inspera sends acknowledgement for Set Or Update Options")
             .method("POST")
             .headers(reqheaders)
             .body(reqBody)
             .path("/api/v1/test/84561316/options")
             .willRespondWith()
             .status(200)
             .headers(resheaders)
             .body(resBody)
             .toPact();
            }


    @Test
    @Category(Test_Options_IDS_Consumer.class)
    @PactVerification()
    public void doTest() throws IOException {
        HttpPost httpPost = null;
        HttpClient httpClient = new DefaultHttpClient();
        String url=String.format(providerUrl_POST_Test);
        System.out.println("using url: "+url);
        HttpEntity httpEntity = new StringEntity((postbody), "utf-8");
        httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", JWTtoken);
        httpPost.setEntity(httpEntity);
        System.out.println("postbody :" + postbody);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        System.out.println("Response : "+httpResponse);
        System.out.println("Post : "+httpPost);
    }

}
