/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.item;

import com.mojang.authlib.GameProfile;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public interface EnvItemHandler {
    public int fetch(BlockEntity var1, ItemStack var2, boolean var3);

    public int deposit(BlockEntity var1, Direction var2, ItemStack var3, GameProfile var4, boolean var5);

    public int deposit(AdjacentInventory var1, ItemStack var2, boolean var3);

    public int distribute(BlockEntity var1, ItemStack var2, boolean var3);

    @Nullable
    public AdjacentInventory getAdjacentInventory(BlockEntity var1, Direction var2);

    public List<? extends AdjacentInventory> getAdjacentInventories(BlockEntity var1);

    public AdjacentInventory wrapInventory(BlockEntity var1, Direction var2);

    public int transfer(AdjacentInventory var1, AdjacentInventory var2, int var3);

    public int transfer(AdjacentInventory var1, AdjacentInventory var2, int var3, Predicate<ItemStack> var4);

    @ApiStatus.NonExtendable
    public static interface AdjacentInventory {
        public Direction getSide();
    }
}

