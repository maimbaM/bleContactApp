package com.maimba.west.bleContactApp.DB;

import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ScannedDao_Impl implements ScannedDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ScannedPacket> __insertionAdapterOfScannedPacket;

  private final EntityInsertionAdapter<ExposurePacket> __insertionAdapterOfExposurePacket;

  private final EntityDeletionOrUpdateAdapter<ScannedPacket> __deletionAdapterOfScannedPacket;

  private final EntityDeletionOrUpdateAdapter<ExposurePacket> __deletionAdapterOfExposurePacket;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldPackets;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldExpPkts;

  public ScannedDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfScannedPacket = new EntityInsertionAdapter<ScannedPacket>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ScannedPackets_Table` (`id`,`pktData`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ScannedPacket value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getPktData() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPktData());
        }
      }
    };
    this.__insertionAdapterOfExposurePacket = new EntityInsertionAdapter<ExposurePacket>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ExposurePackets_Table` (`id`,`userData`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ExposurePacket value) {
        stmt.bindLong(1, value.getId());
        if (value.getUserData() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUserData());
        }
      }
    };
    this.__deletionAdapterOfScannedPacket = new EntityDeletionOrUpdateAdapter<ScannedPacket>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ScannedPackets_Table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ScannedPacket value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
      }
    };
    this.__deletionAdapterOfExposurePacket = new EntityDeletionOrUpdateAdapter<ExposurePacket>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ExposurePackets_Table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ExposurePacket value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__preparedStmtOfDeleteOldPackets = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM ScannedPackets_Table";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldExpPkts = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM EXPOSUREPACKETS_TABLE";
        return _query;
      }
    };
  }

  @Override
  public void insert(final ScannedPacket scannedPacket) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfScannedPacket.insert(scannedPacket);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertExposurePkt(final ExposurePacket exposurePacket) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfExposurePacket.insert(exposurePacket);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final ScannedPacket scannedPacket) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfScannedPacket.handle(scannedPacket);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteExposurePkt(final ExposurePacket exposurePacket) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfExposurePacket.handle(exposurePacket);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteOldPackets() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldPackets.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteOldPackets.release(_stmt);
    }
  }

  @Override
  public void deleteOldExpPkts() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldExpPkts.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteOldExpPkts.release(_stmt);
    }
  }
}
