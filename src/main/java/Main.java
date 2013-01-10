import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class Main extends HttpServlet {

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
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        String r;
        if (q == null) {
            r = "@see http://code-story.net";
        } else {
            try {
                r = routes.getString(q);
            } catch(JSONException e) {
                r = "Désole, je ne comprends pas votre question";
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        resp.getWriter().print(r);
   }
}
