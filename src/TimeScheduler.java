/*
 * TimeSchedulerŬ������ ������ ���� �����Ѵ�.
 * 1. Visitor������ ���� �� ��ȭ�� ����������� ���Ѵ�.
 * 2. ����ȭ�� ����������� ����, �� �ð��뺰 �����층�� �����Ѵ�.
 * 3. �� �ð��뺰 �����층�� ������ ���� ������ ����ȴ�.
 * 		3-0. �� �����층���� ���� �������� �ִٸ� ������.
 * 		3-1. �� �ð��뿡 ��ȭ�� ���� �ð��� �ִ��� �ľ�. ���ٸ� �ð��뺰 �����층 ����.
 * 		3-2. �켱���� ť�� ���� ���� ���� ���� ����������� ���� ��ȭ�� ���� ȿ������ ���� ��ġ.
 * 		3-3. �켱���� ť�� ä�� ��������ŭ �� ��ȭ������ �ٽ� �Է�
 * 		3-4. 3-1 Ż�����Ǳ��� �ݺ�.
 * 		3-5. Ż�� �� ������������ ��ȭ�� ���� �����층�� �������� ������.
 *
 *�����ؾ�����: ���� ��� ��ȭ�� ���������� 0�̵ȴٸ�, while�� Ż��.
 *			function������
 */

import java.util.PriorityQueue;

public class TimeScheduler {
	final static int SCREEN = 6;  // �� ��ũ���� ���� 6��
	private TimeTable timeTable;
	private Movie[] remainAudience;
			
	TimeScheduler(){		}		
	TimeScheduler( Movie[] movieList, int visitor, TimeTable timeTable ){
		this.timeTable = timeTable;
		remainAudience = new Movie[movieList.length];
		initmovieList(remainAudience, movieList);
		getExpectedAudience(movieList, visitor);
		scheduling(movieList);
		
	}
	private void initmovieList(Movie[] remainList, Movie[] movieList){
		for(int i = 0; i<movieList.length;i++){
			remainAudience[i] = new Movie();
			remainAudience[i].setMovieName(movieList[i].getMovieName());
			remainAudience[i].setExpectedAudience(0);
		}
	}
	
	private void getExpectedAudience(Movie[] movieList, int visitor){ //�Ѱ�����(visitor)�� �̿��Ͽ� ��ȭ���� ��������� ���Ѵ�.
		for(int i = 0 ; i < movieList.length ; i++){
			int tmp = (int)(movieList[i].getPredictRate() * visitor);
			movieList[i].setExpectedAudience(tmp);;  
		}	
	}
	private void scheduling(Movie[] movieList){ //��ȭ ����Ʈ�� ������, �����층�� �����Ѵ�.
		for(TimePreferenceInfo tpInfo : TimePreferenceInfo.values() ){ // �ð���ȣ���� ���� ���ð��� ���ɵ⸵.
			String startTime = tpInfo.getStartTime();
			String endTime = tpInfo.getEndTime();
			double ratio = tpInfo.getRatio();
			schedulingByTime(movieList, ratio, startTime, endTime);
		}
	}
	
	//�ð��뺰�� �����층�� �����Ѵ�. �ð��� ��ȣ��(ratio), �ð��� ���۽ð�(starttime), �ð��� �����½ð�(endTime)
	//�ð��밡 �� ����, �����층�� �����ϰ� 
	private void schedulingByTime(Movie[] movieList, double ratio, String startTime, String endTime){
		Movie[] tmpMovieList = hardCopyMovieList(movieList);
		PriorityQueue<Movie> movieQueue = new PriorityQueue<Movie>(); 
		///��ȭ���� �ð��뺰 ������ �Է��ϰ�, �̸����� �켱����ť�� �ִ´�.
		for(int i = 0; i < tmpMovieList.length; i++){
			int tmp =(int)(tmpMovieList[i].getExpectedAudience() * ratio); 
			tmpMovieList[i].setExpectedAudience(tmp);
			movieQueue.offer(tmpMovieList[i]);
		}
		
		addRemainAudience(tmpMovieList);
		
		while(!timeTable.timeFull(startTime,endTime)){
			Movie highDemandMovie = movieQueue.poll();
			int higheff_screen;
			int resetAudience; 
			int flag = 0;
			
			for(int i =0; i< tmpMovieList.length; i++){
				if(tmpMovieList[i].getExpectedAudience() != 0){
					flag = 1;
				}
			}
			if(flag == 0){
				timeTable.closeScreen(startTime, endTime);
				break;
			}
			
			ScreenEfficiency screen = new ScreenEfficiency(highDemandMovie, startTime, endTime, timeTable);
			higheff_screen = screen.getHighEffScreen();
			resetAudience = setResetAudience(highDemandMovie, screen);
			highDemandMovie.setExpectedAudience(resetAudience);
			timeTable.setMovie(highDemandMovie, higheff_screen);
			movieQueue.offer(highDemandMovie);
		}
		
		for(int i =0; i< tmpMovieList.length; i++){
				remainAudience[i] = tmpMovieList[i];
		}
		return;
	}
	private void addRemainAudience(Movie[] movieList){
		
		for(int i = 0; i < movieList.length; i++){
			for(int j =0; j < remainAudience.length; j++){
				if(movieList[i].getMovieName().compareTo(remainAudience[j].getMovieName())== 0){
					int tmp = remainAudience[j].getExpectedAudience() + movieList[i].getExpectedAudience();
					movieList[i].setExpectedAudience(tmp);
				}
			}
		}
	}
	
	private int setResetAudience(Movie movie, ScreenEfficiency screen){
		int resetAudience;
		if(movie.getExpectedAudience() > screen.reducedAudience){
			resetAudience = movie.getExpectedAudience() - screen.reducedAudience;
		}
		else{resetAudience = 0;}
		return resetAudience;
		
	}
	
	private Movie[] hardCopyMovieList(Movie[] movieList){
		Movie[] list = new Movie[movieList.length];
		for(int i = 0; i< movieList.length; i++){
			try {
				list[i] = movieList[i].clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
}

	

