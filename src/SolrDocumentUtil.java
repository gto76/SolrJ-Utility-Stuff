package si.gto76.solrjutilitystuff;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.solr.common.SolrDocument;

/**
 * Static class for basic manipulation of SolrJ's SolrDocument data structure.
 * @author Jure Sorn
 *
 */
public class SolrDocumentUtil {
	// Noise prevents scores from being equal,
	// that helps at shaving results..
	public static final double NOISE_AMPLITUDE = 0.0001;
	public static final boolean ADD_NOISE = false;
	private static final Random rand = new Random();

	public static double getScore(SolrDocument doc) {
		Object scoreO = doc.getFieldValue("score");
		Double scoreD = new Double((Float) scoreO);
		return scoreD;
	}

	public static void setScore(SolrDocument doc, double score) {
		doc.put("score", (float) score);
	}

	public static void multiplyScore(SolrDocument doc, double factor) {
		double oldScore = getScore(doc);
		if (ADD_NOISE) {
			factor += factor * (rand.nextDouble() * NOISE_AMPLITUDE);
		}
		setScore(doc, oldScore * factor);
	}

	public static void addScore(SolrDocument doc, double addend) {
		double oldScore = getScore(doc);
		setScore(doc, oldScore + addend);
	}

	public static void devideScoreBy(SolrDocument doc, double divider) {
		double oldScore = getScore(doc);
		setScore(doc, oldScore / divider);
	}

	public static String getId(SolrDocument doc, String idFieldName) {
		String idS = (String) doc.getFieldValue(idFieldName);
		return idS;
	}

	public static void removeOtherFields(SolrDocument d, Set<String> fields) {
		Collection<String> fieldNames = new HashSet<String>(d.getFieldNames());
		for (String fieldName : fieldNames) {
			// if field is not listed among fields, then delete it
			if ((!fields.contains(fieldName))
					&& (!fieldName.equals("score"))) {
				d.removeFields(fieldName);
			}
		}
	}

}
