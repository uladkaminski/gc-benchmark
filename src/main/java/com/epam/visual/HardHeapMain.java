package com.epam.visual;

import org.apache.log4j.Logger;

public class HardHeapMain {
    private static final Logger LOG = Logger.getLogger(HardHeapMain.class);

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        // The Serial Collector:  -XX:+UseSerialGC
        // G1:  –XX:+UseG1GC
        // CMS: -XX:+UseConcMarkSweepGC
        NestingDoll root;

        while (true) {
            root = new NestingDoll(String.valueOf(i));

            for (int j = 0; j < 30; j++) {
                i++;
                root.putNestingDoll(new NestingDoll(String.valueOf(i)));
                LOG.info("Current deep is: " + root.getDeep());
            }
        }
    }
}
