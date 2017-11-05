package TestForGP; 

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By; 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.annotation.en.Given; 
import cucumber.annotation.en.Then; 
import cucumber.annotation.en.When;
import cucumber.annotation.en.And;

public class ScenariousForGP { 
   WebDriver driver = null; 
   
   @Given("^My Firefox Browser is open on William Hill Bet Page$") 
	
   public void goToWilliamHillWebSide() { 
      driver = new FirefoxDriver(); 
      driver.manage().window().maximize();
      WebDriverWait wait = new WebDriverWait(driver, 5);
      driver.navigate().to("http://sports.williamhill.com/betting/en-gb/"); 
   }
      
    @When ("^I find English Premier Leage place to bet$")
 
    public void findEnglishPremierLeaugeEvent() {
      driver.findElement(By.partialLinkText("Betting")).click();
      //I had some problems with internet and loading last to long so i put wait for 3 sec.
      driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
      driver.findElement(By.partialLinkText("Football")).click();
      //Selecting "UK Match Betting" Tab. firstly i used xpath but during testing this field was changed 
      //on webside and my tests started to fail so i changed it to find by partialName
      //driver.findElement(By.xpath(".//*[@id='highlights-coupons']/div/div[2]/section[5]/a")).click();
      driver.findElement(By.partialLinkText("UK Match Betting")).click();
    }
    
    
    @And ("^I place a bet for Home win with value \"(.*)\"$")
    
    public void betting(String bet) {
    	//I clik in to home win 
      WebElement element = driver.findElement(By.id("OB_OU1766786773"));
      Actions actions = new Actions(driver);
      actions.moveToElement(element).click().perform();
      driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
      //I send bet 
      driver.findElement(By.id("stake-input__1766786773L")).sendKeys(bet);
    }
    
    
    @Then ("^Webside should return proper value of money to win$")
    
    public void checks() {
      //Getting value of MyBet from field in browser - i will use this value to compere if it is the same as my bet
      String MyBet;
      MyBet = driver.findElement(By.id("total-stake-price")).getText();
      
      
      //Getting value of money to win in string type
      String MoneyToWin;
      MoneyToWin = driver.findElement(By.id("total-to-return-price")).getText();
      
      //Getting value of BettingOdds
      String x;
      x = driver.findElement(By.id("OB_OU1766786773")).getText();
      
      //Spliting value of Betting Odds by "/"
      String wynik[] =null;
      wynik = x.split("/");
      
      //Bettings Odds conversion from string to Double
      Double BettingOdds = Double.valueOf(wynik[0])/Double.valueOf(wynik[1]);
      
      //Money To win conversion from string to Double
      Double MyMoneyToWin = Double.valueOf(MoneyToWin);
      
      //My Bet conversion from string to Double
      Double MyOwnBet = Double.valueOf(MyBet);
      
      
      //Checking if our webside corectly count price to win  and Rounding up to 2 decimal places
      Double ValueOfWebWinPrice = MyOwnBet*BettingOdds+MyOwnBet;
      ValueOfWebWinPrice*=100;
      ValueOfWebWinPrice = (double) Math.round(ValueOfWebWinPrice);
      ValueOfWebWinPrice /=100;
      
      System.out.println("Wartosc ValueOfWebWing to" + ValueOfWebWinPrice );
      System.out.println("Wartosc MyMoneyToWin to" + MyMoneyToWin );
      
      if(ValueOfWebWinPrice == MyMoneyToWin) {
    	  System.out.println("Everythink is ok");
      }
      else {
    	  System.out.println("Browser is returning wrong value of Money to Win");
      }
      
      driver.close();
      
    
    }
      
      
      
     
} 