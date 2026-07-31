/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.api.items;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class IDrinkableFluid {
    public static final Map<ResourceLocation, IDrinkableFluid> REGISTRY = new Object2ObjectOpenHashMap();
    final ResourceLocation id;

    public IDrinkableFluid(ResourceLocation id) {
        this.id = id;
        if (REGISTRY.put(id, this) != null) {
            throw new IllegalStateException("Duplicated Drinkable Fluids are not allowed");
        }
    }

    public abstract boolean drink(ItemStack var1, Level var2, Player var3);

    public boolean hasSpecialName() {
        return false;
    }

    public Component getSpecialName(ItemStack stack) {
        return null;
    }

    public List<ItemStack> generateSubStates(ItemStack base, boolean textures) {
        return Collections.singletonList(base);
    }

    public abstract ResourceLocation getTexture(ItemStack var1, String var2);

    public int getTextureIndex(ItemStack stack) {
        return 0;
    }

    public final ResourceLocation getID() {
        return this.id;
    }

    public final int hashCode() {
        return this.id.hashCode();
    }

    public final boolean equals(Object obj) {
        if (obj instanceof IDrinkableFluid) {
            return ((IDrinkableFluid)obj).getID().equals((Object)this.getID());
        }
        return false;
    }
}

