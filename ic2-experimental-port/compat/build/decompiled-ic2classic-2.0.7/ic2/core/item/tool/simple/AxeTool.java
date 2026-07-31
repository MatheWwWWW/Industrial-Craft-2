/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.item.AxeItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.item.tool.simple;

import ic2.core.IC2;
import ic2.core.block.resource.RubberwoodLogBlock;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.platform.rendering.features.item.IToolModel;
import ic2.core.utils.plugins.IRegistryProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public class AxeTool
extends AxeItem
implements ISimpleItemModel,
IToolModel,
IRegistryProvider {
    private String textureName;
    private String textureFolder;
    private ResourceLocation id;

    public AxeTool(String itemName, Item.Properties properties, String textureFolder, String textureName, Tier tier) {
        super(tier, 6.0f, -3.1f, properties);
        this.id = GameData.checkPrefix((String)itemName, (boolean)false);
        this.textureFolder = textureFolder;
        this.textureName = textureName;
    }

    public AxeTool(String itemName, String textureFolder, String textureName, Tier tier) {
        this(itemName, new Item.Properties().m_41491_(IC2.IC2_MAIN_GROUP), textureFolder, textureName, tier);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    public InteractionResult m_6225_(UseOnContext context) {
        InteractionResult result = super.m_6225_(context);
        if (result != InteractionResult.PASS) {
            return result;
        }
        Level world = context.m_43725_();
        BlockPos pos = context.m_8083_();
        BlockState state = context.m_43725_().m_8055_(pos);
        if (!world.m_5776_()) {
            if (state.m_60734_() == IC2Blocks.RUBBERWOOD_LOG) {
                world.m_7731_(pos, (BlockState)((BlockState)((BlockState)((BlockState)IC2Blocks.RUBBER_LOG_STRIPPED.m_49966_().m_61124_(RubberwoodLogBlock.AXIS, (Comparable)((Direction.Axis)state.m_61143_(RubberwoodLogBlock.AXIS)))).m_61124_((Property)RubberwoodLogBlock.RESIN, (Comparable)((Boolean)state.m_61143_((Property)RubberwoodLogBlock.RESIN)))).m_61124_((Property)RubberwoodLogBlock.COLLECTABLE, (Comparable)((Boolean)state.m_61143_((Property)RubberwoodLogBlock.COLLECTABLE)))).m_61124_((Property)RubberwoodLogBlock.RESIN_FACING, (Comparable)((Direction)state.m_61143_((Property)RubberwoodLogBlock.RESIN_FACING))), 11);
                return InteractionResult.SUCCESS;
            }
            if (state.m_60734_() == IC2Blocks.RUBBERWOOD_LOG_BARKED) {
                world.m_7731_(pos, (BlockState)IC2Blocks.RUBBER_LOG_BARKED_STRIPPED.m_49966_().m_61124_(RubberwoodLogBlock.AXIS, (Comparable)((Direction.Axis)state.m_61143_(RubberwoodLogBlock.AXIS))), 11);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), this.textureFolder).get(this.textureName);
    }
}

