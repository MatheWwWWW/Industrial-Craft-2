/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.gui.feature;

import ic2.core.inventory.gui.IC2Screen;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ITooltipProvider {
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(IC2Screen var1, int var2, int var3, Consumer<Component> var4);
}

