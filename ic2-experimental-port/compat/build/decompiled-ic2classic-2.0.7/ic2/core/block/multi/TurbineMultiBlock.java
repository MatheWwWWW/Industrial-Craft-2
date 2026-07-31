/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMaps
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.multi;

import ic2.api.util.DirectionList;
import ic2.core.block.base.blocks.BaseCTBlock;
import ic2.core.block.base.blocks.ValveBlock;
import ic2.core.block.generators.TurbineBladeBlock;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.block.rendering.block.TurbineMultiModel;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TurbineMultiBlock
extends BaseCTBlock
implements IRenderType,
ICustomBlockModel {
    public static final BooleanProperty ACTIVE = IC2Properties.ACTIVE;
    public static final IntegerProperty FORMED = IntegerProperty.m_61631_((String)"formed", (int)0, (int)4);

    public TurbineMultiBlock() {
        super("turbine_multi_block", BaseMachineBlock.BASE_MACHINE, "electric/generator/steam_tunnel/structure", null);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false))).m_61124_((Property)FORMED, (Comparable)Integer.valueOf(4))).m_61124_((Property)CONNECTION, (Comparable)Integer.valueOf(0)));
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{ACTIVE, FORMED});
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return RenderType.m_110463_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new TurbineMultiModel(state, this, IC2Textures.getMappedEntriesBlockIC2("electric/generator/steam_tunnel").get((Boolean)state.m_61143_((Property)ACTIVE) != false ? "active_overlay" : "inactive_overlay"));
    }

    @Override
    public List<BlockState> getModelStates() {
        return Collections.emptyList();
    }

    @Override
    protected boolean isBlockValid(BlockState state) {
        return state.m_60713_((Block)this) || state.m_60713_(IC2Blocks.STEAM_TUNNEL) || state.m_60734_() instanceof ValveBlock;
    }

    public static Long2ObjectMap<BlockState> createMultiBlockFrame(Box box) {
        Long2ObjectLinkedOpenHashMap states = new Long2ObjectLinkedOpenHashMap();
        for (BlockPos pos : box.getHollowIterator()) {
            if (pos.m_123341_() >= 1 && !box.isEdge(pos)) continue;
            states.put(pos.m_121878_(), (Object)IC2Blocks.STEAM_TUNNEL_MULTIBLOCK.m_49966_());
        }
        for (int i = 0; i < 9; ++i) {
            if (i == 4) continue;
            states.put(BlockPos.m_121882_((int)2, (int)(i / 3), (int)(i % 3 - 1)), (Object)((BlockState)IC2Blocks.STEAM_TUNNEL_MULTIBLOCK.m_49966_().m_61124_((Property)FORMED, (Comparable)Integer.valueOf(Direction.EAST.m_122416_()))));
        }
        states.put(BlockPos.m_121882_((int)0, (int)1, (int)-1), (Object)IC2Blocks.VALVE.m_49966_());
        states.put(BlockPos.m_121882_((int)0, (int)1, (int)1), (Object)IC2Blocks.VALVE.m_49966_());
        states.put(BlockPos.m_121882_((int)2, (int)1, (int)0), (Object)TurbineBladeBlock.createState(IC2Blocks.STEAM_TUNNEL.m_49966_(), false, Direction.EAST, 5));
        for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable((Long2ObjectMap)states)) {
            BlockState state = (BlockState)entry.getValue();
            if (!state.m_60713_(IC2Blocks.STEAM_TUNNEL_MULTIBLOCK)) continue;
            entry.setValue((Object)((BlockState)state.m_61124_((Property)CONNECTION, (Comparable)Integer.valueOf(TurbineMultiBlock.getConnections(entry.getLongKey(), (Long2ObjectMap<BlockState>)states).getCode()))));
        }
        return states;
    }

    public static BlockState create(int facing, boolean active) {
        return (BlockState)((BlockState)IC2Blocks.STEAM_TUNNEL_MULTIBLOCK.m_49966_().m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(active))).m_61124_((Property)FORMED, (Comparable)Integer.valueOf(facing));
    }

    public static DirectionList getConnections(long pos, Long2ObjectMap<BlockState> states) {
        DirectionList connections = DirectionList.EMPTY;
        for (Direction dir : DirectionList.ALL) {
            BlockState state = (BlockState)states.get(BlockPos.m_121915_((long)pos, (Direction)dir));
            if (state == null || !state.m_60713_(IC2Blocks.STEAM_TUNNEL_MULTIBLOCK) && !state.m_60713_(IC2Blocks.STEAM_TUNNEL) && !(state.m_60734_() instanceof ValveBlock)) continue;
            connections = connections.add(dir);
        }
        return connections;
    }
}

