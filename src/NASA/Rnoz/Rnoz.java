/*
                Interactive Rocket Thrust Program

     Program to perform one dimensional design and analysis of
                        rocket nozzle
                Derived from turbine nozzle program

                     Version 1.5b   - 13 Dec 05

                         Written by Tom Benson
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
                 * change layout slightly
                   add altitude input to flow


                                                     TJB 13 Dec 05
*/

import java.awt.*;
import java.lang.Math ;

public class Rnoz extends java.applet.Applet {

   final double convdr = 3.14515926/180.;
   final double runiv = 1545. ;

   static double rthrt, rexit, rzero ;
   static double press, temp ;
   static double ath, pt, pamb, pexit, tt, machth, mflow ;
   static double athroat, athmx, athmn ;
   static double arat, aexmx, aexmn ;
   static double azrat, azmx, azmn, azero ;
   static double ptin, ptmx, ptmn ;
   static double psin, pemx, pemn ;
   static double alt, altin, altmx, altmn ;
   static double ttin, ttmx, ttmn ;
   static double lngth, lngmx, lngmn ;
   static double fact ;
   static double npr,uex,fgros ;
   static double mweight,rgas,gam0,gamma,tcomb,ofrat,mwtab,ttab ;
   static double mexit,aexit,ans,rns,lns,mnsup ;
   static int counter,flomode ;
   static int fuelopt, fuelold, gamopt, tcomopt, molopt, oxopt, ofopt ;

   static double xg[][]  = new double[11][45] ;
   static double yg[][]  = new double[11][45] ;

   static int antim,ancol,lunits ; 
   static double aconv,lconv,fconv,pconv,tref,tconv,mconv1 ;

   int xt,yt,sldloc,mode ;

   Image offscreenImg ;
   Graphics offsGg ;
   Pn pn ;
   Viewer view ;
   CardLayout layout,laycho ;

   public void init() {
     Rnoz a = new Rnoz() ;

     offscreenImg = createImage(this.size().width,
                      this.size().height) ;
     offsGg = offscreenImg.getGraphics() ;

     setLayout(new GridLayout(1,2,5,5)) ;

     setDefaults () ;

     view = new Viewer(this) ;
     pn = new Pn(this) ;

     add(view) ;
     add(pn) ;

     comPute() ;
     view.start() ;
  }


  public Insets insets() {
     return new Insets(10,10,10,10) ;
  }


   public void setDefaults() {

       lunits = 0 ;
       aconv = 1.;                         /*  area sq inches    */
       lconv = 1.;                         /*  length feet    */
       fconv = 1.0;                        /* pounds   */
       pconv = 1.0  ;                   /* lb/sq in */
       tref =  459.7 ;                 /* zero rankine */
       tconv = 1.0 ;                    /* degrees F */
       mconv1 = 1.0 ;                   /* airflow rate lbs/sec*/

       athmx = 300. ;
       athmn = .1 ;
       aexmx = 100. ;
       aexmn = 1. ;
       azmx = 10. ;
       azmn = 1.1 ;
       ptmx = 3000. ;
       ptmn = 1. ;
       pemx = 15. ;
       pemn = 0.0 ;
       ttmx = 6500. ;
       ttmn = 500. ;
       altmn = 0.0 ;
       altmx = 100000. ;
       altin = 0.0 ;
       alt = 0.0 ;
       flomode = 0 ;

       mwtab = mweight = 16.0 ;
       rgas = 1716. ;                /* air - ft2/sec2 R */
       molopt = 1 ;
       fuelold = fuelopt = 4 ;
       oxopt = 1 ;
       ttab = tcomb = ttin = 5870. ;
       gamopt = 1 ;
       gam0 = 1.32 ;
       gamma = 1.22 ;
       tcomopt = 1 ;
       ofopt = 0;
       ofrat = 8.0 ;

       athroat  = 150.0 ;
       arat = 50.0 ;
       azero  = 600.0 ;
       azrat = 4.0 ;
       ptin = 2000. ;
       psin = 14.7 ;

       mode = 0 ;
       ans = 0.0 ;
       fact = .2 ;
       xt = 40;  yt = 0; 
       sldloc = 40 ;
       lngth = .5 ;
       lngmx = (athroat/144.) * 20.0;
       lngmn = (athroat/144.) * .01;

   }

   public void computeAtmos() {

       if (alt <= 36152.) {           // Troposphere
          temp = 518.6 - 3.56 * alt/1000. ;
          press = 2116. * Math.pow(temp/518.6,5.256) ;
       }
       if (alt >= 36152. && alt <= 82345.) {   // Stratosphere
          temp = 389.98 ;
          press = 2116. * .2236 *
             Math.exp((36000.-alt)/(53.35*389.98)) ;
       }
       if (alt >= 82345.) {
          temp = 389.98 + 1.645 * (alt-82345)/1000. ;
          press = 2116. *.02456 * Math.pow(temp/389.98,-11.388) ;
       }

       psin = pconv * press / 144.  ;
   }


   public void comPute() {
       double gm1,fac1,trat,aircor,g0 ;
       double psub,psup ;
       double mnsub,ptrat,anso,ansn,pso,psn,deriv ;
       double gamtfac ;

       g0    = 32.2 ;
       rgas = runiv * g0 / mweight ;
       alt = altin / lconv ;
       ath = athroat / aconv ;
       rthrt = Math.sqrt(ath / 3.1415926) ;
       aexit = arat * ath ;
       rexit = Math.sqrt(aexit / 3.1415926) ;
       azero = azrat * ath ;
       rzero = Math.sqrt(azero / 3.1415926) ;
       pt    = ptin / pconv ;
       
       if (flomode == 1) computeAtmos () ;

       pamb  = psin / pconv ;
       tt    = (ttin + tref) / tconv ;
       if (gamopt == 1) {
           gamtfac = getGama(tt,gamopt) ;
           gamma = gam0 * gamtfac / 1.4 ;
       }
       gm1   = gamma - 1.0 ;
       fac1  = gm1 / gamma ;

       counter = 0 ;
       machth = 1.0 ;         // assume flow is choked
       aircor = getMfr(1.0,rgas,gamma) ;
       mexit = getMach(2,(aircor/arat),rgas,gamma) ;
       psup = getPisen(mexit,gamma) * pt ;
       mflow = aircor*ath*(pt/14.7)/Math.sqrt(tt/518.) ;

  // under expanded nozzle
       if (pamb <= psup) {   
          mode = 0 ;
          trat = getTisen(mexit,gamma) ;
          uex = mexit * Math.sqrt (gamma * rgas * trat * tt) ;
          pexit = psup ;
       }

  // over expanded nozzle
       if (pamb > psup) {  
           // find exit pressure at which normal shock leaves the nozzle
          mnsup = mexit ;
          psub = psup * getNsps(mnsup,gamma) ;

     // slightly overexpanded .. no shock in nozzle
          if (pamb <= psub) {   
             mode = 1 ;
             pexit = psup ;
             trat = getTisen(mexit,gamma) ;
             uex = mexit * Math.sqrt (gamma * rgas * trat * tt) ;
          }

     // highly overexpanded .. normal shock in nozzle
          if (pamb > psub) {   
             mode = 2 ;
             pexit = pamb ;
             anso  = aexit ;
             mnsup = mexit ;
             mnsub = getNsmac(mnsup,gamma) ;
             ptrat = getNspt(mnsup,gamma) ;
             pso = getPisen(mnsub,gamma) * ptrat * pt ;
             ansn = anso - 1. ;
             while ((Math.abs(pexit - pso) > .001) && (counter < 20)) {
                ++ counter ;
                mnsup = getMach(2,(aircor*ath/ansn),rgas,gamma) ;
                mnsub = getNsmac(mnsup,gamma) ;
                ptrat = getNspt(mnsup,gamma) ;
                mexit = getMach(0,(aircor/arat/ptrat),rgas,gamma) ;
                psn = getPisen(mexit,gamma) * ptrat * pt ;
                deriv = (psn-pso)/(ansn-anso) ;
                pso = psn ;
                anso = ansn ;
                ansn = anso + (pexit -pso)/deriv ;
             }
             ans = anso ;
             rns = Math.sqrt(ans / 3.1415926) ;
             trat = getTisen(mexit,gamma) ;
             uex = mexit * Math.sqrt (gamma * rgas * trat * tt) ;
          }
       }

       if (pamb > .0001) npr = pt / pamb ;
       else npr = 1000.;

       fgros = mflow * uex / g0 + (pexit - pamb) * aexit ;

       loadOut() ;
 
       loadGeom() ;

       view.repaint();
    }
 
    public double getGama(double temp, int opt) {
             // Utility to get gamma as a function of temp
       double number,a,b,c,d ;
       a =  -7.6942651e-13;
       b =  1.3764661e-08;
       c =  -7.8185709e-05;
       d =  1.436914;
       if(opt == 0) {
          number = 1.4 ;
       }
       else {
          number = a*temp*temp*temp + b*temp*temp + c*temp +d ;
       }
       return(number) ;
    } 

    public double getMfr(double mach, double gascon, double gam) {
/* Utility to get the corrected weightflow per area given the Mach number */
       double number,fac1,fac2;

       fac2 = (gam+1.0)/(2.0*(gam-1.0)) ;
       fac1 = Math.pow((1.0+.5*(gam-1.0)*mach*mach),fac2);
       number =  20.78 * Math.sqrt(gam/gascon) * mach / fac1 ;

       return(number) ;
    }

