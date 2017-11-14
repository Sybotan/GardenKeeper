@echo off
REM ********************************************************************************************************************
REM
REM              iFHS7.
REM             ;BBMBMBMc                  rZMBMBR              BMB
REM             MBEr:;PBM,               7MBMMEOBB:             BBB                       RBW
REM    XK:      BO     SB.     :SZ       MBM.       c;;     ir  BBM :FFr       :SSF:    ;xBMB:r   iuGXv.    i:. iF2;
REM    DBBM0r.  :D     S7   ;XMBMB       GMBMu.     MBM:   BMB  MBMBBBMBMS   WMBMBMBBK  MBMBMBM  BMBRBMBW  .MBMBMBMBB
REM     :JMRMMD  ..    ,  1MMRM1;         ;MBMBBR:   MBM  ;MB:  BMB:   MBM. RMBr   sBMH   BM0         UMB,  BMB.  KMBv
REM    ;.   XOW  B1; :uM: 1RE,   i           .2BMBs  rMB. MBO   MBO    JMB; MBB     MBM   BBS    7MBMBOBM:  MBW   :BMc
REM    OBRJ.SEE  MRDOWOR, 3DE:7OBM       .     ;BMB   RMR7BM    BMB    MBB. BMB    ,BMR  .BBZ   MMB   rMB,  BMM   rMB7
REM    :FBRO0D0  RKXSXPR. JOKOOMPi       BMBSSWBMB;    BMBB:    MBMB0ZMBMS  .BMBOXRBMB    MBMDE RBM2;SMBM;  MBB   xBM2
REM        iZGE  O0SHSPO. uGZ7.          sBMBMBDL      :BMO     OZu:BMBK,     rRBMB0;     ,EBMB  xBMBr:ER.  RDU   :OO;
REM    ,BZ, 1D0  RPSFHXR. xWZ .SMr                  . .BBB
REM     :0BMRDG  RESSSKR. 2WOMBW;                   BMBMR
REM        i0BM: SWKHKGO  MBDv
REM          .UB  OOGDM. MK,                                          Copyright (c) 2015-2017.  北京力成恒通科技有限公司
REM             ,  XMW  ..
REM                 r                                                                     All rights reserved.
REM
REM ********************************************************************************************************************

setlocal
call "%~dp0zkEnv.cmd"
@echo on
echo %CLASSPATH%
@echo off
set MAINCLASS=com.sybotan.garden.gardenkeeper.shell.GardenKeeperShellKt
call %JAVA% -cp "%CLASSPATH%" %MAINCLASS% %*
endlocal