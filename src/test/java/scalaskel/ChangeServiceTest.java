package scalaskel;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@RunWith(Theories.class)
public class ChangeServiceTest {

    ChangeService s = new ChangeService();

    @DataPoints
    public static Combination[] combinations = {
            new Combination(0, "{}"),
            new Combination(1, "{\"foo\":1}"),
            new Combination(7, "{\"foo\":7}", "{\"bar\":1}"),
            new Combination(11, "{\"foo\":11}", "{\"foo\":4, \"bar\":1}", "{\"qix\":1}"),
            new Combination(21, "{\"foo\":21}",
                    "{\"bar\":3}", "{\"foo\":7, \"bar\":2}", "{\"foo\":14, \"bar\":1}",
                    "{\"foo\":3, \"bar\":1, \"qix\":1}", "{\"foo\":10, \"qix\":1}",
                    "{\"baz\":1}")
    };

    @Theory
    public void should_compute_all_possible_combinations(Combination c) {
        List<Change> changes = s.getPossibleChanges(c.grosDecimal);
        assertThat(changes.size(), equalTo(c.possibleChanges.size()));
        for (Change change : changes) {
            assertThat(c.possibleChanges, hasItem(change.asJson()));
        }

    }

    private static class Combination {
        int grosDecimal;
        List<String> possibleChanges;

        private Combination(int grosDecimal, String ... values) {
            this.grosDecimal = grosDecimal;
            this.possibleChanges = Arrays.asList(values);
        }
    }
}
