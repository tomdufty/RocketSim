/*
                  Interactive Shock Wave Program

     Program to perform two dimensional analysis of supersonic flow
        past a wedge including oblique and normal shock conditions

                     Version 1.3a   - 18 July 06

                              Written by 

                               Tom Benson
                              Chuck Trefny
                              Charles Lamp
                                
                       NASA Glenn Research Center

>                              NOTICE
>This software is in the Public Domain.  It may be freely copied and used in
>non-commercial products, assuming proper credit to the author is given.  IT
>MAY NOT BE RESOLD.  If you want to use the software for commercial
>products, contact the author.
>No copyright is claimed in the United States under Title 17, U. S. Code.
>This software is provided "as is" without any warranty of any kind, either
>express, implied, or statutory, including, but not limited to, any warranty
>that the software will conform to specifications, any implied warranties of
>merchantability, fitness for a particular purpose, and freedom from
>infringement, and any warranty that the documentation will conform to the
>program, or any warranty that the software will be error free.
>In no event shall NASA be liable for any damages, including, but not
>limited to direct, indirect, special or consequential damages, arising out
>of, resulting from, or in any way connected with this software, whether or
>not based on warranty, contract, tort or otherwise, whether or not injury
>was sustained by persons or property or otherwise, and whether or not loss
>was sustained from, or arose out of the results of, or use of, the software
>or services provided hereunder.

      New Test : 
                 * change output box colors
                 * add planets
                 * enlarge display for the portal
                 * expand range for input variables for hypersonic site
                       M=20
                 * include real gas effects
                      calorically imperfect 
                   include shock standoff (Moeckle method?)
                   
                                                     TJB 18 July 06
*/

import java.awt.*;
import java.lang.Math ;

public class Shock extends java.applet.Applet {

   final double convdr = 3.14515926/180.;
   double gama, mach0, ang1, ang2, sepval ;
   double ang1mn, ang1rng, machlo, machhi ;
   double xlong, typeinpt, altmin, altmax ;
   double tempmin, tempmax, alt, gm1, gp1, rgas;
   int itype;
   double machrat, angrat, trrat, prrat, drrat, tprrat;
   double M, M2, THETA, TR, PR, TPR, DR;
   double TT, T, G, Q, DELG, TEST;
   boolean Normal;

   static double angr, delmax, dist, gamma ;
   int prob, nramps, nshocks, nslip, planet ;
              //  flow parameters
   static double[] turning = new double[15] ;
   static double[] mach1   = new double[15] ;
   static double[] mach2   = new double[15] ;
   static double[] prat    = new double[15] ;
   static double[] trat    = new double[15] ;
   static double[] ptrat   = new double[15] ;
   static double[] rhorat  = new double[15] ;
   static double[] defl    = new double[15] ;
   static double[] shkang  = new double[15] ;
   static double[] pspo    = new double[15] ;
   static double[] tsto    = new double[15] ;
   static double[] ptpto   = new double[15] ;
   static double[] rsro    = new double[15] ;
   static boolean[] detach = new boolean[15] ;
              //  wedge geometry
   static double[] ang = new double[3] ;
   static int[] wfamily = new int[3] ;
   static double[] winter = new double[3] ;
   static double[] wxbgn = new double[3] ;
   static double[] wxnd = new double[3] ;
   static double[] wybgn = new double[3] ;
   static double[] wynd = new double[3] ;
   static double[] wslope = new double[3] ;
              // shock geometry
   static double[] sang = new double[15] ;
   static int[] sfamily = new int[15] ;
   static double[] sinter = new double[15] ;
   static double[] sxbgn = new double[15] ;
   static double[] sxnd = new double[15] ;
   static double[] sybgn = new double[15] ;
   static double[] synd = new double[15] ;
   static double[] sslope = new double[15] ;
              // expansion geometry
   static double[] expang = new double[15] ;
   static int[] efamily = new int[15] ;
   static double[] einter = new double[15] ;
   static double[] exbgn = new double[15] ;
   static double[] exnd = new double[15] ;
   static double[] eybgn = new double[15] ;
   static double[] eynd = new double[15] ;
   static double[] eslope = new double[15] ;
              // slip line geometry
   static double[] slinter = new double[7] ;
   static double[] slxbgn = new double[7] ;
   static double[] slxnd = new double[7] ;
   static double[] slybgn = new double[7] ;
   static double[] slynd = new double[7] ;
   static double[] slslope = new double[7] ;

   Viewer view ;
   Dwn dwn ;
   Image offscreenImg ;
   Graphics offsGg ;

   public void init() {
     boolean which = false ;
     Shock a = new Shock() ;
 
     offscreenImg = createImage(this.size().width,
                      this.size().height) ;
     offsGg = offscreenImg.getGraphics() ;
 
     setLayout(new GridLayout(2,1,0,0)) ;

     setDefaults () ;

     a.mach0 = 2.0 ;
     a.ang1  = 10.0 ;
     a.gama  = 1.4 ;
     prob = 0 ;
 
     view = new Viewer(this) ;
     dwn = new Dwn(this) ;

     add(view) ;
     add(dwn) ;

     comPute(a.mach0, a.ang1) ;
  }
 
  public Insets insets() {
     return new Insets(10,10,10,10) ;
  }

   public void setDefaults() {
     nslip = 0 ;
     nramps = 1 ;
     nshocks = 1 ;
     xlong = 350. ;

     planet = 0 ;
     ang1mn = 5.0 ;
     ang1rng = 25.0 ;
     machlo = 1.0 ;
     machhi = 20.0 ;
     M = mach0 = 2.0 ;
     DELG = ang1 = 10.0 ;
     G = gamma  = 1.4 ;
     Q = 5500;
     altmin = 0;
     altmax = 262448;
     tempmin = 500;
     tempmax = 5000;
	 itype = 0;

     dist = 100. ; 
     ang[0]  = 0.0 ;
     prat[0] = 1.0 ;
     trat[0] = 1.0 ;
     ptrat[0] = 1.0 ;
     rhorat[0] = 1.0 ;
     pspo[0] = 1.0 ;
     tsto[0] = 1.0 ;
     rsro[0] = 1.0 ;
     ptpto[0]= 1.0 ;
     defl[0] = 0.0 ;
     turning[0] = 0.0 ;
     shkang[0] = 0.0 ;
   }

   double TQTT(double M, double G)
   {
       return Math.pow((1 + (G - 1) / 2 * Math.pow(M, 2)), -1);
   }

   double CAL_GAM (double T, double G, double Q)
   {
      return (1 + (G - 1) / (1 + (G - 1) * (Math.pow((Q / T), 2) * Math.exp(Q / T) 
          / Math.pow((Math.exp(Q / T) - 1), 2))));
   }

