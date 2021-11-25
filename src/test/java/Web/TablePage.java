package Web;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.ArrayList;
import java.util.List;

public class TablePage {

    @FindBy(how = How.XPATH, using = "//*[name()='path' and @class='highcharts-point']")
    private List<WebElement> cols;

    @FindBy(how = How.CSS, using = "tspan[style='font-weight:bold;']")
    private List<WebElement> info;

    @FindBy(how = How.CSS, using = "svg > g.highcharts-axis-labels.highcharts-xaxis-labels > text:nth-child(1)")
    private WebElement start_year;

    private List<Double> balances;

    @Step("Getting the number of cols using the number of points on the chart")
    //The number of cols is cols.size -1(The 1 is the title point);
    public int getNumberOfCols() {
        return cols.size() - 1;
    }

    @Step("Getting Balances")
    public List<Double> getBalances() {
        balances = new ArrayList<>();
        for (int i = 1; i < cols.size(); i++) {
            cols.get(i).click();
            String balance = info.get(3).getText().replaceAll("[$,]+", "");
            balances.add(Double.parseDouble(balance));
        }

        return balances;
    }


    @Step("Print Col Details")
    //The number of cols is cols.size -1(The 1 is the title point);
    public void printDetails() {
        int year = Integer.parseInt(start_year.getText());
        for (int i = 1; i < cols.size(); i++) {
            System.out.println("Year: " + year);
            cols.get(i).click();
            System.out.println("Taxes & Fees" + info.get(0).getText());
            System.out.println("Interest: " + info.get(1).getText());
            System.out.println("Principal: " + info.get(2).getText());
            System.out.println("Balances: " + info.get(3).getText());
            System.out.println();
            year++;
        }

    }


}
