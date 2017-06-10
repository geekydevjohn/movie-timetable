

/*
 * TimeTable�� ������ ���� ������ �����Ѵ�.
 * 0.Ÿ�����̺��� ����, �ָ��� ���ڷ� �޾� ���� �ٸ� �� ���۽ð��� ���´�.
 * 1.String 2���迭�� ����Ǹ�, ���� ���� �� ���� 10���� �ǹ��Ѵ�. 00:00���� ���� 28�ñ��� ǥ���ϰ� �ִ�.
 * 2.���� ��ȭ�� ����ȴٸ�, �ش� �ð��뿡�� ��ȭ�� �̸��� ����ȴ�.
 * 3.û�ҽð��� ����ȴٸ�, �ش� �ð��뿡�� 'CLEAN'�̶�� String�� ����ȴ�.
 * 4.���� �Է��� �Ұ��� �ð�(���۽ð� ����)�̶��, '0'�� �ִ´�.
 * 5.���� �ð��뿡 �䱸���� �����ٸ� �� �ð��� �� '0'�� ä���.
 */
public class TimeTable {
	final static int SCREEN = 6;
	final static int CLOSINGTIME = 28; // ���� 4��
	private String[][] timeTable = new String[CLOSINGTIME * 6][SCREEN];


	TimeTable(String dayInfo){
		//weekday�� multiplexInfo���� ���� ���۽ð��� �����´�
		//weekend�� multiplexInfo���� �ָ� ���۽ð��� �����´�.
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
	private void initByDay(String startTime){ //initStartInfo ���� �޼ҵ�
		for(MultiplexInfo info : MultiplexInfo.values()){
			int index = getTimeIndex(startTime);
			for(int i=0; i<index; i++){
				timeTable[i][info.getScreenNum()] = "0";
			}
		}
	}
	
	public void setMovie(Movie movie, int screen){ //û�ҽð� 10��, start�ð��� ��ȭ �Է�
		int index = findIndex(screen);
		int runTime = movie.getRunningTime() / 10; //126���̶�� 130���� �ø��Ѵ�.
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
	
	public boolean timeFull(String startTime, String endTime){ //���ϴ� �ð��뿡 �ʱ�ȭ�ȵȰ��� �ִ��� ã�´�.
		// startTime ���ϴ� ���� ��, 0930 �����̶��, startTimeIndex��,���� �ð��� ���ؼ� �ð� * 6�� ����(�� ���� 10�� �����̹Ƿ�)
				// ���� ������� 930 % 100 �� ���� 30�� �������� ���ϰ�, 10�� �������� 3�� ����. ���� ���ϸ� index������.
				int startTimeIndex = getTimeIndex(startTime);
				int endTimeIndex = getTimeIndex(endTime);
				
				int tmp = 0;
				
				for(tmp = startTimeIndex ; tmp < endTimeIndex ; tmp++){				
					for(int i = 0; i < SCREEN ; i++){
						if(timeTable[tmp][i] == null){return false;} // ���� �����ִ�. 
					}
				}
				// ���������� �� ���ִ�.
				return true;
		
	}
	
	public boolean screentimeFull(int screenNum, String startTime, String endTime){
		int startTimeIndex = getTimeIndex(startTime);
		int endTimeIndex = getTimeIndex(endTime);
		
		int tmp = 0;
		tmp = startTimeIndex;
		for(;tmp<endTimeIndex;tmp++){
			if(timeTable[tmp][screenNum] == null){return false;} // ���� �����ִ�.
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
