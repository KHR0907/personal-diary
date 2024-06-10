package com.example.personal_diary_2022551022;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class StorageUtils {

    public static long getTotalStorageSpaceInBytes() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return totalBlocks * blockSize;
    }

    public static long getFreeStorageSpaceInBytes() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }

    // Helper methods for converting bytes to more readable units
    public static String bytesToHumanReadable(long bytes) {
        // ... (Implementation to convert bytes to KB, MB, GB, etc.)
        //convert bytes to KB, MB, GB, etc.
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp-1) + "B";
        return String.format("%.1f %s", bytes / Math.pow(1024, exp), pre);
    }

}
