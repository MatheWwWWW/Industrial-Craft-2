/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.wiki.components.builders;

import ic2.core.block.rendering.world.impl.MultiBlock;
import ic2.core.wiki.base.IPageBuilder;
import ic2.core.wiki.components.MultiBlockComponent;
import ic2.core.wiki.components.builders.IWikiObj;

public class MultiBlockObj
implements IWikiObj {
    MultiBlock block;
    boolean disablePlacement;

    public MultiBlockObj(MultiBlock block) {
        this.block = block;
    }

    public MultiBlockObj setNoPlace() {
        this.disablePlacement = true;
        return this;
    }

    @Override
    public void buildComponents(IPageBuilder builder) {
        builder.addComponent(new MultiBlockComponent(this.block, this.disablePlacement));
    }
}

