package database;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class findnote_Impl implements findnote {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<find> __insertionAdapterOfTodo;

  private final EntityDeletionOrUpdateAdapter<find> __deletionAdapterOfTodo;

  private final EntityDeletionOrUpdateAdapter<find> __updateAdapterOfTodo;

  private final SharedSQLiteStatement __preparedStmtOfUpdate;

  public TodoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTodo = new EntityInsertionAdapter<find>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `Todo` (`item`,`id`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final find entity) {
        if (entity.getItem() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getItem());
        }
        if (entity.getId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getId());
        }
      }
    };
    this.__deletionAdapterOfTodo = new EntityDeletionOrUpdateAdapter<find>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `Todo` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final find entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindLong(1, entity.getId());
        }
      }
    };
    this.__updateAdapterOfTodo = new EntityDeletionOrUpdateAdapter<find>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `Todo` SET `item` = ?,`id` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final find entity) {
        if (entity.getItem() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getItem());
        }
        if (entity.getId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getId());
        }
        if (entity.getId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getId());
        }
      }
    };
    this.__preparedStmtOfUpdate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE Todo SET item = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final find todo, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTodo.insert(todo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final find find, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTodo.handle(todo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final find find, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTodo.handle(find);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Integer id, final String newValue,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate.acquire();
        int _argIndex = 1;
        if (newValue == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, newValue);
        }
        _argIndex = 2;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, id);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdate.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public List<Todo> getAllTodoItems() {
    final String _sql = "SELECT * FROM Todo";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfItem = CursorUtil.getColumnIndexOrThrow(_cursor, "item");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final List<find> _result = new ArrayList<find>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final find _item;
        final String _tmpItem;
        if (_cursor.isNull(_cursorIndexOfItem)) {
          _tmpItem = null;
        } else {
          _tmpItem = _cursor.getString(_cursorIndexOfItem);
        }
        _item = new find(_tmpItem);
        final Integer _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getInt(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public find getOne(final int id) {
    final String _sql = "SELECT * FROM Todo WHERE id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfItem = CursorUtil.getColumnIndexOrThrow(_cursor, "item");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final Todo _result;
      if (_cursor.moveToFirst()) {
        final String _tmpItem;
        if (_cursor.isNull(_cursorIndexOfItem)) {
          _tmpItem = null;
        } else {
          _tmpItem = _cursor.getString(_cursorIndexOfItem);
        }
        _result = new find(_tmpItem);
        final Integer _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getInt(_cursorIndexOfId);
        }
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