   double CAL_TT (double T, double M, double G, double Q)
   {
      double TT = T / TQTT(M,G);
      double EPS = 0.00001;
      double Z = 1;
      double Z2 = 0;
      double TT2 = 0;
      double TT1;
      double Z1;
      double i = 1;
      while (Math.abs(Z) > EPS)
      {
         Z = Math.pow(M, 2) - 2 * TT / CAL_GAM(T, G, Q) / T * 
               (G / (G - 1) * (1 - T / TT) + Q / TT *  
               (1 / (Math.exp(Q / TT) - 1) -1 / (Math.exp(Q / T) - 1)));
         if (i == 1)
         {
            Z2=Z;
            TT2 = TT;
            TT = TT2 + 100;
            i = 2;
         }
         else
         {
            TT1 = TT2;
            Z1 = Z2;
            TT2 = TT;
            Z2 = Z;
            TT = TT2 - Z2 * (TT2 - TT1) / (Z2 - Z1);
         }
       }
       return TT;
   }

   double CAL_T (double TT, double M, double G, double Q)
   {
       double T = TT * TQTT(M,G);
       double EPS = 0.00001;
       double Z=1;
       double Z2 = 0;
       double T2 = 0;
       double T1;
       double Z1;
       double i=1;
       while (Math.abs(Z) > EPS)
       {
            Z = Math.pow(M, 2) - 2 * TT / CAL_GAM(T, G, Q) / T * 
            (G / (G - 1) * (1 - T / TT) + Q / TT * 
            (1 / (Math.exp(Q / TT) - 1) - 1 / (Math.exp(Q / T) - 1)));
            if (i==1)
            {
                Z2 = Z;
                T2 = T;
                T = T2 + 100;
                i=2;
            }
            else 
            {
                T1 = T2;
                Z1 = Z2;
                T2 = T;
                Z2 = Z;
                T = T2 - Z2 * (T2 - T1) / (Z2 - Z1);
            }
       }
       return T;
    }

    double CAL_PQPT (double TT, double M, double G, double Q)
    {
       double T = CAL_T(TT, M, G, Q);
       return (((Math.exp(Q / TT) - 1) / (Math.exp(Q / T) - 1)) * Math.pow((T / TT), 
                (G / (G - 1))) * Math.exp(Q / T * Math.exp(Q / T) / 
                (Math.exp(Q / T) - 1) - Q / TT * Math.exp(Q / TT) / 
                (Math.exp(Q / TT) - 1)));
    }

    double CAL_WAVE (double TT, double M, double DELG, double G, double Q, char PAR)
    {
       double T = CAL_T(TT, M, G, Q);
       double Pi = Pi = 4 * Math.atan(1);
       double TR = 1;
       double i = 0;
       double EPS = 1;
       double EPS1 = 0;
       double EPS2;
       double TR1 = 0;
       double TR2;
       while (Math.abs(EPS) > .0001)
       {
           EPS = 1 - 2 / TR / CAL_GAM(T * TR, G, Q) * (CAL_GAM(T, G, Q) * 
                Math.pow(M, 2) / 2 + G / (G - 1) * (1 - TR) + Q / T * 
                (1 / (Math.exp(Q / T) - 1) - 1 / (Math.exp(Q / TR / T) - 1)));
           if (i==0)
           {
              EPS1 = EPS;
              TR1 = TR;
              TR = 2;
              i = 1;
           }
           else
           {
              EPS2 = EPS1;
              EPS1 = EPS;
              TR2 = TR1;
              TR1 = TR;
              TR = TR1 - EPS1 * (TR2 - TR1) / (EPS2 - EPS1);
           }
       }

       double P1P2 = (1 + CAL_GAM(T * TR, G, Q) * 1 - 
               (1 + CAL_GAM(T, G, Q) * Math.pow(M, 2)) / TR + 
                Math.sqrt(Math.pow((1 + CAL_GAM(T * TR, G, Q) * 
                1 - (1 + CAL_GAM(T, G, Q) * Math.pow(M, 2)) / TR), 2) + 4 / TR)) / 2;
       double SIN2TH = (CAL_GAM(T * TR, G, Q) / CAL_GAM(T, G, Q) * TR / 
               Math.pow(M, 2) - 1) / (Math.pow(P1P2, 2) * Math.pow(TR, 2) - 1);
       double COTDEL = Math.sqrt(SIN2TH / (1 - SIN2TH)) * 
             (CAL_GAM(T, G, Q) * Math.pow(M, 2) / (1 / P1P2 - 1) - 1);
       double DELMAX = Math.atan(1 / COTDEL) * 180 / Pi;
       double TRMAX = TR;
       TR = 1.01;
       double M22 = 0;
       double DEL;
       double Z = 1;
       double Z1 = 0;
       double Z2;
       i = 0;
       while (Math.abs(Z) > .0001)
       {
           M22 = 2 / TR / CAL_GAM(T * TR, G, Q) * (CAL_GAM(T, G, Q) * 
               Math.pow(M, 2) / 2 + G / (G - 1) * (1 - TR) + Q / T * 
              (1 / (Math.exp(Q / T) - 1) - 1 / (Math.exp(Q / TR / T) - 1)));
           P1P2 = (1 + CAL_GAM(T * TR, G, Q) * M22 - (1 + CAL_GAM(T, G, Q) * 
               Math.pow(M, 2)) / TR + Math.sqrt(Math.pow((1 + CAL_GAM(T * TR, G, Q) * 
               M22 - (1 + CAL_GAM(T, G, Q) * Math.pow(M, 2)) / TR), 2) + 4 / TR)) / 2;
           SIN2TH = (CAL_GAM(T * TR, G, Q) / CAL_GAM(T, G, Q) * TR * M22 / 
               Math.pow(M, 2) - 1) / (Math.pow(P1P2, 2) * Math.pow(TR, 2) - 1);
           COTDEL = Math.sqrt(SIN2TH / (1 - SIN2TH)) * (CAL_GAM(T, G, Q) * 
               Math.pow(M, 2) / (1 / P1P2 - 1) - 1);
           DEL = Math.atan(1 / COTDEL) * 180 / Pi;
           Z = (DEL - DELG) / DELG;
           if (i==0)
           {
              Z1 = Z;
              TR1 = TR;
              TR = 0.99 * TRMAX;
              i = 1;
           }
           else
           {
              TR2 = TR;
              Z2 = Z;
              TR = TR1 - Z1 * (TR2 - TR1) / (Z2 - Z1);
           }
       }
       if (PAR == 'A') // Theta
       {
           return 180 / Pi * Math.atan(Math.sqrt(1 / (1 / SIN2TH - 1)));
       }
       if (PAR == 'M') //M2
       {
           return Math.sqrt(M22);
       }
       if (PAR == 'T') //TR
       {
          return TR;
       }
       if (PAR == 'P') //PR
       {
          return 1 / P1P2;
       }
       if (PAR == 'D') //DR
       {
          return 1 / TR / P1P2;
       }
       if (PAR == 'R') //TPR
       {
          return CAL_PQPT(TT, M, G, Q) / CAL_PQPT(TT, Math.sqrt(M22), G, Q) / P1P2;
       }
       else 
       {
          return 999;
       }
    }

