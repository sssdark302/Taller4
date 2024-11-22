package com.example.taller4_142514

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class WidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val dbHelper = DatabaseHelper(context)
        val db = dbHelper.readableDatabase

        // Obtener nombres desde la base de datos
        val cursor = db.query("users", arrayOf("name"), null, null, null, null, null)
        val userNames = mutableListOf<String>()
        while (cursor.moveToNext()) {
            userNames.add(cursor.getString(cursor.getColumnIndexOrThrow("name")))
        }
        cursor.close()

        val displayText = if (userNames.isNotEmpty()) {
            userNames.joinToString("\n")
        } else {
            "No hay nombres almacenados"
        }

        // Configurar el diseño del widget
        val views = RemoteViews(context.packageName, R.layout.widget_layout)
        views.setTextViewText(R.id.widgetTextView, displayText)

        // Configurar botón para actualizar
        val intentUpdate = Intent(context, WidgetProvider::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intentUpdate,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.widgetUpdateButton, pendingIntent)

        // Actualizar el widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}