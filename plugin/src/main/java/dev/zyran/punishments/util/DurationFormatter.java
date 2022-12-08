/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package dev.zyran.punishments.util;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Formats durations to a readable form
 */
public class DurationFormatter {
	public static final DurationFormatter LONG = new DurationFormatter(false);
	public static final DurationFormatter CONCISE = new DurationFormatter(true);
	public static final DurationFormatter CONCISE_LOW_ACCURACY = new DurationFormatter(true, 3);

	private static final ChronoUnit[] UNITS = new ChronoUnit[] {
			ChronoUnit.YEARS,
			ChronoUnit.MONTHS,
			ChronoUnit.WEEKS,
			ChronoUnit.DAYS,
			ChronoUnit.HOURS,
			ChronoUnit.MINUTES,
			ChronoUnit.SECONDS
	};

	private final boolean concise;
	private final int accuracy;

	public DurationFormatter(boolean concise) {
		this(concise, Integer.MAX_VALUE);
	}

	public DurationFormatter(boolean concise, int accuracy) {
		this.concise = concise;
		this.accuracy = accuracy;
	}

	public String format(Duration duration) {
		long seconds = duration.getSeconds();
		StringBuilder builder = new StringBuilder();
		int outputSize = 0;

		for (ChronoUnit unit : UNITS) {
			long n = seconds / unit.getDuration().getSeconds();
			if (n > 0) {
				seconds -= unit.getDuration().getSeconds() * n;
				if (outputSize != 0) {
					builder.append(' ');
				}
				builder.append(formatPart(n, unit));
				outputSize++;
			}
			if (seconds <= 0 || outputSize >= this.accuracy) {
				break;
			}
		}

		if (outputSize == 0) {
			return formatPart(0, ChronoUnit.SECONDS);
		}
		return builder.toString();
	}

	private String formatPart(long amount, ChronoUnit unit) {
		String name = unit.name().toLowerCase(Locale.ROOT);
		if (concise) {
			return amount + name.charAt(0) + "";
		}
		if (amount == 1) {
			return amount + " " + name.substring(0, name.length() - 1);
		}
		return amount + " " + name;
	}
}