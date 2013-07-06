/*
 Copyright (C) 2008
  
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



import static org.jquantlib.util.Month.JANUARY;
import static org.jquantlib.util.Month.FEBRUARY;
import static org.jquantlib.util.Month.MARCH;
import static org.jquantlib.util.Month.APRIL;
import static org.jquantlib.util.Month.MAY;
import static org.jquantlib.util.Month.JUNE;
import static org.jquantlib.util.Month.JULY;
import static org.jquantlib.util.Month.AUGUST;
import static org.jquantlib.util.Month.SEPTEMBER;
import static org.jquantlib.util.Month.OCTOBER;
import static org.jquantlib.util.Month.NOVEMBER;
import static org.jquantlib.util.Month.DECEMBER;

import org.jquantlib.time.Calendar;
import org.jquantlib.time.Weekday;
import org.jquantlib.time.WesternCalendar;
import org.jquantlib.util.Date;
import org.jquantlib.util.Month;

	//! %Indonesian calendars
    /*! Holidays for the Jakarta stock exchange
        (data from <http://www.jsx.co.id/trading.asp?cmd=menu3>):
        <ul>
        <li>Saturdays</li>
        <li>Sundays</li>
        <li>Good Friday</li>
        <li>New Year's Day, January 1st</li>
        <li>Ascension of Jesus Christ</li>
        <li>Independence Day, August 17th</li>
        <li>Christmas, December 25th</li>
        </ul>

        Other holidays for which no rule is given
        (data available for 2005-2007 only:)
        <ul>
        <li>Idul Adha</li>
        <li>Ied Adha</li>
        <li>Imlek</li>
        <li>Moslem's New Year Day</li>
        <li>Nyepi (Saka's New Year)</li>
        <li>Birthday of Prophet Muhammad SAW</li>
        <li>Waisak</li>
        <li>Ascension of Prophet Muhammad SAW</li>
        <li>Idul Fitri</li>
        <li>Ied Fitri</li>
        <li>Other national leaves</li>
        </ul>
        
    */
public class Indonesia extends DelegateCalendar {
	public static enum Market {
		BEJ,  //!< Jakarta stock exchange
        JSX   //!< Jakarta stock exchange
	};

	
	private final static Indonesia BEJ_CALENDAR = new Indonesia(
			Market.BEJ);
	

	private Indonesia(Market market) {
		Calendar delegate;
		switch (market) {
		case BEJ:
		case JSX:
			delegate = new IndonesiaBEJCalendar();
			break;
		
		default:
			throw new IllegalArgumentException("unknown market");
		}
		setDelegate(delegate);
	}

	public static Indonesia getCalendar(Market market) {
		switch (market) {
		case BEJ:
		case JSX:
			return BEJ_CALENDAR;
		
		default:
			throw new IllegalArgumentException("unknown market");
		}
	}
}

final class IndonesiaBEJCalendar extends WesternCalendar {

	public String getName() {
		return "Jakarta stock exchange";
	}

	public boolean isBusinessDay(Date date) {
		Weekday w = date.getWeekday();
        int d = date.getDayOfMonth(), dd = date.getDayOfYear();
        Month m = date.getMonthEnum();
        int y = date.getYear();
        int em = easterMonday(y);
		
		if (isWeekend(w)
            // New Year's Day
            || (d == 1 && m == JANUARY)
            // Good Friday
            || (dd == em-3)
            // Ascension Thursday
            || (dd == em+38)
            // Independence Day
            || (d == 17 && m == AUGUST)
            // Christmas
            || (d == 25 && m == DECEMBER)
            )
            return false;
        if (y == 2005) {
            if (// Idul Adha
                (d == 21 && m == JANUARY)
                // Imlek
                || (d == 9 && m == FEBRUARY)
                // Moslem's New Year Day
                || (d == 10 && m == FEBRUARY)
                // Nyepi
                || (d == 11 && m == MARCH)
                // Birthday of Prophet Muhammad SAW
                || (d == 22 && m == APRIL)
                // Waisak
                || (d == 24 && m == MAY)
                // Ascension of Prophet Muhammad SAW
                || (d == 2 && m == SEPTEMBER)
                // Idul Fitri
                || ((d == 3 || d == 4) && m == NOVEMBER)
                // National leaves
                || ((d == 2 || d == 7 || d == 8) && m == NOVEMBER)
                || (d == 26 && m == DECEMBER)
                )
                return false;
        }
        if (y == 2006) {
            if (// Idul Adha
                (d == 10 && m == JANUARY)
                // Moslem's New Year Day
                || (d == 31 && m == JANUARY)
                // Nyepi
                || (d == 30 && m == MARCH)
                // Birthday of Prophet Muhammad SAW
                || (d == 10 && m == APRIL)
                // Ascension of Prophet Muhammad SAW
                || (d == 21 && m == AUGUST)
                // Idul Fitri
                || ((d == 24 || d == 25) && m == OCTOBER)
                // National leaves
                || ((d == 23 || d == 26 || d == 27) && m == OCTOBER)
                )
                return false;
        }
        if (y == 2007) {
            if (// Nyepi
                (d == 19 && m == MARCH)
                // Waisak
                || (d == 1 && m == JUNE)
                // Ied Adha
                || (d == 20 && m == DECEMBER)
                // National leaves
                || (d == 18 && m == MAY)
                || ((d == 12 || d == 15 || d == 16) && m == OCTOBER)
                || ((d == 21 || d == 24) && m == OCTOBER)
                )
                return false;
        }
	    return true;
	}
}