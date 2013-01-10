import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Main extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        if (q.equals("Quelle est ton adresse email")) {
            resp.getWriter().print("nicolas.deloof@gmail.com");
        } else if (q.equals("Es tu abonne a la mailing list(OUI/NON)")) {
            resp.getWriter().print("OUI");
        } else {
            resp.getWriter().print("@see http://code-story.net");
        }
   }
}
