/*
 * TimeManager�� ������ ���� ���� �Ѵ�.
 * 1.���߰� �ָ��� �ľ��Ͽ� �湮���� ������ ����.
 * 2.���߰� �ָ��� �ľ��Ͽ� TimeTable ������ ����.
 * 3.1,2�� ���� ���� visitor��, timeTable������ �������� �����층 ����.
 * 4.TimeTable ����� ���� ����.
 */

import java.util.Calendar;

public class TimeManager{
	final static int SCREEN = 6;  // �� ��ũ���� ���� 6��
	private TimeTable timeTable;
	private int visitor; 
	private TimeScheduler timeScheduler;
	TimeManager(Movie[] movieList){ // �������� timeManager�� timeTable��±��� ����Ұ�
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
