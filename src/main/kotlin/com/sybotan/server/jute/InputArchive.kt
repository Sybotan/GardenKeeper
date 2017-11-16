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

package com.sybotan.server.jute

import java.io.IOException

/**
 * @author  Andy by 2017/11/16
 */
interface InputArchive {
    /**
     * 读取一个字节
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun readByte(tag: String): Byte

    /**
     * 读取一个Boolean类型的值
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun readBoolean(tag: String): Boolean

    /**
     * 读取一个Int类型的值
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun readInt(tag: String): Int

    /**
     * 读取一个Long类型的值
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun readLong(tag: String): Long

    /**
     * 读取一个Float类型的值
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun readFloat(tag: String): Float

    /**
     * 读取一个Double类型的值
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun readDouble(tag: String): Double

    /**
     * 读取一个字符串
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun readString(tag: String): String?

    /**
     * 读取Buffer
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun readBuffer(tag: String): ByteArray?

    /**
     * 读取记录
     *
     * @param       r       读取的记录
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun readRecord(r: Record, tag: String)

    /**
     * 开始记录
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun startRecord(tag: String)

    /**
     * 结束记录
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun endRecord(tag: String)

    /**
     * 开始Vector
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun startVector(tag: String): Index

    /**
     * 结束Vector
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun endVector(tag: String)

    /**
     * 开始Map
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun startMap(tag: String): Index

    /**
     * 结束Map
     *
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun endMap(tag: String)
} // Interface InputArchive