    double CAL_WAVE_TEST (double TT, double M, double DELG, double G, double Q, char PAR)
    {
       double T = CAL_T(TT, M, G, Q);
       double Pi = Pi = 4 * Math.atan(1);
       double TR = 1;
       double i = 0;
       double EPS = 1;
       double EPS1 = 0;
       double EPS2;
       double TR1 = 0;
       double TR2;
       while (Math.abs(EPS) > .0001)
       {
           EPS = 1 - 2 / TR / CAL_GAM(T * TR, G, Q) * (CAL_GAM(T, G, Q) *
           Math.pow(M, 2) / 2 + G / (G - 1) * (1 - TR) + Q / T * 
           (1 / (Math.exp(Q / T) - 1) - 1 / (Math.exp(Q / TR / T) - 1)));
           if (i==0)
           {
              EPS1 = EPS;
              TR1 = TR;
              TR = 2;
              i = 1;
           }
           else
           {
              EPS2 = EPS1;
              EPS1 = EPS;
              TR2 = TR1;
              TR1 = TR;
              TR = TR1 - EPS1 * (TR2 - TR1) / (EPS2 - EPS1);
           }
        }
        double P1P2 = (1 + CAL_GAM(T * TR, G, Q) * 1 - 
           (1 + CAL_GAM(T, G, Q) * Math.pow(M, 2)) / TR + 
            Math.sqrt(Math.pow((1 + CAL_GAM(T * TR, G, Q) * 1 - 
            (1 + CAL_GAM(T, G, Q) * Math.pow(M, 2)) / TR), 2) + 4 / TR)) / 2;
        double SIN2TH = (CAL_GAM(T * TR, G, Q) / CAL_GAM(T, G, Q) * TR / 
           Math.pow(M, 2) - 1) / (Math.pow(P1P2, 2) * Math.pow(TR, 2) - 1);
        double COTDEL = Math.sqrt(SIN2TH / (1 - SIN2TH)) * 
           (CAL_GAM(T, G, Q) * Math.pow(M, 2) / (1 / P1P2 - 1) - 1);
        double DELMAX = Math.atan(1 / COTDEL) * 180 / Pi;
        double TRMAX = TR;
        TR = 1.01;
        double M22 = 0;
        double DEL;
        double Z = 1;
        double Z1 = 0;
        double Z2;
        i = 0;
        while (Math.abs(Z) > .0001)
        {
             M22 = 2 / TR / CAL_GAM(T * TR, G, Q) * (CAL_GAM(T, G, Q) * 
               Math.pow(M, 2) / 2 + G / (G - 1) * (1 - TR) + Q / T * 
               (1 / (Math.exp(Q / T) - 1) - 1 / (Math.exp(Q / TR / T) - 1)));
             P1P2 = (1 + CAL_GAM(T * TR, G, Q) * M22 - (1 + CAL_GAM(T, G, Q) * 
               Math.pow(M, 2)) / TR + Math.sqrt(Math.pow((1 + 
               CAL_GAM(T * TR, G, Q) * M22 - (1 + CAL_GAM(T, G, Q) * 
               Math.pow(M, 2)) / TR), 2) + 4 / TR)) / 2;
             SIN2TH = (CAL_GAM(T * TR, G, Q) / CAL_GAM(T, G, Q) * TR * M22 / 
               Math.pow(M, 2) - 1) / (Math.pow(P1P2, 2) * Math.pow(TR, 2) - 1);
             COTDEL = Math.sqrt(SIN2TH / (1 - SIN2TH)) * (CAL_GAM(T, G, Q) * 
               Math.pow(M, 2) / (1 / P1P2 - 1) - 1);
             DEL = Math.atan(1 / COTDEL) * 180 / Pi;
             Z = (DEL - DELG) / DELG;
             if (i==0)
             {
                Z1 = Z;
                TR1 = TR;
                TR = 0.99 * TRMAX;
                i = 1;
             }
             else
             {
                TR2 = TR;
                Z2 = Z;
                TR = TR1 - Z1 * (TR2 - TR1) / (Z2 - Z1);
             }
        }
        double TESTTHETA = 180 / Pi * Math.atan(Math.sqrt(1 / (1 / SIN2TH - 1)));
        return TESTTHETA;
   }

   double CAL_TNS (double TT, double M, double G, double Q)
   {
       double T = CAL_T(TT, M, G, Q);
       double TR = (2 * G * Math.pow(M, 2) - (G - 1)) * ((G - 1) * 
         Math.pow(M, 2) + 2) / Math.pow((G + 1), 2) / Math.pow(M, 2);
       double EPS = 0.00001;
       double Z = 1;
       double Z2 = 0;
       double TR2 = 0;
       double TR1;
       double Z1;
       int i=0;
       while (Math.abs(Z) > EPS)
       {
          if (i==0)
          {
            Z2=Z;
            TR2=TR;
            TR=0.98*TR2;
            i=1;
          }
          else
          {
            TR1=TR2;
            Z1=Z2;
            TR2=TR;
            Z2=Z;
            TR=TR2 - Z2 * (TR2 - TR1) / (Z2 - Z1);
          }
          Z = Math.pow((Math.pow((1 + 1 / CAL_GAM(T, G, Q) / Math.pow(M, 2)), 2) - 
            2 / CAL_GAM(T, G, Q) / Math.pow(M, 2) * TR - 2 + G / (G - 1) * 
            (TR - 1) * 4 / CAL_GAM(T, G, Q) / Math.pow(M, 2) + 4 / CAL_GAM(T, G, Q) / 
            Math.pow(M, 2) * Q / T * (1 / (Math.exp(Q / T / TR) - 1) - 1 / 
            (Math.exp(Q / T) - 1))), 2) - (Math.pow((1 + 1 / CAL_GAM(T, G, Q) / 
             Math.pow(M, 2)), 2) * (Math.pow((1 + 1 / CAL_GAM(T, G, Q) / 
             Math.pow(M, 2)), 2) - 4 / CAL_GAM(T, G, Q) / Math.pow(M, 2) * TR));
       }
       return TR;
   }

   double CAL_DNS (double TT, double M, double G, double Q)
   {
       double T = CAL_T(TT, M, G, Q);
       double VRAT2 = 1 - 2 * G / (G - 1) / CAL_GAM(T, G, Q) / 
           Math.pow(M, 2) * (CAL_TNS(TT, M, G, Q) - 1) - 2 * (Q / T) / 
           CAL_GAM(T, G, Q) / Math.pow(M, 2) * (1 / 
           (Math.exp(Q / T / CAL_TNS(TT, M, G, Q)) - 1) - 1 / (Math.exp(Q / T) - 1));
       return (1 / Math.sqrt(VRAT2));
   }

   double CAL_PNS (double TT, double M, double G, double Q)
   {
       return (CAL_DNS(TT, M, G, Q) * CAL_TNS(TT, M, G, Q));
   }

   double CAL_MNS (double TT, double M, double G, double Q)
   {
       double T = CAL_T(TT, M, G, Q);
       double T2 = T * CAL_TNS(TT, M, G, Q);
       double VRAT2 = 1 - 2 * G / (G - 1) / CAL_GAM(T, G, Q) / 
           Math.pow(M, 2) * (CAL_TNS(TT, M, G, Q) - 1) - 2 * (Q / T) / 
           CAL_GAM(T, G, Q) / Math.pow(M, 2) * (1 / (Math.exp(Q / T / 
           CAL_TNS(TT, M, G, Q)) - 1) - 1 / (Math.exp(Q / T) - 1));
           return (M * Math.sqrt(VRAT2) * Math.sqrt(T / T2 * 
             CAL_GAM(T, G, Q) / CAL_GAM(T2, G, Q)));
   }

