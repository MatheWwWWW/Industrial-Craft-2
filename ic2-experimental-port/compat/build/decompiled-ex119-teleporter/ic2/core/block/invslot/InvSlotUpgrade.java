/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.api.upgrade.IAugmentationUpgrade;
import ic2.api.upgrade.IEnergyStorageUpgrade;
import ic2.api.upgrade.IFullUpgrade;
import ic2.api.upgrade.IProcessingUpgrade;
import ic2.api.upgrade.IRedstoneSensitiveUpgrade;
import ic2.api.upgrade.IRemoteAccessUpgrade;
import ic2.api.upgrade.ITransformerUpgrade;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.IUpgradeItem;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.comp.Redstone;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InvSlotUpgrade
extends InvSlot {
    private static final int maxStackSize = 64;
    public int augmentation;
    public int extraProcessTime;
    public double processTimeMultiplier;
    public int extraEnergyDemand;
    public double energyDemandMultiplier;
    public int extraEnergyStorage;
    public double energyStorageMultiplier;
    public int extraTier;
    private List<Redstone.IRedstoneModifier> redstoneModifiers = Collections.emptyList();

    public static InvSlotUpgrade createUnchecked(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n) {
        return new InvSlotUpgrade((IInventorySlotHolder)((Object)((IUpgradableBlock)((Object)iInventorySlotHolder))), string, n);
    }

    public <T extends IInventorySlotHolder<?> & IUpgradableBlock> InvSlotUpgrade(T t, String string, int n) {
        super(t, string, InvSlot.Access.NONE, n);
        this.resetRates();
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        Item item = itemStack.m_41720_();
        if (!(item instanceof IUpgradeItem)) {
            return false;
        }
        IUpgradeItem iUpgradeItem = (IUpgradeItem)item;
        return iUpgradeItem.isSuitableFor(itemStack, ((IUpgradableBlock)((Object)this.base)).getUpgradableProperties());
    }

    @Override
    public void onChanged() {
        Object object;
        this.resetRates();
        IUpgradableBlock iUpgradableBlock = (IUpgradableBlock)((Object)this.base);
        ArrayList<Redstone.IRedstoneModifier> arrayList = new ArrayList<Redstone.IRedstoneModifier>();
        for (int i = 0; i < this.size(); ++i) {
            IUpgradeItem iUpgradeItem;
            ItemStack object2 = this.get(i);
            if (StackUtil.isEmpty(object2) || !this.accepts(object2)) continue;
            object = (IUpgradeItem)object2.m_41720_();
            boolean bl = object instanceof IFullUpgrade;
            int n = StackUtil.getSize(object2);
            if (bl || object instanceof IAugmentationUpgrade) {
                this.augmentation += ((IAugmentationUpgrade)object).getAugmentation(object2, iUpgradableBlock) * n;
            }
            if (bl || object instanceof IProcessingUpgrade) {
                iUpgradeItem = (IProcessingUpgrade)object;
                this.extraProcessTime += iUpgradeItem.getExtraProcessTime(object2, iUpgradableBlock) * n;
                this.processTimeMultiplier *= Math.pow(iUpgradeItem.getProcessTimeMultiplier(object2, iUpgradableBlock), n);
                this.extraEnergyDemand += iUpgradeItem.getExtraEnergyDemand(object2, iUpgradableBlock) * n;
                this.energyDemandMultiplier *= Math.pow(iUpgradeItem.getEnergyDemandMultiplier(object2, iUpgradableBlock), n);
            }
            if (bl || object instanceof IEnergyStorageUpgrade) {
                iUpgradeItem = (IEnergyStorageUpgrade)object;
                this.extraEnergyStorage += iUpgradeItem.getExtraEnergyStorage(object2, iUpgradableBlock) * n;
                this.energyStorageMultiplier *= Math.pow(iUpgradeItem.getEnergyStorageMultiplier(object2, iUpgradableBlock), n);
            }
            if (bl || object instanceof ITransformerUpgrade) {
                this.extraTier += ((ITransformerUpgrade)object).getExtraTier(object2, iUpgradableBlock) * n;
            }
            if (!bl && !(object instanceof IRedstoneSensitiveUpgrade) || !(iUpgradeItem = (IRedstoneSensitiveUpgrade)object).modifiesRedstoneInput(object2, iUpgradableBlock)) continue;
            arrayList.add(new UpgradeRedstoneModifier((IRedstoneSensitiveUpgrade)iUpgradeItem, object2, iUpgradableBlock));
        }
        for (TileEntityComponent tileEntityComponent : ((Ic2TileEntity)this.base.getParent()).getComponents()) {
            if (!(tileEntityComponent instanceof Redstone)) continue;
            object = (Redstone)tileEntityComponent;
            ((Redstone)object).removeRedstoneModifiers(this.redstoneModifiers);
            ((Redstone)object).addRedstoneModifiers(arrayList);
            ((Redstone)object).update();
        }
        this.redstoneModifiers = arrayList.isEmpty() ? Collections.emptyList() : arrayList;
    }

    private void resetRates() {
        this.augmentation = 0;
        this.extraProcessTime = 0;
        this.processTimeMultiplier = 1.0;
        this.extraEnergyDemand = 0;
        this.energyDemandMultiplier = 1.0;
        this.extraEnergyStorage = 0;
        this.energyStorageMultiplier = 1.0;
        this.extraTier = 0;
    }

    public int getOperationsPerTick(int n) {
        if (n == 0) {
            return 64;
        }
        return this.getOpsPerTick(this.getStackOpLen(n));
    }

    public int getOperationLength(int n) {
        if (n == 0) {
            return 1;
        }
        double d = this.getStackOpLen(n);
        int n2 = this.getOpsPerTick(d);
        return Math.max(1, (int)Math.round(d * (double)n2 / 64.0));
    }

    private double getStackOpLen(int n) {
        return ((double)n + (double)this.extraProcessTime) * 64.0 * this.processTimeMultiplier;
    }

    private int getOpsPerTick(double d) {
        return (int)Math.min(Math.ceil(64.0 / d), 2.147483647E9);
    }

    public int getEnergyDemand(int n) {
        return InvSlotUpgrade.applyModifier(n, this.extraEnergyDemand, this.energyDemandMultiplier);
    }

    public int getEnergyStorage(int n, int n2, int n3) {
        int n4 = this.getOperationLength(n2);
        int n5 = this.getEnergyDemand(n3);
        return InvSlotUpgrade.applyModifier(n, this.extraEnergyStorage + n4 * n5, this.energyStorageMultiplier);
    }

    public int getTier(int n) {
        return InvSlotUpgrade.applyModifier(n, this.extraTier, 1.0);
    }

    public int getRemoteRange(int n) {
        for (ItemStack itemStack : this) {
            IUpgradeItem iUpgradeItem;
            if (StackUtil.isEmpty(itemStack) || !this.accepts(itemStack) || !((iUpgradeItem = (IUpgradeItem)itemStack.m_41720_()) instanceof IRemoteAccessUpgrade)) continue;
            n = ((IRemoteAccessUpgrade)iUpgradeItem).getRangeAmplification(itemStack, (IUpgradableBlock)((Object)this.base), n);
        }
        return n;
    }

    private static int applyModifier(int n, int n2, double d) {
        double d2 = Math.round(((double)n + (double)n2) * d);
        return d2 > 2.147483647E9 ? Integer.MAX_VALUE : (int)d2;
    }

    public boolean tickNoMark() {
        IUpgradableBlock iUpgradableBlock = (IUpgradableBlock)((Object)this.base);
        boolean bl = false;
        for (int i = 0; i < this.size(); ++i) {
            ItemStack itemStack = this.get(i);
            if (StackUtil.isEmpty(itemStack) || !(itemStack.m_41720_() instanceof IUpgradeItem) || !((IUpgradeItem)itemStack.m_41720_()).onTick(itemStack, iUpgradableBlock)) continue;
            bl = true;
        }
        return bl;
    }

    public void tick() {
        if (this.tickNoMark()) {
            this.base.getParent().m_6596_();
        }
    }

    private static class UpgradeRedstoneModifier
    implements Redstone.IRedstoneModifier {
        private final IRedstoneSensitiveUpgrade upgrade;
        private final ItemStack stack;
        private final IUpgradableBlock block;

        UpgradeRedstoneModifier(IRedstoneSensitiveUpgrade iRedstoneSensitiveUpgrade, ItemStack itemStack, IUpgradableBlock iUpgradableBlock) {
            this.upgrade = iRedstoneSensitiveUpgrade;
            this.stack = itemStack.m_41777_();
            this.block = iUpgradableBlock;
        }

        @Override
        public int getRedstoneInput(int n) {
            return this.upgrade.getRedstoneInput(this.stack, this.block, n);
        }
    }
}

