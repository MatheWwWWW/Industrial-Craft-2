/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package trinsdar.gravisuit.mixin;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import trinsdar.gravisuit.util.Registry;

@Mixin(value={Block.class})
public class BlockMixin {
    @Inject(method={"playerDestroy"}, at={@At(value="HEAD")})
    private void injectSetSilkTouch(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool, CallbackInfo callbackInfo) {
        if (tool.m_41720_() == Registry.VAJRA) {
            CompoundTag tag = tool.m_41783_();
            Map enchantments = EnchantmentHelper.m_44831_((ItemStack)tool);
            if (tag != null && tag.m_128471_("silkTouch")) {
                enchantments.put(Enchantments.f_44985_, 1);
                EnchantmentHelper.m_44865_((Map)enchantments, (ItemStack)tool);
            }
        }
    }

    @Inject(method={"playerDestroy"}, at={@At(value="TAIL")})
    private void injectRemoveSilkTouch(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool, CallbackInfo callbackInfo) {
        if (tool.m_41720_() == Registry.VAJRA) {
            CompoundTag tag = tool.m_41783_();
            Map enchantments = EnchantmentHelper.m_44831_((ItemStack)tool);
            if (tag != null && tag.m_128471_("silkTouch")) {
                enchantments.remove(Enchantments.f_44985_);
                EnchantmentHelper.m_44865_((Map)enchantments, (ItemStack)tool);
            }
        }
    }
}

