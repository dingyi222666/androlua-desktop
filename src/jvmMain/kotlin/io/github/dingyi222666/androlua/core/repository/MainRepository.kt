package io.github.dingyi222666.androlua.core.repository

import io.github.dingyi222666.androlua.core.project.Project
import java.io.File

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/
object MainRepository {


    fun openProject(file: File): Project {
        if (!Project.isProjectDir(file)) {
            error("这不是一个项目")
        }
        return Project(file)
    }
}