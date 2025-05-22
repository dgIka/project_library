package ika;

import org.springframework.ui.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SimpleModel implements Model {
    private final Map<String, Object> model = new HashMap<String, Object>();

    @Override
    public Model addAttribute(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    @Override
    public Model addAttribute(Object attributeValue) {
        return null;
    }

    @Override
    public Model addAllAttributes(Collection<?> attributeValues) {
        return null;
    }

    @Override
    public Model addAllAttributes(Map<String, ?> attributes) {
        return null;
    }

    @Override
    public Model mergeAttributes(Map<String, ?> attributes) {
        return null;
    }

    @Override
    public boolean containsAttribute(String attributeName) {
        return false;
    }

    @Override
    public Object getAttribute(String attributeName) {
        return model.get(attributeName);
    }

    @Override
    public Map<String, Object> asMap() {
        return null;
    }
}
