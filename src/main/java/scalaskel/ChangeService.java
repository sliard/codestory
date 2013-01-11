package scalaskel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class ChangeService {

    public List<Change> getPossibleChanges(int groDessimal) {
        return completeWithFooBarQixBaz(groDessimal, new Change(0,0,0,0));
    }

    private List<Change> completeWithFooBarQixBaz(int groDessimal, Change c) {
        List<Change> changes = new ArrayList<Change>();
        c.addUpTo(Coin.BAZ, groDessimal);
        for (int i = c.baz; i >= 0; i--) {
            changes.addAll(completeWithFooBarQix(groDessimal, new Change(0, 0, 0, i)));
        }
        return changes;
    }

    private List<Change> completeWithFooBarQix(int groDessimal, Change c) {
        List<Change> changes = new ArrayList<Change>();
        c.addUpTo(Coin.QIX, groDessimal);
        for (int i = c.qix; i >= 0; i--) {
            changes.addAll(completeWithFooBar(groDessimal, new Change(0,0,i,c.baz)));
        }
        return changes;
    }

    private List<Change> completeWithFooBar(int groDessimal, Change c) {
        List<Change> changes = new ArrayList<Change>();
        c.addUpTo(Coin.BAR, groDessimal);
        for (int i = c.bar; i >= 0; i--) {
            changes.add(completeWithFoo(groDessimal, new Change(0,i,c.qix,c.baz)));
        }
        return changes;
    }

    private Change completeWithFoo(int groDessimal, Change c) {
        c.addUpTo(Coin.FOO, groDessimal);
        return c;
    }

}
