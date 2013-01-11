package scalaskel;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public enum Coin {

    FOO(1),
    BAR(7),
    QIX(11),
    BAZ(21);

    public final int value;

    private Coin(int value) {
        this.value = value;
    }


}
