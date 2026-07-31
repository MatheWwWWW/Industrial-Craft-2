/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.misc;

import ic2.api.items.ITagItem;
import ic2.core.item.base.IC2Item;
import ic2.core.item.renders.models.TagModel;
import ic2.core.platform.rendering.features.item.ICustomItemModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TagItem
extends IC2Item
implements ICustomItemModel,
ITagItem {
    public TagItem() {
        super("tag_item");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        ResourceLocation location = TagItem.getTag(stack);
        helper.addSimpleToolTip("tooltip.item.ic2.full_tag", location == null ? "minecraft:empty" : location.toString());
    }

    public Component m_7626_(ItemStack stack) {
        ResourceLocation location = TagItem.getTag(stack);
        return Component.m_237110_((String)"item.ic2.tag_item", (Object[])new Object[]{location == null ? this.string("empty") : this.string(location.m_135815_())});
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, LivingEntity entity) {
        return 0;
    }

    @Override
    public List<ItemStack> getCustomTypes() {
        return ObjectLists.singleton((Object)new ItemStack((ItemLike)this));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getModel(ItemStack stack) {
        return new TagModel();
    }

    @Override
    public boolean matches(ItemStack self, ItemStack toCompare) {
        ResourceLocation location = TagItem.getTag(self);
        return location != null && toCompare.m_204117_(ItemTags.create((ResourceLocation)location));
    }

    public static void setTag(ItemStack stack, ResourceLocation tag) {
        stack.m_41700_("tag", (Tag)StringTag.m_129297_((String)tag.toString()));
    }

    public static ResourceLocation getTag(ItemStack stack) {
        return ResourceLocation.m_135820_((String)StackUtil.getNbtData(stack).m_128461_("tag"));
    }
}

