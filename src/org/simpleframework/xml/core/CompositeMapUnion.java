/*
 * Decompiled with CFR 0_114.
 */
package org.simpleframework.xml.core;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Group;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.LabelMap;
import org.simpleframework.xml.core.Repeater;
import org.simpleframework.xml.core.UnionException;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.stream.Style;

class CompositeMapUnion
implements Repeater {
    private final LabelMap elements;
    private final Expression path;
    private final Context context;
    private final Group group;
    private final Style style;
    private final Type type;

    public CompositeMapUnion(Context context, Group group, Expression path, Type type) throws Exception {
        this.elements = group.getElements(context);
        this.style = context.getStyle();
        this.context = context;
        this.group = group;
        this.type = type;
        this.path = path;
    }

    public Object read(InputNode node) throws Exception {
        String name = node.getName();
        String element = this.path.getElement(name);
        Label label = (Label)this.elements.get(element);
        Converter converter = label.getConverter(this.context);
        return converter.read(node);
    }

    public Object read(InputNode node, Object value) throws Exception {
        String name = node.getName();
        String element = this.path.getElement(name);
        Label label = (Label)this.elements.get(element);
        Converter converter = label.getConverter(this.context);
        return converter.read(node, value);
    }

    public boolean validate(InputNode node) throws Exception {
        String name = node.getName();
        String element = this.path.getElement(name);
        Label label = (Label)this.elements.get(element);
        Converter converter = label.getConverter(this.context);
        return converter.validate(node);
    }

    public void write(OutputNode node, Object source) throws Exception {
        Map map = (Map)source;
        if (this.group.isInline()) {
            if (!map.isEmpty()) {
                this.write(node, map);
            } else if (!node.isCommitted()) {
                node.remove();
            }
        } else {
            this.write(node, map);
        }
    }

    private void write(OutputNode node, Map map) throws Exception {
        for (Object key : map.keySet()) {
            Object item = map.get(key);
            if (item == null) continue;
            Class real = item.getClass();
            Label label = this.group.getLabel(real);
            if (label == null) {
                throw new UnionException("Value of %s not declared in %s with annotation %s", real, this.type, this.group);
            }
            this.write(node, key, item, label);
        }
    }

    private void write(OutputNode node, Object key, Object item, Label label) throws Exception {
        Converter converter = label.getConverter(this.context);
        Map<Object, Object> map = Collections.singletonMap(key, item);
        if (!label.isInline()) {
            String name = label.getName();
            String root = this.style.getElement(name);
            if (!node.isCommitted()) {
                node.setName(root);
            }
        }
        converter.write(node, map);
    }
}

