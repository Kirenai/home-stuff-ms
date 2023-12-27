package me.kirenai.re.nourishment.util;

import me.kirenai.re.nourishment.util.helper.ObjectMapperHelper;
import me.kirenai.re.nourishment.entity.NourishmentType;

import java.io.IOException;

public class NourishmentTypeMocks {

    private static final NourishmentTypeMocks INSTANCE = new NourishmentTypeMocks();

    private final ObjectMapperHelper mapper = new ObjectMapperHelper();

    public static NourishmentTypeMocks getInstance() {
        return INSTANCE;
    }

    public NourishmentType getNourishmentType() throws IOException {
        return this.mapper.readValue(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("mock/nourishment/NourishmentType.json"),
                NourishmentType.class
        );
    }

}
