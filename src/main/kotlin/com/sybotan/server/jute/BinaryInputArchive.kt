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

import java.io.DataInput
import java.io.IOException
import java.nio.charset.Charset

/**
 * @author  Andy by 2017/11/16
 */
class BinaryInputArchive(input: DataInput) : InputArchive {
    val input: DataInput
    // 最大缓冲区大小，默认4M
    val maxBuffer = Integer.getInteger("jute.maxbuffer", 0xfffff)!!

    init {
        this.input = input
    }

    /**
     * 读取一个字节
     *
     * @param       tag     标签
     */
    override fun readByte(tag: String): Byte {
        return input.readByte()
    } // Function readByte()

    /**
     * 读取一个Boolean类型的值
     *
     * @param       tag     标签
     */
    override fun readBoolean(tag: String): Boolean {
        return input.readBoolean()
    } // Function readBoolean()

    /**
     * 读取一个Int类型的值
     *
     * @param       tag     标签
     */
    override fun readInt(tag: String): Int {
        return input.readInt()
    } // Function readInt()

    /**
     * 读取一个Long类型的值
     *
     * @param       tag     标签
     */
    override fun readLong(tag: String): Long {
        return input.readLong()
    } // Function readLong()

    /**
     * 读取一个Float类型的值
     *
     * @param       tag     标签
     */
    override fun readFloat(tag: String): Float {
        return input.readFloat()
    } // Function readFloat()

    /**
     * 读取一个Double类型的值
     *
     * @param       tag     标签
     */
    override fun readDouble(tag: String): Double {
        return input.readDouble()
    } // Function readDouble()

    /**
     * 读取一个字符串
     *
     * @param       tag     标签
     */
    override fun readString(tag: String): String? {
        val len = input.readInt()
        if (len == -1) {
            return null
        }

        checkLength(len)
        val b = ByteArray(len)
        input.readFully(b)
        return b.toString(Charset.forName("UTF-8"))
    } // Function readString()

    /**
     * 读取Buffer
     *
     * @param       tag     标签
     */
    override fun readBuffer(tag: String): ByteArray? {
        val len = readInt(tag)
        if (len == -1) {
            return null
        }

        checkLength(len)
        val buf = ByteArray(len)
        input.readFully(buf)
        return buf
    } // Function readBuffer()

    /**
     * 读取记录
     *
     * @param       r       读取的记录
     * @param       tag     标签
     */
    override fun readRecord(r: Record, tag: String) {
        r.deserialize(this, tag)
        return
    } // Function readRecord()

    /**
     * 开始记录
     *
     * @param       tag     标签
     */
    override fun startRecord(tag: String) {
        // DO NOTHING
        return
    } // Function startRecord()

    /**
     * 结束记录
     *
     * @param       tag     标签
     */
    override fun endRecord(tag: String) {
        // DO NOTHING
        return
    } // Function endRecord()

    /**
     * 开始Vector
     *
     * @param       tag     标签
     */
    override fun startVector(tag: String): Index {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    } // Function startVector()

    /**
     * 结束Vector
     *
     * @param       tag     标签
     */
    override fun endVector(tag: String) {
        // DO NOTHING
        return
    } // Function endVector()

    /**
     * 开始Map
     *
     * @param       tag     标签
     */
    override fun startMap(tag: String): Index {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    } // Function startMap()

    /**
     * 结束Map
     *
     * @param       tag     标签
     */
    override fun endMap(tag: String) {
        // DO NOTHING
        return
    } // Function endMap()

    /**
     * 审查长度的合法性
     *
     * @param   len     被审查的长度
     */
    @Throws(IOException::class)
    private fun checkLength(len: Int) {
        if (len < 0 || len > maxBuffer + 1024) {
            throw IOException("Unreasonable length = $len")
        }
        return
    } // Function checkLength()
} // Class BinaryInputArchive