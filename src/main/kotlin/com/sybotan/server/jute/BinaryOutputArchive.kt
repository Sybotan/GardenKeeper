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

import java.io.DataOutput
import java.nio.ByteBuffer
import java.util.*

/**
 * @author  Andy by 2017/11/16
 */
class BinaryOutputArchive(output: DataOutput) : OutputArchive {
    private var buffer = ByteBuffer.allocate(1024)
    private val output: DataOutput

    // 初始化
    init {
        this.output = output
    } // init

    /**
     * 写一个字节
     *
     * @param       b       值
     * @param       tag     标签
     */
    override fun writeByte(b: Byte, tag: String) {
        output.writeByte(b.toInt())
        return
    } // Function writeByte()

    /**
     * 写Boolean类型的值
     *
     * @param       b       值
     * @param       tag     标签
     */
    override fun writeBoolean(b: Boolean, tag: String) {
        output.writeBoolean(b)
        return
    } // Function writeBoolean()

    /**
     * 写Int类型的值
     *
     * @param       i       值
     * @param       tag     标签
     */
    override fun writeInt(i: Int, tag: String) {
        output.writeInt(i)
        return
    } // Function writeInt()

    /**
     * 写Long类型的值
     *
     * @param       l       值
     * @param       tag     标签
     */
    override fun writeLong(l: Long, tag: String) {
        output.writeLong(l)
        return
    } // Function writeLong()

    /**
     * 写Float类型的值
     *
     * @param       f       值
     * @param       tag     标签
     */
    override fun writeFloat(f: Float, tag: String) {
        output.writeFloat(f)
        return
    } // Function writeFloat()

    /**
     * 写Double类型的值
     *
     * @param       d       值
     * @param       tag     标签
     */
    override fun writeDouble(d: Double, tag: String) {
        output.writeDouble(d)
        return
    } // Function writeDouble()

    /**
     * 写字符串
     *
     * @param       s       字符串
     * @param       tag     标签
     */
    override fun writeString(s: String?, tag: String) {
        if (s == null) {
            writeInt(-1, "len")
            return
        }
        val bb = stringToByteBuffer(s)
        writeInt(bb.remaining(), "len")
        output.write(bb.array(), bb.position(), bb.limit())
    } // Function writeString()

    /**
     * 写缓Buffer
     *
     * @param       buf     缓冲区
     * @param       tag     标签
     */
    override fun writeBuffer(buf: ByteArray, tag: String) {
        if (null == buf) {
            output.writeInt(-1)
            return
        }
        output.writeInt(buf.size)
        output.write(buf)
        return
    } // Function writeBuffer()

    /**
     * 写记录
     *
     * @param       r       记录
     * @param       tag     标签
     */
    override fun writeRecord(r: Record, tag: String) {
        r.serialize(this, tag)
        return
    } // Function writeRecord()

    /**
     * 开始记录
     *
     * @param       r       记录
     * @param       tag     标签
     */
    override fun startRecord(r: Record, tag: String) {
        // DO NOTHING
        return
    } // Function startRecord()

    /**
     * 结束记录
     *
     * @param       r       记录
     * @param       tag     标签
     */
    override fun endRecord(r: Record, tag: String) {
        // DO NOTHING
        return
    } // Function endRecord()

    /**
     * 开始Vector
     *
     * @param       v       值
     * @param       tag     标签
     */
    override fun startVector(v: List<*>, tag: String) {
        if (null == v) {
            writeInt(-1, tag)
            return
        }
        writeInt(v.size, tag)
        return
    } // Function startVector()

    /**
     * 结束Vector
     *
     * @param       v       值
     * @param       tag     标签
     */
    override fun endVector(v: List<*>, tag: String) {
        // DO NOTHING
        return
    } // Function endVector()

    /**
     * 开始Map
     *
     * @param       v       值
     * @param       tag     标签
     */
    override fun startMap(v: TreeMap<*, *>, tag: String) {
        writeInt(v.size, tag)
    } // Function startMap()

    /**
     * 结束Map
     *
     * @param       v       值
     * @param       tag     标签
     */
    override fun endMap(v: TreeMap<*, *>, tag: String) {
        // DO NOTHING
        return
    } // Function endMap()

    /**
     * 创建我们自己的字符串UTF8编码，这比string.getbytes(UTF8)要快。
     *
     * @param   s   将被编码的字符串
     * @return  UTF8编码的序列
     */
    private fun stringToByteBuffer(s: CharSequence): ByteBuffer {
        buffer.clear()
        val len = s.length
        for (i in 0 until len) {
            if (buffer.remaining() < 3) {
                val n = ByteBuffer.allocate(buffer.capacity() shl 1)
                buffer.flip()
                n.put(buffer)
                buffer = n
            }
            val c = s[i]
            if (c.toInt() < 0x80) {
                buffer.put(c.toByte())
            } else if (c.toInt() < 0x800) {
                buffer.put((0xc0 or (c.toInt() shr 6)).toByte())
                buffer.put((0x80 or (c.toInt() and 0x3f)).toByte())
            } else {
                buffer.put((0xe0 or (c.toInt() shr 12)).toByte())
                buffer.put((0x80 or (c.toInt() shr 6 and 0x3f)).toByte())
                buffer.put((0x80 or (c.toInt() and 0x3f)).toByte())
            }
        }
        buffer.flip()
        return buffer
    } // Function stringToByteBuffer()
} // Class BinaryOutputArchive