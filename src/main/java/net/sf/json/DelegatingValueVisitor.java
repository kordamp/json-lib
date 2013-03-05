package net.sf.json;

/**
 * @author Kohsuke Kawaguchi
 */
public abstract class DelegatingValueVisitor<V> extends ValueVisitor<V> {
    protected abstract V accept(Object o);
    protected V accept(JSON json) {
        return accept((Object)json);
    }

    public V accept(JSONFunction f) {
        return accept((Object)f);
    }

    public V accept(JSONString s) {
        return accept((Object)s);
    }

    public V accept(Number n) {
        return accept((Object)n);
    }

    public V accept(Boolean b) {
        return accept((Object)b);
    }

    public V accept(JSONObject o) {
        return accept((JSON)o);
    }

    public V accept(JSONArray a) {
        return accept((JSON)a);
    }
}
