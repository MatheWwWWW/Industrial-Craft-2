/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$MutableBlockPos
 *  net.minecraft.world.ChunkCache
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.steam;

import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.steam.IMultiBlockController;
import ic2.core.block.steam.TileEntityCokeKilnGrate;
import ic2.core.block.steam.TileEntityCokeKilnHatch;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.network.GuiSynced;
import ic2.core.recipe.dynamic.DynamicRecipe;
import ic2.core.recipe.dynamic.DynamicRecipeManager;
import ic2.core.recipe.dynamic.RecipeOutputFluidStack;
import ic2.core.recipe.dynamic.RecipeOutputIngredient;
import ic2.core.recipe.dynamic.RecipeOutputItemStack;
import ic2.core.ref.BlockName;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.util.ParticleUtil;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCokeKiln
extends TileEntityInventory
implements IMultiBlockController,
IHasGui,
IGuiValueProvider {
    protected int tickRate = 20;
    protected int updateTicker = IC2.random.nextInt(this.tickRate);
    protected boolean isFormed = false;
    protected final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1, InvSlot.InvSide.ANY);
    public static DynamicRecipeManager recipeManager;
    protected int progress = 0;
    protected int operationLength = 0;
    protected DynamicRecipe recipe = null;
    @GuiSynced
    protected float guiProgress;

    public static void init() {
        recipeManager = new DynamicRecipeManager();
        recipeManager.createRecipe().withInput("logWood").withOutput(new ItemStack(Items.field_151044_h, 1, 1)).withOutput(new FluidStack(FluidName.creosote.getInstance(), 250)).withOperationDurationTicks(1800).register();
        recipeManager.createRecipe().withInput(new ItemStack(Items.field_151044_h, 1, 0)).withOutput(new ItemStack(ItemName.coke.getInstance(), 1)).withOutput(new FluidStack(FluidName.creosote.getInstance(), 500)).withOperationDurationTicks(1800).register();
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.progress = nbt.func_74762_e("progress");
        this.operationLength = nbt.func_74762_e("operationLength");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("progress", this.progress);
        nbt.func_74768_a("operationLength", this.operationLength);
        return nbt;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.updateTicker++ % this.tickRate != 0) {
            return;
        }
        this.isFormed = this.hasValidStructure();
        if (!this.isFormed) {
            this.progress = 0;
            this.guiProgress = 0.0f;
            this.setActive(false);
            return;
        }
        boolean needsInventoryUpdate = false;
        if (this.canWork()) {
            int progressNeeded;
            this.setActive(true);
            if (this.progress == 0) {
                needsInventoryUpdate = true;
            }
            if (this.progress < (progressNeeded = this.recipe.getOperationDuration())) {
                this.progress += 20;
            }
            if (this.progress >= progressNeeded) {
                this.finishWork();
                needsInventoryUpdate = true;
            }
        } else {
            this.setActive(false);
        }
        this.guiProgress = this.progress == 0 || this.operationLength == 0 ? 0.0f : (float)this.progress / (float)this.operationLength;
        if (needsInventoryUpdate) {
            super.func_70296_d();
        }
    }

    protected boolean canWork() {
        DynamicRecipe maybeRecipe;
        boolean canUse;
        BlockPos hatchPos = new BlockPos(this.field_174879_c.func_177958_n() + -this.getFacing().func_82601_c(), this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + -this.getFacing().func_82599_e());
        TileEntity hatch = this.field_145850_b.func_175625_s(hatchPos);
        if (!(hatch instanceof TileEntityCokeKilnHatch)) {
            return false;
        }
        ItemStack input = ((TileEntityCokeKilnHatch)hatch).inventory.get();
        if (input.func_190926_b()) {
            return false;
        }
        if (this.recipe != null && !(canUse = recipeManager.apply(this.recipe, new ItemStack[]{input}, new FluidStack[0], true))) {
            this.reset();
        }
        if ((maybeRecipe = this.recipe) != null) {
            for (RecipeOutputIngredient entry : this.recipe.getOutputIngredients()) {
                if (entry instanceof RecipeOutputItemStack) {
                    if (this.outputSlot.canAdd((ItemStack)entry.ingredient)) continue;
                    return false;
                }
                if (!(entry instanceof RecipeOutputFluidStack)) continue;
                BlockPos gratePos = new BlockPos(this.field_174879_c.func_177958_n() + -this.getFacing().func_82601_c(), this.field_174879_c.func_177956_o() - 1, this.field_174879_c.func_177952_p() + -this.getFacing().func_82599_e());
                TileEntity grate = this.field_145850_b.func_175625_s(gratePos);
                if (!(grate instanceof TileEntityCokeKilnGrate)) {
                    return false;
                }
                if (((TileEntityCokeKilnGrate)grate).fluidTank.fillInternal((FluidStack)entry.ingredient, false) >= ((FluidStack)entry.ingredient).amount) continue;
                return false;
            }
            return true;
        }
        maybeRecipe = recipeManager.findRecipe(new ItemStack[]{input}, new FluidStack[0]);
        if (maybeRecipe == null) {
            return false;
        }
        this.updateRecipe(maybeRecipe);
        for (RecipeOutputIngredient entry : this.recipe.getOutputIngredients()) {
            if (entry instanceof RecipeOutputItemStack) {
                if (this.outputSlot.canAdd((ItemStack)entry.ingredient)) continue;
                return false;
            }
            if (!(entry instanceof RecipeOutputFluidStack)) continue;
            BlockPos gratePos = new BlockPos(this.field_174879_c.func_177958_n() + -this.getFacing().func_82601_c(), this.field_174879_c.func_177956_o() - 1, this.field_174879_c.func_177952_p() + -this.getFacing().func_82599_e());
            TileEntity grate = this.field_145850_b.func_175625_s(gratePos);
            if (!(grate instanceof TileEntityCokeKilnGrate)) {
                return false;
            }
            if (((TileEntityCokeKilnGrate)grate).fluidTank.fillInternal((FluidStack)entry.ingredient, false) >= ((FluidStack)entry.ingredient).amount) continue;
            return false;
        }
        return true;
    }

    protected void finishWork() {
        BlockPos hatchPos = new BlockPos(this.field_174879_c.func_177958_n() + -this.getFacing().func_82601_c(), this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + -this.getFacing().func_82599_e());
        TileEntity hatch = this.field_145850_b.func_175625_s(hatchPos);
        if (!(hatch instanceof TileEntityCokeKilnHatch)) {
            return;
        }
        InvSlot inventory = ((TileEntityCokeKilnHatch)hatch).inventory;
        if (inventory.get().func_190926_b()) {
            return;
        }
        recipeManager.apply(this.recipe, new ItemStack[]{inventory.get()}, new FluidStack[0], false);
        ArrayList<ItemStack> itemOutputs = new ArrayList<ItemStack>();
        ArrayList<FluidStack> fluidOutputs = new ArrayList<FluidStack>();
        for (RecipeOutputIngredient entry : this.recipe.getOutputIngredients()) {
            if (entry instanceof RecipeOutputItemStack) {
                itemOutputs.add(StackUtil.copy((ItemStack)entry.ingredient));
                continue;
            }
            if (!(entry instanceof RecipeOutputFluidStack)) continue;
            fluidOutputs.add(((FluidStack)entry.ingredient).copy());
        }
        for (ItemStack stack : itemOutputs) {
            int amount = this.outputSlot.add(StackUtil.copy(stack));
            stack.func_190918_g(amount);
        }
        itemOutputs.clear();
        BlockPos gratePos = new BlockPos(this.field_174879_c.func_177958_n() + -this.getFacing().func_82601_c(), this.field_174879_c.func_177956_o() - 1, this.field_174879_c.func_177952_p() + -this.getFacing().func_82599_e());
        TileEntity grate = this.field_145850_b.func_175625_s(gratePos);
        if (grate instanceof TileEntityCokeKilnGrate) {
            for (FluidStack stack : fluidOutputs) {
                int amount = ((TileEntityCokeKilnGrate)grate).fluidTank.fillInternal(stack, true);
                stack.amount -= amount;
            }
        }
        fluidOutputs.clear();
        this.progress = 0;
    }

    protected void updateRecipe(DynamicRecipe recipe) {
        this.operationLength = recipe.getOperationDuration();
        this.recipe = recipe;
    }

    protected void reset() {
        this.progress = 0;
        this.operationLength = 0;
        this.recipe = null;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        if (this.getActive()) {
            World world = this.func_145831_w();
            ParticleUtil.showFlames(world, this.field_174879_c, this.getFacing());
            if (world.field_73012_v.nextDouble() < 0.1) {
                world.func_184134_a((double)this.field_174879_c.func_177958_n() + 0.5, (double)this.field_174879_c.func_177956_o() + 0.5, (double)this.field_174879_c.func_177952_p() + 0.5, SoundEvents.field_187652_bv, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            }
        }
    }

    @Override
    public boolean hasValidStructure() {
        IBlockState state;
        TileEntity tileEntity;
        int z;
        int x;
        int range = 2;
        ChunkCache cache = new ChunkCache(this.func_145831_w(), this.field_174879_c.func_177982_a(-2, -2, -2), this.field_174879_c.func_177982_a(2, 2, 2), 0);
        BlockPos.MutableBlockPos cPos = new BlockPos.MutableBlockPos();
        for (x = -1; x <= 1; ++x) {
            for (z = -1; z <= 1; ++z) {
                cPos.func_181079_c(this.field_174879_c.func_177958_n() + (x - this.getFacing().func_82601_c()), this.field_174879_c.func_177956_o() - 1, this.field_174879_c.func_177952_p() + (z - this.getFacing().func_82599_e()));
                if (x == 0 && z == 0) {
                    tileEntity = cache.func_175625_s((BlockPos)cPos);
                    if (tileEntity == null) {
                        return false;
                    }
                    if (tileEntity instanceof TileEntityCokeKilnGrate) continue;
                    return false;
                }
                state = cache.func_180495_p((BlockPos)cPos);
                if (state.func_177230_c() == BlockName.refractory_bricks.getInstance()) continue;
                return false;
            }
        }
        for (x = -1; x <= 1; ++x) {
            for (z = -1; z <= 1; ++z) {
                cPos.func_181079_c(this.field_174879_c.func_177958_n() + (x - this.getFacing().func_82601_c()), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p() + (z - this.getFacing().func_82599_e()));
                if (x == 0 && z == 0) {
                    state = cache.func_180495_p((BlockPos)cPos);
                    if (state.func_177230_c() == Blocks.field_150350_a) continue;
                    return false;
                }
                if (this.field_174879_c.func_177958_n() == cPos.func_177958_n() && this.field_174879_c.func_177952_p() == cPos.func_177952_p()) {
                    tileEntity = cache.func_175625_s((BlockPos)cPos);
                    if (tileEntity == null) {
                        return false;
                    }
                    if (tileEntity == this) continue;
                    return false;
                }
                state = cache.func_180495_p((BlockPos)cPos);
                if (state.func_177230_c() == BlockName.refractory_bricks.getInstance()) continue;
                return false;
            }
        }
        for (x = -1; x <= 1; ++x) {
            for (z = -1; z <= 1; ++z) {
                cPos.func_181079_c(this.field_174879_c.func_177958_n() + (x - this.getFacing().func_82601_c()), this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + (z - this.getFacing().func_82599_e()));
                if (x == 0 && z == 0) {
                    tileEntity = cache.func_175625_s((BlockPos)cPos);
                    if (tileEntity == null) {
                        return false;
                    }
                    if (tileEntity instanceof TileEntityCokeKilnHatch) continue;
                    return false;
                }
                state = cache.func_180495_p((BlockPos)cPos);
                if (state.func_177230_c() == BlockName.refractory_bricks.getInstance()) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isFormed() {
        return this.isFormed;
    }

    public ContainerBase<TileEntityCokeKiln> getGuiContainer(EntityPlayer player) {
        return DynamicContainer.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return DynamicGui.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public double getGuiValue(String name) {
        if (name.equals("progress")) {
            return this.guiProgress;
        }
        throw new IllegalArgumentException(this.getClass().getSimpleName() + " Cannot get value for " + name);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack stack, List<String> info, ITooltipFlag advanced) {
        info.add("");
        info.add("MultiBlock Structure:");
        info.add("");
        info.add(" Bottom Layer - 3x3 of Refractory Blocks with a Coke Kiln Grate in the centre");
        info.add("");
        info.add(" Middle Layer - 3x3 of Refractory Blocks with a hollow centre and this block in the middle of one of the sides");
        info.add("");
        info.add(" Top Layer - 3x3 of Refractory Blocks with a Coke Kiln Hatch in the centre");
        info.add("");
    }
}

