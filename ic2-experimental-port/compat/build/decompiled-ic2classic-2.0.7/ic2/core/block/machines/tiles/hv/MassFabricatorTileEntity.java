/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.hv;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.ingridients.recipes.SimpleRecipeOutput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.audio.IAudioSource;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.special.ScrapComparator;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.hv.MassFabricatorContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import java.util.Collections;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MassFabricatorTileEntity
extends BaseElectricTileEntity
implements ITickListener,
ITileGui,
IProgressMachine,
ITileActivityProvider {
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    int state = 0;
    boolean requiresScrap = false;
    @NetworkInfo
    int scrap = 0;
    int scrapValue = -1;
    IAudioSource mainSource;
    IAudioSource subSource;

    public MassFabricatorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 2, 512, 7100000);
        this.addGuiFields("scrap");
        this.addNetworkFields("state");
        this.addComparator(new ScrapComparator("scrap", ComparatorNames.SCRAP, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.IMPORT, 0);
        handler.registerSlotAccess(AccessRule.EXPORT, 1);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 0);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 1);
        handler.registerInputFilter(T -> this.getRecipes().getRecipe(T, false) != null, 0);
        handler.registerNamedSlot(SlotType.INPUT, 0);
        handler.registerNamedSlot(SlotType.OUTPUT, 1);
    }

    @Override
    public boolean supportsNotify() {
        return true;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.MASS_FABRICATOR;
    }

    public IMachineRecipeList getRecipes() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).massFabricator;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.scrap = compound.m_128451_("scrap");
        this.scrapValue = compound.m_128451_("scrapValue");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128405_("scrap_value", this.scrapValue);
        compound.m_128405_("scrap", this.scrap);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new MassFabricatorContainer(this, player, windowID);
    }

    @Override
    public float getProgress() {
        return this.energy;
    }

    @Override
    public float getMaxProgress() {
        return 7000000.0f;
    }

    public int getScrap() {
        return this.scrap;
    }

    public int getLastScrap() {
        return this.scrapValue;
    }

    @Override
    public void onTick() {
        this.handleRedstone();
        int state = this.isRedstonePowered() || this.energy <= 0 ? 0 : (this.scrap > 0 ? 2 : 1);
        this.setState(state);
        this.setActive(state > 0);
        if (this.energy >= 7000000 && ((ItemStack)this.inventory.get(1)).m_41613_() < 64) {
            this.setOrGrow(1, new ItemStack((ItemLike)IC2Items.UUMATTER), false);
            this.useEnergy(7000000);
            this.notifyListeners();
        }
        if (!(this.scrap != 0 && this.scrap >= this.scrapValue || ((ItemStack)this.inventory.get(0)).m_41619_())) {
            this.refillAmplifier();
        }
        this.handleComparators();
    }

    public void setState(int state) {
        if (this.state != state) {
            this.state = state;
            this.updateTileField("state");
        }
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("state")) {
            if (this.mainSource == null || !this.mainSource.isValid()) {
                this.mainSource = IC2.AUDIO.createSource(this, IC2Sounds.MASS_FABRICATOR_PROCESSING);
            }
            if (this.subSource == null || !this.subSource.isValid()) {
                this.subSource = IC2.AUDIO.createSource(this, IC2Sounds.MASS_FABRICATOR_SOLO_PROCESSING);
            }
            if (this.mainSource == null || this.subSource == null) {
                return;
            }
            this.mainSource.playStop(this.state > 0);
            this.subSource.playStop(this.state > 1);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        this.mainSource = null;
        this.subSource = null;
        IC2.AUDIO.removeSource(this);
        super.onUnloaded(chunk);
    }

    @Override
    public int getRequestedEnergy() {
        return (!this.requiresScrap || this.scrap >= 0) && !this.isRedstonePowered() ? super.getRequestedEnergy() : 0;
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if (amount <= 0 || amount > 512 || this.requiresScrap && this.scrap <= 0) {
            return 0;
        }
        int bonus = Math.min(this.scrap, amount);
        this.scrap -= bonus;
        this.addEnergy(amount + bonus * 5);
        if (bonus > 0) {
            if (!(this.scrap >= this.scrapValue && this.scrap != 0 || ((ItemStack)this.inventory.get(0)).m_41619_())) {
                this.refillAmplifier();
            }
            this.updateGuiField("scrap");
        }
        return 0;
    }

    public void refillAmplifier() {
        ItemStack stack = (ItemStack)this.inventory.get(0);
        if (stack.m_41619_()) {
            return;
        }
        IMachineRecipeList.RecipeEntry entry = IC2.RECIPES.get((boolean)this.isSimulating()).massFabricator.getRecipe(stack, true);
        if (entry != null) {
            int size = 1;
            if (stack.hasCraftingRemainingItem()) {
                this.inventory.set(0, (Object)stack.getCraftingRemainingItem());
            } else {
                size = Math.min(10, stack.m_41613_());
                stack.m_41774_(size);
            }
            int value = MassFabricatorTileEntity.getValue(entry.getOutput().getMetadata());
            this.scrap += value * size;
            this.scrapValue = value * 10;
            this.updateGuiField("scrap");
        }
    }

    public static void loadAmplifiers(IMachineRecipeList recipes) {
        recipes.addIC2Recipe("scrap", MassFabricatorTileEntity.createCount(1000), IC2Items.SCRAP);
        recipes.addIC2Recipe("scrap_box", MassFabricatorTileEntity.createCount(45000), IC2Items.SCRAPBOX);
        recipes.addIC2Recipe("scrap_metal", MassFabricatorTileEntity.createCount(100000), IC2Items.SCRAP_METAL);
    }

    public static IRecipeOutput createCount(int amplifier) {
        CompoundTag nbt = new CompoundTag();
        nbt.m_128405_("Amplifier", amplifier);
        return new SimpleRecipeOutput(Collections.singletonList(new ItemStack((ItemLike)IC2Items.UUMATTER)), nbt, 0.0f);
    }

    public static int getValue(CompoundTag nbt) {
        return nbt == null ? 0 : nbt.m_128451_("Amplifier");
    }
}

