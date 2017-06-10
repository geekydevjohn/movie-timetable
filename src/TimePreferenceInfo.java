/*
 * TimePreferenceInfo���� �� �ð��뺰 ��ȣ����, �ð��� ������ ����ȴ�.
 * 1.�� �ð��뺰 ��ȣ��(rate)
 * 2.�ð��� ���۽ð�
 * 3.�ð��� ���ð�
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
