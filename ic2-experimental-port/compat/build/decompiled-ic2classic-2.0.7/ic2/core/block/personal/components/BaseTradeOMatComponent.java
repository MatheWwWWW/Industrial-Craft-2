/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.personal.components;

import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class BaseTradeOMatComponent
extends GuiWidget {
    public BaseTradeOMatComponent(Box2i box) {
        super(box);
    }

    @OnlyIn(value=Dist.CLIENT)
    public abstract void onChanged();
}