     public double getMach (int sub, double corair, double gascon, double gam) {
/* Utility to get the Mach number given the corrected airflow per area */
         double number,chokair,a,b,k;     /* iterate for mach number */
         double deriv,machn,macho,airo,airn;
         int iter ;

         a = (gam -1)/2.0 ;
         b = -(gam+1.0)/(2.0*(gam-1.0)) ;
         k = 20.78 * Math.sqrt(gam/gascon) ;

         chokair = getMfr(1.0,gascon,gam) ;
         if (corair > chokair) {
           number = 1.0 ;
           return (number) ;
         }
         else {
           if (sub == 1) macho = 1.0 ;   /* sonic */
           else {
              if (sub == 2) macho = 1.703 ; /* supersonic */
              else macho = .25;                /* subsonic */
              airo = getMfr(macho,gascon,gam) ;    /* initial guess */
              iter = 1 ;
              machn = macho - .2  ;
              while (Math.abs(corair - airo) > .0001 && iter < 50) {
                 airn =  getMfr(machn,gascon,gam) ;
                 deriv = (airn-airo)/(machn-macho) ;
                 airo = airn ;
                 macho = machn ;
                 machn = macho + (corair - airo)/deriv ;
                 ++ iter ;
              }
           }
           number = macho ;
         }
         return(number) ;
     }

     public double getPisen(double machin, double gam)  {
       /* Utility to get the isentropic pressure ratio given the mach number */
       double number,fac1,gm1,mach1s;
       mach1s = machin*machin ;
       gm1 = gam - 1.0 ;
       fac1 = 1.0 + .5*gm1*mach1s;
       number = Math.pow(1.0/fac1,gam/gm1) ;

       return(number) ;
    }

    public double getTisen(double machin, double gam)  {
      /* Utility to get the isentropic temperature ratio given the mach number*/       double number,gm1,mach1s;
       mach1s = machin*machin ;
       gm1 = gam - 1.0 ;
       number = 1.0 /(1.0 + .5*gm1*mach1s) ;

       return(number) ;
    }

    public double getNsps (double machin, double gam) {
          // NACA 1135 - normal shock relation ps ratio
       double number, gm1, gp1, msq, fac1, fac2 ;

       msq = machin * machin ;
       gm1 = gam - 1.0 ;
       gp1 = gam + 1.0 ;

       fac2 = (2.0*gam*msq - gm1)/gp1 ;
       number = fac2 ;

       return(number) ;
    }

    public double getNspt (double machin, double gam) {
          // NACA 1135 - normal shock relation pt ratio
       double number, gm1, gp1, msq, fac1, fac2 ;

       msq = machin * machin ;
       gm1 = gam - 1.0 ;
       gp1 = gam + 1.0 ;

       fac2 = (2.0*gam*msq - gm1)/gp1 ;
       fac1 = (gp1*msq)/(gm1*msq + 2.0) ;
       number = (Math.pow(fac1,(gam/gm1)))
               * (Math.pow((1.0/fac2),(1.0/gm1))) ;

       return(number) ;
    }

    public double getNsmac (double machin, double gam) {
          // NACA 1135 - normal shock relation  mach
       double number, gm1, gp1, msq, fac1, fac2 ;

       msq = machin * machin ;
       gm1 = gam - 1.0 ;
       gp1 = gam + 1.0 ;

       fac2 = (2.0*gam*msq - gm1)/gp1 ;
       fac1 = (gp1*msq)/(gm1*msq + 2.0) ;
       number = Math.sqrt(msq / (fac2 * fac1)) ;

       return(number) ;
   }

   public void loadOut() {
      String outfor,outair,outvel,outprs,outarea,outisp,outtem  ;

      outfor = " lbs" ;
      if (lunits == 1) outfor = " N" ;
      outair = " pps" ;
      if (lunits == 1) outair = " kg/s" ;
      outvel = " fps" ;
      if (lunits == 1) outvel = " mps" ;
      outprs = " psi" ;
      if (lunits == 1) outprs = " kPa" ;
      outarea = " sq in" ;
      if (lunits == 1) outarea = " sq cm" ;
      outisp = " s" ;
      outtem = " F" ;
      if (lunits == 1) outtem = " C" ;

      if (gamopt == 1) {
         pn.in.chem.r.f4.setText(String.valueOf(filter3(gamma)));
      }

      if (fuelopt == 0) pn.out.o16.setText("Air");
      if (fuelopt == 1) pn.out.o16.setText("Al-NH4ClO4");
      if (fuelopt == 2) pn.out.o16.setText("H2O2");
      if (fuelopt == 3) pn.out.o16.setText("N2H4");
      if (fuelopt == 4) pn.out.o16.setText("H2");
      if (fuelopt == 5) pn.out.o16.setText("JP-4");
      if (fuelopt == 6) pn.out.o16.setText("Kerosene");
      if (fuelopt == 7) pn.out.o16.setText("UDMH");
      if (oxopt == 0) pn.out.o17.setText("NONE");
      if (oxopt == 1) pn.out.o17.setText("O2");
      if (oxopt == 2) pn.out.o17.setText("F");
      if (oxopt == 3) pn.out.o17.setText("H2O2");
      if (oxopt == 4) pn.out.o17.setText("N2O");
      if (oxopt == 5) pn.out.o17.setText("RFNA");
      if (oxopt == 0) {
         pn.out.o18.setText("NA");
      }
      if (oxopt >= 1) {
         pn.out.o18.setText(String.valueOf(filter3(ofrat)));
      }

      pn.out.o11.setText(String.valueOf(filter0(ttin)) + outtem);
      pn.out.o9.setText(String.valueOf(filter3(gamma)));
      pn.out.o15.setText(String.valueOf(filter3(mweight)));

      pn.out.o14.setText(String.valueOf(filter3(pt * pconv)));
      pn.out.o5.setText(String.valueOf(filter3(pexit * pconv)));
      pn.out.o13.setText(String.valueOf(filter3(pamb * pconv)));

      pn.out.o12.setText(String.valueOf(filter1(athroat)));
      pn.out.o7.setText(String.valueOf(filter1(aexit * aconv)));
      if (pamb > .0001) {
         if (npr <= 99.99) {
            pn.out.o1.setText(String.valueOf(filter3(npr))) ;
         }
         if (npr >= 99.99) {
            pn.out.o1.setText(String.valueOf(filter0(npr))) ;
         }
      }
      else pn.out.o1.setText("NA") ;

      if (mexit >= .26) {
         pn.out.o3.setText(String.valueOf(filter3(machth)));
         pn.out.o6.setText(String.valueOf(filter3(mexit)));
         pn.out.o2.setText(String.valueOf(filter0(uex * lconv)));

         pn.con.o8.setText(String.valueOf(filter0(fgros * fconv))+ outfor);
         if ((mflow*mconv1) <= 99.99) {
            pn.con.o4.setText(String.valueOf(filter3(mflow * mconv1)) + outair);
         }
         if ((mflow*mconv1) >= 99.99) {
            pn.con.o4.setText(String.valueOf(filter0(mflow * mconv1)) + outair);
         }
         pn.con.o10.setText(String.valueOf(filter0(fgros / mflow)) + outisp);
      }
      else {
         pn.out.o3.setText("NA");
         pn.out.o6.setText("NA");
         pn.out.o2.setText("NA");
         pn.con.o8.setText("NA");
         pn.con.o4.setText("NA");
         pn.con.o10.setText("NA");
      }

   }

   public void loadInput() {
      int i1, i2, i3, i4, i5, i6 ;
      Double V1, V2, V3, V4, V5, V6 ;
      double v1, v2, v3, v4,  v5, v6 ;
      float fl1, fl2, fl3, fl4, fl5, fl6, fl7, fl8 ;

      i1 = (int) (((athroat - athmn)/(athmx-athmn))*1000.) ;
      i2 = (int) (((arat - aexmn)/(aexmx-aexmn))*1000.) ;
      i3 = (int) (((azrat - azmn)/(azmx-azmn))*1000.) ;
      i4 = (int) (((lngth - lngmn)/(lngmx - lngmn))*1000.) ;
      i5 = (int) (((ptin - ptmn)/(ptmx - ptmn))*1000.) ;
      i6 = (int) (((ttin - ttmn)/(ttmx - ttmn))*1000.) ;
  
      v1 = i1 * (athmx-athmn) / 1000. + athmn ;
      v2 = i2 * (aexmx-aexmn) / 1000. + aexmn ;
      v3 = i3 * (azmx-azmn) / 1000. + azmn ;
      v5 = i5 * (ptmx-ptmn) / 1000. + ptmn ;
      v6 = i6 * (ttmx-ttmn) / 1000. + ttmn ;
      if (v5 < psin) v5 = psin ;

      fl1 = (float) v1 ;
      fl2 = (float) v2 ;
      fl3 = (float) v3 ;
      fl5 = (float) v5 ;
      fl6 = (float) v6 ;
      fl7 = (float) gamma ;
      fl8 = (float) mweight ;
 
      pn.in.geo.l.f1.setText(String.valueOf(filter1(fl1))) ;
      pn.in.geo.l.f2.setText(String.valueOf(fl2)) ;
      pn.in.geo.l.f4.setText(String.valueOf(fl3)) ;
      pn.in.flo.l.f1.setText(String.valueOf(fl5)) ;
      pn.in.flo.l.f2.setText(String.valueOf(fl6)) ;
      pn.in.geo.r.s1.setValue(i1) ;
      pn.in.geo.r.s2.setValue(i2) ;
      pn.in.geo.r.s4.setValue(i3) ;
      pn.in.geo.r.s3.setValue(i4) ;
      pn.in.flo.r.s1.setValue(i5) ;
      pn.in.flo.r.s2.setValue(i6) ;
      pn.in.chem.r.f4.setText(String.valueOf(fl7)) ;
      pn.in.chem.r.f5.setText(String.valueOf(fl8)) ;
      pn.in.chem.r.f6.setText(String.valueOf(filter0(fl6))) ;
 
   }

   public float filter3(double inumbr) {
     //  output only to .001
       float number ;
       int intermed ;

       intermed = (int) (inumbr * 1000.) ;
       number = (float) (intermed / 1000. );
       return number ;
   }

   public float filter1(double inumbr) {
     //  output only to .1
       float number ;
       int intermed ;

       intermed = (int) (inumbr * 10.) ;
       number = (float) (intermed / 10. );
       return number ;
   }

   public int filter0(double inumbr) {
     //  output only to .
       int number ;

       number = (int) (inumbr) ;
       return number ;
   }
 
