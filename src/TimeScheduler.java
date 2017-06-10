/*
 * TimeScheduler클래스는 다음과 같은 일을한다.
 * 1. Visitor정보를 통해 각 영화별 예상관객수를 구한다.
 * 2. 각영화별 예상관객수를 통해, 각 시간대별 스케쥴링을 진행한다.
 * 3. 각 시간대별 스케쥴링은 다음과 같은 순으로 진행된다.
 * 		3-0. 전 스케쥴링에서 남은 관객수가 있다면 더해줌.
 * 		3-1. 각 시간대에 영화를 넣을 시간이 있는지 파악. 없다면 시간대별 스케쥴링 종료.
 * 		3-2. 우선순위 큐를 통해 가장 많은 수의 예상관객수를 가진 영화를 가장 효율적인 관에 배치.
 * 		3-3. 우선순위 큐에 채운 관객수만큼 뺀 영화정보가 다시 입력
 * 		3-4. 3-1 탈출조건까지 반복.
 * 		3-5. 탈출 후 만족하지못한 영화는 다음 스케쥴링에 관객수를 더해줌.
 *
 *수정해야할점: 만약 모든 영화의 기대관객수가 0이된다면, while문 탈출.
 *			function나누기
 */

import java.util.PriorityQueue;

public class TimeScheduler {
	final static int SCREEN = 6;  // 총 스크린의 수는 6개
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
	
	private void getExpectedAudience(Movie[] movieList, int visitor){ //총관객수(visitor)을 이용하여 영화마다 예상관객수 구한다.
		for(int i = 0 ; i < movieList.length ; i++){
			int tmp = (int)(movieList[i].getPredictRate() * visitor);
			movieList[i].setExpectedAudience(tmp);;  
		}	
	}
	private void scheduling(Movie[] movieList){ //영화 리스트를 가지고, 스케쥴링을 진행한다.
		for(TimePreferenceInfo tpInfo : TimePreferenceInfo.values() ){ // 시간선호도를 통해 각시간대 스케듈링.
			String startTime = tpInfo.getStartTime();
			String endTime = tpInfo.getEndTime();
			double ratio = tpInfo.getRatio();
			schedulingByTime(movieList, ratio, startTime, endTime);
		}
	}
	
	//시간대별로 스케쥴링을 진행한다. 시간대 선호도(ratio), 시간대 시작시간(starttime), 시간대 끝나는시간(endTime)
	//시간대가 꽉 차면, 스케쥴링을 종료하고 
	private void schedulingByTime(Movie[] movieList, double ratio, String startTime, String endTime){
		Movie[] tmpMovieList = hardCopyMovieList(movieList);
		PriorityQueue<Movie> movieQueue = new PriorityQueue<Movie>(); 
		///영화마다 시간대별 관객수 입력하고, 이를통해 우선순위큐에 넣는다.
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

	

