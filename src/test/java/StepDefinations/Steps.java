package StepDefinations;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;


public class Steps {
	Response response;
	public static JSONObject dataobj;
	public static Logger logger;

	@Given("User completed the initial setup")
	public void initialSetup() throws InterruptedException, IOException, ParseException {
		logger = Logger.getLogger("Steps");
		PropertyConfigurator.configure("log4j.properties");

		dataobj = common.common.readJSON("C:\\Users\\Shrikant\\eclipse-workspace\\TakeAwayAPIAutomation\\src\\test\\java\\data\\testdata.json");	
	}
	@Then("^User completed ([^\"]*) GET call ([^\"]*) with responseBody ([^\"]*)$")
	public void getCall(String path,String statuscode,boolean flag) throws InterruptedException, IOException, ParseException {
		response = common.common.getMethod((String)dataobj.get("url"),path);
		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(statuscode));
		logger.info("GET call completed with status code = "+response.getStatusCode());
		if(flag)
		{
			Assert.assertTrue(response.getBody().asString().equals("{}"));
		}else {Assert.assertTrue(!response.getBody().path("id").toString().isEmpty());}
	}
	@And("^User completed POST call ([^\"]*) and ([^\"]*)$")
	public void postComment(String path,String statuscode) throws InterruptedException, IOException, ParseException {

		response = common.common.postMethod((String)dataobj.get("url"),path);
		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(statuscode));
		logger.info("POST call completed with status code = "+response.getStatusCode());
	}
	@And("^User completed DELETE call ([^\"]*) and ([^\"]*)$")
	public void deleteCall(String path,String statuscode) throws InterruptedException, IOException, ParseException {

		response = common.common.deleteMethod((String)dataobj.get("url"),path);
		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(statuscode));
		logger.info("DELETE call completed with status code = "+response.getStatusCode());
	}
}
