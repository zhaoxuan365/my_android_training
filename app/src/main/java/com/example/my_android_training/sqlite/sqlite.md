// 将旧表的数据迁移到新表, 然后删除旧表   
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.beginTransaction(); // 开启事务
try {
// 创建新表，包含修改后的字段和新增字段
db.execSQL("CREATE TABLE new_table (" +
"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
"userId TEXT, " +
"currentThemeName TEXT, " +
"isUserVip INTEGER, " +
"themeIndex INTEGER DEFAULT 0);");

        // 将旧表数据迁移到新表
        db.execSQL("INSERT INTO new_table (_id, userId, currentThemeName, isUserVip) " +
                "SELECT _id, userId, themeName, isCurrentUserVip FROM old_table;");

        // 删除旧表
        db.execSQL("DROP TABLE old_table;");

        // 将新表重命名为旧表名
        db.execSQL("ALTER TABLE new_table RENAME TO old_table;");

        db.setTransactionSuccessful(); // 标记事务成功
    } catch (Exception e) {
        e.printStackTrace(); // 捕获异常，记录日志
        // 如果发生异常，事务将回滚
    } finally {
        db.endTransaction(); // 结束事务，成功时提交，失败时回滚
    }
}

// 判断表是否存在的代码
private boolean isTableExists(SQLiteDatabase db, String tableName) {
boolean isExists = false;
Cursor cursor = null;
try {
String query = "SELECT name FROM sqlite_master WHERE type='table' AND name=?";
cursor = db.rawQuery(query, new String[]{tableName});
isExists = cursor.getCount() > 0;
} catch (Exception e) {
e.printStackTrace();
} finally {
if (cursor != null) {
cursor.close();
}
}
return isExists;
}

// 通过查询 SQLite 的系统表 PRAGMA table_info 来判断某个数据库表中是否存在某个字段
public boolean isColumnExists(SQLiteDatabase db, String tableName, String columnName) {
boolean isExists = false;
Cursor cursor = null;
try {
// 使用 PRAGMA table_info 查询表的元数据
cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
if (cursor != null) {
while (cursor.moveToNext()) {
// 获取每一列的名称
String existingColumnName = cursor.getString(cursor.getColumnIndex("name"));
if (columnName.equals(existingColumnName)) {
isExists = true;
break;
}
}
}
} catch (Exception e) {
e.printStackTrace();
} finally {
if (cursor != null) {
cursor.close();
}
}
return isExists;
}

// Android的数据库通常存放在应用的私有目录中，默认路径为：
/data/data/<应用包名>/databases/<数据库名>
// 默认情况下，数据库是私有的，只有该应用的用户空间能访问，其他应用不能访问。
// 你可以通过Context.getDatabasePath(String name)获取默认数据库路径。
// 如果需要自定义路径，可以通过SQLiteDatabase.openOrCreateDatabase(String path, CursorFactory factory)指定数据库存储的路径。
你可以将数据库存储在以下位置：
应用私有目录
默认是最安全的方式。
数据库只能被应用本身访问，即使在root用户下，也会受到保护。
String dbPath = context.getDatabasePath("my_database.db").getPath();
SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
