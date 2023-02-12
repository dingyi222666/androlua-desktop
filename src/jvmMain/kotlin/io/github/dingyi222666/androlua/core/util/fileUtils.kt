package io.github.dingyi222666.androlua.core.util


/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/


/**
 * 获取进程名（exe执行文件绝对路径或者类名）
 */
/*fun getProcessPath(): String? {
    val localhost = MonitoredHost.getMonitoredHost("localhost") // 获取监控主机
    val vmList= localhost.activeVms() // 取得所有在活动的虚拟机集合
    for (process in vmList) { // 遍历集合，获取进程号后比较
        val vm = localhost.getMonitoredVm(VmIdentifier("//$process"))
        // 获取进程名（类名、执行文件的绝对路径）
        val processName = MonitoredVmUtil.mainClass(vm, true)
        if (process.toString() == getProcessID()) return processName
    }
    return null
}*/

/**
 * 获取进程id
 */
/*
fun getProcessID(): String {
    val runtimeMXBean = ManagementFactory.getRuntimeMXBean()
    return runtimeMXBean.name.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
}
*/
