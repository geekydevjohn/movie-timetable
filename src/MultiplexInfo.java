
/*
 * MultipPlexInfo에는 각 스크린 별 정보가 저장된다
 * 1. 각 스크린별 좌석정보
 * 2. 각 스크린별 주중 시작 시간
 * 3. 각 스크린별 주말 시작 시간
 */
public enum MultiplexInfo {
	Screen1(158, "0900", "0800",0),
	Screen2(124, "1000", "0900",1),
	Screen3(172, "0800", "0910",2),
	Screen4(124, "0900", "0840",3),
	Screen5(172, "1000", "0820",4),
	Screen6(124, "1100", "0830",5);
	
	private int seat;
	private String startTime_weekDay;
	private String startTime_weekend;
	private int screenNum;
	
	MultiplexInfo(int seat, String startTime_weekDay, String startTime_weekend,int screenNum){
		this.seat = seat;
		this.startTime_weekDay = startTime_weekDay;
		this.startTime_weekend = startTime_weekend;
		this.screenNum = screenNum;
	}
	
	int getSeat(){
		return seat;
	}
	String getStartTime_weekDay(){
		return startTime_weekDay;
	}
	String getStartTime_weekend(){
		return startTime_weekend;
	}
	int getScreenNum(){
		return screenNum;
	}
}
