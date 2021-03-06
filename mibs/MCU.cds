 �-- UPSMAN-MCU-MIB { iso org(3) dod(6) internet(1) private(4) enterprises(1) generex(1356) }
--
-- Title: upsman-mcu.mib
-- (C) Copyright 2005 GENEREX GmbH
-- All Rights Reserved.
--
--
-- Last Edit: 08.09.2005
--
--
   )"This data type is a non-negative value."               6"This data type is a non-zero and non-negative value."                                                                                                     /"The name of the DC-power system manufacturer."                       ("The DC-power system model description."                       +"The serial number of the DC-power system."                       k"A string identifying the DC-power system.
               This object should be set by the administrator."                       C"A possibility for the administrator to describe the power system."                       �"A string containing either the date of system installation or acceptance date (YYYY-MM-DD).
               This string can be set by the administrator."                       �"A string containing nominal output power values of the system, e.g. 48 Volts / 3000 Amps / 3000 Ah.
               This string can be set by the administrator."                           &"The magnitude of the busbar voltage."                       /"The magnitude of the total rectifier current."                       -"The magnitude of the total battery current."                       3"The magnitude of the total system output current."                       >"The magnitude of the present total system output true power."                       �"The status of the power system describing the rectifiers and the batteries.
                 Bit 1: Float
                 Bit 2: Boost
                 Bit 4: Battery Test
                 Bit 5: Discharging
                 Bit 6: Maintenance"                       �"The fault of the power system. The most important fault level is displayed.
                 Bit 1: Normal
                 Bit 2: Minor
                 Bit 3: Major
                 Bit 4: Information"                       '"The power system ambient temperature."                           1"The DC-power system's controller serial number."                       �"The DC-power system software version(s). This variable can be different from
                the dcControllerFirmwareVersion. This variable may or may not have the same
                value as dcControllerAgentSoftwareVersion in some implementations."                       3"The DC-power system's (snmp) agent serial number."                      "The DC-power system agent software version. This variable can be different
                from the dcControllerAgentFirmwareVersion. This variable may or may not have
                the same value as dcControllerSoftwareVersion in some implementations."                           �"The number of rectifers or rectifier-shelfs utilized in this system.
                This variable indicates the number of rows in the rectifier table."                       ~"A list of rectifier table entries.
                The number of entries is given by the value of dcRectifierNumRectifiers."                       G"An entry containing information applicable to a particular rectifier."                       "The rectifier identifier."                       #"The rectifier's firmware version."                       C"The current state of the rectifier. Rectifiers can be on or off. "                       �"This integer holds a bitmask for the retifier faults:
                  Bit 0: Low Input
                  Bit 2: Communication
                  Bit 3: Temperature Fan
                  Bit 7: Rectifier Failure"                       )"The magnitude of the rectifier voltage."                       1"The magnitude of the present rectifier current."                       -"The address of the rectifier in the system."                          "This object indicates a fault of the main components affecting more than one
                battery string, e.g. deep discharge contactor.
                 Bit 0: Main Fuse Fault
                 Bit 1: Main Contactor Fault
                 Bit 2: Batteryroom Fan Fault"                       l"The number of batteries.
                This variable indicates the number of rows in the battery table."                       y"A list of battery table entries.
                The number of entries is given by the value of dcBatteryNumBatteries."                       Q"An entry containing information applicable to a particular string of batteries."                       "The battery identifier."                       �"The fault of the battery string.
                 Bit 1: Temperature Fault
                 Bit 3: Test Fault
                 Bit 4: Fuse Fault
                 Bit 5: Contactor Fault
                 Bit 7: Other Fault"                       '"The magnitude of the battery voltage."                       /"The magnitude of the present battery current."                       "The battery temperature."                       <"A string identifying the battery string, e.g. VHB/4/300Ah."                       +"The address of the battery in the system."                           "The number of load distributions.
                This variable indicates the number of rows in the load distribution table."                       ,"A list of load distribution table entries."                       I"An entry containing information applicable to a particular load string."                       #"The load distribution identifier."                       /"The magnitude of the particular load voltage."                       /"The magnitude of the particular load current."                       "The particular load power."                       0"The description of the particular load string."                       �"The status of the particular load fuse.
                 Bit 1: OK
                 Bit 2: Fuse Blown
                 Bit 4: Contactor Open"                       5"The address of the load distribution in the system."                               j"The number of bypasses.
                This variable indicates the number of rows in the bypass table."                       !"A list of bypass table entries."                       D"An entry containing information applicable to a particular bypass."                       "The bypass identifier."                       "The bypass hardware version."                       �"The current operation mode of the bypass.
                 Bit 1: Mains Operation
                 Bit 2: Inverter Operation
                 Bit 3: Service Bypass Operation"                       �"This object indicates a fault of the bypass.
                 Bit 1: Majoor Fault
                 Bit 2: Minor Fault
                 Bit 3: Switchover Blocked"                       B"The magnitude of the bypass input voltage of the primary source."                       D"The magnitude of the bypass input voltage of the secondary source."                       -"The magnitude of the bypass output voltage."                       5"The magnitude of the present bypass output current."                       "The present bypass frequency."                       3"The magnitude of the present active bypass power."                       5"The magnitude of the present reactive bypass power."                       4"The magnitude of the present complex bypass power."                       *"The address of the bypass in the system."                           p"The number of inverters in the power system. This variable indicates the number of rows in the inverter table."                       j"A list of inverter table entries.  The number of entries is given by the value of dcInverterNumInverter."                       F"An entry containing information applicable to a particular inverter."                       "The inverter identifier."                       ""The inverter's firmware version."                       ("The current state of the inverter. ..."                      	"This object indicates the fault of the inverter.
                 Bit 1: Inverter Failure
                 Bit 6: Overtemperature Fan
                 Bit 8: Output out of Telerance
                 Bit 11: Output Fuse Fault
                 Bit 12: Overload"                       /"The magnitude of the inverter output voltage."                       7"The magnitude of the present inverter output current."                       ,"The address of the inverter in the system."                           q"The number of batteries.
                This variable indicates the number of rows in the battery test table."                       ""A list of battery table entries."                       Q"An entry containing information applicable to a particular battery-string test."                       "The battery test identifier."                       <"The battery string voltage at the end of the battery test."                       3"The duration of the last battery test in minutes."                       6"This discharged energy during the last battery test."                       $"The type of the last battery test."                       &"The result of the last battery test."                       F"A string containing the date of battery test start
   (YYYY-MM-DD)."                       ="A string containing the time of battery test start (HH:MM)."                       +"The address of the battery in the system."                           �"This object controls the system mode. batteryTest(3) starts a battery test and
            setting the mode to float(1) or boost(2) will manually stop a battery test."                       �"This object restarts all faulty units in the system. After setting this object (1), the
             agent responses with 0 if executed."                           ""The float voltage of the system."                       ""The boost voltage of the system."                       A"The stand by-voltage of the system. E.g. during a battery test."                           0"The present number of active alarm conditions."                       "A list of alarm entries."                       C"An entry containing information applicable to a particular alarm."                       N"A unique identifier for an alarm condition. This value must remain constant."                       �"A reference to an alarm description object. The object referenced should not be
             accessible, but rather be used to provide a unique description of the alarm   condition."                       �"The value of sysUpTime when the alarm condition was detected. If the alarm
            condition was detected at the time of agent startup and presumably existed before
            agent startup, the value of dcAlarmTime shall equal 0."                           c"This object indicates a communication problem from the agent to the DC-power
            system."               �"This object indicates an alarm in the AC-input, e.g. ac input fuse blown, ac input
            breaker tripped or mains failure."               m"This object indicates an alarm of rectifiers, e.g. broken rectifier, fan or
            temperature alarm."               g"This object indicates an alarm of batteries, e.g. fuse blown, symmetry or
              temperature."               D"This object indicates an alarm of inverters, e.g. input fuse, fan."               G"This object indicates an alarm of bypasses, e.g. switch-over blocked."               >"This object indicates that the battery is in discharge mode."               ."This object indicates a low battery voltage."               ."This object indicates a high busbar voltage."               A"This object indicates that the battery temperature is abnormal."               ,"This object indicates a disconnected load."               4"This object indicates that all rectifiers are off."               7"This object indicates a loss of rectifier redundancy."               ^"This object indicates an external fault. External faults are e.g. door open, smoke detector."               _"This object indicates an auxiliary fault. Auxiliary faults are e.g. air condition, generator."               8"This object indicates that a battery fuse has tripped."               8"This object indicates that a dc load fuse has tripped."               @"This object indicates that the system is in battery test mode."               9"This object indicates that the battery test has failed."               9"This object indicates that the system is in boost mode."               ("This object indicates a general alarm."                   p"The number of Auxiliaries.
                This variable indicates the number of rows in the auxiliary table."                       w"A list of auxiliary table entries.
                The number of entries is given by the value of dcAuxiliaryNumAux."                       Q"An entry containing information applicable to a particular auxiliary component."                       "The auxiliary identifier."                       X"A string describing the auxiliary component, e.g. fuel level, configured in the agent."                       %"The current state of the component."                           �"This trap is sent upon a alarm in the system appears. Binding in the trap is
            description of alarm and the severity in object dcPowerSystemFault."                 �"This trap is sent upon a alarm in the system disappears. Binding in the trap is
            description of alarm and the severity in object dcPowerSystemFault."                    