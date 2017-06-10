/*
 * TimeManager는 다음과 같은 일을 한다.
 * 1.주중과 주말을 파악하여 방문객수 변수로 저장.
 * 2.주중과 주말을 파악하여 TimeTable 변수로 저장.
 * 3.1,2를 통해 얻은 visitor와, timeTable정보를 바탕으로 스케쥴링 진행.
 * 4.TimeTable 출력을 통한 저장.
 */

import java.util.Calendar;

public class TimeManager{
	final static int SCREEN = 6;  // 총 스크린의 수는 6개
	private TimeTable timeTable;
	private int visitor; 
	private TimeScheduler timeScheduler;
	TimeManager(Movie[] movieList){ // 마지막에 timeManager가 timeTable출력까지 담당할것
		setVisitor();
		setTimeTable();
		timeScheduler = new TimeScheduler(movieList, visitor, timeTable);
		//
		timeTable.printTimeTable();
	}
	
	private void setVisitor(){
		Calendar cal = Calendar.getInstance();
		if(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
			visitor = VisitorInfo.Weekend.getVisitor();
		}
		else{
			visitor = VisitorInfo.WeekDay.getVisitor();
		}
		return;
	}
	private void setTimeTable(){
		Calendar cal = Calendar.getInstance();
		if(cal.get(Calendar.DAY_OF_WEEK)  == 1 || cal.get(Calendar.DAY_OF_WEEK)  == 7){
			timeTable = new TimeTable("weekend");
		}
		else{
			timeTable = new TimeTable("weekday");
		}
	}
}
