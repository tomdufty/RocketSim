# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'mainui.ui'
#
# Created by: PyQt5 UI code generator 5.6
#
# WARNING! All changes made in this file will be lost!

from PyQt5 import QtCore, QtGui, QtWidgets

class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(1042, 767)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")
        self.VehicleTab = QtWidgets.QTabWidget(self.centralwidget)
        self.VehicleTab.setGeometry(QtCore.QRect(10, 10, 1021, 711))
        self.VehicleTab.setObjectName("VehicleTab")
        self.LaunchTab = QtWidgets.QWidget()
        self.LaunchTab.setObjectName("LaunchTab")
        self.widget = MplWidget(self.LaunchTab)
        self.widget.setGeometry(QtCore.QRect(20, 10, 941, 101))
        self.widget.setObjectName("widget")
        self.widget_2 = MplWidget(self.LaunchTab)
        self.widget_2.setGeometry(QtCore.QRect(20, 120, 941, 91))
        self.widget_2.setObjectName("widget_2")
        self.pushButton = QtWidgets.QPushButton(self.LaunchTab)
        self.pushButton.setGeometry(QtCore.QRect(840, 600, 75, 23))
        self.pushButton.setObjectName("pushButton")
        self.widget_3 = MplWidget(self.LaunchTab)
        self.widget_3.setGeometry(QtCore.QRect(20, 230, 941, 91))
        self.widget_3.setObjectName("widget_3")
        self.widget_4 = MplWidget(self.LaunchTab)
        self.widget_4.setGeometry(QtCore.QRect(20, 330, 941, 91))
        self.widget_4.setObjectName("widget_4")
        self.widget_5 = MplWidget(self.LaunchTab)
        self.widget_5.setGeometry(QtCore.QRect(20, 430, 701, 241))
        self.widget_5.setObjectName("widget_5")
        self.VehicleTab.addTab(self.LaunchTab, "")
        self.MotorTab = QtWidgets.QWidget()
        self.MotorTab.setObjectName("MotorTab")
        self.widget_6 = QtWidgets.QWidget(self.MotorTab)
        self.widget_6.setGeometry(QtCore.QRect(10, 10, 1001, 261))
        self.widget_6.setObjectName("widget_6")
        self.formLayoutWidget = QtWidgets.QWidget(self.MotorTab)
        self.formLayoutWidget.setGeometry(QtCore.QRect(10, 280, 381, 381))
        self.formLayoutWidget.setObjectName("formLayoutWidget")
        self.formLayout = QtWidgets.QFormLayout(self.formLayoutWidget)
        self.formLayout.setContentsMargins(0, 0, 0, 0)
        self.formLayout.setObjectName("formLayout")
        self.oxidiserLabel = QtWidgets.QLabel(self.formLayoutWidget)
        self.oxidiserLabel.setObjectName("oxidiserLabel")
        self.formLayout.setWidget(0, QtWidgets.QFormLayout.LabelRole, self.oxidiserLabel)
        self.oxidiserComboBox = QtWidgets.QComboBox(self.formLayoutWidget)
        self.oxidiserComboBox.setObjectName("oxidiserComboBox")
        self.formLayout.setWidget(0, QtWidgets.QFormLayout.FieldRole, self.oxidiserComboBox)
        self.fuelLabel = QtWidgets.QLabel(self.formLayoutWidget)
        self.fuelLabel.setObjectName("fuelLabel")
        self.formLayout.setWidget(1, QtWidgets.QFormLayout.LabelRole, self.fuelLabel)
        self.fuelComboBox = QtWidgets.QComboBox(self.formLayoutWidget)
        self.fuelComboBox.setObjectName("fuelComboBox")
        self.formLayout.setWidget(1, QtWidgets.QFormLayout.FieldRole, self.fuelComboBox)
        self.fullThrottleOxidiserMassFlowRateLabel = QtWidgets.QLabel(self.formLayoutWidget)
        self.fullThrottleOxidiserMassFlowRateLabel.setObjectName("fullThrottleOxidiserMassFlowRateLabel")
        self.formLayout.setWidget(2, QtWidgets.QFormLayout.LabelRole, self.fullThrottleOxidiserMassFlowRateLabel)
        self.fullThrottleOxidiserMassFlowRateLineEdit = QtWidgets.QLineEdit(self.formLayoutWidget)
        self.fullThrottleOxidiserMassFlowRateLineEdit.setObjectName("fullThrottleOxidiserMassFlowRateLineEdit")
        self.formLayout.setWidget(2, QtWidgets.QFormLayout.FieldRole, self.fullThrottleOxidiserMassFlowRateLineEdit)
        self.throatDiameterLabel = QtWidgets.QLabel(self.formLayoutWidget)
        self.throatDiameterLabel.setObjectName("throatDiameterLabel")
        self.formLayout.setWidget(4, QtWidgets.QFormLayout.LabelRole, self.throatDiameterLabel)
        self.throatDiameterLineEdit = QtWidgets.QLineEdit(self.formLayoutWidget)
        self.throatDiameterLineEdit.setObjectName("throatDiameterLineEdit")
        self.formLayout.setWidget(4, QtWidgets.QFormLayout.FieldRole, self.throatDiameterLineEdit)
        self.expansionRationLabel = QtWidgets.QLabel(self.formLayoutWidget)
        self.expansionRationLabel.setObjectName("expansionRationLabel")
        self.formLayout.setWidget(5, QtWidgets.QFormLayout.LabelRole, self.expansionRationLabel)
        self.expansionRationLineEdit = QtWidgets.QLineEdit(self.formLayoutWidget)
        self.expansionRationLineEdit.setObjectName("expansionRationLineEdit")
        self.formLayout.setWidget(5, QtWidgets.QFormLayout.FieldRole, self.expansionRationLineEdit)
        self.nozzleExitDiameterLabel = QtWidgets.QLabel(self.formLayoutWidget)
        self.nozzleExitDiameterLabel.setObjectName("nozzleExitDiameterLabel")
        self.formLayout.setWidget(6, QtWidgets.QFormLayout.LabelRole, self.nozzleExitDiameterLabel)
        self.nozzleExitDiameterLineEdit = QtWidgets.QLineEdit(self.formLayoutWidget)
        self.nozzleExitDiameterLineEdit.setObjectName("nozzleExitDiameterLineEdit")
        self.formLayout.setWidget(6, QtWidgets.QFormLayout.FieldRole, self.nozzleExitDiameterLineEdit)
        self.fuelGrainMassLabel = QtWidgets.QLabel(self.formLayoutWidget)
        self.fuelGrainMassLabel.setObjectName("fuelGrainMassLabel")
        self.formLayout.setWidget(7, QtWidgets.QFormLayout.LabelRole, self.fuelGrainMassLabel)
        self.fuelGrainMassLineEdit = QtWidgets.QLineEdit(self.formLayoutWidget)
        self.fuelGrainMassLineEdit.setObjectName("fuelGrainMassLineEdit")
        self.formLayout.setWidget(7, QtWidgets.QFormLayout.FieldRole, self.fuelGrainMassLineEdit)
        self.motorDryMassLabel = QtWidgets.QLabel(self.formLayoutWidget)
        self.motorDryMassLabel.setObjectName("motorDryMassLabel")
        self.formLayout.setWidget(8, QtWidgets.QFormLayout.LabelRole, self.motorDryMassLabel)
        self.motorDryMassLineEdit = QtWidgets.QLineEdit(self.formLayoutWidget)
        self.motorDryMassLineEdit.setObjectName("motorDryMassLineEdit")
        self.formLayout.setWidget(8, QtWidgets.QFormLayout.FieldRole, self.motorDryMassLineEdit)
        self.fullThrottleChamberPressureLabel = QtWidgets.QLabel(self.formLayoutWidget)
        self.fullThrottleChamberPressureLabel.setObjectName("fullThrottleChamberPressureLabel")
        self.formLayout.setWidget(3, QtWidgets.QFormLayout.LabelRole, self.fullThrottleChamberPressureLabel)
        self.fullThrottleChamberPressureLineEdit = QtWidgets.QLineEdit(self.formLayoutWidget)
        self.fullThrottleChamberPressureLineEdit.setObjectName("fullThrottleChamberPressureLineEdit")
        self.formLayout.setWidget(3, QtWidgets.QFormLayout.FieldRole, self.fullThrottleChamberPressureLineEdit)
        self.VehicleTab.addTab(self.MotorTab, "")
        self.tab = QtWidgets.QWidget()
        self.tab.setObjectName("tab")
        self.VehicleTab.addTab(self.tab, "")
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtWidgets.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 1042, 21))
        self.menubar.setObjectName("menubar")
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtWidgets.QStatusBar(MainWindow)
        self.statusbar.setObjectName("statusbar")
        MainWindow.setStatusBar(self.statusbar)

        self.retranslateUi(MainWindow)
        self.VehicleTab.setCurrentIndex(0)
        self.pushButton.clicked.connect(MainWindow.launchClicked)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        self.pushButton.setText(_translate("MainWindow", "Launch"))
        self.VehicleTab.setTabText(self.VehicleTab.indexOf(self.LaunchTab), _translate("MainWindow", "Launch"))
        self.oxidiserLabel.setText(_translate("MainWindow", "Oxidiser"))
        self.fuelLabel.setText(_translate("MainWindow", "Fuel"))
        self.fullThrottleOxidiserMassFlowRateLabel.setText(_translate("MainWindow", "Full Throttle Oxidiser Mass Flow Rate"))
        self.throatDiameterLabel.setText(_translate("MainWindow", "Throat Diameter"))
        self.expansionRationLabel.setText(_translate("MainWindow", "Expansion Ratio"))
        self.nozzleExitDiameterLabel.setText(_translate("MainWindow", "Nozzle Exit Diameter"))
        self.fuelGrainMassLabel.setText(_translate("MainWindow", "Fuel Grain Mass"))
        self.motorDryMassLabel.setText(_translate("MainWindow", "Motor Dry Mass"))
        self.fullThrottleChamberPressureLabel.setText(_translate("MainWindow", "Full Throttle Chamber Pressure"))
        self.VehicleTab.setTabText(self.VehicleTab.indexOf(self.MotorTab), _translate("MainWindow", "Motor"))
        self.VehicleTab.setTabText(self.VehicleTab.indexOf(self.tab), _translate("MainWindow", "Vehicle"))

from mplwidget import MplWidget

if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    MainWindow = QtWidgets.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())