   double CAL_PTNS (double TT, double M, double G, double Q)
   {
       return (CAL_PQPT(TT, M, G, Q) / CAL_PQPT(TT, CAL_MNS(TT, M, G, Q), G, Q) * 
           CAL_PNS(TT, M, G, Q));
   }

   public void comPute(double machin, double del1) {

       M = mach2[0] = mach1[0] = machin;
       DELG = ang[1] = del1 ;

       if (itype == 0)
       {
	   alt = typeinpt;
	   gm1 = gama -1.0 ;
	   gp1 = gama +1.0 ;
	   rgas = 1718. ;                /* ft2/sec2 R */
	   if (alt <= 36152.) 
	   {           // Troposphere
               T = 518.6 - 3.56 * alt/1000. ;
	   }
	   if (alt >= 36152. && alt <= 82345.) 
	   {   // Stratosphere
               T = 389.98 ;
	   }
	   if (alt >= 82345. && alt <= 155348.) 
	   {          
               T = 389.98 + 1.645 * (alt-82345.)/1000. ;
	   }
	   if (alt >= 155348. && alt <= 175346.) 
	   {          
               T = 508.788 ;
	   }
	   if (alt >= 175346. && alt <= 262448.) 
	   {          
               T = 508.788 - 2.46888 * (alt-175346.)/1000. ;
           }
           TT = CAL_TT (T, M, G, Q);
       }
       if (itype == 1)
       {
	   TT = typeinpt;
       }

       getGeom () ;
 
       loadZero() ;
 
       anlSing() ;

       TEST = CAL_WAVE_TEST (TT, M, DELG, G, Q, 'F');
       if (TEST > 0 && TEST < 90)
       {
           Normal = false;
       }
       else
       {
	   Normal = true;
       }

       if (Normal == true)
       {
          M2 = CAL_MNS (TT, M, G, Q);
          THETA = 90;
          TR = CAL_TNS (TT, M, G, Q);
          PR = CAL_PNS (TT, M, G, Q);
          DR = CAL_DNS (TT, M, G, Q);
          TPR = CAL_PTNS (TT, M, G, Q);
       }

       if (Normal == false)
       {
          M2 = CAL_WAVE (TT, M, DELG, G, Q, 'M');
          THETA = CAL_WAVE (TT, M, DELG, G, Q, 'A');
          TR = CAL_WAVE (TT, M, DELG, G, Q, 'T');
          PR = CAL_WAVE (TT, M, DELG, G, Q, 'P');
          DR = CAL_WAVE (TT, M, DELG, G, Q, 'D');
          TPR = CAL_WAVE (TT, M, DELG, G, Q, 'R');
       }

       machrat = M2 / mach2[1];
       angrat = THETA / shkang[1];
       trrat = TR / trat[1];
       prrat = PR / prat[1];
       drrat = DR / rhorat[1];
       tprrat = TPR / ptrat[1];

       loadOut() ;
 
       view.repaint();
   }

   public void loadZero() {
       int i ;

       nslip = 0 ; 
       for(i=1; i<=14; ++i) {
            pspo[i] = 0.0 ;
            tsto[i] = 0.0 ;
            rsro[i] = 0.0 ;
            ptpto[i] = 0.0 ;
            prat[i] = 0.0 ;
            trat[i] = 0.0 ;
            rhorat[i] = 0.0 ;
            ptrat[i] = 0.0 ;
            turning[i] = 0.0 ;
            defl[i] = 0.0 ;
            shkang[i] = 0.0 ;
            mach2[i] = 0.0 ;
            detach[i] = false ;
            getGeomOblq(300.0,300.0,1,i) ;
       }
   }

   public void anlSing() {     //  Analysis for Single  Wedge.

       mach1[1] = mach2[0] ;
       nshocks = 1 ;
       nramps = 1 ;
       angr  = ang[1] * convdr ;
       detach[1] = false ;

       getAnglim(mach1[1],gamma) ;

       if (ang[1] > delmax) {
	     Normal = true;
         getNorm(mach1[1],gamma,0,1) ;
         getGeomNorm(wxbgn[1],wybgn[1],xlong,wfamily[1],1) ;
         turning[1] = ang[1] ;
       }
       else {
	     Normal = false;
         shkang[1]  = getShkang(mach1[1],angr,gamma) ;
         getOblq(mach1[1],shkang[1],gamma,0,1) ;
         turning[1] = turning[0] + defl[1] ;
         sang[1] = turning[0] + shkang[1] ;
         getGeomOblq(wxbgn[1],wybgn[1],wfamily[1],1) ;
       }
       return ;
   }

   public void getGeom () {
          // wedge geometry
       int i ;
         wfamily[1] = 1 ;
         wslope[1]  = Math.tan(convdr * ang[1]) ;
         wxbgn[1]   = 50. ;
         wybgn[1]   = 0.0 ;
         winter[1]  = wybgn[1] - wslope[1] * wxbgn[1] ;

         wfamily[2] = 1 ;
         wslope[2]  = Math.tan(convdr * (ang[1] + ang[2])) ;
         wxbgn[2]   = wxbgn[1] + dist ;
         wybgn[2]   = wxbgn[2] * wslope[1] + winter[1];
         winter[2]  = wybgn[2] - wslope[2] * wxbgn[2] ;
         wxnd[2]    = xlong ;
         wynd[2]    = wxnd[2] * wslope[2] + winter[2] ;

         wxnd[1]    = wxbgn[2] ;
         wynd[1]    = wybgn[2] ;
 
       return;
   }

   public void getGeomOblq (double xi, double yi, int fam, int index) {
          // oblique shock geometry
       sfamily[index] = fam ;
       sslope[index]  = fam*Math.tan(convdr * sang[index]) ;
       sxbgn[index]   = xi ;
       sybgn[index]   = yi ;
       sinter[index]  = yi - sslope[index] * xi ;
       sxnd[index]    = xlong ;
       synd[index]    = sxnd[index]*sslope[index] + sinter[index] ;
       return;
   }

   public void getGeomNorm (double xi, double yi, double ye,
                             int fam, int index) {
          // normal shock geometry
       sfamily[index] = fam ;
       sslope[index]  = 0.0 ;
       sxbgn[index]   = xi ;
       sybgn[index]   = yi ;
       sinter[index]  = yi ;
       sxnd[index]    = xi ;
       synd[index]    = ye ;
       return;
   }

