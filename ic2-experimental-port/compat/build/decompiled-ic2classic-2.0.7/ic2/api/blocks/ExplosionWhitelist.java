/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  net.minecraft.world.level.block.Block
 */
package ic2.api.blocks;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Collection;
import java.util.Set;
import net.minecraft.world.level.block.Block;

public class ExplosionWhitelist {
    static final Set<Block> WHITELIST = new ObjectOpenHashSet();

    public static void addWhitelist(Block ... blocks) {
        WHITELIST.addAll((Collection<Block>)ObjectArrayList.wrap((Object[])blocks));
    }

    public static void removeWhitelist(Block ... blocks) {
        WHITELIST.removeAll((Collection<?>)ObjectArrayList.wrap((Object[])blocks));
    }

    public static boolean isWhitelisted(Block block) {
        return WHITELIST.contains(block);
    }
}

