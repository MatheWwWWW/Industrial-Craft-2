/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.mv;

import ic2.core.block.machines.components.mv.SlowGrinderComponent;
import ic2.core.block.machines.tiles.mv.SlowGrinderTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.gui.components.simple.ProgressComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.UpgradeSlot;
import ic2.core.inventory.slot.XPSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SlowGrinderContainer
extends ContainerComponent<SlowGrinderTileEntity> {
    public static final Vec2i CHARGE_POS = new Vec2i(176, 0);
    public static final Box2i CHARGE_BOX = new Box2i(56, 36, 14, 14);
    public static final Vec2i PROGRESS_POS = new Vec2i(176, 14);
    public static final Box2i PROGRESS_BOX = new Box2i(79, 34, 24, 16);
    public static final Vec2i INV_OFFSET = new Vec2i(40, 67);
    public static final Vec2i PREVIEW_OFFSET = new Vec2i(51, 56);
    public static final Vec2i COMPARATOR_OFFSET = new Vec2i(51, 67);

    public SlowGrinderContainer(SlowGrinderTileEntity key, Player player, int id) {
        super(key, player, id);
        int i;
        IFilter filter = T -> key.getRecipeList().getRecipe(T, false) != null;
        this.m_38897_(FilterSlot.createDischargeSlot(key, key.tier, 0, 56, 53));
        this.m_38897_(new FilterSlot(key, 1, 56, 17, filter));
        this.m_38897_(new XPSlot(key, 2, 116, 35));
        for (i = 0; i < 8; ++i) {
            int x = i % 2 * 18;
            int y = i / 2 * 18;
            this.m_38897_(new FilterSlot(key, i + 3, 8 + x, 8 + y, filter));
        }
        for (i = 0; i < key.upgradeSlots; ++i) {
            this.m_38897_(new UpgradeSlot(key, 11 + i, 152, 8 + i * 18));
        }
        this.addPlayerInventory(player.m_150109_());
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, CHARGE_POS, true));
        this.addComponent(new ProgressComponent(PROGRESS_BOX, key, PROGRESS_POS, false));
        this.addComponent(new SlowGrinderComponent(key));
    }

    @Override
    public ResourceLocation getTexture() {
        return ((SlowGrinderTileEntity)this.getHolder()).getTexture();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setPlayerInventoryOffset(71, 0);
    }

    @Override
    public Vec2i getInvButtonOffset() {
        return INV_OFFSET;
    }

    @Override
    public Vec2i getPreviewButtonOffset() {
        return PREVIEW_OFFSET;
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return COMPARATOR_OFFSET;
    }

    @Override
    protected boolean m_38903_(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        return this.moveItemStackToPriorizeUpgradeSlots(stack, startIndex, endIndex, reverseDirection);
    }
}

