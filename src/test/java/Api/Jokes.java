package Api;

import Web.AutomationListeners;
import Web.MonteScreenRecorder;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

@Listeners(AutomationListeners.class)
public class Jokes {

    private final String url = "https://api.chucknorris.io/";
    public static RequestSpecification request;
    public Response response;
    private JsonPath jp;
    @BeforeClass
    public void start() {
        RestAssured.baseURI = url;
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
    }
    @BeforeMethod
    public void beforeMethod(Method method) {
        try {
            MonteScreenRecorder.startRecord(method.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test01_Categories() {
        printCategories();
    }

    @Test
    public void test02_verifyLargerJokes() {
        int barackCount = querySearch("Barack Obama");
        int charlieCount = querySearch("Charlie Sheen");
        Assert.assertTrue(barackCount >= charlieCount);

    }

    @Step("Return the number of jokes for a given query")
    public int querySearch(String name) {
        response = request.get("/jokes/search?query= " + name);
        jp = response.jsonPath();
        return Integer.parseInt(jp.get("total").toString());
    }


    @Step("Printing the list of Categories")
    public void printCategories() {
        response = request.get("/jokes/categories");
        jp = response.jsonPath();
        List<String> categories = jp.getList(".");
        for (String category : categories) {
            System.out.println("Category: " + category);
        }
    }


}
