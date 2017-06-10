
public class Manager {
	private Movie MovieList[] = new Movie[6];
	private TimeManager a;
	Manager(){
		setMovieList();
		a = new TimeManager(MovieList);
	}
	
	void setMovieList(){
		MovieList[0] = new Movie(0.4, 120, "영화1" );
		MovieList[1] = new Movie(0.3795, 150, "영화2" );
		MovieList[2] = new Movie(0.15, 90, "영화3" );
		MovieList[3] = new Movie(0.05, 100, "영화4" );
		MovieList[4] = new Movie(0.05, 110, "영화5" );
		MovieList[5] = new Movie(0.05, 130, "영화6" );
		
	};
	Movie[] getMovieList(int index){
		return MovieList;
	};
}