   public void setUnits() {   // Switching Units
       double aths,athms,pts,tts,ttmxs,pss,ptmxs,ttcos,ttabs ;
       double altns, altmxs ;
       int i1,i2,i3,i4 ;
 
       aths = athroat / aconv ;
       athms = athmx / aconv ;
       pts  = ptin / pconv ;
       tts  = ttin / tconv ;
       ttmxs  = ttmx / tconv ;
       ttcos  = tcomb / tconv ;
       ttabs  = ttab / tconv ;
       pss  = psin / pconv ;
       ptmxs  = ptmx / pconv ;
       altns = altin / lconv ;
       altmxs = altmx / lconv ; 

       switch (lunits) {
          case 0: {                             /* English */
            aconv = 1.;                         /*  sq in   */
            lconv = 1.;                         /*  feet    */
            fconv = 1.0;                        /* pounds   */
            pconv = 1.0  ;                   /* lb/sq in */
            tref =  459.7 ;                 /* zero rankine */
            tconv = 1.0 ;                    /* degrees F */
            mconv1 = 1.0 ;                   /* airflow rate lbs/sec*/
            pn.in.geo.l.li1.setText("Ath sq in") ;
            pn.in.flo.l.li1.setText("Pto  psi") ;
            pn.in.flo.l.li2.setText("Tto  F") ;
            if (flomode == 0) pn.in.flo.l.li3.setText("Pfs  psi") ;
            if (flomode == 1) pn.in.flo.l.li3.setText("Alt  ft") ;
            pn.in.chem.r.l6.setText("F") ;
            pn.out.l2.setText("Uex fps") ;
            pn.out.l12.setText("Ath sq in") ;
            pn.out.l14.setText("Pt0 psi") ;
            break;
          }
          case 1: {                             /* Metric */
            aconv = 6.4516;                      /* sq cm */
            lconv = .3048;                      /* meters */
            fconv = 4.448 ;                     /* newtons */
            pconv = 6.891 ;               /* kilo-pascals */
            tref = 273.1 ;                /* zero kelvin */
            tconv = 0.555555 ;             /* degrees C */
            mconv1 = .4536 ;                /* kg/sec */
            pn.in.geo.l.li1.setText("Ath sq cm") ;
            pn.in.flo.l.li1.setText("Pto  kPa") ;
            pn.in.flo.l.li2.setText("Tto  C") ;
            if (flomode == 0) pn.in.flo.l.li3.setText("Pfs  kPa") ;
            if (flomode == 1) pn.in.flo.l.li3.setText("Alt  m") ;
            pn.in.chem.r.l6.setText("C") ;
            pn.out.l2.setText("Uex mps") ;
            pn.out.l12.setText("Ath sq cm") ;
            pn.out.l14.setText("Pt0 kPa") ;
            break ;
          }
       }
 
       athroat = aths * aconv ;
       athmx = athms * aconv ;
       ptin = pts * pconv ;
       ttin = tts * tconv ;
       ttmx = ttmxs * tconv ;
       tcomb = ttcos * tconv ;
       ttab = ttabs * tconv ;
       psin = pss * pconv ;
       ptmx = ptmxs * pconv ;
       altin = altns * lconv ;
       altmx = altmxs * lconv ;
       athmn = .01 * aconv ;
       ptmn = 1. * pconv ;
       pemx = 15. * pconv ;
       pemn = 0.0 * pconv ;
       ttmn = 500. * tconv ;

       pn.in.geo.l.f1.setText(String.valueOf((float) athroat)) ;
       pn.in.flo.l.f1.setText(String.valueOf((float) ptin)) ;
       pn.in.flo.l.f2.setText(String.valueOf((float) ttin)) ;
       if (flomode ==  0) pn.in.flo.l.f3.setText(String.valueOf(filter3(psin))) ;
       if (flomode ==  1) pn.in.flo.l.f3.setText(String.valueOf(filter0(altin))) ;
       pn.in.chem.r.f6.setText(String.valueOf(filter0(tcomb))) ;

       i1 = (int) (((athroat - athmn)/(athmx-athmn))*1000.) ;
       i2 = (int) (((ptin - ptmn)/(ptmx-ptmn))*1000.) ;
       i3 = (int) (((ttin - ttmn)/(ttmx-ttmn))*1000.) ;
       i4 = (int) (((psin - pemn)/(pemx-pemn))*1000.) ;
       if (flomode == 1) i4 = (int) (((altin - altmn)/(altmx-altmn))*1000.) ;

       pn.in.geo.r.s1.setValue(i1) ;
       pn.in.flo.r.s1.setValue(i2) ;
       pn.in.flo.r.s2.setValue(i3) ;
       pn.in.flo.r.s3.setValue(i4) ;

       return ;
    }

    public void loadGeom() {
       double x0,y0,x01,y01,xth,yth,xth1,yth1,xex,yex,xex1,yex1 ;
       double yns,xns ;
       double factor ;
       int i,j ;

       factor = athroat/aconv ;

   // rocket nozzle geometry
       y0   = factor * 1.0 ;                  
       x0   = factor * rzero ;  
       y01  = factor * 20.0 * .5 ;   
       x01  = factor * rzero ;
       yth  = factor * 60.0 * .5 ;   
       xth  = factor * rthrt ;
       yth1 = factor * 80.0 * .5 ;  
       xth1 = factor * rthrt ;
       yex  = factor * 180.0 * (.5 + lngth) ;  
       xex  = factor * rexit ;
       yex1 = factor * 200.0 * (.5 + lngth) ; 
       xex1 = factor * rexit ;

   // shock in nozzle location
       if (mode == 2) {
          xns = factor * rns ;
          lns = lngth * rns / rexit ;
          yns = factor * 180.0 * (.5 + lns) ;
          xg[0][44] = xns ;
          yg[0][44] = yns ;
       }

       for(i=0; i<=5; ++ i) {   //  basic geometry
           xg[0][i] = i * (x01 - x0)/5.0 + x0 ;
           yg[0][i] = i * (y01 - y0)/5.0 + y0 ;
       } 
       for(i=6; i<=14; ++ i) {
           xg[0][i] = (i-5) * (xth - x01)/9.0 + x01 ;
           yg[0][i] = (i-5) * (yth - y01)/9.0 + y01 ;
       } 
       for(i=15; i<=18; ++ i) {
           xg[0][i] = (i-14) * (xth1 - xth)/4.0 + xth ;
           yg[0][i] = (i-14) * (yth1 - yth)/4.0 + yth ;
       } 
       for(i=19; i<=28; ++ i) {
           xg[0][i] = (i-18) * (xex - xth1)/10.0 + xth1 ;
           yg[0][i] = (i-18) * (yex - yth1)/10.0 + yth1 ;
       } 
       for(i=29; i<=34; ++ i) {
           xg[0][i] = (i-28) * (xex1 - xex)/3.0 + xex ;
           yg[0][i] = (i-28) * (yex1 - yex)/3.0 + yex ;
       } 

       for (j=1; j<=5; ++ j) {  // lower flow
           for(i=0; i<=34; ++ i) {
              xg[j][i] = (1.0 - .2 * j) * xg[0][i] ;
              yg[j][i] = yg[0][i] ;
           }
       }

       for (j=6; j<=10; ++ j) {  // mirror
           for(i=0; i<=34; ++ i) {
              xg[j][i] = -xg[10-j][i] ;
              yg[j][i] = yg[0][i] ;
           }
       }
   }

   class Pn extends Panel {
        Rnoz outerparent ;
        Con con ;
        Out out ;
        In in ;

        Pn (Rnoz target) {

           outerparent = target ;
           setLayout(new GridLayout(3,1,5,5)) ;

           con = new Con(outerparent) ;
           in = new In(outerparent) ;
           out = new Out(outerparent) ;

           add(con) ;
           add(in) ;
           add(out) ;
        }

        class Con extends Panel {
           Rnoz outerparent ;
           Label l1,l2,l3 ;
           Button b3 ;
           Choice untch,inpch ;
           TextField o8,o4,o10 ;
        
           Con (Rnoz target) {

               outerparent = target ;
               setLayout(new GridLayout(4,4,5,5)) ;

               l1 = new Label("Rocket Thrust", Label.RIGHT) ;
               l1.setForeground(Color.red) ;

               l2 = new Label("Simulator", Label.LEFT) ;
               l2.setForeground(Color.red) ;
 
               l3 = new Label("Input Panel:", Label.RIGHT) ;
               l3.setForeground(Color.blue) ;

               b3 = new Button("Reset") ;
               b3.setBackground(Color.red) ;
               b3.setForeground(Color.white) ;

               untch = new Choice() ;
               untch.addItem("English") ;
               untch.addItem("Metric");
               untch.select(0) ;
     
               inpch = new Choice() ;
               inpch.addItem("Geometry") ;
               inpch.addItem("Flow");
               inpch.addItem("Propellants");
               inpch.select(0) ;
 
               o8 = new TextField() ;
               o8.setBackground(Color.black) ;
               o8.setForeground(Color.yellow) ;
       
               o4 = new TextField() ;
               o4.setBackground(Color.black) ;
               o4.setForeground(Color.yellow) ;
          
               o10 = new TextField() ;
               o10.setBackground(Color.black) ;
               o10.setForeground(Color.yellow) ;
          
               add(l1) ;
               add(l2) ;
               add(new Label(" ", Label.RIGHT)) ;
               add(b3) ;
   
               add(new Label("Thrust", Label.RIGHT)) ;
               add(o8) ;
               add(new Label("Units: ", Label.RIGHT)) ;
               add(untch) ;

               add(new Label("Flow", Label.RIGHT)) ;
               add(o4) ;
               add(new Label("Isp ", Label.RIGHT)) ;
               add(o10) ;

               add(new Label(" ", Label.RIGHT)) ;
               add(new Label(" ", Label.RIGHT)) ;
               add(l3) ;
               add(inpch) ;
            }
    
