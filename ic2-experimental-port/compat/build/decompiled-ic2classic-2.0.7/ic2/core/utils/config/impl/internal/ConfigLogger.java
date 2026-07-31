/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 */
package ic2.core.utils.config.impl.internal;

import ic2.core.utils.config.api.ILogger;
import java.util.Objects;
import org.apache.logging.log4j.Logger;

public class ConfigLogger
implements ILogger {
    Logger logger;

    public ConfigLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void debug(String s) {
        this.logger.debug(s);
    }

    @Override
    public void debug(String s, Object o) {
        this.logger.debug(s, o);
    }

    @Override
    public void debug(Object o) {
        this.logger.debug(Objects.toString(o));
    }

    @Override
    public void info(String s) {
        this.logger.info(s);
    }

    @Override
    public void info(String s, Object o) {
        this.logger.info(s, o);
    }

    @Override
    public void info(Object o) {
        this.logger.info(Objects.toString(o));
    }

    @Override
    public void warn(String s) {
        this.logger.warn(s);
    }

    @Override
    public void warn(String s, Object o) {
        this.logger.warn(s, o);
    }

    @Override
    public void warn(Object o) {
        this.logger.warn(Objects.toString(o));
    }

    @Override
    public void error(String s) {
        this.logger.error(s);
    }

    @Override
    public void error(String s, Object o) {
        this.logger.error(s, o);
    }

    @Override
    public void error(Object o) {
        this.logger.error(Objects.toString(o));
    }
}

