/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 */
package ic2.core.block.transport.item;

import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class TubeAction {
    private static final Map<String, TubeAction> MAPPED_ACTIONS = CollectionUtils.createMap();
    private static final List<TubeAction> LISTED_ACTIONS = CollectionUtils.createList();
    public static final TubeAction BLOCK_OUTPUT = new TubeAction("block_output", "tooltip.item.ic2.tube_tool.block_output");
    public static final TubeAction EXTRA_EXTRACION_SIDE = new TubeAction("extra_extraction", "tooltip.item.ic2.tube_tool.extra_extraction");
    public static final TubeAction REDSTONE_CONTROL = new TubeAction("redstone_control", "tooltip.item.ic2.tube_tool.redstone_control");
    public static final TubeAction OUTPUT_PRIORITY = new TubeAction("out_priority", "tooltip.item.ic2.tube_tool.out_priority");
    public static final TubeAction ONLY_EXISTING = new TubeAction("only_existing", "tooltip.item.ic2.tube_tool.only_existing");
    public static final TubeAction SPECIAL_EXTRACTION_CONNECTIVITY = new TubeAction("special_connection", "tooltip.item.ic2.tube_tool.special_connection");
    public static final TubeAction PULSE = new TubeAction("pulse", "tooltip.item.ic2.tube_tool.pulse");
    public static final TubeAction COMPARATOR = new TubeAction("comparator", "tooltip.item.ic2.tube_tool.comparator");
    public static final TubeAction LARGE_PICKUP_RADIUS = new TubeAction("pickup_radius", "tooltip.item.ic2.tube_tool.pickup_radius");
    public static final TubeAction VISIBLE_ITEMS = new TubeAction("visible_items", "tooltip.item.ic2.tube_tool.visible_items");
    String id;
    MutableComponent name;
    MutableComponent desc;

    public TubeAction(String id, String name) {
        this.id = id;
        this.name = Component.m_237115_((String)name);
        this.desc = Component.m_237115_((String)(name + ".desc")).m_130940_(ChatFormatting.DARK_GRAY);
        MAPPED_ACTIONS.put(id, this);
        LISTED_ACTIONS.add(this);
    }

    public String getId() {
        return this.id;
    }

    public MutableComponent getName() {
        return this.name.m_6881_();
    }

    public MutableComponent getDesc() {
        return this.desc.m_6881_();
    }

    public static TubeAction byId(String id) {
        return MAPPED_ACTIONS.get(id);
    }

    public static List<TubeAction> getAll() {
        return ObjectArrayList.wrap((Object[])((TubeAction[])LISTED_ACTIONS.toArray(TubeAction[]::new)));
    }

    public static TubeAction byIdOrDefault(String id) {
        if (id == null || id.trim().isEmpty()) {
            return LISTED_ACTIONS.get(0);
        }
        return MAPPED_ACTIONS.get(id);
    }

    public static TubeAction getNextAction(String id) {
        if (id == null || id.trim().isEmpty()) {
            return LISTED_ACTIONS.get(0);
        }
        TubeAction action = MAPPED_ACTIONS.get(id);
        return LISTED_ACTIONS.get((LISTED_ACTIONS.indexOf(action) + 1) % LISTED_ACTIONS.size());
    }
}