   public void loadOut() {
      int outzn ;

      outzn = 1;

      dwn.num2.out.o1.setText(String.valueOf(filter3(mach2[outzn]))) ;
      dwn.num2.out.o3.setText(String.valueOf(filter3(shkang[outzn])));
      dwn.num2.out.o2.setText(String.valueOf(filter3(prat[outzn]))) ;
      dwn.num2.out.o4.setText(String.valueOf(filter3(ptrat[outzn]))) ;
      dwn.num2.out.o6.setText(String.valueOf(filter3(trat[outzn]))) ;
	  dwn.num2.out.o7.setText(String.valueOf(filter3(rhorat[outzn])));
	   if (detach[1])
	   {
		   dwn.num2.out.o8.setText(String.valueOf("Yes"));
	   }
	   else
	   {
		   dwn.num2.out.o8.setText(String.valueOf("No"));
	   }

	  dwn.num2.out.tp1.setText(String.valueOf(filter3(M2)));
	  dwn.num2.out.tp2.setText(String.valueOf(filter3(PR)));
	  dwn.num2.out.tp3.setText(String.valueOf(filter3(THETA)));
	  dwn.num2.out.tp4.setText(String.valueOf(filter3(TPR)));
	  dwn.num2.out.tp6.setText(String.valueOf(filter3(TR)));
	  dwn.num2.out.tp7.setText(String.valueOf(filter3(DR)));
	   if (Normal)
	   {
		   dwn.num2.out.tp8.setText(String.valueOf("Yes"));
	   }
	   else
	   {
		   dwn.num2.out.tp8.setText(String.valueOf("No"));
	   }

	  dwn.num2.out.rat1.setText(String.valueOf(filter3(machrat)));
	  dwn.num2.out.rat2.setText(String.valueOf(filter3(prrat)));
	  dwn.num2.out.rat3.setText(String.valueOf(filter3(angrat)));
	  dwn.num2.out.rat4.setText(String.valueOf(filter3(tprrat)));
	  dwn.num2.out.rat6.setText(String.valueOf(filter3(trrat)));
	  dwn.num2.out.rat7.setText(String.valueOf(filter3(drrat)));
  }

   public float filter3(double inumbr) {
     //  output only to .001
       float number ;
       int intermed ;

       intermed = (int) (inumbr * 1000.) ;
       number = (float) (intermed / 1000. );
       return number ;
  }

   public void getAnglim (double machin, double gam) {
       double a1, ab1, ac1, sints, msq, mfor, thmx, cotd ;

       msq = machin * machin ;
       mfor = msq * msq ;

       a1 = 2.0 * gam * mfor ;
       ab1 = 4.0 * msq - (gam + 1.0) * mfor ;
       ac1 = -(2.0 + (gam + 1.0) * msq) ;
       sints = (-ab1 + Math.sqrt(Math.pow(ab1,2.0)-4.0*a1*ac1))/(2.0*a1) ;
       thmx = Math.asin(Math.sqrt(sints)) ;

       cotd = Math.tan(thmx)*(((gam+1.0)*msq)/    
                (2.0*(msq * sints - 1.0))-1.0);
       delmax = (Math.atan(1.0/cotd))/convdr ;
 
       return;
   }

   public double getShkang (double machin, double delr, double gam) {
        // Iterate to get Shock Angle given Wedge Angle.
      double cotd,mst,gp1,number ;    
      double theto,thetn,delo,deln,deriv ;

      gp1 = gam + 1.0 ;
      theto = Math.asin(1.0/machin) ; 
      delo = 0.0 ;
      thetn = theto + 3.0 * convdr ;
      while (Math.abs(delr - delo) > .0001) {
         mst = machin * Math.sin(thetn) ;
         cotd = Math.tan(thetn)*((gp1*machin*machin)/    
                (2.0*(mst * mst - 1.0))-1.0);
         deln = Math.atan(1.0/cotd) ;
         deriv = (deln-delo)/(thetn-theto) ;
         delo = deln ;
         theto = thetn ;
         thetn = theto + (delr-delo)/deriv ;
      }

      number = theto / convdr ;
      return number;
   }

   public void getOblq (double machin, double shang, double gam,
                        int upstrm, int index) {
          // NACA 1135 - oblique shock relations.
       double mst, gm1, gp1, msq, m2sq, cotd ;

       mst = machin * Math.sin(shang*convdr) ;
       msq = machin * machin ;
       gm1 = gam - 1.0 ;
       gp1 = gam + 1.0 ;

       prat[index] = (2.0*gam*mst*mst - gm1)/gp1 ;
       rhorat[index] = (gp1*mst*mst)/(gm1*mst*mst + 2.0) ;
       trat[index] = (2.0*gam*mst*mst - gm1) * (gm1*mst*mst + 2.0)
                  /(mst*mst*Math.pow(gp1,2.0)) ; 
       ptrat[index] = (Math.pow(((gp1*mst*mst)/(gm1*mst*mst+2.0)),(gam/gm1)))
               * Math.pow((gp1/(2.0*gam*mst*mst - gm1)),(1.0/gm1)) ;
       m2sq = ((msq * mst * mst * Math.pow(gp1,2.0)) +
              (-4.0 * (mst*mst  - 1.0) * (gam*mst*mst + 1.0))) /
              ((2.0*gam*mst*mst - gm1) * (gm1*mst*mst + 2.0)) ;
       mach2[index] = Math.sqrt(m2sq) ;
       cotd = Math.tan(shang*convdr)*((gp1*msq)/    
                (2.0*(mst * mst - 1.0))-1.0);
       defl[index] = (Math.atan(1.0/cotd))/convdr ;
       mach1[index] = machin ;

       pspo[index] = pspo[upstrm]*prat[index] ;
       tsto[index] = tsto[upstrm]*trat[index] ;
       rsro[index] = rsro[upstrm]*rhorat[index] ;
       ptpto[index] = ptpto[upstrm]*ptrat[index] ;
  
       return;
   }

   public void getNorm (double machin, double gam, 
                         int upstrm, int index) {
          // NACA 1135 - normal shock relations.
       double gm1, gp1, msq, m2sq ;

       msq = machin * machin ;
       gm1 = gam - 1.0 ;
       gp1 = gam + 1.0 ;

       prat[index] = (2.0*gam*msq - gm1)/gp1 ;
       rhorat[index] = (gp1*msq)/(gm1*msq + 2.0) ;
       trat[index] = prat[index] / rhorat[index] ;
       ptrat[index] = (Math.pow(rhorat[index],(gam/gm1)))
               * (Math.pow((1.0/prat[index]),(1.0/gm1))) ;
       m2sq = msq / (prat[index] * rhorat[index]) ;
       mach2[index] = Math.sqrt(m2sq) ;
       defl[index] = 0.0 ;
       mach1[index] = machin ;
       shkang[index] = 90.0 ;
       sang[index] = 90.0 ;
       detach[index] = true ;

       pspo[index] = pspo[upstrm]*prat[index] ;
       tsto[index] = tsto[upstrm]*trat[index] ;
       rsro[index] = rsro[upstrm]*rhorat[index] ;
       ptpto[index] = ptpto[upstrm]*ptrat[index] ;
 
       return;
   }

class Dwn extends Panel 
{
	Shock outerparent ;
	Num num ;
	Num2 num2 ;

	Dwn (Shock target) 
	{                           
           outerparent = target ;
           setLayout(new GridLayout(1,2,10,10)) ;

           num = new Num(outerparent) ;  
           num2 = new Num2(outerparent) ;  

           add(num) ;
           add(num2) ;
	}

  class Num extends Panel 
  {
	Shock outerparent ;
	Inp inp ;

	Num (Shock target) 
	{                           
           outerparent = target ;
           setLayout(new GridLayout(1,1,10,10)) ;

           inp = new Inp(outerparent) ;  

           add(inp) ;
	}

