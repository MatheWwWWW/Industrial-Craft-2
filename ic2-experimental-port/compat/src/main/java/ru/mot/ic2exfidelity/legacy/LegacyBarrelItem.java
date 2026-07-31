package ru.mot.ic2exfidelity.legacy;

import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.ref.Ic2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Places an IC2 booze barrel by replacing the original wooden scaffold. */
public final class LegacyBarrelItem extends Item {
    private static final String VALUE_TAG = "BoozeValue";

    public LegacyBarrelItem(Properties properties) {
        super(properties.m_41487_(1));
    }

    public static ItemStack create(int value) {
        ItemStack stack = new ItemStack(RestoredLegacyContent.BARREL.get());
        if (value != 0) {
            stack.m_41784_().m_128405_(VALUE_TAG, value);
        }
        return stack;
    }

    public static int getValue(ItemStack stack) {
        CompoundTag tag = stack.m_41783_();
        return tag == null ? 0 : tag.m_128451_(VALUE_TAG);
    }

    @Override
    public Component m_7626_(ItemStack stack) {
        int amount = LegacyBoozeItem.getAmountOfValue(getValue(stack));
        return amount > 0
                ? Component.m_237110_("item.ic2.barrel.filled", amount)
                : Component.m_237115_("item.ic2.barrel.empty");
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        Level level = context.m_43725_();
        BlockPos pos = context.m_8083_();
        if (level.m_8055_(pos).m_60734_() != Ic2Blocks.WOODEN_SCAFFOLD) {
            return InteractionResult.PASS;
        }

        Direction facing = context.m_43719_();
        if (facing.m_122434_().m_122478_()) {
            facing = Direction.NORTH;
        }
        Ic2TileEntityBlock block = RestoredLegacyContent.BARREL_BLOCK.get();
        BlockState state = block.m_49966_()
                .m_61124_(block.facingProperty, facing)
                .m_61124_(Ic2TileEntityBlock.ACTIVE, Boolean.FALSE);
        if (!level.m_7731_(pos, state, 3)) {
            return InteractionResult.FAIL;
        }
        if (!level.f_46443_) {
            if (!(level.m_7702_(pos) instanceof LegacyBarrelBlockEntity barrel)) {
                return InteractionResult.FAIL;
            }
            barrel.loadFromItem(context.m_43722_());
            Player player = context.m_43723_();
            if (player == null || !player.m_150110_().f_35937_) {
                context.m_43722_().m_41774_(1);
            }
        }
        return InteractionResult.m_19078_(level.f_46443_);
    }
}
