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
import java.util.*

/**
 * @author  Andy by 2017/11/16
 */
interface OutputArchive {
    /**
     * 写一个字节
     *
     * @param       b       值
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun writeByte(b: Byte, tag: String)

    /**
     * 写Boolean类型的值
     *
     * @param       b       值
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun writeBoolean(b: Boolean, tag: String)

    /**
     * 写Int类型的值
     *
     * @param       i       值
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun writeInt(i: Int, tag: String)

    /**
     * 写Long类型的值
     *
     * @param       l       值
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun writeLong(l: Long, tag: String)

    /**
     * 写Float类型的值
     *
     * @param       f       值
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun writeFloat(f: Float, tag: String)

    /**
     * 写Double类型的值
     *
     * @param       d       值
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun writeDouble(d: Double, tag: String)

    /**
     * 写字符串
     *
     * @param       s       字符串
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun writeString(s: String?, tag: String)

    /**
     * 写缓Buffer
     *
     * @param       buf     缓冲区
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun writeBuffer(buf: ByteArray, tag: String)

    /**
     * 写记录
     *
     * @param       r       记录
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun writeRecord(r: Record, tag: String)

    /**
     * 开始记录
     *
     * @param       r       记录
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun startRecord(r: Record, tag: String)

    /**
     * 结束记录
     *
     * @param       r       记录
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun endRecord(r: Record, tag: String)

    /**
     * 开始Vector
     *
     * @param       v       值
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun startVector(v: List<*>, tag: String)

    /**
     * 结束Vector
     *
     * @param       v       值
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun endVector(v: List<*>, tag: String)

    /**
     * 开始Map
     *
     * @param       v       值
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun startMap(v: TreeMap<*, *>, tag: String)

    /**
     * 结束Map
     *
     * @param       v       值
     * @param       tag     标签
     */
    @Throws(IOException::class)
    abstract fun endMap(v: TreeMap<*, *>, tag: String)
} // Interface OutputArchive