	class Inp extends Panel 
	{
           TextField f1, f2, f3, f4;

           Shock outerparent ;
           Rt rt ;

           Inp (Shock target) 
           {
                            
               outerparent = target ;
               setLayout(new GridLayout(1,1,10,10)) ;

               rt = new Rt(outerparent) ;

               add(rt) ;
           }

           class Rt extends Panel 
           {
               Shock outerparent ;
               Scrollbar s1, s3, s4 ;
               Label l1,l2 ;
               Choice type;
//             Choice plntch
			
               Rt (Shock target) 
               {
                  int i1, i3, i6 = 0;

                  outerparent = target ;
                  setLayout(new GridLayout(9,3,10,10)) ;

                  i1 = (int) (((mach0 - machlo)/(machhi - machlo))*1000.) ;
                  i3 = (int) (((ang1 - 5.0)/30.0)*1000.) ;

                  s1 = new Scrollbar(Scrollbar.HORIZONTAL,i1,10,0,1000);
                  s3 = new Scrollbar(Scrollbar.HORIZONTAL,i3,10,0,1000);
                  s4 = new Scrollbar(Scrollbar.HORIZONTAL,i6,10,0,1000);

                  l1 = new Label("Input", Label.LEFT) ;
                  l1.setForeground(Color.blue) ;

                  l2 = new Label("Upstream", Label.LEFT) ;
                  l2.setForeground(Color.blue) ;

                  f1 = new TextField(String.valueOf(mach0),5) ;
                  f2 = new TextField(String.valueOf(gamma),5) ;
                  f3 = new TextField(String.valueOf(ang1),5) ;
                  f4 = new TextField(String.valueOf(typeinpt),5);

                  type = new Choice();
                  type.addItem("Altitude (ft)");
                  type.addItem("Total Temp (R)");
                  type.setBackground(Color.white) ;
                  type.setForeground(Color.blue) ;
                  type.select(0);

//	plntch = new Choice() ;
//      plntch.addItem("Earth") ;
//	plntch.addItem("Mars");
//	plntch.setBackground(Color.white) ;
//	plntch.setForeground(Color.blue) ;
//	plntch.select(0) ;


                  add(l1) ; 
                  add(new Label(" ", Label.CENTER));
                  add(new Label(" ", Label.CENTER));

                  add(new Label(" ", Label.CENTER));
                  add(new Label(" ", Label.CENTER)) ;
                  add(new Label(" ", Label.CENTER)) ;

                  add(l2) ;
                  add(new Label(" ", Label.CENTER)) ;
                  add(new Label(" ", Label.CENTER));

                  add(new Label("Mach", Label.CENTER)) ;  
                  add(f1) ;
                  add(s1) ;

                  add(new Label(" Angle", Label.CENTER)) ;
                  add(f3) ;
                  add(s3) ;

//	add(plntch) ;
                  add(new Label(" Gamma", Label.CENTER)) ;
                  add(f2) ;
                  add(new Label(" ", Label.CENTER));

                  add(type);
                  add(f4) ;
                  add(s4);

                  add(new Label(" ", Label.CENTER)) ;
                  add(new Label(" ", Label.CENTER));
                  add(new Label(" ", Label.CENTER));

                  add(new Label(" ", Label.CENTER));
                  add(new Label(" ", Label.CENTER));
                  add(new Label(" ", Label.CENTER));
       }

       public boolean handleEvent(Event evt) 
       {
            if(evt.id == Event.ACTION_EVENT) 
            {
               //this.handleCho(evt) ;
               //return true ;
            }
            if(evt.id == Event.SCROLL_ABSOLUTE) 
            {
               this.handleBar(evt) ;
               return true ;
            }
            if(evt.id == Event.SCROLL_LINE_DOWN) 
            {
               this.handleBar(evt) ;
               return true ;
            }
            if(evt.id == Event.SCROLL_LINE_UP) 
            {
               this.handleBar(evt) ;
               return true ;
            }
            if(evt.id == Event.SCROLL_PAGE_DOWN) 
            {
               this.handleBar(evt) ;
               return true ;
            }
            if(evt.id == Event.SCROLL_PAGE_UP) 
            {
               this.handleBar(evt) ;
               return true ;
            }
            if(evt.id == Event.ACTION_EVENT) 
            {
               this.handleText(evt) ;
               return true ;
            }
            else return false ;
      }

      public void handleBar(Event evt) 
      {
          int i1, i3, i4, i5, i6 ;
          Double V2 ;
          double v1, v2, v3, v4, v5, v6 = 0 ;
          float fl1, fl3, fl4, fl5, fl6 ;

          i1 = s1.getValue() ;
          i3 = s3.getValue() ;
          i6 = s4.getValue() ;

          v1 = i1 * (machhi - machlo)/ 1000. + machlo ;
          v3 = i3 * ang1rng / 1000. + ang1mn ;
          if (itype == 0)
          {
             v6 = i6 * (altmax - altmin)/ 1000. + altmin;
          }
          if (itype == 1)
          {
             v6 = i6 * (tempmax - tempmin)/ 1000. + tempmin;
          }


          fl1 = (float) v1 ;
          fl3 = (float) v3 ;
          fl6 = (float) v6 ;

          inp.f1.setText(String.valueOf(fl1)) ;
          inp.f3.setText(String.valueOf(fl3)) ;
          inp.f4.setText(String.valueOf(fl6)) ;

          mach0 = v1 ;
          ang1 = v3 ;
          typeinpt = v6;

          comPute(mach0, ang1) ;    
      }

      public void handleCho(Event evt) 
       {     // range
//					float fl2, 
            float fl6 = 0 ;
//					double v4;
//					planet  = plntch.getSelectedIndex() ;

//					if (planet == 0) gamma = 1.4;
//					if (planet == 1) gamma = 1.29;

//					fl2 = (float) gamma ;
//					rt.f2.setText(String.valueOf(fl2)) ;

           itype = type.getSelectedIndex();
           if (itype == 0)
           {
              fl6 = (float) 0;
           }
           if (itype == 1)
           {
             fl6 = (float) 500;
           }
           inp.f4.setText(String.valueOf(fl6));

           comPute(mach0, ang1) ;    
     }

