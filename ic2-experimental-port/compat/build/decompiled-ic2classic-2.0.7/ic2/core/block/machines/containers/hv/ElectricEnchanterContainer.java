/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.stats.Stats
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.DataSlot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.hv;

import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IElectricEnchantable;
import ic2.core.block.machines.components.hv.ElectricEnchanterComponent;
import ic2.core.block.machines.tiles.hv.ElectricEnchanterTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.gui.components.simple.ProgressComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.item.manager.GatewayElectricItemManager;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ElectricEnchanterContainer
extends ContainerComponent<ElectricEnchanterTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/hv/gui_electric_enchanter.png");
    public static final Vec2i CHARGE_POS = new Vec2i(176, 0);
    public static final Box2i CHARGE_BOX = new Box2i(14, 20, 14, 14);
    public static final Vec2i PROGRESS_POS = new Vec2i(176, 14);
    public static final Box2i PROGRESS_BOX = new Box2i(90, 39, 24, 16);
    DataSlot seed = DataSlot.m_39401_();
    Player player;

    public ElectricEnchanterContainer(ElectricEnchanterTileEntity key, Player player, int id) {
        super(key, player, id);
        this.player = player;
        this.m_38897_(FilterSlot.createDischargeSlot(key, key.tier, 0, 13, 39));
        this.m_38897_(new FilterSlot(key, 1, 42, 39, new SimpleFilter((ItemLike)Items.f_42534_)));
        this.m_38897_(new FilterSlot(key, 2, 46, 16, SpecialFilters.BOOK_FILTER));
        this.m_38897_(new FilterSlot(key, 3, 64, 16, SpecialFilters.BOOK_FILTER));
        this.m_38897_(new FilterSlot(key, 4, 82, 16, SpecialFilters.BOOK_FILTER));
        this.m_38897_(new FilterSlot(key, 5, 64, 39, SpecialFilters.ENCHANT_FILTER));
        this.m_38897_(new EnchantOutput((IHasInventory)key, player, 6, 126, 39));
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 6);
        this.addComponent(new ElectricEnchanterComponent(key, this.seed));
        this.addComponent(new ProgressComponent(PROGRESS_BOX, key, PROGRESS_POS, false));
        this.addComponent(new ChargebarComponent(CHARGE_BOX, key, CHARGE_POS, true));
        this.m_38895_(this.seed);
        this.seed.m_6422_(player.m_36322_());
    }

    public void m_38946_() {
        this.seed.m_6422_(this.player.m_36322_());
        super.m_38946_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.modifySize(0, 6);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    public static class EnchantOutput
    extends FilterSlot {
        Player player;

        public EnchantOutput(IHasInventory inv, Player player, int index, int xPosition, int yPosition) {
            super(inv, index, xPosition, yPosition, SpecialFilters.ALWAYS_FALSE);
            this.player = player;
        }

        protected void m_5845_(ItemStack stack) {
            super.m_5845_(stack);
            this.player.m_36220_(Stats.f_12964_);
            if (stack.m_41720_() instanceof IElectricEnchantable && ((GatewayElectricItemManager)ElectricItem.MANAGER).getManager(stack) != null) {
                this.player.m_36220_(IC2Stats.ELECTRIC_ENCHANTED);
            }
        }
    }
}

