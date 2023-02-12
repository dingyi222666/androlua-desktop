package io.github.dingyi222666.androlua.core.local.db.adapter

import io.github.dingyi222666.androlua.core.local.db.DBAdapter
import org.json.JSONObject
import java.io.File

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/
class FileAdapter(
    private val path: File
) : DBAdapter {
    override suspend fun readData(): JSONObject? {
        return if (path.exists()) {
            JSONObject(path.readText())
        } else {
            null
        }
    }

    override suspend fun writeData(data: JSONObject) {
        path.writeText(data.toString())
    }
}