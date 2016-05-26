/*
 * Decompiled with CFR 0_114.
 */
package algorithm;

import java.util.Iterator;

import algorithm.AbstractAlgorithm;
import algorithm.Aores;
import core.IBinaryContext;
import core.IMySet;
import core.MyConcept;
import core.MyConceptSet;
import core.MyGSH;
import core.MySetWrapper;
import util.Chrono;

public class Ares
extends AbstractAlgorithm {
    private IBinaryContext matrix;
    private MyGSH gsh;
    private Chrono chrono = null;

    public Ares(IBinaryContext matrix, Chrono chrono) {
        this.matrix = matrix;
        this.chrono = chrono;
    }

    public Ares(IBinaryContext matrix) {
        this(matrix, null);
    }

    private void aresLimitCase(IMySet missingAttributes) throws Exception {
        MyConcept bottom = this.gsh.getBottomTentative();
        if (bottom != null && bottom.getExtent().isEmpty()) {
            bottom.getIntent().addAll(missingAttributes);
            bottom.getReducedIntent().addAll(missingAttributes);
        } else {
            bottom = new MyConcept(new MySetWrapper(0), (IMySet)missingAttributes.clone(), new MySetWrapper(0), missingAttributes);
            MyConceptSet bottomConcepts = new MyConceptSet();
            Iterator<MyConcept> i = (Iterator<MyConcept>) this.gsh.getBasicIterator();
            while (i.hasNext()) {
                MyConcept concept = i.next();
                if (!concept.getLowerCover().isEmpty()) continue;
                bottomConcepts.add(concept);
            }
            i = bottomConcepts.iterator();
            while (i.hasNext()) {
                bottom.getIntent().addAll(((MyConcept)i.next()).getIntent());
            }
            this.gsh.addConcept(bottom);
            i = bottomConcepts.iterator();
            while (i.hasNext()) {
                this.gsh.addPrecedenceConnection(bottom, i.next());
            }
        }
    }

    public MyGSH computeGSH() throws Exception {
        this.gsh = new MyGSH(this.matrix.getObjectNumber(), MyGSH.EXTENT_LEVEL_INDEX);
        if (this.chrono != null) {
            this.chrono.start("concept/order");
        }
        MySetWrapper missingAttributes = new MySetWrapper(this.matrix.getAttributeCount());
        missingAttributes.fill(this.matrix.getAttributeCount());
        int i = 0;
        while (i < this.matrix.getObjectNumber()) {
            IMySet fA = this.matrix.getIntent(i);
            missingAttributes.clearAll(fA);
            Aores aores = new Aores(this.gsh, i, fA);
            this.gsh = aores.updateGSH();
            ++i;
        }
        if (!missingAttributes.isEmpty()) {
            this.aresLimitCase(missingAttributes);
        }
        if (this.chrono != null) {
            this.chrono.stop("concept/order");
        }
        this.gsh.setNameId("Ares");
        this.gsh.isReduicedlyLabelled(true);
        return this.gsh;
    }

    @Override
    public void exec() {
        try {
            this.result = this.computeGSH();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Ares";
    }
}

