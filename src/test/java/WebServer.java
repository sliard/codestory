import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;



/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class WebServer implements TestRule {


    private final int portNumber;

    public WebServer() {
        this.portNumber = Integer.getInteger("port", 8080);
    }

    public String getURL() {
        return "http://localhost:" + portNumber + "/";
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Server server = new Server(portNumber);
                ServletContextHandler context = new ServletContextHandler();
                context.setContextPath("/");
                server.setHandler(context);
                context.addServlet(new ServletHolder(new Main()),"/*");
                server.start();
                try {
                    base.evaluate();
                } finally {
                    server.stop();
                }
            }
        };
    }
}
