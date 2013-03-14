/*
 * Copyright (c) 2007-2013 by Stefan Laubenberger.
 *
 * "wichtel" is free software: you can redistribute it and/or modify
 * it under the terms of the General Public License v2.0.
 *
 * "wichtel" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details:
 * <http://www.gnu.org/licenses>
 *
 * This distribution is available at:
 * <https://github.com/slaubenberger/wichtel/>
 *
 * Contact information:
 * Stefan Laubenberger
 * Bullingerstrasse 53
 * CH-8004 Zuerich
 *
 * <http://www.laubenberger.net>
 *
 * <laubenberger@gmail.com>
 */

package net.laubenberger.wichtel.model.misc;

import java.util.Locale;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Possible languages.
 * <strong>Note:</strong> The language is always the main language of a country.
 *
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-14
 * @since 0.0.1
 */
@XmlRootElement(name = "language")
public enum Language { //TODO complete the list with all languages
	ALBANIAN(new Locale("sq")), //$NON-NLS-1$
	ARABIC(new Locale("ar")), //$NON-NLS-1$
	BELARUSIAN(new Locale("be")), //$NON-NLS-1$
	BULGARIAN(new Locale("bg")), //$NON-NLS-1$
	CATALAN(new Locale("ca")), //$NON-NLS-1$
	CHINESE(new Locale("zh")), //$NON-NLS-1$
	CROATIAN(new Locale("hr")), //$NON-NLS-1$
	CZECH(new Locale("cs")), //$NON-NLS-1$
	DANISH(new Locale("da")), //$NON-NLS-1$
	DUTCH(new Locale("nl")), //$NON-NLS-1$
	ENGLISH(new Locale("en")), //$NON-NLS-1$
	ESTONIAN(new Locale("et")), //$NON-NLS-1$
	FINNISH(new Locale("fi")), //$NON-NLS-1$
	FRENCH(new Locale("fr")), //$NON-NLS-1$
	GERMAN(new Locale("de")), //$NON-NLS-1$
	GREEK(new Locale("el")), //$NON-NLS-1$
	HEBREW(new Locale("iw")), //$NON-NLS-1$
	HINDI(new Locale("hi")), //$NON-NLS-1$
	HUNGARIAN(new Locale("hu")), //$NON-NLS-1$
	ICELANDIC(new Locale("is")), //$NON-NLS-1$
	INDONESIAN(new Locale("in")), //$NON-NLS-1$
	IRISH(new Locale("ga")), //$NON-NLS-1$
	ITALIAN(new Locale("it")), //$NON-NLS-1$
	JAPANESE(new Locale("ja")), //$NON-NLS-1$
	KOREAN(new Locale("ko")), //$NON-NLS-1$
	LATVIAN(new Locale("lv")), //$NON-NLS-1$
	LITHUANIAN(new Locale("lt")), //$NON-NLS-1$
	MACEDONIAN(new Locale("mk")), //$NON-NLS-1$
	MALAY(new Locale("ms")), //$NON-NLS-1$
	MALTESE(new Locale("mt")), //$NON-NLS-1$
	NORWEGIAN(new Locale("no")), //$NON-NLS-1$
	POLISH(new Locale("pl")), //$NON-NLS-1$
	PORTUGUESE(new Locale("pt")), //$NON-NLS-1$
	ROMANIAN(new Locale("ro")), //$NON-NLS-1$
	RUSSIAN(new Locale("ru")), //$NON-NLS-1$
	SERBIAN(new Locale("sr")), //$NON-NLS-1$
	SLOVAK(new Locale("sk")), //$NON-NLS-1$
	SLOVENIAN(new Locale("sl")), //$NON-NLS-1$
	SPANISH(new Locale("es")), //$NON-NLS-1$
	SWEDISH(new Locale("sv")), //$NON-NLS-1$
	THAI(new Locale("th")), //$NON-NLS-1$
	TURKISH(new Locale("tr")), //$NON-NLS-1$
	UKRAINIAN(new Locale("uk")), //$NON-NLS-1$
	VIETNAMESE(new Locale("vi")); //$NON-NLS-1$

//	private static final Logger log = LoggerFactory.getLogger(Language.class);
	
	private final Locale locale;
//	private transient BufferedImage icon;
	
	Language(final Locale locale) {
		this.locale = locale;
	}

	public String getName() {
		return locale.getDisplayLanguage();
	}

	public String getName(final Locale locale) {
		return this.locale.getDisplayLanguage(locale);
	}

	public String getCode() { //ISO 639-1
		return locale.getLanguage();
	}

	public Locale getLocale() {
		return locale;
	}
	
//	public BufferedImage getIcon() {
//		if (null == icon) {
//			try {
//				icon = HelperImage.readImage(Language.class.getClassLoader().getResourceAsStream("net/laubenberger/wichtel/icon/language/" + getCode() + ".png")); //$NON-NLS-1$ //$NON-NLS-2$
//			} catch (IOException ex) {
//				// should never happen!
//				log.error("Could not load icon", ex); //$NON-NLS-1$
//			}
//		}
//		
//		return icon;
//	}
}	