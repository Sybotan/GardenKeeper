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

package com.sybotan.garden.gardenkeeper.server

import org.apache.logging.log4j.LogManager
import java.util.concurrent.Executors

import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.Channel
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory
import org.jboss.netty.channel.group.DefaultChannelGroup
import org.jboss.netty.channel.group.ChannelGroup
import java.net.InetSocketAddress
import java.io.IOException
import io.netty.channel.nio.NioEventLoopGroup


/**
 * @author  Andy    by 2017/11/16
 */
class NettyServerCnxnFactory {
    // 日志记录器
    private val logger = LogManager.getLogger(NettyServerCnxnFactory::class.java)
    val bootstrap: ServerBootstrap
    val bossGroup = NioEventLoopGroup()
    val workerGroup = NioEventLoopGroup()

    var parentChannel: Channel? = null
    var channelGroup: ChannelGroup = DefaultChannelGroup("gkServerCnxns")
    var channelHandler = NettyCnxnChannelHandler()
    var localAddress: InetSocketAddress? = null
    var maxClientCnxns: Int = 60

    init {
        bootstrap = ServerBootstrap(NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()))
        bootstrap.setOption("reuseAddress", true)
        bootstrap.setOption("child.tcpNoDelay", true)
        bootstrap.setOption("child.soLinger", -1)

        bootstrap.getPipeline().addLast("servercnxnfactory", channelHandler)
    }

    /**
     * 配置Server
     *
     * @param   addr            服务器地址
     * @param   maxClientCnxns  最大客户端连接数
     */
    @Throws(IOException::class)
    fun configure(addr: InetSocketAddress, maxClientCnxns: Int = 60) {
        //configureSaslLogin()
        localAddress = addr
        this.maxClientCnxns = maxClientCnxns
        return
    } // Function configure()

    /**
     * 启动服务器
     */
    fun start() {
        logger.info("binding to port " + localAddress)
        parentChannel = bootstrap.bind(localAddress)
        return
    } // Function start()

} // Class NettyServerCnxnFactory