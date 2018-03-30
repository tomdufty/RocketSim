#define thermo dynamic cycles and calculate paramters for thermodynamic operations

#module imports
import ThermoPy


class Process:

    def __init__(self):
        self.type = 'adiabatic'
        self.state0 = State()
        self.state1 = State()
        #isobaric,isothermal,isochoric, isentrpoic

class State:

    def __init__(self,P = None,T = None,h = None,S =None):
        self.P = P
        self.T = T
        self.h = h
        self.S = S
        self.V = V

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


