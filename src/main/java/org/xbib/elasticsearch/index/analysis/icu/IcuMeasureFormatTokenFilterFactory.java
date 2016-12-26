package org.xbib.elasticsearch.index.analysis.icu;

import com.ibm.icu.text.MeasureFormat;
import com.ibm.icu.util.ULocale;
import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;
import org.elasticsearch.index.analysis.MultiTermAwareComponent;

/**
 * Experimental.
 */
public class IcuMeasureFormatTokenFilterFactory extends AbstractTokenFilterFactory implements MultiTermAwareComponent {

    private final MeasureFormat measureFormat;

    public IcuMeasureFormatTokenFilterFactory(IndexSettings indexSettings, Environment environment, String name,
                                              Settings settings) {
        super(indexSettings, name, settings);
        ULocale locale = settings.get("locale") != null ?
                new ULocale(settings.get("locale")) : ULocale.getDefault();
        String formatWidthStr = settings.get("format", "NARROW");
        MeasureFormat.FormatWidth formatWidth = MeasureFormat.FormatWidth.NARROW;
        switch (formatWidthStr) {
            case "NARROW":
                // "3h"
                formatWidth = MeasureFormat.FormatWidth.NARROW;
                break;
            case "NUMERIC":
                // "3:17"
                formatWidth = MeasureFormat.FormatWidth.NUMERIC;
                break;
            case "WIDE":
                // "3 hours"
                formatWidth = MeasureFormat.FormatWidth.WIDE;
                break;
            case "SHORT":
                // "3 hrs"
                formatWidth = MeasureFormat.FormatWidth.SHORT;
                break;
            default:
                break;
        }
        this.measureFormat = MeasureFormat.getInstance(locale, formatWidth);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new IcuMeasureFormatTokenFilter(tokenStream, measureFormat);
    }

    @Override
    public Object getMultiTermComponent() {
        return this;
    }
}
