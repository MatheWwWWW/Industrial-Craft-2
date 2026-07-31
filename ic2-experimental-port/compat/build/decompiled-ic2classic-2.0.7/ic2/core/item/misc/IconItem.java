/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.ByteTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.misc;

import ic2.core.item.base.IC2Item;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IconItem
extends IC2Item
implements IItemModel {
    List<Tuple<ResourceLocation, String>> textures = this.createIcons();

    public IconItem() {
        super("item_icons");
    }

    public IconItem(String name) {
        super(name);
    }

    public ItemStack create(int index) {
        ItemStack stack = new ItemStack((ItemLike)this);
        stack.m_41700_("index", (Tag)ByteTag.m_128266_((byte)((byte)Mth.m_14045_((int)index, (int)0, (int)(this.textures.size() - 1)))));
        return stack;
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, LivingEntity entity) {
        return Mth.m_14045_((int)StackUtil.getNbtData(stack).m_128451_("index"), (int)0, (int)(this.textures.size() - 1));
    }

    @Override
    public List<ItemStack> getModelTypes() {
        ObjectList list = CollectionUtils.createList();
        int m = this.textures.size();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = new ItemStack((ItemLike)this);
            stack.m_41700_("index", (Tag)ByteTag.m_128266_((byte)((byte)i)));
            list.add((ItemStack)stack);
        }
        return list;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack input) {
        Tuple<ResourceLocation, String> entry = this.textures.get(Mth.m_14045_((int)StackUtil.getNbtData(input).m_128451_("index"), (int)0, (int)(this.textures.size() - 1)));
        return IC2Textures.getMappedEntries((ResourceLocation)entry.m_14418_()).get(entry.m_14419_());
    }

    public List<Tuple<ResourceLocation, String>> createIcons() {
        ObjectList icons = CollectionUtils.createList();
        icons.add((Tuple)new Tuple((Object)new ResourceLocation("ic2", "misc/gui"), (Object)"yes"));
        icons.add((Tuple)new Tuple((Object)new ResourceLocation("ic2", "misc/gui"), (Object)"no"));
        icons.add((Tuple)new Tuple((Object)new ResourceLocation("ic2", "misc/gui"), (Object)"memory_stick"));
        icons.add((Tuple)new Tuple((Object)new ResourceLocation("ic2", "misc/gui"), (Object)"add"));
        icons.add((Tuple)new Tuple((Object)new ResourceLocation("ic2", "misc/gui"), (Object)"remove"));
        icons.add((Tuple)new Tuple((Object)new ResourceLocation("ic2", "misc/gui"), (Object)"swap"));
        icons.add((Tuple)new Tuple((Object)new ResourceLocation("ic2", "item/upgrades/inventory"), (Object)"slot_diversity"));
        icons.add((Tuple)new Tuple((Object)new ResourceLocation("ic2", "item/upgrades/inventory"), (Object)"stack_diversity"));
        return icons;
    }
}

