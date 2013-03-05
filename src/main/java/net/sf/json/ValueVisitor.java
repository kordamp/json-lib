package net.sf.json;

/**
 * @author Kohsuke Kawaguchi
 */
public abstract class ValueVisitor<V> {
    public abstract V acceptNull();
    public abstract V accept(JSONFunction f);
    public abstract V accept(JSONString s);
    public abstract V accept(Number n);
    public abstract V accept(Boolean b);
    public abstract V accept(JSONObject o);
    public abstract V accept(JSONArray a);
}
