
public class Manager {
	private Movie MovieList[] = new Movie[6];
	private TimeManager a;
	Manager(){
		setMovieList();
		a = new TimeManager(MovieList);
	}
	
	void setMovieList(){
		MovieList[0] = new Movie(0.4, 120, "��ȭ1" );
		MovieList[1] = new Movie(0.3795, 150, "��ȭ2" );
		MovieList[2] = new Movie(0.15, 90, "��ȭ3" );
		MovieList[3] = new Movie(0.05, 100, "��ȭ4" );
		MovieList[4] = new Movie(0.05, 110, "��ȭ5" );
		MovieList[5] = new Movie(0.05, 130, "��ȭ6" );
		
	};
	Movie[] getMovieList(int index){
		return MovieList;
	};
}
