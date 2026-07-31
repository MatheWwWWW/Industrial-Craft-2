/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.ev;

import ic2.core.block.machines.components.ev.CrafterComponent;
import ic2.core.block.machines.tiles.ev.CrafterTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.MemorySlot;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrafterContainer
extends ContainerComponent<CrafterTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/ev/gui_crafter.png");

    public CrafterContainer(CrafterTileEntity key, Player player, int id) {
        super(key, player, id);
        for (int i = 0; i < 18; ++i) {
            this.m_38897_(new SlotBase(key, i, 8 + i % 9 * 18, 50 + i / 9 * 18));
        }
        this.m_38897_(new FilterSlot(key.upgrades, 0, 8, 24, new SimpleFilter((ItemLike)IC2Items.CRAFTING_UPGRADE)));
        this.m_38897_(new FilterSlot(key.upgrades, 1, 26, 24, new SimpleFilter((ItemLike)IC2Items.OVERCLOCKER_UPGRADE)));
        this.m_38897_(new FilterSlot(key.upgrades, 2, 152, 24, new SimpleFilter((ItemLike)IC2Items.MEMORY_STICK)));
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                this.m_38897_(new MemorySlot(key, x + y * 3, 180 + 18 * x, 6 + 18 * y));
            }
        }
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 13);
        this.addComponent(new CrafterComponent());
        this.addComponent(new ChargebarComponent(new Box2i(45, 24, 14, 14), key, new Vec2i(0, 179), true));
    }

    @Override
    public int getInventorySize() {
        return 30;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setYSize(179);
    }
}

