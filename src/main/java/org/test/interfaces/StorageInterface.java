package org.test.interfaces;

import java.math.BigInteger;

public interface StorageInterface {
    void init();
    void storeString(String value);
    void storeInteger(BigInteger value);
    void storeDouble(Double value);
}