            public boolean action(Event evt, Object arg) {
                String label = (String)arg ;
                int  whichin ;
     
                if (evt.target instanceof Choice) {
                    lunits = untch.getSelectedIndex() ;
                    whichin = inpch.getSelectedIndex() ;

                    setUnits() ;
     
                    if (whichin == 0) {
                       layout.show(in, "first") ;
                    }
                    if (whichin == 1) {
                       layout.show(in, "second") ;
                    }
                    if (whichin == 2) {
                       layout.show(in, "third") ;
                    }

                    comPute() ;
                    return true ;
                }
    
                if(evt.target instanceof Button) {
                   if(label.equals("Reset")) {
                      setDefaults() ;

                      layout.show(in, "first") ;
                      untch.select(0) ;
                      inpch.select(0) ;
                      
                      in.chem.l.gamch.select(1) ;
                      in.chem.l.fuelch.select(4) ;
                      in.chem.r.oxch.select(1) ;
                      in.chem.l.tcomch.select(1) ;
                      in.chem.l.molch.select(1) ;
                      in.chem.r.f4.setBackground(Color.black) ;
                      in.chem.r.f4.setForeground(Color.yellow) ;
                      in.chem.r.f5.setBackground(Color.black) ;
                      in.chem.r.f5.setForeground(Color.yellow) ;
                      in.chem.r.f6.setBackground(Color.black) ;
                      in.chem.r.f6.setForeground(Color.yellow) ;
                      in.flo.l.floch.select(0) ;

                      setUnits() ;
                      loadInput() ;

                      comPute() ;
                   } 
                   return true ;
                }
 
                else return false ;
            }
 
        }  // end Con

        class In extends Panel {
             Rnoz outerparent ;
             Geo geo ;
             Flo flo ;
             Chem chem ;
     
             In (Rnoz target) {

                outerparent = target ;
                layout = new CardLayout() ;
                setLayout(layout) ;

                geo = new Geo(outerparent) ;
                flo = new Flo(outerparent) ;
                chem = new Chem(outerparent) ;

                add ("first", geo) ;
                add ("second", flo) ;
                add ("third", chem) ;
     
             }
 
             class Geo extends Panel {
                Rnoz outerparent ;
                L l ;
                R r ;
        
                Geo (Rnoz target) {

                   outerparent = target ;
                   setLayout(new GridLayout(1,2,5,5)) ;
 
                   l = new L(outerparent) ;
                   r = new R(outerparent) ;

                   add(l) ;
                   add(r) ;
                }
 
                class L extends Panel {
                      Rnoz outerparent ;
                      TextField f1,f2,f4;
                      Label li1,li2,li4 ;
       
                      L (Rnoz target) {
       
                        outerparent = target ;
                        setLayout(new GridLayout(4,2,2,5)) ;

                        f1 = new TextField(String.valueOf(athroat),5) ;
                        li1 = new Label("Ath sq in", Label.CENTER) ;

                        f2 = new TextField(String.valueOf(arat),5) ;
                        li2 = new Label("Aex/Ath", Label.CENTER) ;

                        f4 = new TextField(String.valueOf(azrat),5) ;
                        li4 = new Label("Ao/Ath", Label.CENTER) ;

                        add(li1) ;
                        add(f1) ;

                        add(li2) ;
                        add(f2) ;

                        add(li4) ;
                        add(f4) ;

                        add(new Label(" ", Label.LEFT)) ;
                        add(new Label("Length", Label.RIGHT)) ;
                    }
 
                    public boolean handleEvent(Event evt) {
                        Double V1, V2, V4 ;
                        double v1, v2, v4 ;
                        float fl1 ;
                        int i1, i2, i3, i4 ;

                        if(evt.id == Event.ACTION_EVENT) {
                        // Throat area
                           V1 = Double.valueOf(f1.getText()) ;
                           v1 = V1.doubleValue() ;
                           if(v1 < athmn) {
                              v1 = athmn ;
                              fl1 = (float) v1 ;
                              f1.setText(String.valueOf(fl1)) ;
                           }
                           if(v1 > athmx) {
                              v1 = athmx ;
                              fl1 = (float) v1 ;
                              f1.setText(String.valueOf(fl1)) ;
                           }

                        // Exit area ratio
                           V2 = Double.valueOf(f2.getText()) ;
                           v2 = V2.doubleValue() ;
                           if(v2 < aexmn) {
                              v2 = aexmn ;
                              fl1 = (float) v2 ;
                              f2.setText(String.valueOf(fl1)) ;
                           }
                           if(v2 > aexmx) {
                              v2 = aexmx ;
                              fl1 = (float) v2 ;
                              f2.setText(String.valueOf(fl1)) ;
                           }

                        // Plenum area ratio
                           V4 = Double.valueOf(f4.getText()) ;
                           v4 = V4.doubleValue() ;
                           if(v4 < azmn) {
                              v4 = azmn ;
                              fl1 = (float) v4 ;
                              f4.setText(String.valueOf(fl1)) ;
                           }
                           if(v4 > azmx) {
                              v4 = azmx ;
                              fl1 = (float) v4 ;
                              f4.setText(String.valueOf(fl1)) ;
                           }
 
                           athroat = v1 ;
                           arat = v2 ;
                           azrat = v4 ;
                          
                           i1 = (int) (((v1 - athmn)/(athmx-athmn))*1000.) ;
                           i2 = (int) (((v2 - aexmn)/(aexmx-aexmn))*1000.) ;
                           i4 = (int) (((v4 - azmn)/(azmx-azmn))*1000.) ;

                           r.s1.setValue(i1) ;
                           r.s2.setValue(i2) ;
                           r.s4.setValue(i4) ;

                           i3 = r.s3.getValue() ;
                           lngmx = 20.0 * athroat/144./aconv ;
                           lngmn = .01 * athroat/144./aconv ;
                           lngth = i3 * (lngmx - lngmn)/ 1000. + lngmn ;

                           comPute() ;

                           return true ;
                        }
                        else return false ;
                    }
                }
 
                class R extends Panel {
                     Rnoz outerparent ;
                     Scrollbar s1, s2, s3, s4 ;

                     R (Rnoz target) {
                        int i1,i2,i3,i4 ;

                        outerparent = target ;
                        setLayout(new GridLayout(4,1,5,5)) ;

                        i1 = (int) ((((double)athroat - athmn)/(athmx-athmn))
                                       *1000.) ;
                        s1 = new Scrollbar(Scrollbar.HORIZONTAL,i1,10,0,1000);

                        i2 = (int) ((((double)arat - aexmn)/(aexmx-aexmn))*1000.);
                        s2 = new Scrollbar(Scrollbar.HORIZONTAL,i2,10,0,1000);
 
                        i3 = (int) ((((double)lngth - lngmn)/(lngmx-lngmn))*1000.) ;
                        s3 = new Scrollbar(Scrollbar.HORIZONTAL,i3,10,0,1000);

                        i4 = (int) ((((double)azrat - azmn)/(azmx-azmn))*1000.);
                        s4 = new Scrollbar(Scrollbar.HORIZONTAL,i4,10,0,1000);

                        add(s1) ;
                        add(s2) ;
                        add(s4) ;
                        add(s3) ;
                    }

                    public boolean handleEvent(Event evt) {
                         if(evt.id == Event.ACTION_EVENT) {
                            this.handleBar(evt) ;
                            return true ;
                         }
                         if(evt.id == Event.SCROLL_ABSOLUTE) {
                            this.handleBar(evt) ;
                            return true ;
                         }
                         if(evt.id == Event.SCROLL_LINE_DOWN) {
                            this.handleBar(evt) ;
                            return true ;
                         }
                         if(evt.id == Event.SCROLL_LINE_UP) {
                            this.handleBar(evt) ;
                            return true ;
                         }
                         if(evt.id == Event.SCROLL_PAGE_DOWN) {
                            this.handleBar(evt) ;
                            return true ;
                         }
                         if(evt.id == Event.SCROLL_PAGE_UP) {
                            this.handleBar(evt) ;
                            return true ;
                         }
                         else return false ;
                     }

                     public void handleBar(Event evt) {
                        int i1, i2, i3, i4 ;
                        Double V1, V2, V4 ;
                        double v1, v2, v4 ;
                        float fl1, fl2, fl4 ;

                        i1 = s1.getValue() ;
                        i2 = s2.getValue() ;
                        i4 = s4.getValue() ;
                        i3 = s3.getValue() ;

                        v1 = i1 * (athmx-athmn) / 1000. + athmn ;
                        v2 = i2 * (aexmx-aexmn) / 1000. + aexmn ;
                        v4 = i4 * (azmx-azmn) / 1000. + azmn ;

                        fl1 = (float) v1 ;
                        fl2 = (float) v2 ;
                        fl4 = (float) v4 ;

                        athroat = v1 ;
                        arat = v2 ;
                        azrat = v4 ;

                        lngmx = 20.0 * athroat/144./aconv ;
                        lngmn = .01 * athroat/144./aconv ;
                        lngth = i3 * (lngmx - lngmn)/ 1000. + lngmn ;

                        l.f1.setText(String.valueOf(filter1(fl1))) ;
                        l.f2.setText(String.valueOf(fl2)) ;
                        l.f4.setText(String.valueOf(fl4)) ;
                        
                        comPute() ;
                     }
                 }
             }

             class Flo extends Panel {
                Rnoz outerparent ;
                L l ;
                R r ;
        
                Flo (Rnoz target) {

                   outerparent = target ;
                   setLayout(new GridLayout(1,2,5,5)) ;
 
                   l = new L(outerparent) ;
                   r = new R(outerparent) ;

                   add(l) ;
                   add(r) ;
                }

                class L extends Panel {
                      Rnoz outerparent ;
                      TextField f1,f2,f3;
                      Label li1,li2,li3 ;
                      Choice floch ;
       
                      L (Rnoz target) {
       
                        outerparent = target ;
                        setLayout(new GridLayout(4,2,2,5)) ;

                        f1 = new TextField(String.valueOf(ptin),5) ;
                        li1 = new Label("Pto psi", Label.CENTER) ;

                        f2 = new TextField(String.valueOf(ttin),5) ;
                        li2 = new Label("Tto  F", Label.CENTER) ;

                        f3 = new TextField(String.valueOf(psin),5) ;
                        li3 = new Label("Pfs psi", Label.CENTER) ;

                        floch = new Choice() ;
                        floch.addItem("Pressure");
                        floch.addItem("Altitude") ;
                        floch.select(0) ;

                        add(li1) ;
                        add(f1) ;

                        add(li2) ;
                        add(f2) ;

                        add(new Label("Input:", Label.RIGHT)) ;
                        add(floch) ;

                        add(li3) ;
                        add(f3) ;
                    }
 
