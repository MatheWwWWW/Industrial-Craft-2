/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.upgrades.special;

import ic2.api.items.IUpgradeItem;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.tiles.IMachine;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.upgrades.base.BaseUpgradeItem;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class ExpCollectorUpgradeItem
extends BaseUpgradeItem.SimpleUpgradeItem {
    public ExpCollectorUpgradeItem() {
        super("exp_collector", new PropertiesBuilder().maxStackSize(1));
        this.functions.add(IUpgradeItem.Functions.RECIPE);
    }

    @Override
    public int getExtraEnergyDemand(ItemStack stack, IMachine machine) {
        return 5;
    }

    @Override
    public void onMachineFinishedRecipePre(ItemStack stack, IMachine machine, IRecipeOutput output, CompoundTag recipeFlags) {
        float exp = output.getExperience();
        if (exp > 0.0f) {
            this.addExp(stack, exp * 0.5f);
        }
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        ItemStack upgradeStack = player.m_21120_(hand);
        int exp = (int)this.getExp(upgradeStack);
        if (exp > 0) {
            int left;
            ItemStack toolStack;
            world.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11871_, SoundSource.PLAYERS, 0.1f, 0.5f * ((world.f_46441_.m_188501_() - world.f_46441_.m_188501_()) * 0.7f + 1.8f));
            Map.Entry entry = EnchantmentHelper.m_44906_((Enchantment)Enchantments.f_44962_, (LivingEntity)player);
            int used = 0;
            if (entry != null && !(toolStack = (ItemStack)entry.getValue()).m_41619_() && toolStack.m_41768_()) {
                int i = Math.min(exp * 2, toolStack.m_41773_());
                used += i * 2;
                toolStack.m_41721_(toolStack.m_41773_() - i);
            }
            if ((left = exp - used) > 0) {
                player.m_6756_(left);
            }
            this.removeExp(upgradeStack, exp);
            return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)upgradeStack);
        }
        return new InteractionResultHolder(InteractionResult.PASS, (Object)upgradeStack);
    }

    public void addExp(ItemStack item, float exp) {
        CompoundTag nbt = item.m_41784_();
        float stuff = nbt.m_128457_("exp");
        if ((stuff += exp) > 5000.0f) {
            stuff = 5000.0f;
        }
        nbt.m_128350_("exp", stuff);
    }

    public void removeExp(ItemStack stack, float exp) {
        CompoundTag nbt = stack.m_41784_();
        float amount = nbt.m_128457_("exp");
        if ((amount -= exp) < 0.0f) {
            amount = 0.0f;
        }
        nbt.m_128350_("exp", amount);
    }

    public float getExp(ItemStack item) {
        return StackUtil.getNbtData(item).m_128457_("exp");
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.PROCESSING_MOD;
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addSimpleToolTip("tooltip.item.ic2.exp_collector", Formatters.XP_FORMAT.format(this.getExp(stack)));
    }
}

