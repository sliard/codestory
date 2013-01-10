import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Main extends HttpServlet {

    private static final String QUELLE_EST_TON_ADRESSE_EMAIL = "Quelle est ton adresse email";
    private static final String ES_TU_ABONNE_A_LA_MAILING_LIST = "Es tu abonne a la mailing list(OUI/NON)";
    private static final String ES_TU_HEUREUX_DE_PARTICIPER = "Es tu heureux de participer(OUI/NON)";

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
        } else if (q.equals(QUELLE_EST_TON_ADRESSE_EMAIL)) {
            r = "nicolas.deloof@gmail.com";
        } else if (q.equals(ES_TU_ABONNE_A_LA_MAILING_LIST)) {
            r = "OUI";
        } else if (q.equals(ES_TU_HEUREUX_DE_PARTICIPER)) {
            r = "OUI";
        } else {
            r = "Désole, je ne comprends pas votre question";
        }

        resp.getWriter().print(r);
   }
}
