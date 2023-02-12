package io.github.dingyi222666.androlua.core.local.db

import org.json.JSONObject

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/
interface DBAdapter {

    suspend fun readData(): JSONObject?

    suspend fun writeData(data: JSONObject)
}