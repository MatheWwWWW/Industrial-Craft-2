/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Matrix3f
 *  com.mojang.math.Matrix4f
 *  com.mojang.math.Quaternion
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.RenderHighlightEvent$Block
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package ic2.core.block.rendering.world;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import ic2.api.blocks.IWrenchable;
import ic2.api.blocks.WrenchHelper;
import ic2.api.util.DirectionList;
import ic2.core.platform.rendering.RenderShapes;
import ic2.core.platform.rendering.misc.IC2RenderTypes;
import ic2.core.utils.math.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WrenchOverlay {
    private static final int CHANGE_FACING_COLOR = -1977136975;
    private static final int REMOVE_BLOCK_COLOR = -1967911903;
    private static final int SPECIAL_ACTION_COLOR = -1967870452;
    public static final WrenchOverlay INSTANCE = new WrenchOverlay();

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public void onHighlightEvent(RenderHighlightEvent.Block event) {
        BlockHitResult result = event.getTarget();
        if (result.m_6662_() == HitResult.Type.MISS) {
            return;
        }
        Minecraft mc = Minecraft.m_91087_();
        BlockPos pos = result.m_82425_();
        BlockState state = mc.f_91073_.m_8055_(pos);
        IWrenchable wrench = IWrenchable.WrenchRegistry.INSTANCE.getWrenchable(state);
        if (wrench == null || !WrenchHelper.hasWrench((Player)mc.f_91074_)) {
            return;
        }
        DirectionList validSides = this.canSetAnyFacing(wrench, state, pos, (Player)mc.f_91074_);
        boolean remove = wrench.canRemoveBlock(state, (Level)mc.f_91073_, pos, (Player)mc.f_91074_);
        int slot = validSides.size() > 0 ? WrenchHelper.getDirectionIndex(result) : (remove ? 1 : 0);
        Direction currentDir = result.m_82434_();
        Direction nextDir = WrenchHelper.getFacingFromIndex(currentDir, slot, (Player)mc.f_91074_);
        if (nextDir == null || validSides.notContains(nextDir)) {
            AABB box = wrench.hasSpecialAction(state, (Level)mc.f_91073_, pos, currentDir, (Player)mc.f_91074_, result.m_82450_().m_82546_(Vec3.m_82528_((Vec3i)pos)));
            if (box != null) {
                PoseStack stack = event.getPoseStack();
                stack.m_85836_();
                Vec3 info = event.getCamera().m_90583_();
                stack.m_85837_(-info.m_7096_(), -info.m_7098_(), -info.m_7094_());
                stack.m_85837_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
                RenderShapes.renderColorCube(DirectionList.ALL, box.m_82400_((double)0.001f), -1967870452, event.getMultiBufferSource().m_6299_(IC2RenderTypes.POS_COLOR_TRANSLUCENT), stack);
                stack.m_85849_();
                return;
            }
            if (!remove) {
                return;
            }
        }
        PoseStack stack = event.getPoseStack();
        stack.m_85836_();
        Vec3 info = event.getCamera().m_90583_();
        stack.m_85837_(-info.m_7096_(), -info.m_7098_(), -info.m_7094_());
        stack.m_85837_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
        float offset = result.m_82434_().m_122421_() == Direction.AxisDirection.POSITIVE ? (float)(1.0 - state.m_60808_((BlockGetter)mc.f_91073_, pos).m_83297_(result.m_82434_().m_122434_())) : (float)state.m_60808_((BlockGetter)mc.f_91073_, pos).m_83288_(result.m_82434_().m_122434_());
        Vec3i dirInfo = result.m_82434_().m_122436_();
        stack.m_85837_((double)(-((float)dirInfo.m_123341_() * offset)), (double)(-((float)dirInfo.m_123342_() * offset)), (double)(-((float)dirInfo.m_123343_() * offset)));
        switch (currentDir) {
            case DOWN: {
                stack.m_85845_(new Quaternion(90.0f, 0.0f, 0.0f, true));
                stack.m_85837_(0.0, 0.0, -1.0);
                break;
            }
            case UP: {
                stack.m_85845_(new Quaternion(270.0f, 0.0f, 0.0f, true));
                stack.m_85837_(0.0, -1.0, 0.0);
                break;
            }
            case SOUTH: {
                break;
            }
            case NORTH: {
                stack.m_85845_(new Quaternion(0.0f, 180.0f, 0.0f, true));
                stack.m_85837_(-1.0, 0.0, -1.0);
                break;
            }
            case EAST: {
                stack.m_85845_(new Quaternion(0.0f, 90.0f, 0.0f, true));
                stack.m_85837_(-1.0, 0.0, 0.0);
                break;
            }
            case WEST: {
                stack.m_85845_(new Quaternion(0.0f, 270.0f, 0.0f, true));
                stack.m_85837_(0.0, 0.0, -1.0);
            }
        }
        MultiBufferSource buffer = event.getMultiBufferSource();
        this.drawFrame(buffer.m_6299_(RenderType.m_110504_()), stack, -16777216);
        this.drawSide(buffer.m_6299_(IC2RenderTypes.POS_COLOR_TRANSLUCENT), stack, nextDir == null || validSides.isEmpty() || validSides.notContains(nextDir) ? -1967911903 : -1977136975, slot);
        stack.m_85849_();
    }

    private DirectionList canSetAnyFacing(IWrenchable wrench, BlockState state, BlockPos pos, Player player) {
        DirectionList list = DirectionList.EMPTY;
        for (Direction dir : DirectionList.ALL) {
            if (!wrench.canSetFacing(state, player.f_19853_, pos, player, dir)) continue;
            list = list.add(dir);
        }
        return list;
    }

    private void drawSide(VertexConsumer builder, PoseStack stack, int color, int side) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        float r = ColorUtils.getFloatR(color);
        float g = ColorUtils.getFloatG(color);
        float b = ColorUtils.getFloatB(color);
        float a = ColorUtils.getFloatA(color);
        if ((side & 1) != 0) {
            RenderShapes.renderColorQuad(Direction.SOUTH, 0.2f, 0.2f, 0.0f, 0.8f, 0.8f, 1.01f, color, builder, stack);
        }
        if ((side & 2) != 0) {
            RenderShapes.renderColorQuad(Direction.SOUTH, 0.2f, 0.0f, 0.0f, 0.8f, 0.2f, 1.01f, color, builder, stack);
            builder.m_85982_(matrix, 0.0f, 0.0f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.2f, 0.0f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.2f, 0.2f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 1.0f, 0.0f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.8f, 0.2f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.8f, 0.0f, 1.01f).m_85950_(r, g, b, a).m_5752_();
        }
        if ((side & 4) != 0) {
            RenderShapes.renderColorQuad(Direction.SOUTH, 0.2f, 0.8f, 0.0f, 0.8f, 1.0f, 1.01f, color, builder, stack);
            builder.m_85982_(matrix, 0.0f, 1.0f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.2f, 0.8f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.2f, 1.0f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 1.0f, 1.0f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.8f, 1.0f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.8f, 0.8f, 1.01f).m_85950_(r, g, b, a).m_5752_();
        }
        if ((side & 8) != 0) {
            RenderShapes.renderColorQuad(Direction.SOUTH, 0.0f, 0.2f, 0.0f, 0.2f, 0.8f, 1.01f, color, builder, stack);
            builder.m_85982_(matrix, 0.0f, 0.8f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.2f, 0.8f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.0f, 1.0f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.0f, 0.0f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.2f, 0.2f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.0f, 0.2f, 1.01f).m_85950_(r, g, b, a).m_5752_();
        }
        if ((side & 0x10) != 0) {
            RenderShapes.renderColorQuad(Direction.SOUTH, 0.8f, 0.2f, 0.0f, 1.0f, 0.8f, 1.01f, color, builder, stack);
            builder.m_85982_(matrix, 0.8f, 0.8f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 1.0f, 0.8f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 1.0f, 1.0f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 1.0f, 0.0f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 1.0f, 0.2f, 1.01f).m_85950_(r, g, b, a).m_5752_();
            builder.m_85982_(matrix, 0.8f, 0.2f, 1.01f).m_85950_(r, g, b, a).m_5752_();
        }
    }

    private void drawFrame(VertexConsumer builder, PoseStack pose, int color) {
        Matrix4f matrix = pose.m_85850_().m_85861_();
        Matrix3f normal = pose.m_85850_().m_85864_();
        float r = ColorUtils.getFloatR(color);
        float g = ColorUtils.getFloatG(color);
        float b = ColorUtils.getFloatB(color);
        float a = ColorUtils.getFloatA(color);
        builder.m_85982_(matrix, 0.0f, 0.0f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.0f, 1.0f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 1.0f, 1.0f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.0f, 1.0f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 1.0f, 0.0f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.0f, 0.0f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 1.0f, 0.0f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 1.0f, 1.0f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.2f, 0.2f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.2f, 0.8f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.8f, 0.8f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.2f, 0.8f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.8f, 0.2f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.2f, 0.2f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.8f, 0.2f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.8f, 0.8f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.0f, 0.0f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.2f, 0.2f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.0f, 1.0f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.2f, 0.8f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 1.0f, 1.0f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.8f, 0.8f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 1.0f, 0.0f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
        builder.m_85982_(matrix, 0.8f, 0.2f, 1.01f).m_85950_(r, g, b, a).m_85977_(normal, 0.0f, 0.0f, 1.0f).m_5752_();
    }
}

