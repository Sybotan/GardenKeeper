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

import jline.console.ConsoleReader
import org.apache.commons.cli.*
import org.apache.logging.log4j.LogManager
import java.util.*

/**
 * GardenKeeper Shell实现类
 */
class GardenKeeperShell(server: String, timeout: Int, readonly: Boolean) {
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
    } // init

    /**
     * 启动Shell
     */
    fun exec() {
        // 如果在命令行启动，则使用功能完备的ConsoleReader。否则使用功能单一的Scanner。
        if (null != System.console()) {
            logger.debug("Console is ConsoleReader.")
            var console = ConsoleReader()
            do {
                var command = console.readLine(getPrompt()).trim()
                processCommand(command)
            } while (command!! != exitCmd)
        } else {
            logger.debug("Console is Scanner.")
            var console = Scanner(System.`in`)
            do {
                System.out.print(getPrompt())
                var command = console.nextLine().trim()
                processCommand(command)
            } while (command!! != exitCmd)
        }
        return
    } // Function exec()

    /**
     * 获得命令行提示符
     */
    fun getPrompt(): String = "GardenKeeper> "

    /**
     * 处理Shell命令
     *
     * @param  command      用户在Shell输入的命令
     */
    fun processCommand(command: String) {
        logger.debug("command=$command")
        return
    } // Function processCommand()
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
    var readonly: Boolean = false

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