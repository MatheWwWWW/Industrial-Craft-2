/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 *  oshi.SystemInfo
 *  oshi.hardware.CentralProcessor
 *  oshi.hardware.CentralProcessor$ProcessorIdentifier
 *  oshi.hardware.GlobalMemory
 *  oshi.hardware.GraphicsCard
 *  oshi.hardware.HardwareAbstractionLayer
 *  oshi.hardware.PhysicalMemory
 *  oshi.hardware.VirtualMemory
 */
package net.minecraft;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import org.slf4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.VirtualMemory;

public class SystemReport {
    public static final long f_143506_ = 0x100000L;
    private static final long f_143507_ = 1000000000L;
    private static final Logger f_143508_ = LogUtils.getLogger();
    private static final String f_143509_ = System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
    private static final String f_143510_ = System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
    private static final String f_143511_ = System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
    private final Map<String, String> f_143512_ = Maps.newLinkedHashMap();

    public SystemReport() {
        this.m_143519_("Minecraft Version", SharedConstants.m_183709_().getName());
        this.m_143519_("Minecraft Version ID", SharedConstants.m_183709_().getId());
        this.m_143519_("Operating System", f_143509_);
        this.m_143519_("Java Version", f_143510_);
        this.m_143519_("Java VM Version", f_143511_);
        this.m_143522_("Memory", () -> {
            Runtime $$0 = Runtime.getRuntime();
            long $$1 = $$0.maxMemory();
            long $$2 = $$0.totalMemory();
            long $$3 = $$0.freeMemory();
            long $$4 = $$1 / 0x100000L;
            long $$5 = $$2 / 0x100000L;
            long $$6 = $$3 / 0x100000L;
            return $$3 + " bytes (" + $$6 + " MiB) / " + $$2 + " bytes (" + $$5 + " MiB) up to " + $$1 + " bytes (" + $$4 + " MiB)";
        });
        this.m_143522_("CPUs", () -> String.valueOf(Runtime.getRuntime().availableProcessors()));
        this.m_143516_("hardware", () -> this.m_143535_(new SystemInfo()));
        this.m_143522_("JVM Flags", () -> {
            List $$0 = Util.m_137582_().collect(Collectors.toList());
            return String.format(Locale.ROOT, "%d total; %s", $$0.size(), String.join((CharSequence)" ", $$0));
        });
    }

    public void m_143519_(String p_143520_, String p_143521_) {
        this.f_143512_.put(p_143520_, p_143521_);
    }

    public void m_143522_(String p_143523_, Supplier<String> p_143524_) {
        try {
            this.m_143519_(p_143523_, p_143524_.get());
        }
        catch (Exception $$2) {
            f_143508_.warn("Failed to get system info for {}", (Object)p_143523_, (Object)$$2);
            this.m_143519_(p_143523_, "ERR");
        }
    }

    private void m_143535_(SystemInfo p_143536_) {
        HardwareAbstractionLayer $$1 = p_143536_.getHardware();
        this.m_143516_("processor", () -> this.m_143539_($$1.getProcessor()));
        this.m_143516_("graphics", () -> this.m_143552_($$1.getGraphicsCards()));
        this.m_143516_("memory", () -> this.m_143541_($$1.getMemory()));
    }

    private void m_143516_(String p_143517_, Runnable p_143518_) {
        try {
            p_143518_.run();
        }
        catch (Throwable $$2) {
            f_143508_.warn("Failed retrieving info for group {}", (Object)p_143517_, (Object)$$2);
        }
    }

    private void m_143531_(List<PhysicalMemory> p_143532_) {
        int $$1 = 0;
        for (PhysicalMemory $$2 : p_143532_) {
            String $$3 = String.format(Locale.ROOT, "Memory slot #%d ", $$1++);
            this.m_143522_($$3 + "capacity (MB)", () -> String.format(Locale.ROOT, "%.2f", Float.valueOf((float)$$2.getCapacity() / 1048576.0f)));
            this.m_143522_($$3 + "clockSpeed (GHz)", () -> String.format(Locale.ROOT, "%.2f", Float.valueOf((float)$$2.getClockSpeed() / 1.0E9f)));
            this.m_143522_($$3 + "type", () -> ((PhysicalMemory)$$2).getMemoryType());
        }
    }

