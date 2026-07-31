/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.machines.tiles.ev;

import ic2.api.energy.impl.LinkedSink;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.api.util.DirectionList;
import ic2.core.block.base.cache.CapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.IWrenchRemovable;
import ic2.core.block.base.tiles.BaseMultiElectricTileEntity;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.StructureBuilder;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class ElectricFisherTileEntity
extends BaseMultiElectricTileEntity
implements IProgressMachine,
IWrenchRemovable {
    public static final ResourceLocation LOOT_TABLE = new ResourceLocation("ic2", "electric_fisher");
    ICache<IItemHandler> cache = new CapabilityCache<IItemHandler>(this, DirectionList.UP, ForgeCapabilities.ITEM_HANDLER);
    private final ItemStack fishingRod = new ItemStack((ItemLike)Items.f_42523_);
    public int length;
    public int progress = 0;

    public ElectricFisherTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 0, 512, 5000);
        this.addCaches(this.cache);
        this.fishingRod.m_41663_(Enchantments.f_44953_, 3);
    }

    @Override
    public float getProgress() {
        return this.progress;
    }

    @Override
    public float getMaxProgress() {
        return 300.0f;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128405_("length", this.length);
        compound.m_128405_("progress", this.progress);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.length = compound.m_128451_("length");
        this.progress = compound.m_128451_("progress");
    }

    @Override
    public void onStructureTick() {
        if (!this.hasEnergy(150)) {
            if (this.progress > 0) {
                --this.progress;
            }
            return;
        }
        this.useEnergy(150);
        ++this.progress;
        if (this.progress < 300) {
            return;
        }
        this.progress = 0;
        IItemTransporter transporter = TransporterManager.getTransporter(this.cache.getHandler(Direction.UP));
        if (transporter == null) {
            return;
        }
        LootContext.Builder context = new LootContext.Builder((ServerLevel)this.f_58857_).m_78972_(LootContextParams.f_81460_, (Object)Vec3.m_82512_((Vec3i)this.f_58858_.m_7495_())).m_78972_(LootContextParams.f_81463_, (Object)this.fishingRod).m_230911_(this.f_58857_.f_46441_).m_78963_(1.0f + (float)this.length - 5.0f);
        LootTable loottable = this.f_58857_.m_7654_().m_129898_().m_79217_(LOOT_TABLE);
        for (int i = 0; i < 32; ++i) {
            for (ItemStack stack : loottable.m_230922_(context.m_78975_(LootContextParamSets.f_81414_))) {
                transporter.addItem(stack, Direction.DOWN, false);
            }
        }
    }

    @Override
    public List<IEnergyTile> getTiles() {
        ObjectList tiles = CollectionUtils.createList();
        tiles.add((ElectricFisherTileEntity)this);
        int baseY = this.m_58899_().m_123342_();
        for (long entry : this.structure.structurePositions) {
            BlockPos pos = BlockPos.m_122022_((long)entry);
            if (pos.m_123342_() != baseY) continue;
            tiles.add((LinkedSink)new LinkedSink(this.f_58857_, pos));
        }
        return tiles;
    }

    @Override
    public boolean isStateStillValid(BlockPos actualPos, BlockPos structurePos, BlockState newState) {
        if (structurePos.m_123342_() == 0) {
            int max = Math.max(Math.abs(structurePos.m_123341_()), Math.abs(structurePos.m_123343_()));
            return newState.m_204336_(max == 1 ? BlockTags.f_13090_ : BlockTags.f_13106_);
        }
        if (structurePos.m_123342_() > 0) {
            return this.isValidContainer(this.f_58857_.m_7702_(actualPos));
        }
        if (structurePos.m_123341_() == 0 && structurePos.m_123341_() == 0 && newState.m_60734_() != Blocks.f_50184_) {
            return false;
        }
        return this.isWaterLogged(newState);
    }

    public boolean isValidContainer(BlockEntity tile) {
        if (tile == null) {
            return false;
        }
        IItemHandler handler = (IItemHandler)tile.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).orElse(null);
        return handler != null && handler.getSlots() > 20;
    }

    public boolean isWaterLogged(BlockState state) {
        if (state.m_61138_((Property)BlockStateProperties.f_61362_) && ((Boolean)state.m_61143_((Property)BlockStateProperties.f_61362_)).booleanValue()) {
            return true;
        }
        FluidState fluid = state.m_60819_();
        return fluid.m_76170_() && fluid.m_76152_() == Fluids.f_76193_;
    }

    @Override
    public boolean testStructure(StructureBuilder builder) {
        if (!this.isValidContainer(builder.up().getTile())) {
            return false;
        }
        if (!builder.resetPosition().left(1).forward(1).circleSquareStructure(3, 3, () -> builder.isBlock((TagKey<Block>)BlockTags.f_13090_))) {
            return false;
        }
        if (!builder.left(1).forward(1).circleSquareStructure(5, 5, () -> builder.isBlock((TagKey<Block>)BlockTags.f_13106_))) {
            return false;
        }
        builder.resetPosition();
        int length = builder.findLength(10, false, Direction.DOWN, T -> T.isBlock(Blocks.f_50184_) && T.isWaterlogged());
        if (length < 5) {
            return false;
        }
        for (BlockPos pos : Box.fromPos(BlockPos.f_121853_.m_7918_(-2, -1, -2), BlockPos.f_121853_.m_7918_(2, -length, 2))) {
            if (builder.setOffset(pos).isWaterlogged()) continue;
            return false;
        }
        this.length = length;
        return true;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.FISHER;
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.7;
    }
}

