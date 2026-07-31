/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipes.registries;

import ic2.api.recipes.registries.IListenableRegistry;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface IElectrolyzerRecipeList
extends IListenableRegistry<IElectrolyzerRecipeList> {
    public static final int CHARGE = 1;
    public static final int DISCHARGE = 2;

    default public void addChargeRecipe(ResourceLocation id, ItemStack input, ItemStack output, int energy) {
        this.addRecipe(id, input, output, 1, energy);
    }

    default public void addDischargeRecipe(ResourceLocation id, ItemStack input, ItemStack output, int energy) {
        this.addRecipe(id, input, output, 2, energy);
    }

    default public void addDualRecipe(ResourceLocation id, ItemStack input, ItemStack output, int energy) {
        this.addRecipe(id, input, output, 3, energy);
    }

    public void addRecipe(ResourceLocation var1, ItemStack var2, ItemStack var3, int var4, int var5);

    public void removeRecipe(ItemStack var1, boolean var2);

    public void removeRecipe(ResourceLocation var1);

    public List<ElectrolyzerRecipe> getRecipes();

    public ElectrolyzerRecipe getRecipe(ItemStack var1, boolean var2, boolean var3);

    public static class ElectrolyzerRecipe {
        ResourceLocation id;
        ItemStack input;
        ItemStack output;
        int flags;
        int energy;

        public ElectrolyzerRecipe(FriendlyByteBuf buffer) {
            this.id = buffer.m_130281_();
            this.input = buffer.m_130267_();
            this.output = buffer.m_130267_();
            this.flags = buffer.readByte();
            this.energy = buffer.readInt();
        }

        public ElectrolyzerRecipe(ResourceLocation id, ItemStack input, ItemStack output, int flags, int energy) {
            this.id = id;
            this.input = input;
            this.output = output;
            this.flags = flags;
            this.energy = energy;
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public ItemStack getInput() {
            return this.input;
        }

        public ItemStack getOutput() {
            return this.output;
        }

        public int getEnergy() {
            return this.energy;
        }

        public boolean isChargeRecipe() {
            return (this.flags & 1) != 0;
        }

        public boolean isDischargeRecipe() {
            return (this.flags & 2) != 0;
        }

        public boolean isDual() {
            return (this.flags & 3) != 0;
        }

        public boolean matches(ItemStack input, boolean charge) {
            return charge ? input.m_41613_() >= this.input.m_41613_() : input.m_41613_() >= this.output.m_41613_();
        }

        public void serialize(FriendlyByteBuf buffer) {
            buffer.m_130085_(this.id);
            buffer.m_130055_(this.input);
            buffer.m_130055_(this.output);
            buffer.writeByte(this.flags);
            buffer.writeInt(this.energy);
        }
    }
}

