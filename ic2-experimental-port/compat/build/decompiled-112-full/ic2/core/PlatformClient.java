/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.color.IBlockColor
 *  net.minecraft.client.renderer.color.IItemColor
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.entity.RenderSnowball
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.command.ICommand
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraft.util.text.TextComponentTranslation
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.client.ClientCommandHandler
 *  net.minecraftforge.client.model.ICustomModelLoader
 *  net.minecraftforge.client.model.ModelLoaderRegistry
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fml.client.SplashProgress
 *  net.minecraftforge.fml.client.registry.ClientRegistry
 *  net.minecraftforge.fml.client.registry.IRenderFactory
 *  net.minecraftforge.fml.client.registry.RenderingRegistry
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.LoaderState
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.opengl.Display
 */
package ic2.core;

import ic2.core.GuiOverlayer;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.Platform;
import ic2.core.audio.PositionSpec;
import ic2.core.block.BlockTileEntity;
import ic2.core.block.EntityDynamite;
import ic2.core.block.EntityIC2Explosive;
import ic2.core.block.KineticGeneratorRenderer;
import ic2.core.block.RenderBlockWall;
import ic2.core.block.RenderExplosiveBlock;
import ic2.core.block.TeBlockRegistry;
import ic2.core.block.beam.EntityParticle;
import ic2.core.block.beam.RenderBeam;
import ic2.core.block.generator.tileentity.TileEntityWaterGenerator;
import ic2.core.block.generator.tileentity.TileEntityWindGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWaterKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
import ic2.core.block.personal.TileEntityPersonalChest;
import ic2.core.block.personal.TileEntityPersonalChestRenderer;
import ic2.core.block.transport.items.PipeModel;
import ic2.core.block.transport.items.PipeType;
import ic2.core.block.wiring.CableModel;
import ic2.core.command.CommandIc2c;
import ic2.core.crop.CropModel;
import ic2.core.gui.GlTexture;
import ic2.core.item.ElectricItemTooltipHandler;
import ic2.core.item.EntityIC2Boat;
import ic2.core.item.FluidCellModel;
import ic2.core.item.RenderIC2Boat;
import ic2.core.item.block.ItemFluidPipe;
import ic2.core.item.logistics.ItemPumpCover;
import ic2.core.item.logistics.PumpCoverType;
import ic2.core.item.tool.EntityMiningLaser;
import ic2.core.item.tool.RenderCrossed;
import ic2.core.item.tool.RenderObscurator;
import ic2.core.model.IReloadableModel;
import ic2.core.model.Ic2ModelLoader;
import ic2.core.network.RpcHandler;
import ic2.core.profile.ProfileManager;
import ic2.core.ref.BlockName;
import ic2.core.ref.FluidName;
import ic2.core.ref.IBlockModelProvider;
import ic2.core.ref.IFluidModelProvider;
import ic2.core.ref.IItemModelProvider;
import ic2.core.ref.ItemName;
import ic2.core.ref.MetaTeBlock;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.io.File;
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.command.ICommand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.SplashProgress;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.Display;