     public void handleText(Event evt) 
     {
         Double V1,V2,V3,V4,V5,V6 ;
         double v1,v2,v3,v4,v5,v6;
         float fl1,fl2,fl3 ;
         int i1,i3,i4,i5, i6 = 0;

   // Mach number - range from machlo to machhi
         V1 = Double.valueOf(f1.getText()) ;
         v1 = V1.doubleValue() ;
         if(v1 < machlo) 
         {
            v1 = machlo+ .02 ;
            fl1 = (float) v1 ;
            f1.setText(String.valueOf(fl1)) ;
         }
         if(v1 > machhi) 
         {
            v1 = machhi ;
            fl1 = (float) v1 ;
            f1.setText(String.valueOf(fl1)) ;
         }
   // Gamma - range from 1.0 to 2.0
         V2 = Double.valueOf(f2.getText()) ;
         v2 = V2.doubleValue() ;
         if(v2 < 1.0) 
         {
            v2 = 1.02 ;
            fl2 = (float) v2 ;
            f2.setText(String.valueOf(fl2)) ;
         }
         if(v2 > 2.0) 
         {
            v2 = 2.0 ;
            fl2 = (float) v2 ;
            f2.setText(String.valueOf(fl2)) ;
         }
    // ramp angle # 1  range from 0 to +30 for prob = 0
         V3 = Double.valueOf(f3.getText()) ;
         v3 = V3.doubleValue() ;
         if(v3 < ang1mn) 
         {
            v3 = ang1mn ;
            fl1 = (float) v3 ;
            f3.setText(String.valueOf(fl1)) ;
         }
         if(v3 > 30.0) 
         {
            v3 =  30.0 ;
            fl1 = (float) v3 ;
            f3.setText(String.valueOf(fl1)) ;
         }
       
         i1 = (int) (((v1 - machlo)/(machhi - machlo))*1000.) ;
         i3 = (int) (((v3 - ang1mn)/ang1rng)*1000.) ;

         rt.s1.setValue(i1) ;
         rt.s3.setValue(i3) ;

         V6 = Double.valueOf(f4.getText());
         v6 = V6.doubleValue();
         itype = type.getSelectedIndex();
         if (itype == 0)
         {
            if (v6 < altmin)
            {
              v6 = altmin;
              fl3 = (float) v6;
              f4.setText(String.valueOf(fl3));
            }
            if (v6 > altmax)
            {
              v6 = altmax;
              fl3 = (float) v6;
              f4.setText(String.valueOf(fl3));
            }
            i6 = (int) (((v6 - altmin)/(altmax - altmin))*1000.) ;
         }
         if (itype == 1)
         {
            if (v6 < tempmin)
            {
              v6 = tempmin;
              fl3 = (float) v6;
              f4.setText(String.valueOf(fl3));
            }
            if (v6 > tempmax)
            {
              v6 = tempmax;
              fl3 = (float) v6;
              f4.setText(String.valueOf(fl3));
            }
            i6 = (int) (((v6 - tempmin)/(tempmax - tempmin))*1000.) ;
         }

         rt.s4.setValue(i6) ;

         M = mach0 = v1 ;
         G = gamma = v2 ;
         DELG = ang1 = v3 ;
         typeinpt = v6;

         comPute(mach0, ang1) ;
        }
      }
    }
  }

  class Num2 extends Panel 
  {
	Shock outerparent ;
	Out out ;

	Num2 (Shock target) 
	{                           
            outerparent = target ;
            setLayout(new GridLayout(1,1,10,10)) ;

            out = new Out(outerparent) ;  

            add(out) ;
	}

	class Out extends Panel 
	{
            Shock outerparent ;
            TextField o1, o2, o3, o4, o6, o7, o8, tp1, tp2, tp3, tp4, tp6, tp7, tp8 ;
            TextField rat1, rat2, rat3, rat4, rat6, rat7;
            Label lo1,lo2,lo3,lo4, lo5, lo6, ht4, ht5, ht6, hb4, hb5, hb6 ;

            Out (Shock target) 
            {

               outerparent = target ;
               setLayout(new GridLayout(10,4,2,10)) ;

               o1 = new TextField() ;
               o1.setBackground(Color.black) ;
               o1.setForeground(Color.green) ;
               o2 = new TextField() ;
               o2.setBackground(Color.black) ;
               o2.setForeground(Color.green) ;
               o3 = new TextField() ;
               o3.setBackground(Color.black) ;
               o3.setForeground(Color.green) ;
               o4 = new TextField() ;
               o4.setBackground(Color.black) ;
               o4.setForeground(Color.green) ;
               o6 = new TextField() ;
               o6.setBackground(Color.black) ;
               o6.setForeground(Color.green) ;
               o7 = new TextField() ;
               o7.setBackground(Color.black) ;
               o7.setForeground(Color.green) ;
               o8 = new TextField() ;
               o8.setBackground(Color.black) ;
               o8.setForeground(Color.green) ;

               tp1 = new TextField() ;
               tp1.setBackground(Color.black) ;
               tp1.setForeground(Color.green) ;
               tp2 = new TextField() ;
               tp2.setBackground(Color.black) ;
               tp2.setForeground(Color.green) ;
               tp3 = new TextField() ;
               tp3.setBackground(Color.black) ;
               tp3.setForeground(Color.green) ;
               tp4 = new TextField() ;
               tp4.setBackground(Color.black) ;
               tp4.setForeground(Color.green) ;
               tp6 = new TextField() ;
               tp6.setBackground(Color.black) ;
               tp6.setForeground(Color.green) ;
               tp7 = new TextField() ;
               tp7.setBackground(Color.black) ;
               tp7.setForeground(Color.green) ;
               tp8 = new TextField() ;
               tp8.setBackground(Color.black) ;
               tp8.setForeground(Color.green) ;

               rat1 = new TextField() ;
               rat1.setBackground(Color.black) ;
               rat1.setForeground(Color.cyan) ;
               rat2 = new TextField() ;
               rat2.setBackground(Color.black) ;
               rat2.setForeground(Color.cyan) ;
               rat3 = new TextField() ;
               rat3.setBackground(Color.black) ;
               rat3.setForeground(Color.cyan) ;
               rat4 = new TextField() ;
               rat4.setBackground(Color.black) ;
               rat4.setForeground(Color.cyan) ;
               rat6 = new TextField() ;
               rat6.setBackground(Color.black) ;
               rat6.setForeground(Color.cyan) ;
               rat7 = new TextField() ;
               rat7.setBackground(Color.black) ;
               rat7.setForeground(Color.cyan) ;

               lo1 = new Label(" ", Label.RIGHT) ;
               lo1.setForeground(Color.red) ;
               lo2 = new Label("Downstream", Label.LEFT) ;
               lo2.setForeground(Color.red) ;
               lo3 = new Label("Output ", Label.CENTER) ;
               lo3.setForeground(Color.red) ;
               lo4 = new Label("Zone 1", Label.CENTER) ;
               lo4.setForeground(Color.red) ;
               lo5 = new Label("Zone 1", Label.CENTER) ;
               lo5.setForeground(Color.red) ;
               lo6 = new Label("Perfect Ratio", Label.CENTER) ;
               lo6.setForeground(Color.red) ;

               ht4 = new Label(" ", Label.CENTER) ;
               ht4.setForeground(Color.red) ;
               ht5 = new Label("Calorically ", Label.CENTER) ;
               ht5.setForeground(Color.red) ;
               ht6 = new Label("Calorically", Label.CENTER) ;
               ht6.setForeground(Color.red) ;

               hb4 = new Label("Perfect", Label.CENTER) ;
               hb4.setForeground(Color.red) ;
               hb5 = new Label("Imperfect", Label.CENTER) ;
               hb5.setForeground(Color.red) ;
               hb6 = new Label("Imperfect to", Label.CENTER) ;
               hb6.setForeground(Color.red) ;

               add(lo3) ;
               add(ht4);
               add(ht5);
               add(ht6);

               add(new Label(" ", Label.CENTER)) ;
               add(hb4);
               add(hb5);
               add(hb6);

               add(lo2) ;
               add(lo4) ;
               add(lo5) ;
               add(lo6) ;

               add(new Label("Mach", Label.CENTER)) ;  
               add(o1) ;  
               add(tp1);
               add(rat1);

               add(new Label("Shock Angle", Label.CENTER)) ; 
               add(o3) ; 
               add(tp3);
               add(rat3);

               add(new Label("p ratio", Label.CENTER)) ;  
               add(o2) ;
               add(tp2) ;
               add(rat2) ;

               add(new Label("pt ratio", Label.CENTER)) ;  
               add(o4) ; 
               add(tp4) ;
               add(rat4) ;

               add(new Label("T ratio", Label.CENTER)) ; 
               add(o6) ;
               add(tp6) ;
               add(rat6) ;

               add(new Label("r ratio", Label.CENTER)) ;  
               add(o7) ; 
               add(tp7) ;
               add(rat7) ;

               add(new Label("Detached", Label.CENTER));
               add(o8);
               add(tp8);
               add(new Label(" ", Label.CENTER));
           }
       } 
   }
}

