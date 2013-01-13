package javascript;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class Booking {

    public String vol;
    public int depart;
    public int duree;
    public int prix;

    public static Booking from(JSONObject json) {
        Booking b = new Booking();
        b.vol = json.getString("VOL");
        b.depart = json.getInt("DEPART");
        b.duree = json.getInt("DUREE");
        b.prix = json.getInt("PRIX");
        return b;
    }

    public static List<Booking> from(JSONArray json) {
        List<Booking> l = new ArrayList<Booking>(json.size());
        for (Object o : json) {
            l.add(from((JSONObject) o));
        }
        return l;
    }
}
