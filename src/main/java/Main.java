import groovy.lang.GroovyShell;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import scalaskel.Change;
import scalaskel.ChangeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends HttpServlet {

    private static final String SCALASKEL = "/scalaskel/change/";
    private ChangeService service = new ChangeService();

    private final Pattern operation = Pattern.compile("(\\d+)([ *])(\\d+)"); // '+' char in URL converted to blank

    private JSONObject routes;

    @Override
    public void init() throws ServletException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("route.json");
        try {
            routes = JSONObject.fromObject(IOUtils.toString(stream));
        } catch (IOException e) {
            throw new ServletException("failed to load routes definition");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().log("POST:" + IOUtils.toString(req.getInputStream()));
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // TODO implement some routing logic as this become unreadable

        String path = req.getPathInfo();

        // tomcat don't behave like jetty
        if (path == null) path = req.getServletPath();

        if (path.equals("/")) {
            String q = req.getParameter("q");
            String r;
            if (q == null) {
                r = "@see http://code-story.net";
            } else {
                try {
                    r = routes.getString(q);
                } catch(JSONException e) {
                    q = q.replace(' ', '+') // URL encoding use '+' for blank
                         .replace(',', '.'); // Force french-style decimalformat
                    r = String.valueOf( new GroovyShell().evaluate(q) ).replace('.', ',');
                    r = truncate(r);
                }
            }
            resp.getWriter().print(r);
        }
        else if (path.startsWith(SCALASKEL)) {
            int groDessimal = Integer.parseInt(path.substring(SCALASKEL.length()));
            resp.setContentType("application/json");
            PrintWriter w = resp.getWriter();
            String sep = "[";
            for (Change change : service.getPossibleChanges(groDessimal)) {
                w.print(sep);
                sep = ", ";
                w.print(change.asJson());
            }
            w.print("]");
        }
   }

    private String truncate(String r) {
        // probably could be done a better way
        if (r.endsWith(",0")) r = r.substring(0, r.length() - 2);
        if (r.endsWith(",00")) r = r.substring(0, r.length() - 3);
        return r;
    }
}
