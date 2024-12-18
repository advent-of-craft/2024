import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@SelectClasspathResource("features")
@CucumberOptions(plugin = {"pretty"})
public class RunCucumberTest {
}