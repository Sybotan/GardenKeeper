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

package com.sybotan.server.shell

import jline.console.ConsoleReader
import org.apache.logging.log4j.LogManager
import java.util.*
import java.util.Comparator

/**
 * @author  Andy by 2017/11/14
 */
open class Shell(exitCmd: String = "exit") {
    // 日志记录器
    private val logger = LogManager.getLogger(Shell::class.java)
    // 退出命令
    private val exitCmd: String
    // 命令映射
    private var commandMap = HashMap<String, ShellCommand>()

    // 初始化操作
    init {
        this.exitCmd = exitCmd
        registCommand(ShellCommand(
                exitCmd,
                "",
                Shell::class.java.getDeclaredMethod("cmdExit", Array<String>::class.java),
                false))
    } // init

    /**
     * 启动Shell
     */
    fun exec() {
        // 如果在命令行启动，则使用功能完备的ConsoleReader。否则使用功能单一的Scanner。
        if (null != System.console()) {
            logger.debug("console = ConsoleReader")
            var console = ConsoleReader()
            do {
                var command = console.readLine(getPrompt()).trim()
                processCommand(command)
            } while (command != exitCmd)
        } else {
            logger.debug("console = Scanner")
            var console = Scanner(System.`in`)
            do {
                System.out.print(getPrompt())
                var command = console.nextLine().trim()
                processCommand(command)
            } while (command != exitCmd)
        }
        return
    } // Function exec()

    /**
     * 处理命令
     *
     * @param   command     用户在Shell输入的命令
     */
    protected open fun processCommand(command: String) {
        execCommand(command)
        return
    } // Function processCommand()

    /**
     * 获得命令行提示符
     *
     * @return  命令行提示符
     */
    open fun getPrompt(): String = "Sybatan> "

    /**
     * 如果需要网络连接执行的命令，则判断是否连接
     *
     * @return  连接到服务器返回true,否则返回false
     */
    open fun isConnected(): Boolean = true

    /**
     * 注册命令
     */
    fun registCommand(cmd: ShellCommand) {
        commandMap.put(cmd.command, cmd)
        return
    } // Function pubCommand()

    /**
     * 显示命令列晴
     *
     * @param   cmdSyntax       命令语法
     * @param   needAlign       参数是否需要对齐显示
     */
    fun dumpCommands(cmdSyntax: String, needAlign: Boolean = false) {
        // 命令语法不为空，打印命令语法
        if (!cmdSyntax.isBlank()) {
            println(cmdSyntax)
        }

        // 排序
        val commandSet = commandMap.toList()
        Collections.sort(commandSet, Comparator<Pair<String, ShellCommand>> { obj1, obj2 ->
            obj1.first .compareTo(obj2.first)
        })

        // 如果参数需要对齐
        if (needAlign) {
            // 打印
            for(cmd in commandSet) {
                var c = cmd.first
                if (c.length <= 20) {
                    var str = "                                                        "
                    println("    ${cmd.first}${str.substring(0, 24 - c.length)}${cmd.second.description}")
                } else {
                    println("    ${cmd.first}")
                    println("                         ${cmd.second.description}")
                }
            }
        } else {
            // 打印
            for(cmd in commandSet) {
                println("    ${cmd.first} ${cmd.second.description}")
            }
        }


        return
    } // Function dumpCommands()

    /**
     * 退出命令
     *
     * @param   args    命令参数
     */
    open fun cmdExit(args: Array<String>) {
        logger.debug(args)
        return
    } // Function cmdExit()

    /**
     * 执行Shell命令
     *
     * @param  command      用户在Shell输入的命令
     */
    private fun execCommand(command: String) {
        logger.debug("command = $command")
        val cmdLine = command
        // 防止空指令
        if (cmdLine.isEmpty()) {
            return
        }

        var args = cmdLine.split(" ").toTypedArray()
        var cmd = args[0]
        var shellCmd = commandMap[cmd]
        if (null != shellCmd) {
            // 如果命令需要连接到服务器，但当前状态为未连接，则打印未连接并返回
            if (shellCmd.needConnected && !isConnected()) {
                println("Not connected")
                return
            }
            shellCmd.method.invoke(this, args)
        } else {
            println("Unknow command '$cmdLine'")
        }
        return
    } // Function execCommand()

} // Class Shell