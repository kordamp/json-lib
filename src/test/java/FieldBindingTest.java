import junit.framework.TestCase;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

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
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setIgnorePublicFields(false);
        JSONObject o = JSONObject.fromObject(f, jsonConfig);
        assertEquals(o.getInt("x"), 5);
        assertEquals(o.getString("y"), "test");
        assertEquals(o.size(), 2);
    }

    private final class Bar {
        public int x;
        public String y;
    }

    public void test2() throws Exception {
        // Property introspection is not capable of reading properties
        // from a private member class, even if they are defined as
        // public fields, thus the returned JSONObject must be empty

        Bar f = new Bar();
        f.x = 5;
        f.y = "test";
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setIgnorePublicFields(false);
        JSONObject o = JSONObject.fromObject(f, jsonConfig);
        assertTrue(o.isEmpty());
        // assertEquals(o.getInt("x"), 5);
        // assertEquals(o.getString("y"), "test");
        // assertEquals(o.size(), 2);
    }
}
