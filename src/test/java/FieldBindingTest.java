import junit.framework.TestCase;
import net.sf.json.JSONObject;

/**
 * @author Kohsuke Kawaguchi
 */
public class FieldBindingTest extends TestCase {
    public final class Foo {
        public int x;
        public String y;
    }

    public void test1() throws Exception {
        Foo f = new Foo();
        f.x = 5;
        f.y = "test";
        JSONObject o = JSONObject.fromObject(f);
        assertEquals(o.getInt("x"),5);
        assertEquals(o.getString("y"),"test");
        assertEquals(o.size(),2);
    }

    private final class Bar {
        public int x;
        public String y;
    }

    public void test2() throws Exception {
        Bar f = new Bar();
        f.x = 5;
        f.y = "test";
        JSONObject o = JSONObject.fromObject(f);
        assertEquals(o.getInt("x"),5);
        assertEquals(o.getString("y"),"test");
        assertEquals(o.size(),2);
    }
}
