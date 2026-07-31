/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArraySet
 *  net.minecraft.world.item.Item
 */
package ic2.api.entity.boat;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.world.item.Item;

public class BoatType {
    private static final Set<BoatType> VALUES = new ObjectArraySet();
    private final String name;
    private final Item baseItem;

    protected BoatType(Item item, String string) {
        this.name = string;
        this.baseItem = item;
    }

    public static BoatType register(Item item, String string) {
        BoatType boatType = new BoatType(item, string);
        VALUES.add(boatType);
        return boatType;
    }

    public static Stream<BoatType> stream() {
        return VALUES.stream();
    }

    public String getName() {
        return this.name;
    }

    public Item getBaseItem() {
        return this.baseItem;
    }
}

