public class Moment {
	String title;
	String content;// 40 ��
	String dat;
	int mntNum;// ��ʾ�û�Ŀǰ�м�������Ȧ

	Moment(String title, String content, String dat) {
		this.title = title;
		this.content = content;
		this.dat = dat;
	}

	public String toString() {
		return ("\r" + "title:" + title + "\t\t\t" + "dat:" + dat + "\r"
				+ "content:" + content);

	}

}