package com.example.myweather_app.model

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.myweather_app.view.App
import java.lang.IllegalArgumentException

private const val URI_ALL = 1 // URI для всех записей
private const val URI_ID = 2 // URI для конкретной записи
private const val ENTITY_PATH = "HistoryEntity"

class HistoryContentProvider: ContentProvider() {

    private var authorities: String? = null // Адрес URI
    private lateinit var uriMatcher: UriMatcher // Помогает определить тип адреса URI

    private var entityContentType: String? = null  // Набор строк
    private var entityContentItemType: String? = null  // Одна строка

    private lateinit var contentUri: Uri  // Адрес URI Provider

    override fun onCreate(): Boolean {
        authorities = "geekbrains.provider"         // Задаем путь
        // для опделения URI
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(authorities, ENTITY_PATH, URI_ALL)
        uriMatcher.addURI(authorities, "$ENTITY_PATH/#", URI_ID)

        entityContentType = "vnd.android.cursor.dir/vnd.$authorities.$ENTITY_PATH"
        entityContentItemType = "vnd.android.cursor.item/vnd.$authorities.$ENTITY_PATH"

        contentUri = Uri.parse("content://$authorities/$authorities/$ENTITY_PATH")

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val historyDAO: HistoryDAO = App.getHistoryDao()

        val cursor = when(uriMatcher.match(uri)) {
            URI_ALL -> historyDAO.getHistoryCursor()
            URI_ID -> {
                val id = ContentUris.parseId(uri)
                historyDAO.getHistoryCursor(id)
            }
            else -> throw IllegalArgumentException("Wrong uri: $uri")
        }
        // фиксириуем этот URI в contentResolver
        cursor.setNotificationUri(context?.contentResolver, contentUri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}