 class Viewer extends Canvas {
    Shock outerparent ;

    Viewer (Shock target) {
        setBackground(Color.white) ;
    }

    public void update(Graphics g) {
        view.paint(g) ;
    }

    public void paint(Graphics g) {
      int i,k ;
      int exes[] = new int[3] ;
      int whys[] = new int[3] ;
      int yorgn = 240 ;
      int xorgn = 150 ;

      offsGg.setColor(Color.white) ;
      offsGg.fillRect(0,0,700,500) ;
      offsGg.setColor(Color.blue) ;
      offsGg.drawString("Upstream", 0, 40) ;
      offsGg.drawString("Flow", xorgn-40, 55) ;
      offsGg.setColor(Color.black) ;
   offsGg.drawString("Ideal Shock Graphic", 50, 10) ;
      offsGg.fillRect(xorgn-40,62,30,9) ;
      exes[0] = xorgn ;
      whys[0] = 66;
      exes[1] = xorgn - 10;
      whys[1] = 76;
      exes[2] = xorgn - 10;
      whys[2] = 56;
      Polygon poly1 = new Polygon(exes,whys,3) ;
      offsGg.fillPolygon(poly1) ;
	 // draw geometry	   
      offsGg.setColor(Color.red) ;
         // draw ramps
       for (i = 1; i <= nramps; ++i) {
        exes[0] = xorgn + (int) wxbgn[i] ;
        whys[0] = yorgn - (int) (wxbgn[i]*wslope[i] + winter[i]) ;
        exes[1] = xorgn + (int) xlong ;
        whys[1] = yorgn - (int) (xlong*wslope[i] + winter[i]) ;
        exes[2] = xorgn + (int) xlong ;
//        whys[2] = yorgn + (int) (xlong*wslope[i] + winter[i]) ;
        whys[2] = whys[0] ;
        offsGg.setColor(Color.red) ;
        Polygon poly = new Polygon(exes,whys,3) ;
        offsGg.fillPolygon(poly) ;
        offsGg.drawString("1", exes[1]-20, whys[1]-10) ;
//        offsGg.drawString("1", exes[1]-20, whys[2]+10) ;
        exes[0] = xorgn + (int) wxbgn[i] ;
        whys[0] = yorgn - (int) (wxbgn[i]*wslope[i] + winter[i]) ;
        exes[1] = xorgn + (int) xlong ;
        whys[1] = whys[0] ;
        offsGg.setColor(Color.white) ;
        offsGg.drawLine(exes[0],whys[0],exes[1],whys[1]) ;
        offsGg.drawString("Angle", exes[1]-50, whys[0]-5) ;
      }
         // draw streamlines
      for (i = 1; i <= 4; ++i) {
        exes[0] = 0 ;
        whys[0] = yorgn - (i-1)*20 ;
        whys[1] = yorgn - (i-1)*20 ;
        if (detach[1]) {
           exes[1] = xorgn ;
         exes[1] = xorgn + (int) sxbgn[1] ;
           exes[2] = xorgn + (int) xlong ;
           whys[2] = yorgn - (int) (xlong*wslope[1] + winter[1]) -(i-1)*20 ;
        }
        else {
           exes[1] = xorgn + (int) (((i-1)*20 - sinter[1])/sslope[1]) ;
           exes[2] = xorgn + (int) xlong ;
           whys[2] = yorgn - (int) (wslope[1]*(exes[2]-exes[1])) -(i-1)*20 ;
        }
        offsGg.setColor(Color.black) ; 
        offsGg.drawLine(exes[0],whys[0],exes[1],whys[1]) ;
        offsGg.drawLine(exes[1],whys[1],exes[2],whys[2]) ;
        exes[0] = 10+i*5 ;
        exes[1] = exes[0]-5;
        whys[1] = whys[0]-5;
        exes[2] = exes[1];
        whys[2] = whys[0]+5;
        offsGg.fillPolygon(exes,whys,3) ;
      }
/*
      for (i = 1; i <= 4; ++i) {
        exes[0] = 0 ;
        whys[0] = yorgn + (i-1)*20 ;
        whys[1] = yorgn + (i-1)*20 ;
        if (detach[1]) {
            exes[1] = xorgn ;
            exes[2] = (int) xlong ;
            whys[2] = yorgn + (int) (xlong*wslope[1] + winter[1]) +(i-1)*20 ;
         }
         else {
            exes[1] = (int) (((i-1)*20 - sinter[1])/sslope[1]) ;
            exes[2] = (int) xlong ;
            whys[2] = yorgn + (int) (wslope[1]*(exes[2]-exes[1])) +(i-1)*20 ;
         }
         offsGg.setColor(Color.black) ; 
         offsGg.drawLine(exes[0],whys[0],exes[1],whys[1]) ;
         offsGg.drawLine(exes[1],whys[1],exes[2],whys[2]) ;
         exes[0] = 10+i*5 ;
         exes[1] = exes[0]-5;
         whys[1] = whys[0]-5;
         exes[2] = exes[1];
         whys[2] = whys[0]+5;
         offsGg.fillPolygon(exes,whys,3) ;
       }
*/
          // draw shock waves or expansion
       for(i=1; i <= nshocks; ++i) {
         exes[0] = xorgn + (int) sxbgn[i] ;
         whys[0] = yorgn - (int) sybgn[i] ;
         exes[1] = xorgn + (int) sxnd[i] ;
         whys[1] = yorgn - (int) synd[i] ;
         if (detach[i]) {
           offsGg.setColor(Color.magenta) ; 
           offsGg.drawLine(exes[0],whys[0],exes[1],whys[1]) ;
         }
         else {
           offsGg.setColor(Color.blue) ; 
           offsGg.drawLine(exes[0],whys[0],exes[1],whys[1]) ;  
         }
/*
         whys[1] = yorgn + (int) synd[i] ;
         if (detach[i]) {
           offsGg.setColor(Color.magenta) ; 
           offsGg.drawLine(exes[0],whys[0],exes[1],whys[1]) ;
         }
         else {
           offsGg.setColor(Color.blue) ; 
           offsGg.drawLine(exes[0],whys[0],exes[1],whys[1]) ;  
         }
*/
       }
       g.drawImage(offscreenImg,0,0,this) ;
    }
  }
}