    private void m_143549_(VirtualMemory p_143550_) {
        this.m_143522_("Virtual memory max (MB)", () -> String.format(Locale.ROOT, "%.2f", Float.valueOf((float)p_143550_.getVirtualMax() / 1048576.0f)));
        this.m_143522_("Virtual memory used (MB)", () -> String.format(Locale.ROOT, "%.2f", Float.valueOf((float)p_143550_.getVirtualInUse() / 1048576.0f)));
        this.m_143522_("Swap memory total (MB)", () -> String.format(Locale.ROOT, "%.2f", Float.valueOf((float)p_143550_.getSwapTotal() / 1048576.0f)));
        this.m_143522_("Swap memory used (MB)", () -> String.format(Locale.ROOT, "%.2f", Float.valueOf((float)p_143550_.getSwapUsed() / 1048576.0f)));
    }

    private void m_143541_(GlobalMemory p_143542_) {
        this.m_143516_("physical memory", () -> this.m_143531_(p_143542_.getPhysicalMemory()));
        this.m_143516_("virtual memory", () -> this.m_143549_(p_143542_.getVirtualMemory()));
    }

    private void m_143552_(List<GraphicsCard> p_143553_) {
        int $$1 = 0;
        for (GraphicsCard $$2 : p_143553_) {
            String $$3 = String.format(Locale.ROOT, "Graphics card #%d ", $$1++);
            this.m_143522_($$3 + "name", () -> ((GraphicsCard)$$2).getName());
            this.m_143522_($$3 + "vendor", () -> ((GraphicsCard)$$2).getVendor());
            this.m_143522_($$3 + "VRAM (MB)", () -> String.format(Locale.ROOT, "%.2f", Float.valueOf((float)$$2.getVRam() / 1048576.0f)));
            this.m_143522_($$3 + "deviceId", () -> ((GraphicsCard)$$2).getDeviceId());
            this.m_143522_($$3 + "versionInfo", () -> ((GraphicsCard)$$2).getVersionInfo());
        }
    }

    private void m_143539_(CentralProcessor p_143540_) {
        CentralProcessor.ProcessorIdentifier $$1 = p_143540_.getProcessorIdentifier();
        this.m_143522_("Processor Vendor", () -> ((CentralProcessor.ProcessorIdentifier)$$1).getVendor());
        this.m_143522_("Processor Name", () -> ((CentralProcessor.ProcessorIdentifier)$$1).getName());
        this.m_143522_("Identifier", () -> ((CentralProcessor.ProcessorIdentifier)$$1).getIdentifier());
        this.m_143522_("Microarchitecture", () -> ((CentralProcessor.ProcessorIdentifier)$$1).getMicroarchitecture());
        this.m_143522_("Frequency (GHz)", () -> String.format(Locale.ROOT, "%.2f", Float.valueOf((float)$$1.getVendorFreq() / 1.0E9f)));
        this.m_143522_("Number of physical packages", () -> String.valueOf(p_143540_.getPhysicalPackageCount()));
        this.m_143522_("Number of physical CPUs", () -> String.valueOf(p_143540_.getPhysicalProcessorCount()));
        this.m_143522_("Number of logical CPUs", () -> String.valueOf(p_143540_.getLogicalProcessorCount()));
    }

    public void m_143525_(StringBuilder p_143526_) {
        p_143526_.append("-- ").append("System Details").append(" --\n");
        p_143526_.append("Details:");
        this.f_143512_.forEach((p_143529_, p_143530_) -> {
            p_143526_.append("\n\t");
            p_143526_.append((String)p_143529_);
            p_143526_.append(": ");
            p_143526_.append((String)p_143530_);
        });
    }

    public String m_143515_() {
        return this.f_143512_.entrySet().stream().map(p_143534_ -> (String)p_143534_.getKey() + ": " + (String)p_143534_.getValue()).collect(Collectors.joining(System.lineSeparator()));
    }
}

