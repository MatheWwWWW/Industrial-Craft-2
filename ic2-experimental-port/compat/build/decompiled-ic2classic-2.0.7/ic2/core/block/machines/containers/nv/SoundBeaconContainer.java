/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.nv;

import ic2.core.block.machines.components.misc.SoundBeaconComponent;
import ic2.core.block.machines.tiles.nv.SoundBeaconTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SetItemFilter;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.AreaOfEffectComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SoundBeaconContainer
extends ContainerComponent<SoundBeaconTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/stone/gui_sound_beacon.png");

    public SoundBeaconContainer(SoundBeaconTileEntity key, Player player, int id) {
        super(key, player, id);
        SetItemFilter filter = new SetItemFilter(IC2Items.LOUDNESS_UPGRADE, IC2Items.MUFFLER_UPGRADE, IC2Items.MUTE_UPGRADE, IC2Items.BASIC_FIELD_PAD_UPGRADE, IC2Items.FIELD_PAD_UPGRADE, IC2Items.ADVANCED_FIELD_PAD_UPGRADE);
        this.m_38897_(new FilterSlot(key, 0, 22, 70, filter));
        this.m_38897_(new FilterSlot(key, 1, 35, 48, filter));
        this.m_38897_(new FilterSlot(key, 2, 46, 70, filter));
        this.m_38897_(new FilterSlot(key, 3, 73, 70, filter));
        this.m_38897_(new FilterSlot(key, 4, 86, 48, filter));
        this.m_38897_(new FilterSlot(key, 5, 97, 70, filter));
        this.m_38897_(new FilterSlot(key, 6, 125, 70, filter));
        this.m_38897_(new FilterSlot(key, 7, 138, 48, filter));
        this.m_38897_(new FilterSlot(key, 8, 149, 70, filter));
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 18);
        this.addComponent(new SoundBeaconComponent(key));
        this.addComponent(new AreaOfEffectComponent(key, new Box2i(119, 88, 50, 12)));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setYSize(184);
    }
}

