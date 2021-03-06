 �------------------------------------------------------------
-- Emerson Energy Systems (EES)
-- Power MIB
------------------------------------------------------------
  #"
		This integer value represents the operational or administrative
		status of the system. Also used as alarm severity.
		Depending on situation there may be limits on allowed values.
		Operational values:
		(1) unknown - status has not yet been defined
		(2) normal - there are no activated alarms
		(3) warning - OA, lowest level of 'abnormal' status
		(4) minor - A3
		(5) major - MA
		(6) critical - CA, highest level of 'abnormal' status
		Administrative values:
		(7) unmanaged
		(8) restricted
		(9) testing
		(10) disabled"               B"
		Indicates 'activated' (1) or 'deactivated' (2) alarm events."                                                                                 9"
		Emerson Energy Systems (EES) Power MIB, revision B." :"
		Emerson Energy Systems
		141 82 Stockholm
		Sweden"                   -"
		The name of the equipment manufacturer."                       >"
		The manufacturers model designation of the power system."                       8"
		The firmware (software) version of the controller."                       U"
		The name of the power plant. This object should be set by
		the administrator."                          p"
		Status of the complete plant (highest alarm). One of
		(1) unknown - status has not yet been defined
		(2) normal - there are no activated alarms
		(3) warning - OA, lowest level of 'abnormal' status
		(4) minor - A3
		(5) major - MA
		(6) critical - CA, highest level of 'abnormal' status
		(7) unmanaged
		(8) restricted
		(9) testing
		(10) disabled"                       |"
		System voltage, stored as mV, including positive or negative
		sign. The integer 2147483647 represents invalid value."                       |"
		System current, stored as mA, including positive or negative
		sign. The integer 2147483647 represents invalid value."                       k"
		Used capacity, stored as % of the total capacity.
		The integer 2147483647 represents invalid value."                           y"Battery voltage, stored as mV, including positive or negative
		sign. The integer 2147483647 represents invalid value."                       y"Battery current, stored as mA, including positive or negative
		sign. The integer 2147483647 represents invalid value."                           ["The AC line A voltage, stored as mV. The integer 2147483647 
		represents invalid value."                       ["The AC line B voltage, stored as mV. The integer 2147483647 
		represents invalid value."                       ["The AC line C voltage, stored as mV. The integer 2147483647 
		represents invalid value."                           s"The first route temperature, stored as 0.001 Celsius degree. 
		The integer 2147483647 represents invalid value."                       u"The second route temperature, stored as 0.001 Celsius degree. 
		The integer 2147483647 represents invalid value.."                       �"The status of communication with the Power System. interrupt(3) indicates
             some errors occurred between Power System and agent."                      5"The status of battery modes, 
		FloatCharging(2),
		ShortTest(3),
		BoostChargingForTest(4),
		ManualTesting(5),
		PlanTesting(6),
		ACFailTesting(7),
		ACFail(8),
		ManualBoostCharging(9),
		AutoBoostCharging(10),
		CyclicBoostCharging(11),
		MasterBoostCharging(12),
		MasterBateryTesting(13)."                           "The number of SM AC module."                       "The number of SM BAT module."                       "The number of SM IO module."                       ["
		The sequence number of last submitted alarm trap,
		also last row in alarmTrapTable."                       @"
		Table holding information about the submitted alarm traps."                       6"
		An entry (conceptual row) in the alarmTrapTable."                       4"
		The unique sequence number of this alarm trap."                       f"
		Date and time when event occured (local time), including
		timezone if supported by controller."                       J"
		The type of alarm change. One of
		(1) activated
		(2) deactivated"                       �"
		The severity of the alarm. One of
		(3) warning - OA, lowest level of alarm severity
		(4) minor - A3
		(5) major - MA
		(6) critical - CA, highest level of alarm severity"                       %"
		Free-text description of alarm."                       ?"
		Alarm type, i.e. an integer specifying the type of alarm."                          Q"
		An alarm trap is sent when an alarm occurs (activated) or
		returns to normal state (deactivated). Alarm traps are logged
		in alarmTrapTable. Variables in this trap:
		* alarmTrapNo - The unique sequence number of this alarm trap.
		* alarmTime - Date and time when event occured (local time),
		      including timezone if supported by controller.
		* alarmStatusChange - (1) activated or (2) deactivated.
		* alarmSeverity - Integer describing the severity of the alarm.
		* alarmDescription - Free-text description of alarm.
		* alarmType - Integer indicating type of alarm."                    