/*
 * VisitorInfo���� �湮�ڼ��� ����ȴ�.
 * �湮�ڼ��� ���߰� �ָ��� �ٸ���.(�� 2��)
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