                    public boolean action(Event evt, Object arg) {
                      if(evt.target instanceof TextField) {
                          this.handleText(evt,arg) ;
                          return true ;
                      }
                      if(evt.target instanceof Choice) {
                         this.handleCho(evt,arg) ;
                         return true ;
                      }
                      else return false ;
                    }

                    public void handleCho(Event evt, Object arg) {
                      int i3 ;

                      flomode  = floch.getSelectedIndex() ;

                      if (flomode == 0) { // pressure input
                        if (lunits == 0) li3.setText("Pfs  psi") ;
                        if (lunits == 1) li3.setText("Pfs  kPa") ;
                        f3.setText(String.valueOf(filter3(psin))) ;
                      }
                      if (flomode == 1) { // altitude input
                        if (lunits == 0) li3.setText("Alt  ft") ;
                        if (lunits == 1) li3.setText("Alt  m") ;
                        f3.setText(String.valueOf(filter0(altin))) ;
                      }

                      i3 = (int) (((psin - pemn)/(pemx-pemn))*1000.) ;
                      if (flomode == 1) i3 = (int) (((altin - altmn)/(altmx-altmn))*1000.) ;
                      r.s3.setValue(i3) ;

                      comPute() ;
                    }

                    public void handleText(Event evt, Object arg) {
                        Double V1, V2, V3 ;
                        double v1, v2, v3 ;
                        float fl1 ;
                        int i1, i2, i3 ;

                   // total pressure
                        V1 = Double.valueOf(f1.getText()) ;
                        v1 = V1.doubleValue() ;
                        if(v1 < ptmn) {
                           v1 = ptmn ;
                           fl1 = (float) v1 ;
                           f1.setText(String.valueOf(fl1)) ;
                        }
                        if(v1 > ptmx) {
                           v1 = ptmx ;
                           fl1 = (float) v1 ;
                           f1.setText(String.valueOf(fl1)) ;
                        }
                        if(v1 < psin) {
                           v1 = psin ;
                           fl1 = (float) v1 ;
                           f1.setText(String.valueOf(fl1)) ;
                        }
                    // total temperature
                        V2 = Double.valueOf(f2.getText()) ;
                        v2 = V2.doubleValue() ;
                        if(v2 < ttmn) {
                           v2 = ttmn ;
                           fl1 = (float) v2 ;
                           f2.setText(String.valueOf(fl1)) ;
                        }
                        if(v2 > ttmx) {
                           v2 = ttmx ;
                           fl1 = (float) v2 ;
                           f2.setText(String.valueOf(fl1)) ;
                        }
           
                        i3 = 0 ;
                        if (flomode == 0) {
                       // exit pressure
                           V3 = Double.valueOf(f3.getText()) ;
                           v3 = V3.doubleValue() ;
                           if(v3 < pemn) {
                              v3 = pemn ;
                              fl1 = (float) v3 ;
                              f3.setText(String.valueOf(fl1)) ;
                           }
                           if(v3 > pemx) {
                              v3 = pemx ;
                              fl1 = (float) v3 ;
                              f3.setText(String.valueOf(fl1)) ;
                           }
                           if(v3 > v1) {
                              v3 = v1 ;
                              fl1 = (float) v3 ;
                              f3.setText(String.valueOf(fl1)) ;
                           }

                           psin = v3 ;
                           i3 = (int) (((v3 - pemn)/(pemx-pemn))*1000.) ;
                        }
                        if (flomode == 1) {
                       // altitude input
                           V3 = Double.valueOf(f3.getText()) ;
                           v3 = V3.doubleValue() ;
                           if(v3 < altmn) {
                              v3 = altmn ;
                              fl1 = (float) v3 ;
                              f3.setText(String.valueOf(fl1)) ;
                           }
                           if(v3 > altmx) {
                              v3 = altmx ;
                              fl1 = (float) v3 ;
                              f3.setText(String.valueOf(fl1)) ;
                           }

                           altin = v3 ;
                           i3 = (int) (((v3 - altmn)/(altmx-altmn))*1000.) ;
                         }
 
                         ptin = v1 ;
                         ttin = v2 ;
                          
                         i1 = (int) (((v1 - ptmn)/(ptmx-ptmn))*1000.) ;
                         i2 = (int) (((v2 - ttmn)/(ttmx-ttmn))*1000.) ;

                         r.s1.setValue(i1) ;
                         r.s2.setValue(i2) ;
                         r.s3.setValue(i3) ;

                         comPute() ;
                     } // end handle text
                } // end Left

                class R extends Panel {
                     Rnoz outerparent ;
                     Scrollbar s1, s2, s3 ;
                     TextField f4;

                     R (Rnoz target) {
                        int i1,i2, i3 ;

                        outerparent = target ;
                        setLayout(new GridLayout(4,1,5,5)) ;

                        i1 = (int) ((((double)ptin - ptmn)/(ptmx-ptmn))*1000.) ;
                        i2 = (int) ((((double)ttin - ttmn)/(ttmx-ttmn))*1000.) ;
                        i3 = (int) ((((double)psin - pemn)/(pemx-pemn))*1000.) ;

                        s1 = new Scrollbar(Scrollbar.HORIZONTAL,i1,10,0,1000);
                        s2 = new Scrollbar(Scrollbar.HORIZONTAL,i2,10,0,1000);
                        s3 = new Scrollbar(Scrollbar.HORIZONTAL,i3,10,0,1000);

                        add(s1) ;
                        add(s2) ;
                        add(new Label(" ", Label.RIGHT)) ;
                        add(s3) ;
                    }

                    public boolean handleEvent(Event evt) {
                        Double V2 ;
                        double v2 ;
                        float fl1 ;

                         if(evt.id == Event.ACTION_EVENT) {
                            return true ;
                         }
                         if(evt.id == Event.SCROLL_ABSOLUTE) {
                            this.handleBar(evt) ;
                            return true ;
                         }
                         if(evt.id == Event.SCROLL_LINE_DOWN) {
                            this.handleBar(evt) ;
                            return true ;
                         }
                         if(evt.id == Event.SCROLL_LINE_UP) {
                            this.handleBar(evt) ;
                            return true ;
                         }
                         if(evt.id == Event.SCROLL_PAGE_DOWN) {
                            this.handleBar(evt) ;
                            return true ;
                         }
                         if(evt.id == Event.SCROLL_PAGE_UP) {
                            this.handleBar(evt) ;
                            return true ;
                         }
                         else return false ;
                     }

                     public void handleBar(Event evt) {
                        int i1, i2, i3 ;
                        Double V1, V2, V3 ;
                        double v1, v2, v3 ;
                        float fl1, fl2, fl3 ;

                        i1 = s1.getValue() ;
                        i2 = s2.getValue() ;
                        i3 = s3.getValue() ;

                        v1 = i1 * (ptmx-ptmn) / 1000. + ptmn ;
                        if (v1 < psin) v1 = psin ;
                        v3 = 0.0 ;
                        if (flomode == 0) {
                           v3 = i3 * (pemx-pemn) / 1000. + pemn ;
                           if (v3 > v1) v3 = v1 ;
                        }
                        if (flomode == 1) {
                           v3 = i3 * (altmx-altmn) / 1000. + altmn ;
                        }
                        v2 = i2 * (ttmx-ttmn) / 1000. + ttmn ;

                        fl1 = (float) v1 ;
                        fl2 = (float) v2 ;
                        fl3 = (float) v3 ;

                        ptin = v1 ;
                        ttin = v2 ;
                        if (flomode == 0) psin = v3 ;
                        if (flomode == 1) altin = v3 ;
 
                        l.f1.setText(String.valueOf(fl1)) ;
                        l.f2.setText(String.valueOf(fl2)) ;
                        l.f3.setText(String.valueOf(fl3)) ;
                        
                        comPute() ;
                     }
                 }
             }

             class Chem extends Panel {
                Rnoz outerparent ;
                L l ;
                R r ;
        
                Chem (Rnoz target) {

                   outerparent = target ;
                   setLayout(new GridLayout(1,2,5,5)) ;
 
                   l = new L(outerparent) ;
                   r = new R(outerparent) ;

                   add(l) ;
                   add(r) ;
                }

                class L extends Panel {
                   Rnoz outerparent ;
                   Choice gamch,fuelch,tcomch, molch ;
       
                   L (Rnoz target) {
       
                        outerparent = target ;
                        setLayout(new GridLayout(5,2,2,5)) ;

                        gamch = new Choice() ;
                        gamch.addItem("Input") ;
                        gamch.addItem("Gam(T)");
                        gamch.select(1) ;

                        molch = new Choice() ;
                        molch.addItem("Input");
                        molch.addItem("Table") ;
                        molch.select(1) ;

                        fuelch = new Choice() ;
                        fuelch.addItem("Air") ;
                        fuelch.addItem("Al-NH4ClO4") ;
                        fuelch.addItem("H2O2-mono");
                        fuelch.addItem("N2H4");
                        fuelch.addItem("Liquid H2");
                        fuelch.addItem("JP 4");
                        fuelch.addItem("Kerosene");
                        fuelch.addItem("UDMH");
                        fuelch.select(4) ;

                        tcomch = new Choice() ;
                        tcomch.addItem("Input") ;
                        tcomch.addItem("Table");
                        tcomch.select(1) ;

                        add(new Label("Fuel", Label.RIGHT)) ;
                        add(fuelch) ;

                        add(new Label(" ", Label.RIGHT)) ;
                        add(new Label("Ox / Fuel Ratio", Label.RIGHT)) ;

                        add(new Label("Mol. Wt. Ex", Label.RIGHT)) ;
                        add(molch) ;

                        add(new Label("Gamma:", Label.RIGHT)) ;
                        add(gamch) ;

                        add(new Label("T combust", Label.RIGHT)) ;
                        add(tcomch) ;
                   }
 
