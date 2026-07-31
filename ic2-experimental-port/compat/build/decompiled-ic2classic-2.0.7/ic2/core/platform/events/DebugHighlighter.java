/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.events;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.misc.GLUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DebugHighlighter {
    public static final DebugHighlighter INSTANCE = new DebugHighlighter();
    List<Component> toRender = new ObjectArrayList();

    public void storeDebug(List<Component> s, CompoundTag nbt) {
        this.toRender.clear();
        this.toRender.addAll(s);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void onToolTip(PoseStack pose) {
        Minecraft mc = Minecraft.m_91087_();
        LocalPlayer player = mc.f_91074_;
        if (player == null) {
            return;
        }
        boolean found = false;
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = player.m_150109_().m_8020_(i);
            if (stack.m_41720_() != IC2Items.DEBUG_ITEM || !StackUtil.getNbtData(stack).m_128471_("active")) continue;
            found = true;
            break;
        }
        if (!found) {
            return;
        }
        pose.m_85836_();
        pose.m_85841_(0.5f, 0.5f, 1.0f);
        GLUtils.drawTooltip(pose, this.toRender, -5, 0);
        pose.m_85849_();
    }
}

