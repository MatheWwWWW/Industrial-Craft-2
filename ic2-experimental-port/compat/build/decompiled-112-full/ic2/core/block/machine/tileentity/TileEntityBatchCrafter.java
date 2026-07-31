/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  gnu.trove.TIntCollection
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.CraftingManager
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.util.NonNullList
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import com.google.common.base.Predicate;
import gnu.trove.TIntCollection;
import ic2.api.network.ClientModifiable;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.IUpgradeItem;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerBatchCrafter;
import ic2.core.block.machine.gui.GuiBatchCrafter;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.profile.NotClassic;
import ic2.core.util.InventorySlotCrafting;
import ic2.core.util.StackUtil;
import ic2.core.util.Tuple;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityBatchCrafter
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock,
IGuiValueProvider,
INetworkClientTileEntityEventListener {
    private static final Set<UpgradableProperty> UPGRADES = EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    public static final int defaultTier = 1;
    public static final int defaultEnergyConsume = 2;
    public static final int defaultOperationLength = 40;
    public static final int defaultEnergyStorage = 20000;
    @ClientModifiable
    public final ItemStack[] craftingGrid = new ItemStack[9];
    public final InvSlot[] ingredientsRow = new InvSlot[this.craftingGrid.length];
    public final InvSlotOutput craftingOutput = new InvSlotOutput(this, "output", 1, InvSlot.InvSide.SIDE);
    public final InvSlotOutput containerOutput = new InvSlotOutput(this, "containersOut", this.craftingGrid.length, InvSlot.InvSide.NOTSIDE);
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
    protected final InventoryCrafting crafting = new InventorySlotCrafting(3, 3){

        @Override
        protected ItemStack get(int index) {
            return StackUtil.wrapEmpty(TileEntityBatchCrafter.this.craftingGrid[index]);
        }

        @Override
        protected void put(int index, ItemStack stack) {
            TileEntityBatchCrafter.this.craftingGrid[index] = stack;
        }

        @Override
        public boolean func_191420_l() {
            for (ItemStack stack : TileEntityBatchCrafter.this.craftingGrid) {
                if (StackUtil.isEmpty(stack)) continue;
                return false;
            }
            return true;
        }

        @Override
        public void func_174888_l() {
            Arrays.fill(TileEntityBatchCrafter.this.craftingGrid, StackUtil.emptyStack);
        }
    };
    public final InventoryCrafting ingredients = new InventorySlotCrafting(3, 3){

        @Override
        protected ItemStack get(int index) {
            return TileEntityBatchCrafter.this.ingredientsRow[index].get();
        }

        @Override
        protected void put(int index, ItemStack stack) {
            TileEntityBatchCrafter.this.ingredientsRow[index].put(stack);
        }

        @Override
        public boolean func_191420_l() {
            for (InvSlot slot : TileEntityBatchCrafter.this.ingredientsRow) {
                if (slot.isEmpty()) continue;
                return false;
            }
            return true;
        }

        @Override
        public void func_174888_l() {
            for (InvSlot slot : TileEntityBatchCrafter.this.ingredientsRow) {
                slot.clear();
            }
        }
    };
    public final Predicate<Tuple.T2<ItemStack, Integer>> acceptPredicate = new Predicate<Tuple.T2<ItemStack, Integer>>(){

        public boolean apply(Tuple.T2<ItemStack, Integer> input) {
            return TileEntityBatchCrafter.this.ingredientsRow[(Integer)input.b].accepts((ItemStack)input.a);
        }
    };
    protected IRecipe recipe = null;
    protected boolean canCraft = false;
    protected boolean newChange = true;
    protected boolean attemptToBalance = false;
    public ItemStack recipeOutput = StackUtil.emptyStack;
    public int energyConsume;
    public int operationLength;
    public int operationsPerTick;
    protected short progress = 0;
    protected float guiProgress = 0.0f;

    public TileEntityBatchCrafter() {
        super(20000, 1);
        int i = 0;
        while (i < this.ingredientsRow.length) {
            final int slot = i++;
            this.ingredientsRow[slot] = new InvSlot(this, "ingredient[" + slot + ']', InvSlot.Access.I, 1){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public boolean accepts(ItemStack ingredient) {
                    IRecipe recipe;
                    IRecipe iRecipe = recipe = ((TileEntityBatchCrafter)TileEntityBatchCrafter.this).field_145850_b.field_72995_K ? TileEntityBatchCrafter.this.findRecipe() : TileEntityBatchCrafter.this.recipe;
                    if (recipe == null) {
                        return false;
                    }
                    assert (recipe.func_77569_a(TileEntityBatchCrafter.this.crafting, TileEntityBatchCrafter.this.field_145850_b));
                    ItemStack recipeStack = TileEntityBatchCrafter.this.craftingGrid[slot];
                    try {
                        TileEntityBatchCrafter.this.craftingGrid[slot] = ingredient;
                        boolean bl = recipe.func_77569_a(TileEntityBatchCrafter.this.crafting, TileEntityBatchCrafter.this.field_145850_b);
                        return bl;
                    }
                    finally {
                        TileEntityBatchCrafter.this.craftingGrid[slot] = recipeStack;
                    }
                }

                @Override
                public void onChanged() {
                    super.onChanged();
                    TileEntityBatchCrafter.this.ingredientChange(slot);
                }
            };
        }
        this.energyConsume = 2;
        this.operationLength = 40;
        this.operationsPerTick = 1;
        this.comparator.setUpdate(() -> this.progress * 15 / this.operationLength);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.progress = nbt.func_74765_d("progress");
        NBTTagList grid = nbt.func_150295_c("grid", 10);
        for (int i = 0; i < grid.func_74745_c(); ++i) {
            NBTTagCompound contentTag = grid.func_150305_b(i);
            this.craftingGrid[contentTag.func_74771_c((String)"index")] = new ItemStack(contentTag);
        }
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74777_a("progress", this.progress);
        NBTTagList grid = new NBTTagList();
        for (byte i = 0; i < this.craftingGrid.length; i = (byte)((byte)(i + 1))) {
            ItemStack content = this.craftingGrid[i];
            if (StackUtil.isEmpty(content)) continue;
            NBTTagCompound contentTag = new NBTTagCompound();
            contentTag.func_74774_a("index", i);
            content.func_77955_b(contentTag);
            grid.func_74742_a((NBTBase)contentTag);
        }
        nbt.func_74782_a("grid", (NBTBase)grid);
        return nbt;
    }

    protected IRecipe findRecipe() {
        World world = this.func_145831_w();
        return CraftingManager.func_192413_b((InventoryCrafting)this.crafting, (World)world);
    }

    public void matrixChange(int slot) {
        if (this.recipe == null || !this.recipe.func_77569_a(this.crafting, this.func_145831_w())) {
            this.recipe = this.findRecipe();
        }
        this.recipeOutput = this.recipe != null ? this.recipe.func_77572_b(this.crafting) : StackUtil.emptyStack;
        this.newChange = true;
    }

    public void ingredientChange(int slot) {
        this.newChange = true;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.func_145831_w().field_72995_K) {
            this.setOverclockRates();
            this.matrixChange(-1);
        }
    }

    @Override
    public void func_70296_d() {
        super.func_70296_d();
        if (!this.func_145831_w().field_72995_K) {
            this.setOverclockRates();
            this.attemptToBalance = true;
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = false;
        if (this.attemptToBalance) {
            if (!this.ingredients.func_191420_l()) {
                needsInvUpdate |= !((TIntCollection)StackUtil.balanceStacks((IInventory)this.ingredients, this.acceptPredicate).b).isEmpty();
            }
            this.attemptToBalance = false;
        }
        if (this.newChange) {
            this.canCraft = this.canCraft();
            this.newChange = false;
        }
        if (this.canCraft && this.craftingOutput.canAdd(this.recipeOutput) && this.energy.useEnergy(this.energyConsume)) {
            this.setActive(true);
            this.progress = (short)(this.progress + 1);
            if (this.progress >= this.operationLength) {
                this.doCrafting();
                needsInvUpdate = true;
                this.newChange = true;
                this.progress = 0;
            }
        } else {
            if (!this.hasRecipe()) {
                this.progress = 0;
            }
            this.setActive(false);
        }
        this.guiProgress = (float)this.progress / (float)this.operationLength;
        if (needsInvUpdate |= this.upgradeSlot.tickNoMark()) {
            super.func_70296_d();
        }
    }

    public boolean hasRecipe() {
        return this.recipe != null;
    }

    public boolean canCraft() {
        if (!this.hasRecipe()) {
            return false;
        }
        for (int slot = 0; slot < this.craftingGrid.length; ++slot) {
            if (StackUtil.isEmpty(this.craftingGrid[slot]) || this.ingredientsRow[slot].accepts(this.ingredientsRow[slot].get())) continue;
            return false;
        }
        return true;
    }

    protected void doCrafting() {
        for (int operation = 0; operation < this.operationsPerTick; ++operation) {
            List<ItemStack> outputs = Collections.singletonList(this.recipeOutput);
            for (ItemStack stack : this.upgradeSlot) {
                if (stack == null || !(stack.func_77973_b() instanceof IUpgradeItem)) continue;
                ((IUpgradeItem)stack.func_77973_b()).onProcessEnd(stack, this, outputs);
            }
            this.craft();
            if (!this.hasRecipe() || !this.craftingOutput.canAdd(this.recipeOutput)) break;
        }
    }

    protected void craft() {
        assert (this.hasRecipe());
        assert (this.craftingOutput.canAdd(this.recipeOutput));
        this.craftingOutput.add(this.recipeOutput);
        NonNullList stacks = this.recipe.func_179532_b(this.ingredients);
        World world = this.func_145831_w();
        for (int slot = 0; slot < this.ingredientsRow.length; ++slot) {
            ItemStack oldStack = this.ingredientsRow[slot].get();
            if (!StackUtil.isEmpty(oldStack) && !StackUtil.isEmpty(this.craftingGrid[slot])) {
                oldStack = StackUtil.decSize(oldStack);
                this.ingredientsRow[slot].put(oldStack);
            }
            if (stacks.size() <= slot || StackUtil.isEmpty((ItemStack)stacks.get(slot))) continue;
            ItemStack newStack = (ItemStack)stacks.get(slot);
            if (StackUtil.isEmpty(oldStack) && this.ingredientsRow[slot].accepts(newStack)) {
                this.ingredientsRow[slot].put(newStack);
                continue;
            }
            if (StackUtil.checkItemEqualityStrict(oldStack, newStack)) {
                this.ingredientsRow[slot].put(StackUtil.incSize(newStack, StackUtil.getSize(oldStack)));
                continue;
            }
            if (this.containerOutput.canAdd(newStack)) {
                this.containerOutput.add(newStack);
                continue;
            }
            StackUtil.dropAsEntity(world, this.field_174879_c, newStack);
        }
        for (int i = this.ingredientsRow.length; i < stacks.size(); ++i) {
            ItemStack newStack = (ItemStack)stacks.get(i);
            if (this.containerOutput.canAdd(newStack)) {
                this.containerOutput.add(newStack);
                continue;
            }
            StackUtil.dropAsEntity(world, this.field_174879_c, newStack);
        }
    }

    protected void setOverclockRates() {
        this.upgradeSlot.onChanged();
        double previousProgress = (double)this.progress / (double)this.operationLength;
        this.operationsPerTick = this.upgradeSlot.getOperationsPerTick(40);
        this.operationLength = this.upgradeSlot.getOperationLength(40);
        this.energyConsume = this.upgradeSlot.getEnergyDemand(2);
        int tier = this.upgradeSlot.getTier(1);
        this.energy.setSinkTier(tier);
        this.dischargeSlot.setTier(tier);
        this.energy.setCapacity(this.upgradeSlot.getEnergyStorage(20000, 40, 2));
        this.progress = (short)Math.floor(previousProgress * (double)this.operationLength + 0.1);
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        switch (event) {
            case 0: {
                this.matrixChange(-1);
            }
        }
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return UPGRADES;
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double amount) {
        return this.energy.useEnergy(amount);
    }

    @Override
    public ContainerBase<?> getGuiContainer(EntityPlayer player) {
        return new ContainerBatchCrafter(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiBatchCrafter(new ContainerBatchCrafter(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public double getGuiValue(String name) {
        if ("progress".equals(name)) {
            return this.guiProgress;
        }
        throw new IllegalArgumentException("Unexpected value requested: " + name);
    }
}

