import com.meterware.httpunit.*;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class AcceptanceTest {

    public static @ClassRule WebServer server = new WebServer();

    WebConversation wc = new WebConversation();    

    @Test
    public void should_return_my_email() throws Exception {
        WebRequest req = new GetMethodWebRequest("http://localhost:8080/?q=Quelle+est+ton+adresse+email");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getText(), equalTo("nicolas.deloof@gmail.com"));
    }

    @Test
    public void should_confirm_I_subscribed_the_mailing_list() throws Exception {
        WebRequest req = new GetMethodWebRequest("http://localhost:8080/?q=Es+tu+abonne+a+la+mailing+list(OUI/NON)");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getText(), equalTo("OUI"));
    }

    @Test
    public void should_confirm_I_m_happy() throws Exception {
        WebRequest req = new GetMethodWebRequest("http://localhost:8080/?q=Es+tu+heureux+de+participer(OUI/NON)");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getText(), equalTo("OUI"));
    }

    @Test
    public void should_accept_POST_challenges() throws Exception {
        WebRequest req = new GetMethodWebRequest("http://localhost:8080/?q=Es+tu+pret+a+recevoir+une+enonce+au+format+markdown+par+http+post(OUI/NON)");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getText(), equalTo("OUI"));
    }

    @Test
    public void should_log_POST() throws Exception {
        PostMethodWebRequest req = new PostMethodWebRequest("http://localhost:8080");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getResponseCode(), equalTo(201));
    }

    @Test
    public void should_not_always_answer_YES() throws Exception {
        WebRequest req = new GetMethodWebRequest("http://localhost:8080/?q=Est+ce+que+tu+reponds+toujours+oui(OUI/NON)");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getText(), equalTo("NON"));
    }

    @Test
    public void should_have_received_challenge_1() throws Exception {
        WebRequest req = new GetMethodWebRequest("http://localhost:8080/?q=As+tu+bien+recu+le+premier+enonce(OUI/NON)");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getText(), equalTo("OUI"));
    }

    @Test
    public void should_compute_scalakel() throws Exception {
        WebRequest req = new GetMethodWebRequest("http://localhost:8080/scalaskel/change/1");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getText(), equalTo("[{\"foo\":1}]"));
    }

    @Test
    public void should_compute_1and1() throws Exception {
        WebRequest req = new GetMethodWebRequest("http://localhost:8080/?q=1+1");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getText(), equalTo("2"));
    }

    @Test
    public void should_compute_2and2() throws Exception {
        WebRequest req = new GetMethodWebRequest("http://localhost:8080/?q=2+2");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getText(), equalTo("4"));
    }

    @Test
    public void should_compute_3x3() throws Exception {
        WebRequest req = new GetMethodWebRequest("http://localhost:8080/?q=3*3");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getText(), equalTo("9"));
    }

    @Test
    public void should_compute_3div2() throws Exception {
        WebRequest req = new GetMethodWebRequest("http://localhost:8080/?q=3/2");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getText(), equalTo("1,5")); // french
    }

    @Test
    public void should_compute_1dot5x4() throws Exception {
        WebRequest req = new GetMethodWebRequest("http://localhost:8080/?q=1,5*4");
        WebResponse resp = wc.getResponse( req );
        assertThat(resp.getText(), equalTo("6,0")); // french
    }

}
