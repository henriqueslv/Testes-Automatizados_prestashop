package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber .class)
@CucumberOptions(
        features = "src\\test\\resources\\features\\comprar_produtos.feature",
        glue = "steps",
        //tags = "@fluxopadrao",
        plugin = {"pretty","html:target/cucumber.html"},
        monochrome = true
)

public class Runner {
}
