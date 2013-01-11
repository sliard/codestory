package scalaskel;

import static scalaskel.Coin.*;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class Change {

    public int foo, bar, qix, baz;
    public int grosDessimalValue;

    public Change(int foo, int bar, int qix, int baz) {
        this.foo = foo;
        this.bar = bar;
        this.qix = qix;
        this.baz = baz;
        this.grosDessimalValue = FOO.value * foo + BAR.value * bar + QIX.value * qix + BAZ.value * baz;
    }

    public void add(Coin coin) {
        switch (coin) {
            case FOO: foo++; break;
            case BAR: bar++; break;
            case QIX: qix++; break;
            case BAZ: baz++; break;
        }
        grosDessimalValue += coin.value;
    }

    public void addUpTo(Coin coin, int grosDessimal) {
        while (grosDessimalValue + coin.value <= grosDessimal) {
            add(coin);
        }
    }

    public String asJson() {
        String sep = "";
        StringBuilder s = new StringBuilder("{");
        if (foo > 0) {
            s.append(sep).append("\"foo\":").append(foo);
            sep = ", ";
        }
        if (bar > 0) {
            s.append(sep).append("\"bar\":").append(bar);
            sep = ", ";
        }
        if (qix > 0) {
            s.append(sep).append("\"qix\":").append(qix);
            sep = ", ";
        }
        if (baz > 0) {
            s.append(sep).append("\"baz\":").append(baz);
            sep = ", ";
        }
        return s.append("}").toString();
    }
}
