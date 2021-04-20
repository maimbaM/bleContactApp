package com.maimba.west.bleContactApp.DB;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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

  @Override
  public LiveData<List<MatchedPackets>> getMatchedPackets() {
    final String _sql = "SELECT DISTINCT ScannedPackets_Table.pktData FROM ScannedPackets_Table INNER JOIN ExposurePackets_Table on ScannedPackets_Table.pktData = ExposurePackets_Table.userData";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"ScannedPackets_Table","ExposurePackets_Table"}, false, new Callable<List<MatchedPackets>>() {
      @Override
      public List<MatchedPackets> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPacket = CursorUtil.getColumnIndexOrThrow(_cursor, "pktData");
          final List<MatchedPackets> _result = new ArrayList<MatchedPackets>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MatchedPackets _item;
            _item = new MatchedPackets();
            final String _tmpPacket;
            _tmpPacket = _cursor.getString(_cursorIndexOfPacket);
            _item.setPacket(_tmpPacket);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
