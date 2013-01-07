import org.junit.ClassRule;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class Roud0Test {

    public static @ClassRule WebServer server = new WebServer();

    @Test
    public void should_return_my_email() {
        setBaseUrl("http://localhost:8080");
        beginAt("/?q=Quelle+est+ton+adresse+email");

        assertThat(getPageSource(), equalTo("nicolas.deloof@gmail.com"));
    }
}
