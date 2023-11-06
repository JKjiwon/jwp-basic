package core.mvc;

import java.util.HashMap;
import java.util.Set;

public class ModelMap extends HashMap<String, Object> {

    public ModelMap() {
    }

    public void setAttribute(String name, Object o) {
        put(name, o);
    }

    public Object getAttribute(String name) {
        return get(name);
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return super.keySet();
    }
}
