import groovy.lang.GroovyShell;
import javascript.OptimizeService;
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

public class Main extends HttpServlet {

    // challenge #1
    private static final String SCALASKEL = "/scalaskel/change/";
    private ChangeService scalaskel = new ChangeService();

    // challenge #2
    private static final String JAVASCRIPT = "/javascript/optimize";
    private OptimizeService javascript = new OptimizeService();

    // handle ?q=...
    private JSONObject routes;

    @Override
    public void init() throws ServletException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("questions.json");
        try {
            routes = JSONObject.fromObject(IOUtils.toString(stream));
        } catch (IOException e) {
            throw new ServletException("failed to load routes definition");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().log("POST:" + getBody(req));
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
                    r = calculator(q);
                }
            }
            resp.getWriter().print(r);
        }
        else if (path.startsWith(SCALASKEL)) {
            int groDessimal = Integer.parseInt(path.substring(SCALASKEL.length()));
            resp.setContentType("application/json");
            Change.asJson(resp.getWriter(), scalaskel.getPossibleChanges(groDessimal));
        }
        else if (path.startsWith(JAVASCRIPT)) {
            resp.setContentType("application/json");
            resp.getWriter().print(javascript.optimize(getBody(req)).asJson());
        }
   }

    private String getBody(HttpServletRequest req) throws IOException {
        return IOUtils.toString(req.getInputStream());
    }

    private String calculator(String q) {
        String r;
        q = q.replace(' ', '+') // URL encoding use '+' for blank
             .replace(',', '.'); // Force french-style decimalformat
        r = String.valueOf( new GroovyShell().evaluate(q) ).replace('.', ',');
        r = truncate(r);
        return r;
    }

    private String truncate(String r) {
        // probably could be done a better way
        if (r.endsWith(",0")) r = r.substring(0, r.length() - 2);
        if (r.endsWith(",00")) r = r.substring(0, r.length() - 3);
        return r;
    }
}
