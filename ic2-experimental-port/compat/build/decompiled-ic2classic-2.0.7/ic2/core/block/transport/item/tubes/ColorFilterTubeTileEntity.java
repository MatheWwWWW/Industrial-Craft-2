/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.ICopyableSettings;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.api.util.DirectionList;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.block.transport.item.container.ColorFilterTubeContainer;
import ic2.core.inventory.base.ICamouflageGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.SynchronizedList;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ColorFilterTubeTileEntity
extends TubeTileEntity
implements ICamouflageGui,
ICopyableSettings {
    @NetworkInfo
    public int[] colorDirections = new int[16];
    @NetworkInfo
    public int emptyDirections = 63;
    @NetworkInfo
    public boolean invPriority = false;
    @NetworkInfo
    public SynchronizedList<DyeColor> usedColors = new SynchronizedList<DyeColor>(IOutputBuffer::writeEnum, T -> T.readEnum(DyeColor.class));

    public ColorFilterTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("colorDirections", "emptyDirections", "invPriority", "usedColors");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.COLOR_FILTER_TUBE;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putBoolean(compound, "invPriority", this.invPriority, false);
        compound.m_128385_("color_dirs", this.colorDirections);
        byte[] dirs = new byte[this.usedColors.size() * 2];
        int m = this.usedColors.size();
        for (int i = 0; i < m; ++i) {
            DyeColor color = (DyeColor)this.usedColors.get(i);
            dirs[i * 2] = (byte)color.m_41060_();
            dirs[i * 2 + 1] = (byte)this.colorDirections[color.m_41060_()];
        }
        compound.m_128382_("filters", dirs);
        compound.m_128405_("emptyDirs", this.emptyDirections);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        byte[] dirs = compound.m_128463_("filters");
        int m = dirs.length / 2;
        for (int i = 0; i < m; ++i) {
            DyeColor color = DyeColor.m_41053_((int)dirs[i * 2]);
            this.usedColors.add(color);
            this.colorDirections[color.m_41060_()] = dirs[i * 2 + 1];
        }
        this.invPriority = compound.m_128471_("invPriority");
        this.emptyDirections = compound.m_128441_("emptyDirs") ? compound.m_128451_("emptyDirs") : this.generateEmptyDirs();
    }

    @Override
    public void saveSettings(CompoundTag tag) {
        NBTUtils.putBoolean(tag, "invPriority", this.invPriority, false);
        tag.m_128385_("color_dirs", this.colorDirections);
        tag.m_128405_("emptyDirs", this.emptyDirections);
        byte[] dirs = new byte[this.usedColors.size() * 2];
        int m = this.usedColors.size();
        for (int i = 0; i < m; ++i) {
            DyeColor color = (DyeColor)this.usedColors.get(i);
            dirs[i * 2] = (byte)color.m_41060_();
            dirs[i * 2 + 1] = (byte)this.colorDirections[color.m_41060_()];
        }
        tag.m_128382_("filters", dirs);
    }

    @Override
    public void loadSettings(CompoundTag tag) {
        this.colorDirections = new int[16];
        this.usedColors.clear();
        byte[] dirs = tag.m_128463_("filters");
        int m = dirs.length / 2;
        for (int i = 0; i < m; ++i) {
            DyeColor color = DyeColor.m_41053_((int)dirs[i * 2]);
            this.usedColors.add(color);
            this.colorDirections[color.m_41060_()] = dirs[i * 2 + 1];
        }
        this.invPriority = tag.m_128471_("invPriority");
        this.emptyDirections = tag.m_128441_("emptyDirs") ? tag.m_128451_("emptyDirs") : this.generateEmptyDirs();
        this.updateGuiFields("colorDirections", "emptyDirections", "invPriority", "usedColors");
    }

    private int generateEmptyDirs() {
        DirectionList list = DirectionList.ALL;
        for (int i = 0; i < 16; ++i) {
            list = list.remove(DirectionList.ofNumber(this.colorDirections[i]));
        }
        return list.getCode();
    }

    @Override
    public boolean isFoamed() {
        return this.foamed > 0;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ColorFilterTubeContainer(this, player, windowID);
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 1) {
            if (value >= 0 && value < this.usedColors.size()) {
                this.colorDirections[((DyeColor)this.usedColors.remove((int)value)).m_41060_()] = 0;
                this.updateGuiFields("usedColors", "colorDirections");
            }
        } else if (key == 2) {
            this.invPriority = !this.invPriority;
            this.updateGuiField("invPriority");
        } else if (key == 3) {
            DyeColor color = DyeColor.m_41053_((int)value);
            if (this.usedColors.contains(color)) {
                return;
            }
            this.usedColors.add(color);
            this.colorDirections[color.m_41060_()] = DirectionList.ALL.getCode();
            this.updateGuiFields("usedColors", "colorDirections");
        } else if (key >= 10 && key < 16) {
            if (value >= 0 && value < this.usedColors.size()) {
                DyeColor color = (DyeColor)this.usedColors.get(value);
                int n = color.m_41060_();
                this.colorDirections[n] = this.colorDirections[n] ^ 1 << key - 10;
                this.updateGuiField("colorDirections");
            } else if (value == -1) {
                this.emptyDirections ^= 1 << Direction.m_122376_((int)(key - 10)).m_122411_();
                this.updateGuiField("emptyDirections");
            }
        }
    }

    @Override
    public DirectionList getValidDirections(TransportedItem item) {
        DirectionList list = super.getValidDirections(item);
        if (this.isSimulating() && list.size() > 0) {
            DirectionList filter;
            list = item.getColor() != null ? ((filter = list.keep(DirectionList.ofNumber(this.colorDirections[item.getColor().m_41060_()]))).isEmpty() ? list.keep(DirectionList.ofNumber(this.emptyDirections)) : filter) : list.keep(DirectionList.ofNumber(this.emptyDirections));
            if (this.invPriority && item.getRequestId() == null && list.keep(this.inventories.getPresentSides()).size() > 0) {
                list = list.keep(this.inventories.getPresentSides());
            }
        }
        return list;
    }
}

