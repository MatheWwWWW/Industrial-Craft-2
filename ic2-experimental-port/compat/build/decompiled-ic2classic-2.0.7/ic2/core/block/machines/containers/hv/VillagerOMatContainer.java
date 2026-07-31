/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.hv;

import ic2.core.block.machines.components.hv.villager.VillagerOMatComponent;
import ic2.core.block.machines.components.hv.villager.VillagerSelectorComponent;
import ic2.core.block.machines.tiles.hv.VillagerOMatTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.AreaOfEffectComponent;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VillagerOMatContainer
extends ContainerComponent<VillagerOMatTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/hv/gui_villager_o_mat.png");

    public VillagerOMatContainer(VillagerOMatTileEntity key, Player player, int id) {
        super(key, player, id);
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, 47);
        VillagerOMatComponent villager = new VillagerOMatComponent(key);
        this.addComponent(villager);
        this.addComponent(new VillagerSelectorComponent(key, villager::setVillager));
        this.addComponent(new ChargebarComponent(new Box2i(155, 34, 14, 14), key, new Vec2i(177, 0), true));
        this.addComponent(new AreaOfEffectComponent(key, new Box2i(119, 117, 50, 12)));
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.setMaxSize(177, 213);
    }
}

