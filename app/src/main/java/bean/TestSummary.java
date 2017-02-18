package bean;
/**
 * 试题总的描述
 * @author Administrator
 *
 */

public class TestSummary {
	//题库试题总数目
	long count;
	//已完成试题
	long hasFinished;
	//正确率
	double percent;



	public TestSummary() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TestSummary(long number, long finishe_number, double percent2) {
		count=number;
		hasFinished=finishe_number;
		percent=percent2;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long  getHasFinished() {
		return hasFinished;
	}
	public void setHasFinished(long  hasFinished) {
		this.hasFinished = hasFinished;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}


}
