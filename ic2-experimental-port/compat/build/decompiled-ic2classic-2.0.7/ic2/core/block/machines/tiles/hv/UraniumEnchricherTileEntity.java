/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.hv;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.ItemInput;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.api.tiles.readers.ISubProgressMachine;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.ProgressComparator;
import ic2.core.block.base.misc.comparator.types.base.SubProgressComparator;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.hv.UraniumEnricherContainer;
import ic2.core.block.machines.recipes.EnricherRecipeList;
import ic2.core.block.machines.recipes.misc.EnrichRecipe;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.math.ColorUtils;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class UraniumEnchricherTileEntity
extends BaseElectricTileEntity
implements ITickListener,
IProgressMachine,
ISubProgressMachine,
ITileGui,
ITileActivityProvider {
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int mainProgress;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int secondaryProgress;
    @NetworkInfo
    public ResourceLocation storedType;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int storedPoints;

    public UraniumEnchricherTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3, 512, 100000);
        this.addGuiFields("mainProgress", "secondaryProgress", "storedType", "storedPoints");
        this.addComparator(new ProgressComparator("progress", ComparatorNames.PROGRESS, this));
        this.addComparator(new SubProgressComparator("sub_progress", ComparatorNames.SUB_PROGRESS, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerSlotAccess(AccessRule.IMPORT, 0, 1);
        handler.registerSlotAccess(AccessRule.EXPORT, 2);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 0, 1);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 2);
        handler.registerInputFilter(this::isValidInput, 0);
        handler.registerInputFilter(this::isValidCatalyst, 1);
        handler.registerNamedSlot(SlotType.EXTRA_INPUT, 0);
        handler.registerNamedSlot(SlotType.INPUT, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128376_("main", (short)this.mainProgress);
        compound.m_128344_("sub", (byte)this.secondaryProgress);
        compound.m_128376_("points", (short)this.storedPoints);
        if (this.storedType != null) {
            compound.m_128359_("recipe", this.storedType.toString());
        }
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.mainProgress = compound.m_128451_("main");
        this.secondaryProgress = compound.m_128451_("sub");
        this.storedPoints = compound.m_128451_("points");
        String s = compound.m_128461_("recipe");
        this.storedType = s.length() <= 0 ? null : new ResourceLocation(s);
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.URANIUM_ENRICHER;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new UraniumEnricherContainer(this, player, windowID);
    }

    public EnricherRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).enricher;
    }

    public EnrichRecipe getStoredRecipe() {
        return this.storedType == null || this.storedPoints <= 0 ? null : this.getRecipeList().getRecipe(this.storedType);
    }

    public EnrichRecipe getFuelRecipe(ItemStack stack) {
        return this.getRecipeList().getRecipeFromFuel(stack);
    }

    public boolean isValidInput(ItemStack stack) {
        EnrichRecipe recipe = this.getStoredRecipe();
        return recipe != null ? recipe.getInputs().contains(stack.m_41720_()) : this.getRecipeList().hasRecipeForInput(stack);
    }

    public boolean isValidCatalyst(ItemStack stack) {
        EnrichRecipe recipe = this.getStoredRecipe();
        return recipe != null ? recipe.getFuelValue(stack) > 0 : this.getRecipeList().getRecipeFromFuel(stack) != null;
    }

    @Override
    public void onTick() {
        EnrichRecipe recipe = this.getStoredRecipe();
        boolean active = false;
        if (recipe != null && this.storedPoints >= recipe.getRequiredPoints() && this.hasEnergy(recipe.getEnergyCost()) && recipe.isValidInput((ItemStack)this.inventory.get(0), (ItemStack)this.inventory.get(2))) {
            active = true;
            ++this.mainProgress;
            this.useEnergy(recipe.getEnergyCost());
            if (this.mainProgress >= 1000) {
                this.mainProgress = 0;
                this.storedPoints -= recipe.getRequiredPoints();
                ((ItemStack)this.inventory.get(0)).m_41774_(1);
                this.setOrGrow(2, recipe.getOutput(), true);
                this.updateGuiField("storedPoints");
            }
            this.updateGuiField("mainProgress");
        } else {
            this.mainProgress = 0;
            this.updateGuiField("mainProgress");
        }
        if (this.hasEnergy(100) && !((ItemStack)this.inventory.get(1)).m_41619_()) {
            int points;
            boolean empty;
            recipe = this.getStoredRecipe();
            boolean bl = empty = recipe == null;
            if (empty) {
                recipe = this.getFuelRecipe((ItemStack)this.inventory.get(1));
            }
            int n = points = recipe == null ? 0 : recipe.getFuelValue((ItemStack)this.inventory.get(1));
            if (points > 0 && (empty || this.storedPoints + points <= 1000)) {
                active = true;
                ++this.secondaryProgress;
                this.useEnergy(100);
                if (this.secondaryProgress >= 100) {
                    this.secondaryProgress = 0;
                    ((ItemStack)this.inventory.get(1)).m_41774_(1);
                    if (empty) {
                        this.storedType = recipe.getId();
                    }
                    this.storedPoints += points;
                    this.updateGuiFields("storedPoints", "storedType");
                }
            } else {
                this.secondaryProgress = 0;
            }
            this.updateGuiField("secondaryProgress");
        } else {
            this.secondaryProgress = 0;
            this.updateGuiField("secondaryProgress");
        }
        this.setActive(active);
        this.handleComparators();
    }

    @Override
    public float getSubProgress() {
        return this.secondaryProgress;
    }

    @Override
    public float getMaxSubProgress() {
        return 100.0f;
    }

    @Override
    public float getProgress() {
        return this.mainProgress;
    }

    @Override
    public float getMaxProgress() {
        return 1000.0f;
    }

    public int getPoints() {
        return this.storedPoints;
    }

    public int getMaxPoints() {
        return 1000;
    }

    public static void registerRecipes(EnricherRecipeList recipes) {
        recipes.registerRecipe(EnrichRecipe.createIC2Recipe(IC2Items.INGOT_URANIUM, Items.f_42585_, 200, IC2Items.INGOT_URANIUM_ENRICHED_BLAZE, 100, ColorUtils.rgb(232, 155, 7, 255), 20, "blaze"));
        Object2IntLinkedOpenHashMap inputs = new Object2IntLinkedOpenHashMap();
        inputs.put((Object)new ItemInput(Items.f_42414_), 25);
        inputs.put((Object)new ItemInput((ItemLike)IC2Blocks.CHARCOAL_BLOCK), 200);
        recipes.registerRecipe(EnrichRecipe.createIC2Recipe(IC2Items.INGOT_URANIUM, (Object2IntMap<IInput>)inputs, IC2Items.INGOT_URANIUM_ENRICHED_CHARCOAL, 100, ColorUtils.rgb(54, 54, 54, 255), 5, "charcoal"));
        recipes.registerRecipe(EnrichRecipe.createIC2Recipe(IC2Items.INGOT_URANIUM, Items.f_42584_, 100, IC2Items.INGOT_URANIUM_ENRICHED_ENDERPEARL, 100, ColorUtils.rgb(35, 174, 113, 255), 75, "ender"));
        recipes.registerRecipe(EnrichRecipe.createIC2Recipe(IC2Items.INGOT_URANIUM, Items.f_42686_, 200, IC2Items.INGOT_URANIUM_ENRICHED_NETHERSTAR, 150, ColorUtils.rgb(255, 239, 106, 255), 150, "nether_star"));
        inputs = new Object2IntLinkedOpenHashMap();
        inputs.put((Object)new ItemInput(Items.f_42451_), 25);
        inputs.put((Object)new ItemInput(Items.f_42153_), 200);
        recipes.registerRecipe(EnrichRecipe.createIC2Recipe(IC2Items.INGOT_URANIUM, (Object2IntMap<IInput>)inputs, IC2Items.INGOT_URANIUM_ENRICHED_REDSTONE, 100, ColorUtils.RED, 25, "redstone"));
    }
}

