# define thermo dynamic cycles and calculate paramters for thermodynamic operations

# module imports
import math
#import ThermoPy
#import matplotlib
# from PyQt4 import QtGui, QtCore


def _calcSoS(gamma, P=None, rho=None, T=None, R=None):
    if (P!= None and rho!=None) and (T == None and R == None):
        a= math.sqrt(gamma*(P/rho))
    elif (P == None and rho==None) and (T != None and R != None):
        a= math.sqrt(gamma*T*R)
    else:
        print("incorrect imputs. require gamma and either RT or P/rho")
    return a


def calcM(a,v):
    return v/a


def mdot_choke(A, pt, Tt, gamma, R):
    #calculates choked mass flow rate for an ideal compressible gas
    mdot = A*pt/math.sqrt(Tt)*math.sqrt(gamma/R)*((gamma+1)/2)**(-((gamma+1)/(2*(gamma-1))))
    return mdot

#isentropic equations
def ponpt(gamma,M=None,rho = None,rhot = None,T=None,Tt=None):
    # pressure on total pressure (pressure ratio)
    if (rho!= None and rhot!=None) and (T == None and Tt == None):
        ponpt = math.pow(rho/rhot,gamma)
    elif (rho == None and rhot==None) and (T != None and Tt != None):
        ponpt = math.pow(T/Tt,gamma/(gamma-1))
    elif M != None:
        math.pow(1+(gamma -1)/2*M*M,-gamma/(gamma-1))
    else:
        print("incorrect imputs")
    return ponpt


def ratioAAstar(gamma,M):
    term1 = math.pow((gamma+1)/2, -(gamma+1)/(2*(gamma-1)))
    term2 = math.pow(1 + (gamma-1)/2*M*M,(gamma+1)/(2*(gamma-1)))
    ratio = term1*term2/M
    return ratio


def p2v2(gamma,P,V):
    p2v2 = (P*V**gamma)**(1/gamma)
    return p2v2


def tpr(gamma =None,pt4=None,pt5=None,Tt4=None,Tt5=None):
    if pt4 != None and pt5 != None:
        tpr = pt5/pt4
    elif gamma!= None and Tt4 != None and Tt5 != None:
        tpr = (Tt5/Tt4)**((gamma - 1)/gamma)
    else:
        print("incorrect inputs")
        tpr = None

    return tpr


def turbinework(gamma,cp,Tt4,tpr, eff =1):
    Q = eff*cp*Tt4(1-tpr**((gamma-1)/gamma))
    return Q


def comprwork(gamma, cp, Tt2, cpr, effc=1):
    Q = cp*Tt2/effc*(cpr**((gamma-1)/gamma)-1)


def enthalpy(cp,T):
    h = cp*T
    return h


def exitvel(gamma, cp, Tt, eff, npr):
    ve=math.sqrt(2*cp*Tt*eff*(1-(1/npr)**((gamma-1)/gamma)))
    return ve

def Qbalance(Qc,Qt):
    netQ = Qt - Qc
    return netQ

def requiredtpr(gamma, Tt2,Tt4,cpr,effc=1,efft=1):
    tprgamma = 1-Tt2/(effc*efft*Tt4)*(cpr**((gamma-1)/gamma)-1)
    #note single gamma used - should vary with termp. gamma at 2 should be differetn to gamma at 4
    tpr= tprgamma**(gamma/(gamma-1))
    return tpr


def burnerRatio(afr,effb,q,cp,Tt3):
    # rough burning ratio - to be improved with thermpy
    ratio = (1+ afr*effb*q*cp*Tt3)/(1+afr)
    return ratio

def afr(Tt3,Tt4,cp,effb,q):
    #calcualte air fuel ratio for a give TiT
    afr=(Tt4/Tt3 - 1)/(effb*q/cp*Tt3-Tt4/Tt3)
    return afr

def specificthrust(afr,ve,v0):
    Fs = (1+afr)*ve - v0

### inlet pressure recovery equations -maybe used in flight modelling??
#based on mil spec likely presure recovery lower then this
def milspec(effi,M):
#returns pt2/pt0
    if M >= 1:
        pt2onpt0 = effi*(1-0.075*(M-1)**1.35)
    else:
        pt2onpt0 = effi

