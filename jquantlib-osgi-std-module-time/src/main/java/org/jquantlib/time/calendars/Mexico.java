/*
 Copyright (c)  Q Boiler 

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

import static org.jquantlib.util.Month.JULY;

import org.jquantlib.time.Calendar;
import org.jquantlib.time.Weekday;
import org.jquantlib.time.WesternCalendar;
import org.jquantlib.util.Date;
import org.jquantlib.util.Month;

// ! Mexican calendar
// 
/**
 * 
 * <strong>Banking holidays:</strong> ?? TODO: List the banking holidays and
 * fill in that section.
 * 
 * 
 * %Mexican calendars Holidays for the Mexican stock exchange (data from
 * <http://www.bmv.com.mx/>):
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, January 1st</li>
 * <li>Constitution Day, February 5th</li>
 * <li>Birthday of Benito Juarez, March 21st</li>
 * <li>Holy Thursday</li>
 * <li>Good Friday</li>
 * <li>Labour Day, May 1st</li>
 * <li>National Day, September 16th</li>
 * <li>Our Lady of Guadalupe, December 12th</li>
 * <li>Christmas, December 25th</li>
 * </ul>
 * 
 */
public class Mexico extends DelegateCalendar {

	public static enum Market {
		SETTLEMENT, BVM
	};

	private final static Mexico SETTLEMENT_CALENDAR = new Mexico(
			Market.SETTLEMENT);
	private final static Mexico BVM_CALENDAR = new Mexico(Market.BVM);

	private Mexico(Market market) {
		Calendar delegate;
		switch (market) {
		case SETTLEMENT:
			delegate = new MexicoSettlementCalendar();
			break;
		case BVM:
			delegate = new BVMExchangeCalendar();
			break;
		default:
			throw new IllegalArgumentException("unknown market");
		}
		setDelegate(delegate);
	}

	public static Mexico getCalendar(Market market) {
		switch (market) {
		case SETTLEMENT:
			return SETTLEMENT_CALENDAR;
		case BVM:
			return BVM_CALENDAR;
		default:
			throw new IllegalArgumentException("unknown market");
		}
	}
}

// TODO: DON'T KNOW HOW TO GET BANK HOLIDAYS, SO USING MARKET HOLIDAYS.
final class MexicoSettlementCalendar extends WesternCalendar {

	public String getName() {
		return "Mexico stock Market";
	}

	public boolean isBusinessDay(Date date) {
		Weekday w = date.getWeekday();
		int d = date.getDayOfMonth();
		Month m = date.getMonthEnum();
		int y = date.getYear();
		int dd = date.getDayOfYear();
		int em = easterMonday(y);

		if (isWeekend(w)
		// New Year's Day
				|| (d == 1 && m == Month.JANUARY)
				// Constitution Day
				|| (d == 5 && m == Month.FEBRUARY)
				// Birthday of Benito Juarez
				|| (d == 21 && m == Month.MARCH)
				// Holy Thursday
				|| (dd == em - 4)
				// Good Friday
				|| (dd == em - 3)
				// Labour Day
				|| (d == 1 && m == Month.MAY)
				// National Day
				|| (d == 16 && m == Month.SEPTEMBER)
				// Our Lady of Guadalupe
				|| (d == 12 && m == Month.DECEMBER)
				// Christmas
				|| (d == 25 && m == Month.DECEMBER))
			return false;
		return true;
	}

}

// TODO: Tests.
final class BVMExchangeCalendar extends WesternCalendar {

	public String getName() {
		return "Mexican Stock Exchange";
	}

	public boolean isBusinessDay(Date date) {
		Weekday w = date.getWeekday();
		int d = date.getDayOfMonth();
		Month m = date.getMonthEnum();
		int y = date.getYear();
		int dd = date.getDayOfYear();
		int em = easterMonday(y);

		if (isWeekend(w)
		// New Year's Day
				|| (d == 1 && m == Month.JANUARY)
				// Constitution Day
				|| (d == 5 && m == Month.FEBRUARY)
				// Birthday of Benito Juarez
				|| (d == 21 && m == Month.MARCH)
				// Holy Thursday
				|| (dd == em - 4)
				// Good Friday
				|| (dd == em - 3)
				// Labour Day
				|| (d == 1 && m == Month.MAY)
				// National Day
				|| (d == 16 && m == Month.SEPTEMBER)
				// Our Lady of Guadalupe
				|| (d == 12 && m == Month.DECEMBER)
				// Christmas
				|| (d == 25 && m == Month.DECEMBER))
			return false;
		return true;
	}
}
