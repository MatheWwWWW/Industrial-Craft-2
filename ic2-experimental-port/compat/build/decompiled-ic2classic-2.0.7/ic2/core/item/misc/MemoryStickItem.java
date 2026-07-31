/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.misc;

import ic2.core.IC2;
import ic2.core.block.machines.logic.crafter.CraftRecipe;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MemoryStickItem
extends IC2Item
implements ISimpleItemModel {
    public MemoryStickItem() {
        super("memory_stick", new PropertiesBuilder().maxStackSize(1));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.ALT_KEY, "tooltip.item.ic2.memory_stick.clear", new Object[0]));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        if (IC2.KEYBOARD.isAltKeyDown(player)) {
            ItemStack stack = player.m_21120_(hand);
            stack.m_41751_(null);
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(world, player, hand);
    }

    public static void saveRecipe(ItemStack stack, int index, CraftRecipe recipe) {
        stack.m_41784_().m_128365_("recipe_" + index, (Tag)recipe.writeRecipe(new CompoundTag()));
    }

    public static void deleteRecipe(ItemStack stack, int index) {
        StackUtil.getNbtData(stack).m_128473_("recipe_" + index);
    }

    public static CraftRecipe loadRecipe(ItemStack stack, int index) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        if (nbt.m_128441_("recipe_" + index)) {
            CraftRecipe recipe = new CraftRecipe();
            recipe.readRecipe(nbt.m_128469_("recipe_" + index));
            return recipe;
        }
        return null;
    }

    public static void setSetup(ItemStack stack, String text, String name) {
        CompoundTag data = stack.m_41784_();
        data.m_128359_("plan", text);
        data.m_128359_("plan_name", name);
    }

    public static boolean hasSetup(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128441_("plan");
    }

    public static String getSetup(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128461_("plan");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools").get("memory_stick");
    }
}

