/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.LeavesBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool.electric;

import ic2.api.items.electric.ElectricItem;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.item.base.features.IMultiTargetTool;
import ic2.core.item.tool.electric.ChainsawTool;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.utils.collection.IterableWrapper;
import ic2.core.utils.helpers.AABBUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AdvancedChainsaw
extends ChainsawTool
implements IMultiTargetTool {
    public AdvancedChainsaw() {
        super("advanced_chainsaw", 2, 100, 16.0f);
        this.tier = 3;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools/chainsaw").get("advanced");
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        if (this.isMultiMining(stack) && this.canMultiMine(stack)) {
            Level world = player.f_19853_;
            BlockHitResult ray = AdvancedChainsaw.m_41435_((Level)world, (Player)player, (ClipContext.Fluid)ClipContext.Fluid.NONE);
            int removed = 0;
            for (BlockPos offset : IterableWrapper.wrap(this.getHitPositions(stack, player, pos, ray.m_82434_()))) {
                BlockState state = world.m_8055_(offset);
                if (state.m_60795_() || !state.canHarvestBlock((BlockGetter)world, offset, player)) continue;
                BlockEntity tile = world.m_7702_(offset);
                if (!state.onDestroyedByPlayer(world, offset, player, true, state.m_60819_())) continue;
                Block block = state.m_60734_();
                block.m_6786_((LevelAccessor)world, offset, state);
                block.m_6240_(world, player, offset, state, tile, stack);
                ++removed;
            }
            if (removed > 0) {
                IC2.AUDIO.playSound(player, player.f_19853_.f_46441_.m_188499_() ? USE_1 : USE_2, AudioManager.SoundType.ITEM, 1.0f, 1.0f);
                player.m_36220_(IC2Stats.BLOCKS_SAWED);
                ElectricItem.MANAGER.use(stack, this.getEnergyCost(stack) * removed, (LivingEntity)player);
                return true;
            }
        }
        return super.onBlockStartBreak(stack, pos, player);
    }

    @Override
    public boolean isMultiMining(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canMultiMine(ItemStack stack) {
        return true;
    }

    @Override
    public Iterator<BlockPos> getHitPositions(ItemStack stack, Player player, BlockPos pos, Direction dir) {
        final CompoundTag nbt = stack.m_41784_();
        if (pos.m_121878_() != nbt.m_128454_("lastPos")) {
            long[] toCheck = nbt.m_128467_("positions");
            boolean found = false;
            if (toCheck.length > 0) {
                long toFind = pos.m_121878_();
                for (int i = 0; i < toCheck.length; ++i) {
                    if (toFind != toCheck[i]) continue;
                    found = true;
                    break;
                }
            }
            if (!found) {
                boolean remove = true;
                int limit = ElectricItem.MANAGER.getCharge(stack) / this.getEnergyCost(stack);
                if (limit > 0) {
                    BlockState state = player.f_19853_.m_8055_(pos);
                    Block block = state.m_60734_();
                    if (state.m_204336_(BlockTags.f_13106_)) {
                        Object2ObjectMap<Block, List<BlockPos>> positions = AABBUtil.getValidBlocks(player.f_19853_, pos, 20, (AABBUtil.IBlockFilter)new TreeChopper(block), 32, DirectionList.ALL, 200).getMappedPositions();
                        List logs = (List)positions.remove(block);
                        if (positions.size() > 0) {
                            long[] result = new long[Math.min(limit, logs.size())];
                            for (int i = 0; i < result.length; ++i) {
                                result[i] = ((BlockPos)logs.get(i)).m_121878_();
                            }
                            nbt.m_128388_("positions", result);
                            remove = false;
                        }
                    }
                }
                if (remove) {
                    nbt.m_128473_("positions");
                }
            }
            nbt.m_128356_("lastPos", pos.m_121878_());
        }
        return new Iterator<BlockPos>(){
            long[] positions;
            int index;
            BlockPos.MutableBlockPos position;
            {
                this.positions = nbt.m_128467_("positions");
                this.index = 0;
                this.position = new BlockPos.MutableBlockPos();
            }

            @Override
            public boolean hasNext() {
                return this.index < this.positions.length;
            }

            @Override
            public BlockPos next() {
                return this.position.m_122188_(this.positions[this.index++]);
            }
        };
    }

    public static class TreeChopper
    implements AABBUtil.IBlockFilter {
        Block baseBlock;

        public TreeChopper(Block baseBlock) {
            this.baseBlock = baseBlock;
        }

        @Override
        public boolean isValid(BlockState state) {
            return state.m_60734_() == this.baseBlock || state.m_60734_() instanceof LeavesBlock && (Integer)state.m_61143_((Property)LeavesBlock.f_54418_) == 1;
        }
    }
}

