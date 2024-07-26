package org.test.factories;

import org.test.storages.FileStorage;
import org.test.interfaces.StorageFactoryInterface;
import org.test.interfaces.StorageInterface;

public class StorageFactory implements StorageFactoryInterface{

    @Override
    public StorageInterface getStorage() {
        return FileStorage.getInstance();
    }
}
