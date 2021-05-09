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
import java.lang.Long;
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

  private final EntityInsertionAdapter<Location> __insertionAdapterOfLocation;

  private final EntityInsertionAdapter<ServiceData> __insertionAdapterOfServiceData;

  private final EntityDeletionOrUpdateAdapter<ScannedPacket> __deletionAdapterOfScannedPacket;

  private final EntityDeletionOrUpdateAdapter<ExposurePacket> __deletionAdapterOfExposurePacket;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldPackets;

  private final SharedSQLiteStatement __preparedStmtOfInsertWithTime;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldExpPkts;

  public ScannedDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfScannedPacket = new EntityInsertionAdapter<ScannedPacket>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ScannedPackets_Table` (`id`,`pktData`,`timeSeen`,`location`) VALUES (?,?,?,?)";
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
        if (value.getTimeSeen() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTimeSeen());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getLocation());
        }
      }
    };
    this.__insertionAdapterOfExposurePacket = new EntityInsertionAdapter<ExposurePacket>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ExposurePackets_Table` (`id`,`userData`,`userID`,`FirstName`,`LastName`,`userPhone`,`caseDisease`,`caseDateReported`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ExposurePacket value) {
        stmt.bindLong(1, value.getId());
        if (value.getUserData() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUserData());
        }
        if (value.getUserID() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUserID());
        }
        if (value.getFirstName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getLastName());
        }
        if (value.getUserPhone() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getUserPhone());
        }
        if (value.getCaseDisease() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCaseDisease());
        }
        if (value.getCaseDateReported() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getCaseDateReported());
        }
      }
    };
    this.__insertionAdapterOfLocation = new EntityInsertionAdapter<Location>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Location_Table` (`id`,`locationCoordinates`,`addressLine`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Location value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getLocationCoordinates() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getLocationCoordinates());
        }
        if (value.getAddressLine() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAddressLine());
        }
      }
    };
    this.__insertionAdapterOfServiceData = new EntityInsertionAdapter<ServiceData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ServiceData_Table` (`id`,`serviceData`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ServiceData value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getServiceData() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getServiceData());
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
    this.__preparedStmtOfInsertWithTime = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "INSERT INTO ScannedPackets_Table (pktData,location) VALUES (?,?) ";
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
  public void insertLocation(final Location location) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfLocation.insert(location);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertServiceData(final ServiceData serviceData) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfServiceData.insert(serviceData);
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
  public void insertWithTime(final String pktData, final String location) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfInsertWithTime.acquire();
    int _argIndex = 1;
    if (pktData == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, pktData);
    }
    _argIndex = 2;
    if (location == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, location);
    }
    __db.beginTransaction();
    try {
      _stmt.executeInsert();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfInsertWithTime.release(_stmt);
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
  public LiveData<List<ScannedPacket>> getAllScanPkts() {
    final String _sql = "SELECT `ScannedPackets_Table`.`id` AS `id`, `ScannedPackets_Table`.`pktData` AS `pktData`, `ScannedPackets_Table`.`timeSeen` AS `timeSeen`, `ScannedPackets_Table`.`location` AS `location` FROM scannedpackets_table ORDER BY timeSeen DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"scannedpackets_table"}, false, new Callable<List<ScannedPacket>>() {
      @Override
      public List<ScannedPacket> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPktData = CursorUtil.getColumnIndexOrThrow(_cursor, "pktData");
          final int _cursorIndexOfTimeSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "timeSeen");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final List<ScannedPacket> _result = new ArrayList<ScannedPacket>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ScannedPacket _item;
            final String _tmpPktData;
            _tmpPktData = _cursor.getString(_cursorIndexOfPktData);
            _item = new ScannedPacket(_tmpPktData);
            final Long _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getLong(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpTimeSeen;
            _tmpTimeSeen = _cursor.getString(_cursorIndexOfTimeSeen);
            _item.setTimeSeen(_tmpTimeSeen);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            _item.setLocation(_tmpLocation);
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

  @Override
  public String selectLastLocation() {
    final String _sql = "SELECT Location_Table.addressLine FROM Location_Table WHERE id=(SELECT max(id) FROM Location_Table)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getString(0);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public String selectServiceData() {
    final String _sql = "SELECT ServiceData_Table.serviceData FROM ServiceData_Table WHERE id=(SELECT max(id) FROM ServiceData_Table)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getString(0);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<MatchedPackets>> getMatchedPackets() {
    final String _sql = "SELECT DISTINCT ScannedPackets_Table.timeSeen ,ScannedPackets_Table.location , ExposurePackets_Table.caseDisease , ExposurePackets_Table.FirstName , ExposurePackets_Table.userPhone FROM ScannedPackets_Table INNER JOIN ExposurePackets_Table on ScannedPackets_Table.pktData = ExposurePackets_Table.userData";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"ScannedPackets_Table","ExposurePackets_Table"}, false, new Callable<List<MatchedPackets>>() {
      @Override
      public List<MatchedPackets> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTimeExposed = CursorUtil.getColumnIndexOrThrow(_cursor, "timeSeen");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDisease = CursorUtil.getColumnIndexOrThrow(_cursor, "caseDisease");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "FirstName");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "userPhone");
          final List<MatchedPackets> _result = new ArrayList<MatchedPackets>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MatchedPackets _item;
            _item = new MatchedPackets();
            final String _tmpTimeExposed;
            _tmpTimeExposed = _cursor.getString(_cursorIndexOfTimeExposed);
            _item.setTimeExposed(_tmpTimeExposed);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            _item.setLocation(_tmpLocation);
            final String _tmpDisease;
            _tmpDisease = _cursor.getString(_cursorIndexOfDisease);
            _item.setDisease(_tmpDisease);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            _item.setPhone(_tmpPhone);
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

  @Override
  public LiveData<List<String>> getExpUID() {
    final String _sql = "SELECT DISTINCT ExposurePackets_Table.userID FROM ExposurePackets_Table INNER JOIN ScannedPackets_Table on ScannedPackets_Table.pktData = ExposurePackets_Table.userData";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"ExposurePackets_Table","ScannedPackets_Table"}, false, new Callable<List<String>>() {
      @Override
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
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
