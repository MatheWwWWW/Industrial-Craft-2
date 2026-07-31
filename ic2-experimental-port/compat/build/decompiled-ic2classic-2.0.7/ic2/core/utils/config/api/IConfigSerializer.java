/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.api;

import ic2.core.utils.config.api.buffer.IReadBuffer;
import ic2.core.utils.config.api.buffer.IWriteBuffer;
import ic2.core.utils.config.utils.IEntryDataType;
import ic2.core.utils.config.utils.ParseResult;
import java.util.function.BiConsumer;
import java.util.function.Function;

public interface IConfigSerializer<T> {
    public T getExample();

    public IEntryDataType.CompoundDataType getFormat();

    public ParseResult<Boolean> isValid(T var1);

    public ParseResult<T> deserialize(String[] var1);

    public String[] serialize(T var1);

    public T deserialize(IReadBuffer var1);

    public void serialize(IWriteBuffer var1, T var2);

    public static <T> IConfigSerializer<T> noSync(IEntryDataType.CompoundDataType format, T example, Function<String[], ParseResult<T>> reader, Function<T, String[]> writer) {
        return new FunctionWriter<T>(format, example, reader, writer, null, null, null);
    }

    public static <T> IConfigSerializer<T> noSync(IEntryDataType.CompoundDataType format, T example, Function<String[], ParseResult<T>> reader, Function<T, String[]> writer, Function<T, ParseResult<Boolean>> filter) {
        return new FunctionWriter<T>(format, example, reader, writer, filter, null, null);
    }

    public static <T> IConfigSerializer<T> withSync(IEntryDataType.CompoundDataType format, T example, Function<String[], ParseResult<T>> reader, Function<T, String[]> writer, Function<IReadBuffer, T> readBuffer, BiConsumer<IWriteBuffer, T> writeBuffer) {
        return new FunctionWriter<T>(format, example, reader, writer, null, readBuffer, writeBuffer);
    }

    public static <T> IConfigSerializer<T> withSync(IEntryDataType.CompoundDataType format, T example, Function<String[], ParseResult<T>> reader, Function<T, String[]> writer, Function<T, ParseResult<Boolean>> filter, Function<IReadBuffer, T> readBuffer, BiConsumer<IWriteBuffer, T> writeBuffer) {
        return new FunctionWriter<T>(format, example, reader, writer, filter, readBuffer, writeBuffer);
    }

    public static class FunctionWriter<T>
    implements IConfigSerializer<T> {
        IEntryDataType.CompoundDataType format;
        T example;
        Function<String[], ParseResult<T>> reader;
        Function<T, String[]> writer;
        Function<T, ParseResult<Boolean>> filter;
        Function<IReadBuffer, T> readBuffer;
        BiConsumer<IWriteBuffer, T> writeBuffer;

        public FunctionWriter(IEntryDataType.CompoundDataType format, T example, Function<String[], ParseResult<T>> reader, Function<T, String[]> writer, Function<T, ParseResult<Boolean>> filter, Function<IReadBuffer, T> readBuffer, BiConsumer<IWriteBuffer, T> writeBuffer) {
            this.format = format;
            this.example = example;
            this.reader = reader;
            this.writer = writer;
            this.filter = filter;
            this.readBuffer = readBuffer;
            this.writeBuffer = writeBuffer;
        }

        @Override
        public T getExample() {
            return this.example;
        }

        @Override
        public IEntryDataType.CompoundDataType getFormat() {
            return this.format;
        }

        @Override
        public ParseResult<Boolean> isValid(T value) {
            return this.filter == null ? ParseResult.success(true) : this.filter.apply(value);
        }

        @Override
        public ParseResult<T> deserialize(String[] value) {
            return this.reader.apply(value);
        }

        @Override
        public String[] serialize(T value) {
            return this.writer.apply(value);
        }

        @Override
        public T deserialize(IReadBuffer buffer) {
            if (this.readBuffer == null || this.writeBuffer == null) {
                throw new UnsupportedOperationException("No Read/Write Buffer Provided");
            }
            return this.readBuffer.apply(buffer);
        }

        @Override
        public void serialize(IWriteBuffer buffer, T value) {
            if (this.readBuffer == null || this.writeBuffer == null) {
                throw new UnsupportedOperationException("No Read/Write Buffer Provided");
            }
            this.writeBuffer.accept(buffer, (IWriteBuffer)value);
        }
    }
}

