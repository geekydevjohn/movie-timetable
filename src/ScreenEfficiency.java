

public class ScreenEfficiency {
	final static int SCREEN = 6;
	int highEff_screen;
	double highEff;
	int reducedAudience;
	
	ScreenEfficiency(Movie movie, String start, String end, TimeTable timeTable){
		setHighEffScreen(movie, start, end, timeTable);
		setReducedAudience();
	}
	
	public int getHighEffScreen(){
		return highEff_screen;
	}
	public int getReducedAudience(){
		return reducedAudience;
	}
	
	private void setHighEffScreen(Movie movie, String start, String end, TimeTable timeTable){
		double MAX_eff = 0;
		int screen = -1;
		for(int i = 0; i<SCREEN; i++){
			if(!timeTable.screentimeFull(i, start, end)){
				double tmp_eff = getEfficiency(movie,i);
				if(MAX_eff < tmp_eff){
					MAX_eff = tmp_eff;
					screen = i;
				}
			}
		}
		highEff = MAX_eff;
		highEff_screen = screen;
	}
	
	private double getEfficiency(Movie movie, int screenNum){
		/*
		 * �������� ���� ȿ���� ����, ȿ���� = ������/�¼��� , ���� 100�ۼ�Ʈ���  �¼����� �ѱ�(�¼��� �� 100�� �̻��̹Ƿ�)
		 */
		double eff;
		int audience = movie.getExpectedAudience();
		int seat = getScreenSeat(screenNum);
	
		if(audience >= seat){eff = seat;}
		else{
			eff = 100 * audience / seat ;
		}
		return eff;
		
	}
	private int getScreenSeat(int screenNum){
		for(MultiplexInfo info : MultiplexInfo.values()){
			if(info.getScreenNum()== screenNum){ return info.getSeat();}
		}
		return 0;
	}
	private void setReducedAudience(){
		if(highEff > 100){
			reducedAudience = (int)highEff;
		}
		else{
			reducedAudience = (int)(highEff * getScreenSeat(highEff_screen));
		}
	}
}








