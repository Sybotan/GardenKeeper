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

/**
 * GardenKeeper Shell实现类
 */
class GardenKeeperShell(server: String, timeout: Int, readonly: Boolean) : Shell() {
    // 日志记录器
    private val logger = LogManager.getLogger(GardenKeeperShell::class.java)
    // 服务器地址
    val server: String
    // 连接超时时间
    val timeout: Int
    // 只读连接
    val readonly: Boolean

    var exitCmd: String = "exit"                // 退出命令

    // 初始化
    init {
        this.server = server
        this.timeout = timeout
        this.readonly = readonly

        // 注册命令
        registCommands()
    } // init

    /**
     * 获得命令行提示符
     */
    override fun getPrompt(): String = "GardenKeeper> "

    /**
     * 如果需要网络连接执行的命令，则判断是否连接
     */
    override fun isConnected(): Boolean = false

    /**
     * 注册命令
     */
    private fun registCommands() {
        registCommand(ShellCommand(
                "addauth",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdAddauth", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "cd",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdCd", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "close",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdClose", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "connect",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdConnect", Array<String>::class.java),
                false))

        registCommand(ShellCommand(
                "create",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdCreate", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "del",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdDelete", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "delete",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdDelete", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "deletequota",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdDeleteQuota", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "delquota",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdDeleteQuota", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "get",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdGet", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "getAcl",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdGetAcl", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "getacl",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdGetAcl", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "help",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdHelp", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "listquota",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdListQuota", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "lsquota",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdListQuota", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "list",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdList", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "ls",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdList", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "list2",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdList2", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "ls2",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdList2", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "printwatches",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdPrintwatches", Array<String>::class.java),
                false))

        registCommand(ShellCommand(
                "pwd",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdPwd", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "rmr",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdRmr", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "set",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdSet", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "setAcl",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdSetAcl", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "setacl",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdSetAcl", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "setquota",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdSetQuota", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "stat",
                GardenKeeperShell::class.java.getDeclaredMethod("cmdStat", Array<String>::class.java),
                true))

        registCommand(ShellCommand(
                "sync",
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
        return
    } // Function cmdAddauth()

    /**
     * 处理cd命令
     *
     * @param   args    命令参数
     */
    fun cmdCd(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdCd()

    /**
     * 处理close命令
     *
     * @param   args    命令参数
     */
    fun cmdClose(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdClose()

    /**
     * 处理connect命令
     *
     * @param   args    命令参数
     */
    fun cmdConnect(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdConnect()

    /**
     * 处理create命令
     *
     * @param   args    命令参数
     */
    fun cmdCreate(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdCreate()

    /**
     * 处理del/delete命令
     *
     * @param   args    命令参数
     */
    fun cmdDelete(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdDelete()

    /**
     * 处理delquota/deletequota命令
     *
     * @param   args    命令参数
     */
    fun cmdDeleteQuota(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdDeleteQuota()

    /**
     * 处理get命令
     *
     * @param   args    命令参数
     */
    fun cmdGet(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdGet()

    /**
     * 处理getAcl/getacl命令
     *
     * @param   args    命令参数
     */
    fun cmdGetAcl(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdGetAcl()

    /**
     * 处理help命令
     *
     * @param   args    命令参数
     */
    fun cmdHelp(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdHelp()

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
     * 处理ls/list命令
     *
     * @param   args    命令参数
     */
    fun cmdList(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdList()

    /**
     * 处理ls2/list2命令
     *
     * @param   args    命令参数
     */
    fun cmdList2(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdLs2()

    /**
     * 处理printwatches命令
     *
     * @param   args    命令参数
     */
    fun cmdPrintwatches(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdPrintwatches()

    /**
     * 处理pwd命令
     *
     * @param   args    命令参数
     */
    fun cmdPwd(args: Array<String>) {
        logger.debug(args)
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
        return
    } // Function cmdPwd()

    /**
     * 处理setAcl/setacl命令
     *
     * @param   args    命令参数
     */
    fun cmdSetAcl(args: Array<String>) {
        logger.debug(args)
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
        return
    } // Function cmdStat()

    /**
     * 处理Sync命令
     *
     * @param   args    命令参数
     */
    fun cmdSync(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdSync()
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
    val cmdLineSyntax = "gkCli [-s server:port[,server:port]...] [-t timeout] [-r]"
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