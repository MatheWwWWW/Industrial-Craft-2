/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMaps
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.level.saveddata.SavedData
 */
package ic2.core.block.misc;

import ic2.core.IC2;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.features.block.IEmptyBlockModel;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.plugins.IRegistryProvider;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.function.BooleanSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.saveddata.SavedData;

public class LightBlock
extends AirBlock
implements IEmptyBlockModel,
IRegistryProvider {
    public static final BooleanProperty EXTRA = BooleanProperty.m_61465_((String)"extra");
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;

    public LightBlock() {
        super(BlockBehaviour.Properties.m_60939_((Material)Material.f_76296_).m_60910_().m_60996_().m_222994_().m_60953_(T -> 15).m_60977_());
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)EXTRA, (Comparable)Boolean.valueOf(false))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation("ic2", "light_block");
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{EXTRA}).m_61104_(new Property[]{WATER});
    }

    public RenderShape m_7514_(BlockState state) {
        return (Boolean)state.m_61143_((Property)WATER) != false ? RenderShape.MODEL : RenderShape.INVISIBLE;
    }

    public FluidState m_5888_(BlockState state) {
        return (Boolean)state.m_61143_((Property)WATER) != false ? Fluids.f_76193_.m_76145_() : Fluids.f_76191_.m_76145_();
    }

    public void m_6807_(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        LightBlock.scheduleTick(worldIn, pos, (Block)this);
    }

    public static boolean placeLightBlock(Level world, BlockPos pos, int tickRate, BooleanSupplier canPlace) {
        if (!world.f_46443_ && world.m_46467_() % (long)tickRate == 0L && (canPlace == null || canPlace.getAsBoolean())) {
            BlockState state = world.m_8055_(pos);
            if (state.m_60795_() && state.m_60734_() != IC2Blocks.LIGHT_SOURCE) {
                return world.m_46597_(pos, IC2Blocks.LIGHT_SOURCE.m_49966_());
            }
            if (state.m_60713_(IC2Blocks.LIGHT_SOURCE) && !((Boolean)state.m_61143_((Property)EXTRA)).booleanValue()) {
                return world.m_46597_(pos, (BlockState)state.m_61124_((Property)EXTRA, (Comparable)Boolean.valueOf(true)));
            }
            if (state.m_60713_(Blocks.f_49990_) && state.m_60819_().m_192917_((Fluid)Fluids.f_76193_)) {
                return world.m_46597_(pos, (BlockState)IC2Blocks.LIGHT_SOURCE.m_49966_().m_61124_((Property)WATER, (Comparable)Boolean.valueOf(true)));
            }
        }
        return false;
    }

    public boolean m_7923_(BlockState state) {
        return (Boolean)state.m_61143_((Property)EXTRA) == false;
    }

    public static void scheduleTick(Level world, BlockPos pos, Block block) {
        if (world.f_46443_) {
            return;
        }
        if (WorldData.addTick((ServerLevel)world, pos)) {
            IC2.TICK_HANDLER.addWorldCallback(world, T -> LightBlock.onTick(T, pos, block), 10);
        }
    }

    public static void onChunkLoaded(ServerLevel world, ChunkPos pos) {
        LongSet list = WorldData.onChunkLoaded(world, pos);
        if (list == null) {
            return;
        }
        LongIterator iter = list.iterator();
        while (iter.hasNext()) {
            LightBlock.scheduleTick((Level)world, BlockPos.m_122022_((long)iter.nextLong()), IC2Blocks.LIGHT_SOURCE);
        }
    }

    public static int onTick(Level world, BlockPos pos, Block block) {
        if (!world.m_46749_(pos) || !(world instanceof ServerLevel)) {
            return -1;
        }
        BlockState state = world.m_8055_(pos);
        if (state.m_60734_() != block) {
            return -1;
        }
        if (((Boolean)state.m_61143_((Property)EXTRA)).booleanValue()) {
            world.m_7731_(pos, (BlockState)state.m_61124_((Property)EXTRA, (Comparable)Boolean.valueOf(false)), 50);
            return 10;
        }
        world.m_7731_(pos, ((Boolean)state.m_61143_((Property)WATER) != false ? Blocks.f_49990_ : Blocks.f_50016_).m_49966_(), 50);
        WorldData.onUnloaded((ServerLevel)world, pos);
        return -1;
    }

    private static class WorldData
    extends SavedData {
        Long2ObjectMap<LongSet> blocksToTick = new Long2ObjectLinkedOpenHashMap();

        public WorldData() {
        }

        public WorldData(CompoundTag compound) {
            for (CompoundTag data : NBTListWrapper.wrap(compound.m_128437_("lights", 10), CompoundTag.class)) {
                this.blocksToTick.put(data.m_128454_("chunk"), (Object)new LongLinkedOpenHashSet(data.m_128467_("pos")));
            }
        }

        public static WorldData get(ServerLevel world) {
            return (WorldData)world.m_8895_().m_164861_(WorldData::new, WorldData::new, "light_storage");
        }

        public static boolean addTick(ServerLevel world, BlockPos pos) {
            return WorldData.get(world).add(pos);
        }

        public static void onUnloaded(ServerLevel world, BlockPos pos) {
            WorldData.get(world).remove(pos);
        }

        public static LongSet onChunkLoaded(ServerLevel world, ChunkPos pos) {
            return WorldData.get(world).get(pos);
        }

        private boolean add(BlockPos pos) {
            this.m_77762_();
            return ((LongSet)this.blocksToTick.computeIfAbsent(ChunkPos.m_45589_((int)(pos.m_123341_() >> 4), (int)(pos.m_123343_() >> 4)), T -> new LongLinkedOpenHashSet())).add(pos.m_121878_());
        }

        private void remove(BlockPos pos) {
            long chunk = ChunkPos.m_45589_((int)(pos.m_123341_() >> 4), (int)(pos.m_123343_() >> 4));
            LongSet list = (LongSet)this.blocksToTick.get(chunk);
            if (list == null) {
                return;
            }
            if (list.remove(pos.m_121878_())) {
                if (list.isEmpty()) {
                    this.blocksToTick.remove(chunk);
                }
                this.m_77762_();
            }
        }

        private LongSet get(ChunkPos pos) {
            this.m_77762_();
            return (LongSet)this.blocksToTick.remove(pos.m_45588_());
        }

        public CompoundTag m_7176_(CompoundTag compound) {
            ListTag list = new ListTag();
            for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable(this.blocksToTick)) {
                CompoundTag nbt = new CompoundTag();
                nbt.m_128356_("chunk", entry.getLongKey());
                nbt.m_128388_("pos", ((LongSet)entry.getValue()).toLongArray());
                list.add((Object)nbt);
            }
            compound.m_128365_("lights", (Tag)list);
            return compound;
        }
    }
}

