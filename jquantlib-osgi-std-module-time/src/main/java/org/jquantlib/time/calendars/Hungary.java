/*
 Copyright (C) 2008 Jia Jia

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

import static org.jquantlib.util.Month.DECEMBER;
import static org.jquantlib.util.Month.NOVEMBER;
import static org.jquantlib.util.Month.MARCH;
import static org.jquantlib.util.Month.MAY;
import static org.jquantlib.util.Month.AUGUST;
import static org.jquantlib.util.Month.JANUARY;
import static org.jquantlib.util.Month.OCTOBER;

import org.jquantlib.time.Weekday;
import org.jquantlib.time.WesternCalendar;
import org.jquantlib.util.Date;
import org.jquantlib.util.Month;

/**
 * Hungarian calendar Holidays:
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>Easter Monday</li>
 * <li>Whit(Pentecost) Monday</li>
 * <li>New Year's Day, January 1st</li>
 * <li>National Day, March 15th</li>
 * <li>Labour Day, May 1st</li>
 * <li>Constitution Day, August 20th</li>
 * <li>Republic Day, October 23rd</li>
 * <li>All Saints Day, November 1st</li>
 * <li>Christmas, December 25th</li>
 * <li>2nd Day of Christmas, December 26th</li>
 * </ul>
 * 
 * @author Jia Jia
 * 
 */
public class Hungary extends WesternCalendar {
	private static final Hungary HUNGARY = new Hungary();

	public static Hungary getCalendar() {
		return HUNGARY;
	}

	private Hungary() {
	}

	public String getName() {
		return "Hungary";
	}
	
	public boolean isBusinessDay(final Date date /* @ReadOnly */) /* @ReadOnly */{
		Weekday w = date.getWeekday();
		int d = date.getDayOfMonth(), dd = date.getDayOfYear();
		Month m = date.getMonthEnum();
		int y = date.getYear();
		int em = easterMonday(y);
        if (isWeekend(w)
            // Easter Monday
            || (dd == em)
            // Whit Monday
            || (dd == em+49)
            // New Year's Day
            || (d == 1  && m == JANUARY)
            // National Day
            || (d == 15  && m == MARCH)
            // Labour Day
            || (d == 1  && m == MAY)
            // Constitution Day
            || (d == 20  && m == AUGUST)
            // Republic Day
            || (d == 23  && m == OCTOBER)
            // All Saints Day
            || (d == 1  && m == NOVEMBER)
            // Christmas
            || (d == 25 && m == DECEMBER)
            // 2nd Day of Christmas
            || (d == 26 && m == DECEMBER))
            return false;
        return true;

	}
	
}
