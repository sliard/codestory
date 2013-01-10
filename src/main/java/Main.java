import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Main extends HttpServlet {

    private static final String QUELLE_EST_TON_ADRESSE_EMAIL = "Quelle est ton adresse email";
    private static final String ES_TU_ABONNE_A_LA_MAILING_LIST = "Es tu abonne a la mailing list(OUI/NON)";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        String r;
        if (q.equals(QUELLE_EST_TON_ADRESSE_EMAIL)) {
            r = "nicolas.deloof@gmail.com";
        } else if (q.equals(ES_TU_ABONNE_A_LA_MAILING_LIST)) {
            r = "OUI";
        } else {
            r = "@see http://code-story.net";
        }
        resp.getWriter().print(r);
   }
}
