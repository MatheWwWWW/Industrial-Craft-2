/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.texture.TextureUtil
 *  net.minecraft.client.shader.Framebuffer
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.CommandException
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.command.WrongUsageException
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldServer
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.DimensionManager
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.registry.ForgeRegistries
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  net.minecraftforge.oredict.OreDictionary
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package ic2.core.command;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.recipe.IRecipeInput;
import ic2.core.IC2;
import ic2.core.IWorldTickCallback;
import ic2.core.energy.grid.EnergyNetGlobal;
import ic2.core.energy.grid.EnergyNetLocal;
import ic2.core.energy.grid.EnergyNetSettings;
import ic2.core.energy.grid.GridInfo;
import ic2.core.item.ItemCropSeed;
import ic2.core.ref.IMultiBlock;
import ic2.core.ref.IMultiItem;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.core.uu.DropScan;
import ic2.core.uu.UuGraph;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class CommandIc2
extends CommandBase {
    public String func_71517_b() {
        return "ic2";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return "/ic2 uu-world-scan <tiny|small|medium|large> | debug (dumpUuValues | resolveIngredient <name> | dumpTextures <name> <size> | dumpLargeGrids | enet (logIssues | logUpdates) (true|false)) | currentItem | itemNameWithVariant | giveCrop <owner> <name> <growth (1-31)> <gain (1-31)> <resistance (1-31)>";
    }

    public List<String> func_184883_a(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return CommandIc2.func_71530_a((String[])args, (String[])new String[]{"uu-world-scan", "debug", "currentItem", "itemNameWithVariant", "giveCrop"});
        }
        if (args.length == 2 && args[0].equals("uu-world-scan")) {
            return CommandIc2.func_71530_a((String[])args, (String[])new String[]{"tiny", "small", "medium", "large"});
        }
        if (args.length >= 2 && args[0].equals("debug")) {
            return this.getDebugTabCompletionOptions(server, sender, args, pos);
        }
        if (args.length == 6 && args[0].equals("giveCrop")) {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    private List<String> getDebugTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 2) {
            return CommandIc2.func_71530_a((String[])args, (String[])new String[]{"dumpUuValues", "resolveIngredient", "dumpTextures", "dumpLargeGrids", "enet"});
        }
        if (args.length == 3 && args[1].equals("resolveIngredient")) {
            ArrayList<String> possibilities = new ArrayList<String>(1024);
            for (ResourceLocation loc : Item.field_150901_e.func_148742_b()) {
                possibilities.add(loc.toString());
            }
            for (String name : OreDictionary.getOreNames()) {
                possibilities.add("OreDict:" + name);
            }
            for (String name : FluidRegistry.getRegisteredFluids().keySet()) {
                possibilities.add("Fluid:" + name);
            }
            return CommandIc2.func_175762_a((String[])args, possibilities);
        }
        if (args.length >= 3 && "dumpTextures".equals(args[1])) {
            if (args.length == 3) {
                ArrayList<String> possibilities = new ArrayList<String>(1024);
                for (ResourceLocation loc : Item.field_150901_e.func_148742_b()) {
                    possibilities.add(loc.toString());
                }
                return CommandIc2.func_175762_a((String[])args, possibilities);
            }
            if (args.length == 4) {
                ArrayList<String> possibilities = new ArrayList<String>();
                for (int num = 512; num > 8; num = (int)((short)(num >> 1))) {
                    possibilities.add(Integer.toString(num));
                }
                return CommandIc2.func_175762_a((String[])args, possibilities);
            }
        } else if (args.length >= 3 && "enet".equals(args[1])) {
            if (args.length == 3) {
                ArrayList<String> possibilities = new ArrayList<String>(1024);
                for (ResourceLocation loc : Item.field_150901_e.func_148742_b()) {
                    possibilities.add(loc.toString());
                }
                return CommandIc2.func_71530_a((String[])args, (String[])new String[]{"logIssues", "logUpdates"});
            }
            if (args.length == 4) {
                return CommandIc2.func_71530_a((String[])args, (String[])new String[]{"true", "false"});
            }
        }
        return Collections.emptyList();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
        }
        if (args.length == 2 && args[0].equals("uu-world-scan")) {
            this.cmdUuWorldScan(sender, args[1]);
            return;
        } else if (args[0].equals("debug")) {
            if (args.length == 2 && args[1].equals("dumpUuValues")) {
                this.cmdDumpUuValues(sender);
                return;
            } else if (args.length == 3 && args[1].equals("resolveIngredient")) {
                this.cmdDebugResolveIngredient(sender, args[2]);
                return;
            } else if (args.length == 4 && args[1].equals("dumpTextures")) {
                this.cmdDebugDumpTextures(sender, args[2], args[3]);
                return;
            } else if (args.length == 2 && args[1].equals("dumpLargeGrids")) {
                this.dumpLargeGrids(sender);
                return;
            } else {
                if (args.length != 4 || !args[1].equals("enet")) throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
                this.cmdDebugEnet(sender, args[2], CommandIc2.func_180527_d((String)args[3]));
            }
            return;
        } else if (args.length == 1 && args[0].equals("currentItem")) {
            CommandIc2.cmdCurrentItem(sender);
            return;
        } else if (args.length == 1 && args[0].equals("itemNameWithVariant") && sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)sender;
            ItemStack stack = player.field_71071_by.func_70448_g();
            if (StackUtil.isEmpty(stack)) {
                CommandIc2.msg(sender, "empty: " + StackUtil.toStringSafe(stack));
                return;
            } else if (!stack.func_77973_b().getClass().getCanonicalName().startsWith("ic2.core")) {
                CommandIc2.msg(sender, "Not an IC2 Item.");
                return;
            } else {
                String name = Util.getName(stack.func_77973_b()).func_110623_a();
                String variant = null;
                if (stack.func_77973_b() instanceof IMultiItem) {
                    variant = ((IMultiItem)stack.func_77973_b()).getVariant(stack);
                } else if (stack.func_77973_b() instanceof ItemBlock && ((ItemBlock)stack.func_77973_b()).func_179223_d() instanceof IMultiBlock) {
                    variant = ((IMultiBlock)((ItemBlock)stack.func_77973_b()).func_179223_d()).getVariant(stack);
                }
                CommandIc2.msg(sender, "Name: " + name + (variant == null ? "" : " Variant: " + variant));
            }
            return;
        } else if (args.length == 6 && args[0].equals("giveCrop") && sender instanceof EntityPlayer) {
            this.cmdGiveCrop(sender, args);
            return;
        } else {
            CommandIc2.msg(sender, "Unknown Command.");
        }
    }

    private void cmdUuWorldScan(ICommandSender sender, String arg) throws CommandException {
        int areaCount;
        if (arg.equals("tiny")) {
            areaCount = 128;
        } else if (arg.equals("small")) {
            areaCount = 1024;
        } else if (arg.equals("medium")) {
            areaCount = 2048;
        } else if (arg.equals("large")) {
            areaCount = 4096;
        } else {
            throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
        }
        float time = (float)areaCount * 0.0032f;
        CommandIc2.msg(sender, String.format("Starting world scan, this will take about %.1f minutes with a powerful cpu.", Float.valueOf(time)));
        CommandIc2.msg(sender, "The server will not respond while the calculations are running.");
        WorldServer world = null;
        world = sender instanceof EntityPlayerMP ? ((EntityPlayerMP)sender).func_71121_q() : DimensionManager.getWorld((int)0);
        if (world == null) {
            CommandIc2.msg(sender, "Can't determine the world to scan.");
            return;
        }
        int area = 50000;
        int range = 5;
        DropScan scan = new DropScan(world, range);
        scan.start(area, areaCount);
        scan.cleanup();
    }

    private void cmdDumpUuValues(ICommandSender sender) {
        ArrayList<Map.Entry<ItemStack, Double>> list = new ArrayList<Map.Entry<ItemStack, Double>>();
        Iterator<Map.Entry<ItemStack, Double>> it = UuGraph.iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        Collections.sort(list, new Comparator<Map.Entry<ItemStack, Double>>(){

            @Override
            public int compare(Map.Entry<ItemStack, Double> a, Map.Entry<ItemStack, Double> b) {
                return a.getKey().func_77973_b().func_77653_i(a.getKey()).compareTo(b.getKey().func_77973_b().func_77653_i(b.getKey()));
            }
        });
        CommandIc2.msg(sender, "UU Values:");
        for (Map.Entry entry : list) {
            CommandIc2.msg(sender, String.format("  %s: %s", ((ItemStack)entry.getKey()).func_77973_b().func_77653_i((ItemStack)entry.getKey()), entry.getValue()));
        }
        CommandIc2.msg(sender, "(check console for full list)");
    }

    private void cmdDebugResolveIngredient(ICommandSender sender, String arg) {
        try {
            IRecipeInput input = ConfigUtil.asRecipeInput(arg);
            if (input == null) {
                CommandIc2.msg(sender, "No match");
            } else {
                List<ItemStack> inputs = input.getInputs();
                CommandIc2.msg(sender, inputs.size() + " matches:");
                for (ItemStack stack : inputs) {
                    if (stack == null) {
                        CommandIc2.msg(sender, " null");
                        continue;
                    }
                    CommandIc2.msg(sender, String.format(" %s (%s, od: %s, name: %s / %s)", StackUtil.toStringSafe(stack), Util.getName(stack.func_77973_b()), this.getOreDictNames(stack), stack.func_77977_a(), stack.func_82833_r()));
                }
            }
        }
        catch (Exception e) {
            CommandIc2.msg(sender, "Error: " + e);
        }
    }

    private String getOreDictNames(ItemStack stack) {
        String ret = "";
        for (int oreId : OreDictionary.getOreIDs((ItemStack)stack)) {
            if (!ret.isEmpty()) {
                ret = ret + ", ";
            }
            ret = ret + OreDictionary.getOreName((int)oreId);
        }
        return ret.isEmpty() ? "<none>" : ret;
    }

    private void cmdDebugDumpTextures(ICommandSender sender, String name, String size) {
        if (FMLCommonHandler.instance().getSide().isServer()) {
            CommandIc2.msg(sender, "Can't dump textures on the dedicated server.");
            return;
        }
        CommandIc2.msg(sender, "Dumping requested textures to sprites texture...");
        Integer meta = null;
        int pos = name.indexOf(64);
        if (pos != -1) {
            meta = Integer.valueOf(name.substring(pos + 1));
            name = name.substring(0, pos);
        }
        String regex = '^' + Pattern.quote(name).replace("*", "\\E.*\\Q") + '$';
        Pattern pattern = Pattern.compile(regex);
        IC2.tickHandler.requestSingleWorldTick(IC2.platform.getPlayerWorld(), new TextureDumper(pattern, Integer.valueOf(size), meta));
    }

    private void dumpLargeGrids(ICommandSender sender) {
        ArrayList<GridInfo> allGrids = new ArrayList<GridInfo>();
        for (WorldServer world : DimensionManager.getWorlds()) {
            EnergyNetLocal energyNet = EnergyNetGlobal.getLocal((World)world);
            allGrids.addAll(energyNet.getGridInfos());
        }
        Collections.sort(allGrids, new Comparator<GridInfo>(){

            @Override
            public int compare(GridInfo a, GridInfo b) {
                return b.complexNodeCount - a.complexNodeCount;
            }
        });
        CommandIc2.msg(sender, "found " + allGrids.size() + " grids overall");
        for (int i = 0; i < 8 && i < allGrids.size(); ++i) {
            GridInfo grid = (GridInfo)allGrids.get(i);
            if (grid.nodeCount == 0) {
                CommandIc2.msg(sender, "grid " + grid.id + " is empty");
                continue;
            }
            CommandIc2.msg(sender, String.format("%d complex / %d total nodes in grid %d (%d/%d/%d - %d/%d/%d)", grid.complexNodeCount, grid.nodeCount, grid.id, grid.minX, grid.minY, grid.minZ, grid.maxX, grid.maxY, grid.maxZ));
        }
    }

    private void cmdDebugEnet(ICommandSender sender, String option, boolean value) throws CommandException {
        if ("logIssues".equals(option)) {
            CommandIc2.msg(sender, "setting logGridUpdateIssues to " + value);
            EnergyNetSettings.logGridUpdateIssues = value;
        } else if ("logUpdates".equals(option)) {
            CommandIc2.msg(sender, "setting logGridUpdatesVerbose to " + value);
            EnergyNetSettings.logGridUpdatesVerbose = value;
        } else {
            throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
        }
    }

    public static void msg(ICommandSender sender, String text) {
        sender.func_145747_a((ITextComponent)new TextComponentString(text));
    }

    static void cmdCurrentItem(ICommandSender sender) {
        if (!(sender.func_174793_f() instanceof EntityPlayer)) {
            CommandIc2.msg(sender, "Not applicable for non-player");
        }
        EntityPlayer player = (EntityPlayer)sender.func_174793_f();
        ItemStack stack = player.field_71071_by.func_70448_g();
        if (StackUtil.isEmpty(stack)) {
            CommandIc2.msg(sender, "empty: " + StackUtil.toStringSafe(stack));
        } else {
            CommandIc2.msg(sender, String.format("ID: %s, Raw Meta: %d, Meta: %d, Damage: %d, NBT: %s", stack.func_77973_b().getRegistryName(), StackUtil.getRawMeta(stack), stack.func_77960_j(), stack.func_77952_i(), stack.func_77978_p()));
            CommandIc2.msg(sender, "Current Item excluding amount: " + ConfigUtil.fromStack(stack));
            CommandIc2.msg(sender, "Current Item including amount: " + ConfigUtil.fromStackWithAmount(stack));
        }
    }

    private void cmdGiveCrop(ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = (EntityPlayer)sender;
        if (!StackUtil.isEmpty(player.field_71071_by.func_70448_g())) {
            CommandIc2.msg(sender, "The currently selected slot needs to be empty.");
        } else {
            CropCard crop = Crops.instance.getCropCard(args[1], args[2]);
            if (crop == null) {
                CommandIc2.msg(sender, "The crop you specified does not exist.");
            } else {
                int resistance;
                int gain;
                int growth;
                try {
                    growth = Integer.parseInt(args[3]);
                    gain = Integer.parseInt(args[4]);
                    resistance = Integer.parseInt(args[5]);
                }
                catch (NumberFormatException exception) {
                    throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
                }
                if (growth < 1 || growth > 31 || gain < 1 || gain > 31 || resistance < 1 || resistance > 31) {
                    throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
                }
                player.field_71071_by.func_70441_a(ItemCropSeed.generateItemStackFromValues(crop, growth, gain, resistance, 4));
            }
        }
    }

    public static class TextureDumper
    implements IWorldTickCallback {
        private final Pattern pattern;
        private final int size;
        private final Integer meta;

        TextureDumper(Pattern pattern, int size, Integer meta) {
            this.pattern = pattern;
            this.size = size;
            this.meta = meta;
        }

        @Override
        public void onTick(World world) {
            if (this.size > 0) {
                MinecraftForge.EVENT_BUS.register((Object)this);
            }
        }

        @SubscribeEvent
        @SideOnly(value=Side.CLIENT)
        public void onRenderWorldLast(RenderWorldLastEvent event) {
            IC2.log.info(LogCategory.General, "Starting texture dump.");
            int count = 0;
            GlStateManager.func_179094_E();
            GlStateManager.func_179123_a();
            for (Item item : ForgeRegistries.ITEMS) {
                block12: {
                    String regName = Util.getName(item).toString();
                    if (this.pattern.matcher(regName).matches()) {
                        if (this.meta == null) {
                            if (item instanceof IMultiItem) {
                                for (ItemStack stack : ((IMultiItem)item).getAllStacks()) {
                                    assert (stack != null) : item + " produced a null stack in getAllStacks()";
                                    this.dump(stack, regName);
                                    ++count;
                                }
                            } else {
                                HashSet<String> processedNames = new HashSet<String>();
                                for (int i = 0; i < Short.MAX_VALUE; ++i) {
                                    ItemStack stack;
                                    block13: {
                                        stack = new ItemStack(item, 1, i);
                                        try {
                                            String name = stack.func_77977_a();
                                            if (name == null) break block12;
                                            if (!processedNames.add(name)) {
                                            }
                                            break block13;
                                        }
                                        catch (Exception e) {
                                            IC2.log.info(LogCategory.General, e, "Exception for %s.", stack);
                                        }
                                        break;
                                    }
                                    this.dump(stack, regName);
                                    ++count;
                                }
                            }
                        } else {
                            this.dump(new ItemStack(item, 1, this.meta.intValue()), regName);
                            ++count;
                        }
                    }
                }
                if (!Keyboard.isKeyDown((int)1)) continue;
                break;
            }
            GlStateManager.func_179099_b();
            GlStateManager.func_179121_F();
            IC2.log.info(LogCategory.General, "Dumped %d sprites.", count);
            MinecraftForge.EVENT_BUS.unregister((Object)this);
        }

        @SideOnly(value=Side.CLIENT)
        private void dump(ItemStack stack, String name) {
            Minecraft mc = Minecraft.func_71410_x();
            GL11.glClear((int)16640);
            GL11.glMatrixMode((int)5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho((double)0.0, (double)((double)mc.field_71443_c * 16.0 / (double)this.size), (double)((double)mc.field_71440_d * 16.0 / (double)this.size), (double)0.0, (double)1000.0, (double)3000.0);
            GL11.glMatrixMode((int)5888);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
            RenderHelper.func_74520_c();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)32826);
            mc.func_175599_af().func_175042_a(stack, 0, 0);
            BufferedImage img = new BufferedImage(this.size, this.size, 2);
            if (OpenGlHelper.func_148822_b()) {
                Framebuffer fb = mc.func_147110_a();
                int width = fb.field_147622_a;
                int height = fb.field_147620_b;
                IntBuffer buffer = BufferUtils.createIntBuffer((int)(width * height));
                int[] data = new int[width * height];
                GlStateManager.func_187425_g((int)3333, (int)1);
                GlStateManager.func_187425_g((int)3317, (int)1);
                GlStateManager.func_179144_i((int)fb.field_147617_g);
                GlStateManager.func_187433_a((int)3553, (int)0, (int)32993, (int)33639, (IntBuffer)buffer);
                buffer.get(data);
                int[] mirroredData = new int[data.length];
                for (int y = 0; y < height; ++y) {
                    System.arraycopy(data, y * width, mirroredData, (height - y - 1) * width, width);
                }
                img.setRGB(0, 0, this.size, this.size, mirroredData, 0, width);
            } else {
                IntBuffer buffer = BufferUtils.createIntBuffer((int)(this.size * this.size));
                int[] data = new int[this.size * this.size];
                GlStateManager.func_187425_g((int)3333, (int)1);
                GlStateManager.func_187425_g((int)3317, (int)1);
                GlStateManager.func_187413_a((int)0, (int)0, (int)this.size, (int)this.size, (int)32993, (int)33639, (IntBuffer)buffer);
                buffer.get(data);
                TextureUtil.func_147953_a((int[])data, (int)this.size, (int)this.size);
                img.setRGB(0, 0, this.size, this.size, data, 0, this.size);
            }
            try {
                File dir = new File(IC2.platform.getMinecraftDir(), "sprites");
                dir.mkdir();
                String modId = name.indexOf(58) >= 0 ? name.substring(0, name.indexOf(58)) : name;
                String fileName = "Sprite_" + modId + '_' + stack.func_82833_r() + '_' + this.size;
                fileName = fileName.replaceAll("[^\\w\\- ]+", "");
                File file = new File(dir, fileName + ".png");
                int extra = 0;
                while (file.exists()) {
                    file = new File(dir, fileName + '_' + extra++ + ".png");
                }
                ImageIO.write((RenderedImage)img, "png", file);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            GL11.glPopMatrix();
            GL11.glMatrixMode((int)5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode((int)5888);
        }
    }
}

