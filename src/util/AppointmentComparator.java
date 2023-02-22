package util;

import model.Appointment;

/**
 * AppointmentComparator is a functional interface to be implemented where
 * a comparison needs to be made between all fields of two appointment objects.
 */
public interface AppointmentComparator
{
    /**
     * appointmentsAreEqual is the method to be implemented.
     * @param first the first appointment to be compared.
     * @param second the second appointment to be compared.
     * @return the result of the comparison.
     */
    boolean appointmentsAreEqual(Appointment first, Appointment second);
}
