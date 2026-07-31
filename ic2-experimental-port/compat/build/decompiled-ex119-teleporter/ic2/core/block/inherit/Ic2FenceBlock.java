/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.CrossCollisionBlock
 *  net.minecraft.world.level.block.FenceBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.inherit;

import ic2.api.item.ItemWrapper;
import ic2.core.IC2;
import ic2.core.block.machine.tileentity.TileEntityMagnetizer;
import ic2.core.ref.Ic2Blocks;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

public class Ic2FenceBlock
extends FenceBlock {
    public static final Map<Direction, BooleanProperty> connectProperties = Ic2FenceBlock.getConnectProperties();
    public final boolean canBoost;

    public Ic2FenceBlock(BlockBehaviour.Properties properties, boolean bl) {
        super(properties);
        this.canBoost = bl;
    }

    public BlockState m_5573_(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.m_43725_().m_6425_(blockPlaceContext.m_8083_());
        return this.getActualState((BlockState)this.m_49966_().m_61124_((Property)f_52313_, (Comparable)Boolean.valueOf(fluidState.m_192917_((Fluid)Fluids.f_76193_))), (BlockGetter)blockPlaceContext.m_43725_(), blockPlaceContext.m_8083_());
    }

    public BlockState m_7417_(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (((Boolean)blockState.m_61143_((Property)f_52313_)).booleanValue()) {
            levelAccessor.m_186469_(blockPos, (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)levelAccessor));
        }
        return this.getActualState(blockState, (BlockGetter)levelAccessor, blockPos);
    }

    public BlockState getActualState(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        BlockState blockState2;
        boolean bl = true;
        boolean bl2 = false;
        BlockState blockState3 = blockState;
        for (Direction direction : Util.HORIZONTAL_DIRS) {
            blockState2 = blockGetter.m_8055_(blockPos.m_121945_(direction));
            if (this.isFence(blockState2)) {
                bl = false;
                if (bl2) break;
                blockState3 = (BlockState)blockState3.m_61124_((Property)connectProperties.get(direction), (Comparable)Boolean.valueOf(true));
                continue;
            }
            if (bl && Ic2FenceBlock.getMagnetizer(blockGetter, blockPos.m_121945_(direction), direction, blockGetter.m_8055_(blockPos.m_121945_(direction)), false) != null) {
                bl2 = true;
                blockState3 = (BlockState)blockState3.m_61124_((Property)connectProperties.get(direction), (Comparable)Boolean.valueOf(true));
                continue;
            }
            blockState3 = (BlockState)blockState3.m_61124_((Property)connectProperties.get(direction), (Comparable)Boolean.valueOf(false));
        }
        if (!bl && bl2) {
            blockState3 = blockState;
            for (Direction direction : Util.HORIZONTAL_DIRS) {
                blockState2 = blockGetter.m_8055_(blockPos.m_121945_(direction));
                blockState3 = this.isFence(blockState2) ? (BlockState)blockState3.m_61124_((Property)connectProperties.get(direction), (Comparable)Boolean.valueOf(true)) : (BlockState)blockState3.m_61124_((Property)connectProperties.get(direction), (Comparable)Boolean.valueOf(false));
            }
        }
        return blockState3;
    }

    private boolean isFence(BlockState blockState) {
        return blockState.m_204336_(BlockTags.f_13039_) && !blockState.m_204336_(BlockTags.f_13098_);
    }

    public void m_7892_(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        boolean bl;
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player)entity;
        boolean bl2 = this.isPowered(level, blockPos);
        boolean bl3 = Ic2FenceBlock.hasMetalShoes(player);
        boolean bl4 = player.m_6144_();
        Vec3 vec3 = player.m_20184_();
        boolean bl5 = bl = vec3.f_82480_ >= -0.25 || vec3.f_82480_ < 1.6;
        if (bl) {
            player.f_19789_ = 0.0f;
        }
        if (!bl2) {
            if (bl4 && !bl && bl3) {
                player.m_20256_(vec3.m_82542_(1.0, 0.9, 1.0));
            }
        } else if (bl4) {
            if (!bl) {
                player.m_20256_(vec3.m_82542_(1.0, 0.8, 1.0));
            }
        } else {
            player.m_20256_(vec3.m_82520_(0.0, 0.075, 0.0));
            vec3 = player.m_20184_();
            if (vec3.m_7098_() > 0.0) {
                player.m_20256_(vec3.m_82542_(1.0, 1.03, 1.0));
            }
            double d = IC2.keyboard.isAltKeyDown(player) ? 0.1 : (bl3 ? 1.5 : 0.5);
            vec3 = player.m_20184_();
            player.m_20334_(vec3.f_82479_, Math.min(vec3.m_7098_(), d), vec3.f_82481_);
        }
        if (!level.f_46443_) {
            List<TileEntityMagnetizer> list = this.getMagnetizers((BlockGetter)level, blockPos, false);
            for (TileEntityMagnetizer tileEntityMagnetizer : list) {
                IC2.network.get(true).updateTileEntityField(tileEntityMagnetizer, "energy");
            }
        }
    }

    private static TileEntityMagnetizer getMagnetizer(BlockGetter blockGetter, BlockPos blockPos, Direction direction, BlockState blockState, boolean bl) {
        if (blockState.m_60734_() != Ic2Blocks.MAGNETIZER) {
            return null;
        }
        BlockEntity blockEntity = blockGetter.m_7702_(blockPos);
        if (blockEntity instanceof TileEntityMagnetizer) {
            TileEntityMagnetizer tileEntityMagnetizer = (TileEntityMagnetizer)blockEntity;
            if (direction != null && !direction.m_122424_().equals((Object)tileEntityMagnetizer.getFacing())) {
                return null;
            }
            if (!bl || tileEntityMagnetizer.canBoost()) {
                return tileEntityMagnetizer;
            }
        }
        return null;
    }

    public static boolean hasMetalShoes(Player player) {
        ItemStack itemStack = (ItemStack)player.m_150109_().f_35975_.get(0);
        Item item = itemStack.m_41720_();
        return item == Items.f_42471_ || item == Items.f_42479_ || item == Items.f_42467_ || ItemWrapper.isMetalArmor(itemStack, player);
    }

    private boolean isPowered(Level level, BlockPos blockPos) {
        if (!this.canBoost) {
            return false;
        }
        List<TileEntityMagnetizer> list = this.getMagnetizers((BlockGetter)level, blockPos, true);
        if (list.isEmpty()) {
            return false;
        }
        double d = 1.0 / (double)list.size();
        for (TileEntityMagnetizer tileEntityMagnetizer : list) {
            tileEntityMagnetizer.boost(d);
        }
        return true;
    }

    private List<TileEntityMagnetizer> getMagnetizers(BlockGetter blockGetter, BlockPos blockPos, boolean bl) {
        TileEntityMagnetizer tileEntityMagnetizer;
        ArrayList<TileEntityMagnetizer> arrayList = new ArrayList<TileEntityMagnetizer>();
        BlockPos blockPos2 = new BlockPos((Vec3i)blockPos);
        BlockPos blockPos3 = new BlockPos(0, 0, 0);
        for (Direction direction : Util.HORIZONTAL_DIRS) {
            BlockPos blockPos4 = blockPos2.m_7918_(0, 0, 0).m_121945_(direction);
            BlockState blockState = blockGetter.m_8055_(blockPos4);
            if (this.isFence(blockState)) {
                return Collections.emptyList();
            }
            tileEntityMagnetizer = Ic2FenceBlock.getMagnetizer(blockGetter, blockPos4, direction, blockState, bl);
            if (tileEntityMagnetizer == null) continue;
            arrayList.add(tileEntityMagnetizer);
        }
        if (!arrayList.isEmpty()) {
            return arrayList;
        }
        int n = 0;
        int n2 = 2;
        for (int i = 1; i <= 20; ++i) {
            boolean bl2 = false;
            block2: for (int j = n; j < n2; ++j) {
                int n3 = j * 2 - 1;
                tileEntityMagnetizer = blockGetter.m_8055_(blockPos2 = blockPos2.m_175288_(blockPos.m_123342_() + n3 * i));
                if (!(tileEntityMagnetizer.m_60734_() instanceof Ic2FenceBlock) || !((Ic2FenceBlock)tileEntityMagnetizer.m_60734_()).canBoost) {
                    if (j == 0) {
                        n = 1;
                    } else {
                        n2 = 1;
                    }
                    if (n != n2) break;
                    bl2 = true;
                    break;
                }
                int n4 = arrayList.size();
                for (Direction direction : Util.HORIZONTAL_DIRS) {
                    BlockPos blockPos5 = blockPos2.m_7918_(0, 0, 0).m_121945_(direction);
                    BlockState blockState = blockGetter.m_8055_(blockPos5);
                    if (this.isFence(blockState)) {
                        if (j == 0) {
                            n = 1;
                        } else {
                            n2 = 1;
                        }
                        if (n == n2) {
                            bl2 = true;
                        }
                        while (arrayList.size() > n4) {
                            arrayList.remove(arrayList.size() - 1);
                        }
                        continue block2;
                    }
                    TileEntityMagnetizer tileEntityMagnetizer2 = Ic2FenceBlock.getMagnetizer(blockGetter, blockPos5, direction, blockState, bl);
                    if (tileEntityMagnetizer2 == null) continue;
                    bl2 = true;
                    arrayList.add(tileEntityMagnetizer2);
                }
            }
            if (bl2) break;
        }
        return arrayList;
    }

    private static Map<Direction, BooleanProperty> getConnectProperties() {
        return CrossCollisionBlock.f_52314_;
    }
}

