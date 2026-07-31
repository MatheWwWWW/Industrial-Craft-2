/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.core.block.machines.recipes.misc;

import com.google.gson.JsonObject;
import ic2.api.events.ScrapBoxEvent;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.registries.IScrapBoxRegistry;
import ic2.core.IC2;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class ScrapBoxOutput
implements IRecipeOutput {
    List<ItemStack> allDrops = null;

    public ScrapBoxOutput(JsonObject obj) {
    }

    public ScrapBoxOutput(FriendlyByteBuf buffer) {
    }

    public ScrapBoxOutput() {
    }

    @Override
    public List<ItemStack> onRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags) {
        ObjectList list = CollectionUtils.createList();
        IScrapBoxRegistry.IDrop drop = IC2.RECIPES.get().scrapBoxes.getRandomDrop(new ItemStack((ItemLike)IC2Items.SCRAPBOX), false);
        if (drop != null) {
            list.add((ItemStack)drop.getDrop().m_41777_());
            MinecraftForge.EVENT_BUS.post((Event)new ScrapBoxEvent((List<ItemStack>)list, new ItemStack((ItemLike)IC2Items.SCRAPBOX)));
        }
        return list;
    }

    @Override
    public List<ItemStack> getAllOutputs() {
        if (this.allDrops == null) {
            this.allDrops = CollectionUtils.createList();
            for (IScrapBoxRegistry.IDrop drop : IC2.RECIPES.get().scrapBoxes.getAllDrops()) {
                this.allDrops.add(drop.getDrop().m_41777_());
            }
        }
        return this.allDrops;
    }

    @Override
    public CompoundTag getMetadata() {
        return EMPTY_COMPOUND;
    }

    @Override
    public float getExperience() {
        return 0.0f;
    }

    @Override
    public JsonObject serialize() {
        return new JsonObject();
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
    }
}

