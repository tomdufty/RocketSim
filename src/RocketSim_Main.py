# main code for GUI
from PyQt5 import QtWidgets
from src import mainUI
from src import LaunchSim
from src import AtmoSim
from src import VehicleSim as VS
import sys


class mainWindow(QtWidgets.QMainWindow):

    def __init__(self):
        super(mainWindow, self).__init__()

        self.ui = mainUI.Ui_MainWindow()

        self.ui.setupUi(self)

    def launchClicked(self):
        v = VS.Vehicle()
        atmo = AtmoSim.load_atmosphere()
        v.load_ldcurve()
        cl = v.lookup_cl(3)

        print("lift =")
        print(v.calc_lift(atmo, cl, 1000, 0))

        scheme = LaunchSim.ControlScheme()
        launch = LaunchSim.Launch()
        launch.RunLaunch(scheme)
        print("launch succesful")


app = QtWidgets.QApplication([])

application = mainWindow()

application.show()

sys.exit(app.exec())