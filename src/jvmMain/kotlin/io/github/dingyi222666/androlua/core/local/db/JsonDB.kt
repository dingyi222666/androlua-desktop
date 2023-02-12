package io.github.dingyi222666.androlua.core.local.db

import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/
class JsonDB(
    val adapter: DBAdapter
) {

    private lateinit var rootJsonObject: JSONObject


    suspend fun sync() {
        rootJsonObject = adapter.readData() ?: JSONObject()
    }

    fun setLong(key: String, value: Long) {
        rootJsonObject.put(key, value)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return rootJsonObject.optLong(key, defaultValue)
    }

    fun setString(key: String, value: String) {
        rootJsonObject.put(key, value)
    }

    fun getString(key: String, defaultValue: String): String {
        return rootJsonObject.optString(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        rootJsonObject.put(key, value)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return rootJsonObject.optBoolean(key, defaultValue)
    }

    fun setInt(key: String, value: Int) {
        rootJsonObject.put(key, value)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return rootJsonObject.optInt(key, defaultValue)
    }

    fun setDouble(key: String, value: Double) {
        rootJsonObject.put(key, value)
    }

    fun getDouble(key: String, defaultValue: Double): Double {
        return rootJsonObject.optDouble(key, defaultValue)
    }

    fun setFloat(key: String, value: Float) {
        rootJsonObject.put(key, value)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return rootJsonObject.optDouble(key, defaultValue.toDouble()).toFloat()
    }

    fun setJsonObject(key: String, value: JSONObject) {
        rootJsonObject.put(key, value)
    }

    fun getJsonObject(key: String): JSONObject? {
        return rootJsonObject.optJSONObject(key)
    }

    fun setJsonArray(key: String, value: JSONArray) {
        rootJsonObject.put(key, value)
    }

    fun getJsonArray(key: String): JSONArray? {
        return rootJsonObject.optJSONArray(key)
    }

    suspend fun write() {
        adapter.writeData(rootJsonObject)
    }
}