                   public boolean handleEvent(Event evt) {
                        int i6 ;
                        double v6 ;
                        float fl6 ;
                        
                        if(evt.id == Event.ACTION_EVENT) {
                          molopt  = molch.getSelectedIndex() ;
                          gamopt  = gamch.getSelectedIndex() ;
                          tcomopt = tcomch.getSelectedIndex() ;

                          fuelopt = fuelch.getSelectedIndex() ;
                         
                          if (fuelopt == fuelold ) {
                 // molecular weight options
                             if (molopt == 0) {  // input
                                r.f5.setBackground(Color.white) ;
                                r.f5.setForeground(Color.black) ;
                             }
                             if (molopt == 1) {  // specified
                                r.f5.setBackground(Color.black) ;
                                r.f5.setForeground(Color.yellow) ;
                             }
                 // gamma  options
                             if (gamopt == 0) {  // input
                                r.f4.setBackground(Color.white) ;
                                r.f4.setForeground(Color.black) ;
                                gamma = gam0 ;
                             }
   
                             if (gamopt == 1) { // specified
                                r.f4.setBackground(Color.black) ;
                                r.f4.setForeground(Color.yellow) ;
                             }

                 // combustion temperature options
                             if (tcomopt == 0) { // input
                                r.f6.setBackground(Color.white) ;
                                r.f6.setForeground(Color.black) ;
                             }
   
                             if (tcomopt == 1) {  // specified
                                r.f6.setBackground(Color.black) ;
                                r.f6.setForeground(Color.yellow) ;
                             }
                          }
                          
            // change fuel options
                          else {
                             fuelold = fuelopt ;
                             if (fuelopt == 0) {  // air
                                oxopt = 0 ;
                                mwtab = 29.0 ;
                                if (gamopt == 1) gam0 = 1.4 ;
                                ttab = 4000. * tconv ;
                             }
                             if (fuelopt == 1) {  // solid - aluminum
                                oxopt = 0 ;
                                mwtab = 29.4 ;
                                if (gamopt == 1) gam0 = 1.24 ;
                                ttab = 5846. * tconv ;
                             }
                             if (fuelopt == 2) {  // hydrogen peroxide
                                oxopt = 0 ;
                                mwtab = 22.2 ;
                                if (gamopt == 1) gam0 = 1.32 ;
                                ttab = 1450. * tconv ;
                             }
                             if (fuelopt == 3) {  // hydrazine
                                oxopt = 0 ;
                                mwtab = 32.1 ;
                                if (gamopt == 1) gam0 = 1.42 ;
                                ttab = 1125. * tconv ;
                             }
                             if (fuelopt == 4) {  // H2
                                    // default oxidizer is O2 
                                oxopt = 1 ;
                                mwtab = 16.0 ;
                                if (gamopt == 1) gam0 = 1.32 ;
                                ttab = 5870. * tconv ;
                                ofrat = 8.0 ;
                             }
                             if (fuelopt == 5) {  // JP
                                    // default oxidizer is O2
                                oxopt = 1 ;
                                mwtab = 22.0 ;
                                if (gamopt == 1) gam0 = 1.34 ;
                                ttab = 5770. * tconv ;
                                ofrat = 2.3 ;
                             }
                             if (fuelopt == 6) {  // Kerosene
                                    // default oxidizer is O2
                                oxopt = 1 ;
                                mwtab = 22.0 ;
                                if (gamopt == 1) gam0 = 1.34 ;
                                ttab = 5825. * tconv ;
                                ofrat = 2.3 ;
                             }
                             if (fuelopt == 7) {  // UDMH
                                    // default oxidizer is RFNA
                                oxopt = 5 ;
                                mwtab = 22.0 ;
                                if (gamopt == 1) gam0 = 1.34 ;
                                ttab = 5200. * tconv ;
                                ofrat = 2.6 ;
                             }
   
                             r.oxch.select(oxopt) ;
   
                             if (fuelopt == 4) laycho.show(in.chem.r.o, "second");
                             else laycho.show(in.chem.r.o, "first") ;
    

                             if (oxopt == 0) {
                                r.f7.setText("NA") ;
                             }
                             if (oxopt >= 1) {
                                r.f7.setText(String.valueOf(ofrat)) ;
                             }
                          }

                          if (molopt == 1) mweight = mwtab;

                          if (tcomopt == 1) tcomb = ttab ;
                          ttin = tcomb ;
                          i6 = (int) (((ttin - ttmn)/(ttmx - ttmn))*1000.) ;
                          v6 = i6 * (ttmx-ttmn) / 1000. + ttmn ;
                          fl6 = (float) v6 ;
                          pn.in.flo.l.f2.setText(String.valueOf(fl6)) ;
                          pn.in.flo.r.s2.setValue(i6) ;

                          r.f6.setText(String.valueOf(filter0(tcomb))) ;
                          r.f5.setText(String.valueOf(mweight)) ;
                          r.f4.setText(String.valueOf(gam0)) ;
   
                          comPute();
                          return true ;
                        }
                        else  return false;
                    }
                }

                class R extends Panel {
                     Rnoz outerparent ;
                     TextField f4,f5,f6,f7;
                     Choice oxch,ofch ;
                     Label l6;
                     O o ;

                     R (Rnoz target) {
                        int i1,i2, i3 ;

                        outerparent = target ;
                        setLayout(new GridLayout(5,2,5,5)) ;

                        f4 = new TextField(String.valueOf((float)gamma),5) ;
                        f4.setBackground(Color.black) ;
                        f4.setForeground(Color.yellow) ;

                        f5 = new TextField(String.valueOf((float)mweight),5) ;
                        f5.setBackground(Color.black) ;
                        f5.setForeground(Color.yellow) ;

                        f6 = new TextField(String.valueOf((float)tcomb),5) ;
                        f6.setBackground(Color.black) ;
                        f6.setForeground(Color.yellow) ;

                        f7 = new TextField(String.valueOf((float)ofrat),5) ;
                        f7.setBackground(Color.black) ;
                        f7.setForeground(Color.yellow) ;

                        o = new O(outerparent) ;

                        oxch = new Choice() ;
                        oxch.addItem(" NONE ");
                        oxch.addItem("Liquid O2");
                        oxch.addItem("Liquid F");
                        oxch.addItem("H2O2");
                        oxch.addItem("N2O");
                        oxch.addItem("RFNA");
                        oxch.select(1) ;

                        l6 = new Label("F", Label.LEFT) ;

                        add(new Label("Oxidizer ", Label.RIGHT)) ;
                        add(oxch) ;

                        add(f7) ;
                        add(o) ;

                        add(f5) ;
                        add(new Label(" ", Label.RIGHT)) ;

                        add(f4) ;
                        add(new Label(" ", Label.RIGHT)) ;

                        add(f6) ;
                        add(l6) ;
                    }

                    public boolean action(Event evt, Object arg) {
                      if(evt.target instanceof TextField) {
                          this.handleText(evt,arg) ;
                          return true ;
                      }
                      if(evt.target instanceof Choice) {
                         this.handleCho(evt,arg) ;
                         return true ;
                      }
                      else return false ;
                    }

