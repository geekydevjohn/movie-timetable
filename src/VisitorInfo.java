/*
 * VisitorInfo에는 방문자수가 저장된다.
 * 방문자수는 주중과 주말이 다르다.(약 2배)
 */
public enum VisitorInfo {
	Weekend(3000),
	WeekDay(9000);
	
	private int visitor;
	
	VisitorInfo(int visitor){
		this.visitor = visitor;
	}
	
	int getVisitor(){
		return visitor;
	}
}
