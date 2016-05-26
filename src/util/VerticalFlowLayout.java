/*
 * Decompiled with CFR 0_114.
 */
package util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.io.Serializable;

public class VerticalFlowLayout
extends FlowLayout
implements Serializable {
    public static final int TOP = 0;
    public static final int MIDDLE = 1;
    public static final int BOTTOM = 2;
    int hgap;
    int vgap;
    boolean hfill;
    boolean vfill;

    public VerticalFlowLayout() {
        this(0, 5, 5, true, false);
    }

    public VerticalFlowLayout(boolean hfill, boolean vfill) {
        this(0, 5, 5, hfill, vfill);
    }

    public VerticalFlowLayout(int align) {
        this(align, 5, 5, true, false);
    }

    public VerticalFlowLayout(int align, boolean hfill, boolean vfill) {
        this(align, 5, 5, hfill, vfill);
    }

    public VerticalFlowLayout(int align, int hgap, int vgap, boolean hfill, boolean vfill) {
        this.setAlignment(align);
        this.hgap = hgap;
        this.vgap = vgap;
        this.hfill = hfill;
        this.vfill = vfill;
    }

    @Override
    public int getHgap() {
        return this.hgap;
    }

    @Override
    public void setHgap(int hgap) {
        super.setHgap(hgap);
        this.hgap = hgap;
    }

    @Override
    public int getVgap() {
        return this.vgap;
    }

    @Override
    public void setVgap(int vgap) {
        super.setVgap(vgap);
        this.vgap = vgap;
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        Dimension tarsiz = new Dimension(0, 0);
        int i = 0;
        while (i < target.getComponentCount()) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getPreferredSize();
                tarsiz.width = Math.max(tarsiz.width, d.width);
                if (i > 0) {
                    tarsiz.height += this.vgap;
                }
                tarsiz.height += d.height;
            }
            ++i;
        }
        Insets insets = target.getInsets();
        tarsiz.width += insets.left + insets.right + this.hgap * 2;
        tarsiz.height += insets.top + insets.bottom + this.vgap * 2;
        return tarsiz;
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        Dimension tarsiz = new Dimension(0, 0);
        int i = 0;
        while (i < target.getComponentCount()) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getMinimumSize();
                tarsiz.width = Math.max(tarsiz.width, d.width);
                if (i > 0) {
                    tarsiz.height += this.vgap;
                }
                tarsiz.height += d.height;
            }
            ++i;
        }
        Insets insets = target.getInsets();
        tarsiz.width += insets.left + insets.right + this.hgap * 2;
        tarsiz.height += insets.top + insets.bottom + this.vgap * 2;
        return tarsiz;
    }

    public void setVerticalFill(boolean vfill) {
        this.vfill = vfill;
    }

    public boolean getVerticalFill() {
        return this.vfill;
    }

    public void setHorizontalFill(boolean hfill) {
        this.hfill = hfill;
    }

    public boolean getHorizontalFill() {
        return this.hfill;
    }

    private void placethem(Container target, int x, int y, int width, int height, int first, int last) {
        int align = this.getAlignment();
        Insets insets = target.getInsets();
        if (align == 1) {
            y += height / 2;
        }
        if (align == 2) {
            y += height;
        }
        int i = first;
        while (i < last) {
            Component m = target.getComponent(i);
            Dimension md = m.getSize();
            if (m.isVisible()) {
                int px = x + (width - md.width) / 2;
                m.setLocation(px, y);
                y += this.vgap + md.height;
            }
            ++i;
        }
    }

    @Override
    public void layoutContainer(Container target) {
        Insets insets = target.getInsets();
        int maxheight = target.getSize().height - (insets.top + insets.bottom + this.vgap * 2);
        int maxwidth = target.getSize().width - (insets.left + insets.right + this.hgap * 2);
        int numcomp = target.getComponentCount();
        int x = insets.left + this.hgap;
        int y = 0;
        int colw = 0;
        int start = 0;
        int i = 0;
        while (i < numcomp) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getPreferredSize();
                if (this.vfill && i == numcomp - 1) {
                    d.height = Math.max(maxheight - y, m.getPreferredSize().height);
                }
                if (this.hfill) {
                    m.setSize(maxwidth, d.height);
                    d.width = maxwidth;
                } else {
                    m.setSize(d.width, d.height);
                }
                if (y + d.height > maxheight) {
                    this.placethem(target, x, insets.top + this.vgap, colw, maxheight - y, start, i);
                    y = d.height;
                    x += this.hgap + colw;
                    colw = d.width;
                    start = i;
                } else {
                    if (y > 0) {
                        y += this.vgap;
                    }
                    y += d.height;
                    colw = Math.max(colw, d.width);
                }
            }
            ++i;
        }
        this.placethem(target, x, insets.top + this.vgap, colw, maxheight - y, start, numcomp);
    }
}

