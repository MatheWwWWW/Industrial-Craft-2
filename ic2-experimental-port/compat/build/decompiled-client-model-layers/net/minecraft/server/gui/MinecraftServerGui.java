/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogQueues
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.gui;

import com.google.common.collect.Lists;
import com.mojang.logging.LogQueues;
import com.mojang.logging.LogUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.gui.PlayerListComponent;
import net.minecraft.server.gui.StatsComponent;
import org.slf4j.Logger;

public class MinecraftServerGui
extends JComponent {
    private static final Font f_139899_ = new Font("Monospaced", 0, 12);
    private static final Logger f_139900_ = LogUtils.getLogger();
    private static final String f_142884_ = "Minecraft server";
    private static final String f_142885_ = "Minecraft server - shutting down!";
    private final DedicatedServer f_139901_;
    private Thread f_139902_;
    private final Collection<Runnable> f_139903_ = Lists.newArrayList();
    final AtomicBoolean f_139904_ = new AtomicBoolean();

    public static MinecraftServerGui m_139921_(final DedicatedServer p_139922_) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception exception) {
            // empty catch block
        }
        final JFrame $$1 = new JFrame(f_142884_);
        final MinecraftServerGui $$2 = new MinecraftServerGui(p_139922_);
        $$1.setDefaultCloseOperation(2);
        $$1.add($$2);
        $$1.pack();
        $$1.setLocationRelativeTo(null);
        $$1.setVisible(true);
        $$1.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent p_139944_) {
                if (!$$2.f_139904_.getAndSet(true)) {
                    $$1.setTitle(MinecraftServerGui.f_142885_);
                    p_139922_.m_7570_(true);
                    $$2.m_139935_();
                }
            }
        });
        $$2.m_139909_($$1::dispose);
        $$2.m_139908_();
        return $$2;
    }

    private MinecraftServerGui(DedicatedServer p_139907_) {
        this.f_139901_ = p_139907_;
        this.setPreferredSize(new Dimension(854, 480));
        this.setLayout(new BorderLayout());
        try {
            this.add((Component)this.m_139934_(), "Center");
            this.add((Component)this.m_139932_(), "West");
        }
        catch (Exception $$1) {
            f_139900_.error("Couldn't build server GUI", (Throwable)$$1);
        }
    }

    public void m_139909_(Runnable p_139910_) {
        this.f_139903_.add(p_139910_);
    }

    private JComponent m_139932_() {
        JPanel $$0 = new JPanel(new BorderLayout());
        StatsComponent $$1 = new StatsComponent(this.f_139901_);
        this.f_139903_.add($$1::m_139964_);
        $$0.add((Component)$$1, "North");
        $$0.add((Component)this.m_139933_(), "Center");
        $$0.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
        return $$0;
    }

    private JComponent m_139933_() {
        PlayerListComponent $$0 = new PlayerListComponent(this.f_139901_);
        JScrollPane $$1 = new JScrollPane($$0, 22, 30);
        $$1.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
        return $$1;
    }

    private JComponent m_139934_() {
        JPanel $$0 = new JPanel(new BorderLayout());
        JTextArea $$1 = new JTextArea();
        JScrollPane $$2 = new JScrollPane($$1, 22, 30);
        $$1.setEditable(false);
        $$1.setFont(f_139899_);
        JTextField $$3 = new JTextField();
        $$3.addActionListener(p_139920_ -> {
            String $$2 = $$3.getText().trim();
            if (!$$2.isEmpty()) {
                this.f_139901_.m_139645_($$2, this.f_139901_.m_129893_());
            }
            $$3.setText("");
        });
        $$1.addFocusListener(new FocusAdapter(){

            @Override
            public void focusGained(FocusEvent p_139949_) {
            }
        });
        $$0.add((Component)$$2, "Center");
        $$0.add((Component)$$3, "South");
        $$0.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
        this.f_139902_ = new Thread(() -> {
            String $$2;
            while (($$2 = LogQueues.getNextLogEvent((String)"ServerGuiConsole")) != null) {
                this.m_139914_($$1, $$2, $$2);
            }
        });
        this.f_139902_.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_139900_));
        this.f_139902_.setDaemon(true);
        return $$0;
    }

    public void m_139908_() {
        this.f_139902_.start();
    }

    public void m_139925_() {
        if (!this.f_139904_.getAndSet(true)) {
            this.m_139935_();
        }
    }

    void m_139935_() {
        this.f_139903_.forEach(Runnable::run);
    }

    public void m_139914_(JTextArea p_139915_, JScrollPane p_139916_, String p_139917_) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> this.m_139914_(p_139915_, p_139916_, p_139917_));
            return;
        }
        Document $$3 = p_139915_.getDocument();
        JScrollBar $$4 = p_139916_.getVerticalScrollBar();
        boolean $$5 = false;
        if (p_139916_.getViewport().getView() == p_139915_) {
            $$5 = (double)$$4.getValue() + $$4.getSize().getHeight() + (double)(f_139899_.getSize() * 4) > (double)$$4.getMaximum();
        }
        try {
            $$3.insertString($$3.getLength(), p_139917_, null);
        }
        catch (BadLocationException badLocationException) {
            // empty catch block
        }
        if ($$5) {
            $$4.setValue(Integer.MAX_VALUE);
        }
    }
}

