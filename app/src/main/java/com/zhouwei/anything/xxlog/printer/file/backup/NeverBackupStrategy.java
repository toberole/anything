
package com.zhouwei.anything.xxlog.printer.file.backup;

import java.io.File;

public class NeverBackupStrategy implements BackupStrategy {
    @Override
    public boolean shouldBackup(File file) {
        return false;
    }
}
