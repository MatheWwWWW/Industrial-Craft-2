/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.crafting.helpers;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockTagInput
implements IInput {
    TagKey<Block> tag;

    public BlockTagInput(TagKey<Block> tag) {
        this.tag = tag;
    }

    @Override
    public List<ItemStack> getComponents() {
        ObjectList list = CollectionUtils.createList();
        for (Block block : ForgeRegistries.BLOCKS.tags().getTag(this.tag)) {
            ItemStack stack = new ItemStack((ItemLike)block);
            if (stack.m_41619_()) continue;
            list.add((ItemStack)stack);
        }
        return list;
    }

    @Override
    public int getInputSize() {
        return 1;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return false;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
    }

    @Override
    public JsonObject serialize() {
        return new JsonObject();
    }
}

