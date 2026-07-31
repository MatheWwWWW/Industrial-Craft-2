/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.wiki.components.builders;

import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.ItemInput;
import ic2.api.recipes.ingridients.inputs.ItemStackInput;
import ic2.api.recipes.ingridients.inputs.ItemTagInput;
import ic2.core.platform.recipes.crafting.helpers.BlockTagInput;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.wiki.base.IPageBuilder;
import ic2.core.wiki.components.ItemListComponent;
import ic2.core.wiki.components.builders.IWikiObj;
import java.util.List;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class ItemListObj
implements IWikiObj {
    List<IInput> inputs = CollectionUtils.createList();
    int rows;

    public ItemListObj(int rows) {
        this.rows = rows;
    }

    public ItemListObj add(ItemLike ... providers) {
        for (ItemLike provider : providers) {
            this.inputs.add(new ItemInput(provider));
        }
        return this;
    }

    public ItemListObj addItems(ItemStack ... stacks) {
        for (ItemStack stack : stacks) {
            this.inputs.add(new ItemStackInput(stack));
        }
        return this;
    }

    public ItemListObj addItemTag(TagKey<Item> tag) {
        this.inputs.add(new ItemTagInput(tag));
        return this;
    }

    public ItemListObj addBlockTag(TagKey<Block> tag) {
        this.inputs.add(new BlockTagInput(tag));
        return this;
    }

    @Override
    public void buildComponents(IPageBuilder builder) {
        builder.addComponent(new ItemListComponent(this.rows, builder.getItemList(this.inputs)));
    }
}