                    public void  handleCho(Event evt, Object arg) {
                       int i6 ;
                       double v6 ;
                       float fl6 ;
        // oxidizer options
                      oxopt  = oxch.getSelectedIndex() ;
        // resets for allowed combinations
                      if (fuelopt <= 2) {  // reset for air, mono-props
                         oxopt = 0 ;
                         oxch.select(oxopt) ;
                      }
                      if (fuelopt == 3) {  // resets for N2H4
                         if (oxopt == 2) {   // do not allow F
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 4) {   // do not allow N2O
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 5) {  // do not allow RFNA
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                      }
                      if (fuelopt == 4) {  // resets for H2
                         if (oxopt == 0) {   // do not allow none
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 3) {   // do not allow H2O2
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 4) {   // do not allow N2O
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 5) {  // do not allow RFNA
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                      }
                      if (fuelopt == 5) {  // resets for JP-4
                         if (oxopt == 0) {   // do not allow none
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 2) {   // do not allow F
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 4) {   // do not allow N2O
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 5) {  // do not allow RFNA
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                      }
                      if (fuelopt == 6) {  // resets for Kerosene
                         if (oxopt == 0) {   // do not allow none
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 2) {   // do not allow F
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 3) {   // do not allow H2O2
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 5) {  // do not allow RFNA
                            oxopt = 1 ;
                            oxch.select(oxopt) ;
                         }
                      }
                      if (fuelopt == 7) {  // resets for UDMH
                         if (oxopt == 0) {   // do not allow none
                            oxopt = 5 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 1) {  // do not allow O2
                            oxopt = 5 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 2) {   // do not allow F
                            oxopt = 5 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 3) {   // do not allow H2O2
                            oxopt = 5 ;
                            oxch.select(oxopt) ;
                         }
                         if (oxopt == 4) {   // do not allow N2O
                            oxopt = 5 ;
                            oxch.select(oxopt) ;
                         }
                      }
        // set default mweights, gam0 , tcomb
                      if (oxopt == 1) {       // oxygen
                         if (fuelopt == 3) { // hydrazine
                             mwtab = 18.0 ;
                             if (gamopt == 1) gam0 = 1.35 ;
                             ttab = 5370. * tconv ;
                             ofrat = .75 ;
                         }
                         if (fuelopt == 4) { // hydrogen
                              // default ofrat 8.0
                             ofopt = 0 ;
                             o.y.ofch.select(ofopt) ;
                             mwtab = 16.0 ;
                             if (gamopt == 1) gam0 = 1.32 ;
                             ttab = 5870. * tconv ;
                             ofrat = 8.0 ;
                         }
                         if (fuelopt == 5) { // JP-4
                             mwtab = 22.0 ;
                             if (gamopt == 1) gam0 = 1.34 ;
                             ttab = 5770. * tconv ;
                             ofrat = 2.3 ;
                         }
                         if (fuelopt == 6) { // Kerosene
                             mwtab = 22.0 ;
                             if (gamopt == 1) gam0 = 1.34 ;
                             ttab = 5825. * tconv ;
                             ofrat = 2.3 ;
                         }
                      }

                      if (oxopt == 2) {       // flourine
                         if (fuelopt == 4) { // hydrogen
                              // default ofrat 9.42
                             ofopt = 0 ;
                             o.y.ofch.select(ofopt) ;
                             mwtab = 10.0 ;
                             if (gamopt == 1) gam0 = 1.42 ;
                             ttab = 8100. * tconv ;
                             ofrat = 9.42 ;
                         }
                      }

                      if (oxopt == 3) {       // hydrogen peroxide
                         if (fuelopt == 3) { // hydrazine
                             mwtab = 19.0 ;
                             if (gamopt == 1) gam0 = 1.32 ;
                             ttab = 4690. * tconv ;
                             ofrat = 1.7 ;
                         }
                         if (fuelopt == 5) { // JP-4
                             mwtab = 22.0 ;
                             if (gamopt == 1) gam0 = 1.3 ;
                             ttab = 5770. * tconv ;
                             ofrat = 2.3 ;
                         }
                      }

                      if (oxopt == 4) {       // nitrous oxide
                         if (fuelopt == 6) { // Kerosene
                             mwtab = 25.3 ;
                             if (gamopt == 1) gam0 = 1.34 ;
                             ttab = 5461. * tconv ;
                             ofrat = 6.5 ;
                         }
                      }

                      if (oxopt == 5) {       // RFNA
                         if (fuelopt == 7) { // UDMH
                             mwtab = 22.0 ;
                             if (gamopt == 1) gam0 = 1.34 ;
                             ttab = 5200. * tconv ;
                             ofrat = 2.6 ;
                         }
                      }
        // show the O/F options button
                      laycho.show(in.chem.r.o, "first") ;

                      if (fuelopt == 4) laycho.show(in.chem.r.o, "second") ;
        // show the default values
                      if (tcomopt == 1) tcomb = ttab ;
                      ttin = tcomb ;
                      i6 = (int) (((ttin - ttmn)/(ttmx - ttmn))*1000.) ;
                      v6 = i6 * (ttmx-ttmn) / 1000. + ttmn ;
                      fl6 = (float) v6 ;
                      pn.in.flo.l.f2.setText(String.valueOf(fl6)) ;
                      pn.in.flo.r.s2.setValue(i6) ;

                      if (molopt == 1) mweight = mwtab;

                      f6.setText(String.valueOf(filter0(tcomb))) ;
                      f5.setText(String.valueOf(mweight)) ;
                      f4.setText(String.valueOf(gam0)) ;

                      if (oxopt == 0) {
                         f7.setText("NA") ;
                      }
                      if (oxopt >= 1) {
                         f7.setText(String.valueOf(ofrat)) ;
                      }

                      comPute();
                    } // end handler

                    public void  handleText(Event evt, Object arg) {
                        Double V4,V5,V6 ;
                        double v4,v5,v6 ;
                        float fl1 ;
                        int i1, i2, i3 ;
                        
                        if (gamopt == 0) {

                           V4 = Double.valueOf(f4.getText()) ;
                           v4 = V4.doubleValue() ;
   
                           gamma = v4 ;
                           if(v4 < 1.0) {
                              gamma = v4 =  1.0 ;
                              fl1 = (float) v4 ;
                              f4.setText(String.valueOf(fl1)) ;
                           }
                           if(v4 > 2.0) {
                              gamma = v4 = 2.0 ;
                              fl1 = (float) v4 ;
                              f4.setText(String.valueOf(fl1)) ;
                           }
                        }

                        if (molopt == 0) {

                           V5 = Double.valueOf(f5.getText()) ;
                           v5 = V5.doubleValue() ;
   
                           mweight = v5 ;
                           if(v5 < 1.0) {
                              mweight = v5 =  1.0 ;
                              fl1 = (float) v5 ;
                              f5.setText(String.valueOf(fl1)) ;
                           }
                           if(v5 > 50.0) {
                              mweight = v5 = 50.0 ;
                              fl1 = (float) v5 ;
                              f5.setText(String.valueOf(fl1)) ;
                           }
                        }

                        if (tcomopt == 0) {

                           V6 = Double.valueOf(f6.getText()) ;
                           v6 = V6.doubleValue() ;
 
                           tcomb = v6 ;
                           if(v6 < ttmn) {
                              tcomb = v6 =  ttmn ;
                              fl1 = (float) v6 ;
                              f6.setText(String.valueOf(fl1)) ;
                           }
                           if(v6 > ttmx) {
                              tcomb = v6 = ttmx ;
                              fl1 = (float) v6 ;
                              f6.setText(String.valueOf(fl1)) ;
                           }

                           ttin = tcomb ;
                           i3 = (int) (((ttin - ttmn)/(ttmx - ttmn))*1000.) ;
                           fl1 = (float) v6 ;
                           pn.in.flo.l.f2.setText(String.valueOf(fl1)) ;
                           pn.in.flo.r.s2.setValue(i3) ;

                        }

                        comPute();
                    }  // end handler
 
                    class O extends Panel {
                        Rnoz outerparent ;
                        Y y ;
                        N n ;

                        O (Rnoz target) {
                           outerparent = target ;
                           laycho = new CardLayout() ;
                           setLayout(laycho) ;

                           y  = new Y(outerparent) ;
                           n = new N(outerparent) ;

                           add ("second", y) ;
                           add ("first", n) ;
                        }

                        class Y extends Panel {  // show oxidizer fuel ratio
                           Rnoz outerparent ;
                           Choice ofch ;

                           Y (Rnoz target) {
                              outerparent = target ;
                              setLayout(new GridLayout(1,1,0,0)) ;

                              ofch = new Choice() ;
                              ofch.addItem("Option 1");
                              ofch.addItem("Option 2");
                              ofch.addItem("Option 3");
                              ofch.select(0) ;

                              add(ofch) ;
                           }

                           public boolean action(Event evt, Object arg) {
                              int i6 ;
                              double v6 ;
                              float fl6 ;

                              if(evt.target instanceof Choice) {
                                  // oxidizer/fuel ratio  options
                                 ofopt  = ofch.getSelectedIndex() ;

                                 if (oxopt == 1) {  // hydrogen - oxygen
                                   if (ofopt == 0) {   // ofrat = 8
                                      mwtab = 16.0 ;
                                      if (gamopt == 1) gam0 = 1.32 ;
                                      ttab = 5870. * tconv ;
                                      ofrat = 8.0 ;
                                   }
                                   if (ofopt == 1) {   // ofrat = 6
                                      mwtab = 13.0 ;
                                      if (gamopt == 1) gam0 = 1.32 ;
                                      ttab = 5500. * tconv ;
                                      ofrat = 6.0 ;
                                   }
                                   if (ofopt == 2) {   // ofrat = 4
                                      mwtab = 10.0 ;
                                      if (gamopt == 1) gam0 = 1.32 ;
                                      ttab = 5000. * tconv ;
                                      ofrat = 4.0 ;
                                   }
                                 }
                                 if (oxopt == 2) {  // hydrogen - flourine
                                   if (ofopt == 0) {   // ofrat = 9.4
                                      mwtab = 10.0 ;
                                      if (gamopt == 1) gam0 = 1.42 ;
                                      ttab = 8100. * tconv ;
                                      ofrat = 9.42 ;
                                   }
                                   if (ofopt == 1) {   // ofrat = 3.8
                                      mwtab = 7.8 ;
                                      if (gamopt == 1) gam0 = 1.42 ;
                                      ttab = 4600. * tconv ;
                                      ofrat = 3.8 ;
                                   }
                                 }
        // show the default values
                                 if (tcomopt == 1) tcomb = ttab ;
                                 ttin = tcomb ;
                                 i6 = (int) (((ttin - ttmn)/(ttmx - ttmn))*1000.) ;
                                 v6 = i6 * (ttmx-ttmn) / 1000. + ttmn ;
                                 fl6 = (float) v6 ;
                                 pn.in.flo.l.f2.setText(String.valueOf(fl6)) ;
                                 pn.in.flo.r.s2.setValue(i6) ;

                                 if (molopt == 1) mweight = mwtab;

                                 f6.setText(String.valueOf(filter0(tcomb))) ;
                                 f5.setText(String.valueOf(mweight)) ;
                                 f4.setText(String.valueOf(gam0)) ;

                                 if (oxopt == 0) {
                                    f7.setText("NA") ;
                                 }
                                 if (oxopt >= 1) {
                                    f7.setText(String.valueOf(ofrat)) ;
                                 }

                                 comPute();
                                 return true ;
                              }
                              else return false ;
                           }
                        }  // end Yes Show

                        class N extends Panel {
                           Rnoz outerparent ;
                           Label l2 ;

                           N (Rnoz target) {
                              outerparent = target ;
                              setLayout(new GridLayout(1,1,5,5)) ;

                              l2 = new Label(" ", Label.RIGHT) ;

                              add(l2) ;
                           }
                        }  // No Show
                    } // end O
                 } // end R
             } // Chem
        } // end In

        class Out extends Panel {
           Rnoz outerparent ;
           Label l12,l14,l2 ; 
           TextField o9, o11, o12 ;
           TextField o1, o3, o6 ;
           TextField o2, o18, o13 ;
           TextField o5, o7, o14 ;
           TextField o15, o16, o17 ;
    
