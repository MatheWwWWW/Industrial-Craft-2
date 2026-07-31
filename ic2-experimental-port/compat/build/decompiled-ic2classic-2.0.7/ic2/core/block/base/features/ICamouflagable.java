/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.core.block.base.features;

import ic2.api.events.RetextureEvent;
import ic2.core.block.rendering.camouflage.CamouflageStorage;
import net.minecraft.core.Direction;

public interface ICamouflagable {
    public CamouflageStorage getStorage();

    public boolean applyTexture(Direction var1, RetextureEvent.TextureContainer var2);

    public boolean isSideEnabled(Direction var1);

    public boolean removeCamouflage();
}

