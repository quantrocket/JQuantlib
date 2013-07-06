/*
 Copyright (C) 2008 Tim Swetonic
 
 This source code is release under the BSD License.
 
 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the JQuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquant-devel@lists.sourceforge.net>. The license is also available online at
 <http://www.jquantlib.org/index.php/LICENSE.TXT>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
 
 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */

package org.jquantlib.time.calendars;

import static org.jquantlib.time.Weekday.MONDAY;
import static org.jquantlib.time.Weekday.TUESDAY;
import static org.jquantlib.util.Month.APRIL;
import static org.jquantlib.util.Month.DECEMBER;
import static org.jquantlib.util.Month.JANUARY;
import static org.jquantlib.util.Month.AUGUST;
import static org.jquantlib.util.Month.JUNE;
import static org.jquantlib.util.Month.OCTOBER;

import org.jquantlib.time.WesternCalendar;
import org.jquantlib.time.Weekday;
import org.jquantlib.util.Date;
import org.jquantlib.util.Month;

//! Australian calendar
/*! Holidays:
    <ul>
    <li>Saturdays</li>
    <li>Sundays</li>
    <li>New Year's Day, January 1st</li>
    <li>Australia Day, January 26th (possibly moved to MONDAY)</li>
    <li>Good Friday</li>
    <li>Easter MONDAY</li>
    <li>ANZAC Day. April 25th (possibly moved to MONDAY)</li>
    <li>Queen's Birthday, second MONDAY in June</li>
    <li>Bank Holiday, first MONDAY in August</li>
    <li>Labour Day, first MONDAY in October</li>
    <li>Christmas, December 25th (possibly moved to MONDAY or TUESDAY)</li>
    <li>Boxing Day, December 26th (possibly moved to MONDAY or
        TUESDAY)</li>
    </ul>

    @Author Tim Swetonic
*/

public class Australia extends WesternCalendar {
	private static Australia AUSTRALIA = new Australia();

	private Australia() {
	}

	public static Australia getCalendar() {
		return AUSTRALIA;
	}

	public boolean isBusinessDay(Date date) {
        Weekday w = date.getWeekday();
        int d = date.getDayOfMonth(), dd = date.getDayOfYear();
        Month m = date.getMonthEnum();
        int y = date.getYear();
        int em = easterMonday(y);
        if (isWeekend(w)
            // New Year's Day (possibly moved to MONDAY)
            || (d == 1  && m == JANUARY)
            // Australia Day, JANUARY 26th (possibly moved to MONDAY)
            || ((d == 26 || ((d == 27 || d == 28) && w == MONDAY)) &&
                m == JANUARY)
            // Good Friday
            || (dd == em-3)
            // Easter MONDAY
            || (dd == em)
            // ANZAC Day, April 25th (possibly moved to MONDAY)
            || ((d == 25 || (d == 26 && w == MONDAY)) && m == APRIL)
            // Queen's Birthday, second MONDAY in June
            || ((d > 7 && d <= 14) && w == MONDAY && m == JUNE)
            // Bank Holiday, first MONDAY in August
            || (d <= 7 && w == MONDAY && m == AUGUST)
            // Labour Day, first MONDAY in October
            || (d <= 7 && w == MONDAY && m == OCTOBER)
            // Christmas, December 25th (possibly MONDAY or TUESDAY)
            || ((d == 25 || (d == 27 && (w == MONDAY || w == TUESDAY)))
                && m == DECEMBER)
            // Boxing Day, December 26th (possibly MONDAY or TUESDAY)
            || ((d == 26 || (d == 28 && (w == MONDAY || w == TUESDAY)))
                && m == DECEMBER))
            return false;
        return true;

	}
    public String getName() {
       return "Australia";
    }
    
}
