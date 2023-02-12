package io.github.dingyi222666.androlua.core.project

import java.io.File

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/
class Project(
    val rootDir: File
) {


    companion object {

        fun isProjectDir(dir: File): Boolean {
            return dir.exists() && dir.isDirectory && dir.listFiles()?.any { it.name == "init.lua" } == true
        }
    }

}