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

package com.sybotan.gardenkeeper.proto

import org.apache.jute.InputArchive
import org.apache.jute.OutputArchive
import org.apache.jute.Record

data class ConnectResponse(
        var protocolVersion: Int = 0,
        var timeOut: Int = 0,
        var sessionId: Long = 0,
        var passwd: ByteArray? = null
    ) : Record {

    /**
     * 序列化
     *
     * @param output    序列化输出对象
     * @param tag       序列化标签
     */
    override fun serialize(output: OutputArchive, tag: String) {
        output.startRecord(this,tag)
        output.writeInt(protocolVersion,"protocolVersion")
        output.writeInt(timeOut,"timeOut")
        output.writeLong(sessionId,"sessionId")
        output.writeBuffer(passwd,"passwd")
        output.endRecord(this,tag)
        return
    } // Function serialize()

    /**
     * 反序列化
     *
     * @param input     反序列化输入对象
     * @param tag       返序列化标签
     */
    override fun deserialize(input: InputArchive, tag: String) {
        input.startRecord(tag)
        protocolVersion = input.readInt("protocolVersion")
        timeOut         = input.readInt("timeOut")
        sessionId       = input.readLong("sessionId")
        passwd          = input.readBuffer("passwd")
        input.endRecord(tag)
        return
    } // Function deserialize()
} // Class ConnectResponse