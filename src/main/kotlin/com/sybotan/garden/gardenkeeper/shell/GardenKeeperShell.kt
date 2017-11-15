/*
 * ********************************************************************************************************************
 *
 *               iFHS7.
 *              ;BBMBMBMc                  rZMBMBR              BMB
 *              MBEr:;PBM,               7MBMMEOBB:             BBB                       RBW
 *     XK:      BO     SB.     :SZ       MBM.       c;;     ir  BBM :FFr       :SSF:    ;xBMB:r   iuGXv.    i:. iF2;
 *     DBBM0r.  :D     S7   ;XMBMB       GMBMu.     MBM:   BMB  MBMBBBMBMS   WMBMBMBBK  MBMBMBM  BMBRBMBW  .MBMBMBMBB
 *      :JMRMMD  ..    ,  1MMRM1;         ;MBMBBR:   MBM  ;MB:  BMB:   MBM. RMBr   sBMH   BM0         UMB,  BMB.  KMBv
 *     ;.   XOW  B1; :uM: 1RE,   i           .2BMBs  rMB. MBO   MBO    JMB; MBB     MBM   BBS    7MBMBOBM:  MBW   :BMc
 *     OBRJ.SEE  MRDOWOR, 3DE:7OBM       .     ;BMB   RMR7BM    BMB    MBB. BMB    ,BMR  .BBZ   MMB   rMB,  BMM   rMB7
 *     :FBRO0D0  RKXSXPR. JOKOOMPi       BMBSSWBMB;    BMBB:    MBMB0ZMBMS  .BMBOXRBMB    MBMDE RBM2;SMBM;  MBB   xBM2
 *         iZGE  O0SHSPO. uGZ7.          sBMBMBDL      :BMO     OZu:BMBK,     rRBMB0;     ,EBMB  xBMBr:ER.  RDU   :OO;
 *     ,BZ, 1D0  RPSFHXR. xWZ .SMr                  . .BBB
 *      :0BMRDG  RESSSKR. 2WOMBW;                   BMBMR
 *         i0BM: SWKHKGO  MBDv
 *           .UB  OOGDM. MK,                                          Copyright (c) 2015-2017.  北京力成恒通科技有限公司
 *              ,  XMW  ..
 *                  r                                                                     All rights reserved.
 *
 * ********************************************************************************************************************
 */

package com.sybotan.garden.gardenkeeper.shell

import com.sybotan.server.shell.Shell
import com.sybotan.server.shell.ShellCommand
import org.apache.commons.cli.*
import org.apache.logging.log4j.LogManager
import org.apache.zookeeper.*
import org.apache.zookeeper.data.Stat
import java.util.*
import org.apache.zookeeper.data.ACL
import org.apache.zookeeper.data.Id
import org.apache.zookeeper.CreateMode

/**
 * GardenKeeper Shell实现类
 */
class GardenKeeperShell(server: String, timeout: Int, readonly: Boolean) : Shell() {
    // 日志记录器
    private val logger = LogManager.getLogger(GardenKeeperShell::class.java)
    // 服务器地址
    var server: String
    // 连接超时时间
    var timeout: Int
    // 只读连接
    var readonly: Boolean

    var gk: ZooKeeper
    var watcher = ClientWatcher()

    var currentPath = "/"

    // 初始化
    init {
        this.server = server
        this.timeout = timeout
        this.readonly = readonly

        // 注册命令
        registCommands()

        // 连接到GK
        gk = ZooKeeper(server, 3000, watcher, readonly)
    } // init

    /**
     * 获得命令行提示符
     *
     * @return  命令行提示符
     */
    override fun getPrompt(): String = "$server ${gk.state} $currentPath> "

    /**
     * 如果需要网络连接执行的命令，则判断是否连接
     *
     * @return  连接到服务器返回true,否则返回false
     */
    override fun isConnected(): Boolean = gk.state.isConnected

    /**
     * 注册命令
     */
    private fun registCommands() {
        registCommand(ShellCommand(
                "addauth",
                "<scheme> <auth>",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdAddauth", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "cd",
                "[path]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdCd", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "close",
                "",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdClose", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "connect",
                "[host[:port]]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdConnect", Array<String>::class.java),
                false))

