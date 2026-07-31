/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  net.minecraft.network.chat.Component
 */
package ic2.core.inventory.handler;

import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.network.chat.Component;

public final class SlotType {
    private static final Map<String, SlotType> SLOT_REGISTRY = Object2ObjectMaps.synchronize(CollectionUtils.createMap());
    public static final SlotType ROOT = SlotType.getOrCreate("root", (Component)Component.m_237115_((String)"misc.ic2.slots.root"), null);
    public static final SlotType IO = SlotType.getOrCreate("io", (Component)Component.m_237115_((String)"misc.ic2.slots.io"), ROOT);
    public static final SlotType UPGRADES = SlotType.getOrCreate("upgrades", (Component)Component.m_237115_((String)"misc.ic2.slots.upgrades"), ROOT);
    public static final SlotType INPUTS = SlotType.getOrCreate("inputs", (Component)Component.m_237115_((String)"misc.ic2.slots.inputs"), IO);
    public static final SlotType OUTPUTS = SlotType.getOrCreate("outputs", (Component)Component.m_237115_((String)"misc.ic2.slots.outputs"), IO);
    public static final SlotType FUELS = SlotType.getOrCreate("energy", (Component)Component.m_237115_((String)"misc.ic2.slots.energy"), IO);
    public static final SlotType INPUT = SlotType.getOrCreate("input", (Component)Component.m_237115_((String)"misc.ic2.slots.input"), INPUTS);
    public static final SlotType EXTRA_INPUT = SlotType.getOrCreate("extra_input", (Component)Component.m_237115_((String)"misc.ic2.slots.extra_input"), INPUTS);
    public static final SlotType CONTAINER = SlotType.getOrCreate("container", (Component)Component.m_237115_((String)"misc.ic2.slots.container"), INPUTS);
    public static final SlotType MINING_PIPES = SlotType.getOrCreate("mining_pipes", (Component)Component.m_237115_((String)"misc.ic2.slots.mining_pipes"), INPUTS);
    public static final SlotType TOOLS = SlotType.getOrCreate("tools", (Component)Component.m_237115_((String)"misc.ic2.slots.tools"), INPUTS);
    public static final SlotType OUTPUT = SlotType.getOrCreate("output", (Component)Component.m_237115_((String)"misc.ic2.slots.output"), OUTPUTS);
    public static final SlotType EXTRA_OUTPUT = SlotType.getOrCreate("extra_output", (Component)Component.m_237115_((String)"misc.ic2.slots.extra_output"), OUTPUTS);
    public static final SlotType FUEL = SlotType.getOrCreate("fuel", (Component)Component.m_237115_((String)"misc.ic2.slots.fuel"), FUELS);
    public static final SlotType BATTERY = SlotType.getOrCreate("battery", (Component)Component.m_237115_((String)"misc.ic2.slots.battery"), FUELS);
    public static final SlotType CHARGE = SlotType.getOrCreate("charge", (Component)Component.m_237115_((String)"misc.ic2.slots.charge"), FUELS);
    public static final SlotType FERTILIZER = SlotType.getOrCreate("fertilizer", (Component)Component.m_237115_((String)"misc.ic2.slots.fertilizer"), INPUTS);
    public static final SlotType HYDRATION = SlotType.getOrCreate("hydration", (Component)Component.m_237115_((String)"misc.ic2.slots.hydration"), INPUTS);
    public static final SlotType WEED_EX = SlotType.getOrCreate("weed_ex", (Component)Component.m_237115_((String)"misc.ic2.slots.weed-ex"), INPUTS);
    public static final SlotType SCANNER = SlotType.getOrCreate("scanner", (Component)Component.m_237115_((String)"misc.ic2.slots.scanner"), IO);
    public static final SlotType RECYCLER = SlotType.getOrCreate("recycler", (Component)Component.m_237115_((String)"misc.ic2.slots.recycler"), IO);
    public static final SlotType FILLER = SlotType.getOrCreate("filler", (Component)Component.m_237115_((String)"misc.ic2.slots.filler"), IO);
    public static final SlotType REFUEL_REMOTE = SlotType.getOrCreate("refuel_remote", (Component)Component.m_237115_((String)"misc.ic2.slots.refuel_remote"), IO);
    public static final SlotType CHUNKLOADER = SlotType.getOrCreate("chunkloader", (Component)Component.m_237115_((String)"misc.ic2.slots.chunkloader"), IO);
    public static final SlotType STORAGE = SlotType.getOrCreate("storage", (Component)Component.m_237115_((String)"misc.ic2.slots.storage"), IO);
    public static final SlotType REACTOR = SlotType.getOrCreate("reactor", (Component)Component.m_237115_((String)"misc.ic2.slots.reactor"), IO);
    private final String id;
    private final Component name;
    private final SlotType parent;
    private final Set<SlotType> children = new ObjectOpenHashSet();

    private SlotType(String id, Component name, SlotType parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    public static SlotType getOrCreate(String type, Component name, SlotType parent) {
        return SLOT_REGISTRY.computeIfAbsent(type, T -> new SlotType(type, name, parent));
    }

    public String getId() {
        return this.id;
    }

    public Component getName() {
        return this.name;
    }

    public SlotType getParent() {
        return this.parent;
    }

    public boolean isParent(SlotType type) {
        return type == this || type == this.parent || this.parent != null && this.parent.isParent(type);
    }

    public boolean containsChild(SlotType type) {
        return type == this || this.children.contains(type);
    }

    public boolean contains(SlotType type) {
        return this.children.contains(type);
    }
}

