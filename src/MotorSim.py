# this module contains functions and definitions to calculate and generate motor characterisations. this module
# also also for time variant simulation of a motors fuel consumption and thrust output

import math
import csv
import ThermoSim.py

class Fuel:

    def __init__(self):
        print("new fuel")
        self.name = None
        self.density = None
        self.Oxidiser = None
        self.StoicN2O = None
        self.IspSL = None
        self.cstar = None
        self.a = None
        self.G = None
        self.n = None

    def reg(self,a,G,n,x,m):
        reg = a*G^n*x^m
        return

    def regsimp(self, a, gox, n):
        rdot = a*gox^n
        return rdot

    def calc_gox(self, mdoto, A):
        gox = mdoto/A
        return gox

    def mdotf (rho, A, rdot):
        mdotf = rho*A*rdot
        return mdotf

    def setfuel(self, name):
        self.name = name

    def getdata(self,name = None):
        with open('fueltable') as csvDataFile:
            csvReader = csv.reader(csvDataFile)
            for row in csvReader:
                print(row[0])
                if row[0] == name:
                    print(row[1])


class Oxidiser:
    def __init__(self):
        print("oxidiser initialised")
        self.name = None


class Pressurant:
    def __init__(self):
        print("pressurant initialised")


class OxidiserTank:
    def __init__(self):
        print("oxidiser tank initailised")
        self.Volume = None
        self.mass = None


class PressurantTank:
    def __init__(self):
        print("presssurant tank initialised")


class FuelGrain:
    def __init__(self, fuel=None, do=None, di=None, lx=None):
        self.do = do
        self.di = di
        self.lx = lx
        self.fuel = fuel

    def setfuel(self, fuel):
        self.fuel = fuel

    def setdo(self, do):
        self.do = do

    def setdi(self, di):
        self.di = di

    def setlx(self,lx):
        self.lx = lx


class Nozzle:
    def __init__(self):
        self.m = None
        self.throat = None
        self.Aratio = None




class Injector:
    def __init__(self):
        print("injector initialised")

    def chokemdot (self,Cd,rho,Pl,Pc,A):
        mdot = Cd*A*math.sqrt(2*rho*(Pl-Pc))
        return mdot


#main program starts here

fuel = Fuel()
fuel.getdata('HDPE')