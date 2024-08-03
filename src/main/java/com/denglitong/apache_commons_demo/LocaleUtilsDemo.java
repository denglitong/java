package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.LocaleUtils;

import java.util.Locale;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/16
 */
public class LocaleUtilsDemo {

    public static void main(String[] args) {
        System.out.println(LocaleUtils.availableLocaleList());
        System.out.println(LocaleUtils.availableLocaleSet());

        // [zh_TW, zh_HK, zh_SG, zh_CN]
        System.out.println(LocaleUtils.countriesByLanguage("zh"));
        // [en_US, en_SG, en_MT, en_PH, en_NZ, en_ZA, en_AU, en_IE, en_CA, en_IN, en_GB]
        System.out.println(LocaleUtils.countriesByLanguage("en"));

        Locale locale = Locale.getDefault();
        System.out.println(locale); // zh_CN
        System.out.println(LocaleUtils.isAvailableLocale(locale));

        // [zh_CN]
        System.out.println(LocaleUtils.languagesByCountry("CN"));
        // [en_US, es_US]
        System.out.println(LocaleUtils.languagesByCountry("US"));

        Locale locale1 = new Locale("zh", "CN", "GuangZhou");
        // [zh_CN_GuangZhou, zh_CN, zh]
        // [Locale("zh", "CN", "GuangZhou"), Locale("zh", "CN"), Locale("zh")]
        System.out.println(LocaleUtils.localeLookupList(locale1));

        Locale locale2 = new Locale("en", "US", "NewYork");
        // [zh_CN_GuangZhou, zh_CN, zh, en_US_NewYork]
        // end up with default locale
        System.out.println(LocaleUtils.localeLookupList(locale1, locale2));

        System.out.println(LocaleUtils.toLocale(locale2)); // en_US_NewYork
        System.out.println(LocaleUtils.toLocale((Locale)null)); // zh_CN getDefault()

        System.out.println(LocaleUtils.toLocale("en_US_NewYork")); // Locale("en", "US", "NewYork")
        System.out.println(LocaleUtils.toLocale((String)null)); // null
    }
}
