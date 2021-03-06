package com.baeldung.junit.log;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

/**
 * In memory slf4j appender<br/>
 * Convenient appender to be able to check slf4j invocations
 */
public class MemoryAppender extends ListAppender<ILoggingEvent> {

    public void reset() {
        this.list.clear();
    }

    public void checkContains(String string, Level level) {
        Assert.assertTrue("Message expected to contain " + string + " with severity " + level, 
                this.list.stream()
                         .anyMatch(event -> event.getMessage().toString().contains(string) 
                                            && event.getLevel().equals(level)));
    }

    public int countEventsForLogger(String loggerName) {
        return (int) this.list.stream()
                .filter(event -> event.getLoggerName().contains(loggerName))
                .count();
    }

    public List<ILoggingEvent> search(String string) {
        return this.list.stream()
                .filter(event -> event.getMessage().toString().contains(string))
                .collect(Collectors.toList());
    }

    public List<ILoggingEvent> search(String string, Level level) {
        return this.list.stream()
                .filter(event -> event.getMessage().toString().contains(string) 
                                 && event.getLevel().equals(level))
                .collect(Collectors.toList());
    }

    public int getSize() {
        return this.list.size();
    }

    public List<ILoggingEvent> getLoggedEvents() {
        return Collections.unmodifiableList(this.list);
    }

    @Override
    public String toString() {
        final StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(super.toString());
        stringBuffer.append(" ");
        stringBuffer.append(this.list.toString());

        return stringBuffer.toString();
    }
}
