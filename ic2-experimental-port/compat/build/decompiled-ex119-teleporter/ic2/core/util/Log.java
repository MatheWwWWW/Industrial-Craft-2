/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package ic2.core.util;

import ic2.core.util.LogCategory;
import ic2.core.util.LogOutputStream;
import ic2.core.util.Util;
import java.io.PrintStream;
import java.util.EnumMap;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
    private static final boolean debug = Util.hasAssertions();
    private final Logger parent;
    private final Map<LogCategory, Logger> loggers = new EnumMap<LogCategory, Logger>(LogCategory.class);

    public Log(Logger logger) {
        this.parent = logger;
    }

    public void error(LogCategory logCategory, String string, Object ... objectArray) {
        this.log(logCategory, Level.FATAL, string, objectArray);
    }

    public void error(LogCategory logCategory, Throwable throwable, String string, Object ... objectArray) {
        this.log(logCategory, Level.FATAL, throwable, string, objectArray);
    }

    public void error(LogCategory logCategory, String string) {
        this.log(logCategory, Level.FATAL, string);
    }

    public void error(LogCategory logCategory, Throwable throwable, String string) {
        this.log(logCategory, Level.FATAL, throwable, string);
    }

    public void warn(LogCategory logCategory, String string, Object ... objectArray) {
        this.log(logCategory, Level.WARN, string, objectArray);
    }

    public void warn(LogCategory logCategory, Throwable throwable, String string, Object ... objectArray) {
        this.log(logCategory, Level.WARN, throwable, string, objectArray);
    }

    public void warn(LogCategory logCategory, String string) {
        this.log(logCategory, Level.WARN, string);
    }

    public void warn(LogCategory logCategory, Throwable throwable, String string) {
        this.log(logCategory, Level.WARN, throwable, string);
    }

    public void info(LogCategory logCategory, String string, Object ... objectArray) {
        this.log(logCategory, Level.INFO, string, objectArray);
    }

    public void info(LogCategory logCategory, Throwable throwable, String string, Object ... objectArray) {
        this.log(logCategory, Level.INFO, throwable, string, objectArray);
    }

    public void info(LogCategory logCategory, String string) {
        this.log(logCategory, Level.INFO, string);
    }

    public void info(LogCategory logCategory, Throwable throwable, String string) {
        this.log(logCategory, Level.INFO, throwable, string);
    }

    public void debug(LogCategory logCategory, String string, Object ... objectArray) {
        this.log(logCategory, Level.DEBUG, string, objectArray);
    }

    public void debug(LogCategory logCategory, Throwable throwable, String string, Object ... objectArray) {
        this.log(logCategory, Level.DEBUG, throwable, string, objectArray);
    }

    public void debug(LogCategory logCategory, String string) {
        this.log(logCategory, Level.DEBUG, string);
    }

    public void debug(LogCategory logCategory, Throwable throwable, String string) {
        this.log(logCategory, Level.DEBUG, throwable, string);
    }

    public void trace(LogCategory logCategory, String string, Object ... objectArray) {
        this.log(logCategory, Level.TRACE, string, objectArray);
    }

    public void trace(LogCategory logCategory, Throwable throwable, String string, Object ... objectArray) {
        this.log(logCategory, Level.TRACE, throwable, string, objectArray);
    }

    public void trace(LogCategory logCategory, String string) {
        this.log(logCategory, Level.TRACE, string);
    }

    public void trace(LogCategory logCategory, Throwable throwable, String string) {
        this.log(logCategory, Level.TRACE, throwable, string);
    }

    public void log(LogCategory logCategory, Level level, String string, Object ... objectArray) {
        if (objectArray.length > 0) {
            if (debug) {
                assert (!string.contains("{}"));
                for (Object object : objectArray) {
                    assert (!(object instanceof Throwable));
                }
            }
            string = String.format(string, objectArray);
        }
        this.log(logCategory, level, string);
    }

    public void log(LogCategory logCategory, Level level, Throwable throwable, String object, Object ... objectArray) {
        if (objectArray.length > 0) {
            if (debug) {
                assert (!((String)object).contains("{}"));
                for (Object object2 : objectArray) {
                    assert (!(object2 instanceof Throwable));
                }
            }
            try {
                object = String.format((String)object, objectArray);
            }
            catch (Throwable throwable2) {
                this.log(LogCategory.General, Level.WARN, throwable2, "Log string format failed.");
                for (Object object3 : objectArray) {
                    object = (String)object + " " + object3;
                }
            }
        }
        this.log(logCategory, level, throwable, (String)object);
    }

    public void log(LogCategory logCategory, Level level, String string) {
        this.getLogger(logCategory).log(level, string);
    }

    public void log(LogCategory logCategory, Level level, Throwable throwable, String string) {
        this.getLogger(logCategory).log(level, string, throwable);
    }

    public PrintStream getPrintStream(LogCategory logCategory, Level level) {
        return new PrintStream(new LogOutputStream(this, logCategory, level), true);
    }

    private Logger getLogger(LogCategory logCategory) {
        Logger logger = this.loggers.get((Object)logCategory);
        if (logger == null) {
            logger = LogManager.getLogger((String)(this.parent.getName() + "." + logCategory.name()));
            this.loggers.put(logCategory, logger);
        }
        return logger;
    }
}

