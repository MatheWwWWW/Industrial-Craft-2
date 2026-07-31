/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.wiki.base.managers.IWikiProvider;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public interface IWikiComponent {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/items/wiki/book_components.png");
    public static final int TEXT_COLOR = -12829907;

    public int getHeight();

    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen var1);

    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen var1, int var2, int var3, PoseStack var4, int var5, int var6, float var7);

    @OnlyIn(value=Dist.CLIENT)
    public void renderForeground(IC2Screen var1, int var2, int var3, PoseStack var4, int var5, int var6, float var7);

    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(IC2Screen var1, int var2, int var3, int var4, int var5, IWikiProvider var6);

    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseRelease(IC2Screen var1, int var2, int var3, int var4, int var5);

    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseScroll(IC2Screen var1, int var2, int var3, int var4, int var5, int var6, IWikiProvider var7);

    @OnlyIn(value=Dist.CLIENT)
    public void addToolTips(IC2Screen var1, int var2, int var3, PoseStack var4, int var5, int var6, Consumer<Component> var7);

    @OnlyIn(value=Dist.CLIENT)
    default public ItemStack getHoveredStack(IC2Screen screen, int x, int y, int mouseX, int mouseY) {
        return ItemStack.f_41583_;
    }

    default public boolean isOver(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void addSearchString(Consumer<Component> var1);
}

