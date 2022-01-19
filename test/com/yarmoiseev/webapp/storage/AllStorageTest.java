package com.yarmoiseev.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ArrayStorageTest.class,
                SortedArrayStorageTest.class,
                MapResumeStorageTest.class,
                MapUuidStorageTest.class,
                ListStorageTest.class,
                FileStorageTest.class,
                PathStorageTest.class

        })
public class AllStorageTest {
}
