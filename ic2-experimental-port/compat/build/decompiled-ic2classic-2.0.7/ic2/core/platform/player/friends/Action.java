/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.platform.player.friends;

import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public final class Action {
    static final SortedMap<String, Action> ACTION_MAP = Object2ObjectSortedMaps.synchronize(CollectionUtils.createLinkedMap());
    public static final Action PERSONAL_BLOCKS_ACCESS = Action.createAction("personal_blocks_access", () -> new ItemStack((ItemLike)IC2Blocks.PERSONAL_CHEST), () -> new ItemStack((ItemLike)Items.f_42545_));
    public static final Action PERSONAL_BLOCKS_EDIT = Action.createAction("personal_blocks_edit", () -> new ItemStack((ItemLike)IC2Blocks.PERSONAL_CHEST), () -> new ItemStack((ItemLike)IC2Items.WRENCH));
    public static final Action PERSONAL_BLOCKS_ADMIN = Action.createAction("personal_blocks_admin", () -> new ItemStack((ItemLike)IC2Blocks.PERSONAL_CHEST), () -> new ItemStack((ItemLike)Items.f_42430_));
    public static final Action TRADE_SETTINGS = Action.createAction("trade_settings", () -> new ItemStack((ItemLike)IC2Blocks.TRADE_O_MAT), () -> new ItemStack((ItemLike)Items.f_42430_));
    public static final Action TELEPORT_NETWORK = Action.createAction("teleport_network", () -> new ItemStack((ItemLike)IC2Blocks.TELEPORT_TUBE), () -> new ItemStack((ItemLike)Items.f_42155_));
    public static final Action TELEPORT_SETTINGS = Action.createAction("teleport_settings", () -> new ItemStack((ItemLike)IC2Blocks.TELEPORT_TUBE), () -> new ItemStack((ItemLike)Items.f_42430_));
    public static final Action PLAYER_DETECTOR_SETTINGS = Action.createAction("player_detector_settings", () -> new ItemStack((ItemLike)IC2Blocks.PLAYER_DETECTOR), () -> new ItemStack((ItemLike)IC2Items.WRENCH));
    public static final Action PLAYER_DETECTOR_DETECTION = Action.createAction("player_detector_detection", () -> new ItemStack((ItemLike)IC2Blocks.PLAYER_DETECTOR), () -> new ItemStack((ItemLike)Items.f_42545_));
    public static final Action BREAK_IRIDIUM = Action.createAction("player_break_iridium", () -> new ItemStack((ItemLike)IC2Blocks.IRIDIUM_STONE), () -> new ItemStack((ItemLike)Items.f_42390_));
    public static final Action CHUNKLOADER_ACCESS = Action.createAction("chunkloader_access", () -> new ItemStack((ItemLike)IC2Blocks.CHUNKLOADER), () -> new ItemStack((ItemLike)Items.f_42545_));
    public static final Action CHUNKLOADER_EDIT = Action.createAction("chunkloader_edit", () -> new ItemStack((ItemLike)IC2Blocks.CHUNKLOADER), () -> new ItemStack((ItemLike)IC2Items.WRENCH));
    String name;
    Component local;
    Supplier<ItemStack> mainIcon;
    Supplier<ItemStack> subIcon;

    private Action(String name, Supplier<ItemStack> mainIcon, Supplier<ItemStack> subIcon) {
        this.name = name;
        this.local = Component.m_237115_((String)("gui.ic2.friends." + name));
        this.mainIcon = mainIcon;
        this.subIcon = subIcon;
    }

    public static Action createAction(String name, Supplier<ItemStack> main, Supplier<ItemStack> sub) {
        Action value = (Action)ACTION_MAP.get(name);
        if (value == null) {
            value = new Action(name, main, sub);
            ACTION_MAP.put(name, value);
        }
        return value;
    }

    public static Action getAction(String name) {
        return (Action)ACTION_MAP.get(name);
    }

    public static List<Action> getAllActions() {
        return new ObjectArrayList(ACTION_MAP.values());
    }

    public String getName() {
        return this.name;
    }

    public Component getLocal() {
        return this.local;
    }

    public ItemStack getMainIcon() {
        return this.mainIcon == null ? ItemStack.f_41583_ : this.getOrDefault(this.mainIcon.get());
    }

    public ItemStack getSubIcon() {
        return this.subIcon == null ? ItemStack.f_41583_ : this.getOrDefault(this.subIcon.get());
    }

    private ItemStack getOrDefault(ItemStack stack) {
        return stack == null ? ItemStack.f_41583_ : stack;
    }
}