        registCommand(ShellCommand(
                "create",
                "[-s] [-e] <path> <data> [acl]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdCreate", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "del",
                "<path> [version]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdDelete", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "delete",
                "<path> [version]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdDelete", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "deletequota",
                "[-n|-b] <path>",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdDeleteQuota", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "delquota",
                "[-n|-b] path",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdDeleteQuota", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "get",
                "[path] [watch]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdGet", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "getAcl",
                "[path] [watch]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdGetAcl", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "getacl",
                "[path] [watch]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdGetAcl", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "help",
                "",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdHelp", Array<String>::class.java),
                false))

        registCommand(ShellCommand(
                "listquota",
                "[path]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdListQuota", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "lsquota",
                "[path]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdListQuota", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "list",
                "[path] [watch]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdList", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "ls",
                "[path] [watch]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdList", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "list2",
                "[path] [watch]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdList2", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "ls2",
                "[path] [watch]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdList2", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "printwatches",
                "on|off",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdPrintwatches", Array<String>::class.java),
                false))

        registCommand(ShellCommand(
                "pwd",
                "",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdPwd", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "rmr",
                "<path>",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdRmr", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "set",
                "<path> <data> [version]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdSet", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "setAcl",
                "<path> <acl>",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdSetAcl", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "setacl",
                "<path> <acl>",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdSetAcl", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "setquota",
                "-n|-b <val> <path>",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdSetQuota", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "stat",
                "[path] [watch]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdStat", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "sync",
                "[path]",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdSync", Array<String>::class.java),
                true))
        return
    } // Function registCommands()

    /**
     * 处理addauth命令
     *
     * @param   args    命令参数
     */
    fun cmdAddauth(args: Array<String>) {
        logger.debug(args)
        if (args.size < 2) {
            return
        }

        var b: ByteArray? = null
        if (args.size >= 3)
            b = args[2].toByteArray()

        gk.addAuthInfo(args[1], b)
        return
    } // Function cmdAddauth()

    /**
     * 处理cd命令
     *
     * @param   args    命令参数
     */
    fun cmdCd(args: Array<String>) {
        logger.debug(args)
        if (args.size < 2) {
            cmdPwd(args)
            return
        }

        val path = absolutePath(args[1])
        if (existPath(path)) {
            currentPath = path
        } else {
            println("Path '$path' does not exist.")
        }
        return
    } // Function cmdCd()

    /**
     * 处理close命令
     *
     * @param   args    命令参数
     */
    fun cmdClose(args: Array<String>) {
        logger.debug(args)
        gk.close()
        return
    } // Function cmdClose()

    /**
     * 处理connect命令
     *
     * @param   args    命令参数
     */
    fun cmdConnect(args: Array<String>) {
        logger.debug(args)
        if (args.size >= 2) {
            server = args[1]
        }
        connectGk(server)
        return
    } // Function cmdConnect()

    /**
     * 处理create命令
     *
     * @param   args    命令参数
     */
    fun cmdCreate(args: Array<String>) {
        logger.debug(args)
        if (args.size < 3)
        {
            return
        }

        var first = 0
        var aclList: List<ACL> = ZooDefs.Ids.OPEN_ACL_UNSAFE;
        var flags = CreateMode.PERSISTENT
        if (args[1].equals("-e") && args[2].equals("-s") || args[1].equals("-s") && args[2].equals("-e")) {
            first += 2
            flags = CreateMode.EPHEMERAL_SEQUENTIAL
        } else if (args[1].equals("-e")) {
            first++
            flags = CreateMode.EPHEMERAL
        } else if (args[1].equals("-s")) {
            first++
            flags = CreateMode.PERSISTENT_SEQUENTIAL
        }
        if (args.size === first + 4) {
            aclList = parseACLs(args[first + 3])
        }
        val path = absolutePath(args[first + 1])
        try {
            val newPath = gk.create(path, args[first + 2].toByteArray(), aclList, flags)
            println("Created " + newPath)
        } catch (e: Exception) {
            // DO NOTHING
        }
        return
    } // Function cmdCreate()

    /**
     * 处理del/delete命令
     *
     * @param   args    命令参数
     */
    fun cmdDelete(args: Array<String>) {
        logger.debug(args)
        if (args.size < 2) {
            return
        }

        var path = absolutePath(args[1])
        var version: Int = -1
        try {
            if (args.size >=3 ) {
                version = Integer.parseInt(args[2])
            }
            gk.delete(path, version)
        } catch (e: KeeperException.NoNodeException) {
            println("Path '$path' does not exist.")
        } catch (e: Exception) {
            // DO NOTHING
        }

        return
    } // Function cmdDelete()

    /**
     * 处理delquota/deletequota命令
     *
     * @param   args    命令参数
     */
    fun cmdDeleteQuota(args: Array<String>) {
        logger.debug(args)
        if (args.size < 2) {
            return
        }

        var path = absolutePath(args[1])
        var version: Int = -1
        try {
            if (args.size >=3 ) {
                version = Integer.parseInt(args[2])
            }
            gk.delete(path, version)
        } catch (e: KeeperException.NoNodeException) {
            println("Path '$path' does not exist.")
        }  catch (e: Exception) {
            // DO NOTHING
        }
        return
    } // Function cmdDeleteQuota()

    /**
     * 处理get命令
     *
     * @param   args    命令参数
     */
    fun cmdGet(args: Array<String>) {
        logger.debug(args)

        val stat = Stat()
        var path = ""
        try {
            if (args.size >= 2) {
                path = args[1]
            }
            path = absolutePath(path)
            var data = gk.getData(path, watcher, stat)
            println(String(data))
            printStat(stat)
        } catch (e: KeeperException.NoNodeException) {
            println("Path '$path' does not exist.")
        } catch (e: Exception) {
            // DO NOTHING
        }

        return
    } // Function cmdGet()

    /**
     * 处理getAcl/getacl命令
     *
     * @param   args    命令参数
     */
    fun cmdGetAcl(args: Array<String>) {
        logger.debug(args)
        val stat = Stat()
        var path = ""
        try {
            if (args.size >= 2) {
                path = args[1]
            }
            path = absolutePath(path)
            val aclList = gk.getACL(path, stat);
            for (acl in aclList) {
                println("${acl.id}:${permToString(acl.perms)}")
            }
        } catch (e: KeeperException.NoNodeException) {
            println("Path '$path' does not exist.")
        } catch (e: Exception) {
            // DO NOTHING
        }
        return
    } // Function cmdGetAcl()

    /**
     * 处理help命令
     *
     * @param   args    命令参数
     */
    fun cmdHelp(args: Array<String>) {
        logger.debug(args)
        dumpCommands("gkCli [-s server:port[,server:port]...] [-t timeout] [-r] [cmd] [args]", false)
        return
    } // Function cmdHelp()

    /**
     * 处理ls/list命令
     *
     * @param   args    命令参数
     */
    fun cmdList(args: Array<String>) {
        logger.debug(args)

        var children: List<String>? = null
        var path = ""
        try {
            if (args.size >= 2) {
                path = args[1]
            }
            path = absolutePath(path)
            children = gk.getChildren(path, watcher)
            println(children)
        } catch (e: KeeperException.NoNodeException) {
            println("Path '$path' does not exist.")
        } catch (e: Exception) {
            // DO NOTHING
        }
        return
    } // Function cmdList()

    /**
     * 处理ls2/list2命令
     *
     * @param   args    命令参数
     */
    fun cmdList2(args: Array<String>) {
        logger.debug(args)

        var children: List<String>? = null
        val stat = Stat()
        var path = ""
        try {
            if (args.size >= 2) {
                path = args[1]
            }
            path = absolutePath(path)
            children = gk.getChildren(path, watcher, stat)
            println(children)
            printStat(stat)
        } catch (e: KeeperException.NoNodeException) {
            println("Path '$path' does not exist.")
        } catch (e: Exception) {
            // DO NOTHING
        }
        return
    } // Function cmdLs2()

    /**
     * 处理lsquota/listquota命令
     *
     * @param   args    命令参数
     */
    fun cmdListQuota(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdListQuota()

    /**
     * 处理printwatches命令
     *
     * @param   args    命令参数
     */
    fun cmdPrintwatches(args: Array<String>) {
        logger.debug(args)
        if (args.size >= 2) {
            watcher.printWatches = (args[1] == "on")
        }

        var watch = "off"
        if (watcher.printWatches)
        {
            watch = "on"
        }
        println("printwatches is $watch")
        return
    } // Function cmdPrintwatches()

    /**
     * 处理pwd命令
     *
     * @param   args    命令参数
     */
    fun cmdPwd(args: Array<String>) {
        logger.debug(args)
        println(currentPath)
        return
    } // Function cmdPwd()

    /**
     * 处理rmr命令
     *
     * @param   args    命令参数
     */
    fun cmdRmr(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdPwd()

    /**
     * 处理set命令
     *
     * @param   args    命令参数
     */
    fun cmdSet(args: Array<String>) {
        logger.debug(args)
        if (args.size < 3) {
            return
        }

        var path = absolutePath(args[1])
        var version: Int = -1
        var data = args[2].toByteArray()
        try {
            if (args.size >=4 ) {
                version = Integer.parseInt(args[3])
            }
            val stat = gk.setData(path, data, version)
            printStat(stat)
        } catch (e: KeeperException.NoNodeException) {
            println("Path '$path' does not exist.")
        } catch (e: Exception) {
            // DO NOTHING
        }

        return
    } // Function cmdPwd()

    /**
     * 处理setAcl/setacl命令
     *
     * @param   args    命令参数
     */
    fun cmdSetAcl(args: Array<String>) {
        logger.debug(args)
        if (args.size < 3) {
            return
        }

        var path = absolutePath(args[1])
        var version: Int = -1
        try {
            if (args.size > 4) {
                version = Integer.parseInt(args[3])
            }
            var stat = gk.setACL(path, parseACLs(args[2]), version)
            printStat(stat)
        } catch (e: KeeperException.NoNodeException) {
            println("Path '$path' does not exist.")
        } catch (e: Exception) {
            // DO NOTHING
        }

        return
    } // Function cmdSetAcl()

    /**
     * 处理setquota命令
     *
     * @param   args    命令参数
     */
    fun cmdSetQuota(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdSetQuota()

    /**
     * 处理stat命令
     *
     * @param   args    命令参数
     */
    fun cmdStat(args: Array<String>) {
        logger.debug(args)

        var path = ""
        try {
            if (args.size >= 2) {
                path = args[1]
            }
            path = absolutePath(path)
            val stat = gk.exists(path, args.size >= 3)
            printStat(stat)
        } catch (e: KeeperException.NoNodeException) {
            println("Path '$path' does not exist.")
        } catch (e: Exception) {
            // DO NOTHING
        }
        return
    } // Function cmdStat()

    /**
     * 处理Sync命令
     *
     * @param   args    命令参数
     */
    fun cmdSync(args: Array<String>) {
        logger.debug(args)
        var path = ""
        try {
            if (args.size >= 2) {
                path = args[1]
            }
            path = absolutePath(path)
            gk.sync(path, AsyncCallback.VoidCallback { rc, path, ctx -> println("Sync returned " + rc) }, null)
        } catch (e: KeeperException.NoNodeException) {
            println("Path '$path' does not exist.")
        } catch (e: Exception) {
            // DO NOTHING
        }
        return
    } // Function cmdSync()

    /**
     * 连接到GK
     *
     * @param   new     新服务器地址
     */
    private fun connectGk(newHost: String) {
        if (gk.state.isAlive) {
            gk.close()
        }

        server = newHost
        currentPath = "/"
        gk = ZooKeeper(server, timeout, watcher, false)
        return
    } // Function connectGk()

    /**
     * 将path转换成绝对路径
     *
     * @param   path        要转换的路径
     * @param   currPath    当关路径
     * @return  path的绝对路径
     */
    private fun absolutePath(path: String, currPath: String = currentPath): String {
        // 如果路径为空字符串，则返回当前路径
        if (path.isBlank() || "." == path) {
            return currPath
        }
        // 将Windows路径分隔符转换成linux路径分隔符
        var newPath : String = path.replace("\\", "/")
        if (path.startsWith("..")) {
            newPath = currPath.substring(0, currPath.lastIndexOf("/")) + path.substring(2)
        } else if (!path.startsWith("/")) { // 如果path不是以”/“打头，则拼接当前路径
            newPath = currPath + "/" + path
        }

        // 将"//"转换为"/"
        newPath = newPath.replace("//", "/")

        // 如果路径不是以”/”打头，则增加“/”
        if (!newPath.startsWith("/")) {
            newPath = "/" + newPath
        }
        return newPath
    } // Function absolutePath()

    /**
     * 判断节点路径是否存在
     *
     * @param   path  节点路径
     * @return  存在返回true，否则返回false
     */
    private fun existPath(path: String): Boolean = (gk.exists(path, false) != null)
    /**
     * 打印节点状态信息
     *
     * @param stat  节点状态
     */
    private fun printStat(stat: Stat) {
        println("cZxid = 0x" + java.lang.Long.toHexString(stat.czxid))
        println("ctime = " + Date(stat.ctime).toString())
        println("mZxid = 0x" + java.lang.Long.toHexString(stat.mzxid))
        println("mtime = " + Date(stat.mtime).toString())
        println("pZxid = 0x" + java.lang.Long.toHexString(stat.pzxid))
        println("cversion = " + stat.cversion)
        println("dataVersion = " + stat.version)
        println("aclVersion = " + stat.aversion)
        println("ephemeralOwner = 0x" + java.lang.Long.toHexString(stat.ephemeralOwner))
        println("dataLength = " + stat.dataLength)
        println("numChildren = " + stat.numChildren)
        return
    } // Function printStat()

    /**
     * 权限转字符串
     *
     * @param   perms       整数描述的权限
     * @return  字符串描述的权限
     */
    private fun permToString(perms: Int): String {
        val p = StringBuilder()
        if (perms and ZooDefs.Perms.CREATE != 0) {
            p.append('c')
        }
        if (perms and ZooDefs.Perms.DELETE != 0) {
            p.append('d')
        }
        if (perms and ZooDefs.Perms.READ != 0) {
            p.append('r')
        }
        if (perms and ZooDefs.Perms.WRITE != 0) {
            p.append('w')
        }
        if (perms and ZooDefs.Perms.ADMIN != 0) {
            p.append('a')
        }
        return p.toString()
    } // getPermString（）

    private fun parseACLs(aclString: String): List<ACL> {
        val acl: MutableList<ACL>
        val acls = aclString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        acl = ArrayList()
        for (a in acls) {
            val firstColon = a.indexOf(':')
            val lastColon = a.lastIndexOf(':')
            if (firstColon == -1 || lastColon == -1 || firstColon == lastColon) {
                System.err
                        .println(a + " does not have the form scheme:id:perm")
                continue
            }
            val newAcl = ACL()
            newAcl.id = Id(a.substring(0, firstColon), a.substring(
                    firstColon + 1, lastColon))
            newAcl.perms = getPermFromString(a.substring(lastColon + 1))
            acl.add(newAcl)
        }
        return acl
    }

    private fun getPermFromString(permString: String): Int {
        var perm = 0
        for (i in 0 until permString.length) {
            when (permString[i]) {
                'r' -> perm = perm or ZooDefs.Perms.READ
                'w' -> perm = perm or ZooDefs.Perms.WRITE
                'c' -> perm = perm or ZooDefs.Perms.CREATE
                'd' -> perm = perm or ZooDefs.Perms.DELETE
                'a' -> perm = perm or ZooDefs.Perms.ADMIN
                else -> System.err
                        .println("Unknown perm type: " + permString[i])
            }
        }
        return perm
    }
} // Class GardenKeeperShell

/**
 * GardenKeeper Shell应用入口
 *
 * @param   args    保存命令行参数
 */
fun main(args: Array<String>) {
    // 日志记录器
    val logger = LogManager.getLogger("gardenkeeper")
    // 命令行语法定义
    val cmdLineSyntax = "gkCli [-s server:port[,server:port]...] [-t timeout] [-r] [cmd] [args]"
    // 连接服务器地址
    var server: String = "localhost:2181"
    // 连接超时时间
    var timeout: Int = 3000
    // 是否只读方式打开
    var readonly: Boolean

    // 定义命令行
    val opt = Options()
    opt.addOption(Option("s","server", true,"server:port"))
    opt.addOption(Option("t","timeout", true,"timeout"))
    opt.addOption(Option("r","readonly", false,"readonly"))
    opt.addOption(Option("h","help", false,"print help for the command."))

    // 解析命令行
    val cmdLine = DefaultParser().parse(opt, args)

    // 处理-h/--help参数
    if (cmdLine.hasOption("h")) {
        val hf = HelpFormatter()
        hf.printHelp(cmdLineSyntax, "", opt, "")
    }
    // 处理-s/--server参数
    if (cmdLine.hasOption("s")) {
        server = cmdLine.getOptionValue("s")
        logger.debug("server=$server")
    }
    // 处理-t/--timeout参数
    if (cmdLine.hasOption("t")) {
        timeout = cmdLine.getOptionValue("t")!!.toInt()
        logger.debug("timeout=$timeout")
    }
    // 处理-r参数
    readonly = cmdLine.hasOption("r")
    logger.debug("readonly=$readonly")

    println("Connecting to  $server ...")
    GardenKeeperShell(server, timeout, readonly).exec()

    return
} // Function main()