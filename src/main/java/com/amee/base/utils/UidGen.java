package com.amee.base.utils;

import java.io.Serializable;
import java.util.Random;

/**
 * These have a probability of 3.55271368 ? 10^-15 of not being unique :)
 */
public class UidGen implements Serializable {

    // An instance for creating typical 12 char AMEE UIDs (ranging from 000000000000 to ZZZZZZZZZZZZ).
    public final static UidGen INSTANCE_12 = new UidGen("", 6, 2, 36);

    // An instance for creating typical 16 char AMEE UIDs (ranging from 0000000000000000 to ZZZZZZZZZZZZZZZZ).
    public final static UidGen INSTANCE_16 = new UidGen("", 8, 2, 36);

    private final static Random RANDOM = new Random();

    private String separator;
    private int parts;
    private int partSize;
    private int radix;
    private int range;

    private UidGen() {
        super();
    }

    public UidGen(String separator, int parts, int partSize, int radix) {
        this();
        this.separator = separator;
        this.parts = parts;
        this.partSize = partSize;
        this.radix = radix;
        this.range = (int) Math.pow(radix, partSize);
    }

    /**
     * Generates a 'unique' uid containing hex values.
     * <p/>
     * Example: 2DF512B4F183
     *
     * @return uid
     */
    public String getUid() {
        StringBuffer uid = new StringBuffer();
        for (int i = 0; i < parts; i++) {
            addUidPart(uid);
            if (i != parts) {
                uid.append(separator);
            }
        }
        return uid.toString().toUpperCase();
    }

    private void addUidPart(StringBuffer uid) {
        StringBuffer part = new StringBuffer();
        part.append(Integer.toHexString(RANDOM.nextInt(range)));
        while (part.length() < partSize) {
            part.insert(0, '0'); // left pad with 0's
        }
        uid.append(part);
    }

    public boolean isValid(String uid) {
        if (uid == null) return false;
        if (uid.length() != (parts * partSize)) return false;
        for (int i = 0; i < uid.length(); i++) {
            if (Character.digit(uid.charAt(i), radix) == -1) return false;
        }
        return true;
    }
}