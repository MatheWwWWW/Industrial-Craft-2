/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  net.minecraft.client.renderer.ItemMeshDefinition
 *  net.minecraft.client.renderer.block.model.ModelBakery
 *  net.minecraft.client.renderer.block.model.ModelResourceLocation
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.upgrade;

import com.google.common.base.Predicate;
import ic2.api.item.ElectricItem;
import ic2.api.item.ICustomDamageItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.api.upgrade.IFullUpgrade;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.api.upgrade.UpgradeRegistry;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.state.IIdProvider;
import ic2.core.gui.dynamic.DynamicHandHeldContainer;
import ic2.core.init.Localization;
import ic2.core.item.IHandHeldSubInventory;
import ic2.core.item.ItemIC2;
import ic2.core.item.ItemMulti;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.item.upgrade.ComparisonType;
import ic2.core.item.upgrade.HandHeldAdvancedUpgrade;
import ic2.core.item.upgrade.NbtSettings;
import ic2.core.item.upgrade.Settings;
import ic2.core.ref.ItemName;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemUpgradeModule
extends ItemMulti<UpgradeType>
implements IFullUpgrade,
IHandHeldSubInventory,
IItemHudInfo {
    private static final DecimalFormat decimalformat = new DecimalFormat("0.##");
    private static final List<StackUtil.AdjacentInv> emptyInvList = Collections.emptyList();
    private static final List<LiquidUtil.AdjacentFluidHandler> emptyFhList = Collections.emptyList();

    public ItemUpgradeModule() {
        super(ItemName.upgrade, UpgradeType.class);
        this.func_77627_a(true);
        for (UpgradeType type : UpgradeType.values()) {
            UpgradeRegistry.register(new ItemStack((Item)this, 1, type.getId()));
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(final ItemName name) {
        ModelLoader.setCustomMeshDefinition((Item)this, (ItemMeshDefinition)new ItemMeshDefinition(){

            public ModelResourceLocation func_178113_a(ItemStack stack) {
                EnumFacing dir;
                UpgradeType type = (UpgradeType)ItemUpgradeModule.this.getType(stack);
                if (type == null) {
                    return new ModelResourceLocation("builtin/missing", "missing");
                }
                if (type.directional && (dir = ItemUpgradeModule.getDirection(stack)) != null) {
                    return ItemIC2.getModelLocation(name, type.getName() + '_' + dir.func_176610_l());
                }
                return ItemIC2.getModelLocation(name, type.getName());
            }
        });
        for (UpgradeType type : this.typeProperty.getAllowedValues()) {
            ModelBakery.registerItemVariants((Item)this, (ResourceLocation[])new ResourceLocation[]{ItemUpgradeModule.getModelLocation(name, type.getName())});
            if (!type.directional) continue;
            for (EnumFacing dir : EnumFacing.field_82609_l) {
                ModelBakery.registerItemVariants((Item)this, (ResourceLocation[])new ResourceLocation[]{ItemUpgradeModule.getModelLocation(name, type.getName() + '_' + dir.func_176610_l())});
            }
        }
    }

    @Override
    public List<String> getHudInfo(ItemStack stack, boolean advanced) {
        LinkedList<String> info = new LinkedList<String>();
        info.add("Machine Upgrade");
        return info;
    }

    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
        super.func_77624_a(stack, world, tooltip, advanced);
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return;
        }
        switch (type) {
            case overclocker: {
                tooltip.add(Localization.translate("ic2.tooltip.upgrade.overclocker.time", decimalformat.format(100.0 * Math.pow(this.getProcessTimeMultiplier(stack, null), StackUtil.getSize(stack)))));
                tooltip.add(Localization.translate("ic2.tooltip.upgrade.overclocker.power", decimalformat.format(100.0 * Math.pow(this.getEnergyDemandMultiplier(stack, null), StackUtil.getSize(stack)))));
                break;
            }
            case transformer: {
                tooltip.add(Localization.translate("ic2.tooltip.upgrade.transformer", this.getExtraTier(stack, null) * StackUtil.getSize(stack)));
                break;
            }
            case energy_storage: {
                tooltip.add(Localization.translate("ic2.tooltip.upgrade.storage", this.getExtraEnergyStorage(stack, null) * StackUtil.getSize(stack)));
                break;
            }
            case ejector: 
            case advanced_ejector: {
                String side = ItemUpgradeModule.getSideName(stack);
                tooltip.add(Localization.translate("ic2.tooltip.upgrade.ejector", Localization.translate(side)));
                break;
            }
            case pulling: 
            case advanced_pulling: {
                String side = ItemUpgradeModule.getSideName(stack);
                tooltip.add(Localization.translate("ic2.tooltip.upgrade.pulling", Localization.translate(side)));
                break;
            }
            case fluid_ejector: {
                String side = ItemUpgradeModule.getSideName(stack);
                tooltip.add(Localization.translate("ic2.tooltip.upgrade.ejector", Localization.translate(side)));
                break;
            }
            case fluid_pulling: {
                String side = ItemUpgradeModule.getSideName(stack);
                tooltip.add(Localization.translate("ic2.tooltip.upgrade.pulling", Localization.translate(side)));
                break;
            }
            case redstone_inverter: {
                tooltip.add(Localization.translate("ic2.tooltip.upgrade.redstone"));
                break;
            }
            case remote_interface: {
                tooltip.add(Localization.translate("ic2.tooltip.upgrade.remote_interface", StackUtil.getSize(stack)));
            }
        }
    }

    private static String getSideName(ItemStack stack) {
        EnumFacing dir = ItemUpgradeModule.getDirection(stack);
        if (dir == null) {
            return "ic2.tooltip.upgrade.ejector.anyside";
        }
        switch (dir) {
            case WEST: {
                return "ic2.dir.west";
            }
            case EAST: {
                return "ic2.dir.east";
            }
            case DOWN: {
                return "ic2.dir.bottom";
            }
            case UP: {
                return "ic2.dir.top";
            }
            case NORTH: {
                return "ic2.dir.north";
            }
            case SOUTH: {
                return "ic2.dir.south";
            }
        }
        throw new RuntimeException("invalid dir: " + dir);
    }

    @Override
    public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xOffset, float yOffset, float zOffset) {
        ItemStack stack = StackUtil.get(player, hand);
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return EnumActionResult.PASS;
        }
        if (type.directional) {
            int dir = 1 + side.ordinal();
            NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
            if (nbtData.func_74771_c("dir") == dir) {
                nbtData.func_74774_a("dir", (byte)0);
            } else {
                nbtData.func_74774_a("dir", (byte)dir);
            }
            if (IC2.platform.isRendering()) {
                switch (type) {
                    case ejector: 
                    case advanced_ejector: {
                        IC2.platform.messagePlayer(player, Localization.translate("ic2.tooltip.upgrade.ejector", Localization.translate(ItemUpgradeModule.getSideName(stack))), new Object[0]);
                        break;
                    }
                    case pulling: 
                    case advanced_pulling: {
                        IC2.platform.messagePlayer(player, Localization.translate("ic2.tooltip.upgrade.pulling", Localization.translate(ItemUpgradeModule.getSideName(stack))), new Object[0]);
                        break;
                    }
                    case fluid_ejector: {
                        IC2.platform.messagePlayer(player, Localization.translate("ic2.tooltip.upgrade.ejector", Localization.translate(ItemUpgradeModule.getSideName(stack))), new Object[0]);
                        break;
                    }
                    case fluid_pulling: {
                        IC2.platform.messagePlayer(player, Localization.translate("ic2.tooltip.upgrade.pulling", Localization.translate(ItemUpgradeModule.getSideName(stack))), new Object[0]);
                        break;
                    }
                }
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type != null) {
            switch (type) {
                case advanced_ejector: 
                case advanced_pulling: {
                    HandHeldInventory base;
                    if (player.func_130014_f_().field_72995_K || StackUtil.isEmpty(stack) || !(player.field_71070_bA instanceof DynamicHandHeldContainer) || !((base = (HandHeldInventory)((DynamicHandHeldContainer)player.field_71070_bA).base) instanceof HandHeldAdvancedUpgrade) || !base.isThisContainer(stack)) break;
                    base.saveAsThrown(stack);
                    player.func_71053_j();
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isSuitableFor(ItemStack stack, Set<UpgradableProperty> types) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return false;
        }
        switch (type) {
            case ejector: 
            case advanced_ejector: {
                return types.contains((Object)UpgradableProperty.ItemProducing);
            }
            case pulling: 
            case advanced_pulling: {
                return types.contains((Object)UpgradableProperty.ItemConsuming);
            }
            case fluid_ejector: {
                return types.contains((Object)UpgradableProperty.FluidProducing);
            }
            case fluid_pulling: {
                return types.contains((Object)UpgradableProperty.FluidConsuming);
            }
            case energy_storage: {
                return types.contains((Object)UpgradableProperty.EnergyStorage);
            }
            case overclocker: {
                return types.contains((Object)UpgradableProperty.Processing) || types.contains((Object)UpgradableProperty.Augmentable);
            }
            case redstone_inverter: {
                return types.contains((Object)UpgradableProperty.RedstoneSensitive);
            }
            case transformer: {
                return types.contains((Object)UpgradableProperty.Transformer);
            }
            case remote_interface: {
                return types.contains((Object)UpgradableProperty.RemotelyAccessible);
            }
        }
        return false;
    }

    @Override
    public int getAugmentation(ItemStack stack, IUpgradableBlock parent) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return 0;
        }
        switch (type) {
            case overclocker: {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int getExtraProcessTime(ItemStack stack, IUpgradableBlock parent) {
        return 0;
    }

    @Override
    public double getProcessTimeMultiplier(ItemStack stack, IUpgradableBlock parent) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return 1.0;
        }
        switch (type) {
            case overclocker: {
                return 0.7;
            }
        }
        return 1.0;
    }

    @Override
    public int getExtraEnergyDemand(ItemStack stack, IUpgradableBlock parent) {
        return 0;
    }

    @Override
    public double getEnergyDemandMultiplier(ItemStack stack, IUpgradableBlock parent) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return 1.0;
        }
        switch (type) {
            case overclocker: {
                return 1.6;
            }
        }
        return 1.0;
    }

    @Override
    public int getExtraEnergyStorage(ItemStack stack, IUpgradableBlock parent) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return 0;
        }
        switch (type) {
            case energy_storage: {
                return 10000;
            }
        }
        return 0;
    }

    @Override
    public double getEnergyStorageMultiplier(ItemStack stack, IUpgradableBlock parent) {
        return 1.0;
    }

    @Override
    public int getExtraTier(ItemStack stack, IUpgradableBlock parent) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return 0;
        }
        switch (type) {
            case transformer: {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public boolean modifiesRedstoneInput(ItemStack stack, IUpgradableBlock parent) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return false;
        }
        switch (type) {
            case redstone_inverter: {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getRedstoneInput(ItemStack stack, IUpgradableBlock parent, int externalInput) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return externalInput;
        }
        switch (type) {
            case redstone_inverter: {
                return 15 - externalInput;
            }
        }
        return externalInput;
    }

    @Override
    public int getRangeAmplification(ItemStack stack, IUpgradableBlock parent, int existingRange) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return existingRange;
        }
        switch (type) {
            case remote_interface: {
                return existingRange << 1;
            }
        }
        return existingRange;
    }

    @Override
    public boolean onTick(ItemStack stack, IUpgradableBlock parent) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return false;
        }
        int size = StackUtil.getSize(stack);
        TileEntity te = (TileEntity)parent;
        boolean ret = false;
        switch (type) {
            case ejector: {
                int amount = (int)Math.pow(4.0, Math.min(4, size - 1));
                for (StackUtil.AdjacentInv inv : ItemUpgradeModule.getTargetInventories(stack, te)) {
                    StackUtil.transfer(te, inv.te, inv.dir, amount);
                }
                break;
            }
            case advanced_ejector: {
                int amount = (int)Math.pow(4.0, Math.min(4, size - 1));
                for (StackUtil.AdjacentInv inv : ItemUpgradeModule.getTargetInventories(stack, te)) {
                    StackUtil.transfer(te, inv.te, inv.dir, amount, ItemUpgradeModule.stackChecker(stack));
                }
                break;
            }
            case pulling: {
                int amount = (int)Math.pow(4.0, Math.min(4, size - 1));
                for (StackUtil.AdjacentInv inv : ItemUpgradeModule.getTargetInventories(stack, te)) {
                    StackUtil.transfer(inv.te, te, inv.dir.func_176734_d(), amount);
                }
                break;
            }
            case advanced_pulling: {
                int amount = (int)Math.pow(4.0, Math.min(4, size - 1));
                for (StackUtil.AdjacentInv inv : ItemUpgradeModule.getTargetInventories(stack, te)) {
                    StackUtil.transfer(inv.te, te, inv.dir.func_176734_d(), amount, ItemUpgradeModule.stackChecker(stack));
                }
                break;
            }
            case fluid_ejector: {
                if (!LiquidUtil.isFluidTile(te, null)) {
                    return false;
                }
                int amount = (int)(50.0 * Math.pow(4.0, Math.min(4, size - 1)));
                for (LiquidUtil.AdjacentFluidHandler fh : ItemUpgradeModule.getTargetFluidHandlers(stack, te)) {
                    LiquidUtil.transfer(te, fh.dir, fh.handler, amount);
                }
                break;
            }
            case fluid_pulling: {
                if (!LiquidUtil.isFluidTile(te, null)) {
                    return false;
                }
                int amount = (int)(50.0 * Math.pow(4.0, Math.min(4, size - 1)));
                for (LiquidUtil.AdjacentFluidHandler fh : ItemUpgradeModule.getTargetFluidHandlers(stack, te)) {
                    LiquidUtil.transfer(fh.handler, fh.dir.func_176734_d(), te, amount);
                }
                break;
            }
            default: {
                return false;
            }
        }
        return ret;
    }

    private static Predicate<ItemStack> stackChecker(final ItemStack stack) {
        return new Predicate<ItemStack>(){
            private boolean hasInitialised = false;
            private Set<ItemStack> filters;
            private Settings meta;
            private Settings damage;
            private Settings energy;
            private NbtSettings nbt;

            private void initalise() {
                assert (!this.hasInitialised);
                NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
                this.filters = this.getFilterStacks(tag);
                this.meta = new Settings(HandHeldAdvancedUpgrade.getTag(tag, "meta"));
                this.damage = null;
                this.nbt = NbtSettings.getFromNBT(HandHeldAdvancedUpgrade.getTag(tag, "nbt").func_74771_c("type"));
                this.energy = new Settings(HandHeldAdvancedUpgrade.getTag(tag, "energy"));
                this.hasInitialised = true;
            }

            private Set<ItemStack> getFilterStacks(NBTTagCompound nbt) {
                HashSet<ItemStack> ret = new HashSet<ItemStack>();
                NBTTagList contentList = nbt.func_150295_c("Items", 10);
                for (int tag = 0; tag < contentList.func_74745_c(); ++tag) {
                    ItemStack filter;
                    NBTTagCompound slotNbt = contentList.func_150305_b(tag);
                    byte slot = slotNbt.func_74771_c("Slot");
                    if (slot < 0 || slot >= 9 || StackUtil.isEmpty(filter = new ItemStack(slotNbt))) continue;
                    ret.add(filter);
                }
                return ret;
            }

            private boolean checkMeta(ItemStack stack2, ItemStack filter) {
                assert (this.meta.active);
                assert (this.meta.comparison == ComparisonType.DIRECT);
                return stack2.func_77960_j() == filter.func_77960_j();
            }

            private boolean checkDamage(ItemStack stack2, ItemStack filter, boolean customStack) {
                assert (this.damage.active);
                assert (this.damage.comparison == ComparisonType.DIRECT);
                return customStack && filter.func_77973_b() instanceof ICustomDamageItem ? ((ICustomDamageItem)stack2.func_77973_b()).getCustomDamage(stack2) == ((ICustomDamageItem)filter.func_77973_b()).getCustomDamage(filter) : filter.func_77952_i() == stack2.func_77952_i();
            }

            private boolean checkNBT(ItemStack stack2, ItemStack filter) {
                switch (this.nbt) {
                    case IGNORED: {
                        return true;
                    }
                    case FUZZY: {
                        return StackUtil.checkNbtEquality(stack2.func_77978_p(), filter.func_77978_p());
                    }
                    case EXACT: {
                        return StackUtil.checkNbtEqualityStrict(stack2, filter);
                    }
                }
                throw new IllegalStateException("Unexpected NBT state: " + (Object)((Object)this.nbt));
            }

            private boolean checkEnergy(ItemStack stack2, ItemStack filter) {
                assert (this.energy.active);
                assert (this.energy.comparison == ComparisonType.DIRECT);
                return filter.func_77973_b() instanceof IElectricItem && Util.isSimilar(ElectricItem.manager.getCharge(stack2), ElectricItem.manager.getCharge(filter));
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public boolean apply(ItemStack stack2) {
                boolean checkEnergy;
                boolean checkMeta;
                if (!this.hasInitialised) {
                    this.initalise();
                }
                if (!this.meta.comparison.ignoreFilters()) {
                    if (!this.meta.doComparison(stack2.func_77960_j())) return false;
                    checkMeta = false;
                } else {
                    checkMeta = this.meta.active;
                }
                boolean customStack = stack2.func_77973_b() instanceof ICustomDamageItem;
                boolean checkDamage = false;
                if (!this.energy.comparison.ignoreFilters()) {
                    if (!(stack2.func_77973_b() instanceof IElectricItem)) return false;
                    if (!this.energy.doComparison((int)ElectricItem.manager.getCharge(stack2))) return false;
                    checkEnergy = false;
                } else {
                    checkEnergy = this.energy.active;
                    if (checkEnergy && !(stack2.func_77973_b() instanceof IElectricItem)) {
                        return false;
                    }
                }
                for (ItemStack filter : this.filters) {
                    if (filter.func_77973_b() != stack2.func_77973_b() || checkMeta && !this.checkMeta(stack2, filter) || checkDamage && !this.checkDamage(stack2, filter, customStack) || !this.checkNBT(stack2, filter)) continue;
                    if (!checkEnergy) return true;
                    if (!this.checkEnergy(stack2, filter)) continue;
                    return true;
                }
                if (!this.filters.isEmpty()) return false;
                if (!this.meta.active) return false;
                if (checkMeta) return false;
                if (!this.energy.active) return false;
                if (checkEnergy) return false;
                return true;
            }
        };
    }

    private static List<StackUtil.AdjacentInv> getTargetInventories(ItemStack stack, TileEntity parent) {
        EnumFacing dir = ItemUpgradeModule.getDirection(stack);
        if (dir == null) {
            return StackUtil.getAdjacentInventories(parent);
        }
        StackUtil.AdjacentInv inv = StackUtil.getAdjacentInventory(parent, dir);
        if (inv == null) {
            return emptyInvList;
        }
        return Collections.singletonList(inv);
    }

    private static List<LiquidUtil.AdjacentFluidHandler> getTargetFluidHandlers(ItemStack stack, TileEntity parent) {
        EnumFacing dir = ItemUpgradeModule.getDirection(stack);
        if (dir == null) {
            return LiquidUtil.getAdjacentHandlers(parent);
        }
        LiquidUtil.AdjacentFluidHandler fh = LiquidUtil.getAdjacentHandler(parent, dir);
        if (fh == null) {
            return emptyFhList;
        }
        return Collections.singletonList(fh);
    }

    @Override
    public Collection<ItemStack> onProcessEnd(ItemStack stack, IUpgradableBlock parent, Collection<ItemStack> output) {
        return output;
    }

    @Override
    public IHasGui getInventory(EntityPlayer player, ItemStack stack) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return null;
        }
        switch (type) {
            case advanced_ejector: 
            case advanced_pulling: {
                return new HandHeldAdvancedUpgrade(player, stack);
            }
        }
        return null;
    }

    @Override
    public IHasGui getSubInventory(EntityPlayer player, ItemStack stack, int ID) {
        UpgradeType type = (UpgradeType)this.getType(stack);
        if (type == null) {
            return null;
        }
        switch (type) {
            case advanced_ejector: 
            case advanced_pulling: {
                return HandHeldAdvancedUpgrade.delegate(player, stack, ID);
            }
        }
        return null;
    }

    private static EnumFacing getDirection(ItemStack stack) {
        byte rawDir = StackUtil.getOrCreateNbtData(stack).func_74771_c("dir");
        if (rawDir < 1 || rawDir > 6) {
            return null;
        }
        return EnumFacing.field_82609_l[rawDir - 1];
    }

    public static enum UpgradeType implements IIdProvider
    {
        overclocker(false),
        transformer(false),
        energy_storage(false),
        redstone_inverter(false),
        ejector(true),
        advanced_ejector(true),
        pulling(true),
        advanced_pulling(true),
        fluid_ejector(true),
        fluid_pulling(true),
        remote_interface(false);

        public final boolean directional;

        private UpgradeType(boolean directional) {
            this.directional = directional;
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public int getId() {
            return this.ordinal();
        }
    }
}

