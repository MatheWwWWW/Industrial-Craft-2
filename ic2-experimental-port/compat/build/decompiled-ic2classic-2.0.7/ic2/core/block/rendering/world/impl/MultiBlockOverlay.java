/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Key
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.client.event.InputEvent
 *  net.minecraftforge.client.event.InputEvent$Key
 *  net.minecraftforge.client.event.RenderLevelStageEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package ic2.core.block.rendering.world.impl;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ic2.core.IC2;
import ic2.core.block.rendering.world.IWorldOverlay;
import ic2.core.block.rendering.world.impl.MultiBlock;
import ic2.core.platform.player.KeyboardClient;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.platform.rendering.misc.ColoringVertexBuilder;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.geometry.Box;
import ic2.core.utils.tooltips.ILangHelper;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MultiBlockOverlay
implements IWorldOverlay,
ILangHelper {
    public static final MultiBlockOverlay INSTANCE = new MultiBlockOverlay();
    Map<MultiBlock, Renderer> renderer = CollectionUtils.createMap();
    MultiBlock selectedMultiBlock = null;
    BlockPos selectedPos = BlockPos.f_121853_;
    Rotation selectedRotation = Rotation.NONE;
    public static final Supplier<MultiBlock> TEST_BLOCK = () -> {
        MultiBlock block = new MultiBlock();
        Box bounding = Box.fromPos(new BlockPos(-2, 0, -2), new BlockPos(2, 4, 2));
        for (BlockPos pos : bounding.getHollowIterator()) {
            if (bounding.isEdge(pos) || bounding.getBorderSide(pos) == Direction.DOWN) {
                block.addBlock(Blocks.f_50075_, pos);
                continue;
            }
            block.addBlock(Blocks.f_50058_, pos);
        }
        return block.addBlock(IC2Blocks.TANK, 2, 2, 0);
    };

    public void addMultiBlock(MultiBlock block, BlockPos pos) {
        this.addMultiBlock(block, pos, Rotation.NONE);
    }

    public void addMultiBlock(MultiBlock block, BlockPos pos, Rotation rotation) {
        this.addMultiBlock(block, () -> pos, () -> rotation);
    }

    public void addMultiBlock(MultiBlock block, Supplier<BlockPos> pos) {
        this.addMultiBlock(block, pos, () -> Rotation.NONE);
    }

    public void addMultiBlock(MultiBlock block, Supplier<BlockPos> pos, Supplier<Rotation> rotation) {
        this.renderer.put(block, new Renderer(block, pos, rotation));
    }

    public void removeMultiBlock(MultiBlock block) {
        this.renderer.remove(block);
    }

    public void setSelectedMultiBlock(MultiBlock multiBlock) {
        if (this.selectedMultiBlock != null) {
            return;
        }
        this.renderer.remove(multiBlock);
        this.renderer.put(multiBlock, new Renderer(multiBlock, this::selectedPos, this::selectedRotation));
        this.selectedMultiBlock = multiBlock;
    }

    public boolean hasSelectedMultiBlock() {
        return this.selectedMultiBlock != null;
    }

    public void clearSelectedMultiBlock() {
        if (this.selectedMultiBlock == null) {
            return;
        }
        this.renderer.remove(this.selectedMultiBlock);
        this.selectedMultiBlock = null;
        this.selectedRotation = Rotation.NONE;
        this.selectedPos = BlockPos.f_121853_;
    }

    public void onRenderUI(PoseStack stack) {
        if (!this.hasSelectedMultiBlock()) {
            return;
        }
        Minecraft mc = Minecraft.m_91087_();
        Font font = mc.f_91062_;
        font.m_92889_(stack, (Component)this.buildKeyDescription(this.combineKeys(mc.f_91066_.f_92096_), "remove"), 2.0f, 2.0f, -1);
        font.m_92889_(stack, (Component)this.buildKeyDescription(this.combineKeys(mc.f_91066_.f_92095_), "place"), 2.0f, 11.0f, -1);
        font.m_92889_(stack, (Component)this.buildKeyDescription(this.combineKeys(((KeyboardClient)IC2.KEYBOARD).expandInfoKey, mc.f_91066_.f_92097_), "pick existing structure"), 2.0f, 20.0f, -1);
        font.m_92889_(stack, (Component)this.buildKeyDescription(InputConstants.Type.KEYSYM.m_84895_(82).m_84875_().m_6881_().m_130940_(ChatFormatting.GOLD), "rotate"), 2.0f, 29.0f, -1);
    }

    @SubscribeEvent
    public void onKeyPressed(InputEvent event) {
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91073_ != null && mc.f_91080_ == null) {
            MultiBlock block;
            if (this.hasSelectedMultiBlock()) {
                InputEvent.Key key;
                if (mc.f_91066_.f_92095_.m_90859_()) {
                    mc.f_91066_.f_92095_.m_7249_(false);
                    MultiBlock toPlace = this.selectedMultiBlock;
                    Rotation newRotation = this.selectedRotation;
                    BlockPos pos = this.selectedPos;
                    this.clearSelectedMultiBlock();
                    this.addMultiBlock(toPlace, pos, newRotation);
                } else if (mc.f_91066_.f_92096_.m_90859_()) {
                    mc.f_91066_.f_92096_.m_7249_(false);
                    this.clearSelectedMultiBlock();
                } else if (event instanceof InputEvent.Key && (key = (InputEvent.Key)event).getAction() == 1 && key.getKey() == 82) {
                    KeyMapping.m_90837_((InputConstants.Key)InputConstants.Type.KEYSYM.m_84895_(82), (boolean)false);
                    this.selectedRotation = this.selectedRotation.m_55952_(Rotation.CLOCKWISE_90);
                }
            } else if (PlayerHandler.getClientHandler().hudModeKeyDown && mc.f_91066_.f_92097_.m_90859_() && (block = this.findLookingAt((Player)mc.f_91074_, 100.0)) != null) {
                this.selectedRotation = this.renderer.get((Object)block).rotation.get();
                this.setSelectedMultiBlock(block);
            }
        }
    }

    @Override
    public void cleanup() {
        this.renderer.clear();
        this.clearSelectedMultiBlock();
    }

    @Override
    public void update(Level world, Player player) {
        if (this.selectedMultiBlock != null) {
            this.selectedPos = this.getViewPosition(player, 50.0);
        }
        if (this.renderer.isEmpty()) {
            return;
        }
        if (world.m_46467_() % 20L == 0L) {
            Iterator<Renderer> iter = this.renderer.values().iterator();
            while (iter.hasNext()) {
                if (!iter.next().isFullyBuild(world)) continue;
                iter.remove();
            }
        }
    }

    @Override
    public void render(Level world, Player player, RenderLevelStageEvent event, Frustum helper) {
        if (this.renderer.isEmpty()) {
            return;
        }
        boolean formed = !IC2.KEYBOARD.isHudModeKeyDown(player);
        RenderSystem.m_69472_();
        RenderSystem.m_69465_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderUtils.draw(event.getPoseStack(), (B, M) -> {
            MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_((BufferBuilder)B);
            ColoringVertexBuilder builder = new ColoringVertexBuilder(buffer.m_6299_(RenderType.m_110466_()), 128);
            for (Renderer render : this.renderer.values()) {
                render.render((PoseStack)M, builder, world, formed);
            }
            buffer.m_109911_();
        });
        RenderSystem.m_69493_();
        RenderSystem.m_69482_();
    }

    private BlockPos getViewPosition(Player player, double range) {
        HitResult result = player.m_19907_(range, 1.0f, false);
        if (!(result instanceof BlockHitResult) || result.m_6662_() == HitResult.Type.MISS) {
            Vec3 lookingDir = player.m_20252_(1.0f);
            BlockPos.MutableBlockPos end = new BlockPos.MutableBlockPos().m_122190_((Vec3i)new BlockPos(player.m_20299_(1.0f).m_82520_(lookingDir.m_7096_() * range, lookingDir.m_7098_() * range, lookingDir.m_7094_() * range)));
            while (this.isEmptyBlock(player.f_19853_, (BlockPos)end) && end.m_123342_() > 0) {
                end.m_122173_(Direction.DOWN);
            }
            return end.m_122173_(Direction.UP).m_7949_();
        }
        BlockPos.MutableBlockPos end = new BlockPos.MutableBlockPos().m_122190_((Vec3i)((BlockHitResult)result).m_82425_()).m_122173_(Direction.UP);
        while (this.isEmptyBlock(player.f_19853_, (BlockPos)end) && end.m_123342_() > 0) {
            end.m_122173_(Direction.DOWN);
        }
        return end.m_122173_(Direction.UP).m_7949_();
    }

    private boolean isEmptyBlock(Level world, BlockPos pos) {
        BlockState state = world.m_8055_(pos);
        return state.m_60795_() || !state.m_60767_().m_76334_();
    }

    private MultiBlock findLookingAt(Player player, double range) {
        if (this.renderer.isEmpty()) {
            return null;
        }
        Vec3 lookingDir = player.m_20252_(1.0f);
        Vec3 start = player.m_20299_(1.0f);
        Vec3 end = start.m_82520_(lookingDir.m_7096_() * range, lookingDir.m_7098_() * range, lookingDir.m_7094_() * range);
        MultiBlock current = null;
        double distance = Double.MAX_VALUE;
        for (Renderer block : this.renderer.values()) {
            double d1;
            Optional clip = block.getBox().m_82371_(start, end);
            if (!clip.isPresent() || !((d1 = start.m_82557_((Vec3)clip.get())) < distance)) continue;
            current = block.block;
            distance = d1;
        }
        return current;
    }

    private BlockPos selectedPos() {
        return this.selectedPos;
    }

    private Rotation selectedRotation() {
        return this.selectedRotation;
    }

    private static class Renderer {
        MultiBlock block;
        Supplier<BlockPos> position;
        Supplier<Rotation> rotation;

        public Renderer(MultiBlock block, Supplier<BlockPos> position, Supplier<Rotation> rotation) {
            this.block = block;
            this.position = position;
            this.rotation = rotation;
        }

        public AABB getBox() {
            return this.block.getBounds().m_82338_(this.position.get());
        }

        public boolean isFullyBuild(Level world) {
            return this.block.isFullyBuild(world, this.position.get());
        }

        public void render(PoseStack stack, VertexConsumer builder, Level world, boolean formed) {
            BlockPos pos = this.position.get();
            stack.m_85836_();
            stack.m_85837_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
            this.block.renderMultiBlock(stack, builder, world, pos, this.rotation.get(), formed);
            stack.m_85849_();
        }
    }
}

