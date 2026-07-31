/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.DimensionType
 *  net.minecraft.world.World
 */
package ic2.core.item.tfbp;

import ic2.api.item.ITerraformingBP;
import ic2.core.IC2;
import ic2.core.block.state.IIdProvider;
import ic2.core.item.ItemMulti;
import ic2.core.item.tfbp.Chilling;
import ic2.core.item.tfbp.Cultivation;
import ic2.core.item.tfbp.Desertification;
import ic2.core.item.tfbp.Flatification;
import ic2.core.item.tfbp.Irrigation;
import ic2.core.item.tfbp.Mushroom;
import ic2.core.item.tfbp.TerraformerBase;
import ic2.core.ref.ItemName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class Tfbp
extends ItemMulti<TfbpType>
implements ITerraformingBP {
    public static void init() {
        for (TfbpType tfbp : TfbpType.values()) {
            if (tfbp.logic == null) continue;
            tfbp.logic.init();
        }
    }

    public Tfbp() {
        super(ItemName.tfbp, TfbpType.class);
        this.func_77625_d(1);
    }

    @Override
    public double getConsume(ItemStack stack) {
        TfbpType type = (TfbpType)this.getType(stack);
        return type == null ? 0.0 : type.consume;
    }

    @Override
    public int getRange(ItemStack stack) {
        TfbpType type = (TfbpType)this.getType(stack);
        return type == null ? 0 : type.range;
    }

    @Override
    public boolean canInsert(ItemStack stack, EntityPlayer player, World world, BlockPos pos) {
        TfbpType type = (TfbpType)this.getType(stack);
        if (type == null) {
            return false;
        }
        if (type == TfbpType.cultivation && world.field_73011_w.func_186058_p() == DimensionType.THE_END) {
            IC2.achievements.issueAchievement(player, "terraformEndCultivation");
        }
        return true;
    }

    @Override
    public boolean terraform(ItemStack stack, World world, BlockPos pos) {
        TfbpType type = (TfbpType)this.getType(stack);
        if (type == null) {
            return false;
        }
        if (type.logic == null) {
            return false;
        }
        return type.logic.terraform(world, pos);
    }

    public static enum TfbpType implements IIdProvider
    {
        blank(0.0, 0, null),
        chilling(2000.0, 50, new Chilling()),
        cultivation(4000.0, 40, new Cultivation()),
        desertification(2500.0, 40, new Desertification()),
        flatification(4000.0, 40, new Flatification()),
        irrigation(3000.0, 60, new Irrigation()),
        mushroom(8000.0, 25, new Mushroom());

        public final double consume;
        public final int range;
        final TerraformerBase logic;

        private TfbpType(double consume, int range, TerraformerBase logic) {
            this.consume = consume;
            this.range = range;
            this.logic = logic;
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

