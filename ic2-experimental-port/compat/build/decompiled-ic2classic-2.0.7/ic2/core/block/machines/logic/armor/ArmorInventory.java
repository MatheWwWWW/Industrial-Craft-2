/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machines.logic.armor;

import ic2.api.items.armor.IArmorModule;
import ic2.core.block.machines.logic.armor.OverflowInventory;
import ic2.core.block.machines.tiles.nv.ArmorConfiguratorTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;

public class ArmorInventory {
    public static final IHasInventory EMPTY = new OverflowInventory(0);
    protected ArmorConfiguratorTileEntity owner;
    protected SimpleInventory armor = new SimpleInventory(1);
    protected Map<IArmorModule.ModuleType, SimpleInventory> modules = CollectionUtils.createLinkedMap();
    protected List<IArmorModule.ModuleType> activeTypes = CollectionUtils.createList();
    protected Object2IntMap<IArmorModule.ModuleType> invSizes = new Object2IntOpenHashMap();

    public ArmorInventory(ArmorConfiguratorTileEntity owner) {
        this.owner = owner;
        for (IArmorModule.ModuleType type : IArmorModule.ModuleType.getAllTypes()) {
            this.modules.put(type, new OverflowInventory(9));
        }
    }

    public IHasInventory getArmor() {
        return this.armor;
    }

    public int getTypes() {
        return this.activeTypes.size();
    }

    public IHasInventory getSlotInventory(int index) {
        return (index += this.owner.offset) < 0 || index >= this.activeTypes.size() ? EMPTY : (IHasInventory)this.modules.get(this.activeTypes.get(index));
    }

    public int getSize(int index) {
        return (index += this.owner.offset) < 0 || index >= this.activeTypes.size() ? 0 : this.invSizes.getInt((Object)this.activeTypes.get(index));
    }

    public IArmorModule.ModuleType getType(int index) {
        return (index += this.owner.offset) < 0 || index >= this.activeTypes.size() ? null : this.activeTypes.get(index);
    }

    public CompoundTag writeToNBT(CompoundTag nbt) {
        nbt.m_128365_("armor", (Tag)this.armor.save(new CompoundTag()));
        return nbt;
    }

    public void readFromNBT(CompoundTag nbt) {
        this.armor.load(nbt.m_128469_("armor"));
        this.onSlotSet();
    }

    public void onSlotSet() {
        this.reset();
        ItemStack armorStack = this.armor.getStackInSlot(0);
        if (armorStack.m_41720_() instanceof IArmorModule.IArmorModuleHolder) {
            IArmorModule.IArmorModuleHolder holder = (IArmorModule.IArmorModuleHolder)armorStack.m_41720_();
            for (Object2IntMap.Entry entry : holder.getModuleLimits(armorStack).object2IntEntrySet()) {
                this.activeTypes.add((IArmorModule.ModuleType)entry.getKey());
                this.invSizes.put((Object)((IArmorModule.ModuleType)entry.getKey()), entry.getIntValue());
            }
            for (Object2IntMap.Entry entry : IC2ModularElectricArmor.getModuleTypes(armorStack).object2ObjectEntrySet()) {
                List list = (List)entry.getValue();
                IHasInventory inv = this.modules.get(entry.getKey());
                if (inv == null) continue;
                int size = Math.min(this.invSizes.getInt(entry.getKey()), 9);
                int m = Math.min(list.size(), size);
                for (int i = 0; i < m; ++i) {
                    ItemStack subStack = (ItemStack)list.get(i);
                    inv.setStackInSlot(i, subStack);
                }
            }
        }
        this.activeTypes.sort(null);
        this.owner.syncArmor();
    }

    public void reset() {
        this.activeTypes.clear();
        this.invSizes.clear();
        for (SimpleInventory inv : this.modules.values()) {
            inv.clear();
        }
        this.owner.syncArmor();
    }

    public void save() {
        if (this.armor.getStackInSlot(0).m_41619_()) {
            return;
        }
        Object2ObjectSortedMap maps = CollectionUtils.createLinkedMap();
        int m = this.activeTypes.size();
        for (int i = 0; i < m; ++i) {
            SimpleInventory inv = this.modules.get(this.activeTypes.get(i));
            if (inv == null) continue;
            ObjectList list = CollectionUtils.createList();
            int n = inv.getSlotCount();
            for (int j = 0; j < n; ++j) {
                ItemStack stack = inv.getStackInSlot(j);
                if (stack.m_41619_()) continue;
                list.add((ItemStack)stack);
            }
            maps.put(this.activeTypes.get(i), list);
        }
        IC2ModularElectricArmor.setModuleTypes(this.armor.getStackInSlot(0), maps);
        this.owner.syncArmor();
    }

    public boolean canInsert(ItemStack stack, int index) {
        ItemStack armorSlot = this.armor.getStackInSlot(0);
        if (!(stack.m_41720_() instanceof IArmorModule) || !(armorSlot.m_41720_() instanceof IArmorModule.IArmorModuleHolder)) {
            return false;
        }
        IArmorModule module = (IArmorModule)stack.m_41720_();
        if (!module.canInstallInArmor(stack, armorSlot, Mob.m_147233_((ItemStack)armorSlot))) {
            return false;
        }
        IArmorModule.ModuleType type = module.getType(stack);
        IArmorModule.ModuleType other = this.getType(index);
        return type == IArmorModule.ModuleType.ANY && other != IArmorModule.ModuleType.HUD || type == other;
    }

    public void onInserted(ItemStack stack) {
        ItemStack armorStack = this.armor.getStackInSlot(0);
        if (!(stack.m_41720_() instanceof IArmorModule) || armorStack.m_41619_()) {
            return;
        }
        IArmorModule module = (IArmorModule)stack.m_41720_();
        module.onInstall(stack, armorStack, (IArmorModule.IArmorModuleHolder)armorStack.m_41720_());
        this.save();
    }

    public void onExtracted(ItemStack stack) {
        ItemStack armorStack = this.armor.getStackInSlot(0);
        if (!(stack.m_41720_() instanceof IArmorModule) || armorStack.m_41619_()) {
            return;
        }
        IArmorModule module = (IArmorModule)stack.m_41720_();
        module.onUninstall(stack, armorStack, (IArmorModule.IArmorModuleHolder)armorStack.m_41720_());
        this.save();
    }
}

