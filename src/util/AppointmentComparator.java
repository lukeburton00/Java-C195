package util;

import model.Appointment;

public interface AppointmentComparator
{
    public boolean appointmentsAreEqual(Appointment first, Appointment second);
}
