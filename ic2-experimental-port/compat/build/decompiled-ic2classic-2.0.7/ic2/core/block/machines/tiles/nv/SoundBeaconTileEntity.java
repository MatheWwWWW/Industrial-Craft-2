/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.machines.tiles.nv;

import ic2.api.items.IUpgradeItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.FakePlayerMachine;
import ic2.core.IC2;
import ic2.core.audio.AudioEffect;
import ic2.core.audio.AudioManager;
import ic2.core.block.base.features.IAreaOfEffect;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.base.tiles.impls.BaseChargePadTileEntity;
import ic2.core.block.machines.containers.nv.SoundBeaconContainer;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.INotifyInventory;
import ic2.core.item.upgrades.PadUpgradeItem;
import ic2.core.platform.registries.IC2Tiles;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SoundBeaconTileEntity
extends BaseInventoryTileEntity
implements ITileGui,
INotifyInventory,
IAreaOfEffect {
    @NetworkInfo
    public float itemMod = 1.0f;
    @NetworkInfo
    public float armorMod = 1.0f;
    @NetworkInfo
    public float blockMod = 1.0f;
    @NetworkInfo
    public float itemRange = 1.0f;
    @NetworkInfo
    public float armorRange = 1.0f;
    @NetworkInfo
    public float blockRange = 1.0f;
    int visualId = -1;

    public SoundBeaconTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 9);
        this.addNetworkFields("itemMod", "armorMod", "blockMod", "itemRange", "armorRange", "blockRange");
    }

    @Override
    public boolean allowsUI() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.SOUND_BEACON;
    }

    @Override
    public AABB getAreaOfEffect() {
        return new AABB(this.f_58858_).m_82400_((double)Math.max(this.armorRange, Math.max(this.blockRange, this.itemRange)));
    }

    @Override
    public int getAreaOfEffectColor() {
        return -2134588745;
    }

    @Override
    public void setVisualizationId(int id) {
        this.visualId = id;
    }

    @Override
    public int getVisualizationId() {
        return this.visualId;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new SoundBeaconContainer(this, player, windowID);
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("itemMod") || fields.contains("armorMod") || fields.contains("blockMod") || fields.contains("itemRange") || fields.contains("armorRange") || fields.contains("blockRange")) {
            EnumMap<AudioManager.SoundType, AudioEffect> effects = new EnumMap<AudioManager.SoundType, AudioEffect>(AudioManager.SoundType.class);
            effects.put(AudioManager.SoundType.ITEM, new AudioEffect(this.itemRange, this.itemMod));
            effects.put(AudioManager.SoundType.BACKPACK, new AudioEffect(this.armorRange, this.armorMod));
            effects.put(AudioManager.SoundType.STATIC, new AudioEffect(this.blockRange, this.blockMod));
            IC2.AUDIO.addModifier(this.m_58899_(), effects);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        IC2.AUDIO.removeModifier(this.m_58899_());
        super.onUnloaded(chunk);
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (IC2.PLATFORM.isSimulating()) {
            this.updateEffects();
        }
    }

    @Override
    public void onNotify(IHasInventory inventory, int slot) {
        if (this.isSimulating()) {
            this.updateEffects();
        }
    }

    public void updateEffects() {
        FakePlayerMachine.FakeMachine machine = new FakePlayerMachine.FakeMachine(EnumSet.of(IUpgradeItem.UpgradeType.AUDIO_MOD), this.f_58857_, this.f_58858_);
        this.itemMod = 1.0f;
        this.armorMod = 1.0f;
        this.blockMod = 1.0f;
        this.itemRange = 1.0f;
        this.armorRange = 1.0f;
        this.blockRange = 1.0f;
        block10: for (int i = 0; i < 9; ++i) {
            Object upgrade;
            ItemStack stack = (ItemStack)this.inventory.get(i);
            if (stack.m_41619_()) continue;
            if (stack.m_41720_() instanceof IUpgradeItem) {
                upgrade = (IUpgradeItem)stack.m_41720_();
                upgrade.onInstall(stack, machine);
                double modifier = Math.pow(upgrade.getSoundMultiplier(stack, machine), stack.m_41613_());
                switch (i / 3) {
                    case 0: {
                        this.blockMod = (float)((double)this.blockMod * modifier);
                        break;
                    }
                    case 1: {
                        this.itemMod = (float)((double)this.itemMod * modifier);
                        break;
                    }
                    case 2: {
                        this.armorMod = (float)((double)this.armorMod * modifier);
                    }
                }
                continue;
            }
            if (!(stack.m_41720_() instanceof PadUpgradeItem)) continue;
            upgrade = ((PadUpgradeItem)stack.m_41720_()).getUpgrade();
            int range = upgrade == BaseChargePadTileEntity.PadUpgrade.FIELD_MK1 ? 2 : (upgrade == BaseChargePadTileEntity.PadUpgrade.FIELD_MK2 ? 5 : (upgrade == BaseChargePadTileEntity.PadUpgrade.FIELD_MK3 ? 10 : 0)) * stack.m_41613_();
            switch (i / 3) {
                case 0: {
                    this.blockRange += (float)range;
                    continue block10;
                }
                case 1: {
                    this.itemRange += (float)range;
                    continue block10;
                }
                case 2: {
                    this.armorRange += (float)range;
                }
            }
        }
        this.updateTileFields("itemMod", "armorMod", "blockMod", "itemRange", "armorRange", "blockRange");
        this.setActive(this.itemMod != 1.0f || this.armorMod != 1.0f || this.blockMod != 1.0f);
    }
}