           Out (Rnoz target) {

              outerparent = target ;
              setLayout(new GridLayout(5,6,2,2)) ;
 
              l2 = new Label("Uex fps", Label.RIGHT) ;
              l12 = new Label("Ath sq in", Label.RIGHT) ;
              l14 = new Label("Pt0 psi", Label.RIGHT) ;

              o2 = new TextField() ;
              o2.setBackground(Color.black) ;
              o2.setForeground(Color.yellow) ;
              o18 = new TextField() ;
              o18.setBackground(Color.black) ;
              o18.setForeground(Color.yellow) ;
              o13 = new TextField() ;
              o13.setBackground(Color.black) ;
              o13.setForeground(Color.yellow) ;
      
              o5 = new TextField() ;
              o5.setBackground(Color.black) ;
              o5.setForeground(Color.yellow) ;
              o7 = new TextField() ;
              o7.setBackground(Color.black) ;
              o7.setForeground(Color.yellow) ;
              o14 = new TextField() ;
              o14.setBackground(Color.black) ;
              o14.setForeground(Color.yellow) ;
      
              o9 = new TextField() ;
              o9.setBackground(Color.black) ;
              o9.setForeground(Color.yellow) ;
              o11 = new TextField() ;
              o11.setBackground(Color.black) ;
              o11.setForeground(Color.yellow) ;
              o12 = new TextField() ;
              o12.setBackground(Color.black) ;
              o12.setForeground(Color.yellow) ;
      
              o15 = new TextField() ;
              o15.setBackground(Color.black) ;
              o15.setForeground(Color.yellow) ;
              o16 = new TextField() ;
              o16.setBackground(Color.black) ;
              o16.setForeground(Color.yellow) ;
              o17 = new TextField() ;
              o17.setBackground(Color.black) ;
              o17.setForeground(Color.yellow) ;
      
              o1 = new TextField() ;
              o1.setBackground(Color.black) ;
              o1.setForeground(Color.yellow) ;
              o3 = new TextField() ;
              o3.setBackground(Color.black) ;
              o3.setForeground(Color.yellow) ;
              o6 = new TextField() ;
              o6.setBackground(Color.black) ;
              o6.setForeground(Color.yellow) ;
      
              add(new Label("Fuel", Label.RIGHT)) ;
              add(o16) ;
              add(new Label("Oxidizer", Label.RIGHT)) ;
              add(o17) ;
              add(new Label("O/F ", Label.RIGHT)) ;
              add(o18) ;

              add(new Label("Tt0", Label.RIGHT)) ;
              add(o11) ;
              add(new Label("Gamma", Label.RIGHT)) ;
              add(o9) ;
              add(new Label("Mol Wt", Label.RIGHT)) ;
              add(o15) ;

              add(l14) ;
              add(o14) ;
              add(new Label("Pex", Label.RIGHT)) ;
              add(o5) ;
              add(new Label("Pfs", Label.RIGHT)) ;
              add(o13) ;

              add(l12) ;
              add(o12) ;
              add(new Label("Aex", Label.RIGHT)) ;
              add(o7) ;
              add(new Label("NPR", Label.RIGHT)) ;
              add(o1) ;

              add(new Label("Mth", Label.RIGHT)) ;
              add(o3) ;
              add(new Label("Mex", Label.RIGHT)) ;
              add(o6) ;
              add(l2) ;
              add(o2) ;
           }
       }  // end Out
  }

  class Viewer extends Canvas 
         implements Runnable{
     Rnoz outerparent ;
     Thread runner ;
     Point locate,anchor;

     Viewer (Rnoz target) {
         setBackground(Color.white) ;
         runner = null ;
     }

     public boolean mouseDown(Event evt, int x, int y) {
        anchor = new Point(x,y) ;
        return true;
     }

     public boolean mouseUp(Event evt, int x, int y) {
        handleb(x,y) ;
        return true;
     }

     public boolean mouseDrag(Event evt, int x, int y) {
        handle(x,y) ;
        return true;
     }

     public void handle(int x, int y) {
         // determine location
         if (y >= 30) {
             if (x >= 30 ) {   // translate
                locate = new Point(x,y) ;
                yt =  yt + (int) (.2*(locate.y - anchor.y)) ;
                xt =  xt + (int) (.4*(locate.x - anchor.x))  ;
                if (xt > 320) xt = 320 ;
                if (xt < -280) xt = -280 ;
                if (yt > 300) yt = 300 ;
                if (yt <-300) yt = -300 ;
             }
             if (x < 30 ) {   // zoom widget
               sldloc = y ;
               if (sldloc < 30) sldloc = 30;
               if (sldloc > 315) sldloc = 315;
               fact = .1 + (sldloc-30)*.1 ;
             }
         }
     }
     public void handleb(int x, int y) {
         if (y < 15) {
             if (x <= 30) {   //find
                 fact = .2 ;
                 xt = 40;  yt = 0; 
                 sldloc = 40 ;
             }
         }
         view.repaint() ;
     }

     public void start() {
        if (runner == null) {
           runner = new Thread(this) ;
           runner.start() ;
        }
        antim = 0 ;                
        ancol = 1 ;                        
     }

     public void run() {
       int timer ;

       timer = 100 ;
       while (true) {
          ++ antim ;
          try { Thread.sleep(timer); }
          catch (InterruptedException e) {}
          view.repaint() ;
          if (antim == 3) {
             antim = 0;
             ancol = - ancol ;       
          }
       }
     }

     public void update(Graphics g) {
         view.paint(g) ;
     }

     public void paint(Graphics g) {

       int exes[] = new int[10] ;
       int whys[] = new int[10] ;
       int yorgn = 170 ;
       int xorgn = 150 ;
       int i,j ;
       int npts ;

       offsGg.setColor(Color.black) ;
       offsGg.fillRect(0,0,500,900) ;
       
       xorgn = 150 ;
       yorgn = 0 ;

       npts = 30 ;

   /*  geometry */
       exes[1] = xorgn + (int) (.05*fact*xg[0][0]) + xt ;
       whys[1] = yorgn + (int) (.05*fact*yg[0][0]) + yt ;
       exes[2] = exes[1] + 5 ;
       whys[2] = whys[1] ;
       for (i=1 ; i<= npts; ++i) { 
          exes[0] = exes[1] ;
          whys[0] = whys[1] ;
          exes[1] = xorgn + (int) (.05*fact*xg[0][i]) + xt ;
          whys[1] = yorgn + (int) (.05*fact*yg[0][i]) + yt ;
          exes[3] = exes[2] ;
          whys[3] = whys[2] ;
          exes[2] = exes[1] + 5 ;
          whys[2] = whys[1] ;
               /* labels */
          if (i == npts) {
             offsGg.setColor(Color.white) ;
             offsGg.drawString("Exit-ex",exes[2],whys[2]+20) ;
             offsGg.drawString("Free Stream - fs",exes[2]-20,whys[2]+70) ;
          }
          if (i == 15) {
             offsGg.setColor(Color.white) ;
             if (mode ==0)offsGg.drawString("Under expanded",exes[2]+5,whys[2]+10);
             if (mode ==1)offsGg.drawString("Over expanded",exes[2]+5,whys[2]+10);
             if (mode ==2) {
                offsGg.setColor(Color.cyan) ;
                offsGg.drawString("Shock in Nozzle",exes[2]+5,whys[2]+10);
             }
          }
          offsGg.setColor(Color.white) ;
          offsGg.fillPolygon(exes,whys,4) ;
       }

       exes[1] = xorgn + (int) (.05*fact*xg[10][0]) + xt ;
       whys[1] = yorgn + (int) (.05*fact*yg[10][0]) + yt ;

       exes[2] = exes[1] - 5 ;
       whys[2] = whys[1] ;
       /* labels */
       offsGg.setColor(Color.white) ;
       offsGg.drawString("Plenum-0",exes[2]-65,whys[2]+20) ;

       for (i=1 ; i<= npts; ++i) { 
          exes[0] = exes[1] ;
          whys[0] = whys[1] ;
          exes[1] = xorgn + (int) (.05*fact*xg[10][i]) + xt ;
          whys[1] = yorgn + (int) (.05*fact*yg[10][i]) + yt ;
          exes[3] = exes[2] ;
          whys[3] = whys[2] ;
          exes[2] = exes[1] - 5 ;
          whys[2] = whys[1] ;
               /* labels */
          if (i == 15) {
             offsGg.setColor(Color.white) ;
             offsGg.drawString("Throat-th",exes[2]-60,whys[2]+10) ;
          }
          offsGg.setColor(Color.white) ;
          offsGg.fillPolygon(exes,whys,4) ;
       }

    /* animated flow */
       npts = 34 ;
       for (j=1 ; j<=9 ; ++ j) {  
          exes[1] = xorgn + (int) (.05*fact*xg[j][0]) + xt ;
          whys[1] = yorgn + (int) (.05*fact*yg[j][0]) + yt ;
          for (i=1 ; i<= npts; ++i) { 
             exes[0] = exes[1] ;
             whys[0] = whys[1] ;
             exes[1] = xorgn + (int) (.05*fact*xg[j][i]) + xt ;
             whys[1] = yorgn + (int) (.05*fact*yg[j][i]) + yt ;
             if ((i-antim)/3*3 == (i-antim)) {
               if (ancol == -1) {         
                 if((i-antim)/6*6 == (i-antim))offsGg.setColor(Color.yellow);
                 if((i-antim)/6*6 != (i-antim))offsGg.setColor(Color.red);
               }
               if (ancol == 1) {     
                 if((i-antim)/6*6 == (i-antim))offsGg.setColor(Color.red);
                 if((i-antim)/6*6 != (i-antim))offsGg.setColor(Color.yellow);
               }
               offsGg.drawLine(exes[0],whys[0],exes[1],whys[1]) ;
             }
          }
       }

      /* shock in nozzle */
        if (mode == 2) {
          exes[1] = xorgn + (int) (.05*fact*xg[0][44]) + xt ;
          whys[1] = yorgn + (int) (.05*fact*yg[0][44]) + yt ;
          exes[2] = xorgn + (int) (-.05*fact*xg[0][44]) + xt ;
          whys[2] = yorgn + (int) (.05*fact*yg[0][44]) + yt ;
          offsGg.setColor(Color.cyan) ;
          offsGg.drawLine(exes[1],whys[1],exes[2],whys[2]) ;
        }
 
        offsGg.setColor(Color.yellow) ;
        offsGg.drawString("Find",10,15) ;

 // zoom in
        offsGg.setColor(Color.black) ;
        offsGg.fillRect(0,30,30,300) ;
        offsGg.setColor(Color.green) ;
        offsGg.drawString("Zoom",2,330) ;
        offsGg.drawLine(15,35,15,315) ;
        offsGg.fillRect(5,sldloc,20,5) ;

       g.drawImage(offscreenImg,0,0,this) ;
    }
  }
}

