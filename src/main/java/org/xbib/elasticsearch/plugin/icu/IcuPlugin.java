package org.xbib.elasticsearch.plugin.icu;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.index.analysis.CharFilterFactory;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;
import org.xbib.elasticsearch.index.analysis.icu.IcuCollationKeyAnalyzerProvider;
import org.xbib.elasticsearch.index.analysis.icu.IcuCollationTokenizerFactory;
import org.xbib.elasticsearch.index.analysis.icu.IcuFoldingCharFilterFactory;
import org.xbib.elasticsearch.index.analysis.icu.IcuFoldingTokenFilterFactory;
import org.xbib.elasticsearch.index.analysis.icu.IcuMeasureFormatTokenFilterFactory;
import org.xbib.elasticsearch.index.analysis.icu.IcuNormalizerCharFilterFactory;
import org.xbib.elasticsearch.index.analysis.icu.IcuNormalizerTokenFilterFactory;
import org.xbib.elasticsearch.index.analysis.icu.IcuNumberFormatTokenFilterFactory;
import org.xbib.elasticsearch.index.analysis.icu.IcuTransformTokenFilterFactory;
import org.xbib.elasticsearch.index.analysis.icu.segmentation.IcuTokenizerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class IcuPlugin extends Plugin implements AnalysisPlugin {

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<CharFilterFactory>> getCharFilters() {
        Map<String, AnalysisModule.AnalysisProvider<CharFilterFactory>> extra = new LinkedHashMap<>();
        extra.put("icu_normalizer", IcuNormalizerCharFilterFactory::new);
        extra.put("icu_folding", IcuFoldingCharFilterFactory::new);
        return extra;
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
        Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> extra = new LinkedHashMap<>();
        extra.put("icu_normalizer", IcuNormalizerTokenFilterFactory::new);
        extra.put("icu_folding", IcuFoldingTokenFilterFactory::new);
        extra.put("icu_transform", IcuTransformTokenFilterFactory::new);
        extra.put("icu_numberformat", IcuNumberFormatTokenFilterFactory::new);
        extra.put("icu_measureformat", IcuMeasureFormatTokenFilterFactory::new);
        return extra;
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
        Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> extra = new LinkedHashMap<>();
        extra.put("icu_collation_tokenizer", IcuCollationTokenizerFactory::new);
        extra.put("icu_tokenizer", IcuTokenizerFactory::new);
        return extra;
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> extra = new LinkedHashMap<>();
        extra.put("icu_collation", IcuCollationKeyAnalyzerProvider::new);
        return extra;
    }
}
