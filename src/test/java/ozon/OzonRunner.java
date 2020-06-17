package ozon;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        glue = {"ozon.steps"},
        features = {"src/test/resources/"}
)
public class OzonRunner {
}
