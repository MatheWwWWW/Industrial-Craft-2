/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.upgrade;

import ic2.api.network.ClientModifiable;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.gui.dynamic.DynamicHandHeldContainer;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.gui.dynamic.IGuiConditionProvider;
import ic2.core.gui.dynamic.IHolographicSlotProvider;
import ic2.core.item.ContainerHandHeldInventory;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.item.upgrade.HandHeldOre;
import ic2.core.item.upgrade.HandHeldUpgradeOption;
import ic2.core.item.upgrade.HandHeldValueConfig;
import ic2.core.item.upgrade.NbtSettings;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class HandHeldAdvancedUpgrade
extends HandHeldInventory
implements IHolographicSlotProvider,
IGuiConditionProvider {
    @GuiSynced
    protected boolean meta;
    @GuiSynced
    protected boolean energy;
    @ClientModifiable
    protected NbtSettings nbt;
    private static final int META_GUI = 0;
    private static final int DAMAGE_GUI = 1;
    private static final int ENERGY_GUI = 2;
    private static final int ORE_GUI = 3;
    private static final ResourceLocation GUI_XML = IC2.getIdentifier("advanced_upgrade");

    private static ItemStack checkContainerStack(Player player, ItemStack itemStack) {
        if (!player.m_20193_().f_46443_ && player.f_36096_ instanceof ContainerHandHeldInventory && ((ContainerHandHeldInventory)player.f_36096_).base instanceof HandHeldUpgradeOption) {
            HandHeldAdvancedUpgrade.addMaintainedPlayer(player);
            return ((HandHeldInventory)((ContainerHandHeldInventory)player.f_36096_).base).getContainerStack();
        }
        return itemStack;
    }

    public HandHeldAdvancedUpgrade(Player player, InteractionHand interactionHand, ItemStack itemStack) {
        super(player, interactionHand, HandHeldAdvancedUpgrade.checkContainerStack(player, itemStack), 9);
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        this.meta = HandHeldAdvancedUpgrade.readTag(compoundTag, "meta");
        this.nbt = NbtSettings.getFromNBT(HandHeldAdvancedUpgrade.getTag(compoundTag, "nbt").m_128445_("type"));
        this.energy = HandHeldAdvancedUpgrade.readTag(compoundTag, "energy");
    }

    @Override
    protected void save() {
        super.save();
        if (IC2.sideProxy.isSimulating()) {
            CompoundTag compoundTag = this.containerStack.m_41783_();
            assert (compoundTag != null);
            HandHeldAdvancedUpgrade.writeTag(compoundTag, "meta", this.meta);
            CompoundTag compoundTag2 = HandHeldAdvancedUpgrade.getTag(compoundTag, "nbt");
            compoundTag2.m_128379_("active", this.nbt.enabled());
            compoundTag2.m_128344_("type", this.nbt.getForNBT());
            compoundTag.m_128365_("nbtSettings", (Tag)compoundTag2);
            HandHeldAdvancedUpgrade.writeTag(compoundTag, "energy", this.energy);
        }
    }

    public static CompoundTag getTag(CompoundTag compoundTag, String string) {
        return compoundTag.m_128469_(string + "Settings");
    }

    protected static boolean readTag(CompoundTag compoundTag, String string) {
        return HandHeldAdvancedUpgrade.getTag(compoundTag, string).m_128471_("active");
    }

    protected static void writeTag(CompoundTag compoundTag, String string, boolean bl) {
        CompoundTag compoundTag2 = HandHeldAdvancedUpgrade.getTag(compoundTag, string);
        compoundTag2.m_128379_("active", bl);
        compoundTag.m_128365_(string + "Settings", (Tag)compoundTag2);
    }

    static IHasGui delegate(Player player, InteractionHand interactionHand, ItemStack itemStack, int n) {
        switch (n) {
            case 0: {
                return new HandHeldValueConfig(new HandHeldAdvancedUpgrade(player, interactionHand, itemStack), "meta");
            }
            case 1: {
                return null;
            }
            case 2: {
                return new HandHeldValueConfig(new HandHeldAdvancedUpgrade(player, interactionHand, itemStack), "energy");
            }
            case 3: {
                return new HandHeldOre(new HandHeldAdvancedUpgrade(player, interactionHand, itemStack));
            }
        }
        IC2.log.warn(LogCategory.Network, "Unexpected delegate ID: " + n);
        return null;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return DynamicHandHeldContainer.create(Ic2ScreenHandlers.ADVANCED_UPGRADE, n, player.m_150109_(), this, this.getNode());
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicHandHeldContainer.create(Ic2ScreenHandlers.ADVANCED_UPGRADE, n, this.player.m_150109_(), this, this.getNode());
    }

    protected GuiParser.GuiNode getNode() {
        return GuiParser.parse(GUI_XML, HandHeldAdvancedUpgrade.class);
    }

    Player getPlayer() {
        return this.player;
    }

    InteractionHand getHand() {
        return this.hand;
    }

    @Override
    public ItemStack[] getStacksForName(String string) {
        if ("filter".equals(string)) {
            return this.inventory;
        }
        throw new IllegalArgumentException("Unexpected stack array name requested: " + string);
    }

    @Override
    public boolean getGuiState(String string) {
        if ("meta".equals(string)) {
            return this.meta;
        }
        if ("nbt".equals(string)) {
            return this.nbt.enabled();
        }
        if ("energy".equals(string)) {
            return this.energy || this.nbt == NbtSettings.EXACT;
        }
        if ("dev".equals(string)) {
            return Util.inDev();
        }
        throw new IllegalArgumentException("Unexpected conditional name requested: " + string);
    }

    @Override
    public void onEvent(String string) {
        boolean bl = false;
        if (Util.inDev() && string.endsWith("Dev")) {
            bl = true;
            string = string.substring(0, string.lastIndexOf("Dev"));
        }
        if ("meta".equals(string)) {
            if (!bl) {
                this.meta = !this.meta;
            } else {
                new HandHeldValueConfig(this, "meta").openManagedItem(this.player, this.hand, 0);
            }
        } else if ("energy".equals(string)) {
            if (!bl) {
                this.energy = !this.energy;
            } else {
                new HandHeldValueConfig(this, "energy").openManagedItem(this.player, this.hand, 2);
            }
        } else if ("ore".equals(string)) {
            assert (bl);
            new HandHeldOre(this).openManagedItem(this.player, this.hand, 3);
        } else {
            super.onEvent(string);
        }
    }
}

