/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.CrashReport
 *  net.minecraft.CrashReportCategory
 *  net.minecraft.CrashReportDetail
 *  net.minecraft.ReportedException
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.slot.SlotHologramSlot;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class HandHeldInventory
implements IHasGui {
    protected ItemStack containerStack;
    protected final ItemStack[] inventory;
    public final Player player;
    protected final InteractionHand hand;
    private boolean cleared;
    private static final Set<Player> PLAYERS_IN_GUI = new HashSet<Player>();

    public HandHeldInventory(Player player, InteractionHand interactionHand, ItemStack itemStack, int n) {
        this.containerStack = itemStack;
        this.inventory = new ItemStack[n];
        this.player = player;
        this.hand = interactionHand;
        if (IC2.sideProxy.isSimulating()) {
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
            if (!compoundTag.m_128425_("uid", 3)) {
                compoundTag.m_128405_("uid", IC2.random.m_188502_());
            }
            ListTag listTag = compoundTag.m_128437_("Items", 10);
            for (int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag2 = listTag.m_128728_(i);
                byte by = compoundTag2.m_128445_("Slot");
                if (by < 0 || by >= this.inventory.length) continue;
                this.inventory[by] = ItemStack.m_41712_((CompoundTag)compoundTag2);
            }
        }
    }

    public int m_6643_() {
        return this.inventory.length;
    }

    public boolean m_7983_() {
        for (ItemStack itemStack : this.inventory) {
            if (StackUtil.isEmpty(itemStack)) continue;
            return false;
        }
        return true;
    }

    public ItemStack m_8020_(int n) {
        return StackUtil.wrapEmpty(this.inventory[n]);
    }

    public ItemStack m_7407_(int n, int n2) {
        ItemStack itemStack;
        if (n >= 0 && n < this.inventory.length && !StackUtil.isEmpty(itemStack = this.inventory[n])) {
            ItemStack itemStack2;
            if (n2 >= StackUtil.getSize(itemStack)) {
                itemStack2 = itemStack;
                this.inventory[n] = StackUtil.emptyStack;
            } else {
                itemStack2 = StackUtil.copyWithSize(itemStack, n2);
                this.inventory[n] = StackUtil.decSize(itemStack, n2);
            }
            this.save();
            return itemStack2;
        }
        return StackUtil.emptyStack;
    }

    public void m_6836_(int n, ItemStack itemStack) {
        if (!StackUtil.isEmpty(itemStack) && StackUtil.getSize(itemStack) > this.m_6893_()) {
            itemStack = StackUtil.copyWithSize(itemStack, this.m_6893_());
        }
        this.inventory[n] = StackUtil.isEmpty(itemStack) ? StackUtil.emptyStack : itemStack;
        this.save();
    }

    public int m_6893_() {
        return 64;
    }

    public boolean m_7013_(int n, ItemStack itemStack) {
        return false;
    }

    public void m_6596_() {
        this.save();
    }

    public boolean m_6542_(Player player) {
        return player == this.player && this.getPlayerInventoryIndex() >= -1;
    }

    public ItemStack m_8016_(int n) {
        ItemStack itemStack = this.m_8020_(n);
        if (!StackUtil.isEmpty(itemStack)) {
            this.m_6836_(n, null);
        }
        return itemStack;
    }

    @Override
    public void onScreenClosed(Player player) {
        this.save();
        if (!player.m_20193_().f_46443_) {
            if (PLAYERS_IN_GUI.contains(player)) {
                PLAYERS_IN_GUI.remove(player);
            } else {
                StackUtil.getOrCreateNbtData(this.containerStack).m_128473_("uid");
            }
        }
    }

    public ItemStack getContainerStack() {
        return this.containerStack;
    }

    public boolean isThisContainer(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack) || itemStack.m_41720_() != this.containerStack.m_41720_()) {
            return false;
        }
        CompoundTag compoundTag = itemStack.m_41783_();
        return compoundTag != null && compoundTag.m_128451_("uid") == this.getUid();
    }

    protected int getUid() {
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(this.containerStack);
        return compoundTag.m_128451_("uid");
    }

    protected int getPlayerInventoryIndex() {
        ItemStack itemStack = this.player.f_36096_.m_142621_();
        if (this.isThisContainer(itemStack)) {
            return -1;
        }
        for (int i = 0; i < this.player.m_150109_().m_6643_(); ++i) {
            ItemStack itemStack2 = this.player.m_150109_().m_8020_(i);
            if (!this.isThisContainer(itemStack2)) continue;
            return i;
        }
        return Integer.MIN_VALUE;
    }

    protected void save() {
        CompoundTag compoundTag;
        int n;
        if (!IC2.sideProxy.isSimulating()) {
            return;
        }
        if (this.cleared) {
            return;
        }
        boolean bl = false;
        for (int i = 0; i < this.inventory.length; ++i) {
            if (!this.isThisContainer(this.inventory[i])) continue;
            this.inventory[i] = null;
            bl = true;
        }
        ListTag listTag = new ListTag();
        for (n = 0; n < this.inventory.length; ++n) {
            if (StackUtil.isEmpty(this.inventory[n])) continue;
            compoundTag = new CompoundTag();
            compoundTag.m_128344_("Slot", (byte)n);
            this.inventory[n].m_41739_(compoundTag);
            listTag.add((Object)compoundTag);
        }
        StackUtil.getOrCreateNbtData(this.containerStack).m_128365_("Items", (Tag)listTag);
        try {
            this.containerStack = StackUtil.copyWithSize(this.containerStack, 1);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            compoundTag = new CrashReport("Hand held container stack vanished", (Throwable)illegalArgumentException);
            CrashReportCategory crashReportCategory = compoundTag.m_127514_("Container stack");
            crashReportCategory.m_128159_("Stack", (Object)StackUtil.toStringSafe(this.containerStack));
            crashReportCategory.m_128159_("NBT", (Object)this.containerStack.m_41783_());
            crashReportCategory.m_128159_("Position", (Object)this.getPlayerInventoryIndex());
            crashReportCategory.m_128159_("Had thrown", (Object)bl);
            crashReportCategory = compoundTag.m_127514_("Container info");
            crashReportCategory.m_128159_("Type", (Object)this.getClass().getName());
            crashReportCategory.m_128159_("Container", this.player.f_36096_ == null ? null : this.player.f_36096_.getClass().getName());
            if (this.player.f_19853_.f_46443_) {
                crashReportCategory.m_128165_("GUI", (CrashReportDetail)new CrashReportDetail<String>(){

                    public String call() throws Exception {
                        Screen screen = Minecraft.m_91087_().f_91080_;
                        return screen == null ? null : screen.getClass().getName();
                    }
                });
            }
            crashReportCategory.m_128159_("Opened by", (Object)this.player);
            throw new ReportedException((CrashReport)compoundTag);
        }
        if (bl) {
            StackUtil.dropAsEntity(this.player.m_20193_(), this.player.m_20183_(), this.containerStack);
            this.m_6211_();
        } else {
            n = this.getPlayerInventoryIndex();
            if (n < -1) {
                IC2.log.warn(LogCategory.Item, "Handheld inventory saving failed for player " + this.player.m_5446_().getString() + ".");
                this.m_6211_();
            } else if (n == -1) {
                this.player.f_36096_.m_142503_(this.containerStack);
            } else {
                this.player.m_150109_().m_6836_(n, this.containerStack);
            }
        }
    }

    public void saveAsThrown(ItemStack itemStack) {
        assert (IC2.sideProxy.isSimulating());
        ListTag listTag = new ListTag();
        for (int i = 0; i < this.inventory.length; ++i) {
            if (StackUtil.isEmpty(this.inventory[i]) || this.isThisContainer(this.inventory[i])) continue;
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.m_128344_("Slot", (byte)i);
            this.inventory[i].m_41739_(compoundTag);
            listTag.add((Object)compoundTag);
        }
        StackUtil.getOrCreateNbtData(itemStack).m_128365_("Items", (Tag)listTag);
        assert (StackUtil.getOrCreateNbtData(itemStack).m_128451_("uid") == 0);
        this.m_6211_();
    }

    public void m_6211_() {
        for (int i = 0; i < this.inventory.length; ++i) {
            this.inventory[i] = null;
        }
        this.cleared = true;
    }

    public SlotHologramSlot.ChangeCallback makeSaveCallback() {
        return new SlotHologramSlot.ChangeCallback(){

            @Override
            public void onChanged(int n) {
                HandHeldInventory.this.save();
            }
        };
    }

    public void onEvent(String string) {
    }

    public static void addMaintainedPlayer(Player player) {
        PLAYERS_IN_GUI.add(player);
    }
}

