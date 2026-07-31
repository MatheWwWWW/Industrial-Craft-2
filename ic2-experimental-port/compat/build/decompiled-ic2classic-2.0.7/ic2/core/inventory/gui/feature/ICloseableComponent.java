/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.gui.feature;

import ic2.core.inventory.gui.IC2Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICloseableComponent {
    @OnlyIn(value=Dist.CLIENT)
    public void closeComponent(IC2Screen var1);
}

