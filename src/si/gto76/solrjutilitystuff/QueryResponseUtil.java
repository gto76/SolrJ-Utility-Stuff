package si.gto76.solrjutilitystuff;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;


/**
 * Static class for basic manipulation of SolrJ's QueryResponse data structure.
 * @author Jure Sorn
 *
 */
public class QueryResponseUtil {

	/**
	 * Removes highlights that are without document.
	 * @param qr
	 */
	public static void removeRedundantHighlights(QueryResponse qr) {
		SolrDocumentList ddd = qr.getResults();
		Map<String, Map<String, List<String>>> hl = qr.getHighlighting();
		Set<String> ids = new HashSet<String>(hl.keySet());
		for (String id : ids) {
			if (!SolrDocumentListUtil.containsDocument(ddd, id)) {
				hl.remove(id);
			}
		}
	}

	public static boolean containsDocument(QueryResponse qr, String id,
			String idFieldName) {
		return SolrDocumentListUtil.containsDocument(qr.getResults(), id, idFieldName);
	}

	public static void orderByScore(QueryResponse qr) {
		SolrDocumentListUtil.orderByScore(qr.getResults());
	}
	
	public static void combineDocumentsWithSameValuesForField(
			QueryResponse qr, String fieldName) {
		SolrDocumentListUtil.combineDocumentsWithSameValuesForField(qr.getResults(),
				fieldName);
	}

	public static void divideScoresBy(QueryResponse qr, double divider) {
		SolrDocumentListUtil.devideAllScoresBy(qr.getResults(), divider);
	}

	public static void multiplyScoresBy(QueryResponse qr, double factor) {
		SolrDocumentListUtil.multiplyScores(qr.getResults(), factor);
	}
	
	public static void removeDocumentsWithScoreBelow(QueryResponse qr,
			double threshold) {
		SolrDocumentListUtil.removeDocumentsWithScoreBelow(qr.getResults(), threshold);
	}
	
	public static void removeOtherFields(QueryResponse qr, Set<String> fields) {
		SolrDocumentListUtil.removeOtherFields(qr.getResults(), fields);
	}

	public static SolrDocument getDocument(QueryResponse qr, String id,
			String idFieldName) {
		return SolrDocumentListUtil.getDocument(qr.getResults(), id, idFieldName);
	}

}
