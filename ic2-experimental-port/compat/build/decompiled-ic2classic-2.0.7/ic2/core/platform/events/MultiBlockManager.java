/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickBlock
 *  net.minecraftforge.event.level.LevelEvent$Unload
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package ic2.core.platform.events;

import ic2.core.block.base.features.multiblock.IMultiBlockClickable;
import ic2.core.platform.events.StructureManager;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class MultiBlockManager {
    public static final MultiBlockManager INSTANCE = new MultiBlockManager();
    Map<LevelAccessor, MultiBlockListeners> listeners = CollectionUtils.createMap();

    public IMultiBlockClickable getMultiBlock(Level world, BlockPos pos) {
        return this.listeners.get(world).getPos(pos);
    }

    @SubscribeEvent
    public void onBlockClick(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity() == null || event.getEntity().m_6144_()) {
            return;
        }
        Level world = event.getLevel();
        BlockPos pos = event.getPos();
        IMultiBlockClickable listener = this.getListeners((LevelAccessor)world).getPos(pos);
        if (listener != null && listener.getRequiredActions().canDoRightClick()) {
            BlockState state = world.m_8055_(pos);
            if (listener.shouldLinkToBlock(pos, state)) {
                if (world.m_8055_(listener.getOrigin()).m_60664_(world, event.getEntity(), event.getHand(), new BlockHitResult(Vec3.m_82528_((Vec3i)listener.getOrigin()).m_82520_(0.5, 0.5, 0.5), event.getFace(), listener.getOrigin(), false)) != InteractionResult.PASS) {
                    event.setCanceled(true);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                }
            } else if (listener.onRightClick(event.getEntity(), event.getHand(), event.getFace(), new BlockHitResult(Vec3.m_82512_((Vec3i)event.getPos()), event.getFace(), event.getPos(), false))) {
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }

    @SubscribeEvent
    public void onBlockLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        BlockState state;
        if (event.getEntity() == null) {
            return;
        }
        Level world = event.getLevel();
        BlockPos pos = event.getPos();
        IMultiBlockClickable listener = this.getListeners((LevelAccessor)world).getPos(pos);
        if (listener != null && listener.getRequiredActions().canDoLeftClick() && listener.shouldAbsorbClick(pos, state = world.m_8055_(pos))) {
            if (listener.shouldLinkToBlock(pos, state)) {
                world.m_8055_(listener.getOrigin()).m_60686_(world, listener.getOrigin(), event.getEntity());
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            } else if (listener.onLeftClick(event.getEntity(), pos)) {
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }

    @SubscribeEvent
    public void onWorldUnload(LevelEvent.Unload event) {
        MultiBlockListeners listener = this.listeners.remove(event.getLevel());
        if (listener != null) {
            listener.clear();
        }
    }

    public void onServerStopped() {
        this.listeners.clear();
        StructureManager.INSTANCE.onServerStopped();
    }

    public void addListeners(Level world, BlockPos pos, long[] listeners, IMultiBlockClickable listener) {
        if (listeners == null || listeners.length <= 0 || listener == null) {
            return;
        }
        this.getListeners((LevelAccessor)world).addListeners(pos, listeners, listener);
    }

    public void removeListeners(Level world, BlockPos positions) {
        this.getListeners((LevelAccessor)world).removeListeners(positions);
    }

    private MultiBlockListeners getListeners(LevelAccessor world) {
        MultiBlockListeners listener = this.listeners.get(world);
        if (listener == null) {
            listener = new MultiBlockListeners();
            this.listeners.put(world, listener);
        }
        return listener;
    }

    public static class MultiBlockListeners {
        Long2ObjectMap<IMultiBlockClickable> listeners = new Long2ObjectOpenHashMap();
        Long2ObjectMap<long[]> mappedPositions = new Long2ObjectOpenHashMap();

        public void addListeners(BlockPos pos, long[] positions, IMultiBlockClickable listener) {
            this.mappedPositions.put(pos.m_121878_(), (Object)positions);
            int m = positions.length;
            for (int i = 0; i < m; ++i) {
                this.listeners.put(positions[i], (Object)listener);
            }
        }

        public void removeListeners(BlockPos pos) {
            long[] mapped = (long[])this.mappedPositions.remove(pos.m_121878_());
            if (mapped != null) {
                int m = mapped.length;
                for (int i = 0; i < m; ++i) {
                    this.listeners.remove(mapped[i]);
                }
            }
        }

        public void clear() {
            this.listeners.clear();
            this.mappedPositions.clear();
            this.listeners = null;
            this.mappedPositions = null;
        }

        public IMultiBlockClickable getPos(BlockPos pos) {
            return (IMultiBlockClickable)this.listeners.get(pos.m_121878_());
        }
    }
}

