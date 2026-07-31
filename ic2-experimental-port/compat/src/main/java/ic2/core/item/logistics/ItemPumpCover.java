package ic2.core.item.logistics;

import ic2.core.block.transport.TileEntityFluidPipe;
import ic2.core.block.transport.cover.CoverProperty;
import ic2.core.block.transport.cover.ICoverHolder;
import ic2.core.block.transport.cover.ICoverItem;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.util.RotationUtil;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.IFluidHandler;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** The LV/MV pump cover, including its one-way capability gating. */
public final class ItemPumpCover extends Item implements ICoverItem {
    private static final String TYPE_TAG = "type";
    private static final String SIDE_TAG = "side";

    public ItemPumpCover(Properties properties) {
        super(properties);
    }

    public static ItemStack getCover(PumpCoverType type) {
        ItemStack stack = new ItemStack(RestoredLegacyContent.COVER.get());
        stack.m_41784_().m_128344_(
                TYPE_TAG, (byte) (type == null ? PumpCoverType.pump_lv : type).ordinal());
        return stack;
    }

    public static PumpCoverType getType(ItemStack stack) {
        CompoundTag tag = stack == null ? null : stack.m_41783_();
        return PumpCoverType.byId(tag == null ? 0 : tag.m_128451_(TYPE_TAG));
    }

    @Override
    public String m_5671_(ItemStack stack) {
        return "item.ic2.cover." + getType(stack).getName();
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        Level level = context.m_43725_();
        BlockPos pos = context.m_8083_();
        BlockEntity tile = level.m_7702_(pos);
        if (!(tile instanceof ICoverHolder holder)) {
            return InteractionResult.PASS;
        }
        Vec3 hit = context.m_43720_();
        Direction side = RotationUtil.rotateByHit(
                context.m_43719_(),
                (float) (hit.m_7096_() - pos.m_123341_()),
                (float) (hit.m_7098_() - pos.m_123342_()),
                (float) (hit.m_7094_() - pos.m_123343_()));
        ItemStack held = context.m_43722_();
        if (!holder.canPlaceCover(level, pos, side, held)) {
            return InteractionResult.FAIL;
        }
        if (!level.f_46443_) {
            holder.placeCover(level, pos, side, held);
            Player player = context.m_43723_();
            if (player == null || !player.m_150110_().f_35937_) {
                held.m_41774_(1);
            }
        }
        return InteractionResult.m_19078_(level.f_46443_);
    }

    @Override
    public boolean isSuitableFor(ItemStack stack, Set<CoverProperty> properties) {
        return properties.contains(CoverProperty.FluidConsuming);
    }

    @Override
    public boolean onTick(ItemStack stack, ICoverHolder holder) {
        if (!(holder instanceof BlockEntity tile)
                || !(tile instanceof ICapabilityProvider targetProvider)) {
            return false;
        }
        CompoundTag tag = stack.m_41783_();
        int index = tag == null ? -1 : tag.m_128445_(SIDE_TAG) & 255;
        if (index < 0 || index >= Direction.values().length || tile.m_58904_() == null) {
            return false;
        }
        Direction side = Direction.values()[index];
        BlockEntity neighbor = tile.m_58904_().m_7702_(tile.m_58899_().m_121945_(side));
        if (!(neighbor instanceof ICapabilityProvider sourceProvider)) {
            return false;
        }
        IFluidHandler source = sourceProvider
                .getCapability(ForgeCapabilities.FLUID_HANDLER, side.m_122424_()).orElse(null);
        IFluidHandler target = targetProvider
                .getCapability(ForgeCapabilities.FLUID_HANDLER, side).orElse(null);
        return TileEntityFluidPipe.transfer(source, target, getType(stack).transferRate / 20) > 0;
    }

    @Override
    public boolean allowsInput(ItemStack stack) {
        return false;
    }

    @Override
    public boolean allowsInput(Ic2FluidStack stack) {
        return true;
    }

    @Override
    public boolean allowsOutput(ItemStack stack) {
        return false;
    }

    @Override
    public boolean allowsOutput(Ic2FluidStack stack) {
        return false;
    }

    @Override
    public void m_7373_(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.m_237113_("Transfer rate: " + getType(stack).transferRate + " mB/sec"));
    }

    @Override
    public void m_6787_(CreativeModeTab tab, NonNullList<ItemStack> items) {
        if (!m_220152_(tab)) {
            return;
        }
        for (PumpCoverType type : PumpCoverType.values) {
            items.add(getCover(type));
        }
    }
}
