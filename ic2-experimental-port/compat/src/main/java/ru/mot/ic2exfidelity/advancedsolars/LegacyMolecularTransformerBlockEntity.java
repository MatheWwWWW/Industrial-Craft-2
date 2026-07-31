package ru.mot.ic2exfidelity.advancedsolars;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Energy;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.tileentity.TileEntityBase;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/** Tier-6, recipe-sized EU accumulator from Advanced Solars 2.0.2. */
public final class LegacyMolecularTransformerBlockEntity extends TileEntityBase implements IHasGui {
    private static final int OUTPUT_LIMIT = 22;

    public final InvSlot inputSlot;
    public final InvSlotOutput outputSlot;
    private final Energy energy;
    private LegacyMolecularTransformerRecipes.Recipe recipe;
    private boolean consumedInputs;

    public LegacyMolecularTransformerBlockEntity(BlockPos position, BlockState state) {
        super(LegacyAdvancedSolarsContent.MOLECULAR_TRANSFORMER_BLOCK_ENTITY.get(),
                position, state);
        this.inputSlot = new InvSlot(this, "input", InvSlot.Access.I, 1, InvSlot.InvSide.ANY) {
            @Override
            public boolean accepts(ItemStack stack) {
                return LegacyMolecularTransformerRecipes.find(stack) != null;
            }
        };
        this.outputSlot = new InvSlotOutput(this, "output", 1, InvSlot.InvSide.ANY);
        this.energy = addComponent(Energy.asBasicSink(this, 0.0, 6));
        this.energy.setReceivingEnabled(false);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        processTick();
    }

    public void processTick() {
        boolean active = prepareRecipe();
        if (active && energy.getEnergy() >= recipe.energy()) {
            energy.useEnergy(energy.getEnergy());
            outputSlot.add(recipe.outputStack());
            consumedInputs = false;
            recipe = null;
            energy.setCapacity(0.0);
            energy.setReceivingEnabled(false);
            m_6596_();
        }
        setActiveState(active);
    }

    private boolean prepareRecipe() {
        if (consumedInputs) {
            if (recipe == null) {
                resetProcess();
                return false;
            }
            return true;
        }

        LegacyMolecularTransformerRecipes.Recipe candidate =
                LegacyMolecularTransformerRecipes.find(inputSlot.get());
        if (candidate == null || !canFitOutput(candidate)) {
            resetProcess();
            return false;
        }

        recipe = candidate;
        ItemStack input = inputSlot.get();
        input.m_41774_(candidate.inputCount());
        if (input.m_41619_()) {
            inputSlot.clear();
        } else {
            inputSlot.put(input);
        }
        consumedInputs = true;
        energy.setCapacity(candidate.energy());
        energy.setReceivingEnabled(true);
        m_6596_();
        return true;
    }

    private boolean canFitOutput(LegacyMolecularTransformerRecipes.Recipe candidate) {
        ItemStack existing = outputSlot.get();
        if (existing.m_41619_()) {
            return candidate.outputCount() <= Math.min(OUTPUT_LIMIT, candidate.output().m_41459_());
        }
        return existing.m_41720_() == candidate.output()
                && existing.m_41613_() + candidate.outputCount()
                        <= Math.min(OUTPUT_LIMIT, existing.m_41741_());
    }

    private void resetProcess() {
        recipe = null;
        consumedInputs = false;
        energy.useEnergy(energy.getEnergy());
        energy.setCapacity(0.0);
        energy.setReceivingEnabled(false);
    }

    @Override
    public void m_142466_(CompoundTag tag) {
        super.m_142466_(tag);
        consumedInputs = tag.m_128471_("consumedInputs");
        recipe = LegacyMolecularTransformerRecipes.byIndex(tag.m_128451_("recipeIndex"));
        if (consumedInputs && recipe != null) {
            energy.setCapacity(recipe.energy());
            energy.setReceivingEnabled(true);
        } else {
            resetProcess();
        }
    }

    @Override
    public void m_183515_(CompoundTag tag) {
        super.m_183515_(tag);
        tag.m_128379_("consumedInputs", consumedInputs);
        tag.m_128405_("recipeIndex", LegacyMolecularTransformerRecipes.indexOf(recipe));
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return DynamicContainer.create(syncId, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(
            int syncId, Inventory inventory, GrowingBuffer buffer) {
        return DynamicContainer.create(syncId, inventory, this);
    }

    public int getStoredEU() {
        return (int) energy.getEnergy();
    }

    public int getMaxEU() {
        return recipe == null ? 0 : recipe.energy();
    }

    public int getTier() {
        return energy.getSinkTier();
    }

    public boolean hasConsumedInputs() {
        return consumedInputs;
    }

    public LegacyMolecularTransformerRecipes.Recipe getCurrentRecipe() {
        return recipe;
    }

    public void forceAddEnergyForTest(double amount) {
        energy.addEnergy(amount);
    }
}
