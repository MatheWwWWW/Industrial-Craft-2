/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machines.logic.terraformer;

import ic2.api.items.ITerraformerBP;
import ic2.api.tiles.ITerraformer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface ITerraformerLogic {
    public boolean canRun();

    public boolean run(ITerraformer var1, ItemStack var2, ITerraformerBP var3);

    public CompoundTag save(CompoundTag var1);

    public void load(CompoundTag var1);
}

