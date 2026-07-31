/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.client.event.RenderLevelStageEvent
 */
package ic2.core.block.rendering.world.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import ic2.core.block.rendering.world.IWorldOverlay;
import ic2.core.item.tool.EUReaderTool;
import ic2.core.platform.rendering.RenderShapes;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.platform.rendering.misc.GLUtils;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.event.RenderLevelStageEvent;

public class EuReaderOverlay
implements IWorldOverlay {
    public static final int EU_READER_COLOR = -2130720768;

    @Override
    public void cleanup() {
    }

    @Override
    public void update(Level world, Player player) {
    }

    @Override
    public void render(Level world, Player player, RenderLevelStageEvent event, Frustum helper) {
        ItemStack stack = player.m_21205_();
        if (!(stack.m_41720_() instanceof EUReaderTool) && !((stack = player.m_21206_()).m_41720_() instanceof EUReaderTool)) {
            return;
        }
        CompoundTag nbt = StackUtil.getNbtData(stack);
        String dim = nbt.m_128461_("dim");
        BlockPos pos = BlockPos.m_122022_((long)nbt.m_128454_("pos"));
        if (!dim.equals(world.m_46472_().m_135782_().toString()) || pos.m_123331_((Vec3i)player.m_20183_()) > 6400.0) {
            return;
        }
        GLUtils.enableHighlight(true);
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        RenderUtils.draw(event.getPoseStack(), (B, M) -> {
            B.m_166779_(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.f_85815_);
            RenderShapes.renderColorCube(new AABB(pos).m_82400_((double)0.1f), -2130720768, (VertexConsumer)B, M);
        }).m_85914_();
        GLUtils.disableHighlight(true);
    }
}

