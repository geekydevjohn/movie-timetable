

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
		 * 각관에서 갖는 효율성 리턴, 효율성 = 관객수/좌석수 , 만약 100퍼센트라면  좌석수를 넘김(좌석이 다 100석 이상이므로)
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








