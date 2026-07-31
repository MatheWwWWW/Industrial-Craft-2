/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.api.tiles.display;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.tiles.display.IDisplayRegistry;
import ic2.api.tiles.display.IMonitorRenderer;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IDisplayInfo {
    public static final IDisplayRegistry REGISTRY = new IDisplayRegistry.DelegateRegistry();

    @OnlyIn(value=Dist.CLIENT)
    public void render(PoseStack var1, int var2, int var3, int var4, int var5, Alignment var6, IMonitorRenderer var7);

    @OnlyIn(value=Dist.CLIENT)
    public int getHeight(int var1, Alignment var2);

    public boolean isValid();

    public void serialize(FriendlyByteBuf var1);

    public Tag getServerData();

    public static enum Alignment {
        LEFT,
        CENTER,
        RIGHT;


        public int getXOffset(int width) {
            return this == CENTER ? width >> 1 : (this == RIGHT ? width : 0);
        }
    }
}

