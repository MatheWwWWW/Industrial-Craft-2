package ic2.core.item.block;

import ic2.core.block.transport.BlockFluidPipe;
import ic2.core.block.transport.TileEntityFluidPipe;
import ic2.core.block.transport.items.PipeSize;
import ic2.core.block.transport.items.PipeType;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** One registry item carrying the exact legacy material and size variant NBT. */
public final class ItemFluidPipe extends Item {
    private static final String TYPE_TAG = "type";
    private static final String SIZE_TAG = "size";

    public ItemFluidPipe(Properties properties) {
        super(properties);
    }

    public static ItemStack getPipe(PipeType type, PipeSize size) {
        ItemStack stack = new ItemStack(RestoredLegacyContent.PIPE.get());
        CompoundTag tag = stack.m_41784_();
        tag.m_128344_(TYPE_TAG, (byte) (type == null ? PipeType.bronze : type).ordinal());
        tag.m_128344_(SIZE_TAG, (byte) (size == null ? PipeSize.small : size).ordinal());
        return stack;
    }

    public static ItemStack getItemStack(PipeType type) {
        return getPipe(type, PipeSize.small);
    }

    public static ItemStack getItemStack(String variant) {
        PipeType type = PipeType.bronze;
        PipeSize size = PipeSize.small;
        if (variant != null) {
            for (String field : variant.split(",")) {
                String[] pair = field.trim().split(":", 2);
                if (pair.length != 2) {
                    continue;
                }
                if (pair[0].equals("type") && PipeType.get(pair[1]) != null) {
                    type = PipeType.get(pair[1]);
                } else if (pair[0].equals("size") && PipeSize.get(pair[1]) != null) {
                    size = PipeSize.get(pair[1]);
                }
            }
        }
        return getPipe(type, size);
    }

    public static PipeType getPipeType(ItemStack stack) {
        CompoundTag tag = stack == null ? null : stack.m_41783_();
        return PipeType.byId(tag == null ? 0 : tag.m_128451_(TYPE_TAG));
    }

    public static PipeSize getPipeSize(ItemStack stack) {
        CompoundTag tag = stack == null ? null : stack.m_41783_();
        return PipeSize.byId(tag == null ? PipeSize.small.ordinal() : tag.m_128451_(SIZE_TAG));
    }

    public static String getVariant(ItemStack stack) {
        return "type:" + getPipeType(stack).getName() + ",size:" + getPipeSize(stack).getName();
    }

    @Override
    public String m_5671_(ItemStack stack) {
        return "item.ic2.pipe." + getPipeType(stack).getName(getPipeSize(stack));
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        Level level = context.m_43725_();
        BlockPos pos = context.m_8083_();
        if (!level.m_8055_(pos).m_60795_()) {
            pos = pos.m_121945_(context.m_43719_());
        }
        if (!level.m_8055_(pos).m_60795_()) {
            return InteractionResult.FAIL;
        }

        ItemStack stack = context.m_43722_();
        PipeType type = getPipeType(stack);
        PipeSize size = getPipeSize(stack);
        BlockState state = RestoredLegacyContent.FLUID_PIPE_BLOCK.get().m_49966_()
                .m_61124_(BlockFluidPipe.TYPE, type)
                .m_61124_(BlockFluidPipe.SIZE, size);
        if (!level.m_7731_(pos, state, 3)) {
            return InteractionResult.FAIL;
        }
        if (!level.f_46443_) {
            if (!(level.m_7702_(pos) instanceof TileEntityFluidPipe pipe)) {
                return InteractionResult.FAIL;
            }
            pipe.configure(type, size);
            pipe.onPlaced(context.m_43719_());
            Player player = context.m_43723_();
            if (player == null || !player.m_150110_().f_35937_) {
                stack.m_41774_(1);
            }
        }
        return InteractionResult.m_19078_(level.f_46443_);
    }

    @Override
    public void m_7373_(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        PipeType type = getPipeType(stack);
        PipeSize size = getPipeSize(stack);
        int capacity = (int) (type.transferRate * size.multiplier);
        tooltip.add(Component.m_237113_("Transfer rate: " + capacity + " mB/sec"));
        tooltip.add(Component.m_237113_("Inner capacity: " + capacity + " mB"));
        tooltip.add(Component.m_237113_("Use a wrench to connect pipes").m_130940_(ChatFormatting.GOLD));
    }

    @Override
    public void m_6787_(CreativeModeTab tab, NonNullList<ItemStack> items) {
        if (!m_220152_(tab)) {
            return;
        }
        for (PipeType type : PipeType.values) {
            for (PipeSize size : PipeSize.values) {
                items.add(getPipe(type, size));
            }
        }
    }
}
