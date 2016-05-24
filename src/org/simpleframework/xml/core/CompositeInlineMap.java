/*
 * Decompiled with CFR 0_114.
 */
package org.simpleframework.xml.core;

import java.util.Map;
import java.util.Set;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Entry;
import org.simpleframework.xml.core.MapFactory;
import org.simpleframework.xml.core.Repeater;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.Mode;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.stream.Style;

class CompositeInlineMap
implements Repeater {
    private final MapFactory factory;
    private final Converter value;
    private final Converter key;
    private final Style style;
    private final Entry entry;

    public CompositeInlineMap(Context context, Entry entry, Type type) throws Exception {
        this.factory = new MapFactory(context, type);
        this.value = entry.getValue(context);
        this.key = entry.getKey(context);
        this.style = context.getStyle();
        this.entry = entry;
    }

    public Object read(InputNode node) throws Exception {
        Object value = this.factory.getInstance();
        Map table = (Map)value;
        if (table != null) {
            return this.read(node, table);
        }
        return null;
    }

    public Object read(InputNode node, Object value) throws Exception {
        Map map = (Map)value;
        if (map != null) {
            return this.read(node, map);
        }
        return this.read(node);
    }

    private Object read(InputNode node, Map map) throws Exception {
        InputNode from = node.getParent();
        String name = node.getName();
        while (node != null) {
            Object index = this.key.read(node);
            Object item = this.value.read(node);
            if (map != null) {
                map.put(index, item);
            }
            node = from.getNext(name);
        }
        return map;
    }

    public boolean validate(InputNode node) throws Exception {
        InputNode from = node.getParent();
        String name = node.getName();
        while (node != null) {
            if (!this.key.validate(node)) {
                return false;
            }
            if (!this.value.validate(node)) {
                return false;
            }
            node = from.getNext(name);
        }
        return true;
    }

    public void write(OutputNode node, Object source) throws Exception {
        OutputNode parent = node.getParent();
        Mode mode = node.getMode();
        Map map = (Map)source;
        if (!node.isCommitted()) {
            node.remove();
        }
        this.write(parent, map, mode);
    }

    private void write(OutputNode node, Map map, Mode mode) throws Exception {
        String root = this.entry.getEntry();
        String name = this.style.getElement(root);
        for (Object index : map.keySet()) {
            OutputNode next = node.getChild(name);
            Object item = map.get(index);
            next.setMode(mode);
            this.key.write(next, index);
            this.value.write(next, item);
        }
    }
}

