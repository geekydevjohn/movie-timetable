
public class Movie implements Comparable<Movie>, Cloneable{
	private double predictRate; //필수
	private int runningTime;	//필수
	private String MovieName;	//필수
	private int expectedAudience;
	Movie(){}
	Movie(double predictRate, int runningTime, String MovieName){
		this.predictRate = predictRate;
		this.runningTime = runningTime;
		this.MovieName = MovieName;
	}
	public void setExpectedAudience(int expectedAudience){
		this.expectedAudience = expectedAudience;
	}
	public void setMovieName(String name){this.MovieName = name;}
	public int getExpectedAudience(){return expectedAudience;}
	public double getPredictRate(){return predictRate;}
	public int getRunningTime(){return runningTime;}
	public String getMovieName(){return MovieName;}
	@Override
	public int compareTo(Movie cmp_movie) {
		if(this.expectedAudience >= cmp_movie.getExpectedAudience()){
			
			return -1;
		}
		else
			return 1;
	}
	
	public Movie clone() throws CloneNotSupportedException{
		Movie movie =(Movie)super.clone();
		movie.predictRate = this.predictRate;
		movie.MovieName = this.MovieName;
		movie.expectedAudience = this.expectedAudience;
		movie.runningTime = this.runningTime;
		return movie;
	}
	
}
