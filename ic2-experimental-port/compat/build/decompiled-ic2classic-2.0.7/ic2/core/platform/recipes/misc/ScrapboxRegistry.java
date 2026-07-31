/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.crafting.CraftingHelper
 */
package ic2.core.platform.recipes.misc;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.registries.IScrapBoxRegistry;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.platform.recipes.misc.BaseRegistry;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ScrapboxRegistry
extends BaseRegistry<IScrapBoxRegistry>
implements IScrapBoxRegistry,
IRecipeList {
    Random rand = new Random();
    List<Drop> drops = CollectionUtils.createList();
    float maxChance = 0.0f;

    public ScrapboxRegistry(Consumer<IScrapBoxRegistry> listener) {
        this.registerListener(listener);
    }

    @Override
    protected void reloadInternals() {
        this.drops.clear();
        this.maxChance = 0.0f;
    }

    @Override
    protected void onAfterReload() {
        this.reloadPool();
    }

    protected void reloadPool() {
        int i;
        int m = this.drops.size();
        for (i = 0; i < m; ++i) {
            this.maxChance += this.drops.get(i).getChance();
        }
        m = this.drops.size();
        for (i = 0; i < m; ++i) {
            Drop drop = this.drops.get(i);
            drop.setPoolChance(drop.getChance() / this.maxChance);
        }
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        buffer.writeFloat(this.maxChance);
        buffer.m_130130_(this.drops.size());
        int m = this.drops.size();
        for (int i = 0; i < m; ++i) {
            this.drops.get(i).write(buffer);
        }
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        this.maxChance = buffer.readFloat();
        int size = buffer.m_130242_();
        ObjectList dropSync = CollectionUtils.createList();
        for (int i = 0; i < size; ++i) {
            dropSync.add((Drop)new Drop(buffer));
        }
        this.drops = dropSync;
    }

    @Override
    public String getFolder() {
        return "scrap_drops";
    }

    @Override
    public Map<ResourceLocation, JsonObject> writeRecipes() {
        JsonArray array = new JsonArray();
        for (Drop drop : this.drops) {
            JsonObject obj = new JsonObject();
            obj.add("drop", (JsonElement)IInput.writeItemStack(drop.getDrop()));
            obj.addProperty("chance", (Number)Float.valueOf(drop.getChance()));
            array.add((JsonElement)obj);
        }
        return CollectionUtils.createLinkedMap(new ResourceLocation("ic2:drops"), IRecipeList.mapArray("drops", array));
    }

    @Override
    public void readRecipe(ResourceLocation id, JsonObject data) throws Exception {
        boolean remove = GsonHelper.m_13855_((JsonObject)data, (String)"remove", (boolean)false);
        IRecipeList.iterateObject(data.get("drops"), T -> {
            ItemStack stack = CraftingHelper.getItemStack((JsonObject)GsonHelper.m_13930_((JsonObject)T, (String)"drop"), (boolean)true);
            if (stack.m_41619_()) {
                throw new RuntimeException("Drop is Empty");
            }
            if (remove) {
                this.removeDrops(stack);
            } else {
                this.addDrop(stack, GsonHelper.m_13915_((JsonObject)T, (String)"chance"));
            }
        });
    }

    @Override
    public void addDrop(ItemStack stack, float chance) {
        if (stack.m_41619_() || chance < 0.0f) {
            return;
        }
        this.drops.add(new Drop(stack, chance));
    }

    @Override
    public IScrapBoxRegistry.IDrop getRandomDrop(ItemStack source, boolean consumeItem) {
        if (source.m_41619_()) {
            return null;
        }
        if (consumeItem) {
            source.m_41774_(1);
        }
        float currentChance = this.rand.nextFloat() * this.maxChance;
        int m = this.drops.size();
        for (int i = 0; i < m; ++i) {
            IScrapBoxRegistry.IDrop drop = this.drops.get(i);
            if (currentChance > drop.getChance()) {
                currentChance -= drop.getChance();
                continue;
            }
            return drop;
        }
        return null;
    }

    @Override
    public void removeDrops(ItemStack stack) {
        int m = this.drops.size();
        for (int i = 0; i < m; ++i) {
            if (!StackUtil.isStackEqual(this.drops.get(i).getDrop(), stack)) continue;
            this.drops.remove(i--);
            --m;
        }
    }

    @Override
    public void removeDrop(IScrapBoxRegistry.IDrop drop) {
        this.drops.remove(drop);
    }

    @Override
    public List<IScrapBoxRegistry.IDrop> getAllDrops() {
        return new ObjectArrayList(this.drops);
    }

    public static class Drop
    implements IScrapBoxRegistry.IDrop {
        ItemStack stack;
        float chance;
        float poolChance;

        public Drop(FriendlyByteBuf buffer) {
            this.stack = buffer.m_130267_();
            this.chance = buffer.readFloat();
            this.poolChance = buffer.readFloat();
        }

        public Drop(ItemStack stack, float chance) {
            this.stack = stack.m_41777_();
            this.chance = chance;
        }

        public void write(FriendlyByteBuf buffer) {
            buffer.m_130055_(this.stack);
            buffer.writeFloat(this.chance);
            buffer.writeFloat(this.poolChance);
        }

        public void setPoolChance(float poolChance) {
            this.poolChance = poolChance;
        }

        @Override
        public ItemStack getDrop() {
            return this.stack;
        }

        @Override
        public float getChance() {
            return this.chance;
        }

        @Override
        public float getPoolChance() {
            return this.poolChance;
        }

        public int hashCode() {
            return Objects.hash(this.stack, Float.valueOf(this.chance));
        }

        public boolean equals(Object obj) {
            if (obj instanceof IScrapBoxRegistry.IDrop) {
                IScrapBoxRegistry.IDrop drop = (IScrapBoxRegistry.IDrop)obj;
                return StackUtil.isStackEqual(this.stack, drop.getDrop()) && this.chance == drop.getChance();
            }
            return false;
        }
    }
}

