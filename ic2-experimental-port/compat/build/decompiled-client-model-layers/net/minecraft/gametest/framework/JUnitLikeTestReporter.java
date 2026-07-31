/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Stopwatch
 */
package net.minecraft.gametest.framework;

import com.google.common.base.Stopwatch;
import java.io.File;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.TestReporter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class JUnitLikeTestReporter
implements TestReporter {
    private final Document f_177659_;
    private final Element f_177660_;
    private final Stopwatch f_177661_;
    private final File f_177662_;

    public JUnitLikeTestReporter(File p_177664_) throws ParserConfigurationException {
        this.f_177662_ = p_177664_;
        this.f_177659_ = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        this.f_177660_ = this.f_177659_.createElement("testsuite");
        Element $$1 = this.f_177659_.createElement("testsuite");
        $$1.appendChild(this.f_177660_);
        this.f_177659_.appendChild($$1);
        this.f_177660_.setAttribute("timestamp", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        this.f_177661_ = Stopwatch.createStarted();
    }

    private Element m_177670_(GameTestInfo p_177671_, String p_177672_) {
        Element $$2 = this.f_177659_.createElement("testcase");
        $$2.setAttribute("name", p_177672_);
        $$2.setAttribute("classname", p_177671_.m_127645_());
        $$2.setAttribute("time", String.valueOf((double)p_177671_.m_177485_() / 1000.0));
        this.f_177660_.appendChild($$2);
        return $$2;
    }

    @Override
    public void m_8014_(GameTestInfo p_177669_) {
        Element $$4;
        String $$1 = p_177669_.m_127633_();
        String $$2 = p_177669_.m_127642_().getMessage();
        if (p_177669_.m_127643_()) {
            Element $$3 = this.f_177659_.createElement("failure");
            $$3.setAttribute("message", $$2);
        } else {
            $$4 = this.f_177659_.createElement("skipped");
            $$4.setAttribute("message", $$2);
        }
        Element $$5 = this.m_177670_(p_177669_, $$1);
        $$5.appendChild($$4);
    }

    @Override
    public void m_142335_(GameTestInfo p_177674_) {
        String $$1 = p_177674_.m_127633_();
        this.m_177670_(p_177674_, $$1);
    }

    @Override
    public void m_142411_() {
        this.f_177661_.stop();
        this.f_177660_.setAttribute("time", String.valueOf((double)this.f_177661_.elapsed(TimeUnit.MILLISECONDS) / 1000.0));
        try {
            this.m_177666_(this.f_177662_);
        }
        catch (TransformerException $$0) {
            throw new Error("Couldn't save test report", $$0);
        }
    }

    public void m_177666_(File p_177667_) throws TransformerException {
        TransformerFactory $$1 = TransformerFactory.newInstance();
        Transformer $$2 = $$1.newTransformer();
        DOMSource $$3 = new DOMSource(this.f_177659_);
        StreamResult $$4 = new StreamResult(p_177667_);
        $$2.transform($$3, $$4);
    }
}