@SideOnly(value=Side.CLIENT)
public class PlatformClient
extends Platform {
    private final Minecraft mc = Minecraft.func_71410_x();

    @Override
    public boolean isRendering() {
        return Minecraft.func_71410_x().func_152345_ab();
    }

    @Override
    public void preInit() {
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandIc2c());
        for (BlockName blockName : BlockName.values) {
            if (!blockName.hasInstance()) {
                IC2.log.warn(LogCategory.Block, "The block " + (Object)((Object)blockName) + " is not initialized.");
                continue;
            }
            ((IBlockModelProvider)blockName.getInstance()).registerModels(blockName);
        }
        for (BlockTileEntity block : TeBlockRegistry.getAllBlocks()) {
            if (block.isIC2()) continue;
            block.registerModels(null);
        }
        for (ItemName itemName : ItemName.values) {
            if (!itemName.hasInstance()) {
                IC2.log.warn(LogCategory.Item, "The item " + itemName + " is not initialized.");
                continue;
            }
            ((IItemModelProvider)itemName.getInstance()).registerModels(itemName);
        }
        for (FluidName fluidName : FluidName.values) {
            if (!fluidName.hasInstance()) {
                IC2.log.warn(LogCategory.Block, "The fluid " + fluidName + " is not initialized.");
                continue;
            }
            Fluid provider = fluidName.getInstance();
            if (!(provider instanceof IFluidModelProvider)) continue;
            ((IFluidModelProvider)provider).registerModels(fluidName);
        }
        Ic2ModelLoader loader = new Ic2ModelLoader();
        loader.register("models/block/cf/wall", (IReloadableModel)new RenderBlockWall());
        loader.register("models/block/crop/crop", (IReloadableModel)new CropModel());
        loader.register("models/block/wiring/cable", (IReloadableModel)new CableModel());
        loader.register("models/block/transport/item_pipe", (IReloadableModel)new PipeModel());
        loader.register("models/item/cell/fluid_cell", (IReloadableModel)new FluidCellModel());
        loader.register("models/item/tool/electric/obscurator", (IReloadableModel)new RenderObscurator());
        ModelLoaderRegistry.registerLoader((ICustomModelLoader)loader);
        ProfileManager.doTextureChanges();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPersonalChest.class, (TileEntitySpecialRenderer)new TileEntityPersonalChestRenderer());
        KineticGeneratorRenderer kineticRenderer = new KineticGeneratorRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindKineticGenerator.class, kineticRenderer);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWaterKineticGenerator.class, kineticRenderer);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindGenerator.class, kineticRenderer);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWaterGenerator.class, kineticRenderer);
        RenderingRegistry.registerEntityRenderingHandler(EntityIC2Explosive.class, (IRenderFactory)new IRenderFactory<EntityIC2Explosive>(){

            public Render<EntityIC2Explosive> createRenderFor(RenderManager manager) {
                return new RenderExplosiveBlock(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityMiningLaser.class, (IRenderFactory)new IRenderFactory<EntityMiningLaser>(){

            public Render<EntityMiningLaser> createRenderFor(RenderManager manager) {
                return new RenderCrossed(manager, new ResourceLocation("ic2", "textures/models/laser.png"));
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityIC2Boat.class, (IRenderFactory)new IRenderFactory<EntityBoat>(){

            public Render<EntityBoat> createRenderFor(RenderManager manager) {
                return new RenderIC2Boat(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, (IRenderFactory)new IRenderFactory<EntityDynamite>(){

            public Render<EntityDynamite> createRenderFor(RenderManager manager) {
                return new RenderSnowball(manager, ItemName.dynamite.getInstance(), PlatformClient.this.mc.func_175599_af());
            }
        });
        if (Util.inDev()) {
            RenderingRegistry.registerEntityRenderingHandler(EntityParticle.class, manager -> new RenderBeam(manager));
        }
        GlTexture.init();
    }

    @Override
    public void displayError(String error, Object ... args) {
        if (!this.mc.func_152345_ab()) {
            super.displayError(error, args);
            return;
        }
        if (args.length > 0) {
            error = String.format(error, args);
        }
        error = "IndustrialCraft 2 Error\n\n" + error;
        String dialogError = error.replaceAll("([^\n]{80,}?) ", "$1\n");
        error = error.replace("\n", System.getProperty("line.separator"));
        dialogError = dialogError.replace("\n", System.getProperty("line.separator"));
        IC2.log.error(LogCategory.General, "%s", error);
        this.mc.func_71364_i();
        try {
            if (!Loader.instance().hasReachedState(LoaderState.AVAILABLE)) {
                SplashProgress.finish();
            }
            Display.destroy();
            JFrame frame = new JFrame("IndustrialCraft 2 Error");
            frame.setUndecorated(true);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            JOptionPane.showMessageDialog(frame, dialogError, "IndustrialCraft 2 Error", 0);
        }
        catch (Throwable t) {
            IC2.log.error(LogCategory.General, t, "Exception caught while showing an error.");
        }
        Util.exit(1);
    }

    @Override
    public EntityPlayer getPlayerInstance() {
        return this.mc.field_71439_g;
    }

    @Override
    public World getWorld(int dimId) {
        if (this.isSimulating()) {
            return super.getWorld(dimId);
        }
        WorldClient world = this.mc.field_71441_e;
        return world.field_73011_w.getDimension() == dimId ? world : null;
    }

    @Override
    public World getPlayerWorld() {
        return this.mc.field_71441_e;
    }

    @Override
    public void messagePlayer(EntityPlayer player, String message, Object ... args) {
        if (args.length > 0) {
            this.mc.field_71456_v.func_146158_b().func_146227_a((ITextComponent)new TextComponentTranslation(message, (Object[])this.getMessageComponents(args)));
        } else {
            this.mc.field_71456_v.func_146158_b().func_146227_a((ITextComponent)new TextComponentString(message));
        }
    }

    @Override
    public boolean launchGuiClient(EntityPlayer player, IHasGui inventory, boolean isAdmin) {
        this.mc.func_147108_a(inventory.getGui(player, isAdmin));
        return true;
    }

    @Override
    public void profilerStartSection(String section) {
        if (this.isRendering()) {
            this.mc.field_71424_I.func_76320_a(section);
        } else {
            super.profilerStartSection(section);
        }
    }

    @Override
    public void profilerEndSection() {
        if (this.isRendering()) {
            this.mc.field_71424_I.func_76319_b();
        } else {
            super.profilerEndSection();
        }
    }

    @Override
    public void profilerEndStartSection(String section) {
        if (this.isRendering()) {
            this.mc.field_71424_I.func_76318_c(section);
        } else {
            super.profilerEndStartSection(section);
        }
    }

    @Override
    public File getMinecraftDir() {
        return this.mc.field_71412_D;
    }

    @Override
    public void playSoundSp(String sound, float f, float g) {
        IC2.audioManager.playOnce(this.getPlayerInstance(), PositionSpec.Hand, sound, true, IC2.audioManager.getDefaultVolume());
    }

    @Override
    public void onPostInit() {
        MinecraftForge.EVENT_BUS.register((Object)new GuiOverlayer(this.mc));
        new RpcHandler();
        new ElectricItemTooltipHandler();
        Object leaves = BlockName.leaves.getInstance();
        this.mc.func_184125_al().func_186722_a(new IBlockColor(){

            public int func_186720_a(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
                return 0x669944;
            }
        }, new Block[]{leaves});
        this.mc.getItemColors().func_186731_a(new IItemColor(){

            public int func_186726_a(ItemStack stack, int tintIndex) {
                return 0x669944;
            }
        }, new Block[]{leaves});
        this.mc.getItemColors().func_186730_a(new IItemColor(){

            public int func_186726_a(ItemStack stack, int tintIndex) {
                return tintIndex > 0 ? -1 : ((ItemArmor)stack.func_77973_b()).func_82814_b(stack);
            }
        }, new Item[]{ItemName.quantum_helmet.getInstance(), ItemName.quantum_chestplate.getInstance(), ItemName.quantum_leggings.getInstance(), ItemName.quantum_boots.getInstance()});
        this.mc.getItemColors().func_186730_a(new IItemColor(){

            public int func_186726_a(ItemStack stack, int tintIndex) {
                PipeType type = ItemFluidPipe.getPipeType(stack);
                return (type.red & 0xFF) << 16 | (type.green & 0xFF) << 8 | type.blue & 0xFF;
            }
        }, new Item[]{ItemName.pipe.getInstance()});
        this.mc.func_184125_al().func_186722_a(new IBlockColor(){

            public int func_186720_a(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
                String variant = ((MetaTeBlock)state.func_177229_b(((BlockTileEntity)state.func_177230_c()).typeProperty)).teBlock.getName();
                if (variant.endsWith("_storage_box")) {
                    switch (variant) {
                        case "wooden_storage_box": {
                            return 10454093;
                        }
                        case "iron_storage_box": {
                            return 0xC8C8C8;
                        }
                        case "bronze_storage_box": {
                            return 0xFF8000;
                        }
                        case "steel_storage_box": {
                            return 0x808080;
                        }
                    }
                    return 0xFFFFFF;
                }
                return 0xFFFFFF;
            }
        }, new Block[]{BlockName.te.getInstance()});
        this.mc.getItemColors().func_186731_a(new IItemColor(){

            public int func_186726_a(ItemStack stack, int tintIndex) {
                String variant = Objects.requireNonNull(BlockName.te.getVariant(stack));
                if (variant.endsWith("_storage_box")) {
                    switch (variant) {
                        case "wooden_storage_box": {
                            return 10454093;
                        }
                        case "iron_storage_box": {
                            return 0xC8C8C8;
                        }
                        case "bronze_storage_box": {
                            return 0xFF8000;
                        }
                        case "steel_storage_box": {
                            return 0x808080;
                        }
                    }
                    return 0xFFFFFF;
                }
                return 0xFFFFFF;
            }
        }, new Block[]{BlockName.te.getInstance()});
        this.mc.getItemColors().func_186730_a((stack, tintIndex) -> {
            PumpCoverType type = (PumpCoverType)((ItemPumpCover)stack.func_77973_b()).getType(stack);
            return tintIndex == 1 ? type.color : 0xFFFFFF;
        }, new Item[]{ItemName.cover.getInstance()});
    }

    @Override
    public void requestTick(boolean simulating, Runnable runnable) {
        if (simulating) {
            super.requestTick(simulating, runnable);
        } else {
            this.mc.func_152344_a(runnable);
        }
    }

    @Override
    public int getColorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tint) {
        return this.mc.func_184125_al().func_186724_a(state, world, pos, tint);
    }
}

