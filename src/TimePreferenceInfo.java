/*
 * TimePreferenceInfo에는 각 시간대별 선호도와, 시간대 정보가 저장된다.
 * 1.각 시간대별 선호도(rate)
 * 2.시간대 시작시간
 * 3.시간대 끝시간
 */
public enum TimePreferenceInfo {
	_8to11(0.16, "0080", "1100"),
	_11to15(0.28, "1100", "1500"),
	_15to19(0.38, "1500", "1900"),
	_19to23(0.17, "1900", "2300");
	private double ratio;
	private String startTime;
	private String endTime;
	TimePreferenceInfo(double ratio, String startTime, String endTime){
		this.ratio = ratio;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public String getStartTime(){return this.startTime;}
	public String getEndTime(){return this.endTime;}
	public double getRatio(){return this.ratio;}
}
