/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 */
package ic2.api.recipes.registries;

import ic2.api.recipes.registries.IListenableRegistry;
import java.util.List;
import java.util.Map;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public interface IPotionBrewRegistry
extends IListenableRegistry<IPotionBrewRegistry> {
    default public void registerBrew(Item item, MobEffect potion) {
        this.registerBrew(item, null, potion);
    }

    public void registerBrew(Item var1, MobEffect var2, MobEffect var3);

    public void removeBrew(Item var1);

    public MobEffect getEffect(Item var1, MobEffect var2);

    public Map<Item, Map<MobEffect, MobEffect>> getEffects();

    public List<Item> getRequiredItems(MobEffect var1);

    public void registerName(MobEffect var1, String var2);

    public String getName(MobEffect var1);

    public void registerPotionContainer(Item var1, PotionContainer var2);

    public PotionContainer getPotionContainer(Item var1);

    public Map<Item, PotionContainer> getContainers();

    public static final class PotionContainer {
        final Item output;
        final int fluidUsage;
        final float durationEffectiveness;

        public PotionContainer(Item output, int fluidUsage, float durationEffectiveness) {
            if (output == Items.f_41852_ || output == null || fluidUsage <= 0 || durationEffectiveness <= 0.0f) {
                throw new IllegalStateException("Potion Brew Result is invalid");
            }
            this.output = output;
            this.fluidUsage = fluidUsage;
            this.durationEffectiveness = durationEffectiveness;
        }

        public float getDurationEffectiveness() {
            return this.durationEffectiveness;
        }

        public int getFluidUsage() {
            return this.fluidUsage;
        }

        public Item getOutput() {
            return this.output;
        }
    }
}

