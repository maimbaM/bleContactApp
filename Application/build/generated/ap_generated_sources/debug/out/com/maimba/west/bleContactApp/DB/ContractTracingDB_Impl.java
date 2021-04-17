package com.maimba.west.bleContactApp.DB;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ContractTracingDB_Impl extends ContractTracingDB {
  private volatile ScannedDao _scannedDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ScannedPackets_Table` (`id` INTEGER, `pktData` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ExposurePackets_Table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userData` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b2d782972baae64c92895fe509daed27')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `ScannedPackets_Table`");
        _db.execSQL("DROP TABLE IF EXISTS `ExposurePackets_Table`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsScannedPacketsTable = new HashMap<String, TableInfo.Column>(2);
        _columnsScannedPacketsTable.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScannedPacketsTable.put("pktData", new TableInfo.Column("pktData", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysScannedPacketsTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesScannedPacketsTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoScannedPacketsTable = new TableInfo("ScannedPackets_Table", _columnsScannedPacketsTable, _foreignKeysScannedPacketsTable, _indicesScannedPacketsTable);
        final TableInfo _existingScannedPacketsTable = TableInfo.read(_db, "ScannedPackets_Table");
        if (! _infoScannedPacketsTable.equals(_existingScannedPacketsTable)) {
          return new RoomOpenHelper.ValidationResult(false, "ScannedPackets_Table(com.maimba.west.bleContactApp.DB.ScannedPacket).\n"
                  + " Expected:\n" + _infoScannedPacketsTable + "\n"
                  + " Found:\n" + _existingScannedPacketsTable);
        }
        final HashMap<String, TableInfo.Column> _columnsExposurePacketsTable = new HashMap<String, TableInfo.Column>(2);
        _columnsExposurePacketsTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExposurePacketsTable.put("userData", new TableInfo.Column("userData", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExposurePacketsTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesExposurePacketsTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExposurePacketsTable = new TableInfo("ExposurePackets_Table", _columnsExposurePacketsTable, _foreignKeysExposurePacketsTable, _indicesExposurePacketsTable);
        final TableInfo _existingExposurePacketsTable = TableInfo.read(_db, "ExposurePackets_Table");
        if (! _infoExposurePacketsTable.equals(_existingExposurePacketsTable)) {
          return new RoomOpenHelper.ValidationResult(false, "ExposurePackets_Table(com.maimba.west.bleContactApp.DB.ExposurePacket).\n"
                  + " Expected:\n" + _infoExposurePacketsTable + "\n"
                  + " Found:\n" + _existingExposurePacketsTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b2d782972baae64c92895fe509daed27", "82c1527dbc5af7483558ea0b2abae87a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "ScannedPackets_Table","ExposurePackets_Table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `ScannedPackets_Table`");
      _db.execSQL("DELETE FROM `ExposurePackets_Table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public ScannedDao scannedDao() {
    if (_scannedDao != null) {
      return _scannedDao;
    } else {
      synchronized(this) {
        if(_scannedDao == null) {
          _scannedDao = new ScannedDao_Impl(this);
        }
        return _scannedDao;
      }
    }
  }
}
