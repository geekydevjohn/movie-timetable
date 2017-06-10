

/*
 * TimeTable은 다음과 같은 정보를 저장한다.
 * 0.타임테이블은 주중, 주말을 인자로 받아 각각 다른 상영 시작시간을 갖는다.
 * 1.String 2차배열로 저장되며, 세로 셀은 각 셀당 10분을 의미한다. 00:00부터 익일 28시까지 표현하고 있다.
 * 2.만약 영화가 저장된다면, 해당 시간대에는 영화의 이름이 저장된다.
 * 3.청소시간이 저장된다면, 해당 시간대에는 'CLEAN'이라는 String이 저장된다.
 * 4.만약 입력이 불가한 시간(시작시간 이전)이라면, '0'을 넣는다.
 * 5.만약 시간대에 요구량이 더없다면 빈 시간은 다 '0'을 채운다.
 */
public class TimeTable {
	final static int SCREEN = 6;
	final static int CLOSINGTIME = 28; // 익일 4시
	private String[][] timeTable = new String[CLOSINGTIME * 6][SCREEN];


	TimeTable(String dayInfo){
		//weekday면 multiplexInfo에서 주중 시작시간을 가져온다
		//weekend면 multiplexInfo에서 주말 시작시간을 가져온다.
		initStartInfo(dayInfo);
	}
	
	private void initStartInfo(String dayInfo){
		if(dayInfo.compareTo("weekday")==0){
			for(MultiplexInfo info : MultiplexInfo.values()){
				int index = getTimeIndex(info.getStartTime_weekDay());
				for(int i=0; i<index; i++){
					timeTable[i][info.getScreenNum()] = "0";
				}
			}
		}
		else if(dayInfo.compareTo("weekend")==0){
			for(MultiplexInfo info : MultiplexInfo.values()){
				int index = getTimeIndex(info.getStartTime_weekend());
				for(int i=0; i<index; i++){
					timeTable[i][info.getScreenNum()] = "0";
				}
			}
		}
	}
	private void initByDay(String startTime){ //initStartInfo 하위 메소드
		for(MultiplexInfo info : MultiplexInfo.values()){
			int index = getTimeIndex(startTime);
			for(int i=0; i<index; i++){
				timeTable[i][info.getScreenNum()] = "0";
			}
		}
	}
	
	public void setMovie(Movie movie, int screen){ //청소시간 10분, start시간에 영화 입력
		int index = findIndex(screen);
		int runTime = movie.getRunningTime() / 10; //126분이라면 130으로 올림한다.
		if(movie.getRunningTime() % 10 != 0){ runTime = runTime + 1;}
		if(index != -1){
			for(int i = 0 ; i<runTime; i++){
				timeTable[index++][screen] = movie.getMovieName();
			}
			timeTable[index][screen] = "CLEAN";
		}
	}
	
	private int findIndex(int screenNum){
		for(int i = 0; i < CLOSINGTIME * 6 ; i++){
			if(timeTable[i][screenNum] == null){return i;}
		}
		return -1; // full
	}
	
	public boolean timeFull(String startTime, String endTime){ //원하는 시간대에 초기화안된곳이 있는지 찾는다.
		// startTime 구하는 공식 예, 0930 시작이라면, startTimeIndex는,먼저 시간을 구해서 시간 * 6을 해줌(각 셀은 10분 간격이므로)
				// 같은 방식으로 930 % 100 을 통해 30의 나머지를 구하고, 10을 나눔으로 3을 구함. 각각 더하면 index가나옴.
				int startTimeIndex = getTimeIndex(startTime);
				int endTimeIndex = getTimeIndex(endTime);
				
				int tmp = 0;
				
				for(tmp = startTimeIndex ; tmp < endTimeIndex ; tmp++){				
					for(int i = 0; i < SCREEN ; i++){
						if(timeTable[tmp][i] == null){return false;} // 아직 안차있다. 
					}
				}
				// 빠져나오면 다 차있다.
				return true;
		
	}
	
	public boolean screentimeFull(int screenNum, String startTime, String endTime){
		int startTimeIndex = getTimeIndex(startTime);
		int endTimeIndex = getTimeIndex(endTime);
		
		int tmp = 0;
		tmp = startTimeIndex;
		for(;tmp<endTimeIndex;tmp++){
			if(timeTable[tmp][screenNum] == null){return false;} // 아직 안차있다.
		}
		return true;
	}
	
	public void closeScreen(String startTime, String endTime){
		int sIndex = getTimeIndex(startTime);
		int eIndex = getTimeIndex(endTime);
		
		int tmp = sIndex;
		for(;tmp<eIndex;tmp++){
			for(int i=0; i< SCREEN; i++){
				if(timeTable[tmp][i] == null){timeTable[tmp][i] = "0";} 
			}
		}
		
	}
	private int getTimeIndex(String time){
		return (Integer.parseInt(time)/100) * 6 + (Integer.parseInt(time)%100) / 10;
	}
	
	public void printTimeTable(){
		
		for(int j = 48 ; j < 144 ; j++){
			if(j % 6 == 0){
			System.out.print(j/6);
			System.out.print("\t");
			}
			else
				System.out.print("\t");
			for(int i = 0 ; i < SCREEN ; i++){
				System.out.print(timeTable[j][i]);
				System.out.print("\t");
			}
			System.out.println();
		}
	}
}
