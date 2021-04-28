import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	
	private static String mainPath() {
		return System.getProperty("user.dir");
	}
	
	private static String resourcePath() {
		return mainPath() + "/resources/";
	}
	
	private static String getMegaFile(String filePath) {
		return filePath + "Mega.csv";
	}

	public static void main(String[] args) {
		String opt = args[0];
		String num = args[1];
//		String opt = "mega";
//		String num = "15";
		Map<String, String> analysisMap = new HashMap<>();
		List<List<String>> lines = CsvParser.csvToArray(getMegaFile(resourcePath()));
		switch(opt.toLowerCase()) {
		case "mega":analysisMap = getAnalysis("mega",num,lines);break;
		case "pick5":analysisMap = getAnalysis("pick5",num,lines);break;
		}
		System.out.println(analysisMap);
	}
	
	private static Map<String,String> getAnalysis(String option, String tgtNum, List<List<String>> lines) {
		Map<String, String> analysisMap = new HashMap<>();
		analysisMap.put("LAST_PICK_DAYS", "");
		analysisMap.put("TIMES_PICKED", "");
		analysisMap.put("FREQUENCE_PICKED", "");
		analysisMap.put("AVG_FREQUENCE", "");
		analysisMap.put("LONGEST_FREQUENCE", "");
		analysisMap.put("DATES_PICKED", "");
		if (lines.size() > 0) {
			int colRangeFrom =0,colRangeTo=0;
			switch(option) {
			case "mega":
				colRangeFrom = 6;
				colRangeTo = 6;
				break;
			case "pick5":
				colRangeFrom = 1;
				colRangeTo = 5;
				break;
			}
			int timesPicked = 0;
			for (int i=0;i<lines.size();i++) {
				for (int j = 0;j<lines.get(0).size();j++) {
					if(j>=colRangeFrom&&j<=colRangeTo) {
						String actualVal = lines.get(i).get(j);
						if(actualVal.equals(tgtNum)) {
							String date = analysisMap.get("DATES_PICKED") + lines.get(i).get(0) + ",";
							analysisMap.put("DATES_PICKED", date);
							timesPicked++;
							break;
						}
					}
				}
			}
			analysisMap.put("TIMES_PICKED", String.valueOf(timesPicked));
			String datesStr = analysisMap.get("DATES_PICKED");
			String[] datesArr = datesStr.split(",");
			String currentDate = DateUtils.getCurrentDate();
			String prevDate = "";
			int longest = 0;
			int sum = 0;
			for(String d:datesArr) {
				if(prevDate.equals("")) {
					String days = DateUtils.getDateDifference(d,currentDate);
					analysisMap.put("LAST_PICK_DAYS", days);
				}else {
					String days = DateUtils.getDateDifference(d,prevDate);
					int longestInt = Integer.valueOf(days);
					analysisMap.put("FREQUENCE_PICKED", days);
					if(longestInt>longest) longest = longestInt;
					sum = sum + longestInt;
				}
				prevDate = d;
			}
			analysisMap.put("LONGEST_FREQUENCE", String.valueOf(longest));
			int avg = sum / timesPicked;
			analysisMap.put("AVG_FREQUENCE", String.valueOf(avg));
		}
		
		return analysisMap;
	}

}