def spillagedrag(mdot,V1,V0,A1,p1,p0,K = 0.4):
    dspill = K*(mdot(V1-V0)*A1*(p1-p0))
    return dspill
###





##super sonic equations to be used as part of aerodynamic analyis
def machangle(M):
    u = math.asin(1/M)
    return u

def prandtlmeyerv(gamma,M):
    v = math.sqrt((gamma+1)/(gamma-1))*math.atan(math.sqrt(((gamma-1)/(gamma+1))*(M**2-1)))-math.atan(math.sqrt(M**2-1))
    return v

def turnangle(gamma,M1,M2):
    turnangle = prandtlmeyerv(gamma,M1)-prandtlmeyerv(gamma,M2)
    return turnangle

def sspmratio(gamma,M1,M2):
    tempratio = (1+(gamma-1)/2*(M1**2))/(1+(gamma-1)/2*(M2**2))
    presratio = ((1+(gamma-1)/2*(M1**2))/(1+(gamma-1)/2*(M2**2)))**(gamma/(gamma-1))
    densratio = ((1+(gamma-1)/2*(M1**2))/(1+(gamma-1)/2*(M2**2)))**(1/(gamma-1))
    return tempratio,presratio,densratio
##end supersnoic equations


# process class defines the start and end states of a given process
class Process:

    def __init__(self,type = 'adiabatic',name = None):
        self.type = type
        self.name = name
        self.state0 = State()
        self.state1 = State()
        #isobaric,isothermal,isochoric, isentrpoic
        #fixed cycle and geometric paramters
        self.eff = 1
        self.Area0 = None
        self.Area1 = None
        self.FuelWorkIn = 0
        self.NetShaftWork = 0

    #set geometries
    def setareas(self,A0 = None, A1 = None):
        if A0 != None:
            self.Area0 = A0
        if A1 != None:
            self.Area1 = A1

    #calcaualte net work for a process
    def calculatework(self):
        work = 0
        return work

    #posotional argument ERROR ?????
    #calcualte end state based on initial state process type and gemetry
    def calculatestate1(self):
        state = self.state0
        self.state1 = state


class State:

    def __init__(self,P = None,T = None,V = None,h = None,S =None,v = None):
        self.P = P
        self.T = T
        self.h = h
        self.S = S
        self.V = V
        self.v = v
        self.mdot = 1

    def setpar (self,P = None,T = None,V = None, h = None,S = None):
        if P != None:
            self.P = P
        if T != None:
            self.T = T
        if V != None:
            self.V = V
        if h != None:
            self.h = h
        if h != None:
            self.S = S

    def getpar (self):
        return self.P,self.T,self.V,self.h,self.S


class Cycle:

    def __init__(self):
        print("new cycle")
        self.processList = []

    def calculate_power(self):
        power = 0
        return power

    def add_process(self,process):
        self.processList.append(process)

    def remove_process(self,process):
        self.processList.remove(process)

    def insert_process(self,process,index):
        self.processList.insert(index,process)


#define public methods
def _plotPV(cycle):
    print("plot PV diagram")

# test code
if __name__ == '__main__':
    testCycle = Cycle()
    process1 = Process()
    process1.name = '1'
    process1.setareas(100,50)
    process2 = Process()
    process2.name = '2'
    process2.setareas(process1.Area1, 50)
    process3 = Process()
    process3.name = '3'
    process2.setareas(process2.Area1, 10)
    testCycle.add_process(process1)
    testCycle.add_process(process2)
    testCycle.add_process(process3)

    initialState = State(100, 100, 100, 100)
    testCycle.processList[0].state0 = initialState

    currentState = testCycle.processList[0].state0
    for prcss in testCycle.processList:
        print('calculate process',prcss.name)
        prcss.state0 = currentState
        prcss.calculatestate1
        currentState = prcss.state1

    # app = QtGui.QApplication(sys.argv)
    #
    # main = Window()
    # main.show()
    #
    # sys.exit(app.exec_())


