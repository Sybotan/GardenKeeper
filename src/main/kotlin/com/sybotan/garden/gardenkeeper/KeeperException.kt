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

package com.sybotan.garden.gardenkeeper

import org.apache.logging.log4j.LogManager

/**
 * 异常类
 *
 * @author  Andy by 2017/11/16
 */
open class KeeperException(code: Code, path: String = "") : Exception("") {
    // 节点路径
    val path: String

    // 初始化
    init {
        this.path = path
    } // init

    companion object {
        // 日志记录器
        private val logger = LogManager.getLogger(KeeperException::class.java)

        fun create(code: Code): KeeperException {
            when(code) {
                Code.SYSTEMERROR            -> SystemErrorException()
                Code.RUNTIMEINCONSISTENCY   -> RuntimeInconsistencyException()
                Code.DATAINCONSISTENCY      -> DataInconsistencyException()
                Code.CONNECTIONLOSS         -> ConnectionLossException()
                Code.MARSHALLINGERROR       -> MarshallingErrorException()
                Code.UNIMPLEMENTED          -> UnimplementedException()
                Code.OPERATIONTIMEOUT       -> OperationTimeoutException()
                Code.BADARGUMENTS           -> BadArgumentsException("")
                Code.APIERROR               -> APIErrorException()
                Code.NONODE                 -> NoNodeException("")
                Code.NOAUTH                 -> NoAuthException()
                Code.BADVERSION             -> BadVersionException("")
                Code.NOCHILDRENFOREPHEMERALS-> NoChildrenForEphemeralsException("")
                Code.NODEEXISTS             -> NodeExistsException("")
                Code.NOTEMPTY               -> NotEmptyException("")
                Code.SESSIONEXPIRED         -> SessionExpiredException()
                Code.INVALIDCALLBACK        -> InvalidCallbackException()
                Code.INVALIDACL             -> InvalidACLException("")
                Code.AUTHFAILED             -> AuthFaildException()
                Code.SESSIONMOVED           -> SessionMovedException()
                Code.NOTREADONLY            -> NotReadOnlyException()
            }
            throw IllegalArgumentException("Invalid exception code")
        } // Function create()
    } // companion object


    enum class Code(val value: Int) {
        OK                      (   0),
        SYSTEMERROR             (  -1),
        RUNTIMEINCONSISTENCY    (  -2),
        DATAINCONSISTENCY       (  -3),
        CONNECTIONLOSS          (  -4),
        MARSHALLINGERROR        (  -5),
        UNIMPLEMENTED           (  -6),
        OPERATIONTIMEOUT        (  -7),
        BADARGUMENTS            (  -8),
        APIERROR                (-100),
        NONODE                  (-101),
        NOAUTH                  (-102),
        BADVERSION              (-103),
        NOCHILDRENFOREPHEMERALS (-108),
        NODEEXISTS              (-110),
        NOTEMPTY                (-111),
        SESSIONEXPIRED          (-112),
        INVALIDCALLBACK         (-113),
        INVALIDACL              (-114),
        AUTHFAILED              (-115),
        SESSIONMOVED            (-118),
        NOTREADONLY             (-119);
    } // enum Code

    class APIErrorException                             : KeeperException(Code.APIERROR)
    class AuthFaildException                            : KeeperException(Code.AUTHFAILED)
    class BadArgumentsException(path: String)           : KeeperException(Code.BADARGUMENTS, path)
    class BadVersionException(path: String)             : KeeperException(Code.BADVERSION, path)
    class ConnectionLossException                       : KeeperException(Code.CONNECTIONLOSS)
    class DataInconsistencyException                    : KeeperException(Code.DATAINCONSISTENCY)
    class InvalidACLException(path: String)             : KeeperException(Code.INVALIDACL, path)
    class InvalidCallbackException                      : KeeperException(Code.INVALIDCALLBACK)
    class MarshallingErrorException                     : KeeperException(Code.MARSHALLINGERROR)
    class NoAuthException                               : KeeperException(Code.NOAUTH)
    class NoChildrenForEphemeralsException(path:String) : KeeperException(Code.NOCHILDRENFOREPHEMERALS, path)
    class NoNodeException(path: String)                 : KeeperException(Code.NONODE, path)
    class NodeExistsException(path: String)             : KeeperException(Code.NODEEXISTS, path)
    class NotEmptyException(path: String)               : KeeperException(Code.NOTEMPTY, path)
    class NotReadOnlyException                          : KeeperException(Code.NOTREADONLY)
    class OperationTimeoutException                     : KeeperException(Code.OPERATIONTIMEOUT)
    class RuntimeInconsistencyException                 : KeeperException(Code.RUNTIMEINCONSISTENCY)
    class SessionExpiredException                       : KeeperException(Code.SESSIONEXPIRED)
    class SessionMovedException                         : KeeperException(Code.SESSIONMOVED)
    class SystemErrorException                          : KeeperException(Code.SYSTEMERROR)
    class UnimplementedException            : KeeperException(Code.AUTHFAILED)
} // Class KeeperException