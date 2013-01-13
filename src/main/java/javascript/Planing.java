package javascript;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class Planing implements Cloneable {

    List<Booking> bookings = new ArrayList<Booking>();

    public int benefits;

    public boolean add(Booking booking) {
        int t = 0;
        if (!bookings.isEmpty()) {
            Booking b = bookings.get(bookings.size()-1);
            t = b.depart + b.duree;
        }
        if (booking.depart < t) return false;

        bookings.add(booking);
        benefits += booking.prix;
        return true;
    }

    public Planing clone() {
        Planing p = new Planing();
        p.bookings.addAll(this.bookings);
        p.benefits = this.benefits;
        return p;
    }

    public String asJson() {
        StringBuilder s = new StringBuilder("{")
            .append("\"gain\" : ").append(benefits).append(",")
            .append("\"path\" : [");
        String sep = "";
        for (Booking booking : bookings) {
            s.append(sep).append("\"").append(booking.vol).append("\"");
            sep = ",";
        }
        s.append("]}");
        return s.toString();

    }
}
