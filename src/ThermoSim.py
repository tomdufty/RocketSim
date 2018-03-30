#define thermo dynamic cycles and calculate paramters for thermodynamic operations

#module imports
#import ThermoPy
#from PyQt4 import QtGui, QtCore


#process class defines the start and end states of a given process
class Process:

    def __init__(self):
        self.type = 'adiabatic'
        self.state0 = State()
        self.state1 = State()
        #isobaric,isothermal,isochoric, isentrpoic

        #fixed geometric paramters
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
        self.processList = [Process]

    def calculate_power(self):
        power = 0
        return power

    def add_process(self,process):
        self.processList.append(process)

    def remove_process(self,process):
        self.processList.remove(process)

    def insert_process(self,process,index):
        self.processList.insert(index,process)


#define puvlic methods
def _plotPV(cycle):
    print("plot PV diagram")

# test code
if __name__ == '__main__':
    testCycle = Cycle()
    process1 = Process()
    process1.setareas(100,50)
    process2 = Process()
    process2.setareas(process1.Area1, 50)
    process3 = Process()
    process2.setareas(process2.Area1, 10)
    testCycle.add_process(process1)
    testCycle.add_process(process2)
    testCycle.add_process(process3)

    initialState = State(100, 100, 100, 100)
    testCycle.processList[0].state0 = initialState

    currentState = testCycle.processList[0].state0
    for prcss in testCycle.processList:
        prcss.state0 = currentState
        prcss.calculatestate1
        currentState = prcss.state1

    # app = QtGui.QApplication(sys.argv)
    #
    # main = Window()
    # main.show()
    #
    # sys.exit(app.exec_())


