/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArrowItem
 *  net.minecraft.world.item.BoatItem
 *  net.minecraft.world.item.BowItem
 *  net.minecraft.world.item.BucketItem
 *  net.minecraft.world.item.CrossbowItem
 *  net.minecraft.world.item.DiggerItem
 *  net.minecraft.world.item.FishingRodItem
 *  net.minecraft.world.item.HoeItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.MinecartItem
 *  net.minecraft.world.item.PotionItem
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.helpers;

import ic2.api.items.IBoxableItem;
import ic2.api.items.IUpgradeItem;
import ic2.api.items.ItemRegistries;
import ic2.api.items.electric.ICustomElectricItem;
import ic2.api.items.electric.IElectricItem;
import ic2.core.item.food_and_drink.GlassItem;
import ic2.core.item.food_and_drink.MugItem;
import ic2.core.item.food_and_drink.TinCanItem;
import ic2.core.item.misc.CellItem;
import ic2.core.item.misc.CoinItem;
import ic2.core.item.misc.FuelCanItem;
import ic2.core.item.misc.IC2BoatItem;
import ic2.core.item.tool.PainterTool;
import ic2.core.item.tool.ToolBoxTool;
import ic2.core.item.wearable.base.IC2ShieldBase;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.Set;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class Boxables
implements IBoxableItem {
    public static final Boxables INSTANCE = new Boxables();
    Set<Item> validItems = CollectionUtils.createSet();
    Set<String> prefixes = CollectionUtils.createLinkedSet();

    public static void loadBoxableItems() {
        INSTANCE.init();
    }

    private void init() {
        this.registerItem(IC2Items.WIKI_ITEM);
        this.registerItem(IC2Items.TREETAP);
        this.registerItem(IC2Items.EU_READER);
        this.registerItem(Items.f_42446_);
        this.registerItem(Items.f_42409_);
        this.registerItem(Blocks.f_50081_.m_5456_());
        this.registerItem(IC2Blocks.SCAFFOLD_WOOD.m_5456_());
        this.registerItem(IC2Blocks.SCAFFOLD_IRON.m_5456_());
        this.registerItem(Items.f_42741_);
        this.registerItem(IC2Items.CROP_STICKS);
        this.registerItem(Items.f_42524_);
        this.registerItem(Items.f_42522_);
        this.registerItem(Items.f_42399_);
        this.registerItem(IC2Items.MINER_REMOTE);
        this.registerItem(IC2Items.CUTTER);
        this.registerItem(IC2Blocks.MINING_PIPE_SHAFT.m_5456_());
        this.registerItem(IC2Items.HYDRATION_CELL);
        this.registerItem(IC2Items.WEEDEX);
        this.registerItem(Items.f_42684_);
        this.registerItem(Items.f_42685_);
        this.registerItem(IC2Items.MEMORY_STICK);
        this.registerItem(IC2Items.STICKY_DYNAMITE);
        this.registerItem(IC2Items.CF_SPRAYER);
        this.registerItem(IC2Items.DYNAMITE_REMOTE);
        this.registerItem(IC2Items.FREQUENCY_TRANSMITTER);
        this.registerItem(Items.f_42455_);
        this.registerItem(IC2Items.CELL_AIR);
        this.registerItem(Items.f_42574_);
        this.registerItem(IC2Items.TIN_CAN);
        this.registerItem(IC2Blocks.PIPE.m_5456_());
        this.registerItem(IC2Blocks.DETECTOR_PIPE.m_5456_());
        this.registerItem(IC2Blocks.SPLITTER_PIPE.m_5456_());
        this.registerItem(IC2Blocks.SIMPLE_TUBE.m_5456_());
        this.registerItem(IC2Blocks.SPEED_TUBE.m_5456_());
        this.registerItem(IC2Blocks.TRANSPORT_TUBE.m_5456_());
        this.registerItem(IC2Blocks.VOID_TUBE.m_5456_());
        this.registerItem(IC2Blocks.HOVER_TUBE.m_5456_());
        this.registerItem(IC2Blocks.DIRECTIONAL_TUBE.m_5456_());
        this.registerItem(IC2Blocks.FILTER_TUBE.m_5456_());
        this.registerItem(IC2Blocks.INSERTION_TUBE.m_5456_());
        this.registerItem(IC2Blocks.EXTRACTOR_TUBE.m_5456_());
        this.registerItem(IC2Blocks.FILTERED_EXTRACTION_TUBE.m_5456_());
        this.registerItem(IC2Blocks.STACKING_TUBE.m_5456_());
        this.registerItem(IC2Blocks.PICKUP_TUBE.m_5456_());
        this.registerItem(IC2Blocks.REDSTONE_TUBE.m_5456_());
        this.registerItem(IC2Blocks.SWITCH_TUBE.m_5456_());
        this.registerItem(IC2Blocks.ROUND_ROBIN_TUBE.m_5456_());
        this.registerItem(IC2Blocks.TELEPORT_TUBE.m_5456_());
        this.registerItem(IC2Blocks.DROPPING_TUBE.m_5456_());
        this.registerItem(IC2Blocks.DYEING_TUBE.m_5456_());
        this.registerItem(IC2Blocks.COLOR_FILTER_TUBE.m_5456_());
        this.registerItem(IC2Blocks.REQUEST_TUBE.m_5456_());
        this.registerItem(IC2Blocks.PROVIDER_TUBE.m_5456_());
        this.registerItem(IC2Blocks.FLUID_TUBE.m_5456_());
        this.registerPrefix("tool", "wrench", "wand", "rod", "scepter", "screwdriver", "meter", "hammer", "magnifying", "pipette", "grafter", "grafter", "reader", "soldering", "meter", "handsaw", "gun", "key", "chisel", "probe", "scoop");
        for (Item item : ForgeRegistries.ITEMS) {
            if (!item.m_41472_() && !(item instanceof TinCanItem) && !(item instanceof MugItem) && !(item instanceof GlassItem) && !(item instanceof FuelCanItem) && !(item instanceof CellItem) && !(item instanceof CoinItem) && !(item instanceof IC2ShieldBase) && !(item instanceof BoatItem) && !(item instanceof IC2BoatItem) && !(item instanceof MinecartItem) && !(item instanceof PainterTool) && !(item instanceof PotionItem) && !(item instanceof ArrowItem) && !(item instanceof SwordItem) && !(item instanceof IElectricItem) && !(item instanceof ICustomElectricItem) && !(item instanceof IUpgradeItem) && !(item instanceof DiggerItem) && !(item instanceof ArmorItem) && !(item instanceof HoeItem) && !(item instanceof BucketItem) && !(item instanceof CrossbowItem) && !(item instanceof BowItem) && !(item instanceof FishingRodItem) && !this.containsPrefix(item)) continue;
            this.registerItem(item);
        }
    }

    public void registerItem(Item item) {
        if (item instanceof ToolBoxTool) {
            return;
        }
        ItemRegistries.registerBoxableItems(this, item);
        this.validItems.add(item);
    }

    public void registerPrefix(String ... s) {
        this.prefixes.addAll((Collection<String>)ObjectArrayList.wrap((Object[])s));
    }

    private boolean containsPrefix(Item item) {
        String s = ForgeRegistries.ITEMS.getKey((Object)item).m_135815_();
        for (String value : this.prefixes) {
            if (!s.contains(value)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean canStoreIntoBox(ItemStack stack) {
        return this.validItems.contains(stack.m_41720_());
    }
}

