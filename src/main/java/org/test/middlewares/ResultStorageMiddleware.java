package org.test.middlewares;

import org.test.factories.StorageFactory;
import org.test.tools.Type;
import org.test.interfaces.StorageFactoryInterface;
import org.test.interfaces.StorageInterface;

import java.math.BigInteger;

public class ResultStorageMiddleware extends StringMiddleware {
    private final StorageFactoryInterface factory = new StorageFactory();
    private final StorageInterface storage = factory.getStorage();

    public ResultStorageMiddleware() {
        storage.init();
    }

    @Override
    public boolean check(String text) {
        Type.Types type = Type.getType(text);
        switch (type) {
            case INT_TYPE -> storage.storeInteger(new BigInteger(text));
            case FLOAT_TYPE -> storage.storeFloat(Float.parseFloat(text));
            case STRING_TYPE -> storage.storeString(text);

            default -> {
                System.out.println("Wrong type: " + text);
                System.out.println("Skipping...");
            }
        }
        return checkNext(text);
    }
}
