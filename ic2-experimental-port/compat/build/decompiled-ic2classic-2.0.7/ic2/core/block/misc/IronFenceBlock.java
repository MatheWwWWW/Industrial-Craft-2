/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.FenceBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc;

import ic2.api.items.ItemRegistries;
import ic2.core.block.base.IAutoCreator;
import ic2.core.block.base.IToolProvider;
import ic2.core.block.base.misc.IDualLogged;
import ic2.core.block.rendering.block.base.FenceModel;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.plugins.IRegistryProvider;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IronFenceBlock
extends FenceBlock
implements ICustomBlockModel,
IAutoCreator,
IDualLogged,
IRegistryProvider,
IToolProvider {
    Tool tool = Tool.PICKAXE.withLevel(1);
    public static final IntegerProperty CHARGE = IntegerProperty.m_61631_((String)"charge", (int)0, (int)15);
    public static final BooleanProperty LAVA_LOGGED = IC2Properties.LAVA_LOGGED;

    public IronFenceBlock() {
        super(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50183_));
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)CHARGE, (Comparable)Integer.valueOf(0))).m_61124_((Property)LAVA_LOGGED, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    public void registerTools() {
        this.tool.register((Block)this);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation("ic2", "iron_fence");
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{CHARGE}).m_61104_(new Property[]{LAVA_LOGGED});
    }

    public BlockState m_5573_(BlockPlaceContext p_196258_1_) {
        FluidState fluidstate = p_196258_1_.m_43725_().m_6425_(p_196258_1_.m_8083_());
        return (BlockState)super.m_5573_(p_196258_1_).m_61124_((Property)LAVA_LOGGED, (Comparable)Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76195_));
    }

    public FluidState m_5888_(BlockState p_204507_1_) {
        return IDualLogged.getFluidState(p_204507_1_);
    }

    public BlockState m_7417_(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, LevelAccessor p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        if (((Boolean)p_196271_1_.m_61143_((Property)LAVA_LOGGED)).booleanValue()) {
            p_196271_4_.m_186469_(p_196271_5_, (Fluid)Fluids.f_76195_, Fluids.f_76195_.m_6718_((LevelReader)p_196271_4_));
        }
        return super.m_7417_(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new FenceModel(state, "misc", "iron_fence");
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this);
    }

    public void setCharge(Level world, BlockPos pos, BlockState state, int charge) {
        world.m_46597_(pos, (BlockState)state.m_61124_((Property)CHARGE, (Comparable)Integer.valueOf(charge)));
    }

    public int getCharge(BlockState state) {
        return (Integer)state.m_61143_((Property)CHARGE);
    }

    public void m_7892_(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player player = (Player)entityIn;
        if (!ItemRegistries.isMetalArmor(player, EquipmentSlot.FEET)) {
            return;
        }
        int charge = this.getCharge(state);
        if (charge > 0) {
            this.setCharge(worldIn, pos, state, charge - 1);
            Vec3 motion = player.m_20184_();
            player.m_20334_(motion.f_82479_, Math.min((motion.f_82480_ + 0.075) * (motion.f_82480_ > 0.0 ? 1.03 : 1.0), player.m_6144_() ? (motion.f_82480_ > 0.0 ? motion.f_82480_ - 0.03 : -0.3) : 1.5), motion.f_82481_);
            player.f_19789_ = 0.0f;
        }
    }

    public List<ItemStack> m_7381_(BlockState state, LootContext.Builder builder) {
        LootTable loottable = builder.m_78962_().m_7654_().m_129898_().m_79217_(this.m_60589_());
        if (loottable != LootTable.f_79105_) {
            return loottable.m_230922_(builder.m_78972_(LootContextParams.f_81461_, (Object)state).m_78975_(LootContextParamSets.f_81421_));
        }
        ObjectList drops = CollectionUtils.createList();
        drops.add((ItemStack)new ItemStack((ItemLike)this));
        return drops;
    }
}

