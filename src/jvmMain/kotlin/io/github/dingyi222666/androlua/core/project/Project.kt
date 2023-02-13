package io.github.dingyi222666.androlua.core.project

import io.github.dingyi222666.androlua.core.local.db.JsonDB
import io.github.dingyi222666.androlua.core.local.db.adapter.FileAdapter
import org.json.JSONArray
import java.io.File
import java.util.prefs.Preferences

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/
class Project(
    val rootDir: File
) {


    private val db = JsonDB(FileAdapter(rootDir.resolve(".project.json")))


    private val openedFiles = mutableListOf<File>()

    suspend fun initProjectData() {
        db.sync()

        (db.getJsonArray("openedFiles") ?: JSONArray()).forEach {
            openedFiles.add(File(it.toString()))
        }
        db.setJsonArray("openedFiles", db.getJsonArray("openedFiles") ?: JSONArray())
    }

    fun getOpenedFiles(): List<File> {
        return openedFiles
    }

    fun addOpenedFile(file: File) {
        openedFiles.add(file)
        db.getJsonArray("openedFiles")?.put(file.absolutePath)
    }

    fun removeOpenedFile(file: File) {
        openedFiles.remove(file)
        db.getJsonArray("openedFiles")?.let {
            it.remove(it.indexOf(file.absolutePath))
        }
    }

    fun supportOpen(file: File): Boolean {
        return when (file.extension) {
            "lua", "aly" -> true
            else -> false
        }
    }

    companion object {

        val EMPTY = Project(File(""))

        fun isProjectDir(dir: File): Boolean {
            return dir.exists() && dir.isDirectory && dir.listFiles()?.any { it.name == "init.lua" } == true
        }


    }

}