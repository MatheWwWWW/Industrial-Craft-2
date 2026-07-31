/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  mcjty.theoneprobe.api.IElement
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.api.NumberFormat
 *  mcjty.theoneprobe.apiimpl.elements.ElementProgress
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.templates.EmptyFluidHandler
 */
package ic2.probeplugin.base;

import ic2.api.tiles.readers.IEUStorage;
import ic2.core.block.machines.tiles.hv.TeleporterTileEntity;
import ic2.core.inventory.handler.IHasInventoryHandler;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.utils.collection.CollectionUtils;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.components.GridElement;
import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.override.styles.ITextStyleBuilder;
import ic2.probeplugin.styles.IC2Styles;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.apiimpl.elements.ElementProgress;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.EmptyFluidHandler;

public class ProbePluginHelper {
    public static final DecimalFormatSymbols US = new DecimalFormatSymbols(Locale.US);
    public static final DecimalFormat THERMAL_GEN = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.US));
    public static final DecimalFormat SOLAR_TURBINE = new DecimalFormat("#00.00", new DecimalFormatSymbols(Locale.US));
    public static final DecimalFormat NATURAL = new DecimalFormat("###,##0", new DecimalFormatSymbols(Locale.US));
    public static final DecimalFormat DECIMAL = new DecimalFormat(".#########", new DecimalFormatSymbols(Locale.US));
    public static final String SUFFIX = "kmbt";

    public static IExpandedProbeInfo addTanks(BlockEntity tile, IExpandedProbeInfo element, boolean showEmpty) {
        if (tile instanceof IFluidHandler) {
            ProbePluginHelper.addTanks((IFluidHandler)tile, element, showEmpty);
        } else {
            LazyOptional optional = tile.getCapability(ForgeCapabilities.FLUID_HANDLER);
            if (optional.isPresent()) {
                ProbePluginHelper.addTanks((IFluidHandler)optional.orElse((Object)EmptyFluidHandler.INSTANCE), element, showEmpty);
            }
        }
        return element;
    }

    public static void addTanks(IFluidHandler fluidHandler, IExpandedProbeInfo element, boolean showEmpty) {
        for (int i = 0; i < fluidHandler.getTanks(); ++i) {
            FluidStack fluidStack = fluidHandler.getFluidInTank(i);
            if ((!fluidStack.isEmpty() || !showEmpty) && fluidStack.isEmpty()) continue;
            element.tankSimple(fluidHandler.getTankCapacity(i), fluidStack, IC2Styles.tank(fluidStack.getFluid() == Fluids.f_76195_ ? -29925 : IClientFluidTypeExtensions.of((Fluid)fluidStack.getFluid()).getTintColor() | 0xFF000000, fluidStack.isEmpty() ? Component.m_237113_((String)"Empty") : (MutableComponent)fluidStack.getDisplayName(), fluidStack.getAmount(), fluidHandler.getTankCapacity(i)));
        }
    }

    public static String formatInt(int number, int digits) {
        return ProbePluginHelper.formatInt(number, digits, false);
    }

    public static String formatNumber(double number, int digits) {
        return ProbePluginHelper.formatNumber(number, digits, false);
    }

    public static String formatInt(int number, int digits, boolean fixedLength) {
        return ProbePluginHelper.formatNumber(number, digits, fixedLength);
    }

    public static String formatNumber(double number, int digits, boolean fixedLength) {
        String suffix = "";
        boolean allow = (number >= 1.0E9 ? String.valueOf((long)number) : String.valueOf(number)).length() > digits;
        double outputNumber = number;
        for (int i = 0; i < SUFFIX.length() && outputNumber >= 1000.0 && allow; outputNumber /= 1000.0, ++i) {
            suffix = Character.toString(SUFFIX.charAt(i));
        }
        int actualDigits = digits - suffix.length();
        if (outputNumber % 1.0 == 1.0) {
            ++actualDigits;
        }
        int naturalLength = NATURAL.format((int)outputNumber).length();
        int decimalLength = DECIMAL.format(outputNumber - (double)((int)outputNumber)).length();
        StringBuilder patternBuilder = new StringBuilder();
        int i = 1;
        while (actualDigits > 1 && naturalLength > 1) {
            patternBuilder.insert(0, "#");
            if (i % 2 == 0 && --actualDigits > 1 && --naturalLength > 1) {
                if (actualDigits == 2 || naturalLength == 2) break;
                patternBuilder.insert(0, ",");
                --actualDigits;
                --naturalLength;
            }
            ++i;
        }
        patternBuilder.append("0");
        if (actualDigits > 1 && decimalLength > 0) {
            patternBuilder.append(".");
            --actualDigits;
            while (actualDigits > 0 && decimalLength > 0) {
                patternBuilder.append("#");
                --actualDigits;
                --decimalLength;
            }
        }
        String pattern = patternBuilder.toString();
        Object output = new DecimalFormat(pattern + suffix, US).format(outputNumber);
        String fill = "";
        int length = ((String)output).length();
        if (fixedLength && ((String)output).length() < digits) {
            for (int i2 = 0; i2 < digits - length; ++i2) {
                fill = fill.concat(" ");
            }
            output = fill + (String)output;
        }
        return output;
    }

    public static IElement generateEUBar(BlockEntity tile) {
        return ProbePluginHelper.generateEUBar(tile, 118, 12, false);
    }

    public static IElement generateHiddenBar(BlockEntity tile) {
        return ProbePluginHelper.generateEUBar(tile, 118, 12, true);
    }

    public static IElement generateEUBar(BlockEntity tile, int width) {
        return ProbePluginHelper.generateEUBar(tile, width, 12, false);
    }

    public static IElement generateEUBar(BlockEntity tile, int width, int height, boolean hideMax) {
        if (tile instanceof IEUStorage) {
            IEUStorage eu = (IEUStorage)tile;
            if ((hideMax || eu.getStoredEU() == eu.getMaxEU() && eu.getMaxEU() < 100000000) && eu.getStoredEU() > 0) {
                return new ElementProgress((long)eu.getStoredEU(), (long)eu.getMaxEU(), IC2Styles.EU_BAR.copy().numberFormat(NumberFormat.NONE).prefix("ic2.probe.eu.storage.name", new Object[]{ProbePluginHelper.formatNumber(eu.getStoredEU(), 10)}).bounds(width, height));
            }
            int[] range = ProbePluginHelper.generateDigitRange(eu.getMaxEU());
            return new ElementProgress((long)eu.getStoredEU(), (long)eu.getMaxEU(), IC2Styles.EU_BAR.copy().numberFormat(NumberFormat.NONE).prefix("ic2.probe.eu.storage.name", new Object[]{ProbePluginHelper.formatInt(eu.getStoredEU(), range[0]) + "/" + ProbePluginHelper.formatInt(eu.getMaxEU(), range[1])}).bounds(width, height));
        }
        if (tile instanceof TeleporterTileEntity) {
            TeleporterTileEntity eu = (TeleporterTileEntity)tile;
            long current = eu.getAvailableEnergy();
            long max = eu.getMaxEnergy();
            int[] range = ProbePluginHelper.generateDigitRange(max);
            return new ElementProgress(current, max, IC2Styles.EU_BAR.copy().numberFormat(NumberFormat.NONE).prefix("ic2.probe.eu.storage.name", new Object[]{ProbePluginHelper.formatNumber(current, range[0]) + "/" + ProbePluginHelper.formatNumber(max, range[1])}).bounds(width, height));
        }
        return null;
    }

    private static int[] generateDigitRange(long maxEU) {
        while (maxEU >= 1000L) {
            maxEU /= 1000L;
        }
        if (maxEU >= 100L) {
            return new int[]{4, 3};
        }
        if (maxEU >= 10L) {
            return new int[]{5, 3};
        }
        if (maxEU >= 1L) {
            return new int[]{6, 2};
        }
        return new int[]{6, 2};
    }

    public static Map<Component, List<ItemStack>> generateSlots(BlockEntity tile, boolean showEmpty, SlotType ... types) {
        Object2ObjectSortedMap resultMap = CollectionUtils.createLinkedMap();
        if (tile instanceof IHasInventoryHandler) {
            InventoryHandler inventoryHandler = ((IHasInventoryHandler)tile).getInventoryHandler();
            for (SlotType type : types) {
                ObjectList stacks = CollectionUtils.createList();
                IntListIterator intListIterator = inventoryHandler.getAllOfType(type).iterator();
                while (intListIterator.hasNext()) {
                    int slot = (Integer)intListIterator.next();
                    ItemStack inputStack = inventoryHandler.getInventory().getStackInSlot(slot);
                    if (inputStack.m_41619_()) continue;
                    stacks.add((ItemStack)inputStack);
                }
                if (!showEmpty && stacks.isEmpty()) continue;
                if (stacks.isEmpty()) {
                    stacks.add((ItemStack)ItemStack.f_41583_);
                }
                resultMap.put(type.getName().m_6881_().m_130946_(": "), stacks);
            }
        }
        return resultMap;
    }

    public static void generateAnySlots(BlockEntity tile, boolean showEmpty, IProbeInfo elements) {
        Object2ObjectSortedMap slots = CollectionUtils.createLinkedMap();
        if (tile instanceof IHasInventoryHandler) {
            InventoryHandler inventoryHandler = ((IHasInventoryHandler)tile).getInventoryHandler();
            for (SlotType type : inventoryHandler.getSlotsOfType(SlotType.ROOT).keySet()) {
                ObjectList stacks = CollectionUtils.createList();
                IntListIterator intListIterator = inventoryHandler.getAllOfType(type).iterator();
                while (intListIterator.hasNext()) {
                    int slot = (Integer)intListIterator.next();
                    ItemStack inputStack = inventoryHandler.getInventory().getStackInSlot(slot);
                    if (inputStack.m_41619_() && !showEmpty) continue;
                    stacks.add((ItemStack)inputStack);
                }
                if (!showEmpty && stacks.isEmpty()) continue;
                if (stacks.isEmpty()) {
                    stacks.add((ItemStack)ItemStack.f_41583_);
                }
                slots.put(type.getName().m_6881_().m_130946_(": "), stacks);
            }
        }
        if (!slots.isEmpty()) {
            Panel mainContainer = new Panel(Panel.Type.VERTICAL);
            mainContainer.text(ProbePluginHelper.translate("ic2.probe.slots"), ITextStyleBuilder.create().bottomPadding(2));
            mainContainer.vertical(IC2Styles.SLOT_CONTAINER_STYLE).vertical(IC2Styles.SLOTS_STYLE).element(new GridElement((Map<Component, List<ItemStack>>)slots));
            elements.element((IElement)mainContainer);
        }
    }

    public static void generateDefaultSlots(BlockEntity tile, boolean showEmpty, IProbeInfo elements) {
        ProbePluginHelper.generateSlots(tile, showEmpty, elements, SlotType.INPUTS, SlotType.OUTPUTS, SlotType.BATTERY, SlotType.FUEL, SlotType.CHARGE);
    }

    public static void generateSlots(BlockEntity tile, boolean showEmpty, IProbeInfo elements, SlotType ... types) {
        ProbePluginHelper.generateSlotInfo(ProbePluginHelper.generateSlots(tile, showEmpty, types), elements);
    }

    public static void generateSlotInfo(Component name, List<ItemStack> slots, boolean showEmpty, IProbeInfo info) {
        if (slots.isEmpty() && !showEmpty) {
            return;
        }
        ProbePluginHelper.generateSlotInfo((Map<Component, List<ItemStack>>)Object2ObjectMaps.singleton((Object)name, slots), info);
    }

    public static void generateSlotInfo(Map<Component, List<ItemStack>> slots, IProbeInfo info) {
        if (!slots.isEmpty()) {
            Panel mainContainer = new Panel(Panel.Type.VERTICAL);
            mainContainer.text(ProbePluginHelper.translate("ic2.probe.slots"), ITextStyleBuilder.create().bottomPadding(2));
            mainContainer.vertical(IC2Styles.SLOT_CONTAINER_STYLE).vertical(IC2Styles.SLOTS_STYLE).element(new GridElement(slots));
            info.element((IElement)mainContainer);
        }
    }

    static Component translate(String s) {
        return Component.m_237115_((String)s);
    }
}

