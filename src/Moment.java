public class Moment {
	String title;
	String content;// 40 字
	String dat;
	int mntNum;// 表示用户目前有几条朋友圈

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