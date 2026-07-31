/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.cables;

import ic2.core.block.cables.CableBlock;
import ic2.core.block.cables.Cables;
import ic2.core.block.rendering.block.tubes.SuperCableModel;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.models.BaseModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SuperCableBlock
extends CableBlock {
    public static final CableBlock.CableInstance INSTANCE = new CableBlock.CableInstance(false, false, false, 0, new float[]{3.0f}, "electric/cable", Cables::getSuperConductor);

    public SuperCableBlock() {
        super("super_cable", IC2Tiles.SUPER_CABLE);
        this.setOverrideRarity(Rarity.UNCOMMON);
    }

    @Override
    public CableBlock.CableInstance createInstance() {
        return INSTANCE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(BlockState state, boolean center, Direction dir, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("electric/cable/super").get(side.m_122434_().m_122479_() ? "super_side" : "super_top");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return (Integer)state.m_61143_((Property)FOAMED) == 0 ? new SuperCableModel(this, state) : super.getForCustomState(state);
    }
}

