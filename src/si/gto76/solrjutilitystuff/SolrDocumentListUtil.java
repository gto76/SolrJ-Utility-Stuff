package si.gto76.solrjutilitystuff;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;


/**
 * Static class for basic manipulation of SolrJ's SolrDocumentList data structure.
 * @author Jure Sorn
 *
 */
public class SolrDocumentListUtil {

	public static void multiplyScores(SolrDocumentList ddd, double factor) {
		for (SolrDocument d : ddd) {
			SolrDocumentUtil.multiplyScore(d, factor);
		}
	}

	public static boolean containsDocument(SolrDocumentList ddd, String id) {
		return containsDocument(ddd, id, "id");
	}

	public static boolean containsDocument(SolrDocumentList ddd, String id,
			String idFieldName) {
		for (SolrDocument d : ddd) {
			String documentsId = SolrDocumentUtil.getId(d, idFieldName);
			if (documentsId == null)
				System.out.println("Solr Document's id is null!!!");
			if (documentsId.equals(id)) {
				return true;
			}
		}
		return false;
	}

	public static SolrDocument getDocument(SolrDocumentList ddd, String id,
			String idFieldName) {
		for (SolrDocument d : ddd) {
			if (SolrDocumentUtil.getId(d, idFieldName).equals(id)) {
				return d;
			}
		}
		return null;
	}

	public static void removeDocumentsWithScoreBelow(SolrDocumentList ddd,
			double threshold) {
		int size = ddd.size();
		for (int i = 0; i < size; i++) {
			SolrDocument doc = ddd.get(i);
			double score = SolrDocumentUtil.getScore(doc);
			if (score < threshold) {
				ddd.remove(i);
				i--;
				size--;
			}
		}
	}

	public static void removeOtherFields(SolrDocumentList ddd, Set<String> fields) {
		for (SolrDocument d : ddd) {
			SolrDocumentUtil.removeOtherFields(d, fields);
		}
	}

	public static String toString(SolrDocumentList ddd) {
		StringBuffer sb = new StringBuffer();
		for (SolrDocument d : ddd) {
			sb.append(d.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void orderByScore(SolrDocumentList ddd) {
		Comparator<SolrDocument> documentListComp = new ScoreComparator();
		Collections.sort(ddd, documentListComp);
	}

	public static void combineDocumentsWithSameValuesForField(
			SolrDocumentList sdl, String fieldName) {

		for (int i = 0; i < (sdl.size() - 1); i++) {
			SolrDocument documentA = sdl.get(i);
			String fieldValueA = SolrDocumentUtil.getId(documentA, fieldName);
			for (int j = i + 1; j < sdl.size(); j++) {
				SolrDocument documentB = sdl.get(j);
				String fieldValueB = SolrDocumentUtil.getId(documentB,
						fieldName);
				// If it already exists, then we add it's score to existing
				// one's score.
				if (fieldValueA.equals(fieldValueB)) {
					double scoreToAdd = SolrDocumentUtil.getScore(documentB);
					SolrDocumentUtil.addScore(documentA, scoreToAdd);
					sdl.remove(documentB);
					j--;
				}
			}
		}

	}

	public static void divideAllScoresBy(SolrDocumentList ddd, double divider) {
		for (SolrDocument d : ddd) {
			SolrDocumentUtil.divideScoreBy(d, divider);
		}
	}

}

class ScoreComparator implements Comparator<SolrDocument> {

	@Override
	public int compare(SolrDocument sd1, SolrDocument sd2) {

		Double sd1Score = SolrDocumentUtil.getScore(sd1);
		Double sd2Score = SolrDocumentUtil.getScore(sd2);

		if (sd1Score > sd2Score)
			return -1;
		else if (sd1Score < sd2Score)
			return 1;
		else
			return 0;
	}

}
