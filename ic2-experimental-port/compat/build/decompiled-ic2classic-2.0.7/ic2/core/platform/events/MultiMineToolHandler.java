/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntIterator
 *  it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.MultiPlayerGameMode
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.border.WorldBorder
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.RenderHighlightEvent$Block
 *  net.minecraftforge.common.ForgeMod
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$PlayerTickEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$BreakSpeed
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package ic2.core.platform.events;

import com.mojang.blaze3d.vertex.VertexConsumer;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.features.IMultiTargetTool;
import ic2.core.platform.corehacks.mixins.client.PlayerControllerMixin;
import ic2.core.platform.rendering.RenderShapes;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.IterableWrapper;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MultiMineToolHandler {
    public static final MultiMineToolHandler INSTANCE = new MultiMineToolHandler();
    Map<UUID, Map.Entry<BlockPos, Direction>> positionMap = CollectionUtils.createMap();
    IntSet positions = new IntLinkedOpenHashSet();
    IntSet lastPositions = new IntLinkedOpenHashSet();

    @SubscribeEvent
    public void onBlockBreakSpeed(PlayerEvent.BreakSpeed event) {
        Item item;
        Player player = event.getEntity();
        ItemStack stack = player.m_21205_();
        Optional position = event.getPosition();
        if (position.isPresent() && (item = stack.m_41720_()) instanceof IMultiTargetTool) {
            IMultiTargetTool tool = (IMultiTargetTool)item;
            if (!tool.isMultiMining(stack) || !tool.canMultiMine(stack)) {
                return;
            }
            HitResult result = IC2Item.rayTrace(player.f_19853_, (LivingEntity)player, false, player.m_21051_((Attribute)ForgeMod.REACH_DISTANCE.get()).m_22135_());
            if (result instanceof BlockHitResult) {
                BlockHitResult blockHit = (BlockHitResult)result;
                float totalHardness = 0.0f;
                int found = 0;
                for (BlockPos pos : IterableWrapper.wrap(tool.getHitPositions(stack, player, (BlockPos)position.get(), blockHit.m_82434_()))) {
                    BlockState state = player.f_19853_.m_8055_(pos);
                    if (state.m_60795_() || state.m_60767_().m_76332_() || !state.canHarvestBlock((BlockGetter)player.f_19853_, pos, player) || state.m_60800_((BlockGetter)player.f_19853_, pos) == -1.0f) continue;
                    ++found;
                    totalHardness += Math.max(1.0f, state.m_60800_((BlockGetter)player.f_19853_, pos));
                }
                if (found > 0) {
                    event.setNewSpeed(event.getNewSpeed() / totalHardness * Math.max(1.0f, event.getState().m_60800_((BlockGetter)player.f_19853_, (BlockPos)position.get())));
                }
            }
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getSide().isServer()) {
            return;
        }
        this.positionMap.put(event.getEntity().m_20148_(), new AbstractMap.SimpleEntry<BlockPos, Direction>(event.getPos(), event.getFace()));
    }

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public void onDestroyProgress(TickEvent.PlayerTickEvent event) {
        if (event.side.isServer() || event.phase == TickEvent.Phase.START) {
            return;
        }
        MultiPlayerGameMode controller = Minecraft.m_91087_().f_91072_;
        LevelRenderer renderer = Minecraft.m_91087_().f_91060_;
        if (!controller.m_105296_()) {
            this.positionMap.remove(event.player.m_20148_());
            if (this.positions.size() > 0) {
                IntIterator intIterator = this.positions.iterator();
                while (intIterator.hasNext()) {
                    int id = (Integer)intIterator.next();
                    renderer.m_109774_(id, BlockPos.f_121853_, -1);
                }
                this.positions.clear();
            }
            return;
        }
        Map.Entry<BlockPos, Direction> entry = this.positionMap.get(event.player.m_20148_());
        if (entry == null) {
            return;
        }
        ItemStack stack = event.player.m_21205_();
        if (stack.m_41720_() instanceof IMultiTargetTool) {
            IMultiTargetTool target = (IMultiTargetTool)stack.m_41720_();
            if (!target.isMultiMining(stack) || !target.canMultiMine(stack)) {
                return;
            }
            this.lastPositions.addAll((IntCollection)this.positions);
            this.positions.clear();
            int progress = (int)(((PlayerControllerMixin)controller).getDestroyProgress() * 10.0f) - 1;
            for (BlockPos pos : IterableWrapper.wrap(target.getHitPositions(stack, event.player, entry.getKey(), entry.getValue()))) {
                int id = Math.abs(pos.equals((Object)entry.getKey()) ? event.player.m_19879_() : pos.hashCode());
                renderer.m_109774_(id, pos.m_7949_(), progress);
                this.positions.add(id);
            }
            this.lastPositions.removeAll((IntCollection)this.positions);
            if (this.lastPositions.size() > 0) {
                IntIterator intIterator = this.lastPositions.iterator();
                while (intIterator.hasNext()) {
                    int id = (Integer)intIterator.next();
                    renderer.m_109774_(id, BlockPos.f_121853_, -1);
                }
                this.lastPositions.clear();
            }
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public void onBlockHighlightEvent(RenderHighlightEvent.Block event) {
        LocalPlayer player = Minecraft.m_91087_().f_91074_;
        if (player == null) {
            return;
        }
        ItemStack stack = player.m_21205_();
        Item item = stack.m_41720_();
        if (item instanceof IMultiTargetTool) {
            IMultiTargetTool target = (IMultiTargetTool)item;
            if (!target.isMultiMining(stack) || !target.canMultiMine(stack)) {
                return;
            }
            WorldBorder border = player.f_19853_.m_6857_();
            Vec3 view = event.getCamera().m_90583_();
            BlockHitResult result = event.getTarget();
            VertexConsumer builder = event.getMultiBufferSource().m_6299_(RenderType.m_110504_());
            int count = 0;
            for (BlockPos pos : IterableWrapper.wrap(target.getHitPositions(stack, (Player)player, result.m_82425_(), result.m_82434_()))) {
                BlockState state = player.f_19853_.m_8055_(pos);
                if (state.m_60795_() || state.m_60800_((BlockGetter)player.f_19853_, pos) == -1.0f || !border.m_61937_(pos)) continue;
                RenderShapes.drawSelectionBox(event.getPoseStack(), builder, (Entity)player, view.m_7096_(), view.m_7098_(), view.m_7094_(), pos, state);
                ++count;
            }
            if (count > 0) {
                event.setCanceled(true);
            }
        }
    }
}

