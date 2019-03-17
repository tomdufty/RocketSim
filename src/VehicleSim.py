#this module calculates wing lift and drag tables for given wing parameters and saves them to a files.
# thi module provides fucntions for the instantaneious calcualtion of lift and drag parameters
#source for function http://www.ltas-aea.ulg.ac.be/cms/uploads/Expaero04.pdf

import math
import csv
from src import AtmoSim

class Wing:
    def __init__(self):
        self.aoa0Ax = 1
        self.aoa0Ay = 0.2
        self.AR = 1
        self.sweepAng = math.degrees(math.atan(self.AR/4))

    def calcCL(self,alpha):
        # slender body theory
        CL0 = math.pi*self.AR/2*math.sin(math.radians(alpha))*math.cos(math.radians(alpha))
        CD0 = CL0*math.sin(alpha)

        #combined slender body with vortex correction
        Cdp = 1.95 #approximation drag of a flat plate in free stream
        CL = CL0 + Cdp * math.sin(math.radians(alpha))**2 * math.cos(math.radians(alpha))
        CD = math.pi*self.AR/2 * math.sin(math.radians(alpha))**2 +Cdp * math.sin(math.radians(alpha))**3

        # to do - include Polhamus lift and drag equations for comparison

        return CL,CD

    def calcWingPress(self):
        print("calculating wing pressure")

    def calcWingBendI(self):
        print("calculating the minimum I required for relatively stiff wing")

    def calcLocalThickness(self):
        print('calculating ')

    def calcWingSkin(self):
        print()

class Vehicle:
    def __init__(self):
        print("vehicle created")
        self.mass = 0
        self.fuel0 = 10000
        self.fueli = self.fuel0
        self.thrust = 100000
        self.tsfc = 0.0001
        self.cd =0.5
        self.cl =0.5
        self.frontA = 1
        self.wingA = 3

    def load_ldcurve(self):
        atmo = []

        with open('LDcurve') as csvDataFile:
            csvReader = list(csv.reader(csvDataFile, delimiter='\t'))
            ld = csvReader
            print("atmosphere loaded")
            self.ld = ld

    def useFuel(self,thrust, timestep):
        self.fueli = self.fueli -self.tsfc*thrust*timestep

    def calc_lift(self, atmo, cl, vt, alt):
        print("calculating lift")
        AtmoSim.get_atmo_value(atmo, alt, 'rho')
        lift = 0.5 * cl * (vt**2) * self.wingA
        return lift

    def calc_drag(self):
        print("calculating drag")

    def lookup_cl(self,M):
        #lookups cl from CLtable previous loaded. itnerpolates on M
        ld = self.ld
        col = 1
        a0 = None
        i = 1
        while float(ld[i][0]) <= M:
            a0 = float(ld[i][0])
            i = i + 1
        p0 = float(ld[i - 1][col])
        p1 = float(ld[i][col])
        a1 = float(ld[i][0])
        print(a0)
        print(a1)
        ratio = (M - a0) / (a1 - a0)
        p = ratio * (p1 - p0) + p0
        print("cl = ")
        print(p)
        return p

    def lookup_cd(self,M):
        #lookups cl from CLtable previous loaded. itnerpolates on M
        ld = self.ld
        col = 2
        a0 = None
        i = 1
        while float(ld[i][0]) <= M:
            a0 = float(ld[i][0])
            i = i + 1
        p0 = float(ld[i - 1][col])
        p1 = float(ld[i][col])
        a1 = float(ld[i][0])
        print(a0)
        print(a1)
        ratio = (M - a0) / (a1 - a0)
        p = ratio * (p1 - p0) + p0
        print("cl = ")
        print(p)
        return p


#main test

wing = Wing()
print(wing.calcCL(10))