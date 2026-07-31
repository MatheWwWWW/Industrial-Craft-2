/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Matrix3f
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.api.tiles.display.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix3f;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.IMonitorRenderer;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemDisplayInfo
implements IDisplayInfo {
    ItemStack item;
    boolean showCount;
    Supplier<ItemStack> itemProvider;
    BooleanSupplier aliveProvider;

    public ItemDisplayInfo(FriendlyByteBuf buffer) {
        this.showCount = buffer.readBoolean();
        this.item = buffer.m_130267_();
        if (this.showCount) {
            this.item.m_41764_(buffer.m_130242_());
        }
    }

    public ItemDisplayInfo(boolean showCount, Supplier<ItemStack> itemProvider, BooleanSupplier aliveProvider) {
        this.showCount = showCount;
        this.itemProvider = itemProvider;
        this.aliveProvider = aliveProvider;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void render(PoseStack stack, int x, int y, int width, int height, IDisplayInfo.Alignment align, IMonitorRenderer helper) {
        stack.m_85836_();
        stack.m_85850_().m_85864_().m_8169_(Matrix3f.m_8174_((float)1.0f, (float)-1.0f, (float)1.0f));
        helper.renderGuiItems(stack, this.item, x, y);
        if (this.showCount) {
            helper.renderGuiItemText(stack, this.item, x, y, null);
        }
        stack.m_85849_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getHeight(int width, IDisplayInfo.Alignment align) {
        return 18;
    }

    @Override
    public boolean isValid() {
        return this.aliveProvider.getAsBoolean();
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        ItemStack stack = this.itemProvider.get();
        buffer.writeBoolean(this.showCount);
        buffer.m_130055_(stack);
        if (this.showCount) {
            buffer.m_130130_(stack.m_41613_());
        }
    }

    @Override
    public Tag getServerData() {
        ItemStack stack = this.itemProvider.get();
        CompoundTag data = stack.m_41739_(new CompoundTag());
        if (this.showCount) {
            data.m_128405_("Count", stack.m_41613_());
        } else {
            data.m_128473_("Count");
        }
        return data;
    }
}

